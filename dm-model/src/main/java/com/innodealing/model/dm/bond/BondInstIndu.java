package com.innodealing.model.dm.bond;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 机构行业
 * 
 * @author liuqi
 *
 */
@Table(name="t_bond_inst_indu")
public class BondInstIndu implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "indu_uni_code")
	private Long induUniCode;

	@Column(name = "indu_class_name")
	private String induClassName;

	@Column(name = "inst_id")
	private Integer instId;

	@Column(name = "indu_level")
	private Integer induLevel;

	@Column(name = "fat_uni_code")
	private Integer fatUniCode;

	@Column(name = "indu_class_desc")
	private String induClassDesc;

	@Column(name = "is_valid")
	private Integer isValid;

	//原节点父类
	private Long oldFatUniCode;

	//原节点顺序
	private Long oldInduLevel;
	
	//原节点ID
	private Long oldInduUniCode;

	private Long count;

	public Long getInduUniCode() {
		return induUniCode;
	}

	public void setInduUniCode(Long induUniCode) {
		this.induUniCode = induUniCode;
	}

	public String getInduClassName() {
		return induClassName;
	}

	public void setInduClassName(String induClassName) {
		this.induClassName = induClassName;
	}

	public Integer getInstId() {
		return instId;
	}

	public void setInstId(Integer instId) {
		this.instId = instId;
	}

	public Integer getInduLevel() {
		return induLevel;
	}

	public void setInduLevel(Integer induLevel) {
		this.induLevel = induLevel;
	}

	public Integer getFatUniCode() {
		return fatUniCode;
	}

	public void setFatUniCode(Integer fatUniCode) {
		this.fatUniCode = fatUniCode;
	}

	public String getInduClassDesc() {
		return induClassDesc;
	}

	public void setInduClassDesc(String induClassDesc) {
		this.induClassDesc = induClassDesc;
	}

	public Integer getIsValid() {
		return isValid;
	}

	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public Long getOldFatUniCode() {
		return oldFatUniCode;
	}

	public void setOldFatUniCode(Long oldFatUniCode) {
		this.oldFatUniCode = oldFatUniCode;
	}

	public Long getOldInduLevel() {
		return oldInduLevel;
	}

	public void setOldInduLevel(Long oldInduLevel) {
		this.oldInduLevel = oldInduLevel;
	}

	public Long getOldInduUniCode() {
		return oldInduUniCode;
	}

	public void setOldInduUniCode(Long oldInduUniCode) {
		this.oldInduUniCode = oldInduUniCode;
	}

	

}
