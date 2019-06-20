package com.innodealing.model.dm.bond;

import java.math.BigDecimal;
import java.util.Date;

public class BondSpotRate {

    private Integer id;

    private String curveCode;

    private Date createTime;
    
    private  BigDecimal byd_0y;
    private  BigDecimal byd_7d;
    private  BigDecimal byd_14d;
    private  BigDecimal byd_1m;
    private BigDecimal byd_3m;
    private BigDecimal byd_6m;
    private BigDecimal byd_9m;
    private BigDecimal byd_1y;
    private BigDecimal byd_2y;
    private BigDecimal byd_3y;
    private BigDecimal byd_4y;
    private BigDecimal byd_5y;
    private BigDecimal byd_6y;
    private BigDecimal byd_7y;
    private BigDecimal byd_8y;
    private BigDecimal byd_9y;
    private BigDecimal byd_10y;
    private BigDecimal byd_15y;
    private BigDecimal byd_20y;
    private BigDecimal byd_30y;
    private BigDecimal byd_40y;
    private BigDecimal byd_50y;

    
    
    public BigDecimal getByd_0y() {
		return byd_0y;
	}

	public void setByd_0y(BigDecimal byd_0y) {
		this.byd_0y = byd_0y;
	}

	public BigDecimal getByd_7d() {
		return byd_7d;
	}

	public void setByd_7d(BigDecimal byd_7d) {
		this.byd_7d = byd_7d;
	}

	public BigDecimal getByd_14d() {
		return byd_14d;
	}

	public void setByd_14d(BigDecimal byd_14d) {
		this.byd_14d = byd_14d;
	}

	public BigDecimal getByd_1m() {
		return byd_1m;
	}

	public void setByd_1m(BigDecimal byd_1m) {
		this.byd_1m = byd_1m;
	}

	public BigDecimal getByd_2y() {
		return byd_2y;
	}

	public void setByd_2y(BigDecimal byd_2y) {
		this.byd_2y = byd_2y;
	}

	public BigDecimal getByd_4y() {
		return byd_4y;
	}

	public void setByd_4y(BigDecimal byd_4y) {
		this.byd_4y = byd_4y;
	}

	public BigDecimal getByd_6y() {
		return byd_6y;
	}

	public void setByd_6y(BigDecimal byd_6y) {
		this.byd_6y = byd_6y;
	}

	public BigDecimal getByd_8y() {
		return byd_8y;
	}

	public void setByd_8y(BigDecimal byd_8y) {
		this.byd_8y = byd_8y;
	}

	public BigDecimal getByd_9y() {
		return byd_9y;
	}

	public void setByd_9y(BigDecimal byd_9y) {
		this.byd_9y = byd_9y;
	}

	public BigDecimal getByd_40y() {
		return byd_40y;
	}

	public void setByd_40y(BigDecimal byd_40y) {
		this.byd_40y = byd_40y;
	}

	private Integer userid;

    private Integer isShow;

    private Date curveTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCurveCode() {
		return curveCode;
	}

	public void setCurveCode(String curveCode) {
		this.curveCode = curveCode;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public BigDecimal getByd_3m() {
		return byd_3m;
	}

	public void setByd_3m(BigDecimal byd_3m) {
		this.byd_3m = byd_3m;
	}

	public BigDecimal getByd_6m() {
		return byd_6m;
	}

	public void setByd_6m(BigDecimal byd_6m) {
		this.byd_6m = byd_6m;
	}

	public BigDecimal getByd_9m() {
		return byd_9m;
	}

	public void setByd_9m(BigDecimal byd_9m) {
		this.byd_9m = byd_9m;
	}


	public BigDecimal getByd_1y() {
		return byd_1y;
	}

	public void setByd_1y(BigDecimal byd_1y) {
		this.byd_1y = byd_1y;
	}

	public BigDecimal getByd_3y() {
		return byd_3y;
	}

	public void setByd_3y(BigDecimal byd_3y) {
		this.byd_3y = byd_3y;
	}

	public BigDecimal getByd_5y() {
		return byd_5y;
	}

	public void setByd_5y(BigDecimal byd_5y) {
		this.byd_5y = byd_5y;
	}

	public BigDecimal getByd_7y() {
		return byd_7y;
	}

	public void setByd_7y(BigDecimal byd_7y) {
		this.byd_7y = byd_7y;
	}

	public BigDecimal getByd_10y() {
		return byd_10y;
	}

	public void setByd_10y(BigDecimal byd_10y) {
		this.byd_10y = byd_10y;
	}

	public BigDecimal getByd_15y() {
		return byd_15y;
	}

	public void setByd_15y(BigDecimal byd_15y) {
		this.byd_15y = byd_15y;
	}

	public BigDecimal getByd_20y() {
		return byd_20y;
	}

	public void setByd_20y(BigDecimal byd_20y) {
		this.byd_20y = byd_20y;
	}

	public BigDecimal getByd_30y() {
		return byd_30y;
	}

	public void setByd_30y(BigDecimal byd_30y) {
		this.byd_30y = byd_30y;
	}

	public BigDecimal getByd_50y() {
		return byd_50y;
	}

	public void setByd_50y(BigDecimal byd_50y) {
		this.byd_50y = byd_50y;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public Integer getIsShow() {
		return isShow;
	}

	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}

	public Date getCurveTime() {
		return curveTime;
	}

	public void setCurveTime(Date curveTime) {
		this.curveTime = curveTime;
	}

	@Override
	public String toString() {
		return "BondSpotRate [id=" + id + ", curveCode=" + curveCode + ", createTime=" + createTime + ", byd_3m="
				+ byd_3m + ", byd_6m=" + byd_6m + ", byd_9m=" + byd_9m + ", byd_1y=" + byd_1y + ", byd_3y=" + byd_3y
				+ ", byd_5y=" + byd_5y + ", byd_7y=" + byd_7y + ", byd_10y=" + byd_10y + ", byd_15y=" + byd_15y
				+ ", byd_20y=" + byd_20y + ", byd_30y=" + byd_30y + ", byd_50y=" + byd_50y + ", userid=" + userid
				+ ", isShow=" + isShow + ", curveTime=" + curveTime + "]";
	}



  

}
