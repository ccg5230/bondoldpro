package com.innodealing.util;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Comparator;

public class BondCityComparator implements Comparator {

	private String columnName;

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public BondCityComparator(String columnName) {
		super();
		this.columnName = columnName;
	}

	public BondCityComparator() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int compare(Object arg0, Object arg1) {
		BigDecimal b1 = BigDecimal.valueOf(0);
		BigDecimal b2 = BigDecimal.valueOf(0);
		try {
			if (BeanUtil.getProperty(arg0, columnName) != null) {
				b1 = (BigDecimal) BeanUtil.getProperty(arg0, columnName);
			}
			if (BeanUtil.getProperty(arg1, columnName) != null) {
				b2 = (BigDecimal) BeanUtil.getProperty(arg1, columnName);
			}
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IntrospectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return b2.compareTo(b1);
	}

}
