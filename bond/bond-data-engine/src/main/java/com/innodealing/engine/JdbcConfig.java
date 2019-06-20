package com.innodealing.engine;

import java.util.Properties;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.innodealing.util.PropertyUtil;

/**
 * jdbc 数据配置
 * @author zhaozhenglai
 * @since 2016年8月17日 下午5:36:21 
 * Copyright © 2016 DealingMatrix.cn. All Rights Reserved.
 */
@Configuration
@EnableConfigurationProperties({
    DmDatasourceProperties.class,
    CcxeDatasourceProperties.class,
    CcxeSnapshotDatasourceProperties.class,
    AsbrsDatasourceProperties.class,
    GateDatasourceProperties.class,
    AsbrsPerResultDatasourceProperties.class
    })
@EnableTransactionManagement
public class JdbcConfig {

    private static final Logger LOG = LoggerFactory.getLogger(JdbcConfig.class);
    
    @Autowired
    private DmDatasourceProperties dmDatasourceProperties;
    
    @Autowired
    private CcxeDatasourceProperties ccxeDatasourceProperties;

    @Autowired
    private CcxeSnapshotDatasourceProperties ccxeSnapshotDatasourceProperties;
    
    @Autowired
    private AsbrsDatasourceProperties asbrsDatasourceProperties;
    
    @Autowired
    private GateDatasourceProperties gateDatasourceProperties;
    
    @Autowired
    private AsbrsPerResultDatasourceProperties asbrsPerResultDatasourceProperties;

    @Primary
    public DataSource oneDataSource() {
        Properties properties = new Properties();
        properties.put("URL", dmDatasourceProperties.getUrl());
        properties.put("user", dmDatasourceProperties.getUser());
        properties.put("password", dmDatasourceProperties.getPassword());
        properties.put("pinGlobalTxToPhysicalConnection", "true");
        LOG.info("oneDataSource:" + PropertyUtil.getPropertyAsString(properties));
        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setXaProperties(properties);
        xaDataSource.setXaDataSourceClassName(dmDatasourceProperties.getXaDataSourceClassName());
        xaDataSource.setUniqueResourceName("dmjdbc");
        xaDataSource.setPoolSize(dmDatasourceProperties.getMaxPoolSize());
        xaDataSource.setBorrowConnectionTimeout(1);
        xaDataSource.setTestQuery("select 1");
        return xaDataSource;
    }
    
    public DataSource ccxeDataSource() {
       Properties properties = new Properties();
       properties.put("URL", ccxeDatasourceProperties.getUrl());
       properties.put("user", ccxeDatasourceProperties.getUser());
       properties.put("password", ccxeDatasourceProperties.getPassword());
       properties.put("pinGlobalTxToPhysicalConnection", "true");
       LOG.info("ccxeDataSource:" + PropertyUtil.getPropertyAsString(properties));
       AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
       xaDataSource.setXaProperties(properties);
       xaDataSource.setXaDataSourceClassName(ccxeDatasourceProperties.getXaDataSourceClassName());
       xaDataSource.setUniqueResourceName("ccxejdbc");
       xaDataSource.setPoolSize(ccxeDatasourceProperties.getMaxPoolSize());
       xaDataSource.setBorrowConnectionTimeout(1);
       xaDataSource.setTestQuery("select 1");
       return xaDataSource;
   }
    
    public DataSource ccxeSnapshotDataSource() {
        Properties properties = new Properties();
        properties.put("URL", ccxeSnapshotDatasourceProperties.getUrl());
        properties.put("user", ccxeSnapshotDatasourceProperties.getUser());
        properties.put("password", ccxeSnapshotDatasourceProperties.getPassword());
        properties.put("pinGlobalTxToPhysicalConnection", "true");
        LOG.info("ccxeSnapshotDataSource:" + PropertyUtil.getPropertyAsString(properties));
        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setXaProperties(properties);
        xaDataSource.setXaDataSourceClassName(ccxeSnapshotDatasourceProperties.getXaDataSourceClassName());
        xaDataSource.setUniqueResourceName("ccxesnapshotjdbc");
        xaDataSource.setPoolSize(ccxeSnapshotDatasourceProperties.getMaxPoolSize());
        xaDataSource.setBorrowConnectionTimeout(1);
        xaDataSource.setTestQuery("select 1");
        return xaDataSource;
    }
    
    public DataSource asbrsDataSource() {
       Properties properties = new Properties();
       properties.put("URL", asbrsDatasourceProperties.getUrl());
       properties.put("user", asbrsDatasourceProperties.getUser());
       properties.put("password", asbrsDatasourceProperties.getPassword());
       properties.put("pinGlobalTxToPhysicalConnection", "true");
       LOG.info("asbrsDataSource:" + PropertyUtil.getPropertyAsString(properties));
       AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
       xaDataSource.setXaProperties(properties);
       xaDataSource.setXaDataSourceClassName(asbrsDatasourceProperties.getXaDataSourceClassName());
       xaDataSource.setUniqueResourceName("asbrsjdbc");
       xaDataSource.setPoolSize(asbrsDatasourceProperties.getMaxPoolSize());
       xaDataSource.setBorrowConnectionTimeout(1);
       xaDataSource.setTestQuery("select 1");
       return xaDataSource;
   }
    
   public DataSource gateDataSource() {
       Properties properties = new Properties();
       properties.put("URL", gateDatasourceProperties.getUrl());
       properties.put("user", gateDatasourceProperties.getUser());
       properties.put("password", gateDatasourceProperties.getPassword());
       properties.put("pinGlobalTxToPhysicalConnection", "true");
       AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
       LOG.info("gateDataSource:" + PropertyUtil.getPropertyAsString(properties));
       xaDataSource.setXaProperties(properties);
       xaDataSource.setXaDataSourceClassName(gateDatasourceProperties.getXaDataSourceClassName());
       xaDataSource.setUniqueResourceName("gatejdbc");
       xaDataSource.setPoolSize(gateDatasourceProperties.getMaxPoolSize());
       xaDataSource.setBorrowConnectionTimeout(1);
       xaDataSource.setTestQuery("select 1");
       return xaDataSource;
   }
   
   public DataSource asbrsPerResultDataSource() {
       Properties properties = new Properties();
       properties.put("URL", asbrsPerResultDatasourceProperties.getUrl());
       properties.put("user", asbrsPerResultDatasourceProperties.getUser());
       properties.put("password", asbrsPerResultDatasourceProperties.getPassword());
       properties.put("pinGlobalTxToPhysicalConnection", "true");
       LOG.info("asbrsPerResultDataSource:" + PropertyUtil.getPropertyAsString(properties));
       AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
       xaDataSource.setXaProperties(properties);
       xaDataSource.setXaDataSourceClassName(asbrsPerResultDatasourceProperties.getXaDataSourceClassName());
       xaDataSource.setUniqueResourceName("asbrsPerResultJdbc");
       xaDataSource.setPoolSize(asbrsPerResultDatasourceProperties.getMaxPoolSize());
       xaDataSource.setBorrowConnectionTimeout(1);
       xaDataSource.setTestQuery("select 1");
       return xaDataSource;
   }
   
    @Bean(name="jdbcTemplate")
    public JdbcTemplate JdbcTemplate() {
        return new JdbcTemplate(oneDataSource());
    }
    
    @Bean(name="ccxeJdbcTemplate")
    public JdbcTemplate ccxeJdbcTemplate() {
        return new JdbcTemplate(ccxeDataSource());
    }
    
    @Bean(name="ccxeSnapshotJdbcTemplate")
    public JdbcTemplate ccxeSnapshotJdbcTemplate() {
        return new JdbcTemplate(ccxeSnapshotDataSource());
    }
 
    @Bean(name="asbrsJdbcTemplate")
    public JdbcTemplate asbrsJdbcTemplate() {
        return new JdbcTemplate(asbrsDataSource());
    }
    
    @Bean(name="gateJdbcTemplate")
    public JdbcTemplate gateJdbcTemplate() {
        return new JdbcTemplate(gateDataSource());
    }
    
    @Bean(name="asbrsPerResultJdbcTemplate")
    public JdbcTemplate asbrsPerResultJdbcTemplate() {
        return new JdbcTemplate(asbrsPerResultDataSource());
    }
}
