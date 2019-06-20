package com.innodealing.dao.jdbc.dm.bond.asbrs_p;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.innodealing.dao.jdbc.BaseDao;

/**
 * @author kunkun.zhou
 * @date 2017年02月24日
 * @clasename BondPersonalFinanceResultDao.java
 * @decription TODO
 */
@Component
public class BondPersonalFinanceResultDao extends BaseDao<Object> {
	
	public final static Logger logger = LoggerFactory.getLogger(BondPersonalFinanceResultDao.class);
	
	public Map<String, Object> getRating(Long taskId){
		Map<String, Object> ratingRes = null;
		try{
			String sql = "select rating,create_time FROM v_dm_personal_rating_data_sheet where visible=1 AND taskid=" + taskId + " order by year,quan_month desc limit 1";
			List<Map<String, Object>> list = asbrsPerResultJdbcTemplate.queryForList(sql);
			if (null != list && list.size() > 0) {
				ratingRes = list.get(0);
			}
			logger.info("getRating=="+sql);
		}catch(Exception ex){
			logger.error("getRating error:"+ex.getMessage(), ex);
		}
		
		return ratingRes;
	}

}
