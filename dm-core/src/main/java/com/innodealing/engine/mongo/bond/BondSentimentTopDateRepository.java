package com.innodealing.engine.mongo.bond;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import com.innodealing.model.mongo.dm.BondSentimentTopDateDoc;

@Component
public interface BondSentimentTopDateRepository extends MongoRepository<BondSentimentTopDateDoc, Long> {
	
}
