package com.innodealing.model.mongo.dm;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;

@JsonInclude(Include.NON_NULL)
@Document(collection = "bond_basic_info_class_one")
public class BondBasicInfoClassOneDoc implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static final Integer RISK_WARNING_PD_THRESHOLD = 18; // CCC

	@Id
	@ApiModelProperty(value = "债券id")
	private Long bondUniCode;
	
	@Indexed
    @ApiModelProperty(value = "发行主体code")
    private Long comUniCode;
	
    @ApiModelProperty(value = "发行人名称:来自于bond_ccxe.d_pub_com_info_2或者dmms人工填写")
    private String issName;

	@Indexed
	@ApiModelProperty(value = "债券代码")
	private String bondCode;

	@Indexed
	@ApiModelProperty(value = "债券全称")
	private String bondFullName;

	@Indexed
	@ApiModelProperty(value = "债券简称")
	private String bondShortName;

	@ApiModelProperty(value = "发行主体名称")
	private String issuer;

	@ApiModelProperty(value = "债券期限")
	private String bondMatu;

	@Indexed
	@ApiModelProperty(value = "债券期限-天")
	private Integer matuUnitParDays;

	@ApiModelProperty(value = "计划发行规模")
	private Double planIssScale;

	@ApiModelProperty(value = "计划发行规模单位：bond_ccxe.pub_par where PAR_SYS_CODE=6007 ")
	private String planIssScaleUnit;

	@Indexed
	@ApiModelProperty(value = "dm量化评分 ")
	private String dmRatingScore;
	
	@ApiModelProperty(value = "dm量化评分排序值 ")
	private Integer dmRatingScoreSort; 
	
	@ApiModelProperty(value = "dm量化评分违约概率 ")
    private String dmRatingScorePD;
	
	@ApiModelProperty(value = "DM量化评分评级日期")
	@JsonFormat(pattern="yyyy/MM/dd HH:mm:ss")
    private Date dmRatingDate;

	@Indexed
	@ApiModelProperty(value = "主体评级 ")
	private String issRating;
	
	@Indexed
	@ApiModelProperty(value = "债项评级 ")
	private String bondRating;

	@Indexed
	@ApiModelProperty(value = "rating机构评分：YY评分")
	private String orgRating;
	
	@ApiModelProperty(value = "评级分类:0-城投 1-产业")
	private String ratingType;

	@ApiModelProperty(value = "rating机构定价下限：YY定价")
	private Double orgPriceLower;

	@ApiModelProperty(value = "rating机构定价上限：YY定价")
	private Double orgPriceSuper;
	
	@ApiModelProperty(value = "rating狗利差下限（单位BP）")
    private Double orgMarginLower;
	
	@ApiModelProperty(value = "rating狗利差上限（单位BP）")
    private Double orgMarginSuper;
	
	@ApiModelProperty(value = "rating定价理由")
    private String fixPriceReason;

	@ApiModelProperty(value = "招标区间下限")
	private Double didIntervalLow;
	
	@ApiModelProperty(value = "招标区间上限")
    private Double didIntervalSup;

	/** 若发行方式为簿记建档，返回  簿记建档截止日  的数据  ,若发行方式为招标，返回  截标结束时间 的数据*/
	@ApiModelProperty(value = "截标结束日开始时间")
	@JsonFormat(pattern="yyyy/MM/dd HH:mm:ss")
    private Date stopBidStartDate;
	
	@ApiModelProperty(value = "截标结束日结束时间:存放截标或者簿记建档截止日开始时间")
	@JsonFormat(pattern="yyyy/MM/dd HH:mm:ss")
	private Date stopBidEndDate;
	
	@ApiModelProperty(value = "簿记建档截止日开始时间")
	@JsonFormat(pattern="yyyy/MM/dd HH:mm:ss")
    private Date bookStartDate; 

	@ApiModelProperty(value = "簿记建档截止日结束时间")
	@JsonFormat(pattern="yyyy/MM/dd HH:mm:ss")
    private Date bookEndDate; 
	
	@ApiModelProperty(value = "上市日期")
	@JsonFormat(pattern="yyyy/MM/dd HH:mm:ss")
	private Date listDate;

	@ApiModelProperty(value = "主承销商")
	private String undeName;

	@ApiModelProperty(value = "企业性质(所有制), 列表元素并列关系，取值范围1~3, 含义参考dmdb.t_pub_par(PAR_SYS_CODE:2006)")
	private Integer comAttrPar;

	@ApiModelProperty(value = "发行方式  //0-招标  1-薄记建档 ")
	private String issCls;

	@Indexed
	@ApiModelProperty(value = "债券类型（bond_ccxe.pub_par where PAR_SYS_CODE=3051）")
	private Integer bondTypePar;

	@Indexed
	@ApiModelProperty(value = "发行起始日")
	@JsonFormat(pattern="yyyy/MM/dd HH:mm:ss")
	private Date issStartDate;
	
	@Indexed
	@ApiModelProperty(value = "发行截止日")
	@JsonFormat(pattern="yyyy/MM/dd HH:mm:ss")
    private Date issEndDate;

	@ApiModelProperty(value = "创建时间")
	@JsonFormat(pattern="yyyy/MM/dd HH:mm:ss")
	private Date createTime;

	@ApiModelProperty(value = "第一大股东名称")
	private String firstMajShareholderName;

	@ApiModelProperty(value = "第一大股东企业性质")
	private String enterpriseNature;
	
	@ApiModelProperty(value = "股东占比（单位%）")
    private Double proportionShareholder;
	
	@ApiModelProperty(value = "申购区间下限")
	private Double subscriptionIntervalLower;
	
	@ApiModelProperty(value = "申购区间上限")
    private Double subscriptionIntervalSuper;

    @ApiModelProperty(value = "所处行业")
	private String trade;

	@ApiModelProperty(value = "股东背景")
	private String shareholderRelations;

	@ApiModelProperty(value = "经营状况")
	private String stateOperation;

	@ApiModelProperty(value = "财务状况")
	private String financialStanding;

	@ApiModelProperty(value = "重点关注")
	private String focusOn;
	
	@ApiModelProperty(value = "加入关注")
    private Integer favorite;
	
    @ApiModelProperty(value = "主体量化风险等级变化： >0上升 0持平 <0下降")
    private Long pdDiff;
    
    @ApiModelProperty(value = "风险警告标志, 量化风险等级<=CCC")
    private Boolean riskWarning;
    
    @ApiModelProperty(value = "主体评级排序值 ")
    private Integer issRatingSort; 
    
    @ApiModelProperty(value = "募集金额/发行总额")
    private Double actuFirIssAmut;
    
    @ApiModelProperty(value = "募集金额/发行总额单位：bond_ccxe.pub_par  PAR_SYS_CODE=6007 ")
    private String actuFirIssAmutUnit;
    
    @ApiModelProperty(value = "票面利率")
    private Double issCoupRate;
    
    @Indexed
    @ApiModelProperty(value = "兴业主体评分")
    private String industrialSubjectScore;
    
    @ApiModelProperty(value = "兴业主体评分排序值:1比5好")
    private Integer industSubjectScoreSort;
    
    @Indexed
    @ApiModelProperty(value = "兴业债项评分")
    private String industrialBondScore;
    
    @ApiModelProperty(value = "兴业主体点评")
    private String subjectComment;
    
    @ApiModelProperty(value = "兴业个劵点评")
    private String ticketComment;
    
    @ApiModelProperty(value = "兴业参考收益率(%)：兴业定价")
    private Double referenceReturnsRatio;
    
    @ApiModelProperty(value = "兴业利差(BP)")
    private String  industrialZMargin;
    
    @ApiModelProperty(value = "发行规模排序值（乘以单位）：计划发行规模和募集金额/发行总额，若募集金额/发行总额不为空则展示募集金额/发行总额，若募集金额/发行总额为空，则展示计划发行规模")
    private Double issScaleSort;
    
    @ApiModelProperty(value = "YY评分排序值：小的更好")
    private Integer  yyRationgSort;
    
    @ApiModelProperty(value = "兴业展望：0-负面 1-稳定")
    private String industExpectation;
    
    @ApiModelProperty(value = "利率排序值：票面利率若不为空，则展示票面利率字段，若票面利率为空，展示利率区间（若为簿记建档，展示申购区间，若为招标发行，展示招标区间）")
    private Double rateSort;
    
    @ApiModelProperty(value = "发行状态:0-发行中 1-已上市 2-延迟发行 3-取消发行")
    private String issStatus;
    
    @ApiModelProperty(value = "取消发行日期")
    @JsonFormat(pattern="yyyy/MM/dd HH:mm:ss")
    private Date cancelIssueDate;
    
    @ApiModelProperty(value = "城投地位")
    private String cityInvestStatus;
    
    @ApiModelProperty(value = "前端截标开始时间：用于查询")
    private Date frontBidStartDate;
    
    @ApiModelProperty(value = "前端取值截标结束时间：用于显示和排序")
    private Date frontBidEndDate;

	public Long getBondUniCode() {
		return bondUniCode;
	}

	public void setBondUniCode(Long bondUniCode) {
		this.bondUniCode = bondUniCode;
	}

	public Long getComUniCode() {
		return comUniCode;
	}

	public void setComUniCode(Long comUniCode) {
		this.comUniCode = comUniCode;
	}

	public String getIssName() {
		return issName;
	}

	public void setIssName(String issName) {
		this.issName = issName;
	}

	public String getBondCode() {
		return bondCode;
	}

	public void setBondCode(String bondCode) {
		this.bondCode = bondCode;
	}

	public String getBondFullName() {
		return bondFullName;
	}

	public void setBondFullName(String bondFullName) {
		this.bondFullName = bondFullName;
	}

	public String getBondShortName() {
		return bondShortName;
	}

	public void setBondShortName(String bondShortName) {
		this.bondShortName = bondShortName;
	}

	public String getIssuer() {
		return issuer;
	}

	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}

	public String getBondMatu() {
		return bondMatu;
	}

	public void setBondMatu(String bondMatu) {
		this.bondMatu = bondMatu;
	}

	public Integer getMatuUnitParDays() {
		return matuUnitParDays;
	}

	public void setMatuUnitParDays(Integer matuUnitParDays) {
		this.matuUnitParDays = matuUnitParDays;
	}

	public Double getPlanIssScale() {
		return planIssScale;
	}

	public void setPlanIssScale(Double planIssScale) {
		this.planIssScale = planIssScale;
	}

	public String getPlanIssScaleUnit() {
		return planIssScaleUnit;
	}

	public void setPlanIssScaleUnit(String planIssScaleUnit) {
		this.planIssScaleUnit = planIssScaleUnit;
	}

	public String getDmRatingScore() {
		return dmRatingScore;
	}

	public void setDmRatingScore(String dmRatingScore) {
		this.dmRatingScore = dmRatingScore;
	}

	public Integer getDmRatingScoreSort() {
		return dmRatingScoreSort;
	}

	public void setDmRatingScoreSort(Integer dmRatingScoreSort) {
		this.dmRatingScoreSort = dmRatingScoreSort;
	}

	public String getDmRatingScorePD() {
		return dmRatingScorePD;
	}

	public void setDmRatingScorePD(String dmRatingScorePD) {
		this.dmRatingScorePD = dmRatingScorePD;
	}

	public Date getDmRatingDate() {
		return dmRatingDate;
	}

	public void setDmRatingDate(Date dmRatingDate) {
		this.dmRatingDate = dmRatingDate;
	}

	public String getIssRating() {
		return issRating;
	}

	public void setIssRating(String issRating) {
		this.issRating = issRating;
	}

	public String getBondRating() {
		return bondRating;
	}

	public void setBondRating(String bondRating) {
		this.bondRating = bondRating;
	}

	public String getOrgRating() {
		return orgRating;
	}

	public void setOrgRating(String orgRating) {
		this.orgRating = orgRating;
	}

	public String getRatingType() {
		return ratingType;
	}

	public void setRatingType(String ratingType) {
		this.ratingType = ratingType;
	}

	public Double getOrgPriceLower() {
		return orgPriceLower;
	}

	public void setOrgPriceLower(Double orgPriceLower) {
		this.orgPriceLower = orgPriceLower;
	}

	public Double getOrgPriceSuper() {
		return orgPriceSuper;
	}

	public void setOrgPriceSuper(Double orgPriceSuper) {
		this.orgPriceSuper = orgPriceSuper;
	}

	public Double getOrgMarginLower() {
		return orgMarginLower;
	}

	public void setOrgMarginLower(Double orgMarginLower) {
		this.orgMarginLower = orgMarginLower;
	}

	public Double getOrgMarginSuper() {
		return orgMarginSuper;
	}

	public void setOrgMarginSuper(Double orgMarginSuper) {
		this.orgMarginSuper = orgMarginSuper;
	}

	public String getFixPriceReason() {
		return fixPriceReason;
	}

	public void setFixPriceReason(String fixPriceReason) {
		this.fixPriceReason = fixPriceReason;
	}

	public Double getDidIntervalLow() {
		return didIntervalLow;
	}

	public void setDidIntervalLow(Double didIntervalLow) {
		this.didIntervalLow = didIntervalLow;
	}

	public Double getDidIntervalSup() {
		return didIntervalSup;
	}

	public void setDidIntervalSup(Double didIntervalSup) {
		this.didIntervalSup = didIntervalSup;
	}

	public Date getStopBidEndDate() {
		return stopBidEndDate;
	}

	public void setStopBidEndDate(Date stopBidEndDate) {
		this.stopBidEndDate = stopBidEndDate;
	}

	public Date getListDate() {
		return listDate;
	}

	public void setListDate(Date listDate) {
		this.listDate = listDate;
	}

	public String getUndeName() {
		return undeName;
	}

	public void setUndeName(String undeName) {
		this.undeName = undeName;
	}

	public Integer getComAttrPar() {
		return comAttrPar;
	}

	public void setComAttrPar(Integer comAttrPar) {
		this.comAttrPar = comAttrPar;
	}

	public String getIssCls() {
		return issCls;
	}

	public void setIssCls(String issCls) {
		this.issCls = issCls;
	}

	public Integer getBondTypePar() {
		return bondTypePar;
	}

	public void setBondTypePar(Integer bondTypePar) {
		this.bondTypePar = bondTypePar;
	}

	public Date getIssStartDate() {
		return issStartDate;
	}

	public void setIssStartDate(Date issStartDate) {
		this.issStartDate = issStartDate;
	}

	public Date getIssEndDate() {
		return issEndDate;
	}

	public void setIssEndDate(Date issEndDate) {
		this.issEndDate = issEndDate;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getFirstMajShareholderName() {
		return firstMajShareholderName;
	}

	public void setFirstMajShareholderName(String firstMajShareholderName) {
		this.firstMajShareholderName = firstMajShareholderName;
	}

	public String getEnterpriseNature() {
		return enterpriseNature;
	}

	public void setEnterpriseNature(String enterpriseNature) {
		this.enterpriseNature = enterpriseNature;
	}

	public Double getProportionShareholder() {
		return proportionShareholder;
	}

	public void setProportionShareholder(Double proportionShareholder) {
		this.proportionShareholder = proportionShareholder;
	}

	public Double getSubscriptionIntervalLower() {
		return subscriptionIntervalLower;
	}

	public void setSubscriptionIntervalLower(Double subscriptionIntervalLower) {
		this.subscriptionIntervalLower = subscriptionIntervalLower;
	}

	public Double getSubscriptionIntervalSuper() {
		return subscriptionIntervalSuper;
	}

	public void setSubscriptionIntervalSuper(Double subscriptionIntervalSuper) {
		this.subscriptionIntervalSuper = subscriptionIntervalSuper;
	}

	public String getTrade() {
		return trade;
	}

	public void setTrade(String trade) {
		this.trade = trade;
	}

	public String getShareholderRelations() {
		return shareholderRelations;
	}

	public void setShareholderRelations(String shareholderRelations) {
		this.shareholderRelations = shareholderRelations;
	}

	public String getStateOperation() {
		return stateOperation;
	}

	public void setStateOperation(String stateOperation) {
		this.stateOperation = stateOperation;
	}

	public String getFinancialStanding() {
		return financialStanding;
	}

	public void setFinancialStanding(String financialStanding) {
		this.financialStanding = financialStanding;
	}

	public String getFocusOn() {
		return focusOn;
	}

	public void setFocusOn(String focusOn) {
		this.focusOn = focusOn;
	}

	public Integer getFavorite() {
		return favorite;
	}

	public void setFavorite(Integer favorite) {
		this.favorite = favorite;
	}

	public Long getPdDiff() {
		return pdDiff;
	}

	public void setPdDiff(Long pdDiff) {
		this.pdDiff = pdDiff;
	}

	public Boolean getRiskWarning() {
		return riskWarning;
	}

	public void setRiskWarning(Boolean riskWarning) {
		this.riskWarning = riskWarning;
	}

    public Integer getIssRatingSort() {
        return issRatingSort;
    }

    public void setIssRatingSort(Integer issRatingSort) {
        this.issRatingSort = issRatingSort;
    }

    public Double getActuFirIssAmut() {
        return actuFirIssAmut;
    }

    public void setActuFirIssAmut(Double actuFirIssAmut) {
        this.actuFirIssAmut = actuFirIssAmut;
    }

    public String getActuFirIssAmutUnit() {
        return actuFirIssAmutUnit;
    }

    public void setActuFirIssAmutUnit(String actuFirIssAmutUnit) {
        this.actuFirIssAmutUnit = actuFirIssAmutUnit;
    }

    public Double getIssCoupRate() {
        return issCoupRate;
    }

    public void setIssCoupRate(Double issCoupRate) {
        this.issCoupRate = issCoupRate;
    }

    public String getIndustrialSubjectScore() {
        return industrialSubjectScore;
    }

    public void setIndustrialSubjectScore(String industrialSubjectScore) {
        this.industrialSubjectScore = industrialSubjectScore;
    }

    public Integer getIndustSubjectScoreSort() {
        return industSubjectScoreSort;
    }

    public void setIndustSubjectScoreSort(Integer industSubjectScoreSort) {
        this.industSubjectScoreSort = industSubjectScoreSort;
    }

    public String getIndustrialBondScore() {
        return industrialBondScore;
    }

    public void setIndustrialBondScore(String industrialBondScore) {
        this.industrialBondScore = industrialBondScore;
    }

    public String getSubjectComment() {
        return subjectComment;
    }

    public void setSubjectComment(String subjectComment) {
        this.subjectComment = subjectComment;
    }

    public String getTicketComment() {
        return ticketComment;
    }

    public void setTicketComment(String ticketComment) {
        this.ticketComment = ticketComment;
    }

    public Double getReferenceReturnsRatio() {
        return referenceReturnsRatio;
    }

    public void setReferenceReturnsRatio(Double referenceReturnsRatio) {
        this.referenceReturnsRatio = referenceReturnsRatio;
    }

    public String getIndustrialZMargin() {
        return industrialZMargin;
    }

    public void setIndustrialZMargin(String industrialZMargin) {
        this.industrialZMargin = industrialZMargin;
    }

    public Double getIssScaleSort() {
        return issScaleSort;
    }

    public void setIssScaleSort(Double issScaleSort) {
        this.issScaleSort = issScaleSort;
    }

    public Integer getYyRationgSort() {
        return yyRationgSort;
    }

    public void setYyRationgSort(Integer yyRationgSort) {
        this.yyRationgSort = yyRationgSort;
    }

    public String getIndustExpectation() {
        return industExpectation;
    }

    public void setIndustExpectation(String industExpectation) {
        this.industExpectation = industExpectation;
    }

    public Double getRateSort() {
        return rateSort;
    }

    public void setRateSort(Double rateSort) {
        this.rateSort = rateSort;
    }

    public Date getBookEndDate() {
        return bookEndDate;
    }

    public void setBookEndDate(Date bookEndDate) {
        this.bookEndDate = bookEndDate;
    }

    public String getIssStatus() {
        return issStatus;
    }

    public void setIssStatus(String issStatus) {
        this.issStatus = issStatus;
    }

    public Date getCancelIssueDate() {
        return cancelIssueDate;
    }

    public void setCancelIssueDate(Date cancelIssueDate) {
        this.cancelIssueDate = cancelIssueDate;
    }
    
    public Date getStopBidStartDate() {
        return stopBidStartDate;
    }

    public void setStopBidStartDate(Date stopBidStartDate) {
        this.stopBidStartDate = stopBidStartDate;
    }

    public Date getBookStartDate() {
        return bookStartDate;
    }

    public void setBookStartDate(Date bookStartDate) {
        this.bookStartDate = bookStartDate;
    }

    public String getCityInvestStatus() {
        return cityInvestStatus;
    }

    public void setCityInvestStatus(String cityInvestStatus) {
        this.cityInvestStatus = cityInvestStatus;
    }
    
    public Date getFrontBidStartDate() {
        return frontBidStartDate;
    }

    public void setFrontBidStartDate(Date frontBidStartDate) {
        this.frontBidStartDate = frontBidStartDate;
    }

    public Date getFrontBidEndDate() {
        return frontBidEndDate;
    }

    public void setFrontBidEndDate(Date frontBidEndDate) {
        this.frontBidEndDate = frontBidEndDate;
    }

    @Override
    public String toString() {
        return "BondBasicInfoClassOneDoc [bondUniCode=" + bondUniCode + ", comUniCode=" + comUniCode + ", issName=" + issName + ", bondCode=" + bondCode
                + ", bondFullName=" + bondFullName + ", bondShortName=" + bondShortName + ", issuer=" + issuer + ", bondMatu=" + bondMatu + ", matuUnitParDays="
                + matuUnitParDays + ", planIssScale=" + planIssScale + ", planIssScaleUnit=" + planIssScaleUnit + ", dmRatingScore=" + dmRatingScore
                + ", dmRatingScoreSort=" + dmRatingScoreSort + ", dmRatingScorePD=" + dmRatingScorePD + ", dmRatingDate=" + dmRatingDate + ", issRating="
                + issRating + ", bondRating=" + bondRating + ", orgRating=" + orgRating + ", ratingType=" + ratingType + ", orgPriceLower=" + orgPriceLower
                + ", orgPriceSuper=" + orgPriceSuper + ", orgMarginLower=" + orgMarginLower + ", orgMarginSuper=" + orgMarginSuper + ", fixPriceReason="
                + fixPriceReason + ", didIntervalLow=" + didIntervalLow + ", didIntervalSup=" + didIntervalSup + ", stopBidStartDate=" + stopBidStartDate
                + ", stopBidEndDate=" + stopBidEndDate + ", bookStartDate=" + bookStartDate + ", bookEndDate=" + bookEndDate + ", listDate=" + listDate
                + ", undeName=" + undeName + ", comAttrPar=" + comAttrPar + ", issCls=" + issCls + ", bondTypePar=" + bondTypePar + ", issStartDate="
                + issStartDate + ", issEndDate=" + issEndDate + ", createTime=" + createTime + ", firstMajShareholderName=" + firstMajShareholderName
                + ", enterpriseNature=" + enterpriseNature + ", proportionShareholder=" + proportionShareholder + ", subscriptionIntervalLower="
                + subscriptionIntervalLower + ", subscriptionIntervalSuper=" + subscriptionIntervalSuper + ", trade=" + trade + ", shareholderRelations="
                + shareholderRelations + ", stateOperation=" + stateOperation + ", financialStanding=" + financialStanding + ", focusOn=" + focusOn
                + ", favorite=" + favorite + ", pdDiff=" + pdDiff + ", riskWarning=" + riskWarning + ", issRatingSort=" + issRatingSort + ", actuFirIssAmut="
                + actuFirIssAmut + ", actuFirIssAmutUnit=" + actuFirIssAmutUnit + ", issCoupRate=" + issCoupRate + ", industrialSubjectScore="
                + industrialSubjectScore + ", industSubjectScoreSort=" + industSubjectScoreSort + ", industrialBondScore=" + industrialBondScore
                + ", subjectComment=" + subjectComment + ", ticketComment=" + ticketComment + ", referenceReturnsRatio=" + referenceReturnsRatio
                + ", industrialZMargin=" + industrialZMargin + ", issScaleSort=" + issScaleSort + ", yyRationgSort=" + yyRationgSort + ", industExpectation="
                + industExpectation + ", rateSort=" + rateSort + ", issStatus=" + issStatus + ", cancelIssueDate=" + cancelIssueDate + ", cityInvestStatus="
                + cityInvestStatus + ", frontBidStartDate=" + frontBidStartDate + ", frontBidEndDate=" + frontBidEndDate + "]";
    }

	

}
