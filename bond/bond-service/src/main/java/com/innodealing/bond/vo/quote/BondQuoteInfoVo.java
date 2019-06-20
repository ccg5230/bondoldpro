package com.innodealing.bond.vo.quote;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author stephen.ma
 * @date 2016年9月19日
 * @clasename BondQuoteInfoVo.java
 * @decription TODO
 */
public class BondQuoteInfoVo {
	
    private static final Logger LOG = LoggerFactory.getLogger(BondQuoteInfoVo.class);
    
	@ApiModelProperty(value = "债券table中的ID")
	private Long quoteId;
	
	@ApiModelProperty(value = "债券ID")
	private Long bondId;
	
	@ApiModelProperty(value = "债券代码")
	private String code;

	@ApiModelProperty(value = "债券名称")
	private String name;
	
	@ApiModelProperty(value = "1收券-bid，2出券-ofr")
    private Integer side;

	@ApiModelProperty(value = "ofr 卖价")
	private BigDecimal ofrPrice;

	@ApiModelProperty(value = "ofr 卖量")
	private BigDecimal ofrVol;

	@ApiModelProperty(value = "bid 买价")
	private BigDecimal bidPrice;

	@ApiModelProperty(value = "bid 买量")
	private BigDecimal bidVol;

	@ApiModelProperty(value = "0 QQ的个人群1 QQ的Report群2 DM数据源3 DM众包4 IDM私人群")
	private Integer source;

	@ApiModelProperty(value = "0 QQ1微信2 PC客户端3 PC网页端4手机APP(老版本)5管理后台6 IM群7 IOS8 Android 9 Broker来源")
	private Integer postfrom;

	@ApiModelProperty(value = "群号")
	@TextIndexed
    private String troopId;
	
	@ApiModelProperty(value = "状态")
	@Indexed
    private Integer status;
	
	@ApiModelProperty(value = "发布时间, yyy/MM/dd HH:mm:ss")
	private String sendDatetime;
	
	@ApiModelProperty(value = "备注")
    private String remark;
	
    @ApiModelProperty(value = "期限")
    private String tenor;
    
    @ApiModelProperty(value = "主体量化风险等级")
    private String pd;
    
    @ApiModelProperty(value = "主体量化风险等级变化")
    private Long pdDiff;
    
    @JsonFormat(pattern="yyyy/MM/dd")
    @ApiModelProperty(value = "pd更新时间")
    private String pdTime;
    
    @ApiModelProperty(value = "正面舆情数")
    private Integer poPositive;
    
    @ApiModelProperty(value = "中性舆情数")
    private Integer poNeutral;
    
    @ApiModelProperty(value = "负面舆情数")
    private Integer poNegtive;
    
    @ApiModelProperty(value = "最近一个月舆情总数")
    private Integer poCount;

    @ApiModelProperty(value = "历史最高风险等级")
    private String worstPd;
    
    @ApiModelProperty(value = "历史最高风险值")
    private Long worstPdNum;
    
    @ApiModelProperty(value = "历史最高风险警告标志, 量化风险等级<=CCC")
    private Boolean worstRiskWarning;
    
    @ApiModelProperty(value = "历史最高风险等级-更新时间")
    private String worstPdTime;
    
    @ApiModelProperty(value = "实际违约债券名")
    private String defaultBondName;
    
    @ApiModelProperty(value = "实际违约事件描述")
    private String defaultEvent;
    
    @ApiModelProperty(value = "实际违约事件日期")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date defaultDate;
    
	@ApiModelProperty(value = "风险警告标志, 量化风险等级<=CCC")
	private Boolean riskWarning;
	
	public Long getQuoteId() {
		return quoteId;
	}

	public void setQuoteId(Long quoteId) {
		this.quoteId = quoteId;
	}

	public Long getBondId() {
		return bondId;
	}

	public void setBondId(Long bondId) {
		this.bondId = bondId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String bondCode) {
		this.code = bondCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String bondShortName) {
		this.name = bondShortName;
	}

	public Integer getSide() {
		return side;
	}

	public void setSide(Integer side) {
		this.side = side;
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

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
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

	public String getSendDatetime() {
		return sendDatetime;
	}

	public void setSendDatetime(String sendDatetime) {
	    SimpleDateFormat inFormat = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
	    SimpleDateFormat outFormat = new SimpleDateFormat("yyy/MM/dd HH:mm:ss");
	    try {
	        this.sendDatetime = outFormat.format(inFormat.parse(sendDatetime));
	    } catch (ParseException e) {
	        e.printStackTrace();
	        LOG.error("failed to convert sendDateTime:" + sendDatetime, e );
	    }
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public Long getPdDiff() {
		return pdDiff;
	}

	public void setPdDiff(Long pdDiff) {
		this.pdDiff = pdDiff;
	}

	public Integer getPoPositive() {
		return poPositive;
	}

	public void setPoPositive(Integer poPositive) {
		this.poPositive = poPositive;
	}

	public Integer getPoNeutral() {
		return poNeutral;
	}

	public void setPoNeutral(Integer poNeutral) {
		this.poNeutral = poNeutral;
	}

	public Integer getPoNegtive() {
		return poNegtive;
	}

	public void setPoNegtive(Integer poNegtive) {
		this.poNegtive = poNegtive;
	}

	/**
	 * @return the pdTime
	 */
	public String getPdTime() {
		return pdTime;
	}

	/**
	 * @param pdTime the pdTime to set
	 */
	public void setPdTime(String pdTime) {
		this.pdTime = pdTime;
	}

	/**
	 * @return the worstPd
	 */
	public String getWorstPd() {
		return worstPd;
	}

	/**
	 * @param worstPd the worstPd to set
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
	 * @param worstPdNum the worstPdNum to set
	 */
	public void setWorstPdNum(Long worstPdNum) {
		this.worstPdNum = worstPdNum;
	}

	/**
	 * @return the worstPdTime
	 */
	public String getWorstPdTime() {
		return worstPdTime;
	}

	/**
	 * @param worstPdTime the worstPdTime to set
	 */
	public void setWorstPdTime(String worstPdTime) {
		this.worstPdTime = worstPdTime;
	}

	/**
	 * @return the defaultBondName
	 */
	public String getDefaultBondName() {
		return defaultBondName;
	}

	/**
	 * @param defaultBondName the defaultBondName to set
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
	 * @param defaultEvent the defaultEvent to set
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
	 * @param defaultDate the defaultDate to set
	 */
	public void setDefaultDate(Date defaultDate) {
		this.defaultDate = defaultDate;
	}

	/**
	 * @return the riskWarning
	 */
	public Boolean getRiskWarning() {
		return riskWarning;
	}

	/**
	 * @param riskWarning the riskWarning to set
	 */
	public void setRiskWarning(Boolean riskWarning) {
		this.riskWarning = riskWarning;
	}

	public Integer getPoCount() {
		return poCount;
	}

	public void setPoCount(Integer poCount) {
		this.poCount = poCount;
	}

    public Boolean getWorstRiskWarning() {
        return worstRiskWarning;
    }

    public void setWorstRiskWarning(Boolean worstRiskWarning) {
        this.worstRiskWarning = worstRiskWarning;
    }
	
    public static void main(String[] args) {
        BondQuoteInfoVo qv = new BondQuoteInfoVo();
        qv.setSendDatetime("2016-11-12 08:00:54");
        String result = qv.getSendDatetime();
        System.out.println(result);
    }

}
