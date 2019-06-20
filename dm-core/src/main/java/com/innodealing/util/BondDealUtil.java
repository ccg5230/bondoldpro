package com.innodealing.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.cache.annotation.Cacheable;

/**
 * @author feng.ma
 * @date 2017年4月7日 上午11:50:37
 * @describe
 */
public class BondDealUtil {

	// 信用债，dm债券种类
	private static final List<Integer> CREDITDEBT_BT_LIST = new ArrayList<Integer>(
			Arrays.asList(2, 7, 8, 9, 10, 11, 12, 13));
	
	// 信用债，不包含存单
	private static final List<Integer> CREDITDEBT_BT_NOT_NCD_LIST = new ArrayList<Integer>(
			Arrays.asList( 2, 7, 8, 9, 11, 12, 13 ));

	public static boolean isCreditDebt(Integer dmBondType) {
		return CREDITDEBT_BT_LIST.contains(dmBondType);
	}
	
	public static boolean isCreditDebtNotNcd(Integer dmBondType) {
		return CREDITDEBT_BT_NOT_NCD_LIST.contains(dmBondType);
	}

	public static boolean isInTime0830And1700(Date date) {
		Calendar calbgn = Calendar.getInstance();
		Calendar calend = Calendar.getInstance();
		calbgn.set(Calendar.HOUR_OF_DAY, 8);
		calbgn.set(Calendar.MINUTE, 30);
		calbgn.set(Calendar.SECOND, 0);

		calend.set(Calendar.HOUR_OF_DAY, 17);
		calend.set(Calendar.MINUTE, 0);
		calend.set(Calendar.SECOND, 0);
		
		if (date.after(calbgn.getTime()) && date.before(calend.getTime())) {
			return true;
		}
		return false;
	}
}
