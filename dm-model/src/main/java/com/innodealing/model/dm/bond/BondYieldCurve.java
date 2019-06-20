package com.innodealing.model.dm.bond;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
* @ClassName: NdbondYieldCurve
* @author lihao
* @date 2016年9月8日 下午2:34:56
* @Description: 曲线信息表
 */
@Entity
@Table(name="t_bond_yield_curve")
public class BondYieldCurve implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(nullable=false, length=19,name="create_time")
    private Date createTime;
    
    @Column(length=10,name="ytm_3m")
    private BigDecimal ytm3m;
    
    @Column(length=10,name="ytm_6m")
    private BigDecimal ytm6m;
    
    @Column(length=10,name="ytm_9m")
    private BigDecimal ytm9m;
    
    @Column(length=10,name="ytm_1y")
    private BigDecimal ytm1y;
    
    @Column(length=10,name="ytm_3y")
    private BigDecimal ytm3y;
    
    @Column(length=10,name="ytm_5y")
    private BigDecimal ytm5y;
    
    @Column(length=10,name="ytm_7y")
    private BigDecimal ytm7y;
    
    @Column(length=10,name="ytm_10y")
    private BigDecimal ytm10y;
    
    @Column(length=10,name="ytm_15y")
    private BigDecimal ytm15y;
    
    @Column(length=10,name="ytm_20y")
    private BigDecimal ytm20y;
    
    @Column(length=10,name="ytm_30y")
    private BigDecimal ytm30y;
    
    @Column(length=10,name="ytm_50y")
    private BigDecimal ytm50y;
    
    @Column(name="curve_code")
    private Integer curvecode;
    
    @Column(name="userid")
    private Integer userid;
    
    @Column(name="is_show")
    private Integer isshow;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public BigDecimal getYtm3m() {
		return ytm3m;
	}

	public void setYtm3m(BigDecimal ytm3m) {
		this.ytm3m = ytm3m;
	}

	public BigDecimal getYtm6m() {
		return ytm6m;
	}

	public void setYtm6m(BigDecimal ytm6m) {
		this.ytm6m = ytm6m;
	}

	public BigDecimal getYtm9m() {
		return ytm9m;
	}

	public void setYtm9m(BigDecimal ytm9m) {
		this.ytm9m = ytm9m;
	}

	public BigDecimal getYtm1y() {
		return ytm1y;
	}

	public void setYtm1y(BigDecimal ytm1y) {
		this.ytm1y = ytm1y;
	}

	public BigDecimal getYtm3y() {
		return ytm3y;
	}

	public void setYtm3y(BigDecimal ytm3y) {
		this.ytm3y = ytm3y;
	}

	public BigDecimal getYtm5y() {
		return ytm5y;
	}

	public void setYtm5y(BigDecimal ytm5y) {
		this.ytm5y = ytm5y;
	}

	public BigDecimal getYtm7y() {
		return ytm7y;
	}

	public void setYtm7y(BigDecimal ytm7y) {
		this.ytm7y = ytm7y;
	}

	public BigDecimal getYtm10y() {
		return ytm10y;
	}

	public void setYtm10y(BigDecimal ytm10y) {
		this.ytm10y = ytm10y;
	}

	public BigDecimal getYtm15y() {
		return ytm15y;
	}

	public void setYtm15y(BigDecimal ytm15y) {
		this.ytm15y = ytm15y;
	}

	public BigDecimal getYtm20y() {
		return ytm20y;
	}

	public void setYtm20y(BigDecimal ytm20y) {
		this.ytm20y = ytm20y;
	}

	public BigDecimal getYtm30y() {
		return ytm30y;
	}

	public void setYtm30y(BigDecimal ytm30y) {
		this.ytm30y = ytm30y;
	}

	public BigDecimal getYtm50y() {
		return ytm50y;
	}

	public void setYtm50y(BigDecimal ytm50y) {
		this.ytm50y = ytm50y;
	}

	public Integer getCurvecode() {
		return curvecode;
	}

	public void setCurvecode(Integer curvecode) {
		this.curvecode = curvecode;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public Integer getIsshow() {
		return isshow;
	}

	public void setIsshow(Integer isshow) {
		this.isshow = isshow;
	}
}
