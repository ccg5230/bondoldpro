package com.innodealing.bond.vo.reportdata;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author xiaochao
 * @time 2017年3月9日
 * @description: 财报数据VO
 */
public class BondReportDataVO {

	@ApiModelProperty(value = "发行人全称")
	private String instName;

	@ApiModelProperty(value = "所属行业全称")
	private String industryName;

	@ApiModelProperty(value = "用户所选行业类别(0-GICS, 1-申万))")
	private int induClass;

	@ApiModelProperty(value = "所属行业的标准(GICS/申万)")
	private String industryStandard;

	@ApiModelProperty(value = "评级季度(yyyy/[一季报/中报/三季报/年报])")
	private String evaluateQuarter;

	@ApiModelProperty(value = "当前日期(yyyy年MM月dd日)")
	private String currdate;

	@ApiModelProperty(value = "最终评级")
	private String finalRating;

	@ApiModelProperty(value = "文件的季度名称(yyyy[一季报/中报/三季报/年报], 中间没有斜杠)")
	private String fileName;

	@ApiModelProperty(value = "外部评级近况(个人上传不存在该项)")
	private BondIssExtRatingSubVO externalRating = new BondIssExtRatingSubVO();

	@ApiModelProperty(value = "主体量化风险等级近况(个人上传含部分该项)")
	private BondIssDMRatingSubVO riskLevel = new BondIssDMRatingSubVO();

	@ApiModelProperty(value = "重点风险指标揭示(共用)")
	private BondIssRatingRatioSubVO riskIndicator = new BondIssRatingRatioSubVO();

	@ApiModelProperty(value = "该行业专项财务指标行业对比情况(个人上传不存在该项)")
	private BondIssFinanceInfoSubVO indexComparison = new BondIssFinanceInfoSubVO();

	@ApiModelProperty(value = "同行业信用等级排名(共用)")
	private BondIssPdRankSubVO creditRank = new BondIssPdRankSubVO();

	public String getInstName() {
		return instName;
	}

	public void setInstName(String instName) {
		this.instName = instName;
	}

	public String getIndustryName() {
		return industryName;
	}

	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}

	public String getIndustryStandard() {
		return industryStandard;
	}

	public void setIndustryStandard(String industryStandard) {
		this.industryStandard = industryStandard;
	}

	public String getEvaluateQuarter() {
		return evaluateQuarter;
	}

	public void setEvaluateQuarter(String evaluateQuarter) {
		this.evaluateQuarter = evaluateQuarter;
	}

	public String getCurrdate() {
		return currdate;
	}

	public void setCurrdate(String currdate) {
		this.currdate = currdate;
	}

	public String getFinalRating() {
		return finalRating;
	}

	public void setFinalRating(String finalRating) {
		this.finalRating = finalRating;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public BondIssExtRatingSubVO getExternalRating() {
		return externalRating;
	}

	public void setExternalRating(BondIssExtRatingSubVO externalRating) {
		this.externalRating = externalRating;
	}

	public BondIssDMRatingSubVO getRiskLevel() {
		return riskLevel;
	}

	public void setRiskLevel(BondIssDMRatingSubVO riskLevel) {
		this.riskLevel = riskLevel;
	}

	public BondIssRatingRatioSubVO getRiskIndicator() {
		return riskIndicator;
	}

	public void setRiskIndicator(BondIssRatingRatioSubVO riskIndicator) {
		this.riskIndicator = riskIndicator;
	}

	public BondIssFinanceInfoSubVO getIndexComparison() {
		return indexComparison;
	}

	public void setIndexComparison(BondIssFinanceInfoSubVO indexComparison) {
		this.indexComparison = indexComparison;
	}

	public BondIssPdRankSubVO getCreditRank() {
		return creditRank;
	}

	public void setCreditRank(BondIssPdRankSubVO creditRank) {
		this.creditRank = creditRank;
	}

	public int getInduClass() {
		return induClass;
	}

	public void setInduClass(int induClass) {
		this.induClass = induClass;
	}

}
