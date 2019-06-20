package com.innodealing.model.dm.bond;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
@Entity
@Table(name = "t_bond_area")
@JsonInclude(Include.NON_NULL)
public class BondArea implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	private BigInteger id;

	@ApiModelProperty(value = "省")
	@Column(name = "area_name1")
	private String areaName1;

	@ApiModelProperty(value = "地级市")
	@Column(name = "area_name2")
	private String areaName2;

	@ApiModelProperty(value = "区域ID")
	@Column(name = "area_uni_code")
	private BigInteger areaUniCode;
	
	@ApiModelProperty(value = "父ID")
	@Column(name = "sub_uni_code")
	private BigInteger subUniCode;
	
	@ApiModelProperty(value = "区域类型")
	@Column(name = "city_type")
	private String cityType;
	
	@Column(name = "update_by")
	@ApiModelProperty(value = "修改人id")
	private BigInteger updateBy;

	@Column(length = 19, name = "update_time")
	@ApiModelProperty(value = "修改时间")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date updateTime;

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getAreaName1() {
		return areaName1;
	}

	public void setAreaName1(String areaName1) {
		this.areaName1 = areaName1;
	}

	public String getAreaName2() {
		return areaName2;
	}

	public void setAreaName2(String areaName2) {
		this.areaName2 = areaName2;
	}

	public BigInteger getAreaUniCode() {
		return areaUniCode;
	}

	public void setAreaUniCode(BigInteger areaUniCode) {
		this.areaUniCode = areaUniCode;
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

	public BigInteger getSubUniCode() {
		return subUniCode;
	}

	public void setSubUniCode(BigInteger subUniCode) {
		this.subUniCode = subUniCode;
	}

	public String getCityType() {
		return cityType;
	}

	public void setCityType(String cityType) {
		this.cityType = cityType;
	}
	
	
	
}
