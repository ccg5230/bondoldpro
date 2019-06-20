package com.innodealing.engine.jdbc.bond;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.google.common.base.Objects;
import com.innodealing.exception.BusinessException;
import com.innodealing.model.dm.bond.BondCom;
import com.innodealing.util.SafeUtils;

/**
 * 原始财务指标dao
 * @author 赵正来
 *
 */
@Component
public class IndicatorDao {
	private @Autowired JdbcTemplate jdbcTemplate;

	public static final String BANK = "bank",INSU = "insu",INDU = "indu",SECU = "secu";

	private static Map<String, List<String>> FIELDS_CACHE = new HashMap<>();


	private  static List<String> FIELDS = null;
	
	/**
	 * 正向指标
	 */
	private static List<String> POSITIVE_INDICATORS;
	
	/**
	 * 财务指标指标
	 */
	private static List<String> FINANCE_INDICATORS;

	/**
	 * FINANCE 财务指标，SPECIAL 专项指标
	 */
	public static final Integer FINANCE = 1, SPECIAL = 2;
	
	Logger log = LoggerFactory.getLogger(IndicatorDao.class);
	
	
	

	/**
	 * 查找某一个行业的财务指标，用于计算指标在行业的排名等
	 * @param compId
	 * @param finDate
	 * @param fields
	 * @param indicatorType
	 * @return
	 */
	public List<Map<String,Object>> findIndicatorsByInduAndFinDate1(Long compId, Date finDate, Collection<String> fields, Integer indicatorType){
    	
		StringBuffer stringSelect = new StringBuffer(" ");
		String tableName = findTableNameByCompId(compId, indicatorType);
		//指标
		if(fields == null){
			fields = getFields(tableName, indicatorType);
		}
		fields.forEach(field -> {
			stringSelect.append(field)
			.append(",");
		});
		//模型判断
		String model = findIssuerModelByCompId(compId);
		if(Objects.equal(model, INDU)){
			model = "manu";
		}
		String sql = "SELECT  " + stringSelect.substring(0, stringSelect.length() - 1) + " FROM dmdb." + tableName + " WHERE COMP_ID IN ("+
				" SELECT ama_com_id FROM dmdb.t_bond_com_ext WHERE indu_uni_code = (SELECT indu_uni_code FROM dmdb.t_bond_com_ext WHERE ama_com_id =  ?)"+
				" ) AND FIN_DATE = ?";
		
		return jdbcTemplate.queryForList(sql, compId, finDate);
    }
	
	/**
	 * 查找某一个行业的财务指标，用于计算指标在行业的排名等
	 * @param bondCom
	 * @param finDate
	 * @param fields
	 * @param indicatorType
	 * @return
	 */
	public List<Map<String,Object>> findIndicatorsByInduSwAndFinDate(BondCom bondCom, Date finDate, Collection<String> fields, Integer indicatorType){
    	
		StringBuffer stringSelect = new StringBuffer(" ");
		String tableName = findTableNameByCompId(bondCom.getAmsIssId(), indicatorType);
		//指标
		if(fields == null){
			fields = getFields(tableName, indicatorType);
		}
		fields.forEach(field -> {
			stringSelect.append(field)
			.append(",");
		});
		//模型判断
		String model = findIssuerModelByCompId(bondCom.getAmsIssId());
		if(Objects.equal(model, INDU)){
			model = "manu";
		}
		String sql = "SELECT  " + stringSelect.substring(0, stringSelect.length() - 1) + " FROM dmdb." + tableName + " WHERE COMP_ID IN ("+
				" SELECT ama_com_id FROM dmdb.t_bond_com_ext WHERE indu_uni_code_sw = ?"+
				" ) AND FIN_DATE = ?";
		
		return jdbcTemplate.queryForList(sql, bondCom.getInduIdSw(), finDate);
    }
	
	/**
	 * 查询主体的行业模型
	 * @param compId
	 * @return indu,bank,insu,secu
	 */
	public String findIssuerModelByCompId(Long compId){
		String sql = "SELECT c.model_id FROM dmdb.t_bond_com_ext e INNER JOIN dmdb.t_bond_industry_classification c ON e.indu_uni_code_l4 = c.industry_code WHERE ama_com_id = " + compId;
		String model = null;
		
		try {
			model = jdbcTemplate.queryForObject(sql, String.class);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		if(model == null){
			log.error(sql);
			throw new BusinessException("行业 [dmdb.t_bond_industry_classification ，compId=" + compId + "] 找不到对应的模型。");
		}
		if(!BANK.equals(model) && !INSU.equals(model) && !SECU.equals(model)){
			model = INDU;
		}
		return model;
	}
	
	/**
	 * 查找主体的指标 
	 * @param compId
	 * @param finDate
	 * @param fields
	 * @return
	 */
	public Map<String, Object> findByCompIdAndFinDate(Long compId, Date finDate, Collection<String> fields,  Integer indicatorType) throws Exception{
		StringBuffer stringSelect = new StringBuffer(" ");
		//表判断
		String tableName = findTableNameByCompId(compId, indicatorType);
		//指标
		if(fields == null){
			fields = getFields(tableName, indicatorType);
		}
		fields.forEach(field -> {
			stringSelect.append(field)
			.append(",");
		});
		String dateString = SafeUtils.convertDateToString(finDate, SafeUtils.DATE_FORMAT);
		String sql = "SELECT  " + stringSelect.substring(0, stringSelect.length() - 1) + " FROM dmdb." + tableName + " WHERE COMP_ID = " + compId +" and fin_date = '"+ dateString+ "'";
		//log.info("findByCompIdAndFinDate sql ->" + sql );
		Map<String, Object> result = null;
		try {
			result = jdbcTemplate.queryForMap(sql);
		} catch (EmptyResultDataAccessException e) {
			log.info("findByCompIdAndFinDate exception sql ->" + sql + e.getMessage());
			throw new BusinessException("compId=" + compId + ",该主体没有" + SafeUtils.convertDateToString(finDate, SafeUtils.DATE_FORMAT) +  "财报");
		}
	    return result;
	}
	
	/**
	 * 查找主体的最新指标 
	 * @param compId
	 * @param fields
	 * @param indicatorType
	 * @param withFinDate
	 * @return withFinDate是否要返回财报日期
	 */
	public List<Map<String, Object>> findLastFinDateByCompId(Long compId, Collection<String> fields,  Integer indicatorType, boolean withFinDate){
		StringBuffer stringSelect = new StringBuffer(" ");
		//表判断
		String tableName = findTableNameByCompId(compId, indicatorType);
		//指标
		if(fields == null){
			fields = getFields(tableName, indicatorType);
		}
		fields.forEach(field -> {
			stringSelect.append(field)
			.append(",");
		});
		if(withFinDate){
			stringSelect.append("fin_date,");
		}
		String sql = "SELECT  " + stringSelect.substring(0, stringSelect.length() - 1) + " FROM dmdb." + tableName + " WHERE COMP_ID = ? GROUP BY FIN_DATE order by fin_date desc";
	    return jdbcTemplate.queryForList(sql, compId);
	}
	
	/**
	 * 查找主体分类
	 * @param compId
	 * @return
	 */
	public Long findComInduId(Long compId){
		String sql = "SELECT indu_uni_code FROM dmdb.t_bond_com_ext WHERE ama_com_id = " + compId;
		return jdbcTemplate.queryForObject(sql, Long.class);
	}

	/**
	 * 获取主体模型分类，通过发行人id
	 * @param compId
	 * @return
	 */
	public String getModelNameByCompId(Long compId) {
		String result = "";
		try {
			String sql = "SELECT cls.model_id FROM dmdb.t_bond_com_ext AS ext" +
					" INNER JOIN dmdb.t_bond_industry_classification AS cls ON cls.industry_code=ext.indu_uni_code_l4" +
					" WHERE ext.com_uni_code=" + compId;
			result = jdbcTemplate.queryForObject(sql, String.class);
		} catch (Exception ex) {
			log.warn(String.format("getModelNameByCompId error: cannot find modelName with compId[%1$d]", compId));
		}
		return result;
	}

	/**
	 * 获取主体模型列表，通过发行人id
	 * @param issuerIdList
	 * @return
	 */
	public List<Map<String, Object>> getModelNameListByIssuerIdList(List<Long> issuerIdList) {
		String issuerIdStr = issuerIdList.isEmpty() ? "''" : StringUtils.join(issuerIdList.toArray(), ",");
		String sql = "SELECT ext.com_uni_code AS issuerId, cls.model_id AS modelName FROM dmdb.t_bond_com_ext AS ext" +
					" INNER JOIN dmdb.t_bond_industry_classification AS cls ON cls.industry_code=ext.indu_uni_code_l4" +
					" WHERE ext.com_uni_code IN (%1$s)";
		String formatSql = String.format(sql, issuerIdStr);
		return jdbcTemplate.queryForList(formatSql);
	}
	
	/**
	 * 根据主体的行业分类获取表名字
	 * @param compId
	 * @param indicatorType 
	 * @return
	 */
	public String findTableNameByCompId(Long compId, Integer indicatorType){
		String model = findIssuerModelByCompId(compId);
		String pretableName = "t_bond_?_fina_sheet";
		if(Objects.equal(indicatorType, SPECIAL)){
			pretableName = "dm_analysis_?";
		}
		if(Objects.equal(model, INDU) && Objects.equal(indicatorType, FINANCE)){
			model = "manu";
		}
		return pretableName.replace("?", model);
	}
	
	/**
	 * 根据指标类型查找指标code
	 * @param tableName
	 * @param indicatorType
	 * @return
	 */
	public List<String> getFields(String tableName, Integer indicatorType ){
	    String mappingTable = "dmdb.t_bond_indicator_info";
		String searchField = tableName;
		if(Objects.equal(indicatorType, FINANCE)){//财务指标
			mappingTable = "dmdb.field_group_mapping";
			searchField = tableName.replaceAll("t_bond_", "");
		}
		String key = SafeUtils.getString(tableName + "_" + indicatorType + searchField);
		if(FIELDS_CACHE.get(key) == null){
			String  sql = "select column_name from " + mappingTable + " where table_name = '" + searchField + "'";
			if(Objects.equal(indicatorType, SPECIAL)){
			    sql += " AND indicator_type=0 AND effective=1";
			}
			List<String>  fields = jdbcTemplate.queryForList(sql, String.class);
			FIELDS_CACHE.put(key, fields);
			return fields;
		}else{
			return FIELDS_CACHE.get(key);
		}
	}
	
	/**
	 * 获取所有的指标code（财务指标,专项指标）
	 * @return
	 */
	public List<String> getAllFields(){
		if(FIELDS ==null){
		    String sql1 = "SELECT column_name from dmdb.t_bond_indicator_info WHERE indicator_type=0 AND effective=1";
			String sql2 = "SELECT column_name from dmdb.field_group_mapping";
			List<String> finance = jdbcTemplate.queryForList(sql1, String.class);
			List<String> special = jdbcTemplate.queryForList(sql2, String.class);
			finance.addAll(special);
			FIELDS = finance;
		}
		return FIELDS;
	}
	
	/**
	 * 查询所有正向指标
	 * @return
	 */
	public  List<String> findPositiveIndicator(){
		if (POSITIVE_INDICATORS == null) {
			synchronized (this) {
				String sql = "SELECT column_name FROM dmdb.t_bond_indicator_info WHERE field_type IN (1,3) "
				        + " AND indicator_type=0 AND effective=1";
				POSITIVE_INDICATORS = jdbcTemplate.queryForList(sql, String.class);
			}
		}
		return POSITIVE_INDICATORS;
	}
	
	/**
	 * 某一指标是是否指正向指标
	 * @param field
	 * @return
	 */
	public boolean isPositive(String field){
		List<String> positiveIndicators =  findPositiveIndicator();
		return positiveIndicators.contains(field);
	}
	
	/**
	 * 查询所有财务指标
	 * @return
	 */
	public  List<String> findFinanceIndicators(){
		if (FINANCE_INDICATORS == null) {
			synchronized (this) {
				String sql = "SELECT column_name FROM dmdb.field_group_mapping";
				FINANCE_INDICATORS = jdbcTemplate.queryForList(sql, String.class);
			}
		}
		return FINANCE_INDICATORS;
	}
	
	/**
	 * 查找主题行业模型
	 * @param comUniCode
	 * @return
	 */
	public String findModelByComUniCode(Long comUniCode){
		String sql = "SELECT c.model_id FROM dmdb.t_bond_com_ext e INNER JOIN dmdb.t_bond_industry_classification c ON e.indu_uni_code_l4 = c.industry_code WHERE e.com_uni_code = " + comUniCode;
		log.info("findModelByComUniCode sql -->" + sql);
		String model = jdbcTemplate.queryForObject(sql, String.class);
		if(!BANK.equals(model) && !INSU.equals(model) && !SECU.equals(model)){
			model = INDU;
		}
		return model;
	}
	
	/**
	 * 查询有财报的主体compId
	 * @param finDate
	 * @return
	 */
	public List<Long> findCompId(String finDate){
		String sql = "SELECT COMP_ID FROM dmdb.t_bond_manu_fina_sheet WHERE FIN_DATE = '" + finDate + "' " +
				" UNION " +
				" SELECT COMP_ID FROM dmdb.t_bond_bank_fina_sheet WHERE FIN_DATE = '" + finDate + "' " +
				" UNION " +
				" SELECT COMP_ID FROM dmdb.t_bond_secu_fina_sheet WHERE FIN_DATE = '" + finDate + "' " +
				" UNION " +
				" SELECT COMP_ID FROM dmdb.t_bond_insu_fina_sheet WHERE FIN_DATE = '" + finDate + "' " ;
		log.info("findCompId sql -->" + sql);
		return jdbcTemplate.queryForList(sql, Long.class);
	}
	
	/**
	 * 查找主体是否有财报
	 * @param compId
	 * @param finDate
	 * @return
	 */
	public boolean hasFinance(Long compId, String finDate) throws Exception{
		//表判断
		String tableName = findTableNameByCompId(compId, FINANCE);
		String sql = "SELECT count(*) FROM dmdb."  + tableName  +  " WHERE COMP_ID ="  + compId + " AND FIN_DATE = '" + finDate + "'";
		log.info("hasFinance sql ->" + sql );
		Integer result = jdbcTemplate.queryForObject(sql, Integer.class);
	    return !Objects.equal(result, 0);
	}
}
