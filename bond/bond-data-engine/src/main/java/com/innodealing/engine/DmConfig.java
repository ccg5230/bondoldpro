package com.innodealing.engine;

import java.util.HashMap;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
/**
 * DM数据源、实体管理器 、事务配置(注：多数据源的basePackages必须独立，不能有上下级的包，否则下级包的数据源不能生效)
 * @author zhaozhenglai
 * @since 2016年8月17日 下午5:33:24 
 * Copyright © 2016 DealingMatrix.cn. All Rights Reserved.
 */
@Configuration
@DependsOn("transactionManager")
@EnableJpaRepositories(basePackages = "com.innodealing.dao.jpa.dm.base", entityManagerFactoryRef = "dmEntityManager", transactionManagerRef = "transactionManager")
@EnableConfigurationProperties(DmDatasourceProperties.class)
public class DmConfig {

    @Autowired
    private JpaVendorAdapter jpaVendorAdapter;

    @Autowired
    private DmDatasourceProperties dmDatasourceProperties;

    @Primary
    @Bean(name = "dmDataSource", initMethod = "init", destroyMethod = "close")
    public DataSource dmDataSource() {
        Properties properties = new Properties();
        properties.put("URL", dmDatasourceProperties.getUrl());
        properties.put("user", dmDatasourceProperties.getUser());
        properties.put("password", dmDatasourceProperties.getPassword());
        properties.put("pinGlobalTxToPhysicalConnection", "true");
        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setXaProperties(properties);
        xaDataSource.setXaDataSourceClassName(dmDatasourceProperties.getXaDataSourceClassName());
        xaDataSource.setUniqueResourceName("dm");
        xaDataSource.setMaxPoolSize(dmDatasourceProperties.getMaxPoolSize());
        xaDataSource.setTestQuery("select 1");
        return xaDataSource;
    }

    @Primary
    @Bean(name = "dmEntityManager")
    @DependsOn("transactionManager")
    public LocalContainerEntityManagerFactoryBean dmEntityManager() throws Throwable {
        HashMap<String, Object> properties = new HashMap<String, Object>();
        properties.put("hibernate.transaction.jta.platform", AtomikosJtaPlatform.class.getName());
        //声明jta事务
        properties.put("javax.persistence.transactionType", "JTA");
        properties.put("hibernate.hbm2ddl.auto", "none");
        LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
        DataSource ds = dmDataSource();
        entityManager.setJtaDataSource(ds);
        //jpa适配器
        entityManager.setJpaVendorAdapter(jpaVendorAdapter);
        //实体包扫描
        entityManager.setPackagesToScan("com.innodealing.model.dm");
        //给定一个PersistenceUnit
        entityManager.setPersistenceUnitName("dmPersistenceUnit");
        //设置jpa参数
        entityManager.setJpaPropertyMap(properties);
        return entityManager;
    }

}