package com.innodealing.model.mongo.dm;


import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;

@JsonInclude(Include.NON_NULL)
@Document(collection="bond_‎comparison") 
public class BondComparisonDoc implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4338470216011792156L;

	@Indexed
	@ApiModelProperty(value = "用户编号")
    private Long userId;
    
	@ApiModelProperty(value = "对比债券编号")
    private Long bondId;

	public BondComparisonDoc() {

	}
	public BondComparisonDoc(Long userId, Long bondId) {
		super();
		this.userId = userId;
		this.bondId = bondId;
	}

	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * @return the bondUniCode
	 */
	public Long getBondId() {
		return bondId;
	}

	/**
	 * @param bondUniCode the bondUniCode to set
	 */
	public void setBondId(Long bondUniCode) {
		this.bondId = bondUniCode;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Bond‎Comparison [" + (userId != null ? "userId=" + userId + ", " : "")
				+ (bondId != null ? "bondUniCode=" + bondId : "") + "]";
	}

   
    
}
