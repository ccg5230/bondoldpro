package com.innodealing.service.asbrs;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.innodealing.dao.jdbc.dm.bond.asbrs.BondInsuFinaSheetDao;
import com.innodealing.dao.jdbc.dm.bond.ccxe.BondTargetInsuFinaDao;
import com.innodealing.dao.jpa.dm.bond.asbrs.BondInsuFinaSheetRep;
import com.innodealing.model.dm.bond.asbrs.BondInsuFinaSheet;
import com.innodealing.service.BondComExtService;
import com.innodealing.service.BondRuleRowKeyService;
import com.innodealing.util.DateUtils;

@Service
public class BondFinanceInsuService {

	private static final Logger LOG = LoggerFactory.getLogger(BondFinanceInsuService.class);

	@Autowired
	BondInsuFinaSheetRep bondInsuFinaSheetRep;
	
	@Autowired
	BondInsuFinaSheetDao bondInsuFinaSheetDao;
	
	@Autowired
	BondTargetInsuFinaDao bondTargetInsuFinaDao;

	@Autowired
	BondRuleRowKeyService bondRuleRowKeyService;
	
	@Autowired
	BondComExtService bondComExtService;
	
	@Transactional(rollbackFor=Exception.class)
	public void saveToInsu(Long comUniCode, Long bondUniCode, Date endDate) throws Exception {		

		Long startTime = System.currentTimeMillis();
		LOG.info("saveToInsu comUniCode:" + comUniCode + ",endDate:" + endDate);
		BondInsuFinaSheet vo = bondTargetInsuFinaDao.getTargetData(comUniCode, bondUniCode, endDate);

		Date finDate = vo.getEND_DATE();
		Long comId = bondComExtService.getComId(vo.getCOM_UNI_CODE(), "insu");
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
			LOG.error("--- ---saveToInsu rowkey error : comId is " + vo.getCOMP_ID() 
			+ ",finDate is " + DateUtils.convertDateToString(vo.getFIN_DATE(), DateUtils.DATE_FORMAT) 
			+ ". exception:", e);
			return ;
		}
				
		bondInsuFinaSheetRep.save(vo);
		Long endTime = System.currentTimeMillis();
		LOG.info("saveToInsu comUniCode:" + comUniCode + ",endDate:" + endDate + ",period:" + (endTime-startTime));
	}
	
	public void deleteAllData(){
		bondInsuFinaSheetDao.deleteAll();
	}

	public void integrateData(Date now, Integer length) {
		Integer insuCount = bondTargetInsuFinaDao.getAllCount();
		int doSize = 0;
		for (int i = 0; i < insuCount; i = i + length) {
			Integer size = batchInsuData(now, i, length);
			doSize = doSize + size;
		}
		LOG.info("finance data integration result: insu ori size is " + insuCount + ", input to insu size is " + doSize);
	}
	
	public Integer batchInsuData(Date now, Integer start, Integer length) {

		Long startTime = System.currentTimeMillis();
		LOG.info("batchInsuData start:" + start + ",length:" + length + ",startTime:" + startTime);
		List<BondInsuFinaSheet> oriList = bondTargetInsuFinaDao.getList(start, length);
		
		if (null == oriList || oriList.isEmpty()) {
			return 0;
		}
		
		Date finDate = null;
		Long comUniCode;
		List<BondInsuFinaSheet> targetList = new ArrayList<>();
		int targetListSize = 0;
		for (BondInsuFinaSheet vo : oriList) {
			finDate = vo.getEND_DATE();
			comUniCode = bondComExtService.getComId(vo.getCOM_UNI_CODE(), "insu");
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
			batchSize = bondInsuFinaSheetDao.batchSave(targetList);
		}
		Long endTime = System.currentTimeMillis();
		LOG.info("batchInsuData start:" + start + ",length:" + length + ",endTime:" + endTime + ",period:" + (endTime-startTime));
		LOG.info("finance data integration result: input to batchInsuData size is " + batchSize + ",and targetListSize is " + targetListSize + ",and oriListSize is " + oriList.size());
		LOG.info("finance data integration result: input to batchInsuData size is " + batchSize + ",finDate is " + finDate + ",finDateTime is " + finDate.getTime());
		return batchSize;
	}

}
