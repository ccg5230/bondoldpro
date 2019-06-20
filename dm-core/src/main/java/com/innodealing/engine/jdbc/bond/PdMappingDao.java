package com.innodealing.engine.jdbc.bond;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.innodealing.domain.vo.bond.PdMappingVo;

@Component
public class PdMappingDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(PdMappingDao.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static List<PdMappingVo> mapping = null;

	public List<PdMappingVo> getPdMapping() {
		String sql = "SELECT pd, rating FROM dmdb.t_bond_pd_par ORDER BY id ASC";
		if (mapping == null) {
			mapping = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(PdMappingVo.class));
		}
		return mapping;
	}

	//@Cacheable(value = "ratingMapCache", key = "#rating")
	public PdMappingVo getPdMappingByRating(String rating) {
		try {
			String sql = String.format("SELECT pd, rating FROM dmdb.t_bond_pd_par WHERE rating ='%1$s'", rating);
			return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<PdMappingVo>(PdMappingVo.class));
		} catch (Exception ex) {
			LOGGER.error("getPdMappingByRating error:{}", ex.getMessage());
		}
		return null;
	}

}
