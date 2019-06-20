package com.innodealing.model.dm.bond;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name="t_bond_implied_rating")
public class BondImpliedRating implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="id")
    private Long id;
    
    @Column(name="bond_id")
    private String bondId; //其实是code
 
    @Column(name="bond_code")
    private Long bondCode; //其实是id。。。
    
    @Column(name="bond_name")
    private String bondName;
    
    @Column(name="issuer_id")
    private Long issuerId; 
    
    @Column(name="issuer_name")
    private String issuerName;
    
    @Column(name="implied_rating")
    private String impliedRating;
    
    @Column(name="data_date")
    private Date dataDate;
    
    @Column(name="create_by")
    private Long createBy;
    
    @Column(name="create_time")
    private Date createTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBondId() {
		return bondId;
	}

	public void setBondId(String bondId) {
		this.bondId = bondId;
	}

	public Long getBondCode() {
		return bondCode;
	}

	public void setBondCode(Long bondCode) {
		this.bondCode = bondCode;
	}

	public String getBondName() {
		return bondName;
	}

	public void setBondName(String bondName) {
		this.bondName = bondName;
	}

	public Long getIssuerId() {
		return issuerId;
	}

	public void setIssuerId(Long issuerId) {
		this.issuerId = issuerId;
	}

	public String getIssuerName() {
		return issuerName;
	}

	public void setIssuerName(String issuerName) {
		this.issuerName = issuerName;
	}

	public String getImpliedRating() {
		return impliedRating;
	}

	public void setImpliedRating(String impliedRating) {
		this.impliedRating = impliedRating;
	}

	public Date getDataDate() {
		return dataDate;
	}

	public void setDataDate(Date dataDate) {
		this.dataDate = dataDate;
	}

	public Long getCreateBy() {
		return createBy;
	}

	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
    
    
}
