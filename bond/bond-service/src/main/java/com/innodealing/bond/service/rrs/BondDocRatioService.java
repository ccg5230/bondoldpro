package com.innodealing.bond.service.rrs;

import static com.innodealing.bond.service.rrs.BondDocGrammer.CURRENY;
import static com.innodealing.bond.service.rrs.BondDocGrammer.RATIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.innodealing.bond.service.BondInduService;
import com.innodealing.bond.service.rrs.BondDocGrammer.FdFmt;
import com.innodealing.bond.service.rrs.BondDocGrammer.GrammaticLink;
import com.innodealing.bond.vo.summary.BondIssDMRatingSummaryVO;
import com.innodealing.engine.jpa.dm.BondComExtRepository;
import com.innodealing.engine.mongo.bond.BondComInfoRepository;
import com.innodealing.model.dm.bond.BondComExt;
import com.innodealing.model.mongo.dm.BondComInfoDoc;

@Service
public class BondDocRatioService {

	private static final Logger LOG = LoggerFactory.getLogger(BondDocRatioService.class);
	
	@Autowired
	protected BondInduService induService;

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
    @Autowired
    @Qualifier("asbrsPerResultJdbcTemplate")
    protected JdbcTemplate jdbcTemplateAsbr;
    
	@Autowired
	private BondComInfoRepository comInfoRepository;

	@Autowired
	private BondComExtRepository comExtRepository;
	
	public String findInduNameByIssuerId(Long issuerId, Long userId) 
	{
		BondComInfoDoc comInfo = comInfoRepository.findOne(issuerId);
		if (comInfo != null) {
			Boolean isGisc = induService.isGicsInduClass(userId);
			return isGisc? comInfo.getInduName() : comInfo.getInduNameSw();
		}
		return "";
	}
	

	@Cacheable(value = "prvModelTypeCache", key = "#taskId")
	public Integer getModelTypeByTaskId(String taskId) {
	    try {
	        String format = "select  model_id from  asbrs.v_dm_personal_rating_ratio_score AS S where S.taskid = %1$s group by S.taskid";
	        return jdbcTemplateAsbr.queryForObject(String.format(format, taskId), Integer.class);
	    } catch (Exception ex) {
	        LOG.error("getModelType exception with taskId[" + taskId + "]: " + ex.getMessage());
	    }
	    return null;
	}
	
}
