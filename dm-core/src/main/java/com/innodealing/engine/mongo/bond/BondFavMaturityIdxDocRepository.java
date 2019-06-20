package com.innodealing.engine.mongo.bond;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import com.innodealing.model.mongo.dm.BondFavMaturityIdxDoc;

/** 
* @author feng.ma
* @date 2017年5月17日 下午3:51:03 
* @describe 
*/
@Component	
public interface BondFavMaturityIdxDocRepository extends MongoRepository<BondFavMaturityIdxDoc, String> {

}
