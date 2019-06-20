package com.innodealing.model.dm.bond;

import java.io.Serializable;
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
@Table(name="t_bond_trade_event")
public class BondTradeEvent implements Serializable{
	/**
     * 
     */
    private static final long serialVersionUID = 6207699528915793167L;

    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
	/**
	 * 
	 */
    @Column(length=10)
	@Temporal(TemporalType.DATE)
    private Date eventTime;
    
	/**
	 * 
	 */
    @Column(length=20)
    private String bondCode;
    
	/**
	 * 
	 */
    @Column(length=10)
    private Integer bondUniCode;
    
	/**
	 * 
	 */
    @Column(length=200)
    private String bondShortName;
    
	/**
	 * 
	 */
    @Column(length=3)
    private Integer eventCode;
    
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
	 * @param eventTime the eventTime to set
	 */
    public void setEventTime(Date eventTime){
       this.eventTime = eventTime;
    }
    
    /**
     * @return the eventTime 
     */
    public Date getEventTime(){
       return this.eventTime;
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
	 * @param bondUniCode the bondUniCode to set
	 */
    public void setBondUniCode(Integer bondUniCode){
       this.bondUniCode = bondUniCode;
    }
    
    /**
     * @return the bondUniCode 
     */
    public Integer getBondUniCode(){
       return this.bondUniCode;
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
	 * @param eventCode the eventCode to set
	 */
    public void setEventCode(Integer eventCode){
       this.eventCode = eventCode;
    }
    
    /**
     * @return the eventCode 
     */
    public Integer getEventCode(){
       return this.eventCode;
    }
}
