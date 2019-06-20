package com.innodealing.model.im.user;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "t_sysuser_permission")
@NamedQuery(name = "SysuserPermission.findAll", query = "SELECT t FROM SysuserPermission t")
public class SysuserPermission implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8914814010215233031L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * 
	 */
	@Column(nullable = false, length = 64)
	private String dmacct;

	/**
	 * 
	 */
	@Column(nullable = false, length = 10, name = "code_id")
	private Long codeId;

	/**
	 * 
	 */
	@Column(nullable = false, length = 10)
	private Integer status = 1;

	/**
	 * 
	 */
	@Column(nullable = true, length = 255)
	private String remark;

	/**
	 * 
	 */
	@Column(nullable = false, length = 64)
	private String operator;

	/**
	 * 
	 */
	@Column(nullable = false, length = 19,name = "create_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;

	/**
	 * 
	 */
	@Column(nullable = false, length = 19,name="update_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateTime;

	/**
	 * 
	 */
	@Column(nullable = false, length = 3 ,name = "is_deleted")
	private Integer isDeleted = 0;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDmacct() {
		return dmacct;
	}

	public void setDmacct(String dmacct) {
		this.dmacct = dmacct;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Long getCodeId() {
		return codeId;
	}

	public void setCodeId(Long codeId) {
		this.codeId = codeId;
	}
	
	public enum StatusEnum{
        ENABLE(1,"启用"),DISABLE(0, "禁用");
        
	    StatusEnum(int code, String msg){
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
