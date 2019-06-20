package com.innodealing.engine.mongo.bond;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import com.innodealing.model.mongo.dm.BondDetailDoc;

@Component
public interface BondDetailRepository extends MongoRepository<BondDetailDoc, Long> {
	
	public BondDetailDoc findByCode(String code);
	
	public BondDetailDoc findByBondUniCode(Long bondUniCode);
}
