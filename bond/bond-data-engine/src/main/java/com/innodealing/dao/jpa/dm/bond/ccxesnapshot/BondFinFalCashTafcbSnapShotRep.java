package com.innodealing.dao.jpa.dm.bond.ccxesnapshot;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import com.innodealing.dao.jpa.BaseRepository;
import com.innodealing.model.dm.bond.ccxe.BondFinFalCashTafcb;

@Component("snapshotBean_BondFinFalCashTafcbRep")
public interface BondFinFalCashTafcbSnapShotRep extends BaseRepository<BondFinFalCashTafcb, String>{
	
	@Query(nativeQuery=true,value="select count(1) from d_bond_fin_fal_cash_tafcb where id = ?1 and bond_uni_code = ?2 and com_uni_code =?3 and end_date=?4")
	Long getCount(String id, Long bongUniCode, Long comUniCode, String endDate);
}
