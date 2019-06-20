package com.innodealing.engine.jdbc;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.innodealing.json.portfolio.BondMaturityJson;
import com.innodealing.json.portfolio.ImpliedRatingJson;
import com.innodealing.model.mongo.dm.BondCredRatingDoc;
import com.innodealing.model.mongo.dm.FinanceAlertInfoDoc;
import com.innodealing.model.mongo.dm.IssCredRatingDoc;
import com.innodealing.model.mongo.dm.IssPdRatingDoc;

/**
 * @author stephen.ma
 * @date 2016年11月14日
 * @clasename NotificationMsgDao.java
 * @decription TODO
 */
@Component
public class NotificationMsgDao {
	
    public final static Logger logger = LoggerFactory.getLogger(NotificationMsgDao.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    //债券的存续变动的数据
    public List<BondMaturityJson> findMaturity(int index, int limit){
    	StringBuffer sql = new StringBuffer();
    	sql.append("SELECT ");
    	sql.append("	tt.BOND_UNI_CODE AS bondUniCode, ");
    	sql.append("	tt.YEAR_PAY_DATE AS yearPayDate, ");
    	sql.append("	tt.EXER_PAY_DATE AS exerPayDate, ");
    	sql.append("	substring(tt.THEO_END_DATE,1,10) AS theoEndDate, ");
    	sql.append("	DATEDIFF(substring(tt.THEO_END_DATE,1,10),DATE_FORMAT(NOW(),'%Y-%m-%d')) AS theoDiffdays ");
    	sql.append("FROM( ");
    	commonMaturitySql(sql);
    	sql.append("	)tt WHERE tt.THEO_END_DATE > DATE_FORMAT(NOW(), '%Y-%m-%d 00:00:00') ");
    	sql.append("ORDER BY tt.THEO_END_DATE DESC ");
    	sql.append("LIMIT ").append(index).append(",").append(limit);
    	
    	return jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<>(BondMaturityJson.class));
    }
    
    public int getMaturityCount() {
    	StringBuffer countsql = new StringBuffer();
    	countsql.append("SELECT COUNT(1) FROM (");
    	commonMaturitySql(countsql);
    	countsql.append(" )tt WHERE tt.THEO_END_DATE > DATE_FORMAT(NOW(), '%Y-%m-%d 00:00:00') ");
		return jdbcTemplate.queryForObject(countsql.toString(), Integer.class);
	}

	private void commonMaturitySql(StringBuffer sql) {
		sql.append("	SELECT	t1.BOND_UNI_CODE,t1.YEAR_PAY_DATE,t1.EXER_PAY_DATE,t1.THEO_END_DATE ");
    	sql.append("	FROM bond_ccxe.d_bond_basic_info_1 t1 ");
    	sql.append("	WHERE t1.ISVALID = 1 ");
    	sql.append("	UNION ");
    	sql.append("	SELECT t2.BOND_UNI_CODE,t2.YEAR_PAY_DATE,t2.EXER_PAY_DATE,t2.THEO_END_DATE ");
    	sql.append("	FROM bond_ccxe.d_bond_basic_info_2 t2 ");
    	sql.append("	WHERE t2.ISVALID = 1 ");
    	sql.append("	UNION ");
    	sql.append("	SELECT t3.BOND_UNI_CODE,t3.YEAR_PAY_DATE,t3.EXER_PAY_DATE,t3.THEO_END_DATE ");
    	sql.append("	FROM bond_ccxe.d_bond_basic_info_3 t3 ");
    	sql.append("	WHERE t3.ISVALID = 1 ");
    	sql.append("	UNION ");
    	sql.append("	SELECT t4.BOND_UNI_CODE,t4.YEAR_PAY_DATE,t4.EXER_PAY_DATE,t4.THEO_END_DATE ");
    	sql.append("	FROM bond_ccxe.d_bond_basic_info_4 t4 ");
    	sql.append("	WHERE t4.ISVALID = 1 ");
    	sql.append("	UNION ");
    	sql.append("	SELECT t5.BOND_UNI_CODE,t5.YEAR_PAY_DATE,t5.EXER_PAY_DATE,t5.THEO_END_DATE ");
    	sql.append("	FROM bond_ccxe.d_bond_basic_info_5 t5 ");
    	sql.append("	WHERE t5.ISVALID = 1 ");
	}
	
	public int getFinancialAlertInfoCount(){
    	StringBuffer countsql = new StringBuffer();
    	countsql.append("SELECT COUNT(1) FROM (");
    	mainFinancialAlertInfoSql(countsql);
    	countsql.append(" ) op");
    	return jdbcTemplate.queryForObject(countsql.toString(), Integer.class);
	}

    //财务预警数据
    public List<FinanceAlertInfoDoc> findFinancialAlertInfo(int index, int limit){
    	StringBuffer sql = new StringBuffer();
    	mainFinancialAlertInfoSql(sql);
    	sql.append(" LIMIT ").append(index).append(",").append(limit);
    	return jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<>(FinanceAlertInfoDoc.class));
    }

	/**
	 * @param sql
	 */
	private void mainFinancialAlertInfoSql(StringBuffer sql) {
		sql.append("SELECT w.comUniCode,w.compId,w.currRatio,w.quckRatio,w.invntryDay,w.arDay,w.bizCycleDay,w.totAsstRtrn,w.liab2Asst,w.grssMrgn,w.finDate FROM ( ");
    	sql.append("SELECT u.comUniCode,u.compId,u.currRatio,u.quckRatio,u.invntryDay,u.arDay,u.bizCycleDay,u.totAsstRtrn,u.liab2Asst,u.grssMrgn,u.finDate FROM ( ");
    	commonFinancialAlertInfoSql(sql);
    	sql.append(" ORDER BY t.Comp_ID DESC,t.fin_date DESC ) u ORDER BY u.finDate DESC ");
    	sql.append(" ) w GROUP BY w.comUniCode ");
	}

	private void commonFinancialAlertInfoSql(StringBuffer sql) {
		sql.append("SELECT ");
    	sql.append("	m.com_uni_code AS comUniCode, ");
    	sql.append("	t.Comp_ID AS compId, ");
    	sql.append("	t.Curr_Ratio AS currRatio, ");
    	sql.append("    t.Quck_Ratio AS quckRatio, ");
    	sql.append("	t.Invntry_Day AS invntryDay, ");
    	sql.append("	t.AR_Day AS arDay, ");
    	sql.append("	(t.Invntry_Day + t.AR_Day)AS bizCycleDay, ");
    	sql.append("	t.Tot_Asst_Rtrn AS totAsstRtrn, ");
    	sql.append("	t.Liab2Asst AS liab2Asst, ");
    	sql.append("	t.Grss_Mrgn AS grssMrgn, ");
    	sql.append("	t.fin_date AS finDate ");
    	sql.append("FROM ( ");
    	sql.append("		SELECT ");
    	sql.append("			a.Comp_ID, ");
    	sql.append("			a.Comp_Name, ");
    	sql.append("			a.Curr_Ratio, ");
    	sql.append("			a.Quck_Ratio, ");
    	sql.append("			a.Invntry_Day, ");
    	sql.append("			a.AR_Day, ");
    	sql.append("			a.Tot_Asst_Rtrn, ");
    	sql.append("			a.Liab2Asst, ");
    	sql.append("			a.Grss_Mrgn, ");
    	sql.append("			a.fin_date ");
    	sql.append("		FROM  /*amaresun*/ dmdb.dm_analysis_indu a ");
    	sql.append("		UNION ");
    	sql.append("			SELECT ");
    	sql.append("				b.Comp_ID, ");
    	sql.append("				b.Comp_Name, ");
    	sql.append("				b.Curr_Ratio, ");
    	sql.append("				b.Quck_Ratio, ");
    	sql.append("				b.Invntry_Day, ");
    	sql.append("				b.AR_Day, ");
    	sql.append("				b.Tot_Asst_Rtrn, ");
    	sql.append("				b.Liab2Asst, ");
    	sql.append("				b.Grss_Mrgn, ");
    	sql.append("				b.fin_date ");
    	sql.append("			FROM  /*amaresun*/ dmdb.dm_analysis_indu_annual b ");
    	sql.append("	)t, dmdb.t_bond_com_ext m ");
    	sql.append("WHERE t.Comp_ID = m.ama_com_id ");
	}
	
	public FinanceAlertInfoDoc findLastFinancialAlertInfo(Long comUniCode){
		FinanceAlertInfoDoc finAlertInfo = null;
    	StringBuffer infosql = new StringBuffer();
    	commonFinancialAlertInfoSql(infosql);
    	infosql.append(" AND m.com_uni_code=").append(comUniCode);
    	infosql.append(" ORDER BY t.fin_date DESC LIMIT 1");
    	
    	List<FinanceAlertInfoDoc> list = jdbcTemplate.query(infosql.toString(), new BeanPropertyRowMapper<>(FinanceAlertInfoDoc.class));
		if (null != list && list.size() > 0) {
			finAlertInfo = list.get(0);
		}
		
		return finAlertInfo;
	 }
	
	public List<FinanceAlertInfoDoc> findLastFinancialAlertInfos(){
    	StringBuffer sql = new StringBuffer();
//    	sql.append("SELECT  ");
//    	sql.append("	tt.comUniCode, ");
//    	sql.append("	tt.compId, ");
//    	sql.append("	tt.currRatio, ");
//    	sql.append("	tt.quckRatio, ");
//    	sql.append("	tt.invntryDay, ");
//    	sql.append("	tt.arDay, ");
//    	sql.append("	tt.bizCycleDay, ");
//    	sql.append("	tt.totAsstRtrn, ");
//    	sql.append("	tt.liab2Asst, ");
//    	sql.append("	tt.grssMrgn, ");
//    	sql.append("	tt.finDate ");
//    	sql.append("FROM ");
//    	sql.append("	( ");
//    	sql.append("		SELECT ");
//    	sql.append("			m.com_uni_code AS comUniCode, ");
//    	sql.append("			t.Comp_ID AS compId, ");
//    	sql.append("			t.Curr_Ratio AS currRatio, ");
//    	sql.append("			t.Quck_Ratio AS quckRatio, ");
//    	sql.append("			t.Invntry_Day AS invntryDay, ");
//    	sql.append("			t.AR_Day AS arDay, ");
//    	sql.append("			(t.Invntry_Day + t.AR_Day)AS bizCycleDay, ");
//    	sql.append("			t.Tot_Asst_Rtrn AS totAsstRtrn, ");
//    	sql.append("			t.Liab2Asst AS liab2Asst, ");
//    	sql.append("			t.Grss_Mrgn AS grssMrgn, ");
//    	sql.append("			t.fin_date AS finDate ");
//    	sql.append("		FROM ");
//    	sql.append("			( ");
//    	sql.append("				SELECT ");
//    	sql.append("					a.Comp_ID, ");
//    	sql.append("					a.Comp_Name, ");
//    	sql.append("					a.Curr_Ratio, ");
//    	sql.append("					a.Quck_Ratio, ");
//    	sql.append("					a.Invntry_Day, ");
//    	sql.append("					a.AR_Day, ");
//    	sql.append("					a.Tot_Asst_Rtrn, ");
//    	sql.append("					a.Liab2Asst, ");
//    	sql.append("					a.Grss_Mrgn, ");
//    	sql.append("					a.fin_date ");
//    	sql.append("				FROM ");
//    	sql.append("					 /*amaresun*/ dmdb.dm_analysis_indu a ");
//    	sql.append("				UNION ");
//    	sql.append("					SELECT ");
//    	sql.append("						b.Comp_ID, ");
//    	sql.append("						b.Comp_Name, ");
//    	sql.append("						b.Curr_Ratio, ");
//    	sql.append("						b.Quck_Ratio, ");
//    	sql.append("						b.Invntry_Day, ");
//    	sql.append("						b.AR_Day, ");
//    	sql.append("						b.Tot_Asst_Rtrn, ");
//    	sql.append("						b.Liab2Asst, ");
//    	sql.append("						b.Grss_Mrgn, ");
//    	sql.append("						b.fin_date ");
//    	sql.append("					FROM ");
//    	sql.append("						 /*amaresun*/ dmdb.dm_analysis_indu_annual b ");
//    	sql.append("			)t, ");
//    	sql.append("			dmdb.t_bond_com_ext m ");
//    	sql.append("		WHERE ");
//    	sql.append("			t.Comp_ID = m.ama_com_id ");
//    	sql.append("		ORDER BY ");
//    	sql.append("			t.Comp_ID, ");
//    	sql.append("			t.fin_date DESC ");
//    	sql.append("	)tt ");
//    	sql.append("GROUP BY ");
//    	sql.append("	tt.compId ");
    	this.mainFinancialAlertInfoSql(sql);
		
		return jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<>(FinanceAlertInfoDoc.class));
	 }
	
    //债项评级
    public List<BondCredRatingDoc> findBondCredRating(int index, int limit){
    	StringBuffer sql = new StringBuffer();
    	commonBondCredRatingSql(sql);
    	sql.append("LIMIT ").append(index).append(",").append(limit);
    	return jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<>(BondCredRatingDoc.class));
    }

	/**
	 * @param sql
	 */
	private void commonBondCredRatingSql(StringBuffer sql) {
		sql.append("SELECT T.* FROM( ");
    	sql.append("		SELECT	t.BOND_UNI_CODE AS bondUniCode,t.BOND_SHORT_NAME AS bondShortName,t.ORG_UNI_CODE AS orgUniCode, r.CHI_SHORT_NAME AS orgShortName,t.RATE_WRIT_DATE AS rateWritDate,t.BOND_CRED_LEVEL_PAR AS credLevelPar,t.BOND_CRED_LEVEL AS credLevel,	t.RATE_PROS_PAR AS rateProsPar, ");
    	sql.append("		CASE WHEN t.RATE_PROS_PAR = 1 THEN '正面' WHEN t.RATE_PROS_PAR = 2 THEN	'稳定' WHEN t.RATE_PROS_PAR = 3 THEN '观望' WHEN t.RATE_PROS_PAR = 4 THEN '负面' ELSE '' END AS parName, ");
    	sql.append("		t.IS_NEW_RATE AS isNewRate,	DATE_FORMAT(t.CCXEID,'%Y-%m-%d %H:%i:%s')AS ccxeDate ");
    	sql.append("	FROM bond_ccxe.d_bond_cred_chan t, bond_ccxe.d_pub_org_info_r r ");
    	sql.append("	WHERE t.ORG_UNI_CODE = r.ORG_UNI_CODE ");
    	sql.append("	AND t.ISVALID = 1 ");
    	sql.append("	ORDER BY t.RATE_WRIT_DATE DESC )T ");
    	sql.append("GROUP BY concat(T.bondUniCode,'-',T.orgUniCode) ORDER BY T.bondUniCode ASC ");
	}
    
    public int getBondCredRatingCount() {
    	StringBuffer countsql = new StringBuffer();
    	countsql.append("SELECT COUNT(1) FROM ( ");
    	commonBondCredRatingSql(countsql);
    	countsql.append(" ) ct");
		return jdbcTemplate.queryForObject(countsql.toString(), Integer.class);
	}
    
    public BondCredRatingDoc findLastBondCredRating(Long bondUniCode, Long orgUniCode){
    	BondCredRatingDoc bcrDoc = null; 
    	StringBuffer sql = new StringBuffer();
		 sql.append("SELECT ");
		 sql.append("	t.BOND_UNI_CODE AS bondUniCode, ");
		 sql.append("	t.BOND_SHORT_NAME AS bondShortName, ");
		 sql.append("	t.ORG_UNI_CODE AS orgUniCode, ");
		 sql.append("	r.CHI_SHORT_NAME AS orgShortName, ");
		 sql.append("	t.RATE_WRIT_DATE AS rateWritDate, ");
		 sql.append("	t.BOND_CRED_LEVEL_PAR AS credLevelPar, ");
		 sql.append("	t.BOND_CRED_LEVEL AS credLevel, ");
		 sql.append("	t.RATE_PROS_PAR AS rateProsPar, ");
		 sql.append("	CASE WHEN t.RATE_PROS_PAR = 1 THEN '正面' ");
		 sql.append("	WHEN t.RATE_PROS_PAR = 2 THEN '稳定' ");
		 sql.append("	WHEN t.RATE_PROS_PAR = 3 THEN '观望' ");
		 sql.append("	WHEN t.RATE_PROS_PAR = 4 THEN '负面' ");
		 sql.append("ELSE '' END AS parName, ");
		 sql.append("t.IS_NEW_RATE AS isNewRate, ");
		 sql.append("DATE_FORMAT(t.CCXEID,'%Y-%m-%d %H:%i:%s') AS ccxeDate ");
		 sql.append("FROM bond_ccxe.d_bond_cred_chan t,bond_ccxe.d_pub_org_info_r r ");
		 sql.append("WHERE t.ORG_UNI_CODE = r.ORG_UNI_CODE AND t.ISVALID = 1 AND t.IS_NEW_RATE=1  AND t.BOND_UNI_CODE=").append(bondUniCode).append(" AND t.ORG_UNI_CODE=").append(orgUniCode).append(" ");
		 sql.append("ORDER BY t.RATE_WRIT_DATE DESC LIMIT 1 ");
		 
		 List<BondCredRatingDoc> bcrDocs =  jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<>(BondCredRatingDoc.class));
		 if (null != bcrDocs && bcrDocs.size() > 0) {
			 bcrDoc = bcrDocs.get(0);
		}
		
		return bcrDoc;
    }
    
    //债项主体评级
    public List<IssCredRatingDoc> findIssCredRating(int index, int limit){
    	StringBuffer sql = new StringBuffer();
    	commonIssCredRatingSql(sql);
    	sql.append("LIMIT ").append(index).append(",").append(limit);
    	
    	return jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<>(IssCredRatingDoc.class));
    }

	/**
	 * @param sql
	 */
	private void commonIssCredRatingSql(StringBuffer sql) {
		sql.append("SELECT T.* FROM( ");
    	sql.append("SELECT t.COM_UNI_CODE AS comUniCode,t.ORG_UNI_CODE AS orgUniCode,r.CHI_SHORT_NAME AS orgShortName,t.ISS_CRED_LEVEL AS credLevel,t.ISS_CRED_LEVEL_PAR AS credLevelPar, t.RATE_WRIT_DATE AS rateWritDate,t.RATE_PROS_PAR AS rateProsPar, ");
    	sql.append("CASE WHEN t.RATE_PROS_PAR = 1 THEN '正面' WHEN t.RATE_PROS_PAR = 2 THEN '稳定' WHEN t.RATE_PROS_PAR = 3 THEN '观望' WHEN t.RATE_PROS_PAR = 4 THEN '负面' ELSE '' END AS parName,DATE_FORMAT(t.CCXEID,'%Y-%m-%d %H:%i:%s')AS ccxeDate, ");
    	sql.append("t.IS_NEW_RATE AS isNewRate ");
    	sql.append("FROM bond_ccxe.d_bond_iss_cred_chan t,	bond_ccxe.d_pub_org_info_r r ");
    	sql.append("WHERE t.ORG_UNI_CODE = r.ORG_UNI_CODE ");
    	sql.append("AND t.ISVALID = 1 ");
    	sql.append("ORDER BY t.RATE_WRIT_DATE DESC ");
    	sql.append(")T GROUP BY concat(T.comUniCode,'-',T.orgUniCode) ORDER BY T.comUniCode ASC ");
	}
    
    public int getIssCredRatingCount() {
    	StringBuffer countsql = new StringBuffer();
    	countsql.append("SELECT COUNT(1) FROM ( ");
    	commonIssCredRatingSql(countsql);
    	countsql.append(") tt");
		return jdbcTemplate.queryForObject(countsql.toString(), Integer.class);
	}
    
    public IssCredRatingDoc findLastIssCredRating(Long comUniCode, Long orgUniCode){
    	IssCredRatingDoc icrDoc = null;
    	StringBuffer sql = new StringBuffer();
    	sql.append("SELECT ");
    	sql.append("	t.COM_UNI_CODE AS comUniCode, ");
    	sql.append("	t.ORG_UNI_CODE AS orgUniCode, ");
    	sql.append("	r.CHI_SHORT_NAME AS orgShortName, ");
    	sql.append("	t.ISS_CRED_LEVEL AS credLevel, ");
    	sql.append("	t.ISS_CRED_LEVEL_PAR AS credLevelPar, ");
    	sql.append("	t.RATE_WRIT_DATE AS rateWritDate, ");
    	sql.append("	t.RATE_PROS_PAR AS rateProsPar, ");
    	sql.append("	CASE WHEN t.RATE_PROS_PAR = 1 THEN '正面' ");
    	sql.append("  WHEN t.RATE_PROS_PAR = 2 THEN '稳定' ");
    	sql.append("	WHEN t.RATE_PROS_PAR = 3 THEN '观望' ");
    	sql.append("	WHEN t.RATE_PROS_PAR = 4 THEN '负面' ");
    	sql.append("	ELSE '' END AS parName, ");
    	sql.append("  DATE_FORMAT(t.CCXEID,'%Y-%m-%d %H:%i:%s') AS ccxeDate, ");
    	sql.append("	t.IS_NEW_RATE AS isNewRate ");
    	sql.append("FROM bond_ccxe.d_bond_iss_cred_chan t,bond_ccxe.d_pub_org_info_r r ");
    	sql.append("WHERE t.ORG_UNI_CODE = r.ORG_UNI_CODE AND t.COM_UNI_CODE=").append(comUniCode).append(" AND t.ORG_UNI_CODE=").append(orgUniCode).append(" ");
    	sql.append("AND t.ISVALID = 1 AND t.IS_NEW_RATE=1 ORDER BY t.RATE_WRIT_DATE DESC LIMIT 1 ");
    	
		List<IssCredRatingDoc> icrDocs =  jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<>(IssCredRatingDoc.class));
		if (null != icrDocs && icrDocs.size() > 0) {
			 icrDoc = icrDocs.get(0);
		}
    	
    	return icrDoc;
    }
    
    public List<IssPdRatingDoc> findIssPdRating(){
    	StringBuilder sql = new StringBuilder();
    	commonIssPdRatingSql(sql);
    	return jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<>(IssPdRatingDoc.class));
    }
    
    public List<IssPdRatingDoc> findIssPdRating(int index, int limit){
    	StringBuilder sql = new StringBuilder();
    	commonIssPdRatingSql(sql);
    	sql.append("LIMIT ").append(index).append(",").append(limit);
    	return jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<>(IssPdRatingDoc.class));
    }

	private void commonIssPdRatingSql(StringBuilder sql) {
		sql.append("SELECT ");
    	sql.append("	dm_bond.Comp_ID AS compId, ");
    	sql.append("	dm_bond.Comp_Name AS compName, ");
    	sql.append("	t_bond_com_ext.com_uni_code AS comUniCode, ");
    	sql.append("	t_bond_pd_par.id AS pdParNum, ");
    	sql.append("	t_bond_pd_par.pd AS pdPar, ");
    	sql.append("	dm_bond.Rating AS pdRating, ");
    	sql.append("	CONCAT(dm_bond.YEAR,'-',dm_bond.quan_month,'-1')AS worstPdTime, ");
    	sql.append("	dm_bond.last_update_timestamp AS lastUpdateTime  ");
    	sql.append("FROM ");
    	sql.append("	(SELECT b.Comp_ID,b.Comp_Name,b.quan_month,b.year,b.Rating,b.visible,b.last_update_timestamp FROM(SELECT a.Comp_ID,a.Comp_Name,a.quan_month,a.year,a.Rating,a.visible,a.last_update_timestamp FROM  /*amaresun*/ dmdb.dm_bond a ORDER BY a.Comp_ID DESC,a.year DESC,a.quan_month DESC) b GROUP BY b.Comp_ID)AS dm_bond ");
    	sql.append("	INNER JOIN dmdb.t_bond_com_ext AS t_bond_com_ext ON dm_bond.Comp_ID = t_bond_com_ext.ama_com_id ");
    	sql.append("	LEFT JOIN dmdb.t_bond_pd_par AS t_bond_pd_par ON t_bond_pd_par.rating = dm_bond.Rating ");
    	sql.append("WHERE dm_bond.visible = 1 AND dm_bond.Rating IS NOT null ");
	}
    
    public int getIssPdRatingCount() {
    	StringBuilder countsql = new StringBuilder();
    	countsql.append("SELECT COUNT(1) FROM ( ");
    	commonIssPdRatingSql(countsql);
    	countsql.append(") tt");
		return jdbcTemplate.queryForObject(countsql.toString(), Integer.class);
	}
    
    public IssPdRatingDoc findLastIssPdRating(Long compId){
    	IssPdRatingDoc issPdRatingDoc = null;
    	StringBuilder sql = new StringBuilder();
    	sql.append("SELECT ");
    	sql.append("	a.Comp_ID AS compId,a.Comp_Name AS compName, ");
    	sql.append("	b.id AS pdParNum,b.pd AS pdPar, ");
    	sql.append("	a.Rating AS pdRating, 0 AS comUniCode, ");
    	sql.append("	CONCAT(a.YEAR,'-',a.quan_month,'-1')AS worstPdTime, ");
    	sql.append("	a.last_update_timestamp AS lastUpdateTime ");
    	sql.append("FROM  /*amaresun*/ dmdb.dm_bond a LEFT JOIN dmdb.t_bond_pd_par b ON b.rating = a.Rating ");
    	sql.append("WHERE a.visible = 1 AND a.Comp_ID =").append(compId).append(" ");
    	sql.append("ORDER BY a.YEAR DESC,a.quan_month DESC LIMIT 1 ");
    	
    	List<IssPdRatingDoc> issPdRatingDocs = jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<>(IssPdRatingDoc.class));
    	if (null != issPdRatingDocs && issPdRatingDocs.size() > 0) {
    		issPdRatingDoc = issPdRatingDocs.get(0);
		}
    	
    	return issPdRatingDoc;
    }
    
	public List<String> getLastTwodaysInImpliedRatingHist() {
		try {
			String sql = "SELECT a.data_date as dataDate FROM dmdb.t_bond_implied_rating_hist a GROUP BY DATE_FORMAT(a.data_date,'%Y-%m-%d') ORDER BY a.data_date DESC LIMIT 2";

			return jdbcTemplate.queryForList(sql, String.class);
		} catch (Exception ex) {
			logger.error("getLastTwodaysInImpliedRatingHist error," + ex.getMessage(), ex);
		}
		return null;
	}

	public List<ImpliedRatingJson> findImpliedRatingHistData(String beginDate, String endDate) {
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT ");
			sql.append("	mm1.bond_code AS bondUniCode ,mm1.bond_name AS bondName,(mm1.ratingId-mm2.ratingId) AS rateDiff, mm1.implied_rating AS fstImpliedRat,mm2.implied_rating AS secImpliedRat,mm1.issuer_id AS issuerId ");
			sql.append("FROM ");
			sql.append("		( ");
			sql.append("			SELECT ");
			sql.append("				t1.bond_id, ");
			sql.append("				t1.bond_code, ");
			sql.append("				t1.bond_name, ");
			sql.append("				t1.issuer_id, ");
			sql.append("				t1.implied_rating, ");
			sql.append("				p1.id AS ratingId, ");
			sql.append("				p1.pd ");
			sql.append("			FROM ");
			sql.append("				dmdb.t_bond_implied_rating_hist t1 ");
			sql.append("			LEFT JOIN dmdb.t_bond_rating_par p1 ON t1.implied_rating = p1.rating ");
			sql.append("			WHERE ");
			sql.append("				t1.data_date = ? ");
			sql.append("			AND t1.bond_code > 0 ");
			sql.append("			AND t1.implied_rating IS NOT NULL ");
			sql.append("		)mm1, ");
			sql.append("		( ");
			sql.append("			SELECT ");
			sql.append("				t2.bond_id, ");
			sql.append("				t2.bond_code, ");
			sql.append("				t2.bond_name, ");
			sql.append("				t2.issuer_id, ");
			sql.append("				t2.implied_rating, ");
			sql.append("				p2.id AS ratingId, ");
			sql.append("				p2.pd ");
			sql.append("			FROM ");
			sql.append("				dmdb.t_bond_implied_rating_hist t2 ");
			sql.append("			LEFT JOIN dmdb.t_bond_rating_par p2 ON t2.implied_rating = p2.rating ");
			sql.append("			WHERE ");
			sql.append("				t2.data_date = ? ");
			sql.append("			AND t2.bond_code > 0 ");
			sql.append("			AND t2.implied_rating IS NOT NULL ");
			sql.append("		)mm2 ");
			sql.append("	WHERE ");
			sql.append("		mm1.bond_code = mm2.bond_code AND (mm1.ratingId-mm2.ratingId) <> 0");

			return jdbcTemplate.query(sql.toString(), new Object[] {beginDate, endDate}, new BeanPropertyRowMapper<ImpliedRatingJson>(ImpliedRatingJson.class));
		} catch (Exception ex) {
			logger.error("findImpliedRatingHistData error," + ex.getMessage(), ex);
		}

		return null;
	}

	public int getExerDataCount() {
    	StringBuffer countsql = new StringBuffer();
    	countsql.append("SELECT COUNT(1) ");
    	commonExerDataSql(countsql);
		return jdbcTemplate.queryForObject(countsql.toString(), Integer.class);
	}

	private void commonExerDataSql(StringBuffer sql) {
    	sql.append("FROM bond_ccxe.d_bond_resa_redem a ");
    	sql.append("WHERE a.ISVALID = 1 ");
    	sql.append("AND YEAR (a.DECL_DATE) = YEAR (NOW()) ");
    	sql.append("AND a.EXER_PROC_PAR BETWEEN 2 AND 3 ");
    	sql.append("AND a.DECL_DATE >= NOW() ");
    	sql.append("OR a.APPL_START_DATE >= NOW() ");
	}

	public List<BondMaturityJson> findExerData(int index, int limit) {
    	StringBuffer sql = new StringBuffer();
    	sql.append("SELECT  ");
    	sql.append("	a.BOND_UNI_CODE AS bondUniCode,  ");
    	sql.append("	a.DECL_DATE AS declDate, ");
    	sql.append("	a.APPL_START_DATE AS applStartDate, ");
    	sql.append("	a.APPL_END_DATE AS applEndDate ");
    	commonExerDataSql(sql);
    	sql.append("LIMIT ").append(index).append(",").append(limit);
    	return jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<>(BondMaturityJson.class));
	}

	/**
	 * 根据userId, groupId, bondIdList, radarTypeList以及readMsgListIdList，直接分页获取消息记录，按照未读->已读，再按照时间来排序
	 * @param userId
	 * @param groupId
	 * @param bondIdList
	 * @param readMsgIdListList
	 * @param radarTypeList
	 * @param page
	 * @param limit
	 * @param clzz
	 * @param <T>
	 * @return
	 */
	public <T> List<T> getPagedGroupMsgVOList(Integer userId, Integer groupId, List<Long> bondIdList,
											  List<Long> readMsgIdListList, List<Long> radarTypeList,boolean isgrouprepeatmsg,
											  int page, Integer limit, Class<T> clzz) {
		String bondIdStr = bondIdList.isEmpty() ? "''" : StringUtils.join(bondIdList.toArray(), ",");
		String readMsgIdStr = readMsgIdListList.isEmpty() ? "''" : StringUtils.join(readMsgIdListList.toArray(), ",");
		String radarTypeStr = radarTypeList.isEmpty() ? "''" : StringUtils.join(radarTypeList.toArray(), ",");
		Integer start = page * limit;
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT msg.seq_id AS msgId, msg.bond_id AS bondId, msg.msg_content AS msgContent, ");
		sql.append(" msg.event_type AS radarTypeId, msg.type_name AS radarTypeName, msg.news_index AS newsIndex, ");
		sql.append(" msg.important AS important, msg.emotion_tag AS emotionTag, msg.create_time AS createTime, ");
		sql.append(" CASE WHEN msg.seq_id IN (%4$s) THEN '1' WHEN msg.seq_id<=msg.bookmark THEN '1' ELSE '0' END AS readStatus ");
		sql.append(" FROM ");
		sql.append(" (SELECT smsg.*, fav.bookmark, rs.type_name FROM t_bond_notification_msg AS smsg ");
		sql.append(" INNER JOIN t_bond_favorite AS fav ON fav.user_id=%1$d AND fav.group_id=%2$d AND fav.bond_uni_code=smsg.bond_id AND fav.is_delete=0 ");
		sql.append(" INNER JOIN t_bond_favorite_radar_schema AS rs ON rs.id=smsg.event_type AND rs.`status`=1 ");
		sql.append(" WHERE smsg.bond_id IN (%3$s) AND smsg.event_type IN (%5$s) ");
		if (isgrouprepeatmsg) {
			sql.append(" AND smsg.isgrouprepeatmsg = 0 ");
		}
		sql.append(" AND smsg.group_id=%2$d) AS msg ");
		sql.append(" ORDER BY readStatus, create_time DESC, seq_id DESC LIMIT %6$d, %7$d; ");
		
		String formatSql = String.format(sql.toString(), userId, groupId, bondIdStr, readMsgIdStr, radarTypeStr, start, limit);
		return jdbcTemplate.query(formatSql, new BeanPropertyRowMapper<>(clzz));
	}

	/**
	 * 根据userId, groupId, bondIdList以及radarTypeList，获取消息总数
	 * @param groupId
	 * @param bondIdList
	 * @param radarTypeList
	 * @return
	 */
	public Long getCountOfPagedGroupMsgVOList(Integer groupId, List<Long> bondIdList,  List<Long> radarTypeList) {
		String bondIdStr = bondIdList.isEmpty() ? "''" : StringUtils.join(bondIdList.toArray(), ",");
		String radarTypeStr = radarTypeList.isEmpty() ? "''" : StringUtils.join(radarTypeList.toArray(), ",");
		String sql = "SELECT COUNT(1) FROM t_bond_notification_msg AS msg" +
				" WHERE msg.group_id=%1$d AND msg.bond_id IN (%2$s) AND msg.event_type IN (%3$s) AND msg.isgrouprepeatmsg=0";
		String formatSql = String.format(sql, groupId, bondIdStr, radarTypeStr);
		return jdbcTemplate.queryForObject(formatSql, Long.class);
	}

	/**
	 * 根据雷达获取用户所有未读消息
	 *
	 * @return
	 */
	public <T> List<T> getAllFavoriteMsg(Integer userId, List<Long> readMsgIdList, List<Long> groupIdList, Class<T> clzz) {
		String groupIdStr = groupIdList.isEmpty() ? "''" : StringUtils.join(groupIdList.toArray(), ",");
		String sql = "SELECT COUNT(1) AS radarMsgNumber, msg.event_type AS radarTypeId" +
				" FROM t_bond_notification_msg AS msg" +
				" INNER JOIN t_bond_favorite AS fav ON msg.bond_id=fav.bond_uni_code AND msg.group_id=fav.group_id" +
				" WHERE fav.user_id=%1$d AND fav.is_delete=0 AND msg.seq_id NOT IN (%2$s) AND msg.group_id IN (%3$s)" +
				" AND msg.seq_id > fav.bookmark GROUP BY event_type";
		String readMsgIdStr = readMsgIdList.isEmpty() ? "''" : StringUtils.join(readMsgIdList.toArray(), ",");
		String formatSql = String.format(sql, userId, readMsgIdStr, groupIdStr);
		return jdbcTemplate.query(formatSql, new BeanPropertyRowMapper<>(clzz));
	}

	/**
	 * 获取舆情查看标记
	 */
	public Long getUserSentimentReadCount(Integer userId, Integer newsIndex) {
		String sql = "SELECT COUNT(1) from t_bond_user_sentiment_msg_read WHERE user_id = %1$d AND news_index = %2$d";
		String formatSql = String.format(sql, userId, newsIndex);
		return jdbcTemplate.queryForObject(formatSql, Long.class);
	}

	public Long getMsgCount(List<Long> groupIdList, List<Long> bondIdList, List<Long> radarTypeList) {
		String groupIdStr = groupIdList.isEmpty() ? "''" : StringUtils.join(groupIdList.toArray(), ",");
		String bondIdStr = bondIdList.isEmpty() ? "''" : StringUtils.join(bondIdList.toArray(), ",");
		String radarTypeStr = radarTypeList.isEmpty() ? "''" : StringUtils.join(radarTypeList.toArray(), ",");
		String sql = "SELECT COUNT(1) FROM t_bond_notification_msg AS msg" +
				" WHERE msg.group_id IN (%1$s) AND msg.bond_id IN (%2$s) AND msg.event_type IN (%3$s)";
		String formatSql = String.format(sql, groupIdStr, bondIdStr, radarTypeStr);
		return jdbcTemplate.queryForObject(formatSql, Long.class);
	}

	/**
	 * 获取用户所有未读消息
	 *
	 * @return
	 */
	public <T> List<T> getAllFavoriteUnreadMsg(Integer userId, List<Long> readMsgIdList, Integer page, Integer limit, Class<T> clzz) {
		String readMsgIdStr = readMsgIdList.isEmpty() ? "''" : StringUtils.join(readMsgIdList.toArray(), ",");
		Integer start = page * limit;
		String sql = "SELECT msg.seq_id AS msgId, msg.bond_id AS bondId,detail.bond_short_name AS bondName, msg.msg_content AS msgContent," +
				" rs.id AS radarTypeId, rs.type_name AS radarTypeName, msg.news_index AS newsIndex," +
				" msg.important AS important, msg.emotion_tag AS emotionTag, msg.create_time AS createTime" +
				" FROM t_bond_notification_msg AS msg" +
				" INNER JOIN t_bond_favorite AS fav ON fav.bond_uni_code=msg.bond_id AND msg.group_id=fav.group_id" +
				" LEFT JOIN t_bond_favorite_radar_schema AS rs ON rs.id=msg.event_type" +
				" LEFT JOIN t_bond_basic_info AS detail ON detail.bond_uni_code=msg.bond_id" +
				" WHERE msg.seq_id NOT IN (%2$s) AND fav.user_id=%1$d AND fav.is_delete=0 AND msg.seq_id > fav.bookmark" +
				" ORDER BY msg.create_time DESC LIMIT %3$d, %4$d;";
		String formatSql = String.format(sql, userId, readMsgIdStr, start, limit);
		return jdbcTemplate.query(formatSql, new BeanPropertyRowMapper<>(clzz));
	}

	/**
	 * 获取用户所有未读消息ID
	 *
	 * @return
	 */
	public List<Long> getSketchyNewMsgIdListByUserId(Integer userId, List<Long> groupIdList) {
		String groupIdStr = groupIdList.isEmpty() ? "''" : StringUtils.join(groupIdList.toArray(), ",");
		String sql = "SELECT msg.seq_id FROM t_bond_notification_msg AS msg" +
				" INNER JOIN t_bond_favorite AS fav ON fav.bond_uni_code=msg.bond_id AND msg.group_id=fav.group_id" +
				" WHERE fav.user_id=%1$d AND fav.is_delete=0 AND msg.group_id IN (%2$s) AND msg.seq_id>fav.bookmark;";
		String finalSql = String.format(sql, userId, groupIdStr);
		return jdbcTemplate.queryForList(finalSql, Long.class);
	}

	/**
	 * 获取用户所有未读消息
	 *
	 * @return
	 */
	public <T> List<T> getAllFavoriteUnreadMsgSimple(Integer userId, List<Long> readMsgIdList, List<Long> radarTypeList, Class<T> clzz) {
		String radarSql = "";
		if (radarTypeList != null) {
			String radarTypeStr = radarTypeList.isEmpty() ? "''" : StringUtils.join(radarTypeList.toArray(), ",");
			radarSql = " AND msg.event_type IN (" + radarTypeStr + ") ";
		}
		String readMsgIdStr = readMsgIdList.isEmpty() ? "''" : StringUtils.join(readMsgIdList.toArray(), ",");
		String sql = "SELECT msg.seq_id AS msgId, msg.bond_id AS bondId" +
				" FROM t_bond_notification_msg AS msg" +
				" INNER JOIN t_bond_favorite AS fav ON fav.bond_uni_code=msg.bond_id AND msg.group_id=fav.group_id" +
				" WHERE msg.seq_id NOT IN (%2$s) AND fav.user_id=%1$d " +
				" AND fav.is_delete=0" + radarSql +" AND msg.seq_id > fav.bookmark;";
		String formatSql = String.format(sql, userId, readMsgIdStr);
		return jdbcTemplate.query(formatSql, new BeanPropertyRowMapper<>(clzz));
	}

	public List<Long> getToBeRemovedReadCacheMsgIdList(List<Long> msgIdList) {
		String readMsgIdStr = msgIdList.isEmpty() ? "''" : StringUtils.join(msgIdList.toArray(), ",");
		String sql = "SELECT msg.seq_id FROM t_bond_notification_msg AS msg " +
				"INNER JOIN t_bond_favorite AS fav ON fav.bond_uni_code=msg.bond_id AND fav.group_id=msg.group_id " +
				"AND fav.is_delete=0 AND fav.bookmark>=msg.seq_id " +
				"WHERE msg.seq_id IN (%1$s) AND msg.group_id>0";
		String formatSql = String.format(sql, readMsgIdStr);
		return jdbcTemplate.queryForList(formatSql, Long.class);
	}

	public int deleteByMsgIdList(List<Long> msgIdList) {
		String msgIdListStr = msgIdList.isEmpty() ? "''" : StringUtils.join(msgIdList.toArray(), ",");
		String sql = "DELETE FROM t_bond_notification_msg WHERE seq_id IN (%1$s);";
		String formatSql = String.format(sql, msgIdListStr);
		return jdbcTemplate.update(formatSql);
	}

	public List<Long> getMsgListWithInvalidGroupId() {
		String sql = "SELECT seq_id FROM t_bond_notification_msg AS msg WHERE group_id=0";
		return jdbcTemplate.queryForList(sql, Long.class);
	}
}
