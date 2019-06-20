package com.innodealing.bond.service.finance;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.google.common.base.Objects;
import com.innodealing.bond.vo.finance.IndicatorChangeVo;
import com.innodealing.bond.vo.finance.IssIToDSendMqVo;
import com.innodealing.engine.jdbc.bond.AmaBondDao;
import com.innodealing.engine.jdbc.bond.IndicatorDao;
import com.innodealing.json.consts.Constants;
import com.innodealing.json.portfolio.FinSpclindicatorJson;
import com.innodealing.model.dm.bond.BondCom;
import com.innodealing.model.mongo.dm.bond.finance.IndicatorCareTaker;
import com.innodealing.model.mongo.dm.bond.finance.IndicatorMemento;
import com.innodealing.model.mongo.dm.bond.finance.IndicatorSnapshot;
import com.innodealing.rabbitmq.MqSenderService;
import com.innodealing.util.SafeUtils;

/**
 * 负责处理指标状态变动，主要用于指标同期、自身、和行业排名变化
 * <p>
 * 设计方式参考备忘录模式
 * 
 * @author 赵正来
 *
 */



@Service
public class IndicatorStateService {

	private @Autowired MongoTemplate mongoTemplate;
	
	private @Autowired IndicatorDao indicatorDao;
	
	private @Autowired AmaBondDao amaBondDao;
	
	private @Autowired BondFinanceInfoService bondFinanceInfoService;
	
	private @Autowired MqSenderService mqSenderService;
	
	private final Logger log = LoggerFactory.getLogger(IndicatorStateService.class);
	
	
	
	/**
	 * 初始化主体的备忘录信息
	 * @param comUniCode
	 * @return
	 */
	public boolean initIndicatorCareTaker(Long comUniCode){
		
		LinkedList<IndicatorMemento> indicatorMementos =  getIndicatorMementos(comUniCode);
		indicatorMementos.sort(new Comparator<IndicatorMemento>() {

			@Override
			public int compare(IndicatorMemento m1, IndicatorMemento m2) {
				return m2.getState().compareTo(m1.getState());
			}
		});
		batchSave(indicatorMementos, comUniCode);
		return true;
	}
	
	/**
	 * 添加备忘录并通知指标变动
	 * @param comUniCode
	 * @param finDate
	 * @return
	 * @throws Exception 
	 */
	public boolean addInduAndSendMsg(Long comUniCode,Date finDate, boolean rankIndu, Integer finRptFlag) throws Exception{
		//查找该主体行业内所有的指标变化，并通知客户端
		List<Long> comUniCodes = new ArrayList<>();
		if(rankIndu){
			comUniCodes = amaBondDao.getInduComUniCodes(comUniCode);
		}
		comUniCodes.remove(comUniCode);
		comUniCodes.add(0, comUniCode);
//		ExecutorService service = Executors.newFixedThreadPool(2);
		for (Long comCode : comUniCodes) {
			IndicatorCareTaker careTaker = mongoTemplate.findOne(new Query(Criteria.where("comUniCode").is(comCode)), IndicatorCareTaker.class);
			boolean onlyRank = !Objects.equal(comUniCode, comCode);
			//是否有备忘录，没有则初始化
			Date date = null;
			if(Objects.equal(careTaker, null)){
				//发送mq
				if(onlyRank){
					//初始化
					save(comCode, null, true);
					careTaker = mongoTemplate.findOne(new Query(Criteria.where("comUniCode").is(comCode)), IndicatorCareTaker.class);
					try {
						date = SafeUtils.convertStringToDate(careTaker.getLastFinDate(), SafeUtils.DATE_FORMAT);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
			}else{
				try {
					date = SafeUtils.convertStringToDate(careTaker.getLastFinDate(), SafeUtils.DATE_FORMAT);
					if(onlyRank){
						save(comCode, date, false);
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			sendMsg(comCode, date, onlyRank, finRptFlag);
		}
		return true;
	}
	
	/**
	 * 第一项，披露财报的判定：<br>
	 *当主体新增一期财报，首先判断财报日期与当前时间的比对。<br>
	 *若为一季报，就判断是否大于本年度5月1日<br>
	 *若为半年报，判断是否大于本年度9月1日<br>
	 *若为三季报，判断是否大于本年度11月1日<br>
	 *若为年报，判断是否大于下一年度5月1日<p>
	 *例如：<br>
	 *当主体新增2017年一季报时，当前时间若小于2017年5月1日，则推送消息<br>
	 *当主体新增2017年半年报（二季报，Q2）时，当前时间若小于2017年9月1日，则推送消息<br>
	 *当主体新增2016年三季报时，当前时间若小于2016年11月1日，则推送消息<br>
	 *当主体新增2015年年报时，当前时间若小于2016年5月1日，则推送消息<br>
	 */
	public boolean saveAndSend(Long comUniCode,Date finDate, boolean rankIndu) throws Exception{
		boolean isMatchSendCriteria = isMatchSendCriteria(comUniCode, finDate);
		if(isMatchSendCriteria){
			save(comUniCode, finDate, finDate == null);
			addInduAndSendMsg(comUniCode, finDate, rankIndu, Constants.TRUE);
		}
		return true;
	}

	/**
	 * 是否满足发送条件
	 * @param finDate
	 * @return
	 * @throws ParseException
	 */
	private boolean isMatchSendCriteria(Long comUniCode, Date finDate) throws ParseException {
		BondCom bondComExt = amaBondDao.findIssMap(comUniCode);
		if(bondComExt == null){
			return false;
		}
		//判断是否为新增财报
		//List<Map<String, Object>> indicators = indicatorDao.findLastFinDateByCompId(bondComExt.getAmsIssId(), null, IndicatorDao.FINANCE, true);
		IndicatorCareTaker careTaker = mongoTemplate.findOne(new Query(Criteria.where("comUniCode").is(comUniCode)), IndicatorCareTaker.class);
		if(careTaker != null){
			IndicatorMemento memento = careTaker.get(SafeUtils.convertDateToString(finDate, SafeUtils.DATE_FORMAT));
			if(memento == null){
				return true;
			}else{
				return false;
			}
		}else{
			return true;
		}
	}
	
	/**
	 * IndicatorChangeForNotice 保存到备忘录
	 * @param state
	 * @param isRebuild
	 * @return
	 * @throws Exception 
	 */
	public boolean save(Long comUniCode,Date finDate, boolean isRebuild) throws Exception{
		long s = System.currentTimeMillis();
		
		if(isRebuild){
			//如果财报日期为空初始化整个主体
			initIndicatorCareTaker(comUniCode);
			return true;
		}
		IndicatorCareTaker careTaker = mongoTemplate.findOne(new Query(Criteria.where("comUniCode").is(comUniCode)), IndicatorCareTaker.class);
		//careTaker为空的话初始化一个
		if(careTaker == null){
			//如果为空初始化整个主体
			initIndicatorCareTaker(comUniCode);
			return true;
		}
		IndicatorMemento memento = getIndicatorMementos(comUniCode, finDate);
		careTaker.add(memento);
		mongoTemplate.save(careTaker);
		long e = System.currentTimeMillis();
		log.info("保存-单个[" + comUniCode + "-"+ finDate + "]指标变查询算时间为：" + (e-s) + "ms");
		return true;
	
	}

	/**
	 * 发送
	 * @param comUniCode
	 * @param finDate
	 * @param onlyRank
	 */
	private void sendMsg(Long comUniCode, Date finDate, boolean onlyRank, Integer finRptFlag) {
		//发送通知mq
		FinSpclindicatorJson json = null;
		try {
			json = checkChange(getIndicatorForNotice(comUniCode, finDate == null ? null : SafeUtils.convertDateToString(finDate, SafeUtils.DATE_FORMAT)), onlyRank, finRptFlag);
			json.setComUniCode(comUniCode);
			json.setFinQuarter(SafeUtils.getQuarter(finDate));
			json.setFinRptFlag(finRptFlag);
		} catch (Exception e) {
			
			log.error(e.getMessage());
		}
		if(json != null){
			mqSenderService.sendIndicatorForNotice(json);
		}
	}
	
	/**
	 * IndicatorChangeForNotice 保存到备忘录
	 * @param states
	 * @return
	 */
	public boolean batchSave(LinkedList<IndicatorMemento> mementos, Long comUniCode){
		try {
			//先清空数据
			mongoTemplate.remove(new Query(Criteria.where("comUniCode").is(comUniCode)), IndicatorCareTaker.class);
			//careTaker为空的话初始化一个
			IndicatorCareTaker careTaker = new IndicatorCareTaker();
			careTaker.setComUniCode(comUniCode);
			careTaker.setMementoList(mementos);
			mongoTemplate.save(careTaker);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 查找备忘录持有者
	 * @param comUniCode
	 * @return
	 */
	public IndicatorCareTaker find(Long comUniCode){
		IndicatorCareTaker careTaker = mongoTemplate.findOne(new Query(Criteria.where("comUniCode").is(comUniCode)), IndicatorCareTaker.class);
		return careTaker;
	}
	
	/**
	 * 获取
	 * @param comUniCode
	 * @return
	 */
	public IndicatorMemento getIndicatorMementos(Long comUniCode, Date finDate) throws Exception{
		BondCom  com = amaBondDao.findIssMap(comUniCode);
		if(com == null){
			return null;
		}
		Map<String,Object> finance = indicatorDao.findByCompIdAndFinDate(com.getAmsIssId(), finDate, null, IndicatorDao.FINANCE);
		//元转万
		//finance = yueToWan(finance);
		Map<String,Object> special = indicatorDao.findByCompIdAndFinDate(com.getAmsIssId(), finDate, null, IndicatorDao.SPECIAL);
		finance.putAll(special);
		return getIndicatorMemento(com, finance, finDate);
	}

	/**
	 * 获取
	 * @param comUniCode
	 * @return
	 */
	public LinkedList<IndicatorMemento> getIndicatorMementos(Long comUniCode){
		BondCom  com = amaBondDao.findIssMap(comUniCode);
		if(com == null){
			return null;
		}
		//财务指标
		List<Map<String,Object>> financeIndicators = indicatorDao.findLastFinDateByCompId(com.getAmsIssId(), null, IndicatorDao.FINANCE, true);
//		List<Map<String,Object>> newList = new ArrayList<>();
//		financeIndicators.forEach(item ->{
//			newList.add(yueToWan(item));
//		});
//		financeIndicators = newList;
		//专项指标
		List<Map<String,Object>> specialIndicators =indicatorDao.findLastFinDateByCompId(com.getAmsIssId(), null, IndicatorDao.SPECIAL, true);
		//指标快照集合
		LinkedList<IndicatorMemento> indicatorMementos = new LinkedList<>();
		for (int i = 0; i < financeIndicators.size(); i++) {
			try {
				//合并
				Map<String,Object> indicatorItem = financeIndicators.get(i);
				if (!specialIndicators.isEmpty() && specialIndicators.size() <= i) {
					indicatorItem.putAll(specialIndicators.get(i));
				}
				Date finDate = (Date) indicatorItem.remove("fin_date");
				IndicatorMemento indicatorMemento = getIndicatorMemento(com, indicatorItem, finDate);
				indicatorMementos.add(indicatorMemento);
			} catch (Exception e) {
				// TODO: handle exception
				log.error("financeIndicators[],specialIndicators[] error,", e);
			}
		}
		return indicatorMementos;
	}
	
	
	/**
	 * 获取用于指标计算指标变动提醒计算IndicatorChangeVo
	 * @param comUniCode
	 * @param finDate
	 * @return
	 */
	public IndicatorChangeVo getIndicatorForNotice(Long comUniCode, String finDate){
		IndicatorCareTaker careTaker = find(comUniCode);
		//所有指标（专项和财务）
		List<String> fields = indicatorDao.getAllFields();
		if(careTaker ==null ||  careTaker.getMementoList() == null ||  careTaker.getMementoList().getFirst() == null){
			return null;
		}
		//获取当前、更新前、和同比数据
		if(finDate == null){
			finDate = careTaker.getMementoList().getFirst().getState();
		}
		//如果为最后最新财报，才做通知
		if(!Objects.equal(finDate, careTaker.getLastFinDate())){
			return new  IndicatorChangeVo();
		}
		IndicatorMemento  memento =  careTaker.get(finDate);
		IndicatorMemento  secondMemento = careTaker.getSecond(finDate);
		IndicatorMemento  yoyMemento = careTaker.getYoy(finDate);
		
		//IndicatorChangeVo用于指标计算指标变动提醒计算
		IndicatorChangeVo change = new  IndicatorChangeVo();
		for (String field : fields) {
			IndicatorSnapshot snapshot = null;
			if(memento != null){
				snapshot = memento.getIndicatorSnapshot(field);
			}
			IndicatorSnapshot secondSnapshot = null;
			if(secondMemento != null){
				secondSnapshot = secondMemento.getIndicatorSnapshot(field);
			}
			IndicatorSnapshot yoySnapshot = null;
			if(yoyMemento != null){
				yoySnapshot = yoyMemento.getIndicatorSnapshot(field);
			}
			if(snapshot != null){
				change.getNewestDataInserDb().put(field, snapshot.getValue());
				change.getNewestDataRankInsert().put(field, snapshot.getQuartile());
			}
			if(secondSnapshot != null){
				change.getNewestDataInDB().put(field, secondSnapshot.getValue());
				change.getNewestDataRankInDb().put(field, secondSnapshot.getQuartile());
			}
			if(yoySnapshot != null){
				change.getYoyData().put(field, yoySnapshot.getValue());
			}
		}
		return change;
	}

	/**
	 * 获取 存储状态 IndicatorMemento
	 * @param com
	 * @param indicatorItem
	 * @param finDate
	 * @return
	 */
	private IndicatorMemento getIndicatorMemento(BondCom com, Map<String, Object> indicatorItem, Date finDate) {
		//行业主体指标
		Map<String,List<BigDecimal>> indicatorsFinance = bondFinanceInfoService.getInduIndicators(com, finDate, null, IndicatorDao.FINANCE);
		Map<String,List<BigDecimal>> indicatorsSpecial = bondFinanceInfoService.getInduIndicators(com, finDate, null, IndicatorDao.SPECIAL);
		indicatorsFinance.putAll(indicatorsSpecial);
		//指标变化快照（同期，自身，行业排名）
		IndicatorMemento indicatorMemento = new IndicatorMemento();
		indicatorMemento.setState(SafeUtils.convertDateToString(finDate, SafeUtils.DATE_FORMAT));
		indicatorMemento.setUpdateTime(new Date());
		//算出各个指标的行业分位
		LinkedList<IndicatorSnapshot> list = new LinkedList<>();
		indicatorItem.forEach((field,value)->{
			if(value != null){
				Integer type = indicatorDao.isPositive(field) ? SafeUtils.QUANTILE_ASC : SafeUtils.QUANTILE_DESC;
				IndicatorSnapshot indicatorChange = new IndicatorSnapshot();
				List<BigDecimal>  listIndu = indicatorsFinance.get(field);
				if(listIndu == null){
					log.info("getIndicatorMemento ->" + com + "field=" + field + ";" + finDate + "无数据");
				}
				Integer quartile = SafeUtils.getQuantileValue(listIndu , (BigDecimal)value, type);
				indicatorChange.setField(field);
				indicatorChange.setQuartile(quartile);
				indicatorChange.setValue((BigDecimal) value);
				list.add(indicatorChange);
			}
		});
		indicatorMemento.setList(list);
		return indicatorMemento;
	}
	/**
	 * 获取mq 指标变动提醒mq body
	 * @param indicatorChangeVo
	 * @return
	 */
	private FinSpclindicatorJson checkChange(IndicatorChangeVo indicatorChangeVo, boolean onlyRank, Integer finRptFlag){
		FinSpclindicatorJson json = new FinSpclindicatorJson();
		if(!onlyRank){
			//同比check
			Map<String,Object> yoy = getYoY(indicatorChangeVo.getNewestDataInserDb(), indicatorChangeVo.getYoyData());
			//自身比较
			Map<String,Object> self = indicatorChangeVo.getNewestDataInserDb();
			json.setSELF(yueToWan(self));
			json.setYOY(yueToWan(yoy));
		}
		//行业排名
		Map<String,Object> rank = indicatorChangeVo.getNewestDataRankInsert();
		json.setRANK(rank);
		return json;
	}
	

	/**
	 * 检查出同期和自身变化的指标
	 * @param data1 基础数据
	 * @param data2 和data1比较的数据
	 * @return
	 */
	private Map<String, Object> checkdSelfAndRank(Map<String,Object> data1, Map<String,Object> data2){
		//空值判断
		if(data1 == null || data2 == null){
			return null;
		}
		Map<String,Object> result = new HashMap<>();
		//每个指标进行比较
		data1.forEach((field, value) -> {
			if(!Objects.equal(value, data2.get(field))){
				result.put(field, value);
			}
		});
		return result;
	}
	
	/**
	 * 检查出同期和自身变化的指标
	 * @param data1 基础数据
	 * @param data2 和data1比较的数据
	 * @return
	 */
	private Map<String, Object> checkYoY(Map<String,Object> data1, Map<String,Object> data2){
		//空值判断
		if(data1 == null || data2 == null){
			return null;
		}
		Map<String,Object> result = new HashMap<>();
		//每个指标进行比较
		data1.forEach((field, value) -> {
			BigDecimal d1 = (BigDecimal) value;
			BigDecimal d2 = (BigDecimal) data2.get(field);
			if(!Objects.equal(d1, d2) && d1 != null && d2 != null){
				try {
					result.put(field, d1.subtract(d2).divide(d2,2,BigDecimal.ROUND_HALF_UP));
				} catch (Exception e) {
					log.info(" / by zero");
				}
			}
		});
		return result;
	}
	
	/**
	 * 获取同比排行数据
	 * @param data1 基础数据
	 * @param data2 和data1比较的数据
	 * @return
	 */
	private Map<String, Object> getYoY(Map<String,Object> data1, Map<String,Object> data2){
		//空值判断
		if(data1 == null || data2 == null){
			return null;
		}
		Map<String,Object> result = new HashMap<>();
		//每个指标进行比较
		data1.forEach((field, value) -> {
			BigDecimal d1 = (BigDecimal) value;
			BigDecimal d2 = (BigDecimal) data2.get(field);
			if(d1 != null && d2 != null){
				try {
					result.put(field, d1.subtract(d2).divide(d2,4,BigDecimal.ROUND_HALF_UP));
				} catch (Exception e) {
					log.info(" / by zero");
				}
			}
		});
		return result;
	}
	
	/**
	 * 元转万元
	 * @param finance
	 * @return
	 */
	public Map<String, Object>  yueToWan(Map<String, Object> finance) {
		if(finance == null){
			return null;
		}
		Map<String, Object> map = new HashMap<>();
		List<String> financeIndicators = indicatorDao.findFinanceIndicators();
		finance.forEach((k,v) -> {
			if(v != null){
				try {
					if(financeIndicators.contains(k)){
						v = new BigDecimal(v.toString()).divide(new BigDecimal(10000),2, BigDecimal.ROUND_HALF_UP);
					}
					map.put(k, v);
				} catch (Exception e) {
					log.warn("神奇的财报数据:" + k + "=" + v  + ";" + e.getMessage());
				}
			}
		});
		return map;
	}
	
	/**
	 * 发送被遗漏的财报主体指标变动
	 * @param finDate 财报日期
	 * @return
	 * @throws Exception
	 */
	public boolean sendMissTakerMq(String finDate) throws Exception{
		log.info("sendMissTakerMq start");
		List<IssIToDSendMqVo> listToSend =  findComToSendMq(finDate);
		log.info("missing taker iss count ->" + listToSend.size());
		int index = 1;
		for (IssIToDSendMqVo issIToDSendMqVo : listToSend) {
			Date date = SafeUtils.convertStringToDate(finDate, SafeUtils.DATE_FORMAT);
			saveAndSend(issIToDSendMqVo.getComUniCode(), date , false);
			log.info("missing taker send num ->" + index++);
		}
		log.info("sendMissTakerMq end");
		return true;
	}
	
	/**
	 * 
	 * @param finDate 弥补发送财报日期
	 * @return
	 */
	public List<IssIToDSendMqVo> findComToSendMq(String finDate){
		log.info("findComToSendMq -> start");
		//已有备忘录信息
		Query query = new Query();
		query.fields().slice("mementoList", 1);
		List<IndicatorCareTaker> all = mongoTemplate.find(query, IndicatorCareTaker.class);
		Iterator<IndicatorCareTaker> iteratorAll =  all.iterator();
		//将本已有的搜集起来
		Set<Long> setCom = new HashSet<>();
		while (iteratorAll.hasNext()) {
			IndicatorCareTaker taker =  iteratorAll.next();
			String lastFinDate = taker.getLastFinDate();
			if(Objects.equal(finDate, lastFinDate)){
				setCom.add(taker.getComUniCode());
			}
		}
		//专项指标最新的财报主体信息
		 List<IssIToDSendMqVo> newsIndicators =  findLastFinDateIComs(finDate);
		 Iterator<IssIToDSendMqVo> iterator = newsIndicators.iterator();
		 while (iterator.hasNext()) {
			 IssIToDSendMqVo mqVo = iterator.next();
			 if(setCom.contains(mqVo.getComUniCode())){
				 iterator.remove();
			 }
		}
		 log.info("findComToSendMq -> end");
		 //过滤已添加到备忘录的数据
		return newsIndicators;
	}
	
	
	@Autowired JdbcTemplate jdbcTemplate;
	public List<IssIToDSendMqVo> findLastFinDateIComs(String finDate){

		String sql = "SELECT sp.fin_date as finDate, e.com_uni_code as comUniCode FROM( " +
			"SELECT Comp_ID,MAX(fin_date) AS fin_date FROM dmdb.dm_analysis_indu GROUP BY Comp_ID " +
			/*"UNION " +
			"SELECT Comp_ID,MAX(fin_date) AS fin_date FROM dmdb.dm_analysis_insu GROUP BY Comp_ID " +
			"UNION " +
			"SELECT Comp_ID,MAX(fin_date) AS fin_date FROM dmdb.dm_analysis_bank GROUP BY Comp_ID " +
			"UNION " +
			"SELECT Comp_ID,MAX(fin_date) AS fin_date FROM dmdb.dm_analysis_secu GROUP BY Comp_ID " +*/
			") sp  INNER JOIN dmdb.t_bond_com_ext e ON sp.Comp_ID = e.ama_com_id  where fin_date = '" + finDate + "'";
		log.info("findLastFinDateIComs sql ->" + sql);
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(IssIToDSendMqVo.class));
		
	}
	
}
