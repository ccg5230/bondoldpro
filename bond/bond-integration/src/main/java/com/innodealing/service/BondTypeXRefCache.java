package com.innodealing.service;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.innodealing.aop.NoLogging;
import com.innodealing.engine.jpa.dm.BondTypeXRefRepository;
import com.innodealing.model.dm.bond.BondTypeXRef;

@Component
public class BondTypeXRefCache {
	
	private static final Logger LOG = LoggerFactory.getLogger(BondTypeXRefCache.class);
	
	@Autowired
	BondTypeXRefRepository typeXRefReposity;

	private HashMap<Integer, BondTypeXRef> bondTypeMap = new HashMap<Integer, BondTypeXRef>();
	
	public BondTypeXRefCache(){}

	@NoLogging
	public synchronized BondTypeXRef get(Integer ccxeBondType)
	{
		if (ccxeBondType == null) 
			return null;
		
		if (!bondTypeMap.isEmpty()) return bondTypeMap.get(ccxeBondType);
		List<BondTypeXRef> comExtList = typeXRefReposity.findAll();
		for (BondTypeXRef comExt : comExtList) {
			bondTypeMap.put(comExt.getCcxeCode(), comExt);
		}
		
    	return bondTypeMap.get(ccxeBondType);
	}
}
