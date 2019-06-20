package com.innodealing.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class BondUserOperationService {

	private static final Logger LOG = LoggerFactory.getLogger(BondUserOperationService.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * @param id
	 * @param type
	 *            类型 1[机构] 2[用户]
	 * @return
	 */
	public Boolean instInduAuthorization(Long id, Integer type) {
		LOG.info("instInduAuthorization id:" + id + ",type:" + type);
		Boolean flay = false;
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT COUNT(1) FROM innodealing.dm_bond_industry_relation \n\t");
		sql.append("WHERE type = %1$d AND  ref_id = %2$d");
		flay = jdbcTemplate.queryForObject(String.format(sql.toString(), type, id), Integer.class) > 0;
		return flay;
	}
	
	public Map<String,Object> bondAuthorization(Integer userId){
		StringBuffer sql = new StringBuffer("");
		sql.append("SELECT func.code as code , 1 as status FROM innodealing.dm_bond_func func \n\t");
		sql.append("INNER JOIN innodealing.dm_bond_role_func rf ON func.id = rf.func_id \n\t");
		sql.append("INNER JOIN innodealing.dm_bond_role role ON role.id = rf.role_id \n\t");
		sql.append("INNER JOIN innodealing.dm_bond_user_role ur ON ur.role_id = role.id AND ur.user_id = " + userId);
		List<Map<String,Object>> list = jdbcTemplate.queryForList(sql.toString());
		Map<String,Object> result = new HashMap<String,Object>();
		list.stream().forEach(obj->{
			result.put(obj.get("code").toString(), obj.get("status"));
		});
		return result;
	}

}
