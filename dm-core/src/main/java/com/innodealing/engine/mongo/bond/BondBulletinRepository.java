package com.innodealing.engine.mongo.bond;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import com.innodealing.model.mongo.dm.BondBulletinDoc;

/**
 * @author xiaochao
 * @time 2017年7月18日
 * @description:
 */
@Component
public interface BondBulletinRepository extends MongoRepository<BondBulletinDoc, Long> {

}
