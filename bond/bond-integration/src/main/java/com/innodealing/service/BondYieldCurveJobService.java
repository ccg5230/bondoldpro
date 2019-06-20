package com.innodealing.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.innodealing.engine.mongo.bond.BondPubParRepository;
import com.innodealing.engine.mongo.bond.BondYieldCurveRepository;
import com.innodealing.model.mongo.dm.BondPubPar;
import com.innodealing.model.mongo.dm.BondYieldCurve;
import com.innodealing.util.SafeUtils;

/**
 * @author lihao
 * @date 2016年9月13日
 * @ClassName BondYieldCurveService
 * @Description: 定时刷新 mysql 表 t_bond_yield_curve 到 mongodb conllection t_bond_yield_curve
 */
@EnableScheduling
@Component
public class BondYieldCurveJobService {

	private @Autowired JdbcTemplate jdbcTemplate;
	
	private @Autowired BondYieldCurveRepository dao;
	
    private @Autowired BondPubParRepository pubdao;
    
    private @Autowired MongoTemplate mongoTemplate;
	
	public List<Map<String, Object>> findBondYieldCurves(){
		String sql = "SELECT * FROM t_bond_yield_curve";
		return jdbcTemplate.queryForList(sql);
	}
	
	public List<Map<String, Object>> getPubPars(){
		String sql = "SELECT * FROM dmdb.`t_pub_par` p WHERE p.`PAR_SYS_CODE`=1010";
		return jdbcTemplate.queryForList(sql);
	}
	
	public void refreshBondYieldCure(){
		if(!mongoTemplate.collectionExists("t_bond_yield_curve")){
			mongoTemplate.createCollection("t_bond_yield_curve");
		}
		//定时任务刷新 5分钟
    	List<Map<String, Object>> findBondYieldCurves = findBondYieldCurves();
    	List<BondYieldCurve> bs = new ArrayList<>();
    	for (Map<String, Object> map : findBondYieldCurves) {
			 BondYieldCurve byc = new BondYieldCurve();
			 byc.setCurveId(SafeUtils.getInteger(map.get("curve_code")));
			 byc.setDate(SafeUtils.getString(map.get("curve_time")));
			 byc.setThreem(SafeUtils.getString(map.get("ytm_3m")));
			 byc.setSixm(SafeUtils.getString(map.get("ytm_6m")));
			 byc.setNinem(SafeUtils.getString(map.get("ytm_9m")));
			 byc.setOney(SafeUtils.getString(map.get("ytm_1y")));
			 byc.setThreey(SafeUtils.getString(map.get("ytm_3y")));
			 byc.setFivey(SafeUtils.getString(map.get("ytm_5y")));
			 byc.setSeveny(SafeUtils.getString(map.get("ytm_7y")));
			 byc.setTeny(SafeUtils.getString(map.get("ytm_10y")));
			 byc.setStatus(SafeUtils.getString(map.get("is_show")));
			 bs.add(byc);
		}
    	dao.deleteAll();
    	dao.save(bs);
	}

	public void refreshPubPar(){
		if(!mongoTemplate.collectionExists("t_pub_par")){
			mongoTemplate.createCollection("t_pub_par");
		}
		//每天早上上午10:15
		List<Map<String, Object>> maps = getPubPars();
    	List<BondPubPar> pubpars = new ArrayList<>();
    	for (Map<String, Object> map : maps) {
			BondPubPar pub = new BondPubPar();
			pub.setCurveId(SafeUtils.getInteger(map.get("PAR_CODE")));
			pub.setName((SafeUtils.getString(map.get("PAR_NAME"))));
			pubpars.add(pub);
		}
    	pubdao.deleteAll();
    	pubdao.save(pubpars);
	}
	
}
