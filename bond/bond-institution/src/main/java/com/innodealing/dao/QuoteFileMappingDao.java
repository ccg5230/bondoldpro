package com.innodealing.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.innodealing.mapper.QuoteFileMappingMapper;
import com.innodealing.model.mysql.QuoteFileMapping;

@Repository
public class QuoteFileMappingDao {

	private @Autowired QuoteFileMappingMapper fileMappingMapper;

	public int save(QuoteFileMapping entity) {
		return fileMappingMapper.save(entity);
	}

	public QuoteFileMapping queryByOssKey(String ossKey) {
		return fileMappingMapper.queryByOssKey(ossKey);
	}

}
