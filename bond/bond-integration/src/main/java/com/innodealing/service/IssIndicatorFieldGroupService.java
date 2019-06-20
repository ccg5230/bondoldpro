package com.innodealing.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.innodealing.engine.jdbc.bond.BondIndicatorExpressionDao;
import com.innodealing.model.mongo.dm.bond.detail.analyse.IssIndicatorFieldGroupMapping;
import com.innodealing.model.mongo.dm.bond.finance.IndicatorField;
import com.innodealing.model.mongo.dm.bond.finance.IndicatorFieldGroup;
import com.innodealing.model.mongo.dm.bond.finance.SpecialIndicatorFilterDoc;
import com.innodealing.util.SafeUtils;
/**
 * 财务专项指标分类service
 * @author zhaozhenglai
 * @date 2017年2月13日下午5:33:16
 *Copyright © 2016 DealingMatrix.cn. All Rights Reserved.
 */
@Service
public class IssIndicatorFieldGroupService {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired 
	private BondIndicatorExpressionDao dondIndicatorExpressionDao;

	@Autowired
	private MongoTemplate mongoTemplate;
	
	public boolean buildIndicatorSpecailFilter(){
		try {
			String[] tables = {"dm_analysis_bank", "dm_analysis_secu", "dm_analysis_indu", "dm_analysis_insu"};
			//专项指标
            String sql = "SELECT i.column_name as field_name,rg.group_id,s.selected FROM dmdb.t_bond_indicator_info i LEFT JOIN dmdb.t_bond_indicator_relevance_group rg ON i.id = rg.indicator_id LEFT JOIN (SELECT * FROM  dmdb.t_bond_finance_special_indicator WHERE selected = 1) as s ON i.column_name = s.field_name";
            List<Map<String,Object>> indicatorsSpecial = jdbcTemplate.queryForList(sql);
            //专项指标表达式汉子描述
            Map<String,String> fieldExpressMap = dondIndicatorExpressionDao.findExpressDescription();
			for (String tableName : tables) {
				Map<String,IndicatorFieldGroup> indicatorFieldGroupMap = new HashMap<>();
				
				//所有指标
				List<IssIndicatorFieldGroupMapping> indicators = findSpecailInicator(tableName);
				Map<String,IssIndicatorFieldGroupMapping> indicatorsMap = new HashMap<>();
				for (IssIndicatorFieldGroupMapping item : indicators) {
					indicatorsMap.put(item.getColumnName() + ":" + item.getGroupId(), item);
				}
				String type = tableName.substring(tableName.lastIndexOf("_") + 1, tableName.length());
				Map<String,String> groupIdName = new HashMap<>();
				for (Map<String, Object> map : indicatorsSpecial) {
					String field = map.get("field_name") + ":" + map.get("group_id");
					int selected = SafeUtils.getInt(map.get("selected") + "");
					IssIndicatorFieldGroupMapping issIndicatorField = indicatorsMap.get(field);
					if(issIndicatorField == null){
						continue;
					}
					String groupId = issIndicatorField.getGroupId();
					groupIdName.put(groupId, issIndicatorField.getGroupName());
					if(indicatorFieldGroupMap.get(groupId) == null){
						List<IndicatorField> indicatorFields = new ArrayList<>();
						IndicatorField indicatorField = new IndicatorField();
						indicatorField.setField(issIndicatorField.getColumnName());
						indicatorField.setFieldName(issIndicatorField.getFieldName());
						indicatorField.setSelected(selected);
						indicatorField.setExpressDescription(fieldExpressMap.get(issIndicatorField.getColumnName()));
						indicatorFields.add(indicatorField);
						//指标分组
						IndicatorFieldGroup indicatorFieldGroup = new IndicatorFieldGroup();
						indicatorFieldGroup.setGroup(groupId);
						indicatorFieldGroup.setIndicatorFields(indicatorFields);
						indicatorFieldGroupMap.put(groupId, indicatorFieldGroup);
					}else{
						IndicatorField indicatorField = new IndicatorField();
						indicatorField.setField(issIndicatorField.getColumnName());
						indicatorField.setFieldName(issIndicatorField.getFieldName());
						indicatorField.setSelected(selected);
						indicatorField.setExpressDescription(fieldExpressMap.get(issIndicatorField.getColumnName()));
						indicatorFieldGroupMap.get(groupId).getIndicatorFields().add(indicatorField);
					}
				}
				//保存
				List<IndicatorFieldGroup> indicatorFieldGroups = new ArrayList<>();
				indicatorFieldGroupMap.forEach((k, v) -> {
					v.setGroup(groupIdName.get(k));
					indicatorFieldGroups.add(v);
				});
				SpecialIndicatorFilterDoc specialIndicatorFilterDoc = new SpecialIndicatorFilterDoc();
				specialIndicatorFilterDoc.setIndicatorFieldGroups(indicatorFieldGroups);
				specialIndicatorFilterDoc.setType(type);
				mongoTemplate.remove(new Query(Criteria.where("type").is(type)), SpecialIndicatorFilterDoc.class);
				mongoTemplate.save(specialIndicatorFilterDoc);
			}
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 获取专项指标信息
	 * @return
	 */
	public List<IssIndicatorFieldGroupMapping> findSpecailInicator(){
//		String sql = "select column_name AS columnName,table_name AS tableName,field_name AS fieldName,group_name AS groupName,field_type as fieldType,percent FROM  /*amaresun*/ dmdb.dm_field_group_mapping WHERE column_name IN (SELECT field_name FROM dmdb.t_bond_finance_special_indicator) ORDER BY table_name ASC, group_name ASC";
	    StringBuffer sql = new StringBuffer();
	    sql.append("SELECT "). 
	    append(" f.column_name  AS columnName,").
	    append(" f.table_name  AS tableName,").
	    append(" f.field_name AS fieldName,").
	    append(" g.group_name AS groupName,").
	    append(" f.field_type AS fieldType,").
	    append(" f.percent AS percent ").
	    append(" from t_bond_indicator_info  f ").
	    append(" LEFT JOIN t_bond_indicator_relevance_group r ON f.id = r.indicator_id ").
	    append(" LEFT JOIN t_bond_indicator_group g ON r.group_id = g.id ").
	    append(" WHERE f.indicator_type=0 AND f.effective=1 "). 
	    append(" AND f.column_name IN ( ").
	    append(" SELECT field_name FROM dmdb.t_bond_finance_special_indicator ").
	    append(" ) ").
	    append(" ORDER BY f.table_name ASC, g.group_name ASC ");
		return jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<IssIndicatorFieldGroupMapping>(IssIndicatorFieldGroupMapping.class));
	}
	
	/**
	 * 获取专项指标信息
	 * @return
	 */
	public List<IssIndicatorFieldGroupMapping> findSpecailInicator(String tableName){
//		String sql = "select column_name AS columnName,table_name AS tableName,field_name AS fieldName,group_name AS groupName,field_type as fieldType,percent FROM  /*amaresun*/ dmdb.dm_field_group_mapping WHERE table_name = '" + tableName + "' and column_name IN (SELECT field_name FROM dmdb.t_bond_finance_special_indicator) ORDER BY table_name ASC, group_name ASC";
	    StringBuffer sql = new StringBuffer();
	    sql.append("SELECT "). 
        append(" f.column_name  AS columnName,").
        append(" f.table_name  AS tableName,").
        append(" f.field_name AS fieldName,").
        append(" g.group_name AS groupName,").
        append(" f.field_type AS fieldType,").
        append(" r.group_id,").
        append(" f.percent AS percent ").
        append(" from t_bond_indicator_info  f ").
        append(" LEFT JOIN t_bond_indicator_relevance_group r ON f.id = r.indicator_id ").
        append(" LEFT JOIN t_bond_indicator_group g ON r.group_id = g.id ").
        append(" WHERE f.indicator_type=0 AND f.effective=1 ").
        append(" AND f.table_name = '").append(tableName).append("' ").
//        append(" AND f.column_name IN ( ").
//        append(" SELECT field_name FROM dmdb.t_bond_finance_special_indicator ").
//        append(" ) ").
        append(" ORDER BY f.table_name ASC, g.group_name ASC ");
	    return jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<IssIndicatorFieldGroupMapping>(IssIndicatorFieldGroupMapping.class));
	}
	
	public static void main(String[] args) {
		String str = "ytyt_fdf_bank";
		System.out.println(str.substring(str.lastIndexOf("_") + 1, str.length()));
		System.out.println(Calendar.getInstance().get(Calendar.YEAR) - 2);
	}
}
