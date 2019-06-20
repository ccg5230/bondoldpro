package com.innodealing.model.dm.bond;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.omg.CORBA.ServerRequest;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author xiaochao
 * @time 2017年3月31日
 * @description:
 */
@Entity
@Table(name = "innodealing.t_mine_field_cfg")
public class MineFieldCfg implements Serializable {
	private static final long serialVersionUID = 8166013989844735562L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@ApiModelProperty(value = "id")
	@Column(name = "id")
	private Long id;

	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property="id", scope=ServerRequest.class)
	@OneToOne(cascade=CascadeType.REFRESH)
    @JoinColumn(name="mine_id")
    private DmmsMineField mainDMF;

	@ApiModelProperty(value = "关联的主体ID")
	@Column(name = "issuer_id")
	private Long issuerId;

	@ApiModelProperty(value = "关联的主体名称")
	@Column(name = "issuer_name")
	private String issuerName;

	@ApiModelProperty(value = "关联一些债券的id")
	@Column(name = "bond_ids")
	@JsonIgnore
	private String bondIds;

	@ApiModelProperty(value = "关联一些债券的名称")
	@Column(name = "bond_names")
	@JsonIgnore
	private String bondNames;

	@ApiModelProperty(value = "标题")
	@Column(name = "title")
	private String title;

	@ApiModelProperty(value = "概述")
	@Column(name = "outlines")
	private String outlines;

	@ApiModelProperty(value = "分类")
	@Column(name = "classification")
	private Integer classification;

	@ApiModelProperty(value = "是否显示，1=显示，0=不显示")
	@Column(name = "isdisplayed")
	@JsonIgnore
	private Integer isdisplayed;

	@ApiModelProperty(value = "是否负面消息， 1=负面， 0=正面 ")
	@Column(name = "isnegative")
	@JsonIgnore
	private Integer isnegative;

	@ApiModelProperty(value = "编辑人")
	@Column(name = "operator")
	@JsonIgnore
	private String operator;

	@ApiModelProperty(value = "关联t_mine_field_tags的ID")
	@Column(name = "tag_ids")
	@JsonIgnore
	private String tagIds;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIssuerId() {
		return issuerId;
	}

	public void setIssuerId(Long issuerId) {
		this.issuerId = issuerId;
	}

	public String getIssuerName() {
		return issuerName;
	}

	public void setIssuerName(String issuerName) {
		this.issuerName = issuerName;
	}

	public String getBondIds() {
		return bondIds;
	}

	public void setBondIds(String bondIds) {
		this.bondIds = bondIds;
	}

	public String getBondNames() {
		return bondNames;
	}

	public void setBondNames(String bondNames) {
		this.bondNames = bondNames;
	}

	public String getOutlines() {
		return outlines;
	}

	public void setOutlines(String outlines) {
		this.outlines = outlines;
	}

	public Integer getClassification() {
		return classification;
	}

	public void setClassification(Integer classification) {
		this.classification = classification;
	}

	public Integer getIsdisplayed() {
		return isdisplayed;
	}

	public void setIsdisplayed(Integer isdisplayed) {
		this.isdisplayed = isdisplayed;
	}

	public Integer getIsnegative() {
		return isnegative;
	}

	public void setIsnegative(Integer isnegative) {
		this.isnegative = isnegative;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTagIds() {
		return tagIds;
	}

	public void setTagIds(String tagIds) {
		this.tagIds = tagIds;
	}

	public DmmsMineField getMainDMF() {
		return mainDMF;
	}

	public void setMainDMF(DmmsMineField mainDMF) {
		this.mainDMF = mainDMF;
	}
}
