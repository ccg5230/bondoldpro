package com.innodealing.mapper;

import java.util.List;
import java.util.Map;

import com.innodealing.model.mysql.BondCreditRatingGroup;

public interface BondCreditRatingGroupMapper {

	public int save(BondCreditRatingGroup creditRatingGroup);

	public BondCreditRatingGroup queryByName(String groupName);
	
	public int delete(Long groupId);
	
	public int update(BondCreditRatingGroup creditRatingGroup);

	public int updateGroupId(Map<String, Object> map);

	public BondCreditRatingGroup queryById(Long groupId);
	
	public List<BondCreditRatingGroup> queryByUserId(Integer userId);
	
	public List<BondCreditRatingGroup> queryByOrgId(Integer orgId);

	public List<BondCreditRatingGroup> queryExceptId(Long groupId);
	
	public BondCreditRatingGroup queryByIssuerId(Map<String, Object> map);

	public BondCreditRatingGroup queryByNameAndOrgId(Map<String, Object> map);

	public BondCreditRatingGroup queryDefaultOneByOrgId(Map<String, Object> map);

}
