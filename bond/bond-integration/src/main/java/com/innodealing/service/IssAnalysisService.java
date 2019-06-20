package com.innodealing.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.innodealing.annotation.IndicatorType;
import com.innodealing.aop.NoLogging;
import com.innodealing.cache.FieldGroupMappingCache;
import com.innodealing.consts.Constants;
import com.innodealing.engine.jdbc.bond.IndicatorDao;
import com.innodealing.model.BuildResult;
import com.innodealing.model.dm.bond.BondCom;
import com.innodealing.model.mongo.dm.BondComInfoDoc;
import com.innodealing.model.mongo.dm.BondDetailDoc;
import com.innodealing.model.mongo.dm.BondFieldGroupMappingDoc;
import com.innodealing.model.mongo.dm.bond.detail.analyse.IssFinDatesDoc;
import com.innodealing.model.mongo.dm.bond.detail.analyse.IssIndicatorFieldGroupMapping;
import com.innodealing.model.mongo.dm.bond.detail.analyse.IssIndicatorsDoc;
import com.innodealing.model.mongo.dm.bond.detail.analyse.IssInduMapDoc;
import com.innodealing.model.mongo.dm.bond.detail.analyse.IssKeyIndicatorDoc;
import com.innodealing.model.mongo.dm.bond.detail.analyse.IssProvinceMapDoc;
import com.innodealing.model.mongo.dm.bond.finance.FinanceIndicator;
import com.innodealing.util.SafeUtils;
import com.innodealing.util.StringUtils;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * 债券违约概率分析
 * 
 * @author zhaozhenglai
 * @since 2016年9月18日 下午7:42:26 Copyright © 2016 DealingMatrix.cn. All Rights
 *        Reserved.
 */
@Service
public class IssAnalysisService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private RatingService ratingService;

    @Autowired
    private InduService induService;

    @Autowired
    private BondFinanceIndicatorService bondFinanceIndicatorService;

    @Autowired
    private IssIndicatorFieldGroupService issIndicatorFieldGroupService;
    
    @Autowired IndicatorDao indicatorDao;

    @Autowired
    private FieldGroupMappingCache fieldGroupMappingCache;

    @Autowired
    MongoOperations mongoOperations;

    private static Logger logger = LoggerFactory.getLogger(IssAnalysisService.class);

    /**
     * 构建DM、中诚信、安硕映射信息
     * 
     * @return
     */
    public boolean buildIndicatorFieldMapping() {
        boolean result = true;
        try {
            String sql = "SELECT column_name AS columnName,table_name AS tableName,field_name AS fieldName,group_name AS groupName,field_type as fieldType,percent FROM amaresun.dm_field_group_mapping ";
            List<IssIndicatorFieldGroupMapping> list = jdbcTemplate.query(sql.toString(),
                    new BeanPropertyRowMapper<IssIndicatorFieldGroupMapping>(IssIndicatorFieldGroupMapping.class));
            mongoTemplate.remove(new Query(), IssIndicatorFieldGroupMapping.class);
            mongoTemplate.insert(list, IssIndicatorFieldGroupMapping.class);
        } catch (DataAccessException e) {
            result = false;
            e.printStackTrace();
        }
        return result;
    }

    public boolean buildAllIssIndicators() {
        boolean result = true;
        try {
            issInduMap.clear();
            issProvinceMap.clear();
            // 安硕、中诚信主体映射关系map
            Map<Long, BondCom> comMapping = findBondComMapping();
            String[] orgTypes = { "bank", "indu", "secu", "insu" };
            // ExecutorService executorService =
            // Executors.newFixedThreadPool(4);
            List<Long> activityIssIds = induService.getActiveIss();
            for (String orgType : orgTypes) {
                buildIssIndicators(orgType, comMapping, activityIssIds,null);
            }
            // 行业和地区映射关系构建
            buildInduProvinceMap();
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        }
        return result;
    }

    public boolean buildIssIndicator(Long compId){
    	String orgType = indicatorDao.findIssuerModelByCompId(compId);
    	//安硕、中诚信主体映射关系map
        Map<Long,BondCom> comMapping = findBondComMapping();
        //有效的主体
        List<Long> activityIssIds = induService.getActiveIss();
    	buildIssIndicators(orgType, comMapping, activityIssIds, compId);
    	return true;
    }
    
    private static Map<Long, Long> provinceIssMapping = null;
    private static Map<Long, IssInduMapDoc> issInduMap = new HashMap<>();
    private static Map<Long, IssProvinceMapDoc> issProvinceMap = new HashMap<>();

    private void buildIssIndicators(String orgType , Map<Long,BondCom> comMapping, List<Long> activityIssIds, Long compId){
        
        if(provinceIssMapping == null){
            provinceIssMapping = getProvinceIssMapping();
        }
        String tname = "dm_analysis_" + orgType;
        //String sql = "SELECT * FROM  /*amaresun*/ dmdb.dm_field_group_mapping WHERE table_name = '"+tname+"'";
        
        String sql = "SELECT i.column_name,i.table_name,i.field_name,i.percent,i.field_type,g.group_name FROM dmdb.t_bond_indicator_info i " +
        		"LEFT JOIN dmdb.t_bond_indicator_relevance_group gm ON i.id = gm.indicator_id  " +
        		"LEFT JOIN dmdb.t_bond_indicator_group g ON gm.group_id = g.id where i.table_name = '"+tname+"' group by i.field_name";
        
        String tableName = " /*amaresun*/ dmdb." + tname;
        String tableNameAnnual = tableName + "_annual";
        List<Map<String,Object>> indicatorList = jdbcTemplate.queryForList(sql);
        
        String baseSql = " select  create_time,one_yr_pd,Comp_ID,Comp_Name,INDUSTRY_ID,INDUSTRY_ID_NM,fin_date";
        StringBuffer subSql = new StringBuffer(" ");
        subSql.append(baseSql);
        //其他指标
        List<String> indicatorColumns = new ArrayList<>();
        
        //指标mapping
        Map<String,Map<String,Object>> indicatormapping = new HashMap<>();
        for (Map<String, Object> indicator : indicatorList) {
            String column = indicator.get("column_name") + "";
            if("AveInco_Urban".equals(column) || "Gov_Level_NM".equals(column) || "comp_type".equals(column) || "OPERATING_PROFIT_MRG".equals(column) || "TNGBLASST_TURNVR".equals(column)){
                indicatorColumns.add(column);
                continue;
            }
            indicatormapping.put(column, indicator);
            subSql.append(",")
                  .append(column);
        }
        subSql.append(" from ")
              .append(tableName).append(" where EXISTS ( select 1 from dmdb.t_bond_com_ext where ").append(tableName).append(".Comp_ID = ama_com_id) ");
//              .append(" UNION ")
//              .append(baseSql);
//        for (Map<String, Object> indicator : indicatorList) {
//            String column = indicator.get("column_name") + "";
//            if("AveInco_Urban".equals(column) || "Gov_Level_NM".equals(column) || "comp_type".equals(column) || "OPERATING_PROFIT_MRG".equals(column) || "TNGBLASST_TURNVR".equals(column)){
//                continue;
//            }
//            subSql.append(",")
//                  .append(column);
//        }
//        subSql.append(" from ")
//              .append(tableNameAnnual)
//              .append(" where EXISTS ( select 1 from dmdb.t_bond_com_ext where ").append(tableNameAnnual).append(".Comp_ID = ama_com_id) ");   
        
        StringBuffer sqlSb = new StringBuffer("select ");
        sqlSb.append("create_time,one_yr_pd,Comp_ID,Comp_Name,INDUSTRY_ID,INDUSTRY_ID_NM,GROUP_CONCAT(fin_date ORDER BY fin_date DESC) AS fin_date ");
        
        for (Map<String, Object> indicator : indicatorList) {
        	//IFNULL(ICF301,"null")
            String column =  indicator.get("column_name") + "";
        	String columnIf = "IFNULL(" + indicator.get("column_name") + ",'null')";
            if("AveInco_Urban".equals(column) || "Gov_Level_NM".equals(column) || "comp_type".equals(column) || "OPERATING_PROFIT_MRG".equals(column) || "TNGBLASST_TURNVR".equals(column)){
                continue;
            }
            sqlSb.append(",")
                 .append("GROUP_CONCAT( fin_date,'='," + columnIf +" ORDER BY fin_date DESC) AS " + column);
            
        }
        sqlSb.append(" from ")
             .append( "(" + subSql + ") as tem ")
             .append(compId == null ? "" : " where Comp_ID = " + compId )
             .append(" GROUP BY  Comp_ID ");
        
        
        logger.info("sql---->" + sqlSb.toString());
        List<Map<String,Object>> listIndicators = jdbcTemplate.queryForList(sqlSb.toString());
       
        //指标类别
        //Map<String,IssIndicatorFieldGroupMapping> mapping = issIndicatorFieldGroupRepository.findIndicatorFieldGroupMapping();
        String[] noNeedFields = {"create_time","one_yr_pd","Comp_ID","Comp_Name","INDUSTRY_ID","INDUSTRY_ID_NM","fin_date"};
        //查找其他指标
//        Map<Long,Object> issTypeMap = new HashMap<>();
//        for (String other : indicatorColumns) {
//            String othersSql = "SELECT a.COM_UNI_CODE AS issId,b.PAR_NAME AS issType FROM bond_ccxe.d_pub_com_info_2  a " +
//            "LEFT JOIN bond_ccxe.pub_par b ON a.TYPE_BIG_PAR = b.PAR_CODE GROUP BY a.COM_UNI_CODE";
//            if("comp_type".equals(other)){
//                List<Map<String,Object>> issTypes = jdbcTemplate.queryForList(othersSql);
//                for (Map<String, Object> map : issTypes) {
//                    issTypeMap.put(SafeUtils.getLong(map.get("issId")), map);
//                }
//            }
//        }
        //临时存放指标
        List<IssIndicatorsDoc> toSaveList = new ArrayList<>();
        for (Map<String, Object> map : listIndicators) {
        	List<String> quarters = null;
            //安硕主体id
            Long comId = SafeUtils.getLong(map.get("Comp_ID"));
            
            //安硕、中诚信主体映射关系
            BondCom iss = comMapping.get(comId);
            //主体id
            Long issId = iss != null ? iss.getIssId() : null;
            if(issId == null)
            	continue;
            //是否有流通的债券
            Integer isActive = activityIssIds.contains(issId) ? Constants.TRUE : Constants.FALSE;
            //主体行业
            Long induId = iss == null ? null :iss.getInduId();
            //主体行业
            Long induIdSw = iss == null ? null :iss.getInduIdSw();
            //日期
            Object date =  map.get("fin_date");
            //发行人名称
            String issName = iss.getIssName();
            List<FinanceIndicator> issKeyIndicators = new ArrayList<>();
            for (Entry<String, Object> entry : map.entrySet()) {
                //指标列
                String field = entry.getKey();
                if(Arrays.asList(noNeedFields).contains(field)){
                    continue;
                }
                Integer fieldType = SafeUtils.getInteger(indicatormapping.get(entry.getKey()).get("field_type"));
                //IssIndicatorFieldGroupMapping  issMapping = mapping.get(entry.getKey());
                
                //指标列名称
                String fieldName = SafeUtils.getString(indicatormapping.get(entry.getKey()).get("field_name"));
                //指标分组名
                String category = SafeUtils.getString(indicatormapping.get(entry.getKey()).get("group_name"));
                
                
                
                //最后发布日期
                String lastFinDate = date == null ? null : date.toString().split(",")[0];
                //百分比类型
                int  percent = SafeUtils.getInteger(indicatormapping.get(entry.getKey()).get("percent"));
                //是否为负向指标
                int negative = 0;
                if(fieldType == 2 || fieldType == 4 || fieldType == 6){
                    negative = 1;
                }
                //指标的值|率类别
                int type =IndicatorType.VALUE.getType();
                if(fieldType < 3){
                    type = IndicatorType.RATE.getType();
                }
                Object indicatorValue = entry.getValue();
                if(issId == 200025464L && field.equals("insu_ratio1")){
                	System.out.println(2);
                }
                List<BigDecimal> indicators = bondFinanceIndicatorService.getIndicators(indicatorValue);
                quarters = quarters == null ? bondFinanceIndicatorService.getFinDates(indicatorValue, issId) : quarters;
                IssIndicatorsDoc indicator = new IssIndicatorsDoc(issId, field, fieldName, category, induId, lastFinDate, negative, orgType, type, percent, indicators, quarters, induIdSw, issName);
                Long provinceId = provinceIssMapping.get(issId);
                indicator.setProvinceId(provinceId);
                FinanceIndicator indicatorKey = new FinanceIndicator();
                indicatorKey.setField(field);
                indicatorKey.setFieldName(fieldName);
                indicatorKey.setCategory(category);
                indicatorKey.setIndicators(indicators);
                indicatorKey.setType(type);
                indicatorKey.setPercent(percent);
                indicatorKey.setNegative(negative);
                issKeyIndicators.add(indicatorKey);
                //toSaveList.add(indicator);
                initInduProvinceMap(indicator);//初始化行业地区映射关系
            }
            //保存一个重点指标到mongodb
            Long provinceId = provinceIssMapping.get(issId);
            IssKeyIndicatorDoc keyDoc = new IssKeyIndicatorDoc();
            keyDoc.setIndicators(issKeyIndicators);
            keyDoc.setInduId(induId);
            keyDoc.setInduIdSw(induIdSw);
            keyDoc.setIssId(issId);
            keyDoc.setIssName(issName);
            keyDoc.setProvinceId(provinceId);
            keyDoc.setQuarters(quarters);
            keyDoc.setOrgType(orgType);
            keyDoc.setIsActive(isActive);
            cacheSingleIssIndicator(keyDoc);
            
           // IssIndicatorsDoc indicator = new IssIndicatorsDoc(issId, field, fieldName, category, induId, lastFinDate, 1, orgType, type, indicators, quarters);
            
        }
//        mongoTemplate.remove(new Query(Criteria.where("orgType").is(orgType)), IssIndicatorsDoc.class);
//        mongoTemplate.insert(toSaveList,IssIndicatorsDoc.class);
        //统一到 新的主体
        Map<Long,IssIndicatorsDoc> mapIssIndicatorsDoc = new HashMap<>();
        for (IssIndicatorsDoc issIndicatorsDoc : toSaveList) {
        	mapIssIndicatorsDoc.put(issIndicatorsDoc.getIssId(), issIndicatorsDoc);
		}
        
    }

    /**
     * 构建业地区映射关系
     */
    public void buildInduProvinceMap() {
        // 持久化行业映射
        if (issInduMap.size() != 0) {
            issInduMap.forEach((key, value) -> mongoTemplate.save(value));
        }

        // 持久化地区映射
        if (issProvinceMap.size() != 0) {
            issProvinceMap.forEach((key, value) -> mongoTemplate.save(value));
        }
    }

    /**
     * 初始化行业地区映射关系
     * 
     * @param indicator
     */
    private void initInduProvinceMap(IssIndicatorsDoc indicator) {
        if (indicator == null)
            return;
        // 行业map
        Long induId = indicator.getInduId();// 行业id
        if (induId == null)
            return;
        if (issInduMap.get(induId) == null) {
            Set<Long> issIds = new HashSet<>();
            issIds.add(indicator.getIssId());
            issInduMap.put(induId, new IssInduMapDoc(induId, issIds));
        } else {
            issInduMap.get(induId).getIssIds().add(indicator.getIssId());
        }

        // 地区map
        Long provinceId = indicator.getProvinceId();// 省份id
        if (provinceId == null)
            return;
        if (issProvinceMap.get(provinceId) == null) {
            Set<Long> issIds = new HashSet<>();
            issIds.add(indicator.getIssId());
            issProvinceMap.put(provinceId, new IssProvinceMapDoc(provinceId, issIds));
        } else {
            issProvinceMap.get(provinceId).getIssIds().add(indicator.getIssId());
        }

    }

    /**
     * 保存
     * 
     * @param issId
     * @param indicators
     */
    private void cacheSingleIssIndicator(IssKeyIndicatorDoc issKeyIndicatorDoc) {
        mongoTemplate.save(issKeyIndicatorDoc);

        if (issKeyIndicatorDoc.getQuarters().size() < 1 || issKeyIndicatorDoc.getIssId() == null)
            return;

        List<DBObject> bondDetailList = getBondDetailList(issKeyIndicatorDoc.getIssId());

        if (bondDetailList == null || bondDetailList.size() < 1)
            return;

        List<String> quarters = issKeyIndicatorDoc.getQuarters();

        List<FinanceIndicator> indicators = issKeyIndicatorDoc.getIndicators();
        if (indicators == null)
            return;

        // 创建线程池
        ExecutorService CachedThreadPool = Executors.newCachedThreadPool();
        List<Callable<String>> tasks = new ArrayList<Callable<String>>();

        for (DBObject dbObject : bondDetailList) {

            Callable<String> c = new Callable<String>() {
                @Override
                public String call() throws Exception {
                    dbObject.put("orgType", issKeyIndicatorDoc.getOrgType());
                    List<DBObject> quarterList = new ArrayList<DBObject>();
                    DBObject quarter = null;
                    for (int i = 0; i < quarters.size(); i++) {

                        if (!isValid(quarters.get(i)))
                            continue;

                        quarter = new BasicDBObject();
                        for (int j = 0; j < indicators.size(); j++) {
                            String field = indicators.get(j).getField();
                            if (!StringUtils.isEmpty(field) && indicators.get(j).getIndicators() != null
                                    && fieldGroupMappingCache.BOND_FINELD_GROUP_MAPPING().get("quarters." + field) != null
                                    && indicators.get(j).getIndicators().get(i) != null) {
                            	Double d = null;
                            	if(indicators.get(j).getPercent()==1){
                            		d = indicators.get(j).getIndicators().get(i).doubleValue()*100;
                            	}else{
                            		d = indicators.get(j).getIndicators().get(i).doubleValue();
                            	}
                            	quarter.put(field, d);
                            }
                        }
                        if (quarter.keySet().size() > 0) {
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

    /**
     * 
     * isValid:(财报是否是三年内数据) TODO(这里描述这个方法适用条件 – 可选) TODO(这里描述这个方法的执行流程 – 可选)
     * TODO(这里描述这个方法的使用方法 – 可选) TODO(这里描述这个方法的注意事项 – 可选)
     *
     * @param @param
     *            quarter
     * @param @return
     *            设定文件
     * @return Boolean DOM对象
     * @throws @since
     *             CodingExample Ver 1.1
     */
    public Boolean isValid(String quarter) {
        if (StringUtils.isEmpty(quarter)) {
            return false;
        }

        Integer oldYear = Integer.parseInt(quarter.substring(0, quarter.indexOf("/")));
        Calendar ca = Calendar.getInstance();
        Integer newYear = ca.get(Calendar.YEAR);

        return newYear - oldYear <= 3 ? true : false;
    }

    /**
     * 查询该发行人所有流通中的债劵
     * 
     * @param comUniCode
     * @return
     */
    public List<BondDetailDoc> getBondDetailListByComUniCode(Long comUniCode) {
        Query query = new Query();
        Criteria criteria = new Criteria();
        // 信用债(存单除外)
        List<Integer> dmBondTypeList = new ArrayList<Integer>() {
            private static final long serialVersionUID = 1L;
            {
                add(7);
                add(8);
                add(9);
                add(11);
                add(12);
                add(13);
            }
        };
        criteria.andOperator(Criteria.where("comUniCode").is(comUniCode), Criteria.where("dmBondType").in(dmBondTypeList));
        query.addCriteria(criteria);
        List<BondDetailDoc> bondDetailList = mongoTemplate.find(query, BondDetailDoc.class);
        return bondDetailList;
    }

    /**
     * 查询该发行人所有流通中的债劵
     * 
     * @param comUniCode
     * @return
     */
    public List<DBObject> getBondDetailList(Long comUniCode) {
        Query query = new Query();
        Criteria criteria = new Criteria();
        List<Integer> dmBondTypeList = new ArrayList<Integer>() {
            private static final long serialVersionUID = 1L;
            {
                add(7);
                add(8);
                add(9);
                add(11);
                add(12);
                add(13);
            }
        };
        criteria.andOperator(Criteria.where("comUniCode").is(comUniCode), Criteria.where("dmBondType").in(dmBondTypeList));
        query.addCriteria(criteria);
        List<DBObject> list = mongoTemplate.find(query, DBObject.class, "bond_detail_info");
        return list;
    }

    private Map<Long, Long> getProvinceIssMapping() {
        Query query = new Query();
        query.fields().include("comUniCode").include("areaUniCode1");
        List<BondComInfoDoc> list = mongoTemplate.find(query, BondComInfoDoc.class);
        Map<Long, Long> mapping = new HashMap<>();
        for (BondComInfoDoc comInfo : list) {
            mapping.put(comInfo.getComUniCode(), comInfo.getAreaUniCode1());
        }
        return mapping;
    }

    /**
     * 主体映射
     * 
     * @return
     */
    @NoLogging
    public Map<Long, BondCom> findBondComMapping() {
        String sql = "SELECT com_uni_code AS issId,ama_com_id AS amsIssId,indu_uni_code AS induId,indu_uni_code_sw AS induIdSw, com_chi_name AS issName FROM t_bond_com_ext WHERE com_uni_code >0 ";
        List<BondCom> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<BondCom>(BondCom.class));
        Map<Long, BondCom> map = new HashMap<>();
        for (BondCom bondCom : list) {
            map.put(bondCom.getAmsIssId(), bondCom);
        }
        return map;
    }

    
    /**
     * 构建单个主体的财务指标和专项指标
     * @param compId
     * @return
     */
    public List<BuildResult> buildOne(Long compId){
 	   List<BuildResult> result = new ArrayList<>();
 	   long start1 = System.currentTimeMillis();
 	   boolean  result1 = bondFinanceIndicatorService.buildFinanceIndicator(compId,true);
 	   long end1 = System.currentTimeMillis();
        String name1 ="构建财务指标";
        result.add(new BuildResult(name1, result1, (end1 - start1) +"ms") );	   
 	   
 	   long start2 = System.currentTimeMillis();
 	   boolean  result2 = buildIssIndicator(compId);
 	   long end2 = System.currentTimeMillis();
        String name2 ="构建专项指标";
        result.add(new BuildResult(name2, result2, (end2 - start2) +"ms") );	 
        return result;
    }
   
    
    /**
     * 构建财报日期
     */
    public boolean buildIssFinanceDate() {
        boolean result = true;
        try {
            StringBuffer sb = new StringBuffer();
            sb.append(" SELECT ").append(" Comp_ID as issId,GROUP_CONCAT(fin_date ORDER BY fin_date DESC) as finDate").append(" FROM ( ")
                    .append("            SELECT Comp_ID,fin_date FROM amaresun.dm_analysis_bank ").append("            UNION  ")
                   // .append("            SELECT Comp_ID,fin_date FROM amaresun.dm_analysis_bank_annual ").append("            UNION ")
                    .append("            SELECT Comp_ID,fin_date FROM amaresun.dm_analysis_indu ").append("            UNION  ")
                   // .append("            SELECT Comp_ID,fin_date FROM amaresun.dm_analysis_indu_annual ").append("            UNION ")
                    .append("            SELECT Comp_ID,fin_date FROM amaresun.dm_analysis_insu ").append("            UNION ")
                    //.append("            SELECT Comp_ID,fin_date FROM amaresun.dm_analysis_insu_annual ").append("            UNION ")
                    .append("            SELECT Comp_ID,fin_date FROM amaresun.dm_analysis_secu ").append("  AS analysis ").append("        GROUP BY Comp_ID ");
                   // .append("            SELECT Comp_ID,fin_date FROM amaresun.dm_analysis_secu_annual) AS analysis ").append("        GROUP BY Comp_ID ");
            List<Map<String, Object>> listDate = jdbcTemplate.queryForList(sb.toString());
            List<IssFinDatesDoc> listDoc = new ArrayList<>();
            // 安硕、中诚信主体映射关系map
            Map<Long, BondCom> comMapping = findBondComMapping();
            for (Map<String, Object> map : listDate) {
                IssFinDatesDoc doc = new IssFinDatesDoc();
                BondCom mapping = comMapping.get(SafeUtils.getLong(map.get("issId")));
                if (mapping != null) {
                    doc.setIssId(mapping.getIssId());
                }
                Object dates = map.get("finDate");
                if (dates != null) {
                    String[] dateArr = dates.toString().split(",");
                    Set<String> setDate = new LinkedHashSet<>();
                    for (String string : dateArr) {
                        setDate.add(string);
                    }
                    doc.setFinDates(setDate);
                }
                listDoc.add(doc);
            }
            mongoTemplate.remove(new Query(), IssFinDatesDoc.class);
            mongoTemplate.insertAll(listDoc);
        } catch (DataAccessException e) {
            result = false;
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 构建数据总入口
     * 
     * @return
     */
    public List<BuildResult> build(boolean initAll) throws Exception {

        List<BuildResult> result = new ArrayList<>();

        long start1 = System.currentTimeMillis();
        boolean result1 = buildIndicatorFieldMapping();
        ;
        long end1 = System.currentTimeMillis();
        String name1 = "构建DM、中诚信、安硕映射信息数据";
        result.add(new BuildResult(name1, result1, (end1 - start1) + "ms"));

        long start3 = System.currentTimeMillis();
        boolean result3 = buildAllIssIndicators();
        long end3 = System.currentTimeMillis();
        String name3 = "构建发行人重点财务指标数据(终极版)";
        result.add(new BuildResult(name3, result3, (end3 - start3) + "ms"));

        long start4 = System.currentTimeMillis();
        boolean result4 = ratingService.buildRatingIssBondDoc();
        long end4 = System.currentTimeMillis();
        String name4 = "构建评级数据（本3月、前3月）";
        result.add(new BuildResult(name4, result4, (end4 - start4) + "ms"));

        long start5 = System.currentTimeMillis();
        boolean result5 = buildIssFinanceDate();
        long end5 = System.currentTimeMillis();
        String name5 = "构建财报日期纪录";
        result.add(new BuildResult(name5, result5, (end5 - start5) + "ms"));

        long start6 = System.currentTimeMillis();
        boolean result6 = bondFinanceIndicatorService.buildFinanceIndicators(initAll);
        long end6 = System.currentTimeMillis();
        String name6 = "构建其他财务数据";
        result.add(new BuildResult(name6, result6, (end6 - start6) + "ms"));

        long start7 = System.currentTimeMillis();
        boolean result7 = issIndicatorFieldGroupService.buildIndicatorSpecailFilter();
        long end7 = System.currentTimeMillis();
        String name7 = "构建财务filter";
        result.add(new BuildResult(name7, result7, (end7 - start7) + "ms"));

        return result;
    }

    public String fieldGroupMapping() throws Exception {

        mongoTemplate.dropCollection("bond_field_group_mapping_doc");

        for (int i = 1; i <= 5; i++) {
            mongoTemplate.insertAll(queryBondFieldGroupMappingList(i));
        }

        return "success";
    }

    public List<BondFieldGroupMappingDoc> queryBondFieldGroupMappingList(Integer type) {

        List<BondFieldGroupMappingDoc> list = new ArrayList<BondFieldGroupMappingDoc>();

        StringBuffer sql = new StringBuffer();

        switch (type) {
            case 1:// 市场指标
                list.add(new BondFieldGroupMappingDoc("estYield", "中债估值(%)", 1, 1, 0));
                // list.add(new BondFieldGroupMappingDoc("estDirtyPrice",
                // "估价全价", 0));
                list.add(new BondFieldGroupMappingDoc("estCleanPrice", "估值净价", 0, 1, 0));
                list.add(new BondFieldGroupMappingDoc("optionYield", "行权收益率(%)", 1, 1, 0));
                list.add(new BondFieldGroupMappingDoc("staticSpread", "单券利差", 0, 1, 0));
                // list.add(new
                // BondFieldGroupMappingDoc("staticSpreadInduQuantile",
                // "单券利差行业分位", 0));
                list.add(new BondFieldGroupMappingDoc("macd", "久期", 0, 1, 0));
                list.add(new BondFieldGroupMappingDoc("modd", "修正久期", 0, 1, 0));
                list.add(new BondFieldGroupMappingDoc("convexity", "凸性", 0, 1, 0));
                list.add(new BondFieldGroupMappingDoc("convRatio", "标准券折算率", 0, 1, 0));
                return list;
            case 2:// 专项指标
                sql.append("SELECT ");
//                sql.append("\n\tcolumn_name  AS columnName,");
//                sql.append("\n\tfield_name AS fieldName,");
//                sql.append("\n\tgroup_name AS groupName,");
//                sql.append("\n\tfield_type AS fieldType,");
//                sql.append("\n\tpercent AS percent,");
//                sql.append("\n\t" + type);
//                sql.append("\t AS type");
//                sql.append("\n\tFROM");
//                sql.append("\n\tdmdb.dm_field_group_mapping ");
//                sql.append("\n\tWHERE table_name = 'dm_analysis_indu' ");
                sql.append("\n\t f.column_name AS columnName,");
                sql.append("\n\t f.field_name AS fieldName,");
                sql.append("\n\t g.group_name AS groupName,");
                sql.append("\n\t f.field_type AS fieldType,");
                sql.append("\n\t f.percent AS percent,");
                sql.append("\n\t" + type).append(" AS type");;
                sql.append("\n\t from dmdb.t_bond_indicator_info f ");
                sql.append("\n\t LEFT JOIN dmdb.t_bond_indicator_relevance_group r ON f.id = r.indicator_id ");
                sql.append("\n\t LEFT JOIN dmdb.t_bond_indicator_group g ON r.group_id = g.id ");
                sql.append("\n\t WHERE f.indicator_type=0 AND f.effective=1 AND f.table_name = 'dm_analysis_indu' ");
                break;
            case 3:// 资产负债
            case 4:// 利润
            case 5:// 现金流量
                sql.append("SELECT ");
                sql.append("\n\tcolumn_name AS columnName,");
                sql.append("\n\tfield_name AS fieldName,");
                sql.append("\n\tgroup_name AS groupName,");
                sql.append("\n\tgroup_name_parent AS groupNameParent,");
                sql.append("\n\t" + type);
                sql.append("\t AS type");
                sql.append("\n\tFROM");
                sql.append("\n\tdmdb.field_group_mapping ");
                sql.append("\n\tWHERE table_name = 'manu_fina_sheet' AND column_name NOT REGEXP '_'");
                int i = 0;
                if (type == 3) {
                    i = 1;
                } else if (type == 4) {
                    i = 2;
                } else if (type == 5) {
                    i = 3;
                }
                sql.append("\n\tAND TYPE = " + i);
                break;
            default:
                break;
        }
        list = jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<>(BondFieldGroupMappingDoc.class));

        // 单位处理
        if (list == null || list.size() < 1) {
            return list;
        }

        list.stream().forEach(doc -> {
            String fieldName = doc.getFieldName();
            if (!StringUtils.isEmpty(fieldName)) {
                for (String key : COMPANYS.keySet()) {
                    if (fieldName.indexOf(key) != -1) {
                        doc.setCompany(key);
                        doc.setCompanyType(COMPANYS.get(key));
                        break;
                    } else {
                        doc.setCompanyType(0);
                    }
                }
            }
        });

        return list;

    }

    private final static Map<String, Integer> COMPANYS = new HashMap<String, Integer>() {
        private static final long serialVersionUID = 1L;
        {
            put("％", 1);
            put("%", 1);
            put("万元", 2);
            put("天", 3);
            put("次", 4);
            put("个", 5);
            put("人", 6);
            put("万元/部", 7);
            put("万元/人", 8);
            put("万元/网点", 9);
            put("亿美元", 10);
        }
    };

}
