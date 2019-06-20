package com.innodealing.service;

import java.lang.reflect.Method;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.innodealing.engine.mongo.bond.BondRiskRepository;
import com.innodealing.model.BondRiskComInfo;
import com.innodealing.model.mongo.dm.BondRiskDoc;
import com.innodealing.uilogic.UIAdapter;
import com.innodealing.model.mongo.dm.BondRiskColumnDoc;
import com.innodealing.util.StringUtils;

/**
 * 债劵今日成交-->风险评价对比
 * 
 * @author liuqi
 *
 */
@Service
public class BondRiskIntegrationService {

	private static final Logger LOG = LoggerFactory.getLogger(BondRiskIntegrationService.class);

	protected @Autowired JdbcTemplate jdbcTemplate;

	protected @Autowired BondRiskRepository bondRiskRepository;

	public String integrateComRiskInfo() {

		synchronized (BondRiskIntegrationService.class) {

			LOG.info("BondRiskIntegrationService start...");

			// 所有评级年份
			List<String> yearList = getYearList();

			// 初始化数据
			refresh(yearList);

			// 执行
			excute(yearList);

			LOG.info("BondRiskIntegrationService end...");

			return "success";
		}

	}

	private void excute(List<String> yearList) {

		// 创建线程池
		ExecutorService CachedThreadPool = Executors.newCachedThreadPool();

		Iterator<String> yearkeys = yearList.iterator();

		while (yearkeys.hasNext()) {
			String year = yearkeys.next();
			Map<String, BondRiskComInfo> map = this.queryComInfo(year);
			CachedThreadPool.execute(new Runnable() {
				@Override
				public void run() {
					LOG.info(Thread.currentThread().getName() + " start ...");
					Iterator<String> keys = map.keySet().iterator();
					while (keys.hasNext()) {
						BondRiskComInfo cominfo = map.get(keys.next());
						if (cominfo == null) {
							continue;
						}
						cominfo.setPdTime(Integer.valueOf(year));
						structureBondRiskDoc(cominfo, null);// 子目录
						structureBondRiskDoc(cominfo, "ok");// 根目录
					}
				}
			});
		}

		CachedThreadPool.shutdown();
		try {
			CachedThreadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
			LOG.error("等待任务完成中发生异常 ", e);
			e.printStackTrace();
		}

		LOG.info("end ...");
	}

	private void structureBondRiskDoc(BondRiskComInfo cominfo, String Type) {

		String rating = "";
		if (Type == null) {
			rating = cominfo.getRating(); // pd评级
		}
		
		if(!"".equals(rating) && !UIAdapter.Ratings.contains(rating)){
			rating = "CCC及以下";
		}

		Integer level = cominfo.getLevel();// pd等级

		Integer year = cominfo.getPdTime(); // 年份

		String id = getBondRiskDocId(level, rating, year);

		try {
			BondRiskDoc doc = this.getBondDealTodayById(id);
			if (doc == null) {
				doc = new BondRiskDoc();
			}
			BondRiskColumnDoc columnDoc = null;

			// 列
			String column = getIssCredLevel(cominfo.getIssCredLevel());

			Method method = doc.getClass().getDeclaredMethod("getColumn" + column);
			if (method != null) {
				columnDoc = (BondRiskColumnDoc) method.invoke(doc);
			}
			method = BondRiskDoc.class.getMethod("setColumn" + getIssCredLevel(cominfo.getIssCredLevel()),
					BondRiskColumnDoc.class);

			if (columnDoc == null) {
				columnDoc = new BondRiskColumnDoc();
			}

			// 统计数量
			if (columnDoc.getCount() == null) {
				columnDoc.setCount(1);
			} else {
				columnDoc.setCount(columnDoc.getCount() + 1);
			}
			columnDoc.getComUniCodeList().add(cominfo.getComUniCode()); // 发行人集合
			columnDoc.setCondition(id + "*" + "getColumn" + column);// 查询条件

			doc = getBondRiskDoc(level, rating, year, Type != null ? true : false, doc);
			method.invoke(doc, columnDoc);

			bondRiskRepository.save(doc);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 查询列里面各自的属性
	 * 
	 * @param key
	 * @return
	 */
	private BondRiskDoc getBondDealTodayById(String key) {

		BondRiskDoc doc = null;

		doc = bondRiskRepository.findOne(key);

		return doc;

	}

	/**
	 * 字段匹配
	 * 
	 * @param issCred
	 * @return
	 */
	private String getIssCredLevel(String issCred) {
		String issCredLevel = "";
		switch (issCred) {
		case "AAA":
			issCredLevel = "1";
			break;
		case "AAA-":
			issCredLevel = "2";
			break;
		case "AA+":
			issCredLevel = "3";
			break;
		case "AA":
			issCredLevel = "4";
			break;
		case "AA-":
			issCredLevel = "5";
			break;
		case "A+":
			issCredLevel = "6";
			break;
		case "A":
			issCredLevel = "7";
			break;
		case "A-":
			issCredLevel = "8";
			break;
		default:
			issCredLevel = "9";
			break;
		}
		return issCredLevel;
	}

	/**
	 * 所有评级年份
	 * 
	 * @return
	 */
	private List<String> getYearList() {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT  a.YEAR as year  FROM  /*amaresun*/ dmdb.dm_bond a");
		sb.append("\n\tINNER JOIN (");
		sb.append("\n\tSELECT YEAR(RATE_WRIT_DATE) AS YEAR FROM  bond_ccxe.d_bond_iss_cred_chan where ISVALID=1  and com_type_par=1 ");
		sb.append("\n\tGROUP BY  YEAR(RATE_WRIT_DATE) ) b ON a.YEAR = b.YEAR");
		sb.append("\n\tGROUP BY  a.YEAR ");
		List<String> list = jdbcTemplate.queryForList(sb.toString(), String.class);
		return list;
	}

	/**
	 * 根据年份查询发行人信息
	 * 
	 * @param year
	 * @return
	 */
	private Map<String, BondRiskComInfo> queryComInfo(String year) {

		if (StringUtils.isEmpty(year)) {
			return null;
		}

		StringBuffer sb = new StringBuffer();
		sb.append("SELECT t.* FROM (SELECT a.rating,");
		sb.append("\n\t(CASE ");
		sb.append("\n\tWHEN a.rating = 'AAA' OR a.rating = 'AA+' THEN 1");
		sb.append("\n\tWHEN a.rating = 'AA' OR a.rating = 'AA-' OR a.rating = 'A+' THEN 2");
		sb.append("\n\tWHEN a.rating = 'A' OR a.rating = 'A-' OR a.rating = 'BBB+' OR a.rating = 'BBB'  THEN 3");
		sb.append("\n\tWHEN a.rating = 'BBB-' OR a.rating = 'BB+' THEN 4");
		sb.append("\n\tWHEN a.rating = 'BB' OR a.rating = 'BB-' OR a.rating = 'B+'  THEN 5");
		sb.append("\n\tWHEN a.rating = 'B' OR a.rating = 'B-' OR a.rating = 'CCC+'  THEN 6");
		sb.append("\n\tELSE");
		sb.append("\n\t7");
		sb.append("\n\tEND");
		sb.append("\n\t) AS level,c.iss_cred_level,b.com_chi_name,b.com_uni_code");
		sb.append("\n\tFROM  /*amaresun*/ dmdb.dm_bond a");
		sb.append("\n\tLEFT JOIN dmdb.t_bond_com_ext b ON b.ama_com_id = a.comp_id ");
		sb.append(
				"\n\tLEFT JOIN bond_ccxe.d_bond_iss_cred_chan c ON c.com_uni_code = b.com_uni_code AND IS_NEW_RATE = 1 AND ISVALID=1  and com_type_par=1 ");
		sb.append("\n\tWHERE a.year <= " + year + "  AND YEAR(c.RATE_WRIT_DATE)<= " + year );
		sb.append("\n\tAND a.rating IS NOT NULL");
		sb.append("\n\tORDER BY c.RATE_WRIT_DATE DESC,a.YEAR  DESC,a.quan_month DESC) t GROUP BY t.com_uni_code");
		List<BondRiskComInfo> results = (List<BondRiskComInfo>) jdbcTemplate.query(sb.toString(),
				new BeanPropertyRowMapper<BondRiskComInfo>(BondRiskComInfo.class));

		Map<String, BondRiskComInfo> map = new HashMap<String, BondRiskComInfo>();
		// 数据封装
		if (results != null && !results.isEmpty()) {

			for (BondRiskComInfo com : results) {
				map.put(com.getComUniCode().toString(), com);
			}

		}

		return map;
	}

	/**
	 * 重置风险评价对比数据
	 * 
	 * @return
	 */
	public String refresh(List<String> yearList) {

		LOG.info("重置风险评价对比数据 begin....");

		// 清除旧数据
		bondRiskRepository.deleteAll();

		List<BondRiskDoc> list = new ArrayList<BondRiskDoc>();

		Iterator<String> yearkeys = yearList.iterator();

		while (yearkeys.hasNext()) {
			String year = yearkeys.next();
			list.addAll(getBondRiskDocList(Integer.valueOf(year)));
		}

		bondRiskRepository.insert(list);

		LOG.info("重置风险评价对比数据 end....");

		return null;
	}

	/**
	 * 初始化数据
	 * 
	 * @param list
	 * @param year
	 * @return
	 */
	private List<BondRiskDoc> getBondRiskDocList(Integer year) {

		List<BondRiskDoc> list = new ArrayList<BondRiskDoc>();

		for (int i = 1; i <= 7; i++) {
			list.add(initBondDealTodayStatsDoc(getBondRiskDoc(i, "", year, true, null), getBondRiskDocId(i, "", year)));
		}

		Integer level = null;
		for (String rating : UIAdapter.getRiskMap().keySet()) {
			level = UIAdapter.getRiskMap().get(rating);
			list.add(initBondDealTodayStatsDoc(getBondRiskDoc(level, rating, year, false, null),
					getBondRiskDocId(level, rating, year)));
		}

		return list;
	}

	/**
	 * 构建对象
	 * 
	 * @param level
	 * @param rating
	 * @param pdTime
	 * @param isParent
	 * @return
	 */
	private BondRiskDoc getBondRiskDoc(Integer level, String rating, Integer pdTime, Boolean isParent,
			BondRiskDoc doc) {

		String id = getBondRiskDocId(level, rating, pdTime);
		// 顺序
		Integer sort = (Integer) UIAdapter.getPriorityAndName(rating, level).get("sort");
		// 名称
		String name = (String) UIAdapter.getPriorityAndName(rating, level).get("name");

		if (doc == null) {
			doc = new BondRiskDoc(id, BigInteger.valueOf(level), rating, sort, pdTime, isParent, name);
		} else {
			doc.setId(id);
			doc.setLevel(BigInteger.valueOf(level));
			doc.setRating(rating);
			doc.setPriority(sort);
			doc.setName(name);
			doc.setPdTime(pdTime);
			doc.setIsParent(isParent);
		}

		return doc;
	}

	/**
	 * 得到唯一主键
	 * 
	 * @param level
	 * @param rating
	 * @param pdTime
	 * @return
	 */
	private String getBondRiskDocId(Integer level, String rating, Integer pdTime) {
		return pdTime + "_" + level + "_" + rating;
	}

	/**
	 * 初始化每列
	 * 
	 * @param doc
	 * @param id
	 * @return
	 */
	private BondRiskDoc initBondDealTodayStatsDoc(BondRiskDoc doc, String id) {
		BondRiskColumnDoc statsDoc = null;
		Method method = null;
		for (int i = 1; i <= 9; i++) {
			try {
				method = BondRiskDoc.class.getMethod("setColumn" + i, BondRiskColumnDoc.class);
				statsDoc = new BondRiskColumnDoc();
				statsDoc.setCondition(id + "*" + "getColumn" + i);// 查询条件
				method.invoke(doc, statsDoc);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return doc;
	}
}
