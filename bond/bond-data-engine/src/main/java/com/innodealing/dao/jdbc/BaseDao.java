package com.innodealing.dao.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;

public class BaseDao<T> {
    
    public final static Logger logger = LoggerFactory.getLogger(BaseDao.class);
    
    @Autowired
    @Qualifier("jdbcTemplate")
    protected JdbcTemplate jdbcTemplate;
    
    @Autowired
    @Qualifier("ccxeJdbcTemplate")
    protected JdbcTemplate ccxeJdbcTemplate;

    @Autowired
    @Qualifier("asbrsJdbcTemplate")
    protected JdbcTemplate asbrsJdbcTemplate;
    
    @Autowired
    @Qualifier("gateJdbcTemplate")
    protected JdbcTemplate gateJdbcTemplate;
    
    @Autowired
    @Qualifier("asbrsPerResultJdbcTemplate")
    protected JdbcTemplate asbrsPerResultJdbcTemplate;

}
