package com.innodealing.engine.mongo.bond;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import com.innodealing.model.mongo.dm.bond.detail.analyse.BondFinanceInfoQuarterDoc;

@Component
public interface BondFinanceInfoQuarterRepository extends MongoRepository<BondFinanceInfoQuarterDoc, Long> {
    
    public BondFinanceInfoQuarterDoc findByCompId(Long compId);
}
