package com.innodealing.util;

import java.text.Collator;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

public class FindInduRatingDownSortComparator implements Comparator<Object> {

	private final static String JAVA_LANG_LONG = "java.lang.Long";
	private final static String JAVA_LANG_INTEGER = "java.lang.Integer";
	private final static String JAVA_LANG_DOUBLE = "java.lang.Double";
	private final static String JAVA_LANG_STRING = "java.lang.String";
	private final static String JAVA_LANG_DATE = "java.util.Date";
	private final static String ASC = "asc";
	private Boolean bool = false;

	// 排序字段
	private String sortField;
	// 排序规则
	private String sortDir;

	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	public String getSortDir() {
		return sortDir;
	}

	public void setSortDir(String sortDir) {
		this.sortDir = sortDir;
	}

	public FindInduRatingDownSortComparator(String sortField, String sortDir) {
		super();
		this.sortField = sortField;
		this.sortDir = sortDir;
		bool = getSort();
	}

	public FindInduRatingDownSortComparator() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int compare(Object arg0, Object arg1) {

		Object obj0 = null;
		Object obj1 = null;

		try {
			obj0 = BeanUtil.getProperty(arg0, sortField);
			obj1 = BeanUtil.getProperty(arg1, sortField);

			// null数据 最后
			if (obj0 == null && obj1 == null)
				return 0;
			if (obj0 == null)
				return 1;
			if (obj1 == null)
				return -1;
			String columnTypeName = getColumnTypeName(arg0);

			if (JAVA_LANG_LONG.equals(columnTypeName)) {
				return compareToLong((Long) BeanUtil.getProperty(arg0, sortField),
						(Long) BeanUtil.getProperty(arg1, sortField));
			} else if (JAVA_LANG_INTEGER.equals(columnTypeName)) {
				return compareToInteger((Integer) BeanUtil.getProperty(arg0, sortField),
						(Integer) BeanUtil.getProperty(arg1, sortField));
			} else if (JAVA_LANG_DOUBLE.equals(columnTypeName)) {
				return compareToDouble((Double) BeanUtil.getProperty(arg0, sortField),
						(Double) BeanUtil.getProperty(arg1, sortField));
			} else if (JAVA_LANG_STRING.equals(columnTypeName)) {
				return compareToString((String) BeanUtil.getProperty(arg0, sortField),
						(String) BeanUtil.getProperty(arg1, sortField));
			} else if (JAVA_LANG_DATE.equals(columnTypeName)) {
				return compareToDate((Date) BeanUtil.getProperty(arg0, sortField),
						(Date) BeanUtil.getProperty(arg1, sortField));
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("obj0:" + obj0 + ",obj1" + obj1);
		}
		return 0;
	}

	private int compareToString(String obj0, String obj1) {
		return bool ? Collator.getInstance(Locale.CHINESE).compare(obj0, obj1)
				: Collator.getInstance(Locale.CHINESE).compare(obj1, obj0);
	}

	private int compareToInteger(Integer obj0, Integer obj1) {
		return bool ? obj0.compareTo(obj1) : obj1.compareTo(obj0);
	}

	private int compareToDouble(Double obj0, Double obj1) {
		return bool ? Double.compare(obj0, obj1) : Double.compare(obj1, obj0);
	}

	private int compareToLong(Long obj0, Long obj1) {
		return bool ? obj0.compareTo(obj1) : obj1.compareTo(obj0);
	}

	private int compareToDate(Date obj0, Date obj1) {
		return bool ? obj0.compareTo(obj1) : obj1.compareTo(obj0);
	}

	private String getColumnTypeName(Object arg) throws Exception {
		return BeanUtil.getProperty(arg, sortField).getClass().getTypeName();
	}

	private Boolean getSort() {
		return ASC.equals(sortDir);
	}
}
