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
@Table(name="t_bond_per_finance")
public class BondPerFinance implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
	/**
	 * 用户ID
	 */
    @Column(name="userid",length=10)
    private Integer userid;
    
	/**
	 * 财报发行人名称
	 */
    @Column(name="fin_user",length=64)
    private String finUser;
    
	/**
	 * 所属行业ID（关联t_pub_indu_code表）
	 */
    @Column(name="industry_id",length=10)
    private Integer industryId;
    
	/**
	 * 财报日期-年份
	 */
    @Column(name="fin_year",length=10)
    private Integer finYear;
    
	/**
	 * 财报日期-季度
	 */
    @Column(name="fin_quarter",length=3)
    private Integer finQuarter;
    
	/**
	 * TASK_ID
	 */
    @Column(nullable=false, name="task_id",length=19)
    private Long taskId;
    
    /**
     * COMP_ID
     */
    @Column(name = "comp_id", length=20)
    private Long compId;
    
	/**
	 * comp_cls_code GICS四级分类
	 */
    @Column(name="comp_cls_code",length=32)
    private String compClsCode;
    
	/**
	 * 评级评分
	 */
    @Column(name="rating",length=64)
    private String rating;
    
	/**
	 * 状态（默认0）。0：已上传财报，等待评分；1：评分完成
	 */
    @Column(name="status",length=3)
    private Integer status;
    
	/**
	 * 操作时间
	 */
    @Column(name="operate_time",length=19)
    private Date operateTime;
    
	/**
	 * 评分时间
	 */
    @Column(name="rate_time",length=19)
    private Date rateTime;
    
	/**
	 * 是否删除该记录，0 未删除，1 删除
	 */
    @Column(nullable=false, name="deleted",length=3)
    private Integer deleted;
    
	/**
	 * 所属行业
	 */
    @Column(name="indu_class_name_l4",length=256)
    private String induClassNameL4;
    
    
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
	 * @param userid the userid to set
	 */
    public void setUserid(Integer userid){
       this.userid = userid;
    }
    
    /**
     * @return the userid 
     */
    public Integer getUserid(){
       return this.userid;
    }
	
	/**
	 * @param finUser the finUser to set
	 */
    public void setFinUser(String finUser){
       this.finUser = finUser;
    }
    
    /**
     * @return the finUser 
     */
    public String getFinUser(){
       return this.finUser;
    }
	
	/**
	 * @param industryId the industryId to set
	 */
    public void setIndustryId(Integer industryId){
       this.industryId = industryId;
    }
    
    /**
     * @return the industryId 
     */
    public Integer getIndustryId(){
       return this.industryId;
    }
	
	/**
	 * @param finYear the finYear to set
	 */
    public void setFinYear(Integer finYear){
       this.finYear = finYear;
    }
    
    /**
     * @return the finYear 
     */
    public Integer getFinYear(){
       return this.finYear;
    }
	
	/**
	 * @param finQuarter the finQuarter to set
	 */
    public void setFinQuarter(Integer finQuarter){
       this.finQuarter = finQuarter;
    }
    
    /**
     * @return the finQuarter 
     */
    public Integer getFinQuarter(){
       return this.finQuarter;
    }
	
	/**
	 * @param taskId the taskId to set
	 */
    public void setTaskId(Long taskId){
       this.taskId = taskId;
    }
    
    /**
     * @return the taskId 
     */
    public Long getTaskId(){
       return this.taskId;
    }
    
    /**
	 * @param compId the compId to set
	 */
	public void setCompId(Long compId) {
		this.compId = compId;
	}
	
    /**
     * @return the compId 
     */
	public Long getCompId() {
		return compId;
	}

	/**
	 * @param compClsCode the compClsCode to set
	 */
    public void setCompClsCode(String compClsCode){
       this.compClsCode = compClsCode;
    }
    
    /**
     * @return the compClsCode 
     */
    public String getCompClsCode(){
       return this.compClsCode;
    }
	
	/**
	 * @param rating the rating to set
	 */
    public void setRating(String rating){
       this.rating = rating;
    }
    
    /**
     * @return the rating 
     */
    public String getRating(){
       return this.rating;
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
	 * @param operateTime the operateTime to set
	 */
    public void setOperateTime(Date operateTime){
       this.operateTime = operateTime;
    }
    
    /**
     * @return the operateTime 
     */
    public Date getOperateTime(){
       return this.operateTime;
    }
	
	/**
	 * @param rateTime the rateTime to set
	 */
    public void setRateTime(Date rateTime){
       this.rateTime = rateTime;
    }
    
    /**
     * @return the rateTime 
     */
    public Date getRateTime(){
       return this.rateTime;
    }
	
	/**
	 * @param deleted the deleted to set
	 */
    public void setDeleted(Integer deleted){
       this.deleted = deleted;
    }
    
    /**
     * @return the deleted 
     */
    public Integer getDeleted(){
       return this.deleted;
    }

	/**
	 * @return the induClassNameL4
	 */
	public String getInduClassNameL4() {
		return induClassNameL4;
	}

	/**
	 * @param induClassNameL4 the induClassNameL4 to set
	 */
	public void setInduClassNameL4(String induClassNameL4) {
		this.induClassNameL4 = induClassNameL4;
	}
    
}
