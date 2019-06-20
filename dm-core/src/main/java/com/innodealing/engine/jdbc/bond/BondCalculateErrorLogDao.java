package com.innodealing.engine.jdbc.bond;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * 指标计算日志dao
 * @author 赵正来
 *
 */
@Component
public class BondCalculateErrorLogDao {

	private @Autowired JdbcTemplate jdbcTemplate;
	
	
	Logger log = LoggerFactory.getLogger(BondCalculateErrorLogDao.class);
	
	public List<String> findErrorType(Long comUniCode, Date finDate){
		String sql = "SELECT error_type FROM dmdb.t_bond_calculate_error_log WHERE com_uni_code = ? AND fin_date = ? GROUP BY error_type";
		Object[] args = {comUniCode, finDate};
		log.info("findErrorType -> sql=" + sql + ",args=" + Arrays.toString(args));
		return jdbcTemplate.queryForList(sql, args , String.class);
	}
}
