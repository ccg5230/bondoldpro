package com.innodealing.engine.jdbc.bond;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.innodealing.model.dm.bond.BondCom;
import com.innodealing.model.dm.bond.IssPdHis;

/**
 * 安硕dm_bond表dao
 * @author zhaozhenglai
 * @since 2016年10月26日 上午10:13:12 
 * Copyright © 2016 DealingMatrix.cn. All Rights Reserved.
 */
@Component
public class AmaBondDao {
    public final static Logger logger = LoggerFactory.getLogger(AmaBondDao.class);
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    /**
     * 查找公司类别
     * @param comId 安硕主体id
     * @return
     */
    public String findComCategory(Long comId){
        String sql = "SELECT DISTINCT model_indu_name FROM  /*amaresun*/ dmdb.dm_bond WHERE Comp_ID = " + comId;
        
        String result = null;
        try {
            result = jdbcTemplate.queryForObject(sql, String.class);
        } catch (DataAccessException e) {
            //throw new BusinessException("该主体未对应模型行业，故无法计算得出风险等级。");
            logger.info("=====该主体未对应模型行业，故无法计算得出风险等级。===={amaId:" + comId + "}");
        }
        logger.info("sql --->" + sql);
        return result;
    }
    
   
    /**
     * 查找主体map
     * @param issId dm主体id
     * @return
     */
    public BondCom findIssMap(Long issId){
    	BondCom result = null;
        String sql = "SELECT com_uni_code AS issId,ama_com_id AS amsIssId,indu_uni_code AS induId,indu_uni_code_l4 AS induIdL4,indu_uni_code_sw AS induIdSw, com_chi_name AS issName FROM  dmdb.t_bond_com_ext WHERE com_uni_code = " + issId;
        logger.info("sql --->" + sql);
        try {
			result = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(BondCom.class));
		} catch (DataAccessException e) {
			logger.info("=====dmdb.t_bond_com_ext 表中暂无该主题信息，请确认该主体信息是否录入系统====={amaId:" + issId + "}");
		}
        return result;
    }
    
    /**
     * 主体违约概率历史
     * @param issId
     * @return
     */
    public List<IssPdHis> findIssPdHis(Long comId){
        String sql = "SELECT rating,`year`,w.quan_month AS 'month' from  /*amaresun*/ dmdb.dm_bond  as w WHERE Comp_ID = " + comId + " ORDER BY `year` DESC, quan_month DESC";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(IssPdHis.class));
    }
    
    /**
     * 获取主体行业的所有主体comUniCode
     * @param comUniCode
     * @return
     */
    public List<Long> getInduComUniCodes(Long comUniCode){
    	String sql = "SELECT com_uni_code FROM dmdb.t_bond_com_ext WHERE indu_uni_code = ( SELECT indu_uni_code FROM dmdb.t_bond_com_ext WHERE com_uni_code = " + comUniCode + ")";
    	return jdbcTemplate.queryForList(sql, Long.class);
    }
    
    /**
     * 查找所有主体信息
     * @param issId dm主体id
     * @return
     */
    public List<BondCom > findAll(){
        String sql = "SELECT com_uni_code AS issId,ama_com_id AS amsIssId,indu_uni_code AS induId,indu_uni_code_l4 AS induIdL4,indu_uni_code_sw AS induIdSw, com_chi_name AS issName FROM  dmdb.t_bond_com_ext ";
        logger.info("findAll sql --->" + sql);
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(BondCom.class));
    }
}
