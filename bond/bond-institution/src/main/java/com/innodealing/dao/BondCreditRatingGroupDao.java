package com.innodealing.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.innodealing.consts.InstConstants;
import com.innodealing.mapper.BondCreditRatingGroupMapper;
import com.innodealing.model.mysql.BondCreditRatingGroup;

@Repository
public class BondCreditRatingGroupDao {

	private @Autowired BondCreditRatingGroupMapper creditRatingGroupMapper;

	public int save(BondCreditRatingGroup creditRatingGroup) {
		return creditRatingGroupMapper.save(creditRatingGroup);
	}

	public BondCreditRatingGroup queryById(Long groupId){
		return creditRatingGroupMapper.queryById(groupId);
	}
	
	public BondCreditRatingGroup queryByName(String groupName) {
		return creditRatingGroupMapper.queryByName(groupName);
	}

	public BondCreditRatingGroup queryByName(String groupName, Integer orgId) {
		Map<String, Object> map = new HashMap<>();
		map.put("groupName", groupName);
		map.put("orgId", orgId);
		return creditRatingGroupMapper.queryByNameAndOrgId(map);
	}
	
	public int delete(Long groupId) {
		return creditRatingGroupMapper.delete(groupId);
	}

	public int update(BondCreditRatingGroup creditRatingGroup) {
		return creditRatingGroupMapper.update(creditRatingGroup);
	}
	
	public List<BondCreditRatingGroup> queryByUserId(Integer userId) {
		return creditRatingGroupMapper.queryByUserId(userId);
	}

	public List<BondCreditRatingGroup> queryByOrgId(Integer orgId){
		return creditRatingGroupMapper.queryByOrgId(orgId);
	}
	
	public List<BondCreditRatingGroup> queryExceptId(Long groupId) {
		return creditRatingGroupMapper.queryExceptId(groupId);
	}
	
	public BondCreditRatingGroup queryByIssuerId(Map<String, Object> map){
		return creditRatingGroupMapper.queryByIssuerId(map);
	}
	
	public BondCreditRatingGroup queryDefaultOneByOrgId(Integer orgId){
		Map<String, Object> map = new HashMap<>();
		map.put("groupType", InstConstants.CREDITRATING_GROUPTYPE0);
		map.put("orgId", orgId);
		return creditRatingGroupMapper.queryDefaultOneByOrgId(map);
	}
	
}
