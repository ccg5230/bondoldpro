package com.innodealing.model.mongo.dm.bond.detail.analyse;

import java.io.Serializable;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel("行业主体信息mapping")
@Document(collection = "iss_indu_map")
public class IssInduMapDoc implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主体所在行业id")
	@Id
	private Long induId;
	
//	@ApiModelProperty(value = "主体类型")
//	private String orgType;
	
	@ApiModelProperty(value = "主体id集合")
	private Set<Long> issIds;

	public Long getInduId() {
		return induId;
	}

	public void setInduId(Long induId) {
		this.induId = induId;
	}


	public Set<Long> getIssIds() {
		return issIds;
	}

	public void setIssIds(Set<Long> issIds) {
		this.issIds = issIds;
	}

	public IssInduMapDoc(Long induId, Set<Long> issIds) {
		super();
		this.induId = induId;
		this.issIds = issIds;
	}

	public IssInduMapDoc() {
		super();
	}

	

	
}
