package com.innodealing.engine.jpa.dm;

import com.innodealing.engine.jpa.im.BaseRepositoryDm;
import com.innodealing.model.dm.bond.BondFavorite;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public interface BondFavoriteRepository extends BaseRepositoryDm<BondFavorite, Integer>{

	@Query(nativeQuery = true, value = "SELECT * FROM t_bond_favorite where group_id = ?1 and is_delete = 0 AND bond_uni_code != 0 order by create_time desc")
	List<BondFavorite> findByGroupId(Integer groupId);
	
	@Query(nativeQuery = true, value = "SELECT * FROM t_bond_favorite where group_id = ?1 and is_delete = 0 AND bond_uni_code != 0 order by is_expired ASC,update_time desc LIMIT ?2,?3")
	List<BondFavorite> findByGroupIdAndLimit(Integer groupId, Integer index, Integer limit);
	
	@Query(nativeQuery = true, value = "SELECT * FROM t_bond_favorite where group_id = ?1 and is_delete = 0 AND bond_uni_code != 0 order by update_time desc")
	List<BondFavorite> findByGroupIdOrderByUpdateTimeDesc(Integer groupId);
	
	@Query(nativeQuery = true, value = "SELECT * FROM t_bond_favorite where user_id = ?1 and is_delete = 0")
	List<BondFavorite> findByUserId(Integer groupId);

	@Query(nativeQuery = true, value = "SELECT bond_uni_code FROM t_bond_favorite where bond_uni_code = ?1 and is_delete = 0")
	List<BondFavorite> findByBondUniCode(Long bondUniCode);

    @Modifying
    @Query("update BondFavorite u set u.createTime =?1, u.updateTime =?1 ,u.bookmarkUpdateTime =?1 where u.favoriteId = ?2")
    void updateByFavoriteId(Date date, Integer favoriteId);
	
	@Modifying
	@Query("UPDATE BondFavorite u set u.isDelete=1 where u.groupId = ?1")
	void deleteByGroupId(Integer groupId);

	@Modifying
	@Query("update BondFavorite u set u.isDelete=1 where u.favoriteId = ?1")
	void deleteByFavoriteId(Integer favoriteId);

	@Query(nativeQuery = true, value = "SELECT * FROM t_bond_favorite where group_id = ?1 and bond_uni_code = ?2 and is_delete = 0")
	List<BondFavorite> findFavoriteByGroupIdAndBondUniCode(Integer groupId, Long bondUniCode);
	
	@Query(nativeQuery = true, value = "SELECT count(1) FROM t_bond_favorite where group_id = ?1 and is_delete = 0 AND bond_uni_code != 0 ")
	int getFavoriteCountByGroupId(Integer groupId);
	
	@Query(nativeQuery = true, value = "SELECT count(1) FROM t_bond_favorite where group_id = ?1 and user_id = ?2 and is_delete = 0 AND bond_uni_code != 0 ")
	int getFavoriteCountByGroupIdAndUserId(Integer groupId, Integer userId);
	
	@Query(nativeQuery = true, value = "SELECT * FROM t_bond_favorite where user_id = ?1 and group_id=?2 and bond_uni_code=?3 and is_delete = 0 ORDER BY create_time DESC Limit 1")
    BondFavorite findByUserIdAndGroupIdAndBondUniCode(Integer userId, Integer groupId, Long bondUniCode);

	BondFavorite findOneByFavoriteIdAndIsDelete(Integer favoriteId, Integer isDelete);

	List<BondFavorite> findAllByFavoriteIdInAndIsDelete(List<Integer> favoriteIdList, Integer isDelete);

	List<BondFavorite> findAllByBondUniCodeAndIsDelete(Long bondUniCode, Integer isDelete);

	List<BondFavorite> findAllByBondUniCodeAndIsDeleteAndUserId(Long bondUniCode, Integer isDelete, Integer userId);
	
	BondFavorite findOneByGroupIdAndBondUniCodeAndIsDelete(Integer groupId, Long bondUniCode, Integer isDelete);

	List<BondFavorite> findAllByIsDeleteAndGroupIdIn(Integer isDelete, List<Integer> groupIdList);

	@Modifying
	@Query(nativeQuery = true, value = "UPDATE t_bond_favorite SET is_delete=1, update_time=?3 WHERE user_id=?1 AND group_id=?2 AND is_delete = 0")
	void logicDeleteByGroupId(Integer userId, Integer groupId, String currDateStr);
}
