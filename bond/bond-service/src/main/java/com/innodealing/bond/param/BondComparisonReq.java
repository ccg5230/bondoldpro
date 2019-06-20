package com.innodealing.bond.param;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel
public class BondComparisonReq {
	
	@ApiModelProperty(value = "债券唯一编号")
	List<Long> bondId;


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BondComparisonAddReq [" + (bondId != null ? "bondId=" + bondId : "") + "]";
	}

	/**
	 * @return the bondId
	 */
	public List<Long> getBondId() {
		return bondId;
	}


	/**
	 * @param bondId the bondId to set
	 */
	public void setBondId(List<Long> bondId) {
		this.bondId = bondId;
	}
	

}
