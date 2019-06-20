package com.innodealing.dao;

import com.innodealing.domain.vo.*;
import com.innodealing.model.dm.bond.BondFavorite;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * APP债券扩展查询类
 */
@Component
public class BondCombineDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 根据用户名和雷达获取关注债券
     *
     * @return
     */
    public List<BondFavorite> findByUserIdAndRadar(Integer userId, List<Long> radarTypeList) {
        String radarTypeStr = radarTypeList.isEmpty() ? "''" : StringUtils.join(radarTypeList.toArray(), ",");
        String sql = "SELECT * FROM t_bond_favorite fav" +
                " INNER JOIN t_bond_notification_msg AS msg ON fav.bond_uni_code= msg.bond_id" +
                " WHERE fav.user_id = %1$d and fav.is_delete = 0 AND msg.event_type IN (%2$d)";
        String formatSql = String.format(sql, userId, radarTypeStr);
        return jdbcTemplate.query(formatSql, new BeanPropertyRowMapper<>(BondFavorite.class));
    }

    /**
     * 根据父ID获取子雷达
     *
     * @return
     */
    public List<Long> getAllRadarByParentId(Integer parentId) {
        String sql = "SELECT id FROM t_bond_favorite_radar_schema" +
                " WHERE parent_id = %1$d AND status = 1";
        String formatSql = String.format(sql, parentId);
        return jdbcTemplate.queryForList(formatSql, Long.class);
    }

    /**
     * 根据名称搜索用户投组，债券列表
     *
     * @return
     */
    public List<BondSearchItemVO> searchBondAllList(Integer userId, String searchContent, Integer pageIndex, Integer pageSize) {
        String sql = "SELECT grp.group_id AS groupId, grp.group_name AS groupName, '' AS bondId, '' AS bondName" +
                " FROM t_bond_favorite_group AS grp" +
                " WHERE grp.user_id=%1$d AND grp.group_name LIKE '%%%2$s%%' AND grp.is_delete=0" +
                " UNION" +
                " SELECT '' AS groupId, '' AS groupName, inf.bond_uni_code AS bondId, inf.bond_short_name AS bondName" +
                " FROM t_bond_basic_info AS inf" +
                " WHERE inf.bond_short_name LIKE '%%%2$s%%' OR inf.iss_name LIKE '%%%2$s%%'" +
                " ORDER BY groupId DESC, groupName, bondId DESC, bondName" +
                " LIMIT %3$d, %4$d";
        String formatSql = String.format(sql, userId, searchContent, pageIndex, pageSize);
        return jdbcTemplate.query(formatSql, new BeanPropertyRowMapper<>(BondSearchItemVO.class));
    }

    /**
     * 根据名称搜索用户投组，债券列表(count)
     *
     * @return
     */
    public Long getPageCountOfSearchBondAll(Integer userId, String searchContent) {
        String sql = "SELECT SUM(count) FROM" +
                " (SELECT count(*) as count FROM t_bond_favorite_group AS grp" +
                " WHERE grp.user_id=%1$d AND grp.group_name LIKE '%%%2$s%%' AND grp.is_delete=0" +
                " UNION" +
                " SELECT count(*) as count" +
                " FROM t_bond_basic_info AS inf" +
                " WHERE inf.bond_short_name LIKE '%%%2$s%%' OR inf.iss_name LIKE '%%%2$s%%') a";
        String formatSql = String.format(sql, userId, searchContent);
        return jdbcTemplate.queryForObject(formatSql, Long.class);
    }

    /**
     * 根据名称搜索债券列表
     *
     * @return
     */
    public List<BondSearchItemVO> searchBondList(Integer userId, Long groupId, String searchContent, Integer page, Integer limit) {
        Integer start = page * limit;
        String sql = "SELECT inf.bond_uni_code AS bondId, inf.bond_short_name AS bondName," +
                " CASE WHEN grp.group_id IS NULL THEN FALSE ELSE TRUE END AS favorite" +
                " FROM t_bond_basic_info AS inf" +
                " LEFT JOIN t_bond_favorite AS fav ON inf.bond_uni_code = fav.bond_uni_code" +
                " LEFT JOIN t_bond_favorite_group grp ON grp.group_id = %2$d" +
                " WHERE inf.bond_short_name LIKE '%%%3$s%%' OR inf.iss_name LIKE '%%%3$s%%' AND fav.user_id = %1$d" +
                " ORDER BY fav.create_time DESC" +
                " LIMIT %4$d, %5$d";
        String formatSql = String.format(sql, userId, groupId, searchContent, start, limit);
        return jdbcTemplate.query(formatSql, new BeanPropertyRowMapper<>(BondSearchItemVO.class));
    }

    /**
     * 根据名称搜索债券列表(count)
     *
     * @return
     */
    public Long getPageCountOfSearchBondList(Integer userId, Long groupId, String searchContent) {
        String sql = "SELECT COUNT(1) FROM t_bond_basic_info AS inf" +
                " LEFT JOIN t_bond_favorite AS fav ON inf.bond_uni_code = fav.bond_uni_code" +
                " LEFT JOIN t_bond_favorite_group grp ON grp.group_id = %2$d" +
                " WHERE inf.bond_short_name LIKE '%%%3$s%%' OR inf.iss_name LIKE '%%%3$s%%' AND fav.user_id = %1$d";
        String formatSql = String.format(sql, groupId, userId, searchContent);
        return jdbcTemplate.queryForObject(formatSql, Long.class);
    }

    /**
     * 根据雷达获取用户所有消息(雷达分组)
     *
     * @return
     */
    public List<BondRadarMsgVO> getAllFavoriteMsg(Integer userId, List<Long> readMsgIdList) {
        String sql = "SELECT rs.type_name AS radarTypeName, msg.msg_content AS lastMsgContent,COUNT(1) AS radarMsgNumber," +
                " CASE WHEN rs.parent_id = 0 THEN rs.id ELSE rs.parent_id END AS radarTypeId" +
                " FROM t_bond_favorite AS fav" +
                " INNER JOIN t_bond_notification_msg AS msg ON msg.bond_id=fav.bond_uni_code" +
                " AND msg.create_time>=fav.create_time AND msg.seq_id>fav.bookmark" +
                " INNER JOIN t_bond_favorite_radar_schema AS rs ON rs.id=msg.event_type AND rs.`status`=1" +
                " WHERE fav.is_delete=0 AND fav.user_id = %1$d AND msg.seq_id NOT IN (%2$s)" +
                " GROUP BY event_type" +
                " ORDER BY msg.create_time ASC";
        String readMsgIdStr = readMsgIdList.isEmpty() ? "''" : StringUtils.join(readMsgIdList.toArray(), ",");
        String formatSql = String.format(sql, userId, readMsgIdStr);
        return jdbcTemplate.query(formatSql, new BeanPropertyRowMapper<>(BondRadarMsgVO.class));
    }

    /**
     * 根据类型获取用户所有消息(雷达过滤)
     *
     * @return
     */
    public List<BondSimpleNotiMsgVO> getAllFavoriteMsgByRadar(Integer userId, List<Long> radarTypeList, Integer page, Integer limit) {
        String radarTypeStr = radarTypeList.isEmpty() ? "''" : StringUtils.join(radarTypeList.toArray(), ",");
        Integer start = page * limit;
        String sql = "SELECT msg.seq_id AS msgId, msg.bond_id AS bondId,detail.bond_short_name AS bondName, msg.msg_content AS msgContent," +
                " msg.event_type AS radarTypeId, rs.type_name AS radarTypeName, msg.news_index AS newsIndex," +
                " msg.important AS important, msg.emotion_tag AS emotionTag, msg.create_time AS createTime" +
                " FROM t_bond_favorite AS fav" +
                " INNER JOIN t_bond_notification_msg AS msg ON msg.bond_id=fav.bond_uni_code" +
                " AND msg.create_time>=fav.create_time AND msg.seq_id>fav.bookmark" +
                " INNER JOIN t_bond_favorite_radar_schema AS rs ON rs.id=msg.event_type AND rs.`status`=1" +
                " INNER JOIN t_bond_basic_info AS detail ON detail.bond_uni_code=msg.bond_id" +
                " WHERE fav.is_delete=0 AND fav.user_id = %1$d AND msg.event_type IN (%2$s)" +
                " ORDER BY msg.create_time DESC LIMIT %3$d, %4$d;";
        String formatSql = String.format(sql, userId, radarTypeStr, start, limit);
        return jdbcTemplate.query(formatSql, new BeanPropertyRowMapper<>(BondSimpleNotiMsgVO.class));
    }

    /**
     * 获取某一种雷达的所有消息数量
     *
     * @return
     */
    public Long getCountOfPagedMsgByRadar(Integer userId, List<Long> radarTypeList) {
        String radarTypeStr = radarTypeList.isEmpty() ? "''" : StringUtils.join(radarTypeList.toArray(), ",");
        String sql = "SELECT COUNT(1) FROM t_bond_notification_msg AS msg" +
                " INNER JOIN t_bond_favorite AS fav ON fav.bond_uni_code=msg.bond_id " +
                " WHERE msg.event_type IN (%2$s) AND fav.user_id=%1$d AND fav.is_delete=0" +
                " AND msg.create_time>fav.create_time AND msg.seq_id>fav.bookmark";
        String formatSql = String.format(sql, userId, radarTypeStr);
        return jdbcTemplate.queryForObject(formatSql, Long.class);
    }

    /**
     * 获取用户未读消息ID
     *
     * @return
     */
    public List<Long> getSketchyNewMsgIdListByUserId(Integer userId) {
        String sql = "SELECT msg.seq_id FROM t_bond_favorite AS fav" +
                " INNER JOIN t_bond_notification_msg AS msg ON msg.bond_id=fav.bond_uni_code" +
                " AND msg.create_time>=fav.create_time AND msg.seq_id>fav.bookmark" +
                " WHERE fav.userId=%1$d AND fav.is_delete=0;";
        String finalSql = String.format(sql, userId);
        return jdbcTemplate.queryForList(finalSql, Long.class);
    }

    /**
     * 获取用户所有未读消息
     *
     * @return
     */
    public List<BondSimpleNotiMsgVO> getAllFavoriteUnreadMsg(Integer userId, List<Long> readMsgIdList, Integer page, Integer limit) {
        String readMsgIdStr = readMsgIdList.isEmpty() ? "''" : StringUtils.join(readMsgIdList.toArray(), ",");
        Integer start = page * limit;
        String sql = "SELECT msg.seq_id AS msgId, msg.bond_id AS bondId, msg.msg_content AS msgContent," +
                " msg.event_type AS radarTypeId, msg.type_name AS radarTypeName, msg.news_index AS newsIndex," +
                " msg.important AS important, msg.emotion_tag AS emotionTag, msg.create_time AS createTime" +
                " FROM" +
                " (SELECT ms.*, fav.bookmark, rs.type_name," +
                " FROM t_bond_notification_msg AS ms" +
                " INNER JOIN t_bond_favorite AS fav ON fav.bond_uni_code=ms.bond_id " +
                " INNER JOIN t_bond_favorite_radar_schema AS rs ON rs.id=ms.event_type AND rs.`status`=1" +
                " WHERE fav.user_id=%1$d AND fav.is_delete=0" +
                " AND ms.create_time>fav.create_time AND ms.seq_id>fav.bookmark AND ms.seq_id NOT IN %2$d) AS msg" +
                " ORDER BY readStatus, create_time DESC, seq_id DESC LIMIT %3$d, %4$d;";
        String formatSql = String.format(sql, userId, readMsgIdStr, start, limit);
        return jdbcTemplate.query(formatSql, new BeanPropertyRowMapper<>(BondSimpleNotiMsgVO.class));
    }

    public List<BondSimpleNotiMsgVO> getPagedGroupMsgVOList(Integer userId, Integer groupId, List<Long> bondIdList,
                                                            List<Long> readMsgIdListList, List<Long> radarTypeList,
                                                            int page, Integer limit) {
        String bondIdStr = bondIdList.isEmpty() ? "''" : StringUtils.join(bondIdList.toArray(), ",");
        String readMsgIdStr = readMsgIdListList.isEmpty() ? "''" : StringUtils.join(readMsgIdListList.toArray(), ",");
        String radarTypeStr = radarTypeList.isEmpty() ? "''" : StringUtils.join(radarTypeList.toArray(), ",");
        Integer start = page * limit;
        String sql = "SELECT msg.seq_id AS msgId, msg.bond_id AS bondId, msg.msg_content AS msgContent," +
                " msg.event_type AS radarTypeId, msg.type_name AS radarTypeName, msg.news_index AS newsIndex," +
                " msg.important AS important, msg.create_time AS createTime," +
                " CASE WHEN msg.emotion_tag IS NULL THEN '-1' ELSE msg.emotion_tag END AS emotionTag," +
                " CASE WHEN msg.seq_id IN (%4$s) THEN '1' WHEN msg.seq_id<=msg.bookmark THEN '1' ELSE '0' END AS readStatus" +
                " FROM" +
                " (SELECT smsg.*, fav.bookmark, rs.type_name FROM t_bond_notification_msg AS smsg" +
                " INNER JOIN t_bond_favorite AS fav ON fav.user_id=%1$d AND fav.group_id=%2$d AND fav.bond_uni_code=smsg.bond_id AND fav.is_delete=0" +
                " INNER JOIN t_bond_favorite_radar_schema AS rs ON rs.id=smsg.event_type AND rs.`status`=1" +
                " WHERE smsg.bond_id IN (%3$s) AND smsg.event_type IN (%5$s)" +
                " AND smsg.group_id=%2$d AND smsg.create_time>fav.create_time) AS msg" +
                " ORDER BY readStatus, create_time DESC, seq_id DESC LIMIT %6$d, %7$d;";
        String formatSql = String.format(sql, userId, groupId, bondIdStr, readMsgIdStr, radarTypeStr, start, limit);
        return jdbcTemplate.query(formatSql, new BeanPropertyRowMapper<>(BondSimpleNotiMsgVO.class));
    }
}
