package com.innodealing.engine.jdbc.im;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * @author stephen.ma
 * @date 2016年9月20日
 * @clasename IsHolidayDao.java
 * @decription TODO
 */
@Component
public class IsHolidayDao {

    public final static Logger logger = LoggerFactory.getLogger(IsHolidayDao.class);
    
    private @Autowired JdbcTemplate jdbcTemplate;
    
    public List<Map<String, Object>> findWorkingDays(){
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT t.id,t.date FROM t_is_holiday t WHERE t.is_holiday=0 AND t.date<=DATE_FORMAT(NOW(), '%Y-%m-%d') ORDER BY t.date DESC LIMIT 6");
		
		List<Map<String, Object>> result = jdbcTemplate.queryForList(sql.toString());
    	
		return result;
    }
    
    public Date findLastTradingDay(){
		String sql = "SELECT t.date FROM t_is_holiday AS t WHERE t.is_holiday=0 AND t.date<DATE_FORMAT(NOW(), '%Y-%m-%d')"
				+ " ORDER BY t.date DESC LIMIT 1";
		Date result = jdbcTemplate.queryForObject(sql, Date.class);
		return result;
    }
	
}
