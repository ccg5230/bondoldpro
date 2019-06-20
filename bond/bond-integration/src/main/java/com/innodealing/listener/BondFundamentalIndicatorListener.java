package com.innodealing.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.innodealing.amqp.SenderService;
import com.innodealing.model.FundamentalIndicator;
import com.innodealing.model.mongo.dm.BondDetailDoc;
import com.mongodb.WriteResult;

/**
 * @author feng.ma
 * @date 2017年6月30日 下午5:11:48
 * @describe 债券的市场指标
 */
@Service
public class BondFundamentalIndicatorListener {

	static final Logger LOGGER = LoggerFactory.getLogger(BondFundamentalIndicatorListener.class);
	
	public final static String SELF_CHANGE_TYPE = "SELF";

	private @Autowired MongoTemplate mongoTemplate;

	private @Autowired SenderService senderService;

	@Async
	@EventListener
	public void handleFundamentalIndicator(FundamentalIndicator indicator) {
		LOGGER.info("市场指标更新FundamentalIndicator, " + indicator.toString());
		try {
			sendIndicToMq(indicator);
			updateIndicatorInBondDetail(indicator);
		} 
		catch (Exception ex) {
			LOGGER.error("handleFundamentalIndicator exception", ex);
		}
	}

	private void sendIndicToMq(FundamentalIndicator indicator) {
		if (!isNotNull(indicator)) {
			return;
		}

		JSONObject jsonObj = new JSONObject();
		jsonObj.put("bondUniCode", indicator.getBondUniCode());

		JSONObject subjsonObj = new JSONObject();
		if (isNotNull(indicator.getConvexity())) {
			subjsonObj.put("convexity", indicator.getConvexity());
		}
		if (isNotNull(indicator.getEstCleanPrice())) {
			subjsonObj.put("estCleanPrice", indicator.getEstCleanPrice());
		}
		if (isNotNull(indicator.getEstYield())) {
			subjsonObj.put("estYield", indicator.getEstYield());
		}
		if (isNotNull(indicator.getMacd())) {
			subjsonObj.put("macd", indicator.getMacd());
		}
		if (isNotNull(indicator.getModd())) {
			subjsonObj.put("modd", indicator.getModd());
		}
		if (isNotNull(indicator.getOptionYield())) {
			subjsonObj.put("optionYield", indicator.getOptionYield());
		}
		if (isNotNull(indicator.getStaticSpread())) {
			subjsonObj.put("staticSpread", indicator.getStaticSpread());
		}
		if (isNotNull(indicator.getStaticSpreadInduQuantile())) {
			subjsonObj.put("staticSpreadInduQuantile", indicator.getStaticSpreadInduQuantile());
		}

		if (!subjsonObj.isEmpty()) {
			jsonObj.put(SELF_CHANGE_TYPE, subjsonObj);
			senderService.sendBondFinindic2Rabbitmq(JSON.toJSONString(jsonObj));
		}
	}

	private void updateIndicatorInBondDetail(FundamentalIndicator indicator) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(indicator.getBondUniCode()));
		Update update = new Update();
		if (isNotNull(indicator.getConvexity())) {
			update.set("convexity", indicator.getConvexity());
		}
		if (isNotNull(indicator.getEstCleanPrice())) {
			update.set("estCleanPrice", indicator.getEstCleanPrice());
		}
		if (isNotNull(indicator.getEstYield())) {
			update.set("estYield", indicator.getEstYield());
		}
		if (isNotNull(indicator.getMacd())) {
			update.set("macd", indicator.getMacd());
		}
		if (isNotNull(indicator.getModd())) {
			update.set("modd", indicator.getModd());
		}
		if (isNotNull(indicator.getOptionYield())) {
			update.set("optionYield", indicator.getOptionYield());
		}
		if (isNotNull(indicator.getStaticSpread())) {
			update.set("staticSpread", indicator.getStaticSpread());
		}
		if (isNotNull(indicator.getStaticSpreadInduQuantile())) {
			update.set("staticSpreadInduQuantile", indicator.getStaticSpreadInduQuantile());
		}
		
		WriteResult wr = mongoTemplate.updateFirst(query, update, BondDetailDoc.class);
		if (wr.getN() <= 0) {
			LOGGER.warn("找不到对应的BondDetailDoc, query:" + query.toString() + ", update:" + update.toString());
		}
	}

	private boolean isNotNull(Object obj) {
		boolean result = false;
		if (null != obj) {
			result = true;
		}
		return result;
	}

}
