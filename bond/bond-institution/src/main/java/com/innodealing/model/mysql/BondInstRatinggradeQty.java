package com.innodealing.model.mysql;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 定量打分项计算公式
 * @author liuqi
 *
 */
@ApiModel
@JsonInclude(Include.NON_NULL)
@Table(name = "t_bond_inst_ratinggrade_qty")
public class BondInstRatinggradeQty extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	@ApiModelProperty(value = "评分模板（打分项）表主键ID")
	@Column(name = "tmpl_id")
	private Long tmplId;
	
	@ApiModelProperty(value = "定量表达式")
	@Column(name = "exps")
	private String exps;

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

	public String getExps() {
		return exps;
	}

	public void setExps(String exps) {
		this.exps = exps;
	}

	

	

}
