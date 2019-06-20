package com.innodealing.bond.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.innodealing.util.SafeUtils;

@Service
public class BondComExtService {
	
	private static final Logger LOG = LoggerFactory.getLogger(BondComExtService.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public static final Integer COMP_CLASS_BANK = 1; 
	public static final Integer COMP_CLASS_SECU = 2; 
	public static final Integer COMP_CLASS_INSU = 3; 
	public static final Integer COMP_CLASS_INDU = 4; 
	
	public static final Integer MODEL_TYPE_BUSI = 477; //商业服务业评级模型
	public static final Integer MODEL_TYPE_INDU = 479; //工业评级模型
	public static final Integer MODEL_TYPE_REAL_EST = 474; //房地产企业评级模型
	public static final Integer MODEL_TYPE_BANK = 464; //银行评级模型
	public static final Integer MODEL_TYPE_SECU = 449; //证券评级模型
	public static final Integer MODEL_TYPE_INSU = 444; //保险评级模型

	public static final Integer PRI_MODEL_TYPE_BUSI = 489; //私人数据商业服务业评级模型
	public static final Integer PRI_MODEL_TYPE_INDU = 497; //私人数据工业评级模型
	public static final Integer PRI_MODEL_TYPE_REAL_EST = 498; //私人数据房地产企业评级模型
	public static final Integer PRI_MODEL_TYPE_BANK = 490; //私人数据银行评级模型 
	public static final Integer PRI_MODEL_TYPE_SECU = 491; //私人数据证券评级模型
	public static final Integer PRI_MODEL_TYPE_INSU = 500; //私人数据保险评级模型

	private static final Map<Integer, String> compClassAnaTablePrefix = new HashMap<Integer, String>();
	static 
	{
	    compClassAnaTablePrefix.put(COMP_CLASS_BANK,"bank");
		compClassAnaTablePrefix.put(COMP_CLASS_SECU,"secu");
		compClassAnaTablePrefix.put(COMP_CLASS_INSU,"insu");
		compClassAnaTablePrefix.put(COMP_CLASS_INDU,"indu");
	};
	
	private static final Map<Integer, String> compClassFinaTablePrefix = new HashMap<Integer, String>() ;
	static
	{
		compClassFinaTablePrefix.put(COMP_CLASS_BANK,"bank");
		compClassFinaTablePrefix.put(COMP_CLASS_SECU,"secu");
		compClassFinaTablePrefix.put(COMP_CLASS_INSU,"insu");
		compClassFinaTablePrefix.put(COMP_CLASS_INDU,"manu");
	};

	public String getCompClassAnaTablePrefix(Integer compClass)
	{
		return compClassAnaTablePrefix.get(compClass);
	}
	
	public String getCompClassFinTablePrefix(Integer compClass)
	{
		return compClassFinaTablePrefix.get(compClass);
	}
	
	//@Cacheable(value = "compClassCache", key = "#issuerId")
	public Map<String, Object> getCompClass(String issuerId) {
		try {
			String compClassSql = "SELECT CASE" + " WHEN LOCATE('银行', comp_cls_name)>0 THEN 1"
					+ " WHEN LOCATE('证券', comp_cls_name)>0 THEN 2"
					+ " WHEN LOCATE('保险', comp_cls_name)>0 THEN 3"
					+ " WHEN LOCATE('企业', comp_cls_name)>0 THEN 4" + " ELSE 4 END AS compClsName, ext.ama_com_id AS compId "
					+ " FROM dmdb.t_bond_com_ext AS ext"
					+ "	LEFT JOIN  /*amaresun*/ dmdb.tbl_industry_classification AS ic ON ic.industry_code=ext.indu_uni_code_l4"
					+ "	WHERE ext.com_uni_code=" + issuerId + " LIMIT 1";
			return jdbcTemplate.queryForMap(compClassSql);
//			return jdbcTemplate.queryForObject(compClassSql, Integer.class);
		} catch (Exception ex) {
			LOG.error("getCompClass exception with issuerId[" + issuerId + "]: " + ex.getMessage());
		}
		return null;
	}

	@Cacheable(value = "modelTypeCache", key = "#issuerId")
	public Integer getModelType(String issuerId) {
		try {
			String modelTypeSql = "select model_id from  /*amaresun*/ dmdb.rating_ratio_score R\r\n" + 
					"left join dmdb.t_bond_com_ext E on R.COMP_ID = E.ama_com_id \r\n" + 
					"where E.com_uni_code = " +  issuerId + " \r\n" + 
					"order by YEAR desc limit 1 ";
			
			return jdbcTemplate.queryForObject(modelTypeSql, Integer.class);
		} catch (Exception ex) {
			LOG.error("getModelType exception with issuerId[" + issuerId + "]: " + ex.getMessage());
		}
		return null;
	}
	
	public Map<String, Object> getDebtAssetByIssuerId(String issuerId){
		String compClass = "";
		Long compId = null;
		
		Map<String, Object> map = this.getCompClass(issuerId);
		if (null != map) {
			compClass = SafeUtils.getString(map.get("compClsName"));
			compId = SafeUtils.getLong(map.get("compId"));
		}
		
		return findFinasheetByCompId(compClass, compId);
	}
	
	public Map<String, Object> findFinasheetByCompId(String compClass, Long compId) {
		Map<String, Object> map = null;

		switch (compClass.trim()) {
		case "1":
			map = findBankFinasheetByCompId(compId);
			break;
		case "2":
			map = findSecuFinasheetByCompId(compId);
			break;
		case "3":
			map = findInsuFinasheetByCompId(compId);
			break;
		case "4":
			map = findManuFinasheetByCompId(compId);
			break;
		}

		return map;
	}

	@Cacheable(value = "secuDebtAssetCache", key = "#compId")
	private Map<String, Object> findSecuFinasheetByCompId(Long compId) {
		try {
			String sql = "SELECT a.SBS002 AS totalLiabilities,a.SBS001 AS totalAssets FROM dmdb.t_bond_secu_fina_sheet a WHERE a.COMP_ID=? ORDER BY a.FIN_DATE DESC LIMIT 1";
		
			return jdbcTemplate.queryForMap(sql, compId);
		}catch(Exception ex){
			LOG.error("findSecuFinasheetByCompId exception with compId[" + compId + "]: " + ex.getMessage());
		}
		return null;
	}

	@Cacheable(value = "bankDebtAssetCache", key = "#compId")
	private Map<String, Object> findBankFinasheetByCompId(Long compId) {
		try {
			String sql = "SELECT a.BBS002 AS totalLiabilities,a.BBS001 AS totalAssets FROM dmdb.t_bond_bank_fina_sheet a WHERE a.COMP_ID=? ORDER BY a.FIN_DATE DESC LIMIT 1";
	
			return jdbcTemplate.queryForMap(sql, compId);
		}catch(Exception ex){
			LOG.error("findBankFinasheetByCompId exception with compId[" + compId + "]: " + ex.getMessage());

		}
		return null;
	}

	@Cacheable(value = "insuDebtAssetCache", key = "#compId")
	private Map<String, Object> findInsuFinasheetByCompId(Long compId) {
		try {
			String sql = "SELECT a.IBS002 AS totalLiabilities,a.IBS001 AS totalAssets FROM dmdb.t_bond_insu_fina_sheet a WHERE a.COMP_ID=? ORDER BY a.FIN_DATE DESC LIMIT 1";
			
			return jdbcTemplate.queryForMap(sql, compId);
		}catch(Exception ex){
			LOG.error("findInsuFinasheetByCompId exception with compId[" + compId + "]: " + ex.getMessage());

		}
		return null;
	}

	@Cacheable(value = "manuDebtAssetCache", key = "#compId")
	private Map<String, Object> findManuFinasheetByCompId(Long compId) {
		try {
			String sql = "SELECT a.BS002 AS totalLiabilities,a.BS001 AS totalAssets FROM dmdb.t_bond_manu_fina_sheet a WHERE a.Comp_ID=? ORDER BY a.FIN_DATE DESC LIMIT 1";
			
			return jdbcTemplate.queryForMap(sql, compId);
		}catch(Exception ex){
			LOG.error("findManuFinasheetByCompId exception with compId[" + compId + "]: " + ex.getMessage());

		}
		return null;
	}

}
