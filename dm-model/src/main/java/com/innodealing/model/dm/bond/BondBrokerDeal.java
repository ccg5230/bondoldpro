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

@Entity
@Table(name="t_bond_broker_deal")
public class BondBrokerDeal implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
	/**
	 * 债券的uni code
	 */
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
	 * broker报价bid的价格
	 */
    @Column(nullable=false, name="bid_price",length=10)
    private BigDecimal bidPrice;
    
	/**
	 * broker报价bid交易量
	 */
    @Column(nullable=false, name="bid_vol",length=10)
    private BigDecimal bidVol;
    
	/**
	 * broker报价ofr的价格
	 */
    @Column(nullable=false, name="ofr_price",length=10)
    private BigDecimal ofrPrice;
    
	/**
	 * broker报价ofr交易量
	 */
    @Column(nullable=false, name="ofr_vol",length=10)
    private BigDecimal ofrVol;
    
	/**
	 * Broker成交价
	 */
    @Column(nullable=false, name="strike_price",length=10)
    private BigDecimal strikePrice;
    
	/**
	 * Broker数据的type
	 */
    @Column(name="broker_type",length=32)
    private String brokerType;
    
	/**
	 * 0 默认数据，9 QB Broker来源
	 */
    @Column(nullable=false, name="postfrom",length=3)
    private Integer postfrom;
    
	/**
	 * 数据来源方
	 */
    @Column(name="source_name",length=64)
    private String sourceName;
    
	/**
	 * Broker报价的最后更新时间
	 */
    @Column(name="send_time",length=19)
	@Temporal(TemporalType.TIMESTAMP)
    private Date sendTime;
    
	/**
	 * 创建时间
	 */
    @Column(name="create_time",length=19)
	@Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    
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
	 * @param bidPrice the bidPrice to set
	 */
    public void setBidPrice(BigDecimal bidPrice){
       this.bidPrice = bidPrice;
    }
    
    /**
     * @return the bidPrice 
     */
    public BigDecimal getBidPrice(){
       return this.bidPrice;
    }
	
	/**
	 * @param bidVol the bidVol to set
	 */
    public void setBidVol(BigDecimal bidVol){
       this.bidVol = bidVol;
    }
    
    /**
     * @return the bidVol 
     */
    public BigDecimal getBidVol(){
       return this.bidVol;
    }
	
	/**
	 * @param ofrPrice the ofrPrice to set
	 */
    public void setOfrPrice(BigDecimal ofrPrice){
       this.ofrPrice = ofrPrice;
    }
    
    /**
     * @return the ofrPrice 
     */
    public BigDecimal getOfrPrice(){
       return this.ofrPrice;
    }
	
	/**
	 * @param ofrVol the ofrVol to set
	 */
    public void setOfrVol(BigDecimal ofrVol){
       this.ofrVol = ofrVol;
    }
    
    /**
     * @return the ofrVol 
     */
    public BigDecimal getOfrVol(){
       return this.ofrVol;
    }
	
	/**
	 * @param strikePrice the strikePrice to set
	 */
    public void setStrikePrice(BigDecimal strikePrice){
       this.strikePrice = strikePrice;
    }
    
    /**
     * @return the strikePrice 
     */
    public BigDecimal getStrikePrice(){
       return this.strikePrice;
    }
	
	/**
	 * @param brokerType the brokerType to set
	 */
    public void setBrokerType(String brokerType){
       this.brokerType = brokerType;
    }
    
    /**
     * @return the brokerType 
     */
    public String getBrokerType(){
       return this.brokerType;
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
	 * @param sourceName the sourceName to set
	 */
    public void setSourceName(String sourceName){
       this.sourceName = sourceName;
    }
    
    /**
     * @return the sourceName 
     */
    public String getSourceName(){
       return this.sourceName;
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
	 * @param createTime the createTime to set
	 */
    public void setCreateTime(Date createTime){
       this.createTime = createTime;
    }
    
    /**
     * @return the createTime 
     */
    public Date getCreateTime(){
       return this.createTime;
    }
}
