package com.innodealing.engine.jdbc.bond;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.innodealing.engine.jdbc.BaseDao;
import com.innodealing.model.dm.bond.BondQuote;
import com.innodealing.util.SafeUtils;

/**
 * @author stephen.ma
 * @date 2016年9月7日
 * @clasename BondQuoteDao.java
 * @decription TODO
 */
@Component
public class BondQuoteDao extends BaseDao<BondQuote> {
	
	public List<Map<String, Object>> findBondBestQuoteInYield(Date date){
		String dateParam = SafeUtils.convertDateToString(date, SafeUtils.DATE_FORMAT);
		String dateBgn = dateParam+" 00:00:00";
		String dateEnd = dateParam+" 23:59:59";
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT t.bond_uni_code AS bondUniCode,t.bond_short_name AS bondShortName, ");
		sql.append("MAX(CASE WHEN t.bond_price IS NOT NULL AND t.side = 2 THEN t.bond_price WHEN t.bond_price IS NULL  AND t.side = 2 THEN  0  END) AS ofrPrice, ");
		sql.append("0 AS ofrVol,  ");
		sql.append("MIN(CASE WHEN t.bond_price IS NOT NULL AND t.side = 1 THEN t.bond_price WHEN t.bond_price IS NULL  AND t.side = 1 THEN  0  END) AS bidPrice, ");
		sql.append("0 AS bidVol, ");
		sql.append("CASE WHEN t.send_time IS NULL THEN '' ELSE DATE_FORMAT(t.send_time, '%Y-%m-%d') END AS sendTime  ");
		sql.append("FROM dmdb.t_bond_quote t   ");
		sql.append("WHERE t.`status` = 1 AND t.bond_uni_code > 0 AND t.price_unit = 1 AND t.send_time BETWEEN '").append(dateBgn).append("' AND '").append(dateEnd).append("' ");
		sql.append("GROUP BY t.bond_uni_code ");
		
		logger.info("findBondBestQuoteInYield sql:"+sql);
		
		List<Map<String, Object>> result = jdbcTemplate.queryForList(sql.toString());
		
		return result;
	}
	
	public List<Map<String, Object>> findBondBestQuoteInNetprice(Date date){
		String dateParam = SafeUtils.convertDateToString(date, SafeUtils.DATE_FORMAT);
		String dateBgn = dateParam+" 00:00:00";
		String dateEnd = dateParam+" 23:59:59";
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT t.bond_uni_code AS bondUniCode,t.bond_short_name AS bondShortName, ");
		sql.append("MIN(CASE WHEN t.bond_price IS NOT NULL AND t.side = 2 THEN t.bond_price WHEN t.bond_price IS NULL  AND t.side = 2 THEN 0  END) AS ofrPrice, ");
		sql.append("0 AS ofrVol,  ");
		sql.append("MAX(CASE WHEN t.bond_price IS NOT NULL AND t.side = 1 THEN t.bond_price WHEN t.bond_price IS NULL  AND t.side = 1 THEN  0  END) AS bidPrice, ");
		sql.append("0 AS bidVol, ");
		sql.append("CASE WHEN t.send_time IS NULL THEN '' ELSE DATE_FORMAT(t.send_time, '%Y-%m-%d') END AS sendTime  ");
		sql.append("FROM dmdb.t_bond_quote t   ");
		sql.append("WHERE t.`status` = 1 AND t.bond_uni_code > 0 AND t.price_unit = 2 AND t.send_time BETWEEN '").append(dateBgn).append("' AND '").append(dateEnd).append("' ");
		sql.append("GROUP BY t.bond_uni_code ");
		
		logger.info("findBondBestQuoteInNetprice sql:"+sql);
		
		List<Map<String, Object>> result = jdbcTemplate.queryForList(sql.toString());
		
		return result;
	}
	
	public List<Map<String, Object>> findBondQuoteTodaycurve(Date date){
		String dateParam = SafeUtils.convertDateToString(date, SafeUtils.DATE_FORMAT);
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT ");
		sql.append("	t.bond_uni_code as bondUniCode, ");
		sql.append("	t.bond_short_name as bondShortName, ");
		sql.append("	t.side, ");
		sql.append("	t.bond_price as bondPrice, ");
		sql.append("	t.bond_vol as bondVol, ");
		sql.append("	t.price_unit as priceUnit, ");
		sql.append("	t.send_time as sendTime, ");
		sql.append("	CASE WHEN d.bond_rate IS NULL THEN 0 ELSE d.bond_rate END as bondRate, ");
		sql.append("	CASE WHEN d.bond_weighted_rate IS NULL THEN 0 ELSE d.bond_weighted_rate END as bondWeightedRate ");
		sql.append(" FROM ");
		sql.append("	dmdb.t_bond_quote t LEFT JOIN dmdb.t_bond_deal_data d ON t.bond_uni_code = d.bond_uni_code ");
		sql.append(" WHERE t.STATUS = 1 AND t.price_unit = 1 AND t.bond_uni_code > 0 AND t.bond_uni_code IS NOT NULL ");
		sql.append(" AND DATE_FORMAT(t.send_time, '%Y-%m-%d')= '").append(dateParam).append("' ");
		sql.append(" ORDER BY t.send_time DESC ");
		
		logger.info("findBondQuoteTodaycurve sql:"+sql.toString());
		
		List<Map<String, Object>> result = jdbcTemplate.queryForList(sql.toString());
		
		return result;
	}
	
	public List<Map<String, Object>> findBondQuoteHistorycurve(){
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT  ");
		sql.append("	a.bondUniCode, ");
		sql.append("	a.bondShortName, ");
		sql.append("	SUM(a.bidPrice) AS bidPrice, ");
		sql.append("	SUM(a.bidVol) AS bidVol, ");
		sql.append("	SUM(a.ofrPrice) AS ofrPrice, ");
		sql.append("	SUM(a.ofrVol) AS ofrVol, ");
		sql.append("	SUM(a.bondWeightedRate) AS bondWeightedRate, ");
		sql.append("	SUM(a.bondDealingVolume) AS bondDealingVolume, ");
		sql.append("	a.sendTime ");
		sql.append("FROM ( ");
		sql.append("		SELECT ");
		sql.append("		    t.bond_uni_code AS bondUniCode, ");
		sql.append("		    t.bond_short_name AS bondShortName, ");
		sql.append("			CASE WHEN t.bond_price <= 50 THEN	MAX(t.bond_price) WHEN t.bond_price > 50 THEN	MIN(t.bond_price)	WHEN t.bond_price IS NULL THEN 0 END AS ofrPrice, ");
		sql.append("			SUM(t.bond_vol) AS ofrVol, ");
		sql.append("			0 AS bidPrice, ");
		sql.append("			0 AS bidVol, ");
		sql.append("			CASE WHEN d.bond_weighted_rate IS NULL THEN 0 ELSE d.bond_weighted_rate END AS bondWeightedRate, ");
		sql.append("			CASE WHEN d.bond_dealing_volume IS NULL THEN 0 ELSE d.bond_dealing_volume END AS bondDealingVolume, ");
		sql.append("			CASE WHEN t.send_time IS NULL THEN '' ELSE DATE_FORMAT(t.send_time, '%Y-%m-%d') END AS sendTime ");
		sql.append("	FROM dmdb.t_bond_quote t ");
		sql.append("	LEFT JOIN dmdb.t_bond_deal_data d ON t.bond_uni_code = d.bond_uni_code ");
		sql.append("	WHERE t.`status` = 1 AND t.side = 2 AND t.bond_uni_code > 0 AND t.bond_uni_code IS NOT NULL ");
		sql.append("	AND DATE_FORMAT(t.send_time, '%Y-%m-%d') != DATE_FORMAT(NOW(), '%Y-%m-%d') ");
		sql.append("	GROUP BY t.bond_uni_code,DATE_FORMAT(t.send_time, '%Y-%m-%d') ");
		sql.append("	UNION ");
		sql.append("	SELECT ");
		sql.append("		t.bond_uni_code AS bondUniCode, ");
		sql.append("		t.bond_short_name AS bondShortName, ");
		sql.append("		0 AS ofrPrice, ");
		sql.append("		0 AS ofrVol, ");
		sql.append("		CASE WHEN t.bond_price <= 50 THEN MIN(t.bond_price) WHEN t.bond_price > 50 THEN MAX(t.bond_price) WHEN t.bond_price IS NULL THEN 0 END AS bidPrice, ");
		sql.append("	  SUM(t.bond_vol) AS ofrVol, ");
		sql.append("	  CASE WHEN d.bond_weighted_rate IS NULL THEN 0 ELSE d.bond_weighted_rate END AS bondWeightedRate, ");
		sql.append("    CASE WHEN d.bond_dealing_volume IS NULL THEN 0 ELSE d.bond_dealing_volume END AS bondDealingVolume, ");
		sql.append("    CASE WHEN t.send_time IS NULL THEN '' ELSE DATE_FORMAT(t.send_time, '%Y-%m-%d') END AS sendTime ");
		sql.append("	FROM dmdb.t_bond_quote t ");
		sql.append("	LEFT JOIN dmdb.t_bond_deal_data d ON t.bond_uni_code = d.bond_uni_code ");
		sql.append("	WHERE t.`status` = 1 AND t.side = 1 AND t.bond_uni_code > 0 AND t.bond_uni_code IS NOT NULL ");
		sql.append("	AND DATE_FORMAT(t.send_time, '%Y-%m-%d') != DATE_FORMAT(NOW(), '%Y-%m-%d') ");
		sql.append("	GROUP BY t.bond_uni_code,DATE_FORMAT(t.send_time, '%Y-%m-%d') )a ");
		sql.append("GROUP BY a.bondUniCode,a.sendTime ORDER BY a.sendTime DESC ");
		
		logger.info("findBondQuoteTodaycurve sql:"+sql.toString());
		
		List<Map<String, Object>> result = jdbcTemplate.queryForList(sql.toString());
		
		return result;
	}
	
	
	public List<Map<String, Object>> findBondQuoteHistorycurveWithoutRate(){
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT  ");
		sql.append("	a.bondUniCode, ");
		sql.append("	a.bondShortName, ");
		sql.append("	SUM(a.bidPrice) AS bidPrice, ");
		sql.append("	SUM(a.bidVol) AS bidVol, ");
		sql.append("	SUM(a.ofrPrice) AS ofrPrice, ");
		sql.append("	SUM(a.ofrVol) AS ofrVol, ");
		sql.append("	a.sendTime ");
		sql.append("FROM ( ");
		sql.append("		SELECT ");
		sql.append("		    t.bond_uni_code AS bondUniCode, ");
		sql.append("		    t.bond_short_name AS bondShortName, ");
		sql.append("			CASE WHEN t.bond_price IS NULL THEN 0 ELSE MAX(t.bond_price) END AS ofrPrice, ");
		sql.append("			SUM(t.bond_vol) AS ofrVol, ");
		sql.append("			0 AS bidPrice, ");
		sql.append("			0 AS bidVol, ");
		sql.append("			DATE_FORMAT(t.send_time, '%Y-%m-%d') AS sendTime ");
		sql.append("	FROM dmdb.t_bond_quote t ");
		sql.append("	WHERE t.`status` = 1 AND t.side = 2 AND t.bond_uni_code > 0 AND t.price_unit = 1 ");
		sql.append("	AND DATE_FORMAT(t.send_time, '%Y-%m-%d') != DATE_FORMAT(NOW(), '%Y-%m-%d') ");
		sql.append("	GROUP BY t.bond_uni_code,DATE_FORMAT(t.send_time, '%Y-%m-%d') ");
		sql.append("	UNION ");
		sql.append("	SELECT ");
		sql.append("		t.bond_uni_code AS bondUniCode, ");
		sql.append("		t.bond_short_name AS bondShortName, ");
		sql.append("		0 AS ofrPrice, ");
		sql.append("		0 AS ofrVol, ");
		sql.append("		CASE WHEN t.bond_price IS NULL THEN 0 ELSE MIN(t.bond_price) END AS bidPrice, ");
		sql.append("	  SUM(t.bond_vol) AS ofrVol, ");
		sql.append("    DATE_FORMAT(t.send_time, '%Y-%m-%d') AS sendTime ");
		sql.append("	FROM dmdb.t_bond_quote t ");
		sql.append("	WHERE t.`status` = 1 AND t.side = 1 AND t.bond_uni_code > 0 AND t.price_unit = 1 ");
		sql.append("	AND DATE_FORMAT(t.send_time, '%Y-%m-%d') != DATE_FORMAT(NOW(), '%Y-%m-%d') ");
		sql.append("	GROUP BY t.bond_uni_code,DATE_FORMAT(t.send_time, '%Y-%m-%d') )a ");
		sql.append("GROUP BY a.bondUniCode,a.sendTime ORDER BY a.sendTime DESC ");
		
		logger.info("findBondQuoteHistorycurveWithoutRate sql:"+sql.toString());
		
		List<Map<String, Object>> result = jdbcTemplate.queryForList(sql.toString());
		
		return result;
	}
	
	public List<Map<String, Object>> findSinglebondComparison(String dateBgn, String dateEnd){
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT  ");
		sql.append("	MAX(CASE WHEN t.bond_price IS NOT NULL AND t.side = 2 THEN t.bond_price WHEN t.bond_price IS NULL AND t.side = 2 THEN 0 END) AS ofrPrice, ");
		sql.append("	MIN(CASE WHEN t.bond_price IS NOT NULL AND t.side = 1 THEN t.bond_price WHEN t.bond_price IS NULL AND t.side = 1 THEN 0 END) AS bidPrice, ");
		sql.append("	t.bond_uni_code AS bondUniCode, ");
		sql.append("	t.bond_code AS bondCode, ");
		sql.append("	t.bond_short_name AS bondShortName, ");
		sql.append("	DATE_FORMAT(t.send_time, '%Y-%m-%d') AS sendTime ");
		sql.append("FROM  ");
		sql.append("	dmdb.t_bond_quote t   ");
		sql.append("WHERE t.`status` = 1 AND t.price_unit=1 AND t.bond_uni_code > 0 AND t.send_time BETWEEN '").append(dateBgn).append("' AND '").append(dateEnd).append("' ");
		sql.append("GROUP BY DATE_FORMAT(t.send_time, '%Y-%m-%d'),t.bond_uni_code ");
		
		logger.info("findSinglebondComparison sql:"+sql.toString());
		
		List<Map<String, Object>> result = jdbcTemplate.queryForList(sql.toString());
		
		return result;
	}
	
}
