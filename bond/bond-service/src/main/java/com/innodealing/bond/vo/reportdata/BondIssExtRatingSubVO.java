package com.innodealing.bond.vo.reportdata;

import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author xiaochao
 * @time 2017年3月9日
 * @description: 外部评级历史变动记录
 */
public class BondIssExtRatingSubVO {

	@ApiModelProperty(value = "外部评级概要信息")
	private String shortDesc;

	@ApiModelProperty(value = "外部评级历史变动记录列表(最多五条)")
	private List<BondIssExtRatingHistSubVO> recentChanges = new ArrayList<BondIssExtRatingHistSubVO>();

	@ApiModelProperty(value = "负面展望点")
	private List<String> negativePoints = new ArrayList<String>();

	public String getShortDesc() {
		return shortDesc;
	}

	public void setShortDesc(String shortDesc) {
		this.shortDesc = shortDesc;
	}

	public List<BondIssExtRatingHistSubVO> getRecentChanges() {
		return recentChanges;
	}

	public void setRecentChanges(List<BondIssExtRatingHistSubVO> recentChanges) {
		this.recentChanges = recentChanges;
	}

	public List<String> getNegativePoints() {
		return negativePoints;
	}

	public void setNegativePoints(List<String> negativePoints) {
		this.negativePoints = negativePoints;
	}
}
