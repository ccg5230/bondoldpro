package com.innodealing.engine.jpa.dm;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import com.innodealing.engine.jpa.im.BaseRepositoryDm;
import com.innodealing.model.dm.bond.BondBrokerDeal;
import com.innodealing.model.dm.bond.BondConvRatio;
import com.innodealing.model.dm.bond.BondDuration;
import com.innodealing.model.dm.bond.BondDurationKey;

@Component
public interface BondDurationRepository 
    extends BaseRepositoryDm<BondDuration, BondDurationKey> {

	@Query(nativeQuery = true, value = "select * from dmdb.t_bond_duration  D order by D.duration_date desc limit 1")
	List<BondDuration> findLastestDuration();
}
