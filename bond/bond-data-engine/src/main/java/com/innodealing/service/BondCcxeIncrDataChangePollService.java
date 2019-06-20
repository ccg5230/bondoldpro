package com.innodealing.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Table;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.CachedIntrospectionResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlRowSetResultSetExtractor;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.innodealing.dao.redis.RedisUtil;
import com.innodealing.engine.ccxe.BondCcxeDataChangeEventChannel;
import com.innodealing.model.dm.bond.ccxe.BondFinFalBalaTafbb;
import com.innodealing.util.BeanPropertyRowMapperExt;



@Component
public class BondCcxeIncrDataChangePollService {

    private static final Logger LOG = LoggerFactory.getLogger(BondCcxeIncrDataChangePollService.class);

    @Autowired
    @Qualifier("ccxeJdbcTemplate")
    protected JdbcTemplate ccxeJdbcTemplate;

    @Autowired
    @Qualifier("ccxeSnapshotJdbcTemplate")
    protected JdbcTemplate ccxeSnapshotJdbcTemplate;

    @Autowired
    private ApplicationContext appContext;
    
    static private String packagePrefix = "com.innodealing.model.dm.bond.ccxe.";
    static private String snapshotJpaPackagePrefix = "snapshotBean_";

    private SimpleDateFormat optTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private BondCcxeDataChangeEventChannel eventChannel ;
    
    @SuppressWarnings("rawtypes")
    private void processTableDataChange(String modelName, Date fromTime, Date toTime) throws Exception
    {
        LOG.info("processTableDataChange start, modelName: " + modelName 
                + " fromTime:" + fromTime + " toTime:" + toTime);
        Long taskTartTime = System.currentTimeMillis();
        
        Table table = modelName2Table(modelName);
        String sqlFormat = "select %s from " + table.name() + 
                " where " + 
                " ccxeid >= '" + optTimeFormat.format(fromTime) + "'" +
                " and ccxeid <= '" + optTimeFormat.format(toTime) + "'" ;
        
        //get count of current operation
        String sqlCount = String.format(sqlFormat, "count(1)");
        Long totalCount = ccxeJdbcTemplate.queryForObject(sqlCount.toString(), Long.class);
        LOG.info("changed row count:" + totalCount);
        
        //long start = System.currentTimeMillis();
        //@SuppressWarnings("unused")
        //SqlRowSet rowSet = ccxeJdbcTemplate.queryForRowSet(sqlData);
        //LOG.info("queryForRowSet(" + sqlData + ") elaspsed:" + (System.currentTimeMillis() - start));

        Class<?> entityClass = Class.forName(packagePrefix + modelName);
        String sqlData = String.format(sqlFormat, "*");
        
        Long totalRound = totalCount/5000 + ((totalCount%5000 > 0)? 1:0);     
        for (Integer round = 0; round < totalRound; ++ round) {
            batchProcess(modelName, entityClass, table, sqlData, round*5000, 5000);
        }
        
        LOG.info("processTableDataChange end, modelName: " + modelName + 
                " fromTime:" + fromTime + " toTime" + toTime + 
                " done, elapsed:" +(System.currentTimeMillis() - taskTartTime)/1000
                + " seconds "
                );
    }

    private void batchProcess(String modelName, Class<?> entityClass, Table table, String sqlData, 
            Integer from, Integer count)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        
        String sqlDataLimt = sqlData + String.format(" limit %1$d,%2$d ", from, 5000);
        
        Long funStartTime = System.currentTimeMillis();
        Long loopStartTime = funStartTime;
        Long startTemp = funStartTime;
        
        @SuppressWarnings("unchecked")
        List<?> rows = ccxeJdbcTemplate.query(sqlDataLimt.toString(), new BeanPropertyRowMapperExt(entityClass));
        LOG.info("query(" + sqlData + ") elaspsed:" + (System.currentTimeMillis() - startTemp));
        
        Integer i = 0;
        for ( i = 0; i < rows.size(); ++i)
        {
            Object row = rows.get(i);
            String sqlSnapshotData = "select * from " + table.name() ;
            String sqlSnapshotCond = makeSnapshotQueryCondition(row);
            String sqlSnapshot = sqlSnapshotData + " where " + sqlSnapshotCond;
            
            List<?> snapshotRows = ccxeSnapshotJdbcTemplate.query(sqlSnapshot, 
                    new BeanPropertyRowMapperExt(entityClass));
            if (snapshotRows.isEmpty()) {
                eventChannel.sendCreateEvents(modelName, row);
                Object result = saveCcxeSnapshot(modelName, row);
                LOG.info("new row: " + result);
            }
            else {
                Method methodEquals = row.getClass().getMethod("equals", Object.class);
                Boolean isEqual = (Boolean) methodEquals.invoke(row, snapshotRows.get(0));
                if (!isEqual) {
                    eventChannel.sendUpdateEvents(modelName, row);
                    Object result = saveCcxeSnapshot(modelName, row);
                    LOG.info("update row: " + result.toString());
                }
            }
            
            if (i > 0 && i%1000 == 0) {
                LOG.info("---- process from row:" + i + " done " + "elapsed:" + 
                        (System.currentTimeMillis() - loopStartTime)  +
                        "--------");
                loopStartTime = System.currentTimeMillis();
             }
        }
    }

    static private String makeSnapshotQueryCondition(Object row) 
            throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
    {
        String sqlSnapshotCond = "";

        try {
            Method methodGetBondUniCode = row.getClass().getMethod("getBondUniCode");
            Long bondUniCode = null;
            if (methodGetBondUniCode != null) {
                bondUniCode = (Long) methodGetBondUniCode.invoke(row);
                if (bondUniCode != null) {
                    if (!sqlSnapshotCond.isEmpty())
                        sqlSnapshotCond += " and ";
                    sqlSnapshotCond += " bond_uni_code=" + bondUniCode;
                }
            } 
        } catch (Exception e) {
            LOG.debug("failed to getBondUniCode in makeSnapshotQueryCondition", e);
        }
        
        try {
            Method methodGetComdUniCode = row.getClass().getMethod("getComUniCode");
            Long comUniCode = null;
            if (methodGetComdUniCode != null) {
                comUniCode = (Long) methodGetComdUniCode.invoke(row);
                if (comUniCode != null) {
                    if (!sqlSnapshotCond.isEmpty())
                        sqlSnapshotCond += " and ";
                    sqlSnapshotCond += " com_uni_code=" + comUniCode;
                }
            } 
        } catch (Exception e) {
            LOG.debug("failed to getComUniCode in makeSnapshotQueryCondition", e);
        }
        
        try {
            Method methodGetComdUniCode = row.getClass().getMethod("getOrgUniCode");
            Long comUniCode = null;
            if (methodGetComdUniCode != null) {
                comUniCode = (Long) methodGetComdUniCode.invoke(row);
                if (comUniCode != null) {
                    if (!sqlSnapshotCond.isEmpty())
                        sqlSnapshotCond += " and ";
                    sqlSnapshotCond += " org_uni_code=" + comUniCode;
                }
            } 
        } catch (Exception e) {
            LOG.debug("failed to getOrgUniCode in makeSnapshotQueryCondition", e);
        }
        
        Method methodGetId = row.getClass().getMethod("getId");
        String id = null;
        if (methodGetId != null) {
            id = (String) methodGetId.invoke(row);
            if (id != null) {
                if (!sqlSnapshotCond.isEmpty()) sqlSnapshotCond += " and ";
                sqlSnapshotCond += " id='" +  id + "'";
            }
        }
        return sqlSnapshotCond;
    }

    private Object saveCcxeSnapshot(String modelName, Object row)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        String jpaRepositoryBeanName = snapshotJpaPackagePrefix + 
                modelName.substring(modelName.lastIndexOf(".") + 1, modelName.length()) + 
                "Rep";
        Object repository = appContext.getBean(jpaRepositoryBeanName);
        Class c = repository.getClass();
        for (Method method : c.getDeclaredMethods()) {
            //System.out.println(method.getName() + "," + method.toGenericString() + "," + method.getParameterTypes().toString());
            if (method.getName().equals("save") ) {
                Object type = method.getParameterTypes()[0];
                if (type.equals(Object.class)) {
                    return method.invoke(repository, row);
                }
            }
        }
        return null;
    }

    public synchronized void pollChange(String beanName, Date syncSideTime) throws Exception {
        
        String tableName = modelName2Table(beanName).name();
        Date primarySideTime = findPrimaryMaxCcxeid(tableName);
        Date syncSideScanHeadTime = (syncSideTime == null) ? findSnapshotMaxCcxeid(tableName) : syncSideTime;
        Date syncSideScanTailTime = nextDay(syncSideScanHeadTime);
 
        //如果备份的库时间指针已经越过主库，则停止对比逻辑
        while (!syncSideScanHeadTime.after(primarySideTime)) {
            
            //calculate one day's data each iteration 
            processTableDataChange(beanName, syncSideScanHeadTime, syncSideScanTailTime);
            
            //shift one day until scan pointer cross primary db
            syncSideScanHeadTime = syncSideScanTailTime;
            syncSideScanTailTime = nextDay(syncSideScanTailTime);
        }
    }
    
    private Date nextDay(Date date)
    {
        Calendar c = Calendar.getInstance(); 
        c.setTime(date); 
        c.add(Calendar.DATE, 1);
        return c.getTime();
    }
    
    private Date findSnapshotMaxCcxeid(String tableName)
    {
        String sqlMaxCcxeid = "select max(ccxeid) from " + tableName ;
        Date maxCcxeid = ccxeSnapshotJdbcTemplate.queryForObject(sqlMaxCcxeid, Date.class);
        LOG.info("max CCXEID of "+ tableName + " is: " + maxCcxeid);
        return maxCcxeid;
    }

    private Date findPrimaryMaxCcxeid(String tableName)
    {
        String sqlMaxCcxeid = "select max(ccxeid) from " + tableName ;
        Date maxCcxeid = ccxeJdbcTemplate.queryForObject(sqlMaxCcxeid, Date.class);
        LOG.info("max CCXEID of "+ tableName + " is: " + maxCcxeid);
        return maxCcxeid;
    }

    static private Table modelName2Table(String modelName) throws ClassNotFoundException
    {
        Class<?> entityClass = Class.forName(packagePrefix + modelName);
        Table table = entityClass.getAnnotation(Table.class);
        return table;
    }
}
