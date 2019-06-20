/**
 * bondMongoTemplate.java
 * sentiment.config
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2017年9月1日 		liuqi
 *
 * Copyright (c) 2017, DealingMatrix All Rights Reserved.
*/

package com.innodealing.config.mongodb;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

/**
 * ClassName:bondMongoTemplate Function: TODO ADD FUNCTION Reason: TODO ADD
 * REASON
 *
 * @author liuqi
 * @version
 * @since Ver 1.1
 * @Date 2017年9月1日 下午2:31:02
 *
 * @see
 */
@Configuration
@EnableMongoRepositories(basePackages = "sentiment.mongodb.domain.bond", mongoTemplateRef = "bondMongo")
public class BondMongoTemplate {

    @Autowired
    @Qualifier("bondMongoProperties")
    private MongoProperties mongoProperties;

    @Primary
    @Bean(name = "bondMongo")
    public MongoTemplate bondTemplate() throws Exception {
        return new MongoTemplate(bondFactory(this.mongoProperties));
    }

    @Bean
    @Primary
    public MongoDbFactory bondFactory(MongoProperties mongoProperties) throws Exception {
        
        MongoClientURI connectionString = new MongoClientURI(mongoProperties.getUri());
        MongoClient client = new MongoClient(connectionString);
        return new SimpleMongoDbFactory(client, mongoProperties.getDatabase());

    }

}
