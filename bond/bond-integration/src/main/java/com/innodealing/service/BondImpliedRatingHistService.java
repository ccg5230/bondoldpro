package com.innodealing.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author feng.ma
 * @date 2017年3月27日 下午4:24:53
 * @describe
 */
public class BondImpliedRatingHistService {

	private static final Logger LOGGER = LoggerFactory.getLogger(BondImpliedRatingHistService.class);

	@Autowired
	protected JdbcTemplate jdbcTemplate;

	public List<Map<String, Object>> getLastTwodaysInHist() {
		try {
			String sql = "SELECT a.data_date FROM dmdb.t_bond_implied_rating_hist a GROUP BY DATE_FORMAT(a.data_date,'%Y-%m-%d') ORDER BY a.data_date DESC LIMIT 2";

			return jdbcTemplate.queryForList(sql);
		} catch (Exception ex) {
			LOGGER.error("getLastTwodaysInHist error," + ex.getMessage(), ex);
		}
		return null;
	}

}
