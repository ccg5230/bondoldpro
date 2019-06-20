package com.innodealing.bond.service.rrs;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.innodealing.bond.service.BondComExtService;

@Service
public class BondDocRuleFactory 
{
	private static final Logger LOG = LoggerFactory.getLogger(BondDocRuleFactory.class);
	
	@Autowired
	private ApplicationContext context;
	
	@Autowired
	BondComExtService comExtService;
	
	@Autowired
	BondDocRatioService ratioService;
	    
	
	private static final Map<Integer, String> modelStrategyMap = new HashMap<Integer, String>();
	
	static {
		modelStrategyMap.put(BondComExtService.MODEL_TYPE_BUSI,"BondDocRuleBusi");
		modelStrategyMap.put(BondComExtService.MODEL_TYPE_INDU,"BondDocRuleIndu");
		modelStrategyMap.put(BondComExtService.MODEL_TYPE_REAL_EST,"BondDocRuleRealEstate");
		modelStrategyMap.put(BondComExtService.MODEL_TYPE_BANK,"BondDocRuleBank");
		modelStrategyMap.put(BondComExtService.MODEL_TYPE_SECU,"BondDocRuleSecu");
		modelStrategyMap.put(BondComExtService.MODEL_TYPE_INSU,"BondDocRuleInsu");
		
        modelStrategyMap.put(BondComExtService.PRI_MODEL_TYPE_BUSI,"BondDocRuleBusi");
        modelStrategyMap.put(BondComExtService.PRI_MODEL_TYPE_INDU,"BondDocRuleIndu");
        modelStrategyMap.put(BondComExtService.PRI_MODEL_TYPE_REAL_EST,"BondDocRuleRealEstate");
        modelStrategyMap.put(BondComExtService.PRI_MODEL_TYPE_BANK,"BondDocRuleBank");
        modelStrategyMap.put(BondComExtService.PRI_MODEL_TYPE_SECU,"BondDocRuleSecu");
        modelStrategyMap.put(BondComExtService.PRI_MODEL_TYPE_INSU,"BondDocRuleInsu");
	}

	public StrategyRuleI createStategy(Long issuerId, Long userId, Long year, Long quarter) {
		try {
			Integer type = comExtService.getModelType(issuerId.toString());
			String beanName = modelStrategyMap.get(type);
			StrategyRuleI bean = (StrategyRuleI)context.getBean(beanName, issuerId, userId, year, quarter, context.getBean("BondPubDocEngine"));
			return bean;
		}
		catch(Exception ex) {
			LOG.error("failed to createStategy on issuerId:" + issuerId, ex);
		}
		return null;
	}

	public StrategyRuleI createComparisonStrategy(Long issuerId, Long userId, Long firstDate, Long secondDate) {
		try {
			Integer type = comExtService.getModelType(issuerId.toString());
			String beanName = modelStrategyMap.get(type);
			StrategyRuleI bean = (StrategyRuleI) context.getBean(beanName, issuerId, userId, firstDate, secondDate, context.getBean("BondPubDocEngine"), true);
			return bean;
		} catch(Exception ex) {
			LOG.error("createComparisonStrategy error with issuerId=" + issuerId, ex);
		}
		return null;
	}

	public StrategyRuleI createPriStategy(Long taskId) 
	{
	    try {
	        Integer type = ratioService.getModelTypeByTaskId(taskId.toString());
	        String beanName = modelStrategyMap.get(type);
	        StrategyRuleI bean = (StrategyRuleI)context.getBean(beanName, taskId, null, null, null, context.getBean("BondPriDocEngine"));
	        return bean;
	    }
	    catch(Exception ex) {
	        LOG.error("failed to createPriStategy on issuerId:" + taskId, ex);
	    }
	    return null;
	}
}
