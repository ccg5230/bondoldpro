package com.innodealing.datacanal.util;

import java.util.ArrayList;
import java.util.List;

import com.innodealing.datacanal.config.Config;
import com.innodealing.datacanal.constant.ConstantCalnal;
import com.innodealing.datacanal.model.BondDataTableMap;
import com.innodealing.datacanal.vo.SrcToDestTableVo;

/**
 * 
 * @author 同步表工具类
 *
 */
public class BondDataTableMapUtil {

	//private static Logger logger logger  = Logger
	
	/**
	 * 获取所有映射关系,包括列信息
	 * @return
	 */
	public static List<BondDataTableMap> getBondDataTableMap(){
		List<BondDataTableMap> list = new ArrayList<>();
		Config.TABLE_MAP.forEach((k, v) -> {
			String  srcTableName = k;
			String[] columns = v.split("=")[1].split(",");
			String  destTableName= v.split("=")[0];
			for (int i = 0; i < columns.length; i++) {
				String[] cs = columns[i].split("-");
				String[] sks = cs[cs.length - 1].split(":");
				String destColumnName = sks[sks.length-1];
				String srcColumnName = sks.length == 2 ? cs[0].split(":")[1] : destColumnName;
				BondDataTableMap dataTableMap = new  BondDataTableMap();
				dataTableMap.setDestColumnName(destColumnName);
				dataTableMap.setDestTableName(destTableName);
				dataTableMap.setSrcColumnName(srcColumnName);
				dataTableMap.setSrcTableName(srcTableName);
				if(sks.length == 2){
					dataTableMap.setIsUnique(ConstantCalnal.TRUE);
					
				}else{
					dataTableMap.setIsUnique(ConstantCalnal.FALSE);
				}
				list.add(dataTableMap);
			}
			
		});
		return list;
	}
	
	/**
	 * 获取所有源表和目标表
	 * @return
	 */
	public static List<SrcToDestTableVo> getSrcToDestTableVo(){
		 List<SrcToDestTableVo> list = new ArrayList<>();
		Config.TABLE_MAP.forEach((k, v) -> {
			String  srcTableName = k;
			String  destTableName= v.split("=")[0];
			SrcToDestTableVo srcToDestTableVo = new SrcToDestTableVo();
			srcToDestTableVo.setSrcTableName(srcTableName);
			srcToDestTableVo.setDestTableName(destTableName);
			list.add(srcToDestTableVo);
		});
		return list;
	}
	
	/**
	 * 获取制定源表和目标表
	 * @return
	 */
	public static List<SrcToDestTableVo> getSrcToDestTableVo(String[] srcTables){
		 List<SrcToDestTableVo> list = new ArrayList<>();
		 for (int i = 0; i < srcTables.length; i++) {
			 String  srcTableName = srcTables[i].toUpperCase();
			 String dest = Config.TABLE_MAP.get(srcTables[i].toUpperCase());
			 String  destTableName= dest.split("=")[0];
			 SrcToDestTableVo srcToDestTableVo = new SrcToDestTableVo();
			 srcToDestTableVo.setSrcTableName(srcTableName);
			 srcToDestTableVo.setDestTableName(destTableName);
			 list.add(srcToDestTableVo);
		}
		return list;
	}
	
	/**
	 * 查找唯一索引
	 * @return
	 */
	public static List<String> getSks(String tableName){
		String columns = Config.TABLE_MAP.get(tableName.toUpperCase());
		 List<String> sksList = new ArrayList<>();
		if(columns != null){
			String[] cls = columns.split(",");
			for(int i =0 ; i < cls.length ; i++){
				String[] cl = cls[i].split("-");
				String[] sks =  cl[cl.length -1].split(":");
				if(sks.length == 2){
					sksList.add(sks[sks.length -1 ]);
				}
			}
		}
		return sksList;
	}
	
	
	/**
	 * 查找唯一索引字段
	 * @return column1,column1
	 */
	public static String getSksStr(String tableName){
		List<String> columnsSk = getSks(tableName);
		return columnsSk == null ||  columnsSk.size() == 0 
				? null : columnsSk.toString().replaceAll("\\[", "").replaceAll("\\]", "");	
		
	}
	
	public static void main(String[] args) {
		System.out.println(getSksStr("D_BOND_FIN_GEN_BALA_TACBB"));
	}
}
