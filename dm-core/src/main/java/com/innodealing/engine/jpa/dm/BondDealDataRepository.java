package com.innodealing.engine.jpa.dm;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.innodealing.model.dm.bond.BondDeal;


@Component
public interface BondDealDataRepository extends JpaRepository<BondDeal,Long> {

}
