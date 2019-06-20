package com.innodealing.bond.vo.summary;

import java.math.BigDecimal;


public class BondRatingScoreEntry {
	
	private String scoreName;
	private String scoreClass;
	private String financeDesc;
	private BigDecimal curScore;
	private BigDecimal preScore;
	private String trendDesc;

	public BondRatingScoreEntry(String scoreName, String scoreClass, String financeDesc, BigDecimal curScore, BigDecimal preScore,
			String trendDesc) {
		super();
		this.scoreName = scoreName;
		this.scoreClass = scoreClass;
		this.financeDesc = financeDesc;
		this.curScore = curScore;
		this.preScore = preScore;
		this.trendDesc = trendDesc;
	}
	
	public String getScoreClass() {
		return scoreClass;
	}
	public void setScoreClass(String scoreClass) {
		this.scoreClass = scoreClass;
	}
	public String getScoreName() {
		return scoreName;
	}
	public void setScoreName(String scoreName) {
		this.scoreName = scoreName;
	}
	public String getFinanceDesc() {
		return financeDesc;
	}
	public void setFinanceDesc(String financeDesc) {
		this.financeDesc = financeDesc;
	}
	public BigDecimal getCurScore() {
		return curScore;
	}
	public void setCurScore(BigDecimal curScore) {
		this.curScore = curScore;
	}
	public BigDecimal getPreScore() {
		return preScore;
	}
	public void setPreScore(BigDecimal preScore) {
		this.preScore = preScore;
	}
	public String getTrendDesc() {
		return trendDesc;
	}
	public void setTrendDesc(String trendDesc) {
		this.trendDesc = trendDesc;
	}

}
