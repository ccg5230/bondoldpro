package com.innodealing.bond.service.rrs;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.innodealing.bond.service.BondComExtService;
import com.innodealing.bond.service.BondInduService;
import com.innodealing.bond.service.rrs.BondDocGrammer.GrammaticLink;
import com.innodealing.bond.vo.summary.BondIssDMRatingSummaryVO;
import com.innodealing.engine.jpa.dm.BondPerFinanceRepository;
import com.innodealing.exception.BusinessException;
import com.innodealing.model.dm.bond.BondComExt;
import com.innodealing.model.dm.bond.BondPerFinance;
import com.innodealing.util.SafeUtils;

@Service("BondPriDocEngine")
@Scope(value="prototype")
public class BondPriDocEngine extends BondAbstractDocEngine implements IDocEngine {

	private static final Logger LOG = LoggerFactory.getLogger(BondPriDocEngine.class);

	@Autowired
	@Qualifier("asbrsPerResultJdbcTemplate")
	private JdbcTemplate jdbcTemplateAsbr;

	@Autowired
	@Qualifier("jdbcTemplate")
    protected JdbcTemplate jdbcTemplate;
	
	@Autowired
	private BondInduService induService;
	
	@Autowired
	private BondPerFinanceRepository perFinRepo;
	   
    protected BondDocTableSchema makeRatioTableMeta(Long issuerId)
    {
        return new BondDocTableSchema("v_dm_personal_rating_ratio_score", new HashMap<String, String>());
    }

    protected BondDocTableSchema makeFinTableMeta(Long issuerId)
    {
        return new BondDocTableSchema(comExtService.getCompClassFinTablePrefix(compClas), 
                new HashMap<String, String>());
    }

    protected BondDocTableSchema makeAnaTableMeta(Long issuerId)
    {
        return new BondDocTableSchema(comExtService.getCompClassAnaTablePrefix(compClas), 
                new HashMap<String, String>());
    }
    
    public void init(Long taskId, Long year, Long quarter)
    {
        BondPerFinance perFin = perFinRepo.findOneByTaskId(taskId);
        Map<String, Object> map = induService.getCompClass(perFin.getIndustryId().toString());
        if (null != map) {
            compClas = SafeUtils.getInteger(map.get("compClsName"));
        }
        super.init(taskId, year, quarter);
     }
        
	public void loadData()
	{
		String sqlTemplate = "select S.YEAR AS YEAR, %3$s %5$s, C.Comp_Name as com_chi_name, C.INDUSTRY_ID  from asbrs.%2$s AS S\r\n" + 
		        "left join asbrs.v_dm_personal_%4$s_fina_sheet AS F ON S.taskid = F.taskid AND CONCAT(LEFT(F.FIN_DATE, 4), LPAD(F.FIN_PERIOD, 2, '0')) = S.YEAR\r\n" +
		        "LEFT JOIN asbrs.v_dm_personal_comp_info as C on C.taskid = S.taskid\r\n" +
		        "where S.taskid = %1$d \r\n" + 
		        "ORDER BY S.YEAR DESC\r\n" + 
		        "LIMIT 2; " ;

		rTable.prepareForQuery();
		fTable.prepareForQuery();

		String sql = String.format(sqlTemplate, issuerId, 
				rTable.getTable(), rTable.getFieldstr(),
				fTable.getTable(), (!fTable.getFieldstr().isEmpty())? "," + fTable.getFieldstr() : "" 
				);

		LOG.info(sql);
		List<Map<String, Object>> records = jdbcTemplateAsbr.queryForList(sql);
		if(records.size() < 2) {
			throw new BusinessException("数据不完整，无法生成报表");
		}
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
	}

	
	public Integer findQuantileByIndu(Long taskId, Long userId, String ratio, Long yearmonth) {
	    Integer position = findRatioPositionByIndu(taskId, userId, ratio, yearmonth);
	    Integer count = countRatioPositionByIndu(taskId, userId, yearmonth);
	    Integer quantile = (int) (((double)position/count)*100);
	    return quantile;
	}

	public Integer countRatioPositionByIndu(Long taskId, Long userId, Long yearmonth) {
	    Integer position = null;
	    String sqlFormat = " select count(1) from \r\n" + 
	            "     (\r\n" + 
	            "         SELECT * FROM \r\n" + 
	            "           (\r\n" + 
	            "             select * from  /*amaresun*/ dmdb.rating_ratio_score R \r\n" + 
	            "             left join dmdb.t_bond_com_ext E on R.COMP_ID = E.ama_com_id\r\n" + 
	            "             where E.indu_uni_code = %1$d %2$s \r\n" + 
	            "             order BY R.COMP_ID, R.YEAR DESC \r\n" + 
	            "         ) T1\r\n" + 
	            "         group by COMP_ID\r\n" + 
	            "     ) T2"; //200035402

	    BondPerFinance perfin = perFinRepo.findOneByTaskId(taskId);
	    if (perfin == null) {
	        LOG.error("缺少公司关联信息， issuerId:" + taskId);
	    }
	    else { 
	        String dateCond = "";
	        if (yearmonth != null)
	            dateCond = " and YEAR = " + yearmonth;
	        String sql = String.format(sqlFormat, perfin.getIndustryId(), dateCond);
	        LOG.info("countRatioPositionByIndu's sql:" + sql);
	        position = jdbcTemplate.queryForObject(sql, Integer.class);

	    }
	    return position;
	}

	public Integer findRatioPositionByIndu(Long taskId, Long userId, String ratio, Long yearmonth) {
	    Integer position = null;
	    String sqlFormat = "select position from \r\n" + 
	            "(\r\n" + 
	            "   select T2.*, @rownum := @rownum + 1 AS position from  \r\n" + 
	            "   (\r\n" + 
	            "     SELECT * FROM \r\n" + 
	            "       (\r\n" + 
	            "         select * from  /*amaresun*/ dmdb.rating_ratio_score R \r\n" + 
	            "         left join dmdb.t_bond_com_ext E on R.COMP_ID = E.ama_com_id\r\n" + 
	            "         where E.indu_uni_code = %1$d %4$s \r\n" + 
	            "         order BY R.COMP_ID, R.YEAR DESC \r\n" + 
	            "     ) T1\r\n" + 
	            "     group by COMP_ID\r\n" + 
	            "   ) T2 JOIN (SELECT @rownum := 0) T_ \r\n" + 
	            "  ORDER BY T2.%3$s asc \r\n" + 
	            ") T3\r\n" + 
	            "where %3$s >= %2$,.3f \r\n" + 
	            "limit 1";
	    
	    BondPerFinance perfin = perFinRepo.findOneByTaskId(taskId);
	    if (perfin == null) {
	        LOG.error("缺少公司关联信息， issuerId:" + taskId);
	    }
	    else { 
	        String dateCond = "";
	        if (yearmonth != null)
	            dateCond = " and YEAR = " + yearmonth;
	        String sql = String.format(sqlFormat, perfin.getIndustryId()/100*100, //转换成3级行业
	                this.getField(ratio).getLast(), ratio, dateCond);
	        LOG.info("findRatioPositionByIndu sql:" + sql);
	        position = jdbcTemplate.queryForObject(sql, Integer.class);
	    }
	    return position;
	}
	
	public String findIndu(Long userId, Long taskId)
	{
	    BondPerFinance perfin = perFinRepo.findOneByTaskId(taskId);
	    if (perfin != null && perfin.getIndustryId() != null) {
    	    String sqlTemplate = "select third_name from  /*amaresun*/ dmdb.tbl_industry_classification I where I.industry_code = '%1$s'" ;
    	    String sql = String.format(sqlTemplate, perfin.getIndustryId());
    	    return jdbcTemplate.queryForObject(sql, String.class);
	    }
	    return "";
	}
}
