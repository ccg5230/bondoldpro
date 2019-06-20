package com.innodealing.engine.jpa.dm;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import com.innodealing.engine.jpa.im.BaseRepositoryDm;
import com.innodealing.model.dm.bond.BondAnnAttInfoFtpFilePath;

@Component
public interface BondAnnAttInfoFtpFilePathRepository extends BaseRepositoryDm<BondAnnAttInfoFtpFilePath, Integer> {
	
	//获取ftpfilepath 
	@Query(nativeQuery = true, value = "SELECT id,ann_id,att_type,src_url,ftp_file_path,file_name,publish_date,src_url_md5,file_md5 from t_bond_ann_att_info where ftp_file_path !='' and id>?1 order by publish_date asc LIMIT ?2,?3")
	List<BondAnnAttInfoFtpFilePath> findFtpFilePathByPublishDate(Integer id,Integer index,Integer limit);
	
	//获取ftpfilepath的总数量
	@Query(nativeQuery = true, value = "SELECT count(1) FROM t_bond_ann_att_info where ftp_file_path !='' ")
	int getFtpFilePathCount();

}
