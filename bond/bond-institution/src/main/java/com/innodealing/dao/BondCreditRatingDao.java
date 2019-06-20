package com.innodealing.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.innodealing.mapper.BondCreditRatingMapper;
import com.innodealing.model.mysql.BondCreditRating;
import com.innodealing.model.vo.CreditRatingBondVo;
import com.innodealing.model.vo.CreditRatingIssuerVo;
import com.innodealing.model.vo.IssuerGroupInfoVo;

@Repository
public class BondCreditRatingDao {

	private @Autowired BondCreditRatingMapper creditRatingMapper;

	public List<BondCreditRating> queryByUserId(Integer userId) {
		return creditRatingMapper.queryByUserId(userId);
	}

	public List<BondCreditRating> queryByOrgId(Integer orgId) {
		return creditRatingMapper.queryByOrgId(orgId);
	}

	public int queryIssuercountByGroupId(Map<String, Object> map) {
		return creditRatingMapper.queryIssuercountByGroupId(map);
	}

	public int queryBondcountByGroupId(Map<String, Object> map) {
		return creditRatingMapper.queryBondcountByGroupId(map);
	}

	public int save(BondCreditRating creditRating) {
		return creditRatingMapper.save(creditRating);
	}

	public int update(BondCreditRating creditRating) {
		return creditRatingMapper.update(creditRating);
	}

	public List<BondCreditRating> queryByGroupIdAndIssuerId(Map<String, Object> map) {
		return creditRatingMapper.queryByGroupIdAndIssuerId(map);
	}

	public BondCreditRating queryByGroupIdAndBondId(Map<String, Object> map) {
		return creditRatingMapper.queryByGroupIdAndBondId(map);
	}

	public List<CreditRatingIssuerVo> queryIssuers(Map<String, Object> map) {
		return creditRatingMapper.queryIssuers(map);
	}

	public List<CreditRatingBondVo> queryBonds(Map<String, Object> map) {
		return creditRatingMapper.queryBonds(map);
	}

	public Long queryGroupIdByOrgIdAndIssuerId(Map<String, Object> map) {
		return creditRatingMapper.queryGroupIdByOrgIdAndIssuerId(map);
	}

	public int saveOrUpdate(BondCreditRating creditRating) {
		return creditRatingMapper.saveOrUpdate(creditRating);
	}

	public int queryCount(BondCreditRating creditRating) {
		return creditRatingMapper.queryCount(creditRating);
	}

	public int updateGroup(Map<String, Object> map) {
		return creditRatingMapper.updateGroup(map);
	}

	public int queryIssuersCount(Map<String, Object> map){
		return creditRatingMapper.queryIssuersCount(map);
	}

	public int queryBondsCount(Map<String, Object> map){
		return creditRatingMapper.queryBondsCount(map);
	}
	
	public int updateRatingId(Map<String, Object> map){
		return creditRatingMapper.updateRatingId(map);
	}

	public int updateAdviceId(Map<String, Object> map){
		return creditRatingMapper.updateAdviceId(map);
	}
	
	public int updateGroupId(Long groupId, Long targetGroupId){
		Map<String, Object> map = new HashMap<>();
		map.put("groupId", groupId);
		map.put("targetGroupId", targetGroupId);
		return creditRatingMapper.updateGroupId(map);
	}
	
	public int queryCountByOrgid(BondCreditRating creditRating){
		return creditRatingMapper.queryCountByOrgid(creditRating);
	}
	
	public int queryIssuerCountByOrgid(BondCreditRating creditRating){
		return creditRatingMapper.queryIssuerCountByOrgid(creditRating);
	}
	
	public int updateGroupIdByOrgid(BondCreditRating creditRating){
		return creditRatingMapper.updateGroupIdByOrgid(creditRating);

	}
	
	public int saveCreditRating(BondCreditRating creditRating){
		return creditRatingMapper.saveCreditRating(creditRating);
	}
	
	public List<Map<String, Object>> querySameIssuerGroup(){
		return creditRatingMapper.querySameIssuerGroup();
	}
	
	public BondCreditRating queryIssuerGroup(Map<String, Object> map){
		return creditRatingMapper.queryIssuerGroup(map);
	}
	
	public int updateIssuerGroup(Map<String, Object> map){
		return creditRatingMapper.updateIssuerGroup(map);
	}
	
	public IssuerGroupInfoVo queryIssuerGroupInfo(Map<String, Object> map){
		return creditRatingMapper.queryIssuerGroupInfo(map);
	}
}
