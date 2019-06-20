package com.innodealing.datacanal.model;

import com.innodealing.datacanal.constant.ConstantCalnal;

/**
 * 
 * @author 赵正来
 * 
 */
public class BondDataTableMap {

	private Long id;
	
	/**
	 * 原表名称
	 */
	private String srcTableName;
	
	/**
	 * 原列名称
	 */
	private String srcColumnName;
	
	/**
	 * 目标表名称
	 */
	private String destTableName;
	
	/**
	 * 目标列名称
	 */
	private String destColumnName;
	
	/**
	 * 是否是唯一索引或者唯一索引的一部分
	 */
	private Byte isUnique = ConstantCalnal.FALSE;
	
	/**
	 * 是否是关键的列
	 */
	private Byte isKeyColumn = ConstantCalnal.FALSE;
	
	/**
	 * 是否被启用
	 */
	private Byte status = ConstantCalnal.ENABLE;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSrcTableName() {
		return srcTableName;
	}

	public void setSrcTableName(String srcTableName) {
		this.srcTableName = srcTableName;
	}

	public String getSrcColumnName() {
		return srcColumnName;
	}

	public void setSrcColumnName(String srcColumnName) {
		this.srcColumnName = srcColumnName;
	}

	public String getDestTableName() {
		return destTableName;
	}

	public void setDestTableName(String destTableName) {
		this.destTableName = destTableName;
	}

	public String getDestColumnName() {
		return destColumnName;
	}

	public void setDestColumnName(String destColumnName) {
		this.destColumnName = destColumnName;
	}

	public Byte getIsUnique() {
		return isUnique;
	}

	public void setIsUnique(Byte isUnique) {
		this.isUnique = isUnique;
	}

	public Byte getIsKeyColumn() {
		return isKeyColumn;
	}

	public void setIsKeyColumn(Byte isKeyColumn) {
		this.isKeyColumn = isKeyColumn;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "BondDataTableMap [id=" + id + ", srcTableName=" + srcTableName + ", srcColumnName=" + srcColumnName
				+ ", destTableName=" + destTableName + ", destColumnName=" + destColumnName + ", isUnique=" + isUnique
				+ ", isKeyColumn=" + isKeyColumn + ", status=" + status + "]";
	}
	
	
	
}
