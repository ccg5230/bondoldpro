package com.innodealing.engine.mongo.bond;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import com.innodealing.model.mongo.dm.BondFavSentimentIdxDoc;

/** 
* @author feng.ma
* @date 2017年6月14日 下午2:18:50 
* @describe 
*/
@Component
public interface BondFavSentimentIdxDocRepository extends MongoRepository<BondFavSentimentIdxDoc, String> {

}
