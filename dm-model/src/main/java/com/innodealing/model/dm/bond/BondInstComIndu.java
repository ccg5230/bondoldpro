package com.innodealing.model.dm.bond;

import java.io.Serializable;

import javax.persistence.Column;

import javax.persistence.Table;

/**
 * 机构的行业
 * @author liuqi
 *
 */
@Table(name="t_bond_inst_com_indu")
public class BondInstComIndu implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Column(name = "com_uni_code")
	private Long comUniCode;
	
	@Column(name = "com_chi_name")
	private String comChiName;
	
	@Column(name = "indu_uni_code")
	private Long induUniCode;
	
	@Column(name = "indu_uni_name")
	private String induUniName;
	
	@Column(name = "inst_id")
	private Integer instId;

	public Long getComUniCode() {
		return comUniCode;
	}

	public void setComUniCode(Long comUniCode) {
		this.comUniCode = comUniCode;
	}

	public String getComChiName() {
		return comChiName;
	}

	public void setComChiName(String comChiName) {
		this.comChiName = comChiName;
	}

	public Long getInduUniCode() {
		return induUniCode;
	}

	public void setInduUniCode(Long induUniCode) {
		this.induUniCode = induUniCode;
	}

	public String getInduUniName() {
		return induUniName;
	}

	public void setInduUniName(String induUniName) {
		this.induUniName = induUniName;
	}

	public Integer getInstId() {
		return instId;
	}

	public void setInstId(Integer instId) {
		this.instId = instId;
	}
	
	

}
