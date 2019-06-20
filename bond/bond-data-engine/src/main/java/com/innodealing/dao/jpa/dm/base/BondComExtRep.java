package com.innodealing.dao.jpa.dm.base;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import com.innodealing.dao.jpa.BaseRepository;
import com.innodealing.model.dm.bond.BondComExt;


@Component
public interface BondComExtRep extends BaseRepository<BondComExt, Long>{

	@Query(nativeQuery = true, value = "SELECT bce.ama_com_id FROM t_bond_com_ext bce where bce.com_uni_code = ?1 LIMIT 1")
	Long getComIdByComUniCode(Long comUniCode);
}
