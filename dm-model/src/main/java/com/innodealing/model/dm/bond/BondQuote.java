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


@Entity
@Table(name="t_bond_quote")
public class BondQuote implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
	/**
	 * 报价ID字符串，兼容老系统
	 */
    @Column(name="quote_id",length=32)
    private String quoteId;
    
	/**
	 * 
	 */
    @Column(name="msg_id",length=32)
    private String msgId;
    
	/**
	 * 债券类型，兼容老系统subtype
	 */
    @Column(name="sub_type",length=3)
    private Integer subType;
    
	/**
	 * 债券uni_code
	 */
    @Column(name="bond_uni_code",length=19)
    private Long bondUniCode;
    
	/**
	 * 债券代码
	 */
    @Column(name="bond_code",length=18)
    private String bondCode;
    
	/**
	 * 债券名称,券种
	 */
    @Column(name="bond_short_name",length=200)
    private String bondShortName;
    
	/**
	 * 1收券-bid，2出券-ofr，3发行
	 */
    @Column(name="side",length=3)
    private Integer side;
    
	/**
	 * 
	 */
    @Column(name="bond_price",length=10)
    private BigDecimal bondPrice;
    
	/**
	 * 
	 */
    @Column(name="bond_vol",length=10)
    private BigDecimal bondVol;
    
	/**
	 * 收益率/净价单位，1 % ，2元
	 */
    @Column(name="price_unit",length=3)
    private Integer priceUnit;
    
	/**
	 * 剩余期限，单位天
	 */
    @Column(name="tenor",length=10)
    private Integer tenor;
    
	/**
	 * 是否匿名报价,0 否，1 是
	 */
    @Column(name="anonymous",length=3)
    private Integer anonymous;
    
	/**
	 * 是否隐藏利率，0 否，1 是
	 */
    @Column(name="is_hide_rate",length=3)
    private Integer isHideRate;
    
	/**
	 * 报价中的批准的属性
	 */
    @Column(name="approval",length=3)
    private Integer approval;
    
	/**
	 * 0 QQ的个人群1 QQ的Report群2 DM数据源3 DM众包4 IDM私人群
	 */
    @Column(name="source",length=3)
    private Integer source;
    
	/**
	 * 备注
	 */
    @Column(name="remark",length=256)
    private String remark;
    
	/**
	 * QQ和IDM的原文
	 */
    @Column(name="naturalmsg",length=2147483647)
    private String naturalmsg;
    
	/**
	 * 结构化原文
	 */
    @Column(name="rawcontent",length=65535)
    private String rawcontent;
    
	/**
	 * 群号
	 */
    @Column(name="troop_id",length=32)
    private String troopId;
    
	/**
	 * 群名称
	 */
    @Column(name="troop_name",length=64)
    private String troopName;
    
	/**
	 * 机构类型
	 */
    @Column(name="inst_type",length=3)
    private Integer instType;
    
	/**
	 * 机构简称
	 */
    @Column(name="inst_short",length=64)
    private String instShort;
    
	/**
	 * 机构ID
	 */
    @Column(name="inst_id",length=10)
    private Integer instId;
    
	/**
	 * 账号ID
	 */
    @Column(name="user_id",length=10)
    private Long userId;
    
	/**
	 * 用户名
	 */
    @Column(name="user_name",length=64)
    private String userName;
    
	/**
	 * 用户微信号
	 */
    @Column(name="wechatno",length=64)
    private String wechatno;
    
	/**
	 * 0 QQ1微信2 PC客户端3 PC网页端4手机APP(老版本)5管理后台6 IM群 7 Android 8 IOS 9 Broker来源
	 */
    @Column(name="postfrom",length=10)
    private Integer postfrom;
    
	/**
	 * 状态，1 正常 2 已成交 99 已取消
	 */
    @Column(name="status",length=3)
    private Integer status;
    
	/**
	 * 需求是否同时发送报价，0 需求，1 报价
	 */
    @Column(name="open_quote",length=3)
    private Integer openQuote;
    
	/**
	 * 发布时间
	 */
    @Column(name="send_time",length=19)
    private Date sendTime;
    
	/**
	 * 更新时间
	 */
    @Column(name="update_time",length=19)
    private Date updateTime;
    
	/**
	 * 债券的原始Code，不带市场
	 */
    @Column(name="bond_org_code",length=10)
    private Integer bondOrgCode;
    
	/**
	 * QQ号
	 */
    @Column(name="qq_num",length=64)
    private String qqNum;
    
	/**
	 * 更新记录
	 */
    @Column(name="last_updateby",length=2147483647)
    private String lastUpdateby;
    
	/**
	 * 脏价，全价，结算价
	 */
    @Column(name="dirty_price", length=10)
    private BigDecimal dirtyPrice;
    
	/**
	 * 净价，报价
	 */
    @Column(name="clean_price", length=10)
    private BigDecimal cleanPrice;
    
	/**
	 * 到期收益率
	 */
    @Column(name="ytm", length=10)
    private BigDecimal ytm;
    
	/**
	 * QB中Broker数据的type类型
	 */
    @Column(name="broker_type",length=10)
    private Integer brokerType = 0;
    
    /**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * @param quoteId the quoteId to set
	 */
    public void setQuoteId(String quoteId){
       this.quoteId = quoteId;
    }
    
    /**
     * @return the quoteId 
     */
    public String getQuoteId(){
       return this.quoteId;
    }
	
	/**
	 * @param msgId the msgId to set
	 */
    public void setMsgId(String msgId){
       this.msgId = msgId;
    }
    
    /**
     * @return the msgId 
     */
    public String getMsgId(){
       return this.msgId;
    }
	
	/**
	 * @param subType the subType to set
	 */
    public void setSubType(Integer subType){
       this.subType = subType;
    }
    
    /**
     * @return the subType 
     */
    public Integer getSubType(){
       return this.subType;
    }
	
	/**
	 * @param bondUniCode the bondUniCode to set
	 */
    public void setBondUniCode(Long bondUniCode){
       this.bondUniCode = bondUniCode;
    }
    
    /**
     * @return the bondUniCode 
     */
    public Long getBondUniCode(){
       return this.bondUniCode;
    }
	
	/**
	 * @param bondCode the bondCode to set
	 */
    public void setBondCode(String bondCode){
       this.bondCode = bondCode;
    }
    
    /**
     * @return the bondCode 
     */
    public String getBondCode(){
       return this.bondCode;
    }
	
	/**
	 * @param bondShortName the bondShortName to set
	 */
    public void setBondShortName(String bondShortName){
       this.bondShortName = bondShortName;
    }
    
    /**
     * @return the bondShortName 
     */
    public String getBondShortName(){
       return this.bondShortName;
    }
	
	/**
	 * @param side the side to set
	 */
    public void setSide(Integer side){
       this.side = side;
    }
    
    /**
     * @return the side 
     */
    public Integer getSide(){
       return this.side;
    }
	
	/**
	 * @param bondPrice the bondPrice to set
	 */
    public void setBondPrice(BigDecimal bondPrice){
       this.bondPrice = bondPrice;
    }
    
    /**
     * @return the bondPrice 
     */
    public BigDecimal getBondPrice(){
       return this.bondPrice;
    }
	
	/**
	 * @param bondVol the bondVol to set
	 */
    public void setBondVol(BigDecimal bondVol){
       this.bondVol = bondVol;
    }
    
    /**
     * @return the bondVol 
     */
    public BigDecimal getBondVol(){
       return this.bondVol;
    }
    
	/**
	 * @param priceUnit the priceUnit to set
	 */
    public void setPriceUnit(Integer priceUnit){
       this.priceUnit = priceUnit;
    }
    
    /**
     * @return the priceUnit 
     */
    public Integer getPriceUnit(){
       return this.priceUnit;
    }
	
	/**
	 * @param tenor the tenor to set
	 */
    public void setTenor(Integer tenor){
       this.tenor = tenor;
    }
    
    /**
     * @return the tenor 
     */
    public Integer getTenor(){
       return this.tenor;
    }
	
	/**
	 * @param anonymous the anonymous to set
	 */
    public void setAnonymous(Integer anonymous){
       this.anonymous = anonymous;
    }
    
    /**
     * @return the anonymous 
     */
    public Integer getAnonymous(){
       return this.anonymous;
    }
	
	/**
	 * @param isHideRate the isHideRate to set
	 */
    public void setIsHideRate(Integer isHideRate){
       this.isHideRate = isHideRate;
    }
    
    /**
     * @return the isHideRate 
     */
    public Integer getIsHideRate(){
       return this.isHideRate;
    }
	
	/**
	 * @param approval the approval to set
	 */
    public void setApproval(Integer approval){
       this.approval = approval;
    }
    
    /**
     * @return the approval 
     */
    public Integer getApproval(){
       return this.approval;
    }
	
	/**
	 * @param source the source to set
	 */
    public void setSource(Integer source){
       this.source = source;
    }
    
    /**
     * @return the source 
     */
    public Integer getSource(){
       return this.source;
    }
	
	/**
	 * @param remark the remark to set
	 */
    public void setRemark(String remark){
       this.remark = remark;
    }
    
    /**
     * @return the remark 
     */
    public String getRemark(){
       return this.remark;
    }
	
	/**
	 * @param naturalmsg the naturalmsg to set
	 */
    public void setNaturalmsg(String naturalmsg){
       this.naturalmsg = naturalmsg;
    }
    
    /**
     * @return the naturalmsg 
     */
    public String getNaturalmsg(){
       return this.naturalmsg;
    }
	
	/**
	 * @param rawcontent the rawcontent to set
	 */
    public void setRawcontent(String rawcontent){
       this.rawcontent = rawcontent;
    }
    
    /**
     * @return the rawcontent 
     */
    public String getRawcontent(){
       return this.rawcontent;
    }
	
	/**
	 * @param troopId the troopId to set
	 */
    public void setTroopId(String troopId){
       this.troopId = troopId;
    }
    
    /**
     * @return the troopId 
     */
    public String getTroopId(){
       return this.troopId;
    }
	
	/**
	 * @param troopName the troopName to set
	 */
    public void setTroopName(String troopName){
       this.troopName = troopName;
    }
    
    /**
     * @return the troopName 
     */
    public String getTroopName(){
       return this.troopName;
    }
	
	/**
	 * @param instType the instType to set
	 */
    public void setInstType(Integer instType){
       this.instType = instType;
    }
    
    /**
     * @return the instType 
     */
    public Integer getInstType(){
       return this.instType;
    }
	
	/**
	 * @param instShort the instShort to set
	 */
    public void setInstShort(String instShort){
       this.instShort = instShort;
    }
    
    /**
     * @return the instShort 
     */
    public String getInstShort(){
       return this.instShort;
    }
	
	/**
	 * @param instId the instId to set
	 */
    public void setInstId(Integer instId){
       this.instId = instId;
    }
    
    /**
     * @return the instId 
     */
    public Integer getInstId(){
       return this.instId;
    }
	
	/**
	 * @param userId the userId to set
	 */
    public void setUserId(Long userId){
       this.userId = userId;
    }
    
    /**
     * @return the userId 
     */
    public Long getUserId(){
       return this.userId;
    }
	
	/**
	 * @param userName the userName to set
	 */
    public void setUserName(String userName){
       this.userName = userName;
    }
    
    /**
     * @return the userName 
     */
    public String getUserName(){
       return this.userName;
    }
	
	/**
	 * @param wechatno the wechatno to set
	 */
    public void setWechatno(String wechatno){
       this.wechatno = wechatno;
    }
    
    /**
     * @return the wechatno 
     */
    public String getWechatno(){
       return this.wechatno;
    }
	
	/**
	 * @param postfrom the postfrom to set
	 */
    public void setPostfrom(Integer postfrom){
       this.postfrom = postfrom;
    }
    
    /**
     * @return the postfrom 
     */
    public Integer getPostfrom(){
       return this.postfrom;
    }
	
	/**
	 * @param status the status to set
	 */
    public void setStatus(Integer status){
       this.status = status;
    }
    
    /**
     * @return the status 
     */
    public Integer getStatus(){
       return this.status;
    }
	
	/**
	 * @param openQuote the openQuote to set
	 */
    public void setOpenQuote(Integer openQuote){
       this.openQuote = openQuote;
    }
    
    /**
     * @return the openQuote 
     */
    public Integer getOpenQuote(){
       return this.openQuote;
    }
	
	/**
	 * @param sendTime the sendTime to set
	 */
    public void setSendTime(Date sendTime){
       this.sendTime = sendTime;
    }
    
    /**
     * @return the sendTime 
     */
    public Date getSendTime(){
       return this.sendTime;
    }
	
	/**
	 * @param updateTime the updateTime to set
	 */
    public void setUpdateTime(Date updateTime){
       this.updateTime = updateTime;
    }
    
    /**
     * @return the updateTime 
     */
    public Date getUpdateTime(){
       return this.updateTime;
    }
    
	/**
	 * @param bondOrgCode the bondOrgCode to set
	 */
    public void setBondOrgCode(Integer bondOrgCode){
       this.bondOrgCode = bondOrgCode;
    }
    
    /**
     * @return the bondOrgCode 
     */
    public Integer getBondOrgCode(){
       return this.bondOrgCode;
    }
	
	/**
	 * @param qqNum the qqNum to set
	 */
    public void setQqNum(String qqNum){
       this.qqNum = qqNum;
    }
    
    /**
     * @return the qqNum 
     */
    public String getQqNum(){
       return this.qqNum;
    }
	
	/**
	 * @param lastUpdateby the lastUpdateby to set
	 */
    public void setLastUpdateby(String lastUpdateby){
       this.lastUpdateby = lastUpdateby;
    }
    
    /**
     * @return the lastUpdateby 
     */
    public String getLastUpdateby(){
       return this.lastUpdateby;
    }

	public BigDecimal getDirtyPrice() {
		return dirtyPrice;
	}

	public void setDirtyPrice(BigDecimal dirtyPrice) {
		this.dirtyPrice = dirtyPrice;
	}

	public BigDecimal getCleanPrice() {
		return cleanPrice;
	}

	public void setCleanPrice(BigDecimal cleanPrice) {
		this.cleanPrice = cleanPrice;
	}

	public BigDecimal getYtm() {
		return ytm;
	}

	public void setYtm(BigDecimal ytm) {
		this.ytm = ytm;
	}

	public Integer getBrokerType() {
		return brokerType;
	}

	public void setBrokerType(Integer brokerType) {
		this.brokerType = brokerType;
	}
    
}
