package com.innodealing.engine.jpa.dm;

import com.innodealing.engine.jpa.im.BaseRepositoryDm;
import com.innodealing.model.dm.bond.BondFavoritePriceIndex;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public interface BondFavoritePriceIndexRepository extends BaseRepositoryDm<BondFavoritePriceIndex, Long> {

	List<BondFavoritePriceIndex> findAllByFavoriteId(Long favoriteId);

	@Modifying
	@Transactional
	void deleteByGroupId(Long groupId);

	@Modifying
	@Transactional
	void deleteByFavoriteId(Long favoriteId);

	@Modifying
	@Transactional
	void deleteByFavoriteIdIn(List<Long> favIdList);

	List<BondFavoritePriceIndex> findAllByGroupIdAndFavoriteIdIn(Long groupId, List<Long> favIdList);

	List<BondFavoritePriceIndex> findAllByGroupIdAndFavoriteId(Long groupId, Long favoriteId);

	List<BondFavoritePriceIndex> findAllByGroupIdAndFavoriteIdAndStatus(Long groupId, Long favoriteId,
			Integer status);
	
	@Transactional
	void deleteByGroupIdAndFavoriteId(Long groupId, Long favoriteId);

	@Transactional
	Long deleteByGroupIdIn(List<Long> groupIdList);

	@Transactional
	Long deleteByGroupIdNotOrFavoriteIdNot(Long groupId, Long favoriteId);
}
