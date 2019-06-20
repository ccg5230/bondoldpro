package com.innodealing.model.mongo.dm.bond.detail.analyse;

import java.io.Serializable;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("地区(省份)")
@Document(collection = "iss_province_map")
public class IssProvinceMapDoc implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8086584409764947085L;
	
	@ApiModelProperty("省份id")
	@Id
	private Long provinceId;
	
	@ApiModelProperty("主体集合")
	private Set<Long> issIds;

	public Long getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Long provinceId) {
		this.provinceId = provinceId;
	}

	public Set<Long> getIssIds() {
		return issIds;
	}

	public void setIssIds(Set<Long> issIds) {
		this.issIds = issIds;
	}

	public IssProvinceMapDoc(Long provinceId, Set<Long> issIds) {
		super();
		this.provinceId = provinceId;
		this.issIds = issIds;
	}

	public IssProvinceMapDoc() {
		super();
	}
	
	
	
	
}
