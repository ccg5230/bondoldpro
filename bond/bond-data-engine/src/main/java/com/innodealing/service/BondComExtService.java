package com.innodealing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.innodealing.dao.jdbc.dm.base.BondComExtDao;
import com.innodealing.dao.jpa.dm.base.BondComExtRep;
import com.innodealing.dao.redis.dm.RedisComExtDao;
import com.innodealing.domain.BondComExtVO;
import com.innodealing.util.RedisConstants;
import com.innodealing.util.StringUtils;

@Service
public class BondComExtService {

	@Autowired
	BondComExtRep bondComExtRep;
	
	@Autowired
	BondComExtDao bondComExtDao;

	@Autowired
	RedisComExtDao redisComExtDao;

	/**
	 * 如果DB中查询结果为null，则set到redis中amaComId值为0，下次直接从redis获取，并判断amaComId值是否为0即可
	 * 避免因null值不能判断redis是否存储数据，而不断从DB获取
	 * @param comType: bank银行，secu证券，insu保险。
	 */
	public Long getComId(Long comUniCode, String comType) {
		Long comId = (long) 0;
		
		if (StringUtils.isBlank(comType)) {
			return comId;
		}

		BondComExtVO vo = getComInfo(comUniCode);
		
		String compClsName = vo.getCompClsName();
		
		if (!StringUtils.isBlank(compClsName) && comType.equalsIgnoreCase(compClsName)) {
			comId = vo.getAmaComId();
		}
		return comId;
	}

	/**
	 * 无论是否有数据，都向redis存储，并返回对象
	 */
	public BondComExtVO getComInfo(Long comUniCode) {
		BondComExtVO vo = redisComExtDao.get(RedisConstants.BOND_COM_UNI_CODE_MATCH_ID + comUniCode);
		if (null != vo) {			
			return vo;
		}
		
		vo = bondComExtDao.getComInfoByComUniCode(comUniCode);
		if (null == vo) {
			vo = new BondComExtVO();
			vo.setAmaComId(Long.valueOf(0));
		}		
		redisComExtDao.save(RedisConstants.BOND_COM_UNI_CODE_MATCH_ID + comUniCode, vo);		
		return vo;
	}
}
