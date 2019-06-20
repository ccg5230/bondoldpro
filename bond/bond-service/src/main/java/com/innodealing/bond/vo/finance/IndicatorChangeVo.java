package com.innodealing.bond.vo.finance;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 用于指标计算指标变动提醒计算
 * @author 赵正来
 *
 */
public class IndicatorChangeVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 最新的保存指标数据
	 */
	Map<String,Object> newestDataInserDb = new HashMap<>();
	
	/**
	 * 保存前的最新指标数据
	 */
	Map<String,Object> newestDataInDB = new HashMap<>();
	
	/**
	 * 同比的数据
	 */
	Map<String,Object> yoyData = new HashMap<>();
	
	/**
	 * 最新的保存指标行业排名数据
	 */
	Map<String,Object> newestDataRankInsert = new HashMap<>();
	
	/**
	 * 最新的保存指标行业排名数据
	 */
	Map<String,Object> newestDataRankInDb = new HashMap<>();

	public Map<String, Object> getNewestDataInserDb() {
		return newestDataInserDb;
	}

	public void setNewestDataInserDb(Map<String, Object> newestDataInserDb) {
		this.newestDataInserDb = newestDataInserDb;
	}

	public Map<String, Object> getNewestDataInDB() {
		return newestDataInDB;
	}

	public void setNewestDataInDB(Map<String, Object> newestDataInDB) {
		this.newestDataInDB = newestDataInDB;
	}

	public Map<String, Object> getYoyData() {
		return yoyData;
	}

	public void setYoyData(Map<String, Object> yoyData) {
		this.yoyData = yoyData;
	}

	public Map<String, Object> getNewestDataRankInsert() {
		return newestDataRankInsert;
	}

	public void setNewestDataRankInsert(Map<String, Object> newestDataRankInsert) {
		this.newestDataRankInsert = newestDataRankInsert;
	}

	public Map<String, Object> getNewestDataRankInDb() {
		return newestDataRankInDb;
	}

	public void setNewestDataRankInDb(Map<String, Object> newestDataRankInDb) {
		this.newestDataRankInDb = newestDataRankInDb;
	}

	@Override
	public String toString() {
		return "IndicatorChangeVo [newestDataInserDb=" + newestDataInserDb + ", newestDataInDB=" + newestDataInDB
				+ ", yoyData=" + yoyData + ", newestDataRankInsert=" + newestDataRankInsert + ", newestDataRankInDb="
				+ newestDataRankInDb + "]";
	}
	
	
	
	
}
