package com.innodealing.bond.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.innodealing.consts.Constants;

/**
 * 机构评级处理
 * @author liuqi
 *
 */
@Service
public class BondInstRatingService {

	@Autowired
	BondInduService bondInduService;
	
	public String getRatingIdByUserId(Long userid) {
		String str = null;
		Integer org_id = bondInduService.getInstitution(userid);
		str = Constants.INST_RATING_CODE+org_id;
		return str;
	}
	
	public String getInstInvestmentAdviceIdByUserId(Long userid) {
		String str = null;
		Integer org_id = bondInduService.getInstitution(userid);
		str = Constants.INST_INVESTMENT_ADVICE_CODE+org_id;
		return str;
	}

}
