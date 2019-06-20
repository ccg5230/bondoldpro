package com.innodealing.engine.jpa.dm;

import com.innodealing.engine.jpa.im.BaseRepositoryDm;
import com.innodealing.model.dm.bond.BondFavoriteFinaIndex;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public interface BondFavoriteFinaIndexRepository extends BaseRepositoryDm<BondFavoriteFinaIndex, Long> {

	List<BondFavoriteFinaIndex> findAllByFavoriteId(Long favoriteId);

	@Transactional
	void deleteByGroupId(Long groupId);

	@Transactional
	void deleteByFavoriteId(Long favoriteId);
	
	@Transactional
	void deleteByFavoriteIdIn(List<Long> favIdList);

	List<BondFavoriteFinaIndex> findAllByGroupIdAndFavoriteId(Long groupId, Long favoriteId);

	@Transactional
	void deleteByGroupIdAndFavoriteId(Long groupId, Long favoriteId);

	List<BondFavoriteFinaIndex> findAllByGroupIdAndFavoriteIdAndStatus(Long groupId, Long favoriteId,
			Integer status);

	@Transactional
	Long deleteByGroupIdIn(List<Long> groupIdList);

	@Transactional
	Long deleteByGroupIdNotOrFavoriteIdNot(Long groupId, Long favoriteId);

	List<BondFavoriteFinaIndex> findAllByGroupIdAndFavoriteIdIn(Long groupId, List<Long> favIdList);
}
