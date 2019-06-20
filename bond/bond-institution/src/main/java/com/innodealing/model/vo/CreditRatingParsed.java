package com.innodealing.model.vo;

import java.util.List;

import com.innodealing.model.mysql.BondCreditRatingGroup;
import com.innodealing.model.mysql.BondInstCode;

import io.swagger.annotations.ApiModelProperty;

public class CreditRatingParsed extends CreditRatingParsedBasic {

	@ApiModelProperty(value = "当前投资建议ID")
	private Integer investmentAdviceId = 0;

	@ApiModelProperty(value = "当前投资建议")
	private String investmentAdvice;

	@ApiModelProperty(value = "当前内部评级ID")
	private Integer currentRatingId = 0;

	@ApiModelProperty(value = "当前内部评级")
	private String currentRating;

	@ApiModelProperty(value = "所有的内部评级")
	private List<BondInstCode> allRating;

	@ApiModelProperty(value = "所有的投资建议")
	private List<BondInstCode> allInvestmentAdvice;

	@ApiModelProperty(value = "当前所属信评组")
	private BondCreditRatingGroup currGroup;

	@ApiModelProperty(value = "提示")
	private String reminderHint;
	
	public List<BondInstCode> getAllRating() {
		return allRating;
	}

	public void setAllRating(List<BondInstCode> allRating) {
		this.allRating = allRating;
	}

	public List<BondInstCode> getAllInvestmentAdvice() {
		return allInvestmentAdvice;
	}

	public void setAllInvestmentAdvice(List<BondInstCode> allInvestmentAdvice) {
		this.allInvestmentAdvice = allInvestmentAdvice;
	}

	public BondCreditRatingGroup getCurrGroup() {
		return currGroup;
	}

	public void setCurrGroup(BondCreditRatingGroup currGroup) {
		this.currGroup = currGroup;
	}

	public String getInvestmentAdvice() {
		return investmentAdvice;
	}

	public void setInvestmentAdvice(String investmentAdvice) {
		this.investmentAdvice = investmentAdvice;
	}

	public String getCurrentRating() {
		return currentRating;
	}

	public void setCurrentRating(String currentRating) {
		this.currentRating = currentRating;
	}

	public Integer getInvestmentAdviceId() {
		return investmentAdviceId;
	}

	public void setInvestmentAdviceId(Integer investmentAdviceId) {
		this.investmentAdviceId = investmentAdviceId;
	}

	public Integer getCurrentRatingId() {
		return currentRatingId;
	}

	public void setCurrentRatingId(Integer currentRatingId) {
		this.currentRatingId = currentRatingId;
	}

	public String getReminderHint() {
		return reminderHint;
	}

	public void setReminderHint(String reminderHint) {
		this.reminderHint = reminderHint;
	}
}
