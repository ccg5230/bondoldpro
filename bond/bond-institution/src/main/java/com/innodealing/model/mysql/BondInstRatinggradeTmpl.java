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
 * 打分项
 * @author liuqi
 *
 */
@ApiModel
@JsonInclude(Include.NON_NULL)
@Table(name = "t_bond_inst_ratinggrade_tmpl")
public class BondInstRatinggradeTmpl extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private Long id;
	@ApiModelProperty(value = "用户所属机构ID")
	@Column(name = "org_id")
	private Long orgId;
	@ApiModelProperty(value = "行业ID,0代表通用")
	@Column(name = "indu_id")
	private Long induId;
	@ApiModelProperty(value = "行业类型，1=GISC行业，2=申万行业，3=自定义行业")
	@Column(name = "indu_type")
	private Integer induType;
	@ApiModelProperty(value = "打分项名称")
	@Column(name = "ratinggrade_name")
	private String ratinggradeName;
	@ApiModelProperty(value = "打分项类型，1=定量，2=定性")
	@Column(name = "ratinggrade_type")
	private Integer ratinggradeType;
	@ApiModelProperty(value = "打分权重,百分比")
	@Column(name = "grade_weight")
	private Integer gradeWeight;
	@ApiModelProperty(value = "优先级排序")
	@Column(name = "sort")
	private Integer sort;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getRatinggradeName() {
		return ratinggradeName;
	}

	public void setRatinggradeName(String ratinggradeName) {
		this.ratinggradeName = ratinggradeName;
	}

	public Integer getRatinggradeType() {
		return ratinggradeType;
	}

	public void setRatinggradeType(Integer ratinggradeType) {
		this.ratinggradeType = ratinggradeType;
	}

	public Integer getGradeWeight() {
		return gradeWeight;
	}

	public void setGradeWeight(Integer gradeWeight) {
		this.gradeWeight = gradeWeight;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

}
