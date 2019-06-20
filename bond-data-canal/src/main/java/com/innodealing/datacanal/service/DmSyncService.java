package com.innodealing.datacanal.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.google.common.base.Objects;
import com.innodealing.datacanal.BondDataCanalApplication;
import com.innodealing.datacanal.constant.ConstantCalnal;
import com.innodealing.datacanal.dao.DmSyncDao;
import com.innodealing.datacanal.dao.IdGenerateDao;
import com.innodealing.datacanal.model.TargertData;
import com.innodealing.datacanal.util.BondDataTableMapUtil;
import com.innodealing.datacanal.util.MD5Util;
import com.innodealing.datacanal.vo.DataCompareVo;
import com.innodealing.datacanal.vo.SrcToDestTableVo;
import com.innodealing.datacanal.vo.SyncInfoVo;
import com.innodealing.datacanal.vo.SyncTableTotalVo;

/**
 * DM数据同步，主要弥补canal 出现异常|数据初始化时候有遗漏，导致数据不一致。
 * @author 赵正来  
 *
 */
@Service
public class DmSyncService {

	private @Autowired DmSyncDao dmSyncDao;
	
	private @Autowired DataCanalService canalService;
	
	private @Autowired IdGenerateDao idGenerateDao;
	
	private  CanalStarterClient canalStarterClientBondCCxe = new CanalStarterClient("bond_ccxe");
	
	private  CanalStarterClient canalStarterClientBondCCxeDev = new CanalStarterClient("bond_ccxe_dev");
	
	private  CanalStarterClient canalStarterClientDmdb = new CanalStarterClient("dmdb");

	private @Autowired ConfigurableApplicationContext context;
	
	
	/**
	 * 每次获取的条目数
	 */
	public static int SIZE = 500;
	
	private static Logger log = LoggerFactory.getLogger(DmSyncService.class);
	
	public void chekErrorData(){
	
		
		log.info("同步开始错误数据");
		getSyncMap().forEach(item -> {
			//原数据
			String srcTableName = item.getSrcTableName(),  srcKeyColumn = item.getSrcKeyColumn(),  relationTable =item.getRelationTable(),
					relationDataCenterColum =item.getRelationDataCenterColum(), destKeyColumn = item.getDestKeyColumn(), destTableName = item.getDestTableName();
			//缺少的数据
			List<SyncTableTotalVo> missingData =  findMissData(srcTableName, srcKeyColumn, relationTable, destTableName, destKeyColumn, relationDataCenterColum);
			long total = missingData.size();
			log.info("有问题的数据的数量-》" + total);
			//插入到目标数据库
			for (SyncTableTotalVo data : missingData) {
				//如果源表有数据删除原来的数据
				if(data.getKeyColumn() != null){
					dmSyncDao.deleteErrorDestData(destTableName, destKeyColumn, data.getKeyColumn());
				}
				List<Map<String, Object>> missList = dmSyncDao.findPrepareSyncData(srcTableName, srcKeyColumn, data.getSrcKeyColumn());
				insertDestDbBatch(missList, srcTableName);
				log.info("insert " + missList.size()+  " rows ");
				total--;
				log.info( item.getSrcTableName() + "->" + item.getDestTableName() +  "已处理" + (missingData.size() - total) + "还有" + total + "个未处理！");
				
			}
		});
		
	}
	
	/**
	 * 按照时间戳节点进行同步数据
	 */
	public void syncByCcxeid(){
		getSyncMap().forEach(item ->{
			String destTableName = item.getDestTableName(),srcTableName = item.getSrcTableName();
			//目标表的最后时间戳
			Date ccxeid = dmSyncDao.findDestLastUpdateTime(destTableName);
			//原表中大于目标表时间戳的总行数
			long total = dmSyncDao.findSrcTotal(srcTableName, ccxeid);
			//起始查询偏移量，默认为0
			int page = 1, offset =0;
			while( offset  < total){
				offset = (page -1) * SIZE ;
				List<Map<String, Object>> batchDatas =  dmSyncDao.findSrcData(srcTableName, ccxeid, offset, SIZE);
				for (Map<String, Object> map : batchDatas) {
					insertDestDb(map, srcTableName);
				}
				//insertDestDbBatch(batchDatas, srcTableName);
				log.info(srcTableName + "总数量total=" + total + ";已处理:" + page *SIZE + ";还有" + (total-page *SIZE) + "未处理。");
				page++;
			}
		});
		
	}
	
	/**
	 * 同步所有
	 */
	public void syncAllData(){
		BondDataTableMapUtil.getSrcToDestTableVo().forEach(item ->{
			new Thread(new Runnable() {
				@Override
				public void run() {
					String destTableName = item.getDestTableName(),srcTableName = item.getSrcTableName();
					//原表中大于目标表时间戳的总行数
					long total = dmSyncDao.findSrcTotal(srcTableName);
					//起始查询偏移量，默认为0
					int page = 1, offset = 0;
					while( offset < total){
						offset = (page -1) * SIZE;
						long s = System.currentTimeMillis();
						List<Map<String, Object>> batchDatasSrc = dmSyncDao.findSrcData(srcTableName, "*", offset , SIZE);
						long e = System.currentTimeMillis();
						log.info("拉取数据时间为：" + (e-s) + "ms");
						List<Map<String, Object>> batchDatas = new ArrayList<>();
						batchDatasSrc.forEach(map -> {
							insertDestDb(map, srcTableName);
							//batchDatas.add(e);
						});
						//insertDestDbBatch(batchDatasSrc, srcTableName);
						long e1 = System.currentTimeMillis();
						log.info("插入数据时间为：" + (e1-e) + "ms");
						log.info(srcTableName + "总数量total=" + total + ";已处理:" + (page*SIZE) + ";还有" + (total - (offset + SIZE)) + "未处理。");
						page++;
					}
				}
			}).start();
			
		});
		
	}
	
	
	/**
	 * 同步指定数据
	 * @param srcTables 表
	 * @param offsets srcTables对应的起始起始偏移量，默认为0
	 */
	public void syncData(String[] srcTables,Map<String,Integer> offsets){
		BondDataTableMapUtil.getSrcToDestTableVo(srcTables).forEach(item ->{
			new Thread(new Runnable() {
				@Override
				public void run() {
					String srcTableName = item.getSrcTableName();
					//原表中大于目标表时间戳的总行数
					long total = dmSyncDao.findSrcTotal(srcTableName);
					//起始查询偏移量，默认为0
					int page = 1,offset = 0;
					Integer start = offsets.get(srcTableName) == null ? 0 : offsets.get(srcTableName);
					while( offset < total){
						offset = (page -1) * SIZE + start;
						long s = System.currentTimeMillis();
						List<Map<String, Object>> batchDatasSrc = dmSyncDao.findSrcData(srcTableName, "*", offset , SIZE);
						long e = System.currentTimeMillis();
						log.info("拉取数据时间为：" + (e-s) + "ms");
						batchDatasSrc.forEach(map -> {
							insertDestDb(map, srcTableName);
							//batchDatas.add(e);
						});
						//insertDestDbBatch(batchDatasSrc, srcTableName);
						long e1 = System.currentTimeMillis();
						log.info("插入数据时间为：" + (e1-e) + "ms");
						log.info(srcTableName + "总数量total=" + total + ";已处理:" + (start + page*SIZE) + ";还有" + (total - (offset + SIZE)) + "未处理。");
						page++;
					}
				}
			}).start();
			
		});
		
	}
	
	
	public void chechMissAndMakeUp(String[] srcTables){
		BondDataTableMapUtil.getSrcToDestTableVo(srcTables).forEach(item ->{
			new Thread(new Runnable() {
				@Override
				public void run() {
					List<TargertData> missDatas = new ArrayList<>();
					String srcTableName = item.getSrcTableName();
					//原表中大于目标表时间戳的总行数
					long total = dmSyncDao.findSrcTotal(srcTableName);
					//起始查询偏移量，默认为0
					int page = 1,offset = 0;
					//获取唯一索引的字段
					String columns = BondDataTableMapUtil.getSksStr(srcTableName);
					while( offset < total){
						offset = (page -1) * SIZE + 90000;
						long s = System.currentTimeMillis();
						List<Map<String, Object>> batchDatasSrc = dmSyncDao.findSrcData(srcTableName,columns.toLowerCase(), offset , SIZE);
						long e = System.currentTimeMillis();
						log.info("拉取数据时间为：" + (e-s) + "ms");
						batchDatasSrc.forEach(map -> {
							//封装targert Data
							TargertData  targertData  = CanalClientDm.getExecuteData(map, srcTableName.toUpperCase());
							targertData = CanalClientDm.buildComBondUniCodeInduSw(targertData);
							String unique = idGenerateDao.getUnique(targertData);
							boolean isExist = idGenerateDao.isExist(targertData.getTableName(), MD5Util.getMD5(unique));
							if(!isExist){
								missDatas.add(targertData);
								log.info("数据丢失：" + targertData);
							}
						});
						//insertDestDbBatch(batchDatasSrc, srcTableName);
						//log.info("插入数据时间为：" + (e1-e) + "ms");
						log.info(srcTableName + "总数量total=" + total + ";已处理:" + (page*SIZE) + ";还有" + (total - (offset + SIZE)) + "未处理。");
						page++;
					}
					log.info("数据丢失：" + missDatas);
				}
			}).start();
			
		});
	}
	
	/**
	 * 更新所有status状态为未删除
	 * @return
	 */
	public boolean updateStatusNoDelete(){
		BondDataTableMapUtil.getSrcToDestTableVo().forEach(item -> {
			//new Thread(new Runnable() {
				//@Override
				//public void run() {
					String destTableName = item.getDestTableName();
					Long maxId = dmSyncDao.findMaxIdDest(destTableName) + 1L;
					Long size = 10000L;
					while(size <( maxId + 10000L)){
						dmSyncDao.updataStatusToNoDeleteDestData(destTableName, size, ConstantCalnal.NO_DELETE);
						size += 10000L;
					}
				//}
			//}).start();
		});
		return true;
	}
	
	/**
	 * 开启canal实时同步
	 */
	public void syncCanal(){
		BondDataCanalApplication.initCanalClientDm(context);
		//设置canal 服务端ip
		String hostIp = context.getBean(Environment.class).getProperty("canal.server.host");
		canalStarterClientBondCCxe.setHostIp(hostIp);
		canalStarterClientBondCCxe.run();
//		canalStarterClientBondCCxeDev.setHostIp(hostIp);
//		canalStarterClientBondCCxeDev.run();
		canalStarterClientDmdb.setHostIp(hostIp);
		canalStarterClientDmdb.run();
		//DmSyncService dmSyncService = context.getBean(DmSyncService.class);
		//dmSyncService.viewSyncResult();
		//dmSyncService.checkMissData();
	}
	
	/**
	 * 关闭canal实时同步
	 */
	public void stopCanal(){
		BondDataCanalApplication.initCanalClientDm(context);
		//设置canal 服务端ip
		//String hostIp = context.getBean(Environment.class).getProperty("canal.server.host");
		canalStarterClientBondCCxe.stop();
//		canalStarterClientBondCCxeDev.setHostIp(hostIp);
//		canalStarterClientBondCCxeDev.run();
		//canalStarterClientDmdb.setHostIp(hostIp);
		canalStarterClientDmdb.stop();
		//DmSyncService dmSyncService = context.getBean(DmSyncService.class);
		//dmSyncService.viewSyncResult();
		//dmSyncService.checkMissData();
	}
	
	/**
	 * 查看同步结果
	 */
	public List<DataCompareVo>  viewSyncResult(){
		List<DataCompareVo> syncResult  = new ArrayList<>();
		//获取同步的表
		List<SrcToDestTableVo> listTables =  BondDataTableMapUtil.getSrcToDestTableVo();
		listTables.forEach(table -> {
			long srcTotal = dmSyncDao.findSrcTotal(table.getSrcTableName());
			long destTotal = dmSyncDao.findDestTotal(table.getDestTableName());
			DataCompareVo compareVo = new DataCompareVo();
			compareVo.setDestDbName("dm_data");
			compareVo.setDestTableName(table.getDestTableName());
			compareVo.setDestTotal(destTotal);
			compareVo.setSrcDbName("bond_ccxe");
			compareVo.setSrcTableName(table.getSrcTableName());
			compareVo.setSrcTotal(srcTotal);
			compareVo.setMissing(srcTotal - destTotal);
			syncResult.add(compareVo);
		});
		log.info("同步结果：" + syncResult);
		return syncResult;
	}
	
	
	
	/**
	 * 将丢失的数据入库
	 * @param missingData
	 * @param srcTableName
	 * @return
	 */
	public boolean insertDestDb(Map<String,Object> missingData, String srcTableName){
		//封装targert Data
		TargertData  targertData  = CanalClientDm.getExecuteData(missingData, srcTableName.toUpperCase());
		CanalClientDm.buildComBondUniCodeInduSw(targertData);
		return canalService.insertCanal(targertData);
	}
	
	/**
	 * 将丢失的数据入库
	 * @param missingData
	 * @param srcTableName
	 * @return
	 */
	public boolean insertDestDbBatch(List<Map<String,Object>> missingDatas, String srcTableName){
		//封装targert Data
		List<TargertData> targertDatas = new ArrayList<>();
		missingDatas.forEach(data ->{
			TargertData  targertData  = CanalClientDm.getExecuteData(data, srcTableName.toUpperCase());
			CanalClientDm.buildComBondUniCodeInduSw(targertData);
			targertDatas.add(targertData);
		});
		return canalService.insertCanalBatch(targertDatas);
	}


	private List<SyncInfoVo> getSyncMap(){
		List<SyncInfoVo> syncMap = new ArrayList<>();
		
		SyncInfoVo fal_bala_tafbb = new SyncInfoVo("d_bond_fin_fal_bala_tafbb", "bond_uni_code", "t_bond_uni_code_map"
				, "bond_uni_code_data_center", "bond_uni_code", "t_bond_fin_fal_bala_tafbb");
		syncMap.add(fal_bala_tafbb);
		
		SyncInfoVo falCashTafcb = new SyncInfoVo("d_bond_fin_fal_cash_tafcb", "bond_uni_code", "t_bond_uni_code_map"
				, "bond_uni_code_data_center", "bond_uni_code", "t_bond_fin_fal_cash_tafcb");
		syncMap.add(falCashTafcb);
		
		SyncInfoVo fal_prof_tafpb = new SyncInfoVo("d_bond_fin_fal_prof_tafpb", "bond_uni_code", "t_bond_uni_code_map"
				, "bond_uni_code_data_center", "bond_uni_code", "t_bond_fin_fal_prof_tafpb");
		syncMap.add(fal_prof_tafpb);
		
		SyncInfoVo gen_bala_tacbb = new SyncInfoVo("d_bond_fin_gen_bala_tacbb", "bond_uni_code", "t_bond_uni_code_map"
				, "bond_uni_code_data_center", "bond_uni_code", "t_bond_fin_gen_bala_tacbb");
		syncMap.add(gen_bala_tacbb);
		
		SyncInfoVo gen_cash_taccb = new SyncInfoVo("d_bond_fin_gen_cash_taccb", "bond_uni_code", "t_bond_uni_code_map"
				, "bond_uni_code_data_center", "bond_uni_code", "t_bond_fin_gen_cash_taccb");
		syncMap.add(gen_cash_taccb);
		
		SyncInfoVo gen_prof_tacpb = new SyncInfoVo("d_bond_fin_gen_prof_tacpb", "bond_uni_code", "t_bond_uni_code_map"
				, "bond_uni_code_data_center", "bond_uni_code", "t_bond_fin_gen_prof_tacpb");
		syncMap.add(gen_prof_tacpb);
		
		SyncInfoVo d_bond_iss_cred_chan = new SyncInfoVo("d_bond_iss_cred_chan", "com_uni_code", "t_bond_com_uni_code_map"
				, "com_uni_code_data_center", "com_uni_code", "t_bond_iss_cred_chan");
		syncMap.add(d_bond_iss_cred_chan);
		
		SyncInfoVo d_bond_cred_chan = new SyncInfoVo("d_bond_cred_chan", "bond_uni_code", "t_bond_uni_code_map"
				, "bond_uni_code_data_center", "bond_uni_code", "t_bond_cred_chan");
		syncMap.add(d_bond_cred_chan);
		return syncMap;
	}
	
	
	/**
	 * 原数据
	 * @param srcTableName
	 * @param srcKeyColumn
	 * @param relationTable
	 * @param destKeyColumn
	 * @return
	 */
	private Map<Long,Long> findSrcDataTotal(String srcTableName, String srcKeyColumn, String relationTable, String destKeyColumn) {
		List<SyncTableTotalVo> srcData = dmSyncDao.findFinTotalInfoForSrc(srcTableName, srcKeyColumn, relationTable, destKeyColumn);
		Map<Long,Long> mapData = new HashMap<>();
		if(srcData != null){
			srcData.forEach(data->{
				mapData.put(data.getKeyColumn(), data.getTotal());
			});
		}
		return mapData;
	}

	/**
	 * 查找缺失数据的源表主id
	 * @param srcTableName
	 * @param srcKeyColumn
	 * @param relationTable
	 * @param destTableName
	 * @param destKeyColumn
	 * @return
	 */
	public List<SyncTableTotalVo> findMissData(String srcTableName, String srcKeyColumn, String relationTable, String destTableName, String destKeyColumn,String relationDataCenterColum){
		//缺少数据的原表的srcKeyColumn
		List<SyncTableTotalVo> missDataSrckeyColumn = new ArrayList<>();
		List<SyncTableTotalVo> srcData = dmSyncDao.findFinTotalInfoForSrc(srcTableName, srcKeyColumn, relationTable, relationDataCenterColum);
		//原表数据中和目标数据有交集的数据map集合
		Map<Long,SyncTableTotalVo> srcMapData = new HashMap<>();
		srcData.forEach(item -> {
			if(item.getKeyColumn() == null){
				//没有交集的数据单独存放起来
				missDataSrckeyColumn.add(item);
			}else{
				srcMapData.put(item.getKeyColumn(), item);
			}
		});
		//目标数据集合
		List<SyncTableTotalVo> destData = dmSyncDao.findFinTotalInfoForDest(destTableName, destKeyColumn);
		Map<Long,SyncTableTotalVo> destMapData = new HashMap<>();
		destData.forEach(item -> {
			destMapData.put(item.getKeyColumn(), item);
		});
		//找出srcMapData 和 destMapData中数据不同部分
		srcMapData.forEach((key,value) -> {
			SyncTableTotalVo dest = destMapData.get(key);
			if(dest == null || !Objects.equal(value.getTotal(), dest.getTotal())){
				missDataSrckeyColumn.add(value);
			}
		});
		return missDataSrckeyColumn;
	}
	
	
	
	/**
	 * 目标数据
	 * @param srcTableName
	 * @param srcKeyColumn
	 * @param relationTable
	 * @param destKeyColumn
	 * @return
	 */
	private Map<Long,Long> findDestDataTotal(String destTableName, String destKeyColumn) {
		List<SyncTableTotalVo> srcData = dmSyncDao.findFinTotalInfoForDest(destTableName, destKeyColumn);
		Map<Long,Long> mapData = new HashMap<>();
		if(srcData != null){
			srcData.forEach(data->{
				mapData.put(data.getKeyColumn(), data.getTotal());
			});
		}
		return mapData;
	}
	
	/**
	 * 缺少的数据
	 * @param srcData
	 * @param destData
	 * @return
	 */
	private Map<Long,Long>  findMissingDataKey(Map<Long,Long> srcData, Map<Long,Long> destData){
		Map<Long,Long>  missingKeys = new HashMap<>();
		if(srcData != null){
			srcData.forEach((key, total) -> {
				if(!Objects.equal(total, destData.get(key))){
					missingKeys.put(key, total);
				}
			});
		}
		return missingKeys;
	}

	
	
	/**
	 * 获取实例状态
	 * @return
	 */
	public Map<String,Boolean> syncCanalstatus(){
		//canalStarterClientBondCCxe.running = true;
		boolean bond_ccxe =canalStarterClientBondCCxe.running;
		boolean dmdb = canalStarterClientDmdb.getStatus();
		Map<String,Boolean> result = new HashMap<>();
		result.put("bond_ccxe", bond_ccxe);
		result.put("dmdb", dmdb);
		return result;
	}
	
	
	
	
	
}
