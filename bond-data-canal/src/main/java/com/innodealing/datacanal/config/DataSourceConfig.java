package com.innodealing.datacanal.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 数据源配置
 * @author 在赵正来
 *
 */
@Configuration
public class DataSourceConfig {

	@Bean(name = "dmdbDataSource")
	@Primary
    @ConfigurationProperties("spring.datasource.primary")
    public DataSource dmdbDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "dataCenterDataSource")
    @ConfigurationProperties("spring.datasource.secondary")
    public DataSource dataCenterDataSource() {
        return DataSourceBuilder.create().build();
    }
    
    @Bean(name = "dmdbJdbcTemplate")
    public JdbcTemplate dmdbJdbcTemplate(@Qualifier("dmdbDataSource")DataSource dataSource) {
    	JdbcTemplate jdbcTemplate =  new JdbcTemplate(dmdbDataSource());
    	jdbcTemplate.setQueryTimeout(20000);
        return jdbcTemplate;
    }

    @Bean(name = "dataCenterJdbcTemplate")
    public JdbcTemplate dataCenterJdbcTemplate(@Qualifier("dataCenterDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataCenterDataSource());
    }

    

}
