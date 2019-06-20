package com.innodealing.mapper;

import java.util.List;
import java.util.Map;

import com.innodealing.model.mysql.BondInstRatingHist;

public interface BondInstRatingHistMapper {

	public Long save(BondInstRatingHist entity);

	public int saveOrUpdate(BondInstRatingHist entity);

	public BondInstRatingHist queryLastInstRatingHist(Map<String, Object> map);

	public List<BondInstRatingHist> findInstRatingHistList(Map<String, Object> map);

	public BondInstRatingHist queryLastInstRatingHistByRatingAdvice(Map<String, Object> map);
	
	public List<BondInstRatingHist> queryInstRatingHistByRatingAdvice(Map<String, Object> map);
	
	public List<BondInstRatingHist> queryLastInstRatingHistList(Map<String, Object> map);
	
	public BondInstRatingHist queryInstRatingHistAnalysisText(Map<String, Object> map);
	
	public int updateBondInstRatingHist(BondInstRatingHist entity);
	
	public int checkRepeatData(BondInstRatingHist entity);
	
	public List<BondInstRatingHist> findInstRatingHistAll(Map<String, Object> map);
	
	public Integer deleteInstRatingHistById(Map<String, Object> map);
	
	public BondInstRatingHist findInstRatingHistStatus(Map<String, Object> map);
	
	public List<Map<String,Integer>> findInstRatingHistStatusCount(Map<String,Object> map);
	
	public BondInstRatingHist findInstRatingHistById(Map<String, Object> map);
	
	public BondInstRatingHist findInstRatingHistByCode(Map<String, Object> map);
	
	public BondInstRatingHist findBondInstRatingHist(Map<String, Object> map);
	
	public Boolean updateBondInstRatingHistByFatId(BondInstRatingHist entity);
	
	public List<BondInstRatingHist> findBondInstRatingHistByFatId(Map<String, Object> map);
	
	public List<BondInstRatingHist> queryLastTwoInstRatingHistByDate(Map<String, Object> map);
	
	public int updateLastRatingHistInvAdvByDate(BondInstRatingHist entity);
	
	public int updateLastRatingHistRatingByDate(Map<String, Object> map);
	
	public Integer queryRatingSortByHistdate(Map<String, Object> map);
	
	public BondInstRatingHist queryLastInstRatingHistByType(Map<String, Object> map);

	public BondInstRatingHist queryInstRatingHistAfterRatingDate(Map<String, Object> map);
}
