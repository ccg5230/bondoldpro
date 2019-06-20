package com.innodealing.model.vo;

import io.swagger.annotations.ApiModelProperty;

public class CreditRatingBondVo {

	@ApiModelProperty(value = "债券id")
	private Long bondId;

	@ApiModelProperty(value = "债券简称")
	private String bondName;

	@ApiModelProperty(value = "债券编码")
	private String bondCode;

	@ApiModelProperty(value = "投资建议")
	private String investmentAdvice;

	@ApiModelProperty(value = "investmentAdvice时间，yyyy-mm-dd")
	private String investmentAdviceDate;

	@ApiModelProperty(value = "投资建议--个券分析")
	private String investmentAdviceDesc = "";
	
	@ApiModelProperty(value = "隐含评级")
	private String impliedRating;

	@ApiModelProperty(value = "发行人")
	private String issuer;

    @ApiModelProperty(value = "发行人ID", hidden = true) 
	private Long issuerId;
	
	@ApiModelProperty(value = "行业")
	private String induName;

	@ApiModelProperty(value = "内部评级")
	private String currentRating;

	@ApiModelProperty(value = "内部评级变化")
	private Integer currentRatingDiff;

	@ApiModelProperty(value = "内部评级时间，yyyy-mm-dd")
	private String currentRatingDate;

	@ApiModelProperty(value = "内部评级--发行人分析")
	private String currentRatingDesc = "";
	
	@ApiModelProperty(value = "主体/债项")
	private String issBondRating;

	@ApiModelProperty(value = "主体量化风险等级")
	private String pd;

	@ApiModelProperty(value = "主体评级展望")
	private String issRatePros;
	
    @ApiModelProperty(value = "主体量化风险等级-变化")
    private Long pdDiff;
    
    @ApiModelProperty(value = "主体量化风险等级-更新时间")
    private String pdTime;

	@ApiModelProperty(value = "是否关注")
	private Boolean isFavorited;

	@ApiModelProperty(value = "关注Id")
	private Integer favoriteId;

	@ApiModelProperty(value = "是否已经加入对比")
	private Boolean isCompared;

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

	public String getInvestmentAdvice() {
		return investmentAdvice;
	}

	public void setInvestmentAdvice(String investmentAdvice) {
		this.investmentAdvice = investmentAdvice;
	}

	public String getInvestmentAdviceDate() {
		return investmentAdviceDate;
	}

	public void setInvestmentAdviceDate(String investmentAdviceDate) {
		this.investmentAdviceDate = investmentAdviceDate;
	}

	public String getImpliedRating() {
		return impliedRating;
	}

	public void setImpliedRating(String impliedRating) {
		this.impliedRating = impliedRating;
	}

	public String getIssuer() {
		return issuer;
	}

	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}

	public Long getIssuerId() {
		return issuerId;
	}

	public void setIssuerId(Long issuerId) {
		this.issuerId = issuerId;
	}

	public String getInduName() {
		return induName;
	}

	public void setInduName(String induName) {
		this.induName = induName;
	}

	public String getCurrentRating() {
		return currentRating;
	}

	public void setCurrentRating(String currentRating) {
		this.currentRating = currentRating;
	}

	public Integer getCurrentRatingDiff() {
		return currentRatingDiff;
	}

	public void setCurrentRatingDiff(Integer currentRatingDiff) {
		this.currentRatingDiff = currentRatingDiff;
	}

	public String getCurrentRatingDate() {
		return currentRatingDate;
	}

	public void setCurrentRatingDate(String currentRatingDate) {
		this.currentRatingDate = currentRatingDate;
	}

	public String getIssBondRating() {
		return issBondRating;
	}

	public void setIssBondRating(String issBondRating) {
		this.issBondRating = issBondRating;
	}

	public String getPd() {
		return pd;
	}

	public void setPd(String pd) {
		this.pd = pd;
	}

	public String getIssRatePros() {
		return issRatePros;
	}

	public void setIssRatePros(String issRatePros) {
		this.issRatePros = issRatePros;
	}
	
	public Long getPdDiff() {
		return pdDiff;
	}

	public void setPdDiff(Long pdDiff) {
		this.pdDiff = pdDiff;
	}
	
	public String getPdTime() {
		return pdTime;
	}

	public void setPdTime(String pdTime) {
		this.pdTime = pdTime;
	}

	public Boolean getIsFavorited() {
		return isFavorited;
	}

	public void setIsFavorited(Boolean isFavorited) {
		this.isFavorited = isFavorited;
	}

	public Integer getFavoriteId() {
		return favoriteId;
	}

	public void setFavoriteId(Integer favoriteId) {
		this.favoriteId = favoriteId;
	}

	public Boolean getIsCompared() {
		return isCompared;
	}

	public void setIsCompared(Boolean isCompared) {
		this.isCompared = isCompared;
	}

	public String getInvestmentAdviceDesc() {
		return investmentAdviceDesc;
	}

	public void setInvestmentAdviceDesc(String investmentAdviceDesc) {
		this.investmentAdviceDesc = investmentAdviceDesc;
	}

	public String getCurrentRatingDesc() {
		return currentRatingDesc;
	}

	public void setCurrentRatingDesc(String currentRatingDesc) {
		this.currentRatingDesc = currentRatingDesc;
	}

	public CreditRatingBondVo() {
		super();
	}

}
