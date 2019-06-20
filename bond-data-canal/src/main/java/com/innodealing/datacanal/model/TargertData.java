package com.innodealing.datacanal.model;

import java.util.LinkedHashMap;
/**
 * 数据中转src to dest table and colums info。
 * @author 赵正来
 *
 */
public class TargertData {
	/**
	 * 表名
	 */
	private String tableName;

	/**
	 * 列信息
	 */
	private LinkedHashMap<String, ColumnItem> colums;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public LinkedHashMap<String, ColumnItem> getColums() {
		return colums;
	}

	public void setColums(LinkedHashMap<String, ColumnItem> colums) {
		this.colums = colums;
	}

	@Override
	public String toString() {
		return "TargertData [tableName=" + tableName + ", colums=" + colums + "]";
	}

}
