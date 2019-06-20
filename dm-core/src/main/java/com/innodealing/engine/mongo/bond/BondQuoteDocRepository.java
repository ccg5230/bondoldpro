package com.innodealing.engine.mongo.bond;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import com.innodealing.model.mongo.dm.BondQuoteDoc;

/**
 * @author stephen.ma
 * @date 2016年9月8日
 * @clasename BondQuoteDocRepository.java
 * @decription TODO
 */
@Component
public interface BondQuoteDocRepository extends MongoRepository<BondQuoteDoc, Long> {
	
	public BondQuoteDoc findByQuoteId(Long quoteId);

}
