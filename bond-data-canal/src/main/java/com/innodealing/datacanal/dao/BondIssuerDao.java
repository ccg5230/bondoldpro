package com.innodealing.datacanal.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.innodealing.datacanal.model.BondIssuerInduSwMap;

/**
 * 主体dao
 * @author 赵正来
 *
 */
@Component
public class BondIssuerDao {

	//dmdb 数据源
	private @Autowired   JdbcTemplate dmdbJdbcTemplate;
	
	//主体和主体的申万行业信息映射关系
	
	private static Map<Long,Long> COM_UNI_INDU_CODE_MAP = null;
	
	private final Logger logger = LoggerFactory.getLogger(BondIssuerDao.class);
	
	/**
	 * 查找主体和申万行业map
	 * @return
	 */
	public List<BondIssuerInduSwMap> findIssuerInduSwMap(){
		String sql =  "select com_uni_code,indu_uni_code_sw from dmdb.t_bond_com_ext";
		logger.info("sql -> " + sql);
		return dmdbJdbcTemplate.query(sql, new BeanPropertyRowMapper<>(BondIssuerInduSwMap.class));
	}
	
	/**
	 * 查找主体申万行业
	 * @param comUniCode
	 * @return indu_uni_code_sw
	 */
	public Long findInduUniCodeSw(Long comUniCode){
		String sql = "select indu_uni_code_sw from dmdb.t_bond_com_ext where com_uni_code = " + comUniCode;
		Long induUniCode = null;
		try {
			induUniCode = dmdbJdbcTemplate.queryForObject(sql, Long.class);
		} catch (Exception e) {
			logger.error("没有主体信息comUniCode=" +comUniCode + e.getMessage() + ".sql ->" + sql);
		}
		return induUniCode;
	}
	
	/**
	 * 
	 * @param comUniCode
	 * @return
	 */
	public Long findInduUniCodeSwFromCache(Long comUniCode){
		synchronized (this) {
			if(COM_UNI_INDU_CODE_MAP == null){
				COM_UNI_INDU_CODE_MAP = new HashMap<>();
				findIssuerInduSwMap().forEach(map -> {
					COM_UNI_INDU_CODE_MAP.put(map.getComUniCode(), map.getInduUniCodeSw());
				});
			}
			Long induSw = COM_UNI_INDU_CODE_MAP.get(comUniCode);
			if(induSw == null){
				induSw = findInduUniCodeSw(comUniCode);
				COM_UNI_INDU_CODE_MAP.put(comUniCode, induSw);
			}
			return induSw;
		}
	}
	
}
