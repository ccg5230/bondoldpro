package com.innodealing.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.innodealing.consts.Constants;
import com.innodealing.domain.enums.EmotionTagEnum;
import com.innodealing.domain.enums.FavRadarSchemaEnum;
import com.innodealing.domain.model.FinIndicatorMsg;
import com.innodealing.engine.mongo.bond.BondComInfoRepository;
import com.innodealing.model.mongo.dm.BondComInfoDoc;
import com.innodealing.model.mongo.dm.BondFavFinaIdxDoc;
import com.innodealing.model.mongo.dm.BondFavOtherIdxDoc;
import com.innodealing.util.SafeUtils;
import com.innodealing.util.StringUtils;
import com.innodealing.utils.ExpressionUtil;
import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;
import com.ql.util.express.IExpressContext;

@Service
public class BondPortfolioFinIndicatorHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(BondPortfolioFinIndicatorHandler.class);

	@Autowired
	private BondPortfolioNotificationMsgHandler notificationMsgHandler;

	@Autowired
	private MongoOperations mongoOperations;
	
	@Autowired
	private BondComInfoRepository comInfoRep;
	
	@Autowired
	private BondUserRoleService userRoleService;

	private final static Integer FIXED_IDX_TYPE = 1; // 固有指标
	private final static Integer CUSTOMIZED_IDX_TYPE = 2; // 组合指标

	/*
	 * 输入JSON格式 { "_id" : ObjectId("5926a6953aff7d1004d1329e"), "comUniCode" :
	 * 100001, "finQuanter" : "2017/Q1", "YOY" : { "BSOO1" : 0.11, "BSOO2" :
	 * -0.5 }, "RANK" : { "BSOO1" : 97.0, "AS003" : 12.0 }, "SELF" : { "BSOO1" :
	 * 111111.0, "AS003" : 8888.0 } }
	 */
	public void handleBondFinIndic(JSONObject jsonObj, boolean isBond) {
		FinIndicatorMsg finMsg = new FinIndicatorMsg(jsonObj, isBond);
		Map<String, Map<String, BigDecimal>> changesMap = finMsg.getChgMap();
		
		if (!changesMap.isEmpty()) {
			Iterator iter = changesMap.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry pair = (Map.Entry) iter.next();
				String changeType = (String) pair.getKey();
				handleFieldsChange(finMsg.getComUniCode(), finMsg.getFinQuarter(),
						FinIndicatorMsg.CHANGE_TYPE_MAP.get(changeType), (Map<String, BigDecimal>) pair.getValue(), isBond);
				iter.remove(); // avoids a ConcurrentModificationException
			}
		}else{
			LOGGER.info("handleBondFinIndic changesMap is isEmpty.");
		}
	}

	private void handleFieldsChange(Long uniCode, String finQuarter, Integer changeType,
			Map<String, BigDecimal> fieldsMap, boolean isBond) {
		Iterator it = fieldsMap.entrySet().iterator();
		while (it.hasNext()) {
			Map<String, BigDecimal> paramMap = new HashMap<String, BigDecimal>();
			Map.Entry pair = (Map.Entry) it.next();
			// 字段名称
			String field = (String) pair.getKey();
			// 字段值（变化后）
			BigDecimal value = (BigDecimal) pair.getValue();
			paramMap.put(field, value);
			// 根据变化字段名称找到订阅改字段的投组指标表达式
			List<BondFavFinaIdxDoc> favFinaIdxList = findFavFinaIdxByField(uniCode, changeType, field, isBond);

			for (BondFavFinaIdxDoc finaIdx : favFinaIdxList) {
				if (BondPortfolioIdxConsts.ESTCLEANPRICE_FIELD.equals(field) 
						|| BondPortfolioIdxConsts.ESTYIELD_FIELD.equals(field)) {
					
					if(userRoleService.isDebtRole(finaIdx.getUserId())){
						Map<String, BigDecimal> valueMap = generateValueMap(fieldsMap, finaIdx);
						if (valueMap.isEmpty()) {
							LOGGER.info("buildAndCalculate valueMap is isEmpty, and finaIdx:"+finaIdx);
							continue;
						}
						if (valueMap.size() != finaIdx.getVariables().size()) {
							LOGGER.info("valueMap != variables size, and finaIdx:"+finaIdx);
							continue;
						} else {
							//处理本身就含有计算表达式的指标字段
							if (finaIdx.getVariables().size() > 1) {
								Object res = evaluateExpression(finaIdx.getIndexCodeExpr(), valueMap);
								if (null == res) {
									//跳出数据中不合表达式的循环
									continue;
								}else{
									value = new BigDecimal(res.toString());
								}
							}
						}
						
						String expression = generateExpress(finaIdx);
						Object ret = evaluateExpression(expression, valueMap);
						if (StringUtils.isBlank(expression) || null == ret) {
							LOGGER.info("the expression is invalid! or the result of expression is null, finaIdx:"+finaIdx);
							continue;
						}
						LOGGER.info("evaluateExpression BondUniCode:"+finaIdx.getBondUniCode()+",finaIdx:"+finaIdx.toString()+",expression:"+expression+",result:" + ret+",valueMap:"+valueMap);
						
						if ((Boolean) ret) {
							notificationMsgHandler.pubNotificationMsg(BondPortfolioNotificationMsgHandler.buildMsgParam(finaIdx.getUserId(), finaIdx.getBondUniCode(),
									Constants.EVENMSG_TYPE_FININDICATOR, generateNotification(finQuarter, value, finaIdx),
									new Date(), 0L, 0, finaIdx.getGroupId(), EmotionTagEnum.DEFAULT.getCode(), finaIdx.getNotifiedEnable()));
						}
					}
				} else {
					Map<String, BigDecimal> valueMap = generateValueMap(fieldsMap, finaIdx);
					if (valueMap.isEmpty()) {
						LOGGER.info("buildAndCalculate valueMap is isEmpty, and finaIdx:"+finaIdx);
						continue;
					}
					if (valueMap.size() != finaIdx.getVariables().size()) {
						LOGGER.info("valueMap != variables size, and finaIdx:"+finaIdx);
						continue;
					} else {
						//处理本身就含有计算表达式的指标字段
						if (finaIdx.getVariables().size() > 1) {
							Object res = evaluateExpression(finaIdx.getIndexCodeExpr(), valueMap);
							if (null == res) {
								//跳出数据中不合表达式的循环
								continue;
							}else{
								value = new BigDecimal(res.toString());
							}
						}
					}
					
					String expression = generateExpress(finaIdx);
					Object ret = evaluateExpression(expression, valueMap);
					if (StringUtils.isBlank(expression) || null == ret) {
						LOGGER.info("the expression is invalid! or the result of expression is null, finaIdx:"+finaIdx);
						continue;
					}
					LOGGER.info("evaluateExpression BondUniCode:"+finaIdx.getBondUniCode()+",finaIdx:"+finaIdx.toString()+",expression:"+expression+",result:" + ret+",valueMap:"+valueMap);
					
					if ((Boolean) ret) {
						notificationMsgHandler.pubNotificationMsg(BondPortfolioNotificationMsgHandler.buildMsgParam(finaIdx.getUserId(), finaIdx.getBondUniCode(),
								Constants.EVENMSG_TYPE_FININDICATOR, generateNotification(finQuarter, value, finaIdx),
								new Date(), 0L, 0, finaIdx.getGroupId(), EmotionTagEnum.DEFAULT.getCode(), finaIdx.getNotifiedEnable()));
					}
				}
			}
			it.remove(); // avoids a ConcurrentModificationException
		}
	}

	@Deprecated
	private void buildAndCalculate(String finQuarter, Map<String, BigDecimal> fieldsMap, BigDecimal value,
			BondFavFinaIdxDoc finaIdx) {
		// 构建表达式计算参数，重新评估表达式结果是否满足用户定义的条件
		
		Map<String, BigDecimal> valueMap = generateValueMap(fieldsMap, finaIdx);
		if (valueMap.isEmpty()) {
			LOGGER.info("buildAndCalculate valueMap is isEmpty, and finaIdx:"+finaIdx);
			return;
		}
		if (valueMap.size() != finaIdx.getVariables().size()) {
			LOGGER.info("valueMap != variables size, and finaIdx:"+finaIdx);
			return;
		} else {
			//处理本身就含有计算表达式的指标字段
			if (finaIdx.getVariables().size() > 1) {
				value = (BigDecimal) evaluateExpression(finaIdx.getIndexCodeExpr(), valueMap);
			}
		}
		
		String expression = generateExpress(finaIdx);
		Object ret = evaluateExpression(expression, valueMap);
		if (StringUtils.isBlank(expression) || null == ret) {
			LOGGER.info("the expression is invalid! or the result of expression is null, finaIdx:"+finaIdx);
			return;
		}
		
		LOGGER.info("evaluateExpression BondUniCode:"+finaIdx.getBondUniCode()+",finaIdx:"+finaIdx.toString()+",expression:"+expression+",result:" + ret+",valueMap:"+valueMap);
		
		if ((Boolean) ret) {
			notificationMsgHandler.pubNotificationMsg(BondPortfolioNotificationMsgHandler.buildMsgParam(finaIdx.getUserId(), finaIdx.getBondUniCode(),
					Constants.EVENMSG_TYPE_FININDICATOR, generateNotification(finQuarter, value, finaIdx),
					new Date(), 0L, 0, finaIdx.getGroupId(), EmotionTagEnum.DEFAULT.getCode(), finaIdx.getNotifiedEnable()));
		}
	}

	private Map<String, BigDecimal> generateValueMap(Map<String, BigDecimal> fieldsMap, BondFavFinaIdxDoc finaIdx) {
		Map<String, BigDecimal> fieldsValueMap = new HashMap<>();
		if (!finaIdx.getVariables().isEmpty()) {
			for(String field : finaIdx.getVariables()){
				BigDecimal fieldValue = fieldsMap.get(field);
				if (null != fieldValue) {
					if (SafeUtils.getInt(finaIdx.getIndexValueType()) == FinIndicatorMsg.IDX_VALUE_TYPE_YOY) {
							fieldsValueMap.put(field, fieldValue.multiply(new BigDecimal(100)));
					}else{
						fieldsValueMap.put(field, fieldValue);
					}
				}
			}
		}
		return fieldsValueMap;
	}

	/**
	 * 根据匹配条件类型，生成匹配用的表达式 如果自定义条件，那么直接使用indexCodeExpr
	 * 如果是固定条件，那么根据用户设置生成匹配表达式（比如BS001 > 100)
	 * 
	 * @param finaIdx
	 */
	private String generateExpress(BondFavFinaIdxDoc finaIdx) {
		if (finaIdx.getIndexType().equals(FIXED_IDX_TYPE)) {

			return commonValueNexusExpr(finaIdx, finaIdx.getIndexCodeExpr()).toString();
		} else {

			return commonValueNexusExpr(finaIdx, "(" + finaIdx.getIndexCodeExpr() + ")").toString();
		}
	}

	private String commonValueNexusExpr(BondFavFinaIdxDoc finaIdx, String variable) {
		String result = "";
		int valueNexus = finaIdx.getIndexValueNexus();
		switch (valueNexus) {
		case BondPortfolioIdxConsts.INDEX_STATUS_NEXUS_GTE_A:
			result = String.format("%1$s >= %2$f", variable, finaIdx.getIndexValueLow());
			break;
		case BondPortfolioIdxConsts.INDEX_STATUS_NEXUS_LTE_A:
			result = String.format("%1$s <= %2$f", variable, finaIdx.getIndexValueLow());
			break;
		case BondPortfolioIdxConsts.INDEX_STATUS_NEXUS_BEWTEEN_AB:
			result = String.format("%1$s >= %2$f and %1$s <= %3$f", variable, finaIdx.getIndexValueLow(),
					finaIdx.getIndexValueHigh());
			break;
		case BondPortfolioIdxConsts.INDEX_STATUS_NEXUS_LTE_A_OR_GTE_B:
			result = String.format("%1$s <= %2$f or %1$s >= %3$f", variable, finaIdx.getIndexValueLow(),
					finaIdx.getIndexValueHigh());
			break;
		case BondPortfolioIdxConsts.INDEX_STATUS_NEXUS_FRONT_A:
			result = String.format("%1$s <= %2$f", variable, finaIdx.getIndexValueLow());
			break;
		case BondPortfolioIdxConsts.INDEX_STATUS_NEXUS_END_A:
			result = String.format("%1$s >= %2$f", variable, new BigDecimal(100).subtract(finaIdx.getIndexValueLow()));
			break;
		}
		return result;
	}

	/**
	 * 根据匹配条件类型，获取匹配用的变量 如果固定条件，那么直接使用indexCodeExpr 如果自定义条件，那么根据需要解析表达式，提取变量
	 * 
	 * @param finaIdx
	 */
	private List<String> extractVariablesFromExpress(BondFavFinaIdxDoc finaIdx) {
		if (finaIdx.getIndexType().equals(FIXED_IDX_TYPE)) {
			List<String> variables = new ArrayList<String>();
			variables.add(finaIdx.getIndexCodeExpr());
			return variables;
		} else {
			return ExpressionUtil.extractFieldsInExpression(finaIdx.getIndexCodeExpr());
		}
	}

	private List<BondFavFinaIdxDoc> findFavFinaIdxByField(Long uniCode, Integer indexValueType, String field, boolean isBond) {
		String queryKey = "comUniCode";
		if (isBond) {
			queryKey = "bondUniCode";
		}
		Query idxQuery = new Query();
		idxQuery.addCriteria(
				Criteria.where(queryKey).is(uniCode).and("status").is(FinIndicatorMsg.IDX_STATUS_VALID)
						.and("indexValueType").is(indexValueType).and("variables").in(ExpressionUtil.extractFieldsInExpression(field)));

		return mongoOperations.find(idxQuery, BondFavFinaIdxDoc.class);
	}

	public Object evaluateExpression(String expression, Map<String, BigDecimal> fieldsMap) {
		ExpressRunner runner = new ExpressRunner();
		try {
			IExpressContext<String, Object> context = new DefaultContext<String, Object>();
			Iterator iter = fieldsMap.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry pair = (Map.Entry) iter.next();
				context.put((String) pair.getKey(), (Object) pair.getValue());
				// iter.remove(); // avoids a ConcurrentModificationException
			}
			LOGGER.info("execute before evaluateExpression:" + expression + ",context:" + context);
			return runner.execute(expression, context, null, true, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// {YYYY}年{一季报/半年报/三季报/年报}显示，{财务指标名称}为{xx}{单位}
	// {YYYY}年{一季报/半年报/三季报/年报}显示，{财务指标名称}行业排名为{xx%}
	// {YYYY}年{一季报/半年报/三季报/年报}显示，{财务指标名称}同比增长{xx%}
	//新需求： 针对市场指标，提醒内容修改为：“根据最新指标显示，{财务指标名称}为{xx}{单位}”
	private String generateNotification(String finQuanter, BigDecimal value, BondFavFinaIdxDoc finIndx) {
		// TODO 存在扩展性风险，匹配的表达式列表可能包含不止一个变量，所以将来可能不能简单这么处理
		List<String> variables = extractVariablesFromExpress(finIndx);
		if (variables == null || variables.isEmpty()) {
			LOGGER.error("empty variable, favFinaIdx:" + finIndx.toString());
			return null;
		}

		String variable = variables.get(0);
		String notificationTemplate = "%1$s显示, ";

		if (StringUtils.isEmpty(finQuanter)) {
			finQuanter = "根据最新指标";
		}
		
		String finalStr = "";
		switch (finIndx.getIndexValueType()) {
		case FinIndicatorMsg.IDX_VALUE_TYPE_SELF:
			notificationTemplate += "%2$s为%3$s";
			finalStr = String.format(notificationTemplate, finQuanter, finIndx.getIndexName(), getFinalValueExper(value, finIndx.getIndexValueUnit().intValue()));
			if (finIndx.getIndexValueUnit().intValue() == BondPortfolioIdxConsts.INDEXVALUEUNIT_1) {
				finalStr = finalStr+"%";
			}
			break;
		case FinIndicatorMsg.IDX_VALUE_TYPE_YOY:
			notificationTemplate += "%2$s同比增长%3$s";
			finalStr = String.format(notificationTemplate, finQuanter, finIndx.getIndexName(), getFinalValueExper(value, finIndx.getIndexValueUnit().intValue()))+"%";
			break;
		case FinIndicatorMsg.IDX_VALUE_TYPE_RANK:
			notificationTemplate += "%2$s行业排名为%3$s";
			finalStr = String.format(notificationTemplate, finQuanter, finIndx.getIndexName(), value.toString())+"%";
			break;
		}
		
		return finalStr;
	}
	
	private String getFinalValueExper(BigDecimal value, int indexValueUnit) {
		String finalValueStr = "";
		switch(indexValueUnit){
			case BondPortfolioIdxConsts.INDEXVALUEUNIT_1:
				finalValueStr = String.format("%.2f", value.multiply(BigDecimal.valueOf(100)).doubleValue()).toString();
				break;
			case BondPortfolioIdxConsts.INDEXVALUEUNIT_2:
				finalValueStr = value.toString()+"万元";
				break;
			case BondPortfolioIdxConsts.INDEXVALUEUNIT_3:
				finalValueStr = value.toString()+"天";
				break;
			default:
				finalValueStr = value.toString();
				break;
		}
		return finalValueStr;
	}

	public void handleBondFinRpt(JSONObject jsonObj, boolean isBond) {
		if (isBond) {
			return;
		}
		
		Long comUniCode = SafeUtils.getLong(jsonObj.get(FinIndicatorMsg.COM_UNICODE));
		String finQuarter = SafeUtils.getString(jsonObj.get(FinIndicatorMsg.FIN_QUARTER));
		int finRptFlag = SafeUtils.getInt(jsonObj.get(FinIndicatorMsg.FIN_RPTFLAG));
		
		LOGGER.info("handleBondFinRpt comUniCode:" + comUniCode+",finRptFlag:"+finRptFlag);
		
		if (finRptFlag > 0) {
			BondComInfoDoc comInfo = comInfoRep.findOne(comUniCode);
			if (null == comInfo) {
				LOGGER.info("handleBondFinRpt comUniCode[" + comUniCode+"] is not in system.");
				return;
			}
			
			List<BondFavOtherIdxDoc> favOtherIdxDocs = findFavOtherIdx(comUniCode);
			favOtherIdxDocs.stream().forEach(favOtherIdxDoc -> {
				//披露财报
				handleFinDisclosure(comInfo, finQuarter, finRptFlag, favOtherIdxDoc);
				//规定时间未收集到财报
				handleUncollectedFinRpt(comInfo, finQuarter, finRptFlag, favOtherIdxDoc);
			});
		}
	}

	private void handleUncollectedFinRpt(BondComInfoDoc comInfo, String finQuarter, int finRptFlag,
			BondFavOtherIdxDoc favOtherIdxDoc) {
		if (finRptFlag == FinIndicatorMsg.FIN_RPTFLAG_91 
				&& favOtherIdxDoc.getRadarId().intValue() == FavRadarSchemaEnum.UNCOLLECTED_EARNINGRPT.getCode()) {
			
			notificationMsgHandler.pubNotificationMsg(BondPortfolioNotificationMsgHandler.buildMsgParam(favOtherIdxDoc.getUserId(), favOtherIdxDoc.getBondUniCode(),
					Constants.EVENMSG_TYPE_UNCOLLECTEDRPT, genFavOtherNotification(comInfo, finQuarter, finRptFlag),
					new Date(), 0L, 0, favOtherIdxDoc.getGroupId(), EmotionTagEnum.RISK.getCode(), favOtherIdxDoc.getNotifiedEnable()));
		}
	}

	private void handleFinDisclosure(BondComInfoDoc comInfo, String finQuarter, int finRptFlag,
			BondFavOtherIdxDoc favOtherIdxDoc) {
		//只对新增财报的处理
		if (finRptFlag == FinIndicatorMsg.FIN_RPTFLAG_01 
				&& favOtherIdxDoc.getRadarId().intValue() == FavRadarSchemaEnum.FIN_DISCLOSURE.getCode()) {

			notificationMsgHandler.pubNotificationMsg(BondPortfolioNotificationMsgHandler.buildMsgParam(favOtherIdxDoc.getUserId(), favOtherIdxDoc.getBondUniCode(),
					Constants.EVENMSG_TYPE_FINDISCLOSURE, genFavOtherNotification(comInfo, finQuarter, finRptFlag),
					new Date(), 0L, 0, favOtherIdxDoc.getGroupId(), EmotionTagEnum.DEFAULT.getCode(), favOtherIdxDoc.getNotifiedEnable()));
			
		}
	}

	private String genFavOtherNotification(BondComInfoDoc comInfo, String finQuarter, int finRptFlag) {
		StringBuffer content = new StringBuffer();
		content.append(comInfo.getComChiName()+SafeUtils.convertFromYearQnToYearTitle2(finQuarter));
		if (finRptFlag == FinIndicatorMsg.FIN_RPTFLAG_91) {
			content.append("仍未披露");
		}else{
			content.append("已披露");
		}
		return content.toString();
	}

	private List<BondFavOtherIdxDoc> findFavOtherIdx(Long comUniCode) {
		Query idxQuery = new Query();
		idxQuery.addCriteria(Criteria.where("comUniCode").is(comUniCode).and("radarId").gte(FavRadarSchemaEnum.FIN_DISCLOSURE.getCode()).lte(FavRadarSchemaEnum.UNCOLLECTED_EARNINGRPT.getCode()));

		return mongoOperations.find(idxQuery, BondFavOtherIdxDoc.class);
	}
	
	/*
	public static void main(String[] args) { 
		BondPortfolioFinIndicatorHandler handler = new BondPortfolioFinIndicatorHandler(); 
		Map<String, BigDecimal>  variables = new HashMap<String, BigDecimal>(); 
		variables.put("BS001", new  BigDecimal(10.00)); 
		variables.put("BS002", new BigDecimal(98.93));
		BondFavFinaIdxDoc entity = new BondFavFinaIdxDoc();
		entity.setIndexType(CUSTOMIZED_IDX_TYPE); 
		entity.setIndexValueNexus(2);
		entity.setIndexCodeExpr("2*BS001+BS002"); 
		entity.setIndexValueLow(new BigDecimal(100.00)); 
		entity.setIndexValueHigh(new BigDecimal(200.00));
		entity.setVariables(Arrays.asList("BS001","BS002"));
		System.out.println("generateExpress:" + handler.generateExpress(entity));
		Object r = handler.evaluateExpression(handler.generateExpress(entity), variables); 
		System.out.println(r);
		
		Map<String, BigDecimal>  fieldsMap = new HashMap<String, BigDecimal>(); 
		fieldsMap.put("BS001", new BigDecimal(50));
		fieldsMap.put("BS002", new BigDecimal(10));
		Object res = handler.evaluateExpression("2*BS001+BS002", fieldsMap); 
		System.out.println("==res=="+res);
		
		handler.buildAndCalculate("2017/Q3", fieldsMap, new BigDecimal(100), entity);
	}
*/
	
}
