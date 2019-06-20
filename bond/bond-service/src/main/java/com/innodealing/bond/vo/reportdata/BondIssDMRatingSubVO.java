package com.innodealing.bond.vo.reportdata;

import java.util.ArrayList;
import java.util.List;

import com.innodealing.model.mongo.dm.BondComInfoRatingInduDoc;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author xiaochao
 * @time 2017年3月9日
 * @description: 主体量化风险等级近况
 */
public class BondIssDMRatingSubVO {

	@ApiModelProperty(value = "近五期主体量化风险等级变动情况")
	private List<BondIssDMRatingHistSubVO> recentChanges = new ArrayList<BondIssDMRatingHistSubVO>();

	@ApiModelProperty(value = "主体量化风险等级概要信息")
	private String shortDesc;

	@ApiModelProperty(value = "全市场评级分布图元数据")
	private List<BondComInfoRatingInduDoc> allMarketChartMetadata;

	@ApiModelProperty(value = "当前行业评级分布图元数据")
	private List<BondComInfoRatingInduDoc> industryChartMetadata;

	public List<BondIssDMRatingHistSubVO> getRecentChanges() {
		return recentChanges;
	}

	public void setRecentChanges(List<BondIssDMRatingHistSubVO> recentChanges) {
		this.recentChanges = recentChanges;
	}

	public String getShortDesc() {
		return shortDesc;
	}

	public void setShortDesc(String shortDesc) {
		this.shortDesc = shortDesc;
	}

	public List<BondComInfoRatingInduDoc> getAllMarketChartMetadata() {
		return allMarketChartMetadata;
	}

	public void setAllMarketChartMetadata(List<BondComInfoRatingInduDoc> allMarketChartMetadata) {
		this.allMarketChartMetadata = allMarketChartMetadata;
	}

	public List<BondComInfoRatingInduDoc> getIndustryChartMetadata() {
		return industryChartMetadata;
	}

	public void setIndustryChartMetadata(List<BondComInfoRatingInduDoc> industryChartMetadata) {
		this.industryChartMetadata = industryChartMetadata;
	}
}
