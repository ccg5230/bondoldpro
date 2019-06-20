package com.innodealing.engine.jpa.im;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @author stephen.ma
 * @date 2016年6月8日
 * @clasename BaseRepository.java
 * @decription TODO
 */
@NoRepositoryBean
public interface BaseRepositoryDm<T, ID extends Serializable> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {

}
