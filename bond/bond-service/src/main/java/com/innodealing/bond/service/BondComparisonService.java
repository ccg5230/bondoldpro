package com.innodealing.bond.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.innodealing.bond.param.ComparisonSinglebondParam;
import com.innodealing.bond.vo.quote.BondSingleComparisonVo;
import com.innodealing.exception.BusinessException;
import com.innodealing.exception.WarnException;
import com.innodealing.model.mongo.dm.BondComparisonDoc;
import com.innodealing.model.mongo.dm.BondSingleComparisonDoc;
import com.innodealing.model.mongo.dm.WorkingdateSixdaysDoc;

import javax.annotation.Resource;

/**
 * @author Administrator
 *
 */
@Service
public class BondComparisonService {
	
	private static final Logger LOG = LoggerFactory.getLogger(BondComparisonService.class);

	@Resource(name="bondMongo")
	protected MongoTemplate bondMongoTemplate;

	private Long addComparison(Long userId, Long bondUniCode) 
	{
	    if(bondUniCode == null){
	        throw new BusinessException("请先选择债券！");
	    }
	    
	    //checkInComparsion(userId, bondUniCode);
	    
	    //checkComparsionCount(userId);
		
		BondComparisonDoc comparison = new BondComparisonDoc();
		comparison.setBondId(bondUniCode);
		comparison.setUserId(userId);
		bondMongoTemplate.save(comparison);
		return bondUniCode;
	}

	private void checkComparsionCount(Long userId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("userId").is(userId));
		if (bondMongoTemplate.count(query, BondComparisonDoc.class) >= 12) {
			throw new WarnException("单券对比库已满（最多12条债券）\n 建议您前往对比库删除债券后再添加对比 "); 
		}
	}

	private boolean checkInComparsion(Long userId, Long bondUniCode) {
		Query querydb = new Query(Criteria.where("userId").is(userId).and("bondId").is(bondUniCode));
	    BondComparisonDoc bc = bondMongoTemplate.findOne(querydb, BondComparisonDoc.class);
	    if(bc != null){
	    	return true;
	    }else{
	    	return false;
	    }
	}
	
	//批量添加
	public List<Long> addComparisons(Long userId, List<Long> bondUniCodes) {
	    if(bondUniCodes == null || bondUniCodes.size() == 0){
	        throw new BusinessException("请先选择债券！");
	    }
	    
	    int selected = bondUniCodes.size();
	    Query query = new Query();
	    query.addCriteria(Criteria.where("userId").is(userId));
	    long incount = bondMongoTemplate.count(query, BondComparisonDoc.class);
	    
	    if ((selected + incount) > 12) {
	    	throw new WarnException("单券对比库已满（最多12条债券）\n 建议您前往对比库删除债券后再添加对比 "); 
	    }
	    
	    bondUniCodes = bondUniCodes.stream().filter(bondUniCode -> !checkInComparsion(userId, bondUniCode)).collect(Collectors.toList());
	    
		bondUniCodes.stream().forEach(bondId -> {
			this.addComparison(userId, bondId);
		});
		
		return bondUniCodes;
	}
	
	
	public Long removeComparison(Long userId, Long bondUniCode) 
	{
		Query query = new Query().addCriteria(
				new Criteria().andOperator(
						Criteria.where("userId").is(userId), 
						Criteria.where("bondId").is(bondUniCode)
						)
				);
		bondMongoTemplate.remove(query, BondComparisonDoc.class);
		return bondUniCode;
	}
	
	public List<BondComparisonDoc> findComparisonByUserId(Long userId)
	{
		Query query = new Query().addCriteria(Criteria.where("userId").is(userId));
		return bondMongoTemplate.find(query, BondComparisonDoc.class);
	}

	/**
	 * @param userId
	 * @param params
	 */
	public List<BondSingleComparisonVo> findComparisonSinglebond(Long userId, ComparisonSinglebondParam params) {
		List<String> dateList = new ArrayList<>();
		List<WorkingdateSixdaysDoc> wsList = bondMongoTemplate.findAll(WorkingdateSixdaysDoc.class);
		
		wsList.forEach(wsdate -> {
			dateList.add(wsdate.getDate());
		});
		
		List<BondSingleComparisonVo> list = new ArrayList<>();
		params.getBondIds().forEach(bondUniCode -> {
			BondSingleComparisonVo bscVo = new BondSingleComparisonVo();
			
			Query query = new Query();
			query.addCriteria(Criteria.where("bondUniCode").is(bondUniCode).and("sendTime").in(dateList));
			query.with(new Sort(Sort.Direction.ASC, "sendTime"));
			List<BondSingleComparisonDoc> bscList = bondMongoTemplate.find(query, BondSingleComparisonDoc.class);
			if (null != bscList && bscList.size() > 0) {
				BondSingleComparisonDoc bsc = bscList.get(0);
				bscVo.setBondCode(bsc.getBondCode());
				bscVo.setBondShortName(bsc.getBondShortName());
				bscVo.setValues(bscList);
				
				list.add(bscVo);
			}
		});
		
		return list;
	}

	/**
	 * @return
	 */
	public List<String> findComparisonDates() {
		List<String> dateList = new ArrayList<>();
		Query query = new Query();
		query.with(new Sort(Sort.Direction.ASC, "date"));
		List<WorkingdateSixdaysDoc> wsList = bondMongoTemplate.find(query, WorkingdateSixdaysDoc.class);
		wsList.forEach(wsdate -> {
			dateList.add(wsdate.getDate());
		});
		return dateList;
	}
	
}
