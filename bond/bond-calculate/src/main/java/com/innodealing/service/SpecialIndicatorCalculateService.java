package com.innodealing.service;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.innodealing.constant.CommonConstant;
import com.innodealing.dao.BondComExtDao;
import com.innodealing.dao.BondIndustryClassificationDao;
import com.innodealing.dao.IndicatorDao;
import com.innodealing.datasource.DataSource;
import com.innodealing.datasource.DataSourceFactory;
import com.innodealing.engine.OriginalData;
import com.innodealing.engine.innodealing.CalculateAlibaba;
import com.innodealing.vo.BondComExtVo;
import com.innodealing.vo.IndicatorVo;

/**
 * 计算业务
 * @author 赵正来
 *
 */


@Service
public class SpecialIndicatorCalculateService {

	/**
	 * 阿里表达式计算器
	 */
	
	@Autowired private IndicatorDao indicatorDao;
	
	@Autowired private DataSourceFactory dataSourceFactory;
	
	@Autowired private BondComExtDao bondComExtDao;
	
	@Autowired private BondIndustryClassificationDao bondIndustryClassificationDao;
	
	@Autowired private CreditRatingCalculateService creditRatingCalculateService;
	
	private static final Logger log = LoggerFactory.getLogger(SpecialIndicatorCalculateService.class);
	
	/**
	 * 计算指标
	 * @param indicatorCode
	 * @param indicatorName
	 * @param expression
	 * @param issuerId
	 * @param finDate
	 * @return
	 * @throws Exception
	 */
	public IndicatorVo calculate(String indicatorCode, String indicatorName, String expression, Long issuerId, Date finDate) throws Exception{
		
		//当期的财务数据
		Map<String, Object> data = null;
		if(finDate != null){
			data = indicatorDao.findIndicatorByIssuerIdAndFinDate(issuerId, finDate);
		}else{
			data = indicatorDao.findNewestIndicatorByIssuerId(issuerId);
		}
		
		//List
		List<Map<String, Object>> indicatorItems = new ArrayList<>();
		indicatorItems.add(data);
		
		BigDecimal result = new CalculateAlibaba(new OriginalData(indicatorItems) , getExpression(issuerId)).calculate(data, expression);
		log.info("issuerid is " + issuerId + ",findate is " + finDate + "expression[" + expression + "] value:" + result + ". ");
		IndicatorVo indicatorVo = new IndicatorVo();
		indicatorVo.setIndicatorCode(indicatorCode);
		indicatorVo.setIndicatorName(indicatorName);
		indicatorVo.setIndicatorValue(result == null ? null : result.setScale(2, BigDecimal.ROUND_HALF_UP));
		return indicatorVo;
	}
	
	/**
	 * 获取主体的表达式
	 * @param issuerId
	 * @return
	 */
	public Map<String, String> getExpression(Long issuerId){
		String type = indicatorDao.findIssuerType(issuerId);
		DataSource dataSource = dataSourceFactory.createData(type);
		return dataSource.getExpressions();
	}
	
	/**
	 * 将哪些没有正常初始化的主体(分类有问题的修复并初始化指标信息)
	 */
	@Transactional
	public boolean restoreIndicator(){
		log.info("修复主体指标信息开始！");
		List<BondComExtVo>	list = bondComExtDao.findNotInit();
		if(list == null || list.size() == 0){
			log.info("无需修复主体指标！");
			return true;
		}
		list.forEach(com -> {
			Long induCode = bondIndustryClassificationDao.findInduCodeByFourName(com.getInduUniNameL4());
			bondComExtDao.updateInduCode4(induCode, com.getComUniCode());
			creditRatingCalculateService.addCalculate(com.getAmaComId(), null, CommonConstant.SHEET_SOURCE_CCXE);
			log.info("修复主体" + com + "指标信息！");
			
		});
		log.info("修复主体指标信息结束！共修复" +  list.size() + "个主体.");
		return true;
	}
	
	
	
	public static void main(String[] args) {
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		OutputStream out = null;
		OutputStream err = null ;
		int result = compiler.run(null, out, err, "", "Test.java");
	}
}
