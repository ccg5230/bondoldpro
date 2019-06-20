package com.innodealing.model.mongo.dm.bond.detail.analyse;

import java.io.Serializable;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel("行业主体信息mapping")
@Document(collection = "iss_indu_map_sw")
public class IssInduMapSwDoc implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主体所在行业id")
	@Id
	private Long induIdSw;
	
//	@ApiModelProperty(value = "主体类型")
//	private String orgType;
	
	@ApiModelProperty(value = "主体id集合")
	private Set<Long> issIds;

	


	public Long getInduIdSw() {
		return induIdSw;
	}

	public void setInduIdSw(Long induIdSw) {
		this.induIdSw = induIdSw;
	}

	public Set<Long> getIssIds() {
		return issIds;
	}

	public void setIssIds(Set<Long> issIds) {
		this.issIds = issIds;
	}

	public IssInduMapSwDoc(Long induIdSw, Set<Long> issIds) {
		super();
		this.induIdSw = induIdSw;
		this.issIds = issIds;
	}

	public IssInduMapSwDoc() {
		super();
	}

	

	
}
