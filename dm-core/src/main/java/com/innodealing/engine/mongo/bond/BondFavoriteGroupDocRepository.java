package com.innodealing.engine.mongo.bond;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import com.innodealing.model.mongo.dm.BondFavoriteGroupDoc;

@Component
public interface BondFavoriteGroupDocRepository extends MongoRepository<BondFavoriteGroupDoc, Integer> {

	List<BondFavoriteGroupDoc> findAllByGroupIdIn(List<Integer> groupIdList);

}
