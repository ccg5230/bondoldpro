package com.innodealing.model.mongo.dm;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.innodealing.json.JsonDateSerializer;

import io.swagger.annotations.ApiModelProperty;

@JsonInclude(Include.NON_NULL)
@Document(collection="pd_rank")
// @CompoundIndex(name = "issuer_time_idx", def = "{'reportTime' : 0, 'issuerId' : 1}")
public class BondPdRankDoc implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1199939327115527575L;

	@Id
	@ApiModelProperty(value = "发行人Id")
	private Long issuerId;
	
	@ApiModelProperty(value = "发行人")
	private String issuer;
	
	@ApiModelProperty(value = "财报日期")
	private String reportTime;
	
	@ApiModelProperty(value = "行业id")
	private Integer induId;
	
	@ApiModelProperty(value = "行业")
	private String induName;
	
    @ApiModelProperty(value = "申万行业", hidden = true)
    @JsonIgnore
	private Integer induIdSw;

    @ApiModelProperty(value = "申万行业", hidden = true)
    @JsonIgnore
	private String induNameSw;

	@ApiModelProperty(value = "企业性质")
	private String ownerType; 

	@ApiModelProperty(value = "所在省")
	private String province; 

	@ApiModelProperty(value = "所在市")
	private String city; 
	
	@ApiModelProperty(value = "主体量化风险等级")
	@Indexed
	private String pd;
	
	@ApiModelProperty(value = "主体量化风险值")
	@Indexed
	private Long pdNum;
	
	@ApiModelProperty(value = "主体量化风险值, 分值，来自rating_ratio_score", hidden = true)
	@Indexed
	@JsonIgnore
	private Double pdSortRRs;
	@ApiModelProperty(value = "评级")
	private String rating;
	@ApiModelProperty(value = "评级值")
	private Integer ratingNum;
	
	@ApiModelProperty(value = "风险警告标志, 量化风险等级<=CCC")
	private Boolean riskWarning;
	
	static Integer RISK_WARNING_PD_THRESHOLD = 18; //CCC 
	
	@ApiModelProperty(value = "总债券规模")
	private Double newSizeCount;
	
	@ApiModelProperty(value = "流通中债劵数")
	private Long effectiveBondCount;
	
	@ApiModelProperty(value = "评级机构")
	private String chiShortName;
	
    @ApiModelProperty(value = "最新主体评级")
    private String currRating;

    @ApiModelProperty(value = "上期主体评级")
    private String lastRating;
    
    @ApiModelProperty(value = "最新主体评级")
    @Indexed
    private Integer currR;

    @ApiModelProperty(value = "上期主体评级")
    @Indexed
    private Integer lastR;
    
    @ApiModelProperty(value = "评级时间")
    @JsonSerialize(using = JsonDateSerializer.class)
	private Date rateWritDate;
    
    @ApiModelProperty(value = "评级(评级机构)")
	private String ratingAndName;
    
    @ApiModelProperty(value = "优势")
	private Integer cceAdvt;
    
    @ApiModelProperty(value = "劣势")
	private Integer cceDisadvt;
    
    @ApiModelProperty(value = "关注点")
    private String attPoint;
    
    public String getAttPoint() {
		return attPoint;
	}

	public void setAttPoint(String attPoint) {
		this.attPoint = attPoint;
	}

	@ApiModelProperty(value = "最新主体量化风险等级")
    private String currPd;

    @ApiModelProperty(value = "上期主体量化风险等级")
    private String lastPd;
    
    @ApiModelProperty(value = "最新主体量化风险等级数值")
    @Indexed
    private Long currPdNum;

    @ApiModelProperty(value = "上期主体量化风险等级数值")
    @Indexed
    private Long lastPdNum;
    
	@ApiModelProperty(value = "上期主体量化风险警告标志, 量化风险等级<=CCC")
	private Boolean lastRiskWarning;
	
	@ApiModelProperty(value = "是否城投债")
	private Boolean munInvest;
	
    @ApiModelProperty(value = "机构所属行业")
    private Map<String,Object> institutionInduMap; 
    
	public BondPdRankDoc() {
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
	 * @return the reportTime
	 */
	public String getReportTime() {
		return reportTime;
	}

	/**
	 * @param reportTime the reportTime to set
	 */
	public void setReportTime(String reportTime) {
		this.reportTime = reportTime;
	}

	/**
	 * @return the induId
	 */
	public Integer getInduId() {
		return induId;
	}

	/**
	 * @param induId the induId to set
	 */
	public void setInduId(Integer induId) {
		this.induId = induId;
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
	 * @return the ownerType
	 */
	public String getOwnerType() {
		return ownerType;
	}

	/**
	 * @param ownerType the ownerType to set
	 */
	public void setOwnerType(String ownerType) {
		this.ownerType = ownerType;
	}

	/**
	 * @return the province
	 */
	public String getProvince() {
		return province;
	}

	/**
	 * @param province the province to set
	 */
	public void setProvince(String province) {
		this.province = province;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

   

	/**
	 * @return the pdNum
	 */
	public Long getPdNum() {
		return pdNum;
	}

	/**
	 * @param pdNum the pdNum to set
	 */
	public void setPdNum(Long pdNum) {
		this.pdNum = pdNum;
		if(pdNum!=null)
		this.riskWarning = (pdNum >= RISK_WARNING_PD_THRESHOLD);
	}

	/**
	 * @return the riskWarning
	 */
	public Boolean getRiskWarning() {
		return this.riskWarning ;
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
     * @return the induIdSw
     */
    public Integer getInduIdSw() {
        return induIdSw;
    }

    /**
     * @param induIdSw the induIdSw to set
     */
    public void setInduIdSw(Integer induIdSw) {
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

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "BondPdRankDoc [" + (issuerId != null ? "issuerId=" + issuerId + ", " : "")
                + (issuer != null ? "issuer=" + issuer + ", " : "")
                + (reportTime != null ? "reportTime=" + reportTime + ", " : "")
                + (induId != null ? "induId=" + induId + ", " : "")
                + (induName != null ? "induName=" + induName + ", " : "")
                + (induIdSw != null ? "induIdSw=" + induIdSw + ", " : "")
                + (induNameSw != null ? "induNameSw=" + induNameSw + ", " : "")
                + (ownerType != null ? "ownerType=" + ownerType + ", " : "")
                + (province != null ? "province=" + province + ", " : "") + (city != null ? "city=" + city + ", " : "")
                + (pd != null ? "pd=" + pd + ", " : "") + (pdNum != null ? "pdNum=" + pdNum + ", " : "")
                + (rating != null ? "rating=" + rating + ", " : "")
                + (riskWarning != null ? "riskWarning=" + riskWarning : "") + "]";
    }
    
	public Double getNewSizeCount() {
		return newSizeCount;
	}

	public void setNewSizeCount(Double newSizeCount) {
		this.newSizeCount = newSizeCount;
	}

	public Long getEffectiveBondCount() {
		return effectiveBondCount;
	}

	public void setEffectiveBondCount(Long effectiveBondCount) {
		this.effectiveBondCount = effectiveBondCount;
	}

	public String getChiShortName() {
		return chiShortName;
	}

	public void setChiShortName(String chiShortName) {
		this.chiShortName = chiShortName;
	}

	public Date getRateWritDate() {
		return rateWritDate;
	}

	public void setRateWritDate(Date rateWritDate) {
		this.rateWritDate = rateWritDate;
	}

	public String getCurrRating() {
		return currRating;
	}

	public void setCurrRating(String currRating) {
		this.currRating = currRating;
	}

	public String getLastRating() {
		return lastRating;
	}

	public void setLastRating(String lastRating) {
		this.lastRating = lastRating;
	}

	public Integer getCurrR() {
		return currR;
	}

	public void setCurrR(Integer currR) {
		this.currR = currR;
	}

	public Integer getLastR() {
		return lastR;
	}

	public void setLastR(Integer lastR) {
		this.lastR = lastR;
	}

	public String getRatingAndName() {
		return !StringUtils.isEmpty(this.getRating())?this.getRating()+"("+this.getChiShortName()+")":null;
	}

    /**
     * @return the pdSortRRs
     */
    public Double getPdSortRRs() {
        return pdSortRRs;
    }

    /**
     * @param pdSortRRs the pdSortRRs to set
     */
    public void setPdSortRRs(Double pdSortRRs) {
        this.pdSortRRs = pdSortRRs;
    }

	public Integer getCceAdvt() {
		return cceAdvt;
	}

	public void setCceAdvt(Integer cceAdvt) {
		this.cceAdvt = cceAdvt;
	}

	public Integer getCceDisadvt() {
		return cceDisadvt;
	}

	public void setCceDisadvt(Integer cceDisadvt) {
		this.cceDisadvt = cceDisadvt;
	}

	public Integer getRatingNum() {
		return ratingNum;
	}

	public void setRatingNum(Integer ratingNum) {
		this.ratingNum = ratingNum;
	}

	public String getCurrPd() {
		return currPd;
	}

	public void setCurrPd(String currPd) {
		this.currPd = currPd;
	}

	public String getLastPd() {
		return lastPd;
	}

	public void setLastPd(String lastPd) {
		this.lastPd = lastPd;
	}

	public Long getCurrPdNum() {
		return currPdNum;
	}

	public void setCurrPdNum(Long currPdNum) {
		this.currPdNum = currPdNum;
	}

	public Long getLastPdNum() {
		return lastPdNum;
	}

	public void setLastPdNum(Long lastPdNum) {
		this.lastPdNum = lastPdNum;
		if (lastPdNum != null)
		this.lastRiskWarning = (lastPdNum >= RISK_WARNING_PD_THRESHOLD);
	}

	public Boolean getLastRiskWarning() {
		return lastRiskWarning;
	}

	public Boolean getMunInvest() {
		return munInvest;
	}

	public void setMunInvest(Boolean munInvest) {
		this.munInvest = munInvest;
	}

	public Map<String, Object> getInstitutionInduMap() {
		return institutionInduMap;
	}

	public void setInstitutionInduMap(Map<String, Object> institutionInduMap) {
		this.institutionInduMap = institutionInduMap;
	}
	
	
}
