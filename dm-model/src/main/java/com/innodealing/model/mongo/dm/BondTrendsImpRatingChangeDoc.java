package com.innodealing.model.mongo.dm;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author xiaochao
 * @time 2017年4月12日
 * @description:
 */
@Document(collection = "bond_trends_imprating_change")
public class BondTrendsImpRatingChangeDoc extends BondInstDoc implements Serializable {
	private static final long serialVersionUID = -4918234280964954019L;
	
	@Indexed
	@ApiModelProperty(value = "发布日期")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date pubDate;

	@ApiModelProperty(value = "债券编号")
	private Long bondUniCode;

	@ApiModelProperty(value = "债券代码")
	private String bondCode;

	@ApiModelProperty(value = "债券简称")
	private String bondName;

	@ApiModelProperty(value = "主体编号")
	private Long issuerId;

	@ApiModelProperty(value = "主体名称")
	private String issuerName;

	@ApiModelProperty(value = "主体量化风险等级(违约概率)")
	private String dmPd;

	@ApiModelProperty(value = "主体量化风险等级数值，用于排序")
	@JsonIgnore
	private Long dmPdNum;
	
    @ApiModelProperty(value = "主体量化风险等级-更新时间,显示季度")
    private String dmPdTime;
	
    @ApiModelProperty(value = "主体量化风险等级变动值")
    private Long dmPdDiff;

	@ApiModelProperty(value = "风险警告标志, 量化风险等级<=CCC")
	private Boolean riskWarning;

	@ApiModelProperty(value = "历史最高风险等级")
	private String dmWorstPd;

	@ApiModelProperty(value = "历史最高风险等级数值，用于排序")
	@JsonIgnore
	private Long dmWorstPdNum;
	
    @ApiModelProperty(value = "历史最高风险等级-更新时间,显示季度")
    private String dmWorstPdTime;

	@ApiModelProperty(value = "历史最高风险警告标志, 量化风险等级<=CCC")
	private Boolean worstRiskWarning;

	@ApiModelProperty(value = "估值")
	private Double valuation;

	@ApiModelProperty(value = "变动前")
	private String lastRating;

	@ApiModelProperty(value = "变动前等级值，用于排序")
	@JsonIgnore
	private Long lastRatingPar;

	@ApiModelProperty(value = "变动后")
	private String currRating;

	@ApiModelProperty(value = "变动后等级值，用于排序")
	@JsonIgnore
	private Long currRatingPar;

	@ApiModelProperty(value = "变动结果")
	private String ratingResult;

	@ApiModelProperty(value = "评级变动值[下调-大于0;上调-小于0]")
	private Long rateDiff;

	public Date getPubDate() {
		return pubDate;
	}

	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}

	public String getBondCode() {
		return bondCode;
	}

	public void setBondCode(String bondCode) {
		this.bondCode = bondCode;
	}

	public Double getValuation() {
		return valuation;
	}

	public void setValuation(Double valuation) {
		this.valuation = valuation;
	}

	public String getLastRating() {
		return lastRating;
	}

	public void setLastRating(String lastRating) {
		this.lastRating = lastRating;
	}

	public String getCurrRating() {
		return currRating;
	}

	public void setCurrRating(String currRating) {
		this.currRating = currRating;
	}

	public String getRatingResult() {
		return ratingResult;
	}

	public void setRatingResult(String ratingResult) {
		this.ratingResult = ratingResult;
	}

	public String getBondName() {
		return bondName;
	}

	public void setBondName(String bondName) {
		this.bondName = bondName;
	}

	public String getIssuerName() {
		return issuerName;
	}

	public void setIssuerName(String issuerName) {
		this.issuerName = issuerName;
	}

	public Long getBondUniCode() {
		return bondUniCode;
	}

	public void setBondUniCode(Long bondUniCode) {
		this.bondUniCode = bondUniCode;
	}

	public Long getIssuerId() {
		return issuerId;
	}

	public void setIssuerId(Long issuerId) {
		this.issuerId = issuerId;
	}

	public String getDmPd() {
		return dmPd;
	}

	public void setDmPd(String dmPd) {
		this.dmPd = dmPd;
	}

	public String getDmWorstPd() {
		return dmWorstPd;
	}

	public void setDmWorstPd(String dmWorstPd) {
		this.dmWorstPd = dmWorstPd;
	}

	public Long getLastRatingPar() {
		return lastRatingPar;
	}

	public void setLastRatingPar(Long lastRatingPar) {
		this.lastRatingPar = lastRatingPar;
	}

	public Long getCurrRatingPar() {
		return currRatingPar;
	}

	public void setCurrRatingPar(Long currRatingPar) {
		this.currRatingPar = currRatingPar;
	}

	public Long getDmPdNum() {
		return dmPdNum;
	}

	public void setDmPdNum(Long dmPdNum) {
		this.dmPdNum = dmPdNum;
	}

	public Long getDmWorstPdNum() {
		return dmWorstPdNum;
	}

	public void setDmWorstPdNum(Long dmWorstPdNum) {
		this.dmWorstPdNum = dmWorstPdNum;
	}

	public Boolean getRiskWarning() {
		return riskWarning;
	}

	public void setRiskWarning(Boolean riskWarning) {
		this.riskWarning = riskWarning;
	}

	public String getDmWorstPdTime() {
		return dmWorstPdTime;
	}

	public void setDmWorstPdTime(String dmWorstPdTime) {
		this.dmWorstPdTime = dmWorstPdTime;
	}

	public String getDmPdTime() {
		return dmPdTime;
	}

	public void setDmPdTime(String dmPdTime) {
		this.dmPdTime = dmPdTime;
	}

	public Long getRateDiff() {
		return rateDiff;
	}

	public void setRateDiff(Long rateDiff) {
		this.rateDiff = rateDiff;
	}

	public Boolean getWorstRiskWarning() {
		return worstRiskWarning;
	}

	public void setWorstRiskWarning(Boolean worstRiskWarning) {
		this.worstRiskWarning = worstRiskWarning;
	}

	public Long getDmPdDiff() {
		return dmPdDiff;
	}

	public void setDmPdDiff(Long dmPdDiff) {
		this.dmPdDiff = dmPdDiff;
	}

	
	
}
