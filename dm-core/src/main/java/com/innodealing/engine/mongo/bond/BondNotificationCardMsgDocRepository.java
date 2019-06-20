package com.innodealing.engine.mongo.bond;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import com.innodealing.model.mongo.dm.BondNotificationCardMsgDoc;

/** 
* @author feng.ma
* @date 2017年8月13日 下午2:35:17 
* @describe 
*/
@Component
public interface BondNotificationCardMsgDocRepository extends MongoRepository<BondNotificationCardMsgDoc, Long> {

}
