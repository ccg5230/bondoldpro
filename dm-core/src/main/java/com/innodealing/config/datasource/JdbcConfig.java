package com.innodealing.config.datasource;

import java.util.Properties;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
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
@EnableConfigurationProperties({DmDatasourceProperties.class, AsbrsPerResultDatasourceProperties.class})
@EnableTransactionManagement
public class JdbcConfig {

    private static final Logger LOG = LoggerFactory.getLogger(JdbcConfig.class);
    
    @Autowired
    private DmDatasourceProperties dmDatasourceProperties;
    
    @Autowired
    private AsbrsPerResultDatasourceProperties asbrsPerResultDatasourceProperties;

    @ConfigurationProperties(prefix = "one.datasource")
    @Primary
    public DataSource oneDataSource() {
//       DataSourceBuilder build = DataSourceBuilder.create();
//        build.url(dmDatasourceProperties.getUrl());
//        build.username(dmDatasourceProperties.getUser());
//        build.driverClassName("com.mysql.jdbc.Driver");
//        build.password(dmDatasourceProperties.getPassword());
//        return build.build();
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
        xaDataSource.setBorrowConnectionTimeout(1);
        xaDataSource.setTestQuery("select 1");
        xaDataSource.setMaxPoolSize(dmDatasourceProperties.getMaxPoolSize());
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
        xaDataSource.setMaxPoolSize(asbrsPerResultDatasourceProperties.getMaxPoolSize());
        xaDataSource.setBorrowConnectionTimeout(1);
        xaDataSource.setTestQuery("select 1");
        return xaDataSource;
    }

    @Bean(name="jdbcTemplate")
    public JdbcTemplate JdbcTemplate() {
        return new JdbcTemplate(oneDataSource());
    }
    
    @Bean(name="asbrsPerResultJdbcTemplate")
    public JdbcTemplate asbrsPerResultJdbcTemplate() {
        return new JdbcTemplate(asbrsPerResultDataSource());
    }

}
