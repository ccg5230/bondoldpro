package com.innodealing.model.dm.bond;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
@Entity
@Table(name = "t_bond_custom_area")
public class BondCustomArea implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@ApiModelProperty(value = "用户ID")
	@Column(name = "user_id")
	private BigInteger userId;

	@ApiModelProperty(value = "区域ID")
	@Column(name = "area_uni_code")
	private String areaUniCode;

	@ApiModelProperty(value = "年份")
	@Column(name = "bond_year")
	private BigInteger bondYear;

	@ApiModelProperty(value = "季度")
	@Column(name = "bond_quarter")
	private BigInteger bondQuarter;

	@Column(name = "update_by")
	@ApiModelProperty(value = "修改人id")
	private BigInteger updateBy;

	@Column(length = 19, name = "update_time")
	@ApiModelProperty(value = "修改时间")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date updateTime;

	public String getAreaUniCode() {
		return areaUniCode;
	}

	public void setAreaUniCode(String areaUniCode) {
		this.areaUniCode = areaUniCode;
	}

	public BigInteger getUserId() {
		return userId;
	}

	public void setUserId(BigInteger userId) {
		this.userId = userId;
	}

	public BigInteger getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(BigInteger updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public BigInteger getBondYear() {
		return bondYear;
	}

	public void setBondYear(BigInteger bondYear) {
		this.bondYear = bondYear;
	}

	public BigInteger getBondQuarter() {
		return bondQuarter;
	}

	public void setBondQuarter(BigInteger bondQuarter) {
		this.bondQuarter = bondQuarter;
	}

	public BondCustomArea(String areaUniCode, BigInteger userId, BigInteger bondYear,
			BigInteger bondQuarter, BigInteger updateBy) {
		this.areaUniCode = areaUniCode;
		this.userId = userId;
		this.bondYear = bondYear;
		this.bondQuarter = bondQuarter;
		this.updateBy = updateBy;
	}

	public BondCustomArea() {}
	
	
	
}
