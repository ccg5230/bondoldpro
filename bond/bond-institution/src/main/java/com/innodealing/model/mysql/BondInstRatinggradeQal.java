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
 * 定性打分项规则
 * @author liuqi
 *
 */
@ApiModel
@JsonInclude(Include.NON_NULL)
@Table(name = "t_bond_inst_ratinggrade_qal")
public class BondInstRatinggradeQal extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	@ApiModelProperty(value = "评分模板（打分项）表主键ID")
	@Column(name = "tmpl_id")
	private Long tmplId;

	@ApiModelProperty(value = "最高得分")
	@Column(name = "maxscore")
	private BigDecimal maxscore;

	@ApiModelProperty(value = "最低得分")
	@Column(name = "minscore")
	private BigDecimal minscore;
	
	@ApiModelProperty(value = "对应规则描述")
	@Column(name = "description")
	private String description;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTmplId() {
		return tmplId;
	}

	public void setTmplId(Long tmplId) {
		this.tmplId = tmplId;
	}

	public BigDecimal getMaxscore() {
		return maxscore;
	}

	public void setMaxscore(BigDecimal maxscore) {
		this.maxscore = maxscore;
	}

	public BigDecimal getMinscore() {
		return minscore;
	}

	public void setMinscore(BigDecimal minscore) {
		this.minscore = minscore;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	

}
