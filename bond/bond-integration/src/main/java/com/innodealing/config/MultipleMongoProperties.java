package com.innodealing.config;

import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class MultipleMongoProperties {
    @Bean(name = "bondMongoProperties")
    @Primary
    @ConfigurationProperties(prefix = "spring.data.mongodb.bond")
    public MongoProperties bondMongoProperties() {
        System.out.println("-------------------- bondMongoProperties init ---------------------");
        return new MongoProperties();
    }

    @Bean(name = "sentimentMongoProperties")
    @ConfigurationProperties(prefix = "spring.data.mongodb.sentiment")
    public MongoProperties sentimentMongoProperties() {
        System.out.println("-------------------- sentimentMongoProperties init ---------------------");
        return new MongoProperties();
    }
}
