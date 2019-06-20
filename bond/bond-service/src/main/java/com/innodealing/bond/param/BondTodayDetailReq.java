package com.innodealing.bond.param;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@ApiModel
@JsonInclude(Include.NON_NULL)
public class BondTodayDetailReq implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "用户编号")
	private Long userId;

	@ApiModelProperty(value = "行业列表")
	private List<Integer> induIds;

	@ApiModelProperty(value = "是否包含存单")
	private Boolean isContainsNCD;

	@ApiModelProperty(value = "是否单边报价")
	private Boolean isUnilateralOffer;

	@ApiModelProperty(value = "评级类型:1-DM量化评级;2-外部评级")
	private Integer ratingType;

	@ApiModelProperty(value = "报价方向:1-bid出券;2-offer收券")
	private Integer side;

	@ApiModelProperty(value = "评级名称")
	private String rating;

	@ApiModelProperty(value = "期限")
	private String tenor;

	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * @return the induIds
	 */
	public List<Integer> getInduIds() {
		return induIds;
	}

	/**
	 * @param induIds
	 *            the induIds to set
	 */
	public void setInduIds(List<Integer> induIds) {
		this.induIds = induIds;
	}


	public Boolean getIsContainsNCD() {
		return isContainsNCD;
	}

	public void setIsContainsNCD(Boolean isContainsNCD) {
		this.isContainsNCD = isContainsNCD;
	}

	public Boolean getIsUnilateralOffer() {
		return isUnilateralOffer;
	}

	public void setIsUnilateralOffer(Boolean isUnilateralOffer) {
		this.isUnilateralOffer = isUnilateralOffer;
	}

	public Integer getSide() {
		return side;
	}

	public void setSide(Integer side) {
		this.side = side;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getTenor() {
		return tenor;
	}

	public void setTenor(String tenor) {
		this.tenor = tenor;
	}

	public Integer getRatingType() {
		return ratingType;
	}

	public void setRatingType(Integer ratingType) {
		this.ratingType = ratingType;
	}
}
