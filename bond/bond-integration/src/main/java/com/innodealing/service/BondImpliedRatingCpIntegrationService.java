package com.innodealing.service;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.innodealing.engine.mongo.bond.BondImpliedRatingCpIntegrationRepository;
import com.innodealing.uilogic.UIAdapter;
import com.innodealing.model.mongo.dm.BondImpliedRatingCpInfo;
import com.innodealing.model.mongo.dm.BondRiskColumnDoc;

/**
 * (短融/超短融)隐含评价对比
 * 
 * @author liuqi
 *
 */
@Service
public class BondImpliedRatingCpIntegrationService {

	private static final Logger LOG = LoggerFactory.getLogger(BondImpliedRatingCpIntegrationService.class);

	protected @Autowired JdbcTemplate jdbcTemplate;

	protected @Autowired BondImpliedRatingCpIntegrationRepository bondImpliedRatingRepository;

	protected @Autowired BondImpliedRatingIntegrationService bondImpliedRatingIntegrationService;
	
	final List<String> bondRatingList = Arrays.asList("AAA+", "AAA", "AAA-", "AA+", "AA","AA-","A+","<=A");

	public String integrate() {

		synchronized (BondImpliedRatingCpIntegrationService.class) {

			LOG.info("BondImpliedRatingCpIntegrationService start...");

			// 初始化数据
			refresh();

			// 执行
			excute();

			LOG.info("BondImpliedRatingCpIntegrationService end...");

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
			structureBondRiskDoc(cominfo);
		}
		LOG.info("end ...");
	}

	private void structureBondRiskDoc(Map<String, Object> cominfo) {

		String rating = cominfo.get("implied_rating").toString();

		if (!"".equals(rating) && !bondRatingList.contains(rating))
			rating = "<=A";

		try {
			BondImpliedRatingCpInfo doc = this.getBondDealTodayById(rating);
			if (doc == null)
				doc = new BondImpliedRatingCpInfo();
			BondRiskColumnDoc columnDoc = null;

			String credLevel = cominfo.get("cred_level").toString();

			// 列
			String column = bondImpliedRatingIntegrationService.getIssCredLevel(credLevel);

			Method method = doc.getClass().getDeclaredMethod("getColumn" + column);
			if (method != null)
				columnDoc = (BondRiskColumnDoc) method.invoke(doc);
			method = BondImpliedRatingCpInfo.class.getMethod(
					"setColumn" + bondImpliedRatingIntegrationService.getIssCredLevel(credLevel),
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
			columnDoc.setCondition(rating + "*" + "getColumn" + column);// 查询条件

			doc = getBondRiskDoc(rating, doc);
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
	BondImpliedRatingCpInfo getBondDealTodayById(String key) {

		BondImpliedRatingCpInfo doc = null;

		doc = bondImpliedRatingRepository.findOne(key);

		return doc;

	}

	private Map<String, Map<String, Object>> queryData() {

		StringBuffer sb = new StringBuffer();
		sb.append("\n\tSELECT * FROM ( SELECT a.implied_rating,");
		sb.append("\n\t(CASE ");
		sb.append(
				"\n\tWHEN a.implied_rating = 'AAA+' OR a.implied_rating = 'AAA'  OR a.implied_rating = 'AAA-' OR a.implied_rating = 'AA+' THEN 1");
		sb.append("\n\tWHEN a.implied_rating = 'AA' OR a.implied_rating = 'AA-' OR a.implied_rating = 'A+' THEN 2");
		sb.append(
				"\n\tWHEN a.implied_rating = 'A' OR a.implied_rating = 'A-' OR a.implied_rating = 'BBB+' OR a.implied_rating = 'BBB'  THEN 3");
		sb.append("\n\tWHEN a.implied_rating = 'BBB-' OR a.implied_rating = 'BB+' THEN 4");
		sb.append("\n\tWHEN a.implied_rating = 'BB' OR a.implied_rating = 'BB-' OR a.implied_rating = 'B+'  THEN 5");
		sb.append("\n\tWHEN a.implied_rating = 'B' OR a.implied_rating = 'B-' OR a.implied_rating = 'CCC+'  THEN 6");
		sb.append(
				"\n\tWHEN a.implied_rating = 'CCC' OR a.implied_rating = 'CCC-' OR a.implied_rating = 'CCC' OR a.implied_rating = 'CC+' OR a.implied_rating = 'CC'  OR a.implied_rating = 'CC-' OR a.implied_rating = 'C'   THEN 7");
		sb.append("\n\tEND");
		sb.append("\n\t) AS level,q.ISS_CRED_LEVEL as cred_level,a.bond_code,a.bond_name");
		sb.append("\n\tFROM dmdb.t_bond_implied_rating a");
		sb.append("\n\tINNER JOIN (");
		sb.append("\n\tSELECT bond_uni_code,BOND_TYPE_PAR FROM (");
		sb.append("\n\tSELECT bond_uni_code,BOND_TYPE_PAR FROM bond_ccxe.d_bond_basic_info_1");
		sb.append("\n\tUNION");
		sb.append("\n\tSELECT bond_uni_code,BOND_TYPE_PAR FROM bond_ccxe.d_bond_basic_info_3");
		sb.append("\n\tUNION");
		sb.append("\n\tSELECT bond_uni_code,BOND_TYPE_PAR FROM bond_ccxe.d_bond_basic_info_4");
		sb.append(
				"\n\t) t1 INNER JOIN dmdb.t_bond_type_xref t2 ON t1.BOND_TYPE_PAR = t2.ccxe_code AND t2.dm_filter_code = 7");
		sb.append("\n\t) basic ON basic.bond_uni_code = a.bond_code");
		sb.append("\n\tINNER JOIN (");
		sb.append("\n\tSELECT * FROM (");
		sb.append("\n\tSELECT c.ISS_CRED_LEVEL,c.com_uni_code FROM bond_ccxe.d_bond_iss_cred_chan c ");
		sb.append("\n\tWHERE  IS_NEW_RATE = 1");
		sb.append("\n\tORDER BY c.RATE_WRIT_DATE DESC");
		sb.append("\n\t) t");
		sb.append("\n\tGROUP BY t.com_uni_code ");
		sb.append("\n\t) q ON q.com_uni_code = a.issuer_id ) p");
		sb.append("\n\tWHERE p.LEVEL IS NOT NULL");

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
	 * 重置信用债(短融/超短融)隐含评价对比数据
	 * 
	 * @return
	 */
	public String refresh() {

		LOG.info("重置(短融/超短融)隐含评价对比数据 begin....");

		// 清除旧数据
		bondImpliedRatingRepository.deleteAll();

		bondImpliedRatingRepository.insert(getBondRiskDocList());

		LOG.info("重置(短融/超短融)隐含评价对比数据 end....");

		return null;
	}

	/**
	 * 初始化数据
	 * 
	 * @param list
	 * @param year
	 * @return
	 */
	private List<BondImpliedRatingCpInfo> getBondRiskDocList() {

		List<BondImpliedRatingCpInfo> list = new ArrayList<BondImpliedRatingCpInfo>();

		bondRatingList.stream().forEach(rating -> {
			list.add(initBondDealTodayStatsDoc(getBondRiskDoc(rating,null),rating));
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
	private BondImpliedRatingCpInfo getBondRiskDoc(String rating,
			BondImpliedRatingCpInfo doc) {

		// 顺序
		Integer sort = (Integer) UIAdapter.BondImpliedRatingsSorts.get(rating);

		if (doc == null) {
			doc = new BondImpliedRatingCpInfo(rating, sort);
		} else {
			doc.setId(rating);
			doc.setPriority(sort);
		}

		return doc;
	}

	/**
	 * 初始化每列
	 * 
	 * @param doc
	 * @param id
	 * @return
	 */
	private BondImpliedRatingCpInfo initBondDealTodayStatsDoc(BondImpliedRatingCpInfo doc, String id) {
		BondRiskColumnDoc statsDoc = null;
		Method method = null;
		for (int i = 1; i <= 9; i++) {
			try {
				method = BondImpliedRatingCpInfo.class.getMethod("setColumn" + i, BondRiskColumnDoc.class);
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
