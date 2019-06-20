package com.innodealing.model.im.user;

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
@Table(name="t_sysuser_quote")
public class SysuserQuote {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
	/**
	 * 
	 */
    @Column(nullable=false, length=64)
    private String dmacct;
    
	/**
	 * 
	 */
    @Column(nullable=false, length=10)
    private Integer status;
    
	/**
	 * 
	 */
    @Column(nullable=false, length=50)
    private String editer;
    
	/**
	 * 
	 */
    @Column(nullable=false, length=22)
	@Temporal(TemporalType.TIMESTAMP)
    private Date editme;
    
	/**
	 * 
	 */
    @Column(length=255)
    private String remark;
    
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

    public void setDmacct(String dmacct){
       this.dmacct = dmacct;
    }

    public String getDmacct(){
       return this.dmacct;
    }

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getEditer() {
		return editer;
	}

	public void setEditer(String editer) {
		this.editer = editer;
	}

	public Date getEditme() {
		return editme;
	}

	public void setEditme(Date editme) {
		this.editme = editme;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
