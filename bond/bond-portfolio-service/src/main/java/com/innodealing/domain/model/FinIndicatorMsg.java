package com.innodealing.domain.model;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.innodealing.util.SafeUtils;

/**
 * @author feng.ma
 * @date 2017年5月26日 下午5:34:11
 * @describe:财务数据变动数据模型类
 */
public class FinIndicatorMsg {

	public final static int IDX_STATUS_VALID = 1;
	public final static int IDX_STATUS_INVALID = 0;

	public final static int IDX_VALUE_TYPE_SELF = 1;
	public final static int IDX_VALUE_TYPE_YOY = 2;
	public final static int IDX_VALUE_TYPE_RANK = 3;

	public final static String RANK_CHANGE_TYPE = "RANK";
	public final static String SELF_CHANGE_TYPE = "SELF";
	public final static String YOY_CHANGE_TYPE = "YOY";

	public final static String COM_UNICODE = "comUniCode";
	public final static String FIN_QUARTER = "finQuarter";
	//1，新增财报；2，修改财报; 91 为规定时间未收集到财报
	public final static String FIN_RPTFLAG = "finRptFlag";
	public final static String BOND_UNICODE = "bondUniCode";
	
	//1，新增财报；2，修改财报; 91 为规定时间未收集到财报
	public final static int FIN_RPTFLAG_01 = 1;
	public final static int FIN_RPTFLAG_02 = 2;
	public final static int FIN_RPTFLAG_91 = 91;

	public final static Map<String, Integer> CHANGE_TYPE_MAP = new HashMap<>();
	{
		CHANGE_TYPE_MAP.put(RANK_CHANGE_TYPE, IDX_VALUE_TYPE_RANK);
		CHANGE_TYPE_MAP.put(SELF_CHANGE_TYPE, IDX_VALUE_TYPE_SELF);
		CHANGE_TYPE_MAP.put(YOY_CHANGE_TYPE, IDX_VALUE_TYPE_YOY);
	}

	private Map<String, Map<String, BigDecimal>> changesMap = new HashMap<>();
	private Long comUniCode; // 发生变化的发行人id
	private String finQuarter; // 发生变化的财报日期
	final String[] CHANGE_TYPES = { RANK_CHANGE_TYPE, SELF_CHANGE_TYPE, YOY_CHANGE_TYPE };

	/**
	 * 用户设置的指标有指标本身，同比，行业排名三种， 所以我们用表达式计算来作为通用方法时，需要自己构建内部表达式，比如 BS311 > 100
	 * 在设置为
	 */
	public FinIndicatorMsg(JSONObject jsonObj) {
		comUniCode = SafeUtils.getLong(jsonObj.get(COM_UNICODE));
		finQuarter = SafeUtils.getString(jsonObj.get(FIN_QUARTER));
		// initialize changesMap
		setChangesMap(changesMap, jsonObj);
	}
	
	public FinIndicatorMsg(JSONObject jsonObj, boolean isBond) {
		comUniCode = SafeUtils.getLong(jsonObj.get(getUniCode(isBond)));
		finQuarter = SafeUtils.getString(jsonObj.get(FIN_QUARTER));
		// initialize changesMap
		setChangesMap(changesMap, jsonObj);
	}

	private String getUniCode(boolean isBond) {
		String uniCode = COM_UNICODE;
		if (isBond) {
			uniCode = BOND_UNICODE;
		}
		return uniCode;
	}

	private void setChangesMap(Map<String, Map<String, BigDecimal>> changesMaps, JSONObject jsonObj) {
		for (int i = 0; i < CHANGE_TYPES.length; i++) {
			String changeType = CHANGE_TYPES[i];
			setChangeMapData(changesMaps, jsonObj, changeType);
		}
	}

	private void setChangeMapData(Map<String, Map<String, BigDecimal>> changesMaps, JSONObject jsonObj,
			String changeType) {
		if (null != jsonObj.get(changeType)) {
			Map<String, BigDecimal> dataMap = new HashMap<>();
			JSONObject dataObj = (JSONObject) JSON.toJSON(jsonObj.get(changeType));
			for (String feildName : dataObj.keySet()) {
				dataMap.put(feildName, new BigDecimal(dataObj.get(feildName).toString()));
			}
			if (!dataMap.isEmpty()) {
				changesMaps.put(changeType, dataMap);
			}
		}
	}

	public Long getComUniCode() {
		return comUniCode;
	}

	public String getFinQuarter() {
		return finQuarter;
	}

	public Map<String, BigDecimal> getChgFields(String changeType) {
		return changesMap.get(changeType);
	}

	public Map<String, Map<String, BigDecimal>> getChgMap() {
		return changesMap;
	}

}
