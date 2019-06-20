/**
 * 
 */
package com.innodealing.service;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Table;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.innodealing.dao.jdbc.dm.bond.gate.BondRuleRowKeyDao;
import com.innodealing.dao.redis.dm.bond.asbrs.RedisRuleRowKeyDao;
import com.innodealing.model.dm.bond.gate.BondRuleRowKey;
import com.innodealing.util.DateUtils;
import com.innodealing.util.JavaUtils;
import com.innodealing.util.RedisConstants;
import com.innodealing.util.StringUtils;

import utils.RowKeyUtils;

/**
 * @author Administrator
 *
 */
@Service
public class BondRuleRowKeyService {
	
	private static final Logger LOG = LoggerFactory.getLogger(BondRuleRowKeyService.class);
	
	/*@Autowired
	private BondRuleRowKeyRep bondRuleRowKeyRep;*/
	
	@Autowired
	BondRuleRowKeyDao bondRuleRowKeyDao;
	
	@Autowired
	private RedisRuleRowKeyDao redisRuleRowKeyDao;
	
	/**
	 * 获取rowkey
	 * @throws Exception 
	 */
	public String getRowKey(Object obj) throws Exception {
		if (null == obj) {
			return null;
		}
		String tableName = obj.getClass().getAnnotation(Table.class).name();
		String key = RedisConstants.BOND_RULE_ROW_KEY + tableName;
		BondRuleRowKey bondRuleRowKey = redisRuleRowKeyDao.get(key);
		if (null == bondRuleRowKey) {
			bondRuleRowKey = bondRuleRowKeyDao.getRowKey(tableName);
			if (null == bondRuleRowKey) {
				// 无数据,不处理
				return null;
			}
			redisRuleRowKeyDao.save(key, bondRuleRowKey);
		}
		String primaryKeyPrecisionIncluded = bondRuleRowKey.getPRI_KEY();
        // 返回含有精度的需要校验的字段
        String checkFieldsPrecisionIncluded = bondRuleRowKey.getCOL_NM();
        
		// 切割业务主键
        String[] primaryKeyPrecision = primaryKeyPrecisionIncluded.split(",");
        int precisionInt = 0;
        for (String str : primaryKeyPrecision) {
            if (str.contains("{")) {
                precisionInt = Integer.parseInt(str.substring(str.indexOf("{") + 1, str.indexOf("}")));
            }
        }
        // 得到不含精度且用逗号分隔的业务主键
        String primaryKey = RowKeyUtils.getPureColumns(primaryKeyPrecisionIncluded);


        // 返回用逗号分隔的需要校验的字段
        String checkFields = RowKeyUtils.getPureColumns(checkFieldsPrecisionIncluded);
        // 将需要校验的字段用“|”分隔然后存入一个数组
        String[] fieldsPrecision = checkFieldsPrecisionIncluded.split("\\|");
        // 将需要校验的字段存入一个数组
        String[] fields = checkFields.split(",");
        // 将需要的精度单独提出来放入一个数组(json格式)
        String[] precision = new String[fieldsPrecision.length];
        for (int i = 0; i < precision.length; i++) {
            String temp = fieldsPrecision[i];
            precision[i] = temp.substring(temp.indexOf('{'), temp.indexOf('}') + 1);
        }
        
        
        // 设定业务主键
        String businessKey;
        // 取得公司ID：COMP_ID
        String compId = JavaUtils.getFieldValueAsString(obj, "COMP_ID");
        // 取得财报日期：FIN_DATE
        String finDate = DateUtils.convertDateToString(JavaUtils.getFieldValueAsDate(obj, "FIN_DATE"), DateUtils.DATE_FORMAT);
        // 创建ROW_KEY中的业务主键部分
        businessKey = RowKeyUtils.add0ToPreFix(compId, precisionInt) + "_" + finDate;
        // 创建调用getMd5SourceString方法所需要的map数据
        Map<String, String> data = new HashMap<String, String>();
        // finalValue是data存储的是map键值对中的值
        String finalValue;
        for (int i = 0; i < fields.length; i++) {
            // 在财务表中查找业务主键及需要校验的数据
            Field field = obj.getClass().getDeclaredField(fields[i]);  
            field.setAccessible(true);
            String number = String.valueOf(field.get(obj));
            if (StringUtils.isBlank(number) || "null".equals(number)) {
                finalValue = "NULL";
            } else {
                finalValue = RowKeyUtils.getCorrectedNumber(number, precision[i]);
            }
            data.put(fields[i], finalValue);
        }
        // 创建得到MD5值所需要的字符串
        String md5Source = RowKeyUtils.getMd5SourceString(checkFields, data);
        // 得到该条数据的md5值
        String md5 = RowKeyUtils.md5(md5Source);
        // 创建该条数据的ROW_KEY值
		return businessKey + "_" + md5;
	}

}
