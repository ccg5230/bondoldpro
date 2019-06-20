package com.innodealing.bond.monitor;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.innodealing.consts.Constants;
import com.innodealing.domain.statis.BondQuoteDataCount;
import com.innodealing.engine.redis.RedisUtil;
import com.innodealing.model.mongo.dm.BondQuoteDoc;
import com.innodealing.util.SafeUtils;

/**
 * 统计报价数据
 * 
 * @author Administrator
 *
 */
@Component
public class BondQuoteDataStatistic {

	private @Autowired RedisUtil redisUtil;

	@EventListener
	@Async
	public void handleQuoteSaved(BondQuoteDoc realData) {
		if (null == realData) {
			return;
		}
		String key = Constants.QUOTEDATA_POSTFROM_KEY + realData.getPostfrom();

		BondQuoteDataCount dataCount = (BondQuoteDataCount) redisUtil.get(key);
		if (null != dataCount) {
			int count = dataCount.getCount() + 1;
			dataCount.setCount(count);
			dataCount.setQuoteDate(SafeUtils.parseDate(realData.getSendTime(), SafeUtils.DATE_TIME_FORMAT1));
		} else {
			dataCount = new BondQuoteDataCount(1, new Date(), realData.getPostfrom());
		}

		redisUtil.set(key, dataCount);
	}
}
