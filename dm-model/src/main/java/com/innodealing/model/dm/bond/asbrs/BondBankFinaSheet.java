package com.innodealing.model.dm.bond.asbrs;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@IdClass(BondFinaEmbeddedId.class)
@Table(name = "bank_fina_sheet")
public class BondBankFinaSheet implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 业务主键+MD5值（数据校验用）
	 */
    @Column(length=255)
    private String ROW_KEY;
    
	/**
	 * 公司代码
	 */
    @Id
    @Column(length=19)
    private Long COMP_ID;
    
	/**
	 * 报表日期
	 */
    @Id
    @Column(length=10)
	@Temporal(TemporalType.DATE)
    private Date FIN_DATE;
    
	/**
	 * 报表口径
	 */
    @Column(length=1)
    private String FIN_ENTITY;
    
	/**
	 * 报表类型([HR-历史报表;FRPE-悲观预测;FROP-乐观预测;FRAV-平均预测;SA-情景分析)
	 */
    @Column(length=20)
    private String FIN_STATE_TYPE;
    
	/**
	 * 报表涵盖期间
	 */
    @Column(length=10)
    private Integer FIN_PERIOD;
    
	/**
	 * 现金及存放中央银行款项
	 */
    @Column(length=20)
    private BigDecimal BBS101;
    
	/**
	 * 现金
	 */
    @Column(length=20)
    private BigDecimal BBS101_1;
    
	/**
	 * 存放央行款项
	 */
    @Column(length=20)
    private BigDecimal BBS101_2;
    
	/**
	 * 法定存款准备金
	 */
    @Column(length=20)
    private BigDecimal BBS101_2_1;
    
	/**
	 * 超储
	 */
    @Column(length=20)
    private BigDecimal BBS101_2_2;
    
	/**
	 * 存放同业款项
	 */
    @Column(length=20)
    private BigDecimal BBS102;
    
	/**
	 * 贵金属
	 */
    @Column(length=20)
    private BigDecimal BBS103;
    
	/**
	 * 拆出资金
	 */
    @Column(length=20)
    private BigDecimal BBS104;
    
	/**
	 * 交易性金融资产
	 */
    @Column(length=20)
    private BigDecimal BBS105;
    
	/**
	 * 国债及央票
	 */
    @Column(length=20)
    private BigDecimal BBS105_1;
    
	/**
	 * 金融债
	 */
    @Column(length=20)
    private BigDecimal BBS105_2;
    
	/**
	 * 企业债券
	 */
    @Column(length=20)
    private BigDecimal BBS105_3;
    
	/**
	 * 衍生金融资产
	 */
    @Column(length=20)
    private BigDecimal BBS106;
    
	/**
	 * 买入返售金融资产
	 */
    @Column(length=20)
    private BigDecimal BBS107;
    
	/**
	 * 应收利息
	 */
    @Column(length=20)
    private BigDecimal BBS108;
    
	/**
	 * 发放委托贷款及垫款
	 */
    @Column(length=20)
    private BigDecimal BBS109;
    
	/**
	 * 应收融资租赁款
	 */
    @Column(length=20)
    private BigDecimal BBS1101;
    
	/**
	 * 可供出售金融资产
	 */
    @Column(length=20)
    private BigDecimal BBS110;
    
	/**
	 * 国债及央票
	 */
    @Column(length=20)
    private BigDecimal BBS110_1;
    
	/**
	 * 金融债
	 */
    @Column(length=20)
    private BigDecimal BBS110_2;
    
	/**
	 * 企业债券
	 */
    @Column(length=20)
    private BigDecimal BBS110_3;
    
	/**
	 * 持有至到期投资
	 */
    @Column(length=20)
    private BigDecimal BBS111;
    
	/**
	 * 国债及央票
	 */
    @Column(length=20)
    private BigDecimal BBS111_1;
    
	/**
	 * 金融债
	 */
    @Column(length=20)
    private BigDecimal BBS111_2;
    
	/**
	 * 企业债券
	 */
    @Column(length=20)
    private BigDecimal BBS111_3;
    
	/**
	 * 长期股权投资
	 */
    @Column(length=20)
    private BigDecimal BBS112;
    
	/**
	 * 应收款项类投资
	 */
    @Column(length=20)
    private BigDecimal BBS118;
    
	/**
	 * 固定资产
	 */
    @Column(length=20)
    private BigDecimal BBS114;
    
	/**
	 * 在建工程
	 */
    @Column(length=20)
    private BigDecimal BBS1102;
    
	/**
	 * 无形资产
	 */
    @Column(length=20)
    private BigDecimal BBS115;
    
	/**
	 * 商誉
	 */
    @Column(length=20)
    private BigDecimal BBS1103;
    
	/**
	 * 递延所得税资产
	 */
    @Column(length=20)
    private BigDecimal BBS116;
    
	/**
	 * 投资性房地产
	 */
    @Column(length=20)
    private BigDecimal BBS113;
    
	/**
	 * 其他资产
	 */
    @Column(length=20)
    private BigDecimal BBS117;
    
	/**
	 * 资产总计
	 */
    @Column(length=20)
    private BigDecimal BBS001;
    
	/**
	 * 同业及其他金融机构存放款项
	 */
    @Column(length=20)
    private BigDecimal BBS202;
    
	/**
	 * 向中央银行借款
	 */
    @Column(length=20)
    private BigDecimal BBS201;
    
	/**
	 * 拆入资金
	 */
    @Column(length=20)
    private BigDecimal BBS203;
    
	/**
	 * 交易性金融负债
	 */
    @Column(length=20)
    private BigDecimal BBS204;
    
	/**
	 * 衍生金融负债
	 */
    @Column(length=20)
    private BigDecimal BBS205;
    
	/**
	 * 卖出回购金融资产款
	 */
    @Column(length=20)
    private BigDecimal BBS206;
    
	/**
	 * 吸收存款
	 */
    @Column(length=20)
    private BigDecimal BBS207;
    
	/**
	 * 活期存款
	 */
    @Column(length=20)
    private BigDecimal BBS207_1;
    
	/**
	 * 公司客户
	 */
    @Column(length=20)
    private BigDecimal BBS207_1_1;
    
	/**
	 * 个人客户
	 */
    @Column(length=20)
    private BigDecimal BBS207_1_2;
    
	/**
	 * 定期存款
	 */
    @Column(length=20)
    private BigDecimal BBS207_2;
    
	/**
	 * 公司客户
	 */
    @Column(length=20)
    private BigDecimal BBS207_2_1;
    
	/**
	 * 个人客户
	 */
    @Column(length=20)
    private BigDecimal BBS207_2_2;
    
	/**
	 * 保证金存款
	 */
    @Column(length=20)
    private BigDecimal BBS207_2_3;
    
	/**
	 * 应付职工薪酬
	 */
    @Column(length=20)
    private BigDecimal BBS208;
    
	/**
	 * 应交税费
	 */
    @Column(length=20)
    private BigDecimal BBS209;
    
	/**
	 * 应付利息
	 */
    @Column(length=20)
    private BigDecimal BBS210;
    
	/**
	 * 应付债券
	 */
    @Column(length=20)
    private BigDecimal BBS212;
    
	/**
	 * 递延所得税负债
	 */
    @Column(length=20)
    private BigDecimal BBS213;
    
	/**
	 * 预计负债
	 */
    @Column(length=20)
    private BigDecimal BBS211;
    
	/**
	 * 其他负债
	 */
    @Column(length=20)
    private BigDecimal BBS214;
    
	/**
	 * 负债合计
	 */
    @Column(length=20)
    private BigDecimal BBS002;
    
	/**
	 * 实收资本(或股本)
	 */
    @Column(length=20)
    private BigDecimal BBS401;
    
	/**
	 * 资本公积
	 */
    @Column(length=20)
    private BigDecimal BBS402;
    
	/**
	 * 减:库存股
	 */
    @Column(length=20)
    private BigDecimal BBS402_1;
    
	/**
	 * 盈余公积
	 */
    @Column(length=20)
    private BigDecimal BBS403;
    
	/**
	 * 未分配利润
	 */
    @Column(length=20)
    private BigDecimal BBS405;
    
	/**
	 * 一般风险准备
	 */
    @Column(length=20)
    private BigDecimal BBS404;
    
	/**
	 * 外币报表折算差额
	 */
    @Column(length=20)
    private BigDecimal BBS408;
    
	/**
	 * 少数股东权益
	 */
    @Column(length=20)
    private BigDecimal BBS409;
    
	/**
	 * 归属于母公司所有者权益合计
	 */
    @Column(length=20)
    private BigDecimal BBS406;
    
	/**
	 * 所有者权益(或股东权益)合计
	 */
    @Column(length=20)
    private BigDecimal BBS003;
    
	/**
	 * 负债和所有者权益(或股东权益)总计
	 */
    @Column(length=20)
    private BigDecimal BBS004;
    
	/**
	 * 营业收入
	 */
    @Column(length=20)
    private BigDecimal BPL100;
    
	/**
	 * 利息净收入
	 */
    @Column(length=20)
    private BigDecimal BPL101;
    
	/**
	 * 利息收入
	 */
    @Column(length=20)
    private BigDecimal BPL101_1;
    
	/**
	 * 贷款
	 */
    @Column(length=20)
    private BigDecimal BPL101_1_1;
    
	/**
	 * 公司贷款
	 */
    @Column(length=20)
    private BigDecimal BPL101_1_1_1;
    
	/**
	 * 贴现
	 */
    @Column(length=20)
    private BigDecimal BPL101_1_1_2;
    
	/**
	 * 个人贷款
	 */
    @Column(length=20)
    private BigDecimal BPL101_1_1_3;
    
	/**
	 * 存放央行
	 */
    @Column(length=20)
    private BigDecimal BPL101_1_2;
    
	/**
	 * 存放、拆放同业及买入返售等
	 */
    @Column(length=20)
    private BigDecimal BPL101_1_3;
    
	/**
	 * 债券投资
	 */
    @Column(length=20)
    private BigDecimal BPL101_1_4;
    
	/**
	 * 利息支出
	 */
    @Column(length=20)
    private BigDecimal BPL101_2;
    
	/**
	 * 客户存款
	 */
    @Column(length=20)
    private BigDecimal BPL101_2_1;
    
	/**
	 * 同业存放和拆入及卖出回购
	 */
    @Column(length=20)
    private BigDecimal BPL101_2_2;
    
	/**
	 * 应付债券
	 */
    @Column(length=20)
    private BigDecimal BPL101_2_3;
    
	/**
	 * 手续费及佣金净收入
	 */
    @Column(length=20)
    private BigDecimal BPL102;
    
	/**
	 * 手续费及佣金收入
	 */
    @Column(length=20)
    private BigDecimal BPL102_1;
    
	/**
	 * 结算与清算业务
	 */
    @Column(length=20)
    private BigDecimal BPL102_1_1;
    
	/**
	 * 银行卡业务
	 */
    @Column(length=20)
    private BigDecimal BPL102_1_2;
    
	/**
	 * 代理业务
	 */
    @Column(length=20)
    private BigDecimal BPL102_1_3;
    
	/**
	 * 信贷承诺业务
	 */
    @Column(length=20)
    private BigDecimal BPL102_1_4;
    
	/**
	 * 咨询顾问业务
	 */
    @Column(length=20)
    private BigDecimal BPL102_1_5;
    
	/**
	 * 其他
	 */
    @Column(length=20)
    private BigDecimal BPL102_1_6;
    
	/**
	 * 手续费及佣金支出
	 */
    @Column(length=20)
    private BigDecimal BPL102_2;
    
	/**
	 * 投资收益(损失以“－”号填列)
	 */
    @Column(length=20)
    private BigDecimal BPL103;
    
	/**
	 * 其中:对联营企业和合营企业的投资收益
	 */
    @Column(length=20)
    private BigDecimal BPL103_1;
    
	/**
	 * 公允价值变动收益(损失以“－”号填列)
	 */
    @Column(length=20)
    private BigDecimal BPL104;
    
	/**
	 * 汇兑收益(损失以“-”号填列)
	 */
    @Column(length=20)
    private BigDecimal BPL105;
    
	/**
	 * 其他业务收入
	 */
    @Column(length=20)
    private BigDecimal BPL106;
    
	/**
	 * 营业支出
	 */
    @Column(length=20)
    private BigDecimal BPL200;
    
	/**
	 * 营业税金及附加
	 */
    @Column(length=20)
    private BigDecimal BPL201;
    
	/**
	 * 业务及管理费
	 */
    @Column(length=20)
    private BigDecimal BPL202;
    
	/**
	 * 资产减值损失
	 */
    @Column(length=20)
    private BigDecimal BPL203;
    
	/**
	 * 其他业务成本
	 */
    @Column(length=20)
    private BigDecimal BPL204;
    
	/**
	 * 营业利润(亏损以“－”号填列)
	 */
    @Column(length=20)
    private BigDecimal BPL300;
    
	/**
	 * 营业外收入
	 */
    @Column(length=20)
    private BigDecimal BPL301;
    
	/**
	 * 营业外支出
	 */
    @Column(length=20)
    private BigDecimal BPL302;
    
	/**
	 * 利润总额(亏损总额以“－”号填列)
	 */
    @Column(length=20)
    private BigDecimal BPL400;
    
	/**
	 * 所得税费用
	 */
    @Column(length=20)
    private BigDecimal BPL401;
    
	/**
	 * 净利润(净亏损以“－”号填列)
	 */
    @Column(length=20)
    private BigDecimal BPL500;
    
	/**
	 * 少数股东损益
	 */
    @Column(length=20)
    private BigDecimal BPL501;
    
	/**
	 * 归属于母公司所有者(或股东)的净利润
	 */
    @Column(length=20)
    private BigDecimal BPL502;
    
	/**
	 * 基本每股收益
	 */
    @Column(length=20)
    private BigDecimal BPL503;
    
	/**
	 * 稀释每股收益
	 */
    @Column(length=20)
    private BigDecimal BPL504;
    
	/**
	 * 其他综合收益
	 */
    @Column(length=20)
    private BigDecimal BPL505;
    
	/**
	 * 综合收益总额
	 */
    @Column(length=20)
    private BigDecimal BPL506;
    
	/**
	 * 归属于母公司所有者(或股东)的综合收益总额
	 */
    @Column(length=20)
    private BigDecimal BPL507;
    
	/**
	 * 归属于少数股东的综合收益总额
	 */
    @Column(length=20)
    private BigDecimal BPL508;
    
	/**
	 * 客户存款和同业存放款项净增加额
	 */
    @Column(length=20)
    private BigDecimal BCF101;
    
	/**
	 * 向中央银行借款净增加额
	 */
    @Column(length=20)
    private BigDecimal BCF102;
    
	/**
	 * 向其他金融机构拆入资金净增加额
	 */
    @Column(length=20)
    private BigDecimal BCF103;
    
	/**
	 * 发放贷款及垫款净减少额
	 */
    @Column(length=20)
    private BigDecimal BCF104;
    
	/**
	 * 存放中央银行和同业款项净减少额
	 */
    @Column(length=20)
    private BigDecimal BCF105;
    
	/**
	 * 向其他金融机构拆出资金净减少额
	 */
    @Column(length=20)
    private BigDecimal BCF106;
    
	/**
	 * 收取利息、手续费及佣金的现金
	 */
    @Column(length=20)
    private BigDecimal BCF107;
    
	/**
	 * 收到其他与经营活动有关的现金
	 */
    @Column(length=20)
    private BigDecimal BCF108;
    
	/**
	 * 经营活动现金流入小计
	 */
    @Column(length=20)
    private BigDecimal BCF100;
    
	/**
	 * 客户存款和同业存放款项净减少额
	 */
    @Column(length=20)
    private BigDecimal BCF201;
    
	/**
	 * 向中央银行借款净减少额
	 */
    @Column(length=20)
    private BigDecimal BCF202;
    
	/**
	 * 向其他金融机构拆入资金净减少额
	 */
    @Column(length=20)
    private BigDecimal BCF203;
    
	/**
	 * 客户贷款及垫款净增加额
	 */
    @Column(length=20)
    private BigDecimal BCF204;
    
	/**
	 * 存放中央银行和同业款项净增加额
	 */
    @Column(length=20)
    private BigDecimal BCF205;
    
	/**
	 * 向其他金融机构拆出资金净增加额
	 */
    @Column(length=20)
    private BigDecimal BCF206;
    
	/**
	 * 支付利息、手续费及佣金的现金
	 */
    @Column(length=20)
    private BigDecimal BCF207;
    
	/**
	 * 支付给职工以及为职工支付的现金
	 */
    @Column(length=20)
    private BigDecimal BCF208;
    
	/**
	 * 支付的各项税费
	 */
    @Column(length=20)
    private BigDecimal BCF209;
    
	/**
	 * 支付其他与经营活动有关的现金
	 */
    @Column(length=20)
    private BigDecimal BCF210;
    
	/**
	 * 经营活动现金流出小计
	 */
    @Column(length=20)
    private BigDecimal BCF200;
    
	/**
	 * 经营活动产生的现金流量净额
	 */
    @Column(length=20)
    private BigDecimal BCF001;
    
	/**
	 * 收回投资收到的现金
	 */
    @Column(length=20)
    private BigDecimal BCF301;
    
	/**
	 * 取得投资收益收到的现金
	 */
    @Column(length=20)
    private BigDecimal BCF302;
    
	/**
	 * 处置固定资产、无形资产和其他长期资产收回的现金净额
	 */
    @Column(length=20)
    private BigDecimal BCF303;
    
	/**
	 * 处置子公司及其他营业单位收到的现金净额
	 */
    @Column(length=20)
    private BigDecimal BCF304;
    
	/**
	 * 收到其他与投资活动有关的现金
	 */
    @Column(length=20)
    private BigDecimal BCF305;
    
	/**
	 * 投资活动现金流入小计
	 */
    @Column(length=20)
    private BigDecimal BCF300;
    
	/**
	 * 投资支付的现金
	 */
    @Column(length=20)
    private BigDecimal BCF401;
    
	/**
	 * 购建固定资产、无形资产和其他长期资产支付的现金
	 */
    @Column(length=20)
    private BigDecimal BCF402;
    
	/**
	 * 取得子公司及其他营业单位支付的现金净额
	 */
    @Column(length=20)
    private BigDecimal BCF403;
    
	/**
	 * 支付其他与投资活动有关的现金
	 */
    @Column(length=20)
    private BigDecimal BCF404;
    
	/**
	 * 投资活动现金流出小计
	 */
    @Column(length=20)
    private BigDecimal BCF400;
    
	/**
	 * 投资活动产生的现金流量净额
	 */
    @Column(length=20)
    private BigDecimal BCF002;
    
	/**
	 * 吸收投资收到的现金
	 */
    @Column(length=20)
    private BigDecimal BCF501;
    
	/**
	 * 其中:子公司吸收少数股东投资收到的现金
	 */
    @Column(length=20)
    private BigDecimal BCF502;
    
	/**
	 * 发行债券收到的现金
	 */
    @Column(length=20)
    private BigDecimal BCF503;
    
	/**
	 * 收到其他与筹资活动有关的现金
	 */
    @Column(length=20)
    private BigDecimal BCF504;
    
	/**
	 * 筹资活动现金流入小计
	 */
    @Column(length=20)
    private BigDecimal BCF500;
    
	/**
	 * 偿还债务支付的现金
	 */
    @Column(length=20)
    private BigDecimal BCF601;
    
	/**
	 * 分配股利、利润或偿付利息支付的现金
	 */
    @Column(length=20)
    private BigDecimal BCF602;
    
	/**
	 * 其中:子公司支付给少数股东的股利、利润
	 */
    @Column(length=20)
    private BigDecimal BCF603;
    
	/**
	 * 支付其他与筹资活动有关的现金
	 */
    @Column(length=20)
    private BigDecimal BCF604;
    
	/**
	 * 筹资活动现金流出小计
	 */
    @Column(length=20)
    private BigDecimal BCF600;
    
	/**
	 * 筹资活动产生的现金流量净额
	 */
    @Column(length=20)
    private BigDecimal BCF003;
    
	/**
	 * 汇率变动对现金及现金等价物的影响
	 */
    @Column(length=20)
    private BigDecimal BCF701;
    
	/**
	 * 现金及现金等价物净增加额
	 */
    @Column(length=20)
    private BigDecimal BCF004;
    
	/**
	 * 加:期初现金及现金等价物余额
	 */
    @Column(length=20)
    private BigDecimal BCF801;
    
	/**
	 * 期末现金及现金等价物余额
	 */
    @Column(length=20)
    private BigDecimal BCF005;
    
	/**
	 * 净利润
	 */
    @Column(length=20)
    private BigDecimal BCF901;
    
	/**
	 * 加:资产减值准备
	 */
    @Column(length=20)
    private BigDecimal BCF902;
    
	/**
	 * 固定资产折旧、油气资产折耗、生产性生物资产折旧
	 */
    @Column(length=20)
    private BigDecimal BCF903;
    
	/**
	 * 无形资产摊销
	 */
    @Column(length=20)
    private BigDecimal BCF904;
    
	/**
	 * 长期待摊费用摊销
	 */
    @Column(length=20)
    private BigDecimal BCF905;
    
	/**
	 * 待摊费用减少(减:增加)
	 */
    @Column(length=20)
    private BigDecimal BCF906;
    
	/**
	 * 预提费用增加(减:减少)
	 */
    @Column(length=20)
    private BigDecimal BCF907;
    
	/**
	 * 处置固定资产、无形资产和其他长期资产的损失
	 */
    @Column(length=20)
    private BigDecimal BCF908;
    
	/**
	 * 固定资产报废损失
	 */
    @Column(length=20)
    private BigDecimal BCF909;
    
	/**
	 * 公允价值变动损失
	 */
    @Column(length=20)
    private BigDecimal BCF910;
    
	/**
	 * 财务费用
	 */
    @Column(length=20)
    private BigDecimal BCF911;
    
	/**
	 * 投资损失
	 */
    @Column(length=20)
    private BigDecimal BCF912;
    
	/**
	 * 递延所得税资产减少
	 */
    @Column(length=20)
    private BigDecimal BCF913;
    
	/**
	 * 递延所得税负债增加
	 */
    @Column(length=20)
    private BigDecimal BCF914;
    
	/**
	 * 存货的减少
	 */
    @Column(length=20)
    private BigDecimal BCF915;
    
	/**
	 * 经营性应收项目的减少
	 */
    @Column(length=20)
    private BigDecimal BCF916;
    
	/**
	 * 经营性应付项目的增加
	 */
    @Column(length=20)
    private BigDecimal BCF917;
    
	/**
	 * 其他
	 */
    @Column(length=20)
    private BigDecimal BCF918;
    
	/**
	 * (间接)经营活动产生的现金流量净额
	 */
    @Column(length=20)
    private BigDecimal BCF919;
    
	/**
	 * 债务转为资本
	 */
    @Column(length=20)
    private BigDecimal BCF920;
    
	/**
	 * 一年内到期的可转换公司债券
	 */
    @Column(length=20)
    private BigDecimal BCF921;
    
	/**
	 * 融资租入固定资产
	 */
    @Column(length=20)
    private BigDecimal BCF922;
    
	/**
	 * 现金的期末余额
	 */
    @Column(length=20)
    private BigDecimal BCF923;
    
	/**
	 * 减:现金的期初余额
	 */
    @Column(length=20)
    private BigDecimal BCF924;
    
	/**
	 * 加:现金等价物的期末余额
	 */
    @Column(length=20)
    private BigDecimal BCF925;
    
	/**
	 * 减:现金等价物的期初余额
	 */
    @Column(length=20)
    private BigDecimal BCF926;
    
	/**
	 * (间接)现金及现金等价物净增加额
	 */
    @Column(length=20)
    private BigDecimal BCF927;
    
	/**
	 * 最大单一客户贷款比例(%)
	 */
    @Column(length=20)
    private BigDecimal BTN101;
    
	/**
	 * 最大十家客户贷款比例(%)
	 */
    @Column(length=20)
    private BigDecimal BTN102;
    
	/**
	 * 资本充足率(%)
	 */
    @Column(length=20)
    private BigDecimal BTN103;
    
	/**
	 * 核心资本充足率(%)
	 */
    @Column(length=20)
    private BigDecimal BTN104;
    
	/**
	 * 对公贷款(不含贴现)
	 */
    @Column(length=20)
    private BigDecimal BTN105;
    
	/**
	 * 个人贷款
	 */
    @Column(length=20)
    private BigDecimal BTN106;
    
	/**
	 * 票据贴现
	 */
    @Column(length=20)
    private BigDecimal BTN107;
    
	/**
	 * 法定存款准备金
	 */
    @Column(length=20)
    private BigDecimal BTN108;
    
	/**
	 * 超额存款准备金
	 */
    @Column(length=20)
    private BigDecimal BTN109;
    
	/**
	 * 对公贷款(不含贴现)/贷款(%)
	 */
    @Column(length=20)
    private BigDecimal BTN110;
    
	/**
	 * 个人贷款/贷款(%)
	 */
    @Column(length=20)
    private BigDecimal BTN111;
    
	/**
	 * 票据/贷款(%)
	 */
    @Column(length=20)
    private BigDecimal BTN112;
    
	/**
	 * 计息负债合计
	 */
    @Column(length=20)
    private BigDecimal BTN113;
    
	/**
	 * 正常类贷款
	 */
    @Column(length=20)
    private BigDecimal BTN114;
    
	/**
	 * 关注类贷款
	 */
    @Column(length=20)
    private BigDecimal BTN115;
    
	/**
	 * 次级类贷款
	 */
    @Column(length=20)
    private BigDecimal BTN116;
    
	/**
	 * 可疑类贷款
	 */
    @Column(length=20)
    private BigDecimal BTN117;
    
	/**
	 * 损失类贷款
	 */
    @Column(length=20)
    private BigDecimal BTN118;
    
	/**
	 * 逾期贷款
	 */
    @Column(length=20)
    private BigDecimal BTN119;
    
	/**
	 * 不良贷款率(%)
	 */
    @Column(length=20)
    private BigDecimal BTN120;
    
	/**
	 * 核心资本
	 */
    @Column(length=20)
    private BigDecimal BTN121;
    
	/**
	 * 附属资本
	 */
    @Column(length=20)
    private BigDecimal BTN122;
    
	/**
	 * 资本净额
	 */
    @Column(length=20)
    private BigDecimal BTN123;
    
	/**
	 * 加权风险资产净额
	 */
    @Column(length=20)
    private BigDecimal BTN124;
    
	/**
	 * 股利分配
	 */
    @Column(length=20)
    private BigDecimal BTN125;
    
	/**
	 * 短期贷款
	 */
    @Column(length=20)
    private BigDecimal BTN126;
    
	/**
	 * 中长期贷款
	 */
    @Column(length=20)
    private BigDecimal BTN127;
    
	/**
	 * 流动性比率(%)
	 */
    @Column(length=20)
    private BigDecimal BTN128;
    
	/**
	 * 人均总资产
	 */
    @Column(length=20)
    private BigDecimal BTN129;
    
	/**
	 * 人均经营收入
	 */
    @Column(length=20)
    private BigDecimal BTN130;
    
	/**
	 * 人均拨备前利润
	 */
    @Column(length=20)
    private BigDecimal BTN131;
    
	/**
	 * 单位网点总资产
	 */
    @Column(length=20)
    private BigDecimal BTN132;
    
	/**
	 * 单位网点收入
	 */
    @Column(length=20)
    private BigDecimal BTN133;
    
	/**
	 * 单位网点拨备前利润
	 */
    @Column(length=20)
    private BigDecimal BTN134;
    
	/**
	 * 流动性覆盖比率LCR(%)
	 */
    @Column(length=20)
    private BigDecimal BTN135;
    
	/**
	 * 净稳定资金比例NSFR(%)
	 */
    @Column(length=20)
    private BigDecimal BTN136;
    
	/**
	 * 计息负债规模-其他
	 */
    @Column(length=20)
    private BigDecimal BTN137;
    
	/**
	 * 正常类贷款迁徙率(%)
	 */
    @Column(length=20)
    private BigDecimal BTN138;
    
	/**
	 * 关注类贷款迁徙率(%)
	 */
    @Column(length=20)
    private BigDecimal BTN139;
    
	/**
	 * 次级类贷款迁徙率(%)
	 */
    @Column(length=20)
    private BigDecimal BTN140;
    
	/**
	 * 可疑类贷款迁徙率(%)
	 */
    @Column(length=20)
    private BigDecimal BTN141;
    
	/**
	 * NPL期初余额
	 */
    @Column(length=20)
    private BigDecimal BTN142;
    
	/**
	 * 本期核销额
	 */
    @Column(length=20)
    private BigDecimal BTN143;
    
	/**
	 * 本期净转出
	 */
    @Column(length=20)
    private BigDecimal BTN144;
    
	/**
	 * 员工总数（人）
	 */
    @Column(length=20)
    private BigDecimal BTN145;
    
	/**
	 * 分支机构数量（个）
	 */
    @Column(length=20)
    private BigDecimal BTN146;
    
	/**
	 * 贷款总额
	 */
    @Column(length=20)
    private BigDecimal BTN147;
    
	/**
	 * 贷款损失准备总计
	 */
    @Column(length=20)
    private BigDecimal BTN148;
    
	/**
	 * 
	 */
    @Column(nullable=false, length=19)
	@Temporal(TemporalType.TIMESTAMP)
    private Date last_update_timestamp;
    
    @Transient
    private Date END_DATE;
    @Transient
    private Long COM_UNI_CODE;
    @Transient
    private Date CCXEID;

	public String getROW_KEY() {
		return ROW_KEY;
	}

	public void setROW_KEY(String rOW_KEY) {
		ROW_KEY = rOW_KEY;
	}

	public Long getCOMP_ID() {
		return COMP_ID;
	}

	public void setCOMP_ID(Long cOMP_ID) {
		COMP_ID = cOMP_ID;
	}

	public Date getFIN_DATE() {
		return FIN_DATE;
	}

	public void setFIN_DATE(Date fIN_DATE) {
		FIN_DATE = fIN_DATE;
	}

	public String getFIN_ENTITY() {
		return FIN_ENTITY;
	}

	public void setFIN_ENTITY(String fIN_ENTITY) {
		FIN_ENTITY = fIN_ENTITY;
	}

	public String getFIN_STATE_TYPE() {
		return FIN_STATE_TYPE;
	}

	public void setFIN_STATE_TYPE(String fIN_STATE_TYPE) {
		FIN_STATE_TYPE = fIN_STATE_TYPE;
	}

	public Integer getFIN_PERIOD() {
		return FIN_PERIOD;
	}

	public void setFIN_PERIOD(Integer fIN_PERIOD) {
		FIN_PERIOD = fIN_PERIOD;
	}

	public BigDecimal getBBS101() {
		return BBS101;
	}

	public void setBBS101(BigDecimal bBS101) {
		BBS101 = bBS101;
	}

	public BigDecimal getBBS101_1() {
		return BBS101_1;
	}

	public void setBBS101_1(BigDecimal bBS101_1) {
		BBS101_1 = bBS101_1;
	}

	public BigDecimal getBBS101_2() {
		return BBS101_2;
	}

	public void setBBS101_2(BigDecimal bBS101_2) {
		BBS101_2 = bBS101_2;
	}

	public BigDecimal getBBS101_2_1() {
		return BBS101_2_1;
	}

	public void setBBS101_2_1(BigDecimal bBS101_2_1) {
		BBS101_2_1 = bBS101_2_1;
	}

	public BigDecimal getBBS101_2_2() {
		return BBS101_2_2;
	}

	public void setBBS101_2_2(BigDecimal bBS101_2_2) {
		BBS101_2_2 = bBS101_2_2;
	}

	public BigDecimal getBBS102() {
		return BBS102;
	}

	public void setBBS102(BigDecimal bBS102) {
		BBS102 = bBS102;
	}

	public BigDecimal getBBS103() {
		return BBS103;
	}

	public void setBBS103(BigDecimal bBS103) {
		BBS103 = bBS103;
	}

	public BigDecimal getBBS104() {
		return BBS104;
	}

	public void setBBS104(BigDecimal bBS104) {
		BBS104 = bBS104;
	}

	public BigDecimal getBBS105() {
		return BBS105;
	}

	public void setBBS105(BigDecimal bBS105) {
		BBS105 = bBS105;
	}

	public BigDecimal getBBS105_1() {
		return BBS105_1;
	}

	public void setBBS105_1(BigDecimal bBS105_1) {
		BBS105_1 = bBS105_1;
	}

	public BigDecimal getBBS105_2() {
		return BBS105_2;
	}

	public void setBBS105_2(BigDecimal bBS105_2) {
		BBS105_2 = bBS105_2;
	}

	public BigDecimal getBBS105_3() {
		return BBS105_3;
	}

	public void setBBS105_3(BigDecimal bBS105_3) {
		BBS105_3 = bBS105_3;
	}

	public BigDecimal getBBS106() {
		return BBS106;
	}

	public void setBBS106(BigDecimal bBS106) {
		BBS106 = bBS106;
	}

	public BigDecimal getBBS107() {
		return BBS107;
	}

	public void setBBS107(BigDecimal bBS107) {
		BBS107 = bBS107;
	}

	public BigDecimal getBBS108() {
		return BBS108;
	}

	public void setBBS108(BigDecimal bBS108) {
		BBS108 = bBS108;
	}

	public BigDecimal getBBS109() {
		return BBS109;
	}

	public void setBBS109(BigDecimal bBS109) {
		BBS109 = bBS109;
	}

	public BigDecimal getBBS1101() {
		return BBS1101;
	}

	public void setBBS1101(BigDecimal bBS1101) {
		BBS1101 = bBS1101;
	}

	public BigDecimal getBBS110() {
		return BBS110;
	}

	public void setBBS110(BigDecimal bBS110) {
		BBS110 = bBS110;
	}

	public BigDecimal getBBS110_1() {
		return BBS110_1;
	}

	public void setBBS110_1(BigDecimal bBS110_1) {
		BBS110_1 = bBS110_1;
	}

	public BigDecimal getBBS110_2() {
		return BBS110_2;
	}

	public void setBBS110_2(BigDecimal bBS110_2) {
		BBS110_2 = bBS110_2;
	}

	public BigDecimal getBBS110_3() {
		return BBS110_3;
	}

	public void setBBS110_3(BigDecimal bBS110_3) {
		BBS110_3 = bBS110_3;
	}

	public BigDecimal getBBS111() {
		return BBS111;
	}

	public void setBBS111(BigDecimal bBS111) {
		BBS111 = bBS111;
	}

	public BigDecimal getBBS111_1() {
		return BBS111_1;
	}

	public void setBBS111_1(BigDecimal bBS111_1) {
		BBS111_1 = bBS111_1;
	}

	public BigDecimal getBBS111_2() {
		return BBS111_2;
	}

	public void setBBS111_2(BigDecimal bBS111_2) {
		BBS111_2 = bBS111_2;
	}

	public BigDecimal getBBS111_3() {
		return BBS111_3;
	}

	public void setBBS111_3(BigDecimal bBS111_3) {
		BBS111_3 = bBS111_3;
	}

	public BigDecimal getBBS112() {
		return BBS112;
	}

	public void setBBS112(BigDecimal bBS112) {
		BBS112 = bBS112;
	}

	public BigDecimal getBBS118() {
		return BBS118;
	}

	public void setBBS118(BigDecimal bBS118) {
		BBS118 = bBS118;
	}

	public BigDecimal getBBS114() {
		return BBS114;
	}

	public void setBBS114(BigDecimal bBS114) {
		BBS114 = bBS114;
	}

	public BigDecimal getBBS1102() {
		return BBS1102;
	}

	public void setBBS1102(BigDecimal bBS1102) {
		BBS1102 = bBS1102;
	}

	public BigDecimal getBBS115() {
		return BBS115;
	}

	public void setBBS115(BigDecimal bBS115) {
		BBS115 = bBS115;
	}

	public BigDecimal getBBS1103() {
		return BBS1103;
	}

	public void setBBS1103(BigDecimal bBS1103) {
		BBS1103 = bBS1103;
	}

	public BigDecimal getBBS116() {
		return BBS116;
	}

	public void setBBS116(BigDecimal bBS116) {
		BBS116 = bBS116;
	}

	public BigDecimal getBBS113() {
		return BBS113;
	}

	public void setBBS113(BigDecimal bBS113) {
		BBS113 = bBS113;
	}

	public BigDecimal getBBS117() {
		return BBS117;
	}

	public void setBBS117(BigDecimal bBS117) {
		BBS117 = bBS117;
	}

	public BigDecimal getBBS001() {
		return BBS001;
	}

	public void setBBS001(BigDecimal bBS001) {
		BBS001 = bBS001;
	}

	public BigDecimal getBBS202() {
		return BBS202;
	}

	public void setBBS202(BigDecimal bBS202) {
		BBS202 = bBS202;
	}

	public BigDecimal getBBS201() {
		return BBS201;
	}

	public void setBBS201(BigDecimal bBS201) {
		BBS201 = bBS201;
	}

	public BigDecimal getBBS203() {
		return BBS203;
	}

	public void setBBS203(BigDecimal bBS203) {
		BBS203 = bBS203;
	}

	public BigDecimal getBBS204() {
		return BBS204;
	}

	public void setBBS204(BigDecimal bBS204) {
		BBS204 = bBS204;
	}

	public BigDecimal getBBS205() {
		return BBS205;
	}

	public void setBBS205(BigDecimal bBS205) {
		BBS205 = bBS205;
	}

	public BigDecimal getBBS206() {
		return BBS206;
	}

	public void setBBS206(BigDecimal bBS206) {
		BBS206 = bBS206;
	}

	public BigDecimal getBBS207() {
		return BBS207;
	}

	public void setBBS207(BigDecimal bBS207) {
		BBS207 = bBS207;
	}

	public BigDecimal getBBS207_1() {
		return BBS207_1;
	}

	public void setBBS207_1(BigDecimal bBS207_1) {
		BBS207_1 = bBS207_1;
	}

	public BigDecimal getBBS207_1_1() {
		return BBS207_1_1;
	}

	public void setBBS207_1_1(BigDecimal bBS207_1_1) {
		BBS207_1_1 = bBS207_1_1;
	}

	public BigDecimal getBBS207_1_2() {
		return BBS207_1_2;
	}

	public void setBBS207_1_2(BigDecimal bBS207_1_2) {
		BBS207_1_2 = bBS207_1_2;
	}

	public BigDecimal getBBS207_2() {
		return BBS207_2;
	}

	public void setBBS207_2(BigDecimal bBS207_2) {
		BBS207_2 = bBS207_2;
	}

	public BigDecimal getBBS207_2_1() {
		return BBS207_2_1;
	}

	public void setBBS207_2_1(BigDecimal bBS207_2_1) {
		BBS207_2_1 = bBS207_2_1;
	}

	public BigDecimal getBBS207_2_2() {
		return BBS207_2_2;
	}

	public void setBBS207_2_2(BigDecimal bBS207_2_2) {
		BBS207_2_2 = bBS207_2_2;
	}

	public BigDecimal getBBS207_2_3() {
		return BBS207_2_3;
	}

	public void setBBS207_2_3(BigDecimal bBS207_2_3) {
		BBS207_2_3 = bBS207_2_3;
	}

	public BigDecimal getBBS208() {
		return BBS208;
	}

	public void setBBS208(BigDecimal bBS208) {
		BBS208 = bBS208;
	}

	public BigDecimal getBBS209() {
		return BBS209;
	}

	public void setBBS209(BigDecimal bBS209) {
		BBS209 = bBS209;
	}

	public BigDecimal getBBS210() {
		return BBS210;
	}

	public void setBBS210(BigDecimal bBS210) {
		BBS210 = bBS210;
	}

	public BigDecimal getBBS212() {
		return BBS212;
	}

	public void setBBS212(BigDecimal bBS212) {
		BBS212 = bBS212;
	}

	public BigDecimal getBBS213() {
		return BBS213;
	}

	public void setBBS213(BigDecimal bBS213) {
		BBS213 = bBS213;
	}

	public BigDecimal getBBS211() {
		return BBS211;
	}

	public void setBBS211(BigDecimal bBS211) {
		BBS211 = bBS211;
	}

	public BigDecimal getBBS214() {
		return BBS214;
	}

	public void setBBS214(BigDecimal bBS214) {
		BBS214 = bBS214;
	}

	public BigDecimal getBBS002() {
		return BBS002;
	}

	public void setBBS002(BigDecimal bBS002) {
		BBS002 = bBS002;
	}

	public BigDecimal getBBS401() {
		return BBS401;
	}

	public void setBBS401(BigDecimal bBS401) {
		BBS401 = bBS401;
	}

	public BigDecimal getBBS402() {
		return BBS402;
	}

	public void setBBS402(BigDecimal bBS402) {
		BBS402 = bBS402;
	}

	public BigDecimal getBBS402_1() {
		return BBS402_1;
	}

	public void setBBS402_1(BigDecimal bBS402_1) {
		BBS402_1 = bBS402_1;
	}

	public BigDecimal getBBS403() {
		return BBS403;
	}

	public void setBBS403(BigDecimal bBS403) {
		BBS403 = bBS403;
	}

	public BigDecimal getBBS405() {
		return BBS405;
	}

	public void setBBS405(BigDecimal bBS405) {
		BBS405 = bBS405;
	}

	public BigDecimal getBBS404() {
		return BBS404;
	}

	public void setBBS404(BigDecimal bBS404) {
		BBS404 = bBS404;
	}

	public BigDecimal getBBS408() {
		return BBS408;
	}

	public void setBBS408(BigDecimal bBS408) {
		BBS408 = bBS408;
	}

	public BigDecimal getBBS409() {
		return BBS409;
	}

	public void setBBS409(BigDecimal bBS409) {
		BBS409 = bBS409;
	}

	public BigDecimal getBBS406() {
		return BBS406;
	}

	public void setBBS406(BigDecimal bBS406) {
		BBS406 = bBS406;
	}

	public BigDecimal getBBS003() {
		return BBS003;
	}

	public void setBBS003(BigDecimal bBS003) {
		BBS003 = bBS003;
	}

	public BigDecimal getBBS004() {
		return BBS004;
	}

	public void setBBS004(BigDecimal bBS004) {
		BBS004 = bBS004;
	}

	public BigDecimal getBPL100() {
		return BPL100;
	}

	public void setBPL100(BigDecimal bPL100) {
		BPL100 = bPL100;
	}

	public BigDecimal getBPL101() {
		return BPL101;
	}

	public void setBPL101(BigDecimal bPL101) {
		BPL101 = bPL101;
	}

	public BigDecimal getBPL101_1() {
		return BPL101_1;
	}

	public void setBPL101_1(BigDecimal bPL101_1) {
		BPL101_1 = bPL101_1;
	}

	public BigDecimal getBPL101_1_1() {
		return BPL101_1_1;
	}

	public void setBPL101_1_1(BigDecimal bPL101_1_1) {
		BPL101_1_1 = bPL101_1_1;
	}

	public BigDecimal getBPL101_1_1_1() {
		return BPL101_1_1_1;
	}

	public void setBPL101_1_1_1(BigDecimal bPL101_1_1_1) {
		BPL101_1_1_1 = bPL101_1_1_1;
	}

	public BigDecimal getBPL101_1_1_2() {
		return BPL101_1_1_2;
	}

	public void setBPL101_1_1_2(BigDecimal bPL101_1_1_2) {
		BPL101_1_1_2 = bPL101_1_1_2;
	}

	public BigDecimal getBPL101_1_1_3() {
		return BPL101_1_1_3;
	}

	public void setBPL101_1_1_3(BigDecimal bPL101_1_1_3) {
		BPL101_1_1_3 = bPL101_1_1_3;
	}

	public BigDecimal getBPL101_1_2() {
		return BPL101_1_2;
	}

	public void setBPL101_1_2(BigDecimal bPL101_1_2) {
		BPL101_1_2 = bPL101_1_2;
	}

	public BigDecimal getBPL101_1_3() {
		return BPL101_1_3;
	}

	public void setBPL101_1_3(BigDecimal bPL101_1_3) {
		BPL101_1_3 = bPL101_1_3;
	}

	public BigDecimal getBPL101_1_4() {
		return BPL101_1_4;
	}

	public void setBPL101_1_4(BigDecimal bPL101_1_4) {
		BPL101_1_4 = bPL101_1_4;
	}

	public BigDecimal getBPL101_2() {
		return BPL101_2;
	}

	public void setBPL101_2(BigDecimal bPL101_2) {
		BPL101_2 = bPL101_2;
	}

	public BigDecimal getBPL101_2_1() {
		return BPL101_2_1;
	}

	public void setBPL101_2_1(BigDecimal bPL101_2_1) {
		BPL101_2_1 = bPL101_2_1;
	}

	public BigDecimal getBPL101_2_2() {
		return BPL101_2_2;
	}

	public void setBPL101_2_2(BigDecimal bPL101_2_2) {
		BPL101_2_2 = bPL101_2_2;
	}

	public BigDecimal getBPL101_2_3() {
		return BPL101_2_3;
	}

	public void setBPL101_2_3(BigDecimal bPL101_2_3) {
		BPL101_2_3 = bPL101_2_3;
	}

	public BigDecimal getBPL102() {
		return BPL102;
	}

	public void setBPL102(BigDecimal bPL102) {
		BPL102 = bPL102;
	}

	public BigDecimal getBPL102_1() {
		return BPL102_1;
	}

	public void setBPL102_1(BigDecimal bPL102_1) {
		BPL102_1 = bPL102_1;
	}

	public BigDecimal getBPL102_1_1() {
		return BPL102_1_1;
	}

	public void setBPL102_1_1(BigDecimal bPL102_1_1) {
		BPL102_1_1 = bPL102_1_1;
	}

	public BigDecimal getBPL102_1_2() {
		return BPL102_1_2;
	}

	public void setBPL102_1_2(BigDecimal bPL102_1_2) {
		BPL102_1_2 = bPL102_1_2;
	}

	public BigDecimal getBPL102_1_3() {
		return BPL102_1_3;
	}

	public void setBPL102_1_3(BigDecimal bPL102_1_3) {
		BPL102_1_3 = bPL102_1_3;
	}

	public BigDecimal getBPL102_1_4() {
		return BPL102_1_4;
	}

	public void setBPL102_1_4(BigDecimal bPL102_1_4) {
		BPL102_1_4 = bPL102_1_4;
	}

	public BigDecimal getBPL102_1_5() {
		return BPL102_1_5;
	}

	public void setBPL102_1_5(BigDecimal bPL102_1_5) {
		BPL102_1_5 = bPL102_1_5;
	}

	public BigDecimal getBPL102_1_6() {
		return BPL102_1_6;
	}

	public void setBPL102_1_6(BigDecimal bPL102_1_6) {
		BPL102_1_6 = bPL102_1_6;
	}

	public BigDecimal getBPL102_2() {
		return BPL102_2;
	}

	public void setBPL102_2(BigDecimal bPL102_2) {
		BPL102_2 = bPL102_2;
	}

	public BigDecimal getBPL103() {
		return BPL103;
	}

	public void setBPL103(BigDecimal bPL103) {
		BPL103 = bPL103;
	}

	public BigDecimal getBPL103_1() {
		return BPL103_1;
	}

	public void setBPL103_1(BigDecimal bPL103_1) {
		BPL103_1 = bPL103_1;
	}

	public BigDecimal getBPL104() {
		return BPL104;
	}

	public void setBPL104(BigDecimal bPL104) {
		BPL104 = bPL104;
	}

	public BigDecimal getBPL105() {
		return BPL105;
	}

	public void setBPL105(BigDecimal bPL105) {
		BPL105 = bPL105;
	}

	public BigDecimal getBPL106() {
		return BPL106;
	}

	public void setBPL106(BigDecimal bPL106) {
		BPL106 = bPL106;
	}

	public BigDecimal getBPL200() {
		return BPL200;
	}

	public void setBPL200(BigDecimal bPL200) {
		BPL200 = bPL200;
	}

	public BigDecimal getBPL201() {
		return BPL201;
	}

	public void setBPL201(BigDecimal bPL201) {
		BPL201 = bPL201;
	}

	public BigDecimal getBPL202() {
		return BPL202;
	}

	public void setBPL202(BigDecimal bPL202) {
		BPL202 = bPL202;
	}

	public BigDecimal getBPL203() {
		return BPL203;
	}

	public void setBPL203(BigDecimal bPL203) {
		BPL203 = bPL203;
	}

	public BigDecimal getBPL204() {
		return BPL204;
	}

	public void setBPL204(BigDecimal bPL204) {
		BPL204 = bPL204;
	}

	public BigDecimal getBPL300() {
		return BPL300;
	}

	public void setBPL300(BigDecimal bPL300) {
		BPL300 = bPL300;
	}

	public BigDecimal getBPL301() {
		return BPL301;
	}

	public void setBPL301(BigDecimal bPL301) {
		BPL301 = bPL301;
	}

	public BigDecimal getBPL302() {
		return BPL302;
	}

	public void setBPL302(BigDecimal bPL302) {
		BPL302 = bPL302;
	}

	public BigDecimal getBPL400() {
		return BPL400;
	}

	public void setBPL400(BigDecimal bPL400) {
		BPL400 = bPL400;
	}

	public BigDecimal getBPL401() {
		return BPL401;
	}

	public void setBPL401(BigDecimal bPL401) {
		BPL401 = bPL401;
	}

	public BigDecimal getBPL500() {
		return BPL500;
	}

	public void setBPL500(BigDecimal bPL500) {
		BPL500 = bPL500;
	}

	public BigDecimal getBPL501() {
		return BPL501;
	}

	public void setBPL501(BigDecimal bPL501) {
		BPL501 = bPL501;
	}

	public BigDecimal getBPL502() {
		return BPL502;
	}

	public void setBPL502(BigDecimal bPL502) {
		BPL502 = bPL502;
	}

	public BigDecimal getBPL503() {
		return BPL503;
	}

	public void setBPL503(BigDecimal bPL503) {
		BPL503 = bPL503;
	}

	public BigDecimal getBPL504() {
		return BPL504;
	}

	public void setBPL504(BigDecimal bPL504) {
		BPL504 = bPL504;
	}

	public BigDecimal getBPL505() {
		return BPL505;
	}

	public void setBPL505(BigDecimal bPL505) {
		BPL505 = bPL505;
	}

	public BigDecimal getBPL506() {
		return BPL506;
	}

	public void setBPL506(BigDecimal bPL506) {
		BPL506 = bPL506;
	}

	public BigDecimal getBPL507() {
		return BPL507;
	}

	public void setBPL507(BigDecimal bPL507) {
		BPL507 = bPL507;
	}

	public BigDecimal getBPL508() {
		return BPL508;
	}

	public void setBPL508(BigDecimal bPL508) {
		BPL508 = bPL508;
	}

	public BigDecimal getBCF101() {
		return BCF101;
	}

	public void setBCF101(BigDecimal bCF101) {
		BCF101 = bCF101;
	}

	public BigDecimal getBCF102() {
		return BCF102;
	}

	public void setBCF102(BigDecimal bCF102) {
		BCF102 = bCF102;
	}

	public BigDecimal getBCF103() {
		return BCF103;
	}

	public void setBCF103(BigDecimal bCF103) {
		BCF103 = bCF103;
	}

	public BigDecimal getBCF104() {
		return BCF104;
	}

	public void setBCF104(BigDecimal bCF104) {
		BCF104 = bCF104;
	}

	public BigDecimal getBCF105() {
		return BCF105;
	}

	public void setBCF105(BigDecimal bCF105) {
		BCF105 = bCF105;
	}

	public BigDecimal getBCF106() {
		return BCF106;
	}

	public void setBCF106(BigDecimal bCF106) {
		BCF106 = bCF106;
	}

	public BigDecimal getBCF107() {
		return BCF107;
	}

	public void setBCF107(BigDecimal bCF107) {
		BCF107 = bCF107;
	}

	public BigDecimal getBCF108() {
		return BCF108;
	}

	public void setBCF108(BigDecimal bCF108) {
		BCF108 = bCF108;
	}

	public BigDecimal getBCF100() {
		return BCF100;
	}

	public void setBCF100(BigDecimal bCF100) {
		BCF100 = bCF100;
	}

	public BigDecimal getBCF201() {
		return BCF201;
	}

	public void setBCF201(BigDecimal bCF201) {
		BCF201 = bCF201;
	}

	public BigDecimal getBCF202() {
		return BCF202;
	}

	public void setBCF202(BigDecimal bCF202) {
		BCF202 = bCF202;
	}

	public BigDecimal getBCF203() {
		return BCF203;
	}

	public void setBCF203(BigDecimal bCF203) {
		BCF203 = bCF203;
	}

	public BigDecimal getBCF204() {
		return BCF204;
	}

	public void setBCF204(BigDecimal bCF204) {
		BCF204 = bCF204;
	}

	public BigDecimal getBCF205() {
		return BCF205;
	}

	public void setBCF205(BigDecimal bCF205) {
		BCF205 = bCF205;
	}

	public BigDecimal getBCF206() {
		return BCF206;
	}

	public void setBCF206(BigDecimal bCF206) {
		BCF206 = bCF206;
	}

	public BigDecimal getBCF207() {
		return BCF207;
	}

	public void setBCF207(BigDecimal bCF207) {
		BCF207 = bCF207;
	}

	public BigDecimal getBCF208() {
		return BCF208;
	}

	public void setBCF208(BigDecimal bCF208) {
		BCF208 = bCF208;
	}

	public BigDecimal getBCF209() {
		return BCF209;
	}

	public void setBCF209(BigDecimal bCF209) {
		BCF209 = bCF209;
	}

	public BigDecimal getBCF210() {
		return BCF210;
	}

	public void setBCF210(BigDecimal bCF210) {
		BCF210 = bCF210;
	}

	public BigDecimal getBCF200() {
		return BCF200;
	}

	public void setBCF200(BigDecimal bCF200) {
		BCF200 = bCF200;
	}

	public BigDecimal getBCF001() {
		return BCF001;
	}

	public void setBCF001(BigDecimal bCF001) {
		BCF001 = bCF001;
	}

	public BigDecimal getBCF301() {
		return BCF301;
	}

	public void setBCF301(BigDecimal bCF301) {
		BCF301 = bCF301;
	}

	public BigDecimal getBCF302() {
		return BCF302;
	}

	public void setBCF302(BigDecimal bCF302) {
		BCF302 = bCF302;
	}

	public BigDecimal getBCF303() {
		return BCF303;
	}

	public void setBCF303(BigDecimal bCF303) {
		BCF303 = bCF303;
	}

	public BigDecimal getBCF304() {
		return BCF304;
	}

	public void setBCF304(BigDecimal bCF304) {
		BCF304 = bCF304;
	}

	public BigDecimal getBCF305() {
		return BCF305;
	}

	public void setBCF305(BigDecimal bCF305) {
		BCF305 = bCF305;
	}

	public BigDecimal getBCF300() {
		return BCF300;
	}

	public void setBCF300(BigDecimal bCF300) {
		BCF300 = bCF300;
	}

	public BigDecimal getBCF401() {
		return BCF401;
	}

	public void setBCF401(BigDecimal bCF401) {
		BCF401 = bCF401;
	}

	public BigDecimal getBCF402() {
		return BCF402;
	}

	public void setBCF402(BigDecimal bCF402) {
		BCF402 = bCF402;
	}

	public BigDecimal getBCF403() {
		return BCF403;
	}

	public void setBCF403(BigDecimal bCF403) {
		BCF403 = bCF403;
	}

	public BigDecimal getBCF404() {
		return BCF404;
	}

	public void setBCF404(BigDecimal bCF404) {
		BCF404 = bCF404;
	}

	public BigDecimal getBCF400() {
		return BCF400;
	}

	public void setBCF400(BigDecimal bCF400) {
		BCF400 = bCF400;
	}

	public BigDecimal getBCF002() {
		return BCF002;
	}

	public void setBCF002(BigDecimal bCF002) {
		BCF002 = bCF002;
	}

	public BigDecimal getBCF501() {
		return BCF501;
	}

	public void setBCF501(BigDecimal bCF501) {
		BCF501 = bCF501;
	}

	public BigDecimal getBCF502() {
		return BCF502;
	}

	public void setBCF502(BigDecimal bCF502) {
		BCF502 = bCF502;
	}

	public BigDecimal getBCF503() {
		return BCF503;
	}

	public void setBCF503(BigDecimal bCF503) {
		BCF503 = bCF503;
	}

	public BigDecimal getBCF504() {
		return BCF504;
	}

	public void setBCF504(BigDecimal bCF504) {
		BCF504 = bCF504;
	}

	public BigDecimal getBCF500() {
		return BCF500;
	}

	public void setBCF500(BigDecimal bCF500) {
		BCF500 = bCF500;
	}

	public BigDecimal getBCF601() {
		return BCF601;
	}

	public void setBCF601(BigDecimal bCF601) {
		BCF601 = bCF601;
	}

	public BigDecimal getBCF602() {
		return BCF602;
	}

	public void setBCF602(BigDecimal bCF602) {
		BCF602 = bCF602;
	}

	public BigDecimal getBCF603() {
		return BCF603;
	}

	public void setBCF603(BigDecimal bCF603) {
		BCF603 = bCF603;
	}

	public BigDecimal getBCF604() {
		return BCF604;
	}

	public void setBCF604(BigDecimal bCF604) {
		BCF604 = bCF604;
	}

	public BigDecimal getBCF600() {
		return BCF600;
	}

	public void setBCF600(BigDecimal bCF600) {
		BCF600 = bCF600;
	}

	public BigDecimal getBCF003() {
		return BCF003;
	}

	public void setBCF003(BigDecimal bCF003) {
		BCF003 = bCF003;
	}

	public BigDecimal getBCF701() {
		return BCF701;
	}

	public void setBCF701(BigDecimal bCF701) {
		BCF701 = bCF701;
	}

	public BigDecimal getBCF004() {
		return BCF004;
	}

	public void setBCF004(BigDecimal bCF004) {
		BCF004 = bCF004;
	}

	public BigDecimal getBCF801() {
		return BCF801;
	}

	public void setBCF801(BigDecimal bCF801) {
		BCF801 = bCF801;
	}

	public BigDecimal getBCF005() {
		return BCF005;
	}

	public void setBCF005(BigDecimal bCF005) {
		BCF005 = bCF005;
	}

	public BigDecimal getBCF901() {
		return BCF901;
	}

	public void setBCF901(BigDecimal bCF901) {
		BCF901 = bCF901;
	}

	public BigDecimal getBCF902() {
		return BCF902;
	}

	public void setBCF902(BigDecimal bCF902) {
		BCF902 = bCF902;
	}

	public BigDecimal getBCF903() {
		return BCF903;
	}

	public void setBCF903(BigDecimal bCF903) {
		BCF903 = bCF903;
	}

	public BigDecimal getBCF904() {
		return BCF904;
	}

	public void setBCF904(BigDecimal bCF904) {
		BCF904 = bCF904;
	}

	public BigDecimal getBCF905() {
		return BCF905;
	}

	public void setBCF905(BigDecimal bCF905) {
		BCF905 = bCF905;
	}

	public BigDecimal getBCF906() {
		return BCF906;
	}

	public void setBCF906(BigDecimal bCF906) {
		BCF906 = bCF906;
	}

	public BigDecimal getBCF907() {
		return BCF907;
	}

	public void setBCF907(BigDecimal bCF907) {
		BCF907 = bCF907;
	}

	public BigDecimal getBCF908() {
		return BCF908;
	}

	public void setBCF908(BigDecimal bCF908) {
		BCF908 = bCF908;
	}

	public BigDecimal getBCF909() {
		return BCF909;
	}

	public void setBCF909(BigDecimal bCF909) {
		BCF909 = bCF909;
	}

	public BigDecimal getBCF910() {
		return BCF910;
	}

	public void setBCF910(BigDecimal bCF910) {
		BCF910 = bCF910;
	}

	public BigDecimal getBCF911() {
		return BCF911;
	}

	public void setBCF911(BigDecimal bCF911) {
		BCF911 = bCF911;
	}

	public BigDecimal getBCF912() {
		return BCF912;
	}

	public void setBCF912(BigDecimal bCF912) {
		BCF912 = bCF912;
	}

	public BigDecimal getBCF913() {
		return BCF913;
	}

	public void setBCF913(BigDecimal bCF913) {
		BCF913 = bCF913;
	}

	public BigDecimal getBCF914() {
		return BCF914;
	}

	public void setBCF914(BigDecimal bCF914) {
		BCF914 = bCF914;
	}

	public BigDecimal getBCF915() {
		return BCF915;
	}

	public void setBCF915(BigDecimal bCF915) {
		BCF915 = bCF915;
	}

	public BigDecimal getBCF916() {
		return BCF916;
	}

	public void setBCF916(BigDecimal bCF916) {
		BCF916 = bCF916;
	}

	public BigDecimal getBCF917() {
		return BCF917;
	}

	public void setBCF917(BigDecimal bCF917) {
		BCF917 = bCF917;
	}

	public BigDecimal getBCF918() {
		return BCF918;
	}

	public void setBCF918(BigDecimal bCF918) {
		BCF918 = bCF918;
	}

	public BigDecimal getBCF919() {
		return BCF919;
	}

	public void setBCF919(BigDecimal bCF919) {
		BCF919 = bCF919;
	}

	public BigDecimal getBCF920() {
		return BCF920;
	}

	public void setBCF920(BigDecimal bCF920) {
		BCF920 = bCF920;
	}

	public BigDecimal getBCF921() {
		return BCF921;
	}

	public void setBCF921(BigDecimal bCF921) {
		BCF921 = bCF921;
	}

	public BigDecimal getBCF922() {
		return BCF922;
	}

	public void setBCF922(BigDecimal bCF922) {
		BCF922 = bCF922;
	}

	public BigDecimal getBCF923() {
		return BCF923;
	}

	public void setBCF923(BigDecimal bCF923) {
		BCF923 = bCF923;
	}

	public BigDecimal getBCF924() {
		return BCF924;
	}

	public void setBCF924(BigDecimal bCF924) {
		BCF924 = bCF924;
	}

	public BigDecimal getBCF925() {
		return BCF925;
	}

	public void setBCF925(BigDecimal bCF925) {
		BCF925 = bCF925;
	}

	public BigDecimal getBCF926() {
		return BCF926;
	}

	public void setBCF926(BigDecimal bCF926) {
		BCF926 = bCF926;
	}

	public BigDecimal getBCF927() {
		return BCF927;
	}

	public void setBCF927(BigDecimal bCF927) {
		BCF927 = bCF927;
	}

	public BigDecimal getBTN101() {
		return BTN101;
	}

	public void setBTN101(BigDecimal bTN101) {
		BTN101 = bTN101;
	}

	public BigDecimal getBTN102() {
		return BTN102;
	}

	public void setBTN102(BigDecimal bTN102) {
		BTN102 = bTN102;
	}

	public BigDecimal getBTN103() {
		return BTN103;
	}

	public void setBTN103(BigDecimal bTN103) {
		BTN103 = bTN103;
	}

	public BigDecimal getBTN104() {
		return BTN104;
	}

	public void setBTN104(BigDecimal bTN104) {
		BTN104 = bTN104;
	}

	public BigDecimal getBTN105() {
		return BTN105;
	}

	public void setBTN105(BigDecimal bTN105) {
		BTN105 = bTN105;
	}

	public BigDecimal getBTN106() {
		return BTN106;
	}

	public void setBTN106(BigDecimal bTN106) {
		BTN106 = bTN106;
	}

	public BigDecimal getBTN107() {
		return BTN107;
	}

	public void setBTN107(BigDecimal bTN107) {
		BTN107 = bTN107;
	}

	public BigDecimal getBTN108() {
		return BTN108;
	}

	public void setBTN108(BigDecimal bTN108) {
		BTN108 = bTN108;
	}

	public BigDecimal getBTN109() {
		return BTN109;
	}

	public void setBTN109(BigDecimal bTN109) {
		BTN109 = bTN109;
	}

	public BigDecimal getBTN110() {
		return BTN110;
	}

	public void setBTN110(BigDecimal bTN110) {
		BTN110 = bTN110;
	}

	public BigDecimal getBTN111() {
		return BTN111;
	}

	public void setBTN111(BigDecimal bTN111) {
		BTN111 = bTN111;
	}

	public BigDecimal getBTN112() {
		return BTN112;
	}

	public void setBTN112(BigDecimal bTN112) {
		BTN112 = bTN112;
	}

	public BigDecimal getBTN113() {
		return BTN113;
	}

	public void setBTN113(BigDecimal bTN113) {
		BTN113 = bTN113;
	}

	public BigDecimal getBTN114() {
		return BTN114;
	}

	public void setBTN114(BigDecimal bTN114) {
		BTN114 = bTN114;
	}

	public BigDecimal getBTN115() {
		return BTN115;
	}

	public void setBTN115(BigDecimal bTN115) {
		BTN115 = bTN115;
	}

	public BigDecimal getBTN116() {
		return BTN116;
	}

	public void setBTN116(BigDecimal bTN116) {
		BTN116 = bTN116;
	}

	public BigDecimal getBTN117() {
		return BTN117;
	}

	public void setBTN117(BigDecimal bTN117) {
		BTN117 = bTN117;
	}

	public BigDecimal getBTN118() {
		return BTN118;
	}

	public void setBTN118(BigDecimal bTN118) {
		BTN118 = bTN118;
	}

	public BigDecimal getBTN119() {
		return BTN119;
	}

	public void setBTN119(BigDecimal bTN119) {
		BTN119 = bTN119;
	}

	public BigDecimal getBTN120() {
		return BTN120;
	}

	public void setBTN120(BigDecimal bTN120) {
		BTN120 = bTN120;
	}

	public BigDecimal getBTN121() {
		return BTN121;
	}

	public void setBTN121(BigDecimal bTN121) {
		BTN121 = bTN121;
	}

	public BigDecimal getBTN122() {
		return BTN122;
	}

	public void setBTN122(BigDecimal bTN122) {
		BTN122 = bTN122;
	}

	public BigDecimal getBTN123() {
		return BTN123;
	}

	public void setBTN123(BigDecimal bTN123) {
		BTN123 = bTN123;
	}

	public BigDecimal getBTN124() {
		return BTN124;
	}

	public void setBTN124(BigDecimal bTN124) {
		BTN124 = bTN124;
	}

	public BigDecimal getBTN125() {
		return BTN125;
	}

	public void setBTN125(BigDecimal bTN125) {
		BTN125 = bTN125;
	}

	public BigDecimal getBTN126() {
		return BTN126;
	}

	public void setBTN126(BigDecimal bTN126) {
		BTN126 = bTN126;
	}

	public BigDecimal getBTN127() {
		return BTN127;
	}

	public void setBTN127(BigDecimal bTN127) {
		BTN127 = bTN127;
	}

	public BigDecimal getBTN128() {
		return BTN128;
	}

	public void setBTN128(BigDecimal bTN128) {
		BTN128 = bTN128;
	}

	public BigDecimal getBTN129() {
		return BTN129;
	}

	public void setBTN129(BigDecimal bTN129) {
		BTN129 = bTN129;
	}

	public BigDecimal getBTN130() {
		return BTN130;
	}

	public void setBTN130(BigDecimal bTN130) {
		BTN130 = bTN130;
	}

	public BigDecimal getBTN131() {
		return BTN131;
	}

	public void setBTN131(BigDecimal bTN131) {
		BTN131 = bTN131;
	}

	public BigDecimal getBTN132() {
		return BTN132;
	}

	public void setBTN132(BigDecimal bTN132) {
		BTN132 = bTN132;
	}

	public BigDecimal getBTN133() {
		return BTN133;
	}

	public void setBTN133(BigDecimal bTN133) {
		BTN133 = bTN133;
	}

	public BigDecimal getBTN134() {
		return BTN134;
	}

	public void setBTN134(BigDecimal bTN134) {
		BTN134 = bTN134;
	}

	public BigDecimal getBTN135() {
		return BTN135;
	}

	public void setBTN135(BigDecimal bTN135) {
		BTN135 = bTN135;
	}

	public BigDecimal getBTN136() {
		return BTN136;
	}

	public void setBTN136(BigDecimal bTN136) {
		BTN136 = bTN136;
	}

	public BigDecimal getBTN137() {
		return BTN137;
	}

	public void setBTN137(BigDecimal bTN137) {
		BTN137 = bTN137;
	}

	public BigDecimal getBTN138() {
		return BTN138;
	}

	public void setBTN138(BigDecimal bTN138) {
		BTN138 = bTN138;
	}

	public BigDecimal getBTN139() {
		return BTN139;
	}

	public void setBTN139(BigDecimal bTN139) {
		BTN139 = bTN139;
	}

	public BigDecimal getBTN140() {
		return BTN140;
	}

	public void setBTN140(BigDecimal bTN140) {
		BTN140 = bTN140;
	}

	public BigDecimal getBTN141() {
		return BTN141;
	}

	public void setBTN141(BigDecimal bTN141) {
		BTN141 = bTN141;
	}

	public BigDecimal getBTN142() {
		return BTN142;
	}

	public void setBTN142(BigDecimal bTN142) {
		BTN142 = bTN142;
	}

	public BigDecimal getBTN143() {
		return BTN143;
	}

	public void setBTN143(BigDecimal bTN143) {
		BTN143 = bTN143;
	}

	public BigDecimal getBTN144() {
		return BTN144;
	}

	public void setBTN144(BigDecimal bTN144) {
		BTN144 = bTN144;
	}

	public BigDecimal getBTN145() {
		return BTN145;
	}

	public void setBTN145(BigDecimal bTN145) {
		BTN145 = bTN145;
	}

	public BigDecimal getBTN146() {
		return BTN146;
	}

	public void setBTN146(BigDecimal bTN146) {
		BTN146 = bTN146;
	}

	public BigDecimal getBTN147() {
		return BTN147;
	}

	public void setBTN147(BigDecimal bTN147) {
		BTN147 = bTN147;
	}

	public BigDecimal getBTN148() {
		return BTN148;
	}

	public void setBTN148(BigDecimal bTN148) {
		BTN148 = bTN148;
	}

	public Date getLast_update_timestamp() {
		return last_update_timestamp;
	}

	public void setLast_update_timestamp(Date last_update_timestamp) {
		this.last_update_timestamp = last_update_timestamp;
	}

	public Date getEND_DATE() {
		return END_DATE;
	}

	public void setEND_DATE(Date eND_DATE) {
		END_DATE = eND_DATE;
	}

	public Long getCOM_UNI_CODE() {
		return COM_UNI_CODE;
	}

	public void setCOM_UNI_CODE(Long cOM_UNI_CODE) {
		COM_UNI_CODE = cOM_UNI_CODE;
	}

	public Date getCCXEID() {
		return CCXEID;
	}

	public void setCCXEID(Date cCXEID) {
		CCXEID = cCXEID;
	}
    
	public String createSelectColumnSql(){
	       String selectSql = "";
	       selectSql += ",COMP_ID";
	       selectSql += ",FIN_DATE";
	       selectSql += ",FIN_ENTITY";
	       selectSql += ",FIN_STATE_TYPE";
	       selectSql += ",FIN_PERIOD";
	       selectSql += ",BBS101";
	       selectSql += ",BBS101_1";
	       selectSql += ",BBS101_2";
	       selectSql += ",BBS101_2_1";
	       selectSql += ",BBS101_2_2";
	       selectSql += ",BBS102";
	       selectSql += ",BBS103";
	       selectSql += ",BBS104";
	       selectSql += ",BBS105";
	       selectSql += ",BBS105_1";
	       selectSql += ",BBS105_2";
	       selectSql += ",BBS105_3";
	       selectSql += ",BBS106";
	       selectSql += ",BBS107";
	       selectSql += ",BBS108";
	       selectSql += ",BBS109";
	       selectSql += ",BBS1101";
	       selectSql += ",BBS110";
	       selectSql += ",BBS110_1";
	       selectSql += ",BBS110_2";
	       selectSql += ",BBS110_3";
	       selectSql += ",BBS111";
	       selectSql += ",BBS111_1";
	       selectSql += ",BBS111_2";
	       selectSql += ",BBS111_3";
	       selectSql += ",BBS112";
	       selectSql += ",BBS118";
	       selectSql += ",BBS114";
	       selectSql += ",BBS1102";
	       selectSql += ",BBS115";
	       selectSql += ",BBS1103";
	       selectSql += ",BBS116";
	       selectSql += ",BBS113";
	       selectSql += ",BBS117";
	       selectSql += ",BBS001";
	       selectSql += ",BBS202";
	       selectSql += ",BBS201";
	       selectSql += ",BBS203";
	       selectSql += ",BBS204";
	       selectSql += ",BBS205";
	       selectSql += ",BBS206";
	       selectSql += ",BBS207";
	       selectSql += ",BBS207_1";
	       selectSql += ",BBS207_1_1";
	       selectSql += ",BBS207_1_2";
	       selectSql += ",BBS207_2";
	       selectSql += ",BBS207_2_1";
	       selectSql += ",BBS207_2_2";
	       selectSql += ",BBS207_2_3";
	       selectSql += ",BBS208";
	       selectSql += ",BBS209";
	       selectSql += ",BBS210";
	       selectSql += ",BBS212";
	       selectSql += ",BBS213";
	       selectSql += ",BBS211";
	       selectSql += ",BBS214";
	       selectSql += ",BBS002";
	       selectSql += ",BBS401";
	       selectSql += ",BBS402";
	       selectSql += ",BBS402_1";
	       selectSql += ",BBS403";
	       selectSql += ",BBS405";
	       selectSql += ",BBS404";
	       selectSql += ",BBS408";
	       selectSql += ",BBS409";
	       selectSql += ",BBS406";
	       selectSql += ",BBS003";
	       selectSql += ",BBS004";
	       selectSql += ",BPL100";
	       selectSql += ",BPL101";
	       selectSql += ",BPL101_1";
	       selectSql += ",BPL101_1_1";
	       selectSql += ",BPL101_1_1_1";
	       selectSql += ",BPL101_1_1_2";
	       selectSql += ",BPL101_1_1_3";
	       selectSql += ",BPL101_1_2";
	       selectSql += ",BPL101_1_3";
	       selectSql += ",BPL101_1_4";
	       selectSql += ",BPL101_2";
	       selectSql += ",BPL101_2_1";
	       selectSql += ",BPL101_2_2";
	       selectSql += ",BPL101_2_3";
	       selectSql += ",BPL102";
	       selectSql += ",BPL102_1";
	       selectSql += ",BPL102_1_1";
	       selectSql += ",BPL102_1_2";
	       selectSql += ",BPL102_1_3";
	       selectSql += ",BPL102_1_4";
	       selectSql += ",BPL102_1_5";
	       selectSql += ",BPL102_1_6";
	       selectSql += ",BPL102_2";
	       selectSql += ",BPL103";
	       selectSql += ",BPL103_1";
	       selectSql += ",BPL104";
	       selectSql += ",BPL105";
	       selectSql += ",BPL106";
	       selectSql += ",BPL200";
	       selectSql += ",BPL201";
	       selectSql += ",BPL202";
	       selectSql += ",BPL203";
	       selectSql += ",BPL204";
	       selectSql += ",BPL300";
	       selectSql += ",BPL301";
	       selectSql += ",BPL302";
	       selectSql += ",BPL400";
	       selectSql += ",BPL401";
	       selectSql += ",BPL500";
	       selectSql += ",BPL501";
	       selectSql += ",BPL502";
	       selectSql += ",BPL503";
	       selectSql += ",BPL504";
	       selectSql += ",BPL505";
	       selectSql += ",BPL506";
	       selectSql += ",BPL507";
	       selectSql += ",BPL508";
	       selectSql += ",BCF101";
	       selectSql += ",BCF102";
	       selectSql += ",BCF103";
	       selectSql += ",BCF104";
	       selectSql += ",BCF105";
	       selectSql += ",BCF106";
	       selectSql += ",BCF107";
	       selectSql += ",BCF108";
	       selectSql += ",BCF100";
	       selectSql += ",BCF201";
	       selectSql += ",BCF202";
	       selectSql += ",BCF203";
	       selectSql += ",BCF204";
	       selectSql += ",BCF205";
	       selectSql += ",BCF206";
	       selectSql += ",BCF207";
	       selectSql += ",BCF208";
	       selectSql += ",BCF209";
	       selectSql += ",BCF210";
	       selectSql += ",BCF200";
	       selectSql += ",BCF001";
	       selectSql += ",BCF301";
	       selectSql += ",BCF302";
	       selectSql += ",BCF303";
	       selectSql += ",BCF304";
	       selectSql += ",BCF305";
	       selectSql += ",BCF300";
	       selectSql += ",BCF401";
	       selectSql += ",BCF402";
	       selectSql += ",BCF403";
	       selectSql += ",BCF404";
	       selectSql += ",BCF400";
	       selectSql += ",BCF002";
	       selectSql += ",BCF501";
	       selectSql += ",BCF502";
	       selectSql += ",BCF503";
	       selectSql += ",BCF504";
	       selectSql += ",BCF500";
	       selectSql += ",BCF601";
	       selectSql += ",BCF602";
	       selectSql += ",BCF603";
	       selectSql += ",BCF604";
	       selectSql += ",BCF600";
	       selectSql += ",BCF003";
	       selectSql += ",BCF701";
	       selectSql += ",BCF004";
	       selectSql += ",BCF801";
	       selectSql += ",BCF005";
	       selectSql += ",BCF901";
	       selectSql += ",BCF902";
	       selectSql += ",BCF903";
	       selectSql += ",BCF904";
	       selectSql += ",BCF905";
	       selectSql += ",BCF906";
	       selectSql += ",BCF907";
	       selectSql += ",BCF908";
	       selectSql += ",BCF909";
	       selectSql += ",BCF910";
	       selectSql += ",BCF911";
	       selectSql += ",BCF912";
	       selectSql += ",BCF913";
	       selectSql += ",BCF914";
	       selectSql += ",BCF915";
	       selectSql += ",BCF916";
	       selectSql += ",BCF917";
	       selectSql += ",BCF918";
	       selectSql += ",BCF919";
	       selectSql += ",BCF920";
	       selectSql += ",BCF921";
	       selectSql += ",BCF922";
	       selectSql += ",BCF923";
	       selectSql += ",BCF924";
	       selectSql += ",BCF925";
	       selectSql += ",BCF926";
	       selectSql += ",BCF927";
	       selectSql += ",BTN101";
	       selectSql += ",BTN102";
	       selectSql += ",BTN103";
	       selectSql += ",BTN104";
	       selectSql += ",BTN105";
	       selectSql += ",BTN106";
	       selectSql += ",BTN107";
	       selectSql += ",BTN108";
	       selectSql += ",BTN109";
	       selectSql += ",BTN110";
	       selectSql += ",BTN111";
	       selectSql += ",BTN112";
	       selectSql += ",BTN113";
	       selectSql += ",BTN114";
	       selectSql += ",BTN115";
	       selectSql += ",BTN116";
	       selectSql += ",BTN117";
	       selectSql += ",BTN118";
	       selectSql += ",BTN119";
	       selectSql += ",BTN120";
	       selectSql += ",BTN121";
	       selectSql += ",BTN122";
	       selectSql += ",BTN123";
	       selectSql += ",BTN124";
	       selectSql += ",BTN125";
	       selectSql += ",BTN126";
	       selectSql += ",BTN127";
	       selectSql += ",BTN128";
	       selectSql += ",BTN129";
	       selectSql += ",BTN130";
	       selectSql += ",BTN131";
	       selectSql += ",BTN132";
	       selectSql += ",BTN133";
	       selectSql += ",BTN134";
	       selectSql += ",BTN135";
	       selectSql += ",BTN136";
	       selectSql += ",BTN137";
	       selectSql += ",BTN138";
	       selectSql += ",BTN139";
	       selectSql += ",BTN140";
	       selectSql += ",BTN141";
	       selectSql += ",BTN142";
	       selectSql += ",BTN143";
	       selectSql += ",BTN144";
	       selectSql += ",BTN145";
	       selectSql += ",BTN146";
	       selectSql += ",BTN147";
	       selectSql += ",BTN148";
	       selectSql += ",last_update_timestamp";
	       selectSql += ",ROW_KEY";
		   return selectSql.substring(1);
	    }
	
	public String insertBySelectSql(){
		   String selColumn = createSelectColumnSql();           
	       String sql = "insert into bank_fina_sheet_p ("+selColumn+") select "+selColumn+" from bank_fina_sheet";
	       return sql;
	    }
}
