package com.innodealing.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import com.innodealing.model.mongo.dm.BondInstDoc;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.mongodb.core.index.Indexed;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xiaochao
 * @time 2017年5月18日
 * @description:
 */
@JsonInclude(Include.NON_NULL)
public class BondFavoriteDetailVO extends BondInstDoc implements Serializable {
	private static final long serialVersionUID = 8583397237273589588L;

	@ApiModelProperty(value = "持仓量")
	private Integer openinterest;

	@ApiModelProperty(value = "持仓价格")
	private BigDecimal positionPrice;

	@ApiModelProperty(value = "持仓日期")
	private Date positionDate;

	@ApiModelProperty(value = "变动消息数量")
	private Long eventMsgCount;

	@ApiModelProperty(value = "备注")
	private String remark;

	@ApiModelProperty(value = "是否过期:0-未过期;1-过期")
	private Integer expiredState;

	@ApiModelProperty(value = "评级展望")
	private String ratePros;

	@ApiModelProperty(value = "主体评级展望")
	private String issRatePros;

	@ApiModelProperty(value = "是否存在私人雷达:true-有;false-没有")
	private boolean hasPrivateRadar;

	@ApiModelProperty(value = "是否非金融企业债:true-是;false-不是")
	private boolean isNotFinaCompBond;

	@ApiModelProperty(value = "投组未读消息总数")
	private Long groupNewMsgCount;

	@ApiModelProperty(value = "bid报价时间")
	private String bidSendTime;

	@ApiModelProperty(value = "ofr报价时间")
	private String ofrSendTime;

	@ApiModelProperty(value = "主体债项评级")
	private String issBondRating;

	@ApiModelProperty(value = "是否关注")
	private Boolean isFavorited;

	@ApiModelProperty(value = "投组关注Id")
	private Integer favoriteId;

	@ApiModelProperty(value = "是否已经加入对比")
	private Boolean isCompared;

	@ApiModelProperty(value = "债券id")
	private Long bondUniCode;

	@ApiModelProperty(value = "债券简称")
	private String name;

	@ApiModelProperty(value = "债券代码")
	private String code;

	@ApiModelProperty(value = "ofr价格")
	private Double ofrPrice;

	@ApiModelProperty(value = "ofr报价数额")
	private BigDecimal ofrVol;

	@ApiModelProperty(value = "ofr报价笔数")
	private Long ofrOrderCnt;

	@ApiModelProperty(value = "bid价格")
	private Double bidPrice;

	@ApiModelProperty(value = "bid报价数量")
	private BigDecimal bidVol;

	@ApiModelProperty(value = "bid报价笔数")
	private Long bidOrderCnt;

	@ApiModelProperty(value = "估值")
	private BigDecimal fairValue;

	@ApiModelProperty(value = "更新时间")
	@JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
	private Date updateTime;

	@ApiModelProperty(value = "期限")
	private String tenor;

	@ApiModelProperty(value = "期限（天数），用于排序")
	private Long tenorDays;

	@ApiModelProperty(value = "主体量化风险等级")
	private String pd;

	@ApiModelProperty(value = "主体量化风险值")
	private Long pdNum;

	@ApiModelProperty(value = "主体量化风险等级-更新时间")
	private String pdTime;

	@ApiModelProperty(value = "每年付息日")
	private String yearPayDate;

	@ApiModelProperty(value = "最近付息日")
	private String latelyPayDate;

	@ApiModelProperty(value = "到期日")
	@JsonFormat(pattern = "yyyy/MM/dd", timezone = "GMT+8")
	private Date theoEndDate;

	@ApiModelProperty(value = "发行人名称", hidden = true)
	private String comName;

	@ApiModelProperty(value = "行权兑付日")
	private String exerPayDate; // 行权兑付日

	@ApiModelProperty(value = "历史最高风险等级")
	private String worstPd;

	@ApiModelProperty(value = "历史最高风险值")
	private Long worstPdNum;

	// 显示季度时间YYYY/Qn
	@ApiModelProperty(value = "历史最高风险等级-更新时间")
	private String worstPdTime;

	@ApiModelProperty(value = "历史最高风险警告标志, 量化风险等级<=CCC")
	private Boolean worstRiskWarning;

	@ApiModelProperty(value = "隐含评级")
	private String impliedRating;

	@ApiModelProperty(value = "成交价")
	private Double price;

	@ApiModelProperty(value = "成交价数目")
	private Long priceCount;

	@ApiModelProperty(value = "最后成交日期")
	@JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
	private Date priceUpdateTime;

	@JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
	@ApiModelProperty(value = "创建时间")
	private Date createTime;

	@ApiModelProperty(value = "GICS行业", hidden = true)
	private Long induId;

	@ApiModelProperty(value = "GICS行业")
	private String induName;

	// 筛选条件
	@ApiModelProperty(value = "申万行业", hidden = true)
	@JsonIgnore
	private Long induIdSw;

	@ApiModelProperty(value = "申万行业", hidden = true)
	@JsonIgnore
	private String induNameSw;

	/**
	 * @return the openinterest
	 */
	public Integer getOpeninterest() {
		return openinterest;
	}

	/**
	 * @param openinterest
	 *            the openinterest to set
	 */
	public void setOpeninterest(Integer openinterest) {
		this.openinterest = openinterest;
	}

	/**
	 * @return the eventMsgCount
	 */
	public Long getEventMsgCount() {
		return eventMsgCount;
	}

	/**
	 * @param eventMsgCount
	 *            the eventMsgCount to set
	 */
	public void setEventMsgCount(Long eventMsgCount) {
		this.eventMsgCount = eventMsgCount;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark
	 *            the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return the expiredState
	 */
	public Integer getExpiredState() {
		return expiredState;
	}

	/**
	 * @param expiredState
	 *            the expiredState to set
	 */
	public void setExpiredState(Integer expiredState) {
		this.expiredState = expiredState;
	}

	public String getRatePros() {
		return ratePros;
	}

	public void setRatePros(String ratePros) {
		this.ratePros = ratePros;
	}

	public boolean isHasPrivateRadar() {
		return hasPrivateRadar;
	}

	public void setHasPrivateRadar(boolean hasPrivateRadar) {
		this.hasPrivateRadar = hasPrivateRadar;
	}

	public boolean getIsNotFinaCompBond() {
		return isNotFinaCompBond;
	}

	public void setIsNotFinaCompBond(boolean isNotFinaCompBond) {
		this.isNotFinaCompBond = isNotFinaCompBond;
	}

	public Long getGroupNewMsgCount() {
		return groupNewMsgCount;
	}

	public void setGroupNewMsgCount(Long groupNewMsgCount) {
		this.groupNewMsgCount = groupNewMsgCount;
	}

	public String getIssRatePros() {
		return issRatePros;
	}

	public void setIssRatePros(String issRatePros) {
		this.issRatePros = issRatePros;
	}

	public String getOfrSendTime() {
		return ofrSendTime;
	}

	public void setOfrSendTime(String ofrSendTime) {
		this.ofrSendTime = ofrSendTime;
	}

	public String getBidSendTime() {
		return bidSendTime;
	}

	public void setBidSendTime(String bidSendTime) {
		this.bidSendTime = bidSendTime;
	}

	public Long getBondUniCode() {
		return bondUniCode;
	}

	public void setBondUniCode(Long bondUniCode) {
		this.bondUniCode = bondUniCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Double getOfrPrice() {
		return ofrPrice;
	}

	public void setOfrPrice(Double ofrPrice) {
		this.ofrPrice = ofrPrice;
	}

	public BigDecimal getOfrVol() {
		return ofrVol;
	}

	public void setOfrVol(BigDecimal ofrVol) {
		this.ofrVol = ofrVol;
	}

	public Long getOfrOrderCnt() {
		return ofrOrderCnt;
	}

	public void setOfrOrderCnt(Long ofrOrderCnt) {
		this.ofrOrderCnt = ofrOrderCnt;
	}

	public Double getBidPrice() {
		return bidPrice;
	}

	public void setBidPrice(Double bidPrice) {
		this.bidPrice = bidPrice;
	}

	public BigDecimal getBidVol() {
		return bidVol;
	}

	public void setBidVol(BigDecimal bidVol) {
		this.bidVol = bidVol;
	}

	public Long getBidOrderCnt() {
		return bidOrderCnt;
	}

	public void setBidOrderCnt(Long bidOrderCnt) {
		this.bidOrderCnt = bidOrderCnt;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public BigDecimal getFairValue() {
		return fairValue;
	}

	public void setFairValue(BigDecimal fairValue) {
		this.fairValue = fairValue;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getTenor() {
		return tenor;
	}

	public void setTenor(String tenor) {
		this.tenor = tenor;
	}

	public String getPd() {
		return pd;
	}

	public void setPd(String pd) {
		this.pd = pd;
	}

	public Long getPdNum() {
		return pdNum;
	}

	public void setPdNum(Long pdNum) {
		this.pdNum = pdNum;
	}

	public String getPdTime() {
		return pdTime;
	}

	public void setPdTime(String pdTime) {
		this.pdTime = pdTime;
	}

	public String getYearPayDate() {
		return yearPayDate;
	}

	public void setYearPayDate(String yearPayDate) {
		this.yearPayDate = yearPayDate;
	}

	public Date getTheoEndDate() {
		return theoEndDate;
	}

	public void setTheoEndDate(Date theoEndDate) {
		this.theoEndDate = theoEndDate;
	}

	public String getComName() {
		return comName;
	}

	public void setComName(String comName) {
		this.comName = comName;
	}

	public String getExerPayDate() {
		return exerPayDate;
	}

	public void setExerPayDate(String exerPayDate) {
		this.exerPayDate = exerPayDate;
	}

	public Long getTenorDays() {
		return tenorDays;
	}

	public void setTenorDays(Long tenorDays) {
		this.tenorDays = tenorDays;
	}

	public String getWorstPd() {
		return worstPd;
	}

	public void setWorstPd(String worstPd) {
		this.worstPd = worstPd;
	}

	public Long getWorstPdNum() {
		return worstPdNum;
	}

	public void setWorstPdNum(Long worstPdNum) {
		this.worstPdNum = worstPdNum;
	}

	public String getWorstPdTime() {
		return worstPdTime;
	}

	public void setWorstPdTime(String worstPdTime) {
		this.worstPdTime = worstPdTime;
	}

	public Boolean getWorstRiskWarning() {
		return worstRiskWarning;
	}

	public void setWorstRiskWarning(Boolean worstRiskWarning) {
		this.worstRiskWarning = worstRiskWarning;
	}

	public String getImpliedRating() {
		return impliedRating;
	}

	public void setImpliedRating(String impliedRating) {
		this.impliedRating = impliedRating;
	}

	public Long getPriceCount() {
		return priceCount;
	}

	public void setPriceCount(Long priceCount) {
		this.priceCount = priceCount;
	}

	public Date getPriceUpdateTime() {
		return priceUpdateTime;
	}

	public void setPriceUpdateTime(Date priceUpdateTime) {
		this.priceUpdateTime = priceUpdateTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getFavoriteId() {
		return favoriteId;
	}

	public void setFavoriteId(Integer favoriteId) {
		this.favoriteId = favoriteId;
	}

	public Boolean getIsFavorited() {
		return isFavorited;
	}

	public void setIsFavorited(Boolean isFavorited) {
		this.isFavorited = isFavorited;
	}

	public Boolean getIsCompared() {
		return isCompared;
	}

	public void setIsCompared(Boolean isCompared) {
		this.isCompared = isCompared;
	}

	public Long getInduId() {
		return induId;
	}

	public void setInduId(Long induId) {
		this.induId = induId;
	}

	public String getInduName() {
		return induName;
	}

	public void setInduName(String induName) {
		this.induName = induName;
	}

	public Long getInduIdSw() {
		return induIdSw;
	}

	public void setInduIdSw(Long induIdSw) {
		this.induIdSw = induIdSw;
	}

	public String getInduNameSw() {
		return induNameSw;
	}

	public void setInduNameSw(String induNameSw) {
		this.induNameSw = induNameSw;
	}

	public BigDecimal getPositionPrice() {
		return positionPrice;
	}

	public void setPositionPrice(BigDecimal positionPrice) {
		this.positionPrice = positionPrice;
	}

	public Date getPositionDate() {
		return positionDate;
	}

	public void setPositionDate(Date positionDate) {
		this.positionDate = positionDate;
	}

	public String getLatelyPayDate() {
		return latelyPayDate;
	}

	public void setLatelyPayDate(String latelyPayDate) {
		this.latelyPayDate = latelyPayDate;
	}

	public String getIssBondRating() {
		return issBondRating;
	}

	public void setIssBondRating(String issBondRating) {
		this.issBondRating = issBondRating;
	}
}
