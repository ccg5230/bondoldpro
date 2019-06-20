package com.innodealing.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.innodealing.engine.mongo.bond.BondDetailRepository;

@Service
public class PublicOpinionSyncService {

	private static final Logger LOG = LoggerFactory.getLogger(PublicOpinionSyncService.class);
	
    @Autowired
	private BondDetailRepository detailRepository;
    
	void sync() {
	    RestTemplate restTemplate = new RestTemplate();
	   // PublicOpinion po = restTemplate.getForObject("http://po.innodealing.com/api/sync", PublicOpinion.class);
	  //  LOG.info();
	}
}
