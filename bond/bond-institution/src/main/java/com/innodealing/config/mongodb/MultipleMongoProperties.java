/**
 * MultipleMongoProperties.java
 * sentiment.config
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2017年8月31日 		liuqi
 *
 * Copyright (c) 2017, DealingMatrix All Rights Reserved.
*/

package com.innodealing.config.mongodb;

import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * ClassName:MultipleMongoProperties Function: TODO ADD FUNCTION Reason: TODO
 * ADD REASON
 *
 * @author liuqi
 * @version
 * @since Ver 1.1
 * @Date 2017年8月31日 上午11:13:37
 *
 * @see
 */
@Configuration
public class MultipleMongoProperties {

    @Bean(name = "bondMongoProperties")
    @Primary
    @ConfigurationProperties(prefix = "spring.data.mongodb.bond")
    public MongoProperties bondMongoProperties() {
        System.out.println("-------------------- bondMongoProperties init ---------------------");
        return new MongoProperties();
    }

}
