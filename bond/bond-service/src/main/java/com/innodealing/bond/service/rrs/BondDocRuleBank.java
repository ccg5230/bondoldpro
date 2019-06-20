package com.innodealing.bond.service.rrs;

import static com.innodealing.bond.service.rrs.BondDocGrammer.CURRENY;
import static com.innodealing.bond.service.rrs.BondDocGrammer.RATIO;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.innodealing.bond.service.rrs.BondDocGrammer.FdFmt;
import com.innodealing.bond.service.rrs.BondDocGrammer.FdFmt0;
import com.innodealing.bond.service.rrs.BondDocGrammer.FdFmt3;
import com.innodealing.bond.service.rrs.BondDocGrammer.FormatLink;
import com.innodealing.bond.vo.summary.BondIssDMRatingSummaryVO;
import com.innodealing.bond.vo.summary.BondRatingScoreEntry;
import com.innodealing.bond.vo.summary.IssRatingScoreSummary;
import com.innodealing.exception.BusinessException;

@Service("BondDocRuleBank")
@Scope(value="prototype")
public class BondDocRuleBank implements StrategyRuleI {

	private static final Logger LOG = LoggerFactory.getLogger(BondDocRuleBank.class);
	
	private Long issuerId;
	private Long year;
	private Long quarter;
	
	private IDocEngine e;
	
	public BondDocRuleBank(Long issuerId, Long userid, Long year, Long quarter, IDocEngine e)
	{
		//200022927
		//200040431 兴业银行股份有限公司
		this.issuerId = issuerId;
		this.year = year;
		this.quarter = quarter;
		this.e = e;
	}
	
	BondDocLayout buildDoc()
	{
		e.init(issuerId, year, quarter);
		
		/////////R1 不良贷款率/////////////////////////////////////////
		BondDocField c11 = e.crtFinField("贷款总额", "BTN147", 
				new FdFmt("%1$s为%3$,.2f万元", "%1$s为%3$,.2f万元","%1$s为%3$,.2f万元", "", "%1$s为%3$,.2f万元"), CURRENY);
		BondDocField r11 = e.crtRatField("不良贷款率指标", "RATIO1", 
				new FdFmt("%1$s从%2$.2f上升到%3$.3f", "%1$s从%2$.2f下降到%3$.3f","%1$s为%3$.3f，维持不变", "", "%1$s为%3$.3f"));
		r11.link(new FormatLink("%1$s，其中%2$s。", "%1$s。", "", "", c11));
		
		/////////C2 所有者权益/////////////////////////////////////////
		BondDocField c21 = e.crtFinField("所有者权益", "BBS003", 
				new FdFmt("%1$s从%2$,.2f万元上升到%3$,.2f万元", "%1$s从%2$,.2f万元下降到%3$,.2f万元","%1$s为%3$,.2f万元，维持不变", "", "%1$s为%3$,.2f万元"), CURRENY);
		BondDocField r22 = e.crtRatField("净资产指标", "RATIO2", 
				new FdFmt("%1$s从%2$.3f上升为%3$.3f", "%1$s从%2$.3f下降到%3$.3f", "%1$s为%3$.3f，维持不变", "", "%1$s为%3$.3f"));
		r22.link(new FormatLink("%1$s，%2$s。", "%1$s。", "", "", c21));
		
		/////////R3 利息收入/////////////////////////////////////////
		BondDocField c32 = e.crtFinField("利息收入", "BPL101_1", 
					new FdFmt("%1$s%3$,.2f万元", "%1$s%3$,.2f万元","%1$s为%3$,.2f万元", "", "%1$s为%3$,.2f万元"), CURRENY);
		BondDocField c33 = e.crtFinField("营业收入", "BPL100", 
					new FdFmt("%1$s%3$,.2f万元", "%1$s%3$,.2f万元","%1$s为%3$,.2f万元", "", "%1$s为%3$,.2f万元"), CURRENY);
		BondDocField r31  = e.crtRatField("利息收入占比指标", "RATIO3", 
				new FdFmt("%1$s%3$.3f，较上期增加%4$.3f", "%1$s%3$.3f，较上期降低%4$.3f", "%1$s%3$.3f，较上期维持不变", "", "%1$s%3$.3f"));
		c32.link(new FormatLink("%1$s，%2$s", "%1$s", "%2$s", "", c33));
		r31.link(new FormatLink("当期%2$s，故%1$s。", "%1$s。", "", "", c32));
		
		/////////R4 利息支出能力&非利息收入占比 /////////////////////////////////////////
		BondDocField c41 = e.crtFinField("利润总额", "BPL400", 
				new FdFmt("%1$s%3$,.2f万元", "%1$s%3$,.2f万元","%1$s为%3$,.2f万元", "", "%1$s为%3$,.2f万元"), CURRENY);
		BondDocField c42 = e.crtFinField("利息支出", "BPL101_2", 
				new FdFmt("%1$s%3$,.2f万元", "%1$s%3$,.2f万元","%1$s为%3$,.2f万元", "", "%1$s为%3$,.2f万元"), CURRENY);
		BondDocField r43 = e.crtRatField("利息支出能力指标", "RATIO4", 
				new FdFmt("%1$s上升", "%1$s下降","%1$s不变", "", "%1$s为%3$.3f"));
		c41.link(new FormatLink("%1$s，%2$s。", "%1$s。", "%2$s", "", c42));
		r43.link(new FormatLink("%2$s，因此%1$s。", "%1$s。", "", "", c41));
		
		BondDocField c44 = e.crtFinField("手续费及佣金净收入", "BPL102", 
				new FdFmt("%1$s增加", "%1$s下调","%1$s不变", "", "%1$s为%3$,.2f万元"), CURRENY);
		BondDocField r45 = e.crtRatField("非利息收入占比指标", "RATIO5", 
				new FdFmt("%1$s上升到%3$.3f", "%1$s下降到%3$.3f", "%1$s为%3$.3f，维持上期水平", "", "%1$s为%3$.3f"));
		r45.link(new FormatLink("%2$s，所以%1$s。", "%1$s。", "", "", c44));
		
		/////////R5 存贷款比例///////////////////////
		BondDocField r51  = e.crtRatField("存贷款比例指标", "RATIO6", 
				new FdFmt("%1$s从%2$.3f上升到%3$.3f", "%1$s从%2$.3f下降到%3$.3f", "%1$s为%3$.3f", "", "%1$s为%3$.3f"));
		BondDocField c52 = e.crtFinField("贷款总额", "BTN147", 
				new FdFmt("%1$s%3$,.2f万元，较上期增加%4$,.2f万元 ", "%1$s%3$,.2f万元，较上期降低%4$,.2f万元", "%1$s%3$,.2f万元，较上期基本不变", "", "%1$s%3$,.2f万元"), CURRENY);
		BondDocField c53 = e.crtFinField("拆入资金", "BBS203", 
				new FdFmt("%1$s%3$,.2f万元", "%1$s%3$,.2f万元","%1$s%3$,.2f万元", "", "%1$s%3$,.2f万元"), CURRENY);
		BondDocField c54 = e.crtFinField("交易性金融负债", "BBS204", 
				new FdFmt("%1$s%3$,.2f万元，较上期增加%4$,.2f万元 ", "%1$s%3$,.2f万元，较上期降低%4$,.2f万元", "%1$s%3$,.2f万元，较上期基本不变", "", "%1$s%3$,.2f万元"), CURRENY);
		BondDocField c55 = e.crtFinField("衍生金融负债", "BBS205", 
				new FdFmt("%1$s%3$,.2f万元", "%1$s%3$,.2f万元","%1$s%3$,.2f万元", "", "%1$s%3$,.2f万元"), CURRENY);
		BondDocField c56 = e.crtFinField("卖出回购金融资产款", "BBS206", 
				new FdFmt("%1$s%3$,.2f万元", "%1$s%3$,.2f万元","%1$s%3$,.2f万元", "", "%1$s%3$,.2f万元"), CURRENY);
		BondDocField c57 = e.crtFinField("吸收存款", "BBS207", 
				new FdFmt("%1$s%3$,.2f万元", "%1$s%3$,.2f万元","%1$s%3$,.2f万元", "", "%1$s%3$,.2f万元"), CURRENY);
		c52.link(new FormatLink("当期%1$s；%2$s", "%1$s", "%2$s", "", c53));
		c53.link(new FormatLink("%1$s；%2$s", "%1$s", "%2$s", "", c54));
		c54.link(new FormatLink("%1$s；%2$s", "%1$s", "%2$s", "", c55));
		c55.link(new FormatLink("%1$s；%2$s", "%1$s", "%2$s", "", c56));
		c56.link(new FormatLink("%1$s；%2$s", "%1$s", "%2$s", "", c57));
		r51.link(new FormatLink("%1$s。从财报中可看出，%2$s。", "%1$s。", "", "", c52));

		e.loadData();


		String s1  = r11.genText();
		LOG.info(s1);
		String s2  = r22.genText();
		LOG.info(s2);	
		String s3  = r31.genText();
		LOG.info(s3);
		String s4 = r43.genText() + r45.genText();
		LOG.info(s4);
		String s5  = r51.genText();
		LOG.info(s5);
		
		BondDocLayout layout = e.createLayout().
				addLine(s1).
				addLine(s2).
				addLine(s3).
				addLine(s4).
				addLine(s5);
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
		BondDocField r1 = e.crtRatField("不良贷款率", "RATIO1", FdFmt.DEFAULT_SCORE_FMT);
		BondDocField r2 = e.crtRatField("净资产", "RATIO2", FdFmt.DEFAULT_SCORE_FMT);
		BondDocField r3 = e.crtRatField("利息收入占比", "RATIO3", FdFmt.DEFAULT_SCORE_FMT);
		BondDocField r4 = e.crtRatField("利息支出能力", "RATIO4", FdFmt.DEFAULT_SCORE_FMT);
		BondDocField r5 = e.crtRatField("非利息收入占比", "RATIO5", FdFmt.DEFAULT_SCORE_FMT);
		BondDocField r6 = e.crtRatField("存贷款比例", "RATIO6", FdFmt.DEFAULT_SCORE_FMT);
		e.loadData();
		bu.addScore(r1, "资产质量", "不良贷款/贷款余额");
		bu.addScore(r2, "规模", "所有者权益");
		bu.addScore(r3, "盈利能力", "利息收入/营业收入");
		bu.addScore(r4, "盈利能力", "税前利润/利息支出");
		bu.addScore(r5, "盈利能力", "手续费及佣金净收入/营业收入");
		bu.addScore(r6, "资产流动性", "贷款总额/(同业及其他机构存放款项+拆入资金+交易性金融负债+吸收存款）");;
		bu.finish(e.getLastQuarter(), e.getPreQuarter());
		return true;
	}
	
}
