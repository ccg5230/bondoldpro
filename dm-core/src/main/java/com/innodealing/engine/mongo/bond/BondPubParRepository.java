package com.innodealing.engine.mongo.bond;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import com.innodealing.model.mongo.dm.BondPubPar;

@Component
public interface BondPubParRepository extends MongoRepository<BondPubPar, Integer> {

}
