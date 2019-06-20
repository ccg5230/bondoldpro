package com.innodealing.engine.jpa.dm;

import com.innodealing.model.dm.bond.BondPerFinance;

/**
 * @author xiaochao
 * @time 2017年3月9日
 * @description: 
 */
public interface BondPerFinanceRepository extends BaseRepository<BondPerFinance, Long> {

	BondPerFinance findOneByTaskId(Long taskId);

}
