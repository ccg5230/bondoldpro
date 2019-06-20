package com.innodealing.datacanal.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.innodealing.datacanal.constant.ConstantCalnal;
import com.innodealing.datacanal.util.DateUtil;
import com.innodealing.datacanal.vo.DataCompareVo;
import com.innodealing.datacanal.vo.SyncTableTotalVo;
/**
 * DM数据同步，主要弥补canal 出现异常|数据初始化时候有遗漏，导致数据不一致。
 * @author 赵正来  
 *
 */
@Component
public class DmSyncDao {
	//dmdb 阿里云
	private @Autowired JdbcTemplate dmdbJdbcTemplate;
	
	//DM-IDC数据中心
	private @Autowired JdbcTemplate dataCenterJdbcTemplate;
	
	private static final Logger logger = LoggerFactory.getLogger(DmSyncDao.class);
	
	/**
	 * 
	 * @return
	 */
	public List<DataCompareVo> notInIdcDmDataNum(){
		return null;
	}
	
	/**
	 * 获取源数量信息
	 * @param srcTableName
	 * @param srcKeyColumn
	 * @param relationTable
	 * @param relationDataCenterColum
	 */
	public List<SyncTableTotalVo> findFinTotalInfoForSrc(String srcTableName, String srcKeyColumn, String relationTable, String relationDataCenterColum){
		StringBuffer sb = new StringBuffer("");
		if(relationTable == null){
			sb.append(" select count(").append(srcKeyColumn).append(") as total,")
				.append(srcKeyColumn)
				.append("as key_column from bond_ccxe.").append(srcTableName).append(" group by " + srcKeyColumn);
		}else{
			sb.append(" select ")
			.append(" 	map." + relationDataCenterColum + "  as key_column, tem.total, ")
			.append(" 	tem." + srcKeyColumn + "  as src_key_column ")
			.append(" from ")
			.append(" dmdb."+ relationTable + " map ")
			.append(" right join ( ")
			.append(" select ")
			.append(" count(" + srcKeyColumn + ") as total, ")
			.append( srcKeyColumn )
			.append(" from ")
			.append(" bond_ccxe." + srcTableName)
			.append(" group by ")
			.append(srcKeyColumn)
			.append(" ) tem on tem." + srcKeyColumn + " = map." + srcKeyColumn);
		}
		logger.debug("findFinTotalInfoForSrc slq ->" + sb.toString());
		List<SyncTableTotalVo> result = null;
		try {
			result = dmdbJdbcTemplate.query(sb.toString(), new BeanPropertyRowMapper<>(SyncTableTotalVo.class));
		} catch (DataAccessException e) {
			logger.error(sb.toString(), e);
		}
		return result;
	}
	
	
	/**
	 * 获取源表关键字的值
	 */
	public Long findSrcKeyColumnValue(String relationTable, String srcKeyColumn, String relationDataCenterColum, Long dataCenterKeyValue){
		
		String sql = "select " + srcKeyColumn + " from dmdb." + relationTable + " where " + relationDataCenterColum + " = " + dataCenterKeyValue ;
		logger.debug("findSrcKeyColumnValue slq ->" + sql.toString());
		return dmdbJdbcTemplate.queryForObject(sql, Long.class);
	}
	
	/**
	 * 获取目标数量信息
	 * @param destTableName
	 * @param destKeyColumn
	 */
	public List<SyncTableTotalVo> findFinTotalInfoForDest(String destTableName, String destKeyColumn){
		StringBuffer sb = new StringBuffer("");
		sb.append(" select count(").append(destKeyColumn).append(") as total,").append(destKeyColumn)
		.append(" as key_column from dm_data.").append(destTableName).append(" group by " + destKeyColumn);
		List<SyncTableTotalVo> result = null;
		logger.debug("findFinTotalInfoForSrc slq ->" + sb.toString());
		try {
			result = dataCenterJdbcTemplate.query(sb.toString(), new BeanPropertyRowMapper<>(SyncTableTotalVo.class));
		} catch (DataAccessException e) {
			logger.error(sb.toString(), e);
		}
		return result;
	}
	
	/**
	 * 获取要同步的数据
	 * @param srcTableName
	 * @param key
	 * @param value
	 * @param limit
	 * @return
	 */
	public List<Map<String,Object>> findPrepareSyncData(String srcTableName, String key, Long value){
		String sql = "select * from bond_ccxe." + srcTableName + " where " + key + " = ?  ";
		List<Map<String,Object>> result = null;
		String msg = ("sql ->" + sql + ",args=" + value);
		try {
			logger.info("msg->" + msg);
			result = dmdbJdbcTemplate.queryForList(sql, value);
		} catch (DataAccessException e) {
			logger.error(msg + e.getMessage(), e);
		}
		return result;
	}
	
	/**
	 * 删除错误的数据
	 * @param destTableName
	 * @param key
	 * @param value
	 * @return
	 */
	public int deleteErrorDestData(String destTableName, String key, Long value){
		String sql = "delete from dm_data." + destTableName + " where " + key + " = ? ";
		int result = 0;
		String msg = ("sql ->" + sql + ",args=" + value);
		try {
			result = dataCenterJdbcTemplate.update(sql, value);
			logger.info(result + " rows delete,info->" + msg);
		} catch (DataAccessException e) {
			logger.error(msg + e.getMessage(), e);
		}
		return result;
	}
	
	
	/**
	 * 更新状态为非删除状态
	 * @param destTableName
	 * @param ltId
	 * @param status
	 * @return
	 */
	public int updataStatusToNoDeleteDestData(String destTableName, Long ltId, Byte status){
		String sql = "update  dm_data." + destTableName.toLowerCase() + " set `STATUS` = " + status + " where `STATUS` = 1 and  ID < ? ";
		int result = 0;
		String msg = ("sql ->" + sql + ",id=" + ltId + "");
		try {
			result = dataCenterJdbcTemplate.update(sql, ltId);
			logger.info("update " + (result == 1)  + ",info->" + msg);
		} catch (DataAccessException e) {
			logger.error(msg + e.getMessage(), e);
		}
		return result;
	}
	
	/**
	 * 查找数据中心未删除的数据id
	 * @param destTableName
	 * @return
	 */
	public List<Long> findAllDestIdDelete(String destTableName){
		String sql = "select id from  dm_data." + destTableName + "  where `STATUS` = " + ConstantCalnal.DELETE + " order by ID asc";
		try {
			return dataCenterJdbcTemplate.queryForList(sql, Long.class);
		} catch (DataAccessException e) {
			return new ArrayList<>();
		}
	}
	
	/**
	 * 查找数据中心表最大id
	 * @param destTableName
	 * @return
	 */
	public Long findMaxIdDest(String destTableName){
		String sql = "select MAX(ID) from  dm_data." + destTableName.toLowerCase() ;
		try {
			return dataCenterJdbcTemplate.queryForObject(sql, Long.class);
		} catch (DataAccessException e) {
			return 0L;
		}
	}
	
	/**
	 * 查询最后更新时间
	 * @param destTableName
	 * @return
	 */
	public Date findDestLastUpdateTime(String destTableName){
		String sql = "select max(last_update_time) as last_update_time from dm_data." + destTableName;
		logger.info("findDestLastUpdateTime -> " + sql);
		return dataCenterJdbcTemplate.queryForObject(sql, Date.class);
	}
	
	/**
	 * 查找满足条件的元数据表条数
	 * @param srcTableName
	 * @param ccxeid
	 * @return
	 */
	public long findSrcTotal(String srcTableName, Date ccxeid){
		String sql = "select count(1) from bond_ccxe." + srcTableName + " where ccxeid > ?" ;
		Object[] args = {ccxeid};
		logger.debug("findSrcTotal -> " + sql + ";args=" +  Arrays.toString(args));
		try {
			return dmdbJdbcTemplate.queryForObject(sql, args , Long.class);
		} catch (DataAccessException e) {
			logger.error("findSrcTotal -> " + sql + ";args=" +  Arrays.toString(args), e);
			return 0L;
		}
	}
	
	/**
	 * 查找元数据表条数
	 * @param srcTableName
	 * @param ccxeid
	 * @return
	 */
	public long findSrcTotal(String srcTableName){
		String sql = "select count(1) from bond_ccxe." + srcTableName.toLowerCase();
		logger.debug("findSrcTotal -> " + sql);
		try {
			return dmdbJdbcTemplate.queryForObject(sql , Long.class);
		} catch (DataAccessException e) {
			logger.error("findSrcTotal -> " + sql , e);
			return 0L;
		}
	}
	
	/**
	 * 查找目标数据表条数
	 * @param destTableName
	 * @return
	 */
	public long findDestTotal(String destTableName){
		String sql = "select count(1) from dm_data." + destTableName.toLowerCase() ;
		logger.debug("findDestTotal -> " + sql);
		try {
			return dataCenterJdbcTemplate.queryForObject(sql , Long.class);
		} catch (DataAccessException e) {
			logger.error("findDestTotal -> " + sql , e);
			return 0L;
		}
	}
	
	/**
	 * 数据在目标是否存在
	 * @param destTableName
	 * @param unique
	 * @return 存在 true，不存在false
	 */
	public boolean isExistInDestTable(String destTableName,LinkedHashMap<String, Object> unique){
		StringBuffer whereSql =  new StringBuffer("");
		String sql = "select count(1) from dm_data." + destTableName ;
		int total = 0;
		if(unique != null && unique.size() > 0){
			whereSql.append(" where  ");
			Object[] args = new Object[unique.size()];
			int index = 0;
			for (Entry<String,Object> enrty : unique.entrySet()) {
				whereSql.append(enrty.getKey()).append(" = ? and ");
				args[index ++] = enrty.getValue();
			}
			total = dataCenterJdbcTemplate.queryForObject(sql + whereSql.substring(0, whereSql.length()-4), args, Integer.class);
		}else{
			total = dataCenterJdbcTemplate.queryForObject(sql, Integer.class);
		}
		return total > 0;
	}
	
	/**
	 * 查找满足条件的src信息
	 * @param srcTableName
	 * @param ccxeid
	 * @param offset
	 * @param size
	 * @return
	 */
	public List<Map<String,Object>> findSrcData(String srcTableName, Date ccxeid, int offset, int size){
		String sql = "select * from bond_ccxe." + srcTableName + " where ccxeid > ? order by ccxeid asc limit ?, ?";
		
		Object[] args = {ccxeid, offset, size};
		try {
			logger.debug("findSrcData -> " + sql + ";args=" +  Arrays.toString(args));
			return dmdbJdbcTemplate.queryForList(sql, args);
		} catch (DataAccessException e) {
			logger.error("findSrcData -> " + sql + ";args=" +  Arrays.toString(args), e);
			return new ArrayList<>();
		}
		
	}
	
	/**
	 * 查找满足条件的src信息
	 * @param srcTableName
	 * @param offset
	 * @param size
	 * @return
	 */
	public List<Map<String,Object>> findSrcData(String srcTableName, String columns, int offset, int size){
		String sql = "select " + columns + " from bond_ccxe." + srcTableName + " order by createtime asc limit ?, ?";
		
		Object[] args = {offset, size};
		try {
			logger.debug("findSrcData -> " + sql + ";args=" +  Arrays.toString(args));
			return dmdbJdbcTemplate.queryForList(sql, args);
		} catch (DataAccessException e) {
			logger.error("findSrcData -> " + sql + ";args=" +  Arrays.toString(args), e);
			return new ArrayList<>();
		}
		
	}
	
	
	public static void main(String[] args) {
		//findFinTotalInfoForSrc("pub_par", "par_sys_code", null, "par_sys_code");
		//findFinTotalInfoForDest("t_pub_par", "par_sys_code");
		System.out.println(DateUtil.getFormatDate(new Date(1514388036000L), DateUtil.YYYY_MM_DD_HH_MM_SS));
	}
		
}
