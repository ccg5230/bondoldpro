package com.innodealing.engine.jpa.dm;

import org.springframework.stereotype.Component;

import com.innodealing.engine.jpa.im.BaseRepositoryDm;
import com.innodealing.model.dm.bond.BondBasicInfo;
import com.innodealing.model.dm.bond.BondBrokerDeal;

@Component
public interface BondBasicInfoRepositoryDm extends BaseRepositoryDm<BondBasicInfo, Long> {

}
