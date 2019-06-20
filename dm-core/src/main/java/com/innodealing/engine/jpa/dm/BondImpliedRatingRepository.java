package com.innodealing.engine.jpa.dm;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import com.innodealing.engine.jpa.im.BaseRepositoryDm;
import com.innodealing.model.dm.bond.BondImpliedRating;

@Component
public interface BondImpliedRatingRepository extends BaseRepositoryDm<BondImpliedRating, Long> {
	BondImpliedRating findByBondCode(Long bondUniCode);
	
	@Query(nativeQuery = true, value = "SELECT * FROM t_bond_implied_rating where bond_code = ?1 order by data_date desc limit 1")
	BondImpliedRating findLastByBondCode(Long bondUniCode);
}
