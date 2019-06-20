package com.innodealing.datacanal.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.innodealing.datacanal.config.Config;
import com.innodealing.datacanal.constant.ConstantCalnal;
import com.innodealing.datacanal.model.BondDataTableMap;

/**
 * 监控同步情况
 * @author 在这里
 *
 */
@Service
public class MonitorService {

	public static void main(String[] args) {
		DataSource dataSource = new DataSource();
		dataSource.setUrl("innodealing");
		dataSource.setUsername("inndealing");
		dataSource.setPassword("inndealing");
		dataSource.setDriverClassName("jdbc:mysql://192.168.8.190:3306/dmdb");
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		List<Map<String,Object>> arg = new ArrayList<>();
		Config.TABLE_MAP.forEach((k, v) -> {
			String  srcTableName = k;
			String[] columns = v.split("=")[1].split(",");
			String  destTableName= v.split("=")[0];
			for (int i = 0; i < columns.length; i++) {
				String[] cs = columns[i].split("-");
				
//				Object[] a = new Object[5];
				String[] sks = cs[cs.length - 1].split(":");
				String destColumnName = sks[sks.length-1];
				String srcColumnName = sks.length == 2 ? cs[0].split(":")[1] : destColumnName;
//				a[0] = srcTableName;
//				a[1] = srcColumnName;
//				a[2] = destTableName;
//				a[3] = destColumnName;
				
				BondDataTableMap dataTableMap = new  BondDataTableMap();
				dataTableMap.setDestColumnName(destColumnName);
				dataTableMap.setDestTableName(destTableName);
				dataTableMap.setSrcColumnName(srcColumnName);
				dataTableMap.setSrcTableName(srcTableName);
				if(sks.length == 2){
					dataTableMap.setIsUnique(ConstantCalnal.TRUE);
					
				}else{
					dataTableMap.setIsUnique(ConstantCalnal.FALSE);
				}
				System.out.println(dataTableMap);
			}
			
			
		});
		
		
		
		String sql = "insert canal.bond_data_table_map(src_table_name,src_column_name,dest_table_name,dest_column_name,is_unique) values(?,?,?,?,?,?,?)";
		System.out.println(jdbcTemplate.toString());
		
	}
	
}
