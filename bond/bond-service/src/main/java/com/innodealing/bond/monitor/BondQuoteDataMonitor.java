package com.innodealing.bond.monitor;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.innodealing.consts.Constants;
import com.innodealing.domain.statis.BondQuoteDataCount;
import com.innodealing.engine.jpa.dm.BondQuoteDatastatisRepository;
import com.innodealing.engine.redis.RedisUtil;
import com.innodealing.model.dm.bond.BondQuoteDatastatis;

/**
 * 监控报价数据
 * 
 * @author Administrator
 *
 */

@Service
public class BondQuoteDataMonitor {

	private @Autowired BondQuoteDatastatisRepository bondQuoteDatastatisRep;

	private @Autowired ApplicationEventPublisher publisher;

	private @Autowired RedisUtil redisUtil;

	public String monitorBondQuoteData() {

		for (Integer postfrom : Constants.QUOTEDATA_POSTFROM_LIST) {
			String key = Constants.QUOTEDATA_POSTFROM_KEY + postfrom;

			BondQuoteDataCount dataCount = (BondQuoteDataCount) redisUtil.get(key);
			if (null != dataCount) {
				publisher.publishEvent(dataCount);
				redisUtil.set(key, new BondQuoteDataCount(0, new Date(), postfrom));
			}
		}
		return "done";

	}

	@Async
	@EventListener
	public void handleDataCount(BondQuoteDataCount quoteDataCount) {
		if (null == quoteDataCount) {
			return;
		}
		bondQuoteDatastatisRep.save(new BondQuoteDatastatis(quoteDataCount.getCount(), quoteDataCount.getQuoteDate(),
				quoteDataCount.getPostfrom(), new Date()));
	}
}
