package com.innodealing.model.mysql;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 定量打分项得分规则
 * @author liuqi
 *
 */
@ApiModel
@JsonInclude(Include.NON_NULL)
@Table(name = "t_bond_inst_qty_expsrule")
public class BondInstQtyExpsrule extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	@ApiModelProperty(value = "定量打分项表主键ID")
	@Column(name = "qty_id")
	private Long qtyId;

	@ApiModelProperty(value = "计算结果(定量)")
	@Column(name = "calculate_result")
	private BigDecimal calculateResult;

	@ApiModelProperty(value = "对应得分(定量)")
	@Column(name = "score_mapping")
	private BigDecimal scoreMapping;

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

	public BigDecimal getCalculateResult() {
		return calculateResult;
	}

	public void setCalculateResult(BigDecimal calculateResult) {
		this.calculateResult = calculateResult;
	}

	public BigDecimal getScoreMapping() {
		return scoreMapping;
	}

	public void setScoreMapping(BigDecimal scoreMapping) {
		this.scoreMapping = scoreMapping;
	}

}
