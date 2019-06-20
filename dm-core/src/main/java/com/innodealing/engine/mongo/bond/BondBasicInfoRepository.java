package com.innodealing.engine.mongo.bond;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import com.innodealing.model.mongo.dm.BondBasicInfoDoc;

import java.util.List;

@Component
public interface BondBasicInfoRepository extends MongoRepository<BondBasicInfoDoc, Long> {
	BondBasicInfoDoc findByOrgCode(String code);
	BondBasicInfoDoc findByCode(String code);
	BondBasicInfoDoc findByShortName(String shortName);
	
	@Cacheable(value = "BondBasicInfoDoc", key = "#bondUniCode")
    default
	BondBasicInfoDoc findByBondUniCode(String bondUniCode)
	{
	    return this.findOne(Long.valueOf(bondUniCode));
	}

	List<BondBasicInfoDoc> findAllByBondUniCodeIn(List<Long> bondUniCodeList);
}
