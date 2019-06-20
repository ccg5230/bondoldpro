package com.innodealing.model;

import java.util.Date;



public class BondPdRankSrc {

	private Long issuerId;
	private String issuer;
	private String reportTime;
	private Integer induId;
	private String induName;
	private Integer induIdSw;
	private String induNameSw;
	private String ownerType; 
	private String province; 
	private String city; 
	private String pd;
	private Long pdNum;
	private String rating;
	private Integer comAttrPar;
	private String chiShortName;
	private Date rateWritDate;
	private Integer cceAdvt;
	private Integer cceDisadvt;
	
	private String attPoint;
	
	
	public String getAttPoint() {
		return attPoint;
	}
	public void setAttPoint(String attPoint) {
		this.attPoint = attPoint;
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
	
	public String getRating() {
        return rating;
    }
    public void setRating(String rating) {
        this.rating = rating;
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
	}
	/* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "BondPdRankSrc [" + (issuerId != null ? "issuerId=" + issuerId + ", " : "")
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
                + (comAttrPar != null ? "comAttrPar=" + comAttrPar : "") + "]";
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
	

	
    
}
