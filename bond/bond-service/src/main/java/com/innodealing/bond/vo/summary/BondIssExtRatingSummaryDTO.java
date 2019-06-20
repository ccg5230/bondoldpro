package com.innodealing.bond.vo.summary;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author xiaochao
 * @date 2017年2月10日
 * @decription TODO
 */
@JsonInclude(Include.NON_NULL)
public class BondIssExtRatingSummaryDTO {
	@ApiModelProperty(value = "发行主体")
	private String issuer;
	@ApiModelProperty(value = "行业名称")
	private String induName;
	@ApiModelProperty(value = "行业分类")
	private String induClass;

	@ApiModelProperty(value = "外部评级-评级日期")
	private Date extLatestRatingDate;
	@ApiModelProperty(value = "外部评级-评级结果")
	private String extLatestRatingResult;
	@ApiModelProperty(value = "外部评级-评级机构")
	private String extLatestRatingOrga;
	@ApiModelProperty(value = "外部评级-评级变化")
	private Integer extLatestRatingDiff;
	@ApiModelProperty(value = "外部评级-展望负面点")
	private String extLatestCceDisadvt;
	
	@ApiModelProperty(value = "评级展望")
	private String extLatestRatingForecast;
	@ApiModelProperty(value = "评级展望->评级日期")
	private Date comRateProsParDate;
	@ApiModelProperty(value = "评级展望->评级机构")
	private String comRateProsParOrga;
	
	
	//显示季度YYYY/Qn
	@ApiModelProperty(value = "外部评级-历史最差评级日期")
	private String extWorstRatingTime;
	@ApiModelProperty(value = "外部评级-历史最差评级结果")
	private String extWorstRatingResult;
	
	/**=======债劵信息======== */
	@ApiModelProperty(value = "外部评级-债劵-债券缩写")
	private String bondShortName;
	@ApiModelProperty(value = "外部评级-债劵-最新评级-评级机构")
	private String bondRatingOrga;
	@ApiModelProperty(value = "外部评级-债劵-最新评级-评级日期")
	private Date bondRatingDate;
	@ApiModelProperty(value = "外部评级-债劵-最新评级")
	private String bondRating;
	@ApiModelProperty(value = "外部评级-债劵-评级展望")
	private String bondRateProsPar;
	@ApiModelProperty(value = "外部评级-债劵-评级展望-评级机构")
	private String bondRateProsParOrga;
	@ApiModelProperty(value = "外部评级-债劵-评级展望-评级日期")
	private Date bondRateProsParDate;
	
	public String getIssuer() {
		return issuer;
	}

	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}

	public String getInduName() {
		return induName;
	}

	public void setInduName(String induName) {
		this.induName = induName;
	}

	public Date getExtLatestRatingDate() {
		return extLatestRatingDate;
	}

	public void setExtLatestRatingDate(Date extLatestRatingDate) {
		this.extLatestRatingDate = extLatestRatingDate;
	}

	public String getExtLatestRatingResult() {
		return extLatestRatingResult;
	}

	public void setExtLatestRatingResult(String extLatestRatingResult) {
		this.extLatestRatingResult = extLatestRatingResult;
	}

	public String getExtLatestRatingOrga() {
		return extLatestRatingOrga;
	}

	public void setExtLatestRatingOrga(String extLatestRatingOrga) {
		this.extLatestRatingOrga = extLatestRatingOrga;
	}

	public Integer getExtLatestRatingDiff() {
		return extLatestRatingDiff;
	}

	public void setExtLatestRatingDiff(Integer extLatestRatingDiff) {
		this.extLatestRatingDiff = extLatestRatingDiff;
	}

	public String getExtLatestRatingForecast() {
		return extLatestRatingForecast;
	}

	public void setExtLatestRatingForecast(String extLatestRatingForecast) {
		this.extLatestRatingForecast = extLatestRatingForecast;
	}

	public String getExtWorstRatingTime() {
		return extWorstRatingTime;
	}

	public void setExtWorstRatingTime(String extWorstRatingTime) {
		this.extWorstRatingTime = extWorstRatingTime;
	}

	public String getExtWorstRatingResult() {
		return extWorstRatingResult;
	}

	public void setExtWorstRatingResult(String extWorstRatingResult) {
		this.extWorstRatingResult = extWorstRatingResult;
	}

	public String getExtLatestCceDisadvt() {
		return extLatestCceDisadvt;
	}

	public void setExtLatestCceDisadvt(String extLatestCceDisadvt) {
		this.extLatestCceDisadvt = extLatestCceDisadvt;
	}

	public String getBondShortName() {
		return bondShortName;
	}

	public void setBondShortName(String bondShortName) {
		this.bondShortName = bondShortName;
	}

	public String getBondRatingOrga() {
		return bondRatingOrga;
	}

	public void setBondRatingOrga(String bondRatingOrga) {
		this.bondRatingOrga = bondRatingOrga;
	}

	public Date getBondRatingDate() {
		return bondRatingDate;
	}

	public void setBondRatingDate(Date bondRatingDate) {
		this.bondRatingDate = bondRatingDate;
	}

	public String getBondRating() {
		return bondRating;
	}

	public void setBondRating(String bondRating) {
		this.bondRating = bondRating;
	}

	public String getBondRateProsPar() {
		return bondRateProsPar;
	}

	public void setBondRateProsPar(String bondRateProsPar) {
		this.bondRateProsPar = bondRateProsPar;
	}

	public String getBondRateProsParOrga() {
		return bondRateProsParOrga;
	}

	public void setBondRateProsParOrga(String bondRateProsParOrga) {
		this.bondRateProsParOrga = bondRateProsParOrga;
	}

	public Date getBondRateProsParDate() {
		return bondRateProsParDate;
	}

	public void setBondRateProsParDate(Date bondRateProsParDate) {
		this.bondRateProsParDate = bondRateProsParDate;
	}

	public Date getComRateProsParDate() {
		return comRateProsParDate;
	}

	public void setComRateProsParDate(Date comRateProsParDate) {
		this.comRateProsParDate = comRateProsParDate;
	}

	public String getComRateProsParOrga() {
		return comRateProsParOrga;
	}

	public void setComRateProsParOrga(String comRateProsParOrga) {
		this.comRateProsParOrga = comRateProsParOrga;
	}
	
	
	

	@ApiModelProperty(value = "主体信用报告：主体名称")
	private String comChiName;
	@ApiModelProperty(value = "主体信用报告：评级机构")
	private String orgChiName;
	@ApiModelProperty(value = "主体信用报告：评级日期")
	private Date rateWritDate;
	@ApiModelProperty(value = "主体信用报告：评级结果")
	private String issCredLevel;
	@ApiModelProperty(value = "主体信用报告：评级结果数值")
	private int issCredLevelPar;
	@ApiModelProperty(value = "主体信用报告：评级变化")
	private String pdDiff;
	@ApiModelProperty(value = "主体信用报告：评级展望")
	private String parName;
	@ApiModelProperty(value = "主体信用报告：是否有负面展望点(1-有,0-没有)")
	private boolean hasDisadvt;
	@ApiModelProperty(value = "主体信用报告：负面展望点")
	private String cceDisadvt;

	public String getOrgChiName() {
		return orgChiName;
	}

	public void setOrgChiName(String orgChiName) {
		this.orgChiName = orgChiName;
	}

	public Date getRateWritDate() {
		return rateWritDate;
	}

	public void setRateWritDate(Date rateWritDate) {
		this.rateWritDate = rateWritDate;
	}

	public String getIssCredLevel() {
		return issCredLevel;
	}

	public void setIssCredLevel(String issCredLevel) {
		this.issCredLevel = issCredLevel;
	}

	public int getIssCredLevelPar() {
		return issCredLevelPar;
	}

	public void setIssCredLevelPar(int issCredLevelPar) {
		this.issCredLevelPar = issCredLevelPar;
	}

	public String getCceDisadvt() {
		return cceDisadvt;
	}

	public void setCceDisadvt(String cceDisadvt) {
		this.cceDisadvt = cceDisadvt;
	}

	public String getParName() {
		return parName;
	}

	public void setParName(String parName) {
		this.parName = parName;
	}

	public boolean isHasDisadvt() {
		return hasDisadvt;
	}

	public void setHasDisadvt(boolean hasDisadvt) {
		this.hasDisadvt = hasDisadvt;
	}

	public String getComChiName() {
		return comChiName;
	}

	public void setComChiName(String comChiName) {
		this.comChiName = comChiName;
	}

	public String getPdDiff() {
		return pdDiff;
	}

	public void setPdDiff(String pdDiff) {
		this.pdDiff = pdDiff;
	}
	
}
