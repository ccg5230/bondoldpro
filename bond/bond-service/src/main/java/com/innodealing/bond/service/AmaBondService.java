package com.innodealing.bond.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.innodealing.engine.jdbc.bond.AmaBondDao;
import com.innodealing.model.dm.bond.BondCom;
import com.innodealing.model.dm.bond.IssPdHis;

/**
 * 安硕dm_bond表service
 * @author zhaozhenglai
 * @since 2016年10月26日 上午10:11:31 
 * Copyright © 2016 DealingMatrix.cn. All Rights Reserved.
 */
@Service
public class AmaBondService {
    
    @Autowired
    private AmaBondDao amaBondDao;
    
    
    
    /**
     * 查找主体的行业分类
     * @param issId
     * @return
     */
    public String findIssCategory(Long issId){
        BondCom comMap = amaBondDao.findIssMap(issId);
        if(comMap != null){
            return amaBondDao.findComCategory(comMap.getAmsIssId());
        }else{
            return null;
        }
    }
    
    /**
     * 主体违约概率历史
     * @param issId
     * @return
     */
    public List<IssPdHis> findIssPdHis(Long issId){
        BondCom comMap = amaBondDao.findIssMap(issId);
        if(comMap != null){
            return amaBondDao.findIssPdHis(comMap.getAmsIssId());
        }else{
            return null;
        }
    }
    
}
