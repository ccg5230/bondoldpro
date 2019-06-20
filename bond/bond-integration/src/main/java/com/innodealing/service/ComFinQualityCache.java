package com.innodealing.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.innodealing.aop.NoLogging;
import com.innodealing.model.ComFinQuality;


@Component
public class ComFinQualityCache {

	private static final Logger LOG = LoggerFactory.getLogger(ComFinQualityCache.class);

	@Autowired
	private  JdbcTemplate jdbcTemplate;

	@SuppressWarnings("rawtypes")
	@Autowired
	private RedisTemplate redisTemplate;

	public class ComFinQualityKey implements Serializable
	{
		Long comUniCode;
		Integer year;
		
		public ComFinQualityKey(Long comUniCode, Integer year) {
			super();
			this.comUniCode = comUniCode;
			this.year = year;
		}
		
		@Override
		public int hashCode() {
			return new HashCodeBuilder(17, 31). // two randomly chosen prime numbers
					append(comUniCode).
					append(year).
					toHashCode();
		}
		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof ComFinQualityKey))
				return false;
			if (obj == this)
				return true;

			ComFinQualityKey rhs = (ComFinQualityKey) obj;
			return new EqualsBuilder().
					append(comUniCode, rhs.comUniCode).
					append(year, rhs.year).
					isEquals();
		}
		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "ComFinQualityKey [" + (comUniCode != null ? "comUniCode=" + comUniCode + ", " : "")
					+ (year != null ? "year=" + year : "") + "]";
		}
	}

	private HashMap<ComFinQualityKey, BigDecimal> comFinQualityCache = 
			new HashMap<ComFinQualityKey, BigDecimal>();

	public ComFinQualityCache(){}

	Double convertFinQuality(Float quality) {
		if(quality == null){
			return null;
		}
		if (quality >= 2.05 && quality < 100) {
			return 0.5;
		}
		else if (quality >= 1.75 && quality < 2.05) {
			return 1.0;
		}
		else if (quality >= 1.45 && quality < 1.75) {
			return 1.5;
		}
		else if (quality >= 1.15 && quality < 1.45) {
			return 2.0;
		}
		else if (quality >= 0.85 && quality < 1.15) {
			return 2.5;
		}
		else if (quality >= 0.55 && quality < 0.85) {
			return 3.0;
		}
		else if (quality >= 0.25 && quality < 0.55) {
			return 3.5;
		}
		else if (quality >= -0.05 && quality < 0.25) {
			return 4.0;
		}
		else if (quality >= -0.35 && quality < -0.05) {
			return 4.5;
		}
		else if (quality >= -100 && quality < -0.35) {
			return 5.0;
		}
		return null;
	}

	public void loadData()
	{
		String sql = "select dmdb.t_bond_com_ext.com_uni_code,  /*amaresun*/ dmdb.dm_fin_quality_analysis.year,  /*amaresun*/ dmdb.dm_fin_quality_analysis.quan_score from  /*amaresun*/ dmdb.dm_fin_quality_analysis \r\n" + 
				"inner join dmdb.t_bond_com_ext on  /*amaresun*/ dmdb.dm_fin_quality_analysis.custid = dmdb.t_bond_com_ext.ama_com_id\r\n" + 
				"where   /*amaresun*/ dmdb.dm_fin_quality_analysis.visible=1";
		List<ComFinQuality> results = (List<ComFinQuality>) jdbcTemplate.query(
				sql, new BeanPropertyRowMapper<ComFinQuality>(ComFinQuality.class));
		if (results == null || results.isEmpty()) {
			LOG.error("failed to load dm_fin_quality_analysis");
			return ;
		}
		for (ComFinQuality quality : results) {
			Double ranking  = convertFinQuality(quality.getQuanScore());
			if (ranking != null) {
				BigDecimal rating = new BigDecimal(ranking);
				ComFinQualityKey key = new ComFinQualityKey(quality.getComUniCode(), quality.getYear());
				redisTemplate.opsForValue().set(key.toString(), rating);
			}
		}	
	}

	@NoLogging
	public BigDecimal getComFinQuality(ComFinQualityKey key)
	{
		return (BigDecimal) redisTemplate.opsForValue().get(key.toString());
	}

}
