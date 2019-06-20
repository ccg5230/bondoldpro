package com.innodealing.engine.jdbc.bond;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.innodealing.util.SafeUtils;

/**
 * @author xiaochao
 * @time 2017年5月24日
 * @description:
 */
@Component
public class BondFavoriteGroupDAO {

	public final static Logger logger = LoggerFactory.getLogger(BondFavoriteGroupDAO.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public <T> List<T> getGroupEmailVOList(Integer userId, String startDateStr, String endDateStr, Class<T> clzz) {
		String userCriteria = userId == 0 ? "" : String.format("grp.user_id=%1$d AND", userId);
		String sql = "SELECT DISTINCT(msg.group_id) AS groupId, grp.group_name AS groupName, grp.email, grp.user_id AS userId" +
				" FROM t_bond_notification_msg AS msg " +
				" INNER JOIN t_bond_favorite_group AS grp ON grp.group_id=msg.group_id AND grp.is_delete=0" +
				" AND grp.user_id>0 AND grp.email_enable=1 AND grp.email IS NOT NULL AND grp.email!=''" +
				" WHERE %1$s msg.create_time>='%2$s' AND msg.create_time<='%3$s';";
		String finalSql = String.format(sql, userCriteria, startDateStr, endDateStr);
		return jdbcTemplate.query(finalSql, new BeanPropertyRowMapper<>(clzz));
	}

	/**
	 * 根据用户获取投组
	 *
	 * @return
	 */
	public List<Long> getGroupIdListByUserId(Integer userId) {
		String sql = "SELECT group_id FROM t_bond_favorite_group WHERE user_id=%1$d AND is_delete=0;";
		String formatSql = String.format(sql, userId);
		return jdbcTemplate.queryForList(formatSql, Long.class);
	}
}
