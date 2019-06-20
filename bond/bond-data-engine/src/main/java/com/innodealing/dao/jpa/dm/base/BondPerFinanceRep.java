package com.innodealing.dao.jpa.dm.base;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import com.innodealing.dao.jpa.BaseRepository;
import com.innodealing.model.dm.bond.BondPerFinance;

@Component
public interface BondPerFinanceRep extends BaseRepository<BondPerFinance, Long> {
	
	@Query(nativeQuery = true, value = "SELECT * FROM t_bond_per_finance WHERE deleted = 0 AND task_id = ?")
	public BondPerFinance findByTaskId(Long taskId);

}
