package com.innodealing.engine.mongo.bond;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import com.innodealing.model.mongo.dm.BondFavFinaIdxDoc;

/** 
* @author feng.ma
* @date 2017年5月12日 下午2:24:01 
* @describe 
*/
@Component
public interface BondFavFinaIdxDocRepository extends MongoRepository<BondFavFinaIdxDoc, String> {

}
