package com.innodealing.bond.service.finance;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.google.common.base.Objects;
import com.innodealing.adapter.BondIndicatorAdapter;
import com.innodealing.bond.param.finance.FinanceIndicatorRadarChartParm;
import com.innodealing.bond.param.finance.IndicatorSpecialInstructionsFilter;
import com.innodealing.bond.service.BondInduService;
import com.innodealing.bond.service.user.UserIndicatorFilterService;
import com.innodealing.bond.vo.analyse.BondFinanceInfoVo;
import com.innodealing.bond.vo.analyse.IssFinanceChangeKVo;
import com.innodealing.bond.vo.finance.FinanceIndicatorRadarChartVo;
import com.innodealing.bond.vo.finance.FinanceIndicatorRadarChartVo.RadarIssInduItemVo;
import com.innodealing.bond.vo.finance.IndicatorSpecialIndicatorVo;
import com.innodealing.bond.vo.finance.IssuerSortVo;
import com.innodealing.consts.Constants;
import com.innodealing.engine.jdbc.bond.BondCityAnnexDao;
import com.innodealing.engine.jdbc.bond.BondIndicatorExpressionDao;
import com.innodealing.factory.query.mongo.MongoQueryFactory;
import com.innodealing.model.dm.bond.BondCityAnnex;
import com.innodealing.model.mongo.dm.bond.detail.analyse.IssIndicatorsDoc;
import com.innodealing.model.mongo.dm.bond.detail.analyse.IssKeyIndicatorDoc;
import com.innodealing.model.mongo.dm.bond.finance.FinanceIndicator;
import com.innodealing.model.mongo.dm.bond.finance.IssFinanceIndicators;
import com.innodealing.model.mongo.dm.bond.user.UserIndicatorFilter;
import com.innodealing.util.SafeUtils;
import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 * 财务指标分析
 * 
 * @author zhaozhenglai
 * @date 2017年2月10日下午3:14:51 Copyright © 2016 DealingMatrix.cn. All Rights
 *       Reserved.
 */
@Service
public class IndicatorAnalysisService {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	BondFinanceInfoService bondFinanceInfoService;

	@Autowired
	private BondInduService bondInduService;

	@Autowired 
    private BondIndicatorExpressionDao dondIndicatorExpressionDao;

	@Autowired 
    private UserIndicatorFilterService userIndicatorFilterService;
	
	@Autowired 
    private BondCityAnnexDao bondCityAnnexDao;
	
	private Logger log = LoggerFactory.getLogger(IndicatorAnalysisService.class);
	
	
	/**查询所有城投的comUniCode
	 * @return
	 */
	public List<Long> getComUniCode(){
		List<BondCityAnnex> comUniCode = bondCityAnnexDao.findAllComUniCode();
		List<Long> comUniCodeAll = new ArrayList<>();
		for (BondCityAnnex bondCityAnnex : comUniCode) {
			comUniCodeAll.add(bondCityAnnex.getComUniCode());
		}
		return comUniCodeAll;
	}
	/**
	 * 首页
	 * 
	 * @param filter
	 *            财务专项指标过滤条件{@link IndicatorSpecialInstructionsFilter}
	 * @return
	 */
	public List<IndicatorSpecialIndicatorVo> findIssuerIndicators(Long userId, IndicatorSpecialInstructionsFilter filter) {
		
		//保存用户筛选条件
		if(Objects.equal(filter.getIsSpecial(), Constants.TRUE) && filter.getFields() != null){
			UserIndicatorFilter userIndicatorFilter = userIndicatorFilterService.buildUserIndicatorFilter(userId, filter.getIssId(), filter.getFields());
			userIndicatorFilterService.save(userIndicatorFilter);
		}
		
		List<IssIndicatorsDoc> result = null;
		Query query = MongoQueryFactory.newInstance().getQuery(filter);
		if(filter.getIsSpecial() == Constants.TRUE){
			// query from mongoDb
			IssKeyIndicatorDoc indicatorDoc = mongoTemplate.findOne(new Query(Criteria.where("issId").is(filter.getIssId())), IssKeyIndicatorDoc.class);
			result = bondFinanceInfoService.IssKeyIndicatorsToIssIndicatorsDocs(indicatorDoc);
			result = bondFinanceInfoService.filterIndicatorFields(result, filter.getFields());//过滤指标
		}else{
			IssFinanceIndicators issFinanceIndicators = mongoTemplate.findOne(query, IssFinanceIndicators.class);
			//是否为空
			if(issFinanceIndicators == null) {
				return new ArrayList<>();
			}else{
				result = new ArrayList<>();
				//IssFinanceIndicators 转为 IssIndicatorsDoc
				IssFinanceIndicatorsToIssIndicatorsDocs(result, issFinanceIndicators);
			}
		}

		// do rebuild result
		List<IndicatorSpecialIndicatorVo> resultBuild =  rebuild(result, filter);
		//按分组排序
		sortGroup(resultBuild);
		return  resultBuild;
	}

	
	/**
	 * 专项指标K线图接口
	 * @param issuerId
	 * @param field
	 * @param userId
	 * @param isSpecail
	 * @return
	 */
	public List<IssFinanceChangeKVo> findKchart(Long issuerId, String field, Long userId) {
		
		// 当前主体信息
		/*IssIndicatorsDoc issIndicatorsDoc = mongoTemplate.findOne(new Query(Criteria.where("issId").is(issuerId).and("field").is(field)),
				IssIndicatorsDoc.class);*/
		IssKeyIndicatorDoc indicatorDoc =  mongoTemplate.findOne(new Query(Criteria.where("issId").is(issuerId)), IssKeyIndicatorDoc.class);
		List<IssIndicatorsDoc> issIndicatorsList = bondFinanceInfoService.IssKeyIndicatorsToIssIndicatorsDocs(indicatorDoc);
		IssIndicatorsDoc issIndicatorsDoc = bondFinanceInfoService.getIndicator(issIndicatorsList, field);
		if(issIndicatorsDoc == null){
			return new ArrayList<>();
		}
		List<IssIndicatorsDoc> list = findInduIndicatorList(userId, issIndicatorsDoc, Constants.TRUE,Constants.TRUE);
		//构建数据并返回
		return bondFinanceInfoService.buildIssFinanceChangeVo( issIndicatorsDoc, list);
		
	}
	
	/**
	 * 财务指标K线图接口
	 * @param issuerId
	 * @param field
	 * @param userId
	 * @return
	 */
	public List<IssFinanceChangeKVo> findKchartForFinanceIndicator(Long issuerId, String field, Long userId){
		Query query = new Query(Criteria.where("issId").is(issuerId));
		//query.fields().include("induId").include("induIdSw");
		IssFinanceIndicators issFinanceIndicators = mongoTemplate.findOne(query, IssFinanceIndicators.class);
		
//		boolean isGicsInduClass =  bondInduService.isGicsInduClass(userId);
//		Long induId = isGicsInduClass ? issFinanceIndicators.getInduId() : issFinanceIndicators.getInduIdSw();
//		String induKey = isGicsInduClass ? "induId" : "induIdSw";
		
		List<Long> comUniCodes = bondInduService.findComUniCodeByIndu(issuerId, userId);
		
		List<IssFinanceIndicators>  listIssFinanceIndicators = mongoTemplate.find(new Query(Criteria.where("issId").in(comUniCodes)), IssFinanceIndicators.class);
		//将IssFinanceIndicators 转 IssIndicatorsDoc
		IssIndicatorsDoc issIndicatorsDoc = issFinanceIndicatorsToIssIndicatorsDoc(field, issFinanceIndicators);
		//获取行业指标
		List<BigDecimal> induIndicators = getInduIdicators(field, listIssFinanceIndicators);
		return bondFinanceInfoService.buildIssFinanceChangeVo(induIndicators, issIndicatorsDoc);
	}

	
	
	/**
	 * 获取行业前十
	 * @param issuerId
	 * @param userId
	 * @param field
	 * @param isSpecial
	 * @return 
	 */
	public List<IssuerSortVo> top10InIndu(Long issuerId, Long userId, String field, int isSpecial,int isInvestment){
		
		// 当前主体行业信息
		List<IssIndicatorsDoc> list = findInduIndicatorList(issuerId, field, userId, isSpecial,isInvestment);
		//list = filterNullIndicators(issuerId, list);
        //获取top10
		List<IssuerSortVo> top10  =top10(sort(list));
		//标记自己
		markOneself(top10, issuerId);
        return top10;
	}
	
	/**
	 * 获取当前主体的
	 * @param issuerId
	 * @param userId
	 * @param field
	 * @param isSpecial
	 * @return
	 */
	public List<IssuerSortVo> nearCurrentIssuer5(Long issuerId, Long userId, String field, int isSpecial,int isInvestment){
		// 当前主体信息
		IssIndicatorsDoc issIndicatorsDoc = null;
		if(isSpecial ==  Constants.TRUE){
			IssKeyIndicatorDoc indicatorDoc = mongoTemplate.findOne(new Query(Criteria.where("issId").is(issuerId)), IssKeyIndicatorDoc.class);
			issIndicatorsDoc = bondFinanceInfoService.getIndicator(
					bondFinanceInfoService.IssKeyIndicatorsToIssIndicatorsDocs(indicatorDoc), field);
		}else{
			Query query =new Query(Criteria.where("issId").is(issuerId));
			issIndicatorsDoc = issFinanceIndicatorsToIssIndicatorsDoc(field, mongoTemplate.findOne(query, IssFinanceIndicators.class));
		}
		if(issIndicatorsDoc.getIndicators() == null || issIndicatorsDoc.getIndicators().size() ==0 
				|| issIndicatorsDoc.getIndicators().get(0) == null){
			return new ArrayList<>();
		}
		// 当前主体行业信息
		List<IssIndicatorsDoc> list = findInduIndicatorList(userId, issIndicatorsDoc, isSpecial,isInvestment);
		//去掉没有当季度的数据
		//list = filterNullIndicators(issuerId, list);
		//排序
		List<IssuerSortVo> sortedList = sort(list);
		//标记自己
		markOneself(sortedList, issuerId);
		//选取自己附近5个主体
		List<IssuerSortVo> result = getNear5(sortedList);
		
		//自己数据为空时返回空集合
		boolean isContainMyselfe = false;
		for (IssuerSortVo issuerSortVo : result) {
			if(issuerSortVo.getOneself() == Constants.TRUE){
				isContainMyselfe = true;
			}
		}
		if(!isContainMyselfe){
			result = new ArrayList<>();
		}
		
		return result;
	}

	/**
	 * 去掉没有指标的数据
	 * @param issuerId
	 * @param list
	 * @return
	 */
//	private List<IssIndicatorsDoc> filterNullIndicators(Long issuerId, List<IssIndicatorsDoc> list) {
//		String lastQuarter = null;
//		for (IssIndicatorsDoc issIndicators : list) {
//			if(issIndicators.getIssId().equals(issuerId)){
//				lastQuarter = issIndicators.getQuarters().get(0);
//				break;
//			}
//		}
//		List<IssIndicatorsDoc> temp = new ArrayList<>();
//		for (IssIndicatorsDoc issIndicators : list) {
//			if(issIndicators.getQuarters().contains(lastQuarter)){
//				temp.add(issIndicators);
//			}
//		}
//		list = temp;
//		return list;
//	}

	
	
	/**
	 * 到处excel msg
	 * @param request
	 * @param response
	 * @param filter
	 * @return
	 */
	public boolean reportMsg(HttpServletRequest request, HttpServletResponse response,IndicatorSpecialInstructionsFilter filter){
		try {
			//filter.setIsSpecial(0);
			WritableWorkbook book = null;
			book = Workbook.createWorkbook(response.getOutputStream());
			//創建工作表
			WritableSheet ws = book.createSheet("sheet 1", 0);
			List<IndicatorSpecialIndicatorVo>  list = findIssuerIndicators(null,filter);
			if(list.size() ==0 || list.get(0).getQuarters() == null || list.get(0).getQuarters().size() == 0){
				return false;
			}
			return true;
		} catch (Exception e) {
			log.error("reportMsg error!" + e.getMessage(), e);
			return false;
		}
	}
	
	/**
	 * 导出excel
	 * @param request
	 * @param response
	 * @param filter
	 * @return
	 */
	public boolean report(HttpServletRequest request, HttpServletResponse response,IndicatorSpecialInstructionsFilter filter){
		try {
			//filter.setIsSpecial(0);
			WritableWorkbook book = null;
			book = Workbook.createWorkbook(response.getOutputStream());
			//創建工作表
			WritableSheet ws = book.createSheet("sheet 1", 0);
			List<IndicatorSpecialIndicatorVo>  list = findIssuerIndicators(null,filter);
			//专项指标单位处理
			if(filter.getIsSpecial() == Constants.TRUE){
				BondIndicatorAdapter.doFormatterIndicator(list);
			}
			if(list.size() ==0 || list.get(0).getQuarters() == null || list.get(0).getQuarters().size() == 0){
				return false;
			}
			//初始化开始年份和结束年份
			Integer[] startEndYear = BondIndicatorAdapter.initStartEndYear(filter);
			Integer startYear = startEndYear[0];
			Integer endYear =  startEndYear[1];
			
			List<String> quarterss = list.get(0).getQuarters();
			String fileName = "DM_" + list.get(0).getIssuserName() + startYear + "年到" + endYear + "年财务数据.xls";
			response.setContentType("application/octet-stream");
			response.setCharacterEncoding("utf-8");
			response.setHeader("Content-Disposition", "inline; filename=" + URLEncoder.encode(fileName, "utf-8"));
			//表格季度
			int quarterStartClo = 3;
			for (int i = 0; i < quarterss.size(); i++) {
				String quarter = quarterss.get(i)
						.replace("Q1", "一季报")
						.replace("Q2", "中报")
						.replace("Q3", "三季报")
						.replace("Q4", "年报");
				Label label = new Label(quarterStartClo + i, 0,quarter );
				ws.addCell(label);
			}
			//表格指标和分组
			boolean hasParentGroup = false;
			for (int j = 0; j < list.size() ; j ++ ) {
				IndicatorSpecialIndicatorVo  indicatorSpecialIndicatorVo  =  list.get(j);
				List<BigDecimal> indicaotrs = indicatorSpecialIndicatorVo.getValues();
				if(indicaotrs == null){
					indicaotrs = new ArrayList<>();
					for (int i = 0; i < indicatorSpecialIndicatorVo.getQuarters().size(); i++) {
						indicaotrs.add(null);
					}
				}
				for(int i = 0; i < indicaotrs.size(); i ++){
					BigDecimal value = indicaotrs.get(i);
					String parentGroup = SafeUtils.getString(indicatorSpecialIndicatorVo.getCategoryNameParent());
					if(!"".equals(SafeUtils.getString(parentGroup))){
						hasParentGroup = true;
					}
					ws.addCell(new Label(quarterStartClo - 3, j+1, parentGroup));
					ws.addCell(new Label(quarterStartClo - 2, j+1, SafeUtils.getString(indicatorSpecialIndicatorVo.getCategoryName())));
					ws.addCell(new Label(quarterStartClo - 1, j+1, SafeUtils.getString(indicatorSpecialIndicatorVo.getFieldName())));
					ws.addCell(new Label(i + quarterStartClo, j+1, SafeUtils.getString(value)));
				}
			}
			if(!hasParentGroup){
				ws.removeColumn(0);
			}
			int[] colToMerge = hasParentGroup ? new int[]{0, 1} : new int[]{0};
			//合并相同组的数据分类列
			for (int i : colToMerge) {
				mergeGroup(ws, i);
			}
			book.write();
			book.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	
	/**
	 * 
	 * @param radarChartParm
	 * @return
	 */
	public List<FinanceIndicatorRadarChartVo> indicatorRadarChart(FinanceIndicatorRadarChartParm radarChartParm){
		
		Long issuerId = radarChartParm.getIssuerId(); 
		Long userId = radarChartParm.getUserId(); 
		Integer standardYear = radarChartParm.getStandardYear();
		Integer standardQuarter = radarChartParm.getStandardQuarter(); 
		Integer compareYear = radarChartParm.getCompareYear(); 
		Integer compareQuarter = radarChartParm.getCompareQuarter(); 
		List<Long> provinceIds = radarChartParm.getProvinceIds(); 
		IssKeyIndicatorDoc issKeyIndicatorDoc = mongoTemplate.findOne(new Query(Criteria.where("issId").is(issuerId)), IssKeyIndicatorDoc.class);
		List<String> fields = radarChartParm.getFields() == null ? getFields(issKeyIndicatorDoc) : radarChartParm.getFields();
		
		//行业指标
		//TODO 2.68s 需优化
		long t1 = System.currentTimeMillis();
		List<IssIndicatorsDoc> induList  = findInduIndicatorList(userId, issKeyIndicatorDoc, provinceIds);
		long e1 = System.currentTimeMillis();
		System.out.println("findInduIndicatorList-->" + (e1 -t1));
		//对应季度的list下标值
		List<String> quarterList = issKeyIndicatorDoc.getQuarters();
		int indexStandard = getQuarterIndex(standardYear, standardQuarter, quarterList);
		long t2 = System.currentTimeMillis();
		if(fields.size() > 20){
			fields = fields.subList(0, 20);
			induList = bondFinanceInfoService.filterIndicatorFields(induList, fields);
		}
		//TODO 66.4s
		Map<String,RadarIssInduItemVo> fieldRadarMappingStandard = getRadarInduIss(issKeyIndicatorDoc, indexStandard, induList);
		long e2 = System.currentTimeMillis();
		System.out.println("getRadarInduIss-->" + (e2 -t2));
		int indexCompare = getQuarterIndex(compareYear, compareQuarter, quarterList);
		long t3 = System.currentTimeMillis();
		//TODO 71.27s需要优化 
		Map<String,RadarIssInduItemVo> fieldRadarMappingCompare = getRadarInduIss(issKeyIndicatorDoc, indexCompare, induList);
		long e3 = System.currentTimeMillis();
		System.out.println("getRadarInduIss 2-->" + (e3 -t3));
		//封装结果集
		List<FinanceIndicatorRadarChartVo> result = new ArrayList<>();
		
		for (String field : fields) {
			FinanceIndicatorRadarChartVo item = new FinanceIndicatorRadarChartVo();
			RadarIssInduItemVo standard = fieldRadarMappingStandard.get(field);
			RadarIssInduItemVo compare = fieldRadarMappingCompare.get(field);
			item.setFieldName(standard == null ? null : standard.getFieldName());
			if(compare != null){
				item.setValueInduCompare(compare.getValueIndu());
				item.setValueIssCompare(compare.getValueIss());
				result.add(item);
			}
			if(standard != null){
				item.setValueInduStandard(standard.getValueIndu());
				item.setValueIssStandard(standard.getValueIss());
				item.setNegative(standard.getNegative());		
			}
			if(result.size() == 20)break;
		}
		return result;
	}

	

	/**
	 * 获取主体的财报时间
	 * @param issuserId
	 * @return
	 */
	public List<String> getIndicatorFinDate(Long issuerId){
		IssKeyIndicatorDoc issIndicatorsDoc = mongoTemplate.findOne(new Query(Criteria.where("issId").is(issuerId)), IssKeyIndicatorDoc.class);
		List<String> quarters = issIndicatorsDoc.getQuarters();
		List<String> result = new ArrayList<>();
		for (int i = 0; i < quarters.size(); i++) {
			String q = quarters.get(i).replace("/Q", "-");
			result.add(q);
		}
		return result;
	}
	
	
	/**
	 * 行业对比
	 * @param issuerId
	 * @param userId
	 * @param year
	 * @param quarter
	 * @param provinceIds
	 * @return
	 */
	public List<BondFinanceInfoVo> compareToIndu(Long issuerId,Long userId, Integer year, Integer quarter, List<Long> provinceIds, IndicatorSpecialInstructionsFilter filter){
		List<BondFinanceInfoVo> list = bondFinanceInfoService.findBondFinanceInfoDoc(issuerId, provinceIds, userId,year, quarter, filter);
		return list;
	}


	/**
	 * 获取所有的指标code
	 * @param fields
	 * @param issKeyIndicatorDoc
	 * @return
	 */
	private List<String> getFields( IssKeyIndicatorDoc issKeyIndicatorDoc) {
		List<String> fields = new ArrayList<>(); 
		if(fields == null || fields.size() == 0){
			fields = new ArrayList<>();
			List<FinanceIndicator> list = issKeyIndicatorDoc.getIndicators();
			for (FinanceIndicator issIndicatorsDoc : list) {
				fields.add(issIndicatorsDoc.getField());
			}
		}
		return fields;
	}
	
	/**
	 * 合并分组
	 * @param ws
	 * @param colToMerge
	 * @throws WriteException
	 * @throws RowsExceededException
	 */
	private void mergeGroup(WritableSheet ws, int colToMerge) throws WriteException, RowsExceededException {
		int rows = ws.getRows();
		String group = null;
		int col1 = colToMerge, row1 = 1, col2 = colToMerge, row2 = 0;
		for (int row = 1; row < rows; row++) {
			String currentGroup = ws.getCell(colToMerge, row).getContents();
			if(group == null){
				group = currentGroup;
			}
			if(!currentGroup.equals(group)){
				group = currentGroup;
				row2 = row - 1;
				ws.mergeCells(col1, row1, col2, row2);
				row1 = row;
			}
			if(row == (rows - 1)){
				row2 = row;
				ws.mergeCells(col1, row1, col2, row2);
			}
		}
	}
	
	
	
	/**
	 * 获取对应季度的list下标值
	 * @param standardYear
	 * @param standardQuarter
	 * @param quarterList
	 * @return
	 */
	public int getQuarterIndex(Integer standardYear, Integer standardQuarter, List<String> quarterList) {
		String quarter = standardYear + "/Q" + standardQuarter;
		int index = quarterList.indexOf(quarter);
		return index;
	}
	
	/**
	 * 发行人所在行业发行人总数量
	 * @param issuerId
	 * @param userId
	 * @param field
	 * @return
	 */
	public long findInduIssCount(Long issuerId, Long userId, String field,int isInvestment){
		Query query = new Query(Criteria.where("issId").is(issuerId));
		//锅里条件
		query.fields()
			.include("induId")
			.include("induIdSw")
			.include("quarters")
			.include("orgType");
		//筛选最新季度
//		query.fields()
//			.slice("quarters", 1);
		IssKeyIndicatorDoc indicatorDoc =  mongoTemplate.findOne(query, IssKeyIndicatorDoc.class);
		//分类处理
		
		
//		boolean isGicsInduClass =  bondInduService.isGicsInduClass(userId);
//		Long induId = isGicsInduClass ? indicatorDoc.getInduId() : indicatorDoc.getInduIdSw();
//		String induKey = isGicsInduClass ? "induId" : "induIdSw";
		String quarterLast = indicatorDoc.getQuarters().get(0);
		List<Long> comUniCodes = bondInduService.findComUniCodeByIndu(issuerId, userId);
		if(isInvestment == Constants.TRUE){
			List<Long> comUniCodeAll = getComUniCode();
        	//List<Long> comUniCodeAll = new ArrayList<>();
        	//comUniCodeAll.add((long) 200035803);
        	for(int i = 0;i< comUniCodes.size();i++){
    			if(comUniCodeAll.contains(comUniCodes.get(i))){
    				comUniCodes.remove(i);
    			}
    		}
		}
		
		
		String jsonCommand = 
		        "{"+
		         "   $project:{"+
		         // "      "+ induKey+":1,"+
		           "     indicators:{"+
		            "        $filter:{"+
		             "           input:'$indicators',"+
		              "          as:'indicaotr',"+
		               "         cond:{$eq:['$$indicaotr.field','" + field + "']}"+
		                 "   }"+
		               " },"+
		               " quarters:1"+
		            "}"+
		        "}";
		//$match command
		String matchC="{"+
		         	//"$match:{  "+ induKey +" : " + induId + "}"+
		         	"$match:{  \"_id\" :{$in:"  + comUniCodes.toString() + " }}"+
		        "}";   
		List<DBObject> pipeline = new ArrayList<>();
		DBObject project = (DBObject) JSON.parse(jsonCommand);
		DBObject match = (DBObject) JSON.parse(matchC);
		pipeline.add(project);
		pipeline.add(match);
		System.out.println( "pipeline->" + pipeline);
		AggregationOutput  output = mongoTemplate.getCollection("iss_key_ndicator").aggregate(pipeline);
		Iterator<DBObject> iterator = output.results().iterator();
		//遍历筛选符合条件的主体数量 size
		long size = 0;
		while (iterator.hasNext()) {
			BasicDBList indicators = null;
			BasicDBObject indicator = null;
			DBObject next = iterator.next();
			if(next.get("indicators") != null){
				indicators = (BasicDBList) next.get("indicators");
			}else{
				continue;
			}
			if(indicators.size() > 0 && indicators.get(0) != null){
				indicator = (BasicDBObject) indicators.get(0);
			}else{
				continue;
			}
			BasicDBList indicatorValues = (BasicDBList) indicator.get("indicators");
			BasicDBList quarters = (BasicDBList) next.get("quarters");
			if(quarters == null || indicatorValues == null){
				continue;
			}
			int index = quarters.indexOf(quarterLast);
			if(!(index == -1 || indicatorValues.get(index) == null)){
				size ++;
			}	
		}
		return size;
	}
	

	/**
	 * 获取主体和行业指标的radar值
	 * @param issKeyIndicatorDoc
	 * @param indexToView
	 * @param induList
	 * @return
	 */
	private Map<String,RadarIssInduItemVo> getRadarInduIss(IssKeyIndicatorDoc issKeyIndicatorDoc, int indexToView,
			List<IssIndicatorsDoc> induList) {
		//行业field指标分组化
		long s = System.currentTimeMillis();
		Map<String,List<BigDecimal>> indicatorsMap = bondFinanceInfoService.groupInduIndicators(indexToView, induList);
		long e = System.currentTimeMillis();
		System.out.println("indicatorsMap-->" + (e-s));
		//计算每个指标
		Map<String,RadarIssInduItemVo> fieldRadarMapping = new HashMap<>();
		for (FinanceIndicator issIndicator : issKeyIndicatorDoc.getIndicators()) {
			List<BigDecimal> indicatorList = indicatorsMap.get(issIndicator.getField());
			BigDecimal induin10 = SafeUtils.getQuantile(indicatorList, 10, issIndicator.getNegative());//行业10分位
            BigDecimal induin50 = SafeUtils.getQuantile(indicatorList, 50, issIndicator.getNegative());//行业50分位
            BigDecimal induin90 = SafeUtils.getQuantile(indicatorList, 90, issIndicator.getNegative());//行业90分位
            BigDecimal valueIss =  null;
            BigDecimal valueIndu = null;
            if(!(induin90 == null || induin10 == null)){
            	BigDecimal divisor = (induin90.subtract(induin10));//除数因子
            	
            	//技算主体和行业的radar值
            	BigDecimal issV = null;
            	if(!(issIndicator.getIndicators().size() < (indexToView + 1 )) && indexToView != -1){
            		issV = issIndicator.getIndicators().get(indexToView);
            	}
            
            	if(divisor.doubleValue() == 0 ){
            		if(issV != null){
            			valueIss = BigDecimal.ONE;
            			valueIndu = BigDecimal.ONE;
            		}
            		//if(induin50.doubleValue() != 0 && !induin50.equals(induin10)){
            			
            		//}
            	}else{
            		if(!(issV == null || induin10 ==null)){
            			valueIss =issV.subtract(induin10).divide(divisor, 2, BigDecimal.ROUND_HALF_UP);
            		}
            		if(!(induin50 == null || induin10 ==null)){
            			valueIndu = induin50.subtract(induin10).divide(divisor, 2, BigDecimal.ROUND_HALF_UP);
            		}
            	}
            }
            //格式化雷达值,保证在[0-1]范围内
            valueIndu = BondIndicatorAdapter.radarValueFormatter(valueIndu);
            valueIss =  BondIndicatorAdapter.radarValueFormatter(valueIss);
            RadarIssInduItemVo radarIssInduItemVo = new FinanceIndicatorRadarChartVo(). new RadarIssInduItemVo();
            radarIssInduItemVo.setValueIndu(valueIndu);
            radarIssInduItemVo.setValueIss(valueIss);
            radarIssInduItemVo.setFieldName(issIndicator.getFieldName());
            radarIssInduItemVo.setNegative(issIndicator.getNegative());
            fieldRadarMapping.put(issIndicator.getField(), radarIssInduItemVo);
		}
		return fieldRadarMapping;
	}
	
	
	/**
	 * 重新构建结构
	 * 
	 * @param result
	 * @return
	 */
	private List<IndicatorSpecialIndicatorVo> rebuild(List<IssIndicatorsDoc> result,
			IndicatorSpecialInstructionsFilter filter) {
		Integer[] startEndYear = BondIndicatorAdapter.initStartEndYear(filter);
		Integer startYear = startEndYear[0];
		Integer endYear =  startEndYear[1];
		//初始化开始年份和结束年份
		// 过滤时间
		filterTime(result, startYear, endYear);
		// 过滤季度
		// 财报时间{1:一季度,2:二季度(中报),3:三季度,4:四季度(年报) }
		Integer[] financialReportTypeFilter = filter.getFinancialReportType();
		filterQuarter(result, financialReportTypeFilter);
		//专项指标表达式汉子描述
        Map<String,String> fieldExpressMap = dondIndicatorExpressionDao.findExpressDescription();
		// 封装结果
		List<IndicatorSpecialIndicatorVo> list = new ArrayList<>();
		for (IssIndicatorsDoc indicator : result) {
			IndicatorSpecialIndicatorVo pecialInstructionsVo = new IndicatorSpecialIndicatorVo();
			pecialInstructionsVo.setCategoryName(indicator.getCategory());
			pecialInstructionsVo.setFieldName(indicator.getFieldName());
			pecialInstructionsVo.setQuarters(indicator.getQuarters());
			pecialInstructionsVo.setValues(indicator.getIndicators());
			pecialInstructionsVo.setNegative(indicator.getNegative());
			pecialInstructionsVo.setField(indicator.getField());
			pecialInstructionsVo.setCategoryNameParent(indicator.getCategoryParent());
			pecialInstructionsVo.setIssuserName(indicator.getIssName());
			pecialInstructionsVo.setPercent(indicator.getPercent());
			pecialInstructionsVo.setExpressDescription(fieldExpressMap.get(indicator.getField()));
			if(filter.getTabIndex() != 0){
				if(filter.getTabIndex().equals(indicator.getType()))
					list.add(pecialInstructionsVo);
			}else{
				list.add(pecialInstructionsVo);
			}
		}
		return list;
	}

	/**
	 * 时间过滤
	 * 
	 * @param result
	 * @param startYear
	 * @param endYear
	 */
	private void filterTime(List<IssIndicatorsDoc> result, Integer startYear, Integer endYear) {
		for (IssIndicatorsDoc issIndicatorsDoc : result) {
			if(issIndicatorsDoc.getIndicators() == null){
				continue;
			}
			List<String> quarters = issIndicatorsDoc.getQuarters();
			int index = -1;
			List<String> quartersMatch = new ArrayList<>();
			List<BigDecimal> indicatorsMatch = new ArrayList<>();
			for (String quarter : quarters) {
				String year = quarter.split("/")[0];
				index ++;
				if(!(startYear > SafeUtils.getInt(year) || endYear < SafeUtils.getInt(year))){
					quartersMatch.add(issIndicatorsDoc.getQuarters().get(index));
					indicatorsMatch.add(issIndicatorsDoc.getIndicators().get(index));
				}
				if(index == (issIndicatorsDoc.getIndicators().size() - 1)){
					break;
				}
			}
			issIndicatorsDoc.setIndicators(indicatorsMatch);
			issIndicatorsDoc.setQuarters(quartersMatch);
		}
	}

	/**
	 * 季度过滤
	 * 
	 * @param result
	 * @param financialReportTypeFilter
	 */
	private void filterQuarter(List<IssIndicatorsDoc> result, Integer[] financialReportTypeFilter) {
		List<Integer>  financialReportTypeFilterList = Arrays.asList(financialReportTypeFilter);
		 for (IssIndicatorsDoc issIndicatorsDoc : result) {
			 if(issIndicatorsDoc.getIndicators() == null){
					continue;
			 }
			 List<String> quartersMatch = new ArrayList<>();
			 List<BigDecimal> indicatorsMatch = new ArrayList<>();
			 List<String> quarters = issIndicatorsDoc.getQuarters();
			 int index = -1;
			 for (String quarter : quarters) {
				 String q = quarter.split("/")[1].replace("Q", "");
				 index ++;
				 if(financialReportTypeFilterList.contains(SafeUtils.getInt(q))){
					 quartersMatch.add(issIndicatorsDoc.getQuarters().get(index));
					 indicatorsMatch.add(issIndicatorsDoc.getIndicators().get(index));
				 }
				if(index == (issIndicatorsDoc.getIndicators().size() - 1)){
					break;
				}
			 }
			 issIndicatorsDoc.setIndicators(indicatorsMatch);
			 issIndicatorsDoc.setQuarters(quartersMatch);
		}
	}
	
	/**
	 * 排序
	 * @param list
	 * @return 排序后的主体名称集合
	 */
	private List<IssuerSortVo> sort( List<IssIndicatorsDoc> list){
		//消灭null值
		List<IssIndicatorsDoc> temp = new ArrayList<>();
		for (IssIndicatorsDoc issIndicatorsDoc : list) {
			if(issIndicatorsDoc.getIndicators() == null || issIndicatorsDoc.getIndicators().size() == 0 
					|| issIndicatorsDoc.getIndicators().get(0) == null){
				continue ;
			}else{
				temp.add(issIndicatorsDoc);
			}
		}
		Collections.sort(temp, new IssIndicatorsDocComparator());
		//结果
		List<IssuerSortVo> listSort = new ArrayList<>();
		int indexSort = 1;
		for (IssIndicatorsDoc issIndicatorsDoc : temp) {
			IssuerSortVo issuerSortVo = new IssuerSortVo();
			issuerSortVo.setIssuerName(issIndicatorsDoc.getIssName());
			issuerSortVo.setIssuerId(issIndicatorsDoc.getIssId());
			issuerSortVo.setIdx(indexSort);
			listSort.add(issuerSortVo);
		}
		//负向指标顺序取反
		if(list != null && list.size()>0 && list.get(0).getNegative() == Constants.TRUE){
			List<IssuerSortVo> listSortNegative = new ArrayList<>();
			for (int i = listSort.size() -1 ; i > -1; i--) {
				listSortNegative.add(listSort.get(i));
			}
			listSort = listSortNegative;
		}
		//标记位置
		for (int i = 0; i < listSort.size(); i++) {
			listSort.get(i).setIdx(i + 1);
		}
		return listSort;
	}
	
	/**
	 * 获取前十
	 * @param sortList
	 * @return
	 */
	private List<IssuerSortVo> top10(List<IssuerSortVo> sortList){
		if(sortList == null) return null;
		if(sortList.size() < 10) 
			return sortList;
		else
			return sortList.subList(0, 10);
	}
	
	
	/**
	 * 获取行业指标
	 * @param issuerId
	 * @param field
	 * @param userId
	 * @return
	 */
	private List<IssIndicatorsDoc> findInduIndicatorList(Long issuerId, String field, Long userId, int isSpecail,int isInvestment) {
		
		// 当前主体信息
		IssIndicatorsDoc issIndicatorsDoc = null;
		if(isSpecail ==  Constants.TRUE ){
			IssKeyIndicatorDoc indicatorDoc = mongoTemplate.findOne(new Query(Criteria.where("issId").is(issuerId)), IssKeyIndicatorDoc.class);
			issIndicatorsDoc = bondFinanceInfoService.getIndicator(
					bondFinanceInfoService.IssKeyIndicatorsToIssIndicatorsDocs(indicatorDoc), field);
		}else{
			Query query =new Query(Criteria.where("issId").is(issuerId));
			issIndicatorsDoc = issFinanceIndicatorsToIssIndicatorsDoc(field, mongoTemplate.findOne(query, IssFinanceIndicators.class));
		}
		return findInduIndicatorList(userId, issIndicatorsDoc, isSpecail,isInvestment);
	}
	
	/**
	 * 获取行业指标
	 * @param userId
	 * @param issIndicatorsDoc
	 * @return
	 */
	private List<IssIndicatorsDoc> findInduIndicatorList(Long userId, IssIndicatorsDoc issIndicatorsDoc, int isSpecial,int isInvestment) {
		
		List<Long> comUniCodes = bondInduService.findComUniCodeByIndu(issIndicatorsDoc.getIssId(), userId);
		
		
		
		/*boolean isGicsInduClass =  bondInduService.isGicsInduClass(userId);
		Long induId = isGicsInduClass ? issIndicatorsDoc.getInduId() : issIndicatorsDoc.getInduIdSw();
		String induKey = isGicsInduClass ? "induId" : "induIdSw";*/
		
		
		
		String field = issIndicatorsDoc.getField();
        List<IssIndicatorsDoc> list = null;
        //是否为专项指标，是进行专项指标操作，不是按照一般财务指标操作
        if(isSpecial == Constants.TRUE && isInvestment == Constants.TRUE){
        	//所有城投的ComUniCode 200081067
        	List<Long> comUniCodeAll = getComUniCode();
//        	log.info("comUniCodeAll==="+comUniCodeAll);
//        	for(int i = 0;i< comUniCodes.size();i++){
//    			if(comUniCodeAll.contains(comUniCodes.get(i))){
//    				comUniCodes.remove(i);
//    				i--;
//    				log.info("comUniCodes.remove(i)==="+comUniCodes.remove(i));
//    			}
//    		}
        	List<IssKeyIndicatorDoc> indicatorDocList = mongoTemplate.find(
        			new Query(Criteria.where("issId").in(comUniCodes).nin(comUniCodeAll)),
        			IssKeyIndicatorDoc.class);
        	list = bondFinanceInfoService.IssKeyIndicatorsToIssIndicatorsDocsList(indicatorDocList);
        	list = bondFinanceInfoService.filterIndicatorFields(list, 
        			bondFinanceInfoService.toList(field));
        }
        else if(isSpecial == Constants.TRUE && isInvestment == Constants.FALSE){
        	
        	List<IssKeyIndicatorDoc> indicatorDocList = mongoTemplate.find(
        			new Query(Criteria.where("issId").in(comUniCodes)),
        			IssKeyIndicatorDoc.class);
        	list = bondFinanceInfoService.IssKeyIndicatorsToIssIndicatorsDocsList(indicatorDocList);
        	list = bondFinanceInfoService.filterIndicatorFields(list, 
        			bondFinanceInfoService.toList(field));
        }
        else{
    		List<IssFinanceIndicators>  listIssFinanceIndicators = mongoTemplate.find(new Query(Criteria.where("issId").in(comUniCodes)), IssFinanceIndicators.class);
    		//将IssFinanceIndicators 转 IssIndicatorsDoc
    		list = new ArrayList<>();
    		for (IssFinanceIndicators issFinanceIndicator : listIssFinanceIndicators) {
    			if(issFinanceIndicator == null || issFinanceIndicator.getIndicators() == null){
    				continue;
    			}
    			list.add(issFinanceIndicatorsToIssIndicatorsDoc(field, issFinanceIndicator));
			}
        }
        //整个行业的数据季度与当前的进行同步
		return BondIndicatorAdapter.syncInduToIss(issIndicatorsDoc, list);
	}

	
	
	
	
	/**
	 * 获取行业指标
	 * @param userId
	 * @param issKeyIndicatorDoc
	 * @return
	 */
	private List<IssIndicatorsDoc> findInduIndicatorList(Long userId, IssKeyIndicatorDoc issKeyIndicatorDoc, List<Long> provinceIds) {
//		boolean isGicsInduClass =  bondInduService.isGicsInduClass(userId);
//		Long induId = isGicsInduClass ? issKeyIndicatorDoc.getInduId() : issKeyIndicatorDoc.getInduIdSw();
//		String induKey = isGicsInduClass ? "induId" : "induIdSw";
		
		List<Long> comUniCodes = bondInduService.findComUniCodeByIndu(issKeyIndicatorDoc.getIssId(), userId);
		long s = System.currentTimeMillis();
		Query query = new Query(Criteria.where("issId").in(comUniCodes));
		if(provinceIds != null && provinceIds.size() > 0){
			query.addCriteria(Criteria.where("provinceId").in(provinceIds));
		}
		List<IssKeyIndicatorDoc> indicatorDocList = mongoTemplate.find(query, IssKeyIndicatorDoc.class);
		long e = System.currentTimeMillis();
		System.out.println("indicatorDocList ->" + (e-s));
		return bondFinanceInfoService.IssKeyIndicatorsToIssIndicatorsDocsList(indicatorDocList);
	}
	
	
	/**
	 * mark自身
	 * @param list
	 * @param issuerName
	 */
	private void markOneself(List<IssuerSortVo> list, Long issuerId){
		if(list == null) return;
		for (IssuerSortVo issuerSortVo : list) {
			if(issuerId != null){
				if(issuerId.equals(issuerSortVo.getIssuerId())){
					issuerSortVo.setOneself(Constants.TRUE);
				}
			}
		}
	}
	
	

	/**
	 * 获取自己最近的5个
	 * @param sortedList
	 * @return
	 */
	private List<IssuerSortVo> getNear5(List<IssuerSortVo> sortedList) {
		if(sortedList.size() < 6){
			return sortedList;
		}
		int myIndex = 0;
		for (int i = 0; i < sortedList.size(); i++) {
			if(sortedList.get(i).getOneself() == Constants.TRUE){
				myIndex = i;
				break;
			}
		}
		List<IssuerSortVo> result = null;
		if(myIndex == 0){
			result = sortedList.subList(0, 5);
		}else if(myIndex == 1){
			result = sortedList.subList(1, 6);
		}else if(myIndex == (sortedList.size() -1)){
			result = new ArrayList<>();
			result.add(sortedList.get(myIndex-4));
			result.add(sortedList.get(myIndex-3));
			result.add(sortedList.get(myIndex-2));
			result.add(sortedList.get(myIndex-1));
			result.add(sortedList.get(myIndex));
		}else if(myIndex == (sortedList.size() -2)){
			result = new ArrayList<>();
			result.add(sortedList.get(myIndex-3));
			result.add(sortedList.get(myIndex-2));
			result.add(sortedList.get(myIndex-1));
			result.add(sortedList.get(myIndex));
			result.add(sortedList.get(myIndex+1));
		}else{
			result = new ArrayList<>();
			result.add(sortedList.get(myIndex-2));
			result.add(sortedList.get(myIndex-1));
			result.add(sortedList.get(myIndex));
			result.add(sortedList.get(myIndex+1));
			result.add(sortedList.get(myIndex+2));
		}
		
		return result;
	}
	
	/**
	 * IssFinanceIndicators 转为 IssIndicatorsDoc
	 * @param result
	 * @param FinanceIndicators
	 */
	private void IssFinanceIndicatorsToIssIndicatorsDocs(List<IssIndicatorsDoc> result,
			IssFinanceIndicators issFinanceIndicators) {
		for (FinanceIndicator issFinanceIndicator : issFinanceIndicators.getIndicators()) {
			IssIndicatorsDoc item = new IssIndicatorsDoc();
			item.setCategory(issFinanceIndicator.getCategory());
			item.setCategoryParent(issFinanceIndicator.getCategoryParent());
			item.setField(issFinanceIndicator.getField());
			item.setFieldName(issFinanceIndicator.getFieldName());
			item.setIndicators(issFinanceIndicator.getIndicators());
			item.setInduId(issFinanceIndicators.getInduId());
			item.setInduIdSw(issFinanceIndicators.getInduIdSw());
			item.setIssId(issFinanceIndicators.getIssId());
			item.setType(issFinanceIndicator.getType());
			item.setIssName(issFinanceIndicators.getIssName());
			item.setQuarters(issFinanceIndicators.getQuarters());
			result.add(item);
		}
	}

	/**
	 * 将IssFinanceIndicators 转 IssIndicatorsDoc
	 * @param field
	 * @param issFinanceIndicators
	 * @param listIssFinanceIndicators
	 * @return
	 */
	private IssIndicatorsDoc issFinanceIndicatorsToIssIndicatorsDoc(String field, IssFinanceIndicators listIssFinanceIndicators) {
		IssIndicatorsDoc issIndicatorsDoc = new IssIndicatorsDoc();
		issIndicatorsDoc.setNegative(Constants.FALSE);
		issIndicatorsDoc.setIssId(listIssFinanceIndicators.getIssId());
		issIndicatorsDoc.setIssName(listIssFinanceIndicators.getIssName());
		issIndicatorsDoc.setQuarters(listIssFinanceIndicators.getQuarters());
		issIndicatorsDoc.setInduId(listIssFinanceIndicators.getInduId());
		issIndicatorsDoc.setInduIdSw(listIssFinanceIndicators.getInduIdSw());
		for (FinanceIndicator issFinanceIndicator : listIssFinanceIndicators.getIndicators()){
			if(issFinanceIndicator.getField().equals(field)){
				issIndicatorsDoc.setIndicators(issFinanceIndicator.getIndicators());
				issIndicatorsDoc.setField(issFinanceIndicator.getField());
				break;
			}
		}
		return issIndicatorsDoc;
	}
	
	/**
	 * 获取行业指标
	 * @param field
	 * @param listIssFinanceIndicators
	 * @return
	 */
	private List<BigDecimal> getInduIdicators(String field, List<IssFinanceIndicators> listIssFinanceIndicators) {
		List<BigDecimal> induIndicators = new ArrayList<>();
		for (IssFinanceIndicators issFinanceIndicators2 : listIssFinanceIndicators) {
			List<FinanceIndicator> financeIndicators = issFinanceIndicators2.getIndicators();
			for (FinanceIndicator financeIndicator : financeIndicators) {
				if(financeIndicator.getField().equals(field)){
					induIndicators.addAll(financeIndicator.getIndicators());
				}
			}
		}
		return induIndicators;
	}
	
	/**
	 * 按分组排序
	 * @param resultBuild
	 */
	private void sortGroup(List<IndicatorSpecialIndicatorVo> resultBuild){
		resultBuild.sort(new GroupComparator());
	}
	
	
}
/**
 * IssIndicatorsDoc比较器 按最近一季度的比较
 * @author zhaozhenglai
 * @date 2017年2月15日下午5:23:39
 *Copyright © 2016 DealingMatrix.cn. All Rights Reserved.
 */
class IssIndicatorsDocComparator implements Comparator<IssIndicatorsDoc>{

	@Override
	public int compare(IssIndicatorsDoc o1, IssIndicatorsDoc o2) {
		if(o1.getIndicators() != null && o2.getIndicators() != null)
			return o2.getIndicators().get(0).compareTo(o1.getIndicators().get(0));
		else
			return 0;
	}
	
}

/**
 * 分组比较器
 * @author zhaozhenglai
 * @date 2017年2月22日下午5:41:32
 *Copyright © 2016 DealingMatrix.cn. All Rights Reserved.
 */
class GroupComparator implements Comparator<IndicatorSpecialIndicatorVo>{

	@Override
	public int compare(IndicatorSpecialIndicatorVo o1, IndicatorSpecialIndicatorVo o2) {
		String g1 = SafeUtils.getString(o1.getCategoryNameParent()) + SafeUtils.getString(o1.getCategoryName());
		String g2 = SafeUtils.getString(o2.getCategoryNameParent()) + SafeUtils.getString(o2.getCategoryName());
		return  g1.compareTo(g2);
	}
	
}

