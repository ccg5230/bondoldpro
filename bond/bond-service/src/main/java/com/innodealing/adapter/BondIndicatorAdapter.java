package com.innodealing.adapter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import com.google.common.base.Objects;
import com.innodealing.bond.param.finance.IndicatorSpecialInstructionsFilter;
import com.innodealing.bond.vo.analyse.BondFinanceInfoVo;
import com.innodealing.bond.vo.analyse.FinanceInfoIndicatorVo;
import com.innodealing.bond.vo.analyse.IssFinanceChangeKVo;
import com.innodealing.bond.vo.finance.IndicatorSpecialIndicatorVo;
import com.innodealing.consts.Constants;
import com.innodealing.model.mongo.dm.bond.detail.analyse.IssIndicatorsDoc;
import com.innodealing.util.SafeUtils;

/**
 * 指标适配器
 * @author zhaozhenglai
 * @date 2017年3月3日上午10:40:17
 *Copyright © 2016 DealingMatrix.cn. All Rights Reserved.
 */
public class BondIndicatorAdapter {
	/**
	 * 
	 * @return
	 */
	public static Integer getStartYearEndYear() {
		return null;
	}

	/**
	 * 百分比转数字
	 * 
	 * @param percentVlaue
	 * @return
	 */
	public static BigDecimal conversionPercentToNumber(BigDecimal percentVlaue) {
		// 空值处理
		if (percentVlaue == null) {
			return null;
		}
		// 非空处理
		return percentVlaue.multiply(new BigDecimal(100));
	}

	/**
	 * 保留两位有效小数
	 * 
	 * @param percentVlaue
	 * @return
	 */
	public static BigDecimal KeepTwoDecimalPlaces(BigDecimal percentVlaue) {
		if (percentVlaue == null) {
			return null;
		}
		return percentVlaue.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * 元转万元
	 * @param percentVlaue
	 */
	public static BigDecimal yuanToWan(BigDecimal indicatorValue){
		if(indicatorValue == null){
			return null;
		}
		//除10000
		//indicatorValue = indicatorValue.divide(new BigDecimal(10000));
		//保留两位小数
		return KeepTwoDecimalPlaces(indicatorValue);
	}
	
	/**
	 * 格式化指标数据格式
	 * 
	 * @param indicatorValue
	 * @param percent
	 * @return
	 */
	public static BigDecimal formatterIndicator(BigDecimal indicatorValue, int percent) {
		if(indicatorValue == null){
			return null;
		}
		if (percent == Constants.INDICATOR_PERCENT_L) {
			indicatorValue = conversionPercentToNumber(indicatorValue);
		}else if(percent == Constants.INDICATOR_PERCENT_W){
			//indicatorValue = yuanToWan(indicatorValue);
		}
		return KeepTwoDecimalPlaces(indicatorValue);
	}

	/**
	 * 
	 * @param indicatorValue
	 * @param unit
	 * @return
	 */
	public static BigDecimal formatterUnit(BigDecimal indicatorValue,  Integer unit){
		if(unit != null && indicatorValue != null){
			if(Objects.equal(Constants.WAN, unit)){
				indicatorValue = indicatorValue.divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_UP);
			}
			if(Objects.equal(Constants.YI, unit)){
				indicatorValue = indicatorValue.divide(new BigDecimal(100000000), 2, BigDecimal.ROUND_HALF_UP);
			}
		}
		return indicatorValue;
	}
	
	/**
	 * 格式化指标数据单位
	 */
	public static List<IndicatorSpecialIndicatorVo> doFormatterIndicatorUnit(
			List<IndicatorSpecialIndicatorVo> listIndacator, Integer unit) {
		List<IndicatorSpecialIndicatorVo> listIndacatorFormattered = new ArrayList<>();
		for (IndicatorSpecialIndicatorVo indicatorSpecialIndicatorVo : listIndacator) {
			List<BigDecimal> values = indicatorSpecialIndicatorVo.getValues();
			List<BigDecimal> valuesFormattered = new ArrayList<>();
			for (BigDecimal bigDecimal : values) {
				valuesFormattered.add(formatterUnit(bigDecimal, unit));
			}
			indicatorSpecialIndicatorVo.setValues(valuesFormattered);
			listIndacatorFormattered.add(indicatorSpecialIndicatorVo);
		}
		
		listIndacatorFormattered.sort(new Comparator<IndicatorSpecialIndicatorVo>() {

			@Override
			public int compare(IndicatorSpecialIndicatorVo v1, IndicatorSpecialIndicatorVo v2) {
				return v1.getSort().compareTo(v2.getSort());
			}
		});
		return listIndacatorFormattered;
	}
	
	/**
	 * 格式化指标数据格式
	 */
	public static List<IndicatorSpecialIndicatorVo> doFormatterIndicator(
			List<IndicatorSpecialIndicatorVo> listIndacator) {
		List<IndicatorSpecialIndicatorVo> listIndacatorFormattered = new ArrayList<>();
		for (IndicatorSpecialIndicatorVo indicatorSpecialIndicatorVo : listIndacator) {
			List<BigDecimal> values = indicatorSpecialIndicatorVo.getValues();
			List<BigDecimal> valuesFormattered = new ArrayList<>();
			for (BigDecimal bigDecimal : values) {
				valuesFormattered.add(formatterIndicator(bigDecimal, indicatorSpecialIndicatorVo.getPercent()));
			}
			indicatorSpecialIndicatorVo.setValues(valuesFormattered);
			listIndacatorFormattered.add(indicatorSpecialIndicatorVo);
		}
		return listIndacatorFormattered;
	}
	
	
	/**
	 * 格式化指标数据格式-k线图
	 */
	public static List<IssFinanceChangeKVo> doFormatterIndicatorKchart(
			List<IssFinanceChangeKVo> listIndacator) {
		List<IssFinanceChangeKVo> listIndacatorFormattered = new ArrayList<>();
		for (IssFinanceChangeKVo issFinanceChangeKVo : listIndacator) {
			int percent = issFinanceChangeKVo.getPercent();
			issFinanceChangeKVo.setIssIn(formatterIndicator(issFinanceChangeKVo.getIssIn(), percent));
			issFinanceChangeKVo.setInduInAvg(formatterIndicator(issFinanceChangeKVo.getInduInAvg(), percent));
			issFinanceChangeKVo.setCityInAvg(formatterIndicator(issFinanceChangeKVo.getCityInAvg(), percent));
			issFinanceChangeKVo.setMin(formatterIndicator(issFinanceChangeKVo.getMin(), percent));
			issFinanceChangeKVo.setIn10(formatterIndicator(issFinanceChangeKVo.getIn10(), percent));
			issFinanceChangeKVo.setIn15(formatterIndicator(issFinanceChangeKVo.getIn15(), percent));
			issFinanceChangeKVo.setIn75(formatterIndicator(issFinanceChangeKVo.getIn75(), percent));
			issFinanceChangeKVo.setIn90(formatterIndicator(issFinanceChangeKVo.getIn90(), percent));
			issFinanceChangeKVo.setMax(formatterIndicator(issFinanceChangeKVo.getMax(), percent));
			listIndacatorFormattered.add(issFinanceChangeKVo);
		}
		return listIndacatorFormattered;
	}
	
	/**
	 * 格式化指标数据格式-行业比较
	 */
	public static List<BondFinanceInfoVo> doFormatterIndicatorCompareIndu(
			List<BondFinanceInfoVo> listIndacator) {
		List<BondFinanceInfoVo> listIndacatorFormattered = new ArrayList<>();
		for (BondFinanceInfoVo bondFinanceInfoVo : listIndacator) {
			List<FinanceInfoIndicatorVo> indicators = bondFinanceInfoVo.getIndicators();
			List<FinanceInfoIndicatorVo>  indicatorsTemp = new ArrayList<>();
			for (FinanceInfoIndicatorVo financeInfoIndicatorVo : indicators) {
				int percent = financeInfoIndicatorVo.getPercent();
				financeInfoIndicatorVo.setInduPos(formatterIndicator(financeInfoIndicatorVo.getInduPos(), percent));
				financeInfoIndicatorVo.setInduVal(formatterIndicator(financeInfoIndicatorVo.getInduVal(), percent));
				List<BigDecimal> induValVesTemp = new ArrayList<>();
				if(financeInfoIndicatorVo.getInduValVes() != null){
					for (BigDecimal induVal : financeInfoIndicatorVo.getInduValVes()) {
						induValVesTemp.add(formatterIndicator(induVal, percent));
					}
				}
				financeInfoIndicatorVo.setInduValVes(induValVesTemp);
				financeInfoIndicatorVo.setIssVal(formatterIndicator(financeInfoIndicatorVo.getIssVal(), percent));
				indicatorsTemp.add(financeInfoIndicatorVo);
			}
			
			
			listIndacatorFormattered.add(bondFinanceInfoVo);
		}
		return listIndacatorFormattered;
	}
	
	/**
	 * 初始化开始年份和结束年份
	 * @param filter
	 * @param startYear
	 * @param endYear
	 */
	public static Integer[] initStartEndYear(IndicatorSpecialInstructionsFilter filter){
		 Integer startYear,  endYear;
		// 时间范围({1:3Y,2:5Y,3:10Y})
		Integer timeHorizon = filter.getTimeHorizon();
		int[] horizon = {0, 3, 5, 10};
		// 当前年份
		int nowYear = Calendar.getInstance().get(Calendar.YEAR);
		if (timeHorizon == null && !(filter.getStartYear() == null && filter.getEndYear() == null)) {
			startYear = filter.getStartYear();
			endYear = filter.getEndYear();
			if (endYear == null) {
				endYear = nowYear;
			}
			if (startYear == null) {
				startYear = nowYear - 10;
			}
		} else {
			startYear = nowYear - horizon[timeHorizon] + 1;
			endYear = nowYear;
		}
		Integer[] result = {startYear, endYear};
		return result;
	}
	
	
	/**
	 * 整个行业的数据季度与当前的进行同步
	 * @param issIndicatorsDoc
	 * @param list
	 * @return
	 */
	public static List<IssIndicatorsDoc> syncInduToIss(IssIndicatorsDoc issIndicatorsDoc, List<IssIndicatorsDoc> list) {
		List<String> currentQuarters =  issIndicatorsDoc.getQuarters();
        List<IssIndicatorsDoc> temp = new ArrayList<>();
        for (IssIndicatorsDoc issIndicator : list) {
        	if(!issIndicator.getIssId().equals(issIndicatorsDoc.getIssId())){
        		List<String> otherQuarters = issIndicator.getQuarters();
        		List<BigDecimal> otherIndicators = issIndicator.getIndicators();
        		//重组指标值和当前主体保持一致
        		List<BigDecimal> initList = new ArrayList<>();
        		for (String q : currentQuarters) {
        			int index = otherQuarters.indexOf(q);
        			if(index == -1 || !(index < otherIndicators.size())){
        				initList.add(null);
        			}else{
        				initList.add(otherIndicators.get(index));
        			}
				}
        		//设置季度和指标
        		issIndicator.setQuarters(currentQuarters);
        		issIndicator.setIndicators(initList);
        		temp.add(issIndicator);
        	}else{
        		temp.add(issIndicator);
        	}
		}
		return temp;
	}
	
	
	/**
	 * 格式化雷达图值，保证在[0-1]范围内
	 * @param bigDecimal
	 * @return
	 */
	public static BigDecimal radarValueFormatter(BigDecimal bigDecimal){
		if(bigDecimal == null){
			return null;
		}
		if(bigDecimal.doubleValue()< 0){
			return new BigDecimal(0);
		}
		if(bigDecimal.doubleValue() > 1){
			return new BigDecimal(1);
		}
		return bigDecimal;
	}
	
	
	
	/**
	 * 格式化季度，多减少补
	 * @param quarters 现有季度
	 * @param size 季度数量
	 * @return
	 */
	public static List<String> formatterQuarters(List<String> quarters, int size) {
		if(quarters == null){
			quarters = new ArrayList<>();
			quarters.add(SafeUtils.getQuarter(new Date()));
		}
		if(quarters.size() < size){
        	String alearQuarter = quarters.get(quarters.size()-1);
        	String[] dates = {"", "03-01", "06-01", "09-01", "12-01"};
        	String m = alearQuarter.substring(alearQuarter.length() - 1, alearQuarter.length());
        	String year = alearQuarter.substring(0, 4);
        	String date = year + "-" + dates[SafeUtils.getInt(m)];
        	String[] addQ = SafeUtils.getQuarter(date, 6-quarters.size());
        	for (String q : addQ) {
        		quarters.add(q);
			}
        }
		return quarters;
	}
}
