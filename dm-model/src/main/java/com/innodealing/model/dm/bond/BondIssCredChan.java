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
/**
 * 发行人信誉评级变化记录model
 * @author zhaozhenglai
 * @since 2016年7月1日上午10:02:45 
 * Copyright © 2015 DealingMatrix.cn. All Rights Reserved.
 */
@Entity
@Table(name="t_bond_iss_cred_chan")
public class BondIssCredChan implements Serializable{
	/**
     * 
     */
    private static final long serialVersionUID = -3238776200724672464L;

    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
	/**
	 * 
	 */
    @Column(length=19)
    private Long rateId;
    
	/**
	 * 
	 */
    @Column(length=19)
    private Long comUniCode;
    
	/**
	 * 
	 */
    @Column(length=10)
	@Temporal(TemporalType.DATE)
    private Date ratePublDate;
    
	/**
	 * 
	 */
    @Column(length=10)
	@Temporal(TemporalType.DATE)
    private Date rateWritDate;
    
	/**
	 * 
	 */
    @Column(length=3)
    private Integer isNewRate;
    
	/**
	 * 
	 */
    @Column(length=3)
    private Integer rateType;
    
	/**
	 * 
	 */
    @Column(length=3)
    private Integer rateCls;
    
	/**
	 * 
	 */
    @Column(length=20)
    private String issCredLevel;
    
	/**
	 * 
	 */
    @Column(length=3)
    private Integer ratePros;
    
	/**
	 * 
	 */
    @Column(length=3000)
    private String ratePoint;
    
	/**
	 * 
	 */
    @Column(length=3)
    private Integer rateChg;
    
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
	 * @param rateId the rateId to set
	 */
    public void setRateId(Long rateId){
       this.rateId = rateId;
    }
    
    /**
     * @return the rateId 
     */
    public Long getRateId(){
       return this.rateId;
    }
	
	/**
	 * @param comUniCode the comUniCode to set
	 */
    public void setComUniCode(Long comUniCode){
       this.comUniCode = comUniCode;
    }
    
    /**
     * @return the comUniCode 
     */
    public Long getComUniCode(){
       return this.comUniCode;
    }
	
	/**
	 * @param ratePublDate the ratePublDate to set
	 */
    public void setRatePublDate(Date ratePublDate){
       this.ratePublDate = ratePublDate;
    }
    
    /**
     * @return the ratePublDate 
     */
    public Date getRatePublDate(){
       return this.ratePublDate;
    }
	
	/**
	 * @param rateWritDate the rateWritDate to set
	 */
    public void setRateWritDate(Date rateWritDate){
       this.rateWritDate = rateWritDate;
    }
    
    /**
     * @return the rateWritDate 
     */
    public Date getRateWritDate(){
       return this.rateWritDate;
    }
	
	/**
	 * @param isNewRate the isNewRate to set
	 */
    public void setIsNewRate(Integer isNewRate){
       this.isNewRate = isNewRate;
    }
    
    /**
     * @return the isNewRate 
     */
    public Integer getIsNewRate(){
       return this.isNewRate;
    }
	
	/**
	 * @param rateType the rateType to set
	 */
    public void setRateType(Integer rateType){
       this.rateType = rateType;
    }
    
    /**
     * @return the rateType 
     */
    public Integer getRateType(){
       return this.rateType;
    }
	
	/**
	 * @param rateCls the rateCls to set
	 */
    public void setRateCls(Integer rateCls){
       this.rateCls = rateCls;
    }
    
    /**
     * @return the rateCls 
     */
    public Integer getRateCls(){
       return this.rateCls;
    }
	
	/**
	 * @param issCredLevel the issCredLevel to set
	 */
    public void setIssCredLevel(String issCredLevel){
       this.issCredLevel = issCredLevel;
    }
    
    /**
     * @return the issCredLevel 
     */
    public String getIssCredLevel(){
       return this.issCredLevel;
    }
	
	/**
	 * @param ratePros the ratePros to set
	 */
    public void setRatePros(Integer ratePros){
       this.ratePros = ratePros;
    }
    
    /**
     * @return the ratePros 
     */
    public Integer getRatePros(){
       return this.ratePros;
    }
	
	/**
	 * @param ratePoint the ratePoint to set
	 */
    public void setRatePoint(String ratePoint){
       this.ratePoint = ratePoint;
    }
    
    /**
     * @return the ratePoint 
     */
    public String getRatePoint(){
       return this.ratePoint;
    }
	
	/**
	 * @param rateChg the rateChg to set
	 */
    public void setRateChg(Integer rateChg){
       this.rateChg = rateChg;
    }
    
    /**
     * @return the rateChg 
     */
    public Integer getRateChg(){
       return this.rateChg;
    }
}
