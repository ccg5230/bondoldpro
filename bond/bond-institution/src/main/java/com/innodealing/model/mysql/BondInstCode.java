package com.innodealing.model.mysql;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.innodealing.util.Column;
import com.innodealing.util.ColumnIgnore;
import com.innodealing.util.Table;

import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * 
 * @author 戴永杰
 *
 * @date 2017年9月11日 下午2:15:49
 * @version V1.0
 *
 */
@Table(name="t_bond_inst_code")

public class BondInstCode extends BaseModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@ApiModelProperty(value = "唯一标识")
	private int id;


	@ApiModelProperty(value = "名称")
	private String name;
	
	@ApiModelProperty(value = "排序")
	private int sort;

	/**
	 * 类型 1 内部评级 2 投资建议 
	 */
	@ApiModelProperty(hidden = true)
	@JsonIgnore
	private int type;
	@ApiModelProperty(hidden = true)
	@JsonIgnore
	@Column(name="org_id")
	private int orgId;
	@ApiModelProperty(hidden = true)
	@JsonIgnore
	private int status;
	
	@ApiModelProperty(value = "使用情况 0[未使用] 其他表示在使用")
	@ColumnIgnore
	private int usage;
	
	
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	/**
	 * 版本号
	 */
	@ApiModelProperty(value = "版本号")
	private int version;
	
	public int getUsage() {
		return usage;
	}

	public void setUsage(int usage) {
		this.usage = usage;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getOrgId() {
		return orgId;
	}

	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}

	public BondInstCode() {
	}

	public BondInstCode(String name, int type, int orgId, int status) {
		this.name = name;
		this.type = type;
		this.orgId = orgId;
		this.status = status;
	}
}
