package com.innodealing.bond.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.innodealing.bond.param.BaseQuoteParam;
import com.innodealing.bond.param.BaseQuotePriceParam;
import com.innodealing.consts.Constants;
import com.innodealing.engine.redis.RedisMsgService;
import com.innodealing.util.MD5Util;
import com.innodealing.util.StringUtils;

/**
 * @author stephen.ma
 * @date 2016骞�8鏈�15鏃�
 * @clasename DemandsValidation.java
 * @decription TODO
 */
@Component
public class MessageValidation {

	private @Autowired RedisMsgService redisMsgService;

	/**
	 * 鍒ゆ柇10涓唴鏄惁鏈夊彂甯冮噸澶嶆枃鏈�
	 * 
	 * @param userid
	 * @param postfrom
	 * @param content
	 * @return
	 */
	public boolean isSameContent(String userid, String postfrom, String content) {
		boolean isSameDemandFlag = false;
		StringBuffer buffer = new StringBuffer();
		buffer.append(userid).append("_").append(postfrom).append("_").append(content.replaceAll(" ", ""));
		String msgMd5Key = MD5Util.getMD5(buffer.toString());
		String tempResult = redisMsgService.getMsgContent(msgMd5Key);
		if (!StringUtils.isBlank(tempResult)) {
			isSameDemandFlag = true;
		} else {
			redisMsgService.saveMsgWithTimeout(msgMd5Key, content, Constants.TIMEOUT_SECONDS);
		}
		return isSameDemandFlag;
	}
	/**
	 * 閲嶅彂鎶ヤ环鍘婚噸isSameQuotePrice
	 * @param quoteId
	 * @param quotePrice
	 * @return
	 */
	
	public boolean isSameQuotePrice(Long quoteId, BaseQuotePriceParam quotePrice) {
		boolean isSameDemandFlag = false;
		String msgMd5Key = handleQuotePriceParam(quoteId, quotePrice);
		String tempResult = redisMsgService.getMsgContent(msgMd5Key);
		if (!StringUtils.isBlank(tempResult)) {
			isSameDemandFlag = true;
		} else {
			redisMsgService.saveMsgWithTimeout(msgMd5Key, msgMd5Key, Constants.TIMEOUT_SECONDS);
		}
		return isSameDemandFlag;
	}
	
	/**
	 * saveQuotePrice
	 * @param quoteId
	 * @param quotePrice
	 */
	public void saveQuotePrice(Long quoteId, BaseQuotePriceParam quotePrice) {
		String msgMd5Key = handleQuotePriceParam(quoteId, quotePrice);
		redisMsgService.saveMsgWithTimeout(msgMd5Key, msgMd5Key, Constants.TIMEOUT_SECONDS);
	}
	/**
	 * @param quoteId
	 * @param quotePrice
	 * @return
	 */
	private String handleQuotePriceParam(Long quoteId, BaseQuotePriceParam quotePrice) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(quoteId).append("_").append(quotePrice.getSide()).append("_").append(quotePrice.getBondPrice())
				.append("_").append(quotePrice.getBondVol());
		String msgMd5Key = MD5Util.getMD5(buffer.toString());
		return msgMd5Key;
	}
	/**
	 * getQuotePriceFlag
	 * @param quoteId
	 * @param quotePrice
	 * @return
	 */
	public String getQuotePriceFlag(Long quoteId, BaseQuotePriceParam quotePrice) {
		String msgMd5Key = handleQuotePriceParam(quoteId, quotePrice);
		return redisMsgService.getMsgContent(msgMd5Key);
	}
	
	public boolean isSameQuoteParam(Long userid, BaseQuoteParam quoteParam) {
		boolean isSameDemandFlag = false;
		StringBuffer buffer = new StringBuffer();
		buffer.append(userid).append("_").append(quoteParam.getPostfrom()).append("_").append(quoteParam.getBondCode())
			.append("_").append(quoteParam.getBondShortName()).append("_").append(quoteParam.getBondUniCode()).append("_").append(quoteParam.getAnonymous())
			.append("_").append(quoteParam.getBondPrice()).append("_").append(quoteParam.getBondVol()).append("_").append(quoteParam.getSide());
		String msgMd5Key = MD5Util.getMD5(buffer.toString());
		String tempResult = redisMsgService.getMsgContent(msgMd5Key);
		if (!StringUtils.isBlank(tempResult)) {
			isSameDemandFlag = true;
		} else {
			redisMsgService.saveMsgWithTimeout(msgMd5Key, msgMd5Key, Constants.TIMEOUT_SECONDS);
		}
		return isSameDemandFlag;
	}
	
	public void saveMessage(String msg){
		String msgMd5Key = MD5Util.getMD5(msg);
		redisMsgService.saveMsg(msgMd5Key, msg);
	}
	
	public void deleteMessage(String msg){
		String msgMd5Key = MD5Util.getMD5(msg);
		redisMsgService.deleteMsg(msgMd5Key);
	}
	
	public boolean isSameMessage(String msg){
		boolean isSameFlag = false;
		String msgMd5Key = MD5Util.getMD5(msg);
		String tempResult = redisMsgService.getMsgContent(msgMd5Key);
		if (!StringUtils.isBlank(tempResult)) {
			isSameFlag = true;
		}
		return isSameFlag;
	}
	
	public void saveMsgWithTimeout(String msg, long timeout){
		String msgMd5Key = MD5Util.getMD5(msg);
		redisMsgService.saveMsgWithTimeout(msgMd5Key, msgMd5Key, timeout);
	}
	
}
