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
@Table(name = "t_bond_favorite_fina_index")
@NamedQuery(name = "BondFavoriteFinaIndex.findAll", query = "SELECT t FROM BondFavoriteFinaIndex t")
@JsonInclude(Include.NON_NULL)
public class BondFavoriteFinaIndex implements Serializable {
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

	@ApiModelProperty(value = "财务指标子类型：1-市场指标;2-专项指标;3-资产负债;4-利润;5-现金流量;")
	@Column(name = "fina_sub_type", length = 3)
	private Integer finaSubType;

	@ApiModelProperty(value = "指标类型：1-固有指标；2-组合指标")
	@Column(name = "index_type", nullable = false, length = 3)
	private Integer indexType;

	@ApiModelProperty(value = "指标代码表达式")
	@Column(name = "index_code_expr", nullable = false, length = 128)
	private String indexCodeExpr;

	@ApiModelProperty(value = "指标名称")
	@Column(name = "index_name", nullable = false, length = 32)
	private String indexName;

	@ApiModelProperty(value = "数值类型：1-指标自身；2-同比；3-行业排名")
	@Column(name = "index_value_type", nullable = false, length = 3)
	private Integer indexValueType;

	@ApiModelProperty(value = "条件关系: 1-gteA; 2-lteA; 3-A和B的闭区间; 4-lteA或者gteB; 5-全部; 6-前A; 7-后A;")
	@Column(name = "index_value_nexus", nullable = false, length = 3)
	private Integer indexValueNexus;

	@ApiModelProperty(value = "数值单位：1-%；2-万元")
	@Column(name = "index_value_unit", nullable = false, length = 3)
	private Integer indexValueUnit;

	@ApiModelProperty(value = "数值下限")
	@Column(name = "index_value_low", length = 10)
	private BigDecimal indexValueLow;

	@ApiModelProperty(value = "数值上限")
	@Column(name = "index_value_high", length = 10)
	private BigDecimal indexValueHigh;

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
	 * @param indexType
	 *            the indexType to set
	 */
	public void setIndexType(Integer indexType) {
		this.indexType = indexType;
	}

	/**
	 * @return the indexType
	 */
	public Integer getIndexType() {
		return this.indexType;
	}

	/**
	 * @param indexCodeExpr
	 *            the indexCodeExpr to set
	 */
	public void setIndexCodeExpr(String indexCodeExpr) {
		this.indexCodeExpr = indexCodeExpr;
	}

	/**
	 * @return the indexCodeExpr
	 */
	public String getIndexCodeExpr() {
		return this.indexCodeExpr;
	}

	/**
	 * @param indexName
	 *            the indexName to set
	 */
	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	/**
	 * @return the indexName
	 */
	public String getIndexName() {
		return this.indexName;
	}

	/**
	 * @param indexValueType
	 *            the indexValueType to set
	 */
	public void setIndexValueType(Integer indexValueType) {
		this.indexValueType = indexValueType;
	}

	/**
	 * @return the indexValueType
	 */
	public Integer getIndexValueType() {
		return this.indexValueType;
	}

	/**
	 * @param indexValueUnit
	 *            the indexValueUnit to set
	 */
	public void setIndexValueUnit(Integer indexValueUnit) {
		this.indexValueUnit = indexValueUnit;
	}

	/**
	 * @return the indexValueUnit
	 */
	public Integer getIndexValueUnit() {
		return this.indexValueUnit;
	}

	/**
	 * @param indexValueLow
	 *            the indexValueLow to set
	 */
	public void setIndexValueLow(BigDecimal indexValueLow) {
		this.indexValueLow = indexValueLow;
	}

	/**
	 * @return the indexValueLow
	 */
	public BigDecimal getIndexValueLow() {
		return this.indexValueLow;
	}

	/**
	 * @param indexValueHigh
	 *            the indexValueHigh to set
	 */
	public void setIndexValueHigh(BigDecimal indexValueHigh) {
		this.indexValueHigh = indexValueHigh;
	}

	/**
	 * @return the indexValueHigh
	 */
	public BigDecimal getIndexValueHigh() {
		return this.indexValueHigh;
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

	public Integer getIndexValueNexus() {
		return indexValueNexus;
	}

	public void setIndexValueNexus(Integer indexValueNexus) {
		this.indexValueNexus = indexValueNexus;
	}

	public Integer getFinaSubType() {
		return finaSubType;
	}

	public void setFinaSubType(Integer finaSubType) {
		this.finaSubType = finaSubType;
	}
}
