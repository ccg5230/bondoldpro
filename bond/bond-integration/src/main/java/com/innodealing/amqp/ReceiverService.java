package com.innodealing.amqp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.innodealing.adapter.BrokerBondQuoteAdapter;
import com.innodealing.bond.service.BondBasicInfoService;
import com.innodealing.bond.service.BondQuoteService;
import com.innodealing.consts.MqConstants;
import com.innodealing.engine.mongo.bond.BondBasicInfoRepository;
import com.innodealing.engine.mongo.bond.BondSentimentDistinctRepository;
import com.innodealing.model.dm.bond.BondQuote;
import com.innodealing.model.json.DmmsMineFieldJson;
import com.innodealing.model.json.NdBondJson;
import com.innodealing.model.json.QuoteStatusQueue;
import com.innodealing.model.mongo.dm.BondBasicInfoDoc;
import com.innodealing.model.mongo.dm.BondSentimentDistinctDoc;
import com.innodealing.service.BondAnnAttInfoService;
import com.innodealing.util.SafeUtils;
import com.innodealing.util.StringUtils;

/**
 * @author stephen.ma
 * @date 2016年6月8日
 * @clasename ReceiverService.java
 * @decription TODO
 */
@Component
public class ReceiverService {

	private final static Logger logger = LoggerFactory.getLogger(ReceiverService.class);

	private @Autowired BondQuoteService bondQuoteService;

	private @Autowired BondBasicInfoRepository bondBasicInfoRepository;

	private @Autowired Gson gson;

	protected @Autowired BondSentimentDistinctRepository bondSentimentDistinctRepository;
	
	private @Autowired ApplicationEventPublisher publisher;
	
	private @Autowired BondAnnAttInfoService bondAnnAttInfoService;
	
	private @Autowired BondBasicInfoService bondBasicInfoService;

	@RabbitListener(queues = MqConstants.MQ_QUEUE_BOND)
	public void receiveNdBondMsg(String queue) {
		try {
			if (!StringUtils.isBlank(queue)) {
				logger.info("recive from nd_bond queue,content is: " + queue);
				NdBondJson ndBondJson = gson.fromJson(queue, NdBondJson.class);
				BondQuote bondQuote = new BondQuote();

				handleBondInfo(ndBondJson, bondQuote);
				BeanUtils.copyProperties(ndBondJson, bondQuote);
				
				if(bondQuote.getBondUniCode()==null || bondQuote.getBondUniCode()==0L){
					return;
				}
				
				bondQuote.setBrokerType(0);
				
				if (null != bondQuote) {
					if ("insert".equals(ndBondJson.getActionType())) {
						// insert db and mongodb
						bondQuoteService.saveBondQuote(bondQuote, ndBondJson.getImFlag(), ndBondJson.getPhone(),
								ndBondJson.getMobile(), ndBondJson.getQqNum());
					}
					//QQ quotes for handling BondDebentureQuoteToday 
					if (null != bondQuote.getPostfrom() && 0 == bondQuote.getPostfrom().intValue()) {
						logger.info("handleBrokerBondQuoteParam bondQuote:" + JSON.toJSONString(bondQuote));
						publisher.publishEvent(BrokerBondQuoteAdapter.handleBrokerBondQuoteParam(bondQuote));
					}
				}
			}
		} catch (Exception e) {
			// e.printStackTrace();
			logger.error("receiveNdBondMsg error:" + e.getMessage(), e);
		}
	}

	/**
	 * @param ndBondJson
	 */
	private void handleBondInfo(NdBondJson ndBondJson, BondQuote bondQuote) {
		if (StringUtils.isNotBlank(ndBondJson.getBondCode())) {
			BondBasicInfoDoc bondBasicInfoDoc = null;
			//市场code
			if(ndBondJson.getBondCode().indexOf(".")!=-1){
				bondBasicInfoDoc = bondBasicInfoRepository.findByCode(ndBondJson.getBondCode());
			}else{
				bondBasicInfoDoc = bondBasicInfoRepository.findByOrgCode(ndBondJson.getBondCode());
			}
			if (null != bondBasicInfoDoc) {
				ndBondJson.setBondUniCode(bondBasicInfoDoc.getBondUniCode());
				ndBondJson.setBondCode(bondBasicInfoDoc.getCode());
				ndBondJson.setBondShortName(bondBasicInfoDoc.getShortName());
				bondQuote.setBondOrgCode(SafeUtils.getInteger(bondBasicInfoDoc.getOrgCode()));
			} else {
				if (StringUtils.isNotBlank(ndBondJson.getBondShortName())) {
					exec(ndBondJson,bondQuote);
				} else {
					ndBondJson.setBondUniCode(0L);
				}
			}
		} else {
			if (StringUtils.isNotBlank(ndBondJson.getBondShortName())) {
				exec(ndBondJson,bondQuote);
			} else {
				ndBondJson.setBondUniCode(0L);
			}
		}
	}
	
	private void exec(NdBondJson ndBondJson, BondQuote bondQuote){
		List<BondBasicInfoDoc> bondBasicInfoList = bondBasicInfoService
				.findListByShortName(ndBondJson.getBondShortName());
		if (null != bondBasicInfoList && bondBasicInfoList.size()==1) {
			ndBondJson.setBondUniCode(bondBasicInfoList.get(0).getBondUniCode());
			ndBondJson.setBondCode(bondBasicInfoList.get(0).getCode());
			bondQuote.setBondOrgCode(SafeUtils.getInteger(bondBasicInfoList.get(0).getOrgCode()));
		} else {
			ndBondJson.setBondUniCode(0L);
		}
	}

	@RabbitListener(queues = MqConstants.MQ_QUEUE_BOND_ITEM_STATUS)
	public void receiveBondItemStatus(String queue) {
		logger.info("recive from queue.bond_item_status,content is: " + queue);
		try {
			if (StringUtils.isNotBlank(queue)) {
				QuoteStatusQueue quoteStatus = gson.fromJson(queue, QuoteStatusQueue.class);
				quoteStatus.getItems().forEach(quoteItem -> {
					bondQuoteService.updateBondQuoteStatusFromNorData(quoteItem.getId(), quoteItem.getStatus());
				});
			}
		} catch (Exception e) {
			logger.error("receiveBondItemStatus error:" + e.getMessage(), e);
		}
	}

	/**
	 * 雷区舆情
	 * 
	 * @param queue
	 */
	@RabbitListener(queues = MqConstants.MQ_QUEUE_DMMD_MINE_FIELD)
	public void receiveDmmsMineField(String queue) {

		Assert.notNull(queue, "The dmms_mine_field queue not be null!");

		try {
			logger.info("recive from dmms_mine_field queue,content is: " + queue);
			DmmsMineFieldJson dmmsMineFieldJson = gson.fromJson(queue, DmmsMineFieldJson.class);
			Assert.notNull(dmmsMineFieldJson, "The DmmsMineFieldJson be null!");

			BondSentimentDistinctDoc doc = DmmsMineFieldJson2Vo(dmmsMineFieldJson);
			if (dmmsMineFieldJson.getStatus() == 2) { // 删除
				bondSentimentDistinctRepository.delete(doc);
			} else { // 新增或者修改
				bondSentimentDistinctRepository.save(doc);
			}
		} catch (Exception e) {
			// e.printStackTrace();
			logger.error("receiveDmmsMineField error:" + e.getMessage(), e);
		}

	}

	/**
	 * 数据封装
	 * 
	 * @param json
	 * @return
	 * @throws ParseException 
	 */
	private BondSentimentDistinctDoc DmmsMineFieldJson2Vo(DmmsMineFieldJson json) throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		BondSentimentDistinctDoc doc = new BondSentimentDistinctDoc();

		doc.setId(String.valueOf(json.getId()));
		doc.setComChiName(json.getComName());
		doc.setComUniCode(!StringUtils.isEmpty(json.getComCode())?json.getComCode().toString():null);
		doc.setIndex(0L);
		doc.setImportant(0);
		doc.setPubDate(StringUtils.isEmpty(json.getPublishtime())?null:sdf.parse(json.getPublishtime()));
		doc.setTitle(json.getTitle());
		doc.setUrl(json.getSkipUrl());

		return doc;

	}
	
	/**
	 * 
	 * receiveNewBondAtt:(新债公告附件上传mq监听)
	 *
	 * @param  @param quotJson    设定文件
	 * @return void    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	 */
	@RabbitListener(queues = MqConstants.MQ_QUEUE_NEWBOND_ANNATT_UPLOAD)
    public void receiveNewBondAtt(String quotJson) {
        try {
            bondAnnAttInfoService.bondUploadOss(quotJson);
        } catch (Exception e) {
            logger.error("receiveNewBondAtt error:" + e.getMessage(), e);
        }
    }

}