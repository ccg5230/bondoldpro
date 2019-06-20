package com.innodealing.listener;

import java.math.BigDecimal;

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
import com.innodealing.model.dm.bond.BondConvRatio;
import com.innodealing.model.mongo.dm.BondDetailDoc;
import com.innodealing.service.BondAnalysislIngtService;
import com.mongodb.WriteResult;

/**
 * @author feng.ma
 * @date 2017年6月30日 下午3:20:16
 * @describe 标准劵折算率指标
 */
@Service
public class BondConvRatioListener {

	static final Logger LOGGER = LoggerFactory.getLogger(BondConvRatioListener.class);

	public final static String SELF_CHANGE_TYPE = "SELF";

	private @Autowired MongoTemplate mongoTemplate;

	private @Autowired SenderService senderService;

	@Async
	@EventListener
	public void handleBondConvRatio(BondConvRatio bondConvRatio) {
		LOGGER.info("折算率更新, " + bondConvRatio.toString());
		sendIndicToMq(bondConvRatio);
		updateConvRatioInBondDetail(bondConvRatio);
	}

	private void sendIndicToMq(BondConvRatio bondConvRatio) {
		BigDecimal convRatio = bondConvRatio.getConvRatio();
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("bondUniCode", bondConvRatio.getConvRatioKey().getBondUniCode());
		JSONObject subjsonObj = new JSONObject();
		subjsonObj.put("convRatio", convRatio.doubleValue());
		jsonObj.put(SELF_CHANGE_TYPE, subjsonObj);
		senderService.sendBondFinindic2Rabbitmq(JSON.toJSONString(jsonObj));
	}

	private void updateConvRatioInBondDetail(BondConvRatio bondConvRatio) {
		// 更新mongodb/bond_detail，用于高级筛选
		Update mongoUpdate = new Update();
		mongoUpdate.set("convRatio", bondConvRatio.getConvRatio().doubleValue());
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(bondConvRatio.getConvRatioKey().getBondUniCode()));
		WriteResult wr = mongoTemplate.updateFirst(query, mongoUpdate, BondDetailDoc.class);
		if (wr.getN() <= 0) {
			LOGGER.warn("找不到对应的BondDetailDoc, query:" + query.toString() + ", update:" + mongoUpdate.toString());
		}
	}
}
