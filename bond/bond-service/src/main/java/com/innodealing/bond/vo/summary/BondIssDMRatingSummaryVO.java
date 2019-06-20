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
public class BondIssDMRatingSummaryVO {
	
	@ApiModelProperty(value = "主体量化评级-总述")
	private String ratingSummary;

	@ApiModelProperty(value = "主体量化评级-标题")
	private String ratioTitle;
	
	@ApiModelProperty(value = "主体量化评级-重点风险指标揭示列表")
	private List<String> ratioContentList = new ArrayList<String>();


	public String getRatioTitle() {
		return ratioTitle;
	}

	public void setRatioTitle(String ratioTitle) {
		this.ratioTitle = ratioTitle;
	}

	public List<String> getRatioContentList() {
		return ratioContentList;
	}

	public void setRatioContentList(List<String> ratioContentList) {
		this.ratioContentList = ratioContentList;
	}

	public String getRatingSummary() {
		return ratingSummary;
	}

	public void setRatingSummary(String ratingSummary) {
		this.ratingSummary = ratingSummary;
	}
}
