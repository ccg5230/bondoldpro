package com.innodealing.engine.jdbc.bond;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.innodealing.model.mongo.dm.BondBasicInfoClassOneDoc;

/**
 * 一级发行
 * @author 赵正来
 *
 */
@Component
public class BondBasicInfoClassOneDao {

	private @Autowired JdbcTemplate jdbcTemplate;
	
	private final String showFields = " SELECT" +  
	        " bond.book_start_date," + 
	        " bond.book_end_date," +   
	        " bond.bond_uni_code," +   
	        " bond.com_uni_code," +   
	        " bond.iss_name," +   
	        " bond.bond_code," +   
	        " bond.bond_full_name," +   
	        " bond.bond_short_name," +   
	        " com.COM_CHI_NAME AS issuer," +   
	        " bond.bond_matu," +   
	        " bond.plan_iss_scale," +   
	        " bond.plan_iss_scale_unit," +   
	        " dm_rating.rating AS dm_rating_score," +   
	        " dm_rating.last_update_timestamp AS dm_rating_date," +   
	        " bond.new_rate AS bond_rating," +   
	        " bond.iss_cred_level AS iss_rating," +   
	        " ratingdog.ratingdog_rating AS org_rating," +   
	        " CASE ratingdog.rating_type WHEN 0 THEN '0' WHEN 1 THEN '1' ELSE '1' END AS rating_type," +   
	        " ratingdog.ratingdog_fix_price_lower as org_price_lower," +   
	        " ratingdog.ratingdog_fix_price_super as org_price_super," +   
	        " ratingdog.first_maj_shareholder_name," +   
	        " ratingdog.enterprise_nature," +   
	        " ratingdog.trade," +   
	        " ratingdog.shareholder_relations shareholder_relations," +   
	        " ratingdog.state_operation," +   
	        " ratingdog.proportion_shareholder proportion_shareholder," +   
	        " ratingdog.financial_standing," +   
	        " ratingdog.focus_on," +   
	        " ratingdog.ratingdog_margin_lower org_margin_lower," +   
	        " ratingdog.ratingdog_margin_super org_margin_super," +   
	        " ratingdog.fix_price_reason," + 
	        " ratingdog.city_invest_status," +
	        " bond.did_interval_low AS did_interval_low," +   
	        " bond.did_interval_sup AS did_interval_sup," +   
	        " bond.subscription_interval_lower AS subscription_interval_lower," +   
	        " bond.subscription_interval_super AS subscription_interval_super," + 
	        " bond.stop_bid_start_date," +
	        " bond.stop_bid_end_date," +   
	        " bond.list_date," +   
	        " bond.unde_name," +   
	        " com.COM_ATTR_PAR," +   
	        " bond.iss_cls," +   
	        " bond.create_time," +   
	        " bond.iss_start_date," +   
	        " bond.iss_end_date," +   
	        " bond.bond_type_par," +   
	        " bond.actu_fir_iss_amut," +   
	        " bond.actu_fir_iss_amut_unit," +   
	        " bond.iss_coup_rate," + 
	        " bond.iss_status," +  
	        " bond.cancel_issue_date," +
	        " industrial.subject_score AS industrial_subject_score," +   
	        " industrial.bond_score AS industrial_bond_score," +   
	        " industrial.subject_comment AS subject_comment," +   
	        " industrial.ticket_comment AS ticket_comment," +   
	        " industrial.reference_returns_ratio AS reference_returns_ratio," +   
	        " industrial.industrial_margin AS industrial_margin," +   
	        " industrial.expectation AS industExpectation," +   
	        " 0 favorite";//用户是否关注，后续根据查询用户关注表设置，
	
	/**
	 * 查找所有一级发行中的债券：目前只同步：发行中已推送新债数据
	 * @return
	 */
	public List<BondBasicInfoClassOneDoc> findAll(){
		StringBuffer sbsql = new StringBuffer("");
		sbsql.append(showFields)
		.append(" FROM ")
		.append(" (SELECT * FROM dmdb.t_bond_basic_info WHERE is_new = 1 and iss_status = 0 AND push_status = 1")//已发行已推送新债
		.append(" UNION ALL ")
        .append(" SELECT * FROM dmdb.t_bond_basic_info WHERE is_new = 1 and iss_status = 3 AND push_status = 1 AND DATE_FORMAT(cancel_issue_date,'%Y-%m-%d')=curdate()")//当日取消发行已推送新债数据
		.append(" ) as  bond ")
		.append(" LEFT JOIN bond_ccxe.d_pub_com_info_2 com ON bond.com_uni_code = com.COM_UNI_CODE ")
		.append(" LEFT JOIN dmdb.t_bond_ratingdog_ext ratingdog ON ratingdog.bond_uni_code = bond.bond_uni_code ")
		.append(" LEFT JOIN dmdb.t_bond_ratingindustrial_ext industrial ON industrial.bond_uni_code = bond.bond_uni_code ")
		.append(" LEFT JOIN dmdb.t_bond_com_ext ext ON bond.com_uni_code = ext.com_uni_code ")
		.append(" LEFT JOIN (SELECT * FROM ")
		.append(" (SELECT Rating,Comp_ID,`year`,quan_month,last_update_timestamp FROM dmdb.dm_bond WHERE rating is not null ORDER BY `year`DESC,quan_month DESC  )")
		.append(" as dmrating GROUP BY dmrating.Comp_ID) as dm_rating on dm_rating.comp_id = ext.ama_com_id "); 

		return jdbcTemplate.query(sbsql.toString(), new BeanPropertyRowMapper<>(BondBasicInfoClassOneDoc.class));

	}
	
	/**
	 * 
	 * findByBondUniCode:后台修改重新构建单条mongodb数据
	 * @param  @param bondUniCode
	 * @param  @return    设定文件
	 * @return BondBasicInfoClassOneDoc    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	 */
	public BondBasicInfoClassOneDoc findByBondUniCode(long bondUniCode){
		StringBuffer sbsql = new StringBuffer("");
		sbsql.append(showFields)
		.append(" FROM ")
		.append(" (SELECT * FROM dmdb.t_bond_basic_info WHERE is_new = 1 and iss_status = 0 AND push_status = 1")//已发行已推送新债
		.append(" AND bond_uni_code=").append(bondUniCode)
		.append(" UNION ALL ")
		.append(" SELECT * FROM dmdb.t_bond_basic_info WHERE is_new = 1 and iss_status = 3 AND push_status = 1 AND DATE_FORMAT(cancel_issue_date,'%Y-%m-%d')=curdate()")
		.append(" AND bond_uni_code=").append(bondUniCode)//当日取消发行已推送新债数据
		.append(") as  bond ")
		.append(" LEFT JOIN bond_ccxe.d_pub_com_info_2 com ON bond.com_uni_code = com.COM_UNI_CODE ")
		.append(" LEFT JOIN dmdb.t_bond_ratingdog_ext ratingdog ON ratingdog.bond_uni_code = bond.bond_uni_code ")
		.append(" LEFT JOIN dmdb.t_bond_ratingindustrial_ext industrial ON industrial.bond_uni_code = bond.bond_uni_code ")
		.append(" LEFT JOIN dmdb.t_bond_com_ext ext ON bond.com_uni_code = ext.com_uni_code ")
		.append(" LEFT JOIN (SELECT * FROM ")
		.append(" (SELECT Rating,Comp_ID,`year`,quan_month,last_update_timestamp FROM dmdb.dm_bond WHERE rating is not null ORDER BY `year`DESC,quan_month DESC  ) ")
		.append(" as dmrating GROUP BY dmrating.Comp_ID) as dm_rating on dm_rating.comp_id = ext.ama_com_id "); 

		return jdbcTemplate.queryForObject(sbsql.toString(), new BeanPropertyRowMapper<>(BondBasicInfoClassOneDoc.class));
	}
	
	/**
	 * 
	 * findStopBidAll:(查询所有已截标新债)
	 * @param  @return    设定文件
	 * @return List<BondBasicInfoClassOneDoc>    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	 */
	public List<BondBasicInfoClassOneDoc> findStopBidAll(){
        StringBuffer sbsql = new StringBuffer("");
        sbsql.append(" SELECT bond_uni_code FROM dmdb.t_bond_basic_info WHERE is_new = 1 AND iss_cls=0 AND DATE_FORMAT(stop_bid_end_date,'%Y-%m-%d')<curdate() ")//招标
        .append(" UNION ALL")
        .append(" SELECT bond_uni_code FROM dmdb.t_bond_basic_info WHERE is_new = 1 AND iss_cls=1 AND DATE_FORMAT(book_end_date,'%Y-%m-%d')<curdate() ")//薄记建档
        .append(" UNION ALL")
        .append(" SELECT bond_uni_code FROM dmdb.t_bond_basic_info WHERE is_new = 1 AND (iss_cls!=0 AND iss_cls!=1)  AND DATE_FORMAT(stop_bid_end_date,'%Y-%m-%d')<curdate() ");//未选择发行方式按招标处理 
        return jdbcTemplate.query(sbsql.toString(), new BeanPropertyRowMapper<>(BondBasicInfoClassOneDoc.class));

    }
}
