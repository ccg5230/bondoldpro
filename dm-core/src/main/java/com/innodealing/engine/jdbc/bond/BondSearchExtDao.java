/**
 * BondSearchExtDao.java
 * com.innodealing.engine.jdbc.bond
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2017年9月21日 		chungaochen
 *
 * Copyright (c) 2017, DealingMatrix All Rights Reserved.
*/

package com.innodealing.engine.jdbc.bond;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * ClassName:BondSearchExtDao
 *
 * @author chungaochen
 * @version
 * @since Ver 1.1
 * @Date 2017年9月21日 下午5:00:43
 *
 * @see
 */
@Component
public class BondSearchExtDao {

    public final static Logger logger = LoggerFactory.getLogger(BondSearchExtDao.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Long> findBondUniCodesByQuerykey(String queryString) {
        return jdbcTemplate.queryForList("SELECT bond_uni_code FROM dmdb.t_bond_search_ext WHERE bond_search_key LIKE '%" + queryString+"%'",Long.class);
    }
    
    public List<Long> findComUniCodesByQuerykey(String queryString) {
        return jdbcTemplate.queryForList("SELECT com_uni_code FROM dmdb.t_bond_search_ext WHERE iss_search_key LIKE '%" + queryString+"%'",Long.class);
    }
}
