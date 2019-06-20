package com.innodealing.model.dm.bond;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "t_bond_duration")
public class BondDuration implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private BondDurationKey durationKey;

	@Column(name = "bond_short_name")
	private String bondShortName;

	@Column(name = "yield")
	private BigDecimal yield;

	@Column(name = "macd", scale=2)
	private BigDecimal macd;
	
	@Column(name = "modd", scale=2)
    private BigDecimal modd;
    
    @Column(name = "convexity", scale=2)
    private BigDecimal convexity;
    
    @Column(name = "create_time")
    private Date createTime;
	
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * @return the durationKey
     */
    public BondDurationKey getDurationKey() {
        return durationKey;
    }

    /**
     * @param durationKey the durationKey to set
     */
    public void setDurationKey(BondDurationKey durationKey) {
        this.durationKey = durationKey;
    }

    /**
     * @return the bondShortName
     */
    public String getBondShortName() {
        return bondShortName;
    }

    /**
     * @param bondShortName the bondShortName to set
     */
    public void setBondShortName(String bondShortName) {
        this.bondShortName = bondShortName;
    }

    /**
     * @return the yield
     */
    public BigDecimal getYield() {
        return yield;
    }

    /**
     * @param yield the yield to set
     */
    public void setYield(BigDecimal yield) {
        this.yield = yield;
    }

    /**
     * @return the macd
     */
    public BigDecimal getMacd() {
        return macd;
    }

    /**
     * @param macd the macd to set
     */
    public void setMacd(BigDecimal macd) {
        this.macd = macd;
    }

    /**
     * @return the modd
     */
    public BigDecimal getModd() {
        return modd;
    }

    /**
     * @param modd the modd to set
     */
    public void setModd(BigDecimal modd) {
        this.modd = modd;
    }

    /**
     * @return the createTime
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime the createTime to set
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return the updateTime
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime the updateTime to set
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public BigDecimal getConvexity() {
		return convexity;
	}

	public void setConvexity(BigDecimal convexity) {
		this.convexity = convexity;
	}

    @Override
	public String toString() {
		return "BondDuration [durationKey=" + durationKey + ", bondShortName=" + bondShortName + ", yield=" + yield
				+ ", macd=" + macd + ", modd=" + modd + ", convexity=" + convexity + ", createTime=" + createTime
				+ ", updateTime=" + updateTime + "]";
	}
}
