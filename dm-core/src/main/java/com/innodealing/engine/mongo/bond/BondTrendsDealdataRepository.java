package com.innodealing.engine.mongo.bond;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import com.innodealing.model.mongo.dm.BondTrendsDealdataDoc;

/** 
* @author feng.ma
* @date 2017年4月5日 上午11:57:35 
* @describe 
*/
@Component
public interface BondTrendsDealdataRepository extends MongoRepository<BondTrendsDealdataDoc, String> {

}
