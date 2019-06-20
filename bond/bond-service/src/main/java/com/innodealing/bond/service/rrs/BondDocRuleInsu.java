package com.innodealing.bond.service.rrs;

import static com.innodealing.bond.service.rrs.BondDocGrammer.CURRENY;
import static com.innodealing.bond.service.rrs.BondDocGrammer.RATIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.innodealing.bond.service.BondInduService;
import com.innodealing.bond.service.rrs.BondDocGrammer.FdFmt;
import com.innodealing.bond.service.rrs.BondDocGrammer.FormatLink;
import com.innodealing.bond.service.rrs.BondDocGrammer.GrammaticLink;
import com.innodealing.bond.vo.summary.BondIssDMRatingSummaryVO;
import com.innodealing.bond.vo.summary.IssRatingScoreSummary;
import com.innodealing.engine.jpa.dm.BondComExtRepository;
import com.innodealing.model.dm.bond.BondComExt;

@Service("BondDocRuleInsu")
@Scope(value="prototype")
public class BondDocRuleInsu implements StrategyRuleI {

	private static final Logger LOG = LoggerFactory.getLogger(BondDocRuleInsu.class);
	
	private Long issuerId;
	private Long userId;
	private Long year;
	private Long quarter;
	private Long yearmonth;
	
	@Autowired
	protected BondInduService induService;

	@Autowired
	protected BondComExtRepository comExtRepository;
	
	@Autowired 
	protected BondDocRatioService ratioService;
	
	private IDocEngine e;

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public BondDocRuleInsu(Long issuerId, Long userId, Long year, Long quarter, IDocEngine e)
	{
		//200035402
		//200035800 中国人寿保险股份有限公司
		this.issuerId = issuerId;
		this.userId = userId;
		this.year = year;
		this.quarter = quarter;
		if (year != null && quarter != null) 
			yearmonth = year*100+quarter*3;
		this.e = e;
	}
	
	BondDocLayout buildDoc()
	{
		e.init(issuerId, year, quarter);
				
		/////////1. 净资产收益率/////////////////////////////////////////
		BondDocField r11 = e.crtRatField("净资产收益率指标", "RATIO1", 
				new FdFmt("%1$s从%2$.3f上升到%3$.3f", "%1$s从%2$.3f下降到%3$.3f","%1$s为%3$.3f，维持不变", "", "%1$s为%3$.3f"));
		BondDocField f12 = e.crtFinField("利润总额", "IPL400", 
				new FdFmt("%1$s从%2$,.2f万元上升到%3$,.2f万元", "%1$s从%2$,.2f万元下降到%3$,.2f万元","%1$s为%3$,.2f万元，维持不变", "", "%1$s为%3$,.2f万元"), CURRENY);
		BondDocField f13 = e.crtFinField("所有者权益", "IBS003", 
				new FdFmt("%1$s从%2$,.2f万元上升到%3$,.2f万元", "%1$s从%2$,.2f万元下降到%3$,.2f万元","%1$s为%3$,.2f万元，维持不变", "", "%1$s为%3$,.2f万元"), CURRENY);
		f12.link(new GrammaticLink("%1$s, %2$s", 0, f13));
		r11.link(new GrammaticLink("%1$s, 其中%2$s", 1, f12));
	
		/////////2. 总资产/////////////////////////////////////////
		BondDocField r2 = e.crtRatField("总资产指标", "RATIO2", 
				new FdFmt("%1$s从%2$.3f上升到%3$.3f", "%1$s从%2$.3f下降到%3$.3f","%1$s为%3$.3f，维持不变", "", "%1$s为%3$.3f"));
		
		/////////3. 资本充足率/////////////////////////////////////////
		BondDocField r3 = e.crtRatField("偿付能力充足率(实际资本/最低资本)指标", "RATIO3", 
				new FdFmt("%1$s上升为%3$.3f", "%1$s减少到%3$.3f", "%1$s为%3$.3f，维持不变", "", "%1$s为%3$.3f"));
		
		/////////4. 赔付率/////////////////////////////////////////
		BondDocField r41 = e.crtRatField("赔付率指标", "RATIO4", 
				new FdFmt("%1$s从%2$.3f上升到%3$.3f", "%1$s从%2$.3f下降到%3$.3f","%1$s为%3$.3f，维持不变", "", "%1$s为%3$.3f"));
		BondDocField f42 = e.crtFinField("赔付支出", "IPL202", 
				new FdFmt("%1$s增加为%3$,.2f万元", "%1$s减少为%3$,.2f万元", "%1$s为%3$,.2f万元", "", "%1$s为%3$,.2f万元"), CURRENY);
		BondDocField f43 = e.crtFinField("分出保费", "IPL102_2", 
				new FdFmt("%1$s增加为%3$,.2f万元", "%1$s减少为%3$,.2f万元", "%1$s为%3$,.2f万元", "", "%1$s为%3$,.2f万元"), CURRENY);
		BondDocField f44 = e.crtFinField("保险业务收入", "IPL102", 
				new FdFmt("%1$s增加为%3$,.2f万元", "%1$s减少为%3$,.2f万元", "%1$s为%3$,.2f万元", "", "%1$s为%3$,.2f万元"), CURRENY);
		r41.link(new GrammaticLink("%1$s, 其中%2$s。", 1, f42)).link(new GrammaticLink("%1$s, %2$s", 0, f43))
			.link(new GrammaticLink("%1$s, %2$s", 0, f44));
		
		/////////5. 已赚保费覆盖费用率/////////////////////////////////////////
		BondDocField r51 = e.crtRatField("已赚保费覆盖费用率指标", "RATIO5", 
				new FdFmt("%1$s从%2$.3f上升到%3$.3f", "%1$s从%2$.3f下降到%3$.3f","%1$s为%3$.3f，维持不变", "", "%1$s为%3$.3f"));
		BondDocField f52 = e.crtFinField("营业支出", "IPL200", 
				new FdFmt("%1$s增加为%3$,.2f万元", "%1$s减少为%3$,.2f万元", "%1$s为%3$,.2f万元", "", "%1$s为%3$,.2f万元"), CURRENY);
		BondDocField f53 = e.crtFinField("已赚保费", "IPL101", 
				new FdFmt("%1$s增加为%3$,.2f万元", "%1$s减少为%3$,.2f万元", "%1$s为%3$,.2f万元", "", "%1$s为%3$,.2f万元"), CURRENY);
		r51.link(new GrammaticLink("%1$s, 其中%2$s。", 1, f52)).link(new GrammaticLink("%1$s, %2$s", 0, f53));
		
		/////////6. 保费变化率/////////////////////////////////////////
		BondDocField r61 = e.crtRatField("保费变化率指标", "RATIO6", 
				new FdFmt("%1$s从%2$.3f上升到%3$.3f", "%1$s从%2$.3f下降到%3$.3f","%1$s为%3$.3f，维持不变", "", "%1$s为%3$.3f"));
		BondDocField f62 = e.crtFinField("保险业务收入", "IPL102", 
				new FdFmt("上升为%3$,.2f万元", "减少到%3$,.2f万元", "为%3$,.2f万元，维持不变", "", "为%3$,.2f万元"), CURRENY);
		r61.link(new GrammaticLink("%1$s, "
				+ "该指标反映了保费变化的程度，正值越大越好，反之负值变化越小越好。影响该指标的基础财务指标有保险业务收入(%2$s)，分保收入。", 1, f62));
		
		/////////7. 保费收入份额/////////////////////////////////////////
		BondDocField r71 = e.crtRatField("保费收入份额指标", "RATIO7", 
				new FdFmt("%1$s从%2$.3f上升到%3$.3f", "%1$s从%2$.3f下降到%3$.3f","%1$s为%3$.3f，维持不变", "", "%1$s为%3$.3f"));
		
		/////////8. 流动资金覆盖率/////////////////////////////////////////
		BondDocField r81 = e.crtRatField("流动资金覆盖率指标", "RATIO8", 
				new FdFmt("%1$s从%2$.3f增加至%3$.3f， 说明变现能力最好的资金对成本费用的覆盖程度提高", 
						"%1$s从%2$.3f降低为%3$.3f， 说明变现能力最好的资金对成本费用的覆盖程度减弱",
						"%1$s为%3$.3f，保持不变，说明变现能力最好的资金对成本费用的覆盖程度基本不变", "", 
						"%1$s为%3$.3f，该指标反映变现能力最好的资金对成本费用的覆盖程度，数值越大越好"));
		BondDocField f82 = e.crtFinField("货币资金", "IBS101", 
				new FdFmt("%1$s从%2$,.2f万元上升为%3$,.2f万元", "%1$s从%2$,.2f万元减少到%3$,.2f万元", "%1$s为%3$,.2f万元，维持不变", "", "%1$s为%3$,.2f万元"), CURRENY);
		BondDocField f83 = e.crtFinField("金融性交易资产", "IBS103", 
				new FdFmt("%1$s增加为%3$,.2f万元", "%1$s降低为%3$,.2f万元", "%1$s维持%3$,.2f万元水平", "", "%1$s为%3$,.2f万元"), CURRENY);
		BondDocField f84 = e.crtFinField("总负债", "IBS002", 
				new FdFmt("%1$s增加为%3$,.2f万元", "%1$s降低为%3$,.2f万元", "%1$s维持%3$,.2f万元水平", "", "%1$s为%3$,.2f万元"), CURRENY);
		f82.link(new FormatLink("%1$s，%2$s", "%1$s", "%2$s", "", f83));
		f83.link(new FormatLink("%1$s，%2$s", "%1$s", "%2$s", "", f84));
		r81.link(new FormatLink("%2$s，因此%1$s。", "%1$s。", "", "", f82));

		e.loadData();
		
		String s1  = r11.genText();
		LOG.info(s1);
		
		String s2  = r2.genText();
		LOG.info(s2);

		String s3 = generateR3(r3);
		LOG.info(s3);
		
		String s4 = r41.genText();
		LOG.info(s4);
		
		String s5  = r51.genText();
		LOG.info(s5);
		
		String s6  = r61.genText();
		LOG.info(s6);
		
		String s7 = generateR7(r71);
		LOG.info(s7);
		
		String s8 = r81.genText();
		LOG.info(s8);
		
		BondDocLayout layout = e.createLayout().
				addLine(s1).
				addLine(s2).
				addLine(s3).
				addLine(s4).
				addLine(s5).
				addLine(s6).
				addLine(s7).
				addLine(s8);

		LOG.info(layout.toDoc());
		return layout;
	}
	
	private String generateR7(BondDocField r7)
	{
		String s7  = r7.genText();
		BondDocField f = e.getField("RATIO7");
		Integer position = e.findRatioPositionByIndu(this.issuerId, this.userId, "RATIO7", yearmonth);
		if(position != null) {
			s7 += "，于总行业排名" + position + "位。";
		}
		return s7;
	}
	
	private String generateR3(BondDocField r3)
	{
		String s3  = r3.genText();
		BondDocField f = e.getField("RATIO3");
		
		Integer position = e.findRatioPositionByIndu(this.issuerId, this.userId, "RATIO3", yearmonth);
		if(position != null) {
			s3 += "，于总行业排名" + position + "位。";
		}
		
		Double ratio3 = (Double)f.getLast();
		if (ratio3 != null) {
			if (ratio3 >= 1) {
				s3 += "保监会对偿付能力充足率小于100%的保险公司作为重点监管对象。";
			}
			else if ( 0.7 <= ratio3 && ratio3 < 1 ){
				s3 += "保监会对偿付能力充足率在70%-100%范围的保险公司可要求提出整改方案并限期达到最低偿付能力额度要求，逾期未达到的，"
						+ "可对该公司采取要求增加资本金、责令办理再保险、限制业务范围、限制向股东分红、限制固定资产购置、限制经营费用规模、"
						+ "限制增设分支机构等必要的监管措施，直至其达到最低偿付能力额度要求。";
			}
			else if ( 0.3 <= ratio3 && ratio3 < 0.7 ){
				s3 += "保监会对偿付能力充足率在30%~70%范围的保险公司可要求提出整改方案并限期达到最低偿付能力额度要求，逾期未达到的，"
						+ "可对该公司采取要求增加资本金、责令办理再保险、限制业务范围、限制向股东分红、限制固定资产购置、限制经营费用规模、"
						+ "限制增设分支机构等必要的监管措施，直至其达到最低偿付能力额度要求; 并可责令该公司拍卖不良资产、责令转让保险业务、"
						+ "限制高级管理人员的薪酬水平和在职消费水平、限制公司的商业性广告、责令停止展开新业务等其他措施。";
			}
			else if ( ratio3 < 0.3 ){
				s3 += "保监会对偿付能力充足率小于30%的保险公司可要求提出整改方案并限期达到最低偿付能力额度要求，逾期未达到的，"
						+ "可对该公司采取要求增加资本金、责令办理再保险、限制业务范围、限制向股东分红、限制固定资产购置、限制经营费用规模、"
						+ "限制增设分支机构等必要的监管措施，直至其达到最低偿付能力额度要求; 并可责令该公司拍卖不良资产、责令转让保险业务、"
						+ "限制高级管理人员的薪酬水平和在职消费水平、限制公司的商业性广告、责令停止展开新业务等其他措施； 甚至对保险公司进行接管。";
			}
		}
	
		return s3;
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
		BondDocField r1 = e.crtRatField("净资产收益率", "RATIO1", FdFmt.DEFAULT_SCORE_FMT);
		BondDocField r2 = e.crtRatField("资产总计", "RATIO2", FdFmt.DEFAULT_SCORE_FMT);
		BondDocField r3 = e.crtRatField("偿付能力充足率", "RATIO3", FdFmt.DEFAULT_SCORE_FMT);
		BondDocField r4 = e.crtRatField("赔付率", "RATIO4", FdFmt.DEFAULT_SCORE_FMT);
		BondDocField r5 = e.crtRatField("已赚保费覆盖费用率", "RATIO5", FdFmt.DEFAULT_SCORE_FMT);
		BondDocField r6 = e.crtRatField("保费变化率", "RATIO6", FdFmt.DEFAULT_SCORE_FMT);
		BondDocField r8 = e.crtRatField("流动资金覆盖率", "RATIO8", FdFmt.DEFAULT_SCORE_FMT);
		e.loadData();
		bu.addScore(r1, "盈利能力", "利润总额/所有者权益");
        bu.addScore(r6, "盈利能力", "（本年保险业务收入-上年保险业务收入）/本年保险业务收入");
		bu.addScore(r3, "流动性及资产负债匹配", "实际资本/最低资本");
		bu.addScore(r4, "流动性及资产负债匹配", "赔付支出/保险业务收入");
		bu.addScore(r5, "流动性及资产负债匹配", "营业支出/已赚保费");
		bu.addScore(r8, "流动性及资产负债匹配", "（货币资金+应收利息+交易性金融资产+定期存款+存出保证金）/负债合计");
	    bu.addScore(r2, "规模", "资产总计");
		bu.finish(e.getLastQuarter(), e.getPreQuarter());
		return true;
	}
}
