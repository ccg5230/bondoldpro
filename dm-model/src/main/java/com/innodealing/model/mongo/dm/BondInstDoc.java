package com.innodealing.model.mongo.dm;

import java.util.Map;

import com.innodealing.model.dm.bond.BondCreditRatingGroup;

import io.swagger.annotations.ApiModelProperty;

/**
 * 机构自定义属性
 * @author liuqi
 *
 */
public class BondInstDoc {
	
    @ApiModelProperty(value = "机构所属行业")
    protected Map<String,Object> institutionInduMap; 
    
	@ApiModelProperty(value = "内部评级")
	protected String instRating;
	
	@ApiModelProperty(value = "内部评级ID")
	protected Integer instRatingId;

	@ApiModelProperty(value = "投资建议")
	protected String instInvestmentAdvice;
	
	@ApiModelProperty(value = "投资建议ID")
	protected Integer instInvestmentAdviceId;
	
	@ApiModelProperty(value = "信评池分组信息")
	protected BondCreditRatingGroup bondCreditRatingGroup;
	
	@ApiModelProperty(value = "机构内部评级")
	protected Map<String,Object> instRatingMap;
	
	@ApiModelProperty(value = "机构投资建议")
	protected Map<String,Object> instInvestmentAdviceMap;
	
	@ApiModelProperty(value = "评级说明")
	protected int instRatingDescribe;
	
	@ApiModelProperty(value = "投资建议说明")
	protected int instInvestmentAdviceDescribe;

	public Map<String, Object> getInstitutionInduMap() {
		return institutionInduMap;
	}

	public void setInstitutionInduMap(Map<String, Object> institutionInduMap) {
		this.institutionInduMap = institutionInduMap;
	}

	public String getInstRating() {
		return instRating;
	}

	public void setInstRating(String instRating) {
		this.instRating = instRating;
	}

	public String getInstInvestmentAdvice() {
		return instInvestmentAdvice;
	}

	public void setInstInvestmentAdvice(String instInvestmentAdvice) {
		this.instInvestmentAdvice = instInvestmentAdvice;
	}

	public BondCreditRatingGroup getBondCreditRatingGroup() {
		return bondCreditRatingGroup;
	}

	public void setBondCreditRatingGroup(BondCreditRatingGroup bondCreditRatingGroup) {
		this.bondCreditRatingGroup = bondCreditRatingGroup;
	}

	public Map<String, Object> getInstRatingMap() {
		return instRatingMap;
	}

	public void setInstRatingMap(Map<String, Object> instRatingMap) {
		this.instRatingMap = instRatingMap;
	}

	public Map<String, Object> getInstInvestmentAdviceMap() {
		return instInvestmentAdviceMap;
	}

	public void setInstInvestmentAdviceMap(Map<String, Object> instInvestmentAdviceMap) {
		this.instInvestmentAdviceMap = instInvestmentAdviceMap;
	}

	public int getInstRatingDescribe() {
		return instRatingDescribe;
	}

	public void setInstRatingDescribe(int instRatingDescribe) {
		this.instRatingDescribe = instRatingDescribe;
	}

	public int getInstInvestmentAdviceDescribe() {
		return instInvestmentAdviceDescribe;
	}

	public void setInstInvestmentAdviceDescribe(int instInvestmentAdviceDescribe) {
		this.instInvestmentAdviceDescribe = instInvestmentAdviceDescribe;
	}

	public Integer getInstRatingId() {
		return instRatingId;
	}

	public void setInstRatingId(Integer instRatingId) {
		this.instRatingId = instRatingId;
	}

	public Integer getInstInvestmentAdviceId() {
		return instInvestmentAdviceId;
	}

	public void setInstInvestmentAdviceId(Integer instInvestmentAdviceId) {
		this.instInvestmentAdviceId = instInvestmentAdviceId;
	}

}
