package com.innodealing.engine.jdbc.bond;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.innodealing.model.dm.bond.BondCityAnnex;

@Component
@Repository
public class BondCityAnnexDao {
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	public final static Logger logger = LoggerFactory.getLogger(BondCityAnnexDao.class);
	
	public List<BondCityAnnex> findAllComUniCode(){
		String sql = "select com_uni_code as comUniCode from t_bond_city_annex";
		RowMapper<BondCityAnnex> rowMapper = new BeanPropertyRowMapper<BondCityAnnex>(BondCityAnnex.class);
		List<BondCityAnnex> list = jdbcTemplate.query(sql, rowMapper);
		logger.info("findAllComUniCode----->" + sql);
		return list;
	}

}
