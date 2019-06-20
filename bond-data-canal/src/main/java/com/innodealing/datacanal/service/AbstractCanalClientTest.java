package com.innodealing.datacanal.service;



import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
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

/**
 * 测试基类
 * 
 * @author jianghang 2013-4-15 下午04:17:12
 * @version 1.0.4
 */
public class AbstractCanalClientTest {

	protected static  JdbcTemplate jdbcTemplate;


	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		AbstractCanalClientTest.jdbcTemplate = jdbcTemplate;
	}
    protected final static Logger             logger             = LoggerFactory.getLogger(AbstractCanalClientTest.class);
    protected static final String             SEP                = SystemUtils.LINE_SEPARATOR;
    protected static final String             DATE_FORMAT        = "yyyy-MM-dd HH:mm:ss";
    protected volatile boolean                running            = false;
    protected Thread.UncaughtExceptionHandler handler            = new Thread.UncaughtExceptionHandler() {

                                                                     public void uncaughtException(Thread t, Throwable e) {
                                                                         logger.error("parse events has an error", e);
                                                                     }
                                                                 };
    protected Thread                          thread             = null;
    protected CanalConnector                  connector;
    protected static String                   context_format     = null;
    protected static String                   row_format         = null;
    protected static String                   transaction_format = null;
    protected String                          destination;

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

    public AbstractCanalClientTest(String destination){
        this(destination, null);
    }

    public AbstractCanalClientTest(String destination, CanalConnector connector){
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

        MDC.remove("destination");
    }

    protected void process() {
        int batchSize = 5 * 1024;
        while (running) {
        	 long batchId  = 0L;
            try {
                MDC.put("destination", destination);
                connector.connect();
                connector.subscribe();
                while (running) {
                    Message message = connector.getWithoutAck(batchSize); // 获取指定数量的数据
                    batchId = message.getId();
                    int size = message.getEntries().size();
                    if (batchId == -1 || size == 0) {
                        // try {
                        // Thread.sleep(1000);
                        // } catch (InterruptedException e) {
                        // }
                    } else {
                        printSummary(message, batchId, size);
                        printEntry(message.getEntries());
                    }

                    connector.ack(batchId); // 提交确认
                    connector.rollback(batchId); // 处理失败, 回滚数据
                    
                }
            } catch (Exception e) {
                logger.error("process error!", e);
                connector.rollback(batchId);
            } finally {
                connector.disconnect();
                MDC.remove("destination");
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
        logger.info(context_format, new Object[] { batchId, size, memsize, format.format(new Date()), startPosition,
                endPosition });
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

            if (entry.getEntryType() == EntryType.TRANSACTIONBEGIN || entry.getEntryType() == EntryType.TRANSACTIONEND) {
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
				String tableName = entry.getHeader().getTableName();
				for (RowData rowData : rowChage.getRowDatasList()) {
					if (eventType == EventType.DELETE) {
						printColumn(rowData.getBeforeColumnsList());
						Map<String, Object> data = getData(rowData.getBeforeColumnsList());
						data.put(ConstantCalnal.TABLE_NAME, tableName.toUpperCase());
						//logger.info("data->" + data.toString());
						Map<String, Object> executeData = getExecuteData(data);
						deleteCanal(executeData);
					} else if (eventType == EventType.INSERT) {
						printColumn(rowData.getAfterColumnsList());
						Map<String, Object> data = getData(rowData.getAfterColumnsList());
						data.put(ConstantCalnal.TABLE_NAME, tableName.toUpperCase());
						//logger.info("data->" + data.toString());
						Map<String, Object> executeData = getExecuteData(data);
						insertCanal(executeData);
					} else {
						printColumn(rowData.getAfterColumnsList());
						List<Column> columns = rowData.getAfterColumnsList();
						Map<String, Object> data = getData(columns);
						data.put(ConstantCalnal.TABLE_NAME, tableName.toUpperCase());
						//logger.info("data->" + data.toString());
						Map<String, Object> executeData = getExecuteData(data);
						updateCanal(executeData);
						
					}
				}
            }
        }
    }

    /**
	 * 获取在目标数据库执行的数据
	 * 
	 * @param src
	 * @return
	 */
	public Map<String, Object> getExecuteData(Map<String, Object> src) {
		if (src == null) {
			return null;
		}
		// 获取源信息和目标配置信息
		String tableConstant = src.get(ConstantCalnal.TABLE_NAME).toString();
		if (tableConstant == null) {
			return null;
		}
		if(Config.TABLE_MAP.get(tableConstant) == null){
			return null;
		}
		String[] destInfo = Config.TABLE_MAP.get(tableConstant).split("=");
		// 获取目标tableName
		String destTable = destInfo[0];
		String destColums[] = destInfo[1].split(",");
		Map<String, Object> executeData = new HashMap<>();
		for (String destColum : destColums) {
			String[] c = destColum.split("-");
			// 查找出搜索关键字
			String[] sk = c[c.length - 1].split(":");
			if (sk.length == 2) {
				c[c.length - 1] = sk[1];
				destColum = sk[1];
				if (executeData.get(ConstantCalnal.SEARCH_KEY) != null) {
					String skNew = executeData.get(ConstantCalnal.SEARCH_KEY).toString() + "-" + sk[1];
					executeData.put(ConstantCalnal.SEARCH_KEY, skNew);
				} else {
					executeData.put(ConstantCalnal.SEARCH_KEY, sk[1]);
				}
			}
			if (c.length == 2) {
				executeData.put(c[1], src.get(destColum));
			} else {
				executeData.put(destColum, src.get(destColum));
			}

		}
		executeData.put(ConstantCalnal.TABLE_NAME, destTable);
		return executeData;
	}

	/**
	 * 获取条件语句
	 * 
	 * @param data
	 * @param whereKeys
	 * @return
	 */
	public String getWhereSql(Map<String, Object> data, Object whereKeys) {
		StringBuilder whereSql = new StringBuilder("");
		if (whereKeys == null) {
			return "";
		} else {
			String[] wheres = whereKeys.toString().split("-");
			whereSql.append(" where ");
			for (String w : wheres) {
				whereSql.append(w).append("=").append(data.get(w)).append(" and ");
			}
		}
		return whereSql.substring(0, whereSql.length() - 5);
	}

	/**
	 * 更新同步
	 * 
	 * @return
	 */
	public boolean updateCanal(Map<String, Object> data) {
		if(data == null){
			logger.info("数据为空，检查表映射配置是否遗漏");
			return false;
		}
		// 构建条件语句
		String table = data.remove(ConstantCalnal.TABLE_NAME).toString();
		String whereSql = getWhereSql(data, data.remove(ConstantCalnal.SEARCH_KEY));
		StringBuilder sb = new StringBuilder("update " + table + " set ");
		data.forEach((colum, value) -> {

			sb.append(colum).append(" = ").append(value == null || "".equals(value) ? null : "'" + value + "'")
					.append(", ");
		});

		StringBuilder sbSql = new StringBuilder(sb.substring(0, sb.length() - 2));
		sbSql.append(whereSql);
		jdbcTemplate.update(sbSql.toString());
		logger.info("sql->" + sbSql.toString());
		return true;
	}

	/**
	 * 更新同步
	 * 
	 * @return
	 */
	public boolean insertCanal(Map<String, Object> data) {
		if(data == null){
			logger.info("数据为空，检查表映射配置是否遗漏");
			return false;
		}
		// 构建条件语句
		String table = data.remove(ConstantCalnal.TABLE_NAME).toString();
		data.remove(ConstantCalnal.SEARCH_KEY);
		StringBuilder sb = new StringBuilder("insert into " + table);
		StringBuilder sbColums = new StringBuilder(" ( ");
		StringBuilder values = new StringBuilder(" values( ");
		data.forEach((colum, value) -> {
			sbColums.append(colum).append(", ");
			values.append(value == null || "".equals(value) ? null : " '" + value + "' " ).append(" , ");
		});

		sb.append(sbColums.substring(0, sbColums.length() - 2) + " ) ")
				.append(values.substring(0, values.length() - 2) + " ) ");
		
			try {
				jdbcTemplate.update(sb.toString());
			} catch (DataAccessException e) {
				if(e.getMessage().contains("Duplicate")){
					logger.error("数据已存在" + sb.toString());
				}else{
					logger.error(e.getMessage() , e);
				}
			}
		
		logger.info("sql->" + sb.toString());
		return true;
	}

	/**
	 * 更新同步
	 * 
	 * @return
	 */
	public boolean deleteCanal(Map<String, Object> data) {
		if(data == null){
			logger.info("数据为空，检查表映射配置是否遗漏");
			return false;
		}
		//构建条件语句
		String table = data.remove(ConstantCalnal.TABLE_NAME).toString();
		String  whereSql =  getWhereSql(data,  data.remove(ConstantCalnal.SEARCH_KEY));
		StringBuilder sb = new StringBuilder("delete from " + table);
		sb.append(whereSql);
		jdbcTemplate.update(sb.toString());
		logger.info("sql->" + sb.toString());
		return true;
	}

	private Map<String, Object> getData(List<Column> columns) {
		Map<String, Object> data = new LinkedHashMap<>();
		for (Column column : columns) {
			data.put(column.getName().toUpperCase(), column.getValue());
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

}
