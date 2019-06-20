package com.innodealing.model;

import java.math.BigDecimal;

import org.springframework.data.mongodb.core.mapping.Document;

import com.innodealing.util.Column;

import io.swagger.annotations.ApiModelProperty;

@Document(collection = "bond_detail_info")
public class VBondPubComDetail {

	@ApiModelProperty(value = "唯一标识")
	private int id;



	@Column(name = "bond_uni_code")
	@ApiModelProperty(value = "债券标识")
	private Long bondUniCode;
	
	
	@ApiModelProperty(value = "个卷分析是否有数据")
	private Long instInvestmentAdviceDescribe;
	
	@Column(name = "investment_advice_desdetail")
	private String investmentAdviceDesdetail;
	
	@Column(name = "rating")
	private String instRatingId;
	
	@Column(name = "investment_advice")
	private String instInvestmentAdviceId;
	
	
	public String getInstRatingId() {
		return instRatingId;
	}

	public void setInstRatingId(String instRatingId) {
		this.instRatingId = instRatingId;
	}

	public String getInstInvestmentAdviceId() {
		return instInvestmentAdviceId;
	}

	public void setInstInvestmentAdviceId(String instInvestmentAdviceId) {
		this.instInvestmentAdviceId = instInvestmentAdviceId;
	}

	@Column(name = "rating_describe")
	private String ratingDescribe;
	
	
	
	public String getInvestmentAdviceDesdetail() {
		return investmentAdviceDesdetail;
	}

	public void setInvestmentAdviceDesdetail(String investmentAdviceDesdetail) {
		this.investmentAdviceDesdetail = investmentAdviceDesdetail;
	}

	public String getRatingDescribe() {
		return ratingDescribe;
	}

	public void setRatingDescribe(String ratingDescribe) {
		this.ratingDescribe = ratingDescribe;
	}

	@ApiModelProperty(value = "发行人分析是否有数据 ")
	private Long instRatingDescribe;
	

	public Long getInstInvestmentAdviceDescribe() {
		return instInvestmentAdviceDescribe;
	}

	public void setInstInvestmentAdviceDescribe(Long instInvestmentAdviceDescribe) {
		this.instInvestmentAdviceDescribe = instInvestmentAdviceDescribe;
	}

	public Long getInstRatingDescribe() {
		return instRatingDescribe;
	}

	public void setInstRatingDescribe(Long instRatingDescribe) {
		this.instRatingDescribe = instRatingDescribe;
	}

	@ApiModelProperty(value = "债券名称")
	private String name;

	@ApiModelProperty(value = "债券代码")
	private String code;

	@ApiModelProperty(value = "关联备注")
	private String remark;
	@ApiModelProperty(value = "剩余期限")
	private String tenor;
	@ApiModelProperty(value = "票面")
	private Double coupRate;

	@ApiModelProperty(value = "当前投资建议名称")
	private String instInvestmentAdvice;

	@ApiModelProperty(value = "当前内部评级名称")
	@Column(name = "rating_name")
	private String instRating;

	@ApiModelProperty(value = "投资建议修改时间")
	private String investDate;

	@ApiModelProperty(value = "债项评级")
	private String bondRating;

	@ApiModelProperty(value = "隐含评级")
	private String impliedRating;

	@ApiModelProperty(value = "(中债)估值")
	private BigDecimal fairValue;

	@ApiModelProperty(value = "bid价格")
	private Double bidPrice;

	@ApiModelProperty(value = "bid报价数量")
	private BigDecimal bidVol;

	@ApiModelProperty(value = "bid报价笔数")
	private Long bidOrderCnt;
	
	@ApiModelProperty(value = "ofr价格")
	private Double ofrPrice;
	@ApiModelProperty(value = "ofr报价数额")
	private BigDecimal ofrVol;

	@ApiModelProperty(value = "ofr报价笔数")
	private Long ofrOrderCnt;

	@ApiModelProperty(value = "成交价")
	private Double price;

	@ApiModelProperty(value = "最近付息日")
	private String latelyPayDate;

	public Long getBidOrderCnt() {
		return bidOrderCnt;
	}

	public BigDecimal getBidVol() {
		return bidVol;
	}

	public Double getBidPrice() {
		return bidPrice;
	}

	public void setBidPrice(Double bidPrice) {
		this.bidPrice = bidPrice;
	}
	
	public String getBondRating() {
		return bondRating;
	}

	public Long getBondUniCode() {
		return bondUniCode;
	}

	public String getCode() {
		return code;
	}

	public Double getCoupRate() {
		return coupRate;
	}

	public BigDecimal getFairValue() {
		return fairValue;
	}

	public int getId() {
		return id;
	}

	public String getImpliedRating() {
		return impliedRating;
	}

	public String getInvestDate() {
		return investDate;
	}

	public String getLatelyPayDate() {
		return latelyPayDate;
	}

	public String getName() {
		return name;
	}

	public Long getOfrOrderCnt() {
		return ofrOrderCnt;
	}

	

	public BigDecimal getOfrVol() {
		return ofrVol;
	}


	public String getRemark() {
		return remark;
	}

	public String getTenor() {
		return tenor;
	}

	public void setBidOrderCnt(Long bidOrderCnt) {
		this.bidOrderCnt = bidOrderCnt;
	}

	public void setBidVol(BigDecimal bidVol) {
		this.bidVol = bidVol;
	}

	public void setBondRating(String bondRating) {
		this.bondRating = bondRating;
	}

	public void setBondUniCode(Long bondUniCode) {
		this.bondUniCode = bondUniCode;
	}

	public String getInstInvestmentAdvice() {
		return instInvestmentAdvice;
	}

	public void setInstInvestmentAdvice(String instInvestmentAdvice) {
		this.instInvestmentAdvice = instInvestmentAdvice;
	}

	public String getInstRating() {
		return instRating;
	}

	public void setInstRating(String instRating) {
		this.instRating = instRating;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setCoupRate(Double coupRate) {
		this.coupRate = coupRate;
	}

	public void setFairValue(BigDecimal fairValue) {
		this.fairValue = fairValue;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setImpliedRating(String impliedRating) {
		this.impliedRating = impliedRating;
	}

	public void setInvestDate(String investDate) {
		this.investDate = investDate;
	}

	public void setLatelyPayDate(String latelyPayDate) {
		this.latelyPayDate = latelyPayDate;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOfrOrderCnt(Long ofrOrderCnt) {
		this.ofrOrderCnt = ofrOrderCnt;
	}



	public Double getOfrPrice() {
		return ofrPrice;
	}

	public void setOfrPrice(Double ofrPrice) {
		this.ofrPrice = ofrPrice;
	}

	public void setOfrVol(BigDecimal ofrVol) {
		this.ofrVol = ofrVol;
	}


	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setTenor(String tenor) {
		this.tenor = tenor;
	}

}
