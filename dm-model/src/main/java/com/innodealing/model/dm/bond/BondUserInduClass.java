package com.innodealing.model.dm.bond;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_bond_user_indu_class")
public class BondUserInduClass implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="user_id")
    private Long userId;
    
    @Column(name="indu_class")
    private Integer induClass;
   
    @Column(name="create_time")
    private Date createTime;
    
    @Column(name="update_time")
    private Date updateTime;

    /**
     * @return the userId
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

   

    /**
     * @return the createTime
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime the createTime to set
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return the updateTime
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime the updateTime to set
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * @return the induClass
     */
    public Integer getInduClass() {
        return induClass;
    }

    /**
     * @param induClass the induClass to set
     */
    public void setInduClass(Integer induClass) {
        this.induClass = induClass;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "BondUserInduClass [" + (userId != null ? "userId=" + userId + ", " : "")
                + (induClass != null ? "induClass=" + induClass + ", " : "")
                + (createTime != null ? "createTime=" + createTime + ", " : "")
                + (updateTime != null ? "updateTime=" + updateTime : "") + "]";
    }

 
    
}
