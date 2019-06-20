package com.innodealing.engine.jpa.dm;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.innodealing.engine.jpa.im.BaseRepositoryDm;
import com.innodealing.model.dm.bond.BondBrokerDeal;
import com.innodealing.model.dm.bond.BondComExt;

@Component
public interface BondComExtRepository extends BaseRepositoryDm<BondComExt, Long> {
	BondComExt findByComUniCode(Long issuerId);
}
