package com.innodealing.bond.service.rrs;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.innodealing.model.mongo.dm.BondComInfoDoc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.innodealing.bond.service.BondComExtService;
import com.innodealing.bond.service.rrs.BondDocGrammer.GrammaticLink;
import com.innodealing.bond.vo.summary.BondIssDMRatingSummaryVO;
import com.innodealing.exception.BusinessException;
import com.innodealing.model.dm.bond.BondComExt;
import com.innodealing.util.SafeUtils;

@Service("BondPubDocEngine")
@Scope(value="prototype")
public class BondPubDocEngine extends BondAbstractDocEngine implements IDocEngine {

    private static final Logger LOG = LoggerFactory.getLogger(BondPubDocEngine.class);

    protected BondDocTableSchema makeRatioTableMeta(Long issuerId) {
        return new BondDocTableSchema("rating_ratio_score", new HashMap<>());
    }

    protected BondDocTableSchema makeFinTableMeta(Long issuerId) {
        return new BondDocTableSchema(comExtService.getCompClassFinTablePrefix(compClas), new HashMap<>());
    }

    protected BondDocTableSchema makeAnaTableMeta(Long issuerId) {
        return new BondDocTableSchema(comExtService.getCompClassAnaTablePrefix(compClas), new HashMap<>());
    }

	public void init(Long issuerId, Long year, Long quarter) {
		Map<String, Object> map = comExtService.getCompClass(issuerId.toString());
		if (null != map) {
			compClas = SafeUtils.getInteger(map.get("compClsName"));
		}
		super.init(issuerId, year, quarter);
	}

	public void init(Long issuerId, Long firstDate, Long secondDate, boolean isComparison) {
		Map<String, Object> map = comExtService.getCompClass(issuerId.toString());
		if (null != map) {
			compClas = SafeUtils.getInteger(map.get("compClsName"));
		}
		super.init(issuerId, firstDate, secondDate, isComparison);
	}
    
    public void loadData() {
        assert aTable.getFields().isEmpty() : "暂时不支持重点指标表dm_analysis_xxx";
		String formatSql;

		rTable.prepareForQuery();
		fTable.prepareForQuery();
		aTable.prepareForQuery();

		if (this.isComparison) {
			String sqlTemplate = "SELECT ext.com_chi_name, R.YEAR, %4$s %5$s\n" +
					"FROM dmdb.rating_ratio_score AS R \n" +
					"LEFT JOIN dmdb.t_bond_com_ext AS ext ON ext.ama_com_id=R.COMP_ID\n" +
					"WHERE ext.com_uni_code=%3$d AND COALESCE(%4$s %5$s) IS NOT NULL AND R.`YEAR`=%1$d\n" +
					"UNION\n" +
					"SELECT ext.com_chi_name, R.YEAR, %4$s %5$s\n" +
					"FROM dmdb.rating_ratio_score AS R \n" +
					"LEFT JOIN dmdb.t_bond_com_ext AS ext ON ext.ama_com_id=R.COMP_ID\n" +
					"WHERE ext.com_uni_code=%3$d AND COALESCE(%4$s %5$s) IS NOT NULL AND R.`YEAR`=%2$d";
			formatSql = String.format(sqlTemplate, firstYM, secondYM, issuerId, rTable.getFieldstr(), (!fTable.getFieldstr().isEmpty())? "," + fTable.getFieldstr() : "");
		} else {
			String sqlTemplate = "SELECT * FROM \r\n" +
					"(\r\n" +
					"	SELECT E.com_chi_name, R.YEAR, %3$s %5$s\r\n" +
					"	from  /*amaresun*/ dmdb.%2$s AS R \r\n" + //rating_ratio_score
					"	LEFT JOIN dmdb.t_bond_com_ext AS E ON R.COMP_ID = E.ama_com_id\r\n" +
					"	LEFT JOIN dmdb.t_bond_%4$s_fina_sheet AS B ON B.COMP_ID = E.ama_com_id AND CONCAT(LEFT(B.FIN_DATE, 4), LPAD(B.FIN_PERIOD, 2, '0')) = R.YEAR\r\n" +
					"	WHERE E.com_uni_code = %1$d %8$s AND coalesce(%3$s %5$s) is not null \r\n" +
					"	ORDER BY R.`YEAR` DESC\r\n" +
					"	LIMIT 2\r\n" +
					") T\r\n" +

					(aTable == null || StringUtils.isEmpty(aTable.getFieldstr()) ? "" : new String(
							"LEFT JOIN  (\r\n" +
									"	(\r\n" +
									"		SELECT COMP_ID, fin_date, %7$s\r\n" +
									"		FROM  /*amaresun*/ dmdb.dm_analysis_%6$s as A left join dmdb.t_bond_com_ext AS E ON A.COMP_ID = E.ama_com_id\r\n" +
									"		where E.com_uni_code = %1$d\r\n" +
									"		order by A.fin_date desc limit 1\r\n" +
									"	)\r\n" +
									"	union all\r\n" +
									"	(\r\n" +
									"		SELECT COMP_ID, fin_date, %7$s\r\n" +
									"		FROM  /*amaresun*/ dmdb.dm_analysis_%6$s_annual as A left join dmdb.t_bond_com_ext AS E ON A.COMP_ID = E.ama_com_id\r\n" +
									"		where E.com_uni_code = %1$d\r\n" +
									"		order by A.fin_date desc limit 2\r\n" +
									"	)\r\n" +
									") F on F.COMP_ID = T.COMP_ID AND CONCAT(LEFT(F.FIN_DATE, 4), MID(F.FIN_DATE, 6, 2)) = T.YEAR\r\n" +
									""));

			formatSql = String.format(sqlTemplate, issuerId,
					rTable.getTable(), rTable.getFieldstr(),
					fTable.getTable(), (!fTable.getFieldstr().isEmpty())? "," + fTable.getFieldstr() : "" ,
					aTable.getTable(), (!aTable.getFieldstr().isEmpty())? "," + aTable.getFieldstr() : "" ,
					(selectedQuarter == null)? "" :String.format("and R.YEAR <= %d", selectedQuarter)
			);
		}
		
		List<Map<String, Object>> records = jdbcTemplate.queryForList(formatSql);
		//if(records.size() < 2) {
			// throw new BusinessException("数据不完整，无法生成报表");
			//return;
		//}
		if (records.size() == 2) {
			for(int i = 0; i < 2; ++i) {
				Map<String, Object> record = records.get(i);
				Iterator it = record.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry pair = (Map.Entry)it.next();
					List<BondDocField> fset = col2fieldsMap.get(pair.getKey());
					if (fset == null) 
					{
						if(pair.getKey().equals("YEAR")) {
							if (i == 0) 
								lastQuarter = SafeUtils.convertFromYearQnToYearDesc(SafeUtils.getQuarter(pair.getValue().toString()));
							else
								preQuarter = SafeUtils.convertFromYearQnToYearDesc(SafeUtils.getQuarter(pair.getValue().toString()));
						}
						else if (pair.getKey().equals("com_chi_name")) {
							issuer = pair.getValue().toString();
						}
					}
					else { 
						for(BondDocField f : fset) {
							if (f != null) {
								if (i == 0) 
									f.setLast(f.valPreProc().format(pair.getValue()));
								else
									f.setPre(f.valPreProc().format(pair.getValue()));
							}
						}
					}
					it.remove(); // avoids a ConcurrentModificationException
				}		
			}
		} else if (records.size() == 1) {
			Map<String, Object> record = records.get(0);
			record.keySet().stream().forEach(key -> {
				List<BondDocField> fset = col2fieldsMap.get(key);
				if (fset == null) {
					if (record.containsKey("YEAR")) {
						lastQuarter = SafeUtils.convertFromYearQnToYearDesc(SafeUtils.getQuarter(record.get("YEAR").toString()));
					} else if (record.containsKey("com_chi_name")) {
						issuer = record.get("com_chi_name").toString();
					}
				} else {
					fset.stream().forEach(f -> {
						if (f != null) {
							f.setLast(f.valPreProc().format(record.get(key)));
						}
					});
				}
			});
		}
    }

    public Integer findQuantileByIndu(Long issuerId, Long userId, String ratio, Long yearmonth) {
        Integer position = findRatioPositionByIndu(issuerId, userId, ratio, yearmonth);
        Integer count = countRatioPositionByIndu(issuerId, userId, yearmonth);
        Integer quantile = (int) (((double)position/count)*100);
        return quantile;
    }

    public Integer countRatioPositionByIndu(Long issuerId, Long userId, Long yearmonth) {
        Integer position = null;
        String sqlFormat = " select count(1) from \r\n" + 
                "     (\r\n" + 
                "         SELECT * FROM \r\n" + 
                "           (\r\n" + 
                "             select * from  /*amaresun*/ dmdb.rating_ratio_score R \r\n" + 
                "             left join dmdb.t_bond_com_ext E on R.COMP_ID = E.ama_com_id\r\n" + 
                "             where E.%2$s = %1$d %3$s \r\n" + 
                "             order BY R.COMP_ID, R.YEAR DESC \r\n" + 
                "         ) T1\r\n" + 
                "         group by COMP_ID\r\n" + 
                "     ) T2"; //200035402

        Boolean isGisc = induService.isGicsInduClass(userId);
        BondComExt comExt = comExtRepository.findByComUniCode(issuerId);
        if (comExt == null) {
            LOG.error("缺少公司关联信息， issuerId:" + issuerId);
        }
        else { 
            String dateCond = "";
            if (yearmonth != null)
                dateCond = " and YEAR = " + yearmonth;
            String sql = String.format(sqlFormat, 
                    isGisc?comExt.getInduUniCode():comExt.getInduUniCodeSw() /*行业id*/, 
                            isGisc? "indu_uni_code" : "indu_uni_code_sw"/*行业字段名*/, dateCond);
            LOG.info("countRatioPositionByIndu's sql:" + sql);
            position = jdbcTemplate.queryForObject(sql, Integer.class);

        }
        return position;
    }

    public Integer findRatioPositionByIndu(Long issuerId, Long userId, String ratio, Long yearMonth) {
    	List<Long> highLevelComUniCodeList = induService.getHighLevelComUniCodeListByIndu(issuerId, userId);
		Integer modelType = comExtService.getModelType(issuerId.toString());
		String induComUniCodeArrStr = org.apache.commons.lang.StringUtils.join(highLevelComUniCodeList, ",");
		induComUniCodeArrStr = StringUtils.isEmpty(induComUniCodeArrStr) ? "''" : induComUniCodeArrStr;
		String coreSql = "SELECT T1.*, @rownum:=@rownum+1 AS position FROM" +
				" (SELECT R.RATIO3, E.com_uni_code FROM dmdb.rating_ratio_score AS R" +
				" LEFT JOIN dmdb.t_bond_com_ext AS E ON R.COMP_ID=E.ama_com_id" +
				" WHERE R.MODEL_ID=%1$d AND R.`YEAR`=%2$d AND E.com_uni_code IN (%3$s)) AS T1" +
				" JOIN (SELECT @rownum:=0) AS r ORDER BY T1.RATIO3";
		String formatCoreSql = String.format(coreSql, modelType, yearMonth, induComUniCodeArrStr);
		String positionSql = "SELECT position FROM (%1$s) AS T2 WHERE com_uni_code=%2$d;";
		String formatPositionSql = String.format(positionSql, formatCoreSql, issuerId);
		String formatCountSql = String.format("SELECT COUNT(1) FROM (%1$s) AS T2", formatCoreSql);
		try {
			Integer position = jdbcTemplate.queryForObject(formatPositionSql, Integer.class);
			Integer count = jdbcTemplate.queryForObject(formatCountSql, Integer.class);
			Integer quantile = position * 100 / count;
			return quantile;
		} catch (Exception ex) {
			throw new BusinessException("findRatioPositionByIndu 找不到记录");
		}





//        Integer position = null;
//        String sqlFormat = "select position from \r\n" +
//                "(\r\n" +
//                "   select T2.*, @rownum := @rownum + 1 AS position from  \r\n" +
//                "   (\r\n" +
//                "     SELECT * FROM \r\n" +
//                "       (\r\n" +
//                "         select * from  /*amaresun*/ dmdb.rating_ratio_score R \r\n" +
//                "         left join dmdb.t_bond_com_ext E on R.COMP_ID = E.ama_com_id\r\n" +
//                "         where E.%3$s = %1$d %5$s\r\n" + //indu_uni_code or indu_uni_code_sw //40301000
//                "         order BY R.COMP_ID, R.YEAR DESC \r\n" +
//                "     ) T1\r\n" +
//                "     group by COMP_ID\r\n" +
//                "   ) T2 JOIN (SELECT @rownum := 0) r\r\n" +
//                "  ORDER BY T2.%4$s" +
//                ") T3\r\n" +
//                "where com_uni_code = %2$d "; //200035402
//
//        Boolean isGisc = induService.isGicsInduClass(userId);
//        BondComExt comExt = comExtRepository.findByComUniCode(issuerId);
//        if (comExt == null) {
//            LOG.error("缺少公司关联信息， issuerId:" + issuerId);
//        }
//        else {
//            String dateCond = "";
//            if (yearmonth != null)
//                dateCond = " and YEAR = " + yearmonth;
//            String sql = String.format(sqlFormat, isGisc?comExt.getInduUniCode():comExt.getInduUniCodeSw() /*行业id*/,
//                    issuerId/*公司id*/, isGisc? "indu_uni_code" : "indu_uni_code_sw"/*行业字段名*/, ratio, dateCond);
//            LOG.info("findRatioPositionByIndu sql:" + sql);
//            position = jdbcTemplate.queryForObject(sql, Integer.class);
//
//        }
//        return position;
    }
    
    public String findIndu(Long userId, Long issuerId) {
		if (!induService.isInduInstitution(userId)) {
			try {
				String sqlTemplate = "select third_name from  /*amaresun*/ dmdb.tbl_industry_classification I \r\n" +
						"left join dmdb.t_bond_com_ext E on E.indu_uni_code_l4 = I.industry_code\r\n" +
						"where E.com_uni_code = %1$d";
				String sql = String.format(sqlTemplate, issuerId);
				return jdbcTemplate.queryForObject(sql, String.class);
			} catch (Exception ex) {
				LOG.error("failed to findIndu", ex);
				return "";
			}
		} else {
			// 自定义行业
			String code = induService.getThirdPartyCodeValue(userId, issuerId);
			String sql = "SELECT indu_class_name FROM institution.t_bond_inst_indu WHERE indu_uni_code=" +
					" (SELECT fat_uni_code FROM institution.t_bond_inst_indu WHERE indu_uni_code=%1$s)";
			String formatSql = String.format(sql, code);
			return jdbcTemplate.queryForObject(formatSql, String.class);
		}
	}
}
