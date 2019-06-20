package com.innodealing.config.datasource;

import java.util.HashMap;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import com.atomikos.jdbc.AtomikosDataSourceBean;
/**
 * DM数据源、实体管理器 、事务配置
 * @author zhaozhenglai
 * @since 2016年8月17日 下午5:34:38 
 * Copyright © 2016 DealingMatrix.cn. All Rights Reserved.
 */
@Configuration
@DependsOn("transactionManager")
@EnableJpaRepositories(basePackages = "com.innodealing.engine.jpa.im", entityManagerFactoryRef = "imEntityManager", transactionManagerRef = "transactionManager")
@EnableConfigurationProperties(ImDatasourceProperties.class)
public class ImConfig {

    @Autowired
    private JpaVendorAdapter jpaVendorAdapter;

    @Autowired
    private ImDatasourceProperties imDatasourceProperties;

    @Bean(name = "imDataSource", initMethod = "init", destroyMethod = "close")
    public DataSource imDataSource() {
        Properties properties = new Properties();
        properties.put("URL", imDatasourceProperties.getUrl());
        properties.put("user", imDatasourceProperties.getUser());
        properties.put("password", imDatasourceProperties.getPassword());
        properties.put("pinGlobalTxToPhysicalConnection", "true");
        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setUniqueResourceName("im");
        xaDataSource.setXaProperties(properties);
        xaDataSource.setXaDataSourceClassName(imDatasourceProperties.getXaDataSourceClassName());
        xaDataSource.setMaxPoolSize(imDatasourceProperties.getMaxPoolSize());
        xaDataSource.setTestQuery("select 1");
        return xaDataSource;
    }

    @Bean(name = "imEntityManager")
    public LocalContainerEntityManagerFactoryBean imEntityManager() throws Throwable {

        HashMap<String, Object> properties = new HashMap<String, Object>();
        properties.put("hibernate.transaction.jta.platform", AtomikosJtaPlatform.class.getName());
        properties.put("javax.persistence.transactionType", "JTA");
        properties.put("hibernate.hbm2ddl.auto", "none");
        LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
        entityManager.setJtaDataSource(imDataSource());
        entityManager.setJpaVendorAdapter(jpaVendorAdapter);
        entityManager.setPackagesToScan("com.innodealing.model.im");
        entityManager.setPersistenceUnitName("imPersistenceUnit");
        entityManager.setJpaPropertyMap(properties);
        return entityManager;
    }

}
