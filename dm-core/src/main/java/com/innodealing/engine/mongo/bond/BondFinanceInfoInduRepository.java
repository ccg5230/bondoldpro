package com.innodealing.engine.mongo.bond;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import com.innodealing.model.mongo.dm.bond.detail.analyse.BondFinanceInfoInduDoc;

@Component
public interface BondFinanceInfoInduRepository extends MongoRepository<BondFinanceInfoInduDoc, Long> {
    
    public BondFinanceInfoInduDoc findByCompId(Long compId);
}
