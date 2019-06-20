package com.innodealing.model.dm.bond;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author xiaochao
 * @time 2017年3月31日
 * @description:
 */
@Entity
@Table(name = "t_mine_field_tags")
public class MineFieldTags implements Serializable {
	private static final long serialVersionUID = -8632121637933258039L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@ApiModelProperty(value = "id")
	@Column(name = "id")
	private Long id;

	@ApiModelProperty(value = "标签名称")
	@Column(name = "tag_name")
	private String tagName;

	@ApiModelProperty(value = "分类")
	@Column(name = "classification")
	private Integer classification;

	@ApiModelProperty(value = "是否负面消息， 1=负面， 0=正面 ")
	@Column(name = "isnegative")
	private boolean isnegative;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getClassification() {
		return classification;
	}

	public void setClassification(Integer classification) {
		this.classification = classification;
	}

	public boolean getIsnegative() {
		return isnegative;
	}

	public void setIsnegative(boolean isnegative) {
		this.isnegative = isnegative;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
}
