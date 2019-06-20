package com.innodealing.domain;

import java.util.Date;

/**
 * @author stephen.ma
 * @date 2016年9月5日
 * @decription 联表查询一对一数据,单表一对一
 */
public class SysuserAndOrgainfo {
    //user info
	private Integer id;
    private String name;
    private String spellname;
    private Long usertype;
    private String companyshort;
    private String companytype;
    private String companyphone;
    private String qq;
    private String wechatno;
    private String mobile;
    private String instprovince;
    private Integer onlinestate;
    private Integer imflag;
    private Integer instcity;
    //orgainfo
    private Integer orgainfoId;
    private Integer orgainfoType;
    private String orgainFullName;
    private String orgainShortName;
    private Integer orgainProvinceid;
    private Integer assetScale;
    private Integer sectionType;
    private String cityName;
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSpellname() {
		return spellname;
	}
	public void setSpellname(String spellname) {
		this.spellname = spellname;
	}
	public Long getUsertype() {
		return usertype;
	}
	public void setUsertype(Long usertype) {
		this.usertype = usertype;
	}
	public String getCompanyshort() {
		return companyshort;
	}
	public void setCompanyshort(String companyshort) {
		this.companyshort = companyshort;
	}
	public String getCompanytype() {
		return companytype;
	}
	public void setCompanytype(String companytype) {
		this.companytype = companytype;
	}
	public String getCompanyphone() {
		return companyphone;
	}
	public void setCompanyphone(String companyphone) {
		this.companyphone = companyphone;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getWechatno() {
		return wechatno;
	}
	public void setWechatno(String wechatno) {
		this.wechatno = wechatno;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getInstprovince() {
		return instprovince;
	}
	public void setInstprovince(String instprovince) {
		this.instprovince = instprovince;
	}
	public Integer getOnlinestate() {
		return onlinestate;
	}
	public void setOnlinestate(Integer onlinestate) {
		this.onlinestate = onlinestate;
	}
	public Integer getImflag() {
		return imflag;
	}
	public void setImflag(Integer imflag) {
		this.imflag = imflag;
	}
	public Integer getInstcity() {
		return instcity;
	}
	public void setInstcity(Integer instcity) {
		this.instcity = instcity;
	}
	public Integer getOrgainfoId() {
		return orgainfoId;
	}
	public void setOrgainfoId(Integer orgainfoId) {
		this.orgainfoId = orgainfoId;
	}
	public Integer getOrgainfoType() {
		return orgainfoType;
	}
	public void setOrgainfoType(Integer orgainfoType) {
		this.orgainfoType = orgainfoType;
	}
	public String getOrgainFullName() {
		return orgainFullName;
	}
	public void setOrgainFullName(String orgainFullName) {
		this.orgainFullName = orgainFullName;
	}
	public String getOrgainShortName() {
		return orgainShortName;
	}
	public void setOrgainShortName(String orgainShortName) {
		this.orgainShortName = orgainShortName;
	}
	public Integer getOrgainProvinceid() {
		return orgainProvinceid;
	}
	public void setOrgainProvinceid(Integer orgainProvinceid) {
		this.orgainProvinceid = orgainProvinceid;
	}
	public Integer getAssetScale() {
		return assetScale;
	}
	public void setAssetScale(Integer assetScale) {
		this.assetScale = assetScale;
	}
	public Integer getSectionType() {
		return sectionType;
	}
	public void setSectionType(Integer sectionType) {
		this.sectionType = sectionType;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
}
