package com.innodealing.engine.jpa.dm;

import java.util.List;

import org.springframework.stereotype.Component;

import com.innodealing.engine.jpa.im.BaseRepositoryDm;
import com.innodealing.model.dm.bond.BondFavoriteRadarSchema;

@Component
public interface BondFavoriteRadarSchemaRepository extends BaseRepositoryDm<BondFavoriteRadarSchema, Long> {

	List<BondFavoriteRadarSchema> findAllByParentIdAndStatus(Long parentId, Integer status);

    List<BondFavoriteRadarSchema> findAllByStatus(Integer status);
}
