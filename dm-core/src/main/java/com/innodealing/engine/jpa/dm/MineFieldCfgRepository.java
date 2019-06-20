package com.innodealing.engine.jpa.dm;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import com.innodealing.engine.jpa.im.BaseRepositoryDm;
import com.innodealing.model.dm.bond.MineFieldCfg;

@Component
public interface MineFieldCfgRepository extends BaseRepositoryDm<MineFieldCfg, Long> {

//	@Query(nativeQuery = true, value = "SELECT * FROM innodealing.t_mine_field_cfg WHERE isnegative IN ?1"
//			+ " AND classification IN ?2 AND update_time BETWEEN ?3 AND ?4 AND isdisplayed=1 ORDER BY update_time DESC")
//	List<MineFieldCfg> getWithCreteria(List<Integer> negaTypeList, List<Integer> clsiTypeList,
//			String startDate, String endDate);
	
	@Query(nativeQuery = true, value = "SELECT mfc.*, dmf.publishtime FROM innodealing.t_mine_field_cfg AS mfc"
			+ " LEFT JOIN innodealing.dmms_mine_field AS dmf ON dmf.id=mfc.mine_id"
			+ " WHERE mfc.isnegative IN ?1 AND mfc.classification IN ?2 AND dmf.publishtime BETWEEN ?3 AND ?4"
			+ " AND mfc.isdisplayed=1 AND mfc.issuer_id IS NOT NULL AND mfc.issuer_id!=0"
			+ " ORDER BY dmf.publishtime DESC, mfc.update_time DESC")
	List<MineFieldCfg> getWithCreteria(List<Integer> negaTypeList, List<Integer> clsiTypeList,
			String startDate, String endDate);
	

}
