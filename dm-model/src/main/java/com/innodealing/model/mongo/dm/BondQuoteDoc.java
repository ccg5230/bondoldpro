package com.innodealing.model.mongo.dm;

import java.io.Serializable;
import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;

@JsonInclude(Include.NON_NULL)
@Document(collection = "bond_quotes")
public class BondQuoteDoc implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "债券table中的ID")
	@Indexed
	@Id
	private Long quoteId;
	
	@ApiModelProperty(value = "债券的uni_code")
	@Indexed
    private Long bondUniCode;
    
	@ApiModelProperty(value = "债券代码")
	private String bondCode;

	@ApiModelProperty(value = "债券名称")
	private String bondShortName;
	
	@ApiModelProperty(value = "1收券-bid，2出券-ofr")
	@Indexed
    private Integer side;

	@ApiModelProperty(value = "ofr 卖价")
	private BigDecimal ofrPrice;

	@ApiModelProperty(value = "ofr 卖量")
	private BigDecimal ofrVol;

	@ApiModelProperty(value = "bid 买价")
	private BigDecimal bidPrice;

	@ApiModelProperty(value = "bid 买量")
	private BigDecimal bidVol;

	@ApiModelProperty(name = "是否匿名报价,0 否，1 是")
	private Integer anonymous;

	@ApiModelProperty(value = "0 QQ的个人群1 QQ的Report群2 DM数据源3 DM众包4 IDM私人群")
	private Integer source;

	@ApiModelProperty(value = "结构化原文")
	private String rawcontent;

	@ApiModelProperty(value = "机构简称")
	private String instShort;
	
	@ApiModelProperty(value = "用户ID")
	@Indexed
    private Long userId;

	@ApiModelProperty(value = "用户名")
	private String userName;
	
	@ApiModelProperty(name = "IM权限，0 否，1 是")
	private Integer imFlag;
	
	@ApiModelProperty(value = "QQ号")
	private String qqNum;
	
	@ApiModelProperty(value = "电话号码")
	private String phone;
	
	@ApiModelProperty(value = "手機号码")
	private String mobile;

	@ApiModelProperty(value = "0 QQ1微信2 PC客户端3 PC网页端4手机APP(老版本)5管理后台6 IM群7 IOS8 Android 9 Broker来源")
	private Integer postfrom;

	@ApiModelProperty(value = "群号")
	@TextIndexed
    private String troopId;
	
	@ApiModelProperty(value = "状态")
	@Indexed
    private Integer status;
	
	@ApiModelProperty(value = "发布时间")
	@Indexed
	private String sendTime;
	
	@ApiModelProperty(value = "发布时间格式化yyyy-MM-dd")
	@Indexed
	private String sendTimeFormat;
	
	@ApiModelProperty(value = "备注")
    private String remark;
	
	@ApiModelProperty(value = "收益率/净价单位，1 % ，2元")
	@Indexed
    private Integer priceUnit;
	
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

	@ApiModelProperty(value = "脏价，全价，结算价")
    private BigDecimal dirtyPrice;

	@ApiModelProperty(value = "净价，报价")
    private BigDecimal cleanPrice;
	
	@ApiModelProperty(value = "到期收益率")
    private BigDecimal ytm;
    
	public Long getQuoteId() {
		return quoteId;
	}

	public void setQuoteId(Long quoteId) {
		this.quoteId = quoteId;
	}

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
	
	public Integer getSide() {
		return side;
	}

	public void setSide(Integer side) {
		this.side = side;
	}

	public String getBondShortName() {
		return bondShortName;
	}

	public void setBondShortName(String bondShortName) {
		this.bondShortName = bondShortName;
	}

	public BigDecimal getOfrPrice() {
		return ofrPrice;
	}

	public void setOfrPrice(BigDecimal ofrPrice) {
		this.ofrPrice = ofrPrice;
	}

	public BigDecimal getOfrVol() {
		return ofrVol;
	}

	public void setOfrVol(BigDecimal ofrVol) {
		this.ofrVol = ofrVol;
	}

	public BigDecimal getBidPrice() {
		return bidPrice;
	}

	public void setBidPrice(BigDecimal bidPrice) {
		this.bidPrice = bidPrice;
	}

	public BigDecimal getBidVol() {
		return bidVol;
	}

	public void setBidVol(BigDecimal bidVol) {
		this.bidVol = bidVol;
	}

	public Integer getAnonymous() {
		return anonymous;
	}

	public void setAnonymous(Integer anonymous) {
		this.anonymous = anonymous;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public String getRawcontent() {
		return rawcontent;
	}

	public void setRawcontent(String rawcontent) {
		this.rawcontent = rawcontent;
	}

	public String getInstShort() {
		return instShort;
	}

	public void setInstShort(String instShort) {
		this.instShort = instShort;
	}
	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getImFlag() {
		return imFlag;
	}

	public void setImFlag(Integer imFlag) {
		this.imFlag = imFlag;
	}

	public String getQqNum() {
		return qqNum;
	}

	public void setQqNum(String qqNum) {
		this.qqNum = qqNum;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getPostfrom() {
		return postfrom;
	}

	public void setPostfrom(Integer postfrom) {
		this.postfrom = postfrom;
	}
	
	public String getTroopId() {
		return troopId;
	}

	public void setTroopId(String troopId) {
		this.troopId = troopId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public String getSendTimeFormat() {
		return sendTimeFormat;
	}

	public void setSendTimeFormat(String sendTimeFormat) {
		this.sendTimeFormat = sendTimeFormat;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getPriceUnit() {
		return priceUnit;
	}

	public void setPriceUnit(Integer priceUnit) {
		this.priceUnit = priceUnit;
	}

}
