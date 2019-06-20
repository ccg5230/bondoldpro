package com.innodealing.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.innodealing.BondApp;
import com.innodealing.bond.service.BondYieldCurvesService;
import com.innodealing.engine.mongo.bond.BondPubParRepository;
import com.innodealing.engine.mongo.bond.BondYieldCurveRepository;
import com.innodealing.model.mongo.dm.BondPubPar;
import com.innodealing.model.mongo.dm.BondYieldCurve;
import com.innodealing.service.BondYieldCurveJobService;
import com.innodealing.util.SafeUtils;
import com.mongodb.BasicDBObject;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BondApp.class)
@WebIntegrationTest
public class BondYeildCurseTest {

    @Autowired
    private BondYieldCurvesService bs;
    @Autowired
    private BondYieldCurveJobService bondjob;
    @Autowired
    private BondYieldCurveRepository dao;
    @Autowired
    private BondPubParRepository pubdao;

    @Test
    public void test4(){
    	//定时刷新机构名称
    	List<Map<String, Object>> maps = bondjob.getPubPars();
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
    
    @Test
    public void test3() {
    	//定时任务刷新
    	List<Map<String, Object>> findBondYieldCurves = bondjob.findBondYieldCurves();
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
			 bs.add(byc);
		}
    	dao.deleteAll();
    	dao.save(bs);
    }
    
    @Test
    public void test() {
    	//获取债券名称
    	List<BondPubPar> list = bs.findYieldCurvesBondName();
    	for (BondPubPar bondPubPar : list) {
			System.out.println("-----------"+bondPubPar.getCurveId()+","+bondPubPar.getName());
		}
    }
    
    @Test
    public void test1(){
    	//曲线
    	Map<String, Object> map = bs.findYieldCurves(1, 2);
    	BondYieldCurve m1 = (BondYieldCurve) map.get("curveOne");
    	BondYieldCurve m2 = (BondYieldCurve) map.get("curveTwo");
    	print(m1);
    	print(m2);
    }
    
    @Test
    public void test2(){
    	//曲线对比
    	Map<String, Object> map = bs.findYieldCurvesCompare(1, 2,"threem");
    	List<BondYieldCurve> m1 = (List<BondYieldCurve>) map.get("curveOne");
    	List<BondYieldCurve> m2 = (List<BondYieldCurve>) map.get("curveTwo");
    	List<BondYieldCurve> m3 = (List<BondYieldCurve>) map.get("curveThree");
    	for (BondYieldCurve byc : m1) {
			System.out.println("曲线一："+byc.getThreem()+","+byc.getSixm());
		}
    	for (BondYieldCurve byc : m2) {
			System.out.println("曲线二："+byc.getThreem());
		}
    	for (BondYieldCurve byc : m3) {
			System.out.println("BP："+byc.getThreem());
		}
    }
    
    public void print(BondYieldCurve m){
    	System.out.println("债券编号:"+m.getCurveId()+m.getName()+","
    			+ "三月:"+m.getThreem()+",六月:"+m.getSixm()+",九月:"+m.getNinem()
    			+",一年:"+m.getOney()+",三年:"+m.getThreey()+",五年:"+m.getFivey()
    			+",七年:"+m.getSeveny()+",十年:"+m.getTeny()+",数据日期："+m.getDate());
    }

}
