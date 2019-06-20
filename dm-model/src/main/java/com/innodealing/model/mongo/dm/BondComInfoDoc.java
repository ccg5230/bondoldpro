package com.innodealing.model.mongo.dm;


import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;

@JsonInclude(Include.NON_NULL)
@Document(collection="bond_com_info")
public class BondComInfoDoc extends BondInstDoc implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -2336302348540912391L;

	@Id
	@ApiModelProperty(value = "DM-公司编号")
    private Long comUniCode;
    
	@ApiModelProperty(value = "公司名")
    private String comChiName;
   
	@ApiModelProperty(value = "安硕-公司编号")
    private Long amaComId;

    @Indexed
    @ApiModelProperty(value = "GICS行业编号")
    private Long induId;
    
    @ApiModelProperty(value = "GICS行业")
    private String induName;
    
    @ApiModelProperty(value = "申万行业", hidden = true)
    @Indexed
    @JsonIgnore
    private Long induIdSw; 
    
    @ApiModelProperty(value = "申万行业", hidden = true)
    @JsonIgnore
    private String induNameSw; 
    
    @ApiModelProperty(value = "主体量化风险等级")
    private String pd;
    
    @ApiModelProperty(value = "主体量化风险等级-变化")
    private Long pdDiff;
    
    @ApiModelProperty(value = "主体量化风险等级-数值")
    private Long pdNum;
    
    //显示季度
    @ApiModelProperty(value = "主体量化风险等级-更新时间")
    private String pdTime;
    
    @ApiModelProperty(value = "历史最高风险等级")
    private String worstPd;
    
    @ApiModelProperty(value = "历史最高风险值")
    private Long worstPdNum;
    
    //显示季度
    @ApiModelProperty(value = "历史最高风险等级-更新时间")
    private String worstPdTime;
    
    @ApiModelProperty(value = "主体评级")
	private String issCredLevel; //发行人评级
    
    @ApiModelProperty(value = "评级时间")
    private Date rateWritDate; //评级时间

	@ApiModelProperty(value = "实际违约债券名")
    private String defaultBondName;
    
    @ApiModelProperty(value = "实际违约事件描述")
    private String defaultEvent;
    
    @ApiModelProperty(value = "实际违约事件日期")
    @JsonFormat(pattern="yyyy/MM/dd")
    private Date defaultDate;
    
	Integer comAttrPar; //企业性质
	Long areaUniCode1; //发行人地址省
	Long areaUniCode; //发行人所在区/市
	
	@ApiModelProperty(value = "舆论正面总数")
    private Integer sentimentPositive;
    
    @ApiModelProperty(value = "舆论负面总数")
    private Integer sentimentNegative;
    
    @ApiModelProperty(value = "舆论中性总数")
    private Integer sentimentNeutral;
    
    @ApiModelProperty(value = "最近一个月舆情总数")
    private Integer sentimentMonthCount;
    
	@ApiModelProperty(value = "风险警告标志, 量化风险等级<=CCC")
	private Boolean riskWarning;
	
	static Integer RISK_WARNING_PD_THRESHOLD = 18; //CCC 
	
	@ApiModelProperty(value = "省")
	private String areaName1;
	
	@ApiModelProperty(value = "省code")
	private BigInteger areaCode1;

	@ApiModelProperty(value = "地级市")
	private String areaName2;
	
	@ApiModelProperty(value = "市/县code")
	private BigInteger areaCode2;
	
	@ApiModelProperty(value = "评级展望")
	private String rateProsPar;
	
	@ApiModelProperty(value = "流通中债劵数")
	private Long effectiveBondCount;
	
    @ApiModelProperty(value = "历史最高风险警告标志, 量化风险等级<=CCC")
    private Boolean worstRiskWarning;
    
	@ApiModelProperty(value = "总债券规模")
	private Double newSizeCount;
	
	@ApiModelProperty(value = "到期债券规模")
	private Double maturedSize;
	
	@ApiModelProperty(value = "待付利息本金总额")
	private Double payAmount;
	
    @ApiModelProperty(value = "债券类型，利率债:1 信用债:2")
    private Integer bondType;
    
    @ApiModelProperty(value = "历史最差外部评级")
    private String worstRating;
    
    @ApiModelProperty(value = "历史最差外部评级值")
    private Long worstRatingNum;
    
    //显示季度
    @ApiModelProperty(value = "历史最差外部评级-评级时间")
    private String worstRatingTime;
    
    @ApiModelProperty(value = "发行规模描述")
    private String sizeDesc;
    
	@ApiModelProperty(value = "是否城投债")
	private Boolean munInvest;
	
    
	/**
	 * @return the riskWarning
	 */
	public Boolean getRiskWarning() {
		return riskWarning ;
	}
	
	/**
	 * @return the comUniCode
	 */
	public Long getComUniCode() {
		return comUniCode;
	}

	/**
	 * @param comUniCode the comUniCode to set
	 */
	public void setComUniCode(Long comUniCode) {
		this.comUniCode = comUniCode;
	}

	/**
	 * @return the comChiName
	 */
	public String getComChiName() {
		return comChiName;
	}

	/**
	 * @param comChiName the comChiName to set
	 */
	public void setComChiName(String comChiName) {
		this.comChiName = comChiName;
	}

	/**
	 * @return the amaComId
	 */
	public Long getAmaComId() {
		return amaComId;
	}

	/**
	 * @param amaComId the amaComId to set
	 */
	public void setAmaComId(Long amaComId) {
		this.amaComId = amaComId;
	}

	/**
	 * @return the induUniCode
	 */
	public Long getInduId() {
		return induId;
	}

	/**
	 * @param induUniCode the induUniCode to set
	 */
	public void setInduId(Long induId) {
		this.induId = induId;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the induName
	 */
	public String getInduName() {
		return induName;
	}

	/**
	 * @param induName the induName to set
	 */
	public void setInduName(String induName) {
		this.induName = induName;
	}

	/* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "BondComInfoDoc [" + (comUniCode != null ? "comUniCode=" + comUniCode + ", " : "")
                + (comChiName != null ? "comChiName=" + comChiName + ", " : "")
                + (amaComId != null ? "amaComId=" + amaComId + ", " : "")
                + (induId != null ? "induId=" + induId + ", " : "")
                + (induName != null ? "induName=" + induName + ", " : "")
                + (induIdSw != null ? "induIdSw=" + induIdSw + ", " : "")
                + (induNameSw != null ? "induNameSw=" + induNameSw + ", " : "") + (pd != null ? "pd=" + pd + ", " : "")
                + (pdDiff != null ? "pdDiff=" + pdDiff + ", " : "") + (pdNum != null ? "pdNum=" + pdNum + ", " : "")
                + (pdTime != null ? "pdTime=" + pdTime + ", " : "")
                + (worstPd != null ? "worstPd=" + worstPd + ", " : "")
                + (worstPdNum != null ? "worstPdNum=" + worstPdNum + ", " : "")
                + (worstPdTime != null ? "worstPdTime=" + worstPdTime + ", " : "")
                + (issCredLevel != null ? "issCredLevel=" + issCredLevel + ", " : "")
                + (defaultBondName != null ? "defaultBondName=" + defaultBondName + ", " : "")
                + (defaultEvent != null ? "defaultEvent=" + defaultEvent + ", " : "")
                + (defaultDate != null ? "defaultDate=" + defaultDate + ", " : "")
                + (comAttrPar != null ? "comAttrPar=" + comAttrPar + ", " : "")
                + (areaUniCode1 != null ? "areaUniCode1=" + areaUniCode1 + ", " : "")
                + (areaUniCode != null ? "areaUniCode=" + areaUniCode + ", " : "")
                + (sentimentPositive != null ? "sentimentPositive=" + sentimentPositive + ", " : "")
                + (sentimentNegative != null ? "sentimentNegative=" + sentimentNegative + ", " : "")
                + (sentimentNeutral != null ? "sentimentNeutral=" + sentimentNeutral + ", " : "")
                + (sentimentMonthCount != null ? "sentimentMonthCount=" + sentimentMonthCount + ", " : "")
                + (riskWarning != null ? "riskWarning=" + riskWarning + ", " : "")
                + (areaName1 != null ? "areaName1=" + areaName1 + ", " : "")
                + (areaCode1 != null ? "areaCode1=" + areaCode1 + ", " : "")
                + (areaName2 != null ? "areaName2=" + areaName2 + ", " : "")
                + (areaCode2 != null ? "areaCode2=" + areaCode2 + ", " : "")
                + (rateProsPar != null ? "rateProsPar=" + rateProsPar + ", " : "")
                + (effectiveBondCount != null ? "effectiveBondCount=" + effectiveBondCount + ", " : "")
                + (worstRiskWarning != null ? "worstRiskWarning=" + worstRiskWarning : "") + "]";
    }

	/**
	 * @return the pd
	 */
	public String getPd() {
		return pd;
	}

	/**
	 * @return the pdDiff
	 */
	public Long getPdDiff() {
		return pdDiff;
	}

	/**
	 * @return the issCredLevel
	 */
	public String getIssCredLevel() {
		return issCredLevel;
	}

	/**
	 * @param issCredLevel the issCredLevel to set
	 */
	public void setIssCredLevel(String issCredLevel) {
		this.issCredLevel = issCredLevel;
	}

	/**
	 * @return the comAttrPar
	 */
	public Integer getComAttrPar() {
		return comAttrPar;
	}

	/**
	 * @param comAttrPar the comAttrPar to set
	 */
	public void setComAttrPar(Integer comAttrPar) {
		this.comAttrPar = comAttrPar;
	}

	/**
	 * @return the areaUniCode1
	 */
	public Long getAreaUniCode1() {
		return areaUniCode1;
	}

	/**
	 * @param areaUniCode1 the areaUniCode1 to set
	 */
	public void setAreaUniCode1(Long areaUniCode1) {
		this.areaUniCode1 = areaUniCode1;
	}

	public Integer getSentimentPositive() {
		return sentimentPositive;
	}

	public void setSentimentPositive(Integer sentimentPositive) {
		this.sentimentPositive = sentimentPositive;
	}

	public Integer getSentimentNegative() {
		return sentimentNegative;
	}

	public void setSentimentNegative(Integer sentimentNegative) {
		this.sentimentNegative = sentimentNegative;
	}

	public Integer getSentimentNeutral() {
		return sentimentNeutral;
	}

	public void setSentimentNeutral(Integer sentimentNeutral) {
		this.sentimentNeutral = sentimentNeutral;
	}

	public Long getAreaUniCode() {
		return areaUniCode;
	}

	public void setAreaUniCode(Long areaUniCode) {
		this.areaUniCode = areaUniCode;
	}

	/**
	 * @return the pdTime
	 */
	public String getPdTime() {
		return pdTime;
	}

	/**
	 * @param pdTime the pdTime to set
	 */
	public void setPdTime(String pdTime) {
		this.pdTime = pdTime;
	}

	public Integer getSentimentMonthCount() {
		return sentimentMonthCount;
	}

	public void setSentimentMonthCount(Integer sentimentMonthCount) {
		this.sentimentMonthCount = sentimentMonthCount;
	}
    
	/**
	 * @return the pdNum
	 */
	public Long getPdNum() {
		return pdNum;
	}


	/**
	 * @return the pdNum
	 */
	public Long getPdUIOpt() {
		return pdNum;
	}

	/**
	 * @param pdNum the pdNum to set
	 */
	public void setPdNum(Long pdNum) {
		this.pdNum = pdNum;
		this.riskWarning = (pdNum >= RISK_WARNING_PD_THRESHOLD);
	}

	/**
	 * @param pd the pd to set
	 */
	public void setPd(String pd) {
		this.pd = pd;
	}

	/**
	 * @param pdDiff the pdDiff to set
	 */
	public void setPdDiff(Long pdDiff) {
		this.pdDiff = pdDiff;
	}

	/**
	 * @return the worstPd
	 */
	public String getWorstPd() {
		return worstPd;
	}

	/**
	 * @param worstPd the worstPd to set
	 */
	public void setWorstPd(String worstPd) {
		this.worstPd = worstPd;
	}

	/**
	 * @return the worstPdNum
	 */
	public Long getWorstPdNum() {
		return worstPdNum;
	}

	/**
	 * @param worstPdNum the worstPdNum to set
	 */
	public void setWorstPdNum(Long worstPdNum) {
		this.worstPdNum = worstPdNum;
		if(worstPdNum!=null)
		this.worstRiskWarning = (worstPdNum >= RISK_WARNING_PD_THRESHOLD);
	}

	/**
	 * @return the worstPdTime
	 */
	public String getWorstPdTime() {
		return worstPdTime;
	}

	/**
	 * @param worstPdTime the worstPdTime to set
	 */
	public void setWorstPdTime(String worstPdTime) {
		this.worstPdTime = worstPdTime;
	}

	/**
	 * @return the defaultBondName
	 */
	public String getDefaultBondName() {
		return defaultBondName;
	}

	/**
	 * @param defaultBondName the defaultBondName to set
	 */
	public void setDefaultBondName(String defaultBondName) {
		this.defaultBondName = defaultBondName;
	}

	/**
	 * @return the defaultEvent
	 */
	public String getDefaultEvent() {
		return defaultEvent;
	}

	/**
	 * @param defaultEvent the defaultEvent to set
	 */
	public void setDefaultEvent(String defaultEvent) {
		this.defaultEvent = defaultEvent;
	}

	/**
	 * @return the defaultDate
	 */
	public Date getDefaultDate() {
		return defaultDate;
	}

	/**
	 * @param defaultDate the defaultDate to set
	 */
	public void setDefaultDate(Date defaultDate) {
		this.defaultDate = defaultDate;
	}

	public String getAreaName1() {
		return areaName1;
	}

	public void setAreaName1(String areaName1) {
		this.areaName1 = areaName1;
	}

	public BigInteger getAreaCode1() {
		return areaCode1;
	}

	public void setAreaCode1(BigInteger areaCode1) {
		this.areaCode1 = areaCode1;
	}

	public String getAreaName2() {
		return areaName2;
	}

	public void setAreaName2(String areaName2) {
		this.areaName2 = areaName2;
	}

	public BigInteger getAreaCode2() {
		return areaCode2;
	}

	public void setAreaCode2(BigInteger areaCode2) {
		this.areaCode2 = areaCode2;
	}

	public String getRateProsPar() {
		return rateProsPar;
	}

	public void setRateProsPar(String rateProsPar) {
		this.rateProsPar = rateProsPar;
	}

	public Long getEffectiveBondCount() {
		return effectiveBondCount;
	}

	public void setEffectiveBondCount(Long effectiveBondCount) {
		this.effectiveBondCount = effectiveBondCount;
	}

	public Boolean getWorstRiskWarning() {
		return worstRiskWarning;
	}

    /**
     * @return the induIdSw
     */
    public Long getInduIdSw() {
        return induIdSw;
    }

    /**
     * @param induUniCodeSw the induUniCodeSw to set
     */
    public void setInduIdSw(Long induIdSw) {
        this.induIdSw = induIdSw;
    }

    /**
     * @return the induNameSw
     */
    public String getInduNameSw() {
        return induNameSw;
    }

    /**
     * @param induNameSw the induNameSw to set
     */
    public void setInduNameSw(String induNameSw) {
        this.induNameSw = induNameSw;
    }

    /**
     * @return the bondType
     */
    public Integer getBondType() {
        return bondType;
    }

    /**
     * @param bondType the bondType to set
     */
    synchronized public void setBondType(Integer bondType) {
        this.bondType = bondType;
    }
    
	public Double getNewSizeCount() {
		return newSizeCount;
	}

	public void setNewSizeCount(Double newSizeCount) {
		this.newSizeCount = newSizeCount;
	}

	public String getWorstRating() {
		return worstRating;
	}

	public void setWorstRating(String worstRating) {
		this.worstRating = worstRating;
	}

	public Long getWorstRatingNum() {
		return worstRatingNum;
	}

	public void setWorstRatingNum(Long worstRatingNum) {
		this.worstRatingNum = worstRatingNum;
	}

	public String getWorstRatingTime() {
		return worstRatingTime;
	}

	public void setWorstRatingTime(String worstRatingTime) {
		this.worstRatingTime = worstRatingTime;
	}

    public Date getRateWritDate() {
		return rateWritDate;
	}

	public void setRateWritDate(Date rateWritDate) {
		this.rateWritDate = rateWritDate;
	}

	public Double getMaturedSize() {
		return maturedSize;
	}

	public void setMaturedSize(Double maturedSize) {
		this.maturedSize = maturedSize;
	}

	public Double getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(Double payAmount) {
		this.payAmount = payAmount;
	}

	public String getSizeDesc() {
		return sizeDesc;
	}

	public void setSizeDesc(String sizeDesc) {
		this.sizeDesc = sizeDesc;
	}

	public Boolean getMunInvest() {
		return munInvest;
	}

	public void setMunInvest(Boolean munInvest) {
		this.munInvest = munInvest;
	}

	
	

}
