package com.innodealing.dao.jdbc.dm.base;

import java.util.List;

import org.springframework.stereotype.Component;

import com.innodealing.dao.jdbc.BaseDao;

@Component
public class BondPerFinanceDao extends BaseDao<String>{

	public List<String> getTaskIdInPerFin(){
		String sql = "SELECT t.task_id FROM t_bond_per_finance t WHERE t.status=0 AND t.deleted=0";
		
		return jdbcTemplate.queryForList(sql, String.class);
	}
	
}
