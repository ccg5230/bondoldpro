package com.innodealing.model.dm.bond;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_bond_credit_rating_group")
public class BondCreditRatingGroup implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
	/**
	 * 信评组名称
	 */
    @Column(name="group_name",length=256)
    private String groupName;
    
	/**
	 * 信评组类型, 默认0-全部，默认1-禁投组，9-信评分组
	 */
    @Column(nullable=false, name="group_type",length=3)
    private Integer groupType;
    
	/**
	 * 创建人
	 */
    @Column(nullable=false, name="created_by",length=19)
    private Integer createdBy;
    
	/**
	 * 用户所在的机构ID
	 */
    @Column(nullable=false, name="org_id",length=19)
    private Integer orgId;
    
	/**
	 * 更新时间
	 */
    @Column(name="update_time",length=10)
    private Date updateTime;
    
	/**
	 * 创建时间
	 */
    @Column(name="create_time",length=10)
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
	 * @param groupName the groupName to set
	 */
    public void setGroupName(String groupName){
       this.groupName = groupName;
    }
    
    /**
     * @return the groupName 
     */
    public String getGroupName(){
       return this.groupName;
    }
	
	/**
	 * @param groupType the groupType to set
	 */
    public void setGroupType(Integer groupType){
       this.groupType = groupType;
    }
    
    /**
     * @return the groupType 
     */
    public Integer getGroupType(){
       return this.groupType;
    }
	
	/**
	 * @param createdBy the createdBy to set
	 */
    public void setCreatedBy(Integer createdBy){
       this.createdBy = createdBy;
    }
    
    /**
     * @return the createdBy 
     */
    public Integer getCreatedBy(){
       return this.createdBy;
    }
	
	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
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

	public BondCreditRatingGroup() {
	}
    
	public BondCreditRatingGroup(Long id, String groupName, Integer groupType, Integer createdBy, Date updateTime,
			Date createTime) {
		this.id = id;
		this.groupName = groupName;
		this.groupType = groupType;
		this.createdBy = createdBy;
		this.updateTime = updateTime;
		this.createTime = createTime;
	}
	
}


