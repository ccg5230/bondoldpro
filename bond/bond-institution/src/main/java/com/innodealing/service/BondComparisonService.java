package com.innodealing.service;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.innodealing.model.mongo.dm.BondComparisonDoc;

/**
 * @author mf
 *
 */
@Service
public class BondComparisonService {

	private static final Logger LOGGER = LoggerFactory.getLogger(BondComparisonService.class);

	@Resource(name = "bondMongo")
	protected MongoTemplate bondMongoTemplate;

	public List<BondComparisonDoc> findComparisonByUserId(Integer userId) {
		Query query = new Query().addCriteria(Criteria.where("userId").is(userId));
		return bondMongoTemplate.find(query, BondComparisonDoc.class);
	}
}
