/**
 * 
 */
package com.innodealing.engine.jdbc.bond;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * @author stephen.ma
 * @date 2017年3月16日
 * @clasename BondComExtDao.java
 * @decription TODO
 */
@Component
public class BondComExtDao {
	
	public final static Logger logger = LoggerFactory.getLogger(BondComExtDao.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
    @Cacheable(value = "comsCountCache") 
	public Integer getTotalComCount(){
    	Integer count = 0;
    	try{
    		String sql = "SELECT COUNT(1) FROM dmdb.t_bond_com_ext";
    		
    		count = jdbcTemplate.queryForObject(sql, Integer.class);
    	}catch(Exception ex){
    		logger.error("getTotalComCount error:"+ex.getMessage(), ex);
    	}
		return count;
	}
    
    
    @Cacheable(value = "induComCountCache", key="#induUniCode") 
	public Integer getTotalInduComCount(Long induUniCode){
    	Integer count = 0;
    	try{
    		String sql = "SELECT COUNT(1) FROM dmdb.t_bond_com_ext t WHERE t.indu_uni_code="+induUniCode;
    		
    		count = jdbcTemplate.queryForObject(sql, Integer.class);
    	}catch(Exception ex){
    		logger.error("getTotalComCount error:"+ex.getMessage(), ex);
    	}
		return count;
	}

}
