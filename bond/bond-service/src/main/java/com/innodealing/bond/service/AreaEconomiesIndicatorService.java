package com.innodealing.bond.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.persistence.NamedNativeQueries;

import org.hibernate.loader.plan.exec.process.spi.ReturnReader;
import org.hibernate.metamodel.relational.Datatype;
import org.omg.IOP.CodeSets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.google.common.base.Objects;
import com.innodealing.bond.param.AreaEconomiesIndicatorFilter;
import com.innodealing.bond.param.AreaIndicatorParam;
import com.innodealing.bond.param.AreaUniCodeParam;
import com.innodealing.bond.param.IndexDivisionParam;
import com.innodealing.bond.param.RaddrFilter;
import com.innodealing.bond.param.area.RadarIndicator;
import com.innodealing.bond.service.area.IndicatorAreaService;
import com.innodealing.bond.vo.area.AreaIndicatorVo;
import com.innodealing.bond.vo.area.DateConstant;
import com.innodealing.bond.vo.area.DateConstant.quarterCode;
import com.innodealing.bond.vo.area.RadarVo;
import com.innodealing.model.im.Area;
import com.innodealing.model.mongo.dm.AreaEconomiesDoc;
import com.innodealing.model.mongo.dm.EconomieIndicator;
import com.innodealing.model.mongo.dm.bond.area.AreaIndicatorField;
import com.innodealing.model.mongo.dm.bond.area.AreaIndicatorFilterDoc;
import com.innodealing.model.mongo.dm.bond.area.AreaIndicatorItem;
import com.innodealing.model.mongo.dm.bond.user.UserAreaIndicatorFilter;
import com.innodealing.util.SafeUtils;
import com.innodealing.util.StringUtils;
import com.mysql.fabric.xmlrpc.base.Array;

@Service
public class AreaEconomiesIndicatorService {
	
	private @Autowired JdbcTemplate jdbcTemplate ;	
	private @Autowired MongoTemplate mongoTemplate ;	
	private @Autowired BondCityService bondCityService;	
	private @Autowired IndicatorAreaService indicatorAreaService;
	
	private static final Logger logger = LoggerFactory.getLogger(AreaEconomiesIndicatorService.class);
	
	//过滤条件查询
	public List<AreaIndicatorVo> findAreaEconomiesIndicators(Long userid,AreaEconomiesIndicatorFilter filter){
		List<AreaIndicatorVo> areaIndicatorVos = new ArrayList<>();
		
		//根据filter查找符合的所有数目
		//获取系统当前的 年  季度  月
		int year = getYear();
		Query query = new Query();
		//地区代码
		if(filter.getAreaUniCode() != null){
			query.addCriteria(Criteria.where("areaUniCode").is(filter.getAreaUniCode()));
		}
		//时间维度
		if(null != filter.getTimeHorizon() && filter.getTimeHorizon() !=0){
			if(filter.getTimeHorizon() == 1){
				query.addCriteria(Criteria.where("bondYear").is(year));
			}else if(filter.getTimeHorizon() == 2){
				query.addCriteria(Criteria.where("bondYear").gte(year-2));
			}else if(filter.getTimeHorizon() == 3){
				query.addCriteria(Criteria.where("bondYear").gte(year-4));
			}
		}else{
			Integer startYear = filter.getStartYear();
			Integer endYear = filter.getEndYear();
			
			query.addCriteria(Criteria.where("bondYear").gte(startYear).lte(endYear));
		}
		//数据类型   年度   季度  月度
		if(!StringUtils.isEmpty(filter.getDataType())){
			query.addCriteria(Criteria.where("dataType").is(filter.getDataType()));
		}
		//数据来源l
		if(!StringUtils.isEmpty(filter.getStatisticsType())){
			query.addCriteria(Criteria.where("statisticsType").is(filter.getStatisticsType()));
		}
		
	    //排序		
		if(DateConstant.YEAR.equals(filter.getDataType())){
			query.with(new Sort(Sort.Direction.DESC,"bondYear"));
		}		
		if(DateConstant.QUARTER.equals(filter.getDataType())){
			query.with(new Sort(Sort.Direction.DESC,"bondYear","bondQuarter"));
		}		
		if(DateConstant.MONTH.equals(filter.getDataType())){
			query.with(new Sort(Sort.Direction.DESC,"bondYear","bondMonth"));
		}

		//查找出所有符合条件的doc
		List<AreaEconomiesDoc> docs = mongoTemplate.find(query, AreaEconomiesDoc.class);
		
		AreaIndicatorFilterDoc filterDoc = new AreaIndicatorFilterDoc();
		List<AreaIndicatorItem> list = new ArrayList<>();
		
		//根据用户id,如果是初始化用户则查询默认filter,不然则查询user_area_indicator_filter
		Query query2 = new Query();
		query2.addCriteria(Criteria.where("_id").is(userid));
		UserAreaIndicatorFilter userAreaIndicatorFilter = mongoTemplate.findOne(query2, UserAreaIndicatorFilter.class);
		if(null == userAreaIndicatorFilter){
			//查询默认所有选定的指标
			filterDoc = mongoTemplate.findOne(new Query(), AreaIndicatorFilterDoc.class);
			//获取所有的指标
			list = filterDoc.getIndicatorFieldGroups();
		}else{
			AreaIndicatorFilterDoc doc = userAreaIndicatorFilter.getFilter();
			list = doc.getIndicatorFieldGroups();
		}
		
		if(null==docs || docs.size()==0){
			for(AreaIndicatorItem item:list){
				for(AreaIndicatorField field:item.getIndicatorFields()){
					if(field.getSelected()==1){
						AreaIndicatorVo areaIndicatorVo = new AreaIndicatorVo();
						areaIndicatorVo.setAreaUniCode(filter.getAreaUniCode());
						areaIndicatorVo.setField(field.getField());
						areaIndicatorVo.setFieldName(field.getFieldName());
						areaIndicatorVo.setType(item.getGroup());
						
						areaIndicatorVos.add(areaIndicatorVo);
					}
				}
			}
			
			return areaIndicatorVos;
		}
		
		//所有需要返回的指标
		List<AreaIndicatorParam> params = new ArrayList<>();
		for(AreaIndicatorItem item:list){
			for(AreaIndicatorField field:item.getIndicatorFields()){
				if(field.getSelected()==1){
					AreaIndicatorParam param = new AreaIndicatorParam();
					param.setType(item.getGroup());
					param.setField(field.getField());
					param.setFieldName(field.getFieldName());
										
					params.add(param);
				}
			}
		}
				
		if(params !=null && params.size()>0){
			for(AreaIndicatorParam param:params){
				AreaIndicatorVo areaIndicatorVo = convert(param,docs,filter);
				areaIndicatorVos.add(areaIndicatorVo);
			}
		}
				
		return areaIndicatorVos;				
	}
	
	
	public AreaIndicatorVo  convert(AreaIndicatorParam param,List<AreaEconomiesDoc> docs,AreaEconomiesIndicatorFilter filter){
		AreaIndicatorVo vo = new AreaIndicatorVo();
		
		vo.setField(param.getField());
		vo.setFieldName(param.getFieldName());
		vo.setType(param.getType());
		
		List<String>  quarterList = new ArrayList<>() ;
		List<BigDecimal>  valueList = new ArrayList<>() ;
		for(AreaEconomiesDoc doc:docs){
			for(EconomieIndicator e:doc.getEconomieIndicators()){
				if(param.getField().equals(e.getField())){													
					valueList.add(e.getValue());
					
					//设置日期区间
					if(DateConstant.YEAR.equals(doc.getDataType())){
						quarterList.add(String.valueOf(doc.getBondYear()));
					}
					if(DateConstant.QUARTER.equals(doc.getDataType())){
						if(doc.getBondQuarter()==1){
							quarterList.add(doc.getBondYear()+"/"+quarterCode.ONE_QUARTER.getName());
						}
						if(doc.getBondQuarter()==2){
							quarterList.add(doc.getBondYear()+"/"+quarterCode.TWO_QUARTER.getName());
						}	
						if(doc.getBondQuarter()==3){
							quarterList.add(doc.getBondYear()+"/"+quarterCode.THREE_QUARTER.getName());
						}	
						if(doc.getBondQuarter()==4){
							quarterList.add(doc.getBondYear()+"/"+quarterCode.FOUR_QUARTER.getName());
						}	
					}
					if(DateConstant.MONTH.equals(doc.getDataType())){
						quarterList.add(doc.getBondYear()+"/"+doc.getBondMonth());
					}
					
					vo.setUnit(e.getUnit());
					vo.setAreaChiName(doc.getAreaChiName());
					vo.setAreaUniCode(doc.getAreaUniCode());
					//vo.setBondMonth(doc.getBondMonth());
					
				}
			}
		}
		
		vo.setValues(valueList);
		vo.setQuarters(quarterList);
		
		return vo;	
	}
		

	//获取系统当前年份
	public int getYear(){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		int year = calendar.get(Calendar.YEAR);
		return year;
	}
		
	//根据查询条件返回查询的结果-雷达图
	public List<RadarIndicator> findRadar(RaddrFilter filter,String[] codes){
		List<RadarIndicator> list = new ArrayList<>();
				
		//基准数据
		AreaEconomiesDoc doc1 = findAreaEconomiesDoc(filter.getAreaUniCode(),filter.getBondMonth(),filter.getBondQuarter(),filter.getBondYear(),filter.getDataType(),filter.getStatisticsType());
		//对比数据
		AreaEconomiesDoc doc2 = findAreaEconomiesDoc(filter.getAreaUniCode(),filter.getContrastBondMonth(),filter.getContrastBondQuarter(),filter.getContrastBondYear(),filter.getDataType(),filter.getStatisticsType());			
		//区域均值计算
		//根据areaUniCode 判定是省 市 或者 区、县，拿到所有的areaUniCode ,在计算下面的所有主题发型的债券并计算其值
		List<AreaUniCodeParam> areaUniCodeParams = null;
		try {
			areaUniCodeParams = findAllAreaUniCode(filter.getAreaUniCode());
		} catch (EmptyResultDataAccessException e) {
			// TODO: handle exception
			e.printStackTrace();
			areaUniCodeParams = null;
			return list;
		}
		 
		//根据areaUniCode 查找所有符合的doc,并计算指标均值			
		AreaEconomiesDoc doc3 = findAllAreaEconomiecsDoc(areaUniCodeParams,filter.getBondMonth(),filter.getBondQuarter(),filter.getBondYear(),filter.getDataType(),filter.getStatisticsType(),codes);
		//计算分位值（基准）
		//查找的所有符合的code值
		List<Long> areaUniCodeList = indicatorAreaService.getAreaCodes(filter.getAreaUniCode());
		Query query = buildQuery(filter,codes,areaUniCodeList);
		List<AreaEconomiesDoc> listStarand = mongoTemplate.find(query, AreaEconomiesDoc.class);
		//计算对比
		Query query2 = buildCompareQuery(filter, codes, areaUniCodeList);
		List<AreaEconomiesDoc> listCompare = mongoTemplate.find(query2, AreaEconomiesDoc.class);
		//计算均值与计算基准相同
		List<AreaEconomiesDoc> listAverage = listStarand;
						
		for(String code:codes){
			RadarIndicator radarIndicator = new RadarIndicator();
			
			List<BigDecimal> valueStandList = convertToList(code,listStarand);
			List<BigDecimal> valueCompareList = convertToList(code,listCompare);
			List<BigDecimal> valueAverageList = valueStandList;
			//从库中随机查一条数据
			AreaEconomiesDoc doc = mongoTemplate.findOne(new Query(), AreaEconomiesDoc.class);					
			for(EconomieIndicator field:doc.getEconomieIndicators()){
				if(Objects.equal(field.getField(), code)){
					radarIndicator.setFieldName(field.getFieldName());
					radarIndicator.setUnit(field.getUnit());
				}
			}
			if(doc1 !=null && listStarand !=null && listStarand.size()>0){											
				IndexDivisionParam param1 = calculatedPartition(doc1,valueStandList,code);				
				radarIndicator.setValueAreaStandard(param1.getValue());				
			}
			
			if(doc2!=null && listCompare!=null && listCompare.size()>0){				
				//对比返回的所有指标的值				
				IndexDivisionParam param2 = calculatedPartition(doc2,valueCompareList,code);				
				radarIndicator.setValueAreaCompare(param2.getValue());
			}
			
			if(doc3!=null  && listStarand!=null && listStarand.size()>0){			
				//返回均值指标值					
				IndexDivisionParam param3 = calculatedPartition(doc3,valueAverageList,code);				
				radarIndicator.setValueAreaContrast(param3.getValue());			
			}
						
			list.add(radarIndicator);						
		}			
		
		
		return list;	
	}
	
	//计算分位值
	public IndexDivisionParam calculatedPartition(AreaEconomiesDoc doc,List<BigDecimal> list,String code){
		IndexDivisionParam param = new IndexDivisionParam();
		for(EconomieIndicator indicator:doc.getEconomieIndicators()){
			if(code.equals(indicator.getField())){
				param.setFieldName(indicator.getFieldName());
				param.setUnit(indicator.getUnit());
				param.setValue(indicator.getValue());
				
				if(indicator.getValue()==null){
					return param;
				}
			}
		}
		//去掉集合中的null 值
		List<BigDecimal> list2 = new ArrayList<>();
		for(int i=0;i<list.size();i++){
			if(list.get(i)!=null){
				list2.add(list.get(i));
			}
		}
		
		 //计算分位值
		BigDecimal min      = SafeUtils.getQuantile(list2, 1,SafeUtils.QUANTILE_ASC);//行业min分位
        BigDecimal induin10 = SafeUtils.getQuantile(list2, 10,SafeUtils.QUANTILE_ASC);//行业10分位        
        BigDecimal induin25 = SafeUtils.getQuantile(list2, 25,SafeUtils.QUANTILE_ASC);//行业15分位      
        BigDecimal induin50 = SafeUtils.getQuantile(list2, 50,SafeUtils.QUANTILE_ASC);//行业50分位        
        BigDecimal induin75 = SafeUtils.getQuantile(list2, 75,SafeUtils.QUANTILE_ASC);//行业75分位
        BigDecimal induin90 = SafeUtils.getQuantile(list2, 90,SafeUtils.QUANTILE_ASC);//行业90分位
        BigDecimal max      = SafeUtils.getQuantile(list2, 100,SafeUtils.QUANTILE_ASC);//行业max分位
        
       
        //公司水平=（公司数值-10分位）/（90分位-10分位）
        BigDecimal num1 = (param.getValue()).subtract(induin10);
        BigDecimal num2 = induin90.subtract(induin10).setScale(2, BigDecimal.ROUND_HALF_UP);
        if(num2==null || Objects.equal(num2, new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP))){
        	param.setValue(new BigDecimal("1"));
        }else{
        	 BigDecimal result = num1.divide(num2,2,BigDecimal.ROUND_HALF_UP);
        	 int n= result.compareTo(new BigDecimal("1"));
             if(n==1){
             	param.setValue(new BigDecimal("1"));	
             }else{
             	param.setValue(result);
             }
        }
         
		return param;
	}
	
	//指标的所有值的集合
	public List<BigDecimal> convertToList(String code,List<AreaEconomiesDoc> list2){
		List<BigDecimal> list = new ArrayList<>();
		for(AreaEconomiesDoc doc:list2){
			for(EconomieIndicator indicator:doc.getEconomieIndicators()){
				if(code.equals(indicator.getField())){
					list.add(indicator.getValue());
				}
			}
		}
		
		return list;		
	}
	
	//计算基准
	public Query buildQuery(RaddrFilter filter,String[] codes,List<Long> areaUniCodeList){
		Query query = new Query();
		
		if(filter.getBondYear()!=null && filter.getBondYear()!=0){
			query.addCriteria(Criteria.where("bondYear").is(filter.getBondYear()));
		}
		if(filter.getBondQuarter()!=null && filter.getBondQuarter()!=0){
			query.addCriteria(Criteria.where("bondQuarter").is(filter.getBondQuarter()));
		}
		if(filter.getBondMonth()!=null && filter.getBondMonth()!=0){
			query.addCriteria(Criteria.where("bondMonth").is(filter.getBondMonth()));
		}
		if(StringUtils.isEmpty(filter.getDataType())){
			query.addCriteria(Criteria.where("dataType").is(filter.getDataType()));
		}
		if(!StringUtils.isEmpty(filter.getStatisticsType())){
			query.addCriteria(Criteria.where("statisticsType").is(filter.getStatisticsType()));
		}
		
		query.addCriteria(Criteria.where("areaUniCode").in(areaUniCodeList));
//		query.fields()
//			.include("areaUniCode")
//			.include("bondYear")
//			.include("areaChiName")
//			.include("bondMonth")
//			.include("bondQuarter")
//			.elemMatch("economieIndicators", Criteria.where("field").in(codes));
//		
		return query;
	}
	
	//计算对比值
	public Query buildCompareQuery(RaddrFilter filter,String[] codes,List<Long> areaUniCodeList){
		Query query = new Query();
		
		if(filter.getContrastBondYear()!=null && filter.getContrastBondYear()!=0){
			query.addCriteria(Criteria.where("bondYear").is(filter.getContrastBondYear()));
		}
		if(filter.getContrastBondQuarter()!=null && filter.getContrastBondQuarter()!=0){
			query.addCriteria(Criteria.where("bondQuarter").is(filter.getContrastBondQuarter()));
		}
		if(filter.getContrastBondMonth()!=null && filter.getContrastBondMonth()!=0){
			query.addCriteria(Criteria.where("bondMonth").is(filter.getContrastBondMonth()));
		}
		if(StringUtils.isEmpty(filter.getDataType())){
			query.addCriteria(Criteria.where("dataType").is(filter.getDataType()));
		}
		if(!StringUtils.isEmpty(filter.getStatisticsType())){
			query.addCriteria(Criteria.where("statisticsType").is(filter.getStatisticsType()));
		}
		
		query.addCriteria(Criteria.where("areaUniCode").in(areaUniCodeList));
//		query.fields()
//			.include("areaUniCode")
//			.include("bondYear")
//			.include("areaChiName")
//			.include("bondMonth")
//			.include("bondQuarter")
//			.elemMatch("economieIndicators", Criteria.where("field").in(codes));
//		
		return query;
	}

	//查找到该地区下所有符合的主题发型的债券
	public AreaEconomiesDoc findAllAreaEconomiecsDoc(List<AreaUniCodeParam> list,Integer bondMonth,Integer bondQuarter,Integer bondYear,String dataType,String statisticsType,String[] codes){
		List<AreaEconomiesDoc> docs = new ArrayList<>();		
		List<Long> areaUniCodes = new ArrayList<>();
		for(AreaUniCodeParam param:list){
			areaUniCodes.add(param.getAreaUniCode());
		}
		
		Query query = new Query();
		if(bondMonth !=null && bondMonth !=0){
			query.addCriteria(Criteria.where("bondMonth").is(bondMonth));
		}
		if(bondQuarter!= null && bondQuarter !=0){
			query.addCriteria(Criteria.where("bondQuarter").is(bondQuarter));
		}
		if(bondYear !=null && bondYear !=0){
			query.addCriteria(Criteria.where("bondYear").is(bondYear));
		}
		if(!StringUtils.isEmpty(statisticsType)){
			query.addCriteria(Criteria.where("statisticsType").is(statisticsType));
		}
		if(!StringUtils.isEmpty(dataType)){
			query.addCriteria(Criteria.where("dataType").is(dataType));
		}
		if(list!=null && list.size()>0){
			query.addCriteria(Criteria.where("areaUniCode").in(areaUniCodes));
		}
		
		docs = mongoTemplate.find(query, AreaEconomiesDoc.class);
		
		AreaEconomiesDoc doc = new AreaEconomiesDoc(); 
		List<EconomieIndicator> economieIndicators = new ArrayList<>();	
		if(docs !=null && docs.size()>0){
			for(String code :codes){
				EconomieIndicator economieIndicator = countAverage(code,docs);
				economieIndicators.add(economieIndicator);
			}			
		}else{
			return new AreaEconomiesDoc();
		}
		
		doc.setEconomieIndicators(economieIndicators);
		return doc;
	}
	
	//计算单个指标平均值
	public EconomieIndicator countAverage(String code,List<AreaEconomiesDoc> areaEconomiesDocs){
		List<EconomieIndicator> list = new ArrayList<>();
		//计算指标的平均值
		EconomieIndicator indicator = new EconomieIndicator();
		//所有相同类指标封装到list中
		for(AreaEconomiesDoc doc:areaEconomiesDocs){			
			for(EconomieIndicator indicator2:doc.getEconomieIndicators()){
				if(code.equals(indicator2.getField())){
					indicator.setField(indicator2.getField());
					indicator.setFieldName(indicator2.getFieldName());	
					indicator.setUnit(indicator2.getUnit());
					indicator.setValue(indicator2.getValue());				
						
					list.add(indicator);					
				}
			}
		}
				
		if(list != null && list.size()>0){
			indicator.setField(list.get(0).getField());
			indicator.setFieldName(list.get(0).getFieldName());
			
			double count = 0;
			int size = 0;
			for(EconomieIndicator economieIndicator:list){
				if(economieIndicator.getValue()!=null){
					count = count + economieIndicator.getValue().doubleValue();
					size++;
				}
				
			}
			if(count ==0 || size==0 ){
				indicator.setValue(null);
			}else{
				BigDecimal num = new BigDecimal(count);
				//BigDecimal size = new BigDecimal(list.size());
				double b = num.divide(new BigDecimal(size), 5, BigDecimal.ROUND_HALF_UP).doubleValue();				
				indicator.setValue(new BigDecimal(String.valueOf(b)));
			}		
		}
		
		return indicator;		
	}
	
	//查找到该地区同级所有areaUniCode
	public List<AreaUniCodeParam> findAllAreaUniCode(Long areaUniCode){
		Map<String, Object> map = new HashMap<>();
		List<AreaUniCodeParam> list = null;
		
		StringBuffer buffer = new StringBuffer();
		buffer.append("select area_name1 name1,area_name2 name2,area_name3 name3,area_name4 name4,");
		buffer.append("\n\tarea_uni_code areaUniCode,sub_uni_code subUniCode");
		buffer.append("\n\tfrom  bond_ccxe.pub_area_code");
		buffer.append("\n\twhere isvalid = 1 and area_name1 = '中国' ");
		buffer.append("\n\tand area_name2 is not null");
		buffer.append("\n\tand area_name2 not in ('香港','澳门','台湾')");
		buffer.append("\n\tand area_uni_code="+areaUniCode);
		
		Map<String, Object> queryMap= jdbcTemplate.queryForMap(buffer.toString());		
		if(null == queryMap){
			return null;
		}
		if(!StringUtils.isEmpty(queryMap.get("name4"))){
			//areaUniCode 表示区县一级
			double citySubUniCode = (double)queryMap.get("subUniCode"); //该县所在的市
			long citycode = (long)citySubUniCode;
			
			String sql = "select area_uni_code areaUniCode,sub_uni_code subUniCode from  bond_ccxe.pub_area_code where area_uni_code="+citySubUniCode;
			Map<String, Object> map2= jdbcTemplate.queryForMap(sql);
			double provinceSubUniCode= (double)map2.get("subUniCode");//表示该县所在的省	
			long code=(long)provinceSubUniCode;
			
			long countycode = Long.valueOf(areaUniCode);
			map.put("countyAreaUniCode",countycode);			
			map.put("cityAreaUniCode",citycode);			
			map.put("provinceAreaUniCode",code);
			
			list = bondCityService.queryAreaUniCodes(3,map,String.valueOf(areaUniCode));
		}
		if(StringUtils.isEmpty(queryMap.get("name4")) && !StringUtils.isEmpty(queryMap.get("name3"))){			
			double provinceSubUniCode = (double)queryMap.get("subUniCode"); //该市所在的省
			long code=(long)provinceSubUniCode;
			
			long citycode = Long.valueOf(areaUniCode);
			map.put("cityAreaUniCode",citycode);			
			map.put("provinceAreaUniCode",code);
			
			list = bondCityService.queryAreaUniCodes(2,map,String.valueOf(areaUniCode));
		}
		if(StringUtils.isEmpty(queryMap.get("name4")) && StringUtils.isEmpty(queryMap.get("name3"))){
			//areaUniCode 表示省一级
			long code = Long.valueOf(areaUniCode);
			map.put("provinceAreaUniCode",code);
			
			list = bondCityService.queryAreaUniCodes(1,map,String.valueOf(areaUniCode));
		}
				
		return list;
	}
	
	
	//根据过滤条件查找对应的区域经济数据
	public AreaEconomiesDoc findAreaEconomiesDoc(Long areaUniCode,Integer bondMonth,Integer bondQuarter,Integer bondYear,String dataType,String statisticsType){
		Query query = new Query();
		
		if(areaUniCode!=null){
			query.addCriteria(Criteria.where("areaUniCode").is(areaUniCode));
		}
		if(!StringUtils.isEmpty(dataType)){
			query.addCriteria(Criteria.where("dataType").is(dataType));
		}
		if(bondYear!=null){
			query.addCriteria(Criteria.where("bondYear").is(bondYear));
		}
		if(bondMonth!=null && bondMonth!=0){
			query.addCriteria(Criteria.where("bondMonth").is(bondMonth));
		}
		if(bondQuarter!=null && bondQuarter!=0){
			query.addCriteria(Criteria.where("bondQuarter").is(bondQuarter));
		}
		if(!StringUtils.isEmpty(statisticsType)){
			query.addCriteria(Criteria.where("statisticsType").is(statisticsType));
		}else{
			//没选择时，默认给查询-公报
			query.addCriteria(Criteria.where("statisticsType").is("公报"));
		}
				
		AreaEconomiesDoc doc = mongoTemplate.findOne(query, AreaEconomiesDoc.class);		
		return doc ;	
	}
	

}