package com.innodealing.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;

import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.annotations.ApiModelProperty;

public class BondCcxeBasicInfo {

	Long bondUniCode;
	String bondCode;
	String bondShortName;
	String bondFullName;
	String pledgeName; // 质押券简称
	String pledgeCode; // 质押券代码
	// Long orgUniCode; //发行人代码
	String issName; // 发行人
	BigDecimal bondMatu; // 债券期限
	Integer matuUnitPar; // 存续期单位
	Integer bondTypePar; // 债券类型

	String yearPayDate; // 付息日
	Date inteStartDate; // 起息日
	Date theoEndDate; // 理论到期日
	String newRate; // 最新债项评级
	BigDecimal issCoupRate; // 发行票面利率 发行票息
	BigDecimal newCoupRate; // 最新票面利率
	Integer intePayClsPar; // 付息方式
	Integer intePayFreq; // 付息频率
	String guraName; // 担保人及方式
	Integer baseRatePar; // 浮动利率计息基准
	String bondRredLevel; // 评级
	String issCredLevel; // 发行人评级
	Integer ratePros; // 展望
	Long comUniCode; // 发行人id
	String comChiShortName; // 发行人
	Long areaUniCode1; // 发行人地址省
	Long areaUniCode; // 发行人地址市/区
	Integer secMarPar; // 证券市场
	Integer isCrosMarPar; // 是否跨市场
	Integer isTypePar; // 特殊债券类型
	Integer rateTypePar; // 利率类型
	Integer comAttrPar; // 企业性质
	BigDecimal pd; // 违约概率
	private String crosMarDes;//跨市场说明
	Integer currStatus; // 当前存续状态
	Integer issStaPar; // 发行状态
	Integer listStaPar; // 发行上市状态

	Date matuPayDate; // 兑付日
	String exerPayDate; // 行权兑付日

	String comPro;// 公司简介
	String mainBus;// 主营业务
	String sidBus;// 兼营业务

	BigDecimal fairValue;// 估值

	private Double newSize;// 当前债券规模
	private String guraName1;// 再担保人
	private Integer isRedemPar;// 是否可赎回
	private Integer isResaPar;// 是否可回售
	private String issCls; // 发行方式
	private Date bondRateWritDate; // 债项评级时间
	private String bondRateOrgName; // 债项评级机构
	private String impliedRating;// 隐含评级
	private Double bondParVal;// 面值
	private Double issPri; // 发行价
	private Double payAmount; // 待付利息本金总和

	private Integer listPar;// 是否上市公司

	private Double estYield; // 估价收益率(%)
	private Double estDirtyPrice; // 估价全价
	private Double estCleanPrice; // 估价净价
	private Double optionYield; // 行权收益率(%)
	private Double staticSpread; // 单劵利差
	private Double staticSpreadInduQuantile; // 单劵利差行业分位
	private Double macd; // 久期
	private Double modd; // 修正久期
	private Double convexity; // 凸性
	private Double convRatio; // 标准劵折算率

	private Date issStartDate; // 发行起始日
	private Date issEndDate; // 发行截止日
	private Date payStartDate; // 缴款起始日期
	private Date payEndDate; // 缴款截止日期
	private String yearPayMatu; // 每年付息期限(文字描述)
	private Long credOrgUniCode; // 评级机构ID
	private String rateDes; // 利率简述
	private BigDecimal basSpr; // 基本利差
	private Date listDate; // 上市日期
	private Date listDeclDate; // 上市公告日期
	private String collCapPurp; // 募集资金用途
	private String undeName; // 主承销商
	private Integer undeClsPar; // 承销方式
	private BigDecimal issFeeRate; // 发行手续费率
	private Date bidDate; // 招标日期
	private Date bokepDate; // 簿记建档日
	private Date debtRegDate; // 债权债务登记日

	private BigDecimal subsUnit;// 认购单位(元)
	private BigDecimal leastSubsUnit;// 最小认购数量
	private Integer curyTypePar;// 货币类型
	private Integer isListPar;// 是否流通
	private String tradeUnit;// 交易单位
	private BigDecimal circuAmut;// 流通总额
	private Integer listSectPar;// 上市板块
	private Date theoDelistDate;// 理论退市日期
	private Date lastTradeDate;// 最后交易日
	private Date actuDelistDate;// 摘牌日
	private String speShortName;// 拼音简称
	private String engFullName;// 英文全称
	private String engShortName;// 英文简称
	private String isinCode;// ISIN代码
	private BigDecimal bondParVl;// 债券面值
	private Integer bondFormPar;// 债券形态
	private Date actuEndDate;// 实际到期
	private String inteDes;// 计息说明
	private BigDecimal refYield;// 贴现债参考收益率
	private Date initBaseRateDate;// 首期利率基准日期
	private BigDecimal baseRate;// 首期基准利率
	private BigDecimal optExtraSpr;// 含权额外利差
	private Integer extraSprSeqNum;// 额外利差启用期次
	private BigDecimal rateCeil;// 利率浮动上限
	private BigDecimal rateFloor;// 利率浮动下限
	private Integer inteCalcuClsPar;// 利息计算方式
	private Integer isSegmPar;// 是否分段计息
	private String intePayMeth;// 利息支付方式说明
	private Integer simpCompIntePar;// 单利或复利
	private Integer repayClsPayPar;// 偿还方式
	private BigDecimal payFeeRate;// 兑付手续费率
	private String payMatu;// 兑付期限
	private String payClsDes;// 兑付方式说明
	private String optDes;// 含权说明
	private Integer isGuarPar;// 是否有担保
	private Integer isRepuPar;// 是否可回购
	private Integer isHedgePar;// 是否保值
	private Integer isTaxFreePar;// 是否免税

	private Integer orgIsTypePar;
	private Integer orgIssStaPar;
	
	
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
	 * @return the bondCode
	 */
	public String getBondCode() {
		return bondCode;
	}

	/**
	 * @param bondCode
	 *            the bondCode to set
	 */
	public void setBondCode(String bondCode) {
		this.bondCode = bondCode;
	}

	/**
	 * @return the bondShortName
	 */
	public String getBondShortName() {
		return bondShortName;
	}

	/**
	 * @param bondShortName
	 *            the bondShortName to set
	 */
	public void setBondShortName(String bondShortName) {
		this.bondShortName = bondShortName;
	}

	/**
	 * @return the bondFullName
	 */
	public String getBondFullName() {
		return bondFullName;
	}

	/**
	 * @param bondFullName
	 *            the bondFullName to set
	 */
	public void setBondFullName(String bondFullName) {
		this.bondFullName = bondFullName;
	}

	/**
	 * @return the pledgeName
	 */
	public String getPledgeName() {
		return pledgeName;
	}

	/**
	 * @param pledgeName
	 *            the pledgeName to set
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
	 * @param pledgeCode
	 *            the pledgeCode to set
	 */
	public void setPledgeCode(String pledgeCode) {
		this.pledgeCode = pledgeCode;
	}

	/**
	 * @return the issName
	 */
	public String getIssName() {
		return issName;
	}

	/**
	 * @param issName
	 *            the issName to set
	 */
	public void setIssName(String issName) {
		this.issName = issName;
	}

	/**
	 * @return the bondMatu
	 */
	public BigDecimal getBondMatu() {
		return bondMatu;
	}

	/**
	 * @param bondMatu
	 *            the bondMatu to set
	 */
	public void setBondMatu(BigDecimal bondMatu) {
		this.bondMatu = bondMatu;
	}

	/**
	 * @return the matuUnitPar
	 */
	public Integer getMatuUnitPar() {
		return matuUnitPar;
	}

	/**
	 * @param matuUnitPar
	 *            the matuUnitPar to set
	 */
	public void setMatuUnitPar(Integer matuUnitPar) {
		this.matuUnitPar = matuUnitPar;
	}

	/**
	 * @return the bondTypePar
	 */
	public Integer getBondTypePar() {
		return bondTypePar;
	}

	/**
	 * @param bondTypePar
	 *            the bondTypePar to set
	 */
	public void setBondTypePar(Integer bondTypePar) {
		this.bondTypePar = bondTypePar;
	}

	/**
	 * @return the inteStartDate
	 */
	public Date getInteStartDate() {
		return inteStartDate;
	}

	/**
	 * @param inteStartDate
	 *            the inteStartDate to set
	 */
	public void setInteStartDate(Date inteStartDate) {
		this.inteStartDate = inteStartDate;
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
	 * @return the newRate
	 */
	public String getNewRate() {
		return newRate;
	}

	/**
	 * @param newRate
	 *            the newRate to set
	 */
	public void setNewRate(String newRate) {
		this.newRate = newRate;
	}

	/**
	 * @return the issCoupRate
	 */
	public BigDecimal getIssCoupRate() {
		return issCoupRate;
	}

	/**
	 * @param issCoupRate
	 *            the issCoupRate to set
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
	 * @param newCoupRate
	 *            the newCoupRate to set
	 */
	public void setNewCoupRate(BigDecimal newCoupRate) {
		this.newCoupRate = newCoupRate;
	}

	/**
	 * @return the intePayClsPar
	 */
	public Integer getIntePayClsPar() {
		return intePayClsPar;
	}

	/**
	 * @param intePayClsPar
	 *            the intePayClsPar to set
	 */
	public void setIntePayClsPar(Integer intePayClsPar) {
		this.intePayClsPar = intePayClsPar;
	}

	/**
	 * @return the inteParFreq
	 */
	public Integer getIntePayFreq() {
		return intePayFreq;
	}

	/**
	 * @param inteParFreq
	 *            the inteParFreq to set
	 */
	public void setIntePayFreq(Integer intePayFreq) {
		this.intePayFreq = intePayFreq;
	}

	/**
	 * @return the guraName
	 */
	public String getGuraName() {
		return guraName;
	}

	/**
	 * @param guraName
	 *            the guraName to set
	 */
	public void setGuraName(String guraName) {
		this.guraName = guraName;
	}

	/**
	 * @return the baseRatePar
	 */
	public Integer getBaseRatePar() {
		return baseRatePar;
	}

	/**
	 * @param baseRatePar
	 *            the baseRatePar to set
	 */
	public void setBaseRatePar(Integer baseRatePar) {
		this.baseRatePar = baseRatePar;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BondCcxeBasicInfo [" + (bondUniCode != null ? "bondUniCode=" + bondUniCode + ", " : "")
				+ (bondCode != null ? "bondCode=" + bondCode + ", " : "")
				+ (bondShortName != null ? "bondShortName=" + bondShortName + ", " : "")
				+ (bondFullName != null ? "bondFullName=" + bondFullName + ", " : "")
				+ (issName != null ? "issName=" + issName + ", " : "")
				+ (bondTypePar != null ? "bondTypePar=" + bondTypePar + ", " : "")
				+ (comUniCode != null ? "comUniCode=" + comUniCode + ", " : "")
				+ (comChiShortName != null ? "comChiShortName=" + comChiShortName + ", " : "")
				+ (areaUniCode1 != null ? "areaUniCode1=" + areaUniCode1 + ", " : "")
				+ (areaUniCode != null ? "areaUniCode=" + areaUniCode + ", " : "") + "]";
	}

	/**
	 * @return the credLevel
	 */
	public String getBondCredLevel() {
		return bondRredLevel;
	}

	/**
	 * @param credLevel
	 *            the credLevel to set
	 */
	public void setBondCredLevel(String credLevel) {
		this.bondRredLevel = credLevel;
	}

	/**
	 * @return the issCredLevel
	 */
	public String getIssCredLevel() {
		return issCredLevel;
	}

	/**
	 * @param issCredLevel
	 *            the issCredLevel to set
	 */
	public void setIssCredLevel(String issCredLevel) {
		this.issCredLevel = issCredLevel;
	}

	/**
	 * @return the ratePros
	 */
	public Integer getRatePros() {
		return ratePros;
	}

	/**
	 * @param ratePros
	 *            the ratePros to set
	 */
	public void setRatePros(Integer ratePros) {
		this.ratePros = ratePros;
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
	 * @return the comChiShortName
	 */
	public String getComChiShortName() {
		return comChiShortName;
	}

	/**
	 * @param comChiShortName
	 *            the comChiShortName to set
	 */
	public void setComChiShortName(String comChiShortName) {
		this.comChiShortName = comChiShortName;
	}

	/**
	 * @return the areaUniCode1
	 */
	public Long getAreaUniCode1() {
		return areaUniCode1;
	}

	/**
	 * @param areaUniCode1
	 *            the areaUniCode1 to set
	 */
	public void setAreaUniCode1(Long areaUniCode1) {
		this.areaUniCode1 = areaUniCode1;
	}

	/**
	 * @return the secMarPar
	 */
	public Integer getSecMarPar() {
		return secMarPar;
	}

	/**
	 * @param secMarPar
	 *            the secMarPar to set
	 */
	public void setSecMarPar(Integer secMarPar) {
		this.secMarPar = secMarPar;
	}

	/**
	 * @return the isTypePar
	 */
	public Integer getIsTypePar() {
		return isTypePar;
	}

	/**
	 * @param isTypePar
	 *            the isTypePar to set
	 */
	public void setIsTypePar(Integer isTypePar) {
		this.isTypePar = isTypePar;
	}

	/**
	 * @return the rateTypePar
	 */
	public Integer getRateTypePar() {
		return rateTypePar;
	}

	/**
	 * @param rateTypePar
	 *            the rateTypePar to set
	 */
	public void setRateTypePar(Integer rateTypePar) {
		this.rateTypePar = rateTypePar;
	}

	/**
	 * @return the comAttrPar
	 */
	public Integer getComAttrPar() {
		return comAttrPar;
	}

	/**
	 * @param comAttrPar
	 *            the comAttrPar to set
	 */
	public void setComAttrPar(Integer comAttrPar) {
		this.comAttrPar = comAttrPar;
	}

	/**
	 * @return the bondRredLevel
	 */
	public String getBondRredLevel() {
		return bondRredLevel;
	}

	/**
	 * @param bondRredLevel
	 *            the bondRredLevel to set
	 */
	public void setBondRredLevel(String bondRredLevel) {
		this.bondRredLevel = bondRredLevel;
	}

	/**
	 * @return the isCrosMarPar
	 */
	public Integer getIsCrosMarPar() {
		return isCrosMarPar;
	}

	/**
	 * @param isCrosMarPar
	 *            the isCrosMarPar to set
	 */
	public void setIsCrosMarPar(Integer isCrosMarPar) {
		this.isCrosMarPar = isCrosMarPar;
	}

	/**
	 * @return the pd
	 */
	public BigDecimal getPd() {
		return pd;
	}

	/**
	 * @param pd
	 *            the pd to set
	 */
	public void setPd(BigDecimal pd) {
		this.pd = pd;
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
	 * @return the matuPayDate
	 */
	public Date getMatuPayDate() {
		return matuPayDate;
	}

	/**
	 * @param matuPayDate
	 *            the matuPayDate to set
	 */
	public void setMatuPayDate(Date matuPayDate) {
		this.matuPayDate = matuPayDate;
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

	public Long getAreaUniCode() {
		return areaUniCode;
	}

	public void setAreaUniCode(Long areaUniCode) {
		this.areaUniCode = areaUniCode;
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

	public BigDecimal getFairValue() {
		return fairValue;
	}

	public void setFairValue(BigDecimal fairValue) {
		this.fairValue = fairValue;
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

	public String getIssCls() {
		return issCls;
	}

	public void setIssCls(String issCls) {
		this.issCls = issCls;
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

	public Double getBondParVal() {
		return bondParVal;
	}

	public void setBondParVal(Double bondParVal) {
		this.bondParVal = bondParVal;
	}

	public Double getIssPri() {
		return issPri;
	}

	public void setIssPri(Double issPri) {
		this.issPri = issPri;
	}

	public Double getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(Double payAmount) {
		this.payAmount = payAmount;
	}

	public Integer getListPar() {
		return listPar;
	}

	public void setListPar(Integer listPar) {
		this.listPar = listPar;
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

	public Date getIssStartDate() {
		return issStartDate;
	}

	public void setIssStartDate(Date issStartDate) {
		this.issStartDate = issStartDate;
	}

	public Date getIssEndDate() {
		return issEndDate;
	}

	public void setIssEndDate(Date issEndDate) {
		this.issEndDate = issEndDate;
	}

	public Date getPayStartDate() {
		return payStartDate;
	}

	public void setPayStartDate(Date payStartDate) {
		this.payStartDate = payStartDate;
	}

	public Date getPayEndDate() {
		return payEndDate;
	}

	public void setPayEndDate(Date payEndDate) {
		this.payEndDate = payEndDate;
	}

	public String getYearPayMatu() {
		return yearPayMatu;
	}

	public void setYearPayMatu(String yearPayMatu) {
		this.yearPayMatu = yearPayMatu;
	}

	public Long getCredOrgUniCode() {
		return credOrgUniCode;
	}

	public void setCredOrgUniCode(Long credOrgUniCode) {
		this.credOrgUniCode = credOrgUniCode;
	}

	public String getRateDes() {
		return rateDes;
	}

	public void setRateDes(String rateDes) {
		this.rateDes = rateDes;
	}

	public BigDecimal getBasSpr() {
		return basSpr;
	}

	public void setBasSpr(BigDecimal basSpr) {
		this.basSpr = basSpr;
	}

	public Date getListDate() {
		return listDate;
	}

	public void setListDate(Date listDate) {
		this.listDate = listDate;
	}

	public Date getListDeclDate() {
		return listDeclDate;
	}

	public void setListDeclDate(Date listDeclDate) {
		this.listDeclDate = listDeclDate;
	}

	public String getCollCapPurp() {
		return collCapPurp;
	}

	public void setCollCapPurp(String collCapPurp) {
		this.collCapPurp = collCapPurp;
	}

	public String getUndeName() {
		return undeName;
	}

	public void setUndeName(String undeName) {
		this.undeName = undeName;
	}

	public Integer getUndeClsPar() {
		return undeClsPar;
	}

	public void setUndeClsPar(Integer undeClsPar) {
		this.undeClsPar = undeClsPar;
	}

	public Date getBidDate() {
		return bidDate;
	}

	public void setBidDate(Date bidDate) {
		this.bidDate = bidDate;
	}

	public Date getBokepDate() {
		return bokepDate;
	}

	public void setBokepDate(Date bokepDate) {
		this.bokepDate = bokepDate;
	}

	public BigDecimal getIssFeeRate() {
		return issFeeRate;
	}

	public void setIssFeeRate(BigDecimal issFeeRate) {
		this.issFeeRate = issFeeRate;
	}

	public Date getDebtRegDate() {
		return debtRegDate;
	}

	public void setDebtRegDate(Date debtRegDate) {
		this.debtRegDate = debtRegDate;
	}

	public BigDecimal getSubsUnit() {
		return subsUnit;
	}

	public void setSubsUnit(BigDecimal subsUnit) {
		this.subsUnit = subsUnit;
	}

	public BigDecimal getLeastSubsUnit() {
		return leastSubsUnit;
	}

	public void setLeastSubsUnit(BigDecimal leastSubsUnit) {
		this.leastSubsUnit = leastSubsUnit;
	}

	public Integer getCuryTypePar() {
		return curyTypePar;
	}

	public void setCuryTypePar(Integer curyTypePar) {
		this.curyTypePar = curyTypePar;
	}

	public Integer getIsListPar() {
		return isListPar;
	}

	public void setIsListPar(Integer isListPar) {
		this.isListPar = isListPar;
	}

	public String getTradeUnit() {
		return tradeUnit;
	}

	public void setTradeUnit(String tradeUnit) {
		this.tradeUnit = tradeUnit;
	}

	public BigDecimal getCircuAmut() {
		return circuAmut;
	}

	public void setCircuAmut(BigDecimal circuAmut) {
		this.circuAmut = circuAmut;
	}

	public Integer getListSectPar() {
		return listSectPar;
	}

	public void setListSectPar(Integer listSectPar) {
		this.listSectPar = listSectPar;
	}

	public Date getTheoDelistDate() {
		return theoDelistDate;
	}

	public void setTheoDelistDate(Date theoDelistDate) {
		this.theoDelistDate = theoDelistDate;
	}

	public Date getLastTradeDate() {
		return lastTradeDate;
	}

	public void setLastTradeDate(Date lastTradeDate) {
		this.lastTradeDate = lastTradeDate;
	}

	public Date getActuDelistDate() {
		return actuDelistDate;
	}

	public void setActuDelistDate(Date actuDelistDate) {
		this.actuDelistDate = actuDelistDate;
	}

	public String getSpeShortName() {
		return speShortName;
	}

	public void setSpeShortName(String speShortName) {
		this.speShortName = speShortName;
	}

	public String getEngFullName() {
		return engFullName;
	}

	public void setEngFullName(String engFullName) {
		this.engFullName = engFullName;
	}

	public String getEngShortName() {
		return engShortName;
	}

	public void setEngShortName(String engShortName) {
		this.engShortName = engShortName;
	}

	public String getIsinCode() {
		return isinCode;
	}

	public void setIsinCode(String isinCode) {
		this.isinCode = isinCode;
	}

	public BigDecimal getBondParVl() {
		return bondParVl;
	}

	public void setBondParVl(BigDecimal bondParVl) {
		this.bondParVl = bondParVl;
	}

	public Integer getBondFormPar() {
		return bondFormPar;
	}

	public void setBondFormPar(Integer bondFormPar) {
		this.bondFormPar = bondFormPar;
	}

	public Date getActuEndDate() {
		return actuEndDate;
	}

	public void setActuEndDate(Date actuEndDate) {
		this.actuEndDate = actuEndDate;
	}

	public String getInteDes() {
		return inteDes;
	}

	public void setInteDes(String inteDes) {
		this.inteDes = inteDes;
	}

	public BigDecimal getRefYield() {
		return refYield;
	}

	public void setRefYield(BigDecimal refYield) {
		this.refYield = refYield;
	}

	public Date getInitBaseRateDate() {
		return initBaseRateDate;
	}

	public void setInitBaseRateDate(Date initBaseRateDate) {
		this.initBaseRateDate = initBaseRateDate;
	}

	public BigDecimal getBaseRate() {
		return baseRate;
	}

	public void setBaseRate(BigDecimal baseRate) {
		this.baseRate = baseRate;
	}

	public BigDecimal getOptExtraSpr() {
		return optExtraSpr;
	}

	public void setOptExtraSpr(BigDecimal optExtraSpr) {
		this.optExtraSpr = optExtraSpr;
	}

	public Integer getExtraSprSeqNum() {
		return extraSprSeqNum;
	}

	public void setExtraSprSeqNum(Integer extraSprSeqNum) {
		this.extraSprSeqNum = extraSprSeqNum;
	}

	public BigDecimal getRateCeil() {
		return rateCeil;
	}

	public void setRateCeil(BigDecimal rateCeil) {
		this.rateCeil = rateCeil;
	}

	public BigDecimal getRateFloor() {
		return rateFloor;
	}

	public void setRateFloor(BigDecimal rateFloor) {
		this.rateFloor = rateFloor;
	}

	public Integer getInteCalcuClsPar() {
		return inteCalcuClsPar;
	}

	public void setInteCalcuClsPar(Integer inteCalcuClsPar) {
		this.inteCalcuClsPar = inteCalcuClsPar;
	}

	public Integer getIsSegmPar() {
		return isSegmPar;
	}

	public void setIsSegmPar(Integer isSegmPar) {
		this.isSegmPar = isSegmPar;
	}

	public String getIntePayMeth() {
		return intePayMeth;
	}

	public void setIntePayMeth(String intePayMeth) {
		this.intePayMeth = intePayMeth;
	}

	public Integer getSimpCompIntePar() {
		return simpCompIntePar;
	}

	public void setSimpCompIntePar(Integer simpCompIntePar) {
		this.simpCompIntePar = simpCompIntePar;
	}

	public Integer getRepayClsPayPar() {
		return repayClsPayPar;
	}

	public void setRepayClsPayPar(Integer repayClsPayPar) {
		this.repayClsPayPar = repayClsPayPar;
	}

	public BigDecimal getPayFeeRate() {
		return payFeeRate;
	}

	public void setPayFeeRate(BigDecimal payFeeRate) {
		this.payFeeRate = payFeeRate;
	}

	public String getPayMatu() {
		return payMatu;
	}

	public void setPayMatu(String payMatu) {
		this.payMatu = payMatu;
	}

	public String getPayClsDes() {
		return payClsDes;
	}

	public void setPayClsDes(String payClsDes) {
		this.payClsDes = payClsDes;
	}

	public String getOptDes() {
		return optDes;
	}

	public void setOptDes(String optDes) {
		this.optDes = optDes;
	}

	public Integer getIsGuarPar() {
		return isGuarPar;
	}

	public void setIsGuarPar(Integer isGuarPar) {
		this.isGuarPar = isGuarPar;
	}

	public Integer getIsRepuPar() {
		return isRepuPar;
	}

	public void setIsRepuPar(Integer isRepuPar) {
		this.isRepuPar = isRepuPar;
	}

	public Integer getIsHedgePar() {
		return isHedgePar;
	}

	public void setIsHedgePar(Integer isHedgePar) {
		this.isHedgePar = isHedgePar;
	}

	public Integer getIsTaxFreePar() {
		return isTaxFreePar;
	}

	public void setIsTaxFreePar(Integer isTaxFreePar) {
		this.isTaxFreePar = isTaxFreePar;
	}

	public String getCrosMarDes() {
		return crosMarDes;
	}

	public void setCrosMarDes(String crosMarDes) {
		this.crosMarDes = crosMarDes;
	}

	public Integer getOrgIsTypePar() {
		return orgIsTypePar;
	}

	public void setOrgIsTypePar(Integer orgIsTypePar) {
		this.orgIsTypePar = orgIsTypePar;
	}

	public Integer getOrgIssStaPar() {
		return orgIssStaPar;
	}

	public void setOrgIssStaPar(Integer orgIssStaPar) {
		this.orgIssStaPar = orgIssStaPar;
	}
}
