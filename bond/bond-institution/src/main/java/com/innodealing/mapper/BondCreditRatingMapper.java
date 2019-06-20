package com.innodealing.mapper;

import java.util.List;
import java.util.Map;

import com.innodealing.model.mysql.BondCreditRating;
import com.innodealing.model.vo.CreditRatingBondVo;
import com.innodealing.model.vo.CreditRatingIssuerVo;
import com.innodealing.model.vo.IssuerGroupInfoVo;

public interface BondCreditRatingMapper {

	public List<BondCreditRating> queryByUserId(Integer userId);
	
	public List<BondCreditRating> queryByOrgId(Integer orgId);
	
	public int queryIssuercountByGroupId(Map<String, Object> map);

	public int queryBondcountByGroupId(Map<String, Object> map);

	public int save(BondCreditRating creditRating);

	public int update(BondCreditRating creditRating);

	public List<BondCreditRating> queryByGroupIdAndIssuerId(Map<String, Object> map);

	public BondCreditRating queryByGroupIdAndBondId(Map<String, Object> map);

	public List<CreditRatingIssuerVo> queryIssuers(Map<String, Object> map);
	
	public int queryIssuersCount(Map<String, Object> map);
	
	public List<CreditRatingBondVo> queryBonds(Map<String, Object> map);
	
	public int queryBondsCount(Map<String, Object> map);

	public Long queryGroupIdByOrgIdAndIssuerId(Map<String, Object> map);

	public int saveOrUpdate(BondCreditRating creditRating);
	
	public int queryCount(BondCreditRating creditRating);

	public int updateGroup(Map<String, Object> map);
	
	public int updateRatingId(Map<String, Object> map);

	public int updateAdviceId(Map<String, Object> map);
	
	public int updateGroupId(Map<String, Object> map);
	
	public int queryCountByOrgid(BondCreditRating creditRating);
	
	public int updateGroupIdByOrgid(BondCreditRating creditRating);
	
	public int saveCreditRating(BondCreditRating creditRating);
	
	public List<Map<String, Object>> querySameIssuerGroup();

	public BondCreditRating queryIssuerGroup(Map<String, Object> map);

	public int updateIssuerGroup(Map<String, Object> map);
	
	public IssuerGroupInfoVo queryIssuerGroupInfo(Map<String, Object> map);

	public int queryIssuerCountByOrgid(BondCreditRating creditRating);
}
