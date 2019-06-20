package com.innodealing.engine.mongo.bond;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import com.innodealing.model.mongo.dm.BondPdHistDoc;

@Component
public interface BondPdHistRepository extends MongoRepository<BondPdHistDoc, Long> {
	
	public List<BondPdHistDoc> findByComUniCodeIn(List<Long> comUniCode);
}
