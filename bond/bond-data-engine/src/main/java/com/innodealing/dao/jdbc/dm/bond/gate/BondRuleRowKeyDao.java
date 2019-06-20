package com.innodealing.dao.jdbc.dm.bond.gate;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;

import com.innodealing.dao.jdbc.BaseDao;
import com.innodealing.model.dm.bond.gate.BondRuleRowKey;

/**
 * @author kunkun.zhou
 * @date 2016年12月07日
 * @clasename BondRuleRowKeyDao.java
 * @decription TODO
 */
@Component
public class BondRuleRowKeyDao extends BaseDao<BondRuleRowKey> {
	
	public BondRuleRowKey getRowKey(String tableName) {
		String sql = "select * FROM rule_row_key where TBL_NM='"+tableName+"' limit 1";
		List<BondRuleRowKey> list = gateJdbcTemplate.query(sql, new BeanPropertyRowMapper(BondRuleRowKey.class));
		if (null == list || list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}

}
