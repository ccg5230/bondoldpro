package com.innodealing.engine.mongo.bond;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import com.innodealing.model.mongo.dm.BondImpliedRatingCpInfo;

@Component
public interface BondImpliedRatingCpIntegrationRepository extends MongoRepository<BondImpliedRatingCpInfo, String> {
	
	
}
