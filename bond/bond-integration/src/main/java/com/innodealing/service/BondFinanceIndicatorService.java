package com.innodealing.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.innodealing.aop.NoLogging;
import com.innodealing.cache.FieldGroupMappingCache;
import com.innodealing.engine.jdbc.bond.IndicatorDao;
import com.innodealing.model.dm.bond.BondCom;
import com.innodealing.model.mongo.dm.bond.finance.FinanceIndicator;
import com.innodealing.model.mongo.dm.bond.finance.IssFinanceIndicators;
import com.innodealing.util.NumberUtils;
import com.innodealing.util.SafeUtils;
import com.innodealing.util.StringUtils;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * 主体财务(资产\利润\现金流)
 * 
 * @author zhaozhenglai
 * @date 2017年2月17日上午10:15:30 Copyright © 2016 DealingMatrix.cn. All Rights
 *       Reserved.
 */
@Service
public class BondFinanceIndicatorService {

    private static Logger logger = LoggerFactory.getLogger(BondFinanceIndicatorService.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private IssAnalysisService analysisService;

    @Autowired
    private FieldGroupMappingCache fieldGroupMappingCache;
    
    @Autowired private IndicatorDao indicatorDao;

    /**
     * 构建其他财务数据
     * 
     * @return
     */
    /**
	 * 构建其他财务数据
	 * @return
	 */
	public boolean buildFinanceIndicators(boolean initAll){
		try {
			String[] types = {"t_bond_manu", "t_bond_bank", "t_bond_secu", "t_bond_insu"}; 
			String tableFuffix = "_fina_sheet";
			Map<Long, BondCom> bondComapping = analysisService.findBondComMapping();
			
			//获取已缓存的数据，进行比较，判断是否需要save
			Query query = new Query();
				query.fields().include("quarters").include("issId").slice("quarters", 1);
			
			List<IssFinanceIndicators> listCache = mongoTemplate.find(query, IssFinanceIndicators.class);
			Map<Long,String> mapQ = new HashMap<>();
			for (IssFinanceIndicators issIndicator : listCache) {
				String q = issIndicator.getQuarters() == null || issIndicator.getQuarters().size()==0 
						? "" : issIndicator.getQuarters().get(0).toString();
				mapQ.put(issIndicator.getIssId(), q);
			}
			for (String type : types) {
				String tableName = type + tableFuffix;
				logger.info(tableName + "innit start!!!");
				long start = System.currentTimeMillis();
				List<Map<String, Object>> fieldGroupMap = findFieldGroup(tableName);
				build(fieldGroupMap, bondComapping, tableName, mapQ, null, initAll);
				logger.info(tableName + "innit end!!!");
				long end = System.currentTimeMillis();
				logger.info("elapsed time" + (end -start) + "ms");
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	
	public boolean  buildFinanceIndicator(Long compId, boolean initAll){
		Long induId = indicatorDao.findComInduId(compId);
		if(induId != null){
			String tableName = indicatorDao.findTableNameByCompId(compId, IndicatorDao.FINANCE);
			Map<Long, BondCom> bondComapping = analysisService.findBondComMapping();
			build(findFieldGroup(tableName), bondComapping, tableName, new HashMap<>(), compId, initAll);
		}
		return true;
	}
	
	
	public void build(List<Map<String, Object>> mapList, Map<Long, BondCom> bondComapping, String tableName, Map<Long,String> mapQ, Long compId, boolean initAll){
		logger.info("###### 开始处理：" + tableName );
		//设置group_concat_max_len
		String setGroupConcatSql = "SET SESSION group_concat_max_len=102400";
		jdbcTemplate.execute(setGroupConcatSql);
		
		//生成查询sql
		StringBuffer sql = new StringBuffer( "SELECT COMP_ID");
		for (Map<String, Object> map : mapList) {
			Object field = map.get("column_name");
			sql.append(", GROUP_CONCAT( FIN_DATE,'=',IFNULL(" + field + ",'null') ORDER BY FIN_DATE DESC ) AS " + field);
		}
		String whereCompId = compId == null ? "" : " and T.Comp_ID = " + compId;
		String condition = String.format(" FROM dmdb.%1$s T "
				+ " where EXISTS ( select 1 from dmdb.t_bond_com_ext where T.Comp_ID = ama_com_id ) "
				+  whereCompId
				+ " GROUP BY COMP_ID ", tableName);
		sql.append(condition);
		logger.info(sql.toString());
		
		List<Map<String, Object>> indicatorsList = jdbcTemplate.queryForList(sql.toString());
		
		ExecutorService pool = Executors.newFixedThreadPool(6);
		for (final Map<String, Object> map : indicatorsList) {
			pool.execute(
				new Runnable() {
					@Override
					public void run() {
						buildFinanceIndicators(mapList, bondComapping, new ArrayList<>(), map, mapQ, initAll);
					}
				}
			);
		}
		pool.shutdown();
		try {
			pool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
			logger.error("等待任务完成中发生异常 ", e);
			e.printStackTrace();
		}
		logger.info("###### 结束处理：" + tableName );
	}

	/**
	 * 保存指标
	 * @param mapList
	 * @param bondComapping
	 * @param financeIndicatorList
	 * @param map
	 */
	private  List<IssFinanceIndicators> buildFinanceIndicators(List<Map<String, Object>> mapList, Map<Long, BondCom> bondComapping,
			List<FinanceIndicator> financeIndicatorList, Map<String, Object> map, Map<Long, String> mapQ, boolean initAll) {
		List<IssFinanceIndicators> result = new ArrayList<>();
		Long asIssId = map.get("COMP_ID") == null ? null : SafeUtils.getLong(map.get("COMP_ID"));//安硕 主体id
		BondCom bondCom = bondComapping.get(asIssId);
		if(bondCom == null) return null;
		
		//是否有变化，没有跳过
		if(!initAll){
			if(mapList != null && mapList.size() >0){
				String field = mapList.get(0).get("column_name") == null ? null : mapList.get(0).get("column_name").toString();
				Object indicatorValue = map.get(field);
				List<String> qs = getFinDates(indicatorValue, bondCom.getAmsIssId());
				String qSaved = mapQ.get(bondCom.getIssId());
				if(qs != null && qs.size()>0 &&  qSaved != null ){
					if(qSaved.equals(qs.get(0))){
						return null;
					}
				}
			}
		}
		
		//获取指标信息和分类信息
		mapList.stream().forEach(fieldGroup -> {
			//获取指标信息和分类信息
			String field = fieldGroup.get("column_name") == null ? null :fieldGroup.get("column_name").toString();
			String fieldName = fieldGroup.get("field_name") == null ? null : fieldGroup.get("field_name").toString();
			String groupName = fieldGroup.get("group_name") == null ? null : fieldGroup.get("group_name").toString();
			Integer type = fieldGroup.get("type") == null ? null : Integer.valueOf(fieldGroup.get("type").toString());
			String groupNameParent = fieldGroup.get("group_name_parent") == null ? null : fieldGroup.get("group_name_parent").toString();
			//String tableName = fieldGroup.get("table_name") == null ? null : fieldGroup.get("table_name").toString();
			Object indicatorValue = map.get(field);
			
			//得到指標
			FinanceIndicator financeIndicator = new FinanceIndicator();
			financeIndicator.setCategory(groupName);
			financeIndicator.setCategoryParent(groupNameParent);
			financeIndicator.setField(field);
			financeIndicator.setFieldName(fieldName);
			financeIndicator.setType(type);
			financeIndicator.setIndicators(getIndicators(indicatorValue/*, bondCom.getIssId(), field*/));
			//financeIndicator.setLastFinDate(getLastFinDate(indicatorValue, bondCom.getAmsIssId()));
			//logger.info("bondCom.getIssId()->" + bondCom.getIssId());
			
			financeIndicatorList.add(financeIndicator);
			//result.add(issFinanceIndicators);
		});
		//保存主体财务指标到mongodb
		Object indicatorValue = null;
		indicatorValue = map.get(mapList.get(0).get("column_name"));
		IssFinanceIndicators issFinanceIndicators = new IssFinanceIndicators();
		issFinanceIndicators.setIndicators(financeIndicatorList);
		issFinanceIndicators.setIssId(bondCom.getIssId());
		issFinanceIndicators.setInduId(bondCom.getInduId());
		issFinanceIndicators.setInduIdSw(bondCom.getInduIdSw());
		issFinanceIndicators.setIssId(bondCom.getIssId());
		issFinanceIndicators.setIssName(bondCom.getIssName());
		issFinanceIndicators.setQuarters(getFinDates(indicatorValue, bondCom.getAmsIssId()));
		saveIssFinanceIndicators(issFinanceIndicators);
		result.add(issFinanceIndicators);
		return result;
	}

    private void saveIssFinanceIndicators(IssFinanceIndicators issFinanceIndicators) {

        mongoTemplate.save(issFinanceIndicators);

        if (issFinanceIndicators.getQuarters().size() < 1 || issFinanceIndicators.getIssId() == null)
            return;

        List<DBObject> bondDetailList = analysisService.getBondDetailList(issFinanceIndicators.getIssId());

        if (bondDetailList == null || bondDetailList.size() < 1)
            return;

        List<String> quarters = issFinanceIndicators.getQuarters();

        List<FinanceIndicator> indicators = issFinanceIndicators.getIndicators();
        if (indicators == null)
            return;

        // 创建线程池
        ExecutorService CachedThreadPool = Executors.newCachedThreadPool();
        List<Callable<String>> tasks = new ArrayList<Callable<String>>();

        for (DBObject dbObject : bondDetailList) {

            List<DBObject> quarterList = new ArrayList<DBObject>();

            Callable<String> c = new Callable<String>() {
                @Override
                public String call() throws Exception {

                    @SuppressWarnings("unchecked")
                    Map<String, DBObject> map = dbObject.get("quarters") != null ? quarterList((List<DBObject>) dbObject.get("quarters")) : null;

                    DBObject quarter = null;
                    for (int i = 0; i < quarters.size(); i++) {

                        if (!analysisService.isValid(quarters.get(i)))
                            continue;

                        if (map != null && map.get(quarters.get(i)) != null && map.get(quarters.get(i)).get("quarter").toString().equals(quarters.get(i))) {
                            quarter = map.get(quarters.get(i));
                        } else {
                            quarter = new BasicDBObject();
                        }
                        for (int j = 0; j < indicators.size(); j++) {
                            String field = indicators.get(j).getField();
                            if (!StringUtils.isEmpty(field) && indicators.get(j).getIndicators() != null && indicators.get(j).getField().indexOf("_") == -1
                                    && fieldGroupMappingCache.BOND_FINELD_GROUP_MAPPING().get("quarters." + field) != null
                                    && indicators.get(j).getIndicators().get(i) != null) {
                                quarter.put(field, indicators.get(j).getIndicators().get(i).doubleValue());
                            }
                        }
                        if(quarter.keySet().size()>0){
                            quarter.put("quarter", quarters.get(i));
                            quarterList.add(quarter);
                        }
                    }

                    dbObject.put("quarters", quarterList);
                    mongoTemplate.save(dbObject, "bond_detail_info");
                    return "success";
                }
            };
            tasks.add(c);
        }
        try {
            // 执行任务
            CachedThreadPool.invokeAll(tasks);
        } catch (Exception e) {
            e.printStackTrace();
        }

        CachedThreadPool.shutdown();
        try {
            CachedThreadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            logger.error("等待任务完成中发生异常 ", e);
            e.printStackTrace();
        }
    }

    private Map<String, DBObject> quarterList(List<DBObject> quarterList) {

        Map<String, DBObject> map = new HashMap<String, DBObject>();

        for (DBObject dbObject : quarterList) {
            map.put(dbObject.get("quarter").toString(), dbObject);
        }

        return map;

    }

    public static void main(String[] args) {
        System.out.println(4544 / 100);
        System.out.println(4544 % 100);

        List<Integer> list = new ArrayList<>();

        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);
        list.add(7);
        list.add(8);
        // for (Integer integer : list.subList(0, 2)) {
        // System.out.println(integer);
        // }
        System.out.println(NumberUtils.isNumeric("34..6"));
    }

    /**
     * 获取最后财报时间
     */
    @NoLogging
    String getLastFinDate(Object indicatorValue, Long amaId) {
        List<String> finDates = getFinDates(indicatorValue, amaId);
        return finDates == null || finDates.size() == 0 ? null : finDates.get(0);
    }

    /**
     * 获取指标
     * 
     * @param indicatorValue
     * @return
     */
    @NoLogging
    public List<BigDecimal> getIndicators(
            Object indicatorValue/* , Long issId, String field */) {
        if (indicatorValue == null) {
            return null;
        }
        List<BigDecimal> list = new ArrayList<>();
        String[] datesAndIndicators = indicatorValue.toString().split(",");
        for (String data : datesAndIndicators) {
            if (data == null)
                continue;
            String[] dateAndIndicator = data.split("=");
            if (dateAndIndicator.length == 2) {
                String val = dateAndIndicator[1];

                BigDecimal indiValue = null;
                try {
                    indiValue = val == null || !NumberUtils.isNumeric(val) ? null : new BigDecimal(val);
                } catch (Exception e) {
                    logger.error("the magic of the bug " + e.getMessage() + "[" + val + "]");
                }
                list.add(indiValue);
            } else if (dateAndIndicator.length == 1) {
                list.add(null);
            } else {
                continue;
            }
        }
        return list;
    }

    /**
     * 获取指标
     * 
     * @param indicatorValue
     * @return
     */
    @NoLogging
    public List<String> getFinDates(Object indicatorValue, Long amaId) {
        if (indicatorValue == null)
            return null;
        List<String> list = new ArrayList<>();
        String[] datesAndIndicators = indicatorValue.toString().split(",");
        for (String data : datesAndIndicators) {
            String[] dateAndIndicator = data.split("=");
            if (dateAndIndicator.length == 2) {
                list.add(SafeUtils.getQuarter(dateAndIndicator[0], 1)[0]);
            } else {
                continue;
            }
        }
        return list;
    }

    /**
     * 查找字段分组
     * 
     * @return
     */
    @NoLogging
    private List<Map<String, Object>> findFieldGroup(String tableName) {
    	tableName = tableName.replaceAll("t_bond_", "");
        String sql = "SELECT * FROM dmdb.field_group_mapping where table_name = ?";
        return jdbcTemplate.queryForList(sql, tableName);
    }
}
