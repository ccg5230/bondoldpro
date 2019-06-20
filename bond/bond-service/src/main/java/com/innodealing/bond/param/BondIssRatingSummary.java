package com.innodealing.bond.param;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author xiaochao
 * @date 2017年2月10日
 * @decription 评级概要信息
 */
@ApiModel
@JsonInclude(Include.NON_NULL)
public class BondIssRatingSummary {
	@ApiModelProperty(value = "评级机构")
	private String chiShortName;

	@ApiModelProperty(value = "评级日期")
	@JsonFormat(pattern = "yyyy/MM/dd", timezone = "Asia/Shanghai")
	private Date rateWritDate;

	@ApiModelProperty(value = "评级结果")
	private String issCredLevel;

	@ApiModelProperty(value = "评级结果等级数值")
	private Integer issCredLevelPar;

	@ApiModelProperty(value = "展望负面点")
	private String cceDisadvt;
	
	@ApiModelProperty(value = "评级展望")
	private String parName;
	
	@ApiModelProperty(value = "评级展望->评级日期")
	@JsonFormat(pattern = "yyyy/MM/dd", timezone = "Asia/Shanghai")
	private Date comRateProsParDate;
	
	@ApiModelProperty(value = "评级展望->评级机构")
	private String comRateProsParOrga;

	public String getChiShortName() {
		return chiShortName;
	}

	public void setChiShortName(String chiShortName) {
		this.chiShortName = chiShortName;
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

	public String getParName() {
		return parName;
	}

	public void setParName(String parName) {
		this.parName = parName;
	}

	public String getCceDisadvt() {
		return cceDisadvt;
	}

	public void setCceDisadvt(String cceDisadvt) {
		this.cceDisadvt = cceDisadvt;
	}

	public Integer getIssCredLevelPar() {
		return issCredLevelPar;
	}

	public void setIssCredLevelPar(Integer issCredLevelPar) {
		this.issCredLevelPar = issCredLevelPar;
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
	
}
