package com.innodealing.model.json;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class NdBondJson implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/**
	 * 操作动作
	 */
	private String actionType;
	/**
	 * 
	 */
	/**
	 * 报价ID字符串，兼容老系统
	 */
    private String quoteId;
    
	/**
	 * 
	 */
    private String msgId;
    
	/**
	 * 债券类型，兼容老系统subtype
	 */
    private Integer subType;
    
	/**
	 * 债券uni_code
	 */
    private Long bondUniCode;
    
	/**
	 * 债券代码
	 */
    private String bondCode;
    
	/**
	 * 债券名称,券种
	 */
    private String bondShortName;
    
	/**
	 * 1收券-bid，2出券-ofr，3发行
	 */
    private Integer side;
    
	/**
	 * 
	 */
    private BigDecimal bondPrice;
    
	/**
	 * 
	 */
    private BigDecimal bondVol;
    
	/**
	 * 收益率/净价单位，1 % ，2元
	 */
    private Integer priceUnit;
    
	/**
	 * 剩余期限，单位天
	 */
    private Integer tenor;
    
	/**
	 * 是否匿名报价,0 否，1 是
	 */
    private Integer anonymous;
    
	/**
	 * 是否隐藏利率，0 否，1 是
	 */
    private Integer isHideRate;
    
	/**
	 * 报价中的批准的属性
	 */
    private Integer approval;
    
	/**
	 * 0 QQ的个人群1 QQ的Report群2 DM数据源3 DM众包4 IDM私人群
	 */
    private Integer source;
    
	/**
	 * 备注
	 */
    private String remark;
    
	/**
	 * QQ和IDM的原文
	 */
    private String naturalmsg;
    
	/**
	 * 结构化原文
	 */
    private String rawcontent;
    
	/**
	 * 群号
	 */
    private String troopId;
    
	/**
	 * 群名称
	 */
    private String troopName;
    
	/**
	 * 机构类型
	 */
    private Integer instType;
    
	/**
	 * 机构简称
	 */
    private String instShort;
    
	/**
	 * 机构ID
	 */
    private Integer instId;
    
	/**
	 * 账号ID
	 */
    private Long userId;
    
	/**
	 * 用户名
	 */
    private String userName;
    
	/**
	 * 用户微信号
	 */
    private String wechatno;
    
	/**
	 * 0 QQ1微信2 PC客户端3 PC网页端4手机APP(老版本)5管理后台6 IM群7 IOS8 Android

	 */
    private Integer postfrom;
    
	/**
	 * 状态，1 正常 2 已成交 99 已取消
	 */
    private Integer status;
    
	/**
	 * 需求是否同时发送报价，0 需求，1 报价
	 */
    private Integer openQuote;
    
	/**
	 * 发布时间
	 */
    private Date sendTime;
    
	/**
	 * 更新时间
	 */
    private Date updateTime;
    
    /**
     * 上报JID
     */
    private String jid;
    
    /**
     * imFlag
     */
	private Integer imFlag;
	
    /**
     * phone
     */
	private String phone;
	
    /**
     * mobile
     */
    private String mobile;
	
    /**
     * qqNum
     */
	private String qqNum;
	
    /**
     * lastUpdateby
     */
    private String lastUpdateby;

    
	/**
	 * 
	 * 操作动作
	 */
	public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
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

	public String getJid() {
		return jid;
	}

	public void setJid(String jid) {
		this.jid = jid;
	}

	public Integer getImFlag() {
		return imFlag;
	}

	public void setImFlag(Integer imFlag) {
		this.imFlag = imFlag;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getQqNum() {
		return qqNum;
	}

	public void setQqNum(String qqNum) {
		this.qqNum = qqNum;
	}

	public String getLastUpdateby() {
		return lastUpdateby;
	}

	public void setLastUpdateby(String lastUpdateby) {
		this.lastUpdateby = lastUpdateby;
	}
}
