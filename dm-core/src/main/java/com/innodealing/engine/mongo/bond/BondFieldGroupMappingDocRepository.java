package com.innodealing.engine.mongo.bond;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import com.innodealing.model.mongo.dm.BondFieldGroupMappingDoc;

/**
 * @author xiaochao
 * @date 2017年7月12日
 * @clasename BondFieldGroupMappingDocRepository.java
 * @decription TODO
 */
@Component
public interface BondFieldGroupMappingDocRepository extends MongoRepository<BondFieldGroupMappingDoc, Long> {

	BondFieldGroupMappingDoc findByColumnName(String columnName);

}
