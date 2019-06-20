package com.innodealing.model.dm.bond;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="t_bond_basic_info")
public class BondBasicInfo implements Serializable{
	/**
     * serialVersionUID:TODO（用一句话描述这个变量表示什么）
     *
     * @since Ver 1.1
     */
    
    private static final long serialVersionUID = 1L;

	/**
	 * 债券的uni code
	 */
	@Id
    @Column(nullable=false, name="bond_uni_code",length=19)
    private Long bondUniCode;
    
	/**
	 * 债券代码
	 */
    @Column(name="bond_code",length=18)
    private String bondCode;
    
	/**
	 * 
	 */
    @Column(name="bond_short_name",length=200)
    private String bondShortName;
    
	/**
	 * 债券全称
	 */
    @Column(name="bond_full_name",length=200)
    private String bondFullName;
    
    
	/**
	 * 发行人统一编码
	 */
    @Column(name="com_uni_code",length=20)
    private Long comUniCode;
    
	/**
	 * 发行人名称
	 */
    @Column(name="iss_name",length=200)
    private String issName;
    
    
	/**
	 * 发行起始日
	 */
    @Column(name="iss_start_date")
    private Date issStartDate;
    
	/**
	 * 发行截止日
	 */
    @Column(name="iss_end_date")
    private Date issEndDate;
    
	/**
	 * 缴款起始日期
	 */
    @Column(name="pay_start_date")
    private Date payStartDate;
    
    
	/**
	 * 缴款截止日期
	 */
    @Column(name="pay_end_date")
    private Date payEndDate;
    
	/**
	 * 每年付息日
	 */
    @Column(name="year_pay_date", length=40)
    private String yearPayDate;
    
	/**
	 * 每年付息期限(文字描述)
	 */
    @Column(name="year_pay_matu")
    private String yearPayMatu;
    
	/**
	 * 起息日
	 */
    @Column(name="inte_start_date")
    private Date inteStartDate;
    
	/**
	 * 理论到期日
	 */
    @Column(name="theo_end_date")
    private Date theoEndDate;
    
	/**
	 * 付息频率
	 */
    @Column(name="inte_pay_freq")
    private Integer intePayFreq;
    
	/**
	 * 付息方式
	 */
    @Column(name="inte_pay_cls_par")
    private Integer intePayClsPar;
    
	/**
	 * 行权兑付日
	 */
    @Column(name="exer_pay_date")
    private String exerPayDate;
    
	/**
	 * 行权备注
	 */
    @Column(name="exer_remark")
    private String exerRemark;
    
	/**
	 * 计划发行规模
	 */
    @Column(name="plan_iss_scale")
    private BigDecimal planIssScale;
    
	/**
	 * 计划发行规模单位
	 */
    @Column(name="plan_iss_scale_unit", length=10)
    private String planIssScaleUnit;
    
	/**
	 * 计划发行总额(含增发)
	 */
    @Column(name="plan_iss_amut")
    private BigDecimal planIssAmut;
    
	/**
	 * 计划发行总额(含增发)单位 
	 */
    @Column(name="plan_iss_amut_unit")
    private String planIssAmutUnit;//  
    
	/**
	 * 募集金额/发行总额
	 */
    @Column(name="actu_fir_iss_amut")
    private BigDecimal actuFirIssAmut;//
    
	/**
	 * 募集金额/发行总额单位 
	 */
    @Column(name="actu_fir_iss_amut_unit")
    private String actuFirIssAmutUnit;//
    
    @Column(name="actu_iss_amut")
    private BigDecimal actuIssAmut;//实际发行总额(含增发)
    
    @Column(name="actu_iss_amut_unit")
    private String actuIssAmutUnit;//实际发行总额(含增发)单位       
    
    @Column(name="bond_matu")
    private String bondMatu;//债券期限
    
    @Column(name="bond_type_par")
    private Integer bondTypePar;//债券类型
    
    @Column(name="new_rate")
    private String newRate;//最新债项评级
    
    @Column(name="iss_cred_level")
    private String issCredLevel;//主体评级
    
    @Column(name="cred_uni_code")
    private Long credOrgUniCode;//评级机构
    
    @Column(name="rate_type_par")
    private Integer rateTypePar;//利率类型
    
    @Column(name="rate_des")
    private String rateDes;//利率简述
    
    @Column(name="iss_coup_rate")
    private BigDecimal issCoupRate;//票面利率
    
    @Column(name="base_rate_par")
    private Integer baseRatePar;//浮息基准
    
    @Column(name="bas_spr")
    private BigDecimal basSpr;//利差
    
    @Column(name="list_date")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date listDate;//上市日期
    
    @Column(name="list_decl_date")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date listDeclDate;//上市公告日期
    
    @Column(name="sec_mar_par")
    private Integer secMarPar;//流通市场
    
    @Column(name="coll_cap_purp")
    private String collCapPurp;//募集资金用途
    
    @Column(name="unde_name")
    private String undeName;//主承销商
    
    @Column(name="unde_cls_par")
    private Integer undeClsPar;//承销方式
    
    @Column(name="iss_cls")
    private String issCls;//发行方式  //0-招标  1-薄记建档
    
    @Column(name="iss_cls_des")
    private String issClsDes;//发行方式简述(dm新增)
    
    @Column(name="iss_pri")
    private Double issPri;//发行价格
    
    @Column(name="book_manager")
    private String bookManager;//簿记管理人
    
    @Column(name="subscription_rate")
    private BigDecimal subscriptionRate;//认购倍率
    
    @Column(name="tender_bid")
    private Integer tenderBid;//招标标的
    
    @Column(name="tender_type")
    private Integer tenderType;//招标方式
    
    @Column(name="full_court_mult")
    private BigDecimal fullCourtMult;//全场倍数
    
    @Column(name="marginal_mult")
    private BigDecimal marginalMult;//边际倍数
    
    @Column(name="bid_date")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date bidDate;//招标日
    
    @Column(name="did_interval_low")
    private BigDecimal didIntervalLow;//招标区间下限
    
    @Column(name="did_interval_sup")
    private BigDecimal didIntervalSup;//招标区间上限
    
    @Column(name="stop_bid_start_date")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
    private Date stopBidStartDate;//截标开始时间
    
    @Column(name="stop_bid_end_date")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
    private Date stopBidEndDate;//截标结束时间
    
    @Column(name="iss_status")
    private String issStatus;//发行状态:0-发行中 1-已上市 2-延迟发行 3-取消发行
    
    @Column(name="push_status")
    private String pushStatus;//推送状态:0-未推送 1-已推送
    
    @Column(name="edit_status")
    private String editStatus;//编辑状态:0-未编辑 1-待编辑 2-无需编辑
    
    @Column(name="bond_iss_year")
    private String bondIssYear;//债券发行年度
    
    @Column(name="iss_decl_date")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date issDeclDate;//发行公告日期
    
    @Column(name="iss_obj")
    private String issObj;//发行对象
    
    @Column(name="is_public_iss")
    private String isPublicIss;//是否公开发行:0-否 1-是
    
    @Column(name="iss_fee_rate")
    private BigDecimal issFeeRate;//发行手续费率
    
    @Column(name="bokep_date")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date bokepDate;//簿记建档日
    
    @Column(name="debt_reg_date")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date debtRegDate;//债权债务登记日
    
    @Column(name="new_size")
    private Double newSize;//当前债券规模
    
    @Column(name="subs_unit")
    private BigDecimal subsUnit;//认购单位(元)
    
    @Column(name="least_subs_unit")
    private BigDecimal leastSubsUnit;//最小认购数量
    
    @Column(name="cury_type_par")
    private Integer curyTypePar;//货币类型
    
    @Column(name="is_list_par")
    private Integer isListPar;//是否流通
    
    @Column(name="trade_unit")
    private String tradeUnit;//交易单位
    
    @Column(name="circu_amut")
    private BigDecimal circuAmut;//流通总额
    
    @Column(name="is_cros_mar_par")
    private String isCrosMarPar;//是否跨市场交易:0-否 1-是
    
    @Column(name="cros_mar_des")
    private String crosMarDes;//跨市场说明
    
    @Column(name="list_sect_par")
    private Integer listSectPar;//上市板块
    
    @Column(name="list_sta_par")
    private Integer listStaPar;//上市状态
    
    @Column(name="theo_delist_date")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date theoDelistDate;//理论退市日期
    
    @Column(name="last_trade_date")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date lastTradeDate;//最后交易日
    
    @Column(name="actu_delist_date")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date actuDelistDate;//摘牌日
    
    @Column(name="spe_short_name")
    private String speShortName;//拼音简称
    
    @Column(name="eng_full_name")
    private String engFullName;//英文全称
    
    @Column(name="eng_short_name")
    private String engShortName;//英文简称
    
    @Column(name="isin_code")
    private String isinCode;//ISIN代码
    
    @Column(name="curr_status")
    private Integer currStatus;//当前存续状态
    
    @Column(name="bond_par_val")
    private BigDecimal bondParVl;//债券面值
    
    @Column(name="is_type_par")
    private Integer isTypePar;//特殊债券类型
    
    @Column(name="bond_form_par")
    private Integer bondFormPar;//债券形态
    
    @Column(name="actu_end_date")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date actuEndDate;//实际到期日
    
    @Column(name="inte_des")
    private String inteDes;//计息说明
    
    @Column(name="new_coup_rate")
    private BigDecimal newCoupRate;//最新票面利率
    
    @Column(name="ref_yield")
    private BigDecimal refYield;//贴现债参考收益率
    
    @Column(name="init_base_rate_date")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date initBaseRateDate;//首期利率基准日期
    
    @Column(name="base_rate")
    private BigDecimal baseRate;//首期基准利率
    
    @Column(name="opt_extra_spr")
    private BigDecimal optExtraSpr;//含权额外利差
    
    @Column(name="extra_spr_seq_num")
    private Integer extraSprSeqNum;//额外利差启用期次
    
    @Column(name="rate_ceil")
    private BigDecimal rateCeil;//利率浮动上限
    
    @Column(name="rate_floor")
    private BigDecimal rateFloor;//利率浮动下限
    
    @Column(name="inte_calcu_cls_par")
    private Integer inteCalcuClsPar;//利息计算方式
    
    @Column(name="is_segm_par")
    private Integer isSegmPar;//是否分段计息
    
    @Column(name="inte_pay_meth")
    private String intePayMeth;//利息支付方式说明
    
    @Column(name="simp_comp_inte_par")
    private Integer simpCompIntePar;//单利或复利
    
    @Column(name="repay_cls_pay_par")
    private Integer repayClsPayPar;//偿还方式
    
    @Column(name="matu_pay_date")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date matuPayDate;//兑付日
    
    @Column(name="pay_fee_rate")
    private BigDecimal payFeeRate;//兑付手续费率
    
    @Column(name="pay_matu")
    private String payMatu;//兑付期限
    
    @Column(name="pay_cls_des")
    private String payClsDes;//兑付方式说明
    
    @Column(name="opt_des")
    private String optDes;//含权说明
    
    @Column(name="is_guar_par")
    private Integer isGuarPar;//是否有担保
    
    @Column(name="is_repu_par")
    private Integer isRepuPar;//是否可回购
    
    @Column(name="pledge_code")
    private String pledgeCode;//质押券代码
    
    @Column(name="pledge_name")
    private String pledgeName;//质押券简称
    
    @Column(name="is_redem_par")
    private Integer isRedemPar;//是否可赎回
    
    @Column(name="is_resa_par")
    private Integer isResaPar;//是否可回售
    
    @Column(name="is_hedge_par")
    private Integer isHedgePar;//是否保值
    
    @Column(name="is_tax_free_par")
    private Integer isTaxFreePar;//是否免税
    
    @Column(name="create_user")
    private Long createUser;//创建人
    
    @Column(name="last_update_user")
    private Long lastUpdateUser;//最后编辑人
    
    @Column(name="create_time")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;//创建时间
    
    @Column(name="last_update_time")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateTime;//最后编辑时间
    
    @Column(name="is_new")
    private String isNew; //是否dm搜集的债
    
    //20180312之后新增
    @Column(name="tenor")
    private String tenor; //剩余期限
    
    @Column(name="is_list_par")
    private Integer listPar;// 是否上市公司
    
	public Long getBondUniCode() {
		return bondUniCode;
	}

	public void setBondUniCode(Long bondUniCode) {
		this.bondUniCode = bondUniCode;
	}

	public String getBondCode() {
		return bondCode;
	}

	public void setBondCode(String bondCode) {
		this.bondCode = bondCode;
	}

	public String getBondShortName() {
		return bondShortName;
	}

	public void setBondShortName(String bondShortName) {
		this.bondShortName = bondShortName;
	}

	public String getBondFullName() {
		return bondFullName;
	}

	public void setBondFullName(String bondFullName) {
		this.bondFullName = bondFullName;
	}

	public Long getComUniCode() {
		return comUniCode;
	}

	public void setComUniCode(Long comUniCode) {
		this.comUniCode = comUniCode;
	}

	public String getIssName() {
		return issName;
	}

	public void setIssName(String issName) {
		this.issName = issName;
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

	public String getYearPayDate() {
		return yearPayDate;
	}

	public void setYearPayDate(String yearPayDate) {
		this.yearPayDate = yearPayDate;
	}

	public String getYearPayMatu() {
		return yearPayMatu;
	}

	public void setYearPayMatu(String yearPayMatu) {
		this.yearPayMatu = yearPayMatu;
	}

	public Date getInteStartDate() {
		return inteStartDate;
	}

	public void setInteStartDate(Date inteStartDate) {
		this.inteStartDate = inteStartDate;
	}

	public Date getTheoEndDate() {
		return theoEndDate;
	}

	public void setTheoEndDate(Date theoEndDate) {
		this.theoEndDate = theoEndDate;
	}

	public Integer getIntePayFreq() {
		return intePayFreq;
	}

	public void setIntePayFreq(Integer intePayFreq) {
		this.intePayFreq = intePayFreq;
	}

	public Integer getIntePayClsPar() {
		return intePayClsPar;
	}

	public void setIntePayClsPar(Integer intePayClsPar) {
		this.intePayClsPar = intePayClsPar;
	}

	public String getExerPayDate() {
		return exerPayDate;
	}

	public void setExerPayDate(String exerPayDate) {
		this.exerPayDate = exerPayDate;
	}

	public String getExerRemark() {
		return exerRemark;
	}

	public void setExerRemark(String exerRemark) {
		this.exerRemark = exerRemark;
	}

	public BigDecimal getPlanIssScale() {
		return planIssScale;
	}

	public void setPlanIssScale(BigDecimal planIssScale) {
		this.planIssScale = planIssScale;
	}

	public String getPlanIssScaleUnit() {
		return planIssScaleUnit;
	}

	public void setPlanIssScaleUnit(String planIssScaleUnit) {
		this.planIssScaleUnit = planIssScaleUnit;
	}

	public BigDecimal getPlanIssAmut() {
		return planIssAmut;
	}

	public void setPlanIssAmut(BigDecimal planIssAmut) {
		this.planIssAmut = planIssAmut;
	}

	public String getPlanIssAmutUnit() {
		return planIssAmutUnit;
	}

	public void setPlanIssAmutUnit(String planIssAmutUnit) {
		this.planIssAmutUnit = planIssAmutUnit;
	}

	public BigDecimal getActuFirIssAmut() {
		return actuFirIssAmut;
	}

	public void setActuFirIssAmut(BigDecimal actuFirIssAmut) {
		this.actuFirIssAmut = actuFirIssAmut;
	}

	public String getActuFirIssAmutUnit() {
		return actuFirIssAmutUnit;
	}

	public void setActuFirIssAmutUnit(String actuFirIssAmutUnit) {
		this.actuFirIssAmutUnit = actuFirIssAmutUnit;
	}

	public BigDecimal getActuIssAmut() {
		return actuIssAmut;
	}

	public void setActuIssAmut(BigDecimal actuIssAmut) {
		this.actuIssAmut = actuIssAmut;
	}

	public String getActuIssAmutUnit() {
		return actuIssAmutUnit;
	}

	public void setActuIssAmutUnit(String actuIssAmutUnit) {
		this.actuIssAmutUnit = actuIssAmutUnit;
	}

	public String getBondMatu() {
		return bondMatu;
	}

	public void setBondMatu(String bondMatu) {
		this.bondMatu = bondMatu;
	}

	public Integer getBondTypePar() {
		return bondTypePar;
	}

	public void setBondTypePar(Integer bondTypePar) {
		this.bondTypePar = bondTypePar;
	}

	public String getNewRate() {
		return newRate;
	}

	public void setNewRate(String newRate) {
		this.newRate = newRate;
	}

	public String getIssCredLevel() {
		return issCredLevel;
	}

	public void setIssCredLevel(String issCredLevel) {
		this.issCredLevel = issCredLevel;
	}

	public Long getCredOrgUniCode() {
		return credOrgUniCode;
	}

	public void setCredOrgUniCode(Long credOrgUniCode) {
		this.credOrgUniCode = credOrgUniCode;
	}

	public Integer getRateTypePar() {
		return rateTypePar;
	}

	public void setRateTypePar(Integer rateTypePar) {
		this.rateTypePar = rateTypePar;
	}

	public String getRateDes() {
		return rateDes;
	}

	public void setRateDes(String rateDes) {
		this.rateDes = rateDes;
	}

	public BigDecimal getIssCoupRate() {
		return issCoupRate;
	}

	public void setIssCoupRate(BigDecimal issCoupRate) {
		this.issCoupRate = issCoupRate;
	}

	public Integer getBaseRatePar() {
		return baseRatePar;
	}

	public void setBaseRatePar(Integer baseRatePar) {
		this.baseRatePar = baseRatePar;
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

	public Integer getSecMarPar() {
		return secMarPar;
	}

	public void setSecMarPar(Integer secMarPar) {
		this.secMarPar = secMarPar;
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

	public String getIssCls() {
		return issCls;
	}

	public void setIssCls(String issCls) {
		this.issCls = issCls;
	}

	public String getIssClsDes() {
		return issClsDes;
	}

	public void setIssClsDes(String issClsDes) {
		this.issClsDes = issClsDes;
	}

	public Double getIssPri() {
		return issPri;
	}

	public void setIssPri(Double issPri) {
		this.issPri = issPri;
	}

	public String getBookManager() {
		return bookManager;
	}

	public void setBookManager(String bookManager) {
		this.bookManager = bookManager;
	}

	public BigDecimal getSubscriptionRate() {
		return subscriptionRate;
	}

	public void setSubscriptionRate(BigDecimal subscriptionRate) {
		this.subscriptionRate = subscriptionRate;
	}

	public Integer getTenderBid() {
		return tenderBid;
	}

	public void setTenderBid(Integer tenderBid) {
		this.tenderBid = tenderBid;
	}

	public Integer getTenderType() {
		return tenderType;
	}

	public void setTenderType(Integer tenderType) {
		this.tenderType = tenderType;
	}

	public BigDecimal getFullCourtMult() {
		return fullCourtMult;
	}

	public void setFullCourtMult(BigDecimal fullCourtMult) {
		this.fullCourtMult = fullCourtMult;
	}

	public BigDecimal getMarginalMult() {
		return marginalMult;
	}

	public void setMarginalMult(BigDecimal marginalMult) {
		this.marginalMult = marginalMult;
	}

	public Date getBidDate() {
		return bidDate;
	}

	public void setBidDate(Date bidDate) {
		this.bidDate = bidDate;
	}

	public BigDecimal getDidIntervalLow() {
		return didIntervalLow;
	}

	public void setDidIntervalLow(BigDecimal didIntervalLow) {
		this.didIntervalLow = didIntervalLow;
	}

	public BigDecimal getDidIntervalSup() {
		return didIntervalSup;
	}

	public void setDidIntervalSup(BigDecimal didIntervalSup) {
		this.didIntervalSup = didIntervalSup;
	}

	public Date getStopBidStartDate() {
		return stopBidStartDate;
	}

	public void setStopBidStartDate(Date stopBidStartDate) {
		this.stopBidStartDate = stopBidStartDate;
	}

	public Date getStopBidEndDate() {
		return stopBidEndDate;
	}

	public void setStopBidEndDate(Date stopBidEndDate) {
		this.stopBidEndDate = stopBidEndDate;
	}

	public String getIssStatus() {
		return issStatus;
	}

	public void setIssStatus(String issStatus) {
		this.issStatus = issStatus;
	}

	public String getPushStatus() {
		return pushStatus;
	}

	public void setPushStatus(String pushStatus) {
		this.pushStatus = pushStatus;
	}

	public String getEditStatus() {
		return editStatus;
	}

	public void setEditStatus(String editStatus) {
		this.editStatus = editStatus;
	}

	public String getBondIssYear() {
		return bondIssYear;
	}

	public void setBondIssYear(String bondIssYear) {
		this.bondIssYear = bondIssYear;
	}

	public Date getIssDeclDate() {
		return issDeclDate;
	}

	public void setIssDeclDate(Date issDeclDate) {
		this.issDeclDate = issDeclDate;
	}

	public String getIssObj() {
		return issObj;
	}

	public void setIssObj(String issObj) {
		this.issObj = issObj;
	}

	public String getIsPublicIss() {
		return isPublicIss;
	}

	public void setIsPublicIss(String isPublicIss) {
		this.isPublicIss = isPublicIss;
	}

	public BigDecimal getIssFeeRate() {
		return issFeeRate;
	}

	public void setIssFeeRate(BigDecimal issFeeRate) {
		this.issFeeRate = issFeeRate;
	}

	public Date getBokepDate() {
		return bokepDate;
	}

	public void setBokepDate(Date bokepDate) {
		this.bokepDate = bokepDate;
	}

	public Date getDebtRegDate() {
		return debtRegDate;
	}

	public void setDebtRegDate(Date debtRegDate) {
		this.debtRegDate = debtRegDate;
	}

	public Double getNewSize() {
		return newSize;
	}

	public void setNewSize(Double newSize) {
		this.newSize = newSize;
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

	public String getIsCrosMarPar() {
		return isCrosMarPar;
	}

	public void setIsCrosMarPar(String isCrosMarPar) {
		this.isCrosMarPar = isCrosMarPar;
	}

	public String getCrosMarDes() {
		return crosMarDes;
	}

	public void setCrosMarDes(String crosMarDes) {
		this.crosMarDes = crosMarDes;
	}

	public Integer getListSectPar() {
		return listSectPar;
	}

	public void setListSectPar(Integer listSectPar) {
		this.listSectPar = listSectPar;
	}

	public Integer getListStaPar() {
		return listStaPar;
	}

	public void setListStaPar(Integer listStaPar) {
		this.listStaPar = listStaPar;
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

	public Integer getCurrStatus() {
		return currStatus;
	}

	public void setCurrStatus(Integer currStatus) {
		this.currStatus = currStatus;
	}

	public BigDecimal getBondParVl() {
		return bondParVl;
	}

	public void setBondParVl(BigDecimal bondParVl) {
		this.bondParVl = bondParVl;
	}

	public Integer getIsTypePar() {
		return isTypePar;
	}

	public void setIsTypePar(Integer isTypePar) {
		this.isTypePar = isTypePar;
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

	public BigDecimal getNewCoupRate() {
		return newCoupRate;
	}

	public void setNewCoupRate(BigDecimal newCoupRate) {
		this.newCoupRate = newCoupRate;
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

	public Date getMatuPayDate() {
		return matuPayDate;
	}

	public void setMatuPayDate(Date matuPayDate) {
		this.matuPayDate = matuPayDate;
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

	public String getPledgeCode() {
		return pledgeCode;
	}

	public void setPledgeCode(String pledgeCode) {
		this.pledgeCode = pledgeCode;
	}

	public String getPledgeName() {
		return pledgeName;
	}

	public void setPledgeName(String pledgeName) {
		this.pledgeName = pledgeName;
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

	public Long getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Long createUser) {
		this.createUser = createUser;
	}

	public Long getLastUpdateUser() {
		return lastUpdateUser;
	}

	public void setLastUpdateUser(Long lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public String getIsNew() {
		return isNew;
	}

	public void setIsNew(String isNew) {
		this.isNew = isNew;
	}

    public String getTenor() {
        return tenor;
    }

    public void setTenor(String tenor) {
        this.tenor = tenor;
    }

    public Integer getListPar() {
        return listPar;
    }

    public void setListPar(Integer listPar) {
        this.listPar = listPar;
    }

	
 
}
