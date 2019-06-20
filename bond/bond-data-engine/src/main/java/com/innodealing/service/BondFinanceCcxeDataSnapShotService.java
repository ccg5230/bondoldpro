package com.innodealing.service;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.innodealing.service.ccxe.BondFinFalBalaTafbbService;
import com.innodealing.service.ccxe.BondFinFalCashTafcbService;
import com.innodealing.service.ccxe.BondFinFalProfTafpbService;
import com.innodealing.service.ccxe.BondFinGenBalaTacbbService;
import com.innodealing.service.ccxe.BondFinGenCashTaccbService;
import com.innodealing.service.ccxe.BondFinGenProfTacpbService;

@Component
public class BondFinanceCcxeDataSnapShotService {

    private static final Logger LOG = LoggerFactory.getLogger(BondFinanceCcxeDataSnapShotService.class);

    @Autowired
    private BondFinFalBalaTafbbService bondFinFalBalaTafbbService;
    
    @Autowired
    private BondFinFalProfTafpbService bondFinFalProfTafpbService;
    
    @Autowired
    private BondFinFalCashTafcbService bondFinFalCashTafcbService;
    
    @Autowired
    private BondFinGenBalaTacbbService bondFinGenBalaTacbbService;
    
    @Autowired
    private BondFinGenProfTacpbService bondFinGenProfTacpbService;
    
    @Autowired
    private BondFinGenCashTaccbService bondFinGenCashTaccbService;

    public synchronized void pollChange(String beanName, Date syncSideTime) throws Exception {
    	if (null == beanName) {
        	return;
        }
        if ("BondFinFalBalaTafbb".equalsIgnoreCase(beanName)) {
        	bondFinFalBalaTafbbService.pollChange(beanName, syncSideTime);
        } else if ("BondFinFalProfTafpb".equalsIgnoreCase(beanName)) {
        	bondFinFalProfTafpbService.pollChange(beanName, syncSideTime);
        } else if ("BondFinFalCashTafcb".equalsIgnoreCase(beanName)) {
        	bondFinFalCashTafcbService.pollChange(beanName, syncSideTime);
        } else if ("BondFinGenBalaTacbb".equalsIgnoreCase(beanName)) {
        	bondFinGenBalaTacbbService.pollChange(beanName, syncSideTime);
        } else if ("BondFinGenProfTacpb".equalsIgnoreCase(beanName)) {
        	bondFinGenProfTacpbService.pollChange(beanName, syncSideTime);
        } else if ("BondFinGenCashTaccb".equalsIgnoreCase(beanName)) {
        	bondFinGenCashTaccbService.pollChange(beanName, syncSideTime);
        } else {
            LOG.warn("pollChange is not be handle. Because tableName is " + beanName);
            return ;
        }
    }
}
