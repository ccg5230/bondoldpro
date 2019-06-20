package com.innodealing.datacanal.dao;
/**
 * 错误日志
 * @author 赵正来
 *
 */

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
/**
 * 记录数据同步错误日志信息
 * @author 赵正来
 *
 */
@Component
public class BondDataCanalLogDao {

	
	private @ Autowired JdbcTemplate dataCenterJdbcTemplate;
	
	
	private static Logger logger = LoggerFactory.getLogger(BondDataCanalLogDao.class);
	
	/**
	 * insert
	 * @param msg
	 */
	public void insert(String msg){
		String sql = "insert into canal.bond_data_canal_log(msg,create_time) values(?,?)";
		try {
			dataCenterJdbcTemplate.update(sql, msg, new Date());
		} catch (DataAccessException e) {
			logger.error(e.getMessage() + "args:" + msg, e);
		}
	}
}
