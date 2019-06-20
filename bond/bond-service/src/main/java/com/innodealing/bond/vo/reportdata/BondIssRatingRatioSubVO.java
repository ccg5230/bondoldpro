package com.innodealing.bond.vo.reportdata;

import java.util.ArrayList;
import java.util.List;

import com.innodealing.bond.vo.summary.IssRatingScoreSummary;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author xiaochao
 * @time 2017年3月9日
 * @description: 重点风险指标
 */
public class BondIssRatingRatioSubVO {

	@ApiModelProperty(value = "信息标题")
	private String ratioTitle;

	@ApiModelProperty(value = "重点风险指标要点列表")
	private List<String> keyPoints = new ArrayList<String>();

	@ApiModelProperty(value = "重点风险指标对比信息(表格)")
	private IssRatingScoreSummary scoreSummary;

	public String getRatioTitle() {
		return ratioTitle;
	}

	public void setRatioTitle(String ratioTitle) {
		this.ratioTitle = ratioTitle;
	}

	public List<String> getKeyPoints() {
		return keyPoints;
	}

	public void setKeyPoints(List<String> keyPoints) {
		this.keyPoints = keyPoints;
	}

	public IssRatingScoreSummary getScoreSummary() {
		return scoreSummary;
	}

	public void setScoreSummary(IssRatingScoreSummary scoreSummary) {
		this.scoreSummary = scoreSummary;
	}
}
