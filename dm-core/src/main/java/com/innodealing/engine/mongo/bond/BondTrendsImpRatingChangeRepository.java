package com.innodealing.engine.mongo.bond;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import com.innodealing.model.mongo.dm.BondTrendsImpRatingChangeDoc;

/**
 * @author xiaochao
 * @time 2017年4月12日
 * @description: 
 */
@Component
public interface BondTrendsImpRatingChangeRepository extends MongoRepository<BondTrendsImpRatingChangeDoc, String> {

}
