package com.innodealing.bond.service.radar;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xiaochao
 * @time 2017年4月20日
 * @description: 债券预警雷达接口
 */
public interface BondRadarI {

	void trackingNotiEvent(Long bondUniCode, BigDecimal bondRate, Date pubDate);

}
