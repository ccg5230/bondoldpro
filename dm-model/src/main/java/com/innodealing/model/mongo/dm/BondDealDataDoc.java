package com.innodealing.model.mongo.dm;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author stephen.ma
 * @date 2016年9月29日
 * @clasename BondDealDataDoc.java
 * @decription TODO
 */
@JsonInclude(Include.NON_NULL)
@Document(collection="bond_deal_data") 
public class BondDealDataDoc implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long dataId;

	/** 债券代码 */
	@Indexed
    private Long bondUniCode;
	
	/** 债券简称 */
	private String bondName;

	/** 最新收益率 */
	private BigDecimal bondRate;

	/** 涨跌 */
	private BigDecimal bondBp;

	/** 加权收益率 */
	private BigDecimal bondAddRate;

	/** 交易量(亿) */
	private BigDecimal bondTradingVolume;

	/** 创建时间 */
	@ApiModelProperty(value = "时间，格式：yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Indexed
	private String createTime;

	/** 修改时间 */
	private Date updateTime;

	public Long getDataId() {
		return dataId;
	}

	public void setDataId(Long dataId) {
		this.dataId = dataId;
	}

	public Long getBondUniCode() {
		return bondUniCode;
	}

	public void setBondUniCode(Long bondUniCode) {
		this.bondUniCode = bondUniCode;
	}

	public String getBondName() {
		return bondName;
	}

	public void setBondName(String bondName) {
		this.bondName = bondName;
	}

	public BigDecimal getBondRate() {
		return bondRate;
	}

	public void setBondRate(BigDecimal bondRate) {
		this.bondRate = bondRate;
	}

	public BigDecimal getBondBp() {
		return bondBp;
	}

	public void setBondBp(BigDecimal bondBp) {
		this.bondBp = bondBp;
	}

	public BigDecimal getBondAddRate() {
		return bondAddRate;
	}

	public void setBondAddRate(BigDecimal bondAddRate) {
		this.bondAddRate = bondAddRate;
	}

	public BigDecimal getBondTradingVolume() {
		return bondTradingVolume;
	}

	public void setBondTradingVolume(BigDecimal bondTradingVolume) {
		this.bondTradingVolume = bondTradingVolume;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
}
