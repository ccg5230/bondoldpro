package com.innodealing.model.mysql;

import com.innodealing.util.Column;
import com.innodealing.util.Table;

import io.swagger.annotations.ApiModelProperty;

@Table(name="t_bond_inst_system")
public class BondInstSystem extends BaseModel{

	@ApiModelProperty(value = "唯一标识")
	private int id;
	@ApiModelProperty(value = "1 机构数据")
	private int type;
	@ApiModelProperty(hidden = true)
	@Column(name="role_id")
	private int roleId;
	@ApiModelProperty(value = "状态 0无效，1有效 ")
	private int status;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
}
