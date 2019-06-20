package com.innodealing.engine.jpa.dm;

import com.innodealing.engine.jpa.im.BaseRepositoryDm;
import com.innodealing.model.dm.bond.BondFavoriteGroup;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface BondFavoriteGroupRepository extends BaseRepositoryDm<BondFavoriteGroup, Integer>{

	@Query(nativeQuery = true, value = "SELECT count(*) FROM t_bond_favorite_group where user_id = ?1 and group_name = ?2 and is_delete = 0")
	Integer countGroupNameByUserId(Integer userId, String groupName);
	
	@Query(nativeQuery = true, value = "SELECT * FROM t_bond_favorite_group where user_id = ?1 and is_delete = 0 order by group_type asc, create_time asc")
	List<BondFavoriteGroup> findByUserId(Integer userId);
	
	@Modifying
	@Query("update BondFavoriteGroup u set u.isDelete=1 where u.groupId = ?1")
	void deleteByGroupId(Integer groupId);

	@Query(nativeQuery = true, value = "SELECT count(1) FROM t_bond_favorite_group where user_id = ?1 and group_type = ?2 and is_delete = 0")
	int countFavoriteGroupByUserIdAndGrouptype(Integer userId, Integer groupType);
	
	BondFavoriteGroup findByUserIdAndGroupType(Integer userId, Integer groupType);

	BondFavoriteGroup findOneByGroupIdAndIsDelete(Integer groupId, Integer isDelete);

	List<BondFavoriteGroup> findAllByGroupIdIn(List<Integer> groupIdList);

	List<BondFavoriteGroup> findAllByIsDelete(Integer isDelete);

	@Modifying
	@Query(nativeQuery = true, value = "UPDATE t_bond_favorite_group SET is_delete=1, update_time=?2 WHERE group_id = ?1 AND is_delete=0")
	void logicDeleteByGroupId(Integer groupId, String updateTimeStr);

	BondFavoriteGroup findOneByUserIdAndGroupNameAndIsDelete(Integer userId, String groupName, Integer isDelete);

	BondFavoriteGroup findOneByGroupIdAndUserIdAndIsDelete(Integer groupId, Integer userId, Integer isDelete);
}
