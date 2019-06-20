package com.innodealing.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.innodealing.engine.mongo.bond.BondBasicInfoRepository;
import com.innodealing.engine.mongo.bond.BondDetailRepository;
import com.innodealing.model.BondCcxeBasicInfo;
@Service
public class BondBasicDataService {

	private static final Logger LOG = LoggerFactory.getLogger(BondBasicDataService.class);

	protected @Autowired JdbcTemplate jdbcTemplate;

	@Autowired
	private BondDetailRepository detailRepository;

	@Autowired 
	private BondBasicInfoRepository basicInfolRepository;

	@Autowired 
	private BondBasicDataConstructHandler basicDataConstructHandler;

	@Autowired
	BondComDataService comDataService;
	
	final String sqlJoinBondImpliedRating = "\r\n  ) t  LEFT JOIN  dmdb.t_bond_implied_rating bondrating on bondrating.bond_code = t.BOND_UNI_CODE ";
	
	class TaskPar {
		protected int index;
		protected long startTime ;
	}
	class Parameter extends TaskPar {
		Parameter(int i, String sql) {
			index = i ; this.data = sql; this.startTime = System.currentTimeMillis();
		}
		public String data;
	}
	class Result extends TaskPar {
		Result(int i, String result, long startTime) {
			index = i ; this.data = result; this.startTime = startTime;
		}
		public String data;
	}
	
	final String sqlJoinCashFlow = "	LEFT JOIN (\r\n" + 
			"	    SELECT sum(T.PAY_AMUT) AS PAY_AMUT, T.bond_uni_code\r\n" + 
			"        from (\r\n" + 
			"			  SELECT *\r\n" + 
			"	        FROM bond_ccxe.d_bond_cash_flow_chart F  \r\n" + 
			"	        WHERE F.isvalid = 1 AND CONCAT(CURDATE(), ' 00:00:00') <= F.INTE_END_DATE order by INTE_PERI asc \r\n" + 
			"        ) T\r\n" + 
			"        group by T.bond_uni_code\r\n" + 
			"	) AS PAY ON BASIC.BOND_UNI_CODE = PAY.bond_uni_code ";
	
	final String sqlJoinBondCred = 	
			"	LEFT JOIN   \r\n" + 
					"			 			(  \r\n" + 
					"select * from \r\n" + 
					"(\r\n" + 
					"			select  rate_writ_date, bond_uni_code, bond_cred_level, RATE_PROS_PAR AS rate_pros , R.CHI_SHORT_NAME as bond_rate_org_name, R.ORG_UNI_CODE as cred_org_uni_code \r\n" + 
					"					FROM bond_ccxe.d_bond_cred_chan C\r\n" + 
					"					left join bond_ccxe.d_pub_org_info_r R on C.ORG_UNI_CODE = R.ORG_UNI_CODE\r\n" + 
					"					order BY bond_uni_code, rate_writ_date desc" + 
					") AS BOND_CRED \r\n" + 
					"group by bond_uni_code" +
					"			 			) AS RATING ON BASIC.BOND_UNI_CODE = RATING.bond_uni_code  \r\n" ;

	final String sqlJoinBondCity = " \r\n LEFT JOIN dmdb.t_bond_com_ext t1 ON t1.com_uni_code = BASIC.ORG_UNI_CODE"
			+ " \r\n LEFT JOIN dmdb.t_bond_city_annex  t2 ON t2.com_uni_code  = t1.com_uni_code \r\n";

	final String sqlJoinBondPumComInfo = " \r\n LEFT JOIN bond_ccxe.d_pub_com_info_2 q1 ON q1.COM_UNI_CODE = BASIC.ORG_UNI_CODE";
	
	final String sqlJoinBondInfo = " \r\n LEFT JOIN innodealing.bond_info bondinfo ON bondinfo.bond_code = BASIC.BOND_UNI_CODE";
	
	final String sql = "SELECT t.*,bondrating.implied_rating AS impliedRating  FROM (";

	final String sqlFundatmentalIndicator = "		 LEFT JOIN  \r\n" + 
			"	  (  \r\n" + 
			"	      select * from dmdb.t_bond_duration   where  duration_date = ( select max(data_date) from innodealing.bond_info )\r\n" + 
			"	  ) as DU on DU.bond_uni_code = BASIC.BOND_UNI_CODE\r\n" + 
			"	    LEFT JOIN  \r\n" + 
			"	  (  \r\n" + 
			"	      select * from dmdb.t_bond_conv_ratio   where  create_date = ( select max(create_date) from t_bond_conv_ratio )\r\n" + 
			"	  ) AS CV on CV.bond_uni_code = BASIC.BOND_UNI_CODE\r\n" + 
			"   	LEFT JOIN\r\n" + 
			"   	(\r\n" + 
			"   	   select * from dmdb.t_bond_spread   where  update_date = ( select max(data_date) from innodealing.bond_info )\r\n" + 
			"   	) as SP on SP.bond_uni_code = BASIC.BOND_UNI_CODE";
	
	final String sqlValidBond = 
			"			WHERE BASIC.ISVALID = 1  \r\n" + 
					"						    	group by BASIC.BOND_UNI_CODE";

	final String sqlBond0 =  sql + "SELECT BASIC.BOND_UNI_CODE, BASIC.BOND_CODE, BASIC.BOND_SHORT_NAME, BASIC.BOND_FULL_NAME, BASIC.PLEDGE_NAME, BASIC.PLEDGE_CODE, BASIC.ORG_UNI_CODE as COM_UNI_CODE, BASIC.ISS_NAME, BASIC.BOND_MATU, BASIC.MATU_UNIT_PAR, BASIC.BOND_TYPE_PAR, BASIC.INTE_START_DATE, BASIC.THEO_END_DATE, BASIC.ISS_COUP_RATE, BASIC.NEW_COUP_RATE, BASIC.INTE_PAY_CLS_PAR, q1.COM_PRO as comPro, q1.MAIN_BUS as mainBus, q1.SID_BUS as sidBus, \r\n" + 
			"			  BASIC.INTE_PAY_FREQ, BASIC.GURA_NAME, BASIC.BASE_RATE_PAR, (CASE WHEN  t2.com_uni_code IS NULL THEN 0 ELSE 1 END ) AS IS_TYPE_PAR, BASIC.RATE_TYPE_PAR, BASIC.MATU_PAY_DATE, BASIC.EXER_PAY_DATE, ISS_INFO.SEC_MAR_PAR, ISS_INFO.IS_CROS_MAR_PAR, ISS_INFO.CROS_MAR_DES, RATING.rate_pros, RATING.bond_cred_level, BASIC.CURR_STATUS , (CASE ISS_INFO.ISS_STA_PAR WHEN 1 THEN 1 WHEN 4 THEN 1 ELSE 0 END ) as ISS_STA_PAR, ISS_INFO.LIST_STA_PAR, " + 
			"	          BASIC.YEAR_PAY_DATE, bondinfo.RATE AS fairValue, ISS_INFO.new_size as newSize, ISS_INFO.GURA_NAME1 as guraName1, BASIC.IS_REDEM_PAR as isRedemPar, BASIC.IS_RESA_PAR as isResaPar, ISS_INFO.ISS_CLS as ISS_CLS, RATING.rate_writ_date as bond_rate_writ_date, RATING.bond_rate_org_name, BASIC.BOND_PAR_VAL, ISS_INFO.ISS_PRI, FORMAT(PAY.PAY_AMUT, 4) as PAY_AMOUNT, \r\n" + 
			"             DU.macd, DU.modd, DU.convexity, CV.conv_ratio, SP.static_spread, bondinfo.exercise_yield as optionYield, bondinfo.net_valuation as estCleanPrice,q1.IS_LIST_PAR as listPar, ISS_INFO.iss_start_date as issStartDate, ISS_INFO.iss_end_date as issEndDate, ISS_INFO.pay_start_date as payStartDate, ISS_INFO.pay_end_date as payEndDate, BASIC.year_pay_matu AS yearPayMatu, RATING.cred_org_uni_code as credOrgUniCode, \r\n " + 
			"             BASIC.RATE_DES as rateDes, BASIC.BAS_SPR as basSpr, ISS_INFO.LIST_DATE as listDate, ISS_INFO.list_decl_date as listDeclDate, ISS_INFO.coll_cap_purp as collCapPurp, ISS_INFO.unde_name as undeName, ISS_INFO.unde_cls_par as undeClsPar, ISS_INFO.bid_date as bidDate, ISS_INFO.ISS_FEE_RATE as issFeeRate, ISS_INFO.bokep_date as bokepDate, ISS_INFO.debt_reg_date as debtRegDate, ISS_INFO.subs_unit as subsUnit, \r\n" + 
			"             ISS_INFO.least_subs_unit as leastSubsUnit , ISS_INFO.cury_type_par as curyTypePar, ISS_INFO.is_list_par as isListPar, ISS_INFO.trade_unit  as tradeUnit, ISS_INFO.circu_amut as circuAmut, ISS_INFO.list_sect_par as listSectPar, ISS_INFO.theo_delist_date AS theoDelistDate, ISS_INFO.last_trade_date as lastTradeDate, ISS_INFO.actu_delist_date as actuDelistDate, \r\n" +
			"             BASIC.spe_short_name as speShortName, BASIC.eng_full_name AS engFullName, BASIC.eng_short_name as engShortName, BASIC.isin_code AS isinCode, BASIC.curr_status as currStatus, BASIC.bond_par_val as bondParVal, "
			+ " BASIC.bond_form_par AS bondFormPar, BASIC.actu_end_date AS actuEndDate, BASIC.inte_des AS inteDes, BASIC.ref_yield as refYield, BASIC.init_base_rate_date AS initBaseRateDate, BASIC.base_rate as baseRate, BASIC.opt_extra_spr AS optExtraSpr,  \r\n" +
			"             BASIC.extra_spr_seq_num, BASIC.rate_ceil, BASIC.rate_floor, BASIC.inte_calcu_cls_par, BASIC.is_segm_par, BASIC.inte_pay_meth, BASIC.simp_comp_inte_par, BASIC.repay_cls_pay_par, BASIC.pay_fee_rate, BASIC.pay_matu, BASIC.pay_cls_des, BASIC.opt_des, BASIC.is_guar_par, BASIC.is_repu_par, BASIC.is_hedge_par, BASIC.is_tax_free_par, BASIC.is_type_par as orgIsTypePar, ISS_INFO.ISS_STA_PAR as orgIssStaPar \r\n" +
			"			FROM bond_ccxe.d_bond_basic_info_1 AS BASIC  \r\n" + 
			"           LEFT JOIN bond_ccxe.D_BOND_ISS_INFO_1 AS ISS_INFO ON ISS_INFO.bond_uni_code = BASIC.bond_uni_code\r\n" + 
			sqlJoinBondCity  + sqlJoinBondCred + sqlJoinCashFlow + sqlJoinBondPumComInfo + sqlJoinBondInfo + sqlFundatmentalIndicator + sqlValidBond;

	final String sqlBond1 = sql + "SELECT BASIC.BOND_UNI_CODE, BASIC.BOND_CODE, BASIC.BOND_SHORT_NAME, BASIC.BOND_FULL_NAME, BASIC.PLEDGE_NAME, BASIC.PLEDGE_CODE, BASIC.ORG_UNI_CODE AS COM_UNI_CODE, null as ISS_NAME, BASIC.BOND_MATU, BASIC.MATU_UNIT_PAR, 6 as BOND_TYPE_PAR, BASIC.INTE_START_DATE, BASIC.THEO_END_DATE, BASIC.ISS_COUP_RATE, BASIC.NEW_COUP_RATE, BASIC.INTE_PAY_CLS_PAR, q1.COM_PRO as comPro, q1.MAIN_BUS as mainBus, q1.SID_BUS as sidBus,  \r\n" + 
			"    			 	BASIC.INTE_PAY_FREQ, BASIC.GURA_NAME, null as BASE_RATE_PAR, (CASE WHEN  t2.com_uni_code IS NULL THEN 0 ELSE 1 END ) AS IS_TYPE_PAR, BASIC.RATE_TYPE_PAR, BASIC.MATU_PAY_DATE, BASIC.EXER_PAY_DATE, ISS_INFO.SEC_MAR_PAR, null as IS_CROS_MAR_PAR,  null as CROS_MAR_DES, RATING.rate_pros, RATING.bond_cred_level, BASIC.CURR_STATUS , (CASE ISS_INFO.LIST_STA_PAR WHEN 1 THEN 1 WHEN 4 THEN 1 ELSE 0 END ) as ISS_STA_PAR , ISS_INFO.LIST_STA_PAR, " + 
			"                   BASIC.YEAR_PAY_DATE,bondinfo.RATE AS fairValue, ISS_INFO.new_size as newSize, ISS_INFO.GURA_NAME1 as guraName1, BASIC.IS_REDEM_PAR as isRedemPar,BASIC.IS_RESA_PAR as isResaPar, ISS_INFO.ISS_CLS as ISS_CLS, RATING.rate_writ_date as bond_rate_writ_date, RATING.bond_rate_org_name, BASIC.BOND_PAR_VAL, ISS_INFO.ISS_PRI, FORMAT(PAY.PAY_AMUT, 4) as PAY_AMOUNT, \r\n" + 
			"					DU.macd, DU.modd, DU.convexity, CV.conv_ratio, SP.static_spread, bondinfo.exercise_yield as optionYield, bondinfo.net_valuation as estCleanPrice,q1.IS_LIST_PAR as listPar, ISS_INFO.iss_start_date as issStartDate, ISS_INFO.iss_end_date as issEndDate, ISS_INFO.pay_start_date as payStartDate, ISS_INFO.pay_end_date as payEndDate, BASIC.year_pay_matu AS yearPayMatu, RATING.cred_org_uni_code as credOrgUniCode,  \r\n" +
			"               null as rateDes, null as basSpr, ISS_INFO.LIST_DATE as listDate, ISS_INFO.list_decl_date as listDeclDate, ISS_INFO.coll_cap_purp as collCapPurp, ISS_INFO.unde_name as undeName, ISS_INFO.unde_cls_par as undeClsPar, null as bidDate,  ISS_INFO.ISS_FEE_RATE as issFeeRate, null as bokepDate, ISS_INFO.debt_reg_date as debtRegDate, null as subsUnit,  \r\n" + 
			"             null as leastSubsUnit , null as curyTypePar, null as isListPar, null as tradeUnit, ISS_INFO.circu_amut as circuAmut, ISS_INFO.list_sect_par as listSectPar, ISS_INFO.theo_delist_date AS theoDelistDate, ISS_INFO.last_trade_date as lastTradeDate, ISS_INFO.actu_delist_date as actuDelistDate, \r\n" +
			"             BASIC.spe_short_name as speShortName, BASIC.eng_full_name AS engFullName, BASIC.eng_short_name as engShortName, BASIC.isin_code AS isinCode, BASIC.curr_status as currStatus, BASIC.bond_par_val as bondParVal, BASIC.bond_form_par AS bondFormPar, BASIC.actu_end_date AS actuEndDate, BASIC.inte_des AS inteDes, null as refYield, null as initBaseRateDate, null as baseRate, null AS optExtraSpr,  \r\n" +
			"             null as extra_spr_seq_num, null as rate_ceil, null as rate_floor, BASIC.inte_calcu_cls_par, BASIC.is_segm_par, BASIC.inte_pay_meth, BASIC.simp_comp_inte_par, BASIC.repay_cls_pay_par, null as pay_fee_rate, BASIC.pay_matu, BASIC.pay_cls_des, BASIC.opt_des, BASIC.is_guar_par, BASIC.is_repu_par, null as is_hedge_par, null as is_tax_free_par, null as orgIsTypePar, null as orgIssStaPar \r\n" + 
			"    			FROM bond_ccxe.d_bond_basic_info_2 AS BASIC\r\n" + 
			"    			LEFT JOIN bond_ccxe.D_BOND_ISS_INFO_2 AS ISS_INFO ON ISS_INFO.bond_uni_code = BASIC.bond_uni_code\r\n" + 
			sqlJoinBondCity  + sqlJoinBondCred + sqlJoinCashFlow + sqlJoinBondPumComInfo + sqlJoinBondInfo + sqlFundatmentalIndicator + sqlValidBond;

	final String sqlBond2 = sql + "SELECT BASIC.BOND_UNI_CODE, BASIC.BOND_CODE, BASIC.BOND_SHORT_NAME, BASIC.BOND_FULL_NAME, null as PLEDGE_NAME, null as PLEDGE_CODE, BASIC.ORG_UNI_CODE AS COM_UNI_CODE, null as ISS_NAME, BASIC.BOND_MATU, BASIC.MATU_UNIT_PAR, BASIC.BOND_TYPE_PAR, BASIC.INTE_START_DATE, BASIC.THEO_END_DATE, BASIC.ISS_COUP_RATE, BASIC.NEW_COUP_RATE, BASIC.INTE_PAY_CLS_PAR, q1.COM_PRO as comPro, q1.MAIN_BUS as mainBus, q1.SID_BUS as sidBus,  \r\n" + 
			"    			   BASIC.INTE_PAY_FREQ, BASIC.GURA_NAME, BASIC.BASE_RATE_PAR, null IS_TYPE_PAR, BASIC.RATE_TYPE_PAR, BASIC.MATU_PAY_DATE, BASIC.EXER_PAY_DATE, ISS_INFO.SEC_MAR_PAR, ISS_INFO.IS_CROS_MAR_PAR,  null as CROS_MAR_DES, RATING.rate_pros, RATING.bond_cred_level, BASIC.CURR_STATUS , (CASE ISS_INFO.ISS_STA_PAR WHEN 1 THEN 1 WHEN 4 THEN 1 ELSE 0 END ) as ISS_STA_PAR , ISS_INFO.LIST_STA_PAR, BASIC.YEAR_PAY_DATE, bondinfo.RATE AS fairValue, " + 
			"                  ISS_INFO.new_size as newSize,null  as guraName1,BASIC.IS_REDEM_PAR as isRedemPar,BASIC.IS_RESA_PAR as isResaPar, ISS_INFO.ISS_CLS as ISS_CLS, RATING.rate_writ_date as bond_rate_writ_date, RATING.bond_rate_org_name, BASIC.BOND_PAR_VAL, ISS_INFO.ISS_PRI, FORMAT(PAY.PAY_AMUT, 4) as PAY_AMOUNT, \r\n" +
			" 					DU.macd, DU.modd, DU.convexity, CV.conv_ratio, SP.static_spread, bondinfo.exercise_yield as optionYield, bondinfo.net_valuation as estCleanPrice,q1.IS_LIST_PAR as listPar, ISS_INFO.iss_start_date as issStartDate, ISS_INFO.iss_end_date as issEndDate, ISS_INFO.pay_start_date as payStartDate, ISS_INFO.pay_end_date as payEndDate, BASIC.year_pay_matu AS yearPayMatu, RATING.cred_org_uni_code as credOrgUniCode, \r\n " + 
			"               BASIC.RATE_DES as rateDes, BASIC.BAS_SPR as basSpr, ISS_INFO.LIST_DATE as listDate, ISS_INFO.list_decl_date as listDeclDate, ISS_INFO.coll_cap_purp as collCapPurp, ISS_INFO.unde_name as undeName, ISS_INFO.unde_cls_par as undeClsPar, ISS_INFO.bid_date as bidDate, ISS_INFO.ISS_FEE_RATE as issFeeRate, ISS_INFO.bokep_date as bokepDate, ISS_INFO.debt_reg_date as debtRegDate, ISS_INFO.subs_unit as subsUnit ,  \r\n" + 
			"             ISS_INFO.least_subs_unit as leastSubsUnit, null as curyTypePar, ISS_INFO.is_list_par as isListPar, null as tradeUnit, ISS_INFO.circu_amut as circuAmut, ISS_INFO.list_sect_par as listSectPar, ISS_INFO.theo_delist_date AS theoDelistDate, null as lastTradeDate, ISS_INFO.actu_delist_date as actuDelistDate , \r\n" + 
			"             BASIC.spe_short_name as speShortName, null AS engFullName, null AS engShortName, BASIC.isin_code AS isinCode, BASIC.curr_status as currStatus, BASIC.bond_par_val as bondParVal, BASIC.bond_form_par AS bondFormPar, BASIC.actu_end_date AS actuEndDate, BASIC.inte_des AS inteDes, BASIC.ref_yield as refYield, BASIC.init_base_rate_date AS initBaseRateDate, BASIC.base_rate as baseRate, null AS optExtraSpr,  \r\n" +
			"             null as extra_spr_seq_num, BASIC.rate_ceil, BASIC.rate_floor, BASIC.inte_calcu_cls_par, BASIC.is_segm_par, BASIC.inte_pay_meth, BASIC.simp_comp_inte_par, BASIC.repay_cls_pay_par, null as pay_fee_rate, BASIC.pay_matu, BASIC.pay_cls_des, BASIC.opt_des, BASIC.is_guar_par, null as is_repu_par, null as is_hedge_par, null as is_tax_free_par, null as orgIsTypePar, ISS_INFO.ISS_STA_PAR as orgIssStaPar \r\n" + 
			"    			FROM bond_ccxe.d_bond_basic_info_3 AS BASIC\r\n" + 
			"    			LEFT JOIN bond_ccxe.D_BOND_ISS_INFO_3 AS ISS_INFO ON ISS_INFO.bond_uni_code = BASIC.bond_uni_code\r\n" + 
			sqlJoinBondCity + sqlJoinBondCred + sqlJoinCashFlow + sqlJoinBondPumComInfo + sqlJoinBondInfo + sqlFundatmentalIndicator + sqlValidBond ;

	final String sqlBond3 = sql + "SELECT BASIC.BOND_UNI_CODE, BASIC.BOND_CODE, BASIC.BOND_SHORT_NAME, BASIC.BOND_FULL_NAME, null as PLEDGE_NAME, null as PLEDGE_CODE, BASIC.ORG_UNI_CODE AS COM_UNI_CODE, BASIC.ISS_NAME, BASIC.BOND_MATU, BASIC.MATU_UNIT_PAR, BASIC.BOND_TYPE_PAR, BASIC.INTE_START_DATE, BASIC.THEO_END_DATE, BASIC.ISS_COUP_RATE, BASIC.NEW_COUP_RATE, BASIC.INTE_PAY_CLS_PAR, q1.COM_PRO as comPro, q1.MAIN_BUS as mainBus, q1.SID_BUS as sidBus,  \r\n" + 
			"    			  BASIC.INTE_PAY_FREQ, BASIC.GURA_NAME, BASIC.BASE_RATE_PAR, null as IS_TYPE_PAR, BASIC.RATE_TYPE_PAR, BASIC.MATU_PAY_DATE, BASIC.EXER_PAY_DATE, BASIC.SEC_MAR_PAR, BASIC.IS_CROS_MAR_PAR, null as CROS_MAR_DES, RATING.rate_pros, RATING.bond_cred_level, BASIC.CURR_STATUS , 1 AS ISS_STA_PAR, BASIC.LIST_STA_PAR, BASIC.YEAR_PAY_DATE, bondinfo.RATE AS fairValue, BASIC.NEW_SIZE as newSize, NULL as guraName1, BASIC.IS_REDEM_PAR as isRedemPar, " + 
			"                 BASIC.IS_RESA_PAR as isResaPar, BASIC.ISS_CLS, RATING.rate_writ_date as bond_rate_writ_date, RATING.bond_rate_org_name, BASIC.BOND_PAR_VAL, BASIC.ISS_PRI, FORMAT(PAY.PAY_AMUT, 4) as PAY_AMOUNT,  \r\n" +
			" 					DU.macd, DU.modd, DU.convexity, CV.conv_ratio, SP.static_spread, bondinfo.exercise_yield as optionYield, bondinfo.net_valuation as estCleanPrice,q1.IS_LIST_PAR as listPar, BASIC.iss_start_date as issStartDate, BASIC.iss_end_date as issEndDate, BASIC.pay_start_date as payStartDate, BASIC.pay_end_date as payEndDate, BASIC.year_pay_matu AS yearPayMatu, RATING.cred_org_uni_code as credOrgUniCode, \r\n " +
			"             BASIC.RATE_DES as rateDes, BASIC.BAS_SPR as basSpr, BASIC.LIST_DATE as listDate, BASIC.list_decl_date as listDeclDate, BASIC.coll_cap_purp as collCapPurp, BASIC.unde_name as undeName, BASIC.unde_cls_par as undeClsPar, null as bidDate, null as issFeeRate, null as bokepDate, BASIC.debt_reg_date as debtRegDate, null as subsUnit,  \r\n" + 
			"             null as leastSubsUnit, BASIC.cury_type_par as curyTypePar, BASIC.is_list_par as isListPar, null  as tradeUnit , BASIC.circu_amut as circuAmut, BASIC.list_sect_par as listSectPar, BASIC.theo_delist_date AS theoDelistDate, null as lastTradeDate, BASIC.actu_delist_date as actuDelistDate, \r\n" + 
			"             BASIC.spe_short_name as speShortName, null AS engFullName, null as engShortName, BASIC.isin_code AS isinCode, BASIC.curr_status as currStatus, BASIC.bond_par_val as bondParVal, BASIC.bond_form_par AS bondFormPar, BASIC.actu_end_date AS actuEndDate, null AS inteDes, null as refYield, null AS initBaseRateDate, BASIC.base_rate as baseRate, BASIC.opt_extra_spr AS optExtraSpr,  \r\n" +
			"             BASIC.extra_spr_seq_num, null as rate_ceil, null as rate_floor, BASIC.inte_calcu_cls_par, BASIC.is_segm_par, BASIC.inte_pay_meth, BASIC.simp_comp_inte_par, BASIC.repay_cls_pay_par, null as pay_fee_rate, BASIC.pay_matu, BASIC.pay_cls_des, BASIC.opt_des, BASIC.is_guar_par, null as is_repu_par, null as is_hedge_par, null as is_tax_free_par, null as orgIsTypePar, BASIC.ISS_STA_PAR as orgIssStaPar \r\n" + 
			"    			FROM bond_ccxe.d_bond_basic_info_4 AS BASIC\r\n"   +
			sqlJoinBondCity + sqlJoinBondCred + sqlJoinCashFlow + sqlJoinBondPumComInfo + sqlJoinBondInfo + sqlFundatmentalIndicator + sqlValidBond;

	final String sqlBond4 = sql + "SELECT BASIC.BOND_UNI_CODE, BASIC.BOND_CODE, BASIC.BOND_SHORT_NAME, BASIC.BOND_FULL_NAME, BASIC.PLEDGE_NAME, BASIC.PLEDGE_CODE, BASIC.ORG_UNI_CODE AS COM_UNI_CODE, null as ISS_NAME, BASIC.BOND_MATU, BASIC.MATU_UNIT_PAR, BASIC.BOND_TYPE_PAR, BASIC.INTE_START_DATE, BASIC.THEO_END_DATE, BASIC.ISS_COUP_RATE, BASIC.NEW_COUP_RATE, BASIC.INTE_PAY_CLS_PAR, q1.COM_PRO as comPro, q1.MAIN_BUS as mainBus, q1.SID_BUS as sidBus,  \r\n" + 
			"    			 	BASIC.INTE_PAY_FREQ, null as GURA_NAME, BASIC.BASE_RATE_PAR, (CASE WHEN  t2.com_uni_code IS NULL THEN 0 ELSE 1 END ) AS IS_TYPE_PAR, BASIC.RATE_TYPE_PAR, BASIC.MATU_PAY_DATE, BASIC.EXER_PAY_DATE, ISS_INFO.SEC_MAR_PAR, ISS_INFO.IS_CROS_MAR_PAR, ISS_INFO.CROS_MAR_DES, RATING.rate_pros, RATING.bond_cred_level, BASIC.CURR_STATUS , (CASE ISS_INFO.ISS_STA_PAR WHEN  1 THEN 1 WHEN 4 THEN 1 ELSE 0 END ) AS ISS_STA_PAR, ISS_INFO.LIST_STA_PAR, " + 
			"                   BASIC.YEAR_PAY_DATE, bondinfo.RATE AS fairValue, ISS_INFO.new_size as newSize, null as guraName1, BASIC.IS_REDEM_PAR as isRedemPar, BASIC.IS_RESA_PAR as isResaPar, ISS_INFO.ISS_CLS as ISS_CLS, RATING.rate_writ_date as bond_rate_writ_date, RATING.bond_rate_org_name, BASIC.BOND_PAR_VAL, ISS_INFO.ISS_PRI, FORMAT(PAY.PAY_AMUT, 4) as PAY_AMOUNT, \r\n" +
			" 					DU.macd, DU.modd, DU.convexity, CV.conv_ratio, SP.static_spread, bondinfo.exercise_yield as optionYield, bondinfo.net_valuation as estCleanPrice,q1.IS_LIST_PAR as listPar, ISS_INFO.iss_start_date as issStartDate, ISS_INFO.iss_end_date as issEndDate, ISS_INFO.pay_start_date as payStartDate, ISS_INFO.pay_end_date as payEndDate, BASIC.year_pay_matu AS yearPayMatu, RATING.cred_org_uni_code as credOrgUniCode, \r\n " +
			"              BASIC.RATE_DES as rateDes, BASIC.BAS_SPR as basSpr, ISS_INFO.LIST_DATE as listDate, ISS_INFO.list_decl_date as listDeclDate, ISS_INFO.coll_cap_purp as collCapPurp, null as undeName, null as undeClsPar, ISS_INFO.bid_date as bidDate, ISS_INFO.ISS_FEE_RATE as issFeeRate, null as bokepDate, ISS_INFO.debt_reg_date as debtRegDate, ISS_INFO.subs_unit as subsUnit,   \r\n" + 
			"             ISS_INFO.least_subs_unit as leastSubsUnit, ISS_INFO.cury_type_par as curyTypePar, ISS_INFO.is_list_par as isListPar, null as tradeUnit, ISS_INFO.circu_amut as circuAmut, ISS_INFO.list_sect_par as listSectPar, ISS_INFO.theo_delist_date AS theoDelistDate, ISS_INFO.last_trade_date as lastTradeDate, ISS_INFO.actu_delist_date as actuDelistDate,   \r\n" + 
			"             null as speShortName, null AS engFullName, null as engShortName, BASIC.isin_code AS isinCode, BASIC.curr_status as currStatus, BASIC.bond_par_val as bondParVal,  BASIC.bond_form_par AS bondFormPar, BASIC.actu_end_date AS actuEndDate, null AS inteDes, BASIC.ref_yield as refYield, BASIC.init_base_rate_date AS initBaseRateDate, BASIC.base_rate as baseRate, BASIC.opt_extra_spr AS optExtraSpr,  \r\n" +
			"             BASIC.extra_spr_seq_num, BASIC.rate_ceil, BASIC.rate_floor, BASIC.inte_calcu_cls_par, BASIC.is_segm_par, BASIC.inte_pay_meth, BASIC.simp_comp_inte_par, BASIC.repay_cls_pay_par, BASIC.pay_fee_rate, BASIC.pay_matu, BASIC.pay_cls_des, BASIC.opt_des, null as is_guar_par, BASIC.is_repu_par, BASIC.is_hedge_par, BASIC.is_tax_free_par, BASIC.is_type_par as orgIsTypePar, ISS_INFO.ISS_STA_PAR as orgIssStaPar \r\n" + 
			"    			FROM bond_ccxe.d_bond_basic_info_5 AS BASIC\r\n" + 
			"    			LEFT JOIN bond_ccxe.D_BOND_ISS_INFO_5 AS ISS_INFO ON ISS_INFO.bond_uni_code = BASIC.bond_uni_code\r\n" +  
			sqlJoinBondCity + sqlJoinBondCred + sqlJoinCashFlow + sqlJoinBondPumComInfo + sqlJoinBondInfo + sqlFundatmentalIndicator  + sqlValidBond;

//	final String sqlBond5 = "SELECT BASIC.BOND_UNI_CODE, BASIC.BOND_CODE, BASIC.BOND_SHORT_NAME, BASIC.BOND_FULL_NAME, null as PLEDGE_NAME, null as PLEDGE_CODE, BOND_ISSER.org_uni_code as COM_UNI_CODE, NULL AS ISS_NAME, BASIC.BOND_MATU, BASIC.MATU_UNIT_PAR, 6 AS BOND_TYPE_PAR, BASIC.INTE_START_DATE, BASIC.THEO_END_DATE, BASIC.ISS_COUP_RATE, NULL AS NEW_COUP_RATE, BASIC.INTE_PAY_CLS_PAR,q1.COM_PRO as comPro,q1.MAIN_BUS as mainBus,q1.SID_BUS as sidBus,      \r\n" + 
//			"						    			 			BASIC.INTE_PAY_FREQ, BASIC.GURA_NAME, NULL AS BASE_RATE_PAR, (CASE WHEN  t2.com_uni_code IS NULL THEN 0 ELSE 1 END ) AS IS_TYPE_PAR, BASIC.RATE_TYPE_PAR, null as MATU_PAY_DATE, null as EXER_PAY_DATE, BASIC.SEC_MAR_PAR, RATING.rate_pros, RATING.bond_cred_level, null as CURR_STATUS , null as ISS_STA_PAR , BASIC.LIST_STA_PAR \r\n" + 
//			"						    			FROM bond_ccxe.d_bond_basic_info_6 AS BASIC    \r\n"   +
//			"							LEFT JOIN   bond_ccxe.bond_isser_info as BOND_ISSER on BASIC.BOND_UNI_CODE = BOND_ISSER.bond_uni_code \r\n" + 
//			sqlJoinBondCity + sqlJoinBondCred + sqlJoinBondPumComInfo  + sqlValidBond;
//
//	final String sqlBond6 = "SELECT BASIC.BOND_UNI_CODE, BASIC.BOND_CODE, BASIC.BOND_SHORT_NAME, BASIC.BOND_FULL_NAME, null as PLEDGE_NAME, null as PLEDGE_CODE, BOND_ISSER.org_uni_code as COM_UNI_CODE, NULL AS ISS_NAME, BASIC.BOND_MATU, BASIC.MATU_UNIT_PAR, 6 AS BOND_TYPE_PAR, BASIC.INTE_START_DATE, BASIC.THEO_END_DATE, BASIC.ISS_COUP_RATE, NULL AS NEW_COUP_RATE, BASIC.INTE_PAY_CLS_PAR,q1.COM_PRO as comPro,q1.MAIN_BUS as mainBus,q1.SID_BUS as sidBus,   \r\n" + 
//			"			    			 			BASIC.INTE_PAY_FREQ, BASIC.GURA_NAME, NULL AS BASE_RATE_PAR, (CASE WHEN  t2.com_uni_code IS NULL THEN 0 ELSE 1 END ) AS IS_TYPE_PAR, BASIC.RATE_TYPE_PAR, null as MATU_PAY_DATE, null as EXER_PAY_DATE, BASIC.SEC_MAR_PAR, RATING.rate_pros, RATING.bond_cred_level, null as CURR_STATUS , BASIC.ISS_STA_PAR , BASIC.LIST_STA_PAR  \r\n" + 
//			"			    			FROM bond_ccxe.d_bond_basic_info_7 AS BASIC  \r\n" +
//			"							LEFT JOIN   bond_ccxe.bond_isser_info as BOND_ISSER on BASIC.BOND_UNI_CODE = BOND_ISSER.bond_uni_code \r\n" + 
//			sqlJoinBondCity + sqlJoinBondCred + sqlJoinBondPumComInfo  + sqlValidBond;

	
	public String syncIntegration(Boolean isUpdateMode)
	{
		//class level synchronized
		synchronized(BondBasicDataService.class){
			LOG.info("开始处理重建债券基本信息...");
			ExecutorService pool = Executors.newCachedThreadPool();
			
			final Parameter[] sqlList = { 
					new Parameter(0, sqlBond0), 
					new Parameter(1, sqlBond1), 
					new Parameter(2, sqlBond2), 
					new Parameter(3, sqlBond3), 
					new Parameter(4, sqlBond4), 
					//new Parameter(5, sqlBond5), 
					//new Parameter(6, sqlBond6), 
			};

//			detailRepository.deleteAll();
			basicInfolRepository.deleteAll();
			basicDataConstructHandler.refreshStatics();
			
			for (final Parameter sql : sqlList) {
				pool.execute(
					new Runnable() {
						@Override
						public void run() {
							integration(sql.data);;
						}
					}
				);
			}

			pool.shutdown();
			try {
				pool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
			} catch (InterruptedException e) {
				LOG.error("等待任务完成中发生异常 ", e);
				e.printStackTrace();
			}
			
		    comDataService.refreshAll();
		     
			LOG.info("完成处理重建债券基本信息.");
			return "done successfully";
		}
	}
	
	/**
	 * 
	 * syncccxebond:(同步中诚信存续债到dmdb)
	 * @param  @param bondUniCode
	 * @param  @return    设定文件
	 * @return String    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	 */
	public String syncccxebond(Long bondUniCode)
    {
        //class level synchronized
        synchronized(BondBasicDataService.class){
            LOG.info("开始处理重建债券基本信息...");
            ExecutorService pool = Executors.newCachedThreadPool();
            
            final Parameter[] sqlList = { 
                    new Parameter(0, sqlBond0), 
                    new Parameter(1, sqlBond1), 
                    new Parameter(2, sqlBond2), 
                    new Parameter(3, sqlBond3), 
                    new Parameter(4, sqlBond4), 
            };
            
            for (final Parameter sql : sqlList) {
                pool.execute(
                    new Runnable() {
                        @Override
                        public void run() {
                            integratCccxebond(sql.data,bondUniCode);;
                        }
                    }
                );
            }

            pool.shutdown();
            try {
                pool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            } catch (InterruptedException e) {
                LOG.error("等待任务完成中发生异常 ", e);
                e.printStackTrace();
            }
            
            comDataService.refreshAll();
             
            LOG.info("完成处理同步中诚信存续债到dmdb.");
            return "syncccxebond done successfully";
        }
    }

	public class BondCcxeBasicInfoRowMapper implements RowMapper
	{
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			BondCcxeBasicInfo basicInfo = new BondCcxeBasicInfo();
			return basicInfo;
		}
	}
	
	public String integration(String sqlBase)
	{
		ExecutorService pool = Executors.newFixedThreadPool(30);
		int position = 0;
		int count = 500;
		
		while (true) {
			String sql = sqlBase + String.format(" limit %1$d,%2$d " + sqlJoinBondImpliedRating, 	position, count);
			position += count;
//			LOG.info(sql);
			
			List<BondCcxeBasicInfo> bondBasics = queryBondCcxeBasicInfo(sql);

			if (bondBasics == null ) {
				LOG.error("empty result set");
				return "internal error";
			};
			if (bondBasics.isEmpty()) {
				LOG.info("task has been done successfully");
				break;
			}
			pool.execute(
				new Runnable() {
					@Override
					public void run() {
						basicDataConstructHandler.saveBondInfo2Mongo(bondBasics);
					}
				}
			);
		}
		pool.shutdown();
		try {
			pool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
			LOG.error("等待任务完成中发生异常 ", e);
			e.printStackTrace();
		}
		
		return "task done";
	}
	
	public String integratCccxebond(String sqlBase, Long bondUniCode)
    {
        ExecutorService pool = Executors.newFixedThreadPool(30);
        int position = 0;
        int count = 500;
        
        while (true) {
            String sql = sqlBase + String.format(" limit %1$d,%2$d " + sqlJoinBondImpliedRating,    position, count);
            if(null != bondUniCode && !StringUtils.isEmpty(bondUniCode.toString())) {
                sql = sql + String.format(" WHERE t.BOND_UNI_CODE= %1$d ", bondUniCode);
            }
            position += count;
//            LOG.info(sql);
            
            List<BondCcxeBasicInfo> bondBasics = queryBondCcxeBasicInfo(sql);

            if (bondBasics == null ) {
                LOG.error("empty result set");
                return "internal error";
            };
            if (bondBasics.isEmpty()) {
                LOG.info("task integratCccxebond has been done successfully");
                break;
            }
            pool.execute(
                new Runnable() {
                    @Override
                    public void run() {
                        basicDataConstructHandler.saveBondInfo2DB(bondBasics);
                    }
                }
            );
        }
        pool.shutdown();
        try {
            pool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            LOG.error("等待任务完成中发生异常 ", e);
            e.printStackTrace();
        }
        
        return "integratCccxebond task done";
    }

	private List<BondCcxeBasicInfo> queryBondCcxeBasicInfo(String sql) {
		return (List<BondCcxeBasicInfo>) jdbcTemplate.query(sql, new RowMapper() {
			public BondCcxeBasicInfo mapRow(ResultSet rs, int rowNumber) throws SQLException {
				int i = 0;
				// 01 BASIC.BOND_UNI_CODE, BASIC.BOND_CODE,
				// BASIC.BOND_SHORT_NAME, BASIC.BOND_FULL_NAME,
				// BASIC.PLEDGE_NAME,
				BondCcxeBasicInfo basicInfo = new BondCcxeBasicInfo();
				basicInfo.setBondUniCode(rs.getLong(++i));
				basicInfo.setBondCode(rs.getString(++i));
				basicInfo.setBondShortName(rs.getString(++i));
				basicInfo.setBondFullName(rs.getString(++i));
				basicInfo.setPledgeName(rs.getString(++i));

				// 06 BASIC.PLEDGE_CODE, BASIC.ORG_UNI_CODE as COM_UNI_CODE,
				// BASIC.ISS_NAME, BASIC.BOND_MATU, BASIC.MATU_UNIT_PAR,
				basicInfo.setPledgeCode(rs.getString(++i));
				basicInfo.setComUniCode(rs.getLong(++i));
				basicInfo.setIssName(rs.getString(++i));
				basicInfo.setBondMatu(rs.getBigDecimal(++i));
				basicInfo.setMatuUnitPar(rs.getInt(++i));

				// 11 BASIC.BOND_TYPE_PAR, BASIC.INTE_START_DATE,
				// BASIC.THEO_END_DATE, BASIC.ISS_COUP_RATE,
				// BASIC.NEW_COUP_RATE,
				basicInfo.setBondTypePar(rs.getInt(++i));
				basicInfo.setInteStartDate(rs.getDate(++i));
				basicInfo.setTheoEndDate(rs.getDate(++i));
				basicInfo.setIssCoupRate(rs.getBigDecimal(++i));
				basicInfo.setNewCoupRate(rs.getBigDecimal(++i));

				// 16 BASIC.INTE_PAY_CLS_PAR, q1.COM_PRO as comPro, q1.MAIN_BUS
				// as mainBus, q1.SID_BUS as sidBus, BASIC.INTE_PAY_FREQ,
				basicInfo.setIntePayClsPar(rs.getInt(++i));
				basicInfo.setComPro(rs.getString(++i));
				basicInfo.setMainBus(rs.getString(++i));
				basicInfo.setSidBus(rs.getString(++i));
				basicInfo.setInteParFreq(rs.getInt(++i));

				// 21 BASIC.GURA_NAME, BASIC.BASE_RATE_PAR, (CASE WHEN
				// t2.com_uni_code IS NULL THEN 0 ELSE 1 END ) AS IS_TYPE_PAR,
				// BASIC.RATE_TYPE_PAR, BASIC.MATU_PAY_DATE,
				basicInfo.setGuraName(rs.getString(++i));
				basicInfo.setBaseRatePar(rs.getInt(++i));
				basicInfo.setIsTypePar(rs.getInt(++i));
				basicInfo.setRateTypePar(rs.getInt(++i));
				basicInfo.setMatuPayDate(rs.getDate(++i));

				// 26 BASIC.EXER_PAY_DATE, ISS_INFO.SEC_MAR_PAR,
				// ISS_INFO.IS_CROS_MAR_PAR, CROS_MAR_DES, RATING.rate_pros,
				basicInfo.setExerPayDate(rs.getString(++i));
				basicInfo.setSecMarPar(rs.getInt(++i));
				basicInfo.setIsCrosMarPar(rs.getInt(++i));
				basicInfo.setCrosMarDes(rs.getString(++i));
				basicInfo.setRatePros(rs.getInt(++i));

				// 31 RATING.bond_cred_level, BASIC.CURR_STATUS ,
				// ISS_INFO.ISS_STA_PAR , ISS_INFO.LIST_STA_PAR,
				// BASIC.YEAR_PAY_DATE
				basicInfo.setBondCredLevel(rs.getString(++i));
				basicInfo.setCurrStatus(rs.getInt(++i));
				basicInfo.setIssStaPar(rs.getInt(++i));
				basicInfo.setListStaPar(rs.getInt(++i));
				basicInfo.setYearPayDate(rs.getString(++i));

				//35
				basicInfo.setFairValue(rs.getBigDecimal(++i));
				basicInfo.setNewSize(rs.getDouble(++i));
				basicInfo.setGuraName1(rs.getString(++i));
				basicInfo.setIsRedemPar(rs.getInt(++i));
				basicInfo.setIsResaPar(rs.getInt(++i));
				
				//41
				basicInfo.setIssCls(rs.getString(++i));
				// RATING.rate_writ_date as bond_rate_writ_date
				basicInfo.setBondRateWritDate(rs.getDate(++i));
				basicInfo.setBondRateOrgName(rs.getString(++i));
				basicInfo.setBondParVal(rs.getDouble(++i));
				basicInfo.setIssPri(rs.getDouble(++i));
				
				//46
				basicInfo.setPayAmount(rs.getDouble(++i));

				// fundamental indicator
				double macd = rs.getDouble(++i);
				if (!rs.wasNull()) {
					basicInfo.setMacd(macd);
				}
				double modd = rs.getDouble(++i);
				if (!rs.wasNull()) {
					basicInfo.setModd(modd);
				}
				double convexity = rs.getDouble(++i);
				if (!rs.wasNull()) { 
					basicInfo.setConvexity(convexity);
				}
				double convRatio = rs.getDouble(++i);
				if (!rs.wasNull()) { 
					basicInfo.setConvRatio(convRatio);
				}
				
				//51
				double staticSpread = rs.getDouble(++i);
				if (!rs.wasNull()) {
					basicInfo.setStaticSpread(staticSpread);
				}
				double optionYield = rs.getDouble(++i);
				if (!rs.wasNull()) {
					basicInfo.setOptionYield(optionYield);
				}
				double estCleanPrice = rs.getDouble(++i);
				if (!rs.wasNull()) {
					basicInfo.setEstCleanPrice(estCleanPrice);
				}
				basicInfo.setListPar(rs.getInt(++i));
		
				basicInfo.setIssStartDate(rs.getDate(++i));
				
				//56
				basicInfo.setIssEndDate(rs.getDate(++i));
				basicInfo.setPayStartDate(rs.getDate(++i));
				basicInfo.setPayEndDate(rs.getDate(++i));
				basicInfo.setYearPayMatu(rs.getString(++i));
				basicInfo.setCredOrgUniCode(rs.getLong(++i));
				
				//61
				assert(i==61);
				basicInfo.setRateDes(rs.getString(++i));
				basicInfo.setBasSpr(rs.getBigDecimal(++i));
				basicInfo.setListDate(rs.getDate(++i));
				basicInfo.setListDeclDate(rs.getDate(++i));
				basicInfo.setCollCapPurp(rs.getString(++i));
				
				assert(i==66);
				basicInfo.setUndeName(rs.getString(++i));
				basicInfo.setUndeClsPar(rs.getInt(++i));
				basicInfo.setBidDate(rs.getDate(++i));
				basicInfo.setIssFeeRate(rs.getBigDecimal(++i));
				basicInfo.setBokepDate(rs.getDate(++i));
				
				assert(i==71);
				basicInfo.setDebtRegDate(rs.getDate(++i));
				basicInfo.setSubsUnit(rs.getBigDecimal(++i));
				basicInfo.setLeastSubsUnit(rs.getBigDecimal(++i));
				basicInfo.setCuryTypePar(rs.getInt(++i));
				basicInfo.setIsListPar(rs.getInt(++i));
				
				assert(i==76);
				basicInfo.setTradeUnit(rs.getString(++i));
				basicInfo.setCircuAmut(rs.getBigDecimal(++i));
				basicInfo.setListSectPar(rs.getInt(++i));
				basicInfo.setTheoDelistDate(rs.getDate(++i));
				basicInfo.setLastTradeDate(rs.getDate(++i));
				
				assert(i==81);
				basicInfo.setActuDelistDate(rs.getDate(++i));
				basicInfo.setSpeShortName(rs.getString(++i));
				basicInfo.setEngFullName(rs.getString(++i));
				basicInfo.setEngShortName(rs.getString(++i));
				basicInfo.setIsinCode(rs.getString(++i));
				
				assert(i==86);
				basicInfo.setCurrStatus(rs.getInt(++i));
				basicInfo.setBondParVal(rs.getDouble(++i));
				basicInfo.setBondFormPar(rs.getInt(++i));
				basicInfo.setActuEndDate(rs.getDate(++i));
				basicInfo.setInteDes(rs.getString(++i));
				
				assert(i==91);
				basicInfo.setRefYield(rs.getBigDecimal(++i));
				basicInfo.setInitBaseRateDate(rs.getDate(++i));
				basicInfo.setBaseRate(rs.getBigDecimal(++i));
				basicInfo.setOptExtraSpr(rs.getBigDecimal(++i));
				basicInfo.setExtraSprSeqNum(rs.getInt(++i));
				
				assert(i==96);
				basicInfo.setRateCeil(rs.getBigDecimal(++i));
				basicInfo.setRateFloor(rs.getBigDecimal(++i));
				basicInfo.setInteCalcuClsPar(rs.getInt(++i));
				basicInfo.setIsSegmPar(rs.getInt(++i));
				basicInfo.setIntePayMeth(rs.getString(++i));
				
				assert(i==101);
				basicInfo.setSimpCompIntePar(rs.getInt(++i));
				basicInfo.setRepayClsPayPar(rs.getInt(++i));
				basicInfo.setPayFeeRate(rs.getBigDecimal(++i));
				basicInfo.setPayMatu(rs.getString(++i));
				basicInfo.setPayClsDes(rs.getString(++i));
				
				assert(i==106);
				basicInfo.setOptDes(rs.getString(++i));
				basicInfo.setIsGuarPar(rs.getInt(++i));
				basicInfo.setIsRepuPar(rs.getInt(++i));
				basicInfo.setIsHedgePar(rs.getInt(++i));
				basicInfo.setIsTaxFreePar(rs.getInt(++i));
				
				assert(i==111);
				basicInfo.setOrgIsTypePar(rs.getInt(++i));
				basicInfo.setOrgIssStaPar(rs.getInt(++i));
				
				// note: impliedRating always be the last field
				basicInfo.setImpliedRating(rs.getString(++i));
				return basicInfo;
			}
		});
	}
}

