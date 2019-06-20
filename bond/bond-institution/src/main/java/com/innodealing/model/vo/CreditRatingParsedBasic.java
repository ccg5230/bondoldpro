package com.innodealing.model.vo;

import com.innodealing.model.mysql.BondInstCode;

import io.swagger.annotations.ApiModelProperty;

public class CreditRatingParsedBasic extends CreditRatingBasic {

	@ApiModelProperty(value = "发行人")
	private String issuer;

	@ApiModelProperty(value = "债券id")
	private Long bondId = 0L;

	@ApiModelProperty(value = "债券简称")
	private String bondName;

	@ApiModelProperty(value = "债券代码")
	private String bondCode;

	@ApiModelProperty(value = "信评组名称")
	private String groupName;

	@ApiModelProperty(value = "本次内部评级")
	private BondInstCode selectedRating;

	@ApiModelProperty(value = "本次投资建议")
	private BondInstCode selectedInvestmentAdvice;
	
	@ApiModelProperty(value = "评级说明发行人分析")
	private String currentRatingDes = "";
	
	@ApiModelProperty(value = "投资建议个券分析")
	private String investmentAdviceDesDetail = "";

	public String getIssuer() {
		return issuer;
	}

	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}

	public Long getBondId() {
		return bondId;
	}

	public void setBondId(Long bondId) {
		this.bondId = bondId;
	}

	public String getBondName() {
		return bondName;
	}

	public void setBondName(String bondName) {
		this.bondName = bondName;
	}

	public String getBondCode() {
		return bondCode;
	}

	public void setBondCode(String bondCode) {
		this.bondCode = bondCode;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public BondInstCode getSelectedRating() {
		return selectedRating;
	}

	public void setSelectedRating(BondInstCode selectedRating) {
		this.selectedRating = selectedRating;
	}

	public BondInstCode getSelectedInvestmentAdvice() {
		return selectedInvestmentAdvice;
	}

	public void setSelectedInvestmentAdvice(BondInstCode selectedInvestmentAdvice) {
		this.selectedInvestmentAdvice = selectedInvestmentAdvice;
	}

	public String getCurrentRatingDes() {
		return currentRatingDes;
	}

	public void setCurrentRatingDes(String currentRatingDes) {
		this.currentRatingDes = currentRatingDes;
	}

	public String getInvestmentAdviceDesDetail() {
		return investmentAdviceDesDetail;
	}

	public void setInvestmentAdviceDesDetail(String investmentAdviceDesDetail) {
		this.investmentAdviceDesDetail = investmentAdviceDesDetail;
	}
	
}
