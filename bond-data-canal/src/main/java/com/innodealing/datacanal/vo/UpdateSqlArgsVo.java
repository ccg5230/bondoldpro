package com.innodealing.datacanal.vo;

import java.util.Arrays;

/**
 * 
 * @author 赵正来
 *
 */
public class UpdateSqlArgsVo {

	/**
	 * sql
	 */
	private String sql;
	
	/**
	 * 参数
	 */
	private Object[] args;

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public Object[] getArgs() {
		return args;
	}

	public void setArgs(Object[] args) {
		this.args = args;
	}

	@Override
	public String toString() {
		return "UpdateSqlArgsVo [sql=" + sql + ", args=" + Arrays.toString(args) + "]";
	}
	
	
}
