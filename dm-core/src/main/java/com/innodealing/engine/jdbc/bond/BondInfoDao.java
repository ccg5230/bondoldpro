/**
 * BondInfoDao.java
 * com.innodealing.engine.jdbc.bond
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2017年5月12日 		yig
 *
 * Copyright (c) 2017, DealingMatrix All Rights Reserved.
*/

package com.innodealing.engine.jdbc.bond;

import com.innodealing.model.dm.bond.BondInfo;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * ClassName:BondInfoDao
 *
 * @author   yig
 * @version  
 * @since    Ver 1.1
 * @Date	 2017年5月12日		下午3:09:38
 *
 * @see 	 
 */
@Component
public class BondInfoDao {
    public final static Logger logger = LoggerFactory.getLogger(BondInfoDao.class);
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    public BondInfo findBondInfoById(Long bondUniCode){
        return jdbcTemplate.queryForObject(
                String.format("select * from innodealing.bond_info T where T.bond_code = %1$s", bondUniCode), 
                new BeanPropertyRowMapper<BondInfo>(BondInfo.class));
    }
    
}

