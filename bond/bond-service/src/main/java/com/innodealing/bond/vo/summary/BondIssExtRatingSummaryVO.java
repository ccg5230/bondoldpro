package com.innodealing.bond.vo.summary;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author xiaochao
 * @date 2017年2月10日
 * @decription TODO
 */
@JsonInclude(Include.NON_NULL)
public class BondIssExtRatingSummaryVO {

	@ApiModelProperty(value = "外部评级-概要信息")
	private String shortDesc;

	@ApiModelProperty(value = "外部评级-概要信息列表")
	private List<String> summaryList = new ArrayList<String>();

	@ApiModelProperty(value = "外部评级-重点风险指标揭示列表")
	private String extLatestCceDisadvt;

	public String getShortDesc() {
		return shortDesc;
	}

	public void setShortDesc(String shortDesc) {
		this.shortDesc = shortDesc;
	}

	public List<String> getSummaryList() {
		return summaryList;
	}

	public void setSummaryList(List<String> summaryList) {
		this.summaryList = summaryList;
	}

	public String getExtLatestCceDisadvt() {
		return extLatestCceDisadvt;
	}

	public void setExtLatestCceDisadvt(String extLatestCceDisadvt) {
		this.extLatestCceDisadvt = extLatestCceDisadvt;
	}
}
