package com.innodealing.engine.jdbc;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;

import com.innodealing.util.BeanUtil;
import com.innodealing.util.pagination.Pagination;
import com.innodealing.util.pagination.Query;

public class BaseDao<T> {
    
    public final static Logger logger = LoggerFactory.getLogger(BaseDao.class);
    
    @Autowired
    @Qualifier("jdbcTemplate")
    protected JdbcTemplate jdbcTemplate;

    /**
	 * 分页
	 * @param sql sql语句
	 * @param q 查询条件
	 * @param clazz 类型
	 * @return
	 */
	public <E> Pagination<E> page(String sql ,Query q,Class<E> clazz){
	    //String qf  = q.getFilterSql();
	    String sqlJdbc = sql +q.getFilterSql() + q.getOrderSql() + " limit " +q.getIndex()+","+q.getPagesize();
	    String countSql = "select count(*) from ("+sql+q.getFilterSql()+") tem";
		List<Map<String,Object>> users = jdbcTemplate.queryForList( sqlJdbc );
		long total = jdbcTemplate.queryForObject(countSql, Long.class);
		logger.info("BaseDao query sqlJdbc ->" + sqlJdbc );
		logger.info("BaseDao conut sql ->" + countSql );
		return new Pagination<E>(total,BeanUtil.map2ListBean(users,clazz));
	}


}
