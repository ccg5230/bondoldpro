package com.innodealing.domain.dto;

import java.math.BigDecimal;

/**
 * Created by Frank on 4/7/2017.
 */
public class Company {
    private Integer id;
    private Integer assetScale;
    private Integer cityId;
    private String cityName;
    private String fullName;
    private Integer grade;
    private Integer orgId;
    private Integer provinceId;
    private String provinceName;
    private String shortName;
    private Integer type;

    private BigDecimal capitalAdequacyRatio;
    private BigDecimal defectRate;
    private String externalRating;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAssetScale() {
        return assetScale;
    }

    public void setAssetScale(Integer assetScale) {
        this.assetScale = assetScale;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public BigDecimal getCapitalAdequacyRatio() {
        return capitalAdequacyRatio;
    }

    public void setCapitalAdequacyRatio(BigDecimal capitalAdequacyRatio) {
        this.capitalAdequacyRatio = capitalAdequacyRatio;
    }

    public BigDecimal getDefectRate() {
        return defectRate;
    }

    public void setDefectRate(BigDecimal defectRate) {
        this.defectRate = defectRate;
    }

    public String getExternalRating() {
        return externalRating;
    }

    public void setExternalRating(String externalRating) {
        this.externalRating = externalRating;
    }
}
