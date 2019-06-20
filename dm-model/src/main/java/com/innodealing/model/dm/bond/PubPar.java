package com.innodealing.model.dm.bond;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
* @ClassName: PubPar
* @author lihao
* @date 2016年9月8日 下午3:44:18
* @Description: 曲线名称对应表
 */
@Entity
@Table(name="t_pub_par")
public class PubPar implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name="ISVALID")
	private Integer isvalid;
	
	@Column(name="CREATETIME")
	private Date createtime;
	
	@Column(name="UPDATETIME")
	private Date updatetime;
	
	@Column(name="PAR_SYS_CODE")
	private Integer parsyscode;
	
	@Column(name="PAR_SYS_NAME")
	private String parsysname;
	
	@Column(name="PAR_CODE")
	private Integer parcode;//对应t_bond_yield_currve |curve_code
	
	@Column(name="PAR_NAME")
	private String parname;
	
	@Column(name="PAR_ENG_NAME")
	private String parengname;
	
	@Column(name="PAR_UNIT")
	private String parunit;
	
	@Column(name="Code")
	private String code;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getIsvalid() {
		return isvalid;
	}

	public void setIsvalid(Integer isvalid) {
		this.isvalid = isvalid;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public Integer getParsyscode() {
		return parsyscode;
	}

	public void setParsyscode(Integer parsyscode) {
		this.parsyscode = parsyscode;
	}

	public String getParsysname() {
		return parsysname;
	}

	public void setParsysname(String parsysname) {
		this.parsysname = parsysname;
	}

	public Integer getParcode() {
		return parcode;
	}

	public void setParcode(Integer parcode) {
		this.parcode = parcode;
	}

	public String getParname() {
		return parname;
	}

	public void setParname(String parname) {
		this.parname = parname;
	}

	public String getParengname() {
		return parengname;
	}

	public void setParengname(String parengname) {
		this.parengname = parengname;
	}

	public String getParunit() {
		return parunit;
	}

	public void setParunit(String parunit) {
		this.parunit = parunit;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
