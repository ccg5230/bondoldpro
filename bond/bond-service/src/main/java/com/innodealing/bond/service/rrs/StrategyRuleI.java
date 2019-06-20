package com.innodealing.bond.service.rrs;

import com.innodealing.bond.vo.summary.BondIssDMRatingSummaryVO;
import com.innodealing.bond.vo.summary.IssRatingScoreSummary;

/**
 * @author xiaochao
 * @date 2017年2月10日
 * @decription TODO
 */
public interface StrategyRuleI {
	boolean loadDMRatingSummary(BondIssDMRatingSummaryVO dmRatingSummary);
	default boolean loadIssRatingScoreSummary(IssRatingScoreSummary dmRatingSummary) {
		assert false: "it's a loadIssRatingScoreSummary's default case should never be called";
		return false;
	}
}
