package com.innodealing.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.jdbc.core.JdbcTemplate;

import com.innodealing.model.PageAdapter;
import com.innodealing.util.KitCost;
import com.innodealing.util.SQLParam;
import com.innodealing.util.Table;
import com.mongodb.BasicDBObject;

public class BaseService {

	protected static MongoTemplate bondMongoTemplate;
	

	@Resource(name = "bondMongo")  
    private void setOauthService(MongoTemplate bondMongoTemplate) {  
		BaseService.bondMongoTemplate = bondMongoTemplate;  
    }  
	
	@Autowired
	protected JdbcTemplate jdbcTemplate;

	protected <T> PageAdapter<Map<String, Object>> convToMap(PageAdapter<T> pageList, BasicDBObject fixedCols) {

		List<Map<String, Object>> content = new ArrayList<>();
		for (T t : pageList.getContent()) {
			content.add(KitCost.beanToMap(t, fixedCols));
		}
		PageAdapter<Map<String, Object>> result = new PageAdapter<>(content, pageList.getPageable(),
				pageList.getTotal());
		return result;
	}

	protected int save(Map<String, Object> record, String tableName) {
		Set<String> keySet = record.keySet();
		SQLParam sqlParam = new SQLParam();
		sqlParam.append("insert into ").append(tableName).append("(");
		for (String k : keySet) {
			sqlParam.append(k + ",");
			sqlParam.addArg(record.get(k));
		}
		sqlParam.setLength(sqlParam.length() - 1);
		sqlParam.append(")values(");
		sqlParam.placeholder(keySet.size());
		sqlParam.append(")");
		return jdbcTemplate.update(sqlParam.getSql(), sqlParam.getArg());

	}

	protected int save(Object obj) {
		return save(KitCost.beanToMap(obj), getTableName(obj.getClass()));
	}

	private String getTableName(Class<?> cls) {
		String tableName = cls.getSimpleName();
		Table tName = cls.getAnnotation(Table.class);
		if (tName != null) {
			tableName = tName.name();
		}
		return tableName;
	}

	/**
	 * 
	 * @param obj
	 *            更新对象
	 * @param cols
	 *            跟新列名 null表示更新所有
	 * @param where
	 *            条件
	 * @param args
	 *            参数
	 * @return
	 */
	protected int update(Object obj, String[] cols, String where, Object... args) {
		return update(KitCost.beanToMap(obj), cols, getTableName(obj.getClass()), where, args);
	}

	/**
	 * 
	 * @param record
	 *            更新KV
	 * @param cols
	 *            更新列名 null表示更新所有
	 * @param tableName
	 *            表名称
	 * @param where
	 *            条件
	 * @param args
	 *            参数
	 * @return
	 */
	protected int update(Map<String, Object> record, String[] cols, String tableName, String where, Object... args) {
		SQLParam sqlParam = new SQLParam();
		sqlParam.append("update ").append(tableName).append(" set ");
		if (cols == null) {
			Set<String> keySet = record.keySet();
			for (String k : keySet) {
				sqlParam.append(k + "=? ,");
				sqlParam.addArg(record.get(k));
			}
		} else {
			for (String k : cols) {
				sqlParam.append(k + "=? ,");
				sqlParam.addArg(record.get(k));
			}
		}

		sqlParam.setLength(sqlParam.length() - 1);
		sqlParam.append("where ").append(where);
		sqlParam.addArg(args);
		return jdbcTemplate.update(sqlParam.getSql(), sqlParam.getArg());
	}

}
