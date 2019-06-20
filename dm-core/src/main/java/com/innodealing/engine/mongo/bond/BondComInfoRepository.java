package com.innodealing.engine.mongo.bond;

import java.util.List;
import java.util.Map;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Component;

import com.innodealing.model.mongo.dm.BondComInfoDoc;

@Component
public interface BondComInfoRepository extends MongoRepository<BondComInfoDoc, Long> {
	
	/**
	 * 根据机构所属行业查找主体comUniCode
	 * @param institutionInduMap
	 * @return
	 */
	@Query(fields = "{'comUniCode':1}")
	public List<BondComInfoDoc> findByInstitutionInduMap(Map<String,Object> institutionInduMap);
	
	/**
	 * 根据机构所属行业查找主体comUniCode
	 * @param induId
	 * @return
	 */
	@Query(fields = "{'comUniCode':1}")
	public List<BondComInfoDoc> findByInduId(Long induId);
	
	/**
	 * 根据机构所属行业查找主体comUniCode
	 * @param induIdSw
	 * @return
	 */
	@Query(fields = "{'comUniCode':1}")
	public List<BondComInfoDoc> findByInduIdSw(Long induIdSw);
	
}
