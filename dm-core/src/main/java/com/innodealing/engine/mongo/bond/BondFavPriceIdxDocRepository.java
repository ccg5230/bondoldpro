package com.innodealing.engine.mongo.bond;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import com.innodealing.model.mongo.dm.BondFavPriceIdxDoc;

/** 
* @author feng.ma
* @date 2017年5月10日 下午3:10:19 
* @describe 
*/
@Component
public interface BondFavPriceIdxDocRepository extends MongoRepository<BondFavPriceIdxDoc, String> {

}
