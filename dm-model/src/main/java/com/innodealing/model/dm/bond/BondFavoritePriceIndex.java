package com.innodealing.model.dm.bond;

import java.util.Date;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@Entity
@Table(name = "t_bond_favorite_price_index")
@NamedQuery(name = "BondFavoritePriceIndex.findAll", query = "SELECT t FROM BondFavoritePriceIndex t")
@JsonInclude(Include.NON_NULL)
public class BondFavoritePriceIndex implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@ApiModelProperty(value = "t_bond_favorite_group的ID")
	@Column(name = "group_id", nullable = false, length = 19)
	private Long groupId = 0L;

	@ApiModelProperty(value = "t_bond_favorite的ID")
	@Column(name = "favorite_id", nullable = false, length = 19)
	private Long favoriteId = 0L;

	@ApiModelProperty(value = "价格指标：1-成交价； 2-卖出报价；3-买入报价；4-估值")
	@Column(name = "price_index", nullable = false, length = 3)
	private Integer priceIndex;

	@ApiModelProperty(value = "价格类型：1-收益率； 2-净价；3-估值偏离")
	@Column(name = "price_type", nullable = false, length = 3)
	private Integer priceType;

	@ApiModelProperty(value = "价格比较：1-大于； 2-小于")
	@Column(name = "price_condi", nullable = false, length = 3)
	private Integer priceCondi;

	@ApiModelProperty(value = "价格指标数值")
	@Column(name = "index_value", nullable = false, length = 10)
	private BigDecimal indexValue;

	@ApiModelProperty(value = "价格指标数值单位：1-%； 2-BP；3-元")
	@Column(name = "index_unit", nullable = false, length = 3)
	private Integer indexUnit;

	@ApiModelProperty(value = "当前状态：0-无效；1-有效")
	@Column(name = "status", nullable = false, length = 3)
	private Integer status;

	@ApiModelProperty(value = "创建时间")
	@Column(name = "create_time", nullable = false, length = 19)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @param favoriteId
	 *            the favoriteId to set
	 */
	public void setFavoriteId(Long favoriteId) {
		this.favoriteId = favoriteId;
	}

	/**
	 * @return the favoriteId
	 */
	public Long getFavoriteId() {
		return this.favoriteId;
	}

	/**
	 * @param priceIndex
	 *            the priceIndex to set
	 */
	public void setPriceIndex(Integer priceIndex) {
		this.priceIndex = priceIndex;
	}

	/**
	 * @return the priceIndex
	 */
	public Integer getPriceIndex() {
		return this.priceIndex;
	}

	/**
	 * @param priceType
	 *            the priceType to set
	 */
	public void setPriceType(Integer priceType) {
		this.priceType = priceType;
	}

	/**
	 * @return the priceType
	 */
	public Integer getPriceType() {
		return this.priceType;
	}

	/**
	 * @param priceCondi
	 *            the priceCondi to set
	 */
	public void setPriceCondi(Integer priceCondi) {
		this.priceCondi = priceCondi;
	}

	/**
	 * @return the priceCondi
	 */
	public Integer getPriceCondi() {
		return this.priceCondi;
	}

	/**
	 * @param indexValue
	 *            the indexValue to set
	 */
	public void setIndexValue(BigDecimal indexValue) {
		this.indexValue = indexValue;
	}

	/**
	 * @return the indexValue
	 */
	public BigDecimal getIndexValue() {
		return this.indexValue;
	}

	/**
	 * @param indexUnit
	 *            the indexUnit to set
	 */
	public void setIndexUnit(Integer indexUnit) {
		this.indexUnit = indexUnit;
	}

	/**
	 * @return the indexUnit
	 */
	public Integer getIndexUnit() {
		return this.indexUnit;
	}

	/**
	 * @param createTime
	 *            the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return this.createTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
}
