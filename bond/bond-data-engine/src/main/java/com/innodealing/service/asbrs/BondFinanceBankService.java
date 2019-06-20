package com.innodealing.service.asbrs;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.innodealing.dao.jdbc.dm.bond.asbrs.BondBankFinaSheetDao;
import com.innodealing.dao.jdbc.dm.bond.ccxe.BondTargetBankFinaDao;
import com.innodealing.dao.jpa.dm.bond.asbrs.BondBankFinaSheetRep;
import com.innodealing.model.dm.bond.asbrs.BondBankFinaSheet;
import com.innodealing.service.BondComExtService;
import com.innodealing.service.BondRuleRowKeyService;
import com.innodealing.util.DateUtils;

@Service
public class BondFinanceBankService {

	private static final Logger LOG = LoggerFactory.getLogger(BondFinanceBankService.class);

	@Autowired
	BondBankFinaSheetRep bondBankFinaSheetRep;
	
	@Autowired
	BondBankFinaSheetDao bondBankFinaSheetDao;

	@Autowired
	BondTargetBankFinaDao bondTargetBankFinaDao;
	
	@Autowired
	BondRuleRowKeyService bondRuleRowKeyService;
	
	@Autowired
	BondComExtService bondComExtService;
	
	@Transactional(rollbackFor=Exception.class)
	public void saveToBank(Long comUniCode, Long bondUniCode, Date endDate) throws Exception {		

		Long startTime = System.currentTimeMillis();
		LOG.info("saveToBank comUniCode:" + comUniCode + ",endDate:" + endDate);
		BondBankFinaSheet vo = bondTargetBankFinaDao.getTargetData(comUniCode, bondUniCode, endDate);

		Date finDate = vo.getEND_DATE();
		Long comId = bondComExtService.getComId(vo.getCOM_UNI_CODE(), "bank");
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
			LOG.error("--- ---saveToBank rowkey error : comId is " + vo.getCOMP_ID() 
			+ ",finDate is " + DateUtils.convertDateToString(vo.getFIN_DATE(), DateUtils.DATE_FORMAT) 
			+ ". exception:", e);
			return ;
		}
				
		bondBankFinaSheetRep.save(vo);
		Long endTime = System.currentTimeMillis();
		LOG.info("saveToBank comUniCode:" + comUniCode + ",cutTime:" + endDate + ",period:" + (endTime-startTime));
	}
	
	public void deleteAllData(){
		bondBankFinaSheetDao.deleteAll();
	}
	
	public void integrateData(Date now, Integer length) {
		Integer bankCount = bondTargetBankFinaDao.getAllCount();
		int doSize = 0;
		for (int i = 0; i < bankCount; i = i + length) {
			Integer size = batchBankData(now, i, length);
			doSize = doSize + size;
		}
		LOG.info("finance data integration result: bank ori size is " + bankCount + ", input to bank size is " + doSize);
	}

	public Integer batchBankData(Date now, Integer start, Integer length) {

		Long startTime = System.currentTimeMillis();
		LOG.info("batchBankData start:" + start + ",length:" + length + ",startTime:" + startTime);
		List<BondBankFinaSheet> oriList = bondTargetBankFinaDao.getList(start, length);
		
		if (null == oriList || oriList.isEmpty()) {
			return 0;
		}
		
		Date finDate = null;
		Long comUniCode;
		List<BondBankFinaSheet> targetList = new ArrayList<>();
		int targetListSize = 0;
		for (BondBankFinaSheet vo : oriList) {
			finDate = vo.getEND_DATE();
			comUniCode = bondComExtService.getComId(vo.getCOM_UNI_CODE(), "bank");
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
			batchSize = bondBankFinaSheetDao.batchSave(targetList);
		}
		Long endTime = System.currentTimeMillis();
		LOG.info("batchBankData start:" + start + ",length:" + length + ",endTime:" + endTime + ",period:" + (endTime-startTime));
		LOG.info("finance data integration result: input to batchBankData size is " + batchSize + ",and targetListSize is " + targetListSize + ",and oriListSize is " + oriList.size());
		LOG.info("finance data integration result: input to batchBankData size is " + batchSize + ",finDate is " + finDate + ",finDateTime is " + finDate.getTime());
		return batchSize;
	}
}
