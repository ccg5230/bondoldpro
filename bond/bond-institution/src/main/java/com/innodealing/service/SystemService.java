package com.innodealing.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.innodealing.model.mysql.BondInstSystem;
import com.innodealing.util.KitCost;

/**
 * 
 * 系统参数设置
 * 
 * @author 戴永杰
 *
 * @date 2017年9月8日 上午9:35:20
 * @version V1.0
 *
 */
@Service
public class SystemService extends BaseService {

	public BondInstSystem getFinancialStatus(Integer userId, Integer roleId) {
		Map<String, Object> result = null;
		List<Map<String, Object>> tfSys = jdbcTemplate.queryForList("select * from t_bond_inst_system where role_id=?", roleId);

		if (tfSys.size() == 0) {

			BondInstSystem tf = new BondInstSystem();
			tf.setRoleId(roleId);
			tf.setType(1);
			tf.setCreateBy(userId);
			save(tf);
			result = jdbcTemplate.queryForMap("select * from t_bond_inst_system where role_id=?", roleId);
		} else {
			result = tfSys.get(0);
		}
		return KitCost.mapToBean(result, BondInstSystem.class);
	}

	public int setfinancialStatus(int userId, int id, int status) {
		
		BondInstSystem sys = new BondInstSystem();
		sys.setId(id);
		sys.setStatus(status);
		sys.setUpdateBy(userId);
		sys.setUpdateTime(new Date());
		return update(sys, new String[]{"status","update_by","update_time"},"id = ?", sys.getId());
	}
}
