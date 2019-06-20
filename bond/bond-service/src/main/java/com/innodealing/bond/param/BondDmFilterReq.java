package com.innodealing.bond.param;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.innodealing.model.dm.bond.BondIndicatorFilterReq;
import com.innodealing.model.mongo.dm.BondIssuerDoc;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
@JsonInclude(Include.NON_NULL)
public class BondDmFilterReq implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4623706192249175407L;

	@ApiModelProperty(value = "用户编号")
	private Long userId;

	@ApiModelProperty(value = "筛选方案名", required = true, example = "我的筛选方案1")
	private String filterName;

	@ApiModelProperty(value = "违约概率, 列表元素并列关系，取值范围1~5, 含义参考dmdb.t_pub_par(PAR_SYS_CODE:2001)")
	private List<Integer> pds;

	@ApiModelProperty(value = "期限, 列表元素并列关系，取值范围1~9, 含义参考dmdb.t_pub_par(PAR_SYS_CODE:2004)")
	private List<Integer> tenors;

	@ApiModelProperty(value = "dm债券种类")
	private List<Integer> dmBondTypes;

	@ApiModelProperty(value = "行业列表")
	private List<Integer> induIds;

	@ApiModelProperty(value = "发行人id")
	private BondIssuerDoc issuer;

	@ApiModelProperty(value = "地域")
	private List<Integer> regions;

	@ApiModelProperty(value = "企业(所有制), 列表元素并列关系，取值范围1~3, 含义参考dmdb.t_pub_par(PAR_SYS_CODE:2006)")
	private List<Integer> ownerTypes;

	@ApiModelProperty(value = "债项评级, 列表元素并列关系，取值范围1~7,99, 含义参考dmdb.t_pub_par(PAR_SYS_CODE:2008)")
	private List<Integer> bondRatings;

	@ApiModelProperty(value = "隐含评级, 列表元素并列关系，取值范围1~7,99, 含义参考dmdb.t_pub_par(PAR_SYS_CODE:2008)")
	private List<Integer> impliedRatings;

	@ApiModelProperty(value = "发行人评级, 列表元素并列关系，取值范围1~6,99, 含义参考dmdb.t_pub_par(PAR_SYS_CODE:2008)")
	private List<Integer> issRatings;

	@ApiModelProperty(value = "市场, 列表元素并列关系，取值范围1~4, 含义参考dmdb.t_pub_par(PAR_SYS_CODE:2010)")
	private List<Integer> markets;

	@ApiModelProperty(value = "是否城投债")
	private Boolean munInvest;

	@ApiModelProperty(value = "当日有效报价")
	private Boolean isValidQuoteExist;

	@ApiModelProperty(value = "行业分类")
	private Integer induClass;

	@ApiModelProperty(value = "财务指标条件集合")
	List<BondIndicatorFilterReq> bondIndicatorFilterReqs;

	@ApiModelProperty(value = "财务指标季度")
	private String quarter;

	@ApiModelProperty(value = "期限-开始时间 格式yyyy-MM-dd")
	private String startTime;

	@ApiModelProperty(value = "期限-结束时间 格式yyyy-MM-dd")
	private String endTime;
	
	@ApiModelProperty(value = "是否上市公司")
	private Boolean listPar;
	
	@ApiModelProperty(value = "内部评级")
	private List<Integer> instRatings;
	
	@ApiModelProperty(value = "投资建议")
	private List<Integer> instInvestmentAdvices;
	
	@ApiModelProperty(value = "市/县区域")
	private List<String> areaCodes;

	public BondDmFilterReq() {
	}

	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * @return the pds
	 */
	public List<Integer> getPds() {
		return pds;
	}

	/**
	 * @param pds
	 *            the pds to set
	 */
	public void setPds(List<Integer> pds) {
		this.pds = pds;
	}

	/**
	 * @return the tenors
	 */
	public List<Integer> getTenors() {
		return tenors;
	}

	/**
	 * @param tenors
	 *            the tenors to set
	 */
	public void setTenors(List<Integer> tenors) {
		this.tenors = tenors;
	}

	/**
	 * @return the dmBondTypes
	 */
	public List<Integer> getDmBondTypes() {
		return dmBondTypes;
	}

	/**
	 * @param dmBondTypes
	 *            the dmBondTypes to set
	 */
	public void setDmBondTypes(List<Integer> dmBondTypes) {
		this.dmBondTypes = dmBondTypes;
	}

	/**
	 * @return the induIds
	 */
	public List<Integer> getInduIds() {
		return induIds;
	}

	/**
	 * @param induIds
	 *            the induIds to set
	 */
	public void setInduIds(List<Integer> induIds) {
		this.induIds = induIds;
	}

	/**
	 * @return the issuer
	 */
	public BondIssuerDoc getIssuer() {
		return issuer;
	}

	/**
	 * @param issuer
	 *            the issuer to set
	 */
	public void setIssuer(BondIssuerDoc issuer) {
		this.issuer = issuer;
	}

	/**
	 * @return the regions
	 */
	public List<Integer> getRegions() {
		return regions;
	}

	/**
	 * @param regions
	 *            the regions to set
	 */
	public void setRegions(List<Integer> regions) {
		this.regions = regions;
	}

	/**
	 * @return the ownerTypes
	 */
	public List<Integer> getOwnerTypes() {
		return ownerTypes;
	}

	/**
	 * @param ownerTypes
	 *            the ownerTypes to set
	 */
	public void setOwnerTypes(List<Integer> ownerTypes) {
		this.ownerTypes = ownerTypes;
	}

	/**
	 * @return the bondRatings
	 */
	public List<Integer> getBondRatings() {
		return bondRatings;
	}

	/**
	 * @param bondRatings
	 *            the bondRatings to set
	 */
	public void setBondRatings(List<Integer> bondRatings) {
		this.bondRatings = bondRatings;
	}

	/**
	 * @return the issRatings
	 */
	public List<Integer> getIssRatings() {
		return issRatings;
	}

	/**
	 * @param issRatings
	 *            the issRatings to set
	 */
	public void setIssRatings(List<Integer> issRatings) {
		this.issRatings = issRatings;
	}

	/**
	 * @return the markets
	 */
	public List<Integer> getMarkets() {
		return markets;
	}

	/**
	 * @param markets
	 *            the markets to set
	 */
	public void setMarkets(List<Integer> markets) {
		this.markets = markets;
	}

	/**
	 * @return the munInvest
	 */
	public Boolean getMunInvest() {
		return munInvest;
	}

	/**
	 * @param munInvest
	 *            the munInvest to set
	 */
	public void setMunInvest(Boolean munInvest) {
		this.munInvest = munInvest;
	}

	/**
	 * @return the isValidQuoteExist
	 */
	public Boolean getIsValidQuoteExist() {
		return isValidQuoteExist;
	}

	/**
	 * @param isValidQuoteExist
	 *            the isValidQuoteExist to set
	 */
	public void setIsValidQuoteExist(Boolean isValidQuoteExist) {
		this.isValidQuoteExist = isValidQuoteExist;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "BondDmFilterReq [userId=" + userId + ", filterName=" + filterName + ", pds=" + pds + ", tenors="
				+ tenors + ", dmBondTypes=" + dmBondTypes + ", induIds=" + induIds + ", issuer=" + issuer + ", regions="
				+ regions + ", ownerTypes=" + ownerTypes + ", bondRatings=" + bondRatings + ", impliedRatings="
				+ impliedRatings + ", issRatings=" + issRatings + ", markets=" + markets + ", munInvest=" + munInvest
				+ ", isValidQuoteExist=" + isValidQuoteExist + ", induClass=" + induClass + ", bondIndicatorFilterReqs="
				+ bondIndicatorFilterReqs + "]";
	}

	/**
	 * @return the induClass
	 */
	public Integer getInduClass() {
		return induClass;
	}

	/**
	 * @param induClass
	 *            the induClass to set
	 */
	public void setInduClass(Integer induClass) {
		this.induClass = induClass;
	}

	public List<BondIndicatorFilterReq> getBondIndicatorFilterReqs() {
		return bondIndicatorFilterReqs;
	}

	public void setBondIndicatorFilterReqs(List<BondIndicatorFilterReq> bondIndicatorFilterReqs) {
		this.bondIndicatorFilterReqs = bondIndicatorFilterReqs;
	}

	public List<Integer> getImpliedRatings() {
		return impliedRatings;
	}

	public void setImpliedRatings(List<Integer> impliedRatings) {
		this.impliedRatings = impliedRatings;
	}

	public String getFilterName() {
		return filterName;
	}

	public void setFilterName(String filterName) {
		this.filterName = filterName;
	}

	public String getQuarter() {
		return quarter;
	}

	public void setQuarter(String quarter) {
		this.quarter = quarter;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Boolean getListPar() {
		return listPar;
	}

	public void setListPar(Boolean listPar) {
		this.listPar = listPar;
	}

	public List<Integer> getInstRatings() {
		return instRatings;
	}

	public void setInstRatings(List<Integer> instRatings) {
		this.instRatings = instRatings;
	}

	public List<Integer> getInstInvestmentAdvices() {
		return instInvestmentAdvices;
	}

	public void setInstInvestmentAdvices(List<Integer> instInvestmentAdvices) {
		this.instInvestmentAdvices = instInvestmentAdvices;
	}

	public List<String> getAreaCodes() {
		return areaCodes;
	}

	public void setAreaCodes(List<String> areaCodes) {
		this.areaCodes = areaCodes;
	}
	
	

}
