package com.innodealing.dao.jpa.dm.bond.ccxe;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import com.innodealing.dao.jpa.BaseRepository;
import com.innodealing.model.dm.bond.ccxe.BondFinGenProfTacpb;

@Component
public interface BondFinGenProfTacpbRep extends BaseRepository<BondFinGenProfTacpb, String>{
	
	@Query(nativeQuery=true,value="select * from d_bond_fin_gen_prof_tacpb where ccxeid >= ?1 and ccxeid <=?2 order by ccxeid limit ?3,?4")
	List<BondFinGenProfTacpb> getNewList(String fromTime, String toTime, Integer start, Integer length);
	
	@Query(nativeQuery=true,value="select count(1) from d_bond_fin_gen_prof_tacpb where ccxeid >= ?1 and ccxeid <=?2")
	Long getNewListCount(String fromTime, String toTime);
}
