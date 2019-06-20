package com.innodealing.model.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.innodealing.util.Column;

import io.swagger.annotations.ApiModelProperty;

public class BondPubList {

	
	@ApiModelProperty(value = "债券Code")
	@Column(name="bond_uni_code")
	private int bondUniCode;
	
	@ApiModelProperty(value = "发行人名称")
	@Column(name="com_chi_name")
	private String comChiName;
	
	
	@ApiModelProperty(value = "发行人Code")
	@Column(name="com_uni_code")
	private int comUniCode;
	
	@ApiModelProperty(value = "关联备注")
	private String remark;
	
	@ApiModelProperty(value = "版本号")
	private String version;
	
	// 信凭人员ID
	@JsonIgnore
	@Column(name="rating_by")
	private int ratingBy;

	@ApiModelProperty(value = "信凭人员名称")
	private String ratingByName;



	@ApiModelProperty(value = "信评分组")
	@Column(name="group_name")
	private String groupName;
	@ApiModelProperty(value = "最近内部评级")
	@Column(name="rating_name")
	private String ratingName;
	

	
	@ApiModelProperty(value = "最近内部评级ID")
	@Column(name="rating_id")
	private String ratingId;

	@ApiModelProperty(value = "最近评级时间")
	@Column(name="rating_time")
	private Date ratingTime;

	@ApiModelProperty(value = "所属行业")
	@Column(name="indu_uni_code")
	private int induUniCode;

	@ApiModelProperty(value = "所属行业名称")
	@Column(name="indu_uni_name")
	private String induUniName;



	@ApiModelProperty(value = "父级ID")
	@Column(name="fat_id")
	private int fatId;
	
	
	public int getBondUniCode() {
		return bondUniCode;
	}
	
	
	public String getComChiName() {
		return comChiName;
	}

	public int getComUniCode() {
		return comUniCode;
	}



	public int getFatId() {
		return fatId;
	}
	
	
	public String getGroupName() {
		return groupName;
	}
	
	public int getInduUniCode() {
		return induUniCode;
	}
	
	public String getInduUniName() {
		return induUniName;
	}

	public int getRatingBy() {
		return ratingBy;
	}
	
	public String getRatingByName() {
		return ratingByName;
	}

	public String getRatingId() {
		return ratingId;
	}

	public String getRatingName() {
		return ratingName;
	}

	public Date getRatingTime() {
		return ratingTime;
	}

	public String getRemark() {
		return remark;
	}

	public String getVersion() {
		return version;
	}

	public void setBondUniCode(int bondUniCode) {
		this.bondUniCode = bondUniCode;
	}

	public void setComChiName(String comChiName) {
		this.comChiName = comChiName;
	}

	public void setComUniCode(int comUniCode) {
		this.comUniCode = comUniCode;
	}

	public void setFatId(int fatId) {
		this.fatId = fatId;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public void setInduUniCode(int induUniCode) {
		this.induUniCode = induUniCode;
	}

	public void setInduUniName(String induUniName) {
		this.induUniName = induUniName;
	}

	public void setRatingBy(int ratingBy) {
		this.ratingBy = ratingBy;
	}

	public void setRatingByName(String ratingByName) {
		this.ratingByName = ratingByName;
	}

	public void setRatingId(String ratingId) {
		this.ratingId = ratingId;
	}

	public void setRatingName(String ratingName) {
		this.ratingName = ratingName;
	}

	public void setRatingTime(Date ratingTime) {
		this.ratingTime = ratingTime;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return "BondPubList [bondUniCode=" + bondUniCode + ", comChiName=" + comChiName + ", comUniCode=" + comUniCode
				+ ", remark=" + remark + ", groupName=" + groupName + ", ratingName=" + ratingName + ", ratingTime="
				+ ratingTime + ", induUniCode=" + induUniCode + ", induUniName=" + induUniName + "]";
	}

}
