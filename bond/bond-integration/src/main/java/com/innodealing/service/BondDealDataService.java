package com.innodealing.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.google.common.cache.CacheLoader.InvalidCacheLoadException;
import com.innodealing.bond.service.BondBasicInfoService;
import com.innodealing.engine.jpa.dm.BondDealDataRepository;
import com.innodealing.engine.redis.RedisUtil;
import com.innodealing.model.dm.bond.BondDeal;
import com.innodealing.model.mongo.dm.BondBasicInfoDoc;
import com.innodealing.util.StringUtils;

@Service
public class BondDealDataService {

	private static final Logger LOG = LoggerFactory.getLogger(BondDealDataService.class);

	/** 数据抓取地址 */
	private static String GRADURL = "http://www.chinamoney.com.cn/fe-c/vol10BondDealQuotesMoreAction.do?lang=cn";

	@Autowired
	BondDealDataRepository bondDealDataRepository;

	@Autowired
	protected JdbcTemplate jdbcTemplate;

	@Autowired
	MongoOperations mongoOperations;

	@Autowired
	BondBasicInfoService bondBasicInfoService;

	@Autowired
	private  ApplicationEventPublisher publisher;
	
	@Autowired
	RedisUtil redisUtil;
	
	public String syncIntegration() {
		synchronized (BondDealDataService.class) {
			return integration();
		}
	}
	
	private String integration() {
		try {
			List<BondDeal> list = getDealData();
			if (null == list || list.isEmpty()) {
				return "failed to get data";
			}
			int diffSize = 0;
			for (BondDeal vo : list) {
				BondDeal orgStats = (BondDeal)redisUtil.get("Last_Deal_"+vo.getBondName());
				if(null != orgStats){
					if (orgStats.isNewDeal(vo)) {
						diffSize++;
						this.save(vo);
					}
				}else{   
					this.save(vo);
				}
			}
			LOG.info("Deal data integration result: total size is " + list.size() + ", different size is " + diffSize );
		} catch (Exception e) {
			LOG.error("internal error" + e.getMessage(), e);
			e.printStackTrace();
			return "internal error";
		}
		return "success";
	}

	public static List<BondDeal> getDealData() throws IOException {
		
		String hostname = null;
		int port = 0;
		String scheme = "http";
		
		Object object = getEnv("http_proxy");
		
		//LOG.info(object.toString());
		
		if(null!=object && object.toString().indexOf(":")!=-1){
			String str = object.toString();
			String[] strlist = str.split(":");
			hostname=strlist[0];
			port=Integer.parseInt(strlist[1]);
		}
		
		List<BondDeal> list = null;
		
		HttpClient httpclient = new DefaultHttpClient();
		
		if(null!=object && object.toString().indexOf(":")!=-1){
			HttpHost proxy = new HttpHost(hostname, port, scheme);
			httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
		}

		HttpGet httpget = new HttpGet(GRADURL);
		
		// 查看默认request头部信息
		httpget.setHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.1.2)");
		// 用逗号分隔显示可以同时接受多种编码
		httpget.setHeader("Accept-Language", "zh-cn,zh;q=0.5");
		httpget.setHeader("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7");

		String text = "";

		try {
			// 执行
			HttpResponse response = httpclient.execute(httpget);

			HttpEntity entity = response.getEntity(); // 返回服务器响应
			
			LOG.info(response.getStatusLine().toString());

			System.out.println(response.getStatusLine()); // 服务器返回状态
			if (entity != null) {
				// Header[] headers = response.getAllHeaders(); // 返回的HTTP头信息
				// for (int i = 0; i < headers.length; i++) {
				// System.out.println(headers[i]);
				// }

				// 将源码流保存在一个byte数组当中，因为可能需要两次用到该流
				// 注，如果上面的EntityUtils.toString(response.getEntity())执行了后，就不能再用下面的语句拿数据了，直接用上面的数据
				byte[] bytes = EntityUtils.toByteArray(entity);
				String charSet = "";

				// 如果头部Content-Type中包含了编码信息，那么我们可以直接在此处获取
				charSet = EntityUtils.getContentCharSet(entity);

				// System.out.println("In header: " + charSet);
				// 如果头部中没有，那么我们需要 查看页面源码，这个方法虽然不能说完全正确，因为有些粗糙的网页编码者没有在页面中写头部编码信息
				if (charSet == null || charSet == "") {
					String regEx = "<meta.*charset=['|\"]?([[a-z]|[A-Z]|[0-9]|-]*)['|\"]?";
					Pattern p = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
					Matcher m = p.matcher(new String(bytes)); // 默认编码转成字符串，因为我们的匹配中无中文，所以串中可能的乱码对我们没有影响
					if (m.find()) {
						charSet = m.group(1);
					} else {
						charSet = "";
					}
				}

				if (StringUtils.isBlank(charSet)) {
					charSet = "utf-8";
				}

				text = new String(bytes, charSet);
			}
			httpget.abort();
		} finally {
			httpclient.getConnectionManager().shutdown();
		}

		list = JsoupHtml(text);

		return list;
	}

	/**
	 * 解析HTML
	 * 
	 * @param html
	 */
	public static List<BondDeal> JsoupHtml(String html) {
		
		List<BondDeal> list = new ArrayList<BondDeal>();
		BondDeal vo = null;
		String text = null;
		try{
			Document doc = Jsoup.parse(html);
			Elements trlist = doc.select("tr");
			for (Element l : trlist) {
				text = l.text();
				if (!StringUtils.isBlank(text)) {
					if (matcher("\\d", text.substring(0, 1))) {
						vo = new BondDeal();
						String[] strarr = text.split(" ");
						vo.setBondName(strarr[0]);
						if (!StringUtils.isBlank(strarr[1])) {
							vo.setBondRate(new BigDecimal(strarr[1]));
						}
						if (!StringUtils.isBlank(strarr[2])) {
							if(!"---".equals(strarr[2].trim())){
								vo.setBondBp(new BigDecimal(strarr[2]));
							}else{
								vo.setBondBp(new BigDecimal("0"));
							}
							//保存涨跌趋势
							String bphtml =  l.html();
							if(bphtml.indexOf("icon_up.gif")!=-1){
								vo.setBondBpTrend("up");
							}else if(bphtml.indexOf("icon_down.gif")!=-1){
								vo.setBondBpTrend("down");
							}
						}
						if (!StringUtils.isBlank(strarr[3])) {
							vo.setBondAddRate(new BigDecimal(strarr[3]));
						}
						if (!StringUtils.isBlank(strarr[4])) {
							if (matcher("\\d+", strarr[4])) {
								vo.setBondTradingVolume(new BigDecimal(strarr[4]));
							}
						}
						vo.setCreateTime(new Date());
						list.add(vo);
					}
				}
	
			}
		}catch(ArrayIndexOutOfBoundsException e){
			LOG.error("解析失败,数组下标越界.text="+text+",length="+text.split(" ").length);
		}
		
		return list;
	}

	/**
	 * 正则匹配
	 * 
	 * @param regEx
	 * @param text
	 * @return
	 */
	public static boolean matcher(String regEx, String text) {
		Pattern p = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(text);
		return m.find();
	}

	public void save(BondDeal vo){
		BondBasicInfoDoc doc = null;
		try{
			doc = bondBasicInfoService.findByShortName(vo.getBondName());
		}catch(InvalidCacheLoadException e){
			LOG.error("key="+vo.getBondName()+" is null",e);
		}
		if(null!=doc){
			vo.setBondUniCode(doc.getBondUniCode());
			bondDealDataRepository.save(vo);
		}
		redisUtil.set("Last_Deal_"+vo.getBondName(), vo);
		this.publisher.publishEvent(vo); 
	}
	
	private static Object getEnv(String key){
		Map map = System.getenv();
		Iterator it = map.entrySet().iterator(); 
		Object o = null;
		while(it.hasNext())  
		{  
		    Entry entry = (Entry)it.next();  
		    if(entry.getKey().equals(key)){
		    	o = entry.getValue();
		    	break;
		    }
		}  
		return o;
	}
	
	public static void main(String[] args) {
		String hostname = null;
		int port = 0;
		String scheme = "http";
		
		Object object = getEnv("JAVA_HOME");
		System.out.println(object);
		
		if(null!=object && object.toString().indexOf(":")!=-1){
			String str = object.toString();
			String[] strlist = str.split(":");
			hostname=strlist[0];
			port=Integer.parseInt(strlist[1]);
		}
		System.out.println(hostname);
		System.out.println(port);
	}

}
