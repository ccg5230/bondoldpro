package com.innodealing.datacanal.model;


/**
 * 数据中转src to dest table and colums info。
 * @author 赵正来
 */
public class ColumnItem {
	/**
	 * 列表
	 */
	private String name;

	/**
	 * 值
	 */
	private Object value;

	/**
	 * sql type
	 */
	private int sqlType;

	/**
	 * is key
	 */
	private boolean isKey;

	private boolean isNull;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public int getSqlType() {
		return sqlType;
	}

	public void setSqlType(int sqlType) {
		this.sqlType = sqlType;
	}

	public boolean isKey() {
		return isKey;
	}

	public void setKey(boolean isKey) {
		this.isKey = isKey;
	}

	public ColumnItem() {
		super();
	}

	public boolean isNull() {
		return isNull;
	}

	public void setNull(boolean isNull) {
		this.isNull = isNull;
	}

	public ColumnItem(String name, Object value, int sqlType, boolean isKey, boolean isNull) {
		super();
		this.name = name;
		this.value = value;
		this.sqlType = sqlType;
		this.isKey = isKey;
		this.isNull = isNull;
	}

	@Override
	public String toString() {
		return "ColumnItem [name=" + name + ", value=" + value + ", sqlType=" + sqlType + ", isKey=" + isKey
				+ ", isNull=" + isNull + "]";
	}
	
}
