package com.innodealing.model.dm.bond.gate;

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
@Table(name = "rule_row_key")
public class BondRuleRowKey implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
	/**
	 * 表名称
	 */
    @Column(name="TBL_NM", length=255)
    private String TBL_NM;
    
	/**
	 * 业务主键
	 */
    @Column(name="PRI_KEY", length=255)
    private String PRI_KEY;
    
	/**
	 * 对比字段
	 */
    @Column(name="COL_NM", length=65535)
    private String COL_NM;
    
	/**
	 * 
	 */
    @Column(name="last_update_time", length=19)
	@Temporal(TemporalType.TIMESTAMP)
    private Date last_update_time;
    
	/**
	 * 
	 */
    @Column(length=0)
    private Boolean visible;
    
	/**
	 * 
	 */
    @Column(name="insert_user", length=10)
    private Integer insert_user;
    
	/**
	 * 
	 */
    @Column(name="last_update_user", length=10)
    private Integer last_update_user;
    
	/**
	 * 
	 */
    @Column(name="create_time", length=19)
	@Temporal(TemporalType.TIMESTAMP)
    private Date create_time;
    
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getTBL_NM() {
		return TBL_NM;
	}

	public void setTBL_NM(String tBL_NM) {
		TBL_NM = tBL_NM;
	}

	public String getPRI_KEY() {
		return PRI_KEY;
	}

	public void setPRI_KEY(String pRI_KEY) {
		PRI_KEY = pRI_KEY;
	}

	public String getCOL_NM() {
		return COL_NM;
	}

	public void setCOL_NM(String cOL_NM) {
		COL_NM = cOL_NM;
	}

	public Date getLast_update_time() {
		return last_update_time;
	}

	public void setLast_update_time(Date last_update_time) {
		this.last_update_time = last_update_time;
	}

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	public Integer getInsert_user() {
		return insert_user;
	}

	public void setInsert_user(Integer insert_user) {
		this.insert_user = insert_user;
	}

	public Integer getLast_update_user() {
		return last_update_user;
	}

	public void setLast_update_user(Integer last_update_user) {
		this.last_update_user = last_update_user;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	
    
}
