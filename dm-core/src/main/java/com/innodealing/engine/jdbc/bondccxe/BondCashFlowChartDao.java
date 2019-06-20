/**
 * 
 */
package com.innodealing.engine.jdbc.bondccxe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.innodealing.model.mongo.dm.BondDetailDoc;
import com.innodealing.util.SafeUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.innodealing.model.dm.bond.ccxe.BondCashFlowChart;

@Component
public class BondCashFlowChartDao {
	
	public final static Logger logger = LoggerFactory.getLogger(BondCashFlowChartDao.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

    @Autowired
    MongoOperations mongoOperations;
	
    @Cacheable(value = "futureCashFlow", key="#bondUinCode") 
    public List<BondCashFlowChart> findFutureCashFlow(String bondUinCode)
    {
        String sql = "SELECT INTE_PAY, INTE_PERI, PAY_AMUT, INTE_START_DATE, INTE_END_DATE \r\n" + 
                "FROM bond_ccxe.d_bond_cash_flow_chart F \r\n" + 
                "WHERE F.BOND_UNI_CODE = %1$s AND F.isvalid = 1 AND CONCAT(CURDATE(), ' 00:00:00') <= F.INTE_END_DATE order by INTE_PERI asc";
        return jdbcTemplate.query(String.format(sql, bondUinCode), new BeanPropertyRowMapper<BondCashFlowChart>(BondCashFlowChart.class));
    }
    
    /**
     * 存在跨市场行为的债券，获取一支即可
     */
    public List<Long> getDistCrossMarketBondUniCodeList(List<Long> bondUinCodeList) {
    	String bondUinCodeArrStr = StringUtils.join(bondUinCodeList.toArray(), ",");
        bondUinCodeArrStr = StringUtils.isBlank(bondUinCodeArrStr) ? "''" : bondUinCodeArrStr;
        String sql = "SELECT BOND_UNI_CODE FROM ("
                + " SELECT BOND_ID, BOND_UNI_CODE FROM bond_ccxe.d_bond_basic_info_1 WHERE BOND_UNI_CODE IN (%1$s) UNION ALL"
                + " SELECT BOND_ID, BOND_UNI_CODE FROM bond_ccxe.d_bond_basic_info_2 WHERE BOND_UNI_CODE IN (%1$s) UNION ALL"
                + " SELECT BOND_ID, BOND_UNI_CODE FROM bond_ccxe.d_bond_basic_info_3 WHERE BOND_UNI_CODE IN (%1$s) UNION ALL"
                + " SELECT BOND_ID, BOND_UNI_CODE FROM bond_ccxe.d_bond_basic_info_4 WHERE BOND_UNI_CODE IN (%1$s) UNION ALL"
                + " SELECT BOND_ID, BOND_UNI_CODE FROM bond_ccxe.d_bond_basic_info_5 WHERE BOND_UNI_CODE IN (%1$s)"
                + " ) AS bi GROUP BY bi.BOND_ID ORDER BY bi.bond_ID;";
        String formatSql = String.format(sql, bondUinCodeArrStr);
        return jdbcTemplate.queryForList(formatSql, Long.class);
    }

    /**
     * 获取限定时间内, 给定债券列表的待付本息总数
     * @param bondUinCodeList
     * @param startDateStr
     * @param endDateStr
     * @return
     */
    public Double getValidTotalPayAmount(List<Long> bondUinCodeList, String startDateStr, String endDateStr){
        String bondUinCodeArrStr = StringUtils.join(bondUinCodeList.toArray(), ",");
        bondUinCodeArrStr = StringUtils.isBlank(bondUinCodeArrStr) ? "''" : bondUinCodeArrStr;
        String sql = "SELECT BOND_UNI_CODE, PAY_AMUT FROM bond_ccxe.d_bond_cash_flow_chart"
                + " WHERE ISVALID=1 AND INTE_END_DATE>='%1$s' AND INTE_END_DATE<'%2$s'"
                + " AND BOND_UNI_CODE IN (%3$s)";
        String formatSql = String.format(sql, startDateStr, endDateStr, bondUinCodeArrStr);
        List<Map<String, Object>> cashFlowMapList = jdbcTemplate.queryForList(formatSql);
        if (!cashFlowMapList.isEmpty()) {
            Query query = new Query(Criteria.where("_id").in(bondUinCodeList));
            query.fields().include("_id").include("newSize");
            List<BondDetailDoc> detailList = mongoOperations.find(query, BondDetailDoc.class);
            Map<Long, Double> bondUniCode2SizeMap = detailList.stream()
                    .collect(Collectors.toMap(BondDetailDoc::getBondUniCode, BondDetailDoc::getNewSize));
            List<Double> resultList = new ArrayList<>();
            cashFlowMapList.stream().forEach(cashEntry -> {
                Long bondUniCode = SafeUtils.getLong(cashEntry.get("BOND_UNI_CODE"));
                Double payAmount = SafeUtils.getDouble(cashEntry.get("PAY_AMUT"));
                Double newSize = bondUniCode2SizeMap.get(bondUniCode);
                resultList.add(newSize * payAmount / 100);
            });
            if (!resultList.isEmpty()) {
                return resultList.stream().mapToDouble(item -> item).sum();
            }
        }
        return 0.0;
    }

}
