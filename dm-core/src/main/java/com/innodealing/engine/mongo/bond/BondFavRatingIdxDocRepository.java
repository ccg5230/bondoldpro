package com.innodealing.engine.mongo.bond;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import com.innodealing.model.mongo.dm.BondFavRatingIdxDoc;

/** 
* @author feng.ma
* @date 2017年5月17日 下午3:51:51 
* @describe 
*/
@Component
public interface BondFavRatingIdxDocRepository extends MongoRepository<BondFavRatingIdxDoc, String> {

}
