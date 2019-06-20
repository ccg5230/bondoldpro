package com.innodealing.model.im.user;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;

@Entity
@Table(name = "t_sysuser_permission_schema")
@NamedQuery(name = "SysuserPermissionSchema.findAll", query = "SELECT t FROM SysuserPermissionSchema t")
public class SysuserPermissionSchema implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8017430591809616011L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * 
	 */
	@Column(nullable = false, length = 16)
	private String code;

	/**
	 * 
	 */
	@Column(nullable = true, length = 64)
	private String remark;

	/**
	 * 
	 */
	@Column(nullable = false, length = 10)
	private Integer status = 1;

	/**
	 * 
	 */
	@Column(nullable = false, length = 3 , name = "is_default_on")
	private Integer isDefaultOn = 1;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getIsDefaultOn() {
		return isDefaultOn;
	}

	public void setIsDefaultOn(Integer isDefaultOn) {
		this.isDefaultOn = isDefaultOn;
	}
	
	
	public enum SysuserPermissionSchemaStatusEnum{
	    ENABLE(1,"启用"),DISABLE(0, "禁用");
	    
        SysuserPermissionSchemaStatusEnum(int code, String msg){
	        this.code = code;
	        this.msg = msg;
	    }
	    public int code;
	    public String msg;
	    public int getCode(){
	        return code;
	    }
	    public String getMsg(){
	        return msg;
	    }
	    
	}
	
	
}
