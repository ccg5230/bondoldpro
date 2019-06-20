package com.innodealing.model.mongo.dm;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Document(collection="bond_basic_info")
public class BondBasicInfoDoc extends BondInstDoc implements Serializable {
	
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Id
    @ApiModelProperty(value = "债券id")
    private Long bondUniCode;
    
    @Indexed
    @ApiModelProperty(value = "债券代码")
    private String code;

    @Indexed
    @ApiModelProperty(value = "债券全称")
    private String fullName;
    
    @Indexed
    @ApiModelProperty(value = "债券缩写")
    private String shortName;
    
    @ApiModelProperty(value = "券种")
    private String dmBondTypeName;
    
    @ApiModelProperty(value = "每年付息日")
    private String yearPayDate;
    
    @ApiModelProperty(value = "是否为城投")
    private Boolean munInvest;
    
    @ApiModelProperty(value = "到期日")
    @JsonFormat(pattern="yyyy/MM/dd")
    private Date theoEndDate;
    
    @ApiModelProperty(value = "剩余期限")
    private String tenor;
    
	@ApiModelProperty(value = "主体债项")
    private String issBondRating;
    
    @ApiModelProperty(value = "外部债项评级")
    private String bondRating;
    
    @ApiModelProperty(value = "评级展望")
    private String ratePros;
    
    @ApiModelProperty(value = "主体评级展望")
    private String issRatePros;
    
    @ApiModelProperty(value = "债项评级机构")
    private String bondRateOrgName;
    
    @ApiModelProperty(value = "发行票息")
    private BigDecimal issCoupRate;
    
    @ApiModelProperty(value = "票面利率")
    private BigDecimal newCoupRate;
    
    @ApiModelProperty(value = "起息日")
    @JsonFormat(pattern="yyyy/MM/dd")
    private Date inteStartDate;
    
    @ApiModelProperty(value = "付息频率")
    private Integer intePayFreq;
    
    @ApiModelProperty(value = "付息方式")
    private String intePayCls;
    
    @ApiModelProperty(value = "利率类型")
    private String rateType;

    @ApiModelProperty(value = "浮息基准")
    private String baseRate;

    @ApiModelProperty(value = "所属行业")
    private String induName;
    
    @ApiModelProperty(value = "流通市场")
    private String market;
    
    @ApiModelProperty(value = "发行人")
    private String issuer;
    
    @ApiModelProperty(value = "发行人id")
    @Indexed
    private Long issuerId;
    
    @ApiModelProperty(value = "行业id")
    private Long induId;
    
    @ApiModelProperty(value = "担保人")
    private String guruName;
    
    @ApiModelProperty(value = "质押券简称")
    private String pledgeName;
    
    @ApiModelProperty(value = "质押券代码")
    private String pledgeCode;
    
    @ApiModelProperty(value = "主体量化风险等级")
    private String pd;
    
    @ApiModelProperty(value = "违约评级")
    private String pdRating;
    
    @Indexed
    @ApiModelProperty(value = "债券原始代码", hidden = true)
    private String orgCode;
    
    @JsonFormat(pattern="yyyy/MM/dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    
    @JsonFormat(pattern="yyyy/MM/dd HH:mm:ss")
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
    
    @ApiModelProperty(value = "dm债券种类")
	private Integer dmBondType; 
    
    @ApiModelProperty(value = "当前存续状态", hidden = true) 
    @Indexed
    private Integer currStatus; //当前存续状态
    
    @ApiModelProperty(value = "发行状态", hidden = true) 
    @Indexed
    private Integer issStaPar;  //发行状态
    
    @ApiModelProperty(value = "发行上市状态", hidden = true) 
    @Indexed
    private Integer listStaPar; //发行上市状态
    
    @ApiModelProperty(value = "剩余期限", hidden = true)
    private Long tenorDays;
    
    @ApiModelProperty(value = "企业性质")
    private Integer comAttrPar; 
    
    @ApiModelProperty(value = "公司简介")
    private String comPro;
    
    @ApiModelProperty(value = "主营业务")
    private String mainBus;
    
    @ApiModelProperty(value = "兼营业务")
    private String sidBus;
    
    @ApiModelProperty(value = "是否关注")
    private Boolean isFavorited;
    
    @ApiModelProperty(value = "关注Id")
    private Integer favoriteId;
    
    @ApiModelProperty(value = "是否已经加入对比")
    private Boolean isCompared;
    
    @JsonIgnore
    private String bondCredLevel;
    
    @JsonIgnore
    private Date bondRateWritDate;

	@Indexed
    @ApiModelProperty(value = "申万行业", hidden = true)
    @JsonIgnore
    private Long induIdSw; 
    
    @ApiModelProperty(value = "申万行业", hidden = true)
    @JsonIgnore
    private String induNameSw; 
    
	@ApiModelProperty(value = "当前债券规模")
	private Double newSize;
	
    @ApiModelProperty(value = "再担保人")
    private String guraName1;
    
    @ApiModelProperty(value = "是否可赎回")
    private Integer isRedemPar;
    
    @ApiModelProperty(value = "是否可回售")
    private Integer isResaPar;
    
    @ApiModelProperty(value = "行权兑付日") 
	String exerPayDate;
    
    @ApiModelProperty(value = "发行方式")
    private String issCls;
    
    @ApiModelProperty(value = "隐含评级")
    private String impliedRating;
    
    @ApiModelProperty(value = "估值")
    private BigDecimal fairValue;
    
    @ApiModelProperty(value = "面值")
    private Double parVal;
    
    @ApiModelProperty(value = "发行价")
    private Double issPri;
    
    @ApiModelProperty(value = "待付利息本金总和")
    private Double payAmount;
    
	@ApiModelProperty(value = "风险警告标志, 量化风险等级<=CCC")
	private Boolean riskWarning;
    
	public BondBasicInfoDoc() {
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the shortName
	 */
	public String getShortName() {
		return shortName;
	}

	/**
	 * @param shortName the shortName to set
	 */
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	/**
	 * @return the dmBondTypeName
	 */
	public String getDmBondTypeName() {
		return dmBondTypeName;
	}

	/**
	 * @param dmBondTypeName the dmBondTypeName to set
	 */
	public void setDmBondTypeName(String dmBondTypeName) {
		this.dmBondTypeName = dmBondTypeName;
	}

	/**
	 * @return the yearPayDate
	 */
	public String getYearPayDate() {
		return yearPayDate;
	}

	/**
	 * @param yearPayDate the yearPayDate to set
	 */
	public void setYearPayDate(String yearPayDate) {
		this.yearPayDate = yearPayDate;
	}

	/**
	 * @return the munInvest
	 */
	public Boolean getMunInvest() {
		return munInvest;
	}

	/**
	 * @param munInvest the munInvest to set
	 */
	public void setMunInvest(Boolean munInvest) {
		this.munInvest = munInvest;
	}


	/**
	 * @return the tenor
	 */
	public String getTenor() {
		return tenor;
	}

	/**
	 * @param tenor the tenor to set
	 */
	public void setTenor(String tenor) {
		this.tenor = tenor;
	}

	/**
	 * @return the issBondRating
	 */
	public String getIssBondRating() {
		return issBondRating;
	}

	/**
	 * @param issBondRating the issBondRating to set
	 */
	public void setIssBondRating(String issBondRating) {
		this.issBondRating = issBondRating;
	}

    public String getBondRating() {
		return bondRating;
	}

	public void setBondRating(String bondRating) {
		this.bondRating = bondRating;
	}

	/**
	 * @return the ratePros
	 */
	public String getRatePros() {
		return ratePros;
	}

	/**
	 * @param ratePros the ratePros to set
	 */
	public void setRatePros(String ratePros) {
		this.ratePros = ratePros;
	}

	/**
	 * @return the issCoupRate
	 */
	public BigDecimal getIssCoupRate() {
		return issCoupRate;
	}

	/**
	 * @param issCoupRate the issCoupRate to set
	 */
	public void setIssCoupRate(BigDecimal issCoupRate) {
		this.issCoupRate = issCoupRate;
	}

	/**
	 * @return the newCoupRate
	 */
	public BigDecimal getNewCoupRate() {
		return newCoupRate;
	}

	/**
	 * @param newCoupRate the newCoupRate to set
	 */
	public void setNewCoupRate(BigDecimal newCoupRate) {
		this.newCoupRate = newCoupRate;
	}

	/**
	 * @return the intePayFreq
	 */
	public Integer getIntePayFreq() {
		return intePayFreq;
	}

	/**
	 * @param intePayFreq the intePayFreq to set
	 */
	public void setIntePayFreq(Integer intePayFreq) {
		this.intePayFreq = intePayFreq;
	}

	/**
	 * @return the intePayCls
	 */
	public String getIntePayCls() {
		return intePayCls;
	}

	/**
	 * @param intePayCls the intePayCls to set
	 */
	public void setIntePayCls(String intePayCls) {
		this.intePayCls = intePayCls;
	}

	/**
	 * @return the rateType
	 */
	public String getRateType() {
		return rateType;
	}

	/**
	 * @param rateType the rateType to set
	 */
	public void setRateType(String rateType) {
		this.rateType = rateType;
	}

	/**
	 * @return the market
	 */
	public String getMarket() {
		return market;
	}

	/**
	 * @param market the market to set
	 */
	public void setMarket(String market) {
		this.market = market;
	}

	/**
	 * @return the issuer
	 */
	public String getIssuer() {
		return issuer;
	}

	/**
	 * @param issuer the issuer to set
	 */
	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}

	/**
	 * @return the guruName
	 */
	public String getGuruName() {
		return guruName;
	}

	/**
	 * @param guruName the guruName to set
	 */
	public void setGuruName(String guruName) {
		this.guruName = guruName;
	}

	/**
	 * @return the pledgeName
	 */
	public String getPledgeName() {
		return pledgeName;
	}

	/**
	 * @param pledgeName the pledgeName to set
	 */
	public void setPledgeName(String pledgeName) {
		this.pledgeName = pledgeName;
	}

	/**
	 * @return the pledgeCode
	 */
	public String getPledgeCode() {
		return pledgeCode;
	}

	/**
	 * @param pledgeCode the pledgeCode to set
	 */
	public void setPledgeCode(String pledgeCode) {
		this.pledgeCode = pledgeCode;
	}

	/**
	 * @return the pd
	 */
	public String getPd() {
		return pd;
	}

	/**
	 * @param pd the pd to set
	 */
	public void setPd(String pd) {
		this.pd = pd;
	}


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

	/**
	 * @return the theoEndDate
	 */
	public Date getTheoEndDate() {
		return theoEndDate;
	}

	/**
	 * @param theoEndDate the theoEndDate to set
	 */
	public void setTheoEndDate(Date theoEndDate) {
		this.theoEndDate = theoEndDate;
	}

	/**
	 * @return the baseRate
	 */
	public String getBaseRate() {
		return baseRate;
	}

	/**
	 * @param baseRate the baseRate to set
	 */
	public void setBaseRate(String baseRate) {
		this.baseRate = baseRate;
	}

	/* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "BondBasicInfoDoc [" + (bondUniCode != null ? "bondUniCode=" + bondUniCode + ", " : "")
                + (code != null ? "code=" + code + ", " : "") + (fullName != null ? "fullName=" + fullName + ", " : "")
                + (shortName != null ? "shortName=" + shortName + ", " : "")
                + (dmBondTypeName != null ? "dmBondTypeName=" + dmBondTypeName + ", " : "")
                + (yearPayDate != null ? "yearPayDate=" + yearPayDate + ", " : "")
                + (munInvest != null ? "munInvest=" + munInvest + ", " : "")
                + (theoEndDate != null ? "theoEndDate=" + theoEndDate + ", " : "")
                + (tenor != null ? "tenor=" + tenor + ", " : "")
                + (issBondRating != null ? "issBondRating=" + issBondRating + ", " : "")
                + (ratePros != null ? "ratePros=" + ratePros + ", " : "")
                + (issRatePros != null ? "issRatePros=" + issRatePros + ", " : "")
                + (issCoupRate != null ? "issCoupRate=" + issCoupRate + ", " : "")
                + (newCoupRate != null ? "newCoupRate=" + newCoupRate + ", " : "")
                + (inteStartDate != null ? "inteStartDate=" + inteStartDate + ", " : "")
                + (intePayFreq != null ? "intePayFreq=" + intePayFreq + ", " : "")
                + (intePayCls != null ? "intePayCls=" + intePayCls + ", " : "")
                + (rateType != null ? "rateType=" + rateType + ", " : "")
                + (baseRate != null ? "baseRate=" + baseRate + ", " : "")
                + (induName != null ? "induName=" + induName + ", " : "")
                + (market != null ? "market=" + market + ", " : "") + (issuer != null ? "issuer=" + issuer + ", " : "")
                + (issuerId != null ? "issuerId=" + issuerId + ", " : "")
                + (induId != null ? "induId=" + induId + ", " : "")
                + (guruName != null ? "guruName=" + guruName + ", " : "")
                + (pledgeName != null ? "pledgeName=" + pledgeName + ", " : "")
                + (pledgeCode != null ? "pledgeCode=" + pledgeCode + ", " : "") + (pd != null ? "pd=" + pd + ", " : "")
                + (pdRating != null ? "pdRating=" + pdRating + ", " : "")
                + (orgCode != null ? "orgCode=" + orgCode + ", " : "")
                + (createTime != null ? "createTime=" + createTime + ", " : "")
                + (updateTime != null ? "updateTime=" + updateTime + ", " : "")
                + (dmBondType != null ? "dmBondType=" + dmBondType + ", " : "")
                + (currStatus != null ? "currStatus=" + currStatus + ", " : "")
                + (issStaPar != null ? "issStaPar=" + issStaPar + ", " : "")
                + (listStaPar != null ? "listStaPar=" + listStaPar + ", " : "")
                + (tenorDays != null ? "tenorDays=" + tenorDays + ", " : "")
                + (comAttrPar != null ? "comAttrPar=" + comAttrPar + ", " : "")
                + (comPro != null ? "comPro=" + comPro + ", " : "")
                + (mainBus != null ? "mainBus=" + mainBus + ", " : "")
                + (sidBus != null ? "sidBus=" + sidBus + ", " : "")
                + (isFavorited != null ? "isFavorited=" + isFavorited + ", " : "")
                + (favoriteId != null ? "favoriteId=" + favoriteId + ", " : "")
                + (isCompared != null ? "isCompared=" + isCompared + ", " : "")
                + (induIdSw != null ? "induIdSw=" + induIdSw + ", " : "")
                + (induNameSw != null ? "induNameSw=" + induNameSw : "") + "]";
    }

	/**
	 * @return the orgCode
	 */
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * @param orgCode the orgCode to set
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 * @return the issuerId
	 */
	public Long getIssuerId() {
		return issuerId;
	}

	/**
	 * @param issuerId the issuerId to set
	 */
	public void setIssuerId(Long issuerId) {
		this.issuerId = issuerId;
	}

	/**
	 * @return the induId
	 */
	public Long getInduId() {
		return induId;
	}

	/**
	 * @param long1 the induId to set
	 */
	public void setInduId(Long long1) {
		this.induId = long1;
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

	/**
	 * @return the inteStartDate
	 */
	public Date getInteStartDate() {
		return inteStartDate;
	}

	/**
	 * @param inteStartDate the inteStartDate to set
	 */
	public void setInteStartDate(Date inteStartDate) {
		this.inteStartDate = inteStartDate;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the updateTime
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * @param updateTime the updateTime to set
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * @return the dmBondType
	 */
	public Integer getDmBondType() {
		return dmBondType;
	}

	/**
	 * @param dmBondType the dmBondType to set
	 */
	public void setDmBondType(Integer dmBondType) {
		this.dmBondType = dmBondType;
	}

	/**
	 * @return the currStatus
	 */
	public Integer getCurrStatus() {
		return currStatus;
	}

	/**
	 * @param currStatus the currStatus to set
	 */
	public void setCurrStatus(Integer currStatus) {
		this.currStatus = currStatus;
	}

	/**
	 * @return the issStaPar
	 */
	public Integer getIssStaPar() {
		return issStaPar;
	}

	/**
	 * @param issStaPar the issStaPar to set
	 */
	public void setIssStaPar(Integer issStaPar) {
		this.issStaPar = issStaPar;
	}

	/**
	 * @return the listStaPar
	 */
	public Integer getListStaPar() {
		return listStaPar;
	}

	/**
	 * @param listStaPar the listStaPar to set
	 */
	public void setListStaPar(Integer listStaPar) {
		this.listStaPar = listStaPar;
	}

	/**
	 * @return the tenorDays
	 */
	public Long getTenorDays() {
		return tenorDays;
	}

	/**
	 * @param tenorDays the tenorDays to set
	 */
	public void setTenorDays(Long tenorDays) {
		this.tenorDays = tenorDays;
	}

	/**
	 * @return the fullName
	 */
	public String getFullName() {
		return fullName;
	}

	/**
	 * @param fullName the fullName to set
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

    public Integer getComAttrPar() {
        return comAttrPar;
    }

    public void setComAttrPar(Integer comAttrPar) {
        this.comAttrPar = comAttrPar;
    }

    public String getPdRating() {
        return pdRating;
    }

    public void setPdRating(String pdRating) {
        this.pdRating = pdRating;
    }

	public String getComPro() {
		return comPro;
	}

	public void setComPro(String comPro) {
		this.comPro = comPro;
	}

	public String getMainBus() {
		return mainBus;
	}

	public void setMainBus(String mainBus) {
		this.mainBus = mainBus;
	}

	public String getSidBus() {
		return sidBus;
	}

	public void setSidBus(String sidBus) {
		this.sidBus = sidBus;
	}

	public Boolean getIsFavorited() {
		return isFavorited;
	}

	public void setIsFavorited(Boolean isFavorited) {
		this.isFavorited = isFavorited;
	}

	public Integer getFavoriteId() {
		return favoriteId;
	}

	public void setFavoriteId(Integer favoriteId) {
		this.favoriteId = favoriteId;
	}

	public Boolean getIsCompared() {
		return isCompared;
	}

	public void setIsCompared(Boolean isCompared) {
		this.isCompared = isCompared;
	}

    /**
     * @return the issRatePros
     */
    public String getIssRatePros() {
        return issRatePros;
    }

    /**
     * @param issRatePros the issRatePros to set
     */
    public void setIssRatePros(String issRatePros) {
        this.issRatePros = issRatePros;
    }

    /**
     * @return the induIdSw
     */
    public Long getInduIdSw() {
        return induIdSw;
    }

    /**
     * @param induIdSw the induIdSw to set
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

	public Double getNewSize() {
		return newSize;
	}

	public void setNewSize(Double newSize) {
		this.newSize = newSize;
	}

	public String getGuraName1() {
		return guraName1;
	}

	public void setGuraName1(String guraName1) {
		this.guraName1 = guraName1;
	}

	public Integer getIsRedemPar() {
		return isRedemPar;
	}

	public void setIsRedemPar(Integer isRedemPar) {
		this.isRedemPar = isRedemPar;
	}

	public Integer getIsResaPar() {
		return isResaPar;
	}

	public void setIsResaPar(Integer isResaPar) {
		this.isResaPar = isResaPar;
	}

	public String getExerPayDate() {
		return exerPayDate;
	}

	public void setExerPayDate(String exerPayDate) {
		this.exerPayDate = exerPayDate;
	}

	public String getIssCls() {
		return issCls;
	}

	public void setIssCls(String issCls) {
		this.issCls = issCls;
	}

	public String getBondCredLevel() {
		return bondCredLevel;
	}

	public void setBondCredLevel(String bondCredLevel) {
		this.bondCredLevel = bondCredLevel;
	}

	public Date getBondRateWritDate() {
		return bondRateWritDate;
	}

	public void setBondRateWritDate(Date bondRateWritDate) {
		this.bondRateWritDate = bondRateWritDate;
	}

	public String getBondRateOrgName() {
		return bondRateOrgName;
	}

	public void setBondRateOrgName(String bondRateOrgName) {
		this.bondRateOrgName = bondRateOrgName;
	}

	public String getImpliedRating() {
		return impliedRating;
	}

	public void setImpliedRating(String impliedRating) {
		this.impliedRating = impliedRating;
	}

	public BigDecimal getFairValue() {
		return fairValue;
	}

	public void setFairValue(BigDecimal fairValue) {
		this.fairValue = fairValue;
	}

    /**
     * @return the parVal
     */
    public Double getParVal() {
        return parVal;
    }

    /**
     * @param parVal the parVal to set
     */
    public void setParVal(Double parVal) {
        this.parVal = parVal;
    }

    /**
     * @return the issPri
     */
    public Double getIssPri() {
        return issPri;
    }

    /**
     * @param issPri the issPri to set
     */
    public void setIssPri(Double issPri) {
        this.issPri = issPri;
    }
    
	public Double getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(Double payAmount) {
		this.payAmount = payAmount;
	}

	public Boolean getRiskWarning() {
		return riskWarning;
	}

	public void setRiskWarning(Boolean riskWarning) {
		this.riskWarning = riskWarning;
	}

	

	
}
