package com.innodealing.engine.mongo.bond;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import com.innodealing.model.mongo.dm.bond.detail.analyse.BondInduAnalyseDoc;

@Component
public interface BondInduAnalyseRepository extends MongoRepository<BondInduAnalyseDoc, Long> {
    
    public BondInduAnalyseDoc findByCompId(Long compId);
}
