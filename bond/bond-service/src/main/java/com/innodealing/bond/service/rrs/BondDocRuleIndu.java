package com.innodealing.bond.service.rrs;

import static com.innodealing.bond.service.rrs.BondDocGrammer.CURRENY;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.innodealing.bond.service.rrs.BondDocGrammer.FdFmt;
import com.innodealing.bond.service.rrs.BondDocGrammer.FormatLink;
import com.innodealing.bond.vo.summary.BondIssDMRatingSummaryVO;
import com.innodealing.bond.vo.summary.IssRatingScoreSummary;

@Service("BondDocRuleIndu")
@Scope(value="prototype")
public class BondDocRuleIndu implements StrategyRuleI {

	private static final Logger LOG = LoggerFactory.getLogger(BondDocRuleIndu.class);
	
	private Long issuerId;
	private Long year;
	private Long quarter;
	private Long userId;
	private Long yearmonth;

	private Long firstYM;
	private Long secondYM;
	private boolean isComparison;
	   
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private BondDocRatioService ratioService;

	private IDocEngine engine;

    public BondDocRuleIndu(Long issuerId, Long userid, Long year, Long quarter, IDocEngine engine) {
        //200000648 瑞茂通供应链管理股份有限公司
        this.issuerId = issuerId;
        this.year = year;
        this.quarter = quarter;
        this.userId = userid;
        this.engine = engine;
        if (year != null && quarter != null)
            yearmonth = year*100+quarter*3;
    }

    public BondDocRuleIndu(Long issuerId, Long userId, Long firstYM, Long secondYM, IDocEngine engine, boolean isComparison) {
        //200000648 瑞茂通供应链管理股份有限公司
        this.issuerId = issuerId;
        this.firstYM = firstYM;
        this.secondYM = secondYM;
        this.isComparison = isComparison;
        this.isComparison = isComparison;
        this.userId = userId;
        this.engine = engine;
        if (year != null && quarter != null)
            yearmonth = year*100+quarter*3;
    }

	public BondDocRuleIndu(Long taskId, Long userid, IDocEngine engine) {
	    this.issuerId = taskId;
	    this.userId = userid;
	    this.engine = engine;
	}

	@Override
	public boolean loadDMRatingSummary(BondIssDMRatingSummaryVO vo) 
	{   
	    try {
	        BondDocLayout layout = buildDoc();
	        layout.copy(vo);
	        return true;
	    }
	    catch(Exception ex) {
	        ex.printStackTrace();
	        LOG.error("fail to loadDMRatingSummary {}", ex.getMessage());       }
	    return false;
	}
	   
	/*@Override
	public boolean loadDMRatingSummary(BondIssDMRatingSummaryVO vo) 
	{	
		try {
			IndustryRule rule = new IndustryRule(jdbcTemplate, issuerId)
				.setUserId(userId).setRatioService(ratioService);
			if(year != null && quarter != null)
				rule.setYearMonth(year*100+quarter*3);
			return rule.loadDMRatingSummary(vo);
		}
		catch(Exception ex) {
			ex.printStackTrace();
			LOG.error("fail to loadDMRatingSummary {}", ex.getMessage());		}
		return false;
	}*/

    BondDocLayout buildDoc() {
        engine.init(issuerId, year, quarter);
        /////////1. 所有者权益/////////////////////////////////////////
        BondDocField r11 = engine.crtRatField("所有者权益指标", "RATIO1", new FdFmt(
                "%1$s从%2$.3f上升到%3$.3f。", "%1$s从%2$.3f减少到%3$.3f。", "%1$s为%3$.3f，维持不变。", "", "%1$s为%3$.3f。"));
    
        /////////2. 有息债务资本占比/////////////////////////////////////////
        BondDocField f21 = engine.crtFinField("短期借款", "BS301", null, CURRENY);
        BondDocField f22 = engine.crtFinField("一年内到期的非流动负债", "BS311", null, CURRENY);
        BondDocField f23 = engine.crtFinField("交易性金融负债", "BS302", null, CURRENY);
        BondDocField f24 = engine.crtFinField("所有者权益", "BS004", null, CURRENY);
        BondDocField f25 = engine.crtFinField("应付短期债务", "BS302", null, CURRENY);
        BondDocField f26 = engine.crtFinField("长期借款", "BS401", null, CURRENY);
        BondDocField f27 = engine.crtFinField("应付债券", "BS402", null, CURRENY);
        BondDocField f28 = engine.crtFinField("长期应付款", "BS313", null, CURRENY);
        BondDocField r29 = engine.crtRatField("有息债务资本占比指标", "RATIO2", new FdFmt(
                "有息债务资本占比反映了企业偿还债务本金和支付债务信息的能力。{R29_REASON}该指标%1$s从%2$.3f上升到%3$.3f。", 
                "有息债务资本占比反映了企业偿还债务本金和支付债务信息的能力。{R29_REASON}该指标%1$s从%2$.3f减少到%3$.3f。", 
                "有息债务资本占比反映了企业偿还债务本金和支付债务信息的能力。%1$s为%3$.3f，维持不变。", "", 
                "有息债务资本占比反映了企业偿还债务本金和支付债务信息的能力。%1$s为%3$.3f。"));
        
        /////////3. 短期有息债务占比/////////////////////////////////////////
        BondDocField f31 = engine.crtFinField("交易性金融负债及其他短期债务", "BS302",
                new FdFmt("%1$s上升", "%1$s下降", "", "", ""), CURRENY);
        BondDocField r32 = engine.crtRatField("短期有息债务指标", "RATIO3", new FdFmt(
                "%1$s增加，反映出企业有息债务结构配置的改变", 
                "%1$s减少，反映出企业有息债务结构配置的改变",
                "%1$s为%3$.3f，维持不变", "", 
                "%1$s为%3$.3f"));
        r32.link(new FormatLink("%1$s，故%2$s", "%1$s", "", "", f31));
        
        /////////4. 资本报酬率/////////////////////////////////////////
        BondDocField r41 = engine.crtRatField("资本报酬率(利润总额/总资产)指标", "RATIO4", new FdFmt(
                "%1$s从%2$.3f上升到%3$.3f，说明企业资产的利用效果增高.", 
                "%1$s从%2$.3f下降到%3$.3f，说明企业资产的利用效果弱化.",
                "%1$s为%3$.3f，维持不变，该指标与企业资产的利用效果呈正相关.", "", 
                "%1$s为%3$.3f，该指标与企业资产的利用效果呈正相关."));
        
        /////////5. 经营活动现金流净额/////////////////////////////////////////
        BondDocField f51 = engine.crtFinField("经营活动现金流净额", "CF917", new FdFmt(
                "%1$s上升", "%1$s下降", "本期%1$s仍为%3$,.2f万元", "", "本期%1$s为%3$,.2f万元"), CURRENY);
        BondDocField f52 = engine.crtFinField("营业收入", "PL101", null, CURRENY);
        BondDocField r53 = engine.crtFinField("内部现金投资系数指标", "RATIO7", new FdFmt(
                "同时%1$s从%2$.3f上升到%3$.3f，说明企业本身运用当期的经营性现金覆盖对外投资行为的能力提高，深一步反映出经营活动对投资活动的保证程度变高。", 
                "同时%1$s从%3$.2f下降到%3$.3f，说明企业本身运用当期的经营性现金覆盖对外投资行为的能力变弱，深一步反映出经营活动对投资活动的保证程度下降。", 
                "同时%1$s为%3$.3f，与上期维持不变，说明企业维持本身运用当期的经营性现金覆盖对外投资行为的能力以及经营活动对投资活动的保证程度。", "", 
                "%1$s本期为%3$.3f，该指标反映出企业维持本身运用当期的经营性现金覆盖对外投资行为的能力，数值越大，说明经营活动对投资活动的保证程度越高。"));
        //f51.link(new FormatLink("由于%1$s，同时%2$s。", "", "%2$s。", "", r53));
        
        /////////6. EBITA利息保障倍数指标/////////////////////////////////////////
        BondDocField r61 = engine.crtRatField("EBITA利息保障倍数指标", "RATIO6", new FdFmt(
                "%1$s从%2$.3f上升到%3$.3f，反映出{LAST_QUARTER}利息支出费用的覆盖程度有所上升。(影响该指标的因素有营业利润、利息支出、长期待摊费用摊销等)", 
                "%1$s从%2$.3f下降到%3$.3f，反映出{LAST_QUARTER}利息支出费用的覆盖程度有所下降。(影响该指标的因素有营业利润、利息支出、长期待摊费用摊销等)",
                "%1$s为%3$.3f，维持不变，反映出{LAST_QUARTER}利息支出费用的覆盖程度维持不变。(影响该指标的因素有营业利润、利息支出、长期待摊费用摊销等)", "", 
                "%1$s为%3$.3f。(影响该指标的因素有营业利润、利息支出、长期待摊费用摊销等)"));
        
        /////////7. 营业利润变化率/////////////////////////////////////////
        BondDocField r71 = engine.crtRatField("营业利润变化率指标", "RATIO8", new FdFmt(
                "%1$s从%2$.3f上升到%3$.3f，该指标有效考量了企业在营业活动中产生的利润波动情况。", 
                "%1$s从%2$.3f下降到%3$.3f，该指标有效考量了企业在营业活动中产生的利润波动情况。",
                "%1$s为%3$.3f，维持不变，该指标有效考量了企业在营业活动中产生的利润波动情况。", "", 
                "%1$s为%3$.3f，该指标有效考量了企业在营业活动中产生的利润波动情况。"));

        engine.loadData();
        
        //1. 所有者权益
        String s1  = r11.genText();
        LOG.info(s1);
        
        //2. 有息债务资本占比
        String s2 = refineR29(r29, Arrays.asList(f21, f22, f23, f24, f25, f26, f27, f28));
        LOG.info(s2);
        
        //3. 短期有息债务占比
        String s3  = r32.genText();
        if (!StringUtils.isEmpty(s3)) {
            Integer position = engine.findRatioPositionByIndu(issuerId, userId, "RATIO3", yearmonth);
            if (position != null && position > 0) {
                String indu = engine.findIndu(userId, issuerId);
                s3 += String.format("，该指标在%1$s行业中处于%2$d分位，数值越高，则短期偿债风险越高。", indu, position);
            }
        }
        LOG.info(s3);
        
        //4. 资本报酬率
        String s4 = r41.genText();
        LOG.info(s4);
        
        //5. 经营活动现金流净额
        String s5  = f51.genText();
        if (!StringUtils.isEmpty(s5)) {
            Double cf917 = engine.getField("CF917").getLast();
            Double pl101 = engine.getField("PL101").getLast();
            if (pl101 != null && cf917 != null && 
                    !pl101.isNaN() && !cf917.isNaN() && 
                    pl101.compareTo(new Double(0.00)) > 0 && 
                    cf917.compareTo(new Double(0.00)) > 0 
                     ) {
                s5 += engine.getLastQuarter() + "收入中有" + engine.getField("CF917").getLast()/engine.getField("PL101").getLast() +
                        "的经营性现金流，占比越高，对企业的经营活动效率是一个良好的信号，";
            }
        }
        s5 += r53.genText(); 
        LOG.info(s5);
        
        //6. EBITA利息保障倍数指标
        String s6  = r61.genText();
        s6 = s6.replaceFirst("\\{LAST_QUARTER\\}", engine.getLastQuarter());
        LOG.info(s6);
        
        //7. 营业利润变化率
        String s7  = r71.genText();
        LOG.info(s7);
        
        BondDocLayout layout = engine.createLayout().
                addLine(s1).
                addLine(s2).
                addLine(s3).
                addLine(s4).
                addLine(s5).
                addLine(s6).
                addLine(s7);

        LOG.info(layout.toDoc());
        return layout;
    }
    
    private String refineR29(BondDocField r, List<BondDocField> causes) {
        Double direct = r.getLast() - r.getPre();
        String replacement = "";
        if (!direct.equals(0)) {
            replacement = BondDocUtil.listFieldsByOrder(causes, 3, direct);
            if (direct > 0) {
                replacement = String.format("由于%s等财务指标上升，", replacement);
            }
            else {
                replacement = String.format("由于%s等财务指标下降，", replacement); 
            }
        }
        return r.genText().replaceFirst("\\{R29_REASON\\}", replacement);
    }
    
    
	@Override
	public boolean loadIssRatingScoreSummary(IssRatingScoreSummary scoreSummary) {
        if (year == null || quarter == null) {
            engine.init(issuerId, firstYM, secondYM, true);
        } else {
            engine.init(issuerId, year, quarter);
        }
		IssScoreSummaryBuilder issBuilder = new IssScoreSummaryBuilder(scoreSummary);
		BondDocField r1 = engine.crtRatField("所有者权益", "RATIO1", FdFmt.DEFAULT_SCORE_FMT);
		BondDocField r2 = engine.crtRatField("有息债务资本占比", "RATIO2", FdFmt.DEFAULT_SCORE_FMT);
		BondDocField r3 = engine.crtRatField("短期有息债务占比", "RATIO3", FdFmt.DEFAULT_SCORE_FMT);
		BondDocField r4 = engine.crtRatField("资产利润率", "RATIO4", FdFmt.DEFAULT_SCORE_FMT);
		BondDocField r5 = engine.crtRatField("经营性现金产出率", "RATIO5", FdFmt.DEFAULT_SCORE_FMT);
		BondDocField r6 = engine.crtRatField("EBITDA利息保障倍数", "RATIO6", FdFmt.DEFAULT_SCORE_FMT);
		BondDocField r7 = engine.crtRatField("现金偿债比率", "RATIO7", FdFmt.DEFAULT_SCORE_FMT);
		BondDocField r8 = engine.crtRatField("内部现金投资系数", "RATIO8", FdFmt.DEFAULT_SCORE_FMT);
		BondDocField r9 = engine.crtRatField("营业利润变化比率", "RATIO9", FdFmt.DEFAULT_SCORE_FMT);
		BondDocField r10 = engine.crtRatField("行业风险等级", "RATIO10", FdFmt.DEFAULT_SCORE_FMT);

        engine.loadData();
        issBuilder.addScore(r1, "规模", "所有者权益");
        issBuilder.addScore(r2, "资本结构", "（短期借款+一年内到期的非流动负债+交易性金融负债+应付短期债券+长期借款+ 应付债券+长期应付款 )/"
				+ "(短期借款+一年内到期的非流动负债+交易性金融负债+应付短期债券+长期借款+ 应付债券+长期应付款 +所有者权益)");
        issBuilder.addScore(r3, "资本结构", "（短期借款+一年内到期的非流动负债+交易性金融负债+应付短期债券）/"
				+ "(短期借款+一年内到期的非流动负债+交易性金融负债+应付短期债券+长期借款+ 应付债券+长期应付款)");
		issBuilder.addScore(r4, "盈利能力", "利润总额/资产总计");
		issBuilder.addScore(r5, "营运能力", "经营活动现金流净额/营业收入");
	    issBuilder.addScore(r8, "营运能力", "经营活动现金流净额/投资活动现金流净额");
		issBuilder.addScore(r6, "短期偿债能力", "EBITTDA/利息支出");
		issBuilder.addScore(r7, "短期偿债能力", "");
		issBuilder.addScore(r9, "发展趋势", "（本年营业利润-上年营业利润）/本年营业利润");
		issBuilder.addScore(r10, "", "");
		
		issBuilder.finish(engine.getLastQuarter(), engine.getPreQuarter());
		return true;
	}
}
