package com.innodealing.engine.jdbc.bond;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.innodealing.model.dm.bond.BondPerFinance;
import com.innodealing.util.StringUtils;

@Component
public class BondPerFinanceDao {

	public final static Logger logger = LoggerFactory.getLogger(BondPerFinanceDao.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<BondPerFinance> getPerFinByKeyword(Integer userId, String keyword, Integer offset, Integer limit) {
		List<BondPerFinance> list = null;
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT t.task_id AS taskId,t.fin_user AS finUser,t.fin_year AS finYear,t.fin_quarter AS finQuarter,t.comp_cls_code AS compClsCode,t.userid AS userid,t.industry_id AS industryId,t.comp_cls_code AS compClsCode,t.rating,t.STATUS,t.rate_time AS rateTime,t.indu_class_name_l4 AS induClassNameL4 ");
			sql.append("FROM dmdb.t_bond_per_finance t WHERE t.deleted = 0 ");
			sql.append("AND t.userid=").append(userId).append(" ");
			if (StringUtils.isNotBlank(keyword)) {
				sql.append(" AND t.fin_user LIKE '%").append(keyword).append("%' ");
				sql.append(" ORDER BY CONVERT(t.fin_user USING gbk) ASC");
			}else{
				sql.append(" ORDER BY t.operate_time DESC");
			}

			sql.append(" LIMIT ").append(offset).append(",").append(limit);
			list = jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<>(BondPerFinance.class));
		} catch (Exception ex) {
			logger.error("getPerFinByKeyword error," + ex.getMessage(), ex);
		}

		return list;
	}
	
	public Integer getgetPerFinByKeywordCount(Integer userId, String keyword){
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT count(1) ");
			sql.append("FROM dmdb.t_bond_per_finance t WHERE t.deleted = 0 ");
			sql.append("AND t.userid=").append(userId).append(" ");
			if (StringUtils.isNotBlank(keyword)) {
				sql.append(" AND t.fin_user LIKE '%").append(keyword).append("%' ");
			}
			sql.append("ORDER BY CONVERT(t.fin_user USING gbk) ASC");
			
			return jdbcTemplate.queryForObject(sql.toString(), Integer.class);
		} catch (Exception ex) {
			logger.error("getgetPerFinByKeywordCount error," + ex.getMessage(), ex);
		}
		return 0;
	}
}
