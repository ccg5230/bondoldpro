package com.innodealing.dao.jpa.dm.bond.gate;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import com.innodealing.dao.jpa.BaseRepository;
import com.innodealing.model.dm.bond.gate.BondRuleRowKey;

@Component
public interface BondRuleRowKeyRep extends BaseRepository<BondRuleRowKey, Long>{

	@Query(nativeQuery=true,value="select * FROM rule_row_key where TBL_NM=?1 limit 1")
	BondRuleRowKey getValidRuleRowKey(String tableName);

}
