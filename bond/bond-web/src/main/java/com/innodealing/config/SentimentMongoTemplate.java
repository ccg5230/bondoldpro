package com.innodealing.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.innodealing.engine.mongo.sentiment", mongoTemplateRef = "sentimentMongo")
public class SentimentMongoTemplate {
    @Autowired
    @Qualifier("sentimentMongoProperties")
    private MongoProperties mongoProperties;

    @Bean(name = "sentimentMongo")
    public MongoTemplate sentimentTemplate() throws Exception {
        return new MongoTemplate(sentimentFactory(this.mongoProperties));
    }

    @Bean
    public MongoDbFactory sentimentFactory(MongoProperties mongoProperties) throws Exception {
        MongoClientURI connectionString = new MongoClientURI(mongoProperties.getUri());
        MongoClient client = new MongoClient(connectionString);
        return new SimpleMongoDbFactory(client, mongoProperties.getDatabase());
    }
}
