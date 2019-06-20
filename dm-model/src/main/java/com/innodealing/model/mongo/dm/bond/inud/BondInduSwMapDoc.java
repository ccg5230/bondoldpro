package com.innodealing.model.mongo.dm.bond.inud;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;

import io.swagger.annotations.ApiModelProperty;
/**
 * GICS行业分类和申万分类id映射关系
 * @author zhaozhenglai
 * @date 2017年2月6日下午3:51:13
 *Copyright © 2016 DealingMatrix.cn. All Rights Reserved.
 */
@Document(collection = "indu_map_sw")
public class BondInduSwMapDoc implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty("GICS行业id")
	private Long induId;
	
	@ApiModelProperty("申万行业id")
	private Long induIdSw;
	
	@ApiModelProperty("发行人id")
	private Long issuerId;

	public Long getInduId() {
		return induId;
	}

	public void setInduId(Long induId) {
		this.induId = induId;
	}

	public Long getInduIdSw() {
		return induIdSw;
	}

	public void setInduIdSw(Long induIdSw) {
		this.induIdSw = induIdSw;
	}

	public Long getIssuerId() {
		return issuerId;
	}

	public void setIssuerId(Long issuerId) {
		this.issuerId = issuerId;
	}
	
	

}
