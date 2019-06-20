package com.innodealing.domain;

/**
 * @author stephen.ma
 * @date 2016年10月26日
 * @clasename SysuserInstDto.java
 * @decription TODO
 */
public class SysuserInstDto {

	private Integer userId;
	private String userName;
	private String spellName;
	private String company;
	private String companyShort;
	private Integer orgainfoId;
	private String cityName;
	private Integer instGrade;
	private Integer organType; 
	private Integer assetScope;
    private Integer sectionType;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSpellName() {
		return spellName;
	}

	public void setSpellName(String spellName) {
		this.spellName = spellName;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getCompanyShort() {
		return companyShort;
	}

	public void setCompanyShort(String companyShort) {
		this.companyShort = companyShort;
	}

	public Integer getOrgainfoId() {
		return orgainfoId;
	}

	public void setOrgainfoId(Integer orgainfoId) {
		this.orgainfoId = orgainfoId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Integer getInstGrade() {
		return instGrade;
	}

	public void setInstGrade(Integer instGrade) {
		this.instGrade = instGrade;
	}

	public Integer getOrganType() {
		return organType;
	}

	public void setOrganType(Integer organType) {
		this.organType = organType;
	}

    public Integer getAssetScope() {
        return assetScope;
    }

    public void setAssetScope(Integer assetScope) {
        this.assetScope = assetScope;
    }

    public Integer getSectionType() {
        return sectionType;
    }

    public void setSectionType(Integer sectionType) {
        this.sectionType = sectionType;
    }
	
}
