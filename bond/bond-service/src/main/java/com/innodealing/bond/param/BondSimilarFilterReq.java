package com.innodealing.bond.param;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.innodealing.model.mongo.dm.BondIssuerDoc;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
@JsonInclude(Include.NON_NULL)
public class BondSimilarFilterReq implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4623706192249175407L;

	@NotNull(message = "用户编号不可为空")
	@ApiModelProperty(value = "用户编号")
	private Long userId; 

	@NotNull(message = "相似字段不可为空")
	@ApiModelProperty(value = "列表元素取值范围 tenor, dmBondType, induId, bondRating, issRating")
	private List<String> similarField;

	@NotNull(message = "相似债券id不可为空")
	@ApiModelProperty(value = "债券唯一id")
	private Long bondId;
	
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

	

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BondSimilarFilterReq [" + (userId != null ? "userId=" + userId + ", " : "")
				+ (similarField != null ? "similarField=" + similarField + ", " : "")
				+ (bondId != null ? "bondId=" + bondId : "") + "]";
	}

	/**
	 * @return the bondId
	 */
	public Long getBondId() {
		return bondId;
	}

	/**
	 * @param bondId the bondId to set
	 */
	public void setBondId(Long bondId) {
		this.bondId = bondId;
	}

	/**
	 * @return the similarField
	 */
	public List<String> getSimilarField() {
		return similarField;
	}

	/**
	 * @param similarField the similarField to set
	 */
	public void setSimilarField(List<String> similarField) {
		this.similarField = similarField;
	}

	
}


