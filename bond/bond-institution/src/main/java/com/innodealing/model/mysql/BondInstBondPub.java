package com.innodealing.model.mysql;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.innodealing.util.Column;
import com.innodealing.util.Table;

/**
 * 
 * 债券和发行人关系（天风自定义）
 * @author 戴永杰
 *
 * @date 2017年9月15日 上午10:56:58 
 * @version V1.0   
 *
 */
@Table(name="t_bond_inst_bond_pub")
public class BondInstBondPub extends BaseModel {

	private int id;
	
	@Column(name="com_uni_code")
	private int comUniCode;
	
	@Column(name="bond_uni_code")
	private int bondUniCode;
	
	
	@JsonIgnore
	@Column(name="org_id")
	private int orgId;
	
	public int getOrgId() {
		return orgId;
	}

	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}

	private String remark;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getComUniCode() {
		return comUniCode;
	}

	public void setComUniCode(int comUniCode) {
		this.comUniCode = comUniCode;
	}

	public int getBondUniCode() {
		return bondUniCode;
	}

	public void setBondUniCode(int bondUniCode) {
		this.bondUniCode = bondUniCode;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}


	
}
