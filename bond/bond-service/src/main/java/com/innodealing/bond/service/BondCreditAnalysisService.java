package com.innodealing.bond.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.innodealing.bond.param.PageModel;
import com.innodealing.model.mongo.dm.bond.creditanalysis.CreditAnalysisCiccDoc;
import com.innodealing.model.mongo.dm.bond.creditanalysis.CreditAnalysisGuoJunDoc;
import com.innodealing.model.mongo.dm.bond.creditanalysis.CreditAnalysisIndustrialDoc;
import com.innodealing.model.mongo.dm.bond.creditanalysis.CreditAnalysisRatingDogDoc;

@Service
public class BondCreditAnalysisService {

	private static final Logger LOG = LoggerFactory.getLogger(BondCreditAnalysisService.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private MongoTemplate mongoTemplate;

	public PageModel<CreditAnalysisRatingDogDoc> findRatingDog(Long comUniCode, Integer pagesize, Integer limit) {
		
		PageModel page = new PageModel();
		Query query = new Query();
		query.addCriteria(Criteria.where("comUniCode").is(comUniCode));
		Long count = mongoTemplate.count(query, CreditAnalysisRatingDogDoc.class);

		int SkipCount = (pagesize - 1) * limit;
		query.skip(SkipCount).limit(limit);
		query.with(new Sort(Sort.Direction.DESC, "no"));

		List<CreditAnalysisRatingDogDoc> list2 = mongoTemplate.find(query, CreditAnalysisRatingDogDoc.class);

		page.setRowCount(count);
		page.setOffset(limit);
		page.setPageSize(pagesize);
		page.setDatas(list2);

		return page;
	}

	public List<CreditAnalysisRatingDogDoc> findByComUniCode(Long comUniCode) {
		Query query = new Query();
		query.addCriteria(Criteria.where("comUniCode").is(comUniCode));
		query.with(new Sort(Sort.Direction.ASC,"rateTime"));

		List<CreditAnalysisRatingDogDoc> list = mongoTemplate.find(query, CreditAnalysisRatingDogDoc.class);
		if (list == null || list.size()==0) {
			list = new ArrayList<>();
		}

		return list;
	}

	public PageModel<CreditAnalysisIndustrialDoc> findIndustrial(Long comUniCode, Integer pagesize, Integer limit) {

		PageModel page = new PageModel();
		Query query = new Query();
		query.addCriteria(Criteria.where("comUniCode").is(comUniCode));
		Long count = mongoTemplate.count(query, CreditAnalysisIndustrialDoc.class);

		int SkipCount = (pagesize - 1) * limit;
		query.skip(SkipCount).limit(limit);
		query.with(new Sort(Sort.Direction.DESC, "no"));

		List<CreditAnalysisIndustrialDoc> list2 = mongoTemplate.find(query, CreditAnalysisIndustrialDoc.class);

		page.setRowCount(count);
		page.setOffset(limit);
		page.setPageSize(pagesize);
		page.setDatas(list2);

		return page;
	}
	
	public List<CreditAnalysisIndustrialDoc> findIndustrialBycomUniCode(Long comUniCode) {
		Query query = new Query();
		query.addCriteria(Criteria.where("comUniCode").is(comUniCode));
		query.with(new Sort(Sort.Direction.ASC,"rateTime"));
		
		List<CreditAnalysisIndustrialDoc> list = mongoTemplate.find(query, CreditAnalysisIndustrialDoc.class);
		if (list == null || list.size()==0) {
			list = new ArrayList<>();
		}

		return list;
	}
	
	public PageModel<CreditAnalysisCiccDoc> findCicc(Long comUniCode, Integer pagesize, Integer limit) {

		PageModel page = new PageModel();
		Query query = new Query();
		query.addCriteria(Criteria.where("comUniCode").is(comUniCode));
		Long count = mongoTemplate.count(query, CreditAnalysisCiccDoc.class);

		int SkipCount = (pagesize - 1) * limit;
		query.skip(SkipCount).limit(limit);
		query.with(new Sort(Sort.Direction.DESC, "no"));

		List<CreditAnalysisCiccDoc> list2 = mongoTemplate.find(query, CreditAnalysisCiccDoc.class);

		page.setRowCount(count);
		page.setOffset(limit);
		page.setPageSize(pagesize);
		page.setDatas(list2);

		return page;
	}
	
	public List<CreditAnalysisCiccDoc> findCiccByComUniCode(Long comUniCode) {
		Query query = new Query();
		query.addCriteria(Criteria.where("comUniCode").is(comUniCode));
		query.with(new Sort(Sort.Direction.ASC, "rateTime"));
		
		List<CreditAnalysisCiccDoc>  list = mongoTemplate.find(query, CreditAnalysisCiccDoc.class);
		if (list == null || list.size()==0) {
			list = new ArrayList<>();
		}

		return list;
	}
	
	public PageModel<CreditAnalysisGuoJunDoc> findGuoJun(Long comUniCode, Integer pagesize, Integer limit) {

		PageModel page = new PageModel();
		Query query = new Query();
		query.addCriteria(Criteria.where("comUniCode").is(comUniCode));
		Long count = mongoTemplate.count(query, CreditAnalysisGuoJunDoc.class);

		int SkipCount = (pagesize - 1) * limit;
		query.skip(SkipCount).limit(limit);
		query.with(new Sort(Sort.Direction.DESC, "no"));

		List<CreditAnalysisGuoJunDoc> list2 = mongoTemplate.find(query, CreditAnalysisGuoJunDoc.class);

		page.setRowCount(count);
		page.setOffset(limit);
		page.setPageSize(pagesize);
		page.setDatas(list2);

		return page;
	}
	
	public List<CreditAnalysisGuoJunDoc> findGuoJunByComUniCode(Long comUniCode) {
		Query query = new Query();
		query.addCriteria(Criteria.where("comUniCode").is(comUniCode));
		query.with(new Sort(Sort.Direction.ASC, "rateTime"));

		List<CreditAnalysisGuoJunDoc> list = mongoTemplate.find(query, CreditAnalysisGuoJunDoc.class);
		if (list == null || list.size()==0) {
			list = new ArrayList<>();
		}

		return list;
	}


}
