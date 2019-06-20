package com.innodealing.engine.mongo.bond;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import com.innodealing.model.mongo.dm.BondDmFilterDoc;

@Component
public interface BondDmFilterRepository extends MongoRepository<BondDmFilterDoc, String>{

}
