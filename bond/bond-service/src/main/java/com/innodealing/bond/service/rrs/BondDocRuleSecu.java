package com.innodealing.bond.service.rrs;

import static com.innodealing.bond.service.rrs.BondDocGrammer.CURRENY;
import static com.innodealing.bond.service.rrs.BondDocGrammer.RATIO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.innodealing.bond.service.rrs.BondDocGrammer.FdFmt;
import com.innodealing.bond.service.rrs.BondDocGrammer.FormatLink;
import com.innodealing.bond.service.rrs.BondDocGrammer.GrammaticLink;
import com.innodealing.bond.vo.summary.BondIssDMRatingSummaryVO;
import com.innodealing.bond.vo.summary.IssRatingScoreSummary;

@Service("BondDocRuleSecu")
@Scope(value="prototype")
public class BondDocRuleSecu implements StrategyRuleI {

	private static final Logger LOG = LoggerFactory.getLogger(BondDocRuleSecu.class);
	
	private Long issuerId;
	private Long year;
	private Long quarter;

	private IDocEngine e;
	
	public BondDocRuleSecu(Long issuerId, Long userid, Long year, Long quarter, IDocEngine e)
	{
		//200036114  太平洋证券股份有限公司
		//200034985 广州证券股份有限公司
		this.issuerId = issuerId;
		this.year = year;
		this.quarter = quarter;
		this.e = e;
	}
	
	BondDocLayout buildDoc()
	{
		e.init(issuerId, year, quarter);
	
		/////////1. 总资产/////////////////////////////////////////
		BondDocField r11 = e.crtRatField("总资产指标", "RATIO1", new FdFmt(
				"%1$s从%2$.3f上升到%3$.3f。", "%1$s从%2$.3f下降到%3$.3f。", "%1$s为%3$.3f，维持不变。", "", "%1$s为%3$.3f。"));
	
		/////////2. 有息债务资本占比/////////////////////////////////////////
		BondDocField r21 = e.crtRatField("净资产收益率指标", "RATIO2", new FdFmt(
				"%1$s从%2$.3f上升到%3$.3f", "%1$s从%2$.3f下降到%3$.3f", "%1$s为%3$.3f，维持不变", "", "%1$s为%3$.3f"));
		
		BondDocField f22 = e.crtFinField("利润总额", "SPL400", new FdFmt(
				"%1$s从%2$,.2f万元上升到%3$,.2f万元", "%1$s从%2$,.2f万元减少到%3$,.2f万元", "%1$s为%3$,.2f万元，维持不变", "", "%1$s为%3$,.2f万元"), CURRENY);
		
		BondDocField f23 = e.crtFinField("所有者权益", "SBS003", new FdFmt(
				"%1$s从%2$,.2f万元上升到%3$,.2f万元", "%1$s从%2$,.2f万元减少到%3$,.2f万元", "%1$s为%3$,.2f万元，维持不变", "", "%1$s为%3$,.2f万元"), CURRENY);
		f22.link(new FormatLink("其中%1$s，%2$s", "其中%1$s", "其中%2$s", "", f23));
		r21.link(new FormatLink("%1$s，%2$s", "%1$s", "", "", f22));
		
		/////////3. 营业利润率/////////////////////////////////////////
		BondDocField r31 = e.crtRatField("营业利润率指标", "RATIO3", new FdFmt(
				"%1$s上升到%3$.3f，说明收入产生利润的能力提高，且收入增加或成本费用控制效果良好。", 
				"%1$s减少到%3$.3f，说明收入产生利润的能力减弱，且收入增加或成本费用控制效果变差。", 
				"%1$s为%3$.3f，维持不变，说明收入产生利润的能力基本不变，且收入增加或成本费用控制效果没有发生变化。", "", 
				"%1$s为%3$.3f，该指标反映出收入产生利润的能力，数值越大越好，且收入或成本费用控制效果越好。"));
		
		/////////4. 非利息收入占比/////////////////////////////////////////
		BondDocField r41 = e.crtFinField("非利息收入占比", "RATIO4", new FdFmt(
				"%1$s从%2$.2f上升为%3$.2f", "%1$s从%2$.2f下降为%3$.2f",
				"%1$s为%3$.2f，维持不变", "", "%1$s为%3$.2f"));
		BondDocField f42 = e.crtFinField("营业收入", "SPL100", new FdFmt(
				"%1$s为%3$,.2f万元", "%1$s为%3$,.2f万元", "%1$s为%3$,.2f万元", "", "%1$s为%3$,.2f万元"), CURRENY);
		BondDocField f43 = e.crtFinField("手续费及佣金净收入", "SPL101", new FdFmt(
				"%1$s从%2$,.2f万元上调为%3$,.2f万元", "%1$s从%2$,.2f万元下调到%3$,.2f万元",
				"%1$s为%3$,.2f万元", "", "%1$s为%3$,.2f万元"), CURRENY);

		f42.link(new FormatLink("当期%1$s，%2$s", "当期%1$s", "当期%2$s", "", f43));
		r41.link(new FormatLink("%2$s，所以%1$s。", "%1$s。", "", "", f42));
		
		/////////5. 流动资金覆盖率/////////////////////////////////////////
		BondDocField r51 = e.crtRatField("流动资金覆盖率指标", "RATIO5", new FdFmt(
				"%1$s从%2$.3f增加为%3$.3f，说明变现能力最好的资金对成本费用的覆盖程度提高", 
				"%1$s从%2$.3f下调到%3$.3f，说明变现能力最好的资金对成本费用的覆盖程度减弱",
				"%1$s为%3$.3f，保持不变，说明变现能力最好的资金对成本费用的覆盖程度基本不变", "", 
				"%1$s为%3$.3f，该指标反映变现能力最好的资金对成本费用的覆盖程度，数值越大越好"));
		BondDocField f52 = e.crtFinField("货币资金", "SBS101",  new FdFmt(
				"%1$s从%2$,.2f万元上升到%3$,.2f万元", "%1$s从%2$,.2f万元减少到%3$,.2f万元", "%1$s为%3$,.2f万元，维持不变", "", "%1$s为%3$,.2f万元"), CURRENY);
		BondDocField f53 = e.crtFinField("交易性金融资产", "SBS104", new FdFmt(
				"%1$s增加为%3$,.2f万元", "%1$s降低为%3$,.2f万元", "%1$s维持%3$,.2f万元水平", "", "%1$s为%3$,.2f万元"), CURRENY);
		BondDocField f54 = e.crtFinField("客户资金存款", "SBS101_1", null);
		BondDocField f55 = e.crtFinField("客户备付金", "SBS102_1", null);
		BondDocField f56 = e.crtFinField("短期借款", "SBS201", null);
		BondDocField f57 = e.crtFinField("交易性金融负债", "SBS203", null);
		BondDocField f58 = e.crtFinField("拆入资金", "SBS202", null);
		BondDocField f59 = e.crtFinField("应付职工薪酬", "SBS208", null);
		BondDocField f60 = e.crtFinField("应付利息", "SBS210", null);
		BondDocField f61 = e.crtFinField("应付税费", "SBS209", null);
		f52.link(new FormatLink("%1$s，%2$s{R51_REASON}", "%1$s{R51_REASON}", "%2$s{R51_REASON}", "", f53));
		r51.link(new FormatLink("%2$s，因此%1$s。", "%1$s。", "", "", f52));
		
		/////////6. 资产负债率/////////////////////////////////////////
		BondDocField r61 = e.crtRatField("资产负债率指标", "RATIO6", new FdFmt(
				"%1$s从%2$.3f上升为%3$.3f", "%1$s从%2$.2f下降为%3$.3f",
				"%1$s维持在%3$.3f", "", "%1$s为%3$.3f"));
		BondDocField r62 = e.crtRatField("资产负债率指标", "RATIO6", new FdFmt(
				"为资产负债率的增加提供了基础", "为资产负债率的下降提供了基础", "为资产负债率的水平提供了基础", 
				"", "为资产负债率的变化提供了基础"));
		BondDocField f62 = e.crtFinField("客户资金存款", "SBS101_1", new FdFmt(
				"%1$s从%2$,.2f万元上升到%3$,.2f万元", "%1$s从%2$,.2f万元减少到%3$,.2f万元", "%1$s为%3$,.2f万元", "", "%1$s为%3$,.2f万元"), CURRENY);
		BondDocField f63 = e.crtFinField("代理买卖证券款", "SBS206", new FdFmt(
				"%1$s上升到%3$,.2f万元", "%1$s减少到%3$,.2f万元", "%1$s维持在%3$,.2f万元", "", "%1$s为%3$,.2f万元"), CURRENY);	
		BondDocField f64 = e.crtFinField("代理承销证券款", "SBS207", new FdFmt(
				"%1$s上升到%3$,.2f万元", "%1$s减少到%3$,.2f万元", "%1$s维持在%3$,.2f万元", "", "%1$s为%3$,.2f万元"), CURRENY);	
		BondDocField f65 = e.crtFinField("客户备付金", "SBS102_1", new FdFmt(
				"%1$s增加到%3$,.2f万元", "%1$s下降到%3$,.2f万元", "%1$s维持在%3$,.2f万元", "", "%1$s为%3$,.2f万元"), CURRENY);	
		f62.link(new FormatLink("%1$s，%2$s", "%1$s", "%2$s", "", f63));
		f63.link(new FormatLink("%1$s，%2$s", "%1$s", "%2$s", "", f64));
		f64.link(new FormatLink("%1$s，%2$s", "%1$s", "%2$s", "", f65));
		f65.link(new FormatLink("%1$s，%2$s", "%1$s", "%2$s", "", r62));
		r61.link(new FormatLink("%1$s，同时%2$s", "%1$s", "", "", f62));
		
		e.loadData();
		
		String s1  = r11.genText();
		LOG.info(s1);
		
		String s2  = r21.genText();
		LOG.info(s2);
		
		String s3  = r31.genText();
		LOG.info(s3);
		
		String s4 = r41.genText();
		LOG.info(s4);
		
		String s5 = refineR51(r51, Arrays.asList(f54, f55, f56, f57, f58, f59, f60, f61));
		LOG.info(s5);
		
		String s6  = r61.genText();
		LOG.info(s6);
		
		BondDocLayout layout = e.createLayout().
				addLine(s1).
				addLine(s2).
				addLine(s3).
				addLine(s4).
				addLine(s5).
				addLine(s6);
		//addLine(s6);

		LOG.info(layout.toDoc());
		return layout;
	}
	
	private String refineR51(BondDocField r, List<BondDocField> causes) {
		Double direct = r.getLast() - r.getPre();
		String replacement = BondDocUtil.listFieldsByOrder(causes, 3, direct);
		if (!StringUtils.isEmpty(replacement)) {
			replacement = "，" + replacement + "等财务数据" + ((direct > 0)? "上升":"下降"); 
		}
		return r.genText().replaceFirst("\\{R51_REASON\\}", replacement);
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
		BondDocField r1 = e.crtRatField("资产总计", "RATIO1", FdFmt.DEFAULT_SCORE_FMT);
		BondDocField r2 = e.crtRatField("净资产收益率", "RATIO2", FdFmt.DEFAULT_SCORE_FMT);
		BondDocField r3 = e.crtRatField("营业利润率", "RATIO3", FdFmt.DEFAULT_SCORE_FMT);
		BondDocField r4 = e.crtRatField("非利息收入占比", "RATIO4", FdFmt.DEFAULT_SCORE_FMT);
		BondDocField r5 = e.crtRatField("流动资金覆盖率", "RATIO5", FdFmt.DEFAULT_SCORE_FMT);
		BondDocField r6 = e.crtRatField("资产负债率", "RATIO6", FdFmt.DEFAULT_SCORE_FMT);
		BondDocField r7 = e.crtRatField("利润变化率", "RATIO7", FdFmt.DEFAULT_SCORE_FMT);
		e.loadData();
		bu.addScore(r1, "规模", "资产总计");
		bu.addScore(r2, "盈利能力", "利润总额/所有者权益");
		bu.addScore(r3, "盈利能力", "营业利润/营业收入");
        bu.addScore(r7, "盈利能力", "（本年利润总额-上年利润总额）/本年利润总额");
        bu.addScore(r5, "盈利能力", "（货币资金+交易性金融资产）/（短期借款+交易性金融负债+拆入资金+应付职工薪酬+应付利息）");
        bu.addScore(r6, "资本结构", "负债合计/资产总计");
		bu.addScore(r4, "资本结构", "手续费及佣金收入/营业收入");

		bu.finish(e.getLastQuarter(), e.getPreQuarter());
		return true;
	}
}
