package com.innodealing.service.asbrs;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.innodealing.dao.jdbc.dm.bond.asbrs.BondManuFinaSheetDao;
import com.innodealing.dao.jdbc.dm.bond.ccxe.BondTargetManuFinaDao;
import com.innodealing.dao.jpa.dm.bond.asbrs.BondManuFinaSheetRep;
import com.innodealing.model.dm.bond.asbrs.BondManuFinaSheet;
import com.innodealing.service.BondComExtService;
import com.innodealing.service.BondRuleRowKeyService;
import com.innodealing.util.DateUtils;

@Service
public class BondFinanceManuService {

	private static final Logger LOG = LoggerFactory.getLogger(BondFinanceManuService.class);

	@Autowired
	BondManuFinaSheetRep bondManuFinaSheetRep;
	
	@Autowired
	BondManuFinaSheetDao bondManuFinaSheetDao;
	
	@Autowired
	BondTargetManuFinaDao bondTargetManuFinaDao;

	@Autowired
	BondRuleRowKeyService bondRuleRowKeyService;
	
	@Autowired
	BondComExtService bondComExtService;
	
	@Transactional(rollbackFor=Exception.class)
	public void saveToManu(Long comUniCode, Long bondUniCode, Date endDate) throws Exception {		

		Long startTime = System.currentTimeMillis();
		LOG.info("saveToManu comUniCode:" + comUniCode + ",endDate:" + endDate);
		BondManuFinaSheet vo = bondTargetManuFinaDao.getTargetData(comUniCode, bondUniCode, endDate);

		Date finDate = vo.getEND_DATE();
		Long comId = bondComExtService.getComId(vo.getCOM_UNI_CODE(), "manu");
		if (0 == comId) {
			return ;
		}
		vo.setCOMP_ID(comId);
		vo.setFIN_ENTITY("1");
		vo.setFIN_STATE_TYPE("HR");
		vo.setFIN_DATE(finDate);
		vo.setFIN_PERIOD(finDate.getMonth() + 1);
		vo.setLast_update_timestamp(new Date(System.currentTimeMillis()));
		
		try {
			vo.setROW_KEY(bondRuleRowKeyService.getRowKey(vo));
		} catch (Exception e) {
			LOG.error("--- ---saveToManu rowkey error : comId is " + vo.getCOMP_ID() 
			+ ",finDate is " + DateUtils.convertDateToString(vo.getFIN_DATE(), DateUtils.DATE_FORMAT) 
			+ ". exception:", e);
			return ;
		}
				
		bondManuFinaSheetRep.save(vo);
		Long endTime = System.currentTimeMillis();
		LOG.info("saveToManu comUniCode:" + comUniCode + ",endDate:" + endDate + ",period:" + (endTime-startTime));
	}
	
	public void deleteAllData(){
		bondManuFinaSheetDao.deleteAll();
	}
	
	public void integrateData(Date now, Integer length) {
		Integer manuCount = bondTargetManuFinaDao.getAllCount();
		int doSize = 0;
		for (int i = 0; i < manuCount; i = i + length) {
			Integer size = batchManuData(now, i, length);
			doSize = doSize + size;
		}
		LOG.info("finance data integration result: manu ori size is " + manuCount + ", input to manu size is " + doSize);
	}
	
	public Integer batchManuData(Date now, Integer start, Integer length) {		

		Long startTime = System.currentTimeMillis();
		LOG.info("batchManuData start:" + start + ",length:" + length + ",startTime:" + startTime);
		List<BondManuFinaSheet> oriList = bondTargetManuFinaDao.getList(start, length);
		
		if (null == oriList || oriList.isEmpty()) {
			return 0;
		}
		
		Date finDate = null;
		Long comUniCode;
		List<BondManuFinaSheet> targetList = new ArrayList<>();
		int targetListSize = 0;
		for (BondManuFinaSheet vo : oriList) {
			finDate = vo.getEND_DATE();
			comUniCode = bondComExtService.getComId(vo.getCOM_UNI_CODE(), "manu");
			if (0 == comUniCode) {
				continue;
			}
			vo.setCOMP_ID(comUniCode);
			vo.setFIN_ENTITY("1");
			vo.setFIN_STATE_TYPE("HR");
			vo.setFIN_DATE(finDate);
			vo.setFIN_PERIOD(finDate.getMonth() + 1);
			vo.setLast_update_timestamp(now);						
			
			try {
				vo.setROW_KEY(bondRuleRowKeyService.getRowKey(vo));
			} catch (Exception e) {
				LOG.error("--- ---bondManuFinaSheet rowkey error : comId is " + vo.getCOMP_ID() 
				+ ",finDate is " + DateUtils.convertDateToString(vo.getFIN_DATE(), DateUtils.DATE_FORMAT) 
				+ ". exception:", e);
				continue;
			}
			
			targetList.add(vo);
			targetListSize++;
		}
		
		int batchSize = 0;
		if (!targetList.isEmpty()) {
			batchSize = bondManuFinaSheetDao.batchSave(targetList);
		}
		Long endTime = System.currentTimeMillis();
		LOG.info("batchManuData start:" + start + ",length:" + length + ",endTime:" + endTime + ",period:" + (endTime-startTime));
		LOG.info("finance data integration result: input to bondManuFinaSheet size is " + batchSize + ",and targetListSize is " + targetListSize + ",and oriListSize is " + oriList.size());
		LOG.info("finance data integration result: input to bondManuFinaSheet size is " + batchSize + ",finDate is " + finDate + ",finDateTime is " + finDate.getTime());
		return batchSize;
	}

}
