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
@Table(name="t_bond_credit_rating")
public class BondCreditRating implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
	/**
	 * 信评组ID
	 */
    @Column(nullable=false, name="group_id",length=19)
    private Long groupId;
    
	/**
	 * 债券Unicode
	 */
    @Column(nullable=false, name="bond_uni_code",length=19)
    private Long bondUniCode;
    
	/**
	 * 
	 */
    @Column(name="bond_shortname",length=256)
    private String bondShortname;
    
	/**
	 * 
	 */
    @Column(name="bond_code",length=64)
    private String bondCode;
    
	/**
	 * 债券的主体ID
	 */
    @Column(nullable=false, name="issuer_id",length=19)
    private Long issuerId;
    
	/**
	 * 用户ID
	 */
    @Column(nullable=false, name="user_id",length=19)
    private Integer userId;
    
	/**
	 * 用户所在的机构ID
	 */
    @Column(nullable=false, name="org_id",length=19)
    private Integer orgId;
    
	/**
	 * 创建时间
	 */
    @Column(name="create_time",length=10)
    private Date createTime;
    
	/**
	 * 最新投资建议ID
	 */
    @Column(nullable=false, name="instinvadv_id",length=19)
    private Long instinvadvId;
    
	/**
	 * 最新内部评级ID
	 */
    @Column(nullable=false, name="instrating_id",length=19)
    private Long instratingId;
    
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
	 * @param groupId the groupId to set
	 */
    public void setGroupId(Long groupId){
       this.groupId = groupId;
    }
    
    /**
     * @return the groupId 
     */
    public Long getGroupId(){
       return this.groupId;
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
	 * @param bondShortname the bondShortname to set
	 */
    public void setBondShortname(String bondShortname){
       this.bondShortname = bondShortname;
    }
    
    /**
     * @return the bondShortname 
     */
    public String getBondShortname(){
       return this.bondShortname;
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
	 * @param issuerId the issuerId to set
	 */
    public void setIssuerId(Long issuerId){
       this.issuerId = issuerId;
    }
    
    /**
     * @return the issuerId 
     */
    public Long getIssuerId(){
       return this.issuerId;
    }
	
	/**
	 * @param userId the userId to set
	 */
    public void setUserId(Integer userId){
       this.userId = userId;
    }
    
    /**
     * @return the userId 
     */
    public Integer getUserId(){
       return this.userId;
    }
    
	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
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

	public Long getInstinvadvId() {
		return instinvadvId;
	}

	public void setInstinvadvId(Long instinvadvId) {
		this.instinvadvId = instinvadvId;
	}

	public Long getInstratingId() {
		return instratingId;
	}

	public void setInstratingId(Long instratingId) {
		this.instratingId = instratingId;
	}
}
