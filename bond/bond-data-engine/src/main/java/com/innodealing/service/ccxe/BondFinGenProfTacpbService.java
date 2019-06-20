package com.innodealing.service.ccxe;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.innodealing.dao.jpa.dm.bond.ccxe.BondFinGenProfTacpbRep;
import com.innodealing.dao.jpa.dm.bond.ccxesnapshot.BondFinGenProfTacpbSnapShotRep;
import com.innodealing.engine.ccxe.BondCcxeDataChangeEventChannel;
import com.innodealing.model.dm.bond.ccxe.BondFinGenProfTacpb;
import com.innodealing.util.DateUtils;

@Component
public class BondFinGenProfTacpbService {

    private static final Logger LOG = LoggerFactory.getLogger(BondFinGenProfTacpbService.class);

    @Autowired
    @Qualifier("ccxeJdbcTemplate")
    protected JdbcTemplate ccxeJdbcTemplate;

    @Autowired
    @Qualifier("ccxeSnapshotJdbcTemplate")
    protected JdbcTemplate ccxeSnapshotJdbcTemplate;

    @Autowired
    protected BondFinGenProfTacpbRep bondFinGenProfTacpbRep;
    
    @Autowired
    protected BondFinGenProfTacpbSnapShotRep snapshotBean_BondFinGenProfTacpbRep;

    @Autowired
    private BondCcxeDataChangeEventChannel eventChannel ;
        
    public synchronized void pollChange(String beanName, Date syncSideTime) throws Exception {
    	String sqlMaxCcxeid = "select max(ccxeid) from d_bond_fin_gen_prof_tacpb" ;
    	Date primarySideTime = ccxeJdbcTemplate.queryForObject(sqlMaxCcxeid, Date.class);
        LOG.info("max CCXEID of ccxe.d_bond_fin_gen_prof_tacpb is: " + primarySideTime);
    	
        Date syncSideScanHeadTime = (syncSideTime == null) ? ccxeSnapshotJdbcTemplate.queryForObject(sqlMaxCcxeid, Date.class) : syncSideTime;
        LOG.info("max CCXEID of ccxeSnapshot.d_bond_fin_gen_prof_tacpb is: " + syncSideScanHeadTime);
        
        Date syncSideScanTailTime = DateUtils.nextDay(syncSideScanHeadTime);
 
        //如果备份的库时间指针已经越过主库，则停止对比逻辑
        while (!syncSideScanHeadTime.after(primarySideTime)) {
            
            //calculate one day's data each iteration 
            processTableDataChange(beanName, syncSideScanHeadTime, syncSideScanTailTime);
            
            //shift one day until scan pointer cross primary db
            syncSideScanHeadTime = syncSideScanTailTime;
            syncSideScanTailTime = DateUtils.nextDay(syncSideScanTailTime);
        }
    }
    
    private void processTableDataChange(String beanName, Date fromTime, Date toTime) {
        LOG.info("processTableDataChange start, fromTime:" + fromTime + " toTime:" + toTime);
        Long taskTartTime = System.currentTimeMillis();

        String fromTimeStr = DateUtils.convertDateToString(fromTime);
        String toTimeStr = DateUtils.convertDateToString(toTime);
        
        Long totalCount = bondFinGenProfTacpbRep.getNewListCount(fromTimeStr, toTimeStr);
        LOG.info("changed row count:" + totalCount);
        
        Long totalRound = totalCount/5000 + ((totalCount%5000 > 0)? 1:0);     
        for (Integer round = 0; round < totalRound; ++ round) {
            batchProcess(beanName, fromTimeStr, toTimeStr, round*5000, 5000);
        }
        
        LOG.info("processTableDataChange end, fromTime:" + fromTime + " toTime" + toTime + 
                " done, elapsed:" +(System.currentTimeMillis() - taskTartTime)/1000
                + " seconds "
                );
    }

    private void batchProcess(String beanName, String fromTime, String toTime, Integer from, Integer count) {
        Long funStartTime = System.currentTimeMillis();
        Long loopStartTime = funStartTime;
        Long startTemp = funStartTime;
        List<BondFinGenProfTacpb> rows = bondFinGenProfTacpbRep.getNewList(fromTime, toTime, from, count);
        LOG.info("query elaspsed:" + (System.currentTimeMillis() - startTemp));
        
        Integer i = 0;
        Long startTime = System.currentTimeMillis();
        LOG.info("=======start time:" + startTime + ",from is " + from + ",count is " + count);
        for ( i = 0; i < rows.size(); ++i)
        {
        	BondFinGenProfTacpb row = rows.get(i);
            String endDate = DateUtils.convertDateToString(row.getEndDate());
            Long snapshotCount = snapshotBean_BondFinGenProfTacpbRep.getCount(row.getId(), row.getBondUniCode(), row.getComUniCode(), endDate);
            if (snapshotCount == 0) {
                eventChannel.sendCreateEvents(beanName, row);
                snapshotBean_BondFinGenProfTacpbRep.save(row);
                LOG.info("new row: " + row.toString());
            }
            else {
                Long matchCount = ccxeSnapshotJdbcTemplate.queryForObject(row.createEqualsSql(row.getId(), row.getBondUniCode(), row.getComUniCode(), endDate), Long.class);
                if (matchCount > 1) {
                    eventChannel.sendUpdateEvents(beanName, row);
                    snapshotBean_BondFinGenProfTacpbRep.save(row);
                    LOG.info("update row: " + row.toString());
                }
            }
            
            if (i > 0 && i%1000 == 0) {
                LOG.info("---- process from row:" + i + " done " + "elapsed:" + 
                        (System.currentTimeMillis() - loopStartTime)  +
                        "--------");
                loopStartTime = System.currentTimeMillis();
             }
        }
        Long forEachTime = System.currentTimeMillis();
        LOG.info("=======forEachTime time:" + forEachTime + ", period is " + (forEachTime - startTime));
    }
}
