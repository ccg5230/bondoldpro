package com.innodealing.datacanal.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;

import com.innodealing.datacanal.model.BondComUniCodeMap;

@Component
public class BondComUniCodeMapDao {
	//dmdb 数据源
	private @Autowired   JdbcTemplate dmdbJdbcTemplate;
	
	//中诚信中的com_uni_code 和 dm数据中心的com_uni_code映射
	public static Map<Long,Long> COM_UNI_CODE_DATA_CENTER_MAP = null;
	
	private final Logger logger = LoggerFactory.getLogger(BondComUniCodeMapDao.class);
	
	/**
	 * 查找主体和申万行业map
	 * @return
	 */
	public List<BondComUniCodeMap> findBondComUniCodeMap(){
		String sql =  "select map.com_uni_code,map.com_uni_code_data_center from dmdb.t_bond_com_uni_code_map map";
		logger.debug("sql -> " + sql);
		return dmdbJdbcTemplate.query(sql, new BeanPropertyRowMapper<>(BondComUniCodeMap.class));
	}
	
	/**
	 * 新增
	 * @param comUniCode
	 * @return comUniCodeDateCenter
	 */
	public Long insert(Long comUniCode){
		String sql = "insert into dmdb.t_bond_com_uni_code_map(com_uni_code) values(?)";
		//int r = dmdbJdbcTemplate.update(sql,comUniCode);
		Long comUniCodeDateCenter = null;
		try {
			GeneratedKeyHolder holder = new GeneratedKeyHolder();
			dmdbJdbcTemplate.update(new PreparedStatementCreator() {
				@Override
				public java.sql.PreparedStatement createPreparedStatement(java.sql.Connection con) throws SQLException {
					PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
					statement.setLong(1, comUniCode);
					return statement;
				}
			}, holder);
			comUniCodeDateCenter = holder.getKey().longValue();
		} catch (Exception e) {
			if(e.getMessage().contains("Duplicate ")){
				comUniCodeDateCenter  = dmdbJdbcTemplate
						.queryForObject("select com_uni_code_data_center from dmdb.t_bond_com_uni_code_map where com_uni_code =  " +comUniCode, Long.class) ;
				logger.error("数据已存在：" + sql);
			}else{
				throw e;
			}
		}
		logger.debug("sql -> " + sql);
		return comUniCodeDateCenter;
	}
	
	/**
	 * 查找comUniCodeDateCenter by comUniCode
	 * @param comUniCode
	 * @return comUniCodeDateCenter
	 */
	public Long findComUniCodeDataCenterFromCache(Long comUniCode){
		synchronized (this) {
			if(COM_UNI_CODE_DATA_CENTER_MAP == null){
				COM_UNI_CODE_DATA_CENTER_MAP = new HashMap<>();
				findBondComUniCodeMap().forEach(map ->{
					COM_UNI_CODE_DATA_CENTER_MAP.put(map.getComUniCode(), map.getComUniCodeDataCenter());
				});
			}
			Long comCodeDataCenter = COM_UNI_CODE_DATA_CENTER_MAP.get(comUniCode);
			if(comCodeDataCenter == null){
				comCodeDataCenter = insert(comUniCode);
				COM_UNI_CODE_DATA_CENTER_MAP.put(comUniCode, comCodeDataCenter);
			}
			return comCodeDataCenter;
		}
	}
}
