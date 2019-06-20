/**
 * 
 */
package com.innodealing.engine.jdbc.bond;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.innodealing.model.dm.bond.BondSpotRate;

/**
 * @author yig
 * @date 2017年5月16日
 * @clasename BondSpotRateDao.java
 * @decription TODO
 */
@Component
public class BondSpotRateDao {

    public final static Logger LOG = LoggerFactory.getLogger(BondSpotRateDao.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    //@Cacheable(value = "spotRateCache", key = "#curveCode")
    public BondSpotRate findLastSpotRateByCurveCode(String curveCode) {
        try {
        	String sql = String.format("select * from dmdb.t_bond_spot_rate T where T.curve_code = %1$s "
                    + "order by T.curve_time desc limit 1", curveCode);
            return jdbcTemplate.queryForObject(sql,
                    new BeanPropertyRowMapper<BondSpotRate>(BondSpotRate.class));
        } catch (Exception ex) {
            LOG.error("getLastSpotRateByCurveCode error:" + ex.getMessage(), ex);
        }
        return null;
    }

}
