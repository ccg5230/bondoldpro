package com.innodealing.adapter;

import com.innodealing.bond.param.BrokerBondQuoteParam;
import com.innodealing.model.dm.bond.BondQuote;

/** 
* @author feng.ma
* @date 2017年5月15日 上午11:29:10 
* @describe 
*/
public class BrokerBondQuoteAdapter {
	
	public static BrokerBondQuoteParam handleBrokerBondQuoteParam(BondQuote bondQuote){
		BrokerBondQuoteParam brokerBondQuote = new BrokerBondQuoteParam();
		brokerBondQuote.setAnonymous(0);
		brokerBondQuote.setBondCode(bondQuote.getBondCode());
		brokerBondQuote.setBondPrice(bondQuote.getBondPrice());
		brokerBondQuote.setBondShortName(bondQuote.getBondShortName());
		brokerBondQuote.setBondUniCode(bondQuote.getBondUniCode());
		brokerBondQuote.setBondVol(bondQuote.getBondVol());
		brokerBondQuote.setPostfrom(bondQuote.getPostfrom());
		brokerBondQuote.setRemark(bondQuote.getRemark());
		brokerBondQuote.setSendTime(bondQuote.getSendTime());
		brokerBondQuote.setSide(bondQuote.getSide());
		
		return brokerBondQuote;
	}
	
}
