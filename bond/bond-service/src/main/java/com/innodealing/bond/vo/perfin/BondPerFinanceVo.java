package com.innodealing.bond.vo.perfin;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author stephen.ma
 * @date 2017年3月20日
 * @clasename BondPerFinanceVo.java
 * @decription TODO
 */
public class BondPerFinanceVo {

	@ApiModelProperty(value = "任务ID")
    private Long taskId;
	
	@ApiModelProperty(value = "季度显示")
    private String quarter;
	
	@ApiModelProperty(value = "用户账号ID")
    private Integer userid;
	
	@ApiModelProperty(value = "财报发行人名称")
    private String finUser;
	
	@ApiModelProperty(value = "GICS四级分类说明")
    private String compCls;	
	
	@ApiModelProperty(value = "债券table中的ID")
    private String rating;
	
	@ApiModelProperty(value = "0：已上传财报，等待评分；1：评分完成")
    private Integer status;
	
	@ApiModelProperty(value = "评分时间")
    private String rateTime;

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public String getQuarter() {
		return quarter;
	}

	public void setQuarter(String quarter) {
		this.quarter = quarter;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getFinUser() {
		return finUser;
	}

	public void setFinUser(String finUser) {
		this.finUser = finUser;
	}

	public String getCompCls() {
		return compCls;
	}

	public void setCompCls(String compCls) {
		this.compCls = compCls;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRateTime() {
		return rateTime;
	}

	public void setRateTime(String rateTime) {
		this.rateTime = rateTime;
	}
	
}
