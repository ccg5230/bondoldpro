package com.innodealing.bond.vo.summary;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class IssRatingScoreSummary {
	
	private String curFinQuarter;
	private String preFinQuarter;
	private BigDecimal curSum;
	private BigDecimal preSum;
	private String trendDesc;
	private List<BondRatingScoreEntry> scores = new ArrayList<BondRatingScoreEntry> ();
	
	public String getCurFinQuarter() {
		return curFinQuarter;
	}
	public void setCurFinQuarter(String curFinQuarter) {
		this.curFinQuarter = curFinQuarter;
	}
	public String getPreFinQuarter() {
		return preFinQuarter;
	}
	public void setPreFinQuarter(String preFinQuarter) {
		this.preFinQuarter = preFinQuarter;
	}
	public BigDecimal getCurSum() {
		return curSum;
	}
	public void setCurSum(BigDecimal curSum) {
		this.curSum = curSum;
	}
	public BigDecimal getPreSum() {
		return preSum;
	}
	public void setPreSum(BigDecimal preSum) {
		this.preSum = preSum;
	}
	public List<BondRatingScoreEntry> getScores() {
		return scores;
	}
	public void setScores(List<BondRatingScoreEntry> scores) {
		this.scores = scores;
	}
	public String getTrendDesc() {
		return trendDesc;
	}
	public void setTrendDesc(String trendDesc) {
		this.trendDesc = trendDesc;
	}

}
