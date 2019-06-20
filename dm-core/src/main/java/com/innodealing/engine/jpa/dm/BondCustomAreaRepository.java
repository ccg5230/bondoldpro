package com.innodealing.engine.jpa.dm;

import java.math.BigInteger;

import org.springframework.stereotype.Component;

import com.innodealing.engine.jpa.im.BaseRepositoryDm;
import com.innodealing.model.dm.bond.BondCustomArea;

@Component
public interface BondCustomAreaRepository extends BaseRepositoryDm<BondCustomArea, BigInteger>{
	

}
