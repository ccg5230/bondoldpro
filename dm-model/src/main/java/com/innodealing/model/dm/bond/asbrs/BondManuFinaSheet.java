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
@Table(name = "manu_fina_sheet")
public class BondManuFinaSheet implements Serializable {
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
	 * 货币资金
	 */
    @Column(length=20)
    private BigDecimal BS101;
    
	/**
	 * 保证金存款
	 */
    @Column(length=20)
    private BigDecimal BS101_1;
    
	/**
	 * 质押存款
	 */
    @Column(length=20)
    private BigDecimal BS101_2;
    
	/**
	 * 交易性金融资产
	 */
    @Column(length=20)
    private BigDecimal BS102;
    
	/**
	 * 债券
	 */
    @Column(length=20)
    private BigDecimal BS102_1;
    
	/**
	 * 股票
	 */
    @Column(length=20)
    private BigDecimal BS102_2;
    
	/**
	 * 已质押的交易性金融资产（短期投资）
	 */
    @Column(length=20)
    private BigDecimal BS102_3;
    
	/**
	 * 已计提交易性金融资产（短期投资）跌价准备
	 */
    @Column(length=20)
    private BigDecimal BS102_4;
    
	/**
	 * 应收票据
	 */
    @Column(length=20)
    private BigDecimal BS103;
    
	/**
	 * 应收账款
	 */
    @Column(length=20)
    private BigDecimal BS104;
    
	/**
	 * 应收关联公司贸易款
	 */
    @Column(length=20)
    private BigDecimal BS104_1;
    
	/**
	 * 已计提坏账准备
	 */
    @Column(length=20)
    private BigDecimal BS104_2;
    
	/**
	 * 预付款项
	 */
    @Column(length=20)
    private BigDecimal BS105;
    
	/**
	 * 预付贸易款项
	 */
    @Column(length=20)
    private BigDecimal BS105_1;
    
	/**
	 * 应收利息
	 */
    @Column(length=20)
    private BigDecimal BS106;
    
	/**
	 * 应收股利
	 */
    @Column(length=20)
    private BigDecimal BS107;
    
	/**
	 * 其他应收款
	 */
    @Column(length=20)
    private BigDecimal BS108;
    
	/**
	 * 关联公司拆借款
	 */
    @Column(length=20)
    private BigDecimal BS108_1;
    
	/**
	 * 股东或董事个人拆借款
	 */
    @Column(length=20)
    private BigDecimal BS108_2;
    
	/**
	 * 已计提坏账准备
	 */
    @Column(length=20)
    private BigDecimal BS108_3;
    
	/**
	 * 存货
	 */
    @Column(length=20)
    private BigDecimal BS109;
    
	/**
	 * 原材料
	 */
    @Column(length=20)
    private BigDecimal BS109_1;
    
	/**
	 * 在产品
	 */
    @Column(length=20)
    private BigDecimal BS109_2;
    
	/**
	 * 库存商品
	 */
    @Column(length=20)
    private BigDecimal BS109_3;
    
	/**
	 * 待出售物业
	 */
    @Column(length=20)
    private BigDecimal BS109_4;
    
	/**
	 * 非持续经营相关的存货
	 */
    @Column(length=20)
    private BigDecimal BS109_5;
    
	/**
	 * 已计提存货跌价准备
	 */
    @Column(length=20)
    private BigDecimal BS109_6;
    
	/**
	 * 一年内到期的非流动资产
	 */
    @Column(length=20)
    private BigDecimal BS110;
    
	/**
	 * 其他流动资产
	 */
    @Column(length=20)
    private BigDecimal BS111;
    
	/**
	 * 流动资产合计
	 */
    @Column(length=20)
    private BigDecimal BS100;
    
	/**
	 * 发放委托贷款及垫款
	 */
    @Column(length=20)
    private BigDecimal BS2011;
    
	/**
	 * 可供出售金融资产
	 */
    @Column(length=20)
    private BigDecimal BS201;
    
	/**
	 * 持有至到期投资
	 */
    @Column(length=20)
    private BigDecimal BS202;
    
	/**
	 * 长期应收款
	 */
    @Column(length=20)
    private BigDecimal BS203;
    
	/**
	 * 长期股权投资
	 */
    @Column(length=20)
    private BigDecimal BS204;
    
	/**
	 * 投资性房地产
	 */
    @Column(length=20)
    private BigDecimal BS205;
    
	/**
	 * 长期债权投资
	 */
    @Column(length=20)
    private BigDecimal BS2012;
    
	/**
	 * 其他长期投资
	 */
    @Column(length=20)
    private BigDecimal BS206;
    
	/**
	 * 长期投资合计
	 */
    @Column(length=20)
    private BigDecimal BS210;
    
	/**
	 * 固定资产原值
	 */
    @Column(length=20)
    private BigDecimal BS211;
    
	/**
	 * 房屋、建筑物
	 */
    @Column(length=20)
    private BigDecimal BS211_1;
    
	/**
	 * 机器设备
	 */
    @Column(length=20)
    private BigDecimal BS211_2;
    
	/**
	 * 运输工具
	 */
    @Column(length=20)
    private BigDecimal BS211_3;
    
	/**
	 * 减：累计折旧
	 */
    @Column(length=20)
    private BigDecimal BS2013;
    
	/**
	 * 固定资产净值
	 */
    @Column(length=20)
    private BigDecimal BS2014;
    
	/**
	 * 固定资产减值准备
	 */
    @Column(length=20)
    private BigDecimal BS212;
    
	/**
	 * 固定资产净额
	 */
    @Column(length=20)
    private BigDecimal BS220;
    
	/**
	 * 在建工程
	 */
    @Column(length=20)
    private BigDecimal BS221;
    
	/**
	 * 工程物资
	 */
    @Column(length=20)
    private BigDecimal BS222;
    
	/**
	 * 固定资产清理
	 */
    @Column(length=20)
    private BigDecimal BS223;
    
	/**
	 * 生产性生物资产
	 */
    @Column(length=20)
    private BigDecimal BS224;
    
	/**
	 * 油气资产
	 */
    @Column(length=20)
    private BigDecimal BS225;
    
	/**
	 * 固定资产合计
	 */
    @Column(length=20)
    private BigDecimal BS230;
    
	/**
	 * 无形资产
	 */
    @Column(length=20)
    private BigDecimal BS240;
    
	/**
	 * 已计提累计摊销
	 */
    @Column(length=20)
    private BigDecimal BS240_1;
    
	/**
	 * 无形资产减值准备
	 */
    @Column(length=20)
    private BigDecimal BS240_2;
    
	/**
	 * 研发支出
	 */
    @Column(length=20)
    private BigDecimal BS251;
    
	/**
	 * 商誉
	 */
    @Column(length=20)
    private BigDecimal BS252;
    
	/**
	 * 长期待摊费用
	 */
    @Column(length=20)
    private BigDecimal BS253;
    
	/**
	 * 递延所得税资产
	 */
    @Column(length=20)
    private BigDecimal BS254;
    
	/**
	 * 其他非流动资产
	 */
    @Column(length=20)
    private BigDecimal BS255;
    
	/**
	 * 非流动资产合计
	 */
    @Column(length=20)
    private BigDecimal BS200;
    
	/**
	 * 资产总计
	 */
    @Column(length=20)
    private BigDecimal BS001;
    
	/**
	 * 短期借款
	 */
    @Column(length=20)
    private BigDecimal BS301;
    
	/**
	 * 交易性金融负债
	 */
    @Column(length=20)
    private BigDecimal BS302;
    
	/**
	 * 应付票据
	 */
    @Column(length=20)
    private BigDecimal BS303;
    
	/**
	 * 应付账款
	 */
    @Column(length=20)
    private BigDecimal BS304;
    
	/**
	 * 应付关联公司贸易款
	 */
    @Column(length=20)
    private BigDecimal BS304_1;
    
	/**
	 * 预收款项
	 */
    @Column(length=20)
    private BigDecimal BS305;
    
	/**
	 * 预收贸易款项
	 */
    @Column(length=20)
    private BigDecimal BS305_1;
    
	/**
	 * 应付职工薪酬
	 */
    @Column(length=20)
    private BigDecimal BS306;
    
	/**
	 * 应交税费
	 */
    @Column(length=20)
    private BigDecimal BS307;
    
	/**
	 * 应付利息
	 */
    @Column(length=20)
    private BigDecimal BS308;
    
	/**
	 * 应付股利
	 */
    @Column(length=20)
    private BigDecimal BS309;
    
	/**
	 * 其他应付款
	 */
    @Column(length=20)
    private BigDecimal BS310;
    
	/**
	 * 关联公司垫款
	 */
    @Column(length=20)
    private BigDecimal BS310_1;
    
	/**
	 * 股东或董事个人垫款
	 */
    @Column(length=20)
    private BigDecimal BS310_2;
    
	/**
	 * 一年内到期的非流动负债
	 */
    @Column(length=20)
    private BigDecimal BS311;
    
	/**
	 * 长期金融机构借款
	 */
    @Column(length=20)
    private BigDecimal BS311_1;
    
	/**
	 * 融资租赁负债
	 */
    @Column(length=20)
    private BigDecimal BS311_2;
    
	/**
	 * 其他流动负债
	 */
    @Column(length=20)
    private BigDecimal BS312;
    
	/**
	 * 应付短期债券
	 */
    @Column(length=20)
    private BigDecimal BS313;
    
	/**
	 * 流动负债合计
	 */
    @Column(length=20)
    private BigDecimal BS300;
    
	/**
	 * 长期借款
	 */
    @Column(length=20)
    private BigDecimal BS401;
    
	/**
	 * 向金融机构借款
	 */
    @Column(length=20)
    private BigDecimal BS401_1;
    
	/**
	 * 向关联公司借款
	 */
    @Column(length=20)
    private BigDecimal BS401_2;
    
	/**
	 * 向股东或董事个人借款
	 */
    @Column(length=20)
    private BigDecimal BS401_3;
    
	/**
	 * 应付债券
	 */
    @Column(length=20)
    private BigDecimal BS402;
    
	/**
	 * 长期应付款
	 */
    @Column(length=20)
    private BigDecimal BS403;
    
	/**
	 * 专项应付款
	 */
    @Column(length=20)
    private BigDecimal BS404;
    
	/**
	 * 预计负债
	 */
    @Column(length=20)
    private BigDecimal BS405;
    
	/**
	 * 递延所得税负债
	 */
    @Column(length=20)
    private BigDecimal BS406;
    
	/**
	 * 其他非流动负债
	 */
    @Column(length=20)
    private BigDecimal BS407;
    
	/**
	 * 非流动负债合计
	 */
    @Column(length=20)
    private BigDecimal BS400;
    
	/**
	 * 负债合计
	 */
    @Column(length=20)
    private BigDecimal BS002;
    
	/**
	 * 实收资本(或股本)
	 */
    @Column(length=20)
    private BigDecimal BS501;
    
	/**
	 * 资本公积
	 */
    @Column(length=20)
    private BigDecimal BS502;
    
	/**
	 * 减:库存股
	 */
    @Column(length=20)
    private BigDecimal BS503;
    
	/**
	 * 专项储备
	 */
    @Column(length=20)
    private BigDecimal BS2015;
    
	/**
	 * 盈余公积
	 */
    @Column(length=20)
    private BigDecimal BS504;
    
	/**
	 * 一般风险准备
	 */
    @Column(length=20)
    private BigDecimal BS2016;
    
	/**
	 * 未分配利润
	 */
    @Column(length=20)
    private BigDecimal BS505;
    
	/**
	 * 外币报表折算差额
	 */
    @Column(length=20)
    private BigDecimal BS506;
    
	/**
	 * 归属于母公司所有者权益合计
	 */
    @Column(length=20)
    private BigDecimal BS003;
    
	/**
	 * 少数股东权益
	 */
    @Column(length=20)
    private BigDecimal BS507;
    
	/**
	 * 所有者权益(或股东权益)合计
	 */
    @Column(length=20)
    private BigDecimal BS004;
    
	/**
	 * 负债和所有者权益(或股东权益)总计
	 */
    @Column(length=20)
    private BigDecimal BS005;
    
	/**
	 * 营业收入
	 */
    @Column(length=20)
    private BigDecimal PL101;
    
	/**
	 * 营业成本
	 */
    @Column(length=20)
    private BigDecimal PL102;
    
	/**
	 * 营业税金及附加
	 */
    @Column(length=20)
    private BigDecimal PL103;
    
	/**
	 * 销售费用
	 */
    @Column(length=20)
    private BigDecimal PL201;
    
	/**
	 * 无形资产摊销
	 */
    @Column(length=20)
    private BigDecimal PL202_5;
    
	/**
	 * 管理费用
	 */
    @Column(length=20)
    private BigDecimal PL202;
    
	/**
	 * 投资收益(损失以“－”号填列)
	 */
    @Column(length=20)
    private BigDecimal PL203;
    
	/**
	 * 其中:对联营企业和合营企业的投资收益
	 */
    @Column(length=20)
    private BigDecimal PL203_1;
    
	/**
	 * 资产减值损失
	 */
    @Column(length=20)
    private BigDecimal PL204;
    
	/**
	 * 公允价值变动收益(损失以“－”号填列)
	 */
    @Column(length=20)
    private BigDecimal PL205;
    
	/**
	 * 息税前营业利润（EBIT）
	 */
    @Column(length=20)
    private BigDecimal PL200;
    
	/**
	 * 固定资产折旧
	 */
    @Column(length=20)
    private BigDecimal PL210;
    
	/**
	 * 无形资产摊销
	 */
    @Column(length=20)
    private BigDecimal PL211;
    
	/**
	 * 待摊费用减少
	 */
    @Column(length=20)
    private BigDecimal PL212;
    
	/**
	 * 息税折旧摊销前营业利润(EBITDA)
	 */
    @Column(length=20)
    private BigDecimal PL220;
    
	/**
	 * 财务费用
	 */
    @Column(length=20)
    private BigDecimal PL301;
    
	/**
	 * 利息支出
	 */
    @Column(length=20)
    private BigDecimal PL301_1;
    
	/**
	 * 减：资本化利息支出
	 */
    @Column(length=20)
    private BigDecimal PL301_2;
    
	/**
	 * 减：利息收入
	 */
    @Column(length=20)
    private BigDecimal PL301_3;
    
	/**
	 * 贴现利息支出
	 */
    @Column(length=20)
    private BigDecimal PL301_4;
    
	/**
	 * 减：贴现利息收入
	 */
    @Column(length=20)
    private BigDecimal PL301_5;
    
	/**
	 * 利息净支出(收入以"-"表示)
	 */
    @Column(length=20)
    private BigDecimal PL301_6;
    
	/**
	 * 汇兑损益净额(收入以"-"表示)
	 */
    @Column(length=20)
    private BigDecimal PL301_7;
    
	/**
	 * 减：现金折扣
	 */
    @Column(length=20)
    private BigDecimal PL301_8;
    
	/**
	 * 手续费
	 */
    @Column(length=20)
    private BigDecimal PL301_9;
    
	/**
	 * 担保费
	 */
    @Column(length=20)
    private BigDecimal PL301_10;
    
	/**
	 * 其他财务费用
	 */
    @Column(length=20)
    private BigDecimal PL301_11;
    
	/**
	 * 减：其他财务收入
	 */
    @Column(length=20)
    private BigDecimal PL301_12;
    
	/**
	 * 营业利润(亏损以“－”号填列)
	 */
    @Column(length=20)
    private BigDecimal PL300;
    
	/**
	 * 加：营业外收入
	 */
    @Column(length=20)
    private BigDecimal PL401;
    
	/**
	 * 减：营业外支出
	 */
    @Column(length=20)
    private BigDecimal PL402;
    
	/**
	 * 非流动资产处置损失
	 */
    @Column(length=20)
    private BigDecimal PL402_1;
    
	/**
	 * 利润总额(亏损总额以“－”号填列)
	 */
    @Column(length=20)
    private BigDecimal PL400;
    
	/**
	 * 所得税费用
	 */
    @Column(length=20)
    private BigDecimal PL501;
    
	/**
	 * 净利润
	 */
    @Column(length=20)
    private BigDecimal PL500;
    
	/**
	 * 少数股东损益
	 */
    @Column(length=20)
    private BigDecimal PL601;
    
	/**
	 * 归属于母公司所有者(或股东)的净利润
	 */
    @Column(length=20)
    private BigDecimal PL600;
    
	/**
	 * 加：年初未分配利润
	 */
    @Column(length=20)
    private BigDecimal PL701;
    
	/**
	 * 加：其他转入
	 */
    @Column(length=20)
    private BigDecimal PL702;
    
	/**
	 * 可供分配的利润
	 */
    @Column(length=20)
    private BigDecimal PL703_0;
    
	/**
	 * 减：提取法定盈余公积
	 */
    @Column(length=20)
    private BigDecimal PL703_1;
    
	/**
	 * 减：提取法定公益金
	 */
    @Column(length=20)
    private BigDecimal PL703_2;
    
	/**
	 * 减：职工奖金福利
	 */
    @Column(length=20)
    private BigDecimal PL703_3;
    
	/**
	 * 减：提取企业发展基金
	 */
    @Column(length=20)
    private BigDecimal PL703_4;
    
	/**
	 * 减：提取储备基金
	 */
    @Column(length=20)
    private BigDecimal PL703_5;
    
	/**
	 * 加：其他
	 */
    @Column(length=20)
    private BigDecimal PL708;
    
	/**
	 * 可供投资者分配的利润
	 */
    @Column(length=20)
    private BigDecimal PL703;
    
	/**
	 * 减：应付优先股股利
	 */
    @Column(length=20)
    private BigDecimal PL704;
    
	/**
	 * 减：提取任意盈余公积
	 */
    @Column(length=20)
    private BigDecimal PL705;
    
	/**
	 * 减：应付普通股股利
	 */
    @Column(length=20)
    private BigDecimal PL706;
    
	/**
	 * 减：转作资本(或股本)的普通股股利
	 */
    @Column(length=20)
    private BigDecimal PL707;
    
	/**
	 * 未分配利润
	 */
    @Column(length=20)
    private BigDecimal PL700;
    
	/**
	 * 销售商品、提供劳务收到的现金
	 */
    @Column(length=20)
    private BigDecimal CF101;
    
	/**
	 * 收到的税费返还
	 */
    @Column(length=20)
    private BigDecimal CF102;
    
	/**
	 * 收到其他与经营活动有关的现金
	 */
    @Column(length=20)
    private BigDecimal CF103;
    
	/**
	 * 经营活动现金流入小计
	 */
    @Column(length=20)
    private BigDecimal CF100;
    
	/**
	 * 购买商品、接受劳务支付的现金
	 */
    @Column(length=20)
    private BigDecimal CF201;
    
	/**
	 * 支付给职工以及为职工支付的现金
	 */
    @Column(length=20)
    private BigDecimal CF202;
    
	/**
	 * 支付的各项税费
	 */
    @Column(length=20)
    private BigDecimal CF203;
    
	/**
	 * 支付其他与经营活动有关的现金
	 */
    @Column(length=20)
    private BigDecimal CF204;
    
	/**
	 * 经营活动现金流出小计
	 */
    @Column(length=20)
    private BigDecimal CF200;
    
	/**
	 * 经营活动产生的现金流量净额
	 */
    @Column(length=20)
    private BigDecimal CF001;
    
	/**
	 * 收回投资收到的现金
	 */
    @Column(length=20)
    private BigDecimal CF301;
    
	/**
	 * 取得投资收益收到的现金
	 */
    @Column(length=20)
    private BigDecimal CF302;
    
	/**
	 * 处置固定资产、无形资产和其他长期资产收回的现金净额
	 */
    @Column(length=20)
    private BigDecimal CF303;
    
	/**
	 * 处置子公司及其他营业单位收到的现金净额
	 */
    @Column(length=20)
    private BigDecimal CF305;
    
	/**
	 * 收到其他与投资活动有关的现金
	 */
    @Column(length=20)
    private BigDecimal CF304;
    
	/**
	 * 投资活动现金流入小计
	 */
    @Column(length=20)
    private BigDecimal CF300;
    
	/**
	 * 购建固定资产、无形资产和其他长期资产支付的现金
	 */
    @Column(length=20)
    private BigDecimal CF401;
    
	/**
	 * 投资支付的现金
	 */
    @Column(length=20)
    private BigDecimal CF402;
    
	/**
	 * 取得子公司及其他营业单位支付的现金净额
	 */
    @Column(length=20)
    private BigDecimal CF404;
    
	/**
	 * 支付其他与投资活动有关的现金
	 */
    @Column(length=20)
    private BigDecimal CF403;
    
	/**
	 * 投资活动现金流出小计
	 */
    @Column(length=20)
    private BigDecimal CF400;
    
	/**
	 * 投资活动产生的现金流量净额
	 */
    @Column(length=20)
    private BigDecimal CF002;
    
	/**
	 * 吸收投资收到的现金
	 */
    @Column(length=20)
    private BigDecimal CF501;
    
	/**
	 * 取得借款收到的现金
	 */
    @Column(length=20)
    private BigDecimal CF502;
    
	/**
	 * 发行债券收到的现金
	 */
    @Column(length=20)
    private BigDecimal CF504;
    
	/**
	 * 收到其他与筹资活动有关的现金
	 */
    @Column(length=20)
    private BigDecimal CF503;
    
	/**
	 * 筹资活动现金流入小计
	 */
    @Column(length=20)
    private BigDecimal CF500;
    
	/**
	 * 偿还债务支付的现金
	 */
    @Column(length=20)
    private BigDecimal CF601;
    
	/**
	 * 分配股利、利润或偿付利息支付的现金
	 */
    @Column(length=20)
    private BigDecimal CF602;
    
	/**
	 * 支付其他与筹资活动有关的现金
	 */
    @Column(length=20)
    private BigDecimal CF603;
    
	/**
	 * 筹资活动现金流出小计
	 */
    @Column(length=20)
    private BigDecimal CF600;
    
	/**
	 * 筹资活动产生的现金流量净额
	 */
    @Column(length=20)
    private BigDecimal CF003;
    
	/**
	 * 汇率变动对现金及现金等价物的影响
	 */
    @Column(length=20)
    private BigDecimal CF004;
    
	/**
	 * 现金及现金等价物净增加额
	 */
    @Column(length=20)
    private BigDecimal CF005;
    
	/**
	 * 加:期初现金及现金等价物余额
	 */
    @Column(length=20)
    private BigDecimal CF006;
    
	/**
	 * 期末现金及现金等价物余额
	 */
    @Column(length=20)
    private BigDecimal CF007;
    
	/**
	 * 净利润
	 */
    @Column(length=20)
    private BigDecimal CF901;
    
	/**
	 * 加:资产减值准备
	 */
    @Column(length=20)
    private BigDecimal CF902;
    
	/**
	 * 固定资产折旧、油气资产折耗、生产性生物资产折旧
	 */
    @Column(length=20)
    private BigDecimal CF903;
    
	/**
	 * 无形资产摊销
	 */
    @Column(length=20)
    private BigDecimal CF904;
    
	/**
	 * 长期待摊费用摊销
	 */
    @Column(length=20)
    private BigDecimal CF905;
    
	/**
	 * 处置固定资产、无形资产和其他长期资产的损失
	 */
    @Column(length=20)
    private BigDecimal CF906;
    
	/**
	 * 固定资产报废损失
	 */
    @Column(length=20)
    private BigDecimal CF907;
    
	/**
	 * 公允价值变动损失
	 */
    @Column(length=20)
    private BigDecimal CF908;
    
	/**
	 * 财务费用
	 */
    @Column(length=20)
    private BigDecimal CF909;
    
	/**
	 * 投资损失
	 */
    @Column(length=20)
    private BigDecimal CF910;
    
	/**
	 * 递延所得税资产减少
	 */
    @Column(length=20)
    private BigDecimal CF911;
    
	/**
	 * 递延所得税负债增加
	 */
    @Column(length=20)
    private BigDecimal CF912;
    
	/**
	 * 存货的减少
	 */
    @Column(length=20)
    private BigDecimal CF913;
    
	/**
	 * 经营性应收项目的减少
	 */
    @Column(length=20)
    private BigDecimal CF914;
    
	/**
	 * 经营性应付项目的增加
	 */
    @Column(length=20)
    private BigDecimal CF915;
    
	/**
	 * 其他
	 */
    @Column(length=20)
    private BigDecimal CF916;
    
	/**
	 * (间接)经营活动产生的现金流量净额
	 */
    @Column(length=20)
    private BigDecimal CF917;
    
	/**
	 * 债务转为资本
	 */
    @Column(length=20)
    private BigDecimal CF918;
    
	/**
	 * 一年内到期的可转换公司债券
	 */
    @Column(length=20)
    private BigDecimal CF919;
    
	/**
	 * 融资租入固定资产
	 */
    @Column(length=20)
    private BigDecimal CF920;
    
	/**
	 * 现金的期末余额
	 */
    @Column(length=20)
    private BigDecimal CF921;
    
	/**
	 * 减:现金的期初余额
	 */
    @Column(length=20)
    private BigDecimal CF922;
    
	/**
	 * 加:现金等价物的期末余额
	 */
    @Column(length=20)
    private BigDecimal CF923;
    
	/**
	 * 减:现金等价物的期初余额
	 */
    @Column(length=20)
    private BigDecimal CF924;
    
	/**
	 * (间接)现金及现金等价物净增加额
	 */
    @Column(length=20)
    private BigDecimal CF925;
    
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
	public BigDecimal getBS101() {
		return BS101;
	}
	public void setBS101(BigDecimal bS101) {
		BS101 = bS101;
	}
	public BigDecimal getBS101_1() {
		return BS101_1;
	}
	public void setBS101_1(BigDecimal bS101_1) {
		BS101_1 = bS101_1;
	}
	public BigDecimal getBS101_2() {
		return BS101_2;
	}
	public void setBS101_2(BigDecimal bS101_2) {
		BS101_2 = bS101_2;
	}
	public BigDecimal getBS102() {
		return BS102;
	}
	public void setBS102(BigDecimal bS102) {
		BS102 = bS102;
	}
	public BigDecimal getBS102_1() {
		return BS102_1;
	}
	public void setBS102_1(BigDecimal bS102_1) {
		BS102_1 = bS102_1;
	}
	public BigDecimal getBS102_2() {
		return BS102_2;
	}
	public void setBS102_2(BigDecimal bS102_2) {
		BS102_2 = bS102_2;
	}
	public BigDecimal getBS102_3() {
		return BS102_3;
	}
	public void setBS102_3(BigDecimal bS102_3) {
		BS102_3 = bS102_3;
	}
	public BigDecimal getBS102_4() {
		return BS102_4;
	}
	public void setBS102_4(BigDecimal bS102_4) {
		BS102_4 = bS102_4;
	}
	public BigDecimal getBS103() {
		return BS103;
	}
	public void setBS103(BigDecimal bS103) {
		BS103 = bS103;
	}
	public BigDecimal getBS104() {
		return BS104;
	}
	public void setBS104(BigDecimal bS104) {
		BS104 = bS104;
	}
	public BigDecimal getBS104_1() {
		return BS104_1;
	}
	public void setBS104_1(BigDecimal bS104_1) {
		BS104_1 = bS104_1;
	}
	public BigDecimal getBS104_2() {
		return BS104_2;
	}
	public void setBS104_2(BigDecimal bS104_2) {
		BS104_2 = bS104_2;
	}
	public BigDecimal getBS105() {
		return BS105;
	}
	public void setBS105(BigDecimal bS105) {
		BS105 = bS105;
	}
	public BigDecimal getBS105_1() {
		return BS105_1;
	}
	public void setBS105_1(BigDecimal bS105_1) {
		BS105_1 = bS105_1;
	}
	public BigDecimal getBS106() {
		return BS106;
	}
	public void setBS106(BigDecimal bS106) {
		BS106 = bS106;
	}
	public BigDecimal getBS107() {
		return BS107;
	}
	public void setBS107(BigDecimal bS107) {
		BS107 = bS107;
	}
	public BigDecimal getBS108() {
		return BS108;
	}
	public void setBS108(BigDecimal bS108) {
		BS108 = bS108;
	}
	public BigDecimal getBS108_1() {
		return BS108_1;
	}
	public void setBS108_1(BigDecimal bS108_1) {
		BS108_1 = bS108_1;
	}
	public BigDecimal getBS108_2() {
		return BS108_2;
	}
	public void setBS108_2(BigDecimal bS108_2) {
		BS108_2 = bS108_2;
	}
	public BigDecimal getBS108_3() {
		return BS108_3;
	}
	public void setBS108_3(BigDecimal bS108_3) {
		BS108_3 = bS108_3;
	}
	public BigDecimal getBS109() {
		return BS109;
	}
	public void setBS109(BigDecimal bS109) {
		BS109 = bS109;
	}
	public BigDecimal getBS109_1() {
		return BS109_1;
	}
	public void setBS109_1(BigDecimal bS109_1) {
		BS109_1 = bS109_1;
	}
	public BigDecimal getBS109_2() {
		return BS109_2;
	}
	public void setBS109_2(BigDecimal bS109_2) {
		BS109_2 = bS109_2;
	}
	public BigDecimal getBS109_3() {
		return BS109_3;
	}
	public void setBS109_3(BigDecimal bS109_3) {
		BS109_3 = bS109_3;
	}
	public BigDecimal getBS109_4() {
		return BS109_4;
	}
	public void setBS109_4(BigDecimal bS109_4) {
		BS109_4 = bS109_4;
	}
	public BigDecimal getBS109_5() {
		return BS109_5;
	}
	public void setBS109_5(BigDecimal bS109_5) {
		BS109_5 = bS109_5;
	}
	public BigDecimal getBS109_6() {
		return BS109_6;
	}
	public void setBS109_6(BigDecimal bS109_6) {
		BS109_6 = bS109_6;
	}
	public BigDecimal getBS110() {
		return BS110;
	}
	public void setBS110(BigDecimal bS110) {
		BS110 = bS110;
	}
	public BigDecimal getBS111() {
		return BS111;
	}
	public void setBS111(BigDecimal bS111) {
		BS111 = bS111;
	}
	public BigDecimal getBS100() {
		return BS100;
	}
	public void setBS100(BigDecimal bS100) {
		BS100 = bS100;
	}
	public BigDecimal getBS2011() {
		return BS2011;
	}
	public void setBS2011(BigDecimal bS2011) {
		BS2011 = bS2011;
	}
	public BigDecimal getBS201() {
		return BS201;
	}
	public void setBS201(BigDecimal bS201) {
		BS201 = bS201;
	}
	public BigDecimal getBS202() {
		return BS202;
	}
	public void setBS202(BigDecimal bS202) {
		BS202 = bS202;
	}
	public BigDecimal getBS203() {
		return BS203;
	}
	public void setBS203(BigDecimal bS203) {
		BS203 = bS203;
	}
	public BigDecimal getBS204() {
		return BS204;
	}
	public void setBS204(BigDecimal bS204) {
		BS204 = bS204;
	}
	public BigDecimal getBS205() {
		return BS205;
	}
	public void setBS205(BigDecimal bS205) {
		BS205 = bS205;
	}
	public BigDecimal getBS2012() {
		return BS2012;
	}
	public void setBS2012(BigDecimal bS2012) {
		BS2012 = bS2012;
	}
	public BigDecimal getBS206() {
		return BS206;
	}
	public void setBS206(BigDecimal bS206) {
		BS206 = bS206;
	}
	public BigDecimal getBS210() {
		return BS210;
	}
	public void setBS210(BigDecimal bS210) {
		BS210 = bS210;
	}
	public BigDecimal getBS211() {
		return BS211;
	}
	public void setBS211(BigDecimal bS211) {
		BS211 = bS211;
	}
	public BigDecimal getBS211_1() {
		return BS211_1;
	}
	public void setBS211_1(BigDecimal bS211_1) {
		BS211_1 = bS211_1;
	}
	public BigDecimal getBS211_2() {
		return BS211_2;
	}
	public void setBS211_2(BigDecimal bS211_2) {
		BS211_2 = bS211_2;
	}
	public BigDecimal getBS211_3() {
		return BS211_3;
	}
	public void setBS211_3(BigDecimal bS211_3) {
		BS211_3 = bS211_3;
	}
	public BigDecimal getBS2013() {
		return BS2013;
	}
	public void setBS2013(BigDecimal bS2013) {
		BS2013 = bS2013;
	}
	public BigDecimal getBS2014() {
		return BS2014;
	}
	public void setBS2014(BigDecimal bS2014) {
		BS2014 = bS2014;
	}
	public BigDecimal getBS212() {
		return BS212;
	}
	public void setBS212(BigDecimal bS212) {
		BS212 = bS212;
	}
	public BigDecimal getBS220() {
		return BS220;
	}
	public void setBS220(BigDecimal bS220) {
		BS220 = bS220;
	}
	public BigDecimal getBS221() {
		return BS221;
	}
	public void setBS221(BigDecimal bS221) {
		BS221 = bS221;
	}
	public BigDecimal getBS222() {
		return BS222;
	}
	public void setBS222(BigDecimal bS222) {
		BS222 = bS222;
	}
	public BigDecimal getBS223() {
		return BS223;
	}
	public void setBS223(BigDecimal bS223) {
		BS223 = bS223;
	}
	public BigDecimal getBS224() {
		return BS224;
	}
	public void setBS224(BigDecimal bS224) {
		BS224 = bS224;
	}
	public BigDecimal getBS225() {
		return BS225;
	}
	public void setBS225(BigDecimal bS225) {
		BS225 = bS225;
	}
	public BigDecimal getBS230() {
		return BS230;
	}
	public void setBS230(BigDecimal bS230) {
		BS230 = bS230;
	}
	public BigDecimal getBS240() {
		return BS240;
	}
	public void setBS240(BigDecimal bS240) {
		BS240 = bS240;
	}
	public BigDecimal getBS240_1() {
		return BS240_1;
	}
	public void setBS240_1(BigDecimal bS240_1) {
		BS240_1 = bS240_1;
	}
	public BigDecimal getBS240_2() {
		return BS240_2;
	}
	public void setBS240_2(BigDecimal bS240_2) {
		BS240_2 = bS240_2;
	}
	public BigDecimal getBS251() {
		return BS251;
	}
	public void setBS251(BigDecimal bS251) {
		BS251 = bS251;
	}
	public BigDecimal getBS252() {
		return BS252;
	}
	public void setBS252(BigDecimal bS252) {
		BS252 = bS252;
	}
	public BigDecimal getBS253() {
		return BS253;
	}
	public void setBS253(BigDecimal bS253) {
		BS253 = bS253;
	}
	public BigDecimal getBS254() {
		return BS254;
	}
	public void setBS254(BigDecimal bS254) {
		BS254 = bS254;
	}
	public BigDecimal getBS255() {
		return BS255;
	}
	public void setBS255(BigDecimal bS255) {
		BS255 = bS255;
	}
	public BigDecimal getBS200() {
		return BS200;
	}
	public void setBS200(BigDecimal bS200) {
		BS200 = bS200;
	}
	public BigDecimal getBS001() {
		return BS001;
	}
	public void setBS001(BigDecimal bS001) {
		BS001 = bS001;
	}
	public BigDecimal getBS301() {
		return BS301;
	}
	public void setBS301(BigDecimal bS301) {
		BS301 = bS301;
	}
	public BigDecimal getBS302() {
		return BS302;
	}
	public void setBS302(BigDecimal bS302) {
		BS302 = bS302;
	}
	public BigDecimal getBS303() {
		return BS303;
	}
	public void setBS303(BigDecimal bS303) {
		BS303 = bS303;
	}
	public BigDecimal getBS304() {
		return BS304;
	}
	public void setBS304(BigDecimal bS304) {
		BS304 = bS304;
	}
	public BigDecimal getBS304_1() {
		return BS304_1;
	}
	public void setBS304_1(BigDecimal bS304_1) {
		BS304_1 = bS304_1;
	}
	public BigDecimal getBS305() {
		return BS305;
	}
	public void setBS305(BigDecimal bS305) {
		BS305 = bS305;
	}
	public BigDecimal getBS305_1() {
		return BS305_1;
	}
	public void setBS305_1(BigDecimal bS305_1) {
		BS305_1 = bS305_1;
	}
	public BigDecimal getBS306() {
		return BS306;
	}
	public void setBS306(BigDecimal bS306) {
		BS306 = bS306;
	}
	public BigDecimal getBS307() {
		return BS307;
	}
	public void setBS307(BigDecimal bS307) {
		BS307 = bS307;
	}
	public BigDecimal getBS308() {
		return BS308;
	}
	public void setBS308(BigDecimal bS308) {
		BS308 = bS308;
	}
	public BigDecimal getBS309() {
		return BS309;
	}
	public void setBS309(BigDecimal bS309) {
		BS309 = bS309;
	}
	public BigDecimal getBS310() {
		return BS310;
	}
	public void setBS310(BigDecimal bS310) {
		BS310 = bS310;
	}
	public BigDecimal getBS310_1() {
		return BS310_1;
	}
	public void setBS310_1(BigDecimal bS310_1) {
		BS310_1 = bS310_1;
	}
	public BigDecimal getBS310_2() {
		return BS310_2;
	}
	public void setBS310_2(BigDecimal bS310_2) {
		BS310_2 = bS310_2;
	}
	public BigDecimal getBS311() {
		return BS311;
	}
	public void setBS311(BigDecimal bS311) {
		BS311 = bS311;
	}
	public BigDecimal getBS311_1() {
		return BS311_1;
	}
	public void setBS311_1(BigDecimal bS311_1) {
		BS311_1 = bS311_1;
	}
	public BigDecimal getBS311_2() {
		return BS311_2;
	}
	public void setBS311_2(BigDecimal bS311_2) {
		BS311_2 = bS311_2;
	}
	public BigDecimal getBS312() {
		return BS312;
	}
	public void setBS312(BigDecimal bS312) {
		BS312 = bS312;
	}
	public BigDecimal getBS313() {
		return BS313;
	}
	public void setBS313(BigDecimal bS313) {
		BS313 = bS313;
	}
	public BigDecimal getBS300() {
		return BS300;
	}
	public void setBS300(BigDecimal bS300) {
		BS300 = bS300;
	}
	public BigDecimal getBS401() {
		return BS401;
	}
	public void setBS401(BigDecimal bS401) {
		BS401 = bS401;
	}
	public BigDecimal getBS401_1() {
		return BS401_1;
	}
	public void setBS401_1(BigDecimal bS401_1) {
		BS401_1 = bS401_1;
	}
	public BigDecimal getBS401_2() {
		return BS401_2;
	}
	public void setBS401_2(BigDecimal bS401_2) {
		BS401_2 = bS401_2;
	}
	public BigDecimal getBS401_3() {
		return BS401_3;
	}
	public void setBS401_3(BigDecimal bS401_3) {
		BS401_3 = bS401_3;
	}
	public BigDecimal getBS402() {
		return BS402;
	}
	public void setBS402(BigDecimal bS402) {
		BS402 = bS402;
	}
	public BigDecimal getBS403() {
		return BS403;
	}
	public void setBS403(BigDecimal bS403) {
		BS403 = bS403;
	}
	public BigDecimal getBS404() {
		return BS404;
	}
	public void setBS404(BigDecimal bS404) {
		BS404 = bS404;
	}
	public BigDecimal getBS405() {
		return BS405;
	}
	public void setBS405(BigDecimal bS405) {
		BS405 = bS405;
	}
	public BigDecimal getBS406() {
		return BS406;
	}
	public void setBS406(BigDecimal bS406) {
		BS406 = bS406;
	}
	public BigDecimal getBS407() {
		return BS407;
	}
	public void setBS407(BigDecimal bS407) {
		BS407 = bS407;
	}
	public BigDecimal getBS400() {
		return BS400;
	}
	public void setBS400(BigDecimal bS400) {
		BS400 = bS400;
	}
	public BigDecimal getBS002() {
		return BS002;
	}
	public void setBS002(BigDecimal bS002) {
		BS002 = bS002;
	}
	public BigDecimal getBS501() {
		return BS501;
	}
	public void setBS501(BigDecimal bS501) {
		BS501 = bS501;
	}
	public BigDecimal getBS502() {
		return BS502;
	}
	public void setBS502(BigDecimal bS502) {
		BS502 = bS502;
	}
	public BigDecimal getBS503() {
		return BS503;
	}
	public void setBS503(BigDecimal bS503) {
		BS503 = bS503;
	}
	public BigDecimal getBS2015() {
		return BS2015;
	}
	public void setBS2015(BigDecimal bS2015) {
		BS2015 = bS2015;
	}
	public BigDecimal getBS504() {
		return BS504;
	}
	public void setBS504(BigDecimal bS504) {
		BS504 = bS504;
	}
	public BigDecimal getBS2016() {
		return BS2016;
	}
	public void setBS2016(BigDecimal bS2016) {
		BS2016 = bS2016;
	}
	public BigDecimal getBS505() {
		return BS505;
	}
	public void setBS505(BigDecimal bS505) {
		BS505 = bS505;
	}
	public BigDecimal getBS506() {
		return BS506;
	}
	public void setBS506(BigDecimal bS506) {
		BS506 = bS506;
	}
	public BigDecimal getBS003() {
		return BS003;
	}
	public void setBS003(BigDecimal bS003) {
		BS003 = bS003;
	}
	public BigDecimal getBS507() {
		return BS507;
	}
	public void setBS507(BigDecimal bS507) {
		BS507 = bS507;
	}
	public BigDecimal getBS004() {
		return BS004;
	}
	public void setBS004(BigDecimal bS004) {
		BS004 = bS004;
	}
	public BigDecimal getBS005() {
		return BS005;
	}
	public void setBS005(BigDecimal bS005) {
		BS005 = bS005;
	}
	public BigDecimal getPL101() {
		return PL101;
	}
	public void setPL101(BigDecimal pL101) {
		PL101 = pL101;
	}
	public BigDecimal getPL102() {
		return PL102;
	}
	public void setPL102(BigDecimal pL102) {
		PL102 = pL102;
	}
	public BigDecimal getPL103() {
		return PL103;
	}
	public void setPL103(BigDecimal pL103) {
		PL103 = pL103;
	}
	public BigDecimal getPL201() {
		return PL201;
	}
	public void setPL201(BigDecimal pL201) {
		PL201 = pL201;
	}
	public BigDecimal getPL202_5() {
		return PL202_5;
	}
	public void setPL202_5(BigDecimal pL202_5) {
		PL202_5 = pL202_5;
	}
	public BigDecimal getPL202() {
		return PL202;
	}
	public void setPL202(BigDecimal pL202) {
		PL202 = pL202;
	}
	public BigDecimal getPL203() {
		return PL203;
	}
	public void setPL203(BigDecimal pL203) {
		PL203 = pL203;
	}
	public BigDecimal getPL203_1() {
		return PL203_1;
	}
	public void setPL203_1(BigDecimal pL203_1) {
		PL203_1 = pL203_1;
	}
	public BigDecimal getPL204() {
		return PL204;
	}
	public void setPL204(BigDecimal pL204) {
		PL204 = pL204;
	}
	public BigDecimal getPL205() {
		return PL205;
	}
	public void setPL205(BigDecimal pL205) {
		PL205 = pL205;
	}
	public BigDecimal getPL200() {
		return PL200;
	}
	public void setPL200(BigDecimal pL200) {
		PL200 = pL200;
	}
	public BigDecimal getPL210() {
		return PL210;
	}
	public void setPL210(BigDecimal pL210) {
		PL210 = pL210;
	}
	public BigDecimal getPL211() {
		return PL211;
	}
	public void setPL211(BigDecimal pL211) {
		PL211 = pL211;
	}
	public BigDecimal getPL212() {
		return PL212;
	}
	public void setPL212(BigDecimal pL212) {
		PL212 = pL212;
	}
	public BigDecimal getPL220() {
		return PL220;
	}
	public void setPL220(BigDecimal pL220) {
		PL220 = pL220;
	}
	public BigDecimal getPL301() {
		return PL301;
	}
	public void setPL301(BigDecimal pL301) {
		PL301 = pL301;
	}
	public BigDecimal getPL301_1() {
		return PL301_1;
	}
	public void setPL301_1(BigDecimal pL301_1) {
		PL301_1 = pL301_1;
	}
	public BigDecimal getPL301_2() {
		return PL301_2;
	}
	public void setPL301_2(BigDecimal pL301_2) {
		PL301_2 = pL301_2;
	}
	public BigDecimal getPL301_3() {
		return PL301_3;
	}
	public void setPL301_3(BigDecimal pL301_3) {
		PL301_3 = pL301_3;
	}
	public BigDecimal getPL301_4() {
		return PL301_4;
	}
	public void setPL301_4(BigDecimal pL301_4) {
		PL301_4 = pL301_4;
	}
	public BigDecimal getPL301_5() {
		return PL301_5;
	}
	public void setPL301_5(BigDecimal pL301_5) {
		PL301_5 = pL301_5;
	}
	public BigDecimal getPL301_6() {
		return PL301_6;
	}
	public void setPL301_6(BigDecimal pL301_6) {
		PL301_6 = pL301_6;
	}
	public BigDecimal getPL301_7() {
		return PL301_7;
	}
	public void setPL301_7(BigDecimal pL301_7) {
		PL301_7 = pL301_7;
	}
	public BigDecimal getPL301_8() {
		return PL301_8;
	}
	public void setPL301_8(BigDecimal pL301_8) {
		PL301_8 = pL301_8;
	}
	public BigDecimal getPL301_9() {
		return PL301_9;
	}
	public void setPL301_9(BigDecimal pL301_9) {
		PL301_9 = pL301_9;
	}
	public BigDecimal getPL301_10() {
		return PL301_10;
	}
	public void setPL301_10(BigDecimal pL301_10) {
		PL301_10 = pL301_10;
	}
	public BigDecimal getPL301_11() {
		return PL301_11;
	}
	public void setPL301_11(BigDecimal pL301_11) {
		PL301_11 = pL301_11;
	}
	public BigDecimal getPL301_12() {
		return PL301_12;
	}
	public void setPL301_12(BigDecimal pL301_12) {
		PL301_12 = pL301_12;
	}
	public BigDecimal getPL300() {
		return PL300;
	}
	public void setPL300(BigDecimal pL300) {
		PL300 = pL300;
	}
	public BigDecimal getPL401() {
		return PL401;
	}
	public void setPL401(BigDecimal pL401) {
		PL401 = pL401;
	}
	public BigDecimal getPL402() {
		return PL402;
	}
	public void setPL402(BigDecimal pL402) {
		PL402 = pL402;
	}
	public BigDecimal getPL402_1() {
		return PL402_1;
	}
	public void setPL402_1(BigDecimal pL402_1) {
		PL402_1 = pL402_1;
	}
	public BigDecimal getPL400() {
		return PL400;
	}
	public void setPL400(BigDecimal pL400) {
		PL400 = pL400;
	}
	public BigDecimal getPL501() {
		return PL501;
	}
	public void setPL501(BigDecimal pL501) {
		PL501 = pL501;
	}
	public BigDecimal getPL500() {
		return PL500;
	}
	public void setPL500(BigDecimal pL500) {
		PL500 = pL500;
	}
	public BigDecimal getPL601() {
		return PL601;
	}
	public void setPL601(BigDecimal pL601) {
		PL601 = pL601;
	}
	public BigDecimal getPL600() {
		return PL600;
	}
	public void setPL600(BigDecimal pL600) {
		PL600 = pL600;
	}
	public BigDecimal getPL701() {
		return PL701;
	}
	public void setPL701(BigDecimal pL701) {
		PL701 = pL701;
	}
	public BigDecimal getPL702() {
		return PL702;
	}
	public void setPL702(BigDecimal pL702) {
		PL702 = pL702;
	}
	public BigDecimal getPL703_0() {
		return PL703_0;
	}
	public void setPL703_0(BigDecimal pL703_0) {
		PL703_0 = pL703_0;
	}
	public BigDecimal getPL703_1() {
		return PL703_1;
	}
	public void setPL703_1(BigDecimal pL703_1) {
		PL703_1 = pL703_1;
	}
	public BigDecimal getPL703_2() {
		return PL703_2;
	}
	public void setPL703_2(BigDecimal pL703_2) {
		PL703_2 = pL703_2;
	}
	public BigDecimal getPL703_3() {
		return PL703_3;
	}
	public void setPL703_3(BigDecimal pL703_3) {
		PL703_3 = pL703_3;
	}
	public BigDecimal getPL703_4() {
		return PL703_4;
	}
	public void setPL703_4(BigDecimal pL703_4) {
		PL703_4 = pL703_4;
	}
	public BigDecimal getPL703_5() {
		return PL703_5;
	}
	public void setPL703_5(BigDecimal pL703_5) {
		PL703_5 = pL703_5;
	}
	public BigDecimal getPL708() {
		return PL708;
	}
	public void setPL708(BigDecimal pL708) {
		PL708 = pL708;
	}
	public BigDecimal getPL703() {
		return PL703;
	}
	public void setPL703(BigDecimal pL703) {
		PL703 = pL703;
	}
	public BigDecimal getPL704() {
		return PL704;
	}
	public void setPL704(BigDecimal pL704) {
		PL704 = pL704;
	}
	public BigDecimal getPL705() {
		return PL705;
	}
	public void setPL705(BigDecimal pL705) {
		PL705 = pL705;
	}
	public BigDecimal getPL706() {
		return PL706;
	}
	public void setPL706(BigDecimal pL706) {
		PL706 = pL706;
	}
	public BigDecimal getPL707() {
		return PL707;
	}
	public void setPL707(BigDecimal pL707) {
		PL707 = pL707;
	}
	public BigDecimal getPL700() {
		return PL700;
	}
	public void setPL700(BigDecimal pL700) {
		PL700 = pL700;
	}
	public BigDecimal getCF101() {
		return CF101;
	}
	public void setCF101(BigDecimal cF101) {
		CF101 = cF101;
	}
	public BigDecimal getCF102() {
		return CF102;
	}
	public void setCF102(BigDecimal cF102) {
		CF102 = cF102;
	}
	public BigDecimal getCF103() {
		return CF103;
	}
	public void setCF103(BigDecimal cF103) {
		CF103 = cF103;
	}
	public BigDecimal getCF100() {
		return CF100;
	}
	public void setCF100(BigDecimal cF100) {
		CF100 = cF100;
	}
	public BigDecimal getCF201() {
		return CF201;
	}
	public void setCF201(BigDecimal cF201) {
		CF201 = cF201;
	}
	public BigDecimal getCF202() {
		return CF202;
	}
	public void setCF202(BigDecimal cF202) {
		CF202 = cF202;
	}
	public BigDecimal getCF203() {
		return CF203;
	}
	public void setCF203(BigDecimal cF203) {
		CF203 = cF203;
	}
	public BigDecimal getCF204() {
		return CF204;
	}
	public void setCF204(BigDecimal cF204) {
		CF204 = cF204;
	}
	public BigDecimal getCF200() {
		return CF200;
	}
	public void setCF200(BigDecimal cF200) {
		CF200 = cF200;
	}
	public BigDecimal getCF001() {
		return CF001;
	}
	public void setCF001(BigDecimal cF001) {
		CF001 = cF001;
	}
	public BigDecimal getCF301() {
		return CF301;
	}
	public void setCF301(BigDecimal cF301) {
		CF301 = cF301;
	}
	public BigDecimal getCF302() {
		return CF302;
	}
	public void setCF302(BigDecimal cF302) {
		CF302 = cF302;
	}
	public BigDecimal getCF303() {
		return CF303;
	}
	public void setCF303(BigDecimal cF303) {
		CF303 = cF303;
	}
	public BigDecimal getCF305() {
		return CF305;
	}
	public void setCF305(BigDecimal cF305) {
		CF305 = cF305;
	}
	public BigDecimal getCF304() {
		return CF304;
	}
	public void setCF304(BigDecimal cF304) {
		CF304 = cF304;
	}
	public BigDecimal getCF300() {
		return CF300;
	}
	public void setCF300(BigDecimal cF300) {
		CF300 = cF300;
	}
	public BigDecimal getCF401() {
		return CF401;
	}
	public void setCF401(BigDecimal cF401) {
		CF401 = cF401;
	}
	public BigDecimal getCF402() {
		return CF402;
	}
	public void setCF402(BigDecimal cF402) {
		CF402 = cF402;
	}
	public BigDecimal getCF404() {
		return CF404;
	}
	public void setCF404(BigDecimal cF404) {
		CF404 = cF404;
	}
	public BigDecimal getCF403() {
		return CF403;
	}
	public void setCF403(BigDecimal cF403) {
		CF403 = cF403;
	}
	public BigDecimal getCF400() {
		return CF400;
	}
	public void setCF400(BigDecimal cF400) {
		CF400 = cF400;
	}
	public BigDecimal getCF002() {
		return CF002;
	}
	public void setCF002(BigDecimal cF002) {
		CF002 = cF002;
	}
	public BigDecimal getCF501() {
		return CF501;
	}
	public void setCF501(BigDecimal cF501) {
		CF501 = cF501;
	}
	public BigDecimal getCF502() {
		return CF502;
	}
	public void setCF502(BigDecimal cF502) {
		CF502 = cF502;
	}
	public BigDecimal getCF504() {
		return CF504;
	}
	public void setCF504(BigDecimal cF504) {
		CF504 = cF504;
	}
	public BigDecimal getCF503() {
		return CF503;
	}
	public void setCF503(BigDecimal cF503) {
		CF503 = cF503;
	}
	public BigDecimal getCF500() {
		return CF500;
	}
	public void setCF500(BigDecimal cF500) {
		CF500 = cF500;
	}
	public BigDecimal getCF601() {
		return CF601;
	}
	public void setCF601(BigDecimal cF601) {
		CF601 = cF601;
	}
	public BigDecimal getCF602() {
		return CF602;
	}
	public void setCF602(BigDecimal cF602) {
		CF602 = cF602;
	}
	public BigDecimal getCF603() {
		return CF603;
	}
	public void setCF603(BigDecimal cF603) {
		CF603 = cF603;
	}
	public BigDecimal getCF600() {
		return CF600;
	}
	public void setCF600(BigDecimal cF600) {
		CF600 = cF600;
	}
	public BigDecimal getCF003() {
		return CF003;
	}
	public void setCF003(BigDecimal cF003) {
		CF003 = cF003;
	}
	public BigDecimal getCF004() {
		return CF004;
	}
	public void setCF004(BigDecimal cF004) {
		CF004 = cF004;
	}
	public BigDecimal getCF005() {
		return CF005;
	}
	public void setCF005(BigDecimal cF005) {
		CF005 = cF005;
	}
	public BigDecimal getCF006() {
		return CF006;
	}
	public void setCF006(BigDecimal cF006) {
		CF006 = cF006;
	}
	public BigDecimal getCF007() {
		return CF007;
	}
	public void setCF007(BigDecimal cF007) {
		CF007 = cF007;
	}
	public BigDecimal getCF901() {
		return CF901;
	}
	public void setCF901(BigDecimal cF901) {
		CF901 = cF901;
	}
	public BigDecimal getCF902() {
		return CF902;
	}
	public void setCF902(BigDecimal cF902) {
		CF902 = cF902;
	}
	public BigDecimal getCF903() {
		return CF903;
	}
	public void setCF903(BigDecimal cF903) {
		CF903 = cF903;
	}
	public BigDecimal getCF904() {
		return CF904;
	}
	public void setCF904(BigDecimal cF904) {
		CF904 = cF904;
	}
	public BigDecimal getCF905() {
		return CF905;
	}
	public void setCF905(BigDecimal cF905) {
		CF905 = cF905;
	}
	public BigDecimal getCF906() {
		return CF906;
	}
	public void setCF906(BigDecimal cF906) {
		CF906 = cF906;
	}
	public BigDecimal getCF907() {
		return CF907;
	}
	public void setCF907(BigDecimal cF907) {
		CF907 = cF907;
	}
	public BigDecimal getCF908() {
		return CF908;
	}
	public void setCF908(BigDecimal cF908) {
		CF908 = cF908;
	}
	public BigDecimal getCF909() {
		return CF909;
	}
	public void setCF909(BigDecimal cF909) {
		CF909 = cF909;
	}
	public BigDecimal getCF910() {
		return CF910;
	}
	public void setCF910(BigDecimal cF910) {
		CF910 = cF910;
	}
	public BigDecimal getCF911() {
		return CF911;
	}
	public void setCF911(BigDecimal cF911) {
		CF911 = cF911;
	}
	public BigDecimal getCF912() {
		return CF912;
	}
	public void setCF912(BigDecimal cF912) {
		CF912 = cF912;
	}
	public BigDecimal getCF913() {
		return CF913;
	}
	public void setCF913(BigDecimal cF913) {
		CF913 = cF913;
	}
	public BigDecimal getCF914() {
		return CF914;
	}
	public void setCF914(BigDecimal cF914) {
		CF914 = cF914;
	}
	public BigDecimal getCF915() {
		return CF915;
	}
	public void setCF915(BigDecimal cF915) {
		CF915 = cF915;
	}
	public BigDecimal getCF916() {
		return CF916;
	}
	public void setCF916(BigDecimal cF916) {
		CF916 = cF916;
	}
	public BigDecimal getCF917() {
		return CF917;
	}
	public void setCF917(BigDecimal cF917) {
		CF917 = cF917;
	}
	public BigDecimal getCF918() {
		return CF918;
	}
	public void setCF918(BigDecimal cF918) {
		CF918 = cF918;
	}
	public BigDecimal getCF919() {
		return CF919;
	}
	public void setCF919(BigDecimal cF919) {
		CF919 = cF919;
	}
	public BigDecimal getCF920() {
		return CF920;
	}
	public void setCF920(BigDecimal cF920) {
		CF920 = cF920;
	}
	public BigDecimal getCF921() {
		return CF921;
	}
	public void setCF921(BigDecimal cF921) {
		CF921 = cF921;
	}
	public BigDecimal getCF922() {
		return CF922;
	}
	public void setCF922(BigDecimal cF922) {
		CF922 = cF922;
	}
	public BigDecimal getCF923() {
		return CF923;
	}
	public void setCF923(BigDecimal cF923) {
		CF923 = cF923;
	}
	public BigDecimal getCF924() {
		return CF924;
	}
	public void setCF924(BigDecimal cF924) {
		CF924 = cF924;
	}
	public BigDecimal getCF925() {
		return CF925;
	}
	public void setCF925(BigDecimal cF925) {
		CF925 = cF925;
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
	       selectSql += ",Comp_ID";
	       selectSql += ",FIN_DATE";
	       selectSql += ",FIN_ENTITY";
	       selectSql += ",FIN_STATE_TYPE";
	       selectSql += ",FIN_PERIOD";
	       selectSql += ",BS101";
	       selectSql += ",BS101_1";
	       selectSql += ",BS101_2";
	       selectSql += ",BS102";
	       selectSql += ",BS102_1";
	       selectSql += ",BS102_2";
	       selectSql += ",BS102_3";
	       selectSql += ",BS102_4";
	       selectSql += ",BS103";
	       selectSql += ",BS104";
	       selectSql += ",BS104_1";
	       selectSql += ",BS104_2";
	       selectSql += ",BS105";
	       selectSql += ",BS105_1";
	       selectSql += ",BS106";
	       selectSql += ",BS107";
	       selectSql += ",BS108";
	       selectSql += ",BS108_1";
	       selectSql += ",BS108_2";
	       selectSql += ",BS108_3";
	       selectSql += ",BS109";
	       selectSql += ",BS109_1";
	       selectSql += ",BS109_2";
	       selectSql += ",BS109_3";
	       selectSql += ",BS109_4";
	       selectSql += ",BS109_5";
	       selectSql += ",BS109_6";
	       selectSql += ",BS110";
	       selectSql += ",BS111";
	       selectSql += ",BS100";
	       selectSql += ",BS2011";
	       selectSql += ",BS201";
	       selectSql += ",BS202";
	       selectSql += ",BS203";
	       selectSql += ",BS204";
	       selectSql += ",BS205";
	       selectSql += ",BS2012";
	       selectSql += ",BS206";
	       selectSql += ",BS210";
	       selectSql += ",BS211";
	       selectSql += ",BS211_1";
	       selectSql += ",BS211_2";
	       selectSql += ",BS211_3";
	       selectSql += ",BS2013";
	       selectSql += ",BS2014";
	       selectSql += ",BS212";
	       selectSql += ",BS220";
	       selectSql += ",BS221";
	       selectSql += ",BS222";
	       selectSql += ",BS223";
	       selectSql += ",BS224";
	       selectSql += ",BS225";
	       selectSql += ",BS230";
	       selectSql += ",BS240";
	       selectSql += ",BS240_1";
	       selectSql += ",BS240_2";
	       selectSql += ",BS251";
	       selectSql += ",BS252";
	       selectSql += ",BS253";
	       selectSql += ",BS254";
	       selectSql += ",BS255";
	       selectSql += ",BS200";
	       selectSql += ",BS001";
	       selectSql += ",BS301";
	       selectSql += ",BS302";
	       selectSql += ",BS303";
	       selectSql += ",BS304";
	       selectSql += ",BS304_1";
	       selectSql += ",BS305";
	       selectSql += ",BS305_1";
	       selectSql += ",BS306";
	       selectSql += ",BS307";
	       selectSql += ",BS308";
	       selectSql += ",BS309";
	       selectSql += ",BS310";
	       selectSql += ",BS310_1";
	       selectSql += ",BS310_2";
	       selectSql += ",BS311";
	       selectSql += ",BS311_1";
	       selectSql += ",BS311_2";
	       selectSql += ",BS312";
	       selectSql += ",BS313";
	       selectSql += ",BS300";
	       selectSql += ",BS401";
	       selectSql += ",BS401_1";
	       selectSql += ",BS401_2";
	       selectSql += ",BS401_3";
	       selectSql += ",BS402";
	       selectSql += ",BS403";
	       selectSql += ",BS404";
	       selectSql += ",BS405";
	       selectSql += ",BS406";
	       selectSql += ",BS407";
	       selectSql += ",BS400";
	       selectSql += ",BS002";
	       selectSql += ",BS501";
	       selectSql += ",BS502";
	       selectSql += ",BS503";
	       selectSql += ",BS2015";
	       selectSql += ",BS504";
	       selectSql += ",BS2016";
	       selectSql += ",BS505";
	       selectSql += ",BS506";
	       selectSql += ",BS003";
	       selectSql += ",BS507";
	       selectSql += ",BS004";
	       selectSql += ",BS005";
	       selectSql += ",PL101";
	       selectSql += ",PL102";
	       selectSql += ",PL103";
	       selectSql += ",PL201";
	       selectSql += ",PL202_5";
	       selectSql += ",PL202";
	       selectSql += ",PL203";
	       selectSql += ",PL203_1";
	       selectSql += ",PL204";
	       selectSql += ",PL205";
	       selectSql += ",PL200";
	       selectSql += ",PL210";
	       selectSql += ",PL211";
	       selectSql += ",PL212";
	       selectSql += ",PL220";
	       selectSql += ",PL301";
	       selectSql += ",PL301_1";
	       selectSql += ",PL301_2";
	       selectSql += ",PL301_3";
	       selectSql += ",PL301_4";
	       selectSql += ",PL301_5";
	       selectSql += ",PL301_6";
	       selectSql += ",PL301_7";
	       selectSql += ",PL301_8";
	       selectSql += ",PL301_9";
	       selectSql += ",PL301_10";
	       selectSql += ",PL301_11";
	       selectSql += ",PL301_12";
	       selectSql += ",PL300";
	       selectSql += ",PL401";
	       selectSql += ",PL402";
	       selectSql += ",PL402_1";
	       selectSql += ",PL400";
	       selectSql += ",PL501";
	       selectSql += ",PL500";
	       selectSql += ",PL601";
	       selectSql += ",PL600";
	       selectSql += ",PL701";
	       selectSql += ",PL702";
	       selectSql += ",PL703_0";
	       selectSql += ",PL703_1";
	       selectSql += ",PL703_2";
	       selectSql += ",PL703_3";
	       selectSql += ",PL703_4";
	       selectSql += ",PL703_5";
	       selectSql += ",PL708";
	       selectSql += ",PL703";
	       selectSql += ",PL704";
	       selectSql += ",PL705";
	       selectSql += ",PL706";
	       selectSql += ",PL707";
	       selectSql += ",PL700";
	       selectSql += ",CF101";
	       selectSql += ",CF102";
	       selectSql += ",CF103";
	       selectSql += ",CF100";
	       selectSql += ",CF201";
	       selectSql += ",CF202";
	       selectSql += ",CF203";
	       selectSql += ",CF204";
	       selectSql += ",CF200";
	       selectSql += ",CF001";
	       selectSql += ",CF301";
	       selectSql += ",CF302";
	       selectSql += ",CF303";
	       selectSql += ",CF305";
	       selectSql += ",CF304";
	       selectSql += ",CF300";
	       selectSql += ",CF401";
	       selectSql += ",CF402";
	       selectSql += ",CF404";
	       selectSql += ",CF403";
	       selectSql += ",CF400";
	       selectSql += ",CF002";
	       selectSql += ",CF501";
	       selectSql += ",CF502";
	       selectSql += ",CF504";
	       selectSql += ",CF503";
	       selectSql += ",CF500";
	       selectSql += ",CF601";
	       selectSql += ",CF602";
	       selectSql += ",CF603";
	       selectSql += ",CF600";
	       selectSql += ",CF003";
	       selectSql += ",CF004";
	       selectSql += ",CF005";
	       selectSql += ",CF006";
	       selectSql += ",CF007";
	       selectSql += ",CF901";
	       selectSql += ",CF902";
	       selectSql += ",CF903";
	       selectSql += ",CF904";
	       selectSql += ",CF905";
	       selectSql += ",CF906";
	       selectSql += ",CF907";
	       selectSql += ",CF908";
	       selectSql += ",CF909";
	       selectSql += ",CF910";
	       selectSql += ",CF911";
	       selectSql += ",CF912";
	       selectSql += ",CF913";
	       selectSql += ",CF914";
	       selectSql += ",CF915";
	       selectSql += ",CF916";
	       selectSql += ",CF917";
	       selectSql += ",CF918";
	       selectSql += ",CF919";
	       selectSql += ",CF920";
	       selectSql += ",CF921";
	       selectSql += ",CF922";
	       selectSql += ",CF923";
	       selectSql += ",CF924";
	       selectSql += ",CF925";
	       selectSql += ",last_update_timestamp";
	       selectSql += ",ROW_KEY";
		   return selectSql.substring(1);
	    }

	    public String insertBySelectSql(){
		   String selColumn = createSelectColumnSql();           
	       String sql = "insert into manu_fina_sheet_p ("+selColumn+") select "+selColumn+" from manu_fina_sheet";
	       return sql;
	    }
}
