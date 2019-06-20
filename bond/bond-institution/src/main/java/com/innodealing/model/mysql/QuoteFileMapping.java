package com.innodealing.model.mysql;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_quote_filemapping")
public class QuoteFileMapping implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
	/**
	 * 
	 */
    @Column(name="file_name",length=256)
    private String fileName;
    
	/**
	 * 
	 */
    @Column(name="file_path",length=256)
    private String filePath;
    
	/**
	 * 
	 */
    @Column(name="quoteid",length=19)
    private Long quoteid;
    
	/**
	 * 
	 */
    @Column(name="status",length=10)
    private Integer status;
    
	/**
	 * 
	 */
    @Column(name="update_time",length=19)
    private Date updateTime;
    
	/**
	 * 
	 */
    @Column(name="oss_key",length=50)
    private String ossKey;
    
	/**
	 * 
	 */
    @Column(name="finance_id",length=19)
    private Long financeId;
    
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
	 * @param fileName the fileName to set
	 */
    public void setFileName(String fileName){
       this.fileName = fileName;
    }
    
    /**
     * @return the fileName 
     */
    public String getFileName(){
       return this.fileName;
    }
	
	/**
	 * @param filePath the filePath to set
	 */
    public void setFilePath(String filePath){
       this.filePath = filePath;
    }
    
    /**
     * @return the filePath 
     */
    public String getFilePath(){
       return this.filePath;
    }
	
	/**
	 * @param quoteid the quoteid to set
	 */
    public void setQuoteid(Long quoteid){
       this.quoteid = quoteid;
    }
    
    /**
     * @return the quoteid 
     */
    public Long getQuoteid(){
       return this.quoteid;
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
	 * @param ossKey the ossKey to set
	 */
    public void setOssKey(String ossKey){
       this.ossKey = ossKey;
    }
    
    /**
     * @return the ossKey 
     */
    public String getOssKey(){
       return this.ossKey;
    }
	
	/**
	 * @param financeId the financeId to set
	 */
    public void setFinanceId(Long financeId){
       this.financeId = financeId;
    }
    
    /**
     * @return the financeId 
     */
    public Long getFinanceId(){
       return this.financeId;
    }
}
