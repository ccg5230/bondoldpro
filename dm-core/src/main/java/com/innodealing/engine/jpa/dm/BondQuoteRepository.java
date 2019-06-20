package com.innodealing.engine.jpa.dm;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import com.innodealing.model.dm.bond.BondQuote;

/**
 * @author stephen.ma
 * @date 2016年9月2日
 * @clasename BondQuoteRepository.java
 * @decription TODO
 */
@Component
public interface BondQuoteRepository extends BaseRepository<BondQuote, Long> {
	
	@Modifying
	@Query(value = "UPDATE t_bond_quote t SET t.status = ?1,t.update_time = NOW() WHERE t.id = ?2", nativeQuery=true)
	public int updateQuoteStatus(Integer status, Long quoteId);
	
	@Query(nativeQuery = true, value = "SELECT * FROM dmdb.t_bond_quote where quote_id = ?1 LIMIT 1")
	BondQuote findByQuoteId(String quoteId);
	
}
