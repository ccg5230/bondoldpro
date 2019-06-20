package com.innodealing.service;

import java.lang.reflect.Method;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.innodealing.engine.mongo.bond.BondImpliedRatingIntegrationRepository;
import com.innodealing.uilogic.UIAdapter;
import com.innodealing.model.mongo.dm.BondImpliedRatingInfo;
import com.innodealing.model.mongo.dm.BondRiskColumnDoc;

/**
 * 隐含评价对比
 * 
 * @author liuqi
 *
 */
@Service
public class BondImpliedRatingIntegrationService {

	private static final Logger LOG = LoggerFactory.getLogger(BondImpliedRatingIntegrationService.class);

	protected @Autowired JdbcTemplate jdbcTemplate;

	protected @Autowired BondImpliedRatingIntegrationRepository bondImpliedRatingRepository;

	public String integrate() {

		synchronized (BondImpliedRatingIntegrationService.class) {

			LOG.info("BondImpliedRatingIntegrationService start...");

			// 初始化数据
			refresh();

			// 执行
			excute();

			LOG.info("BondImpliedRatingIntegrationService end...");

			return "success";
		}

	}

	private void excute() {
		Map<String, Map<String, Object>> map = this.queryData();
		Iterator<String> keys = map.keySet().iterator();
		while (keys.hasNext()) {
			Map<String, Object> cominfo = map.get(keys.next());
			if (cominfo == null)
				continue;
			structureBondRiskDoc(cominfo, null);// 子目录
			structureBondRiskDoc(cominfo, "ok");// 根目录
		}
		LOG.info("end ...");
	}

	private void structureBondRiskDoc(Map<String, Object> cominfo, String Type) {

		String rating = "";
		if (Type == null)
			rating = cominfo.get("implied_rating").toString(); // pd评级

		if (!"".equals(rating) && !UIAdapter.impliedRatings.contains(rating))
			return;

		Integer level = (Integer) cominfo.get("level");// pd等级

		String id = getBondRiskDocId(level, rating);

		try {
			BondImpliedRatingInfo doc = this.getBondDealTodayById(id);

			if (doc == null)
				doc = new BondImpliedRatingInfo();

			BondRiskColumnDoc columnDoc = null;

			String credLevel = cominfo.get("cred_level").toString();

			// 列
			String column = getIssCredLevel(credLevel);

			Method method = doc.getClass().getDeclaredMethod("getColumn" + column);

			if (method != null)
				columnDoc = (BondRiskColumnDoc) method.invoke(doc);

			method = BondImpliedRatingInfo.class.getMethod("setColumn" + getIssCredLevel(credLevel),
					BondRiskColumnDoc.class);

			if (columnDoc == null)
				columnDoc = new BondRiskColumnDoc();

			// 统计数量
			if (columnDoc.getCount() == null) {
				columnDoc.setCount(1);
			} else {
				columnDoc.setCount(columnDoc.getCount() + 1);
			}
			columnDoc.getComUniCodeList().add((Long) cominfo.get("bond_code"));
			columnDoc.setCondition(id + "*" + "getColumn" + column);// 查询条件

			doc = getBondRiskDoc(level, rating, Type != null ? true : false, doc);
			method.invoke(doc, columnDoc);

			bondImpliedRatingRepository.save(doc);
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
	BondImpliedRatingInfo getBondDealTodayById(String key) {

		BondImpliedRatingInfo doc = null;

		doc = bondImpliedRatingRepository.findOne(key);

		return doc;

	}

	/**
	 * 字段匹配
	 * 
	 * @param issCred
	 * @return
	 */
	public String getIssCredLevel(String issCred) {
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

	private Map<String, Map<String, Object>> queryData() {

		StringBuffer sb = new StringBuffer();
		sb.append("SELECT * FROM ( SELECT a.implied_rating,");
		sb.append("\n\t(CASE ");
		sb.append(
				"\n\tWHEN a.implied_rating = 'AAA+' OR a.implied_rating = 'AAA'  OR a.implied_rating = 'AAA-' OR a.implied_rating = 'AA+' THEN 1");
		sb.append("\n\tWHEN a.implied_rating = 'AA' OR a.implied_rating = 'AA-' OR a.implied_rating = 'A+' THEN 2");
		sb.append(
				"\n\tWHEN a.implied_rating = 'A' OR a.implied_rating = 'A-' OR a.implied_rating = 'BBB+' OR a.implied_rating = 'BBB'  THEN 3");
		sb.append("\n\tWHEN a.implied_rating = 'BB' OR a.implied_rating = 'B' OR a.implied_rating = 'CCC'  OR a.implied_rating = 'CC'  THEN 4");
		sb.append("\n\tEND");
		sb.append("\n\t) AS level,q.bond_cred_level as cred_level,a.bond_code,a.bond_name");
		sb.append("\n\tFROM dmdb.t_bond_implied_rating a");
		sb.append("\n\tINNER JOIN (");
		sb.append("\n\tSELECT bond_uni_code,BOND_TYPE_PAR FROM (");
		sb.append("\n\tSELECT bond_uni_code,BOND_TYPE_PAR FROM bond_ccxe.d_bond_basic_info_1");
		sb.append("\n\tUNION");
		sb.append("\n\tSELECT bond_uni_code,BOND_TYPE_PAR FROM bond_ccxe.d_bond_basic_info_3");
		sb.append("\n\tUNION");
		sb.append("\n\tSELECT bond_uni_code,BOND_TYPE_PAR FROM bond_ccxe.d_bond_basic_info_4");
		sb.append(
				"\n\t) t1 INNER JOIN dmdb.t_bond_type_xref t2 ON t1.BOND_TYPE_PAR = t2.ccxe_code AND t2.dm_filter_code != 7");
		sb.append("\n\t) basic ON basic.bond_uni_code = a.bond_code");
		sb.append("\n\tINNER JOIN (");
		sb.append("\n\tSELECT * FROM (");
		sb.append("\n\tSELECT c.bond_cred_level,c.bond_uni_code FROM bond_ccxe.d_bond_cred_chan c ");
		sb.append("\n\tWHERE  IS_NEW_RATE = 1");
		sb.append("\n\tORDER BY c.RATE_WRIT_DATE DESC ) t");
		sb.append("\n\tGROUP BY t.bond_uni_code ");
		sb.append("\n\t) q ON q.bond_uni_code = a.bond_code ) p");
		sb.append("\n\tWHERE p.LEVEL IS NOT NULL;");

		List<Map<String, Object>> results = jdbcTemplate.queryForList(sb.toString());

		Map<String, Map<String, Object>> map = new HashMap<String, Map<String, Object>>();
		// 数据封装
		if (results != null && !results.isEmpty())
			results.stream().forEach(m -> {
				map.put(m.get("bond_code").toString(), m);
			});

		return map;
	}

	/**
	 * 重置信用债隐含评价对比数据
	 * 
	 * @return
	 */
	public String refresh() {

		LOG.info("重置信用债隐含评价对比数据 begin....");

		// 清除旧数据
		bondImpliedRatingRepository.deleteAll();

		bondImpliedRatingRepository.insert(getBondRiskDocList());

		LOG.info("重置信用债隐含评价对比数据 end....");

		return null;
	} 

	/**
	 * 初始化数据
	 * 
	 * @param list
	 * @param year
	 * @return
	 */
	private List<BondImpliedRatingInfo> getBondRiskDocList() {

		List<BondImpliedRatingInfo> list = new ArrayList<BondImpliedRatingInfo>();

		for (int i = 1; i <= 4; i++)
			list.add(initBondDealTodayStatsDoc(getBondRiskDoc(i, "", true, null), getBondRiskDocId(i, "")));

		UIAdapter.impliedRatingsMap.keySet().forEach(rating -> {
			Integer level = UIAdapter.impliedRatingsMap.get(rating);
			list.add(initBondDealTodayStatsDoc(getBondRiskDoc(level, rating, false, null),
					getBondRiskDocId(level, rating)));
		});

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
	private BondImpliedRatingInfo getBondRiskDoc(Integer level, String rating, Boolean isParent,
			BondImpliedRatingInfo doc) {

		String id = getBondRiskDocId(level, rating);
		// 顺序
		Integer sort = (Integer) UIAdapter.getImpliedRatingsPriorityAndName(rating, level).get("sort");
		// 名称
		String name = (String) UIAdapter.getImpliedRatingsPriorityAndName(rating, level).get("name");

		if (doc == null) {
			doc = new BondImpliedRatingInfo(id, BigInteger.valueOf(level), rating, sort, isParent, name);
		} else {
			doc.setId(id);
			doc.setLevel(BigInteger.valueOf(level));
			doc.setRating(rating);
			doc.setPriority(sort);
			doc.setName(name);
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
	private String getBondRiskDocId(Integer level, String rating) {
		return level + "_" + rating;
	}

	/**
	 * 初始化每列
	 * 
	 * @param doc
	 * @param id
	 * @return
	 */
	private BondImpliedRatingInfo initBondDealTodayStatsDoc(BondImpliedRatingInfo doc, String id) {
		BondRiskColumnDoc statsDoc = null;
		Method method = null;
		for (int i = 1; i <= 9; i++) {
			try {
				method = BondImpliedRatingInfo.class.getMethod("setColumn" + i, BondRiskColumnDoc.class);
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
