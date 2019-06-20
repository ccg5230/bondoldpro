package com.innodealing.model.mongo.dm;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;

/** 
* @author feng.ma
* @date 2017年4月5日 上午11:33:01 
* @describe 
*/
@JsonInclude(Include.NON_NULL)
@Document(collection="bond_trends_dealdata") 
public class BondTrendsDealdataDoc implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    @Indexed
    @ApiModelProperty(value = "债券id")
    private Long bondUniCode;
    
    @ApiModelProperty(value = "债券代码")
    private String code;

    @ApiModelProperty(value = "债券缩写")
    private String bondName;
	
    @ApiModelProperty(value = "最新收益率,chinamoney上的成交价")
	private BigDecimal bondDealRate;

    @ApiModelProperty(value = "债券估值，bond_info中的数据")
	private BigDecimal valuationRate;
    
    @ApiModelProperty(value = "债券估值偏离")
	private BigDecimal valuationDeviation;
    
    @Indexed
    @ApiModelProperty(value = "高估还是低估， 1 高估，0 低估")
	private Integer valuation;
    
    @Indexed
	@ApiModelProperty(value = "成交价的公布时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date pubDate;

    //显示项目
    @ApiModelProperty(value = "剩余期限")
    private String tenor;
    
    @ApiModelProperty(value = "期限（天数），用于排序", hidden = true)
	@Indexed
    private Long tenorDays;
    
    //显示项目
    @ApiModelProperty(value = "主体评级")
	private String issRating; 
    
    //显示项目
    @ApiModelProperty(value = "债项评级")
	private String bondRating; 
    
    @ApiModelProperty(value = "申万行业")
    private String induNameSw; 

    @ApiModelProperty(value = "GICS行业")
    private String induName;
    
    @ApiModelProperty(value = "发行票息")
    private BigDecimal issCoupRate;
    
    @ApiModelProperty(value = "主体评级展望")
    private String issRatePros;
    
    @ApiModelProperty(value = "发行人")
    private String issuer;
    
    @ApiModelProperty(value = "发行人id")
    private Long issuerId;
    
    @ApiModelProperty(value = "久期")
    private BigDecimal modd;
    
	public Long getBondUniCode() {
		return bondUniCode;
	}

	public void setBondUniCode(Long bondUniCode) {
		this.bondUniCode = bondUniCode;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getBondName() {
		return bondName;
	}

	public void setBondName(String bondName) {
		this.bondName = bondName;
	}

	public BigDecimal getBondDealRate() {
		return bondDealRate;
	}

	public void setBondDealRate(BigDecimal bondDealRate) {
		this.bondDealRate = bondDealRate;
	}

	public BigDecimal getValuationRate() {
		return valuationRate;
	}

	public void setValuationRate(BigDecimal valuationRate) {
		this.valuationRate = valuationRate;
	}

	public BigDecimal getValuationDeviation() {
		return valuationDeviation;
	}

	public void setValuationDeviation(BigDecimal valuationDeviation) {
		this.valuationDeviation = valuationDeviation;
	}

	public Integer getValuation() {
		return valuation;
	}

	public void setValuation(Integer valuation) {
		this.valuation = valuation;
	}

	public Date getPubDate() {
		return pubDate;
	}

	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}

	public String getTenor() {
		return tenor;
	}

	public void setTenor(String tenor) {
		this.tenor = tenor;
	}

	public String getIssRating() {
		return issRating;
	}

	public void setIssRating(String issRating) {
		this.issRating = issRating;
	}

	public String getBondRating() {
		return bondRating;
	}

	public void setBondRating(String bondRating) {
		this.bondRating = bondRating;
	}

	public String getInduNameSw() {
		return induNameSw;
	}

	public void setInduNameSw(String induNameSw) {
		this.induNameSw = induNameSw;
	}

	public String getInduName() {
		return induName;
	}

	public void setInduName(String induName) {
		this.induName = induName;
	}

	public BigDecimal getIssCoupRate() {
		return issCoupRate;
	}

	public void setIssCoupRate(BigDecimal issCoupRate) {
		this.issCoupRate = issCoupRate;
	}

	public String getIssRatePros() {
		return issRatePros;
	}

	public void setIssRatePros(String issRatePros) {
		this.issRatePros = issRatePros;
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

	public BigDecimal getModd() {
		return modd;
	}

	public void setModd(BigDecimal modd) {
		this.modd = modd;
	}

	public Long getTenorDays() {
		return tenorDays;
	}

	public void setTenorDays(Long tenorDays) {
		this.tenorDays = tenorDays;
	}
}
