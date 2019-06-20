package com.innodealing.integration;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.innodealing.BondApp;
import com.innodealing.service.IssIndicatorFieldGroupService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BondApp.class)
@WebIntegrationTest
public class IssIndicatorFieldGroupServiceTest {
	
	private @Autowired IssIndicatorFieldGroupService issIndicatorFieldGroupService;
	
	@Autowired private JdbcTemplate jdbcTemplate;
	
	@Test
	public void buildIndicatorSpecailFilterTest(){
		issIndicatorFieldGroupService.buildIndicatorSpecailFilter();
	}
	
	@Test
	public void buildFieldGroup(){
		//所有的专项指标
		String sql = "SELECT * FROM amaresun.temp_special_indicator";
		List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
		
		//数据库中的现有数据
		String sql2 = "SELECT column_name as columnName FROM amaresun.dm_field_group_mapping";
		List<Map<String,Object>> list2 = jdbcTemplate.queryForList(sql2);
		Map<String,Object> tmp = new HashMap<>();
		for (Map<String, Object> item : list2) {
			 tmp.put(item.get("columnName").toString().toLowerCase() + "", item.get("columnName"));
		}
		int count = 0;
		for (Map<String, Object> action : list) {
			Object field= tmp.get(action.get("columnName").toString().toLowerCase());
			
			String columnName,tableName, fieldName, groupName; int fieldType =1,  percent =1;
			//if(field == null){
				String direction = action.get("direction") + "";
				columnName = action.get("columnName") + "";
				tableName = action.get("tableName") + "";
				tableName = tableName.toLowerCase().replace("fin_ratio", "dm_analysis");
				fieldName = action.get("fieldName") + "";
				groupName = action.get("groupName") + "";
				if(groupName.contains("——")){
					groupName = groupName.substring(groupName.lastIndexOf("——") + 2, groupName.length());
				}
				if(fieldName.contains("％")){
					percent = 1;
					fieldType = 1;
					if(direction.contains("负")) fieldType = 2;
				}else if(fieldName.contains("万")){
					percent = 0;
					fieldType = 3;
					if(direction.contains("负")) fieldType = 4;
				}else{
					percent = 2;
					if(direction.contains("负")) fieldType = 4;
				}
				columnName = action.get("columnName") + "";
				columnName = action.get("columnName") + "";
				count++;
				System.out.println(action);
				save(columnName, tableName, fieldName, groupName, fieldType, percent);
			}
		//}
		System.out.println(count);
	}
	
	@Test
	public void updateFieldGroupName(){
		//所有的专项指标
		String sql = "SELECT * FROM amaresun.temp_special_indicator";
		List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
		Map<String,Object> tmp = new HashMap<>();
		for (Map<String, Object> action : list) {
			String columnName,tableName, fieldName, groupName; int fieldType =1,  percent =1;
			columnName = action.get("columnName") + "";
			tableName = action.get("tableName") + "";
			tableName = tableName.toLowerCase().replace("fin_ratio", "dm_analysis");
			fieldName = action.get("fieldName") + "";
			groupName = action.get("groupName") + "";
			String sqlUpdate = "update amaresun.dm_field_group_mapping set group_name = ? where column_name = ?";
			jdbcTemplate.update(sqlUpdate, groupName, columnName);
		}
	}
	
	
	@Test
	public void buildSpecailField(){
		//所有的专项指标
		String sql = "SELECT * FROM amaresun.temp_special_indicator";
		List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
		
		//数据库中的现有数据
		String sql2 = "SELECT field_name as columnName FROM dmdb.t_bond_finance_special_indicator";
		List<Map<String,Object>> list2 = jdbcTemplate.queryForList(sql2);
		Map<String,Object> tmp = new HashMap<>();
		for (Map<String, Object> item : list2) {
			 tmp.put(item.get("columnName").toString().toLowerCase() + "", item.get("columnName"));
		}
		int count = 0;
		for (Map<String, Object> action : list) {
			Object field= tmp.get(action.get("columnName").toString().toLowerCase());
			if(field == null){
				String columnName = action.get("columnName") + "";
				String sqlInsert = "insert into dmdb.t_bond_finance_special_indicator(field_name, selected) values(?,?)";
				jdbcTemplate.update(sqlInsert, columnName , 0);
				count++;
				System.out.println(action);
				
			}
		}
		System.out.println(count);
	}
	private void save(String columnName, String tableName, String fieldName, String groupName, int fieldType, int percent){
		String querySql = "select count(*) from amaresun.dm_field_group_mapping where table_name = ? and column_name = ?";
		Integer count = jdbcTemplate.queryForObject(querySql, Integer.class, tableName, columnName);
		if(count == 0){
			String sqlInsert = "INSERT INTO amaresun.dm_field_group_mapping(column_name,table_name,field_name,group_name,field_type,percent) VALUES(?,?,?,?,?,?)";
			jdbcTemplate.update(sqlInsert, columnName , tableName, fieldName, groupName, fieldType, percent);
		}else{
			String sqlUpdate = "update amaresun.dm_field_group_mapping set field_type = ?, percent = ?, field_name = ?, group_name = ? where table_name = ? and column_name = ?";
			jdbcTemplate.update(sqlUpdate, fieldType, percent, fieldName, groupName, tableName, columnName);
		}
	}
	
	public static void main(String[] args) {
		System.out.println("Te".equalsIgnoreCase("tE"));
	}
	
}
