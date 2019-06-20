package com.innodealing.service;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.innodealing.dao.jdbc.dm.bond.ccxe.BondPrepareFinaDao;
import com.innodealing.model.dm.BondCcxeDataChangeEvent;
import com.innodealing.service.asbrs.BondFinanceBankService;
import com.innodealing.service.asbrs.BondFinanceInsuService;
import com.innodealing.service.asbrs.BondFinanceManuService;
import com.innodealing.service.asbrs.BondFinanceSecuService;
import com.innodealing.util.DateUtils;
import com.innodealing.util.StringUtils;

@Service
public class BondFinanceDataService {

	private static final Logger LOG = LoggerFactory.getLogger(BondFinanceDataService.class);

	@Autowired
	BondPrepareFinaDao bondPrepareFinaDao;

	@Autowired
	BondFinanceBankService bondFinanceBankService;
	
	@Autowired
	BondFinanceInsuService bondFinanceInsuService;
	
	@Autowired
	BondFinanceManuService bondFinanceManuService;
	
	@Autowired
	BondFinanceSecuService bondFinanceSecuService;
	
	public String syncIntegration() {
		synchronized (BondFinanceDataService.class) {
			return integration();
		}
	}
	
	@Transactional(rollbackFor=Exception.class)
	public String saveByFinFalBala(Long comUniCode, Long bondUniCode, Date endDate) throws Exception {
		bondFinanceBankService.saveToBank(comUniCode, bondUniCode, endDate);
		bondFinanceInsuService.saveToInsu(comUniCode, bondUniCode, endDate);
		bondFinanceSecuService.saveToSecu(comUniCode, bondUniCode, endDate);
		return "success";		
	}
	
	@Transactional(rollbackFor=Exception.class)
	public String saveByFinFalProf(Long comUniCode, Long bondUniCode, Date endDate) throws Exception {
		bondFinanceBankService.saveToBank(comUniCode, bondUniCode, endDate);
		bondFinanceInsuService.saveToInsu(comUniCode, bondUniCode, endDate);
		bondFinanceSecuService.saveToSecu(comUniCode, bondUniCode, endDate);
		return "success";		
	}

	@Transactional(rollbackFor=Exception.class)
	public String saveByFinFalCash(Long comUniCode, Long bondUniCode, Date endDate) throws Exception {
		bondFinanceBankService.saveToBank(comUniCode, bondUniCode, endDate);
		bondFinanceInsuService.saveToInsu(comUniCode, bondUniCode, endDate);
		bondFinanceSecuService.saveToSecu(comUniCode, bondUniCode, endDate);
		return "success";		
	}
	
	@Transactional(rollbackFor=Exception.class)
	public String saveByFinGenBala(Long comUniCode, Long bondUniCode, Date endDate) throws Exception {
		bondFinanceManuService.saveToManu(comUniCode, bondUniCode, endDate);
		return "success";		
	}
	
	@Transactional(rollbackFor=Exception.class)
	public String saveByFinGenProf(Long comUniCode, Long bondUniCode, Date endDate) throws Exception {
		bondFinanceManuService.saveToManu(comUniCode, bondUniCode, endDate);
		return "success";		
	}
	
	@Transactional(rollbackFor=Exception.class)
	public String saveByFinGenCash(Long comUniCode, Long bondUniCode, Date endDate) throws Exception {
		bondFinanceManuService.saveToManu(comUniCode, bondUniCode, endDate);
		return "success";		
	}

	/**
	 * mysql数据：ccxe --> amaresun
	 */
	private String integration() {
		String result = "failed";
		try {			
			boolean isOK = prepareDataBeforeDo();
			if (!isOK) {
				return result;
			}

			Date now = new Date(System.currentTimeMillis());
			Integer length = 10000;
			
			Long startTime = System.currentTimeMillis();
			LOG.info("--------start time is " + startTime);
			bondFinanceManuService.integrateData(now, length);
			Long manuEndTime = System.currentTimeMillis();
			LOG.info("--------manuEndTime time is " + manuEndTime + ",period:" + (manuEndTime-startTime));
			
			bondFinanceBankService.integrateData( now, length);
			Long bankEndTime = System.currentTimeMillis();
			LOG.info("--------bankEndTime time is " + bankEndTime + ",period:" + (bankEndTime - manuEndTime));
			
			bondFinanceInsuService.integrateData(now, length);
			Long insuEndTime = System.currentTimeMillis();
			LOG.info("--------insuEndTime time is " + insuEndTime + ",period:" + (insuEndTime - bankEndTime));
			
			bondFinanceSecuService.integrateData(now, length);
			Long secuEndTime = System.currentTimeMillis();
			LOG.info("--------secuEndTime time is " + secuEndTime + ",period:" + (secuEndTime - insuEndTime));

			Long endTime = System.currentTimeMillis();
			LOG.info("--------end time is " + endTime + ",period is " + (endTime - startTime));			

			result = "success";
		} catch (Exception e) {
			LOG.error("internal error" + e.getMessage(), e);
			result = "internal error";
		} finally {
			removeTmpDataAfterDo();
		}
		return result;
	}
	
	/**
	 * 准备临时表、同步数据、索引
	 */
	public boolean prepareDataBeforeDo() {
		try {
			bondPrepareFinaDao.prepareDataBeforeDo();
			
			// 删除安硕旧数据
			bondFinanceBankService.deleteAllData();
			LOG.info("----bank_fina_sheet delete over.");
			bondFinanceInsuService.deleteAllData();
			LOG.info("----insu_fina_sheet delete over.");
			bondFinanceManuService.deleteAllData();
			LOG.info("----manu_fina_sheet delete over.");
			bondFinanceSecuService.deleteAllData();
			LOG.info("----secu_fina_sheet delete over.");

		} catch (Exception e) {
			LOG.error("---prepareDataBeforeDo() error, ", e);
			return false;
		}		
		return true;
	}
	
	/**
	 * 清除临时的操作：表结构、数据、索引
	 */
	public boolean removeTmpDataAfterDo() {
		try {
			bondPrepareFinaDao.removeTmpDataAfterDo();
		} catch (Exception e) {
			LOG.error("---prepareDataBeforeDo() error, ", e);
			return false;
		}		
		return true;
	}

	@EventListener(condition = "(#createEvent.tableName matches '^BondFin.*') && (#createEvent.actType == 1)")
	@Async
	public void handleCcxeDataCreateEvent(BondCcxeDataChangeEvent createEvent) 
	{
	    try {
	        handleBondFinaEvent(createEvent.getTableName(), createEvent.getData(), createEvent.getCreateTime());
	    } catch (Exception e) {
	        LOG.error("handleCcxeDataCreateEvent error,", e);
	    }
	}

	@EventListener(condition = "(#createEvent.tableName matches '^BondFin.*') && (#createEvent.actType == 2)")
	@Async
	public void handleCcxeDataUpdateEvent(BondCcxeDataChangeEvent createEvent) 
	{
	    try {
	        handleBondFinaEvent(createEvent.getTableName(), createEvent.getData(), createEvent.getCreateTime());
	    } catch (Exception e) {
	        LOG.error("handleCcxeDataUpdateEvent error,", e);
	    }
	}

	public String handleBondFinaEvent(String tableName, Object data, Date createTime) throws Exception {            
	    if (StringUtils.isBlank(tableName)) {
	        LOG.error("handleBondFinaEvent event tableName is empty.");
	        return null;
	    }

	    Method methodGetComdUniCode = data.getClass().getMethod("getComUniCode");
	    Long comUniCode = null;
	    if (methodGetComdUniCode != null) {
	        comUniCode = (Long)methodGetComdUniCode.invoke(data);
	        if (comUniCode == null) {
	            return "failed";
	        }
	    }
	    
	    Method methodGetBondUniCode = data.getClass().getMethod("getBondUniCode");
	    Long bondUniCode = null;
	    if (methodGetBondUniCode != null) {
	    	bondUniCode = (Long)methodGetBondUniCode.invoke(data);
	        if (bondUniCode == null) {
	            return "failed";
	        }
	    }
	    
	    Method methodGetEndDate = data.getClass().getMethod("getEndDate");
	    Date endDate = null;
	    if (methodGetEndDate != null) {
	        endDate = (Date)methodGetEndDate.invoke(data);
	        if (endDate == null) {
	            return "failed";
	        }
	    }

	    LOG.info("handleBondFinaEvent event come.timestamp is " + createTime.getTime() + ",tableName is " + tableName + ",comUniCode is " + comUniCode + ",endDate is " + endDate);

	    try {
	        if ("BondFinFalBalaTafbb".equalsIgnoreCase(tableName)) {
	            saveByFinFalBala(comUniCode, bondUniCode, endDate);
	        } else if ("BondFinFalProfTafpb".equalsIgnoreCase(tableName)) {
	            saveByFinFalProf(comUniCode, bondUniCode, endDate);
	        } else if ("BondFinFalCashTafcb".equalsIgnoreCase(tableName)) {
	            saveByFinFalCash(comUniCode, bondUniCode, endDate);
	        } else if ("BondFinGenBalaTacbb".equalsIgnoreCase(tableName)) {
	            saveByFinGenBala(comUniCode, bondUniCode, endDate);
	        } else if ("BondFinGenProfTacpb".equalsIgnoreCase(tableName)) {
	            saveByFinGenProf(comUniCode, bondUniCode, endDate);
	        } else if ("BondFinGenCashTaccb".equalsIgnoreCase(tableName)) {
	            saveByFinGenCash(comUniCode, bondUniCode, endDate);
	        } else {
	            LOG.warn("handleBondFinaEvent event is not be handle. Because tableName is " + tableName);
	            return null;
	        }
	    } catch (Exception e) {
	        LOG.error("handleBondFinaEvent: listen event is error,", e);
	        return "failed";
	    }
	    LOG.info("handleBondFinaEvent event success.");
	    return "success";
	}
}
