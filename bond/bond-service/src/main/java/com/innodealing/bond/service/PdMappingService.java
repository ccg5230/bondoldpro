package com.innodealing.bond.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 违约概率映射service
 * @author zhaozhenglai
 * @since 2016年11月25日 下午5:08:17 
 * Copyright © 2016 DealingMatrix.cn. All Rights Reserved.
 */

import com.innodealing.domain.vo.bond.PdMappingVo;
import com.innodealing.engine.jdbc.bond.PdMappingDao;

@Service
public class PdMappingService {
    @Autowired
    private PdMappingDao pdMappingDao;

    /**
     * 获取pdMappping
     * @return
     */
    public List<PdMappingVo> getPdMapping() {
       return  pdMappingDao.getPdMapping();
    }
}
