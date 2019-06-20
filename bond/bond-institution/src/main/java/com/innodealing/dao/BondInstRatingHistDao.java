package com.innodealing.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.innodealing.mapper.BondInstRatingFileMapper;
import com.innodealing.mapper.BondInstRatingHistMapper;
import com.innodealing.model.mysql.BondInstRatingFile;
import com.innodealing.model.mysql.BondInstRatingHist;

@Repository
public class BondInstRatingHistDao {

	private @Autowired BondInstRatingHistMapper bondInstRatingHistMapper;
	
	private @Autowired BondInstRatingFileMapper bondInstRatingFileMapper;

	public BondInstRatingHist save(BondInstRatingHist entity) {
		bondInstRatingHistMapper.save(entity);
		return entity;
	}

	public int saveOrUpdate(BondInstRatingHist entity) {
		return bondInstRatingHistMapper.saveOrUpdate(entity);
	}
	
	public int checkRepeatData(BondInstRatingHist entity) {
		return bondInstRatingHistMapper.checkRepeatData(entity);
	}

	public BondInstRatingHist queryLastInstRatingHist(Map<String, Object> map) {
		return bondInstRatingHistMapper.queryLastInstRatingHist(map);
	}

	public List<BondInstRatingHist> findInstRatingHistList(Map<String, Object> map) {
		return bondInstRatingHistMapper.findInstRatingHistList(map);
	}

	public BondInstRatingHist queryLastInstRatingHistByRatingAdvice(Map<String, Object> map) {
		return bondInstRatingHistMapper.queryLastInstRatingHistByRatingAdvice(map);
	}

	public List<BondInstRatingHist> queryInstRatingHistByRatingAdvice(Map<String, Object> map) {
		return bondInstRatingHistMapper.queryInstRatingHistByRatingAdvice(map);
	}

	public List<BondInstRatingHist> queryLastInstRatingHistList(Map<String, Object> map) {
		return bondInstRatingHistMapper.queryLastInstRatingHistList(map);
	}

	public BondInstRatingHist queryInstRatingHistAnalysisText(Map<String, Object> map){
		return bondInstRatingHistMapper.queryInstRatingHistAnalysisText(map);
	}
	
	public int updateBondInstRatingHist(BondInstRatingHist entity){
		return bondInstRatingHistMapper.updateBondInstRatingHist(entity);
	}
	
	public List<BondInstRatingHist> findInstRatingHistAll(Map<String, Object> map){
		return bondInstRatingHistMapper.findInstRatingHistAll(map);
	}
	
	public Integer deleteInstRatingHistById(Map<String, Object> map){
		return bondInstRatingHistMapper.deleteInstRatingHistById(map);
	}
	
	public BondInstRatingHist findInstRatingHistStatus(Map<String, Object> map){
		return bondInstRatingHistMapper.findInstRatingHistStatus(map);
	}
	
	public List<Map<String,Integer>> findInstRatingHistStatusCount(Map<String,Object> map){
		return bondInstRatingHistMapper.findInstRatingHistStatusCount(map);
	}
	
	public List<BondInstRatingFile> queryBondInstRatingFileList(BondInstRatingFile bondInstRatingFile){
		return bondInstRatingFileMapper.queryBondInstRatingFileList(bondInstRatingFile);
	}
	
	public BondInstRatingHist findInstRatingHistById(Map<String, Object> map){
		return bondInstRatingHistMapper.findInstRatingHistById(map);
	}
	
	public BondInstRatingHist findInstRatingHistByCode(Map<String, Object> map){
		return bondInstRatingHistMapper.findInstRatingHistByCode(map);
	}
	
	public BondInstRatingHist findBondInstRatingHist(Map<String, Object> map){
		return bondInstRatingHistMapper.findBondInstRatingHist(map);
	}
	
	public Boolean updateBondInstRatingHistByFatId(BondInstRatingHist entity){
		return bondInstRatingHistMapper.updateBondInstRatingHistByFatId(entity);
	}
	
	public List<BondInstRatingHist> findBondInstRatingHistByFatId(Map<String, Object> map){
		return bondInstRatingHistMapper.findBondInstRatingHistByFatId(map);
	}
	
	public List<BondInstRatingHist> queryLastTwoInstRatingHistByDate(Map<String, Object> map){
		return bondInstRatingHistMapper.queryLastTwoInstRatingHistByDate(map);
	}
	
	public int updateLastRatingHistInvAdvByDate(BondInstRatingHist entity){
		return bondInstRatingHistMapper.updateLastRatingHistInvAdvByDate(entity);
	}
	
	public int updateLastRatingHistRatingByDate(Map<String, Object> map){
		return bondInstRatingHistMapper.updateLastRatingHistRatingByDate(map);
	}
	
	public Integer queryRatingSortByHistdate(Map<String, Object> map){
		return bondInstRatingHistMapper.queryRatingSortByHistdate(map);
	}
	
	public BondInstRatingHist queryLastInstRatingHistByType(Map<String, Object> map){
		return bondInstRatingHistMapper.queryLastInstRatingHistByType(map);
	}
	
	public BondInstRatingHist queryInstRatingHistAfterRatingDate(Map<String, Object> map){
		return bondInstRatingHistMapper.queryInstRatingHistAfterRatingDate(map);
	}
}
