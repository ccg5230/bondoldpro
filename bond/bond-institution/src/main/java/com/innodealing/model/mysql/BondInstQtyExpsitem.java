package com.innodealing.model.mysql;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 打分项单个指标
 * @author liuqi
 *
 */
@ApiModel
@JsonInclude(Include.NON_NULL)
@Table(name = "t_bond_inst_qty_expsitem")
public class BondInstQtyExpsitem extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private Long id;
	@ApiModelProperty(value = "定量打分项表主键ID")
	@Column(name = "qty_id")
	private Long qtyId;

	@ApiModelProperty(value = "财报时间类型(1,当前最新2,去年同期,3前年同期)")
	@Column(name = "finarpt_type")
	private Integer finarptType;

	@ApiModelProperty(value = "指标代码")
	@Column(name = "indicator_code")
	private String indicatorCode;

	@ApiModelProperty(value = "指标简称")
	@Column(name = "indicator_name")
	private String indicatorName;

	@ApiModelProperty(value = "真实财报时间(信评流程审核通过后修改项)")
	@Column(name = "finarpt_time")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date finarptTime;

	@ApiModelProperty(value = "真实财报具体数据值(信评流程审核通过后修改项)")
	@Column(name = "finarpt_value")
	private BigDecimal finarptValue;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getQtyId() {
		return qtyId;
	}

	public void setQtyId(Long qtyId) {
		this.qtyId = qtyId;
	}

	public Integer getFinarptType() {
		return finarptType;
	}

	public void setFinarptType(Integer finarptType) {
		this.finarptType = finarptType;
	}

	public String getIndicatorCode() {
		return indicatorCode;
	}

	public void setIndicatorCode(String indicatorCode) {
		this.indicatorCode = indicatorCode;
	}

	public String getIndicatorName() {
		return indicatorName;
	}

	public void setIndicatorName(String indicatorName) {
		this.indicatorName = indicatorName;
	}

	public Date getFinarptTime() {
		return finarptTime;
	}

	public void setFinarptTime(Date finarptTime) {
		this.finarptTime = finarptTime;
	}

	public BigDecimal getFinarptValue() {
		return finarptValue;
	}

	public void setFinarptValue(BigDecimal finarptValue) {
		this.finarptValue = finarptValue;
	}

}
