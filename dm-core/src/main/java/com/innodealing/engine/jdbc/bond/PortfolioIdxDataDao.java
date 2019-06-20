package com.innodealing.engine.jdbc.bond;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.innodealing.model.mongo.dm.BondFavFinaIdxDoc;
import com.innodealing.model.mongo.dm.BondFavPriceIdxDoc;

@Component
public class PortfolioIdxDataDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(PortfolioIdxDataDao.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public <T> List<T> findBondFavRadarIdxList(int index, int limit, int beginRd, int endRd, Class<T> mappedClass) {
		List<T> mapping = null;
		StringBuffer sql = new StringBuffer();
		sql.append(
				"SELECT a.favorite_id AS favoriteId,b.radar_id AS radarId,b.threshold,a.create_time AS createTime,a.user_id AS userId,a.group_id AS groupId,a.bond_uni_code AS bondUniCode,a.openinterest,a.bookmark,a.bookmark_update_time AS bookmarkUpdateTime,a.remark,a.is_expired AS isExpired,c.notified_enable AS notifiedEnable ");
		commonBondFavRadarMappingSql(beginRd, endRd, sql);
		sql.append("LIMIT ").append(index).append(",").append(limit);
		try {
			mapping = jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<>(mappedClass));
		} catch (Exception ex) {
			LOGGER.error("findBondFavMaturityIdxList error," + ex.getMessage(), ex);
		}
		return mapping;
	}
	
	private void commonBondFavRadarMappingSql(int beginRd, int endRd, StringBuffer sql) {
		sql.append(
				" FROM dmdb.t_bond_favorite a LEFT JOIN dmdb.t_bond_favorite_group c ON c.group_id=a.group_id LEFT JOIN dmdb.t_bond_favorite_radar_mapping b ON b.group_id = c.group_id ");
		sql.append(" WHERE a.is_delete = 0 AND b.id > 0 AND  b.radar_id BETWEEN ").append(beginRd).append(" AND ")
				.append(endRd).append(" ORDER BY a.favorite_id DESC ");
	}

	public Integer getBondFavRadarMappingCount(int beginRd, int endRd) {
		try {
			StringBuffer countsql = new StringBuffer();
			countsql.append(" SELECT COUNT(1) ");
			commonBondFavRadarMappingSql(beginRd, endRd, countsql);
			return jdbcTemplate.queryForObject(countsql.toString(), Integer.class);
		} catch (Exception ex) {
			LOGGER.error("getBondFavRadarMappingCount error," + ex.getMessage(), ex);
		}
		return 0;
	}

	public List<BondFavPriceIdxDoc> findBondFavPriceIdxList(int index, int limit) {
		List<BondFavPriceIdxDoc> favPriceIdx = null;
		StringBuffer sql = new StringBuffer();
		sql.append(
				" SELECT a.id,a.favorite_id AS favoriteId,a.price_index AS priceIndex,b.group_id AS groupId,a.price_type AS priceType,a.price_condi AS priceCondi,a.index_value AS indexValue,a.index_unit AS indexUnit,a.status,a.create_time AS createTime,b.user_id AS userId,b.bond_uni_code AS bondUniCode,b.is_delete AS isDelete,c.notified_enable AS notifiedEnable ");
		sql.append(
				" FROM dmdb.t_bond_favorite_price_index a LEFT JOIN dmdb.t_bond_favorite b ON a.favorite_id=b.favorite_id LEFT JOIN dmdb.t_bond_favorite_group c ON c.group_id=b.group_id WHERE b.is_delete=0 ORDER BY a.create_time DESC ");
		sql.append(" LIMIT ").append(index).append(",").append(limit);
		try {
			favPriceIdx = jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<>(BondFavPriceIdxDoc.class));
		} catch (Exception ex) {
			LOGGER.error("findBondFavPriceIdxList error," + ex.getMessage(), ex);
		}
		return favPriceIdx;
	}

	public int getBondFavPriceIdxCount() {
		try {
			StringBuffer countsql = new StringBuffer();
			countsql.append(" SELECT COUNT(1) ");
			countsql.append(
					" FROM dmdb.t_bond_favorite_price_index a LEFT JOIN dmdb.t_bond_favorite b ON a.favorite_id=b.favorite_id LEFT JOIN dmdb.t_bond_favorite_group c ON c.group_id=b.group_id WHERE b.is_delete=0 ");
			return jdbcTemplate.queryForObject(countsql.toString(), Integer.class);
		} catch (Exception ex) {
			LOGGER.error("getBondFavPriceIdxCount error," + ex.getMessage(), ex);
		}
		return 0;
	}

	public List<BondFavFinaIdxDoc> findBondFavFinIdxList(int index, int limit) {
		List<BondFavFinaIdxDoc> favFinIdx = null;
		StringBuffer sql = new StringBuffer();
		sql.append(
				" SELECT a.id,b.group_id AS groupId,a.favorite_id AS favoriteId,a.index_type AS indexType,a.index_code_expr AS indexCodeExpr,a.index_name AS indexName,a.index_value_type AS indexValueType,a.index_value_unit AS indexValueUnit, ");
		sql.append(
				" a.index_value_low AS indexValueLow,a.index_value_high AS indexValueHigh,a.status,a.create_time AS createTime,b.user_id AS userId,b.bond_uni_code AS bondUniCode,b.is_delete AS isDelete,a.index_value_nexus AS indexValueNexus,c.notified_enable AS notifiedEnable ");
		sql.append(
				" FROM dmdb.t_bond_favorite_fina_index a LEFT JOIN dmdb.t_bond_favorite b ON a.favorite_id=b.favorite_id LEFT JOIN dmdb.t_bond_favorite_group c ON c.group_id=b.group_id WHERE a.favorite_id > 0 ORDER BY a.create_time DESC ");
		sql.append(" LIMIT ").append(index).append(",").append(limit);
		try {
			favFinIdx = jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<>(BondFavFinaIdxDoc.class));
		} catch (Exception ex) {
			LOGGER.error("findBondFavPriceIdxList error," + ex.getMessage(), ex);
		}
		return favFinIdx;
	}

	public int getBondFavFinIdxCount() {
		try {
			StringBuffer countsql = new StringBuffer();
			countsql.append(" SELECT COUNT(1) ");
			countsql.append(
					" FROM dmdb.t_bond_favorite_fina_index a LEFT JOIN dmdb.t_bond_favorite b ON a.favorite_id=b.favorite_id LEFT JOIN dmdb.t_bond_favorite_group c ON c.group_id=b.group_id WHERE a.favorite_id > 0 ");
			return jdbcTemplate.queryForObject(countsql.toString(), Integer.class);
		} catch (Exception ex) {
			LOGGER.error("getBondFavFinIdxCount error," + ex.getMessage(), ex);
		}
		return 0;
	}

	public int getBondFavMaturityIdxCount() {

		return 0;
	}

	public List<Integer> findDebtRoleUserIds() {
		List<Integer> userIds = null;
		try {
			userIds = jdbcTemplate.queryForList("SELECT t.user_id FROM innodealing.dm_user_role t WHERE t.role_id=8", Integer.class);
		} catch (Exception ex) {
			LOGGER.error("findDebtRoleUserIds error," + ex.getMessage(), ex);
		}
		return userIds;
	}
	
}
