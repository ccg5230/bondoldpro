package com.innodealing.engine.mongo.bond;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import com.innodealing.model.mongo.dm.FinanceAlertInfoDoc;

@Component
public interface FinanceAlertInfoRepository extends MongoRepository<FinanceAlertInfoDoc, Long> {
	
	public FinanceAlertInfoDoc findByComUniCode(Long comUniCode);
}
