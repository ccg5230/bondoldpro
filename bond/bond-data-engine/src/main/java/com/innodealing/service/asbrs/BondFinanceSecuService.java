package com.innodealing.service.asbrs;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.innodealing.dao.jdbc.dm.bond.asbrs.BondSecuFinaSheetDao;
import com.innodealing.dao.jdbc.dm.bond.ccxe.BondPrepareFinaDao;
import com.innodealing.dao.jdbc.dm.bond.ccxe.BondTargetSecuFinaDao;
import com.innodealing.dao.jpa.dm.bond.asbrs.BondSecuFinaSheetRep;
import com.innodealing.model.dm.bond.asbrs.BondSecuFinaSheet;
import com.innodealing.service.BondComExtService;
import com.innodealing.service.BondRuleRowKeyService;
import com.innodealing.util.DateUtils;

@Service
public class BondFinanceSecuService {

	private static final Logger LOG = LoggerFactory.getLogger(BondFinanceSecuService.class);

	@Autowired
	BondSecuFinaSheetRep bondSecuFinaSheetRep;
	
	@Autowired
	BondSecuFinaSheetDao bondSecuFinaSheetDao;
	
	@Autowired
	BondPrepareFinaDao bondPrepareFinaDao;
	
	@Autowired
	BondTargetSecuFinaDao bondTargetSecuFinaDao;

	@Autowired
	BondRuleRowKeyService bondRuleRowKeyService;

	@Autowired
	BondComExtService bondComExtService;

	@Transactional(rollbackFor=Exception.class)
	public void saveToSecu(Long comUniCode, Long bondUniCode, Date endDate) throws Exception {		

		Long startTime = System.currentTimeMillis();
		LOG.info("saveToSecu comUniCode:" + comUniCode + ",endDate:" + endDate);
		BondSecuFinaSheet vo = bondTargetSecuFinaDao.getTargetData(comUniCode, bondUniCode, endDate);

		Date finDate = vo.getEND_DATE();
		Long comId = bondComExtService.getComId(vo.getCOM_UNI_CODE(), "secu");
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
			LOG.error("--- ---saveToSecu rowkey error : comId is " + vo.getCOMP_ID() 
			+ ",finDate is " + DateUtils.convertDateToString(vo.getFIN_DATE(), DateUtils.DATE_FORMAT) 
			+ ". exception:", e);
			return ;
		}
				
		bondSecuFinaSheetRep.save(vo);
		Long endTime = System.currentTimeMillis();
		LOG.info("saveToSecu comUniCode:" + comUniCode + ",endDate:" + endDate + ",period:" + (endTime-startTime));
	}
	
	public void deleteAllData(){
		bondSecuFinaSheetDao.deleteAll();
	}
	
	public void integrateData(Date now, Integer length) {
		Integer secuCount = bondTargetSecuFinaDao.getAllCount();
		int doSize = 0;
		for (int i = 0; i < secuCount; i = i + length) {
			Integer size = batchSecuData(now, i, length);
			doSize = doSize + size;
		}
		LOG.info("finance data integration result: secu ori size is " + secuCount + ", input to secu size is " + doSize);
	}
	
	public Integer batchSecuData(Date now, Integer start, Integer length) {

		Long startTime = System.currentTimeMillis();
		LOG.info("batchSecuData start:" + start + ",length:" + length + ",startTime:" + startTime);
		List<BondSecuFinaSheet> oriList = bondTargetSecuFinaDao.getList(start, length);
		
		if (null == oriList || oriList.isEmpty()) {
			return 0;
		}
		
		Date finDate = null;
		Long comUniCode;
		List<BondSecuFinaSheet> targetList = new ArrayList<>();
		int targetListSize = 0;
		for (BondSecuFinaSheet vo : oriList) {
			finDate = vo.getEND_DATE();
			comUniCode = bondComExtService.getComId(vo.getCOM_UNI_CODE(), "secu");
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
				LOG.error("--- ---bondSecuFinaSheet rowkey error : comId is " + vo.getCCXEID() 
				+ ",finDate is " + DateUtils.convertDateToString(vo.getFIN_DATE(), DateUtils.DATE_FORMAT) 
				+ ". exception:", e);
				continue;
			}
			
			targetList.add(vo);
			targetListSize++;
		}
		
		int batchSize = 0;
		if (!targetList.isEmpty()) {
			batchSize = bondSecuFinaSheetDao.batchSave(targetList);
		}
		Long endTime = System.currentTimeMillis();
		LOG.info("batchSecuData start:" + start + ",length:" + length + ",endTime:" + endTime + ",period:" + (endTime-startTime));
		LOG.info("finance data integration result: input to batchSecuData size is " + batchSize + ",and targetListSize is " + targetListSize + ",and oriListSize is " + oriList.size());		
		LOG.info("finance data integration result: input to batchSecuData size is " + batchSize + ",finDate is " + finDate + ",finDateTime is " + finDate.getTime());
		return batchSize;
	}
}
