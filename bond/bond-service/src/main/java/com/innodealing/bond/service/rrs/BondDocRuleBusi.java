package com.innodealing.bond.service.rrs;

import static com.innodealing.bond.service.rrs.BondDocGrammer.CURRENY;
import static com.innodealing.bond.service.rrs.BondDocGrammer.RATIO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.innodealing.bond.service.rrs.BondDocGrammer.FdFmt;
import com.innodealing.bond.service.rrs.BondDocGrammer.FormatLink;
import com.innodealing.bond.service.rrs.BondDocGrammer.GrammaticLink;
import com.innodealing.bond.vo.summary.BondIssDMRatingSummaryVO;
import com.innodealing.bond.vo.summary.IssRatingScoreSummary;

@Service("BondDocRuleBusi")
@Scope(value="prototype")
public class BondDocRuleBusi implements StrategyRuleI {

	private static final Logger LOG = LoggerFactory.getLogger(BondDocRuleBusi.class);
	
	private Long issuerId;
	private Long year;
	private Long quarter;
	
	private IDocEngine e;
	
	public BondDocRuleBusi(Long issuerId, Long userid, Long year, Long quarter, IDocEngine e)
	{
		//200012978 广西玉柴机器股份有限公司
		this.issuerId = issuerId;
		this.year = year;
		this.quarter = quarter;
		this.e = e;
	}
	
	BondDocLayout buildDoc()
	{
		e.init(issuerId, year, quarter);
	
		/////////1. 所有者权益/////////////////////////////////////////
		BondDocField r11 = e.crtRatField("所有者权益指标", "RATIO1", new FdFmt(
				"%1$s从%2$.3f上升到%3$.3f。", "%1$s从%2$.3f减少到%3$.3f。", "%1$s为%3$.3f，维持不变。", "", "%1$s为%3$.3f。"));
	
		/////////2. 有息债务资本占比/////////////////////////////////////////
		BondDocField f21 = e.crtFinField("短期借款", "BS301", null, CURRENY);
		BondDocField f22 = e.crtFinField("一年内到期的非流动负债", "BS311", null, CURRENY);
		BondDocField f23 = e.crtFinField("交易性金融负债", "BS302", null, CURRENY);
		BondDocField f24 = e.crtFinField("所有者权益", "BS004", null, CURRENY);
		BondDocField f25 = e.crtFinField("应付短期债务", "BS302", null, CURRENY);
		BondDocField f26 = e.crtFinField("长期借款", "BS401", null, CURRENY);
		BondDocField f27 = e.crtFinField("应付债券", "BS402", null, CURRENY);
		BondDocField f28 = e.crtFinField("长期应付款", "BS313", null, CURRENY);
		BondDocField r29 = e.crtRatField("有息债务资本占比指标", "RATIO3", new FdFmt(
				"有息债务资本占比反映了企业偿还债务本金和支付债务信息的能力。{R29_REASON}该指标%1$s从%2$.3f上升到%3$.3f。", 
				"有息债务资本占比反映了企业偿还债务本金和支付债务信息的能力。{R29_REASON}该指标%1$s从%2$.3f减少到%3$.3f。", 
				"有息债务资本占比反映了企业偿还债务本金和支付债务信息的能力。%1$s为%3$.3f，维持不变。", "", 
				"有息债务资本占比反映了企业偿还债务本金和支付债务信息的能力。%1$s为%3$.3f。"));
		
		/////////3. 资本报酬率/////////////////////////////////////////
		BondDocField r31 = e.crtRatField("资本报酬率(利润总额/总资产)指标", "RATIO4", new FdFmt(
				"%1$s从%2$.3f上升到%3$.3f，说明企业资产的利用效果增高.", 
				"%1$s从%2$.3f下降到%3$.3f，说明企业资产的利用效果弱化.",
				"%1$s为%3$.3f，维持不变，该指标与企业资产的利用效果呈正相关.", "", 
				"%1$s为%3$.3f，该指标与企业资产的利用效果呈正相关."));
		
		/////////4. 营业利润率/////////////////////////////////////////
		BondDocField r41 = e.crtRatField("营业利润率指标", "RATIO5", new FdFmt(
				"%1$s从%2$.3f上升到%3$.3f，说明企业收入产生利润的能力提高，且企业收入增加或成本费用控制效果良好。", 
				"%1$s从%2$.3f下降到%3$.3f，说明企业收入产生利润的能力下降，且企业收入减少或成本费用控制效果稍差。",
				"%1$s为%3$.3f，维持不变，说明收入产生利润的能力与 成本费用控制效果基本保持不变。", "", 
				"%1$s为%3$.3f，该指标反映收入产生利润的能力与 成本费用控制效果，数值越高越好。"));

		/////////5. 保费变化率/////////////////////////////////////////
		BondDocField r51 = e.crtRatField("EBITA利息保障倍数指标", "RATIO6", new FdFmt(
				"%1$s从%2$.3f上升到%3$.3f，反映出{LAST_QUARTER}利息支出费用的覆盖程度有所上升。(影响该指标的因素有营业利润、利息支出、长期待摊费用摊销等)", 
				"%1$s从%2$.3f下降到%3$.3f，反映出{LAST_QUARTER}利息支出费用的覆盖程度有所下降。(影响该指标的因素有营业利润、利息支出、长期待摊费用摊销等)",
				"%1$s为%3$.3f，维持不变，反映出{LAST_QUARTER}利息支出费用的覆盖程度维持不变。(影响该指标的因素有营业利润、利息支出、长期待摊费用摊销等)", "", 
				"%1$s为%3$.3f。(影响该指标的因素有营业利润、利息支出、长期待摊费用摊销等)"));
		
		/////////6. 保费变化率/////////////////////////////////////////
		BondDocField f61 = e.crtFinField("经营活动现金流净额", "CF917", new FdFmt(
				"%1$s上升", "%1$s下降", "本期%1$s仍为%3$,.2f万元", "", "本期%1$s为%3$,.2f万元"), CURRENY);
		BondDocField r62 = e.crtFinField("内部现金投资系数指标", "RATIO7", new FdFmt(
				"由于%1$s从%2$.3f上升到%3$.3f，说明企业本身运用当期的经营性现金覆盖对外投资行为的能力提高，深一步反映出经营活动对投资活动的保证程度变高", 
				"由于%1$s从%3$.2f下降到%3$.3f，说明企业本身运用当期的经营性现金覆盖对外投资行为的能力变弱，深一步反映出经营活动对投资活动的保证程度下降", 
				"由于%1$s为%3$.3f，与上期维持不变，说明企业维持本身运用当期的经营性现金覆盖对外投资行为的能力以及经营活动对投资活动的保证程度", "", 
				"%1$s本期为%3$.3f，该指标反映出企业维持本身运用当期的经营性现金覆盖对外投资行为的能力，数值越大，说明经营活动对投资活动的保证程度越高"));
		f61.link(new FormatLink("%1$s，同时%2$s。", "", "%2$s。", "", r62));
		
		/////////8. 营业利润变化率/////////////////////////////////////////
		BondDocField r71 = e.crtRatField("营业利润变化率指标", "RATIO8", new FdFmt(
				"%1$s从%2$.3f上升到%3$.3f，该指标有效考量了企业在营业活动中产生的利润波动情况。", 
				"%1$s从%2$.3f下降到%3$.3f，该指标有效考量了企业在营业活动中产生的利润波动情况。",
				"%1$s为%3$.3f，维持不变，该指标有效考量了企业在营业活动中产生的利润波动情况。", "", 
				"%1$s为%3$.3f，该指标有效考量了企业在营业活动中产生的利润波动情况。"));
		
		e.loadData();
		
		String s1  = r11.genText();
		LOG.info(s1);
		
		String s2 = refineR29(r29, Arrays.asList(f21, f22, f23, f24, f25, f26, f27, f28));
		LOG.info(s2);
		
		String s3  = r31.genText();
		LOG.info(s3);
		
		String s4 = r41.genText();
		LOG.info(s4);
		
		String s5  = r51.genText();
		s5 = s5.replaceFirst("\\{LAST_QUARTER\\}", e.getLastQuarter());
		LOG.info(s5);
		
		String s6  = f61.genText();
		LOG.info(s6);
		
		String s7  = r71.genText();
		LOG.info(s7);
		
		BondDocLayout layout = e.createLayout().
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
	public boolean loadDMRatingSummary(BondIssDMRatingSummaryVO vo) 
	{	
		try {
		    BondDocLayout layout = buildDoc();
			layout.copy(vo);
			return true;
		}
		catch(Exception ex) {
			ex.printStackTrace();
			LOG.error("fail to loadDMRatingSummary {}", ex.getMessage());		}
		return false;
	}
	
	@Override
	public boolean loadIssRatingScoreSummary(IssRatingScoreSummary scoreSummary)
	{
		e.init(issuerId, year, quarter);
		IssScoreSummaryBuilder bu = new IssScoreSummaryBuilder(scoreSummary);
		BondDocField r1 = e.crtRatField("净资产", "RATIO1", FdFmt.DEFAULT_SCORE_FMT);
		BondDocField r2 = e.crtRatField("营业收入", "RATIO2", FdFmt.DEFAULT_SCORE_FMT);
		BondDocField r3 = e.crtRatField("有息债务资本占比", "RATIO3", FdFmt.DEFAULT_SCORE_FMT);
		BondDocField r4 = e.crtRatField("资产报酬率", "RATIO4", FdFmt.DEFAULT_SCORE_FMT);
		BondDocField r5 = e.crtRatField("营业利润率", "RATIO5", FdFmt.DEFAULT_SCORE_FMT);
		BondDocField r6 = e.crtRatField("EBITDA利息保障倍数", "RATIO6", FdFmt.DEFAULT_SCORE_FMT);
		BondDocField r7 = e.crtRatField("内部现金投资系数", "RATIO7", FdFmt.DEFAULT_SCORE_FMT);
		BondDocField r8 = e.crtRatField("营业利润变化比率", "RATIO8", FdFmt.DEFAULT_SCORE_FMT);
		e.loadData();
		bu.addScore(r1, "规模", "所有者权益");
		bu.addScore(r2, "规模", "营业收入");
		bu.addScore(r3, "资本结构", "（短期借款+一年内到期的非流动负债+交易性金融负债+应付短期债券+长期借款+ 应付债券+长期应付款 )/(短期借款+一年内到期的非流动负债+交易性金融负债+应付短期债券+长期借款+ 应付债券+长期应付款 +所有者权益)");
		bu.addScore(r4, "盈利能力", "利润总额/资产总计");
		bu.addScore(r5, "盈利能力", "营业利润/营业收入");
		bu.addScore(r6, "发展趋势", "EBITDA/利息支出");
        bu.addScore(r8, "发展趋势", "（本年营业利润-上年营业利润）/本年营业利润");
		bu.addScore(r7, "营运能力", "经营活动现金流净额/投资活动现金流净额");
		bu.finish(e.getLastQuarter(), e.getPreQuarter());
		return true;
	}
}
