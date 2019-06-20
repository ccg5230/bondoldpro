package com.innodealing.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
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

@Configuration
@EnableMongoRepositories(basePackages = "com.innodealing.engine.mongo.bond", mongoTemplateRef = "bondMongo")
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
