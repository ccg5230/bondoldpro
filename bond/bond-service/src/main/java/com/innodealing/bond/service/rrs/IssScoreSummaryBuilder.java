package com.innodealing.bond.service.rrs;

import java.math.BigDecimal;

import com.innodealing.bond.vo.summary.BondRatingScoreEntry;
import com.innodealing.bond.vo.summary.IssRatingScoreSummary;

public class IssScoreSummaryBuilder {
	
	private IssRatingScoreSummary scoreSummary;
	private Double preSum = new Double(0.00);
	private Double curSum = new Double(0.00);
	
	public IssScoreSummaryBuilder(IssRatingScoreSummary scoreSummary) {
		this.scoreSummary = scoreSummary;
	}
	
	public void addScore(BondDocField r, String fdClass, String finDesc) {
		Double preScoreD = r.getPre();
		Double curScoreD = r.getLast();
		BigDecimal preScore = preScoreD == null ? null : BigDecimal.valueOf(preScoreD);
		BigDecimal curScore = BigDecimal.valueOf(curScoreD);
		scoreSummary.getScores().add(new BondRatingScoreEntry(r.getColDesc(), fdClass, finDesc, 
				curScore, preScore, r.genText()));
		if (preScoreD != null) {
			preSum += r.getPre();
		}
		curSum += curScoreD;
	}
	
	public void finish(String curQuarter, String preQuarter)
	{		
		scoreSummary.setCurFinQuarter(curQuarter);
		scoreSummary.setPreFinQuarter(preQuarter);
		scoreSummary.setCurSum(BigDecimal.valueOf(curSum).setScale(3, BigDecimal.ROUND_HALF_UP));
		scoreSummary.setPreSum(BigDecimal.valueOf(preSum).setScale(3, BigDecimal.ROUND_HALF_UP));
		scoreSummary.setTrendDesc(curSum > preSum? "较上期有所上调":curSum < preSum? "较上期有所下调" : "较上期无变化");
	}
	
}
