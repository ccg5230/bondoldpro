package com.innodealing.bond.param;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel
public class BondFavorityAddReq {
	
	@ApiModelProperty(value = "债券唯一编号")
	private Long bondUniCode;
	
	@ApiModelProperty(value = "分组id集合")
	List<Integer> groupIds ;

	/**
	 * @return the bondUniCode
	 */
	public Long getBondUniCode() {
		return bondUniCode;
	}

	/**
	 * @param bondUniCode the bondUniCode to set
	 */
	public void setBondUniCode(Long bondUniCode) {
		this.bondUniCode = bondUniCode;
	}

    public List<Integer> getGroupIds() {
        return groupIds;
    }

    public void setGroupIds(List<Integer> groupIds) {
        this.groupIds = groupIds;
    }

	
}
