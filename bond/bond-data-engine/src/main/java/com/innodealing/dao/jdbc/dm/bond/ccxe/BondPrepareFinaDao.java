package com.innodealing.dao.jdbc.dm.bond.ccxe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.innodealing.dao.jdbc.BaseDao;
import com.innodealing.model.dm.bond.asbrs.BondBankFinaSheet;
import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;

/**
 * @author kunkun.zhou
 * @date 2016年12月12日
 * @clasename BondPrepareFinaDao.java
 * @decription TODO
 */
@Component
public class BondPrepareFinaDao extends BaseDao<BondBankFinaSheet> {
	private static final Logger LOG = LoggerFactory.getLogger(BondPrepareFinaDao.class);
		
	public void prepareDataBeforeDo() throws Exception {
		
		String createIndex1 = "create index cesiindex on d_bond_fin_fal_bala_tafbb (`ISVALID`, `END_DATE`, `SHEET_MARK_PAR`, `COM_UNI_CODE`)"; 
		String createIndex2 = "create index cesiindex on d_bond_fin_fal_cash_tafcb (`ISVALID`, `END_DATE`, `SHEET_MARK_PAR`, `COM_UNI_CODE`)";
		String createIndex3 = "create index cesiindex on d_bond_fin_fal_prof_tafpb (`ISVALID`, `END_DATE`, `SHEET_MARK_PAR`, `COM_UNI_CODE`)";
		String createIndex4 = "create index cesiindex on d_bond_fin_gen_bala_tacbb (`ISVALID`, `END_DATE`, `SHEET_MARK_PAR`, `COM_UNI_CODE`)";
		String createIndex5 = "create index cesiindex on d_bond_fin_gen_cash_taccb (`ISVALID`, `END_DATE`, `SHEET_MARK_PAR`, `COM_UNI_CODE`)";
		String createIndex6 = "create index cesiindex on d_bond_fin_gen_prof_tacpb (`ISVALID`, `END_DATE`, `SHEET_MARK_PAR`, `COM_UNI_CODE`)";
		
		String dropTmpTable1 = "DROP TABLE IF EXISTS  tmp_bond_fin_fal_bala_tafbb";
		String dropTmpTable2 = "DROP TABLE IF EXISTS  tmp_bond_fin_fal_cash_tafcb";
		String dropTmpTable3 = "DROP TABLE IF EXISTS  tmp_bond_fin_fal_prof_tafpb";
		String dropTmpTable4 = "DROP TABLE IF EXISTS  tmp_bond_fin_gen_bala_tacbb";
		String dropTmpTable5 = "DROP TABLE IF EXISTS  tmp_bond_fin_gen_cash_taccb";
		String dropTmpTable6 = "DROP TABLE IF EXISTS  tmp_bond_fin_gen_prof_tacpb";
	//	String dropTmpTable7 = "DROP TABLE IF EXISTS  tmp_com_enddate";
		
		String createTmpTable1 = "CREATE TABLE tmp_bond_fin_fal_bala_tafbb SELECT * from d_bond_fin_fal_bala_tafbb a where a.ISVALID=1 and a.SHEET_MARK_PAR=1 and a.END_DATE >=STR_TO_DATE('2007-01-01 00:00:00','%Y-%m-%d %H:%i:%s') group by a.COM_UNI_CODE,a.END_DATE";
		String createTmpTable2 = "CREATE TABLE tmp_bond_fin_fal_cash_tafcb SELECT * from d_bond_fin_fal_cash_tafcb a where a.ISVALID=1 and a.SHEET_MARK_PAR=1 and a.END_DATE >=STR_TO_DATE('2007-01-01 00:00:00','%Y-%m-%d %H:%i:%s') group by a.COM_UNI_CODE,a.END_DATE";
		String createTmpTable3 = "CREATE TABLE tmp_bond_fin_fal_prof_tafpb SELECT * from d_bond_fin_fal_prof_tafpb a where a.ISVALID=1 and a.SHEET_MARK_PAR=1 and a.END_DATE >=STR_TO_DATE('2007-01-01 00:00:00','%Y-%m-%d %H:%i:%s') group by a.COM_UNI_CODE,a.END_DATE";
		String createTmpTable4 = "CREATE TABLE tmp_bond_fin_gen_bala_tacbb SELECT * from d_bond_fin_gen_bala_tacbb a where a.ISVALID=1 and a.SHEET_MARK_PAR=1 and a.END_DATE >=STR_TO_DATE('2007-01-01 00:00:00','%Y-%m-%d %H:%i:%s') group by a.COM_UNI_CODE,a.END_DATE";
		String createTmpTable5 = "CREATE TABLE tmp_bond_fin_gen_cash_taccb SELECT * from d_bond_fin_gen_cash_taccb a where a.ISVALID=1 and a.SHEET_MARK_PAR=1 and a.END_DATE >=STR_TO_DATE('2007-01-01 00:00:00','%Y-%m-%d %H:%i:%s') group by a.COM_UNI_CODE,a.END_DATE";
		String createTmpTable6 = "CREATE TABLE tmp_bond_fin_gen_prof_tacpb SELECT * from d_bond_fin_gen_prof_tacpb a where a.ISVALID=1 and a.SHEET_MARK_PAR=1 and a.END_DATE >=STR_TO_DATE('2007-01-01 00:00:00','%Y-%m-%d %H:%i:%s') group by a.COM_UNI_CODE,a.END_DATE";
		
		String createTmpIndex1 = "create index cesiindex on tmp_bond_fin_fal_bala_tafbb (`END_DATE`, `COM_UNI_CODE`)";
		String createTmpIndex2 = "create index cesiindex on tmp_bond_fin_fal_cash_tafcb (`END_DATE`, `COM_UNI_CODE`)";
		String createTmpIndex3 = "create index cesiindex on tmp_bond_fin_fal_prof_tafpb (`END_DATE`, `COM_UNI_CODE`)";
		String createTmpIndex4 = "create index cesiindex on tmp_bond_fin_gen_bala_tacbb (`END_DATE`, `COM_UNI_CODE`)";
		String createTmpIndex5 = "create index cesiindex on tmp_bond_fin_gen_cash_taccb (`END_DATE`, `COM_UNI_CODE`)";
		String createTmpIndex6 = "create index cesiindex on tmp_bond_fin_gen_prof_tacpb (`END_DATE`, `COM_UNI_CODE`)";
		
		/*String createTmpComEndDateTable = "CREATE TABLE `tmp_com_enddate` ("
				+ "`id` int(11) NOT NULL AUTO_INCREMENT,"
				+ "`com_uni_code` bigint(20) DEFAULT NULL,"
				+ "`end_date` datetime DEFAULT NULL,"
				+ "PRIMARY KEY (`id`),"
				+ "KEY `com_uni_code` (`com_uni_code`,`end_date`)"
				+ ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";
		
		String insertTmpComEndDateTable1 = "insert into tmp_com_enddate (com_uni_code,end_date) select a.COM_UNI_CODE,a.END_DATE from tmp_bond_fin_fal_bala_tafbb a where not exists (select 1 from tmp_com_enddate b where a.COM_UNI_CODE=b.com_uni_code and a.END_DATE=b.end_date)";
		String insertTmpComEndDateTable2 = "insert into tmp_com_enddate (com_uni_code,end_date) select a.COM_UNI_CODE,a.END_DATE from tmp_bond_fin_fal_cash_tafcb a where not exists (select 1 from tmp_com_enddate b where a.COM_UNI_CODE=b.com_uni_code and a.END_DATE=b.end_date)";
		String insertTmpComEndDateTable3 = "insert into tmp_com_enddate (com_uni_code,end_date) select a.COM_UNI_CODE,a.END_DATE from tmp_bond_fin_fal_prof_tafpb a where not exists (select 1 from tmp_com_enddate b where a.COM_UNI_CODE=b.com_uni_code and a.END_DATE=b.end_date)";
		String insertTmpComEndDateTable4 = "insert into tmp_com_enddate (com_uni_code,end_date) select a.COM_UNI_CODE,a.END_DATE from tmp_bond_fin_gen_bala_tacbb a where not exists (select 1 from tmp_com_enddate b where a.COM_UNI_CODE=b.com_uni_code and a.END_DATE=b.end_date)";
		String insertTmpComEndDateTable5 = "insert into tmp_com_enddate (com_uni_code,end_date) select a.COM_UNI_CODE,a.END_DATE from tmp_bond_fin_gen_cash_taccb a where not exists (select 1 from tmp_com_enddate b where a.COM_UNI_CODE=b.com_uni_code and a.END_DATE=b.end_date)";
		String insertTmpComEndDateTable6 = "insert into tmp_com_enddate (com_uni_code,end_date) select a.COM_UNI_CODE,a.END_DATE from tmp_bond_fin_gen_prof_tacpb a where not exists (select 1 from tmp_com_enddate b where a.COM_UNI_CODE=b.com_uni_code and a.END_DATE=b.end_date)";
		*/
		Long startTime = System.currentTimeMillis();
		LOG.info("--- --- --- ---execute sql begin.startTime is " + startTime);
		try {
			ccxeJdbcTemplate.execute(createIndex1);
		} catch (Exception e) {
			if (e.getCause() instanceof MySQLSyntaxErrorException) {
				LOG.warn("--- ---createIndex1 of d_bond_fin_fal_bala_tafbb warn: index is exists.");
			} else {
				LOG.error("--- ---createIndex1 of d_bond_fin_fal_bala_tafbb error, ", e);
				throw new Exception("createIndex1 of d_bond_fin_fal_bala_tafbb error");
			}			
		}		
		Long createIndex1T = System.currentTimeMillis();
		LOG.info("--- ---createIndex1 of d_bond_fin_fal_bala_tafbb over.createIndex1T is " + createIndex1T + ",period is " + (createIndex1T - startTime));
		try {
			ccxeJdbcTemplate.execute(createIndex2);
		} catch (Exception e) {
			if (e.getCause() instanceof MySQLSyntaxErrorException) {
				LOG.warn("--- ---createIndex2 of d_bond_fin_fal_cash_tafcb warn: index is exists.");
			} else {
				LOG.error("--- ---createIndex2 of d_bond_fin_fal_cash_tafcb error, ", e);
				throw new Exception("createIndex2 of d_bond_fin_fal_cash_tafcb error");
			}			
		}
		Long createIndex2T = System.currentTimeMillis();
		LOG.info("--- ---createIndex2 of d_bond_fin_fal_cash_tafcb over.createIndex2T is " + createIndex2T + ",period is " + (createIndex2T - createIndex1T));
		try {
			ccxeJdbcTemplate.execute(createIndex3);
		} catch (Exception e) {
			if (e.getCause() instanceof MySQLSyntaxErrorException) {
				LOG.warn("--- ---createIndex3 of d_bond_fin_fal_prof_tafpb warn: index is exists.");
			} else {
				LOG.error("--- ---createIndex3 of d_bond_fin_fal_prof_tafpb error, ", e);
				throw new Exception("createIndex3 of d_bond_fin_fal_prof_tafpb error");
			}
		}
		Long createIndex3T = System.currentTimeMillis();
		LOG.info("--- ---createIndex3 of d_bond_fin_fal_prof_tafpb over.createIndex3T is " + createIndex3T + ",period is " + (createIndex3T - createIndex2T));
		try {
			ccxeJdbcTemplate.execute(createIndex4);
		} catch (Exception e) {
			if (e.getCause() instanceof MySQLSyntaxErrorException) {
				LOG.warn("--- ---createIndex4 of d_bond_fin_gen_bala_tacbb warn: index is exists.");
			} else {
				LOG.error("--- ---createIndex4 of d_bond_fin_gen_bala_tacbb error, ", e);
				throw new Exception("createIndex4 of d_bond_fin_gen_bala_tacbb error");
			}
		}
		Long createIndex4T = System.currentTimeMillis();
		LOG.info("--- ---createIndex4 of d_bond_fin_gen_bala_tacbb over.createIndex4T is " + createIndex4T + ",period is " + (createIndex4T - createIndex3T));
		try {
			ccxeJdbcTemplate.execute(createIndex5);
		} catch (Exception e) {
			if (e.getCause() instanceof MySQLSyntaxErrorException) {
				LOG.warn("--- ---createIndex5 of d_bond_fin_gen_cash_taccb warn: index is exists.");
			} else {
				LOG.error("--- ---createIndex5 of d_bond_fin_gen_cash_taccb error, ", e);
				throw new Exception("createIndex5 of d_bond_fin_gen_cash_taccb error");
			}
		}
		Long createIndex5T = System.currentTimeMillis();
		LOG.info("--- ---createIndex5 of d_bond_fin_gen_cash_taccb over.createIndex5T is " + createIndex5T + ",period is " + (createIndex5T - createIndex4T));
		try {
			ccxeJdbcTemplate.execute(createIndex6);
		} catch (Exception e) {
			if (e.getCause() instanceof MySQLSyntaxErrorException) {
				LOG.warn("--- ---createIndex6 of d_bond_fin_gen_prof_tacpb warn: index is exists.");
			} else {
				LOG.error("--- ---createIndex6 of d_bond_fin_gen_prof_tacpb error, ", e);
				throw new Exception("createIndex6 of d_bond_fin_gen_prof_tacpb error");
			}
		}
		Long createIndex6T = System.currentTimeMillis();
		LOG.info("--- ---createIndex6 of d_bond_fin_gen_prof_tacpb over.createIndex6T is " + createIndex6T + ",period is " + (createIndex6T - createIndex5T));
		LOG.info("--- ---createIndex success.");
		
		ccxeJdbcTemplate.execute(dropTmpTable1);
		Long dropTmpTable1T = System.currentTimeMillis();
		LOG.info("--- ---dropTmpTable1 of tmp_bond_fin_fal_bala_tafbb success.dropTmpTable1T is " + dropTmpTable1T + ",period is " + (dropTmpTable1T - createIndex6T));
		ccxeJdbcTemplate.execute(dropTmpTable2);
		Long dropTmpTable2T = System.currentTimeMillis();
		LOG.info("--- ---dropTmpTable2 of tmp_bond_fin_fal_cash_tafcb success.dropTmpTable2T is " + dropTmpTable2T + ",period is " + (dropTmpTable2T - dropTmpTable1T));
		ccxeJdbcTemplate.execute(dropTmpTable3);
		Long dropTmpTable3T = System.currentTimeMillis();
		LOG.info("--- ---dropTmpTable3 of tmp_bond_fin_fal_prof_tafpb success.dropTmpTable3T is " + dropTmpTable3T + ",period is " + (dropTmpTable3T - dropTmpTable2T));
		ccxeJdbcTemplate.execute(dropTmpTable4);
		Long dropTmpTable4T = System.currentTimeMillis();
		LOG.info("--- ---dropTmpTable4 of tmp_bond_fin_gen_bala_tacbb success.dropTmpTable4T is " + dropTmpTable4T + ",period is " + (dropTmpTable4T - dropTmpTable3T));
		ccxeJdbcTemplate.execute(dropTmpTable5);
		Long dropTmpTable5T = System.currentTimeMillis();
		LOG.info("--- ---dropTmpTable5 of tmp_bond_fin_gen_cash_taccb success.dropTmpTable5T is " + dropTmpTable5T + ",period is " + (dropTmpTable5T - dropTmpTable4T));
		ccxeJdbcTemplate.execute(dropTmpTable6);
		Long dropTmpTable6T = System.currentTimeMillis();
		LOG.info("--- ---dropTmpTable6 of tmp_bond_fin_gen_prof_tacpb success.dropTmpTable6T is " + dropTmpTable6T + ",period is " + (dropTmpTable6T - dropTmpTable5T));
		/*ccxeJdbcTemplate.execute(dropTmpTable7);
		Long dropTmpTable7T = System.currentTimeMillis();
		LOG.info("--- ---dropTmpTable7 of tmp_com_enddate success.dropTmpTable7T is " + dropTmpTable7T + ",period is " + (dropTmpTable7T - dropTmpTable6T));*/
		LOG.info("--- ---dropTmpTable success.");
		
		ccxeJdbcTemplate.execute(createTmpTable1);
		Long createTmpTable1T = System.currentTimeMillis();
		LOG.info("--- ---createTmpTable1 of tmp_bond_fin_fal_bala_tafbb success.createTmpTable1T is " + createTmpTable1T + ",period is " + (createTmpTable1T - dropTmpTable6T));
		ccxeJdbcTemplate.execute(createTmpTable2);
		Long createTmpTable2T = System.currentTimeMillis();
		LOG.info("--- ---createTmpTable2 of tmp_bond_fin_fal_cash_tafcb success.createTmpTable2T is " + createTmpTable2T + ",period is " + (createTmpTable2T - createTmpTable1T));
		ccxeJdbcTemplate.execute(createTmpTable3);
		Long createTmpTable3T = System.currentTimeMillis();
		LOG.info("--- ---createTmpTable3 of tmp_bond_fin_fal_prof_tafpb success.createTmpTable3T is " + createTmpTable3T + ",period is " + (createTmpTable3T - createTmpTable2T));
		ccxeJdbcTemplate.execute(createTmpTable4);
		Long createTmpTable4T = System.currentTimeMillis();
		LOG.info("--- ---createTmpTable4 of tmp_bond_fin_gen_bala_tacbb success.createTmpTable4T is " + createTmpTable4T + ",period is " + (createTmpTable4T - createTmpTable3T));
		ccxeJdbcTemplate.execute(createTmpTable5);
		Long createTmpTable5T = System.currentTimeMillis();
		LOG.info("--- ---createTmpTable5 of tmp_bond_fin_gen_cash_taccb success.createTmpTable5T is " + createTmpTable5T + ",period is " + (createTmpTable5T - createTmpTable4T));
		ccxeJdbcTemplate.execute(createTmpTable6);
		Long createTmpTable6T = System.currentTimeMillis();
		LOG.info("--- ---createTmpTable6 of tmp_bond_fin_gen_prof_tacpb success.createTmpTable6T is " + createTmpTable6T + ",period is " + (createTmpTable6T - createTmpTable5T));
		LOG.info("--- ---createTmpTable success.");
		
		ccxeJdbcTemplate.execute(createTmpIndex1);
		Long createTmpIndex1T = System.currentTimeMillis();
		LOG.info("--- ---createTmpIndex1 of tmp_bond_fin_fal_bala_tafbb success.createTmpIndex1T is " + createTmpIndex1T + ",period is " + (createTmpIndex1T - createTmpTable6T));
		ccxeJdbcTemplate.execute(createTmpIndex2);
		Long createTmpIndex2T = System.currentTimeMillis();
		LOG.info("--- ---createTmpIndex2 of tmp_bond_fin_fal_cash_tafcb success.createTmpIndex2T is " + createTmpIndex2T + ",period is " + (createTmpIndex2T - createTmpIndex1T));
		ccxeJdbcTemplate.execute(createTmpIndex3);
		Long createTmpIndex3T = System.currentTimeMillis();
		LOG.info("--- ---createTmpIndex3 of tmp_bond_fin_fal_prof_tafpb success.createTmpIndex3T is " + createTmpIndex3T + ",period is " + (createTmpIndex3T - createTmpIndex2T));
		ccxeJdbcTemplate.execute(createTmpIndex4);
		Long createTmpIndex4T = System.currentTimeMillis();
		LOG.info("--- ---createTmpIndex4 of tmp_bond_fin_gen_bala_tacbb success.createTmpIndex4T is " + createTmpIndex4T + ",period is " + (createTmpIndex4T - createTmpIndex3T));
		ccxeJdbcTemplate.execute(createTmpIndex5);
		Long createTmpIndex5T = System.currentTimeMillis();
		LOG.info("--- ---createTmpIndex5 of tmp_bond_fin_gen_cash_taccb success.createTmpIndex5T is " + createTmpIndex5T + ",period is " + (createTmpIndex5T - createTmpIndex4T));
		ccxeJdbcTemplate.execute(createTmpIndex6);
		Long createTmpIndex6T = System.currentTimeMillis();
		LOG.info("--- ---createTmpIndex6 of tmp_bond_fin_gen_prof_tacpb success.createTmpIndex6T is " + createTmpIndex6T + ",period is " + (createTmpIndex6T - createTmpIndex5T));
		LOG.info("--- ---createTmpIndex success.");
		
		/*ccxeJdbcTemplate.execute(createTmpComEndDateTable);
		Long createTmpComEndDateTableT = System.currentTimeMillis();
		LOG.info("--- ---createTmpComEndDateTable of tmp_com_enddate success.createTmpComEndDateTableT is " + createTmpComEndDateTableT + ",period is " + (createTmpComEndDateTableT - createTmpIndex6T));
		LOG.info("--- ---createTmpComEndDateTable success.");
		
		ccxeJdbcTemplate.execute(insertTmpComEndDateTable1);
		Long insertTmpComEndDateTable1T = System.currentTimeMillis();
		LOG.info("--- ---insert into of tmp_com_enddate from tmp_bond_fin_fal_bala_tafbb success.insertTmpComEndDateTable1T is " + insertTmpComEndDateTable1T + ",period is " + (insertTmpComEndDateTable1T - createTmpComEndDateTableT));
		ccxeJdbcTemplate.execute(insertTmpComEndDateTable2);
		Long insertTmpComEndDateTable2T = System.currentTimeMillis();
		LOG.info("--- ---insert into of tmp_com_enddate from tmp_bond_fin_fal_cash_tafcb success.insertTmpComEndDateTable2T is " + insertTmpComEndDateTable2T + ",period is " + (insertTmpComEndDateTable2T - insertTmpComEndDateTable1T));
		ccxeJdbcTemplate.execute(insertTmpComEndDateTable3);
		Long insertTmpComEndDateTable3T = System.currentTimeMillis();
		LOG.info("--- ---insert into of tmp_com_enddate from tmp_bond_fin_fal_prof_tafpb success.insertTmpComEndDateTable3T is " + insertTmpComEndDateTable3T + ",period is " + (insertTmpComEndDateTable3T - insertTmpComEndDateTable2T));
		ccxeJdbcTemplate.execute(insertTmpComEndDateTable4);
		Long insertTmpComEndDateTable4T = System.currentTimeMillis();
		LOG.info("--- ---insert into of tmp_com_enddate from tmp_bond_fin_gen_bala_tacbb success.insertTmpComEndDateTable4T is " + insertTmpComEndDateTable4T + ",period is " + (insertTmpComEndDateTable4T - insertTmpComEndDateTable3T));
		ccxeJdbcTemplate.execute(insertTmpComEndDateTable5);
		Long insertTmpComEndDateTable5T = System.currentTimeMillis();
		LOG.info("--- ---insert into of tmp_com_enddate from tmp_bond_fin_gen_cash_taccb success.insertTmpComEndDateTable5T is " + insertTmpComEndDateTable5T + ",period is " + (insertTmpComEndDateTable5T - insertTmpComEndDateTable4T));
		ccxeJdbcTemplate.execute(insertTmpComEndDateTable6);
		Long insertTmpComEndDateTable6T = System.currentTimeMillis();
		LOG.info("--- ---insert into tmp_com_enddate from tmp_bond_fin_gen_prof_tacpb success.insertTmpComEndDateTable6T is " + insertTmpComEndDateTable6T + ",period is " + (insertTmpComEndDateTable6T - insertTmpComEndDateTable5T));
		LOG.info("--- ---insertTmpComEndDateTable over.");*/
		LOG.info("--- --- --- ---execute sql over.period is " + (createTmpIndex6T - startTime));
	}

	public void removeTmpDataAfterDo() throws Exception {
		String dropTmpTable1 = "DROP TABLE IF EXISTS  tmp_bond_fin_fal_bala_tafbb";
		String dropTmpTable2 = "DROP TABLE IF EXISTS  tmp_bond_fin_fal_cash_tafcb";
		String dropTmpTable3 = "DROP TABLE IF EXISTS  tmp_bond_fin_fal_prof_tafpb";
		String dropTmpTable4 = "DROP TABLE IF EXISTS  tmp_bond_fin_gen_bala_tacbb";
		String dropTmpTable5 = "DROP TABLE IF EXISTS  tmp_bond_fin_gen_cash_taccb";
		String dropTmpTable6 = "DROP TABLE IF EXISTS  tmp_bond_fin_gen_prof_tacpb";
	//	String dropTmpTable7 = "DROP TABLE IF EXISTS  tmp_com_enddate";
		
		Long startTime = System.currentTimeMillis();
		LOG.info("--- ---drop tmp table execute sql begin.startTime is " + startTime);
		
		ccxeJdbcTemplate.execute(dropTmpTable1);
		Long dropTmpTable1T = System.currentTimeMillis();
		LOG.info("--- ---dropTmpTable1 of tmp_bond_fin_fal_bala_tafbb success.dropTmpTable1T is " + dropTmpTable1T + ",period is " + (dropTmpTable1T - startTime));
		ccxeJdbcTemplate.execute(dropTmpTable2);
		Long dropTmpTable2T = System.currentTimeMillis();
		LOG.info("--- ---dropTmpTable2 of tmp_bond_fin_fal_cash_tafcb success.dropTmpTable2T is " + dropTmpTable2T + ",period is " + (dropTmpTable2T - dropTmpTable1T));
		ccxeJdbcTemplate.execute(dropTmpTable3);
		Long dropTmpTable3T = System.currentTimeMillis();
		LOG.info("--- ---dropTmpTable3 of tmp_bond_fin_fal_prof_tafpb success.dropTmpTable3T is " + dropTmpTable3T + ",period is " + (dropTmpTable3T - dropTmpTable2T));
		ccxeJdbcTemplate.execute(dropTmpTable4);
		Long dropTmpTable4T = System.currentTimeMillis();
		LOG.info("--- ---dropTmpTable4 of tmp_bond_fin_gen_bala_tacbb success.dropTmpTable4T is " + dropTmpTable4T + ",period is " + (dropTmpTable4T - dropTmpTable3T));
		ccxeJdbcTemplate.execute(dropTmpTable5);
		Long dropTmpTable5T = System.currentTimeMillis();
		LOG.info("--- ---dropTmpTable5 of tmp_bond_fin_gen_cash_taccb success.dropTmpTable5T is " + dropTmpTable5T + ",period is " + (dropTmpTable5T - dropTmpTable4T));
		ccxeJdbcTemplate.execute(dropTmpTable6);
		Long dropTmpTable6T = System.currentTimeMillis();
		LOG.info("--- ---dropTmpTable6 of tmp_bond_fin_gen_prof_tacpb success.dropTmpTable6T is " + dropTmpTable6T + ",period is " + (dropTmpTable6T - dropTmpTable5T));
		/*ccxeJdbcTemplate.execute(dropTmpTable7);
		Long dropTmpTable7T = System.currentTimeMillis();
		LOG.info("--- ---dropTmpTable7 of tmp_com_enddate success.dropTmpTable7T is " + dropTmpTable7T + ",period is " + (dropTmpTable7T - dropTmpTable6T));*/
		LOG.info("--- ---dropTmpTable success. drop tmp table period is " + (dropTmpTable6T - startTime));
	}
}
