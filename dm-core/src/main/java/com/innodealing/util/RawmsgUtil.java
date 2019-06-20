package com.innodealing.util;

import com.innodealing.model.dm.bond.BondQuote;

public class RawmsgUtil {
	
	//现券买卖
	public static String generateRawmsg_Type9(BondQuote bondQuote) {
		StringBuffer buffer = new StringBuffer();
		if (1 == SafeUtils.getInt(bondQuote.getSide())){
			buffer.append(" 收券");
		}else if(2 == SafeUtils.getInt(bondQuote.getSide())){
			buffer.append(" 出券");
		}else{
			buffer.append(" 发行");
		}
		buffer.append(" ").append(SafeUtils.getString(bondQuote.getBondShortName()));
		buffer.append(" ").append(SafeUtils.getString(bondQuote.getBondCode()));

		int tmptenor = SafeUtils.getInt(bondQuote.getTenor());
		if(tmptenor > 0){
			buffer.append(" ").append(tmptenor + "天");
		}

		int priceUnit = SafeUtils.getInt(bondQuote.getPriceUnit());
		switch(priceUnit){
		case 1:
			buffer.append(" 收益率/净价");
			double bondYeild = SafeUtils.getDouble(bondQuote.getBondPrice());
			if (bondYeild > 0) {
				buffer.append(" ").append(bondYeild).append("%");
			}
			break;
		case 2:
			buffer.append(" 收益率/净价");
			double bondNetprice = SafeUtils.getDouble(bondQuote.getBondPrice());
			if (bondNetprice > 0) {
				buffer.append(" ").append(bondNetprice).append("元");
			}
			break;
		default:
			break;
		}
		
		double tmpamount = SafeUtils.getDouble(bondQuote.getBondVol());
		if (tmpamount > 0) {
			buffer.append(" 金额").append(getAmountDesc(tmpamount));
		}

		return buffer.toString();
	}
	
	private static String getAmountDesc(double amount) {
		String tmpamount = amount + "万";
		if (amount >= 10000)
			tmpamount = amount / 10000 + "亿";
		return tmpamount;
	}
	
}
