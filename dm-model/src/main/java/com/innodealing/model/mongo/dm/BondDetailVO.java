package com.innodealing.model.mongo.dm;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.innodealing.json.JsonDateSerializer;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.Map;

@JsonInclude(Include.NON_NULL)
@Document(collection = "bond_detail_info")
@CompoundIndex(name = "updatetime_name_idx", def = "{'updateTime' : -1, 'name' : 1}")
public class BondDetailVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -686487420313746021L;

	@ApiModelProperty(value = "债券id")
	@Id
	@Indexed
	private Long bondUniCode;

	// 显示项目
	@ApiModelProperty(value = "债券简称")
	@Indexed
	private String name;

	// 显示项目
	@ApiModelProperty(value = "债券代码")
	@Indexed
	private String code;

	// 显示项目
	@ApiModelProperty(value = "估值")
	private BigDecimal fairValue;

	// 显示项目
	@ApiModelProperty(value = "票面")
	private Double coupRate;

	// 显示项目
	@ApiModelProperty(value = "主体量化风险等级变化")
	@Indexed
	private Long pdDiff;

	// 显示项目
	@ApiModelProperty(value = "正面舆情数")
	private Integer poPositive;

	// 显示项目
	@ApiModelProperty(value = "中性舆情数")
	private Integer poNeutral;

	// 显示项目
	@ApiModelProperty(value = "负面舆情数")
	private Integer poNegtive;

	// 显示项目
	@ApiModelProperty(value = "最近一个月舆情总数")
	private Integer sentimentMonthCount;

	// 显示项目
	// @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@JsonSerialize(using = JsonDateSerializer.class)
	@ApiModelProperty(value = "更新时间")
	@Indexed
	private Date updateTime;

	// 内部数据
	@JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
	@ApiModelProperty(value = "创建时间")
	private Date createTime;

	// 显示项目
	@ApiModelProperty(value = "期限")
	private String tenor;

	// 显示项目
	@ApiModelProperty(value = "主体量化风险等级")
	private String pd;

	// 显示项目
	@ApiModelProperty(value = "主体量化风险值")
	private Long pdNum;

	@ApiModelProperty(value = "主体量化风险值, 分值，来自rating_ratio_score", hidden = true)
	@Indexed
	@JsonIgnore
	private Double pdSortRRs;
	// 显示季度时间YYYY/Qn
	@ApiModelProperty(value = "主体量化风险等级-更新时间")
	private String pdTime;

	// 显示项目
	@ApiModelProperty(value = "主债评级")
	@Indexed
	private String issBondRating;

	// 显示项目
	@ApiModelProperty(value = "主体评级")
	private String issRating;

	// 显示项目
	@ApiModelProperty(value = "债项评级")
	private String bondRating;

	@ApiModelProperty(value = "每年付息日")
	private String yearPayDate;

	@ApiModelProperty(value = "到期日")
	@JsonFormat(pattern = "yyyy/MM/dd", timezone = "GMT+8")
	private Date theoEndDate;

	// 筛选条件, 界面选项枚举
	@ApiModelProperty(hidden = true)
	@Indexed
	private Integer pdUiOpt;

	// 筛选条件, 界面选项枚举
	@ApiModelProperty(hidden = true)
	@Indexed
	private Integer issRatingUiOpt;

	// 筛选条件, 界面选项枚举
	@ApiModelProperty(hidden = true)
	@Indexed
	private Integer bondRatingUiOpt;

	// 筛选条件, 界面选项枚举
	@ApiModelProperty(hidden = true)
	@Indexed
	private Integer tenorUiOpt;

	// 筛选条件
	@ApiModelProperty(value = "dm债券种类", hidden = true)
	@Indexed
	private Integer dmBondType;

	// 筛选条件
	@ApiModelProperty(value = "GICS行业", hidden = true)
	@Indexed
	private Long induId;

	@ApiModelProperty(value = "GICS行业")
	private String induName;

	// 筛选条件
	@ApiModelProperty(value = "申万行业", hidden = true)
	@Indexed
	@JsonIgnore
	private Long induIdSw;

	@ApiModelProperty(value = "申万行业", hidden = true)
	@JsonIgnore
	private String induNameSw;

	// 筛选条件
	@ApiModelProperty(value = "发行人id", hidden = true)
	@Indexed
	private Long comUniCode;

	@ApiModelProperty(value = "发行人名称", hidden = true)
	private String comName;

	// 筛选条件
	@ApiModelProperty(value = "地域", hidden = true)
	@Indexed
	private Long region;

	// 筛选条件
	@ApiModelProperty(value = "企业(所有制)", hidden = true)
	@Indexed
	private Integer ownerType;

	// 筛选条件
	@ApiModelProperty(value = "市场", hidden = true)
	@Indexed
	private Integer market;

	// 筛选条件
	@ApiModelProperty(value = "质押券代码", hidden = true)
	@Indexed
	private String pledgeCode;

	// 筛选条件
	@ApiModelProperty(value = "是否跨市场", hidden = true)
	@Indexed
	private Integer isCrossMar;

	// 筛选条件
	@ApiModelProperty(value = "是否城投债", hidden = true)
	@Indexed
	private Boolean munInvest;

	@ApiModelProperty(value = "当前存续状态", hidden = true)
	@Indexed
	private Integer currStatus; // 当前存续状态

	@ApiModelProperty(value = "发行状态", hidden = true)
	@Indexed
	private Integer issStaPar; // 发行状态

	@ApiModelProperty(value = "发行上市状态", hidden = true)
	@Indexed
	private Integer listStaPar; // 发行上市状态

	@ApiModelProperty(value = "行权兑付日")
	String exerPayDate; // 行权兑付日

	@ApiModelProperty(value = "期限（天数），用于排序", hidden = true)
	@Indexed
	private Long tenorDays;

	@ApiModelProperty(value = "省")
	private String areaName1;

	@ApiModelProperty(value = "省code")
	private BigInteger areaCode1;

	@ApiModelProperty(value = "地级市")
	private String areaName2;

	@ApiModelProperty(value = "市/县code")
	private BigInteger areaCode2;

	@ApiModelProperty(value = "区域类型")
	private String cityType;

	@ApiModelProperty(value = "历史最高风险等级")
	private String worstPd;

	@ApiModelProperty(value = "历史最高风险值")
	private Long worstPdNum;

	// 显示季度时间YYYY/Qn
	@ApiModelProperty(value = "历史最高风险等级-更新时间")
	private String worstPdTime;

	@ApiModelProperty(value = "历史最高风险警告标志, 量化风险等级<=CCC")
	private Boolean worstRiskWarning;

	@ApiModelProperty(value = "实际违约债券名")
	private String defaultBondName;

	@ApiModelProperty(value = "实际违约事件描述")
	private String defaultEvent;

	@ApiModelProperty(value = "实际违约事件日期")
	@JsonFormat(pattern = "yyyy/MM/dd")
	private Date defaultDate;

	@ApiModelProperty(value = "风险警告标志, 量化风险等级<=CCC")
	private Boolean riskWarning;

	@ApiModelProperty(value = "昨日加权收益率")
	private BigDecimal bondAddRate;

	@ApiModelProperty(value = "昨日交易量(万元)")
	private BigDecimal bondTradingVolume;

	@ApiModelProperty(value = "当前债券规模")
	private Double newSize;

	@ApiModelProperty(value = "再担保人")
	private String guraName1;

	@ApiModelProperty(value = "是否可赎回")
	private Integer isRedemPar;

	@ApiModelProperty(value = "是否可回售")
	private Integer isResaPar;

	@ApiModelProperty(value = "隐含评级")
	private String impliedRating;

	// 筛选条件, 界面选项枚举
	@ApiModelProperty(hidden = true)
	@Indexed
	private Integer impliedRatingUiOpt;

	@ApiModelProperty(value = "最近付息日")
	private String latelyPayDate;

	@ApiModelProperty(value = "估价收益率(%)")
	private Double estYield;

	@ApiModelProperty(value = "估价全价")
	private Double estDirtyPrice;

	@ApiModelProperty(value = "估价净价")
	private Double estCleanPrice;

	@ApiModelProperty(value = "行权收益率(%)")
	private Double optionYield;

	@ApiModelProperty(value = "单劵利差")
	private Double staticSpread;

	@ApiModelProperty(value = "单劵利差行业分位")
	private Double staticSpreadInduQuantile;

	@ApiModelProperty(value = "久期")
	private Double macd;

	@ApiModelProperty(value = "修正久期")
	private Double modd;

	@ApiModelProperty(value = "凸性")
	private Double convexity;

	@ApiModelProperty(value = "标准劵折算率")
	private Double convRatio;
	
	@ApiModelProperty(value = "是否上市公司")
	@Indexed
	private Boolean listPar; 
	
    @ApiModelProperty(value = "机构所属行业")
    private Map<String,Object> institutionInduMap; 
    
    @ApiModelProperty(value="债券发行日期")
	@JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
	private Date issStartDate;
	
	/**
	 * @return the riskWarning
	 */
	public Boolean getRiskWarning() {
		return riskWarning;
	}

	public BondDetailVO() {
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the tenor
	 */
	public String getTenor() {
		return tenor;
	}

	/**
	 * @param tenor
	 *            the tenor to set
	 */
	public void setTenor(String tenor) {
		this.tenor = tenor;
	}


	/**
	 * @return the coupRate
	 */
	public Double getCoupRate() {
		return coupRate;
	}

	/**
	 * @param coupRate
	 *            the coupRate to set
	 */
	public void setCoupRate(Double coupRate) {
		this.coupRate = coupRate;
	}

	/**
	 * @return the pd
	 */
	public String getPd() {
		if (pd == null) {
			return pd;
		}
		if (dmBondType !=null && (dmBondType == 3 || dmBondType == 4 || dmBondType == 5 || dmBondType == 6)) {
			return null;
		}
		return pd;
	}

	/**
	 * @param pd
	 *            the pd to set
	 */
	public void setPd(String pd) {
		this.pd = pd;
	}

	/**
	 * @return the pdDiff
	 */
	public Long getPdDiff() {
		if (pd == null) {
			return pdDiff;
		}
		if (dmBondType !=null && (dmBondType == 3 || dmBondType == 4 || dmBondType == 5 || dmBondType == 6)) {
			return null;
		}
		return pdDiff;
	}

	/**
	 * @param pdDiff
	 *            the pdDiff to set
	 */
	public void setPdDiff(Long pdDiff) {
		this.pdDiff = pdDiff;
	}

	/**
	 * @return the poPositive
	 */
	public Integer getPoPositive() {
		return poPositive;
	}

	/**
	 * @param poPositive
	 *            the poPositive to set
	 */
	public void setPoPositive(Integer poPositive) {
		this.poPositive = poPositive;
	}

	/**
	 * @return the poNeutral
	 */
	public Integer getPoNeutral() {
		return poNeutral;
	}

	/**
	 * @param poNeutral
	 *            the poNeutral to set
	 */
	public void setPoNeutral(Integer poNeutral) {
		this.poNeutral = poNeutral;
	}

	/**
	 * @return the poNegtive
	 */
	public Integer getPoNegtive() {
		return poNegtive;
	}

	/**
	 * @param poNegtive
	 *            the poNegtive to set
	 */
	public void setPoNegtive(Integer poNegtive) {
		this.poNegtive = poNegtive;
	}

	/**
	 * @return the updateTime
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * @param updateTime
	 *            the updateTime to set
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
	 * @param dmBondType
	 *            the dmBondType to set
	 */
	public void setDmBondType(Integer dmBondType) {
		this.dmBondType = dmBondType;
	}

	/**
	 * @return the induId
	 */
	public Long getInduId() {
		return induId;
	}

	/**
	 * @param induId
	 *            the induId to set
	 */
	public void setInduId(Long induId) {
		this.induId = induId;
	}

	/**
	 * @return the comUniCode
	 */
	public Long getComUniCode() {
		return comUniCode;
	}

	/**
	 * @param comUniCode
	 *            the comUniCode to set
	 */
	public void setComUniCode(Long comUniCode) {
		this.comUniCode = comUniCode;
	}

	/**
	 * @return the areaUniCode
	 */
	public Long getRegion() {
		return region;
	}

	/**
	 * @param areaUniCode
	 *            the areaUniCode to set
	 */
	public void setRegion(Long region) {
		this.region = region;
	}

	/**
	 * @return the ownerType
	 */
	public Integer getOwnerType() {
		return ownerType;
	}

	/**
	 * @param ownerType
	 *            the ownerType to set
	 */
	public void setOwnerType(Integer ownerType) {
		this.ownerType = ownerType;
	}

	/**
	 * @return the market
	 */
	public Integer getMarket() {
		return market;
	}

	/**
	 * @param market
	 *            the market to set
	 */
	public void setMarket(Integer market) {
		this.market = market;
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
	 * @return the bondUniCode
	 */
	public Long getBondUniCode() {
		return bondUniCode;
	}

	/**
	 * @param bondUniCode
	 *            the bondUniCode to set
	 */
	public void setBondUniCode(Long bondUniCode) {
		this.bondUniCode = bondUniCode;
	}

	/**
	 * @return the issBondRating
	 */
	public String getIssBondRating() {
		return issBondRating;
	}

	/**
	 * @param issBondRating
	 *            the issBondRating to set
	 */
	public void setIssBondRating(String issBondRating) {
		this.issBondRating = issBondRating;
	}

	/**
	 * @return the pdUiOpt
	 */
	public Integer getPdUiOpt() {
		return pdUiOpt;
	}

	/**
	 * @param pdUiOpt
	 *            the pdUiOpt to set
	 */
	public void setPdUiOpt(Integer pdUiOpt) {
		this.pdUiOpt = pdUiOpt;
	}

	/**
	 * @return the issRatingUiOpt
	 */
	public Integer getIssRatingUiOpt() {
		return issRatingUiOpt;
	}

	/**
	 * @param issRatingUiOpt
	 *            the issRatingUiOpt to set
	 */
	public void setIssRatingUiOpt(Integer issRatingUiOpt) {
		this.issRatingUiOpt = issRatingUiOpt;
	}

	/**
	 * @return the bondRatingUiOpt
	 */
	public Integer getBondRatingUiOpt() {
		return bondRatingUiOpt;
	}

	/**
	 * @param bondRatingUiOpt
	 *            the bondRatingUiOpt to set
	 */
	public void setBondRatingUiOpt(Integer bondRatingUiOpt) {
		this.bondRatingUiOpt = bondRatingUiOpt;
	}

	/**
	 * @return the tenorUiOpt
	 */
	public Integer getTenorUiOpt() {
		return tenorUiOpt;
	}

	/**
	 * @param tenorUiOpt
	 *            the tenorUiOpt to set
	 */
	public void setTenorUiOpt(Integer tenorUiOpt) {
		this.tenorUiOpt = tenorUiOpt;
	}

	/**
	 * @return the pledgeCode
	 */
	public String getPledgeCode() {
		return pledgeCode;
	}

	/**
	 * @param pledgeCode
	 *            the pledgeCode to set
	 */
	public void setPledgeCode(String pledgeCode) {
		this.pledgeCode = pledgeCode;
	}

	/**
	 * @return the isCrossMar
	 */
	public Integer getIsCrossMar() {
		return isCrossMar;
	}

	/**
	 * @param isCrossMar
	 *            the isCrossMar to set
	 */
	public void setIsCrossMar(Integer isCrossMar) {
		this.isCrossMar = isCrossMar;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime
	 *            the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the issRating
	 */
	public String getIssRating() {
		return issRating;
	}

	/**
	 * @param issRating
	 *            the issRating to set
	 */
	public void setIssRating(String issRating) {
		this.issRating = issRating;
	}

	/**
	 * @return the bondRating
	 */
	public String getBondRating() {
		return bondRating;
	}

	/**
	 * @param bondRating
	 *            the bondRating to set
	 */
	public void setBondRating(String bondRating) {
		this.bondRating = bondRating;
	}

	/**
	 * @return the currStatus
	 */
	public Integer getCurrStatus() {
		return currStatus;
	}

	/**
	 * @param currStatus
	 *            the currStatus to set
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
	 * @param issStaPar
	 *            the issStaPar to set
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
	 * @param listStaPar
	 *            the listStaPar to set
	 */
	public void setListStaPar(Integer listStaPar) {
		this.listStaPar = listStaPar;
	}

	/**
	 * @return the exerPayDate
	 */
	public String getExerPayDate() {
		return exerPayDate;
	}

	/**
	 * @param exerPayDate
	 *            the exerPayDate to set
	 */
	public void setExerPayDate(String exerPayDate) {
		this.exerPayDate = exerPayDate;
	}

	/**
	 * @return the tenorDays
	 */
	public Long getTenorDays() {
		return tenorDays;
	}

	/**
	 * @param tenorDays
	 *            the tenorDays to set
	 */
	public void setTenorDays(Long tenorDays) {
		this.tenorDays = tenorDays;
	}

	public String getInduName() {
		return induName;
	}

	public void setInduName(String induName) {
		this.induName = induName;
	}

	public String getComName() {
		return comName;
	}

	public void setComName(String comName) {
		this.comName = comName;
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

	public String getCityType() {
		return cityType;
	}

	public void setCityType(String cityType) {
		this.cityType = cityType;
	}

	/**
	 * @return the pdTime
	 */
	public String getPdTime() {
		return pdTime;
	}

	/**
	 * @param pdTime
	 *            the pdTime to set
	 */
	public void setPdTime(String pdTime) {
		this.pdTime = pdTime;
	}

	/**
	 * @return the defaultBondName
	 */
	public String getDefaultBondName() {
		return defaultBondName;
	}

	/**
	 * @param defaultBondName
	 *            the defaultBondName to set
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
	 * @param defaultEvent
	 *            the defaultEvent to set
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
	 * @param defaultDate
	 *            the defaultDate to set
	 */
	public void setDefaultDate(Date defaultDate) {
		this.defaultDate = defaultDate;
	}

	/**
	 * @return the worstPd
	 */
	public String getWorstPd() {
		return worstPd;
	}

	/**
	 * @param worstPd
	 *            the worstPd to set
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
	 * @param worstPdNum
	 *            the worstPdNum to set
	 */
	public void setWorstPdNum(Long worstPdNum) {
		this.worstPdNum = worstPdNum;
		if (worstPdNum != null)
			this.worstRiskWarning = (worstPdNum >= 18);
	}

	/**
	 * @return the worstPdTime
	 */
	public String getWorstPdTime() {
		return worstPdTime;
	}

	/**
	 * @param worstPdTime
	 *            the worstPdTime to set
	 */
	public void setWorstPdTime(String worstPdTime) {
		this.worstPdTime = worstPdTime;
	}

	/**
	 * @return the pdNum
	 */
	public Long getPdNum() {
		return pdNum;
	}

	/**
	 * @param pdNum
	 *            the pdNum to set
	 */
	public void setPdNum(Long pdNum) {
		this.pdNum = pdNum;
		if (pdNum != null)
			this.riskWarning = (pdNum >= 18);

	}

	public Integer getSentimentMonthCount() {
		return sentimentMonthCount;
	}

	public void setSentimentMonthCount(Integer sentimentMonthCount) {
		this.sentimentMonthCount = sentimentMonthCount;
	}

	public Boolean getWorstRiskWarning() {
		return worstRiskWarning;
	}

	public BigDecimal getBondAddRate() {
		return bondAddRate;
	}

	public void setBondAddRate(BigDecimal bondAddRate) {
		this.bondAddRate = bondAddRate;
	}

	public BigDecimal getBondTradingVolume() {
		return bondTradingVolume;
	}

	public void setBondTradingVolume(BigDecimal bondTradingVolume) {
		this.bondTradingVolume = bondTradingVolume;
	}

	/**
	 * @return the yearPayDate
	 */
	public String getYearPayDate() {
		return yearPayDate;
	}

	/**
	 * @param yearPayDate
	 *            the yearPayDate to set
	 */
	public void setYearPayDate(String yearPayDate) {
		this.yearPayDate = yearPayDate;
	}

	/**
	 * @return the theoEndDate
	 */
	public Date getTheoEndDate() {
		return theoEndDate;
	}

	/**
	 * @param theoEndDate
	 *            the theoEndDate to set
	 */
	public void setTheoEndDate(Date theoEndDate) {
		this.theoEndDate = theoEndDate;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @param worstRiskWarning
	 *            the worstRiskWarning to set
	 */
	public void setWorstRiskWarning(Boolean worstRiskWarning) {
		this.worstRiskWarning = worstRiskWarning;
	}

	/**
	 * @param riskWarning
	 *            the riskWarning to set
	 */
	public void setRiskWarning(Boolean riskWarning) {
		this.riskWarning = riskWarning;
	}

	public BigDecimal getFairValue() {
		return fairValue;
	}

	public void setFairValue(BigDecimal fairValue) {
		this.fairValue = fairValue;
	}

	/**
	 * @return the induIdSw
	 */
	public Long getInduIdSw() {
		return induIdSw;
	}

	/**
	 * @param induIdSw
	 *            the induIdSw to set
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
	 * @param induNameSw
	 *            the induNameSw to set
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

	/**
	 * @return the pdSortRRs
	 */
	public Double getPdSortRRs() {
		return pdSortRRs;
	}

	/**
	 * @param pdSortRRs
	 *            the pdSortRRs to set
	 */
	public void setPdSortRRs(Double pdSortRRs) {
		this.pdSortRRs = pdSortRRs;
	}

	public String getImpliedRating() {
		return impliedRating;
	}

	public void setImpliedRating(String impliedRating) {
		this.impliedRating = impliedRating;
	}

	public Integer getImpliedRatingUiOpt() {
		return impliedRatingUiOpt;
	}

	public void setImpliedRatingUiOpt(Integer impliedRatingUiOpt) {
		this.impliedRatingUiOpt = impliedRatingUiOpt;
	}

	public String getLatelyPayDate() {
		return latelyPayDate;
	}

	public void setLatelyPayDate(String latelyPayDate) {
		this.latelyPayDate = latelyPayDate;
	}

	public Double getEstYield() {
		return estYield;
	}

	public void setEstYield(Double estYield) {
		this.estYield = estYield;
	}

	public Double getEstDirtyPrice() {
		return estDirtyPrice;
	}

	public void setEstDirtyPrice(Double estDirtyPrice) {
		this.estDirtyPrice = estDirtyPrice;
	}

	public Double getEstCleanPrice() {
		return estCleanPrice;
	}

	public void setEstCleanPrice(Double estCleanPrice) {
		this.estCleanPrice = estCleanPrice;
	}

	public Double getOptionYield() {
		return optionYield;
	}

	public void setOptionYield(Double optionYield) {
		this.optionYield = optionYield;
	}

	public Double getStaticSpread() {
		return staticSpread;
	}

	public void setStaticSpread(Double staticSpread) {
		this.staticSpread = staticSpread;
	}

	public Double getStaticSpreadInduQuantile() {
		return staticSpreadInduQuantile;
	}

	public void setStaticSpreadInduQuantile(Double staticSpreadInduQuantile) {
		this.staticSpreadInduQuantile = staticSpreadInduQuantile;
	}

	public Double getMacd() {
		return macd;
	}

	public void setMacd(Double macd) {
		this.macd = macd;
	}

	public Double getModd() {
		return modd;
	}

	public void setModd(Double modd) {
		this.modd = modd;
	}

	public Double getConvexity() {
		return convexity;
	}

	public void setConvexity(Double convexity) {
		this.convexity = convexity;
	}

	public Double getConvRatio() {
		return convRatio;
	}

	public void setConvRatio(Double convRatio) {
		this.convRatio = convRatio;
	}

	public Boolean getListPar() {
		return listPar;
	}

	public void setListPar(Boolean listPar) {
		this.listPar = listPar;
	}

	public Map<String, Object> getInstitutionInduMap() {
		return institutionInduMap;
	}

	public void setInstitutionInduMap(Map<String, Object> institutionInduMap) {
		this.institutionInduMap = institutionInduMap;
	}

	public Date getIssStartDate() {
		return issStartDate;
	}

	public void setIssStartDate(Date issStartDate) {
		this.issStartDate = issStartDate;
	}
	
	
}
