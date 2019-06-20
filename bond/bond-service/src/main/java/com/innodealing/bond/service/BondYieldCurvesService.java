package com.innodealing.bond.service;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.innodealing.engine.mongo.bond.BondPubParRepository;
import com.innodealing.engine.mongo.bond.BondYieldCurveRepository;
import com.innodealing.model.mongo.dm.BondPubPar;
import com.innodealing.model.mongo.dm.BondYieldCurve;
import com.innodealing.util.SafeUtils;
import com.innodealing.util.StringUtils;

/**
 * @author lihao
 * @date 2016年9月11日
 * @ClassName BondYieldCurvesService
 * @Description: TODO
 */
@Service
public class BondYieldCurvesService {
	
	private final static Logger logger = LoggerFactory.getLogger(BondQuoteService.class);
	
	@SuppressWarnings("unused")
	private @Autowired BondYieldCurveRepository bondYieldCurveRepository;
	
	private @Autowired BondPubParRepository bondPubParRepository;
	
	private @Autowired MongoOperations mongoOperations;
	
	private @Autowired MongoTemplate mongoTemplate;
	
	/**
	 * 查询曲线债券名称
	 */
	public List<BondPubPar> findYieldCurvesBondName(){
		return bondPubParRepository.findAll();
	}
	
	/**
	 * 根据曲线名称查找,时间desc返回最新的一条数据 
	 * @param bondNameOneId 曲线一 参数
	 * @param bondNameTwoId 曲线二 参数
	 * @return 返回 Map<String,BondYieldCurve> 类型
	 */
	public Map<String,Object> findYieldCurves(Integer bondNameOneId,Integer bondNameTwoId){
		
		//第一次请求,bondNameOneId , bondNameTwoId 可能为 null 获 '' 或是其他不合法的值
		if(StringUtils.isEmpty(bondNameOneId)||SafeUtils.getInteger(bondNameOneId)<=0){
			bondNameOneId = 1;
		}
		if(StringUtils.isEmpty(bondNameTwoId)||SafeUtils.getInteger(bondNameTwoId)<=0){
			bondNameTwoId = 2;
		}
		
		Map<String,Object> curves = new HashMap<>();
		
		//拼接 SQL语句
		//PageRequest pageRequest = new PageRequest(0, 1,new Sort(Sort.Direction.DESC,"date"));
		
		BondYieldCurve newDate = getNewDate();
		if(StringUtils.isEmpty(newDate)){
			curves.put("curveOne","");
			curves.put("curveTwo","");
			curves.put("date", "");
			return curves;
		}else{
			curves.put("date", newDate.getDate());
		}
		
		Query queryOne = new Query();
		//queryOne.addCriteria(Criteria.where("curveId").is(bondNameOneId)).with(pageRequest);
		queryOne.addCriteria(Criteria.where("curveId").is(bondNameOneId).and("date").is(newDate.getDate()).and("status").is("1"));
		//System.out.println(queryOne);
		List<BondYieldCurve> one = mongoOperations.find(queryOne, BondYieldCurve.class);
		
		logger.info(one.toString());
		
		Query queryTwo = new Query();
		//queryTwo.addCriteria(Criteria.where("curveId").is(bondNameTwoId)).with(pageRequest);
		queryTwo.addCriteria(Criteria.where("curveId").is(bondNameTwoId).and("date").is(newDate.getDate()).and("status").is("1"));
		List<BondYieldCurve> two = mongoOperations.find(queryTwo, BondYieldCurve.class);
		
		logger.info(two.toString());
		
		//存放到 Map 对象中
		if(!StringUtils.isEmpty(one)&&one.size()>0){
			one = getCurseName(one);
			curves.put("curveOne",one.get(0));
		}else{
			curves.put("curveOne","");
			logger.info("the one curse is null");
		}
		if(!StringUtils.isEmpty(two)&&two.size()>0){
			two = getCurseName(two);
			curves.put("curveTwo",two.get(0));
		}else{
			curves.put("curveTwo","");
			logger.info("the two curse is null");
		}
		
		return curves;
	} 
	
	//获取最新时间
	public BondYieldCurve getNewDate(){
		BondYieldCurve b = null;
		PageRequest pageRequest = new PageRequest(0, 1,new Sort(Sort.Direction.DESC,"date"));
		Query query = new Query();
		query.with(pageRequest).limit(1);
		List<BondYieldCurve> find = mongoOperations.find(query, BondYieldCurve.class);
		if(!StringUtils.isEmpty(find)&&find.size()>0){
			b= find.get(0);
		}
		return b;
	}
	
	/**
	 * 根据曲线名称和日期,返回对应日期内的所有数据,并比较算出bp值
	 * @param bondNameOneId 曲线对比一 参数
	 * @param bondNameTwoId 曲线对比二 参数
	 * @param bondDate 曲线对比日期 参数
	 * @return Map<String,List<BondYieldCurve>>
	 */
	public Map<String,Object> findYieldCurvesCompare(Integer bondNameOneId,Integer bondNameTwoId,String bondDate){
		
		HashMap<String, Object> curves = new HashMap<>();
		
		//判断 bondDate 是否合法 , 不符合要求默认为3月
		bondDate = getBondDate(bondDate);
		
		//拼接 SQL 语句
		Sort sort = new Sort(Sort.Direction.ASC,"date");
		
		Query queryOne= new Query();
		queryOne.addCriteria(Criteria.where("curveId").is(bondNameOneId)).with(sort).fields().include(bondDate).include("curveId").include("date").include("status");
		
		Query queryTwo= new Query();
		queryTwo.addCriteria(Criteria.where("curveId").is(bondNameTwoId)).with(sort).fields().include(bondDate).include("curveId").include("date").include("status");
		
		//曲线一 集合
		List<BondYieldCurve> y1 = mongoOperations.find(queryOne, BondYieldCurve.class);
		y1 = getCurseName(y1);
		y1 = getBondYieldCurves(y1);
		curves.put("curveOne", y1);
		
		
		//曲线2 集合
		List<BondYieldCurve> y2 = mongoOperations.find(queryTwo, BondYieldCurve.class);
		y2 = getCurseName(y2);
		y2 = getBondYieldCurves(y2);
		curves.put("curveTwo", y2);
		
		//BP 集合 
		List<BondYieldCurve> y3 = getBP(y1,y2,bondDate);
		
		curves.put("curveThree", y3);
		return curves;
	}
	
	//如果状态为2,赋值为0
	public List<BondYieldCurve> getBondYieldCurves(List<BondYieldCurve> bycs){
		if(!StringUtils.isEmpty(bycs)&&bycs.size()>0){
			for (BondYieldCurve b : bycs) {
				if(b.getStatus().equals("2")){
					b.setThreem("0");
					b.setSixm("0");
					b.setNinem("0");
					b.setOney("0");
					b.setThreey("0");
					b.setFivey("0");
					b.setSeveny("0");
					b.setTeny("0");
				}
			}
		}
		return bycs;
	}
	
	// 判断参数日期
	public String getBondDate(String bondDate){
		String str = "";
		if(StringUtils.isEmpty(bondDate)){
			str = "threem";
		}else{
			bondDate = bondDate.toLowerCase();
			if(bondDate.equals("threem")||bondDate.equals("sixm")||bondDate.equals("ninem")||
					bondDate.equals("oney")||bondDate.equals("threey")||bondDate.equals("fivey")||
					bondDate.equals("seveny")||bondDate.equals("teny")){
				str = bondDate;
			}else{
				str = "threem";
			}
		}
		return str;
	}
	
	//计算BP,并为 BP 集合添加数据
	public List<BondYieldCurve> getBP(List<BondYieldCurve> y1, List<BondYieldCurve> y2, String bondDate) {
		LinkedList<BondYieldCurve> y3 = new LinkedList<>();
		
		for (int i = 0; i < y1.size(); i++) {
			BondYieldCurve b1 = y1.get(i);
			BondYieldCurve b2 = y2.get(i);
			BondYieldCurve byc = new BondYieldCurve();
			if (bondDate.equals("threem")&&!StringUtils.isEmpty(b1.getThreem())&&!StringUtils.isEmpty(b2.getThreem())) {
				byc.setThreem(getSub(b1.getThreem(), b2.getThreem()));
			} else if (bondDate.equals("sixm")&&!StringUtils.isEmpty(b1.getSixm())&&!StringUtils.isEmpty(b2.getSixm())) {
				byc.setSixm(getSub(b1.getSixm(), b2.getSixm()));
			} else if (bondDate.equals("ninem")&&!StringUtils.isEmpty(b1.getNinem())&&!StringUtils.isEmpty(b2.getNinem())) {
				byc.setNinem(getSub(b1.getNinem(), b2.getNinem()));
			} else if (bondDate.equals("oney")&&!StringUtils.isEmpty(b1.getOney())&&!StringUtils.isEmpty(b2.getOney())) {
				byc.setOney(getSub(b1.getOney(), b2.getOney()));
			} else if (bondDate.equals("threey")&&!StringUtils.isEmpty(b1.getThreey())&&!StringUtils.isEmpty(b2.getThreey())) {
				byc.setThreey(getSub(b1.getThreey(), b2.getThreey()));
			} else if (bondDate.equals("fivey")&&!StringUtils.isEmpty(b1.getFivey())&&!StringUtils.isEmpty(b2.getFivey())) {
				byc.setFivey(getSub(b1.getFivey(), b2.getFivey()));
			} else if (bondDate.equals("seveny")&&!StringUtils.isEmpty(b1.getSeveny())&&!StringUtils.isEmpty(b2.getSeveny())) {
				byc.setSeveny(getSub(b1.getSeveny(), b2.getSeveny()));
			} else if (bondDate.equals("teny")&&!StringUtils.isEmpty(b1.getTeny())&&!StringUtils.isEmpty(b2.getTeny())) {
				byc.setTeny(getSub(b1.getTeny(), b2.getTeny()));
			}
			byc.setDate(b1.getDate());
			byc.setName(b1.getName()+"-"+b2.getName());
			y3.addLast(byc);
		}
		return y3;
	}
	
	// 两个数值相减装换成BP,保留2位小数
	public String getSub(String s1,String s2){
		if (StringUtils.isBlank(s1)) {
			s1 = "0";
		}
		if (StringUtils.isBlank(s2)) {
			s2 = "0";
		}
		Double d = SafeUtils.getDouble(s1) - SafeUtils.getDouble(s2);
		// 转换成BP 公式 => 0.1% = 10bp
		d = d / 0.01;
		return new DecimalFormat(".##").format(d);
	}
	
	// 根据债券curse_cod 获取 对应的债券名称 债券类型不多所以这边用写死的方式判断
	public List<BondYieldCurve> getCurseName(List<BondYieldCurve> bycs) {
		if (!StringUtils.isEmpty(bycs) && bycs.size() > 0) {
			for (BondYieldCurve b : bycs) {
				if (b.getCurveId() == 1) {
					b.setName("中债国债");
				} else if (b.getCurveId() == 2) {
					b.setName("中债农发债");
				} else if (b.getCurveId() == 3) {
					b.setName("中债进出口行债 ");
				} else if (b.getCurveId() == 4) {
					b.setName("中债国开债");
				} else if (b.getCurveId() == 5) {
					b.setName("中债央行票据");
				} else if (b.getCurveId() == 6) {
					b.setName("中债地方政府债（AAA）");
				} else if (b.getCurveId() == 7) {
					// b.setName("");
				} else if (b.getCurveId() == 8) {
					b.setName("中债地方政府债（AAA-） ");
				} else if (b.getCurveId() == 9) {
					b.setName("中债铁道债");
				} else if (b.getCurveId() == 10) {
					b.setName("中债城投债（AAA）");
				} else if (b.getCurveId() == 11) {
					b.setName("中债中短期票据（AAA）");
				} else if (b.getCurveId() == 12) {
					b.setName("中债商业银行普通债（AAA）");
				} else if (b.getCurveId() == 13) {
					b.setName("中债企业债（AAA）");
				} else if (b.getCurveId() == 14) {
					b.setName("中债企业债（AAA-）");
				} else if (b.getCurveId() == 15) {
					b.setName("中债企业债（AA+）");
				} else if (b.getCurveId() == 16) {
					b.setName("中债企业债（AA-）");
				}
			}
		}
		return bycs;
	}
}
