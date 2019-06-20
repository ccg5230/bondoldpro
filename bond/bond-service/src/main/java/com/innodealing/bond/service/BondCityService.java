package com.innodealing.bond.service;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.google.common.base.Objects;
import com.innodealing.bond.param.AreaUniCodeParam;
import com.innodealing.engine.jpa.dm.BondCustomAreaRepository;
import com.innodealing.model.dm.bond.BondArea;
import com.innodealing.model.dm.bond.BondAreaData;
import com.innodealing.model.dm.bond.BondCity;
import com.innodealing.model.dm.bond.BondCity3;
import com.innodealing.model.dm.bond.BondCitySort;
import com.innodealing.model.dm.bond.BondCustomArea;
import com.innodealing.model.mongo.dm.AreaDataDoc;
import com.innodealing.model.mongo.dm.AreaEconomiesDoc;
import com.innodealing.model.mongo.dm.BondDetailDoc;
import com.innodealing.model.mongo.dm.EconomieIndicator;
import com.innodealing.util.BeanUtil;
import com.innodealing.util.BondCityComparator;
import com.innodealing.util.StringUtils;
import com.mongodb.DB;

@Service
public class BondCityService {

	private static final Logger LOG = LoggerFactory.getLogger(BondCityService.class);

	protected @Autowired JdbcTemplate jdbcTemplate;
	
	protected @Autowired BondCustomAreaRepository bondCustomAreaRepository;

	protected @Autowired MongoOperations mongoOperations;
	
	protected @Autowired MongoTemplate mongoTemplate;

	@Deprecated 
	public List<BondCity> viewList(String areaUniCode, Integer type) {

		List<BondCity> list = null;
		
		/** 非空条件 */
		String notNull = null;
		/** 列名集合 */
		String[] columnList = null;
		/** 页数 */
		Integer page = 0;
		/** 每页显示数量 */
		Integer limit = 6;
		/** 排序规则 */
		String sort = "bondYear:desc";
		/** 分组规则 */
		String group = null;
		/** 类型1,地级市2,省 */
		Integer bondType = 1;

		switch (type) {
		case 1:
			Calendar calendar = Calendar.getInstance();
			/** 当前年月 */
			Integer year = calendar.get(Calendar.YEAR);
			Integer month = calendar.get(Calendar.MONTH);
			StringBuffer sqlStr = new StringBuffer();
			sqlStr.append("\n\tSELECT q.*,(SELECT distinct(area_name2) FROM t_bond_area WHERE area_uni_code = " + areaUniCode
					+ ") as area_chi_name  FROM (");
			sqlStr.append("\n\tSELECT t.bond_year,t.bond_month,t.industrial_growth_monthly FROM  dmdb.t_bond_city  t");
			sqlStr.append("\n\tWHERE t.bond_type = 2 AND t.industrial_growth_monthly IS NOT NULL AND t.bond_year = "
					+ year + " AND t.bond_month <" + month + " AND t.area_uni_code = " + areaUniCode);
			sqlStr.append("\n\tLIMIT 0,6");
			sqlStr.append("\n\tUNION ");
			sqlStr.append("\n\tSELECT t.bond_year,t.bond_month,t.industrial_growth_monthly FROM  dmdb.t_bond_city  t");
			sqlStr.append("\n\tWHERE t.bond_type = 2 AND t.industrial_growth_monthly IS NOT NULL AND t.bond_year = "
					+ (year - 1) + " AND t.bond_month <" + month + " AND t.area_uni_code = " + areaUniCode);
			sqlStr.append("\n\t) q  ORDER BY q.bond_year DESC,q.bond_month DESC");
			sqlStr.append("\n\tLIMIT 0,12");
			list = jdbcTemplate.query(sqlStr.toString(), new BeanPropertyRowMapper<BondCity>(BondCity.class));
			break;
		case 2:
			columnList = new String[] { "bondYear", "bondQuarter", "gdpEquityQuarter", "gdpGrowth" };
			bondType = 2;
			notNull = "gdpEquityQuarter";
			group = "bondYear,bondQuarter";
			sort = "bond_year:DESC,bond_quarter:DESC";
			list = this.list(areaUniCode, notNull, columnList, page, limit, sort, group, bondType);
			break;
		case 3:
			columnList = new String[] { "bondYear", "gdp", "gdpIndustrialAddedValue1", "gdpIndustrialAddedValue2",
					"gdpIndustrialAddedValue3" };
			bondType = 2;
			group = "bondYear";
			list = this.list(areaUniCode, notNull, columnList, page, limit, sort, group, bondType);
			break;
		case 4:
			columnList = new String[] { "bondYear", "gdp", "gdpIndustrialAddedValue1", "gdpIndustrialAddedValue2",
					"gdpIndustrialAddedValue3" };
			bondType = 1;
			list = this.list(areaUniCode, notNull, columnList, page, limit, sort, group, bondType);
			break;
		case 5:
			columnList = new String[] { "bondYear", "budgetRevenue", "budgetaryExpenditure", "financeProduces" };
			bondType = 2;
			group = "bondYear";
			list = this.list(areaUniCode, notNull, columnList, page, limit, sort, group, bondType);
			break;
		case 6:
			columnList = new String[] { "bondYear", "budgetRevenue", "budgetaryExpenditure", "financeProduces" };
			bondType = 1;
			list = this.list(areaUniCode, notNull, columnList, page, limit, sort, group, bondType);
			break;
		case 7:
			columnList = new String[] { "bondYear", "budgetRevenue", "taxRevenue", "nontaxRevenue" };
			bondType = 2;
			group = "bondYear";
			list = this.list(areaUniCode, notNull, columnList, page, limit, sort, group, bondType);
			break;
		case 8:

			break;
		case 9:
			columnList = new String[] { "bondYear", "permanentResidentPopulation", "urbanPopulation",
					"ruralPopulation" };
			bondType = 2;
			group = "bondYear";
			notNull = "permanentResidentPopulation";
			list = this.list(areaUniCode, notNull, columnList, page, limit, sort, group, bondType);
			break;
		case 10:
			columnList = new String[] { "bondYear", "bondQuarter", "urbanDisposableIncome", "ruralDisposableIncome",
					"disposableIncome" };
			bondType = 2;
			group = "bondYear,bondQuarter";
			sort = "bondYear:DESC,bondQuarter:DESC";
			notNull = "urbanDisposableIncome";
			list = this.list(areaUniCode, notNull, columnList, page, limit, sort, group, bondType);
			break;
		case 11:
			columnList = new String[] { "bondYear", "czxf_avg_expenses" };
			bondType = 2;
			group = "bondYear";
			list = this.list(areaUniCode, notNull, columnList, page, limit, sort, group, bondType);
			break;
		case 12:
			columnList = new String[] { "bondYear", "czxf_avg_expenses" };
			bondType = 1;
			group = "bondYear";
			list = this.list(areaUniCode, notNull, columnList, page, limit, sort, group, bondType);
			break;
		default:
			break;
		}

		return list;
	}

	public List<BondCity> list(String areaUniCode, String notNull, String[] columnList, Integer page, Integer limit,
			String sort, String group, Integer type) {

		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append("select \t");

		for (int i = 0; i < columnList.length; i++) {

			switch (columnList[i]) {
			case "ruralPopulation":
				sqlStr.append("IFNULL(permanent_resident_population,0)-IFNULL(urban_population,0) as rural_population");
				break;
			case "financeProduces":
				sqlStr.append("IFNULL(budget_revenue,0)/IFNULL(budgetary_expenditure,0) as finance_produces");
				break;
			case "gdpGrowth":
				sqlStr.append(
						"\n\t(SELECT (t.gdp_equity_quarter-q.gdp_equity_quarter)/q.gdp_equity_quarter FROM dmdb.t_bond_city q");
				sqlStr.append("\n\tWHERE q.gdp_equity_quarter IS NOT NULL");
				sqlStr.append("\n\tAND q.bond_type =" + type + " \t and q.area_uni_code = " + areaUniCode);
				sqlStr.append("\n\tAND q.bond_year = t.bond_year-1");
				sqlStr.append("\n\tAND q.bond_quarter = t.bond_quarter");
				sqlStr.append("\n\t) AS gdp_growth");
				break;
			default:
				sqlStr.append(Util.camelToUnderline(columnList[i]));
				break;
			}

			// if(i<columnList.length-1){
			sqlStr.append(",");
			// }
		}

		sqlStr.append("\n\t (SELECT distinct(area_name2) FROM t_bond_area WHERE area_uni_code = " + areaUniCode
				+ ") as area_chi_name  from dmdb.t_bond_city t");
		sqlStr.append("\n\t where 1=1");
		sqlStr.append("\n\t and bond_type =" + type + " \t and area_uni_code = " + areaUniCode);

		if (!StringUtils.isEmpty(notNull)) {
			sqlStr.append("\t and \t" + Util.camelToUnderline(notNull) + "\t is not null");
		}
		if (!StringUtils.isEmpty(group)) {
			sqlStr.append("\n\t group by \t" + Util.camelToUnderline(group));
		}

		sqlStr = sort(sort, sqlStr);
		sqlStr.append("\n\t limit\t" + page + ",\t" + limit);
		List<BondCity> list = jdbcTemplate.query(sqlStr.toString(),
				new BeanPropertyRowMapper<BondCity>(BondCity.class));

		return list;
	}
	
	/**
	 * 
	 * viewAreaList:(区域经济图表数据：只读公报数据)
	 * @param  @param areaUniCode
	 * @param  @param type
	 * @param  @return    设定文件
	 * @return List<BondAreaData>    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	 */
	public List<BondAreaData> viewAreaList(String areaUniCode, Integer type) {

        List<BondAreaData> list = null;

        /** 页数 */
        Integer page = 0;
        /** 每页显示数量 */
        Integer limit = 6;
        
        Calendar calendar = Calendar.getInstance();
        /** 当前年月 */
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int quarter = getQuarter(month);
        StringBuffer sql = null;
        String laseNotNullSql ="";//非空值sql
        BondAreaData lastData = null;
        switch (type) {
            case 1://季度gdp总量
                //先查询出日期距离最近的一个非空数值，然后按照这个作为结束日期数6期
                laseNotNullSql = "SELECT bond_year,bond_quarter,gdp FROM dmdb.t_bond_area_data WHERE gdp IS NOT null "
                        + " AND area_uni_code = " +areaUniCode +" AND data_type='季度' AND statistics_type='公报'"
                        + " ORDER BY bond_year DESC, bond_quarter DESC LIMIT 1";
                //防止为空抛异常
                try {
                    lastData = jdbcTemplate.queryForObject(laseNotNullSql, new BeanPropertyRowMapper<BondAreaData>(BondAreaData.class));
                } catch (EmptyResultDataAccessException e) {
                    lastData = null;
                }
                if(null != lastData){
                    year = (lastData.getBondYear());
                    quarter = lastData.getBondQuarter();
                    //查询6期数据
                    sql = new StringBuffer();
                    sql.append(" SELECT t.*,");
                    sql.append(" (");
                    sql.append(" SELECT ( t.gdp - q.gdp ) / q.gdp FROM dmdb.t_bond_area_data q");
                    sql.append(" WHERE q.area_uni_code =").append(areaUniCode);
                    sql.append(" AND q.bond_year = t.bond_year - 1 AND q.bond_quarter = t.bond_quarter");//上一年同期
                    sql.append(" AND q.data_type='季度' AND q.statistics_type='公报'");
                    sql.append(" ) AS growth_gdp,");
                    sql.append(" (");
                    sql.append(" SELECT AREA_CHI_NAME FROM bond_ccxe.pub_area_code");
                    sql.append(" WHERE area_uni_code = ").append(areaUniCode);
                    sql.append(" ) AS area_chi_name");
                    sql.append(" FROM");
                    sql.append(" (");
                    sql.append(" SELECT d.bond_year,d.bond_quarter,d.gdp FROM dmdb.t_bond_area_data d");
                    sql.append(" WHERE d.bond_year =").append(year);
                    sql.append(" AND d.bond_quarter <=").append(quarter).append(" AND d.data_type='季度' AND d.statistics_type='公报'"); 
                    sql.append(" AND d.area_uni_code = ").append(areaUniCode);
                    sql.append(" UNION");
                    sql.append(" SELECT d.bond_year,d.bond_quarter,d.gdp FROM dmdb.t_bond_area_data d");
                    sql.append(" WHERE d.bond_year<=").append(year-1).append(" AND d.data_type='季度' AND d.statistics_type='公报'"); 
                    sql.append(" AND d.area_uni_code = ").append(areaUniCode);
                    sql.append(" ) t");
                    sql.append(" GROUP BY bond_year, bond_quarter");
                    sql.append(" ORDER BY bond_year DESC,bond_quarter DESC");
                    sql.append(" LIMIT ").append(page).append(",").append(limit);
                    list = jdbcTemplate.query(sql.toString(),new BeanPropertyRowMapper<BondAreaData>(BondAreaData.class));
                }
                break;
        case 2://季度工业增加值
            //先查询出日期距离最近的一个非空数值，然后按照这个作为结束日期数6期
            laseNotNullSql = "SELECT bond_year,bond_quarter,indu_add_val FROM dmdb.t_bond_area_data WHERE indu_add_val IS NOT null "
                    + " AND area_uni_code = "+areaUniCode 
                    + " AND data_type='季度' AND statistics_type='公报' ORDER BY bond_year DESC, bond_quarter DESC LIMIT 1";
            //防止为空抛异常
            try {
                lastData = jdbcTemplate.queryForObject(laseNotNullSql, new BeanPropertyRowMapper<BondAreaData>(BondAreaData.class));
            } catch (EmptyResultDataAccessException e) {
                lastData = null;
            }
            if(null != lastData){
                year = lastData.getBondYear();
                quarter = lastData.getBondQuarter();
                //查询6期数据
                sql = new StringBuffer();
                sql.append(" SELECT t.*,");
                sql.append(" (");
                sql.append(" SELECT ( t.indu_add_val - q.indu_add_val ) / q.indu_add_val FROM dmdb.t_bond_area_data q");
                sql.append(" WHERE q.area_uni_code =").append(areaUniCode);
                sql.append(" AND q.bond_year = t.bond_year - 1 AND q.bond_quarter = t.bond_quarter");//上一年同期
                sql.append(" AND q.data_type='季度' AND q.statistics_type='公报'");
                sql.append(" ) AS growth_indu_output_val,");
                sql.append(" (");
                sql.append(" SELECT AREA_CHI_NAME FROM bond_ccxe.pub_area_code");
                sql.append(" WHERE area_uni_code = ").append(areaUniCode);
                sql.append(" ) AS area_chi_name");
                sql.append(" FROM");
                sql.append(" (");
                sql.append(" SELECT d.bond_year,d.bond_quarter,d.indu_add_val FROM dmdb.t_bond_area_data d");
                sql.append(" WHERE d.bond_year =").append(year);
                sql.append(" AND d.bond_quarter <=").append(quarter).append(" AND d.data_type='季度' AND d.statistics_type='公报'"); 
                sql.append(" AND d.area_uni_code = ").append(areaUniCode);
                sql.append(" UNION");
                sql.append(" SELECT d.bond_year,d.bond_quarter,d.indu_add_val FROM dmdb.t_bond_area_data d");
                sql.append(" WHERE d.bond_year<=").append(year-1).append(" AND d.data_type='季度' AND d.statistics_type='公报'"); 
                sql.append(" AND d.area_uni_code = ").append(areaUniCode);
                sql.append(" ) t");
                sql.append(" GROUP BY bond_year, bond_quarter");
                sql.append(" ORDER BY bond_year DESC,bond_quarter DESC");
                sql.append(" LIMIT ").append(page).append(",").append(limit);
                list = jdbcTemplate.query(sql.toString(),new BeanPropertyRowMapper<BondAreaData>(BondAreaData.class));
            }
            break;
        case 3://年度产业结构变动
            limit = 4;
            //先查询出日期距离最近的一个非空数值，然后按照这个作为结束日期数4年
            laseNotNullSql = "SELECT d.bond_year, d.gdp, d.gdp_add_val_primary_indu, d.gdp_add_val_secondary_indu, d.gdp_add_val_tertiary_indu" +
                    " FROM dmdb.t_bond_area_data d" +
                    " WHERE gdp_add_val_primary_indu IS NOT null AND data_type='年度' AND statistics_type='公报'" +
                    " AND area_uni_code = " +areaUniCode +
                    " ORDER BY bond_year DESC, bond_quarter DESC LIMIT 1";
            //防止为空抛异常
            try {
                lastData = jdbcTemplate.queryForObject(laseNotNullSql, new BeanPropertyRowMapper<BondAreaData>(BondAreaData.class));
            } catch (EmptyResultDataAccessException e) {
                lastData = null;
            }
            if(null != lastData){ 
                year = lastData.getBondYear();
                sql = new StringBuffer();
                sql.append("SELECT d.bond_year, d.gdp, d.gdp_add_val_primary_indu, d.gdp_add_val_secondary_indu, d.gdp_add_val_tertiary_indu");
                sql.append(" FROM dmdb.t_bond_area_data d");
                sql.append(" WHERE d.bond_year <=").append(year).append(" AND data_type='年度' AND d.statistics_type='公报'");
                sql.append(" AND d.area_uni_code = ").append(areaUniCode);
                sql.append(" ORDER BY d.bond_year DESC");
                sql.append(" LIMIT ").append(page).append(",").append(limit);
                
                list = jdbcTemplate.query(sql.toString(),new BeanPropertyRowMapper<BondAreaData>(BondAreaData.class));
                //转换为占比
                if(null != list && list.size()>0) {
                    convertProportion(list);
                }
            }
            break;
        case 4://地方财政预算情况：年度
            laseNotNullSql = "SELECT bond_year, pub_gov_receipts, pub_fiscal_exp "
                    + " FROM dmdb.t_bond_area_data  "
                    + "WHERE pub_gov_receipts IS NOT null AND data_type='年度' AND statistics_type='公报'"
                    + " AND area_uni_code = " +areaUniCode 
                    + " ORDER BY bond_year DESC, bond_quarter DESC LIMIT 1";
            //防止为空抛异常
            try {
                lastData = jdbcTemplate.queryForObject(laseNotNullSql, new BeanPropertyRowMapper<BondAreaData>(BondAreaData.class));
            } catch (EmptyResultDataAccessException e) {
                lastData = null;
            }
            if(null != lastData){ 
                year = lastData.getBondYear();
                sql = new StringBuffer();
                sql.append("SELECT bond_year, pub_gov_receipts, pub_fiscal_exp,");
                sql.append(" IFNULL(pub_gov_receipts, 0) / IFNULL(pub_fiscal_exp, 0) AS finance_produces,");
                sql.append(" (");
                sql.append(" SELECT AREA_CHI_NAME FROM bond_ccxe.pub_area_code");
                sql.append(" WHERE area_uni_code = ").append(areaUniCode);
                sql.append(" ) AS area_chi_name");
                sql.append(" FROM dmdb.t_bond_area_data t");
                sql.append(" WHERE area_uni_code = ").append(areaUniCode);
                sql.append(" AND data_type='年度' AND statistics_type='公报'");
                sql.append(" AND bond_year <=").append(year);
                sql.append(" ORDER BY bond_year DESC");
                sql.append(" LIMIT ").append(page).append(",").append(limit);
                
                list = jdbcTemplate.query(sql.toString(),new BeanPropertyRowMapper<BondAreaData>(BondAreaData.class));
             }
            break;
        case 5://收入结构分析:年度
            laseNotNullSql = "SELECT bond_year, pub_gov_receipts, tax_rev, nontax_rev "
                    + " FROM dmdb.t_bond_area_data"
                    + " WHERE tax_rev IS NOT null AND data_type='年度'  AND statistics_type='公报' "
                    + " AND area_uni_code = " +areaUniCode 
                    + " ORDER BY bond_year DESC, bond_quarter DESC LIMIT 1";
            //防止为空抛异常
            try {
                lastData = jdbcTemplate.queryForObject(laseNotNullSql, new BeanPropertyRowMapper<BondAreaData>(BondAreaData.class));
            } catch (EmptyResultDataAccessException e) {
                lastData = null;
            }
            if(null != lastData){ 
                year = lastData.getBondYear();
                sql = new StringBuffer();
                sql.append("SELECT bond_year, pub_gov_receipts, tax_rev, nontax_rev,");
                sql.append(" (");
                sql.append(" SELECT AREA_CHI_NAME FROM bond_ccxe.pub_area_code WHERE area_uni_code = ").append(areaUniCode);
                sql.append(" ) AS area_chi_name");
                sql.append(" FROM dmdb.t_bond_area_data");
                sql.append(" WHERE area_uni_code = ").append(areaUniCode);
                sql.append(" AND data_type='年度' AND statistics_type='公报'");
                sql.append(" AND bond_year <=").append(year);
                sql.append(" ORDER BY bond_year DESC");
                sql.append(" LIMIT ").append(page).append(",").append(limit);
                
                list = jdbcTemplate.query(sql.toString(),new BeanPropertyRowMapper<BondAreaData>(BondAreaData.class));
            }
            break;
        case 6://常住人口变动：年度
            limit = 4;
            laseNotNullSql = "SELECT bond_year, permanent_resident_pop "
                    + " FROM dmdb.t_bond_area_data  "
                    + " WHERE permanent_resident_pop IS NOT null AND data_type='年度'  AND statistics_type='公报' "
                    + " AND area_uni_code = " +areaUniCode 
                    + " ORDER BY bond_year DESC, bond_quarter DESC LIMIT 1";
            //防止为空抛异常
            try {
                lastData = jdbcTemplate.queryForObject(laseNotNullSql, new BeanPropertyRowMapper<BondAreaData>(BondAreaData.class));
            } catch (EmptyResultDataAccessException e) {
                lastData = null;
            }
            if(null != lastData){ 
                year = lastData.getBondYear();
                sql = new StringBuffer();
                sql.append("SELECT d.bond_year, d.permanent_resident_pop,"); 
                sql.append(" (");
                sql.append(" SELECT ( d.permanent_resident_pop - q.permanent_resident_pop ) / q.permanent_resident_pop");
                sql.append(" FROM dmdb.t_bond_area_data q");
                sql.append(" WHERE q.area_uni_code = ").append(areaUniCode);
                sql.append(" AND q.data_type='年度' AND q.statistics_type='公报'");
                sql.append(" AND q.bond_year =d.bond_year - 1");
                sql.append(" ) AS growth_pop,");
                sql.append(" ( SELECT AREA_CHI_NAME FROM bond_ccxe.pub_area_code WHERE area_uni_code = ").append(areaUniCode);
                sql.append(" ) AS area_chi_name");
                sql.append(" FROM dmdb.t_bond_area_data d");
                sql.append(" WHERE d.area_uni_code = ").append(areaUniCode);
                sql.append(" AND d.data_type='年度' AND d.statistics_type='公报'");
                sql.append(" AND d.bond_year <=").append(year);
                sql.append(" ORDER BY d.bond_year DESC");
                sql.append(" LIMIT ").append(page).append(",").append(limit);
                list = jdbcTemplate.query(sql.toString(),new BeanPropertyRowMapper<BondAreaData>(BondAreaData.class));
            }
            break;
        case 7://年度人口分布统计
            laseNotNullSql = "SELECT bond_year, permanent_resident_pop, urban_pop, rural_pop "
                    + " FROM dmdb.t_bond_area_data  "
                    + " WHERE permanent_resident_pop IS NOT null AND data_type='年度'  AND statistics_type='公报' "
                    + " AND area_uni_code = " +areaUniCode 
                    + " ORDER BY bond_year DESC, bond_quarter DESC LIMIT 1";
            //防止为空抛异常
            try {
                lastData = jdbcTemplate.queryForObject(laseNotNullSql, new BeanPropertyRowMapper<BondAreaData>(BondAreaData.class));
            } catch (EmptyResultDataAccessException e) {
                lastData = null;
            }
            if(null != lastData){ 
                year = lastData.getBondYear();
                sql = new StringBuffer();
                sql.append("SELECT bond_year, permanent_resident_pop, urban_pop,");
                sql.append(" rural_pop,");
                sql.append(" (");
                sql.append(" SELECT AREA_CHI_NAME FROM bond_ccxe.pub_area_code WHERE area_uni_code = ").append(areaUniCode);
                sql.append(" ) AS area_chi_name");
                sql.append(" FROM dmdb.t_bond_area_data"); 
                sql.append(" WHERE area_uni_code = ").append(areaUniCode);
                sql.append(" AND data_type='年度' AND statistics_type='公报'");
                sql.append(" AND bond_year <=").append(year);
                sql.append(" ORDER BY bond_year DESC");
                sql.append(" LIMIT ").append(page).append(",").append(limit);
                
                list = jdbcTemplate.query(sql.toString(),new BeanPropertyRowMapper<BondAreaData>(BondAreaData.class));
            }
            break;
        default:
            break;
        }
        
        if(null != list && list.size()>0) {
            for(BondAreaData data :list) {
                if(data.getGrowthGdp() != null) {
                    data.setGrowthGdp(data.getGrowthGdp().divide(new BigDecimal(0.01), 4, BigDecimal.ROUND_HALF_UP));//%值
                }
                if(data.getGrowthInduOutputVal() != null) {
                    data.setGrowthInduOutputVal(data.getGrowthInduOutputVal().divide(new BigDecimal(0.01), 4, BigDecimal.ROUND_HALF_UP));
                }
                if(data.getFinanceProduces() != null) {
                    data.setFinanceProduces(data.getFinanceProduces().divide(new BigDecimal(1), 4, BigDecimal.ROUND_HALF_UP));
                }
                if(data.getGrowthPop() != null) {
                    data.setGrowthPop(data.getGrowthPop().divide(new BigDecimal(0.01), 4, BigDecimal.ROUND_HALF_UP));
                }
            }
            
        } 
        
        return list==null? new ArrayList<BondAreaData>() :list;
    }
	
	public List<BondCity> details(String areaUniCode) {
		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append(
				"SELECT t.bond_year as bondYear,IFNULL(t.gdp,0) as gdp,IFNULL(t.tax_revenue,0) as taxRevenue,IFNULL(t.budget_revenue,0)/IFNULL(t.budgetary_expenditure,0) as financeProduces,IFNULL(urban_population,0) as urbanPopulation,IFNULL(t.gdp_industrial_added_value1,0) as gdpIndustrialAddedValue1,IFNULL(t.gdp_industrial_added_value2,0) as gdpIndustrialAddedValue2,IFNULL(t.gdp_industrial_added_value3,0) as gdpIndustrialAddedValue3 FROM  dmdb.t_bond_city  t");
		sqlStr.append("\n\tWHERE t.bond_type = 2 AND t.area_uni_code = " + areaUniCode);
		sqlStr.append("\n\tGROUP BY bond_year");
		sqlStr.append("\n\tORDER BY bond_year DESC");
		sqlStr.append("\n\tLIMIT 1,3");
		List<BondCity> list = jdbcTemplate.query(sqlStr.toString(),
				new BeanPropertyRowMapper<BondCity>(BondCity.class));
		return list;
	}

	public Map<String, Object> projectDetail(String bondUniCode) {
		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append("SELECT t.project_detail FROM dmdb.t_bond_city_project_detail t  WHERE  t.bond_uni_code = "
				+ bondUniCode);
		Map<String, Object> map;
		try {
			map = jdbcTemplate.queryForMap(sqlStr.toString());
		} catch (EmptyResultDataAccessException e) {
			LOG.info("bond_project_detail data is null , bond_uni_code = " + bondUniCode);
			map = null;
		}
		return map;
	}
	
	
	//返回数据排名查询结果
	public List<BondCitySort> compares(String areaUniCode, Integer bondYear, Integer bondQuarter,Integer bondMonth,String statisticsType,String dataType,Integer type ,String sortType,String sortColumn){
		List<BondCitySort> bondCitySorts = new ArrayList<>();
		
		Map<String, Object> bondAreaCodeMap = null;		
		try {
			//封装所有需要查询的areaUniCode值
			if(type ==1 || type ==2 || type == 3){
				bondAreaCodeMap = findAreaUniCode(areaUniCode);
			}		
			
		} catch (EmptyResultDataAccessException e) {
			// TODO: handle exception
			e.printStackTrace();
			return bondCitySorts ;
		}
		
		//返回封装的所有查询的areaUniCode,并标记所在的省和市
		List<AreaUniCodeParam> params = null;
		try {								
			switch(type){					
				case 1://找所有的省
					params = queryAreaUniCodes(type,bondAreaCodeMap,null);
					break;					
				case 2: //查找所有的市
					params = queryAreaUniCodes(type,bondAreaCodeMap,null);				
					break;					
				case 3: //查找所有的区/县
					params = queryAreaUniCodes(type,bondAreaCodeMap,null);				
					break;					
				case 4: //自定义查询
					params = queryAreaUniCodes(type,null,areaUniCode);				
					break;
				default:
					break;				
			}			
		} catch (EmptyResultDataAccessException e) {
			// TODO: handle exception
	
			e.printStackTrace();
			return bondCitySorts;
		}
		
		List<AreaDataDoc> areaDataDocs = null;
		try {
			//根据AreaUniCodeParam 从mongodb查找到所有的数据
			areaDataDocs = queryAllAreaEconomiesDoc(params,bondYear,bondQuarter,bondMonth,statisticsType,dataType);			
		} catch (EmptyResultDataAccessException e) {
			// TODO: handle exception
			e.printStackTrace();
			return bondCitySorts;
		}
		
		
		try {
			//将获得的数据进行排序		
			bondCitySorts = this.areaColumnTop(areaDataDocs,null,sortColumn,sortType);			
		}catch(EmptyResultDataAccessException e){
			e.printStackTrace();
			return bondCitySorts;
		}
		
		return bondCitySorts;		
	}
	
	//根据areaUniCode 找到其所在的省和市
	public Map<String, Object> findAreaUniCode(String areaUniCode){
		Map<String, Object> map = new HashMap<>();
		
		StringBuffer buffer = new StringBuffer();
		buffer.append("select area_name1 name1,area_name2 name2,area_name3 name3,area_name4 name4,");
		buffer.append("\n\tarea_uni_code areaUniCode,sub_uni_code subUniCode");
		buffer.append("\n\tfrom  bond_ccxe.pub_area_code");
		buffer.append("\n\twhere isvalid = 1 and area_name1 = '中国' ");
		buffer.append("\n\tand area_name2 is not null");
		buffer.append("\n\tand area_name2 not in ('香港','澳门','台湾')");
		buffer.append("\n\tand area_uni_code="+areaUniCode);
		
		Map<String, Object> queryMap= jdbcTemplate.queryForMap(buffer.toString());		
		if(null == queryMap){
			return null;
		}
		if(!StringUtils.isEmpty(queryMap.get("name4"))){
			//areaUniCode 表示区县一级
			double citySubUniCode = (double)queryMap.get("subUniCode"); //该县所在的市
			long citycode = (long)citySubUniCode;
			
			String sql = "select area_uni_code areaUniCode,sub_uni_code subUniCode from  bond_ccxe.pub_area_code where area_uni_code="+citySubUniCode;
			Map<String, Object> map2= jdbcTemplate.queryForMap(sql);
			double provinceSubUniCode= (double)map2.get("subUniCode");//表示该县所在的省	
			long code=(long)provinceSubUniCode;
			
			long countycode = Long.valueOf(areaUniCode);
			map.put("countyAreaUniCode",countycode);			
			map.put("cityAreaUniCode",citycode);			
			map.put("provinceAreaUniCode",code);
		}
		if(StringUtils.isEmpty(queryMap.get("name4")) && !StringUtils.isEmpty(queryMap.get("name3"))){			
			double provinceSubUniCode = (double)queryMap.get("subUniCode"); //该市所在的省
			long code=(long)provinceSubUniCode;
			
			long citycode = Long.valueOf(areaUniCode);
			map.put("cityAreaUniCode",citycode);			
			map.put("provinceAreaUniCode",code);						
		}
		if(StringUtils.isEmpty(queryMap.get("name4")) && StringUtils.isEmpty(queryMap.get("name3"))){
			//areaUniCode 表示省一级
			long code = Long.valueOf(areaUniCode);
			map.put("provinceAreaUniCode",code);
		}
				
		return map;
	}
		
	public List<AreaUniCodeParam> queryAreaUniCodes(Integer type,Map<String, Object> bondAreaCodeMap,String areaUniCode){
		List<AreaUniCodeParam> list = new ArrayList<>();
		
		//查找所有省
		if(type == 1){
			String sql = "select area_chi_name areaChiName,	"
						+"area_uni_code areaUniCode	,0  selected "
						+"from  bond_ccxe.pub_area_code where isvalid = 1 "
						+"and AREA_NAME1 = '中国' and area_name2 is not null and AREA_NAME2 not in ('香港','澳门','台湾') "
						+"and area_name3 is NULL and area_name4 is null";
			list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<AreaUniCodeParam>(AreaUniCodeParam.class));
			if(null !=list && list.size()>0){						
				for(AreaUniCodeParam param:list){					
					long code= (long)bondAreaCodeMap.get("provinceAreaUniCode");
					//BigInteger bigcode= new BigInteger(String.valueOf(code));
					if(param.getAreaUniCode()==code){
						param.setSelected(1);
						break;
					}
				}				
			}		
		}
		
		//查找该省下所有的市
		if(type==2){
			String sql = "select area_chi_name areaChiName,area_uni_code areaUniCode ,0  selected  "
						+"from bond_ccxe.pub_area_code where isvalid = 1 and AREA_NAME1 = '中国' and area_name2 is not null "
						+" and sub_uni_code ="+bondAreaCodeMap.get("provinceAreaUniCode");
			list= jdbcTemplate.query(sql, new BeanPropertyRowMapper<AreaUniCodeParam>(AreaUniCodeParam.class));
			if(null != list && list.size()>0 && !StringUtils.isEmpty(bondAreaCodeMap.get("cityAreaUniCode"))){
				for(AreaUniCodeParam param:list){					
					long code = (long)bondAreaCodeMap.get("cityAreaUniCode");
					BigInteger bigcode= new BigInteger(String.valueOf(code));
					if(param.getAreaUniCode()==code){
						param.setSelected(1);
						break;
					}
				}
			}
		}
		//查找该省下所有区/县
		if(type ==3){
			String sql = "select area_chi_name areaChiName,area_uni_code areaUniCode "
						+"from bond_ccxe.pub_area_code where isvalid = 1 and AREA_NAME1 = '中国' and area_name2 is not null "
						+"and sub_uni_code ="+bondAreaCodeMap.get("provinceAreaUniCode");
			List<Map<String, Object>> mapList = jdbcTemplate.queryForList(sql);
			if(null != mapList && mapList.size()>0){
				for(Map<String, Object> map:mapList){					
					String sql2 = "select area_chi_name areaChiName,area_uni_code areaUniCode ,0  selected  "
								+"from bond_ccxe.pub_area_code where isvalid = 1 and AREA_NAME1 = '中国' and area_name2 is not null "
								+"and sub_uni_code ="+map.get("areaUniCode");
					
					List<AreaUniCodeParam> params= jdbcTemplate.query(sql2, new BeanPropertyRowMapper<AreaUniCodeParam>(AreaUniCodeParam.class));
					if(null !=params && params.size()>0){
						list.addAll(params);
					}
				}
			}
			
			if(null != list && list.size()>0 && !StringUtils.isEmpty(bondAreaCodeMap.get("countyAreaUniCode"))){
				for(AreaUniCodeParam param:list){					
					long code = (long)bondAreaCodeMap.get("countyAreaUniCode");
					//BigInteger bigcode = new BigInteger(String.valueOf(code));
					if(param.getAreaUniCode()==code){
						param.setSelected(1);
						break;
					}
				}
			}
		}
		//查找自定义区域
		if(type==4){
			String[] strs = areaUniCode.split(",");
			if(null !=strs && strs.length>0){
				for(int i=0;i<strs.length;i++){
					AreaUniCodeParam param = new AreaUniCodeParam();
					param.setAreaChiName(null);
					param.setAreaUniCode(Long.valueOf(strs[i]));
					param.setSelected(0);
					
					list.add(param);
				}
			}
		}
		
		return list;
	}
	
	//查找到所有的区域经济指标
	public List<AreaDataDoc> queryAllAreaEconomiesDoc(List<AreaUniCodeParam> list,Integer bondYear,Integer bondQuarter,Integer bondMonth,String statisticsType,String dataType){
		List<AreaDataDoc> docList = new ArrayList<>();
				
		for(AreaUniCodeParam param:list){
			AreaEconomiesDoc doc  = new AreaEconomiesDoc();
			Query query = new Query();
			if(null != bondYear &&bondYear !=0){
				query.addCriteria(Criteria.where("bondYear").is(bondYear));
			}
			if(null!=bondQuarter && bondQuarter !=0){
				query.addCriteria(Criteria.where("bondQuarter").is(bondQuarter));
			}
			if(null !=bondMonth && bondMonth!=0){
				query.addCriteria(Criteria.where("bondMonth").is(bondMonth));
			}
			if(!StringUtils.isEmpty(dataType)){
				query.addCriteria(Criteria.where("dataType").is(dataType));
			}
			if(!StringUtils.isEmpty(statisticsType)){
				query.addCriteria(Criteria.where("statisticsType").is(statisticsType));
			}
			
			query.addCriteria(Criteria.where("areaUniCode").is(param.getAreaUniCode()));
			
			System.out.println(param.getAreaChiName()+":"+param.getAreaUniCode()+"-"+"bondYear:"+bondYear+"-"+"dataType:"+dataType);
			
			doc = mongoTemplate.findOne(query, AreaEconomiesDoc.class);
			if(null != doc){
				AreaDataDoc  areaDataDoc= convertEntity(doc);
				if(param.getSelected()==1){
					areaDataDoc.setSelected(1);
				}
				docList.add(areaDataDoc);
			}						
		}
						
		return docList;
	}
	
	//将AreaEconomiesDoc 转换为AreaDataDoc
	public AreaDataDoc convertEntity(AreaEconomiesDoc areaEconomiesDoc){
		AreaDataDoc doc = new AreaDataDoc();
		doc.setId(areaEconomiesDoc.getId());
		doc.setAreaUniCode(areaEconomiesDoc.getAreaUniCode());
		doc.setBondYear(areaEconomiesDoc.getBondYear());
		doc.setBondMonth(areaEconomiesDoc.getBondMonth());
		doc.setBondQuarter(areaEconomiesDoc.getBondQuarter());
		doc.setAreaChiName(areaEconomiesDoc.getAreaChiName());
		doc.setDataType(areaEconomiesDoc.getDataType());
		doc.setStatisticsType(areaEconomiesDoc.getStatisticsType());
		
		List<EconomieIndicator> list = areaEconomiesDoc.getEconomieIndicators();
		if(null !=list && list.size()>0){		
			for(EconomieIndicator indicator:list){
				if(indicator.getField().equals("permanent_resident_pop")){
					doc.setPermanentResidentPop(indicator.getValue());
				}
				if(indicator.getField().equals("domicile_pop")){
					doc.setDomicilePop(indicator.getValue());
				}
				if(indicator.getField().equals("urban_pop")){
					doc.setUrbanPop(indicator.getValue());
				}
				if(indicator.getField().equals("rural_pop")){
					doc.setRuralPop(indicator.getValue());
				}
				if(indicator.getField().equals("air_cargo_throughput")){
					doc.setAirCargoThroughput(indicator.getValue());
				}
				if(indicator.getField().equals("air_freight_transport_volume")){
					doc.setAirFreightTransportVolume(indicator.getValue());
				}
				if(indicator.getField().equals("air_passenger_throughput")){
					doc.setAirPassengerThroughput(indicator.getValue());
				}
				if(indicator.getField().equals("air_passenger_volume")){
					doc.setAirPassengerVolume(indicator.getValue());
				}
				if(indicator.getField().equals("seaports_throughput")){
					doc.setSeaportsThroughput(indicator.getValue());
				}
				if(indicator.getField().equals("expressway_mileage")){
					doc.setExpresswayMileage(indicator.getValue());
				}
				if(indicator.getField().equals("railway_mileage")){
					doc.setRailwayMileage(indicator.getValue());
				}
				if(indicator.getField().equals("urbanization_rate")){
					doc.setUrbanizationRate(indicator.getValue());
				}
				if(indicator.getField().equals("per_exp_urban_residents")){
					doc.setPerExpUrbanResidents(indicator.getValue());
				}
				
				if(indicator.getField().equals("gdp")){
					doc.setGdp(indicator.getValue());
				}
				if(indicator.getField().equals("growth_gdp")){
					doc.setGrowthGdp(indicator.getValue());
				}
				if(indicator.getField().equals("gdp_per_capita")){
					doc.setGdpPerCapita(indicator.getValue());
				}
				if(indicator.getField().equals("growth_gdp_per_capita")){
					doc.setGrowthGdpPerCapita(indicator.getValue());
				}
				
				if(indicator.getField().equals("gdp_add_val_primary_indu")){
					doc.setGdpAddValPrimaryIndu(indicator.getValue());
				}
				if(indicator.getField().equals("growth_add_val_primary_indu")){
					doc.setGrowthAddValPrimaryIndu(indicator.getValue());
				}
				if(indicator.getField().equals("gdp_add_val_secondary_indu")){
					doc.setGdpAddValSecondaryIndu(indicator.getValue());
				}
				if(indicator.getField().equals("growth_add_val_secondary_indu")){
					doc.setGrowthAddValSecondaryIndu(indicator.getValue());
				}
				if(indicator.getField().equals("gdp_add_val_tertiary_indu")){
					doc.setGdpAddValTertiaryIndu(indicator.getValue());
				}
				if(indicator.getField().equals("growth_add_val_tertiary_indu")){
					doc.setGrowthAddValTertiaryIndu(indicator.getValue());
				}
				
				if(indicator.getField().equals("indu_add_val")){
					doc.setInduAddVal(indicator.getValue());
				}
				if(indicator.getField().equals("growth_indu_output_val")){
					doc.setGrowthInduOutputVal(indicator.getValue());
				}
				if(indicator.getField().equals("invest_Fix_assets_whole_society")){
					doc.setInvestFixAssetsWholeSociety(indicator.getValue());
				}
				if(indicator.getField().equals("invest_Fix_assets")){
					doc.setInvestFixAssets(indicator.getValue());
				}
				if(indicator.getField().equals("total_retail_sales_consumer_goods")){
					doc.setTotalRetailSalesConsumerGoods(indicator.getValue());
				}
				
				if(indicator.getField().equals("total_retail_sales_per_consumer_goods")){
					doc.setTotalRetailSalesPerConsumerGoods(indicator.getValue());
				}
				if(indicator.getField().equals("total_import_export_dollar")){
					doc.setTotalImportExportDollar(indicator.getValue());
				}
				if(indicator.getField().equals("total_import_export_per_dollar")){
					doc.setTotalImportExportPerDollar(indicator.getValue());
				}
				if(indicator.getField().equals("total_import_export")){
					doc.setTotalImportExport(indicator.getValue());
				}
				
				if(indicator.getField().equals("total_import_per_export")){
					doc.setTotalImportPerExport(indicator.getValue());
				}
				if(indicator.getField().equals("foreign_currency_deposits_pro")){
					doc.setForeignCurrencyDepositsPro(indicator.getValue());
				}
				if(indicator.getField().equals("rmb_deposits_pro")){
					doc.setRmbDepositsPro(indicator.getValue());
				}
				if(indicator.getField().equals("foreign_currency_loan_bal_pro")){
					doc.setForeignCurrencyLoanBalPro(indicator.getValue());
				}
				if(indicator.getField().equals("rmb_loan_bal_pro")){
					doc.setRmbLoanBalPro(indicator.getValue());
				}
				if(indicator.getField().equals("gov_receipts")){
					doc.setGovReceipts(indicator.getValue());
				}
				if(indicator.getField().equals("pub_gov_receipts")){
					doc.setPubGovReceipts(indicator.getValue());
				}
				if(indicator.getField().equals("tax_rev")){
					doc.setTaxRev(indicator.getValue());
				}
				if(indicator.getField().equals("nontax_rev")){
					doc.setNontaxRev(indicator.getValue());
				}
				if(indicator.getField().equals("tax_rev_proportion")){
					doc.setTaxRevProportion(indicator.getValue());
				}
				if(indicator.getField().equals("pro_pub_budget_rev")){
					doc.setProPubBudgetRev(indicator.getValue());
				}
				if(indicator.getField().equals("pro_pub_budget_tax_rev")){
					doc.setProPubBudgetTaxRev(indicator.getValue());
				}
				if(indicator.getField().equals("pro_pub_budget_nontax_rev")){
					doc.setProPubBudgetNontaxRev(indicator.getValue());
				}
				if(indicator.getField().equals("tax_rev_pro_proportion")){
					doc.setTaxRevProportion(indicator.getValue());
				}
				if(indicator.getField().equals("pub_budget_rev_pro_cor_lev")){
					doc.setPubBudgetRevProCorLev(indicator.getValue());
				}
				if(indicator.getField().equals("tax_rev_pro_cor_lev")){
					doc.setTaxRevProCorLev(indicator.getValue());
				}
				if(indicator.getField().equals("nontax_rev_pro_cor_lev")){
					doc.setNontaxRevProCorLev(indicator.getValue());
				}
				if(indicator.getField().equals("tax_rev_pro_cor_proportion")){
					doc.setTaxRevProCorProportion(indicator.getValue());
				}
				if(indicator.getField().equals("grant_higher_authority")){
					doc.setGrantHigherAuthority(indicator.getValue());
				}
				if(indicator.getField().equals("return_rev")){
					doc.setReturnRev(indicator.getValue());
				}
				if(indicator.getField().equals("gen_transfer_pay_rev")){
					doc.setGenTransferPayRev(indicator.getValue());
				}
				if(indicator.getField().equals("spec_transfer_pay_rev")){
					doc.setSpecTransferPayRev(indicator.getValue());
				}
				if(indicator.getField().equals("gov_fund_rev")){
					doc.setGovFundRev(indicator.getValue());
				}
				if(indicator.getField().equals("whole_pro_gov_fund_budget_rev")){
					doc.setWholeProGovFundBudgetRev(indicator.getValue());
				}
				if(indicator.getField().equals("pro_gov_fund_budget_rev")){
					doc.setProGovFundBudgetRev(indicator.getValue());
				}
				if(indicator.getField().equals("fund_budget_rev_pro_cor_lev")){
					doc.setFundBudgetRevProCorLev(indicator.getValue());
				}
				
				if(indicator.getField().equals("land_leasing_rev")){
					doc.setLandLeasingRev(indicator.getValue());
				}
				if(indicator.getField().equals("extra_budget_finance_spec_account_rev")){
					doc.setExtraBudgetFinanceSpecAccountRev(indicator.getValue());
				}
				if(indicator.getField().equals("fiscal_exp")){
					doc.setFiscalExp(indicator.getValue());
				}
				if(indicator.getField().equals("pub_fiscal_exp")){
					doc.setPubFiscalExp(indicator.getValue());
				}			
				if(indicator.getField().equals("pro_budget_exp")){
					doc.setProBudgetExp(indicator.getValue());
				}
				if(indicator.getField().equals("budget_exp_pro_cor_lev")){
					doc.setBudgetExpProCorLev(indicator.getValue());
				}
				if(indicator.getField().equals("gov_fund_exp")){
					doc.setGovFundExp(indicator.getValue());
				}
				if(indicator.getField().equals("whole_pro_gov_fund_budget_exp")){
					doc.setWholeProGovFundBudgetExp(indicator.getValue());
				}
				if(indicator.getField().equals("pro_gov_fund_budget_exp")){
					doc.setProGovFundBudgetExp(indicator.getValue());
				}
				if(indicator.getField().equals("fund_budget_exp_pro_cor_lev")){
					doc.setFundBudgetExpProCorLev(indicator.getValue());
				}
				
				if(indicator.getField().equals("extra_budget_finance_spec_account_exp")){
					doc.setExtraBudgetFinanceSpecAccountExp(indicator.getValue());
				}
				if(indicator.getField().equals("finance_self_sufficiency_rate")){
					doc.setFinanceSelfSufficiencyRate(indicator.getValue());
				}
				if(indicator.getField().equals("total_issuance_local_bonds")){
					doc.setTotalIssuanceLocalBonds(indicator.getValue());
				}
				if(indicator.getField().equals("gov_debt")){
					doc.setGovDebt(indicator.getValue());
				}
				if(indicator.getField().equals("gov_debt_gen")){
					doc.setGovDebtGen(indicator.getValue());
				}				
				if(indicator.getField().equals("gov_debt_spec")){
					doc.setGovDebtSpec(indicator.getValue());
				}
				if(indicator.getField().equals("gov_debt_limit")){
					doc.setGovDebtLimit(indicator.getValue());
				}
				if(indicator.getField().equals("gov_debt_limit_gen")){
					doc.setGovDebtLimitGen(indicator.getValue());
				}
				if(indicator.getField().equals("gov_debt_limit_spec")){
					doc.setGovDebtLimitSpec(indicator.getValue());
				}
				
				if(indicator.getField().equals("debt_bal_pro_cor_lev")){
					doc.setDebtBalProCorLev(indicator.getValue());
				}
				if(indicator.getField().equals("debt_bal_pro_cor_lev_gen")){
					doc.setDebtBalProCorLevGen(indicator.getValue());
				}
				if(indicator.getField().equals("debt_bal_pro_cor_lev_spec")){
					doc.setDebtBalProCorLevSpec(indicator.getValue());
				}
				if(indicator.getField().equals("debt_limit_pro_cor_lev")){
					doc.setDebtLimitProCorLev(indicator.getValue());
				}
				if(indicator.getField().equals("debt_limit_pro_cor_lev_gen")){
					doc.setDebtLimitProCorLevGen(indicator.getValue());
				}
				if(indicator.getField().equals("debt_limit_pro_cor_lev_spec")){
					doc.setDebtLimitProCorLevSpec(indicator.getValue());
				}
				if(indicator.getField().equals("whole_pro_gov_debt_bal")){
					doc.setWholeProGovDebtBal(indicator.getValue());
				}
				if(indicator.getField().equals("whole_pro_gov_debt_bal_gen")){
					doc.setWholeProGovDebtBalGen(indicator.getValue());
				}
				if(indicator.getField().equals("whole_pro_gov_debt_bal_spec")){
					doc.setWholeProGovDebtBalSpec(indicator.getValue());
				}
			}
		}
		
		return doc;
	}
	
	private StringBuffer StringBufferAppendCode(StringBuffer sqlStr, String areaUniCode) {
		String[] codelist = areaUniCode.split(",");
		for (int i = 0; i < codelist.length; i++) {
			sqlStr.append(codelist[i]);
			if (i < codelist.length - 1) {
				sqlStr.append(",");
			}
		}
		sqlStr.append(")");
		return sqlStr;
	}
	
	/**
	 * 
	 * columnTop:(获取区域数据字段排名)
	 * @param  @param list
	 * @param  @param needColumns: 需要排名的字段列表
	 * @param  @param sortColumn：返回结果按那个字段排序
	 * @param  @param sortType：排序方式：DESC ASC
	 * @param  @return    设定文件
	 * @return List<BondCity2>    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	 */
	@SuppressWarnings("unchecked")
    private List<BondCitySort> areaColumnTop(List<AreaDataDoc> list, String[] needColumns, String sortColumn,  String sortType) {
	    if(null == list) {
	        return null;
	    }
	    if(null == needColumns) {
	        needColumns = new String[] {
	            "permanentResidentPop", "domicilePop", "urbanPop", "ruralPop", "airCargoThroughput",
	            "airFreightTransportVolume", "airPassengerThroughput", "airPassengerVolume", "seaportsThroughput", "expresswayMileage",    
	            "railwayMileage", "urbanizationRate", "perExpUrbanResidents", "gdp", "growthGdp","gdpPerCapita","growthGdpPerCapita",
	            "gdpAddValPrimaryIndu",  "growthAddValPrimaryIndu","gdpAddValSecondaryIndu","growthAddValSecondaryIndu",  
                "gdpAddValTertiaryIndu","growthAddValTertiaryIndu", "induAddVal", "growthInduOutputVal",                           
                "investFixAssetsWholeSociety", "investFixAssets", "totalRetailSalesConsumerGoods", "totalRetailSalesPerConsumerGoods", "totalImportExportDollar",    
                "totalImportExportPerDollar", "totalImportExport", "totalImportPerExport", "foreignCurrencyDepositsPro", "rmbDepositsPro",   
                "foreignCurrencyLoanBalPro", "rmbLoanBalPro", "govReceipts", "pubGovReceipts", "taxRev",   
                "nontaxRev", "taxRevProportion", "proPubBudgetRev", "proPubBudgetTaxRev", "proPubBudgetNontaxRev", 
                "taxRevProProportion", "pubBudgetRevProCorLev", "taxRevProCorLev", "nontaxRevProCorLev", "taxRevProCorProportion ", 
                "grantHigherAuthority", "returnRev", "genTransferPayRev", "specTransferPayRev", "govFundRev", 
                "wholeProGovFundBudgetRev", "proGovFundBudgetRev", "fundBudgetRevProCorLev", "landLeasingRev", "extraBudgetFinanceSpecAccountRev", 
                "fiscalExp", "pubFiscalExp", "proBudgetExp", "budgetExpProCorLev", "govFundExp", 
                "wholeProGovFundBudgetExp", "proGovFundBudgetExp", "fundBudgetExpProCorLev", "extraBudgetFinanceSpecAccountExp", "financeSelfSufficiencyRate", 
                "totalIssuanceLocalBonds", "govDebt", "govDebtGen", "govDebtSpec", "wholeProGovDebtBal", 
                "wholeProGovDebtBalGen", "wholeProGovDebtBalSpec", "debtBalProCorLev", "debtBalProCorLevGen", "debtBalProCorLevSpec", 
                "govDebtLimit", "govDebtLimitGen", "govDebtLimitSpec", "debtLimitProCorLev", "debtLimitProCorLevGen", 
                "debtLimitProCorLevSpec"   
	        };
	    }
	    
	    List<AreaDataDoc> columnlist = new ArrayList<AreaDataDoc>(list);
	    if(StringUtils.isEmpty(sortColumn)) {
	        sortColumn ="permanentResidentPop";//默认排序字段
        }
	    if(StringUtils.isEmpty(sortType)) {
	        sortType ="DESC";
	    }
	    // 排序规则
        BondCityComparator comparator = new BondCityComparator(sortColumn);//降序排列
        Collections.sort(columnlist, comparator);
	    
        List<BondCitySort> resultList = new ArrayList<BondCitySort>();
        BondCitySort bc22 = null;
        for (int i = 0; i < list.size(); i++) {
            // 创建对象并将属性封装
            bc22 = new BondCitySort();
            bc22.setSelected(list.get(i).getSelected()); //
            bc22.setId(list.get(i).getId());
            bc22.setAreaUniCode(list.get(i).getAreaUniCode());
            bc22.setAreaChiName(list.get(i).getAreaChiName());
            bc22.setBondYear(String.valueOf(list.get(i).getBondYear()));
            bc22.setBondMonth(String.valueOf(list.get(i).getBondMonth()));
            bc22.setBondQuarter(String.valueOf(list.get(i).getBondQuarter()));
            resultList.add(bc22);
        }
        final Integer size = resultList.size();

        // 创建线程池
        ExecutorService EXEC = Executors.newCachedThreadPool();
        List<Callable<Map<String, BondCity3>>> tasks = new ArrayList<Callable<Map<String, BondCity3>>>();

        // 增加多个task,每个task处理一列数据
        for (String columnName : needColumns) {
            Callable<Map<String, BondCity3>> c = new Callable<Map<String, BondCity3>>() {
                @Override
                public Map<String, BondCity3> call() throws Exception {
                    return areaColumn(new ArrayList<AreaDataDoc>(list), columnName);
                }
            };
            tasks.add(c);
        }
        try {
            // 执行任务
            List<Future<Map<String, BondCity3>>> results = EXEC.invokeAll(tasks);
            // 每一个对象相当于一整列
            for (Future<Map<String, BondCity3>> r : results) {
                Map<String, BondCity3> map = r.get();
                Iterator<String> iter = map.keySet().iterator();
                while (iter.hasNext()) {
                    String columnName = (String) iter.next();
                    String[] columns = columnName.split(":");
                    for (int i = 0; i < size; i++) {
                        // 数据绑定
                        BondCitySort bc2 = resultList.get(i);
                        // 对象ID跟遍历出来的对象ID比较，相等则赋值，并替换
                        if (bc2.getId() == Integer.parseInt(columns[0])) {
                            BeanUtil.setProperty(bc2, columns[1], map.get(columnName));
                            resultList.set(i, bc2);
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOG.error("failed to build bond basic data.", e);
            e.printStackTrace();
        }
        //对排名按排序结果排序
        List<BondCitySort> sorList = new ArrayList<BondCitySort>();
        if("DESC".equals(sortType.toUpperCase())) {
            for (int i = 0,csize =columnlist.size(); i < csize; i++) {
                for (int j = 0,rsize =resultList.size(); j < rsize; j++) {
                    if(columnlist.get(i).getId().equals( resultList.get(j).getId())) {
                        sorList.add(resultList.get(j));
                        break;
                    }
                }
            }
        } else {
            for (int i = columnlist.size()-1; i >= 0; i--) {//升序
                for (int j = 0,rsize =resultList.size(); j < rsize; j++) {
                    if(columnlist.get(i).getId().equals( resultList.get(j).getId())) {
                        sorList.add(resultList.get(j));
                        break;
                    }
                }
            }
        }
        return sorList;
    }
	
	@SuppressWarnings("unchecked")
    private Map<String, BondCity3> areaColumn(List<AreaDataDoc> list, String columnName) {

        // 排序规则
        BondCityComparator comparator = new BondCityComparator(columnName);
        // 排序
        Collections.sort(list, comparator);

        Map<String, BondCity3> resultMap = new HashMap<String, BondCity3>();
        BondCity3 bondCity3 = null;
        AreaDataDoc bondCity = null;
        for (int i = 1; i <= list.size(); i++) {
            bondCity = list.get(i - 1);
            bondCity3 = new BondCity3();
            try {
                if (BeanUtil.getProperty(bondCity, columnName) != null) {
                    // 排名
                    bondCity3.setTop(i);
                    // 实际值
                    bondCity3.setValue(BeanUtil.getProperty(bondCity, columnName).toString());
                }
                resultMap.put(bondCity.getId() + ":" + columnName, bondCity3);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IntrospectionException e) {
                e.printStackTrace();
            }
        }
        return resultMap;
    }

	

	
//	@SuppressWarnings("unchecked")
//	private Map<String, BondCity3> column(List<BondCity> list, String columnName) {
//
//		// 排序规则
//		BondCityComparator comparator = new BondCityComparator(columnName);
//		// 排序
//		Collections.sort(list, comparator);
//
//		Map<String, BondCity3> resultMap = new HashMap<String, BondCity3>();
//		BondCity3 bondCity3 = null;
//		BondCity bondCity = null;
//		for (int i = 1; i <= list.size(); i++) {
//			bondCity = list.get(i - 1);
//			bondCity3 = new BondCity3();
//			try {
//				if (BeanUtil.getProperty(bondCity, columnName) != null) {
//					// 排名
//					bondCity3.setTop(i);
//					// 实际值
//					bondCity3.setValue(BeanUtil.getProperty(bondCity, columnName).toString());
//				}
//				resultMap.put(bondCity.getId() + ":" + columnName, bondCity3);
//			} catch (IllegalArgumentException e) {
//				e.printStackTrace();
//			} catch (IllegalAccessException e) {
//				e.printStackTrace();
//			} catch (InvocationTargetException e) {
//				e.printStackTrace();
//			} catch (IntrospectionException e) {
//				e.printStackTrace();
//			}
//		}
//		return resultMap;
//	}

	/**
	 * 处理排序
	 * 
	 * @param sort
	 * @param sqlStr
	 * @return
	 */
	public StringBuffer sort(String sort, StringBuffer sqlStr) {
		sqlStr.append("\n\t order by \t");
		String[] sortlist = sort.split(",");
		for (int i = 0; i < sortlist.length; i++) {
			String[] sortPars = sortlist[i].split(":");
			String sortField = sortPars[0];
			String sortDir = sortPars[1].toLowerCase().startsWith("des") ? "desc" : "asc";
			sqlStr.append(Util.camelToUnderline(sortField) + "\t" + sortDir);
			if (i < sortlist.length - 1) {
				sqlStr.append(",");
			}
		}
		return sqlStr;
	}

	public String addCustomArea(BigInteger userId, BigInteger bondYear, BigInteger bondQuarter, String areaUniCode) {

		/** 保存用户当前操作记录 */
		String[] codelist = areaUniCode.split(",");

		String str = "";
		for (int i = 0; i < codelist.length; i++) {
			str += codelist[i];
			if (i < codelist.length - 1) {
				str += ",";
			}
		}
		BondCustomArea vo = new BondCustomArea(str, userId, bondYear, bondQuarter, userId);

		bondCustomAreaRepository.save(vo);

		return "OK";
	}

	public BondCustomArea findCustomArea(BigInteger userId) {
		return bondCustomAreaRepository.findOne(userId);
	}

	public List<BondArea> bondArea(Integer type, String subUniCode) {
		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append("select area_name1,area_name2,area_uni_code,sub_uni_code");
		sqlStr.append("\n\t from dmdb.t_bond_area t");
		sqlStr.append("\n\t where 1=1");

		if (!StringUtils.isEmpty(subUniCode)) {
			sqlStr.append("\t and sub_uni_code = '" + subUniCode + "'");
		}
		if (type == 2) {
			sqlStr.append("\t and sub_uni_code IS NULL");
		}
		if (type == 1) {
			sqlStr.append("\t GROUP BY IFNULL(NULL,area_uni_code)");
		}
		sqlStr.append("\n\t order by \t");
		sqlStr.append("\n\t CONVERT(area_name1 USING gbk ) COLLATE gbk_chinese_ci  asc\t");
		List<BondArea> list = jdbcTemplate.query(sqlStr.toString(),
				new BeanPropertyRowMapper<BondArea>(BondArea.class));
		return list;
	}

	@Deprecated 
	public BondDetailDoc isBondCity(Long uniCode, Integer type) {

		BondDetailDoc vo = null;
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT t.*,e.city_type AS cityType,q.area_name1 AS areaName1,q.area_name2 AS areaName2,q.area_uni_code AS areaCode2");
		sb.append("\n\t,q.sub_uni_code AS areaCode1 FROM (");
		sb.append("\n\tSELECT c.COM_CHI_NAME AS comName,c.COM_UNI_CODE comUniCode,");
		sb.append("\n\tIFNULL(e2.area_uni_code,IFNULL(e1.area_uni_code,e0.area_uni_code))  AS area_uni_code");
		sb.append("\n\t,CASE WHEN b.com_uni_code IS NOT NULL THEN 1 ELSE 0 END AS munInvest ");
		sb.append("\n\tFROM bond_ccxe.d_pub_com_info_2 c ");
		sb.append("\n\tLEFT JOIN dmdb.t_bond_com_ext a ON a.com_uni_code = c.com_uni_code ");
		sb.append("\n\tLEFT JOIN dmdb.t_bond_city_annex b  ON a.com_uni_code = b.com_uni_code  ");
		sb.append("\n\tLEFT JOIN bond_ccxe.pub_area_code d ON d.area_uni_code = c.AREA_UNI_CODE");
		sb.append("\n\tLEFT JOIN bond_ccxe.pub_area_code d1 ON d1.area_uni_code = d.sub_uni_code");
		sb.append("\n\tLEFT JOIN bond_ccxe.pub_area_code d2 ON d2.area_uni_code = d1.sub_uni_code");
		sb.append("\n\tLEFT JOIN dmdb.t_bond_area e2  ON d.area_uni_code = e2.area_uni_code");
		sb.append("\n\tLEFT JOIN dmdb.t_bond_area e1  ON d1.area_uni_code = e1.area_uni_code");
		sb.append("\n\tLEFT JOIN dmdb.t_bond_area e0  ON d2.area_uni_code = e0.area_uni_code");
		sb.append("\n\tWHERE c.com_uni_code = ");
		switch (type) {
		case 1:
			sb.append("\n\t(");
			sb.append("\n\tSELECT org_uni_code FROM bond_ccxe.d_bond_iss_info_1 WHERE bond_uni_code = "+uniCode);
			sb.append("\n\tUNION");
			sb.append("\n\tSELECT org_uni_code FROM bond_ccxe.d_bond_iss_info_2 WHERE bond_uni_code = "+uniCode);
			sb.append("\n\tUNION");
			sb.append("\n\tSELECT org_uni_code FROM bond_ccxe.d_bond_iss_info_3 WHERE bond_uni_code = "+uniCode);
			sb.append("\n\tUNION");
			sb.append("\n\tSELECT org_uni_code FROM bond_ccxe.d_bond_iss_info_5 WHERE bond_uni_code = "+uniCode);	
			sb.append("\n\t)");
			break;
		case 2:
			sb.append("\n\t "+uniCode);
			break;
		}
		sb.append("\n\t) t ");
		sb.append("\n\tLEFT JOIN dmdb.t_bond_city_type e ON e.area_uni_code = t.area_uni_code");
		sb.append("\n\tLEFT JOIN dmdb.t_bond_area q ON q.area_uni_code =  t.area_uni_code AND q.sub_uni_code IS NOT NULL");
		sb.append("\n\tGROUP BY area_uni_code");
		sb.append("\n\tLIMIT 1");
		try{
		vo = jdbcTemplate.queryForObject(sb.toString(),
				new BeanPropertyRowMapper<BondDetailDoc>(BondDetailDoc.class));
		}catch (Exception e) {
			// TODO: handle exception
		}
		return vo;
	}
	
	/**
	 * 
	 * isAreaBondCity:(获取区域名称、是否城投、所属省或直辖市code及名称)
	 * @param  @param uniCode
	 * @param  @param type
	 * @param  @return    设定文件
	 * @return BondDetailDoc    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	 */
	public BondDetailDoc isAreaBondCity(Long uniCode, Integer type) {
        BondDetailDoc vo = null;
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT");
        sb.append("\n\t c.COM_CHI_NAME AS comName,");
        sb.append("\n\t c.COM_UNI_CODE comUniCode,");
        sb.append("\n\t c.AREA_UNI_CODE1 AS areaCode1,");//省/直辖市code
        sb.append("\n\t c.AREA_UNI_CODE AS areaCode2,");//市/县code
        sb.append("\n\t CASE");
        sb.append("\n\t     WHEN b.com_uni_code IS NOT NULL THEN 1");
        sb.append("\n\t     ELSE 0");
        sb.append("\n\t END AS munInvest,");
        sb.append("\n\t CASE");
        sb.append("\n\t     WHEN c.area_uni_code IS NULL THEN NULL");
        sb.append("\n\t     ELSE IFNULL(e.city_type, '区/县')");
        sb.append("\n\t END AS cityType,");
        sb.append("\n\t d.AREA_CHI_NAME AS areaName1,");//省
        sb.append("\n\t d1.AREA_CHI_NAME AS areaName2");//地级市\区县
        sb.append("\n\t FROM");
        sb.append("\n\t     bond_ccxe.d_pub_com_info_2 c");
        sb.append("\n\t LEFT JOIN dmdb.t_bond_com_ext a ON a.com_uni_code = c.com_uni_code");
        sb.append("\n\t LEFT JOIN dmdb.t_bond_city_annex b ON a.com_uni_code = b.com_uni_code");
        sb.append("\n\t LEFT JOIN dmdb.t_bond_city_type e ON e.area_uni_code = c.area_uni_code");
        sb.append("\n\t LEFT JOIN bond_ccxe.pub_area_code d ON d.AREA_UNI_CODE = c.AREA_UNI_CODE1");
        sb.append("\n\t LEFT JOIN bond_ccxe.pub_area_code d1 ON d1.AREA_UNI_CODE = c.AREA_UNI_CODE");
        sb.append("\n\t WHERE c.com_uni_code = ");
        
        switch (type) {
        case 1:
            sb.append("\n\t(");
            sb.append("\n\tSELECT org_uni_code FROM bond_ccxe.d_bond_iss_info_1 WHERE bond_uni_code = "+uniCode);
            sb.append("\n\tUNION");
            sb.append("\n\tSELECT org_uni_code FROM bond_ccxe.d_bond_iss_info_2 WHERE bond_uni_code = "+uniCode);
            sb.append("\n\tUNION");
            sb.append("\n\tSELECT org_uni_code FROM bond_ccxe.d_bond_iss_info_3 WHERE bond_uni_code = "+uniCode);
            sb.append("\n\tUNION");
            sb.append("\n\tSELECT org_uni_code FROM bond_ccxe.d_bond_iss_info_5 WHERE bond_uni_code = "+uniCode);   
            sb.append("\n\t)");
            break;
        case 2:
            sb.append("\n\t "+uniCode);
            break;
        }
        sb.append("\n\t GROUP BY c.AREA_UNI_CODE");
        sb.append("\n\t LIMIT 1");
        try{
        vo = jdbcTemplate.queryForObject(sb.toString(),
                new BeanPropertyRowMapper<BondDetailDoc>(BondDetailDoc.class));
        }catch (Exception e) {
            // TODO: handle exception
        }
        return vo;
    }

	static class Util {
		public static final char UNDERLINE = '_';

		public static String camelToUnderline(String param) {
			if (param == null || "".equals(param.trim())) {
				return "";
			}
			int len = param.length();
			StringBuilder sb = new StringBuilder(len);
			for (int i = 0; i < len; i++) {
				char c = param.charAt(i);
				if (Character.isUpperCase(c)) {
					sb.append(UNDERLINE);
					sb.append(Character.toLowerCase(c));
				} else {
					sb.append(c);
				}
			}
			return sb.toString();
		}

		public static String underlineToCamel(String param) {
			if (param == null || "".equals(param.trim())) {
				return "";
			}
			int len = param.length();
			StringBuilder sb = new StringBuilder(len);
			for (int i = 0; i < len; i++) {
				char c = param.charAt(i);
				if (c == UNDERLINE) {
					if (++i < len) {
						sb.append(Character.toUpperCase(param.charAt(i)));
					}
				} else {
					sb.append(c);
				}
			}
			return sb.toString();
		}

		public static String underlineToCamel2(String param) {
			if (param == null || "".equals(param.trim())) {
				return "";
			}
			StringBuilder sb = new StringBuilder(param);
			Matcher mc = Pattern.compile("_").matcher(param);
			int i = 0;
			while (mc.find()) {
				int position = mc.end() - (i++);
				// String.valueOf(Character.toUpperCase(sb.charAt(position)));
				sb.replace(position - 1, position + 1, sb.substring(position, position + 1).toUpperCase());
			}
			return sb.toString();
		}
	}
	
	/**
	 * 
	 * getQuarter:(获取季度)
	 * @param  @param month
	 * @param  @return    设定文件
	 * @return String    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	 */
	public static int getQuarter(int month){     
        int quarter=1;    
        if(month>=1&&month<=3){     
            quarter=1;     
        }     
        if(month>=4&&month<=6){     
            quarter=2;     
        }     
        if(month>=7&&month<=9){     
            quarter=3;      
        }     
        if(month>=10&&month<=12){     
            quarter=4;     
        }
        return quarter;
    }
	
	/**
	 * 
	 * convertProportion:(计算)
	 * @param  @param list
	 * @param  @return    设定文件
	 * @return List<BondAreaData>    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	 */
	private void convertProportion(List<BondAreaData> list) {
	    for(BondAreaData d : list) {
	        BigDecimal gdpAddValPrimaryIndu = d.getGdpAddValPrimaryIndu()==null? new BigDecimal(0):d.getGdpAddValPrimaryIndu();
	        BigDecimal gdpAddValSecondaryIndu = d.getGdpAddValSecondaryIndu()==null? new BigDecimal(0):d.getGdpAddValSecondaryIndu();
            BigDecimal gdpAddValTertiaryIndu = d.getGdpAddValTertiaryIndu()==null? new BigDecimal(0):d.getGdpAddValTertiaryIndu();
            BigDecimal total = gdpAddValPrimaryIndu.add(gdpAddValSecondaryIndu).add(gdpAddValTertiaryIndu);
            if(total.compareTo(new BigDecimal(0)) == 0) {
                continue;
            }
            BigDecimal prProp = gdpAddValPrimaryIndu.divide(total,4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).divide(new BigDecimal(1),0,BigDecimal.ROUND_HALF_UP);
            BigDecimal secProp = gdpAddValSecondaryIndu.divide(total,4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).divide(new BigDecimal(1),0,BigDecimal.ROUND_HALF_UP);
            BigDecimal terProp = new BigDecimal(100).subtract(prProp.add(secProp));
            d.setGdpAddValPrimaryIndu(prProp);//设置占比
            d.setGdpAddValSecondaryIndu(secProp);
            d.setGdpAddValTertiaryIndu(terProp);
	    }
	}
	
	/**
	 * 查找省份id集合
	 * @return
	 */
	public List<Long> findProvinceIds(){
	    return jdbcTemplate.queryForList("SELECT area_uni_code FROM dmdb.t_bond_area WHERE sub_uni_code IS NULL", Long.class);
	}

}
