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
 * 评分模板与内部评级对应关系
 * @author liuqi
 *
 */
@ApiModel
@JsonInclude(Include.NON_NULL)
@Table(name = "t_bond_inst_codetmpl_mapping")
public class BondInstCodetmplMapping extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private Long id;
	@ApiModelProperty(value = "内部评级ID")
	@Column(name = "inst_code_id")
	private Long instCodeId;
	@ApiModelProperty(value = "用户所属机构ID")
	@Column(name = "org_id")
	private Long orgId;
	@ApiModelProperty(value = "行业ID,0代表通用")
	@Column(name = "indu_id")
	private Long induId;
	@ApiModelProperty(value = "行业类型，1=GISC行业，2=申万行业，3=自定义行业")
	@Column(name = "indu_type")
	private Integer induType;
	@ApiModelProperty(value = "最大值计算符(1,大于等于 2,大于)")
	@Column(name = "maxcalc_type")
	private Integer maxcalcType;
	@ApiModelProperty(value = "最高得分")
	@Column(name = "maxscore")
	private BigDecimal maxscore;
	@ApiModelProperty(value = "最小值计算符(1,大于等于 2,大于)")
	@Column(name = "mincalc_type")
	private Integer mincalcType;
	@ApiModelProperty(value = "最低得分")
	@Column(name = "minscore")
	private BigDecimal minscore;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getInstCodeId() {
		return instCodeId;
	}

	public void setInstCodeId(Long instCodeId) {
		this.instCodeId = instCodeId;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Long getInduId() {
		return induId;
	}

	public void setInduId(Long induId) {
		this.induId = induId;
	}

	public Integer getInduType() {
		return induType;
	}

	public void setInduType(Integer induType) {
		this.induType = induType;
	}

	public Integer getMaxcalcType() {
		return maxcalcType;
	}

	public void setMaxcalcType(Integer maxcalcType) {
		this.maxcalcType = maxcalcType;
	}

	public BigDecimal getMaxscore() {
		return maxscore;
	}

	public void setMaxscore(BigDecimal maxscore) {
		this.maxscore = maxscore;
	}

	public Integer getMincalcType() {
		return mincalcType;
	}

	public void setMincalcType(Integer mincalcType) {
		this.mincalcType = mincalcType;
	}

	public BigDecimal getMinscore() {
		return minscore;
	}

	public void setMinscore(BigDecimal minscore) {
		this.minscore = minscore;
	}

}
