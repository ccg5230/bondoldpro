package com.innodealing.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.mongodb.BasicDBObject;

public class KitMongdb {

	/**
	 * 指定返回的字段
	 * 
	 * @param cols
	 * @return
	 */
	public static BasicDBObject buildFixedCols(String... cols) {
		BasicDBObject result = new BasicDBObject();
		for (String col : cols) {
			result.put(col, true);
		}
		return result;
	}
	
	private static SimpleDateFormat format = new SimpleDateFormat(  
            "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");  
	
	/**
	 * 返回在 时间的 BasicDBObject 条件 
	 * @param startTime (yyyy-MM-dd)
	 * @param endTime	(yyyy-MM-dd)
	 * @return
	 */
	public static BasicDBObject buildTimeBetween(String startTime,String endTime){
		Date stime = KitCost.strToDateStart(startTime, KitValue.YYYYMMDD);
		Date etime = KitCost.strToDateEnd(endTime, KitValue.YYYYMMDD);
		
		BasicDBObject result = new BasicDBObject();
		if(stime!=null){
			result.append("$gte", format.format(stime));
		}
		if(etime!=null){
			result.append("$lte",  format.format(etime));
		}
		return result;
	}
	
}
