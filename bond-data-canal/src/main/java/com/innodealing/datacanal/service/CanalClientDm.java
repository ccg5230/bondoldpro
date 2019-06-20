package com.innodealing.datacanal.service;

import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.protocol.CanalEntry.Column;
import com.alibaba.otter.canal.protocol.CanalEntry.Entry;
import com.alibaba.otter.canal.protocol.CanalEntry.EntryType;
import com.alibaba.otter.canal.protocol.CanalEntry.EventType;
import com.alibaba.otter.canal.protocol.CanalEntry.RowChange;
import com.alibaba.otter.canal.protocol.CanalEntry.RowData;
import com.alibaba.otter.canal.protocol.CanalEntry.TransactionBegin;
import com.alibaba.otter.canal.protocol.CanalEntry.TransactionEnd;
import com.alibaba.otter.canal.protocol.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import com.innodealing.datacanal.config.Config;
import com.innodealing.datacanal.constant.ConstantCalnal;
import com.innodealing.datacanal.dao.BondComUniCodeMapDao;
import com.innodealing.datacanal.dao.BondDataCanalLogDao;
import com.innodealing.datacanal.dao.BondIssuerDao;
import com.innodealing.datacanal.dao.BondUniCodeMapDao;
import com.innodealing.datacanal.model.ColumnItem;
import com.innodealing.datacanal.model.TargertData;

/**
 * dm数据同步canal
 * 
 * @author 赵正来
 *
 */
public class CanalClientDm {


	public static BondComUniCodeMapDao bondComUniCodeMapDao;

	public static BondUniCodeMapDao bondUniCodeMapDao;

	public static DataCanalService dataCanalService;

	//public static JdbcTemplate dmdbJdbcTemplate;

	public static BondIssuerDao bondIssuerDao;
	
	public static BondDataCanalLogDao bondDataCanalLogDao ;

	

	protected final static Logger logger = LoggerFactory.getLogger(CanalClientDm.class);
	protected static final String SEP = SystemUtils.LINE_SEPARATOR;
	protected static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	protected volatile boolean running = false;
	protected Thread.UncaughtExceptionHandler handler = new Thread.UncaughtExceptionHandler() {

		public void uncaughtException(Thread t, Throwable e) {
			String msg = "parse events has an error,destination=" + destination;
			bondDataCanalLogDao.insert(msg);
			logger.error(msg, e);
		}
	};
	protected Thread thread = null;
	protected CanalConnector connector;
	protected static String context_format = null;
	protected static String row_format = null;
	protected static String transaction_format = null;
	protected String destination;

	static {
		context_format = SEP + "****************************************************" + SEP;
		context_format += "* Batch Id: [{}] ,count : [{}] , memsize : [{}] , Time : {}" + SEP;
		context_format += "* Start : [{}] " + SEP;
		context_format += "* End : [{}] " + SEP;
		context_format += "****************************************************" + SEP;

		row_format = SEP
				+ "----------------> binlog[{}:{}] , name[{},{}] , eventType : {} , executeTime : {} , delay : {}ms"
				+ SEP;

		transaction_format = SEP + "================> binlog[{}:{}] , executeTime : {} , delay : {}ms" + SEP;

	}

	public CanalClientDm(String destination) {
		this(destination, null);
	}

	public CanalClientDm(String destination, CanalConnector connector) {
		this.destination = destination;
		this.connector = connector;
	}

	protected void start() {
		Assert.notNull(connector, "connector is null");
		thread = new Thread(new Runnable() {

			public void run() {
				process();
			}
		});

		thread.setUncaughtExceptionHandler(handler);
		thread.start();
		running = true;
	}

	protected void stop() {
		if (!running) {
			return;
		}
		running = false;
		if (thread != null) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				// ignore
			}
		}

		MDC.remove(this.destination);
	}

	protected void process() {
		
		int batchSize = 10;
		while (running) {
			try {
				MDC.put("destination", destination);
				connector.connect();
				connector.subscribe();
				while (running) {
					Message message = connector.getWithoutAck(batchSize); // 获取指定数量的数据
					long batchId  = message.getId();
					int size = message.getEntries().size();
					if (batchId == -1 || size == 0) {
						 try {
						 Thread.sleep(1000);
						 } catch (InterruptedException e) {
						 }
					} else {
						try {
							printSummary(message, batchId, size);
							printEntry(message.getEntries());
							connector.ack(batchId); // 提交确认
						} catch (Exception e) {
							logger.error(e.getMessage(), e);
							connector.rollback(batchId); // 处理失败, 回滚数据
						}
					}
				}
			} catch (Exception e) {
				String msg = "process error!destination=" + destination;
				logger.error(msg, e);
				bondDataCanalLogDao.insert(msg);
			}finally {
				connector.disconnect();
				//MDC.
				MDC.remove(destination);
			}
		}
	}

	private void printSummary(Message message, long batchId, int size) {
		long memsize = 0;
		for (Entry entry : message.getEntries()) {
			memsize += entry.getHeader().getEventLength();
		}

		String startPosition = null;
		String endPosition = null;
		if (!CollectionUtils.isEmpty(message.getEntries())) {
			startPosition = buildPositionForDump(message.getEntries().get(0));
			endPosition = buildPositionForDump(message.getEntries().get(message.getEntries().size() - 1));
		}

		SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
		logger.info(context_format,
				new Object[] { batchId, size, memsize, format.format(new Date()), startPosition, endPosition });
	}

	protected String buildPositionForDump(Entry entry) {
		long time = entry.getHeader().getExecuteTime();
		Date date = new Date(time);
		SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
		return entry.getHeader().getLogfileName() + ":" + entry.getHeader().getLogfileOffset() + ":"
				+ entry.getHeader().getExecuteTime() + "(" + format.format(date) + ")";
	}

	protected void printEntry(List<Entry> entrys) {
		for (Entry entry : entrys) {
			long executeTime = entry.getHeader().getExecuteTime();
			long delayTime = new Date().getTime() - executeTime;

			if (entry.getEntryType() == EntryType.TRANSACTIONBEGIN
					|| entry.getEntryType() == EntryType.TRANSACTIONEND) {
				if (entry.getEntryType() == EntryType.TRANSACTIONBEGIN) {
					TransactionBegin begin = null;
					try {
						begin = TransactionBegin.parseFrom(entry.getStoreValue());
					} catch (InvalidProtocolBufferException e) {
						throw new RuntimeException("parse event has an error , data:" + entry.toString(), e);
					}
					// 打印事务头信息，执行的线程id，事务耗时
					logger.info(transaction_format,
							new Object[] { entry.getHeader().getLogfileName(),
									String.valueOf(entry.getHeader().getLogfileOffset()),
									String.valueOf(entry.getHeader().getExecuteTime()), String.valueOf(delayTime) });
					logger.info(" BEGIN ----> Thread id: {}", begin.getThreadId());
				} else if (entry.getEntryType() == EntryType.TRANSACTIONEND) {
					TransactionEnd end = null;
					try {
						end = TransactionEnd.parseFrom(entry.getStoreValue());
					} catch (InvalidProtocolBufferException e) {
						throw new RuntimeException("parse event has an error , data:" + entry.toString(), e);
					}
					// 打印事务提交信息，事务id
					logger.info("----------------\n");
					logger.info(" END ----> transaction id: {}", end.getTransactionId());
					logger.info(transaction_format,
							new Object[] { entry.getHeader().getLogfileName(),
									String.valueOf(entry.getHeader().getLogfileOffset()),
									String.valueOf(entry.getHeader().getExecuteTime()), String.valueOf(delayTime) });
				}

				continue;
			}

			if (entry.getEntryType() == EntryType.ROWDATA) {
				RowChange rowChage = null;
				try {
					rowChage = RowChange.parseFrom(entry.getStoreValue());
				} catch (Exception e) {
					throw new RuntimeException("parse event has an error , data:" + entry.toString(), e);
				}

				EventType eventType = rowChage.getEventType();

				logger.info(row_format,
						new Object[] { entry.getHeader().getLogfileName(),
								String.valueOf(entry.getHeader().getLogfileOffset()), entry.getHeader().getSchemaName(),
								entry.getHeader().getTableName(), eventType,
								String.valueOf(entry.getHeader().getExecuteTime()), String.valueOf(delayTime) });

				if (eventType == EventType.QUERY || rowChage.getIsDdl()) {
					logger.info(" sql ----> " + rowChage.getSql() + SEP);
					continue;
				}
				// 表名
				String tableName = entry.getHeader().getTableName().toUpperCase();
				for (RowData rowData : rowChage.getRowDatasList()) {
					if (eventType == EventType.DELETE) {
						//printColumn(rowData.getBeforeColumnsList());
						Map<String, Object> data = getData(rowData.getBeforeColumnsList());
						TargertData targertData = getExecuteData(data, tableName.toUpperCase());
						if(targertData != null){
							targertData = buildComBondUniCodeInduSw(targertData);
							dataCanalService.deleteCanal(targertData);
							//logger.info("TargertData->" + targertData);
						}
					} else if (eventType == EventType.INSERT) {
						List<Column> columns = rowData.getAfterColumnsList();
						Map<String, Object> data = getData(columns);
						data.put("status", ConstantCalnal.NO_DELETE);
						//主体只执行dm(dmdb.t_bond_com_ext)包含的
						if("D_PUB_COM_INFO_2".equalsIgnoreCase(tableName)){
							Object c = data.get("COM_UNI_CODE");
							if(c != null && !dataCanalService.isDmIssuer(c.toString())){
								continue;
							}
						}
						TargertData targertData = getExecuteData(data, tableName.toUpperCase());
						if(targertData != null){
							targertData = buildComBondUniCodeInduSw(targertData);
							dataCanalService.insertCanal(targertData);
						}
					} else {
						//printColumn(rowData.getAfterColumnsList());
						List<Column> columns = rowData.getAfterColumnsList();
						Map<String, Object> data = getData(columns);
						TargertData targertData = getExecuteData(data, tableName.toUpperCase());
						if(targertData != null){
							targertData = buildComBondUniCodeInduSw(targertData);
							dataCanalService.updateCanal(targertData);
						}
					}
				}
			}
		}
	}

	/**
	 * 格式主体id 和 bondUniId
	 * 
	 * @param executeData
	 * @param targertData
	 * @return
	 */
	public static TargertData buildComBondUniCodeInduSw(TargertData targertData) {
		LinkedHashMap<String, ColumnItem> columnItemMap = targertData.getColums();
		ColumnItem column = columnItemMap.get("COM_UNI_CODE");
		if (column != null && !column.isNull()) {
			// 主体唯一编码
			Long comUni = bondComUniCodeMapDao.findComUniCodeDataCenterFromCache(Long.valueOf(column.getValue().toString()));
			// 申万行业
			if ("T_BOND_ISSUER".equalsIgnoreCase(targertData.getTableName())) {
				Long induUniCodeSw = bondIssuerDao.findInduUniCodeSwFromCache(Long.valueOf(column.getValue().toString()));
				ColumnItem item = columnItemMap.get("INDU_UNI_CODE_S");
				item.setValue(induUniCodeSw);
				columnItemMap.put("INDU_UNI_CODE_S", item);
			}
			column.setValue(comUni);
			columnItemMap.put("COM_UNI_CODE", column);
		}
		// 债券唯一编码
		ColumnItem bondUniCode = columnItemMap.get("BOND_UNI_CODE");
		if (bondUniCode != null) {
			Long bondUni = bondUniCodeMapDao.findBondUniCodeDataCenterFromCache(Long.valueOf(bondUniCode.getValue().toString()));
			ColumnItem item = columnItemMap.get("BOND_UNI_CODE");
			item.setValue(bondUni);
			columnItemMap.put("BOND_UNI_CODE", item);
		}
		targertData.setColums(columnItemMap);
		return targertData;
	}

	/**
	 * 获取在目标数据库执行的数据，你面有定位行的search_key,和目标数据表名称，其他都是Column。
	 * 
	 * @param src
	 * @return
	 */
	public static TargertData getExecuteData(Map<String, Object> src, String tableName) {
		TargertData trgertData = new TargertData();
		if (src == null || tableName == null) {
			return null;
		}
		if (Config.TABLE_MAP.get(tableName) == null) {
			return null;
		}
		String[] destInfo = Config.TABLE_MAP.get(tableName).split("=");
		// 获取目标tableName
		String destTable = destInfo[0];
		src.put("STATUS", ConstantCalnal.NO_DELETE);

		String destColums[] = destInfo[1].split(",");
		LinkedHashMap<String, ColumnItem> executeData = new LinkedHashMap<>();
		for (String destColum : destColums) {
			ColumnItem item = new ColumnItem();
			String[] c = destColum.split("-");
			// 设置目标列
			destColum = c[c.length - 1];
			// 查找出搜索关键字
			String[] sk = c[c.length - 1].split(":");
			Object columnValue = null;
			if (c.length == 2) {
				columnValue = src.get(c[0]);
				destColum = sk[sk.length -1];
			} else {
				destColum = sk[sk.length - 1];
				columnValue = src.get(destColum);
			}
			item.setName(destColum);
			item.setValue(columnValue);
			//item.setSqlType(column.getSqlType());
			item.setKey(sk.length == 2);
			item.setNull(columnValue == null);
			executeData.put(item.getName(), item);
		}
		// 目标表
		trgertData.setTableName(destTable);
		trgertData.setColums(executeData);
		return trgertData;
	}



	

	/*
	 * 获取数据
	 */
	private Map<String, Object> getData(List<Column> columns) {
		Map<String, Object> data = new LinkedHashMap<>();
		for (Column column : columns) {
			if(column.getMysqlType().equalsIgnoreCase("datetime")){
				data.put(column.getName(), column.getIsNull() ? null : (column.getValue() + ".0"));
			}else{
				data.put(column.getName(), column.getIsNull() ? null : column.getValue());
			}
		}
		return data;
	}

	protected void printColumn(List<Column> columns) {
		for (Column column : columns) {
			StringBuilder builder = new StringBuilder();
			builder.append(column.getName() + " : " + column.getValue());
			builder.append("    type=" + column.getMysqlType());
			if (column.getUpdated()) {
				builder.append("    update=" + column.getUpdated());
			}
			builder.append(SEP);
			logger.info(builder.toString());
		}
	}

	public void setConnector(CanalConnector connector) {
		this.connector = connector;
	}
	
	
	public  boolean isRunning() {
		return running;
	}

	public static void main(String[] args) {
		System.out.println(System.currentTimeMillis());
	}

}
