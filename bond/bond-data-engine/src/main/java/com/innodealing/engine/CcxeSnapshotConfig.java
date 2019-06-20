package com.innodealing.engine;

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
@EnableJpaRepositories(basePackages = "com.innodealing.dao.jpa.dm.bond.ccxesnapshot", entityManagerFactoryRef = "ccxeSnapshotEntityManager", transactionManagerRef = "transactionManager")
@EnableConfigurationProperties(CcxeSnapshotDatasourceProperties.class)
public class CcxeSnapshotConfig {

    @Autowired
    private JpaVendorAdapter jpaVendorAdapter;

    @Autowired
    private CcxeSnapshotDatasourceProperties ccxeSnapshotDatasourceProperties;

    @Bean(name = "ccxeSnapshotDataSource", initMethod = "init", destroyMethod = "close")
    public DataSource ccxeSnapshotDataSource() {
        Properties properties = new Properties();
        properties.put("URL", ccxeSnapshotDatasourceProperties.getUrl());
        properties.put("user", ccxeSnapshotDatasourceProperties.getUser());
        properties.put("password", ccxeSnapshotDatasourceProperties.getPassword());
        properties.put("pinGlobalTxToPhysicalConnection", "true");
        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setUniqueResourceName("ccxe_snapshot");
        xaDataSource.setXaProperties(properties);
        xaDataSource.setXaDataSourceClassName(ccxeSnapshotDatasourceProperties.getXaDataSourceClassName());
        xaDataSource.setMaxPoolSize(ccxeSnapshotDatasourceProperties.getMaxPoolSize());
        xaDataSource.setTestQuery("select 1");
        return xaDataSource;
    }

    @Bean(name = "ccxeSnapshotEntityManager")
    public LocalContainerEntityManagerFactoryBean ccxeSnapshotEntityManager() throws Throwable {

        HashMap<String, Object> properties = new HashMap<String, Object>();
        properties.put("hibernate.transaction.jta.platform", AtomikosJtaPlatform.class.getName());
        properties.put("javax.persistence.transactionType", "JTA");
        properties.put("hibernate.hbm2ddl.auto", "none");
        LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
        entityManager.setJtaDataSource(ccxeSnapshotDataSource());
        entityManager.setJpaVendorAdapter(jpaVendorAdapter);
        entityManager.setPackagesToScan("com.innodealing.model.dm.bond.ccxe");
        entityManager.setPersistenceUnitName("ccxeSnapshotPersistenceUnit");
        entityManager.setJpaPropertyMap(properties);
        return entityManager;
    }

}
