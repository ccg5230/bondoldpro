package com.innodealing.model.mongo.dm;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author lihao
 * @date 2016年9月11日
 * @ClassName BondYieldCurve
 * @Description: 曲线和曲线对比结果集
 */
@JsonInclude(value=Include.NON_NULL)
@Document(collection="t_bond_yield_curve")
public class BondYieldCurve implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(name="债券编号")
	private Integer curveId;
	
	@ApiModelProperty(name="债券名称")
	private String name="";
	
	@ApiModelProperty(name="债券日期")
	private String date="";
	
	@ApiModelProperty(name="3月")
	private String threem="";
	
	@ApiModelProperty(name="6月")
	private String sixm="";
	
	@ApiModelProperty(name="9月")
	private String ninem="";
	
	@ApiModelProperty(name="1年")
	private String oney="";
	
	@ApiModelProperty(name="3年")
	private String threey="";
	
	@ApiModelProperty(name="5年")
	private String fivey="";
	
	@ApiModelProperty(name="7年")
	private String seveny="";
	
	@ApiModelProperty(name="10年")
	private String teny="";
	
	@ApiModelProperty(name="状态")
	private String status;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getThreem() {
		return threem;
	}
	public void setThreem(String threem) {
		this.threem = threem;
	}
	public String getSixm() {
		return sixm;
	}
	public void setSixm(String sixm) {
		this.sixm = sixm;
	}
	public String getNinem() {
		return ninem;
	}
	public void setNinem(String ninem) {
		this.ninem = ninem;
	}
	public String getOney() {
		return oney;
	}
	public void setOney(String oney) {
		this.oney = oney;
	}
	public String getThreey() {
		return threey;
	}
	public void setThreey(String threey) {
		this.threey = threey;
	}
	public String getFivey() {
		return fivey;
	}
	public void setFivey(String fivey) {
		this.fivey = fivey;
	}
	public String getSeveny() {
		return seveny;
	}
	public void setSeveny(String seveny) {
		this.seveny = seveny;
	}
	public String getTeny() {
		return teny;
	}
	public void setTeny(String teny) {
		this.teny = teny;
	}
	public Integer getCurveId() {
		return curveId;
	}
	public void setCurveId(Integer curveId) {
		this.curveId = curveId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
