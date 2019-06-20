package com.innodealing.engine.jpa.dm;

import com.innodealing.engine.jpa.im.BaseRepositoryDm;
import com.innodealing.model.dm.bond.BondFavoriteRadarMapping;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public interface BondFavoriteRadarMappingRepository extends BaseRepositoryDm<BondFavoriteRadarMapping, Long> {

	List<BondFavoriteRadarMapping> findAllByGroupId(Long groupId);

	List<BondFavoriteRadarMapping> findAllByGroupIdAndRadarIdIn(Long groupId, List<Long> subRadarTypeList);

	@Modifying
	@Transactional
	Long deleteByGroupIdIn(List<Long> groupIdList);

	@Modifying
	@Transactional
	Long deleteByGroupId(Long groupId);

	@Modifying
	@Transactional
	Long deleteByGroupIdNot(Long groupId);

	@Modifying
	@Transactional
	Long deleteByRadarId(Long radarId);

	@Modifying
	@Transactional
	Long deleteByRadarIdInAndGroupIdNot(List<Long> radarIdList, Long groupId);

    List<BondFavoriteRadarMapping> findAllByRadarId(Long radarId);
}
