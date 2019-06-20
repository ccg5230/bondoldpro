package com.innodealing.datacanal.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;

import com.google.common.base.Objects;
import com.innodealing.datacanal.constant.ConstantCalnal;
import com.innodealing.datacanal.model.ColumnItem;
import com.innodealing.datacanal.model.TargertData;
import com.innodealing.datacanal.service.DataCanalService;
import com.innodealing.datacanal.util.MD5Util;

/**
 * id生成期
 * 
 * @author 赵正来
 *
 */
@Component
public class IdGenerateDao {

	// dmdb 数据源
	private @Autowired JdbcTemplate dataCenterJdbcTemplate;
	
	

	private final Logger logger = LoggerFactory.getLogger(BondUniCodeMapDao.class);

	public Long insert() {                
		String sql = "insert into dm_data.id_generate(mark) values(?)";
		Long bondUniCodeDateCenter = null;
		try {
			GeneratedKeyHolder holder = new GeneratedKeyHolder();
			dataCenterJdbcTemplate.update(new PreparedStatementCreator() {
				@Override
				public java.sql.PreparedStatement createPreparedStatement(java.sql.Connection con) throws SQLException {
					PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
					statement.setLong(1, 0);
					return statement;
				}
			}, holder);
			bondUniCodeDateCenter = holder.getKey().longValue();
		} catch (Exception e) {

			logger.error(e.getMessage(), e);

		}
		//logger.debug("sql -> " + sql);
		return bondUniCodeDateCenter;
	}
	
	/**
	 * 获取唯一id
	 * @param tableName
	 * @param unique
	 * @return
	 */
	public Long insert(TargertData targertData) {   
		//long s = System.currentTimeMillis();
		Long bondUniCodeDateCenter = null;
		String tableIdgenerate = getFinTableIdGenerate(targertData);
		String unique  = getUnique(targertData);
		if(unique == null){
			return insert();
		}
		String uniqueMd5 = MD5Util.getMD5(unique);
		//先查找
		String selectSql = "select id from dm_data." + tableIdgenerate.toLowerCase() + " where unique_md5 = ?";
		Object[] args = {uniqueMd5};
		try {
			bondUniCodeDateCenter = dataCenterJdbcTemplate.queryForObject(selectSql,args ,Long.class);
		} catch (DataAccessException e1) {
		}
		if(bondUniCodeDateCenter != null){
			return bondUniCodeDateCenter;
		}
		String sql = "insert into dm_data." + tableIdgenerate.toLowerCase() + "(unique_id,unique_md5) values(?,?)";
		//没有在插入
		try {
			GeneratedKeyHolder holder = new GeneratedKeyHolder();
			dataCenterJdbcTemplate.update(new PreparedStatementCreator() {
				@Override
				public java.sql.PreparedStatement createPreparedStatement(java.sql.Connection con) throws SQLException {
					PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
					statement.setString(1, unique);
					statement.setString(2, uniqueMd5);
					return statement;
				}
			}, holder);
			bondUniCodeDateCenter = holder.getKey().longValue();
		} catch (Exception e) {
			logger.error(e.getMessage() +  sql, e);

		}
		//long e = System.currentTimeMillis();
		//logger.info("耗时" + (e-s) + "ms");
		//logger.debug("sql -> " + sql);
		return bondUniCodeDateCenter == null ? System.currentTimeMillis() : bondUniCodeDateCenter;
	}
	
	/**
	 * 判断表是否是财务表
	 * @param tableName
	 * @return
	 */
	public String getFinTableIdGenerate(TargertData targertData){
		if(targertData == null){
			return null;
		}
		String tableName = targertData.getTableName();
		String finTableIdGenerate = getIdGeberateTableName(tableName);
		return finTableIdGenerate == null ? null : finTableIdGenerate.toLowerCase();
	}

	/**
	 * 是否存在
	 * @param srcTableName
	 * @return
	 */
	public  boolean isExist(String srcTableName,String uniqueMd5){
		String idGenerateTable =  getIdGeberateTableName(srcTableName.toUpperCase());
		String sql = "select count(1) from dm_data." + idGenerateTable.toLowerCase() + " where unique_md5 = ?";
		String[] args = {uniqueMd5};
		Integer count = dataCenterJdbcTemplate.queryForObject(sql, args, Integer.class);
		return Objects.equal(count, 1);
	}
	
	/**
	 * 获取id表
	 * @param tableName
	 * @return
	 */
	private String getIdGeberateTableName(String tableName) {
		String finTableIdGenerate = null;
		String profix = "ID_GENERATE_";
		if(tableName.contains(ConstantCalnal.FAL_BALA_TAFBB)){
			finTableIdGenerate =  profix + ConstantCalnal.FAL_BALA_TAFBB;
		}else if(tableName.contains(ConstantCalnal.FAL_CASH_TAFCB)){
			finTableIdGenerate =  profix + ConstantCalnal.FAL_CASH_TAFCB;
		}else if(tableName.contains(ConstantCalnal.FAL_PROF_TAFPB)){
			finTableIdGenerate =  profix + ConstantCalnal.FAL_PROF_TAFPB;
		}else if(tableName.contains(ConstantCalnal.GEN_BALA_TACBB)){
			finTableIdGenerate =  profix + ConstantCalnal.GEN_BALA_TACBB;
		}else if(tableName.contains(ConstantCalnal.GEN_CASH_TACCB)){
			finTableIdGenerate =  profix + ConstantCalnal.GEN_CASH_TACCB;
		}else if(tableName.contains(ConstantCalnal.GEN_PROF_TACPB)){
			finTableIdGenerate =  profix + ConstantCalnal.GEN_PROF_TACPB;
		}else{
			return "ID_GENERATE";	
		}
		return finTableIdGenerate;
	}
	
	
	
	/**
	 * 获取唯一索引
	 * @param getUnique
	 * @return
	 */
	public String getUnique(TargertData targertData){
		List<ColumnItem>  sks = DataCanalService.getSK(targertData);
		StringBuffer unique = new StringBuffer(targertData.getTableName() + "-");
		for (ColumnItem columnItem : sks) {
			unique.append(columnItem.getValue())
				.append("-");
		}
		return unique.substring(0, unique.length()-1);
	}
	

	
	public static void main(String[] args) {
		System.out.println(new IdGenerateDao().isExist("D_BOND_FIN_GEN_BALA_TACBB","12"));
	}
	
}
