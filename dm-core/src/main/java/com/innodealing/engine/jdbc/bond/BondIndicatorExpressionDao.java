package com.innodealing.engine.jdbc.bond;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.innodealing.model.dm.bond.BondIndicatorExpression;

/** 
* @Title: BondIndicatorExpressionDao.java 
* @Package com.innodealing.engine.jdbc.bond 
* @Description: 
* <p>Copyright: Copyright (c) 2017</p> *
* <p>Company: Dealing Matrix</p> *
* @author chungaochen
* @date 2017年5月11日 上午11:22:24 
* @version V1.0 
*/
@Component
public class BondIndicatorExpressionDao {
	
	public static final Logger log = LoggerFactory.getLogger(BondIndicatorExpressionDao.class);
	
	@Autowired private JdbcTemplate jdbcTemplate;
	
	/**
	 * 
	* @Title: listFindByField 
	* @Description: 根据指标代码查询专项指标计算公式
	* @author chungaochen
	* @param @param field
	* @param @return 
	* @return List<BondIndicatorExpression>  
	* @throws
	 */
	public List<BondIndicatorExpression> listFindByField(String field) {
		StringBuilder sql = new StringBuilder(64);
		sql.append("SELECT id,table_name,field,field_name,expression,remark,express_format FROM dmdb.t_bond_indicator_expression ") ;
		sql.append(" WHERE field = '" + field +"'");
		log.info("sql --->" + sql);
		List<BondIndicatorExpression> list = null;
        try {
        	list = jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<>(BondIndicatorExpression.class));
		} catch (DataAccessException e) {
			log.info("=====dmdb.t_bond_indicator_expression 表中暂无该专项信息，请确认该专项信息是否录入系统====={field:" + field + "}");
		}
        return list;
		
	}
	
	/**
	 * 
	 * findExpressDescription:(查询所有专项指标表达式文字描述)
	 * @param  @return    设定文件
	 * @return Map<String,String>    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	 */
	public Map<String,String> findExpressDescription() {
	    Map<String,String> result = new HashMap<>();
        StringBuilder sql = new StringBuilder(64);
        sql.append("SELECT id,table_name,field,field_name,expression,express_description,remark,express_format FROM dmdb.t_bond_indicator_expression ");
        sql.append(" GROUP BY field HAVING COUNT(1) =1 ") ;//仅查询无重复的指标
        log.info("sql --->" + sql);
        List<BondIndicatorExpression> list = null;
        try {
            list = jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<>(BondIndicatorExpression.class));
            for(BondIndicatorExpression bean : list) {
                result.put(bean.getField(), bean.getExpressDescription());
            }
        } catch (DataAccessException e) {
            log.info("=====dmdb.t_bond_indicator_expression 查询出错");
        }
        return result;
        
    }
}
