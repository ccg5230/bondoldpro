package com.innodealing.bond.service.area;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.google.common.base.Objects;
import com.innodealing.bond.vo.area.AreaIndicatorKChartVo;
import com.innodealing.bond.vo.area.AreaIssuerSortItemVo;
import com.innodealing.engine.jdbc.bond.PubAreaCodeDao;
import com.innodealing.model.dm.bond.PubAreaCode;
import com.innodealing.model.mongo.dm.bond.area.AreaBondEconomiesDoc;
import com.innodealing.model.mongo.dm.bond.area.BondEconomieIndicator;
import com.innodealing.util.SafeUtils;
@Service
public class IndicatorAreaService {
	
	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private PubAreaCodeDao pubAreaCodeDao;
	
	
	public List<Long> getAreaCodes(Long areaUniCode) {
		List<PubAreaCode> list = pubAreaCodeDao.findAreaUniCode(areaUniCode);
		List<Long> queryMongodbCodes = new ArrayList<>();
		if (null != list && list.size()>0)  {
			List<PubAreaCode> pl =null; 
			PubAreaCode pubAreaCode = list.get(0);
			if(pubAreaCode.getAreaName3() == null){
				//查询所有省
				pl = pubAreaCodeDao.findAllProvince();
			} else if(pubAreaCode.getAreaName3() != null && pubAreaCode.getAreaName4() == null) {
				//查询同省级下面的市
				Long subcode = pubAreaCode.getSubUniCode();
				pl = pubAreaCodeDao.findCityByProvinceCode(subcode);
				
			} else if(pubAreaCode.getAreaName3() != null && pubAreaCode.getAreaName4() != null) {
				//查询同省所有区县
				pl = pubAreaCodeDao.findAllProvinceCountryByCode(areaUniCode);
			}
			for(PubAreaCode code : pl) {
				queryMongodbCodes.add(code.getAreaUniCode());
			}
		}
		return queryMongodbCodes;
	}
	
	/**
	 * 
	 * @param areaUniCode
	 * @param field
	 * @param statisticsType
	 * @param dataType
	 * @param userid
	 * @return
	 */
	public List<AreaIndicatorKChartVo> findkchart(Long areaUniCode,String field,String statisticsType,String dataType,long userid){
	
		//构建query
		Query query = buildQuery(areaUniCode,field,statisticsType,dataType,userid);
		List<AreaBondEconomiesDoc> areaDatalist = mongoTemplate.find(query, AreaBondEconomiesDoc.class);
		
		//按年份分组
		Map<String, List<AreaBondEconomiesDoc>> areaDatalistGroupYear =  areaDatalist.stream()
					.collect(Collectors.groupingBy(AreaBondEconomiesDoc :: getSortField));
		
		//封装结果
		List<AreaIndicatorKChartVo> result = new ArrayList<>();
		
		areaDatalistGroupYear.forEach((timekey,list)->{
			AreaIndicatorKChartVo areaIndicatorKChartVo = new AreaIndicatorKChartVo();
			List<BigDecimal> areaIndicator = new ArrayList<>();
			list.forEach(item -> {
				List<BondEconomieIndicator> indicators = item.getEconomieIndicators();
				for (BondEconomieIndicator ind : indicators) {
					areaIndicator.add(ind.getValue());
				}
				//当前code和areaUniCode
				if(item.getAreaUniCode().equals(areaUniCode)){
					for (BondEconomieIndicator ind : indicators) {
						//areaIndicatorKChartVo.setSelf(1);
						areaIndicatorKChartVo.setQuantileIss(ind.getValue());
					}
				}
			}); 
			
			areaIndicatorKChartVo.setQuarter(timekey+"");
			areaIndicatorKChartVo.setQuantile10(SafeUtils.getQuantile(areaIndicator, 10, SafeUtils.QUANTILE_ASC));
			areaIndicatorKChartVo.setQuantile25(SafeUtils.getQuantile(areaIndicator, 25, SafeUtils.QUANTILE_ASC));
			areaIndicatorKChartVo.setQuantile50(SafeUtils.getQuantile(areaIndicator, 50, SafeUtils.QUANTILE_ASC));
			areaIndicatorKChartVo.setQuantile75(SafeUtils.getQuantile(areaIndicator, 75, SafeUtils.QUANTILE_ASC));
			areaIndicatorKChartVo.setQuantile90(SafeUtils.getQuantile(areaIndicator, 90, SafeUtils.QUANTILE_ASC));
			areaIndicatorKChartVo.setQuantileMax(SafeUtils.getQuantile(areaIndicator,100 , SafeUtils.QUANTILE_ASC));
			areaIndicatorKChartVo.setQuantileMIn(SafeUtils.getQuantile(areaIndicator, 1, SafeUtils.QUANTILE_ASC));
			result.add(areaIndicatorKChartVo);
		});
		result.sort(new Comparator<AreaIndicatorKChartVo>() {
			@Override
			public int compare(AreaIndicatorKChartVo o1, AreaIndicatorKChartVo o2) {
				
				String o1value = o1.getQuarter();
				String o2value = o2.getQuarter();
				BigDecimal valueO1 = new BigDecimal(o1value);
				BigDecimal valueO2 = new BigDecimal(o2value);
				return valueO2.compareTo(valueO1);
			}
		});
		
		if(result.size() == 0){
			return result;
		}
		if(result.get(0).getQuarter().length()==4){
			if(result.size()>3){
				return result.subList(0, 3);
			}
			 return result.subList(0, result.size());
		}
		if(Objects.equal(dataType, "月度")){
			for (int i = 0; i< result.size(); i++){
				String result1 = result.get(i).getQuarter();
				String result2 = result1.substring(0, 4);
				String result3 = result1.substring(4);
				result.get(i).setQuarter(result2+"/"+result3+"月");
			}
			if(result.size()<=6){
				return result.subList(0,result.size());
			}
		}
		
		if(Objects.equal(dataType, "季度")){
			for (int i = 0; i< result.size(); i++){
				String result1 = result.get(i).getQuarter();
				String result2 = result1.substring(0, 4);
				String result3 = result1.substring(4);
				String result4 = null;
				if(result3.equals("1")){
					result4 = "Q1";
				}
				if(result3.equals("2")){
					result4 = "Q2";
				}
				if(result3.equals("3")){
					result4 = "Q3";
				}
				if(result3.equals("4")){
					result4 = "Q4";
				}
				result.get(i).setQuarter(result2+"/"+result4);
			}
			if(result.size()<=6){
				return result.subList(0, result.size());
			}
		}
		
		return result.subList(0, 6);
		
	}
	
	//区域排名 top10
	public List<AreaIssuerSortItemVo> sortTop10(Long areaUniCode,String field,String statisticsType,String dataType,Long userid){
		//AreaIssuerSortItemVo result = new AreaIssuerSortItemVo();
		Query query = buildQueryForTop10(areaUniCode,field,statisticsType,dataType,userid);
		List<AreaBondEconomiesDoc> areaDatalist = mongoTemplate.find(query, AreaBondEconomiesDoc.class);

		//封装结果
		List<AreaIssuerSortItemVo> result = new ArrayList<>();
		areaDatalist.forEach(item -> {
				AreaIssuerSortItemVo areaIssuerSortItemVo = new AreaIssuerSortItemVo();
				
				List<BondEconomieIndicator> indicators = item.getEconomieIndicators();
				for (BondEconomieIndicator ind : indicators) {
					areaIssuerSortItemVo.setValue(ind.getValue());;
				}
				//当前code和areaUniCode
				if(item.getAreaUniCode().equals(areaUniCode)){
					areaIssuerSortItemVo.setSelf(1);
				}
				areaIssuerSortItemVo.setAreaChiName(item.getAreaChiName());
				areaIssuerSortItemVo.setAreaUniCode(item.getAreaUniCode());
				result.add(areaIssuerSortItemVo);
			});
			result.sort(new Comparator<AreaIssuerSortItemVo>() {
			@Override
			public int compare(AreaIssuerSortItemVo o1, AreaIssuerSortItemVo o2) {
				if(o1.getValue() == null){
					o1.setValue( new BigDecimal(0));
				}
				if(o2.getValue() == null){
					o2.setValue( new BigDecimal(0));
				}
				return o2.getValue().compareTo(o1.getValue());
			}
		}); 
		Integer num = null;
		if(result.size() == 0){
			return result;
		}
		for (int i = 0; i< result.size(); i++){
			result.get(i).setIdx(i+1);
			result.get(0).setTotalNum((long) result.size());
			if(Objects.equal(result.get(i).getSelf(), 1)){
				if(result.get(i).getIdx()<=10){
					if(result.size() > 10){
						num = 10;
					}if(result.size() <= 10){
						num = result.size();
					}
				}
				if(result.get(i).getIdx()>10){
						num = 10;
				}
			}
			if(result.size()>0 && result.size() <5){
				return result;
			}
		}
		if(result.size() == 0){
			return result;
			
		}
		return result.subList(0, num);
	}
	
	//区域排名 near5
	public List<AreaIssuerSortItemVo> sortNear5(Long areaUniCode,String field,String statisticsType,String dataType,Long userid){
		//AreaIssuerSortItemVo result = new AreaIssuerSortItemVo();
		Query query = buildQueryForTop10(areaUniCode,field,statisticsType,dataType,userid);
		List<AreaBondEconomiesDoc> areaDatalist = mongoTemplate.find(query, AreaBondEconomiesDoc.class);

		//封装结果
		List<AreaIssuerSortItemVo> result = new ArrayList<>();
		areaDatalist.forEach(item -> {
				AreaIssuerSortItemVo areaIssuerSortItemVo = new AreaIssuerSortItemVo();
				
				List<BondEconomieIndicator> indicators = item.getEconomieIndicators();
				for (BondEconomieIndicator ind : indicators) {
					areaIssuerSortItemVo.setValue(ind.getValue());;
				}
				//当前code和areaUniCode
				if(item.getAreaUniCode().equals(areaUniCode)){
					areaIssuerSortItemVo.setSelf(1);
				}
				areaIssuerSortItemVo.setAreaChiName(item.getAreaChiName());
				areaIssuerSortItemVo.setAreaUniCode(item.getAreaUniCode());
				result.add(areaIssuerSortItemVo);
				
			});
			result.sort(new Comparator<AreaIssuerSortItemVo>() {
			@Override
			public int compare(AreaIssuerSortItemVo o1, AreaIssuerSortItemVo o2) {
				if(o1.getValue() == null){
					o1.setValue( new BigDecimal(0));
				}
				if(o2.getValue() == null){
					o2.setValue( new BigDecimal(0));
				}
				return o2.getValue().compareTo(o1.getValue());
			}
		});
			Integer idxs = null;
			if(result.size() == 0){
				return result;
			}
			for (int i = 0; i< result.size(); i++){
				result.get(i).setIdx(i+1);
				if(Objects.equal(result.get(i).getSelf(), 1)){
					idxs = result.get(i).getIdx();
				}
			}
			for(int i=0;i<result.size();i++){
				if(Objects.equal(result.get(i).getSelf(), 1)){
					if(result.size() - result.get(i).getIdx() == 1){
						return result.subList(idxs-4, idxs+1);
					}
					if(result.size() - result.get(i).getIdx() == 0){
						return result.subList(idxs-5, idxs);
					}
				}
			}
			if(result.size()>0 && result.size()<5){
				return result;
			}
			return result.subList(idxs-3, idxs+2);
		}
	/**
	 * 构建query
	 * @param areaUniCode
	 * @param field
	 * @param statisticsType
	 * @param dataType
	 * @param userid
	 * @return
	 */
	private Query buildQuery(Long areaUniCode, String field, String statisticsType, String dataType, long userid) {
		List<Integer> years =new ArrayList<>();
		Calendar calendar = Calendar.getInstance();
		/** 当前年月 */
		Integer year = calendar.get(Calendar.YEAR);
		Integer month = calendar.get(Calendar.MONTH);
		int quarter	= getQuarter(month);//获取今年该季度之前6期(可能跨三年）
		if(quarter == 1 || Objects.equal(dataType, "年度")){
			years.add(year-1);
			years.add(year-2);
			years.add(year-3);
		}else{
			years.add(year);
			years.add(year-1);
			years.add(year-2);
		}
		//区域
		List<Long> queryMongodbCodes = getAreaCodes(areaUniCode);
		
		//年份
		Query query = new Query();
		if(Objects.equal(dataType, "年度")){
			query.addCriteria(Criteria.where("bondYear").in(years)
					.and("bondMonth").is(null)
					.and("bondQuarter").is(null)
			);
			query.with(new Sort(new Order(Direction.DESC,"bondYear")));
		}
		//季度
		if(Objects.equal(dataType, "季度")){
			query.addCriteria(Criteria.where("bondYear").in(years)
					.and("bondMonth").is(null)
			);
			query.with(new Sort(new Order(Direction.DESC,"bondYear")))
			.with(new Sort(new Order(Direction.DESC,"bondQuarter")));
		}
		//月度
		if(Objects.equal(dataType, "月度")){
			query.addCriteria(Criteria.where("bondYear").in(years)
					.and("bondQuarter").is(null)
			);
			query.with(new Sort(new Order(Direction.DESC,"bondYear")))
			.with(new Sort(new Order(Direction.DESC,"bondMonth")));
		}
		query.addCriteria(Criteria.where("bondYear").in(years)//年份
				.and("areaUniCode").in(queryMongodbCodes)//.and("areaUniCode").is(areaUniCode)
				//.and("areaUniCode").is(areaUniCode)
				.and("statisticsType").is(statisticsType)//年鉴、公报
				.and("dataType").is(dataType)//年、季、月度
		);
		//filter fields
		query.fields()
			.include("areaUniCode")
			.include("bondYear")
			.include("areaChiName")
			.include("bondMonth")
			.include("bondQuarter")
			.elemMatch("economieIndicators", Criteria.where("field").is(field));
		return query;
	}
	
	private Query buildQueryForTop10(Long areaUniCode, String field, String statisticsType, String dataType, long userid) {
		List<Integer> years =new ArrayList<>();
		Calendar calendar = Calendar.getInstance();
		/** 当前年月 */
		Integer year = calendar.get(Calendar.YEAR);
		Integer month = calendar.get(Calendar.MONTH);
		int quarter	= getQuarter(month);//获取今年该季度之前6期(可能跨三年）
		if(quarter == 1 || Objects.equal(dataType, "年度")){
			years.add(year-1);
		}else{
			years.add(year);
		}
		//区域
		List<Long> queryMongodbCodes = getAreaCodes(areaUniCode);
		//年份
		Query query = new Query();
		if(Objects.equal(dataType, "年度")){
			query.addCriteria(Criteria.where("bondYear").in(years)
					.and("bondMonth").is(null)
					.and("bondQuarter").is(null)
			);
			query.with(new Sort(new Order(Direction.DESC,"bondYear")));
		}
		//季度
		if(Objects.equal(dataType, "季度")){
			query.addCriteria(Criteria.where("bondYear").in(years)
					.and("bondMonth").is(null)
					.and("bondQuarter").is(quarter-1)
			);
			query.with(new Sort(new Order(Direction.DESC,"bondYear")))
			.with(new Sort(new Order(Direction.DESC,"bondQuarter")));
		}
		//月度
		if(Objects.equal(dataType, "月度")){
			query.addCriteria(Criteria.where("bondYear").in(years)
					.and("bondQuarter").is(null)
					.and("bondMonth").is(month)
			);
			query.with(new Sort(new Order(Direction.DESC,"bondYear")))
			.with(new Sort(new Order(Direction.DESC,"bondMonth")));
		}
		query.addCriteria(Criteria.where("bondYear").in(years)//年份
				.and("areaUniCode").in(queryMongodbCodes)//
				.and("statisticsType").is(statisticsType)//年鉴、公报
				.and("dataType").is(dataType)//年、季、月度
		);
		//filter fields
		query.fields()
			.include("areaUniCode")
			.include("bondYear")
			.include("areaChiName")
			.include("bondMonth")
			.include("bondQuarter")
			.elemMatch("economieIndicators", Criteria.where("field").is(field));
		return query;
	}
	
	
	public static int getQuarter(int month){     
        int quarter=1;    
        if(month>=1&&month<=3){     
            quarter=1;     
        }     
        if(month>=4&&month<=6){     
            quarter=2;     
        }     
        if(month>=7&&month<=9){     
            quarter=3;      
        }     
        if(month>=10&&month<=12){     
            quarter=4;     
        }
        return quarter;
    }

} 
