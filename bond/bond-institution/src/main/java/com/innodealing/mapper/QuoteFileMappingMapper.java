package com.innodealing.mapper;

import com.innodealing.model.mysql.QuoteFileMapping;

public interface QuoteFileMappingMapper {
	
	public int save(QuoteFileMapping entity);

	public QuoteFileMapping queryByOssKey(String ossKey);

}
