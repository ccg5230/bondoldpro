package com.innodealing.engine.jpa.im.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import com.innodealing.engine.jpa.im.BaseRepositoryIm;
import com.innodealing.model.im.user.Sysuser;

/**
 * @author stephen.ma
 * @date 2016年7月27日
 * @clasename SysuserRepository.java
 * @decription TODO
 */
@Component
public interface SysuserRepository extends BaseRepositoryIm<Sysuser, Long> {
    
    @Query(nativeQuery = true, value = "SELECT * FROM t_sysuser where id = ?1") 
    public Sysuser findById(Long id);

}
