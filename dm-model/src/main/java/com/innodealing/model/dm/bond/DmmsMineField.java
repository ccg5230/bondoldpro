package com.innodealing.model.dm.bond;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author xiaochao
 * @time 2017年4月10日
 * @description: 
 */
@Entity
@Table(name = "innodealing.dmms_mine_field")
public class DmmsMineField implements Serializable {
	private static final long serialVersionUID = 2794162948971738843L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@ApiModelProperty(value = "id")
	@Column(name = "id")
	private Long id;

	@ApiModelProperty(value = "跳转地址")
	@Column(name = "skip_url")
	@JsonIgnore
	private String skipUrl;
	
	@OneToOne(mappedBy="mainDMF")
	@JsonIgnore
    private MineFieldCfg mainMFC;
	
	@OneToOne(mappedBy="deletedDMF")
	@JsonIgnore
    private MineFieldDeleted deletedMFC;

	@ApiModelProperty(value = "发布时间")
	@Temporal(TemporalType.DATE)
	@Column(name = "publishtime")
	private Date publishtime;
	
	@ApiModelProperty(value = "跳转Json")
	@JsonIgnore
	@Column(name = "skip_json")
	private String skipJson;
	
	@ApiModelProperty(value = "跳转类型")
	@Column(name = "skip_type")
	private Integer skipType;
	
	@ApiModelProperty(value = "跳转Json列表")
	@Transient
	@JsonIgnore
	private List<Object> skipJsonList;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSkipUrl() {
		return skipUrl;
	}

	public void setSkipUrl(String skipUrl) {
		this.skipUrl = skipUrl;
	}

	public MineFieldCfg getMainMFC() {
		return mainMFC;
	}

	public void setMainMFC(MineFieldCfg mainMFC) {
		this.mainMFC = mainMFC;
	}

	public MineFieldDeleted getDeletedMFC() {
		return deletedMFC;
	}

	public void setDeletedMFC(MineFieldDeleted deletedMFC) {
		this.deletedMFC = deletedMFC;
	}

	public Date getPublishtime() {
		return publishtime;
	}

	public void setPublishtime(Date publishtime) {
		this.publishtime = publishtime;
	}

	public String getSkipJson() {
		return skipJson;
	}

	public void setSkipJson(String skipJson) {
		this.skipJson = skipJson;
	}

	public Integer getSkipType() {
		return skipType;
	}

	public void setSkipType(Integer skipType) {
		this.skipType = skipType;
	}

	public List<Object> getSkipJsonList() {
		return skipJsonList;
	}

	public void setSkipJsonList(List<Object> skipJsonList) {
		this.skipJsonList = skipJsonList;
	}
}
