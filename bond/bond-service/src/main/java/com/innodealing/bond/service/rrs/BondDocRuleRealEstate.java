package com.innodealing.bond.service.rrs;

import static com.innodealing.bond.service.rrs.BondDocGrammer.CURRENY;
import static com.innodealing.bond.service.rrs.BondDocGrammer.RATIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.innodealing.bond.service.rrs.BondDocGrammer.FdFmt;
import com.innodealing.bond.service.rrs.BondDocGrammer.GrammaticLink;
import com.innodealing.bond.vo.summary.BondIssDMRatingSummaryVO;
import com.innodealing.bond.vo.summary.IssRatingScoreSummary;

@Service("BondDocRuleRealEstate")
@Scope(value="prototype")
public class BondDocRuleRealEstate implements StrategyRuleI {

	private static final Logger LOG = LoggerFactory.getLogger(BondDocRuleRealEstate.class);
	
	private Long issuerId;
	private Long year;
	private Long quarter;
	
	private IDocEngine e;
	
	public BondDocRuleRealEstate(Long issuerId, Long userid, Long year, Long quarter, IDocEngine e)
	{
		//200000391
		this.issuerId = issuerId;
		this.year = year;
		this.quarter = quarter;
		this.e = e;
	}
	
	BondDocLayout buildDoc()
	{
		e.init(issuerId, year, quarter);
		
		/////////1. 权益偿债倍数/////////////////////////////////////////
		BondDocField r11 = e.crtRatField("权益偿债倍数指标", "RATIO1", new FdFmt(
				"%1$s从%2$.3f上升到%3$.3f，该指标为负向指标，即数值越小越好，反映了企业自有资金用来偿还有息债务的能力有所下降。", 
				"%1$s从%2$.3f下降到%3$.3f，该指标为负向指标，即数值越小越好，反映了企业自有资金用来偿还有息债务的能力有所提升。",
				"%1$s为%3$.3f，维持不变，该指标为负向指标，即数值越小越好，反映了企业自有资金用来偿还有息债务的能力。", "", 
				"%1$s为%3$.3f，该指标为负向指标，即数值越小越好，反映了企业自有资金用来偿还有息债务的能力。"));

		/////////2. 短期有息债务占比/////////////////////////////////////////
		BondDocField c21 = e.crtFinField("短期借款", "BS301",  
				new FdFmt("%1$s增加", "%1$s减少", "%1$s为%3$,.2f万元", "", "%1$s为%3$,.2f万元"), CURRENY);
		BondDocField c22 = e.crtFinField("一年内到期的非流动负债", "BS311", 
				new FdFmt("%1$s增加", "%1$s减少", "%1$s为%3$,.2f万元", "", "%1$s为%3$,.2f万元"), CURRENY);
		BondDocField c23 = e.crtFinField("应付债券", "BS402", new FdFmt(
				"%1$s增加", "%1$s减少", "%1$s为%3$,.2f万元", "", "%1$s为%3$,.2f万元"), CURRENY);
		BondDocField c24 = e.crtFinField("交易性金融负债", "BS302", new FdFmt(
				"%1$s增加", "%1$s减少", "%1$s为%3$,.2f万元", "", "%1$s为%3$,.2f万元"), CURRENY);
		BondDocField r25 = e.crtRatField("短期有息债务指标", "RATIO2", new FdFmt(
				"%1$s增加，反映出企业有息债务结构配置的改变，但是若数值太大则需要注意短期偿债风险提升", 
				"%1$s减少，反映出企业有息债务结构配置的改变，但是若数值太小则需要注意短期偿债风险提升",
				"%1$s为%3$.3f，反映出企业有息债务结构配置的改变，但是若数值太小或者太小则需要注意短期偿债风险提升", "", 
				"%1$s为%3$.3f，反映出企业有息债务结构配置的改变，但是若数值太小或者太小则需要注意短期偿债风险提升"));
		c21.link(new GrammaticLink("%1$s, %2$s", 0, c22))
			.link(new GrammaticLink("%1$s, 同时%2$s", 0, c23))
			.link(new GrammaticLink("%1$s, %2$s", 0, c24))
			.link(new GrammaticLink("%1$s, 使得%2$s", 2, r25));
		
		/////////3. EBITDA有息债务覆盖率/////////////////////////////////////////
		BondDocField c31 = e.crtFinField("营业利润", "PL400", new FdFmt(
				"{LAST_QUARTER}较{PRE_QUARTER}%1$s上升",
				"{LAST_QUARTER}较{PRE_QUARTER}%1$s降低", 
				"{LAST_QUARTER}%1$s为%3$,.2f万元", "", 
				"{LAST_QUARTER}%1$s为%3$,.2f万元"), CURRENY);
		BondDocField c32 = e.crtFinField("固定资产等折旧", "CF903", new FdFmt(
				"%1$s上升", "%1$s降低", "%1$s为%3$.2f%%", "", "%1$s为%3$.2f%%"), RATIO);
		BondDocField c33 = e.crtFinField("营业收入", "PL101", new FdFmt(
				"%1$s上升", "%1$s降低", "%1$s为%3$,.2f万元", "", "%1$s为%3$,.2f万元"), CURRENY);
		BondDocField r34 = e.crtRatField("EBITDA报酬率", "RATIO3", new FdFmt(
				"说明利润指标EBITDA的产生效率有所提高", "说明利润指标EBITDA的产生效率有所回落", 
				"该指标反映出利润EBITDA的产生效率, 数值越大越好", "", "该指标反映出利润EBITDA的产生效率, 数值越大越好"), RATIO);
		BondDocField r35 = e.crtRatField("EBITDA有息债务覆盖率指标", "RATIO6", new FdFmt(
				"%1$s从%2$.3f上调为%3$.3f", "%1$s从%2$.3f下调到%3$.3f",
				"%1$s为%3$.3f", "", "%1$s为%3$.3f"));
		c31.link(new GrammaticLink("%1$s, %2$s",  0, c32))
			.link(new GrammaticLink("%1$s, %2$s",  0, c33))
			.link(new GrammaticLink("%1$s, 故%2$s", 2, r34))
			.link(new GrammaticLink("%1$s, 同时%2$s",  0, r35)); //TODO "同时"需要额外增加一个and的语法

		/////////4. 盈利能力///////////////////////////////////////// 
		BondDocField c41 = e.crtFinField("净利润", "PL500", new FdFmt(
				"%1$s为%3$,.2f万元，较上期上升", "%1$s为%3$,.2f万元，较上期下降", "%1$s为%3$,.2f万元", "", "%1$s为%3$,.2f万元"), CURRENY);
		BondDocField c42 = e.crtFinField("总资产", "BS001", new FdFmt(
				"%1$s较上期上升", "%1$s较上期下降", "", "", ""), CURRENY);
		BondDocField r43 = e.crtRatField("税后的盈利能力指标", "RATIO4", new FdFmt(
				"反映出资产税后的盈利能力提升", "反映出资产税后的盈利能力减弱",
				"反映出资产税后的盈利能力基本不变", "", "净利润与总资产之比反映出资产税后的盈利能力，数值越大越好"));	
		c41.link(new GrammaticLink("{ISSUER}{LAST_QUARTER}%1$s, %2$s",  0, c42))
			.link(new GrammaticLink("%1$s, %2$s", 0, r43));
		
		/////////5. 占总负债比率/////////////////////////////////////////
		BondDocField c51 = e.crtFinField("经营活动现金流净额", "CF917", new FdFmt(
				"%1$s从%2$,.2f万元增加到%3$,.2f万元", "%1$s从%2$,.2f万元降低到%3$,.2f万元",
				"%1$s为%3$,.2f万元", "", "%1$s为%3$,.2f万元"), CURRENY);
		BondDocField r52 = e.crtRatField("占总负债比率指标", "RATIO5", new FdFmt(
				"%1$s增大", "%1$s降低", "%1$s为%3$.3f", "", "%1$s为%3$.3f"));
		final String desc = "该指标考量了企业经营性现金对总体债务的偿还效率，数值越大越好";
		BondDocField t53 = e.crtTxtField("占总负债比率", "RATIO5", new FdFmt(
				desc, desc, desc, "", desc));
		c51.link(new GrammaticLink("%1$s, %2$s", 0, r52))
		 .link(new GrammaticLink("%1$s, %2$s", 1, t53));
		 
		/////////6. 流动资金覆盖率/////////////////////////////////////////
		BondDocField c61 = e.crtFinField("货币资金", "BS101", new FdFmt(
				"%1$s从%2$,.2f万元增加为%3$,.2f万元", "%1$s从%2$,.2f万元降低到%3$,.2f万元", "%1$s为%3$,.2f万元", "", "%1$s为%3$,.2f万元"), CURRENY);
		BondDocField c62 = e.crtFinField("交易性金融资产", "BS302", new FdFmt(
				"%1$s增加为%3$,.2f万元", "%1$s减少为%3$,.2f万元", "%1$s为%3$,.2f万元", "", "%1$s为%3$,.2f万元"), CURRENY);
		BondDocField c63 = e.crtFinField("财务费用", "CF909", new FdFmt(
				"%1$s增加", "%1$s减少", "%1$s为%3$,.2f万元", "", "%1$s为%3$,.2f万元"), CURRENY);
		BondDocField c64 = e.crtFinField("营业成本", "PL102", new FdFmt(
				"%1$s增加", "%1$s减少", "%1$s为%3$,.2f万元", "", "%1$s为%3$,.2f万元"), CURRENY);
		BondDocField r65 = e.crtRatField("流动资金覆盖率", "RATIO8", new FdFmt(
				"%1$s增加至%3$.3f", "%1$s减少至%3$.3f",	"%1$s为%3$.3f", "", "%1$s为%3$.3f"));
		BondDocField r66 = e.crtRatField("流动资金覆盖率指标", "RATIO8", new FdFmt(
				"体现出变现能力最好的资金对成本费用的覆盖程度提升", "体现出变现能力最好的资金对成本费用的覆盖程度减弱",
				"体现出变现能力最好的资金对成本费用的覆盖程度基本不变", "", "数值越大，则变现能力最好的资金对成本费用的覆盖程度越高"));
		c61.link(new GrammaticLink("%1$s, %2$s", 0, c62))
		 .link(new GrammaticLink("%1$s, %2$s", 0, c62))
		 .link(new GrammaticLink("%1$s, %2$s", 0, c63))
		 .link(new GrammaticLink("%1$s, %2$s", 0, c64))
		 .link(new GrammaticLink("%1$s, 导致%2$s", 2, r65))
		 .link(new GrammaticLink("%1$s, %2$s", 1, r66));
		/////////5. 占总负债比率/////////////////////////////////////////
		
		e.loadData();
		
		String s1  = r11.genText();
		LOG.info(s1);
		
		String s2  = c21.genText();
		LOG.info(s2);
		
		String s3  = c31.genText();
		s3 = s3.replaceFirst("\\{LAST_QUARTER\\}", e.getLastQuarter());
		s3 = s3.replaceFirst("\\{PRE_QUARTER\\}", e.getPreQuarter());
		LOG.info(s3);
		
		String s4 = c41.genText().replaceAll("\\{ISSUER\\}", e.getIssuer()).replaceAll("\\{LAST_QUARTER\\}", e.getLastQuarter());
		LOG.info(s4);
		
		String s5  = c51.genText();
		LOG.info(s5);
		
		String s6  = c61.genText();
		LOG.info(s6);
		
		BondDocLayout layout = e.createLayout().
				addLine(s1).
				addLine(s2).
				addLine(s3).
				addLine(s4).
				addLine(s5).
				addLine(s6);

		LOG.info(layout.toDoc());
		return layout;
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
		BondDocField r1 = e.crtRatField("权益偿债倍数", "RATIO1", FdFmt.DEFAULT_SCORE_FMT);
		BondDocField r2 = e.crtRatField("短期有息债务占比", "RATIO2", FdFmt.DEFAULT_SCORE_FMT);
		BondDocField r3 = e.crtRatField("EBITDA报酬率", "RATIO3", FdFmt.DEFAULT_SCORE_FMT);
		BondDocField r4 = e.crtRatField("资产净利率", "RATIO4", FdFmt.DEFAULT_SCORE_FMT);
		BondDocField r5 = e.crtRatField("现金偿债比率", "RATIO5", FdFmt.DEFAULT_SCORE_FMT);
		BondDocField r6 = e.crtRatField("EBITDA有息债务覆盖率", "RATIO6", FdFmt.DEFAULT_SCORE_FMT);
		BondDocField r7 = e.crtRatField("所有者权益", "RATIO7", FdFmt.DEFAULT_SCORE_FMT);
		BondDocField r8 = e.crtRatField("流动资金覆盖率", "RATIO8", FdFmt.DEFAULT_SCORE_FMT);
		e.loadData();
		bu.addScore(r1, "资本结构", "(短期借款+一年内到期的非流动负债+长期借款+应付债券+长期应付款-货币资金)/所有者权益");
		bu.addScore(r2, "资本结构", "(短期借款+一年内到期的非流动负债)/(短期借款+一年内到期的非流动负债+交易性金融负债+应付短期债券+长期借款+ 应付债券+长期应付款)");
	    bu.addScore(r8, "资本结构", "（货币资金+交易性金融资产）/(营业成本+管理费用+财务费用)");
		bu.addScore(r3, "盈利能力", "EBITDA/营业收入");
		bu.addScore(r4, "盈利能力", "净利润 /资产总计");
		bu.addScore(r5, "短期偿债能力", "经营活动现金流净额/负债合计");
		bu.addScore(r6, "发展趋势", "EBITDA/利息支出");
		bu.addScore(r7, "规模", "所有者权益");

		bu.finish(e.getLastQuarter(), e.getPreQuarter());
		return true;
	}
}
