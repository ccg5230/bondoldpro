package com.innodealing.manager;

import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.innodealing.dao.jdbc.dm.bond.asbrs_p.BondPersonalFinanceResultDao;
import com.innodealing.dao.jpa.dm.base.BondPerFinanceRep;
import com.innodealing.model.dm.bond.BondPerFinance;
import com.innodealing.util.SafeUtils;

@Service
public class BondAmarcalculateManager {
	
	private static final Logger LOG = LoggerFactory.getLogger(BondAmarcalculateManager.class);

	@Autowired
	private BondPerFinanceRep bondPerFinanceRep;
	
	@Autowired
	private BondPersonalFinanceResultDao bondPersonalFinanceResultDao;
	
	public void calculationResult(Long taskId) {
		String result = "";
		// get rating result data from asbrs
		try {
			
			Map<String, Object> ratingRes = bondPersonalFinanceResultDao.getRating(taskId);
			if (null != ratingRes) {
				// update dmdb bondPerFinance
				BondPerFinance bondPerFinance = bondPerFinanceRep.findByTaskId(taskId);
				if (null != bondPerFinance) {
					String rating = SafeUtils.getString(ratingRes.get("rating"));
					Date createDate = SafeUtils.parseDate(ratingRes.get("create_time").toString(), SafeUtils.TIMESTAMP_FORMAT);
					bondPerFinance.setRating(rating);
					bondPerFinance.setRateTime(createDate);
					
					bondPerFinanceRep.save(bondPerFinance);
				} else {
					result = "error:该任务在DM的DB中不存在，taskId="+taskId;
					LOG.info(result);
				}
			} else {
				result = "error:查询评级评分为空, taskId="+taskId;
				LOG.info(result);
			}
			
		} catch (Exception e) {
			result = "error:查询评级评分为空, taskId="+taskId;
			LOG.error(result);
		}

	}
}
