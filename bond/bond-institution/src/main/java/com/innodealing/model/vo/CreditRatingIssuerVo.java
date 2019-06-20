package com.innodealing.model.vo;

import java.util.List;

import com.innodealing.model.mysql.BondCreditRatingGroup;

import io.swagger.annotations.ApiModelProperty;

public class CreditRatingIssuerVo {

	@ApiModelProperty(value = "发行人Id")
	private Long issuerId;

	@ApiModelProperty(value = "发行人名称")
	private String issuerName;
	
	@ApiModelProperty(value = "行业名称")
	private String induName;

	@ApiModelProperty(value = "内部评级")
	private String currentRating;
	
	@ApiModelProperty(value = "内部评级变化")
	private Integer currentRatingDiff;
	
	@ApiModelProperty(value = "内部评级时间，yyyy-mm-dd")
	private String currentRatingDate;
	
	@ApiModelProperty(value = "内部评级--发行人分析")
	private String currentRatingDesc;
	
    @ApiModelProperty(value = "历史最高风险等级")
    private String worstPd;
    
    @ApiModelProperty(value = "历史最高风险值")
    private Long worstPdNum;
    
    //显示季度
    @ApiModelProperty(value = "历史最高风险等级-更新时间")
    private String worstPdTime;
    
    @ApiModelProperty(value = "主体评级-外部评级")
	private String issCredLevel;
	
	@ApiModelProperty(value = "评级展望")
	private String rateProsPar;
	
    @ApiModelProperty(value = "主体量化风险等级-变化")
    private Long pdDiff;
    
    @ApiModelProperty(value = "主体量化风险等级")
    private String pd;
    
    @ApiModelProperty(value = "主体量化风险等级-更新时间")
    private String pdTime;
    
	@ApiModelProperty(value = "当前所在信评组")
	private BondCreditRatingGroup currGroup;
	
	@ApiModelProperty(value = "所有信评组")
	private List<BondCreditRatingGroup> allGroup;

	public Long getIssuerId() {
		return issuerId;
	}

	public void setIssuerId(Long issuerId) {
		this.issuerId = issuerId;
	}

	public String getIssuerName() {
		return issuerName;
	}

	public void setIssuerName(String issuerName) {
		this.issuerName = issuerName;
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

	public String getWorstPd() {
		return worstPd;
	}

	public void setWorstPd(String worstPd) {
		this.worstPd = worstPd;
	}

	public Long getWorstPdNum() {
		return worstPdNum;
	}

	public void setWorstPdNum(Long worstPdNum) {
		this.worstPdNum = worstPdNum;
	}

	public String getWorstPdTime() {
		return worstPdTime;
	}

	public void setWorstPdTime(String worstPdTime) {
		this.worstPdTime = worstPdTime;
	}

	public String getIssCredLevel() {
		return issCredLevel;
	}

	public void setIssCredLevel(String issCredLevel) {
		this.issCredLevel = issCredLevel;
	}

	public String getRateProsPar() {
		return rateProsPar;
	}

	public void setRateProsPar(String rateProsPar) {
		this.rateProsPar = rateProsPar;
	}

	public Long getPdDiff() {
		return pdDiff;
	}

	public void setPdDiff(Long pdDiff) {
		this.pdDiff = pdDiff;
	}

	public String getPd() {
		return pd;
	}

	public void setPd(String pd) {
		this.pd = pd;
	}

	public String getPdTime() {
		return pdTime;
	}

	public void setPdTime(String pdTime) {
		this.pdTime = pdTime;
	}

	public BondCreditRatingGroup getCurrGroup() {
		return currGroup;
	}

	public void setCurrGroup(BondCreditRatingGroup currGroup) {
		this.currGroup = currGroup;
	}

	public List<BondCreditRatingGroup> getAllGroup() {
		return allGroup;
	}

	public void setAllGroup(List<BondCreditRatingGroup> allGroup) {
		this.allGroup = allGroup;
	}
	
	public String getCurrentRatingDesc() {
		return currentRatingDesc;
	}

	public void setCurrentRatingDesc(String currentRatingDesc) {
		this.currentRatingDesc = currentRatingDesc;
	}

	public CreditRatingIssuerVo() {
		super();
	}
	
}
