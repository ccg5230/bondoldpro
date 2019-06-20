package com.innodealing.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.innodealing.controller.BaseJunit;
import com.innodealing.model.mongo.dm.BondSentiment;

public class NegSentimentCacheTest extends BaseJunit{

	
	@Autowired
	NegSentimentCache negSentimentCache;
	
	@Test
	public void testGetNegSentimentCount() throws Exception {
		BondSentiment negSentimentCount = negSentimentCache.getNegSentimentCount(200000175L);
		System.out.println(negSentimentCount);
	}

}
