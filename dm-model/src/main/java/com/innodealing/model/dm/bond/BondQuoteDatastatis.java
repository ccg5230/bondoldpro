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
@Table(name="t_bond_quote_datastatis")
public class BondQuoteDatastatis implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
	/**
	 * 计数
	 */
    @Column(nullable=false, name="count",length=10)
    private Integer count;
    
	/**
	 * 最后的报价时间
	 */
    @Column(name="quote_date",length=19)
	@Temporal(TemporalType.TIMESTAMP)
    private Date quoteDate;
    
	/**
	 * 0 QQ1微信2 PC客户端3 PC网页端4手机APP(老版本)5管理后台6 IM群 7 Android 8 IOS 9 Broker来源
	 */
    @Column(name="postfrom",length=3)
    private Integer postfrom;
    
	/**
	 * 
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
	 * @param count the count to set
	 */
    public void setCount(Integer count){
       this.count = count;
    }
    
    /**
     * @return the count 
     */
    public Integer getCount(){
       return this.count;
    }
	
	/**
	 * @param quoteDate the quoteDate to set
	 */
    public void setQuoteDate(Date quoteDate){
       this.quoteDate = quoteDate;
    }
    
    /**
     * @return the quoteDate 
     */
    public Date getQuoteDate(){
       return this.quoteDate;
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

	public BondQuoteDatastatis(Integer count, Date quoteDate, Integer postfrom, Date createTime) {
		this.count = count;
		this.quoteDate = quoteDate;
		this.postfrom = postfrom;
		this.createTime = createTime;
	}
    
}
