package com.innodealing.model.dm.bond;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;

@Entity
public class BondCredChan implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	private String id;
	
	@ApiModelProperty("发行人简称")
	private String comChiName;

	@ApiModelProperty("评级机构简称")
	private String orgChiName;

	@ApiModelProperty("评级日期")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date rateWritDate;

	@ApiModelProperty("评级观点")
	private String ratePoint;

	@ApiModelProperty("劣势")
	private String cceDisadvt;
	
	@ApiModelProperty("优势")
	private String cceAdvt;
	
	@ApiModelProperty("关注点")
	private String attPoint;
	
	public String getAttPoint() {
		return attPoint;
	}

	public void setAttPoint(String attPoint) {
		this.attPoint = attPoint;
	}

	public String getComChiName() {
		return comChiName;
	}

	public void setComChiName(String comChiName) {
		this.comChiName = comChiName;
	}

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

	public String getRatePoint() {
		return ratePoint;
	}

	public void setRatePoint(String ratePoint) {
		this.ratePoint = ratePoint;
	}

	public String getCceDisadvt() {
		return cceDisadvt;
	}

	public void setCceDisadvt(String cceDisadvt) {
		this.cceDisadvt = cceDisadvt;
	}

	public String getCceAdvt() {
		return cceAdvt;
	}

	public void setCceAdvt(String cceAdvt) {
		this.cceAdvt = cceAdvt;
	}


	

	
}
