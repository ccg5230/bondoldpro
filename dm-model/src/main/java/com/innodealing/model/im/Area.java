package com.innodealing.model.im;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_area")
public class Area implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2445909831400487608L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name="name",length=50)
	private String name;
	
	@Column(name="sname",length=50)
	private String sname;
	
	@Column(name="code",length=50)
	private String code;
	
	@Column(name="parentcode",length=50)
	private String parentcode;
	
	@Column(name="parentid",length=10)
	private Integer parentid;
	
	@Column(name="status",length=10)
	private Integer status;
	
	@Column(name="region",length=10)
	private String region;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSname() {
		return sname;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getParentcode() {
		return parentcode;
	}
	public void setParentcode(String parentcode) {
		this.parentcode = parentcode;
	}
	public Integer getParentid() {
		return parentid;
	}
	public void setParentid(Integer parentid) {
		this.parentid = parentid;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
    public String getRegion() {
        return region;
    }
    public void setRegion(String region) {
        this.region = region;
    }

}
