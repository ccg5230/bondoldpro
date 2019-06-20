package com.innodealing.bond.service.finance;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.innodealing.adapter.BondIndicatorAdapter;
import com.innodealing.aop.NoLogging;
import com.innodealing.bond.param.finance.IndicatorSpecialInstructionsFilter;
import com.innodealing.bond.service.AmaBondService;
import com.innodealing.bond.service.BondInduService;
import com.innodealing.bond.service.PdMappingService;
import com.innodealing.bond.vo.analyse.BondFinanceInfoVo;
import com.innodealing.bond.vo.analyse.BondIssIndicatorGroupVo;
import com.innodealing.bond.vo.analyse.BondIssIndicatorVo;
import com.innodealing.bond.vo.analyse.FinanceInfoIndicatorVo;
import com.innodealing.bond.vo.analyse.IndicatorGroupVo;
import com.innodealing.bond.vo.analyse.IndicatorVo;
import com.innodealing.bond.vo.analyse.IssFinanceChangeKVo;
import com.innodealing.bond.vo.analyse.IssFinanceChangeVo;
import com.innodealing.bond.vo.analyse.IssIndicatorPdVo;
import com.innodealing.bond.vo.analyse.IssIndicatorVo;
import com.innodealing.bond.vo.analyse.IssIndicatorsVo;
import com.innodealing.bond.vo.finance.ComQuantileInfoIndicatorItemVo;
import com.innodealing.bond.vo.finance.ComQuantileInfoVo;
import com.innodealing.bond.vo.finance.IssNoFinanceInDateVo;
import com.innodealing.domain.vo.bond.PdMappingVo;
import com.innodealing.engine.jdbc.bond.AmaBondDao;
import com.innodealing.engine.jdbc.bond.BondIndicatorExpressionDao;
import com.innodealing.engine.jdbc.bond.IndicatorDao;
import com.innodealing.exception.BusinessException;
import com.innodealing.model.dm.bond.BondCom;
import com.innodealing.model.dm.bond.IssPdHis;
import com.innodealing.model.mongo.dm.BondComInfoDoc;
import com.innodealing.model.mongo.dm.BondDetailDoc;
import com.innodealing.model.mongo.dm.bond.detail.analyse.BondFinanceInfoInduDoc;
import com.innodealing.model.mongo.dm.bond.detail.analyse.BondFinanceInfoQuarterDoc;
import com.innodealing.model.mongo.dm.bond.detail.analyse.FinanceInfoIndicator;
import com.innodealing.model.mongo.dm.bond.detail.analyse.FinanceInfoIndu;
import com.innodealing.model.mongo.dm.bond.detail.analyse.FinanceInfoQuarter;
import com.innodealing.model.mongo.dm.bond.detail.analyse.IssIndicatorFieldGroupMapping;
import com.innodealing.model.mongo.dm.bond.detail.analyse.IssIndicatorsDoc;
import com.innodealing.model.mongo.dm.bond.detail.analyse.IssKeyIndicatorDoc;
import com.innodealing.model.mongo.dm.bond.detail.analyse.IssPdDoc;
import com.innodealing.model.mongo.dm.bond.finance.FinanceIndicator;
import com.innodealing.uilogic.UIAdapter;
import com.innodealing.util.NumberUtils;
import com.innodealing.util.SafeUtils;

import io.swagger.annotations.ApiModelProperty;

@Service
public class BondFinanceInfoService {

//    @Autowired
//    private InduCredRatingRepository induRatingDownRepository;

//    @Autowired
//    private IssBreachProbabilityCompareRepository induBreachProbabilityCompareRepository;

    @Autowired private MongoTemplate mongoTemplate;
    
    @Autowired private AmaBondService amaBondService;
    
    @Autowired private PdMappingService pdMappingService;
    
    @Autowired private BondInduService bondInduService;
    
    @Autowired private IndicatorAnalysisService analysisService;
    
    @Autowired private BondIndicatorExpressionDao dondIndicatorExpressionDao;
    
    @Autowired private IndicatorDao finaSheetDao;
    
    @Autowired private AmaBondDao amaBondDao;
    /**
     * 违约概率分析-财务指标变动情况
     * @param issId  公司id
     */
    public List<IssFinanceChangeVo>  findIssFinanceChange(Long issId){
        List<String> columns = getFields(issId);
        Query query =  new Query(Criteria.where("issId").is(issId).and("indicators.field").in(columns));
        IssKeyIndicatorDoc indicatorDoc = mongoTemplate.findOne(query, IssKeyIndicatorDoc.class);
        
        List<IssIndicatorsDoc> list =  IssKeyIndicatorsToIssIndicatorsDocs(indicatorDoc, 6);//mongoTemplate.find(query, IssIndicatorsDoc.class);
        
        List<IssFinanceChangeVo> result = new ArrayList<>();
        for (IssIndicatorsDoc issIndicator : list) { 
            String category = issIndicator.getCategory();
            List<String> q = issIndicator.getQuarters();
            if(q==null || q.isEmpty())
            	continue;
            BigDecimal currIn = issIndicator.getIndicators().get(0);
            String currInName = issIndicator.getFieldName();
            BigDecimal lastIn = issIndicator.getIndicators().get(1);
            int type = issIndicator.getType();
            int isNegative = issIndicator.getNegative();
            String currQ = q.get(0);
            String lastQ = q.get(1);
            int percent = issIndicator.getPercent();
            //较上期
            BigDecimal toLastIn = null;
            if(currIn != null && lastIn != null){
                toLastIn = currIn.subtract(lastIn);
            }
            IssFinanceChangeVo issFinanceChange = new IssFinanceChangeVo(issId, category.toString(), currIn, lastIn, toLastIn, currQ, lastQ, currInName, type, isNegative, percent);
            issFinanceChange.setFinDate(issIndicator.getLastFinDate());
            result.add(issFinanceChange);
        }
        return result;
    }
    
    /**
     * 违约概率分析-财务指标变动情况k线图
     * @param issId  公司id
     */
    public List<IssFinanceChangeKVo>  findIssFinanceChangeKX(Long issId, String category, String field, List<Long> provinceIds, Long userId){
        Query query =  new Query(Criteria.where("issId").is(issId));
        IssKeyIndicatorDoc indicatorDoc = mongoTemplate.findOne(query, IssKeyIndicatorDoc.class);
        IssIndicatorsDoc  one = getIndicator(IssKeyIndicatorsToIssIndicatorsDocs(indicatorDoc), field);
        if(one == null){
            //throw new BusinessException("该发行主体未披露财报！");
            return new ArrayList<>();
        }
        
        //所属行业所有信息
        Query queryIndu =  new Query();
        //用户行业分类信息判断
        
        List<Long> comUniCodes = bondInduService.findComUniCodeByIndu(issId, userId);
        if(comUniCodes != null){
        	 queryIndu.addCriteria(Criteria.where("comUniCode").in(comUniCodes));
        }
        
        if(provinceIds != null){
            queryIndu.addCriteria(Criteria.where("provinceId").in(provinceIds));
        }
        List<IssKeyIndicatorDoc> listIssKeyIndicatorDoc = mongoTemplate.find(queryIndu, IssKeyIndicatorDoc.class);
        List<IssIndicatorsDoc> list = IssKeyIndicatorsToIssIndicatorsDocsList(listIssKeyIndicatorDoc);
        
        list = BondIndicatorAdapter.syncInduToIss(getIndicator(list, field), list);
        list = filterIndicatorCategorys(list, toList(category));
        list = filterIndicatorFields(list, toList(field));
        //构建返回数据
        List<IssFinanceChangeKVo> result = buildIssFinanceChangeVo(one, list);
        return result;
    }
    
	/**
	 * Object to list
	 * @param value
	 * @return
	 */
    public <T> List<T> toList(T value){
    	List<T> list = new ArrayList<>();
    	list.add(value);
    	return list;
    }
    
    /**
     * 构建数据List<IssFinanceChangeKVo>
     * @param one
     * @param list
     * @return
     */
	public List<IssFinanceChangeKVo> buildIssFinanceChangeVo(IssIndicatorsDoc one, List<IssIndicatorsDoc> list) {
		List<IssFinanceChangeKVo> result = new ArrayList<>();
        //最新的六个季度
        List<String> quarters = BondIndicatorAdapter.formatterQuarters( one.getQuarters(), 6);
        List<BigDecimal> indicators =  one.getIndicators();
        for (int i = 5; i > -1; i--) {
            //行业某个指标集合
            List<BigDecimal> inList = new ArrayList<>();
            for (IssIndicatorsDoc iis : list) {
            	BigDecimal issn = null;
            	if((i + 1) > iis.getIndicators().size()){
            		
            	}else{
            		issn = iis.getIndicators().get(i);
            	}
                if(issn != null){
                    inList.add(issn);
                }
            }
            //季度
            String quarter = quarters.get(i);
            //主体指标
            BigDecimal issIn = null;
            if(!(i + 1 > indicators.size())){
            	issIn = indicators.get(i);
            }
            //行业平均指标
            BigDecimal induInAvg = SafeUtils.getQuantile(inList, 50, one.getNegative());
            //10、15、75、90分位值
            BigDecimal in10 = SafeUtils.getQuantile(inList, 10, one.getNegative()),
                       in15 = SafeUtils.getQuantile(inList, 25, one.getNegative()),
                       in75 = SafeUtils.getQuantile(inList, 75, one.getNegative()),
                       in90 = SafeUtils.getQuantile(inList, 90, one.getNegative()),
                       min = SafeUtils.getQuantile(inList, 1, one.getNegative()),
                       max = SafeUtils.getQuantile(inList, 100, one.getNegative());
            int percent  = one.getPercent();
            String fieldName = one.getFieldName();
            IssFinanceChangeKVo k = new IssFinanceChangeKVo(quarter, issIn, induInAvg, in90, in75, in15, in10, one.getType(), min, max, percent, fieldName);
            result.add(k);
        }
		return result;
	}

	
	
	
	
	/**
     * 构建数据List<IssFinanceChangeKVo>
     * @param one
     * @param list
     * @return
     */
	public List<IssFinanceChangeKVo> buildIssFinanceChangeVo( List<BigDecimal> listIndicotrs, IssIndicatorsDoc one) {
		List<IssFinanceChangeKVo> result = new ArrayList<>();
		List<String> quarters = one.getQuarters();
        List<BigDecimal> indicators =  one.getIndicators();
        for (int i = quarters.size() -1; i > -1; i--) {
            
            //季度
            String quarter = quarters.get(i);
            //主体指标
            BigDecimal issIn = indicators.get(i);
            //行业平均指标
            BigDecimal induInAvg = SafeUtils.getQuantile(listIndicotrs, 50, one.getNegative());
            //10、15、75、90分位值
            BigDecimal in10 = SafeUtils.getQuantile(listIndicotrs, 10, one.getNegative()),
                       in15 = SafeUtils.getQuantile(listIndicotrs, 25, one.getNegative()),
                       in75 = SafeUtils.getQuantile(listIndicotrs, 75, one.getNegative()),
                       in90 = SafeUtils.getQuantile(listIndicotrs, 90, one.getNegative()),
                       min = SafeUtils.getQuantile(listIndicotrs, 1, one.getNegative()),
                       max = SafeUtils.getQuantile(listIndicotrs, 100, one.getNegative());
            int percent  = one.getPercent();
            String fieldName = one.getFieldName();
            IssFinanceChangeKVo k = new IssFinanceChangeKVo(quarter, issIn, induInAvg, in90, in75, in15, in10, one.getType(), min, max, percent, fieldName);
            result.add(k);
        }
		return result;
	}
    
    
   
    /**
     * 违约概率分析-本期重点财务指标于行业位置分析
     * @param compId
     * @param provinceIds
     * @param userId
     * @return
     */
    public List<BondFinanceInfoVo> findBondFinanceInfoDoc(Long compId, List<Long> provinceIds, Long userId, Integer year, Integer quarter, IndicatorSpecialInstructionsFilter filter) {
        List<String> columns = getFields(compId);
        //主体所有财务指标
        Query query =  new Query(Criteria.where("issId").is(compId));
        IssKeyIndicatorDoc indicatorDoc = mongoTemplate.findOne(query, IssKeyIndicatorDoc.class);
        List<IssIndicatorsDoc> listIss =  IssKeyIndicatorsToIssIndicatorsDocs(indicatorDoc);//mongoTemplate.find(query, IssIndicatorsDoc.class);
        if(null != filter) {
            columns = new ArrayList<String>();
            columns.addAll(filter.getFields());
        }
        //指标过滤
        listIss = filterIndicatorFields(listIss, columns);
        if(listIss == null || listIss.size() == 0){
            return new ArrayList<>();
            //throw new BusinessException("该发行主体未披露财报！");
        }
        int indexToView = 0;
        if(year != null && quarter != null){
        	List<String> q = listIss.get(0).getQuarters();
        	indexToView = q.indexOf(year + "/Q" + quarter);
        	if(indexToView == -1) return new ArrayList<>();
        }
        //主体行业所有财务指标
        Map<String, List<BigDecimal>> listInduMap =  buildInduIndicator(listIss.get(0), provinceIds, userId, indexToView);
        //最新季度
        List<String> quarters = indicatorDoc.getQuarters();
        
        //专项指标表达式汉子描述
        Map<String,String> fieldExpressMap = dondIndicatorExpressionDao.findExpressDescription();
        //行业最新指标
        List<FinanceInfoIndicator> indicators = new ArrayList<>();
        List<BondFinanceInfoVo> result = new ArrayList<>();
        for (IssIndicatorsDoc issIndicator : listIss) {
            //主体最新季度指标
            BigDecimal issVal = null;
            if( !(issIndicator.getIndicators().size() < (indexToView + 1))){
            	issVal = issIndicator.getIndicators().get(indexToView);
            }
            //公司上期    
            BigDecimal issValLast = null;
            if( !(issIndicator.getIndicators().size() < (indexToView + 2))){
            	issValLast = issIndicator.getIndicators().get(indexToView + 1);
            }
            //指标名称
            String indicator = issIndicator.getFieldName();
            //负向指标标记
            int isNegative = issIndicator.getNegative();
            // 所属种类
            String category = issIndicator.getCategory();
            //指标类型
            int type = issIndicator.getType();
            //百分比
            int percent = issIndicator.getPercent();
            List<BigDecimal> listInd = listInduMap.get(issIndicator.getField());
            if(listInd == null){
                FinanceInfoIndicator sssIndicator  = new FinanceInfoIndicator(indicator, issVal, null, isNegative, 0, null, null, 
                        category, type, null,percent,fieldExpressMap.get(issIndicator.getField()));
                indicators.add(sssIndicator);
                continue;
            }
           
            BigDecimal min      = SafeUtils.getQuantile(listInd, 1,issIndicator.getNegative());//行业min分位
            BigDecimal induin10 = SafeUtils.getQuantile(listInd, 10,issIndicator.getNegative());//行业10分位
            //BigDecimal induin15 = SafeUtils.getQuantile(listInd, 15);//行业15分位
            BigDecimal induin25 = SafeUtils.getQuantile(listInd, 25,issIndicator.getNegative());//行业15分位
            //BigDecimal induin30 = SafeUtils.getQuantile(listInd, 30);//行业30分位
            //BigDecimal induin45 = SafeUtils.getQuantile(listInd, 45);//行业45分位
            BigDecimal induin50 = SafeUtils.getQuantile(listInd, 50,issIndicator.getNegative());//行业50分位
            //BigDecimal induin60 = SafeUtils.getQuantile(listInd, 60);//行业60分位
            BigDecimal induin75 = SafeUtils.getQuantile(listInd, 75,issIndicator.getNegative());//行业75分位
            BigDecimal induin90 = SafeUtils.getQuantile(listInd, 90,issIndicator.getNegative());//行业90分位
            BigDecimal max      = SafeUtils.getQuantile(listInd, 100,issIndicator.getNegative());//行业max分位
          
           
            // 行业水平=（50分位-10分位）/（90分位-10分位）
            // 公司水平=（公司数值-10分位）/（90分位-10分位）
            BigDecimal divisor = induin90.subtract(induin10);
            BigDecimal issValQ = new BigDecimal(1);
            if(divisor.doubleValue() != 0 && issVal != null){
                issValQ = issVal.subtract(induin10).divide((induin90.subtract(induin10)), 2);//公司水平分位值
            }
            //BigDecimal induValQ = induin50.subtract(induin10).divide((induin90.subtract(induin10)), 2);//行业水平分位值
            
            
            BigDecimal induVal = induin50.doubleValue() == 0 && listInd.size() ==0 ? null : induin50; // 行业水平
//            if("总资产收益率(％)".equals(issIndicator.getFieldName())){
//                System.out.println(1);
//            }
            
          
            //如果实际的分位值小于公司值，将quantile++
            List<BigDecimal> induValVes = new ArrayList<>();
            induValVes.add(min );
            induValVes.add(induin10);
            induValVes.add(induin25);
            induValVes.add(induin50);
            induValVes.add(induin75);
            induValVes.add(induin90);
            induValVes.add(max);
            Map<Double,Integer> quantileMap = new LinkedHashMap<>();
            quantileMap.put(min.doubleValue(), 1);
            quantileMap.put(induin10.doubleValue(), 10);
            quantileMap.put(induin25.doubleValue(), 25);
            quantileMap.put(induin50.doubleValue(), 50);
            quantileMap.put(induin75.doubleValue(), 75);
            quantileMap.put(induin90.doubleValue(), 90);
            quantileMap.put(max.doubleValue(), 100);
            //公司的所在分位值
            Integer quantile = SafeUtils.getQuantileValue(listInd, issVal, issIndicator.getNegative());
            quantile = getQuantileOffSet(issVal, induValVes, quantileMap, quantile);
            //"较上期指标相比(-1下降，0不变，1上升)")
            int toOrevious = 0;
            if(issVal == null || issValLast == null){
                toOrevious = 0;
            }else{
                if(issVal.subtract(issValLast).doubleValue() > 0){
                    toOrevious = 1;
                }else if(issVal.subtract(issValLast).doubleValue() == 0){
                    toOrevious = 0;
                }else{
                    toOrevious = -1;
                }
            }
			if(isNegative == 1)
            	toOrevious = -toOrevious;
            FinanceInfoIndicator sssIndicator  = new FinanceInfoIndicator(indicator, issVal, induVal, isNegative, toOrevious, issValQ, induValVes, 
                    category, type, quantile, percent,fieldExpressMap.get(issIndicator.getField()));
            indicators.add(sssIndicator);
        }
        
        Map<String,List<FinanceInfoIndicatorVo>> map = new HashMap<>();
        for (FinanceInfoIndicator fii : indicators) {
            String category = fii.getCategory();
            if(map.get(category) == null){
                List<FinanceInfoIndicatorVo> list = new ArrayList<>();
                list.add(new FinanceInfoIndicatorVo(fii));
                map.put(category, list);
            }else{
                map.get(category).add(new FinanceInfoIndicatorVo(fii));
            }
        }
        for (Entry<String,List<FinanceInfoIndicatorVo>> bondFinanceInfoDoc : map.entrySet()) {
            result.add(new BondFinanceInfoVo(compId, bondFinanceInfoDoc.getKey(), quarters.get(indexToView), bondFinanceInfoDoc.getValue()));
        }
        return BondIndicatorAdapter.doFormatterIndicatorCompareIndu(result);
    }

    
    /**
     * 主体指标分位值信息
     * @param comUniCode
     * @param fields
     * @param userId
     * @return
     */
    public ComQuantileInfoVo findComQuantile(Long comUniCode,List<String> fields, Long userId) {
        //主体所有财务指标
        Query query =  new Query(Criteria.where("issId").is(comUniCode));
        IssKeyIndicatorDoc indicatorDoc = mongoTemplate.findOne(query, IssKeyIndicatorDoc.class);
        List<IssIndicatorsDoc> listIss =  IssKeyIndicatorsToIssIndicatorsDocs(indicatorDoc);//mongoTemplate.find(query, IssIndicatorsDoc.class);
        
        //主体指标分位值信息
        ComQuantileInfoVo result = new ComQuantileInfoVo();
        
        if(listIss==null || listIss.size()<1){
        	return result;
        }
        
        result.setComChiName(indicatorDoc.getIssName());
        result.setComUniCode(comUniCode);
        List<ComQuantileInfoIndicatorItemVo> quantiles  = new ArrayList<>();
        //指标过滤
        listIss = filterIndicatorFields(listIss, fields);
        if(listIss == null || listIss.size() == 0){
            return result;
            //throw new BusinessException("该发行主体未披露财报！");
        }     
        //主体行业所有财务指标
        Map<String, List<BigDecimal>> listInduMap =  buildInduIndicator(listIss.get(0), null, userId, 0);
        for (IssIndicatorsDoc issIndicator : listIss) {
            //主体最新季度指标
            BigDecimal issVal = issIndicator.getIndicators().get(0);
            //指标名称
            String indicator = issIndicator.getFieldName();
            String field = issIndicator.getField();
            List<BigDecimal> listInd = listInduMap.get(issIndicator.getField());
            //公司的所在分位值
            Integer quantile = SafeUtils.getQuantileValue(listInd, issVal, issIndicator.getNegative());
            quantiles.add(new ComQuantileInfoIndicatorItemVo(indicator, field, quantile));
        }
        result.setQuantiles(quantiles);
        return result;
    }

    
    
    
    
    private Integer getQuantileOffSet(BigDecimal issVal, List<BigDecimal> induValVes, Map<Double, Integer> quantileMap,
            Integer quantile) {
        int[] induValVesStr = {1, 10, 25, 50, 75, 90, 100};
        if(issVal != null){
            for (int i=0 ; i< induValVes.size() - 1 ;i++) {
                double v1 = induValVes.get(i).doubleValue();
                double v2 = induValVes.get(i + 1).doubleValue();
                int k1 = induValVesStr[i];
                int k2 = induValVesStr[i + 1];
                if(quantileMap.containsKey(issVal.doubleValue())){
                    quantile = quantileMap.get(issVal.doubleValue());
                    break;
                }else{
                    if(v1 > v2){
                        double tem = v1;
                        v1 = v2;
                        v2 =tem;
                    }
                    if(issVal.doubleValue() > v1 && issVal.doubleValue() < v2 ){
                        if(!(quantile > k1 && quantile < k2)){
                            quantile = k1 + (k2-k1)/2;
                            break;
                        }
                    }
                }
            }
        }
        return quantile;
    }

   
    /**
     * 违约概率分析-本期重点财务指标与行业比较
     * @param compId
     * @param provinceIds
     * @param userId
     * @param year
     * @param quarter
     * @return
     */
    public BondFinanceInfoInduDoc findBondFinanceInfoInduDoc(Long compId, List<Long> provinceIds, Long userId, Integer year, Integer quarter) {
        List<String> columns = getFields(compId);
        Query query =  new Query(Criteria.where("issId").is(compId));
       
        //主体所有财务指标
        IssKeyIndicatorDoc indicatorDoc = mongoTemplate.findOne(query, IssKeyIndicatorDoc.class);
        List<IssIndicatorsDoc> listIss =   IssKeyIndicatorsToIssIndicatorsDocs(indicatorDoc);//mongoTemplate.find(query, IssIndicatorsDoc.class);
        if(listIss == null || listIss.size() == 0){
            return new BondFinanceInfoInduDoc();
        }
        //过滤指标
        listIss = filterIndicatorFields(listIss, columns);
        //主体行业所有财务指标
        Map<String, List<BigDecimal>> listInduMap = null;
        Integer indexToView = year == null || quarter == null ? 0 : analysisService.getQuarterIndex(year, quarter, listIss.get(0).getQuarters());
        if(provinceIds == null || provinceIds.size() == 0){
            listInduMap = buildInduIndicator(listIss.get(0), null, userId, indexToView);
        }else{
            listInduMap = buildInduIndicator(listIss.get(0), provinceIds, userId, indexToView);
        }
        
        //最新季度
        String[] quarters = SafeUtils.getQuarter(listIss.get(0).getLastFinDate(), 1);
        
        //行业最新指标
        List<FinanceInfoIndu> fiiList = new ArrayList<>();
        for (IssIndicatorsDoc issIndicator : listIss) {
            List<BigDecimal> indicatorList = listInduMap.get(issIndicator.getField());
            BigDecimal issin = issIndicator.getIndicators().get(0);//主体最新季度指标
            BigDecimal issVal = issin ;//公司水平
            BigDecimal induVal = BigDecimal.ZERO;//行业水平
            if(indicatorList == null){
                FinanceInfoIndu  fii = new FinanceInfoIndu(issIndicator.getFieldName(), null, null ,issIndicator.getNegative());
                fiiList.add(fii);
                continue;
            }
            BigDecimal induin10 = SafeUtils.getQuantile(indicatorList, 10,issIndicator.getNegative());//行业10分位
            BigDecimal induin50 = SafeUtils.getQuantile(indicatorList, 50,issIndicator.getNegative());//行业50分位
            BigDecimal induin90 = SafeUtils.getQuantile(indicatorList, 90,issIndicator.getNegative());//行业90分位
            // 行业水平=（50分位-10分位）/（90分位-10分位）
            // 公司水平=（公司数值-10分位）/（90分位-10分位）
            BigDecimal divisor = induin90.subtract(induin10);
            if(issVal != null){
                if(divisor.doubleValue() != 0 && induin10 != null){
                    issVal = issVal.subtract(induin10).divide((divisor), 2);
                }
                if(issVal.doubleValue() > 1){
                    issVal = new BigDecimal(1);
                }
                if(issVal.doubleValue() < 0){
                    issVal = new BigDecimal(0);
                }
            }else{
                issVal = new BigDecimal(0);
            }
            //计算行业水平
            if(induin50.doubleValue() != 0 && divisor.doubleValue() != 0){
                induVal = induin50.subtract(induin10).divide((divisor), 2);
            }
            
            FinanceInfoIndu  fii = new FinanceInfoIndu(issIndicator.getFieldName(), issVal, induVal ,issIndicator.getNegative());
            fiiList.add(fii);
        }
        return new BondFinanceInfoInduDoc(compId, quarters[0], fiiList);
        
      //  return bondFinanceInfoInduRepository.findByCompId(compId);
    }

  
    /**
     * 违约概率分析-近2期重点财务指标对比
     * @param compId
     * @param userId
     * @return
     */
    public BondFinanceInfoQuarterDoc findBondFinanceInfoQuarterDoc(Long compId, Long userId) {
        List<String> columns = getFields(compId);
        //主体所有财务指标
        Query query =  new Query(Criteria.where("issId").is(compId));
        IssKeyIndicatorDoc indicatorDoc = mongoTemplate.findOne(query, IssKeyIndicatorDoc.class);
        List<IssIndicatorsDoc> listIss =  IssKeyIndicatorsToIssIndicatorsDocs(indicatorDoc,6);//mongoTemplate.find(query, IssIndicatorsDoc.class);
        if(listIss == null || listIss.size() == 0){
            return new BondFinanceInfoQuarterDoc();
        }
        //过滤指标
        listIss = filterIndicatorFields(listIss, columns);
        
        //主体所有行业财务指标
        Integer indexToView = 0;
        String orgType = listIss.get(0).getOrgType();
        Map<String, List<BigDecimal>> listInduMap = buildInduIndicator(listIss.get(0), null, userId, indexToView);
        
        //最新2季度
        List<String> quarters = indicatorDoc.getQuarters();
        String currentTime = quarters.get(0),previousTime =   quarters.get(1);
        //行业最新指标
        List<FinanceInfoQuarter> indicatorQuarter = new ArrayList<>();
        for (IssIndicatorsDoc issIndicator : listIss) {
            List<BigDecimal> indicatorList = listInduMap.get(issIndicator.getField());
            String indicator = issIndicator.getFieldName();
            String code = issIndicator.getField();
            int type = issIndicator.getType();
            int isNegative = issIndicator.getNegative();
            if(indicatorList == null){
                indicatorQuarter.add(new FinanceInfoQuarter(indicator, code,null, null, null,null, type, isNegative, null, 0));
                continue;
            }
            BigDecimal induin10 = SafeUtils.getQuantile(indicatorList, 10, issIndicator.getNegative());//行业10分位
            BigDecimal induin50 = SafeUtils.getQuantile(indicatorList, 50, issIndicator.getNegative());//行业50分位
            BigDecimal induin90 = SafeUtils.getQuantile(indicatorList, 90, issIndicator.getNegative());//行业90分位
            //BigDecimal issVal = issin.subtract(induin10).divide((induin90.subtract(induin10)));//公司水平
            BigDecimal current = issIndicator.getIndicators().get(0);//最新季度
            //current = current == null ? BigDecimal.ZERO : current;
            BigDecimal previous = issIndicator.getIndicators().get(1);//上季度
            //previous = previous == null ? BigDecimal.ZERO : previous;
            BigDecimal divisor = (induin90.subtract(induin10));//除数因子
            
            BigDecimal currentVal = BigDecimal.ONE, previousVal = BigDecimal.ONE,currentInduVal = BigDecimal.ONE;
            if(current != null && divisor.doubleValue()!= 0){
                //最新季度分位值
                currentVal = current.subtract(induin10).divide(divisor, 2);
                currentVal = currentVal.doubleValue() <0 ? BigDecimal.ZERO :currentVal;
                
            }
            if(divisor.doubleValue()!= 0){
                //行业最新季度分位值
                currentInduVal = induin50.subtract(induin10).divide(divisor, 2);
                currentInduVal = currentInduVal.doubleValue() <0 ? BigDecimal.ZERO :currentInduVal;
                
            }
            if(previous !=null && divisor.doubleValue()!= 0){
              //上季度分位值
                previousVal = previous.subtract(induin10).divide(divisor, 2);
                previousVal = previousVal.doubleValue() <0 ? BigDecimal.ZERO :previousVal;
            }
            
            int percent = issIndicator.getPercent();
            current = NumberUtils.KeepTwoDecimal(current, percent);
            previous = NumberUtils.KeepTwoDecimal(previous, percent);
            indicatorQuarter.add(new FinanceInfoQuarter(indicator, code,currentVal, previousVal, current == null ? null : current.toString(),previous ==null ? null : previous.toString(), type, isNegative, currentInduVal, percent));
        }
        return new BondFinanceInfoQuarterDoc(compId, orgType, currentTime, previousTime, indicatorQuarter);
        //return bondFinanceInfoQuarterRepository.findByCompId(compId);
    }

    
    /**
     * 行业分析-行业评级变动概览
     *  @param userId
     *            用户id
     *            
     *  @param comUinCode
     *            主体id
     */
  /*  public BondInduAnalyseDoc findBondInduAnalyseV2(Long induId, Long userId, Long comUinCode) {
        
        
        List<InduAnalyse> induaList = new ArrayList<>();
       
       // String induField = bondInduService.isGicsInduClass(userId) ? "induId" : "induIdSw";
        
        List<Long> comUniCodes = bondInduService.findComUniCodeByIndu(comUinCode, userId);
        
        
        long issTotal = mongoTemplate.count(new Query(Criteria.where("issId").gt(0).and("issId").in(comUniCodes).and("currStatus").is(1)), RatingIssBondDoc.class);
        
        List<RatingIssBondDoc> listR = mongoTemplate.find(new Query(Criteria.where("issId").gt(0).and("issId").in(comUniCodes).and("currStatus").is(1)), RatingIssBondDoc.class);
        int thisMR = 0,lastMR = 0,thisMRBond = 0, lastMRBond = 0, thisMP = 0, lastMP = 0,thisMPBond = 0,lastMPBond = 0;
        for (RatingIssBondDoc ratingIss : listR) {
            List<Integer> issRating90 = ratingIss.getIssRating90d();
            List<Integer> issRating180 = ratingIss.getIssRating180d();
            List<Integer> issRatingPar90 = ratingIss.getIssRatingPar90d();
            List<Integer> issRatingPar180 = ratingIss.getIssRatingPar180d();
            
            List<Integer> bondRating90 = ratingIss.getBondRating90d();
            List<Integer> bondRating180 = ratingIss.getBondRating180d();
            List<Integer> bondRatingPar90 = ratingIss.getBondRatingPar90d();
            List<Integer> bondRatingPar180 = ratingIss.getBondRatingPar180d();
            //主体评级
            if(issRating90.size() ==2 && issRating90.get(0) != null && issRating90.get(1) != null && issRating90.get(0) > issRating90.get(1)){
                thisMR ++;
            }
            if(issRating180.size() ==2 && issRating180.get(0) != null && issRating180.get(1) != null && issRating180.get(0) > issRating180.get(1)){
                lastMR ++;
            }
            //债项评级
            if(bondRating90.size() == 2 && bondRating90.get(0) != null && bondRating90.get(1) != null && bondRating90.get(0) > bondRating90.get(1)){
                thisMRBond ++;
            }
            if(bondRating180.size() == 2 && bondRating180.get(0) != null && bondRating180.get(1) != null && bondRating180.get(0) > bondRating180.get(1)){
                lastMRBond ++;
            }
            //主体展望
            if(issRatingPar90.size() > 0 && issRatingPar90.get(0) == 4){
                thisMP ++;
            }
            if(issRatingPar180.size() > 0 && issRatingPar180.get(0) == 4){
                lastMP ++;
            }
            //债项展望
            if(bondRatingPar90.size() >0 && bondRatingPar90.get(0) == 4){
                thisMPBond ++;
            }
            if(bondRatingPar180.size() > 0 && bondRatingPar180.get(0) == 4){
                lastMPBond ++;
            }
        }
        
        InduAnalyse issR = new InduAnalyse("主体评级", thisMR, lastMR);
        InduAnalyse bondR = new InduAnalyse("债项评级", thisMRBond, lastMRBond);
        InduAnalyse issRP = new InduAnalyse("主体展望", thisMP, lastMP);
        InduAnalyse bondRP = new InduAnalyse("债项展望", thisMPBond, lastMPBond);
        induaList.add(issR);
        induaList.add(bondR);
        induaList.add(issRP);
        induaList.add(bondRP);
        
        return new BondInduAnalyseDoc(induId, issTotal, induaList);
       // return bondInduAnalyseRepository.findByCompId(induId);
    }*/
    
    
    
    

    /**
     * 行业分析-同行业违约概率增加
     * 
     * @param induId
     * @param page
     * @param size
     * @param userId
     * @return
     */
    /*public Page<InduBreachProbabilityUpDoc> findInduBreachProbability(Long induId, int page, int size, Long userId) {
    	String induField = bondInduService.isGicsInduClass(userId) ? "this.induId" : "this.induIdSw";
        BasicQuery bq = new BasicQuery("{$where:\"function(){ if(this.pdQ.twoPd > 0 && (this.pdQ.onePd > this.pdQ.twoPd) && " + induField + "==  "+ induId +" && this.currStatus == 1){return true}}\"}");
        //分页处理
        long total = mongoTemplate.count(bq, IssPdDoc.class); 
        bq.skip((page - 1)*size).limit(size);
        List<IssPdDoc> list =  mongoTemplate.find(bq, IssPdDoc.class);
        List<InduBreachProbabilityUpDoc> listUP = new ArrayList<>();
        for (IssPdDoc issPd : list) {
            BigDecimal pd = new BigDecimal(issPd.getPdQ().getOnePd());//本期
            BigDecimal lastPd = new BigDecimal(issPd.getPdQ().getTwoPd());//上期
//            BigDecimal inc = pd.subtract(lastPd);//较上期增加
            String issName = issPd.getIssName();//主体名
            InduBreachProbabilityUpDoc up = new InduBreachProbabilityUpDoc(induId, issName, pd, lastPd);
            listUP.add(up);
        }
        Page<InduBreachProbabilityUpDoc> pagition = new PageImpl<>(listUP, new PageRequest(page -1, size), total);
        return pagition;
    }*/

   
   
    /**
     * 行业分析-同行业主体评级下降
     * @param induIds
     * @param page
     * @param size
     * @param userId
     * @return
     */
    /*public Page<InduCredRatingVo> findInduRatingDown(List<Long> induIds, int page, int size, Long userId) {
    	String induField = bondInduService.isGicsInduClass(userId) ? "induId" : "induIdSw";
        BasicQuery bq = new BasicQuery("{$where:\"function(){ if(this.issRating90d[1] > 0 && (this.issRating90d[0] > this.issRating90d[1]) && this.currStatus == 1 ){return true}}\"}");
        bq.addCriteria(Criteria.where(induField).in(induIds));
        long total = mongoTemplate.count(bq, RatingIssBondDoc.class);
        //分页处理
        bq.skip((page - 1)*size).limit(size);
        List<RatingIssBondDoc> list =  mongoTemplate.find(bq, RatingIssBondDoc.class);
        List<InduCredRatingVo> listR = new ArrayList<>();
        for (RatingIssBondDoc ratingIssBondDoc : list) {
            listR.add(new InduCredRatingVo(ratingIssBondDoc));
        }
        return new  PageImpl<>(listR, new PageRequest(page -1, size), total);
    }*/
    
    /**
     * 行业分析-同行业主体展望负面
     * @param induIds
     * @param page
     * @param size
     * @param userId
     * @return
     */
   /* public Page<InduCredRatingVo> findInduPor(List<Long> induIds, int page, int size, Long userId) {
    	
    	String induField = bondInduService.isGicsInduClass(userId) ? "induId" : "induIdSw";
        Query bq = new Query(Criteria.where("currStatus").is(1).and("issRatingPar90d.0").is(4));
        bq.addCriteria(Criteria.where(induField).in(induIds));
        long total = mongoTemplate.count(bq, RatingIssBondDoc.class);
        //分页处理
        bq.skip((page - 1)*size).limit(size);
        List<RatingIssBondDoc> list =  mongoTemplate.find(bq, RatingIssBondDoc.class);
        List<InduCredRatingVo> listR = new ArrayList<>();
        for (RatingIssBondDoc ratingIssBondDoc : list) {
            listR.add(new InduCredRatingVo(ratingIssBondDoc));
        }
        return new  PageImpl<>(listR, new PageRequest(page -1, size), total);
    }*/
    
    /**
     * 行业分析-同行业主体展望负面
     * @param induId
     * @param page
     * @param size
     * @param userId
     * @return
     */
    /*public Page<InduCredRatingVo> findInduPor(Long induId, int page, int size, Long userId) {
    	String induField = bondInduService.isGicsInduClass(userId) ? "induId" : "induIdSw";
        Query bq = new Query(Criteria.where("currStatus").is(1).and("issRatingPar90d.0").is(4));
        bq.addCriteria(Criteria.where(induField).is(induId));
        long total = mongoTemplate.count(bq, RatingIssBondDoc.class);
        //分页处理
        bq.skip((page - 1)*size).limit(size);
        List<RatingIssBondDoc> list =  mongoTemplate.find(bq, RatingIssBondDoc.class);
        List<InduCredRatingVo> listR = new ArrayList<>();
        for (RatingIssBondDoc ratingIssBondDoc : list) {
            listR.add(new InduCredRatingVo(ratingIssBondDoc));
        }
        return new  PageImpl<>(listR, new PageRequest(page -1, size), total);
    }*/

    /**
     * 行业分析-行业主体评级分布对比
     * 
     * @param issInduId
     *            基准行业id
     * @param compareInduId
     *            对比行业id
     * @param userId
     */
    /*public List<IssInduRatingCompareVo> findIssInduRatingCompare(List<Long> issInduId, List<Long> compareInduId, Long userId) {
    	String induField = bondInduService.isGicsInduClass(userId) ? "induId" : "induIdSw";
        List<IssInduRatingCompareVo> iircList = new ArrayList<>();
        // 信誉评级 AAA:1,AA+:2,AA:3,AA-:4,A+:5,A:6
        Integer cerds[] = { 1, 2, 3, 4, 5, 6 };

        for (Integer cred : cerds) {
            // 基准本期数量
            Query queryIss = new Query(Criteria.where("issRatingPar90d.0").is(cred));
            if(issInduId.size() > 0){
                queryIss.addCriteria(Criteria.where(induField).in(issInduId));
            }
            long issCount = mongoTemplate.count(queryIss, RatingIssBondDoc.class);
                
            // 对比数量
            Query queryIndu = new Query(Criteria.where("issRatingPar90d.0").is(cred));
            if(compareInduId.size() > 0){
                queryIndu.addCriteria(Criteria.where(induField).in(compareInduId));
            }
            long comCount = mongoTemplate.count(queryIndu, RatingIssBondDoc.class);
           
            IssInduRatingCompareVo iirc = new IssInduRatingCompareVo(cred, issCount, comCount);
            iircList.add(iirc);
        }
        return iircList;
    }*/

    
   
    
    /**
     * 行业分析-行业主体违约概率分布对比
     * @param issInduId
     * @param compareInduId
     * @return
     */
   /* public List<PdInduCompareVo> findInduPdStat(List<Long> induIds, List<Long> compareInduIds, Long userId){
    	String induField = bondInduService.isGicsInduClass(userId) ? "induId" : "induIdSw";
        //当前行业
        Map<String,Integer> current = getPdIssNum(induIds, induField);
        //对比行业
        Map<String,Integer>  compare = getPdIssNum(compareInduIds, induField);
        List<PdInduCompareVo> result = new ArrayList<>();
        for (Entry<String,Integer> entry : compare.entrySet()) {
            PdInduCompareVo pdVo = new PdInduCompareVo();
            String key = entry.getKey();
            pdVo.setCompIssNum(compare.get(key));
            pdVo.setOwnIssNum(current.get(key));
            pdVo.setRating(key);
            result.add(pdVo);
        }
        return result;
    }*/

   

    /**
     * 
     * @param induIds
     * @param induField
     * @return
     */
    public Map<String,Integer> getPdIssNum(List<Long> induIds, String induField){
        Query query = new Query(Criteria.where(induField).in(induIds).and("currStatus").is(1));
        List<PdMappingVo> listPdMap = pdMappingService.getPdMapping();
        Map<BigDecimal,String> map = new HashMap<>();
        Map<String,Integer> mapLink = new LinkedHashMap<>();
        for (PdMappingVo pdMappingVo : listPdMap) {
            map.put(pdMappingVo.getPd(), pdMappingVo.getRating());
            mapLink.put(pdMappingVo.getRating(), 0);
        }
        List<IssPdDoc> list = mongoTemplate.find(query, IssPdDoc.class);
        for (IssPdDoc issPdDoc : list) {
            List<Double> pds = issPdDoc.getPds();
            if(pds!=null && pds.size() > 0){
                if(pds.get(0) != null){
                    String key = map.get(new BigDecimal(pds.get(0)).setScale(4, BigDecimal.ROUND_HALF_UP));
                    int count = mapLink.get(key);
                    count++;
                    mapLink.put(key, count);
                }
            }
        }
        return mapLink;
    }
    

    /**
     * 获取主体指标的分位值
     * @param comUniCode
     * @param finDate
     * @param fields 多个指标用,号隔开
     * @param indicatorType 
     * @return
     * @throws Exception 
     */
    public Map<String,Integer> quartileMap(Long comUniCode, Date finDate, List<String> fields, Integer indicatorType) throws Exception{
    	BondCom bondCom = amaBondDao.findIssMap(comUniCode);
    	if(bondCom == null){
    		throw new BusinessException( "主体 [" +  comUniCode + "]不存在");
    	}
    	//行业主体指标
    	Map<String,List<BigDecimal>> indicators = getInduIndicators(bondCom, finDate, fields, indicatorType);
    	 //当前主体指标
    	Map<String,Object> comIndicator = finaSheetDao.findByCompIdAndFinDate(bondCom.getAmsIssId(), finDate, fields, indicatorType);
    	//算出各个指标的行业分位
    	Map<String,Integer> result = new HashMap<>();
    	comIndicator.forEach((field,value)->{
    		if(value != null){
    			Integer q = SafeUtils.getQuantileValue(indicators.get(field), (BigDecimal)value, SafeUtils.QUANTILE_ASC);
    			result.put(field, q);
    		}
    	});
    	return result;
    }
    
    /**
     * 获取行业指标
     * @param induId
     * @param finDate
     * @param fields
     * @param indicatorType
     * @return
     */
    @NoLogging
    public Map<String,List<BigDecimal>> getInduIndicators(BondCom bondCom, Date finDate, Collection<String> fields, Integer indicatorType){
    	
    	List<Map<String,Object>> indicators = finaSheetDao.findIndicatorsByInduSwAndFinDate(bondCom, finDate, fields, indicatorType);
    	//整理指标
    	Map<String,List<BigDecimal>> result = new HashMap<>();
    	indicators.forEach(indicator -> {
    		indicator.forEach((field, value) ->{
    			BigDecimal indicatorValue = value == null ? null : (BigDecimal)value;
    			if(result.get(field) == null){
    				List<BigDecimal> values = new ArrayList<>();
    				values.add(indicatorValue);
    				result.put(field, values);
    			}else{
    				result.get(field).add(indicatorValue);
    			}
    		});
    	});
    	return result;
    }
    
    /**
     * 所属行业所有信息
     * @param induId 
     * @return
     */
    private Map<String, List<BigDecimal>> buildInduIndicator(IssIndicatorsDoc indicatorArg , List<Long> provinceIds ,Long userId, Integer indexToView) {
    	
    	//符合要求的主体id集合
    	//Set<Long> issIds = getMatchProvinceInduIssuers(indicatorArg, provinceIds, userId);
    	//查找指标 主体
    	Query query = new Query();
    	if(provinceIds != null){  
    		query.addCriteria(Criteria.where("provinceId").in(provinceIds));
    	}
    	List<Long> comUniCodes = bondInduService.findComUniCodeByIndu(indicatorArg.getIssId(), userId);
    	query.addCriteria(Criteria.where("issId").in(comUniCodes));
    	
//    	if(bondInduService.isGicsInduClass(userId)){
//    		query.addCriteria(Criteria.where("induId").is(indicatorArg.getInduId()));
//    	}else{
//    		query.addCriteria(Criteria.where("induIdSw").is(indicatorArg.getInduIdSw()));
//    	}
    	
    	
        List<IssKeyIndicatorDoc> listIssKeyIndicatorDoc = mongoTemplate.find(query, IssKeyIndicatorDoc.class);
        
        List<IssIndicatorsDoc> listIndicators = IssKeyIndicatorsToIssIndicatorsDocsList(listIssKeyIndicatorDoc);//mongoTemplate.find(queryIndu, IssIndicatorsDoc.class);
        listIndicators = BondIndicatorAdapter.syncInduToIss(indicatorArg, listIndicators);
        // 行业分组
        Map<String, List<BigDecimal>> listMap = groupInduIndicators(indexToView, listIndicators);
      
        return listMap;
    }

    /**
     * 行业分组
     * @param indexToView
     * @param listIndicators
     * @return
     */
	public Map<String, List<BigDecimal>> groupInduIndicators(Integer indexToView,
			List<IssIndicatorsDoc> listIndicators) {
		Map<String,Set<BigDecimal>> listInduMap = new HashMap<>();
        Map<String,List<BigDecimal>> listMap= new HashMap<>();
        for (IssIndicatorsDoc IssIndicatorsDoc : listIndicators) {
            String key = IssIndicatorsDoc.getField();
            BigDecimal indicator = null;
            if(IssIndicatorsDoc.getIndicators().size() > indexToView && indexToView > -1){
            	indicator = IssIndicatorsDoc.getIndicators().get(indexToView);
            }
            //排除null
            if(listInduMap.get(key) == null){
            	Set<BigDecimal> listIn = new HashSet<>();
            	listIn.add(indicator);
            	listInduMap.put(key, listIn );
            }else{
            	listInduMap.get(key).add(indicator);
            }
        }
        for (Entry<String,Set<BigDecimal>> entry : listInduMap.entrySet()) {
            List<BigDecimal> inlist = new ArrayList<>();
            Iterator<BigDecimal> iterator = entry.getValue().iterator();
            while(iterator.hasNext()){
                inlist.add(iterator.next());
            }
            listMap.put(entry.getKey(), inlist);
        }
		return listMap;
	}

    /**
     * 匹配到行业和省的发行人
     * @param indicatorArg
     * @param provinceIds
     * @param userId
     * @return
     */
	/*public Set<Long> getMatchProvinceInduIssuers(IssIndicatorsDoc indicatorArg, List<Long> provinceIds, Long userId) {
		Set<Long> issIds = getMatchIssIds(indicatorArg, userId);
    	Set<Long> issIdsProvince = new HashSet<>();
    	//地区主体映射
    	if(provinceIds != null){
    		List<IssProvinceMapDoc> listIssProvinceMapDoc = mongoTemplate.find(new Query(Criteria.where("provinceId").in(provinceIds)), IssProvinceMapDoc.class);
    		if(listIssProvinceMapDoc.size() > 0){
    			for (IssProvinceMapDoc issProvinceMapDoc : listIssProvinceMapDoc) {
    				issIdsProvince.addAll(issProvinceMapDoc.getIssIds());
    			}
    		}
    		issIds.retainAll(issIdsProvince);
    	}
		return issIds;
	}*/

    /**
     * 获取匹配的发行人id
     * @param induId
     * @return
     */
	/*private Set<Long> getMatchIssIds(IssIndicatorsDoc indicator, Long userId) {
		//行业主体映射
    	Set<Long> issIds = new HashSet<>();
    	if(bondInduService.isGicsInduClass(userId)){
    		IssInduMapDoc issInduMapDoc = mongoTemplate.findOne(new Query(Criteria.where("induId").is(indicator.getInduId())), IssInduMapDoc.class);
    		if(issInduMapDoc != null)
    			issIds.addAll(issInduMapDoc.getIssIds());
    	}else{
    		IssInduMapSwDoc issInduMapSwDoc = mongoTemplate.findOne(new Query(Criteria.where("induIdSw").is(indicator.getInduIdSw())), IssInduMapSwDoc.class);
    		if(issInduMapSwDoc != null)
    			issIds.addAll(issInduMapSwDoc.getIssIds());
    	}
		return issIds;
	}
    */
    
   
    /**
     * 获取财务指标分组
     * @return
     */
    public List<IndicatorGroupVo> findIndicatorCategory(){
        List<IssIndicatorFieldGroupMapping> list = mongoTemplate.find(new Query(), IssIndicatorFieldGroupMapping.class);
        
        List<IndicatorGroupVo> result = groupIndicatorField(list);
        return result;
    }

    private List<IndicatorGroupVo> groupIndicatorField(List<IssIndicatorFieldGroupMapping> list) {
        Map<String,List<IndicatorVo>> map = new HashMap<>();
        for (IssIndicatorFieldGroupMapping groupMap : list) {
            String key = groupMap.getColumnName();
            String value = groupMap.getFieldName();
            int isNegative = 0;
            if(groupMap.getFieldType() == 2 || groupMap.getFieldType() ==4){
                isNegative = 1;
            }
            String category = groupMap.getGroupName();
            if(map.get(category) == null){
                List<IndicatorVo> indicators = new ArrayList<>();
                indicators.add(new IndicatorVo(key, value, isNegative));
                map.put(category, indicators);
            }else{
                map.get(category).add(new IndicatorVo(key, value, isNegative));
            }
        }
        
        List<IndicatorGroupVo> result = new ArrayList<>();
        
        for (Entry<String,List<IndicatorVo>> entry : map.entrySet()) {
            result.add(new IndicatorGroupVo(entry.getKey(), entry.getValue()));
        }
        return result;
    }
    
    /**
     * 债券财务指标比对
     * @param bondCods
     * @return
     */
    public List<BondIssIndicatorGroupVo> bondCompare(Set<Long> bondCods){
        Set<Long> issIds = new HashSet<>();
        //所有bond的发行人id集合
        List<BondDetailDoc> listBond =  mongoTemplate.find(new Query(Criteria.where("_id").in(bondCods)), BondDetailDoc.class);
        for (BondDetailDoc bond : listBond) {
            if(bond.getComUniCode() != null){
                issIds.add(bond.getComUniCode());
            }
        }
        List<BondIssIndicatorGroupVo> result = new ArrayList<>();
        String issCategory[] = {"bank","indu","insu","secu"};
        for (int i = 0; i < issCategory.length; i++) {
            //发行人财务指标
            Query query = new Query(Criteria.where("issId").in(issIds).and("orgType").is(issCategory[i]));
            IssKeyIndicatorDoc indicatorDoc = mongoTemplate.findOne(query, IssKeyIndicatorDoc.class);
            List<IssIndicatorsDoc> list = IssKeyIndicatorsToIssIndicatorsDocs(indicatorDoc); //mongoTemplate.find(query, IssIndicatorsDoc.class);
            Map<Long,List<IssIndicatorVo>> mapIndicator = new HashMap<>();
            for (IssIndicatorsDoc indicator : list) {
                IssIndicatorVo iiv = new IssIndicatorVo(indicator.getFieldName(), indicator.getIndicators().get(0), indicator.getNegative());
                if(mapIndicator.get(indicator.getIssId()) == null){
                    List<IssIndicatorVo> listIn = new ArrayList<>();
                    listIn.add(iiv);
                    mapIndicator.put(indicator.getIssId(), listIn);
                }else{
                    mapIndicator.get(indicator.getIssId()).add(iiv);
                }
            }
            //比较集合
            List <BondIssIndicatorVo> biiv = new ArrayList<>();
            for (BondDetailDoc bond : listBond) {
                List<IssIndicatorVo> listii =  mapIndicator.get(bond.getComUniCode());
                if(listii != null){
                    String issName = bond.getComName();
                    if(issName == null && bond.getComUniCode() != null){
                        BondComInfoDoc comInfo =  mongoTemplate.findOne(new Query(Criteria.where("_id").is(bond.getComUniCode())), BondComInfoDoc.class);
                        issName = comInfo != null ? comInfo.getComChiName() : null;
                    }
                    biiv.add(new BondIssIndicatorVo(bond.getName(), issName, bond.getComUniCode(), listii) );
                }
            }
            if(biiv.size() > 0 ){
                result.add(new BondIssIndicatorGroupVo(issCategory[i], biiv));
            }
            
        }
        
        
        return result;
    }
    
   
    /**
     * 信用债-债券详情-重点信息-板块6
     * @param issId
     * @param userId
     * @return
     */
    public  List<FinanceInfoQuarter>  findFinanceInfoQuarters(Long issId, Long userId){
       
        BondFinanceInfoQuarterDoc bfi = findBondFinanceInfoQuarterDoc(issId, userId);
        if(bfi == null || bfi.getCompId() ==null){
            return new ArrayList<>();
        }
        Map<String,String[]> orgTypeFileMaping =  new HashMap<>();
        //总资产、存款余额、净利润增长率、资本收益率
        String[]  bank = {"bank_ratio1","bank_ratio3","bank_ratio17","bank_ratio25"};
        //总资产、营业收入净额、毛利润、总资产周转率(次)
        String[]  indu = {"Tot_Asst","Turnover","Grss_Mrgn","Tot_Asst_Turnvr"};
        //资产总额、资产收益率、 净利润、权益性资产/净资产
        String[]  secu = {"secu_ratio1","secu_ratio38","secu_ratio8","secu_ratio53"};
        //总资产、已赚保费收入、总投资收益、赔付率
        String[]  insu = {"insu_ratio1","insu_ratio3","insu_ratio4","insu_ratio26"};
        orgTypeFileMaping.put("bank", bank);
        orgTypeFileMaping.put("indu", indu);
        orgTypeFileMaping.put("secu", secu);
        orgTypeFileMaping.put("insu", insu);
        
        List<FinanceInfoQuarter> indicators =  bfi.getIndicatorQuarter();
        //临时将指标存为map
        Map<String,FinanceInfoQuarter> quartermap = new HashMap<>();
        for (FinanceInfoQuarter quarter : indicators) {
            quartermap.put(quarter.getCode(), quarter);
        }
        //找出对应行业需要的指标数据
        List<FinanceInfoQuarter> result = new ArrayList<>();
        String[] toViewIndicator = orgTypeFileMaping.get(bfi.getOrgType());
        for (String code : toViewIndicator) {
            result.add(quartermap.get(code));
        }
        
        return result;
    }
    
    
    public List<IndicatorGroupVo> findIndicatorCategory(Long issId){
        String tableName = getOrgTable(issId);
        Query queryField = new Query(Criteria.where("tableName").is(tableName));
        List<IssIndicatorFieldGroupMapping> list = mongoTemplate.find(queryField, IssIndicatorFieldGroupMapping.class);
        return groupIndicatorField(list);
    }

    /**
     * 获取orgTable类型
     * @param issId
     * @return
     */
    private String getOrgTable(Long issId) {
        Query query = new Query(Criteria.where("issId").is(issId));
        IssKeyIndicatorDoc iid = mongoTemplate.findOne(query, IssKeyIndicatorDoc.class);//mongoTemplate.findOne(query, IssIndicatorsDoc.class);
        if(iid == null){
            return "";
        }
        String orgType = iid.getOrgType();
        Map<String,String> orgTableMap = new HashMap<String,String>();
        orgTableMap.put("bank", "dm_analysis_bank");
        orgTableMap.put("indu", "dm_analysis_indu");
        orgTableMap.put("insu", "dm_analysis_insu");
        orgTableMap.put("secu", "dm_analysis_secu");
        String tableName = orgTableMap.get(orgType);
        return tableName;
    }
    
    /**
     * findIndicatorCategoryDistr
     * @param issId
     * @return
     */
    public List<IndicatorGroupVo> findIndicatorCategoryDistr(Long issId){
        List<IndicatorGroupVo>  group = findIndicatorCategory(issId);
        if(!(group == null  || group.size() == 0)){
            List<String> columns = getFields(issId);
            getViewFields(columns, group);
        }
        return group;
    }

    private List<String> getFields(Long issId) {
        List<String> columns = new ArrayList<>();
        String tableName = getOrgTable(issId);
        String className = null;
        if("dm_analysis_bank".equals(tableName)){
            className = "BondAnalysisBank";
        }else if("dm_analysis_indu".equals(tableName)){
            className = "BondAnalysisIndu";
        }
        else if("dm_analysis_insu".equals(tableName)){
            className = "BondAnalysisInsu";
        }else{
            className = "BondAnalysisSecu";
        }
        try {
            Field[] fields =  Class.forName("com.innodealing.model.dm.bond." + className).getDeclaredFields() ;
            for (Field field : fields) {
                ApiModelProperty property = field.getAnnotation(ApiModelProperty.class);
                if(property != null){
                    columns.add(field.getName());
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return columns;
    }
     
    
    /**
     * 主体分析-违约概率分析-重点风险指标揭示
     * @param issId
     * @return
     */
    public  List<IssIndicatorsVo>  findIssIndicators(Long issId){
        String category = amaBondService.findIssCategory(issId);
        if(category == null){
            return new ArrayList<IssIndicatorsVo>();
        }
        String categoryCode ="";
        Map<String,String> categoryMapping = new HashMap<String, String>();
        categoryMapping.put("原材料", "indu");
        categoryMapping.put("电信业务与信息技术", "indu");
        categoryMapping.put("工业", "indu");
        categoryMapping.put("公用事业", "indu");
        categoryMapping.put("医疗保健", "indu");
        categoryMapping.put("能源", "indu");
        categoryMapping.put("消费类", "busi");
        categoryMapping.put("城投类", "urban");
        categoryMapping.put("投资银行", "secu");
        categoryMapping.put("商业银行", "bank");
        categoryMapping.put("房地产开发", "house");
        categoryMapping.put("保险类", "insu");
        categoryCode = categoryMapping.get(category);
        if(categoryCode == null){
            return new ArrayList<IssIndicatorsVo>();
        }
        Map<String,String[]> map = new HashMap<>();
        //商业银行
        String[] bank = {"bank_ratio2","bank_ratio43","bank_ratio34","bank_ratio156","bank_ratio157","bank_ratio158","bank_ratio47","bank_ratio65","bank_ratio68"};
        //证券公司
        String[] secu = {"secu_ratio1","secu_ratio39","secu_ratio67","secu_ratio69","secu_ratio68","secu_ratio51","secu_ratio71","secu_ratio70","secu_ratio72"};
        //保险类公司
        String[] insu = {"insu_ratio2","insu_ratio29","insu_ratio25","insu_ratio26","insu_ratio28","insu_ratio16","insu_ratio24","insu_ratio12","insu_ratio7"};
        //城投公司
        String[] urban = {"INDU_RATIO5","Operating_profit_mrgn","AveInco_Urban","Gov_Level_NM","comp_type","Lvrg_Ratio","Debt2Liab","LIAB2TNGBL_ASST","NETASST_DEBT_RATIO"};
        //房地产公司                                                                                                                                        
        String[] house = {"Tot_Eqty","NETDEBT2TOT_EQTY","INDU_RATIO5","INDU_RATIO2","Lvrg_Ratio","EBITDA_Mrgn","Cplt_Prft","INDU_RATIO1","DEF_PPT_MOD_RATIO4"};
        //工业企业
        String[] indu = {"Debt2Cptl","Tot_Eqty","Debt2Liab","INDU_RATIO3","DEF_MANU_MOD_RATIO9","INDU_RATIO4","EBIT_Intrst_Cov","AP_day","TNGBLASST_RTRN","NETDEBT2TOT_EQTY"};
        //商贸服务企业
        String[] busi = {"Tot_Eqty","Equity_Multi","DEF_MANU_MOD_RATIO9","Debt2Cptl","NETASST_RTRN","Operating_profit_mrgn","EBIT_Intrst_Cov","CSH2LIAB","TRD_DAY"};
        
        map.put("bank", bank);
        map.put("secu", secu);
        map.put("insu", insu);
        map.put("urban", urban);
        map.put("house", house);
        map.put("indu", indu);
        map.put("busi", busi);
        List<String> columns = Arrays.asList(map.get(categoryCode));
       
        Query query = new Query();
        query.addCriteria(Criteria.where("issId").is(issId));
        IssKeyIndicatorDoc indicatorDoc = mongoTemplate.findOne(query, IssKeyIndicatorDoc.class);
        List<IssIndicatorsDoc> indicators =  IssKeyIndicatorsToIssIndicatorsDocs(indicatorDoc);//mongoTemplate.find(query, IssIndicatorsDoc.class);
        //过滤指标
        indicators = filterIndicatorFields(indicators, columns);
        List<IssIndicatorsVo> result = new ArrayList<>();
        Map<String,IssIndicatorsVo> resultMap = new HashMap<>();
        for (IssIndicatorsDoc issIndicator : indicators) {
            IssIndicatorsVo issIn = new IssIndicatorsVo();
            issIn.setCategory(issIndicator.getCategory());
            issIn.setFieldName(issIndicator.getFieldName());
            List<BigDecimal> indicatorOrg = issIndicator.getIndicators();
            List<BigDecimal> indicator = new ArrayList<>();
            for (BigDecimal bigDecimal : indicatorOrg) {
                indicator.add(NumberUtils.KeepTwoDecimal(bigDecimal, issIndicator.getPercent()));
            }
            
            if(indicator !=null && indicator.size() > 4){
                issIn.setFieldValue(indicator.subList(0, 4));
            }
            issIn.setNegative(issIndicator.getNegative());
            List<String> quarters = issIndicator.getQuarters();
            if(quarters != null && quarters.size() > 4){
                issIn.setQuarters(quarters.subList(0, 4));
            }
            resultMap.put(issIndicator.getField(), issIn);
        }
        //排序
        for (String field : map.get(categoryCode)) {
            if("AveInco_Urban".equals(field) ||  "Gov_Level_NM".equals(field) || "comp_type".equals(field)){
                continue;
            }
            result.add(resultMap.get(field));
        }
        return result;
    }
    
    
    /**
     * 主体分析-违约概率分析-重点风险指标揭示
     * @param issId
     * @param category
     * @param field
     * @return
     */
    public  List<IssIndicatorPdVo>  findIssIndicators(Long issId, String category, String field){
        Query query = new Query();
        query.addCriteria(Criteria.where("issId").is(issId));
        IssKeyIndicatorDoc indicatorDoc = mongoTemplate.findOne(query, IssKeyIndicatorDoc.class);
        List<IssIndicatorsDoc> indicators =  IssKeyIndicatorsToIssIndicatorsDocs(indicatorDoc);//mongoTemplate.find(query, IssIndicatorsDoc.class);
        //分类和指标过滤
        indicators = filterIndicatorCategorys(indicators, toList(category));
        indicators = filterIndicatorFields(indicators, toList(category));
        List<IssIndicatorPdVo> result = new ArrayList<>();
        Map<String, String> quarterHis = getIssHisPd(issId);
        
        for (IssIndicatorsDoc issIndicator : indicators) {
            IssIndicatorPdVo issIn = new IssIndicatorPdVo();
            
            List<BigDecimal> issIndicators = new ArrayList<>();
            if(issIndicator.getIndicators() != null){
                for (BigDecimal bigDecimal : issIndicator.getIndicators()) {
                    issIndicators.add(NumberUtils.KeepTwoDecimal(bigDecimal, issIndicator.getPercent()));
                }
            }
            issIn.setIndicators(issIndicators);
            issIn.setQuarters(issIndicator.getQuarters());
            List<Double> pds = new ArrayList<>();
            for (String ind : issIn.getQuarters()) {
                double pd = UIAdapter.convAmaRating2Pd(quarterHis.get(ind)).doubleValue();
                pds.add(pd == -1 ? null : pd);
            }
            issIn.setPds(pds);
            result.add(issIn);
        }
        return result;
    }

    /**
     * 历史季度违约概率
     * @param issId
     * @return
     */
    private Map<String, String> getIssHisPd(Long issId) {
        List<IssPdHis> pdhis = amaBondService.findIssPdHis(issId);
        Map<String,String> quarterHis = new HashMap<>();
        for (IssPdHis issPdHis : pdhis) {
            Integer year = issPdHis.getYear();
            Integer month = issPdHis.getMonth();
            String date = SafeUtils.getQuarter(year + "-" + month + "-1", 1)[0];
            if(year != null && month != null && quarterHis.get(date) == null){
                quarterHis.put(date, issPdHis.getRating());
            }
        }
        return quarterHis;
    }
    
    
    /**
     * 主体分析-违约概率分析-重点风险指标分组
     * @param issId
     * @return
     */
    public  List<IndicatorGroupVo>  findIssIndicatorGroup(Long issId){
        String category = amaBondService.findIssCategory(issId);
        if(category == null){
            return new ArrayList<IndicatorGroupVo>();
        }
        Map<String,String> categoryMapping = new HashMap<String, String>();
        categoryMapping.put("原材料", "indu");
        categoryMapping.put("电信业务与信息技术", "indu");
        categoryMapping.put("工业", "indu");
        categoryMapping.put("公用事业", "indu");
        categoryMapping.put("医疗保健", "indu");
        categoryMapping.put("能源", "indu");
        categoryMapping.put("消费类", "busi");
        categoryMapping.put("城投类", "urban");
        categoryMapping.put("投资银行", "secu");
        categoryMapping.put("商业银行", "bank");
        categoryMapping.put("房地产开发", "house");
        categoryMapping.put("保险类", "insu");
        String categoryCode = categoryMapping.get(category);
       
        
        //商业银行
        String[] bank = {"bank_ratio2","bank_ratio43","bank_ratio34",
                "bank_ratio156","bank_ratio157","bank_ratio158",
                "bank_ratio47","bank_ratio65","bank_ratio68"};
        //证券公司
        String[] secu = {"secu_ratio1","secu_ratio39","secu_ratio67",
                "secu_ratio69","secu_ratio68","secu_ratio51",
                "secu_ratio71","secu_ratio70","secu_ratio72"};
        //保险类公司
        String[] insu = {"insu_ratio2","insu_ratio29","insu_ratio25",
                "insu_ratio26","insu_ratio28","insu_ratio16",
                "insu_ratio24","insu_ratio12","insu_ratio7"};
        //城投公司
        String[] urban = {"INDU_RATIO5","Operating_profit_mrgn",/*"AveInco_Urban",
                "Gov_Level_NM","comp_type",*/"Lvrg_Ratio",
                "Debt2Liab","LIAB2TNGBL_ASST","NETASST_DEBT_RATIO"};
        //房地产公司                                                                                                                                        
        String[] house = {"Tot_Eqty","NETDEBT2TOT_EQTY","INDU_RATIO5",
                "INDU_RATIO2","Lvrg_Ratio","EBITDA_Mrgn",
                "Cplt_Prft","INDU_RATIO1","DEF_PPT_MOD_RATIO4"};
        //工业企业
        String[] indu = {"Debt2Cptl","Tot_Eqty","Debt2Liab",
                "INDU_RATIO3","DEF_MANU_MOD_RATIO9","INDU_RATIO4",
                "EBIT_Intrst_Cov","AP_day","TNGBLASST_RTRN","NETDEBT2TOT_EQTY"};
        //商贸服务企业
        String[] busi = {"Tot_Eqty","Equity_Multi","DEF_MANU_MOD_RATIO9",
                "Debt2Cptl","NETASST_RTRN","Operating_profit_mrgn",
                "EBIT_Intrst_Cov","CSH2LIAB","TRD_DAY"};
        Map<String,String[]> map = new HashMap<>();
        map.put("bank", bank);
        map.put("secu", secu);
        map.put("insu", insu);
        map.put("urban", urban);
        map.put("house", house);
        map.put("indu", indu);
        map.put("busi", busi);
        List<String> columns = Arrays.asList(map.get(categoryCode));
        List<IndicatorGroupVo> group = findIndicatorCategory(issId);
        getViewFields(columns, group);
       
        return group;
    }

    /**
     * 筛选出需要展示的指标
     * @param columns
     * @param group
     */
    private void getViewFields(List<String> columns, List<IndicatorGroupVo> group) {
        for (IndicatorGroupVo indicatorGroup : group) {
            Iterator<IndicatorVo> iterator = indicatorGroup.getIndicators().iterator();
            while (iterator.hasNext()) {
                if(!columns.contains(iterator.next().getKey())){
                    iterator.remove();
                }
            }
        }
        Iterator<IndicatorGroupVo> iterator = group.iterator();
        while (iterator.hasNext()) {
            IndicatorGroupVo next = iterator.next();
            if(next.getIndicators() == null || next.getIndicators().size() == 0){
                iterator.remove();
            }
        }
    }
    
    /**
	 * IssKeyIndicatorDoc 转为 IssIndicatorsDoc
	 * @param FinanceIndicators
	 */
	public List<IssIndicatorsDoc> IssKeyIndicatorsToIssIndicatorsDocs(IssKeyIndicatorDoc issFinanceIndicators) {
		List<IssIndicatorsDoc> result = conversionToIssIndicators(issFinanceIndicators, null);
		return result;
	}
  
	/**
	 *  IssKeyIndicatorsToIssIndicatorsDocsList
	 * @param issFinanceIndicators
	 * @return
	 */
	public List<IssIndicatorsDoc> IssKeyIndicatorsToIssIndicatorsDocsList(List<IssKeyIndicatorDoc> issFinanceIndicators) {
		List<IssIndicatorsDoc> result = new ArrayList<>();
		for (IssKeyIndicatorDoc issKeyIndicatorDoc : issFinanceIndicators) {
			result.addAll(IssKeyIndicatorsToIssIndicatorsDocs(issKeyIndicatorDoc));
		}
		return result;
	}
	
	 /**
	 * IssKeyIndicatorDoc 转为 IssIndicatorsDoc
	 * @param FinanceIndicators
	 * @param quarters 季度数量
	 */
	public List<IssIndicatorsDoc> IssKeyIndicatorsToIssIndicatorsDocs(IssKeyIndicatorDoc issFinanceIndicators, Integer quarters) {
		List<IssIndicatorsDoc> result = conversionToIssIndicators(issFinanceIndicators, quarters);
		return result;
	}

	/**
	 * conversionToIssIndicators
	 * @param issFinanceIndicators
	 * @param quarters 季度数量
	 * @return
	 */
	private List<IssIndicatorsDoc> conversionToIssIndicators(IssKeyIndicatorDoc issFinanceIndicators, Integer quarters) {
		if(issFinanceIndicators == null){
			//throw new BusinessException("当前主体没有披露财报信息");
			return new ArrayList<IssIndicatorsDoc>();
		}
		List<IssIndicatorsDoc> result = new ArrayList<>();
		for (FinanceIndicator issFinanceIndicator : issFinanceIndicators.getIndicators()) {
			IssIndicatorsDoc item = new IssIndicatorsDoc();
			item.setCategory(issFinanceIndicator.getCategory());
			item.setCategoryParent(issFinanceIndicator.getCategoryParent());
			item.setField(issFinanceIndicator.getField());
			item.setFieldName(issFinanceIndicator.getFieldName());
			item.setIndicators(issFinanceIndicator.getIndicators());
			item.setIssId(issFinanceIndicators.getIssId());
			item.setNegative(issFinanceIndicator.getNegative());
			item.setPercent(issFinanceIndicator.getPercent());
			item.setInduId(issFinanceIndicators.getInduId());
			item.setInduIdSw(issFinanceIndicators.getInduIdSw());
			item.setType(issFinanceIndicator.getType());
			item.setIssName(issFinanceIndicators.getIssName());
			item.setQuarters(issFinanceIndicators.getQuarters());
			item.setLastFinDate(issFinanceIndicators.getQuarters().get(0));
			item.setOrgType(issFinanceIndicators.getOrgType());
			item.setIsActive(issFinanceIndicators.getIsActive());
			result.add(item);
		}
		return result;
	}
	
	/**
	 * 获取具体指标
	 * @param list
	 * @param field
	 * @return
	 */
	public IssIndicatorsDoc getIndicator(List<IssIndicatorsDoc> list, String field){
		if(list == null || field == null) return null;
		IssIndicatorsDoc issIndicator = null;
		for (IssIndicatorsDoc issIndicatorsDoc : list) {
			if(field.equals(issIndicatorsDoc.getField())){
				issIndicator = issIndicatorsDoc;
				break;
			}
		}
		return issIndicator;
	}
	
	
	/**
	 * 过滤指标
	 * @param list
	 * @param fields
	 * @return
	 */
	public List<IssIndicatorsDoc> filterIndicatorFields(List<IssIndicatorsDoc> list, List<String> fields){
		if(list == null) {
			return null;
		}
		if(fields == null || fields.size() == 0){
			return list;
		}
		List<IssIndicatorsDoc> issIndicators = new ArrayList<>();
		for (IssIndicatorsDoc issIndicatorsDoc : list) {
			if(fields.contains(issIndicatorsDoc.getField())){
				issIndicators.add(issIndicatorsDoc);
			}
		}
		return issIndicators;
	}
	
	/**
	 * 过滤指标
	 * @param list
	 * @param categorys
	 * @return
	 */
	public List<IssIndicatorsDoc >filterIndicatorCategorys(List<IssIndicatorsDoc> list, List<String> categorys){
		if(list == null) return null;
		if(categorys == null || categorys.size() == 0)return list;
		List<IssIndicatorsDoc> issIndicators = new ArrayList<>();
		for (IssIndicatorsDoc issIndicatorsDoc : list) {
			if(categorys.contains(issIndicatorsDoc.getField())){
				issIndicators.add(issIndicatorsDoc);
			}
		}
		return issIndicators;
	}    
	
	
	/**
	 * 获取规定时间未收集到财报主体集合<p>
	 * 节点日期包括：5月1日（年报，一季报），9月1日（半年报），11月1日（三季报）<br>
	 *	不同节点日期检查不同财报，其中5月1日检查的年报时间为上一年的年报<p>
	 *	例如：<br>
	 *	到了2017年5月1日，检查该投组中的主体是否披露了2017年一季报和2016年年报，若没有则推送消息<br>
     *	到了2017年9月1日，检查该投组中的主体是否披露了2017年半年报，若没有则推送消息<br>
	 *	到了2017年11月1日，检查该投组中的主体是否披露了2017年三季报，若没有则推送消息<br>
	 * @param date
	 * @return
	 */
	public List<IssNoFinanceInDateVo> findNotFinanceInDate(Date date){
		//时间点
		String 	datePoint0501 = "05-01",
				datePoint0901 = "09-01",
				datePoint1101 = "11-01";
		String dateString = SafeUtils.convertDateToString(date, SafeUtils.DATE_FORMAT);
		//系统中所有主体comUniCode
		List<BondCom> comps =  amaBondDao.findAll();
		//当年年份
		Integer currentYear =  SafeUtils.getYear(dateString);
		//到了2017年5月1日，检查该投组中的主体是否披露了2017年一季报和2016年年报
		if(dateString.contains(datePoint0501)){
			String currentQ1 = currentYear + "-03-31";
			String preQ4 = (currentYear-1) + "-12-31";
			
			List<IssNoFinanceInDateVo> current = getIssNoFinanceInDareVo(comps, currentQ1);
			List<IssNoFinanceInDateVo> pre = getIssNoFinanceInDareVo(comps, preQ4);
			current.addAll(pre);
			
			return current;
			
		}
		//到了2017年9月1日，检查该投组中的主体是否披露了2017年半年报
		if(dateString.contains(datePoint0901)){
			String currentQ2 = currentYear + "-06-30";
			List<IssNoFinanceInDateVo> list = getIssNoFinanceInDareVo(comps, currentQ2);
			return list;
		}
		//到了2017年11月1日，检查该投组中的主体是否披露了2017年三季报
		if(dateString.contains(datePoint1101)){
			String currentQ3 = currentYear + "-09-30";
			List<IssNoFinanceInDateVo> list = getIssNoFinanceInDareVo(comps, currentQ3);
			return list;
		}
		
		return null;
		
	}

	/**
	 * 获取规定时间未收集到财报信息Vo
	 * @param comps
	 * @param currentQ3
	 * @return
	 */
	private List<IssNoFinanceInDateVo> getIssNoFinanceInDareVo(List<BondCom> comps, String currentQ3) {
		List<Long> compIdList = finaSheetDao.findCompId(currentQ3);
		List<Long> comUniCodes = getBondComForNoFinnance(comps, compIdList);
		List<IssNoFinanceInDateVo> list = buildIssNoFinanceInDateVo(comUniCodes, currentQ3);
		return list;
	}

	/**
	 * 没有财报主体的comUniCode
	 * @param comps
	 * @param currentList
	 * @return
	 */
	private List<Long> getBondComForNoFinnance(List<BondCom> comps, List<Long> currentList) {
		if(comps == null || currentList == null){
			return null;
		}
		List<Long> comUniCodes = new ArrayList<>();
		comps.forEach(comp -> {
			if(!currentList.contains(comp.getAmsIssId())){
				comUniCodes.add(comp.getIssId());
			}
		});
		return comUniCodes;
	}
	
	/**
	 * 
	 * @param compUniCodes
	 * @param quarter
	 * @return
	 */
	List<IssNoFinanceInDateVo> buildIssNoFinanceInDateVo(List<Long> compUniCodes, String quarter){
		if(compUniCodes == null){
			return null;
		}
		List<IssNoFinanceInDateVo> result = new ArrayList<>();
		compUniCodes.forEach(comUniCode -> {
			result.add(new IssNoFinanceInDateVo(comUniCode, SafeUtils.getQuarter(quarter, 1)[0]));
		});
		return result;
	}
    
    public static void main(String[] args) {
        List<BigDecimal> list = new ArrayList<>();
        for (int i = 0; i < 20000; i++) {
            
            list.add( new BigDecimal(0.1234));
            list.add( new BigDecimal(0.14));
            list.add( new BigDecimal(0.23));
            list.add( new BigDecimal(0.2334));
        }
        
//        list.add( new BigDecimal(0.3213));
//        list.add( new BigDecimal(0.4321));
//        list.add( new BigDecimal(0.5332));
//        list.add( new BigDecimal(0.6421));
//        list.add( new BigDecimal(0.7432));
        System.out.println(SafeUtils.getQuantileValue(list,  new BigDecimal(0.14), 0));
    
    
        BigDecimal c = new BigDecimal("6365272.33");
        BigDecimal v10 = new BigDecimal("2268841.16");
        BigDecimal v90 = new BigDecimal("61541104.40");
        BigDecimal d = v90.subtract(v10);
        BigDecimal r = c.subtract(v10).divide(d);
        System.out.println(r.toString());
    }
    
    
    
    
    
    
 }


 

 
 










