/**
 * 
 */
package com.innodealing.engine.jdbc.bond;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author xiaochao
 * @date 2017年8月25日
 * @clasename BondFavoriteRadarSchemaDAO.java
 * @decription TODO
 */
@Component
public class BondFavoriteRadarSchemaDAO {

	public final static Logger logger = LoggerFactory.getLogger(BondFavoriteRadarSchemaDAO.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public <T> List<T> getTypeNameMap(List<Long> radarTypeList, Class<T> clzz) {
		String radarTypeStr = radarTypeList.isEmpty() ? "''" : StringUtils.join(radarTypeList.toArray(), ",");
		String sql = "SELECT s.id AS radarId, CASE WHEN s.rootName IS NULL THEN s.subName ELSE CONCAT(s.rootName, '-', s.subName) END AS radarTypeName" +
				" FROM (SELECT s1.id, s1.`name` AS subName, s2.`name` AS rootName FROM t_bond_favorite_radar_schema AS s1" +
				" LEFT JOIN t_bond_favorite_radar_schema AS s2 ON s2.id=s1.parent_id AND s2.`status`=1" +
				" WHERE s1.id IN (%1$s) AND s1.`status`=1) AS s;";
		String formatSql = String.format(sql, radarTypeStr);
		return jdbcTemplate.query(formatSql, new BeanPropertyRowMapper<>(clzz));
	}
}
