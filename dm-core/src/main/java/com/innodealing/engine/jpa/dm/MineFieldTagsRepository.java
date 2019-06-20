package com.innodealing.engine.jpa.dm;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import com.innodealing.engine.jpa.im.BaseRepositoryDm;
import com.innodealing.model.dm.bond.MineFieldTags;

@Component
public interface MineFieldTagsRepository extends BaseRepositoryDm<MineFieldTags, Long> {

	@Query(nativeQuery = true, value = "SELECT * FROM innodealing.t_mine_field_tags WHERE id IN ?1")
	List<MineFieldTags> findByIdIn(List<Long> tagIdList);

}
