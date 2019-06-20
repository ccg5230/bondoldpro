/**
 * 
 */
package com.innodealing.engine.jdbc.bond;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author xiaochao
 * @date 2017年8月30日
 * @clasename BondFavoriteRadarSchemaDAO.java
 * @decription TODO
 */
@Component
public class BondFavoriteRadarMappingDAO {

	public final static Logger logger = LoggerFactory.getLogger(BondFavoriteRadarMappingDAO.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<Long> getDistinctGroupIdListByRadarId(Long radarId) {
		String sql = "SELECT DISTINCT(group_id) FROM t_bond_favorite_radar_mapping WHERE radar_id=%1$d AND group_id>0;";
		String formatSql = String.format(sql, radarId);
		return jdbcTemplate.queryForList(formatSql, Long.class);
	}

	public List<Long> getAllDistinctGroupIdList() {
		String sql = "SELECT DISTINCT(group_id) FROM t_bond_favorite_radar_mapping WHERE group_id>0;";
		return jdbcTemplate.queryForList(sql, Long.class);
	}

	public int batchDeleteByGroupIdList(List<Integer> groupIdList) {
		String groupIdListStr = groupIdList.isEmpty() ? "''" : StringUtils.join(groupIdList.toArray(), ",");
		String sql = "DELETE FROM t_bond_favorite_radar_mapping WHERE group_id IN (%1$s);";
		String formatSql = String.format(sql, groupIdListStr);
		return jdbcTemplate.update(formatSql);
	}
}
