package com.innodealing.model.dm.bond;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name ="dm_user_role")
public class DmUserRole implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name ="id")
	private Long id;
	
	@Column(name ="user_id")
	private Long userId; 
	
	@Column(name ="role_id")
	private Long roleId;
	
	@Column(name ="create_time")
	private Date createTime;
	
	@Column(name ="effective_time")
	private Date effectiveTime;
	
	@Column(name ="expire_time")
	private Date expireTime;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getEffectiveTime() {
		return effectiveTime;
	}

	public void setEffectiveTime(Date effectiveTime) {
		this.effectiveTime = effectiveTime;
	}

	public Date getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}
	
	
	
}
