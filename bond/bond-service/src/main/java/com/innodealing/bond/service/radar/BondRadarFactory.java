package com.innodealing.bond.service.radar;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.innodealing.consts.Constants;

/**
 * @author xiaochao
 * @time 2017年4月20日
 * @description: 债券预警雷达工厂类
 */
public class BondRadarFactory {

	@Autowired
	private static ApplicationContext appContext;
	
	private static final Logger LOG = LoggerFactory.getLogger(BondRadarFactory.class);
	private static final Map<Integer, String> radarTypeMap = new HashMap<>();
	static {
		radarTypeMap.put(Constants.EVENMSG_TYPE_MATURITY, "BondDurationRadar");
		radarTypeMap.put(Constants.EVENMSG_TYPE_BONDRATE, "BondNotiExtRatingRadar");
		radarTypeMap.put(Constants.EVENMSG_TYPE_FINALERT, "BondFinaRadar");
		radarTypeMap.put(Constants.EVENMSG_TYPE_ISSQURAT, "BondIssDMRatingRadar");
		radarTypeMap.put(Constants.EVENMSG_TYPE_BONDNEWS, "BondIssSentiRadar");
		radarTypeMap.put(Constants.EVENMSG_TYPE_IMRATING, "BondImpRatingRadar");
		radarTypeMap.put(Constants.EVENMSG_TYPE_QUOTEMNT, "BondQuoteRadar");
		radarTypeMap.put(Constants.EVENMSG_TYPE_EXTRATOL, "BondExtRatingOutlookRadar");
	}
	
	public static BondRadarI getBondNotiRadar(Integer eventType) {
		BondRadarI radar = null;
		try {
			if (radarTypeMap.containsKey(eventType)) {
				radar = (BondRadarI) appContext.getBean(radarTypeMap.get(eventType));
			}
		} catch (Exception ex) {
			LOG.error(String.format("createBondNotiRadar exception with eventType[%d] msg[%s]", eventType, ex.getMessage()));
		}
		return radar;
	}
}
