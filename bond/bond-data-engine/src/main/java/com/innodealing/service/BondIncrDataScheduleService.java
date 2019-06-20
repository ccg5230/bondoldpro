package com.innodealing.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

//@Component
public class BondIncrDataScheduleService {

    Logger LOG = Logger.getLogger(BondIncrDataScheduleService.class);
    
    @Autowired
    BondCcxeIncrDataChangePollService ccxeIncrDataChangePollService;
    
    @Scheduled(cron= "* 0/20 * * * *" )
    void pollCcxeData() throws Exception 
    {
        ccxeIncrDataChangePollService.pollChange("BondFinFalBalaTafbb", null);
        ccxeIncrDataChangePollService.pollChange("BondFinFalCashTafcb", null);
        ccxeIncrDataChangePollService.pollChange("BondFinFalProfTafpb", null);
        ccxeIncrDataChangePollService.pollChange("BondFinGenBalaTacbb", null);
        ccxeIncrDataChangePollService.pollChange("BondFinGenProfTacpb", null);
        ccxeIncrDataChangePollService.pollChange("BondFinGenCashTaccb", null);
    }
}
