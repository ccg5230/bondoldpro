package com.innodealing.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 
 * 
 * @author 戴永杰
 *
 * @date 2017年9月18日 下午2:37:37
 * @version V1.0
 *
 */
public class SQLParam {

	/**
	 * 存储SQL语句
	 */
	private StringBuilder strSQL;
	/**
	 * 存储SQL所需要的参数
	 */
	private List<Object> argParams;

	public SQLParam() {
		strSQL = new StringBuilder();
		argParams = new ArrayList<>();
	}

	public void clear() {
		strSQL.setLength(0);
		argParams.clear();
	}

	/**
	 * 为SQL语句设置占位符 ?,?,?
	 */
	public SQLParam placeholder(int size) {

		if (size == 0)
			return this;

		for (int i = 0; i < size; i++) {
			strSQL.append("?,");
		}
		strSQL.setLength(strSQL.length() - 1);
		return this;
	}

	public SQLParam append(String sql) {
		strSQL.append(sql);
		return this;
	}

	public SQLParam addArg(Object arg) {
		argParams.add(arg);
		return this;
	}

	public SQLParam addArg(Object[] args) {
		for (Object arg : args) {
			addArg(arg);
		}
		return this;
	}
	
	public SQLParam showShowSQL(){
		System.out.println(strSQL);
		return this;
	}
	
	/**
	 * %arg%
	 * @param arg
	 * @return
	 */
	public SQLParam addLikeArg(Object arg) {
		argParams.add("%" + arg + "%");
		return this;
	}

	public int length() {
		return strSQL.length();
	}

	public void setLength(int newLength) {
		strSQL.setLength(newLength);
	}

	public String getSql() {
		return strSQL.toString();
	}

	public String getPageCountSQL() {
		int fromIndex = strSQL.toString().toLowerCase().indexOf("from");
		String exceptSQL = strSQL.substring(fromIndex);
		return "select count(*) " + Holder.ORDER_BY_PATTERN.matcher(exceptSQL).replaceAll("");
	}

	public String getPageSQL(int pageNumber, int pageSize) {
		if ((pageNumber - 1) < 0)
			pageNumber = 1;
		return strSQL.toString() + " limit " + (pageNumber - 1) * pageSize + "," + pageSize;

	}

	public Object[] getArg() {
		return argParams.toArray(new Object[argParams.size()]);
	}

	protected static class Holder {
		// "order\\s+by\\s+[^,\\s]+(\\s+asc|\\s+desc)?(\\s*,\\s*[^,\\s]+(\\s+asc|\\s+desc)?)*";
		private static final Pattern ORDER_BY_PATTERN = Pattern.compile(
				"order\\s+by\\s+[^,\\s]+(\\s+asc|\\s+desc)?(\\s*,\\s*[^,\\s]+(\\s+asc|\\s+desc)?)*",
				Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
	}
	
	public static void main(String[] args) {
		
		
		
		System.out.println("dddd"+4*2);
	}

}
