package com.innodealing.engine.jpa.dm;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import com.innodealing.engine.jpa.im.BaseRepositoryDm;
import com.innodealing.model.dm.bond.BondBrokerDeal;
import com.innodealing.model.dm.bond.BondComExt;
import com.innodealing.model.dm.bond.BondConvRatio;
import com.innodealing.model.dm.bond.BondConvRatioKey;
import com.innodealing.model.dm.bond.BondFavoriteGroup;

@Component
public interface BondConvRatioRepository extends BaseRepositoryDm<BondConvRatio, BondConvRatioKey> {
	@Query(nativeQuery = true, value = "select * from t_bond_conv_ratio R where R.create_date < ? order by create_date desc limit 1")
	List<BondConvRatio> findBeforeDate(String createDate);
	
	@Query(nativeQuery = true, value = "select * from t_bond_conv_ratio R order by R.create_date desc limit 1")
	List<BondConvRatio> findLastestConvRatio();
	
	@Query(nativeQuery = true, value = "select * from t_bond_conv_ratio R where R.create_date = ?")
	List<BondConvRatio> findByCreateDate(String createDate);
}
