package com.innodealing.datacanal.service;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.innodealing.datacanal.dao.BondDataCanalLogDao;
import com.innodealing.datacanal.dao.BondIssuerDao;
import com.innodealing.datacanal.dao.IdGenerateDao;
import com.innodealing.datacanal.model.ColumnItem;
import com.innodealing.datacanal.model.TargertData;
import com.innodealing.datacanal.util.MD5Util;
import com.innodealing.datacanal.vo.UpdateSqlArgsVo;
/**
 * 
 * @author 赵正来
 * dmbond阿里云数据汇聚到DM-IDC接口
 */

@Service
public class DataCanalService {
	
	@Autowired
	private JdbcTemplate dataCenterJdbcTemplate;

	@Autowired
	private BondIssuerDao bondIssuerDao;
	
	@Autowired
	private BondDataCanalLogDao bondDataCanalLogDao;
	
	@Autowired
	private IdGenerateDao idGenerateDao;
	
	protected final static Logger logger = LoggerFactory.getLogger(DataCanalService.class);
	/**
	 * 更新同步
	 * @return
	 */
//	@Transactional
	public boolean insertCanal(TargertData targertData) {
		if (targertData == null) {
			logger.info("数据为空，检查表映射配置是否遗漏");
			return false;
		}
		
		UpdateSqlArgsVo argsVo =  buildInsertSqlArgs(targertData);
		Object[] args = argsVo.getArgs();
		String sql = argsVo.getSql(); 
		String msg = null;
		try {
			logger.debug("sql->" + sql + ",args=" + Arrays.toString(args));
			dataCenterJdbcTemplate.update(sql, args);
		} catch (Exception e) {
			if (e.getMessage().contains("Duplicate")) {
				msg = "数据已存在" +sql + ",args=" + Arrays.toString(args);
				logger.error(msg);
			} else {
				msg = e.getMessage() + ",args=" + Arrays.toString(args) + sql;
				logger.error(msg, e);
				bondDataCanalLogDao.insert(msg);
				throw e;
			}
		}

		return true;
	}

	/**
	 * 批量插入
	 * @param targertDatas
	 * @return
	 */
	public boolean insertCanalBatch(List<TargertData>  targertDatas){
		if(targertDatas == null || targertDatas.size() == 0){
			return false;
		}
		List<Object[]> batchArgs = new ArrayList<>();
		String sql = "";
		int index = 0;
		for (TargertData data : targertDatas) {
			UpdateSqlArgsVo  argsVo = buildInsertSqlArgs(data);
			batchArgs.add(argsVo.getArgs());
			if(index++ == 0){
				sql = argsVo.getSql();
			}
		}
		List<Long>  ids = new ArrayList<>();
		for (TargertData data : targertDatas) {
			ColumnItem item = data.getColums().get("ID");
			ids.add(Long.valueOf(item.getValue() == null? "0" : item.getValue().toString()) ) ;
		}
		//先删除
		String tableName = targertDatas.get(0).getTableName();
		dataCenterJdbcTemplate.update("delete from dm_data." + tableName.toLowerCase()  + " where id in (?)", ids);
		//然后插入
		if(batchArgs.size() > 0){
			int[] result = dataCenterJdbcTemplate.batchUpdate(sql, batchArgs);
			logger.info("insertCanalBatch result->" + Arrays.toString(result));
		}
		return true;
	}
	
	
	/**
	 * 更新同步
	 * 
	 * @return
	 */
//	@Transactional
	public boolean updateCanal(TargertData targertData) {
		if (targertData == null) {
			logger.info("数据为空，检查表映射配置是否遗漏");
			return false;
		}
		 //构建条件语句
		 String table = targertData.getTableName();
		// 获取sk
		List<ColumnItem> sks = new ArrayList<>();//getSK(targertData);
		Long id = idGenerateDao.insert(targertData);
		ColumnItem columnItem = new ColumnItem("ID", id, 0, true, false);
		sks.add(columnItem);
		 String whereSql = getWhereSql(sks);
		 StringBuilder sb = new StringBuilder("update " + table.toLowerCase() + " set ");
		 LinkedHashMap<String, ColumnItem> data = targertData.getColums();
		 long seqId = 0;
//		try {
			seqId = findLastSEqIDd(targertData);
//		} catch (EmptyResultDataAccessException e) {
//			insertCanal(targertData);
//			return true;
//		}
		 data.put("SEQ_ID", new ColumnItem("SEQ_ID", seqId + 1, Types.BIGINT, false, false));
		 Object[]args = new Object[data.size() + sks.size()];
		 int index = 0;
		 //update column
		 for (java.util.Map.Entry<String, ColumnItem> entry : data.entrySet()) {
			 sb.append(entry.getKey()).append(" = ").append(" ? ,");
			 args[index] = entry.getValue().isNull() ? null : entry.getValue().getValue();
			 index ++;
		 }
		 //where column
		 for(ColumnItem item : sks){
			 args[index] = item.getValue();
			 index ++;
		 }
		 StringBuilder sbSql = new StringBuilder(sb.substring(0, sb.length() - 2));
		 sbSql.append(whereSql);
		 logger.debug("sql->" + sbSql.toString() + ",args=" + Arrays.toString(args));
		 try {
			dataCenterJdbcTemplate.update(sbSql.toString(), args);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		 return true;
	}

	/**
	 * 更新同步
	 * 
	 * @return
	 */
//	@Transactional
	public boolean deleteCanal(TargertData targertData) {
		if (targertData == null) {
			logger.info("数据为空，检查表映射配置是否遗漏");
			return false;
		}
		// 构建条件语句
		String table = targertData.getTableName();
		// 获取sk
		List<ColumnItem> sks = getSK(targertData);
		getSK(targertData);
		String whereSql = getWhereSql(sks);
		StringBuilder sb = new StringBuilder("delete from " + table.toLowerCase());
		sb.append(whereSql);
		//参数
		Object[] args = new Object[sks.size()];
	    for (int index = 0; index < sks.size(); index++) {
	    	args[index] = sks.get(index).getValue();
		}
	    logger.info("sql->" + sb.toString() + ",args=" + Arrays.toString(args));
		dataCenterJdbcTemplate.update(sb.toString(), args);
		return true;
	}
	
	/**
	 * 查询最后操作次数
	 * @param targertData
	 * @return
	 */
	private Long findLastSEqIDd(TargertData targertData){
		if (targertData == null) {
			logger.info("数据为空，检查表映射配置是否遗漏");
			return 0L;
		}
		// 构建条件语句
		String table = targertData.getTableName();
		// 获取sk
		List<ColumnItem> sks = getSK(targertData);
		getSK(targertData);
		String whereSql = getWhereSql(sks);
		StringBuilder sb = new StringBuilder("select SEQ_ID from " + table);
		sb.append(whereSql);
		//参数
		Object[] args = new Object[sks.size()];
	    for (int index = 0; index < sks.size(); index++) {
	    	args[index] = sks.get(index).getValue();
		}
	    logger.debug("sql->" + sb.toString() + ",args=" + Arrays.toString(args));
		Long seqId = null;
		try {
			seqId = dataCenterJdbcTemplate.queryForObject(sb.toString().toLowerCase(),  Long.class, args);
		} catch (DataAccessException e) {
			logger.error( e.getMessage()+ "sql->" + sb.toString() + ",args=" + Arrays.toString(args),e);
		}
		return seqId == null ? 0L : seqId;
	}
	
	/**
	 * 判断主体是否是dm主体
	 * @param value
	 * @return
	 */
	public boolean isDmIssuer(Object comUniCode) {
		if(comUniCode == null){
			return false;
		}else{
			return bondIssuerDao.findInduUniCodeSwFromCache(Long.valueOf(comUniCode.toString())) != null;
		}
	}



	/**
	 * 查找SK
	 * 
	 * @param trgertData
	 * @return
	 */
	public static List<ColumnItem> getSK(TargertData targertData) {
		if (targertData == null || targertData.getColums() == null) {
			return null;
		}
		List<ColumnItem> sks = new LinkedList<>();
		
		targertData.getColums().forEach((k, v) -> {
			if (v.isKey()) {
				sks.add(v);
			}
		});
		return sks;
	}

	/**
	 * 获取条件语句
	 * 
	 * @param data
	 * @param whereKeys
	 * @return
	 */
	private String getWhereSql(List<ColumnItem> sks) {
		StringBuilder whereSql = new StringBuilder(" where ");
		if (sks == null) {
			return "";
		} else {
			for (ColumnItem sk : sks) {
				whereSql.append(sk.getName()).append(" = ?  and ");
			}
		}
		return whereSql.substring(0, whereSql.length() - 5);
	}
	
	
	/**
	 * 构建insert sql and args
	 * @param targertData
	 * @return
	 */
	private UpdateSqlArgsVo buildInsertSqlArgs(TargertData targertData) {
		// 构建条件语句
		String table = targertData.getTableName();
		StringBuilder sb = new StringBuilder("insert into " + table.toLowerCase());
		StringBuilder sbColums = new StringBuilder(" ( ");
		StringBuilder values = new StringBuilder(" values( ");

		LinkedHashMap<String, ColumnItem> data = targertData.getColums();
		// 设置管理字段
		data.put("ID", new ColumnItem("ID", idGenerateDao.insert(targertData),
				Types.VARCHAR, false, false));
		//data.put("CREATE_TIME", new ColumnItem("CREATE_TIME", updateTime, Types.BIGINT, false, false));
		//data.put("LAST_UPDATE_TIME", new ColumnItem("LAST_UPDATE_TIME", updateTime, Types.BIGINT, false, false));
		Object[] args = new Object[data.size()];
		Object[] argTypes = new Object[data.size() ];
		int index = 0;
		//colum name
		for (java.util.Map.Entry<String, ColumnItem> entry : data.entrySet()) {
			sbColums.append(entry.getKey()).append(", ");
			args[index] = entry.getValue().isNull() ? null : entry.getValue().getValue();
			argTypes[index] = entry.getValue().getSqlType();
			index++;
		}
		//column value
		data.forEach((colum, value) -> {
			values.append("?, ");
		});
		sb.append(sbColums.substring(0, sbColums.length() - 2) + " ) ")
				.append(values.substring(0, values.length() - 2) + " ) ");

		UpdateSqlArgsVo argsVo = new UpdateSqlArgsVo();
		argsVo.setArgs(args);
		argsVo.setSql(sb.toString());
		return argsVo;
	}
	
	public static void main(String[] args) {
		System.out.println(MD5Util.getMD5("343").length());
	}

}
