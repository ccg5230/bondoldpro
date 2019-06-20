/**
 * 
 */
package com.innodealing.engine.jdbc.bond;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author xiaochao
 * @date 2017年9月12日
 * @clasename BondFavoriteFinaIndexDAO.java
 * @decription TODO
 */
@Component
public class BondFavoriteFinaIndexDAO {
	public final static Logger logger = LoggerFactory.getLogger(BondFavoriteFinaIndexDAO.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

    public int batchDeleteByFavIdList(List<Integer> favIdList) {
		String favIdListStr = favIdList.isEmpty() ? "''" : StringUtils.join(favIdList.toArray(), ",");
		String sql = "DELETE FROM t_bond_favorite_fina_index WHERE favorite_id IN (%1$s);";
		String formatSql = String.format(sql, favIdListStr);
		return jdbcTemplate.update(formatSql);
    }

	public int batchDeleteByGroupIdList(List<Integer> groupIdList) {
		String groupIdListStr = groupIdList.isEmpty() ? "''" : StringUtils.join(groupIdList.toArray(), ",");
		String sql = "DELETE FROM t_bond_favorite_fina_index WHERE group_id IN (%1$s);";
		String formatSql = String.format(sql, groupIdListStr);
		return jdbcTemplate.update(formatSql);
	}
}
