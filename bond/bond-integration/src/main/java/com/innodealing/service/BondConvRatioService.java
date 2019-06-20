package com.innodealing.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.innodealing.engine.jpa.dm.BondConvRatioRepository;
import com.innodealing.engine.mongo.bond.BondBasicInfoRepository;
import com.innodealing.model.dm.bond.BondConvRatio;
import com.innodealing.model.dm.bond.BondConvRatioKey;
import com.innodealing.model.dm.bond.BondDuration;
import com.innodealing.model.dm.bond.BondInfo;
import com.innodealing.model.mongo.dm.BondBasicInfoDoc;
import com.innodealing.model.mongo.dm.BondDetailDoc;
import com.innodealing.util.SafeUtils;

@Service
public class BondConvRatioService {

	private static final Logger LOG = LoggerFactory.getLogger(BondConvRatioService.class);

	@Autowired
	private ApplicationEventPublisher publisher;

	private static String WEBSITE = "http://www.chinaclear.cn";
	private static String URL_SH = "http://www.chinaclear.cn/zdjs/zshanhai/center_clist.shtml";
	private static String URL_SZ = "http://www.chinaclear.cn/zdjs/zshenzhen/center_clist.shtml";

	private static Map<String, ExcelParser> parserMap = new HashMap<String, ExcelParser>();
	{
		parserMap.put(URL_SH, new SHExcelParser());
		parserMap.put(URL_SZ, new SZExcelParser());
	}

	private @Autowired JdbcTemplate jdbcTemplate;
	private @Autowired MongoTemplate mongoTemplate;
	private @Autowired BondBasicInfoRepository bondBasicRepo;
	private @Autowired BondConvRatioRepository convRatioRepo;
	private final static String URL_MARK = "关于发布债券适用的标准券折算率的通知 ";
	private Date fileDate ;
	
	private void initFileDate() throws IOException
	{
		String resource = extractDownloadAddress(readHtmlPage(URL_SH));
		String fileName = resource.substring(resource.lastIndexOf('/') + 1);
		fileDate = SafeUtils.parseDate(fileName.substring(0, 8), "yyyyMMdd");
		LOG.info("折算率最新文件:" + fileName);
		LOG.info("折算前最新文件时间是:" + date2String(fileDate));
	}
	
	public String forceConvRatioRebuild() throws IOException 
	{
		initFileDate();
		removeExistingData();
		return integration();
	}
	
	public String convRatioRebuild() throws IOException 
	{
		initFileDate();
		if (isAlreadyBuilded()) {
			LOG.info("数据已经构建");
			return "数据已经构建";
		}
		return integration();
	}
	
	private String integration() throws IOException {

		ExecutorService pool = Executors.newCachedThreadPool();
		for (Entry<String, ExcelParser> entry : parserMap.entrySet()) {
			String url = entry.getKey();
			pool.execute(new Runnable() {
				@Override
				public void run() {
					try {
						integrateByMarket(url);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
		}
		pool.shutdown();
		try {
			pool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
			LOG.error("等待任务完成中发生异常 ", e);
			e.printStackTrace();
		}
		return "task done";
	}
	
	private boolean isAlreadyBuilded() throws IOException {
		
		List<BondConvRatio> convRatios = convRatioRepo.findLastestConvRatio();
		if (convRatios == null || convRatios.isEmpty()) {
			LOG.info("数据未保存，开始重新构建");
			return false;	
		}
		
		Date lastDate = convRatios.get(0).getConvRatioKey().getCreateDate();
		Date l = SafeUtils.removeTime(lastDate);
		Date n = SafeUtils.removeTime(fileDate);
		LOG.info("库中最新保存的文件时间:" + date2String(l) + ", 网站最新文件时间:" + date2String(n));
		if (l.compareTo(n) >= 0) {
			LOG.info("数据已经保存，无需重新构建");
			return true;
		}
		
		LOG.info("数据未保存，开始重新构建");
		return false;
	}

	public void integrateByMarket(String marketUrl) throws FileNotFoundException, IOException {
		String resource = extractDownloadAddress(readHtmlPage(marketUrl));

		URL url = new URL(new URL(WEBSITE), resource);
		String fileName = resource.substring(resource.lastIndexOf('/') + 1);
		File file = File.createTempFile(fileName, "xls");
		LOG.info("Temp file : " + file.getAbsolutePath());

		FileUtils.copyURLToFile(url, file);
		saveExcel2DB(getDateFromExcelName(fileName), file, parserMap.get(marketUrl));
	}

	private String getDateFromExcelName(String fileName) {
		return String.format("%1$s-%2$s-%3$s", fileName.substring(0, 4), fileName.substring(5, 6),
				fileName.substring(7, 8));
	}

	public static String readHtmlPage(String grabUrl) throws IOException {
		HttpClient httpclient = createHttpClient();

		HttpGet httpget = new HttpGet(grabUrl);
		httpget.setHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.1.2)");
		httpget.setHeader("Accept-Language", "zh-cn,zh;q=0.5");
		httpget.setHeader("Accept-Charset", "utf-8");

		String text = "";
		try {
			HttpResponse response = httpclient.execute(httpget);
			//LOG.info(response.getStatusLine().toString());

			HttpEntity entity = response.getEntity();
			if (entity != null) {
				byte[] bytes = EntityUtils.toByteArray(entity);
				text = new String(bytes, "utf-8");
			}
			httpget.abort();
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
		return text;
	}

	private static HttpClient createHttpClient() {
		HttpClient httpclient = new DefaultHttpClient();
		setHttpProxy(httpclient);
		return httpclient;
	}

	private static void setHttpProxy(HttpClient httpclient) {
		Object object = getEnv("http_proxy");
		if (null != object && object.toString().indexOf(":") != -1) {
			String str = object.toString();
			String[] strlist = str.split(":");
			String hostname = strlist[0];
			int port = Integer.parseInt(strlist[1]);
			HttpHost proxy = new HttpHost(hostname, port, "http");
			httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
		}
	}

	public static String extractDownloadAddress(String htmlPage) {
		String lines[] = htmlPage.split("\\r\\n|\\n|\\r");
		for (int i = 0; i < lines.length; ++i) {
			String line = lines[i];
			//LOG.info(line);
			if (line.contains(URL_MARK)) {
				Document doc = Jsoup.parse(line);
				Elements links = doc.select("li a");
				return links.get(0).attr("href");
			}
		}

		return null;
	}

	private static Object getEnv(String key) {
		Map map = System.getenv();
		Iterator it = map.entrySet().iterator();
		Object o = null;
		while (it.hasNext()) {
			Entry entry = (Entry) it.next();
			if (entry.getKey().equals(key)) {
				o = entry.getValue();
				break;
			}
		}
		return o;
	}

	interface ExcelParser {
		public List<BondConvRatio> parse(HSSFSheet sheet);
	}

	private BondConvRatio createConvRatio(String bondCode, String bondShortName, Double ratio, Integer startDate,
			Integer endDate, DateFormat excelDateFmt) {

		BondBasicInfoDoc bond = bondBasicRepo.findByCode(bondCode);
		if (bond == null) {
			bond = bondBasicRepo.findByShortName(bondShortName);
			if (bond == null) {
				String orgCode = bondCode.substring(0, bondCode.indexOf("."));
				bond = bondBasicRepo.findByOrgCode(orgCode);
				if (bond == null) {
					LOG.warn("bond not exist, code:" + bondCode + ", shortName:" + bondShortName);
					return null;
				}
			}
		}

		BondConvRatio convRatio = new BondConvRatio();
		BondConvRatioKey key = new BondConvRatioKey();
		key.setBondUniCode(bond.getBondUniCode());
		key.setCreateDate(SafeUtils.removeTime(fileDate));
		convRatio.setConvRatioKey(key);
		convRatio.setBondShortName(bond.getShortName());
		convRatio.setBondCode(bondCode);
		convRatio.setConvRatio(new BigDecimal(ratio));
		convRatio.setUpdateTime(new Date());
		synchronized (this) {
			try {
				String startDateStr = startDate.toString();
				String endDateStr = endDate.toString();
				convRatio.setStartDate(excelDateFmt.parse(startDateStr));
				convRatio.setEndDate(excelDateFmt.parse(endDateStr));
			} catch (Exception e) {
				e.printStackTrace();
				LOG.error("failed to parser date,", e);
			}
		}
		LOG.info("新建折算率，convRatio:" + convRatio.toString());
		return convRatio;
	}

	DateFormat excelDateFmt = new SimpleDateFormat("yyyyMMdd");

	class SHExcelParser implements ExcelParser {
		@Override
		public List<BondConvRatio> parse(HSSFSheet sheet) {
			List<BondConvRatio> entities = new ArrayList<BondConvRatio>();
			int firstRow = sheet.getFirstRowNum();
			int lastRow = sheet.getLastRowNum();
			for (int i = firstRow + 3; i < lastRow; i++) {
				try {
					HSSFRow row = sheet.getRow(i);
					if (row == null || row.getCell(0) == null)
						continue;
					String plainBondCode = row.getCell(0).getStringCellValue();
					if (!StringUtils.isEmpty(plainBondCode)) {
						String bondCode = plainBondCode + ".SH";
						String bondShortName = StringUtils.trim(row.getCell(1).getStringCellValue());
						Double ratio = row.getCell(2).getNumericCellValue();
						Integer startDate = (int) row.getCell(3).getNumericCellValue();
						Integer endDate = (int) row.getCell(4).getNumericCellValue();
						BondConvRatio convRatio = createConvRatio(bondCode, bondShortName, ratio, startDate, endDate,
								excelDateFmt);
						if (convRatio != null)
							entities.add(convRatio);
					}
				} catch (Exception e) {
					LOG.warn("failed to import conv ratio, maybe end of data", e);
					// break;
				}
			}
			return entities;
		}
	}

	class SZExcelParser implements ExcelParser {
		@Override
		public List<BondConvRatio> parse(HSSFSheet sheet) {
			List<BondConvRatio> entities = new ArrayList<BondConvRatio>();
			int firstRow = sheet.getFirstRowNum();
			int lastRow = sheet.getLastRowNum();
			for (int i = firstRow + 4; i < lastRow; i++) {
				try {
					HSSFRow row = sheet.getRow(i);
					if (row == null || row.getCell(0) == null)
						continue;
					String plainBondCode = row.getCell(1).getStringCellValue();
					if (!StringUtils.isEmpty(plainBondCode)) {
						String bondCode = plainBondCode + ".SZ";
						String bondShortName = StringUtils.trim(row.getCell(2).getStringCellValue());
						Double ratio = Double.valueOf(row.getCell(3).getStringCellValue());
						Integer startDate = Integer.valueOf(row.getCell(4).getStringCellValue().replace("-", ""));
						Integer endDate = Integer.valueOf(row.getCell(5).getStringCellValue().replace("-", ""));
						BondConvRatio convRatio = createConvRatio(bondCode, bondShortName, ratio, startDate, endDate,
								excelDateFmt);
						if (convRatio != null)
							entities.add(convRatio);
					}
				} catch (Exception e) {
					LOG.warn("failed to import conv ratio, maybe end of data", e);
					// break;
				}
			}
			return entities;
		}
	}

	public void saveExcel2DB(String date, File excelFile, ExcelParser parser)
			throws FileNotFoundException, IOException {

		// removeExistingData(date);

		HSSFWorkbook book = new HSSFWorkbook(new FileInputStream(excelFile));
		HSSFSheet sheet = book.getSheetAt(0);
		List<BondConvRatio> entities = parser.parse(sheet);

		List<List<BondConvRatio>> convRatioLists = Lists.partition(entities, 200);
		ExecutorService pool = Executors.newFixedThreadPool(20);
		for (List<BondConvRatio> convRatioList : convRatioLists) {
			pool.execute(new Runnable() {
				@Override
				public void run() {
					saveBatch(convRatioList);
				}
			});
		}
		pool.shutdown();
		try {
			pool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
			LOG.error("等待任务完成中发生异常 ", e);
			e.printStackTrace();
		}
	}

	public void saveBatch(final List<BondConvRatio> durationList) {
		final int batchSize = 500;
		for (int j = 0; j < durationList.size(); j += batchSize) {
			final List<BondConvRatio> batchList = durationList.subList(j,
					j + batchSize > durationList.size() ? durationList.size() : j + batchSize);
			jdbcTemplate.batchUpdate(
					"insert into dmdb.t_bond_conv_ratio (bond_uni_code, bond_short_name, bond_code, conv_ratio, start_date, end_date, create_date) values (?, ?, ?, ?, ?, ?, ?)",
					new BatchPreparedStatementSetter() {
						@Override
						public void setValues(PreparedStatement ps, int i) throws SQLException {
							BondConvRatio r = batchList.get(i);
							ps.setLong(1, r.getConvRatioKey().getBondUniCode());
							ps.setString(2, r.getBondShortName());
							ps.setString(3, r.getBondCode());
							ps.setDouble(4, r.getConvRatio().doubleValue());
							ps.setDate(5, new java.sql.Date(r.getStartDate().getTime()));
							ps.setDate(6, new java.sql.Date(r.getEndDate().getTime()));
							ps.setDate(7, new java.sql.Date(r.getConvRatioKey().getCreateDate().getTime()));
						}

						@Override
						public int getBatchSize() {
							return batchList.size();
						}
					});
			for (BondConvRatio r : batchList) {
				publisher.publishEvent(r);
			}
		}
	}

	private void removeExistingData() {
		try {
			String sql = "delete from dmdb.t_bond_conv_ratio  where DATE(create_date) = '" + date2String(fileDate) + "'";
			LOG.info(sql);
			int rows = jdbcTemplate.update(sql);
			LOG.info("已经删除今天数据: " + rows + "条");
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("删除发生异常 ", e);
		}
	}

	private List<BondConvRatio> findConvRatioChange() throws Exception {
		List<BondConvRatio> changeSet = new ArrayList<BondConvRatio>();
		Map<Long, BondConvRatio> lastConvRatios = findLastConvRatio();
		List<BondConvRatio> todayConvRatios = findTodayConvRatio();
		for (BondConvRatio r : todayConvRatios) {
			if (lastConvRatios.containsKey(r.getConvRatioKey().getBondUniCode())) {
				BondConvRatio org = lastConvRatios.get(r.getConvRatioKey().getBondUniCode());
				if (org.getConvRatio().compareTo(r.getConvRatio()) != 0) {
					changeSet.add(r);
				}
			} else {
				changeSet.add(r);
			}
		}
		return changeSet;
	}

	private String findLastConvRatioDate() throws Exception {
		List<BondConvRatio> convRatios = convRatioRepo.findLastestConvRatio();
		if (convRatios ==null || convRatios.isEmpty()) {
			throw new Exception("折算率数据为空");
		}
		return date2String(SafeUtils.removeTime(convRatios.get(0).getConvRatioKey().getCreateDate()));
	}
	
	private List<BondConvRatio> findTodayConvRatio() throws Exception {
		return findConvRatioByDate(findLastConvRatioDate());
	}

	private Map<Long, BondConvRatio> findLastConvRatio() throws Exception {
		String curDate = findLastConvRatioDate();
		List<BondConvRatio> lastestConvRatios = convRatioRepo.findBeforeDate(curDate);
		Map<Long, BondConvRatio> curConvRatioMap = new HashMap<Long, BondConvRatio>();
		if (lastestConvRatios != null && !lastestConvRatios.isEmpty()) {
			String lastDate = date2String(SafeUtils.removeTime(lastestConvRatios.get(0).getConvRatioKey().getCreateDate()));
			List<BondConvRatio> lastConvRatios = findConvRatioByDate(lastDate);
			if (lastConvRatios != null && !lastConvRatios.isEmpty()) {
				for (BondConvRatio r : lastConvRatios) {
					curConvRatioMap.put(r.getConvRatioKey().getBondUniCode(), r);
				}
			}
		}
		return curConvRatioMap;
	}

	private List<BondConvRatio> findConvRatioByDate(String date) {
		return convRatioRepo.findByCreateDate(date);
	}

	private String date2String(Date d) {
		return new SimpleDateFormat("yyyy-MM-dd").format(d);
	}

	public String handleConvRatioChange() throws Exception {
		List<BondConvRatio> changeSet = findConvRatioChange();
		for (BondConvRatio r : changeSet) {
			publisher.publishEvent(r);
		}
		return "done";
	}

}
