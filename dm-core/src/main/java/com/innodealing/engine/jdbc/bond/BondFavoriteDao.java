/**
 * 
 */
package com.innodealing.engine.jdbc.bond;

import com.innodealing.domain.vo.bond.PdMappingVo;
import com.innodealing.model.dm.bond.BondFavorite;
import com.innodealing.model.dm.bond.BondNotificationMsg;
import com.innodealing.util.SafeUtils;
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
 * @author stephen.ma
 * @date 2017年1月4日
 * @clasename BondFavoriteDao.java
 * @decription TODO
 */
@Component
public class BondFavoriteDao {

	public final static Logger logger = LoggerFactory.getLogger(BondFavoriteDao.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<Long> findDifbondIdByUserId(Integer userId) {
		StringBuffer sql = new StringBuffer();
		sql.append(
				"SELECT DISTINCT a.bond_uni_code FROM dmdb.t_bond_favorite a,dmdb.t_bond_favorite_group b WHERE a.group_id=b.group_id AND b.is_delete=0 AND a.is_delete=0 AND b.notified_enable=1 AND a.bond_uni_code > 0 AND a.user_id=");
		sql.append(userId);
		sql.append(" ORDER BY a.create_time ASC ");

		return jdbcTemplate.queryForList(sql.toString(), Long.class);
	}

	public List<Long> findDifbondIdByUserIdAndGroupId(Integer userId, Integer groupId) {
		StringBuffer sql = new StringBuffer();
		sql.append(
				"SELECT DISTINCT a.bond_uni_code FROM dmdb.t_bond_favorite a WHERE a.is_delete=0 AND a.bond_uni_code > 0 ");
		sql.append("AND a.user_id=").append(userId).append(" AND a.group_id = ").append(groupId);

		return jdbcTemplate.queryForList(sql.toString(), Long.class);
	}

	public List<Long> findDifbondIdByUserIdAndEventtype(Integer userId, String eventtype) {
		StringBuffer sql = new StringBuffer();
		sql.append(
				"SELECT DISTINCT a.bond_uni_code FROM dmdb.t_bond_favorite a,dmdb.t_bond_favorite_group b WHERE a.group_id=b.group_id AND b.is_delete=0 AND a.is_delete=0 AND b.notified_enable=1 AND a.bond_uni_code > 0 AND a.user_id=");
		sql.append(userId);
		sql.append(" AND b.notified_eventtype LIKE '%").append(eventtype).append("%'");
		sql.append(" ORDER BY a.create_time ASC ");

		return jdbcTemplate.queryForList(sql.toString(), Long.class);
	}

	public List<Long> getFinaDiffBondIds(Integer userId) {
		String sql = "SELECT DISTINCT fav.bond_uni_code FROM dmdb.t_bond_favorite AS fav" +
				" INNER JOIN dmdb.t_bond_favorite_group AS grp ON grp.group_id=fav.group_id AND grp.is_delete=0 AND grp.notified_enable=1" +
				" INNER JOIN dmdb.t_bond_favorite_fina_index AS fina ON fina.favorite_id=fav.favorite_id" +
				" WHERE fav.is_delete=0 AND fav.bond_uni_code > 0 AND fav.user_id=%1$d" +
				" ORDER BY fav.create_time ASC;";
		String formatSql = String.format(sql, userId);
		return jdbcTemplate.queryForList(formatSql, Long.class);
	}

	public List<Long> getPriceDiffBondIds(Integer userId) {
		String sql = "SELECT DISTINCT fav.bond_uni_code FROM dmdb.t_bond_favorite AS fav" +
				" INNER JOIN dmdb.t_bond_favorite_group AS grp ON grp.group_id=fav.group_id AND grp.is_delete=0 AND grp.notified_enable=1" +
				" INNER JOIN dmdb.t_bond_favorite_price_index AS price ON price.favorite_id=fav.favorite_id" +
				" WHERE fav.is_delete=0 AND fav.bond_uni_code > 0 AND fav.user_id=%1$d" +
				" ORDER BY fav.create_time ASC;";
		String formatSql = String.format(sql, userId);
		return jdbcTemplate.queryForList(formatSql, Long.class);
	}

	public List<Long> getCommonDiffBondIds(Integer userId, String eventTypes) {
		eventTypes = StringUtils.isBlank(eventTypes) ? "''" : eventTypes;
		String sql = "SELECT DISTINCT fav.bond_uni_code FROM dmdb.t_bond_favorite AS fav" +
				" INNER JOIN dmdb.t_bond_favorite_group AS grp ON grp.group_id=fav.group_id AND grp.is_delete=0 AND grp.notified_enable=1" +
				" INNER JOIN dmdb.t_bond_favorite_radar_mapping AS rdr ON rdr.group_id=grp.group_id AND rdr.radar_id IN (%1$s)" +
				" WHERE fav.is_delete=0 AND fav.bond_uni_code > 0 AND fav.user_id=%2$d" +
				" ORDER BY fav.create_time ASC;";
		String formatSql = String.format(sql, eventTypes, userId);
		return jdbcTemplate.queryForList(formatSql, Long.class);
	}

	public Long findMaxBookmarkCursor() {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT MAX(t.seq_id) AS bookmarkCursor FROM dmdb.t_bond_notification_msg t");

		return jdbcTemplate.queryForObject(sql.toString(), Long.class);
	}

	public String findEventtypeStrInGroup(Integer userId) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT LEFT(GROUP_CONCAT(t.eventtype),50)FROM ( ");
		sql.append("		SELECT b.notified_eventtype AS eventtype ");
		sql.append("		FROM dmdb.t_bond_favorite_group b ");
		sql.append("		WHERE b.is_delete = 0 ");
		sql.append("		AND b.notified_enable = 1 ");
		sql.append("		AND b.user_id = ").append(userId);
		sql.append("		AND b.notified_eventtype IS NOT NULL ");
		sql.append("		GROUP BY b.notified_eventtype ");
		sql.append("	)t ORDER BY LENGTH(t.eventtype) DESC ");

		return jdbcTemplate.queryForObject(sql.toString(), String.class);
	}

	public Long getMsgCountByUserIdAndGroupIdAndEventTypes(Integer userId, Integer groupId, List<Integer> eventTypes) {
		StringBuffer sql = new StringBuffer();
		sql.append(
				" SELECT COUNT(1) FROM dmdb.t_bond_favorite a,dmdb.t_bond_notification_msg b WHERE a.bond_uni_code = b.bond_id AND a.is_delete = 0 ");
		sql.append(" AND a.user_id = ").append(userId);
		sql.append(" AND a.group_id = ").append(groupId);
		sql.append(" AND b.seq_id > a.bookmark AND b.create_time > a.create_time ");
		if (null != eventTypes && eventTypes.size() > 0) {
			sql.append(" AND b.event_type IN").append("(").append(eventTypesToStr(eventTypes)).append(")");
		}

		return jdbcTemplate.queryForObject(sql.toString(), Long.class);
	}

	public List<Long> getNewMsgIdListByConstraint(Integer userId, Integer groupId, Integer favoriteId, Integer dateDiff,
			List<Long> radarTypes) {
		String radarTypeStr = radarTypes.isEmpty() ? "''" : StringUtils.join(radarTypes.toArray(), ",");
		String dateRestrict = "";
		if (dateDiff >= 0) {
			String startDateStr = SafeUtils.getDateFromNow(-dateDiff);
			dateRestrict = String.format("AND msg.create_time>='%1$s'", startDateStr);
		}
		String sql = "SELECT msg.seq_id FROM t_bond_favorite AS fav, t_bond_notification_msg AS msg"
				+ " WHERE fav.bond_uni_code=msg.bond_id AND fav.is_delete=0 AND fav.user_id=%1$d"
				+ " AND fav.favorite_id=%2$d AND msg.event_type IN (%3$s) AND msg.group_id=%4$d"
				+ " AND msg.seq_id>fav.bookmark AND msg.create_time>fav.create_time %5$s;";
		String finalSql = String.format(sql, userId, favoriteId, radarTypeStr, groupId, dateRestrict);
		return jdbcTemplate.queryForList(finalSql, Long.class);
	}

	public int updateExpiredBond(Long bondId) {
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE dmdb.t_bond_favorite tv SET tv.is_expired=1 WHERE tv.bond_uni_code = ").append(bondId);
		int res = jdbcTemplate.update(sql.toString());

		logger.info(sql.toString() + ", result=" + res);

		return res;
	}

	private static String eventTypesToStr(List<Integer> eventTypes) {
		StringBuilder eventtypes = null;
		if (null != eventTypes && eventTypes.size() > 0) {
			eventtypes = new StringBuilder();
			for (Integer eventType : eventTypes) {
				eventtypes.append(eventType).append(",");
			}
		}

		return eventtypes == null ? "" : (eventtypes.toString().substring(0, eventtypes.toString().length() - 1));
	}

	public <T> List<T> getSimpleFavoriteGroupVOListByUserId(Integer userId, Class<T> clzz) {
		String sql = "SELECT grp.group_id AS groupId, grp.group_name AS groupName," +
				" grp.notified_enable AS notifiedEnable, grp.email AS email," +
				" grp.email_enable AS emailEnable, fav.favorite_id AS favoriteId" +
				" FROM t_bond_favorite_group AS grp" +
				" LEFT JOIN t_bond_favorite AS fav ON fav.group_id=grp.group_id AND fav.is_delete=0 AND fav.bond_uni_code>0" +
				" WHERE grp.user_id=%1$d AND grp.is_delete=0 ORDER BY grp.group_id;";
		String formatSql = String.format(sql, userId);
		return jdbcTemplate.query(formatSql, new BeanPropertyRowMapper<>(clzz));
	}

	public <T> List<T> getSimpleFavoriteGroupVOListByGroupIdList(List<Integer> groupIdList, Class<T> clzz) {
		String groupIdStr = groupIdList.isEmpty() ? "''" : StringUtils.join(groupIdList.toArray(), ",");
		String sql = "SELECT grp.group_id AS groupId, grp.group_name AS groupName," +
				" grp.notified_enable AS notifiedEnable, grp.email AS email," +
				" grp.email_enable AS emailEnable, fav.favorite_id AS favoriteId" +
				" FROM t_bond_favorite_group AS grp" +
				" LEFT JOIN t_bond_favorite AS fav ON fav.group_id=grp.group_id AND fav.is_delete=0 AND fav.bond_uni_code>0" +
				" WHERE grp.group_id IN (%1$s) AND grp.is_delete=0 ORDER BY grp.group_id;";
		String formatSql = String.format(sql, groupIdStr);
		return jdbcTemplate.query(formatSql, new BeanPropertyRowMapper<>(clzz));
	}

	public <T> List<T> getShortFavoriteGroupVOListByUserId(Integer userId, Class<T> clzz) {
		String sql = "SELECT grp.group_id AS groupId, grp.group_name AS groupName," +
				" grp.notified_enable AS notifiedEnable, grp.email_enable AS emailEnable, grp.email" +
				" FROM t_bond_favorite_group AS grp" +
				" WHERE grp.user_id=%1$d AND grp.is_delete=0 ORDER BY grp.group_id DESC;";
		String formatSql = String.format(sql, userId);
		return jdbcTemplate.query(formatSql, new BeanPropertyRowMapper<>(clzz));
	}

	public List<Long> getSketchyNewMsgIdListByGroupId(Integer groupId) {
		String sql = "SELECT msg.seq_id FROM t_bond_favorite AS fav" +
				" INNER JOIN t_bond_notification_msg AS msg ON msg.group_id=%1$d" +
				" AND msg.bond_id=fav.bond_uni_code AND msg.create_time>=fav.create_time AND msg.seq_id>fav.bookmark" +
				" WHERE fav.group_id=%1$d AND fav.is_delete=0;";
		// 为了兼容历史消息，先注释 AND msg.group_id>0
		String finalSql = String.format(sql, groupId);
		return jdbcTemplate.queryForList(finalSql, Long.class);
	}

	public List<Integer> getFavoriteListWithPositiveMsgCount(Integer groupId, Integer dateDiff, List<Long> subRadarTypeList) {
		String dateRestrict = "";
		if (dateDiff >= 0) {
			String startDateStr = SafeUtils.getDateFromNow(-dateDiff);
			dateRestrict = String.format("AND msg.create_time>'%1$s'", startDateStr);
		}
		String radarTypeStr = subRadarTypeList.isEmpty() ? "''" : StringUtils.join(subRadarTypeList.toArray(), ",");
		String sql = "SELECT DISTINCT fav.favorite_id FROM t_bond_favorite AS fav" +
				" INNER JOIN t_bond_favorite_group AS grp ON grp.group_id=fav.group_id AND grp.is_delete=0" +
				" INNER JOIN t_bond_notification_msg AS msg ON msg.bond_id=fav.bond_uni_code" +
				" AND msg.group_id=%3$d AND msg.create_time>fav.create_time AND msg.event_type IN (%1$s) %2$s" +
				" WHERE fav.group_id=%3$d AND fav.is_delete=0 AND fav.bond_uni_code>0;";
		String finalSql = String.format(sql, radarTypeStr, dateRestrict, groupId);
		return jdbcTemplate.queryForList(finalSql, Integer.class);
	}

	public List<Integer> getFavIdListByStartDate(Integer groupId, String startDateStr, String endDateStr) {
		String sql = "SELECT favorite_id FROM t_bond_favorite AS fav" +
				" WHERE fav.group_id=%1$d AND fav.is_delete=0 AND fav.bond_uni_code>0 AND" +
				" (SELECT COUNT(1) FROM t_bond_notification_msg AS msg" +
				" WHERE msg.bond_id=fav.bond_uni_code AND msg.group_id=%1$d" +
				" AND msg.create_time>='%2$s' AND msg.create_time<='%3$s')>0;";
		String finalSql = String.format(sql, groupId, startDateStr, endDateStr);
		return jdbcTemplate.queryForList(finalSql, Integer.class);
	}

	public List<Integer> getValidFavoriteCountList(List<Integer> groupIds) {
		String groupIdListStr = groupIds.isEmpty() ? "''" : StringUtils.join(groupIds.toArray(), ",");
		String sql = "SELECT count(1) as count FROM t_bond_favorite_group AS grp" +
				" LEFT JOIN t_bond_favorite AS fav ON fav.group_id=grp.group_id AND fav.is_delete=0 AND fav.bond_uni_code>0" +
				" WHERE grp.is_delete=0 AND grp.group_id IN (%1$s) GROUP BY grp.group_id;";
		String finalSql = String.format(sql, groupIdListStr);
		return jdbcTemplate.queryForList(finalSql, Integer.class);
	}

	public List<Integer> getPrivateRadarFavIdList(List<Integer> favIdList) {
		String favIdListStr = favIdList.isEmpty() ? "''" : StringUtils.join(favIdList.toArray(), ",");
		String sql = "SELECT DISTINCT rdr.favorite_id FROM (" +
				" SELECT favorite_id FROM t_bond_favorite_price_index WHERE favorite_id IN (%1$s) AND (group_id=0 OR (group_id>0 AND `status`=0))" +
				" UNION ALL" +
				" SELECT favorite_id FROM t_bond_favorite_fina_index WHERE favorite_id IN (%1$s) AND (group_id=0 OR (group_id>0 AND `status`=0))" +
				" ) AS rdr ORDER BY rdr.favorite_id;";
		String finalSql = String.format(sql, favIdListStr);
		return jdbcTemplate.queryForList(finalSql, Integer.class);
	}

	public <T> List<T> getDuplicatedFavList(Class<T> clzz) {
		String sql = "SELECT fav.* FROM t_bond_favorite AS fav," +
				" (SELECT group_id, bond_uni_code, is_delete FROM t_bond_favorite WHERE is_delete=0" +
				" GROUP BY group_id, bond_uni_code, is_delete HAVING count(1)>1) AS aggr" +
				" WHERE fav.group_id=aggr.group_id AND fav.bond_uni_code=aggr.bond_uni_code" +
				" AND fav.is_delete=0;";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(clzz));
	}

	public int batchLogicDelete(List<Integer> favIdList, String currDateStr) {
		String favIdListStr = favIdList.isEmpty() ? "''" : StringUtils.join(favIdList.toArray(), ",");
		String sql = "UPDATE t_bond_favorite SET is_delete=1, update_time='%1$s' WHERE favorite_id IN (%2$s)";
		String finalSql = String.format(sql, currDateStr, favIdListStr);
		return jdbcTemplate.update(finalSql);
	}

	/**
	 * 根据用户id获取所有投组的关注债券的债券BondUniCode列表
	 * @param userId
	 * @return
	 */
	public List<Long> getBondUniCodeListByUserId(Integer userId) {
		String sql = "SELECT DISTINCT bond_uni_code FROM t_bond_favorite AS fav " +
				"INNER JOIN t_bond_favorite_group AS grp ON grp.group_id=fav.group_id AND grp.user_id=%1$d AND grp.is_delete=0 " +
				"WHERE fav.user_id=%1$d AND fav.is_delete=0";
		String formatSql = String.format(sql, userId);
		return jdbcTemplate.queryForList(formatSql, Long.class);
	}

	/**
	 * 根据用户id获取所有有效的关注债券列表
	 * @param userId
	 * @param clzz
	 * @param <T>
	 * @return
	 */
	public <T> List<T> getFavListByUserId(Integer userId, Class<T> clzz) {
		String sql = "SELECT fav.* FROM t_bond_favorite AS fav " +
				"INNER JOIN t_bond_favorite_group AS grp ON grp.group_id=fav.group_id AND grp.user_id=%1$d AND grp.is_delete=0 " +
				"WHERE fav.user_id=%1$d AND fav.is_delete=0";
		String formatSql = String.format(sql, userId);
		return jdbcTemplate.query(formatSql, new BeanPropertyRowMapper<>(clzz));
	}
}
