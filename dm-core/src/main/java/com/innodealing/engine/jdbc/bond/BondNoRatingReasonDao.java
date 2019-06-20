package com.innodealing.engine.jdbc.bond;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * 
 * @author 赵正来
 *
 */
@Component
public class BondNoRatingReasonDao {
	
	@Autowired private JdbcTemplate jdbcTemplate;
	
	public static final Logger log = LoggerFactory.getLogger(BondNoRatingReasonDao.class);
	
	/**
	 * 按主体id和财报日期查找
	 * @param issuerId
	 * @param finDate
	 * @return
	 */
	public Map<String,Object> findByCompidAndFindate(Long issuerId, String finDate){
		String sql = "SELECT * FROM dmdb.dm_no_rating_reason "
				+ "WHERE comp_id = (SELECT ext.ama_com_id FROM dmdb.t_bond_com_ext ext WHERE ext.com_uni_code = ? ) "
				+ "AND fin_date = ?";
		
		Map<String,Object> map = null;
		//按主体id和财报日期查找
		try {
			map = jdbcTemplate.queryForMap(sql, issuerId, finDate);
		} catch (EmptyResultDataAccessException e) {
			log.info("no result for query[" + "issuerId=" + issuerId + ",finDate=" + finDate + "]");
		}
		
		return map;
	}

	/**
	 * 按主体id查找
	 * @param issuerId
	 * @return
	 */
	public Map<String, Object> findByCompid(Long issuerId) {
		String sql2 = "SELECT * FROM dmdb.dm_no_rating_reason "
				+ "WHERE fin_date is NULL  and comp_id = (SELECT ext.ama_com_id FROM dmdb.t_bond_com_ext ext WHERE ext.com_uni_code = ? ) limit 1";
		try {
			return jdbcTemplate.queryForMap(sql2, issuerId);
		} catch (EmptyResultDataAccessException e) {
			log.info("no result for query[" + "issuerId=" + issuerId + "]");
			return null;
		}
	}
}
