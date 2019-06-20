package com.innodealing.engine.jpa.im;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import com.innodealing.model.im.Orgainfo;

/**
 * @author stephen.ma
 * @date 2016年7月31日
 * @clasename OrgainfoRepository.java
 * @decription TODO
 */
@Component
public interface OrgainfoRepository extends BaseRepositoryIm<Orgainfo, Long> {
	
	@Query(nativeQuery = true, value = "SELECT * FROM t_orgainfo where status=1 AND fullname = ?1")	
	public Orgainfo findByFullname(String companyname);

}
