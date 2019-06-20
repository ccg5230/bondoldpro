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

import com.innodealing.datacanal.model.BondUniCodeMap;

/**
 * 
 * @author 赵正来
 *
 */
@Component
public class BondUniCodeMapDao {
	//dmdb 数据源
	private @Autowired   JdbcTemplate dmdbJdbcTemplate;
	
	//中诚信中的com_uni_code 和 dm数据中心的com_uni_code映射
	public static Map<Long,Long> BOND_UNI_CODE_DATA_CENTER_MAP = null;
	
	//中诚信中的com_uni_code 和 dm数据中心的com_uni_code映射
	public static Map<Long,Long> BOND_DATA_CENTER_UNI_CODE_MAP = null;
	
	private final Logger logger = LoggerFactory.getLogger(BondUniCodeMapDao.class);
	
	/**
	 * 查找债券统一编码映射关系
	 * @return
	 */
	public List<BondUniCodeMap> findBondComUniCodeMap(){
		String sql =  "select map.bond_uni_code,map.bond_uni_code_data_center from dmdb.t_bond_uni_code_map map ";
		logger.info("sql -> " + sql);
		return dmdbJdbcTemplate.query(sql, new BeanPropertyRowMapper<>(BondUniCodeMap.class));
	}
	
	/**
	 * 新增
	 * @param bondUniCode
	 * @return bondUniCodeDateCenter
	 */
	public Long insert(Long bondUniCode){
		String sql = "insert into dmdb.t_bond_uni_code_map(bond_uni_code) values(?)";
		Long bondUniCodeDateCenter = null;
		try {
			GeneratedKeyHolder holder = new GeneratedKeyHolder();
			dmdbJdbcTemplate.update(new PreparedStatementCreator() {
				@Override
				public java.sql.PreparedStatement createPreparedStatement(java.sql.Connection con) throws SQLException {
					PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
					statement.setLong(1, bondUniCode);
					return statement;
				}
			}, holder);
			bondUniCodeDateCenter = holder.getKey().longValue();
		} catch (Exception e) {
			if(e.getMessage().contains("Duplicate ")){
				bondUniCodeDateCenter  = dmdbJdbcTemplate
						.queryForObject("select bond_uni_code_data_center from dmdb.t_bond_uni_code_map where bond_uni_code =  " +bondUniCode, Long.class) ;
				logger.error("数据已存在：" + sql);
			}else{
				throw e;
			}
		}
		logger.debug("sql -> " + sql);
		return bondUniCodeDateCenter;
	}
	
	/**
	 * 查找bondUniCodeDateCenter by bondUniCode
	 * @param bondUniCode
	 * @return bondUniCodeDateCenter
	 */
	public Long findBondUniCodeDataCenterFromCache(Long bondUniCode){
		synchronized (this) {
			if(BOND_UNI_CODE_DATA_CENTER_MAP == null){
				BOND_UNI_CODE_DATA_CENTER_MAP = new HashMap<>();
				findBondComUniCodeMap().forEach(map ->{
					BOND_UNI_CODE_DATA_CENTER_MAP.put(map.getBondUniCode(), map.getBondUniCodeDataCenter());
				});
			}
			Long comCodeDataCenter = BOND_UNI_CODE_DATA_CENTER_MAP.get(bondUniCode);
			if(comCodeDataCenter == null){
				comCodeDataCenter = insert(bondUniCode);
				BOND_UNI_CODE_DATA_CENTER_MAP.put(bondUniCode, comCodeDataCenter);
				if(BOND_DATA_CENTER_UNI_CODE_MAP == null){
					BOND_DATA_CENTER_UNI_CODE_MAP = new HashMap<>();
				}
				BOND_DATA_CENTER_UNI_CODE_MAP.put(comCodeDataCenter,bondUniCode);
			}
			return comCodeDataCenter;
		}
	}
	
	/**
	 * 查找bondUniCodeDateCenter by bondUniCode
	 * @param bondUniCode
	 * @return bondUniCodeDateCenter
	 */
	public Long findBondUniCodeFromCache(Long bondUniCodeDateCenter){
		synchronized (this) {
			if(BOND_DATA_CENTER_UNI_CODE_MAP == null){
				BOND_DATA_CENTER_UNI_CODE_MAP = new HashMap<>();
				findBondComUniCodeMap().forEach(map ->{
					BOND_DATA_CENTER_UNI_CODE_MAP.put(map.getBondUniCodeDataCenter(), map.getBondUniCode());
				});
			}
			Long bondUniCode = BOND_DATA_CENTER_UNI_CODE_MAP.get(bondUniCodeDateCenter);
			return bondUniCode;
		}
	}
}
