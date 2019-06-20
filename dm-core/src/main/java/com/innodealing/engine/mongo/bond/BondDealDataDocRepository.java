package com.innodealing.engine.mongo.bond;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import com.innodealing.model.mongo.dm.BondDealDataDoc;

/**
 * @author stephen.ma
 * @date 2016年9月29日
 * @clasename BondDealDataRepository.java
 * @decription TODO
 */
@Component
public interface BondDealDataDocRepository extends MongoRepository<BondDealDataDoc, Long> {

}
