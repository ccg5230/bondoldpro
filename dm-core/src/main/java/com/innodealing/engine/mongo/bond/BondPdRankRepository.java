package com.innodealing.engine.mongo.bond;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import com.innodealing.model.mongo.dm.BondBasicInfoDoc;
import com.innodealing.model.mongo.dm.BondDetailDoc;
import com.innodealing.model.mongo.dm.BondPdHistDoc;
import com.innodealing.model.mongo.dm.BondPdRankDoc;

@Component
public interface BondPdRankRepository extends MongoRepository<BondPdRankDoc, Long> {
}
