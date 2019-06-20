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
@Table(name = "insu_fina_sheet")
public class BondInsuFinaSheet implements Serializable {
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
    private BigDecimal IBS101;
    
	/**
	 * 拆出资金
	 */
    @Column(length=20)
    private BigDecimal IBS102;
    
	/**
	 * 交易性金融资产
	 */
    @Column(length=20)
    private BigDecimal IBS103;
    
	/**
	 * 债权型投资
	 */
    @Column(length=20)
    private BigDecimal IBS103_1;
    
	/**
	 * 国债
	 */
    @Column(length=20)
    private BigDecimal IBS103_1_1;
    
	/**
	 * 央行票据
	 */
    @Column(length=20)
    private BigDecimal IBS103_1_2;
    
	/**
	 * 政府机构债
	 */
    @Column(length=20)
    private BigDecimal IBS103_1_3;
    
	/**
	 * 金融债
	 */
    @Column(length=20)
    private BigDecimal IBS103_1_4;
    
	/**
	 * 企业债
	 */
    @Column(length=20)
    private BigDecimal IBS103_1_5;
    
	/**
	 * 股权型投资
	 */
    @Column(length=20)
    private BigDecimal IBS103_2;
    
	/**
	 * 基金
	 */
    @Column(length=20)
    private BigDecimal IBS103_2_1;
    
	/**
	 * 股票
	 */
    @Column(length=20)
    private BigDecimal IBS103_2_2;
    
	/**
	 * 权证
	 */
    @Column(length=20)
    private BigDecimal IBS103_2_3;
    
	/**
	 * 衍生金融资产
	 */
    @Column(length=20)
    private BigDecimal IBS104;
    
	/**
	 * 买入返售金融资产
	 */
    @Column(length=20)
    private BigDecimal IBS105;
    
	/**
	 * 应收保费
	 */
    @Column(length=20)
    private BigDecimal IBS107;
    
	/**
	 * 应收代位追偿款
	 */
    @Column(length=20)
    private BigDecimal IBS108;
    
	/**
	 * 应收分保账款
	 */
    @Column(length=20)
    private BigDecimal IBS109;
    
	/**
	 * 应收利息
	 */
    @Column(length=20)
    private BigDecimal IBS106;
    
	/**
	 * 应收分保未到期责任准备金
	 */
    @Column(length=20)
    private BigDecimal IBS110;
    
	/**
	 * 应收分保未决赔款准备金
	 */
    @Column(length=20)
    private BigDecimal IBS111;
    
	/**
	 * 应收分保寿险责任准备金
	 */
    @Column(length=20)
    private BigDecimal IBS112;
    
	/**
	 * 应收分保长期健康险责任准备金
	 */
    @Column(length=20)
    private BigDecimal IBS113;
    
	/**
	 * 保户质押贷款
	 */
    @Column(length=20)
    private BigDecimal IBS114;
    
	/**
	 * 债权投资计划
	 */
    @Column(length=20)
    private BigDecimal IBS126;
    
	/**
	 * 其他应收款
	 */
    @Column(length=20)
    private BigDecimal IBS127;
    
	/**
	 * 定期存款
	 */
    @Column(length=20)
    private BigDecimal IBS115;
    
	/**
	 * 可供出售金融资产
	 */
    @Column(length=20)
    private BigDecimal IBS116;
    
	/**
	 * 债权型投资
	 */
    @Column(length=20)
    private BigDecimal IBS116_1;
    
	/**
	 * 国债
	 */
    @Column(length=20)
    private BigDecimal IBS116_1_1;
    
	/**
	 * 央行票据
	 */
    @Column(length=20)
    private BigDecimal IBS116_1_2;
    
	/**
	 * 政府机构债
	 */
    @Column(length=20)
    private BigDecimal IBS116_1_3;
    
	/**
	 * 金融债
	 */
    @Column(length=20)
    private BigDecimal IBS116_1_4;
    
	/**
	 * 企业债
	 */
    @Column(length=20)
    private BigDecimal IBS116_1_5;
    
	/**
	 * 股权型投资
	 */
    @Column(length=20)
    private BigDecimal IBS116_2;
    
	/**
	 * 基金
	 */
    @Column(length=20)
    private BigDecimal IBS116_2_1;
    
	/**
	 * 股票
	 */
    @Column(length=20)
    private BigDecimal IBS116_2_2;
    
	/**
	 * 其他权益投资
	 */
    @Column(length=20)
    private BigDecimal IBS116_3;
    
	/**
	 * 持有至到期投资
	 */
    @Column(length=20)
    private BigDecimal IBS117;
    
	/**
	 * 债权型投资
	 */
    @Column(length=20)
    private BigDecimal IBS117_1;
    
	/**
	 * 国债
	 */
    @Column(length=20)
    private BigDecimal IBS117_1_1;
    
	/**
	 * 政府机构债
	 */
    @Column(length=20)
    private BigDecimal IBS117_1_2;
    
	/**
	 * 金融债
	 */
    @Column(length=20)
    private BigDecimal IBS117_1_3;
    
	/**
	 * 企业债
	 */
    @Column(length=20)
    private BigDecimal IBS117_1_4;
    
	/**
	 * 投资性房地产
	 */
    @Column(length=20)
    private BigDecimal IBS120;
    
	/**
	 * 长期股权投资
	 */
    @Column(length=20)
    private BigDecimal IBS118;
    
	/**
	 * 存出资本保证金
	 */
    @Column(length=20)
    private BigDecimal IBS119;
    
	/**
	 * 固定资产
	 */
    @Column(length=20)
    private BigDecimal IBS121;
    
	/**
	 * 在建工程
	 */
    @Column(length=20)
    private BigDecimal IBS128;
    
	/**
	 * 无形资产
	 */
    @Column(length=20)
    private BigDecimal IBS122;
    
	/**
	 * 递延所得税资产
	 */
    @Column(length=20)
    private BigDecimal IBS124;
    
	/**
	 * 商誉
	 */
    @Column(length=20)
    private BigDecimal IBS129;
    
	/**
	 * 其他资产
	 */
    @Column(length=20)
    private BigDecimal IBS125;
    
	/**
	 * 独立账户资产
	 */
    @Column(length=20)
    private BigDecimal IBS123;
    
	/**
	 * 资产总计
	 */
    @Column(length=20)
    private BigDecimal IBS001;
    
	/**
	 * 短期借款
	 */
    @Column(length=20)
    private BigDecimal IBS201;
    
	/**
	 * 拆入资金
	 */
    @Column(length=20)
    private BigDecimal IBS202;
    
	/**
	 * 交易性金融负债
	 */
    @Column(length=20)
    private BigDecimal IBS203;
    
	/**
	 * 衍生金融负债
	 */
    @Column(length=20)
    private BigDecimal IBS204;
    
	/**
	 * 卖出回购金融资产款
	 */
    @Column(length=20)
    private BigDecimal IBS205;
    
	/**
	 * 预收保费
	 */
    @Column(length=20)
    private BigDecimal IBS206;
    
	/**
	 * 应付手续费及佣金
	 */
    @Column(length=20)
    private BigDecimal IBS207;
    
	/**
	 * 应付分保账款
	 */
    @Column(length=20)
    private BigDecimal IBS208;
    
	/**
	 * 应付职工薪酬
	 */
    @Column(length=20)
    private BigDecimal IBS209;
    
	/**
	 * 应交税费
	 */
    @Column(length=20)
    private BigDecimal IBS210;
    
	/**
	 * 应付利息
	 */
    @Column(length=20)
    private BigDecimal IBS223;
    
	/**
	 * 应付赔付款
	 */
    @Column(length=20)
    private BigDecimal IBS211;
    
	/**
	 * 应付保单红利
	 */
    @Column(length=20)
    private BigDecimal IBS212;
    
	/**
	 * 其他应付款
	 */
    @Column(length=20)
    private BigDecimal IBS224;
    
	/**
	 * 保户储金及投资款
	 */
    @Column(length=20)
    private BigDecimal IBS213;
    
	/**
	 * 未到期责任准备金
	 */
    @Column(length=20)
    private BigDecimal IBS214;
    
	/**
	 * 未决赔款准备金
	 */
    @Column(length=20)
    private BigDecimal IBS215;
    
	/**
	 * 寿险责任准备金
	 */
    @Column(length=20)
    private BigDecimal IBS216;
    
	/**
	 * 长期健康险责任准备金
	 */
    @Column(length=20)
    private BigDecimal IBS217;
    
	/**
	 * 长期借款
	 */
    @Column(length=20)
    private BigDecimal IBS218;
    
	/**
	 * 保险保障基金
	 */
    @Column(length=20)
    private BigDecimal IBS225;
    
	/**
	 * 保户投资款及代理业务负债
	 */
    @Column(length=20)
    private BigDecimal IBS226;
    
	/**
	 * 应付债券
	 */
    @Column(length=20)
    private BigDecimal IBS219;
    
	/**
	 * 递延所得税负债
	 */
    @Column(length=20)
    private BigDecimal IBS221;
    
	/**
	 * 其他负债
	 */
    @Column(length=20)
    private BigDecimal IBS222;
    
	/**
	 * 独立账户负债
	 */
    @Column(length=20)
    private BigDecimal IBS220;
    
	/**
	 * 负债合计
	 */
    @Column(length=20)
    private BigDecimal IBS002;
    
	/**
	 * 实收资本(或股本)
	 */
    @Column(length=20)
    private BigDecimal IBS301;
    
	/**
	 * 资本公积
	 */
    @Column(length=20)
    private BigDecimal IBS302;
    
	/**
	 * 减:库存股
	 */
    @Column(length=20)
    private BigDecimal IBS302_2;
    
	/**
	 * 盈余公积
	 */
    @Column(length=20)
    private BigDecimal IBS303;
    
	/**
	 * 一般风险准备
	 */
    @Column(length=20)
    private BigDecimal IBS304;
    
	/**
	 * 未分配利润
	 */
    @Column(length=20)
    private BigDecimal IBS305;
    
	/**
	 * 外币报表折算差额
	 */
    @Column(length=20)
    private BigDecimal IBS308;
    
	/**
	 * 归属于母公司所有者权益合计
	 */
    @Column(length=20)
    private BigDecimal IBS306;
    
	/**
	 * 少数股东权益
	 */
    @Column(length=20)
    private BigDecimal IBS309;
    
	/**
	 * 所有者权益(或股东权益)合计
	 */
    @Column(length=20)
    private BigDecimal IBS003;
    
	/**
	 * 负债和所有者权益(或股东权益)总计
	 */
    @Column(length=20)
    private BigDecimal IBS004;
    
	/**
	 * 营业收入
	 */
    @Column(length=20)
    private BigDecimal IPL100;
    
	/**
	 * 已赚保费
	 */
    @Column(length=20)
    private BigDecimal IPL101;
    
	/**
	 * 保险业务收入
	 */
    @Column(length=20)
    private BigDecimal IPL102;
    
	/**
	 * 其中:分保费收入
	 */
    @Column(length=20)
    private BigDecimal IPL102_1;
    
	/**
	 * 产险
	 */
    @Column(length=20)
    private BigDecimal IPL102_1_1;
    
	/**
	 * 寿险
	 */
    @Column(length=20)
    private BigDecimal IPL102_1_2;
    
	/**
	 * 减:分出保费
	 */
    @Column(length=20)
    private BigDecimal IPL102_2;
    
	/**
	 * 提取未到期责任准备金
	 */
    @Column(length=20)
    private BigDecimal IPL103;
    
	/**
	 * 投资收益(损失以“－”号填列)
	 */
    @Column(length=20)
    private BigDecimal IPL104;
    
	/**
	 * 其中:对联营企业和合营企业的投资收益
	 */
    @Column(length=20)
    private BigDecimal IPL104_1;
    
	/**
	 * 公允价值变动收益(损失以“－”号填列)
	 */
    @Column(length=20)
    private BigDecimal IPL105;
    
	/**
	 * 债权型投资
	 */
    @Column(length=20)
    private BigDecimal IPL105_1;
    
	/**
	 * 股权型投资
	 */
    @Column(length=20)
    private BigDecimal IPL105_2;
    
	/**
	 * 汇兑收益(损失以“-”号填列)
	 */
    @Column(length=20)
    private BigDecimal IPL106;
    
	/**
	 * 其他业务收入
	 */
    @Column(length=20)
    private BigDecimal IPL107;
    
	/**
	 * 营业支出
	 */
    @Column(length=20)
    private BigDecimal IPL200;
    
	/**
	 * 退保金
	 */
    @Column(length=20)
    private BigDecimal IPL201;
    
	/**
	 * 赔付支出
	 */
    @Column(length=20)
    private BigDecimal IPL202;
    
	/**
	 * 减:摊回赔付支出
	 */
    @Column(length=20)
    private BigDecimal IPL202_1;
    
	/**
	 * 提取保险责任准备金
	 */
    @Column(length=20)
    private BigDecimal IPL203;
    
	/**
	 * 减:摊回保险责任准备金
	 */
    @Column(length=20)
    private BigDecimal IPL203_1;
    
	/**
	 * 保单红利支出
	 */
    @Column(length=20)
    private BigDecimal IPL204;
    
	/**
	 * 分保费用
	 */
    @Column(length=20)
    private BigDecimal IPL205;
    
	/**
	 * 营业税金及附加
	 */
    @Column(length=20)
    private BigDecimal IPL206;
    
	/**
	 * 手续费及佣金支出
	 */
    @Column(length=20)
    private BigDecimal IPL207;
    
	/**
	 * 业务及管理费
	 */
    @Column(length=20)
    private BigDecimal IPL208;
    
	/**
	 * 减:摊回分保费用
	 */
    @Column(length=20)
    private BigDecimal IPL208_1;
    
	/**
	 * 其他业务成本
	 */
    @Column(length=20)
    private BigDecimal IPL210;
    
	/**
	 * 资产减值损失
	 */
    @Column(length=20)
    private BigDecimal IPL209;
    
	/**
	 * 营业利润(亏损以“－”号填列)
	 */
    @Column(length=20)
    private BigDecimal IPL300;
    
	/**
	 * 营业外收入
	 */
    @Column(length=20)
    private BigDecimal IPL301;
    
	/**
	 * 营业外支出
	 */
    @Column(length=20)
    private BigDecimal IPL302;
    
	/**
	 * 利润总额(亏损总额以“－”号填列)
	 */
    @Column(length=20)
    private BigDecimal IPL400;
    
	/**
	 * 所得税费用
	 */
    @Column(length=20)
    private BigDecimal IPL401;
    
	/**
	 * 净利润(净亏损以“－”号填列)
	 */
    @Column(length=20)
    private BigDecimal IPL500;
    
	/**
	 * 归属于母公司所有者(或股东)的净利润
	 */
    @Column(length=20)
    private BigDecimal IPL501;
    
	/**
	 * 少数股东损益
	 */
    @Column(length=20)
    private BigDecimal IPL502;
    
	/**
	 * 基本每股收益
	 */
    @Column(length=20)
    private BigDecimal IPL503;
    
	/**
	 * 稀释每股收益
	 */
    @Column(length=20)
    private BigDecimal IPL504;
    
	/**
	 * 其他综合收益
	 */
    @Column(length=20)
    private BigDecimal IPL505;
    
	/**
	 * 综合收益总额
	 */
    @Column(length=20)
    private BigDecimal IPL506;
    
	/**
	 * 归属于母公司所有者(或股东)的综合收益总额
	 */
    @Column(length=20)
    private BigDecimal IPL507;
    
	/**
	 * 归属于少数股东的综合收益总额
	 */
    @Column(length=20)
    private BigDecimal IPL508;
    
	/**
	 * 客户存款和同业存放款项净增加额
	 */
    @Column(length=20)
    private BigDecimal ICF101;
    
	/**
	 * 向中央银行借款净增加额
	 */
    @Column(length=20)
    private BigDecimal ICF102;
    
	/**
	 * 收到原保险合同保费取得的现金
	 */
    @Column(length=20)
    private BigDecimal ICF103;
    
	/**
	 * 收到再保险业务现金净额
	 */
    @Column(length=20)
    private BigDecimal ICF104;
    
	/**
	 * 保户储金及投资款净增加额
	 */
    @Column(length=20)
    private BigDecimal ICF105;
    
	/**
	 * 收取利息、手续费及佣金的现金
	 */
    @Column(length=20)
    private BigDecimal ICF106;
    
	/**
	 * 收到的税费返还
	 */
    @Column(length=20)
    private BigDecimal ICF107;
    
	/**
	 * 收到其他与经营活动有关的现金
	 */
    @Column(length=20)
    private BigDecimal ICF108;
    
	/**
	 * 经营活动现金流入小计
	 */
    @Column(length=20)
    private BigDecimal ICF100;
    
	/**
	 * 客户贷款及垫款净增加额
	 */
    @Column(length=20)
    private BigDecimal ICF201;
    
	/**
	 * 存放中央银行和同业款项净增加额
	 */
    @Column(length=20)
    private BigDecimal ICF202;
    
	/**
	 * 支付原保险合同赔付款项的现金
	 */
    @Column(length=20)
    private BigDecimal ICF203;
    
	/**
	 * 支付利息、手续费及佣金的现金
	 */
    @Column(length=20)
    private BigDecimal ICF204;
    
	/**
	 * 支付保单红利的现金
	 */
    @Column(length=20)
    private BigDecimal ICF205;
    
	/**
	 * 支付给职工以及为职工支付的现金
	 */
    @Column(length=20)
    private BigDecimal ICF206;
    
	/**
	 * 支付的各项税费
	 */
    @Column(length=20)
    private BigDecimal ICF207;
    
	/**
	 * 支付其他与经营活动有关的现金
	 */
    @Column(length=20)
    private BigDecimal ICF208;
    
	/**
	 * 经营活动现金流出小计
	 */
    @Column(length=20)
    private BigDecimal ICF200;
    
	/**
	 * 经营活动产生的现金流量净额
	 */
    @Column(length=20)
    private BigDecimal ICF001;
    
	/**
	 * 收回投资收到的现金
	 */
    @Column(length=20)
    private BigDecimal ICF301;
    
	/**
	 * 取得投资收益收到的现金
	 */
    @Column(length=20)
    private BigDecimal ICF302;
    
	/**
	 * 处置固定资产、无形资产和其他长期资产收回的现金净额
	 */
    @Column(length=20)
    private BigDecimal ICF303;
    
	/**
	 * 处置子公司及其他营业单位收到的现金净额
	 */
    @Column(length=20)
    private BigDecimal ICF304;
    
	/**
	 * 收到其他与投资活动有关的现金
	 */
    @Column(length=20)
    private BigDecimal ICF305;
    
	/**
	 * 投资活动现金流入小计
	 */
    @Column(length=20)
    private BigDecimal ICF300;
    
	/**
	 * 购建固定资产、无形资产和其他长期资产支付的现金
	 */
    @Column(length=20)
    private BigDecimal ICF401;
    
	/**
	 * 投资支付的现金
	 */
    @Column(length=20)
    private BigDecimal ICF402;
    
	/**
	 * 质押贷款净增加额
	 */
    @Column(length=20)
    private BigDecimal ICF403;
    
	/**
	 * 取得子公司及其他营业单位支付的现金净额
	 */
    @Column(length=20)
    private BigDecimal ICF404;
    
	/**
	 * 支付其他与投资活动有关的现金
	 */
    @Column(length=20)
    private BigDecimal ICF405;
    
	/**
	 * 投资活动现金流出小计
	 */
    @Column(length=20)
    private BigDecimal ICF400;
    
	/**
	 * 投资活动产生的现金流量净额
	 */
    @Column(length=20)
    private BigDecimal ICF002;
    
	/**
	 * 吸收投资收到的现金
	 */
    @Column(length=20)
    private BigDecimal ICF501;
    
	/**
	 * 其中:子公司吸收少数股东投资收到的现金
	 */
    @Column(length=20)
    private BigDecimal ICF502;
    
	/**
	 * 取得借款收到的现金
	 */
    @Column(length=20)
    private BigDecimal ICF503;
    
	/**
	 * 发行债券收到的现金
	 */
    @Column(length=20)
    private BigDecimal ICF504;
    
	/**
	 * 收到其他与筹资活动有关的现金
	 */
    @Column(length=20)
    private BigDecimal ICF505;
    
	/**
	 * 筹资活动现金流入小计
	 */
    @Column(length=20)
    private BigDecimal ICF500;
    
	/**
	 * 偿还债务支付的现金
	 */
    @Column(length=20)
    private BigDecimal ICF601;
    
	/**
	 * 分配股利、利润或偿付利息支付的现金
	 */
    @Column(length=20)
    private BigDecimal ICF602;
    
	/**
	 * 其中:子公司支付给少数股东的股利、利润
	 */
    @Column(length=20)
    private BigDecimal ICF603;
    
	/**
	 * 支付其他与筹资活动有关的现金
	 */
    @Column(length=20)
    private BigDecimal ICF604;
    
	/**
	 * 筹资活动现金流出小计
	 */
    @Column(length=20)
    private BigDecimal ICF600;
    
	/**
	 * 筹资活动产生的现金流量净额
	 */
    @Column(length=20)
    private BigDecimal ICF003;
    
	/**
	 * 汇率变动对现金及现金等价物的影响
	 */
    @Column(length=20)
    private BigDecimal ICF701;
    
	/**
	 * 现金及现金等价物净增加额
	 */
    @Column(length=20)
    private BigDecimal ICF004;
    
	/**
	 * 加:期初现金及现金等价物余额
	 */
    @Column(length=20)
    private BigDecimal ICF801;
    
	/**
	 * 期末现金及现金等价物余额
	 */
    @Column(length=20)
    private BigDecimal ICF005;
    
	/**
	 * 净利润
	 */
    @Column(length=20)
    private BigDecimal ICF901;
    
	/**
	 * 加:资产减值准备
	 */
    @Column(length=20)
    private BigDecimal ICF902;
    
	/**
	 * 固定资产折旧、油气资产折耗、生产性生物资产折旧
	 */
    @Column(length=20)
    private BigDecimal ICF903;
    
	/**
	 * 无形资产摊销
	 */
    @Column(length=20)
    private BigDecimal ICF904;
    
	/**
	 * 长期待摊费用摊销
	 */
    @Column(length=20)
    private BigDecimal ICF905;
    
	/**
	 * 待摊费用减少(减:增加)
	 */
    @Column(length=20)
    private BigDecimal ICF906;
    
	/**
	 * 预提费用增加(减:减少)
	 */
    @Column(length=20)
    private BigDecimal ICF907;
    
	/**
	 * 处置固定资产、无形资产和其他长期资产的损失
	 */
    @Column(length=20)
    private BigDecimal ICF908;
    
	/**
	 * 固定资产报废损失
	 */
    @Column(length=20)
    private BigDecimal ICF909;
    
	/**
	 * 公允价值变动损失
	 */
    @Column(length=20)
    private BigDecimal ICF910;
    
	/**
	 * 财务费用
	 */
    @Column(length=20)
    private BigDecimal ICF911;
    
	/**
	 * 投资损失
	 */
    @Column(length=20)
    private BigDecimal ICF912;
    
	/**
	 * 递延所得税资产减少
	 */
    @Column(length=20)
    private BigDecimal ICF913;
    
	/**
	 * 递延所得税负债增加
	 */
    @Column(length=20)
    private BigDecimal ICF914;
    
	/**
	 * 存货的减少
	 */
    @Column(length=20)
    private BigDecimal ICF915;
    
	/**
	 * 经营性应收项目的减少
	 */
    @Column(length=20)
    private BigDecimal ICF916;
    
	/**
	 * 经营性应付项目的增加
	 */
    @Column(length=20)
    private BigDecimal ICF917;
    
	/**
	 * 其他
	 */
    @Column(length=20)
    private BigDecimal ICF918;
    
	/**
	 * (间接)经营活动产生的现金流量净额
	 */
    @Column(length=20)
    private BigDecimal ICF919;
    
	/**
	 * 债务转为资本
	 */
    @Column(length=20)
    private BigDecimal ICF920;
    
	/**
	 * 一年内到期的可转换公司债券
	 */
    @Column(length=20)
    private BigDecimal ICF921;
    
	/**
	 * 融资租入固定资产
	 */
    @Column(length=20)
    private BigDecimal ICF922;
    
	/**
	 * 现金的期末余额
	 */
    @Column(length=20)
    private BigDecimal ICF923;
    
	/**
	 * 减:现金的期初余额
	 */
    @Column(length=20)
    private BigDecimal ICF924;
    
	/**
	 * 加:现金等价物的期末余额
	 */
    @Column(length=20)
    private BigDecimal ICF925;
    
	/**
	 * 减:现金等价物的期初余额
	 */
    @Column(length=20)
    private BigDecimal ICF926;
    
	/**
	 * (间接)现金及现金等价物净增加额
	 */
    @Column(length=20)
    private BigDecimal ICF927;
    
	/**
	 * 退保率
	 */
    @Column(length=20)
    private BigDecimal ITN101;
    
	/**
	 * 赔付率(产险)
	 */
    @Column(length=20)
    private BigDecimal ITN102;
    
	/**
	 * 产险投资收益率
	 */
    @Column(length=20)
    private BigDecimal ITN103;
    
	/**
	 * 寿险投资收益率
	 */
    @Column(length=20)
    private BigDecimal ITN104;
    
	/**
	 * 寿险-实际资本
	 */
    @Column(length=20)
    private BigDecimal ITN105;
    
	/**
	 * 寿险-最低资本
	 */
    @Column(length=20)
    private BigDecimal ITN106;
    
	/**
	 * 偿付能力充足率
	 */
    @Column(length=20)
    private BigDecimal ITN107;
    
	/**
	 * 产险-实际资本
	 */
    @Column(length=20)
    private BigDecimal ITN108;
    
	/**
	 * 产险-最低资本
	 */
    @Column(length=20)
    private BigDecimal ITN109;
    
	/**
	 * 偿付能力充足率
	 */
    @Column(length=20)
    private BigDecimal ITN110;
    
	/**
	 * 投资收益
	 */
    @Column(length=20)
    private BigDecimal ITN111;
    
	/**
	 * 公允价值变动收益
	 */
    @Column(length=20)
    private BigDecimal ITN112;
    
	/**
	 * 活期存款利息收入
	 */
    @Column(length=20)
    private BigDecimal ITN113;
    
	/**
	 * 计提/转回资产减值准备
	 */
    @Column(length=20)
    private BigDecimal ITN114;
    
	/**
	 * 对联营企业和合营企业的投资收益/损失
	 */
    @Column(length=20)
    private BigDecimal ITN115;
    
	/**
	 * 总投资收益
	 */
    @Column(length=20)
    private BigDecimal ITN116;
    
	/**
	 * 可供出售金融资产公允价值变动损益
	 */
    @Column(length=20)
    private BigDecimal ITN117;
    
	/**
	 * 全投资收益合计
	 */
    @Column(length=20)
    private BigDecimal ITN118;
    
	/**
	 * 寿险保费市场份额（%）
	 */
    @Column(length=20)
    private BigDecimal ITN119;
    
	/**
	 * 产险保费市场份额（%）
	 */
    @Column(length=20)
    private BigDecimal ITN120;
    
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

	public BigDecimal getIBS101() {
		return IBS101;
	}

	public void setIBS101(BigDecimal iBS101) {
		IBS101 = iBS101;
	}

	public BigDecimal getIBS102() {
		return IBS102;
	}

	public void setIBS102(BigDecimal iBS102) {
		IBS102 = iBS102;
	}

	public BigDecimal getIBS103() {
		return IBS103;
	}

	public void setIBS103(BigDecimal iBS103) {
		IBS103 = iBS103;
	}

	public BigDecimal getIBS103_1() {
		return IBS103_1;
	}

	public void setIBS103_1(BigDecimal iBS103_1) {
		IBS103_1 = iBS103_1;
	}

	public BigDecimal getIBS103_1_1() {
		return IBS103_1_1;
	}

	public void setIBS103_1_1(BigDecimal iBS103_1_1) {
		IBS103_1_1 = iBS103_1_1;
	}

	public BigDecimal getIBS103_1_2() {
		return IBS103_1_2;
	}

	public void setIBS103_1_2(BigDecimal iBS103_1_2) {
		IBS103_1_2 = iBS103_1_2;
	}

	public BigDecimal getIBS103_1_3() {
		return IBS103_1_3;
	}

	public void setIBS103_1_3(BigDecimal iBS103_1_3) {
		IBS103_1_3 = iBS103_1_3;
	}

	public BigDecimal getIBS103_1_4() {
		return IBS103_1_4;
	}

	public void setIBS103_1_4(BigDecimal iBS103_1_4) {
		IBS103_1_4 = iBS103_1_4;
	}

	public BigDecimal getIBS103_1_5() {
		return IBS103_1_5;
	}

	public void setIBS103_1_5(BigDecimal iBS103_1_5) {
		IBS103_1_5 = iBS103_1_5;
	}

	public BigDecimal getIBS103_2() {
		return IBS103_2;
	}

	public void setIBS103_2(BigDecimal iBS103_2) {
		IBS103_2 = iBS103_2;
	}

	public BigDecimal getIBS103_2_1() {
		return IBS103_2_1;
	}

	public void setIBS103_2_1(BigDecimal iBS103_2_1) {
		IBS103_2_1 = iBS103_2_1;
	}

	public BigDecimal getIBS103_2_2() {
		return IBS103_2_2;
	}

	public void setIBS103_2_2(BigDecimal iBS103_2_2) {
		IBS103_2_2 = iBS103_2_2;
	}

	public BigDecimal getIBS103_2_3() {
		return IBS103_2_3;
	}

	public void setIBS103_2_3(BigDecimal iBS103_2_3) {
		IBS103_2_3 = iBS103_2_3;
	}

	public BigDecimal getIBS104() {
		return IBS104;
	}

	public void setIBS104(BigDecimal iBS104) {
		IBS104 = iBS104;
	}

	public BigDecimal getIBS105() {
		return IBS105;
	}

	public void setIBS105(BigDecimal iBS105) {
		IBS105 = iBS105;
	}

	public BigDecimal getIBS107() {
		return IBS107;
	}

	public void setIBS107(BigDecimal iBS107) {
		IBS107 = iBS107;
	}

	public BigDecimal getIBS108() {
		return IBS108;
	}

	public void setIBS108(BigDecimal iBS108) {
		IBS108 = iBS108;
	}

	public BigDecimal getIBS109() {
		return IBS109;
	}

	public void setIBS109(BigDecimal iBS109) {
		IBS109 = iBS109;
	}

	public BigDecimal getIBS106() {
		return IBS106;
	}

	public void setIBS106(BigDecimal iBS106) {
		IBS106 = iBS106;
	}

	public BigDecimal getIBS110() {
		return IBS110;
	}

	public void setIBS110(BigDecimal iBS110) {
		IBS110 = iBS110;
	}

	public BigDecimal getIBS111() {
		return IBS111;
	}

	public void setIBS111(BigDecimal iBS111) {
		IBS111 = iBS111;
	}

	public BigDecimal getIBS112() {
		return IBS112;
	}

	public void setIBS112(BigDecimal iBS112) {
		IBS112 = iBS112;
	}

	public BigDecimal getIBS113() {
		return IBS113;
	}

	public void setIBS113(BigDecimal iBS113) {
		IBS113 = iBS113;
	}

	public BigDecimal getIBS114() {
		return IBS114;
	}

	public void setIBS114(BigDecimal iBS114) {
		IBS114 = iBS114;
	}

	public BigDecimal getIBS126() {
		return IBS126;
	}

	public void setIBS126(BigDecimal iBS126) {
		IBS126 = iBS126;
	}

	public BigDecimal getIBS127() {
		return IBS127;
	}

	public void setIBS127(BigDecimal iBS127) {
		IBS127 = iBS127;
	}

	public BigDecimal getIBS115() {
		return IBS115;
	}

	public void setIBS115(BigDecimal iBS115) {
		IBS115 = iBS115;
	}

	public BigDecimal getIBS116() {
		return IBS116;
	}

	public void setIBS116(BigDecimal iBS116) {
		IBS116 = iBS116;
	}

	public BigDecimal getIBS116_1() {
		return IBS116_1;
	}

	public void setIBS116_1(BigDecimal iBS116_1) {
		IBS116_1 = iBS116_1;
	}

	public BigDecimal getIBS116_1_1() {
		return IBS116_1_1;
	}

	public void setIBS116_1_1(BigDecimal iBS116_1_1) {
		IBS116_1_1 = iBS116_1_1;
	}

	public BigDecimal getIBS116_1_2() {
		return IBS116_1_2;
	}

	public void setIBS116_1_2(BigDecimal iBS116_1_2) {
		IBS116_1_2 = iBS116_1_2;
	}

	public BigDecimal getIBS116_1_3() {
		return IBS116_1_3;
	}

	public void setIBS116_1_3(BigDecimal iBS116_1_3) {
		IBS116_1_3 = iBS116_1_3;
	}

	public BigDecimal getIBS116_1_4() {
		return IBS116_1_4;
	}

	public void setIBS116_1_4(BigDecimal iBS116_1_4) {
		IBS116_1_4 = iBS116_1_4;
	}

	public BigDecimal getIBS116_1_5() {
		return IBS116_1_5;
	}

	public void setIBS116_1_5(BigDecimal iBS116_1_5) {
		IBS116_1_5 = iBS116_1_5;
	}

	public BigDecimal getIBS116_2() {
		return IBS116_2;
	}

	public void setIBS116_2(BigDecimal iBS116_2) {
		IBS116_2 = iBS116_2;
	}

	public BigDecimal getIBS116_2_1() {
		return IBS116_2_1;
	}

	public void setIBS116_2_1(BigDecimal iBS116_2_1) {
		IBS116_2_1 = iBS116_2_1;
	}

	public BigDecimal getIBS116_2_2() {
		return IBS116_2_2;
	}

	public void setIBS116_2_2(BigDecimal iBS116_2_2) {
		IBS116_2_2 = iBS116_2_2;
	}

	public BigDecimal getIBS116_3() {
		return IBS116_3;
	}

	public void setIBS116_3(BigDecimal iBS116_3) {
		IBS116_3 = iBS116_3;
	}

	public BigDecimal getIBS117() {
		return IBS117;
	}

	public void setIBS117(BigDecimal iBS117) {
		IBS117 = iBS117;
	}

	public BigDecimal getIBS117_1() {
		return IBS117_1;
	}

	public void setIBS117_1(BigDecimal iBS117_1) {
		IBS117_1 = iBS117_1;
	}

	public BigDecimal getIBS117_1_1() {
		return IBS117_1_1;
	}

	public void setIBS117_1_1(BigDecimal iBS117_1_1) {
		IBS117_1_1 = iBS117_1_1;
	}

	public BigDecimal getIBS117_1_2() {
		return IBS117_1_2;
	}

	public void setIBS117_1_2(BigDecimal iBS117_1_2) {
		IBS117_1_2 = iBS117_1_2;
	}

	public BigDecimal getIBS117_1_3() {
		return IBS117_1_3;
	}

	public void setIBS117_1_3(BigDecimal iBS117_1_3) {
		IBS117_1_3 = iBS117_1_3;
	}

	public BigDecimal getIBS117_1_4() {
		return IBS117_1_4;
	}

	public void setIBS117_1_4(BigDecimal iBS117_1_4) {
		IBS117_1_4 = iBS117_1_4;
	}

	public BigDecimal getIBS120() {
		return IBS120;
	}

	public void setIBS120(BigDecimal iBS120) {
		IBS120 = iBS120;
	}

	public BigDecimal getIBS118() {
		return IBS118;
	}

	public void setIBS118(BigDecimal iBS118) {
		IBS118 = iBS118;
	}

	public BigDecimal getIBS119() {
		return IBS119;
	}

	public void setIBS119(BigDecimal iBS119) {
		IBS119 = iBS119;
	}

	public BigDecimal getIBS121() {
		return IBS121;
	}

	public void setIBS121(BigDecimal iBS121) {
		IBS121 = iBS121;
	}

	public BigDecimal getIBS128() {
		return IBS128;
	}

	public void setIBS128(BigDecimal iBS128) {
		IBS128 = iBS128;
	}

	public BigDecimal getIBS122() {
		return IBS122;
	}

	public void setIBS122(BigDecimal iBS122) {
		IBS122 = iBS122;
	}

	public BigDecimal getIBS124() {
		return IBS124;
	}

	public void setIBS124(BigDecimal iBS124) {
		IBS124 = iBS124;
	}

	public BigDecimal getIBS129() {
		return IBS129;
	}

	public void setIBS129(BigDecimal iBS129) {
		IBS129 = iBS129;
	}

	public BigDecimal getIBS125() {
		return IBS125;
	}

	public void setIBS125(BigDecimal iBS125) {
		IBS125 = iBS125;
	}

	public BigDecimal getIBS123() {
		return IBS123;
	}

	public void setIBS123(BigDecimal iBS123) {
		IBS123 = iBS123;
	}

	public BigDecimal getIBS001() {
		return IBS001;
	}

	public void setIBS001(BigDecimal iBS001) {
		IBS001 = iBS001;
	}

	public BigDecimal getIBS201() {
		return IBS201;
	}

	public void setIBS201(BigDecimal iBS201) {
		IBS201 = iBS201;
	}

	public BigDecimal getIBS202() {
		return IBS202;
	}

	public void setIBS202(BigDecimal iBS202) {
		IBS202 = iBS202;
	}

	public BigDecimal getIBS203() {
		return IBS203;
	}

	public void setIBS203(BigDecimal iBS203) {
		IBS203 = iBS203;
	}

	public BigDecimal getIBS204() {
		return IBS204;
	}

	public void setIBS204(BigDecimal iBS204) {
		IBS204 = iBS204;
	}

	public BigDecimal getIBS205() {
		return IBS205;
	}

	public void setIBS205(BigDecimal iBS205) {
		IBS205 = iBS205;
	}

	public BigDecimal getIBS206() {
		return IBS206;
	}

	public void setIBS206(BigDecimal iBS206) {
		IBS206 = iBS206;
	}

	public BigDecimal getIBS207() {
		return IBS207;
	}

	public void setIBS207(BigDecimal iBS207) {
		IBS207 = iBS207;
	}

	public BigDecimal getIBS208() {
		return IBS208;
	}

	public void setIBS208(BigDecimal iBS208) {
		IBS208 = iBS208;
	}

	public BigDecimal getIBS209() {
		return IBS209;
	}

	public void setIBS209(BigDecimal iBS209) {
		IBS209 = iBS209;
	}

	public BigDecimal getIBS210() {
		return IBS210;
	}

	public void setIBS210(BigDecimal iBS210) {
		IBS210 = iBS210;
	}

	public BigDecimal getIBS223() {
		return IBS223;
	}

	public void setIBS223(BigDecimal iBS223) {
		IBS223 = iBS223;
	}

	public BigDecimal getIBS211() {
		return IBS211;
	}

	public void setIBS211(BigDecimal iBS211) {
		IBS211 = iBS211;
	}

	public BigDecimal getIBS212() {
		return IBS212;
	}

	public void setIBS212(BigDecimal iBS212) {
		IBS212 = iBS212;
	}

	public BigDecimal getIBS224() {
		return IBS224;
	}

	public void setIBS224(BigDecimal iBS224) {
		IBS224 = iBS224;
	}

	public BigDecimal getIBS213() {
		return IBS213;
	}

	public void setIBS213(BigDecimal iBS213) {
		IBS213 = iBS213;
	}

	public BigDecimal getIBS214() {
		return IBS214;
	}

	public void setIBS214(BigDecimal iBS214) {
		IBS214 = iBS214;
	}

	public BigDecimal getIBS215() {
		return IBS215;
	}

	public void setIBS215(BigDecimal iBS215) {
		IBS215 = iBS215;
	}

	public BigDecimal getIBS216() {
		return IBS216;
	}

	public void setIBS216(BigDecimal iBS216) {
		IBS216 = iBS216;
	}

	public BigDecimal getIBS217() {
		return IBS217;
	}

	public void setIBS217(BigDecimal iBS217) {
		IBS217 = iBS217;
	}

	public BigDecimal getIBS218() {
		return IBS218;
	}

	public void setIBS218(BigDecimal iBS218) {
		IBS218 = iBS218;
	}

	public BigDecimal getIBS225() {
		return IBS225;
	}

	public void setIBS225(BigDecimal iBS225) {
		IBS225 = iBS225;
	}

	public BigDecimal getIBS226() {
		return IBS226;
	}

	public void setIBS226(BigDecimal iBS226) {
		IBS226 = iBS226;
	}

	public BigDecimal getIBS219() {
		return IBS219;
	}

	public void setIBS219(BigDecimal iBS219) {
		IBS219 = iBS219;
	}

	public BigDecimal getIBS221() {
		return IBS221;
	}

	public void setIBS221(BigDecimal iBS221) {
		IBS221 = iBS221;
	}

	public BigDecimal getIBS222() {
		return IBS222;
	}

	public void setIBS222(BigDecimal iBS222) {
		IBS222 = iBS222;
	}

	public BigDecimal getIBS220() {
		return IBS220;
	}

	public void setIBS220(BigDecimal iBS220) {
		IBS220 = iBS220;
	}

	public BigDecimal getIBS002() {
		return IBS002;
	}

	public void setIBS002(BigDecimal iBS002) {
		IBS002 = iBS002;
	}

	public BigDecimal getIBS301() {
		return IBS301;
	}

	public void setIBS301(BigDecimal iBS301) {
		IBS301 = iBS301;
	}

	public BigDecimal getIBS302() {
		return IBS302;
	}

	public void setIBS302(BigDecimal iBS302) {
		IBS302 = iBS302;
	}

	public BigDecimal getIBS302_2() {
		return IBS302_2;
	}

	public void setIBS302_2(BigDecimal iBS302_2) {
		IBS302_2 = iBS302_2;
	}

	public BigDecimal getIBS303() {
		return IBS303;
	}

	public void setIBS303(BigDecimal iBS303) {
		IBS303 = iBS303;
	}

	public BigDecimal getIBS304() {
		return IBS304;
	}

	public void setIBS304(BigDecimal iBS304) {
		IBS304 = iBS304;
	}

	public BigDecimal getIBS305() {
		return IBS305;
	}

	public void setIBS305(BigDecimal iBS305) {
		IBS305 = iBS305;
	}

	public BigDecimal getIBS308() {
		return IBS308;
	}

	public void setIBS308(BigDecimal iBS308) {
		IBS308 = iBS308;
	}

	public BigDecimal getIBS306() {
		return IBS306;
	}

	public void setIBS306(BigDecimal iBS306) {
		IBS306 = iBS306;
	}

	public BigDecimal getIBS309() {
		return IBS309;
	}

	public void setIBS309(BigDecimal iBS309) {
		IBS309 = iBS309;
	}

	public BigDecimal getIBS003() {
		return IBS003;
	}

	public void setIBS003(BigDecimal iBS003) {
		IBS003 = iBS003;
	}

	public BigDecimal getIBS004() {
		return IBS004;
	}

	public void setIBS004(BigDecimal iBS004) {
		IBS004 = iBS004;
	}

	public BigDecimal getIPL100() {
		return IPL100;
	}

	public void setIPL100(BigDecimal iPL100) {
		IPL100 = iPL100;
	}

	public BigDecimal getIPL101() {
		return IPL101;
	}

	public void setIPL101(BigDecimal iPL101) {
		IPL101 = iPL101;
	}

	public BigDecimal getIPL102() {
		return IPL102;
	}

	public void setIPL102(BigDecimal iPL102) {
		IPL102 = iPL102;
	}

	public BigDecimal getIPL102_1() {
		return IPL102_1;
	}

	public void setIPL102_1(BigDecimal iPL102_1) {
		IPL102_1 = iPL102_1;
	}

	public BigDecimal getIPL102_1_1() {
		return IPL102_1_1;
	}

	public void setIPL102_1_1(BigDecimal iPL102_1_1) {
		IPL102_1_1 = iPL102_1_1;
	}

	public BigDecimal getIPL102_1_2() {
		return IPL102_1_2;
	}

	public void setIPL102_1_2(BigDecimal iPL102_1_2) {
		IPL102_1_2 = iPL102_1_2;
	}

	public BigDecimal getIPL102_2() {
		return IPL102_2;
	}

	public void setIPL102_2(BigDecimal iPL102_2) {
		IPL102_2 = iPL102_2;
	}

	public BigDecimal getIPL103() {
		return IPL103;
	}

	public void setIPL103(BigDecimal iPL103) {
		IPL103 = iPL103;
	}

	public BigDecimal getIPL104() {
		return IPL104;
	}

	public void setIPL104(BigDecimal iPL104) {
		IPL104 = iPL104;
	}

	public BigDecimal getIPL104_1() {
		return IPL104_1;
	}

	public void setIPL104_1(BigDecimal iPL104_1) {
		IPL104_1 = iPL104_1;
	}

	public BigDecimal getIPL105() {
		return IPL105;
	}

	public void setIPL105(BigDecimal iPL105) {
		IPL105 = iPL105;
	}

	public BigDecimal getIPL105_1() {
		return IPL105_1;
	}

	public void setIPL105_1(BigDecimal iPL105_1) {
		IPL105_1 = iPL105_1;
	}

	public BigDecimal getIPL105_2() {
		return IPL105_2;
	}

	public void setIPL105_2(BigDecimal iPL105_2) {
		IPL105_2 = iPL105_2;
	}

	public BigDecimal getIPL106() {
		return IPL106;
	}

	public void setIPL106(BigDecimal iPL106) {
		IPL106 = iPL106;
	}

	public BigDecimal getIPL107() {
		return IPL107;
	}

	public void setIPL107(BigDecimal iPL107) {
		IPL107 = iPL107;
	}

	public BigDecimal getIPL200() {
		return IPL200;
	}

	public void setIPL200(BigDecimal iPL200) {
		IPL200 = iPL200;
	}

	public BigDecimal getIPL201() {
		return IPL201;
	}

	public void setIPL201(BigDecimal iPL201) {
		IPL201 = iPL201;
	}

	public BigDecimal getIPL202() {
		return IPL202;
	}

	public void setIPL202(BigDecimal iPL202) {
		IPL202 = iPL202;
	}

	public BigDecimal getIPL202_1() {
		return IPL202_1;
	}

	public void setIPL202_1(BigDecimal iPL202_1) {
		IPL202_1 = iPL202_1;
	}

	public BigDecimal getIPL203() {
		return IPL203;
	}

	public void setIPL203(BigDecimal iPL203) {
		IPL203 = iPL203;
	}

	public BigDecimal getIPL203_1() {
		return IPL203_1;
	}

	public void setIPL203_1(BigDecimal iPL203_1) {
		IPL203_1 = iPL203_1;
	}

	public BigDecimal getIPL204() {
		return IPL204;
	}

	public void setIPL204(BigDecimal iPL204) {
		IPL204 = iPL204;
	}

	public BigDecimal getIPL205() {
		return IPL205;
	}

	public void setIPL205(BigDecimal iPL205) {
		IPL205 = iPL205;
	}

	public BigDecimal getIPL206() {
		return IPL206;
	}

	public void setIPL206(BigDecimal iPL206) {
		IPL206 = iPL206;
	}

	public BigDecimal getIPL207() {
		return IPL207;
	}

	public void setIPL207(BigDecimal iPL207) {
		IPL207 = iPL207;
	}

	public BigDecimal getIPL208() {
		return IPL208;
	}

	public void setIPL208(BigDecimal iPL208) {
		IPL208 = iPL208;
	}

	public BigDecimal getIPL208_1() {
		return IPL208_1;
	}

	public void setIPL208_1(BigDecimal iPL208_1) {
		IPL208_1 = iPL208_1;
	}

	public BigDecimal getIPL210() {
		return IPL210;
	}

	public void setIPL210(BigDecimal iPL210) {
		IPL210 = iPL210;
	}

	public BigDecimal getIPL209() {
		return IPL209;
	}

	public void setIPL209(BigDecimal iPL209) {
		IPL209 = iPL209;
	}

	public BigDecimal getIPL300() {
		return IPL300;
	}

	public void setIPL300(BigDecimal iPL300) {
		IPL300 = iPL300;
	}

	public BigDecimal getIPL301() {
		return IPL301;
	}

	public void setIPL301(BigDecimal iPL301) {
		IPL301 = iPL301;
	}

	public BigDecimal getIPL302() {
		return IPL302;
	}

	public void setIPL302(BigDecimal iPL302) {
		IPL302 = iPL302;
	}

	public BigDecimal getIPL400() {
		return IPL400;
	}

	public void setIPL400(BigDecimal iPL400) {
		IPL400 = iPL400;
	}

	public BigDecimal getIPL401() {
		return IPL401;
	}

	public void setIPL401(BigDecimal iPL401) {
		IPL401 = iPL401;
	}

	public BigDecimal getIPL500() {
		return IPL500;
	}

	public void setIPL500(BigDecimal iPL500) {
		IPL500 = iPL500;
	}

	public BigDecimal getIPL501() {
		return IPL501;
	}

	public void setIPL501(BigDecimal iPL501) {
		IPL501 = iPL501;
	}

	public BigDecimal getIPL502() {
		return IPL502;
	}

	public void setIPL502(BigDecimal iPL502) {
		IPL502 = iPL502;
	}

	public BigDecimal getIPL503() {
		return IPL503;
	}

	public void setIPL503(BigDecimal iPL503) {
		IPL503 = iPL503;
	}

	public BigDecimal getIPL504() {
		return IPL504;
	}

	public void setIPL504(BigDecimal iPL504) {
		IPL504 = iPL504;
	}

	public BigDecimal getIPL505() {
		return IPL505;
	}

	public void setIPL505(BigDecimal iPL505) {
		IPL505 = iPL505;
	}

	public BigDecimal getIPL506() {
		return IPL506;
	}

	public void setIPL506(BigDecimal iPL506) {
		IPL506 = iPL506;
	}

	public BigDecimal getIPL507() {
		return IPL507;
	}

	public void setIPL507(BigDecimal iPL507) {
		IPL507 = iPL507;
	}

	public BigDecimal getIPL508() {
		return IPL508;
	}

	public void setIPL508(BigDecimal iPL508) {
		IPL508 = iPL508;
	}

	public BigDecimal getICF101() {
		return ICF101;
	}

	public void setICF101(BigDecimal iCF101) {
		ICF101 = iCF101;
	}

	public BigDecimal getICF102() {
		return ICF102;
	}

	public void setICF102(BigDecimal iCF102) {
		ICF102 = iCF102;
	}

	public BigDecimal getICF103() {
		return ICF103;
	}

	public void setICF103(BigDecimal iCF103) {
		ICF103 = iCF103;
	}

	public BigDecimal getICF104() {
		return ICF104;
	}

	public void setICF104(BigDecimal iCF104) {
		ICF104 = iCF104;
	}

	public BigDecimal getICF105() {
		return ICF105;
	}

	public void setICF105(BigDecimal iCF105) {
		ICF105 = iCF105;
	}

	public BigDecimal getICF106() {
		return ICF106;
	}

	public void setICF106(BigDecimal iCF106) {
		ICF106 = iCF106;
	}

	public BigDecimal getICF107() {
		return ICF107;
	}

	public void setICF107(BigDecimal iCF107) {
		ICF107 = iCF107;
	}

	public BigDecimal getICF108() {
		return ICF108;
	}

	public void setICF108(BigDecimal iCF108) {
		ICF108 = iCF108;
	}

	public BigDecimal getICF100() {
		return ICF100;
	}

	public void setICF100(BigDecimal iCF100) {
		ICF100 = iCF100;
	}

	public BigDecimal getICF201() {
		return ICF201;
	}

	public void setICF201(BigDecimal iCF201) {
		ICF201 = iCF201;
	}

	public BigDecimal getICF202() {
		return ICF202;
	}

	public void setICF202(BigDecimal iCF202) {
		ICF202 = iCF202;
	}

	public BigDecimal getICF203() {
		return ICF203;
	}

	public void setICF203(BigDecimal iCF203) {
		ICF203 = iCF203;
	}

	public BigDecimal getICF204() {
		return ICF204;
	}

	public void setICF204(BigDecimal iCF204) {
		ICF204 = iCF204;
	}

	public BigDecimal getICF205() {
		return ICF205;
	}

	public void setICF205(BigDecimal iCF205) {
		ICF205 = iCF205;
	}

	public BigDecimal getICF206() {
		return ICF206;
	}

	public void setICF206(BigDecimal iCF206) {
		ICF206 = iCF206;
	}

	public BigDecimal getICF207() {
		return ICF207;
	}

	public void setICF207(BigDecimal iCF207) {
		ICF207 = iCF207;
	}

	public BigDecimal getICF208() {
		return ICF208;
	}

	public void setICF208(BigDecimal iCF208) {
		ICF208 = iCF208;
	}

	public BigDecimal getICF200() {
		return ICF200;
	}

	public void setICF200(BigDecimal iCF200) {
		ICF200 = iCF200;
	}

	public BigDecimal getICF001() {
		return ICF001;
	}

	public void setICF001(BigDecimal iCF001) {
		ICF001 = iCF001;
	}

	public BigDecimal getICF301() {
		return ICF301;
	}

	public void setICF301(BigDecimal iCF301) {
		ICF301 = iCF301;
	}

	public BigDecimal getICF302() {
		return ICF302;
	}

	public void setICF302(BigDecimal iCF302) {
		ICF302 = iCF302;
	}

	public BigDecimal getICF303() {
		return ICF303;
	}

	public void setICF303(BigDecimal iCF303) {
		ICF303 = iCF303;
	}

	public BigDecimal getICF304() {
		return ICF304;
	}

	public void setICF304(BigDecimal iCF304) {
		ICF304 = iCF304;
	}

	public BigDecimal getICF305() {
		return ICF305;
	}

	public void setICF305(BigDecimal iCF305) {
		ICF305 = iCF305;
	}

	public BigDecimal getICF300() {
		return ICF300;
	}

	public void setICF300(BigDecimal iCF300) {
		ICF300 = iCF300;
	}

	public BigDecimal getICF401() {
		return ICF401;
	}

	public void setICF401(BigDecimal iCF401) {
		ICF401 = iCF401;
	}

	public BigDecimal getICF402() {
		return ICF402;
	}

	public void setICF402(BigDecimal iCF402) {
		ICF402 = iCF402;
	}

	public BigDecimal getICF403() {
		return ICF403;
	}

	public void setICF403(BigDecimal iCF403) {
		ICF403 = iCF403;
	}

	public BigDecimal getICF404() {
		return ICF404;
	}

	public void setICF404(BigDecimal iCF404) {
		ICF404 = iCF404;
	}

	public BigDecimal getICF405() {
		return ICF405;
	}

	public void setICF405(BigDecimal iCF405) {
		ICF405 = iCF405;
	}

	public BigDecimal getICF400() {
		return ICF400;
	}

	public void setICF400(BigDecimal iCF400) {
		ICF400 = iCF400;
	}

	public BigDecimal getICF002() {
		return ICF002;
	}

	public void setICF002(BigDecimal iCF002) {
		ICF002 = iCF002;
	}

	public BigDecimal getICF501() {
		return ICF501;
	}

	public void setICF501(BigDecimal iCF501) {
		ICF501 = iCF501;
	}

	public BigDecimal getICF502() {
		return ICF502;
	}

	public void setICF502(BigDecimal iCF502) {
		ICF502 = iCF502;
	}

	public BigDecimal getICF503() {
		return ICF503;
	}

	public void setICF503(BigDecimal iCF503) {
		ICF503 = iCF503;
	}

	public BigDecimal getICF504() {
		return ICF504;
	}

	public void setICF504(BigDecimal iCF504) {
		ICF504 = iCF504;
	}

	public BigDecimal getICF505() {
		return ICF505;
	}

	public void setICF505(BigDecimal iCF505) {
		ICF505 = iCF505;
	}

	public BigDecimal getICF500() {
		return ICF500;
	}

	public void setICF500(BigDecimal iCF500) {
		ICF500 = iCF500;
	}

	public BigDecimal getICF601() {
		return ICF601;
	}

	public void setICF601(BigDecimal iCF601) {
		ICF601 = iCF601;
	}

	public BigDecimal getICF602() {
		return ICF602;
	}

	public void setICF602(BigDecimal iCF602) {
		ICF602 = iCF602;
	}

	public BigDecimal getICF603() {
		return ICF603;
	}

	public void setICF603(BigDecimal iCF603) {
		ICF603 = iCF603;
	}

	public BigDecimal getICF604() {
		return ICF604;
	}

	public void setICF604(BigDecimal iCF604) {
		ICF604 = iCF604;
	}

	public BigDecimal getICF600() {
		return ICF600;
	}

	public void setICF600(BigDecimal iCF600) {
		ICF600 = iCF600;
	}

	public BigDecimal getICF003() {
		return ICF003;
	}

	public void setICF003(BigDecimal iCF003) {
		ICF003 = iCF003;
	}

	public BigDecimal getICF701() {
		return ICF701;
	}

	public void setICF701(BigDecimal iCF701) {
		ICF701 = iCF701;
	}

	public BigDecimal getICF004() {
		return ICF004;
	}

	public void setICF004(BigDecimal iCF004) {
		ICF004 = iCF004;
	}

	public BigDecimal getICF801() {
		return ICF801;
	}

	public void setICF801(BigDecimal iCF801) {
		ICF801 = iCF801;
	}

	public BigDecimal getICF005() {
		return ICF005;
	}

	public void setICF005(BigDecimal iCF005) {
		ICF005 = iCF005;
	}

	public BigDecimal getICF901() {
		return ICF901;
	}

	public void setICF901(BigDecimal iCF901) {
		ICF901 = iCF901;
	}

	public BigDecimal getICF902() {
		return ICF902;
	}

	public void setICF902(BigDecimal iCF902) {
		ICF902 = iCF902;
	}

	public BigDecimal getICF903() {
		return ICF903;
	}

	public void setICF903(BigDecimal iCF903) {
		ICF903 = iCF903;
	}

	public BigDecimal getICF904() {
		return ICF904;
	}

	public void setICF904(BigDecimal iCF904) {
		ICF904 = iCF904;
	}

	public BigDecimal getICF905() {
		return ICF905;
	}

	public void setICF905(BigDecimal iCF905) {
		ICF905 = iCF905;
	}

	public BigDecimal getICF906() {
		return ICF906;
	}

	public void setICF906(BigDecimal iCF906) {
		ICF906 = iCF906;
	}

	public BigDecimal getICF907() {
		return ICF907;
	}

	public void setICF907(BigDecimal iCF907) {
		ICF907 = iCF907;
	}

	public BigDecimal getICF908() {
		return ICF908;
	}

	public void setICF908(BigDecimal iCF908) {
		ICF908 = iCF908;
	}

	public BigDecimal getICF909() {
		return ICF909;
	}

	public void setICF909(BigDecimal iCF909) {
		ICF909 = iCF909;
	}

	public BigDecimal getICF910() {
		return ICF910;
	}

	public void setICF910(BigDecimal iCF910) {
		ICF910 = iCF910;
	}

	public BigDecimal getICF911() {
		return ICF911;
	}

	public void setICF911(BigDecimal iCF911) {
		ICF911 = iCF911;
	}

	public BigDecimal getICF912() {
		return ICF912;
	}

	public void setICF912(BigDecimal iCF912) {
		ICF912 = iCF912;
	}

	public BigDecimal getICF913() {
		return ICF913;
	}

	public void setICF913(BigDecimal iCF913) {
		ICF913 = iCF913;
	}

	public BigDecimal getICF914() {
		return ICF914;
	}

	public void setICF914(BigDecimal iCF914) {
		ICF914 = iCF914;
	}

	public BigDecimal getICF915() {
		return ICF915;
	}

	public void setICF915(BigDecimal iCF915) {
		ICF915 = iCF915;
	}

	public BigDecimal getICF916() {
		return ICF916;
	}

	public void setICF916(BigDecimal iCF916) {
		ICF916 = iCF916;
	}

	public BigDecimal getICF917() {
		return ICF917;
	}

	public void setICF917(BigDecimal iCF917) {
		ICF917 = iCF917;
	}

	public BigDecimal getICF918() {
		return ICF918;
	}

	public void setICF918(BigDecimal iCF918) {
		ICF918 = iCF918;
	}

	public BigDecimal getICF919() {
		return ICF919;
	}

	public void setICF919(BigDecimal iCF919) {
		ICF919 = iCF919;
	}

	public BigDecimal getICF920() {
		return ICF920;
	}

	public void setICF920(BigDecimal iCF920) {
		ICF920 = iCF920;
	}

	public BigDecimal getICF921() {
		return ICF921;
	}

	public void setICF921(BigDecimal iCF921) {
		ICF921 = iCF921;
	}

	public BigDecimal getICF922() {
		return ICF922;
	}

	public void setICF922(BigDecimal iCF922) {
		ICF922 = iCF922;
	}

	public BigDecimal getICF923() {
		return ICF923;
	}

	public void setICF923(BigDecimal iCF923) {
		ICF923 = iCF923;
	}

	public BigDecimal getICF924() {
		return ICF924;
	}

	public void setICF924(BigDecimal iCF924) {
		ICF924 = iCF924;
	}

	public BigDecimal getICF925() {
		return ICF925;
	}

	public void setICF925(BigDecimal iCF925) {
		ICF925 = iCF925;
	}

	public BigDecimal getICF926() {
		return ICF926;
	}

	public void setICF926(BigDecimal iCF926) {
		ICF926 = iCF926;
	}

	public BigDecimal getICF927() {
		return ICF927;
	}

	public void setICF927(BigDecimal iCF927) {
		ICF927 = iCF927;
	}

	public BigDecimal getITN101() {
		return ITN101;
	}

	public void setITN101(BigDecimal iTN101) {
		ITN101 = iTN101;
	}

	public BigDecimal getITN102() {
		return ITN102;
	}

	public void setITN102(BigDecimal iTN102) {
		ITN102 = iTN102;
	}

	public BigDecimal getITN103() {
		return ITN103;
	}

	public void setITN103(BigDecimal iTN103) {
		ITN103 = iTN103;
	}

	public BigDecimal getITN104() {
		return ITN104;
	}

	public void setITN104(BigDecimal iTN104) {
		ITN104 = iTN104;
	}

	public BigDecimal getITN105() {
		return ITN105;
	}

	public void setITN105(BigDecimal iTN105) {
		ITN105 = iTN105;
	}

	public BigDecimal getITN106() {
		return ITN106;
	}

	public void setITN106(BigDecimal iTN106) {
		ITN106 = iTN106;
	}

	public BigDecimal getITN107() {
		return ITN107;
	}

	public void setITN107(BigDecimal iTN107) {
		ITN107 = iTN107;
	}

	public BigDecimal getITN108() {
		return ITN108;
	}

	public void setITN108(BigDecimal iTN108) {
		ITN108 = iTN108;
	}

	public BigDecimal getITN109() {
		return ITN109;
	}

	public void setITN109(BigDecimal iTN109) {
		ITN109 = iTN109;
	}

	public BigDecimal getITN110() {
		return ITN110;
	}

	public void setITN110(BigDecimal iTN110) {
		ITN110 = iTN110;
	}

	public BigDecimal getITN111() {
		return ITN111;
	}

	public void setITN111(BigDecimal iTN111) {
		ITN111 = iTN111;
	}

	public BigDecimal getITN112() {
		return ITN112;
	}

	public void setITN112(BigDecimal iTN112) {
		ITN112 = iTN112;
	}

	public BigDecimal getITN113() {
		return ITN113;
	}

	public void setITN113(BigDecimal iTN113) {
		ITN113 = iTN113;
	}

	public BigDecimal getITN114() {
		return ITN114;
	}

	public void setITN114(BigDecimal iTN114) {
		ITN114 = iTN114;
	}

	public BigDecimal getITN115() {
		return ITN115;
	}

	public void setITN115(BigDecimal iTN115) {
		ITN115 = iTN115;
	}

	public BigDecimal getITN116() {
		return ITN116;
	}

	public void setITN116(BigDecimal iTN116) {
		ITN116 = iTN116;
	}

	public BigDecimal getITN117() {
		return ITN117;
	}

	public void setITN117(BigDecimal iTN117) {
		ITN117 = iTN117;
	}

	public BigDecimal getITN118() {
		return ITN118;
	}

	public void setITN118(BigDecimal iTN118) {
		ITN118 = iTN118;
	}

	public BigDecimal getITN119() {
		return ITN119;
	}

	public void setITN119(BigDecimal iTN119) {
		ITN119 = iTN119;
	}

	public BigDecimal getITN120() {
		return ITN120;
	}

	public void setITN120(BigDecimal iTN120) {
		ITN120 = iTN120;
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
	       selectSql += ",IBS101";
	       selectSql += ",IBS102";
	       selectSql += ",IBS103";
	       selectSql += ",IBS103_1";
	       selectSql += ",IBS103_1_1";
	       selectSql += ",IBS103_1_2";
	       selectSql += ",IBS103_1_3";
	       selectSql += ",IBS103_1_4";
	       selectSql += ",IBS103_1_5";
	       selectSql += ",IBS103_2";
	       selectSql += ",IBS103_2_1";
	       selectSql += ",IBS103_2_2";
	       selectSql += ",IBS103_2_3";
	       selectSql += ",IBS104";
	       selectSql += ",IBS105";
	       selectSql += ",IBS107";
	       selectSql += ",IBS108";
	       selectSql += ",IBS109";
	       selectSql += ",IBS106";
	       selectSql += ",IBS110";
	       selectSql += ",IBS111";
	       selectSql += ",IBS112";
	       selectSql += ",IBS113";
	       selectSql += ",IBS114";
	       selectSql += ",IBS126";
	       selectSql += ",IBS127";
	       selectSql += ",IBS115";
	       selectSql += ",IBS116";
	       selectSql += ",IBS116_1";
	       selectSql += ",IBS116_1_1";
	       selectSql += ",IBS116_1_2";
	       selectSql += ",IBS116_1_3";
	       selectSql += ",IBS116_1_4";
	       selectSql += ",IBS116_1_5";
	       selectSql += ",IBS116_2";
	       selectSql += ",IBS116_2_1";
	       selectSql += ",IBS116_2_2";
	       selectSql += ",IBS116_3";
	       selectSql += ",IBS117";
	       selectSql += ",IBS117_1";
	       selectSql += ",IBS117_1_1";
	       selectSql += ",IBS117_1_2";
	       selectSql += ",IBS117_1_3";
	       selectSql += ",IBS117_1_4";
	       selectSql += ",IBS120";
	       selectSql += ",IBS118";
	       selectSql += ",IBS119";
	       selectSql += ",IBS121";
	       selectSql += ",IBS128";
	       selectSql += ",IBS122";
	       selectSql += ",IBS124";
	       selectSql += ",IBS129";
	       selectSql += ",IBS125";
	       selectSql += ",IBS123";
	       selectSql += ",IBS001";
	       selectSql += ",IBS201";
	       selectSql += ",IBS202";
	       selectSql += ",IBS203";
	       selectSql += ",IBS204";
	       selectSql += ",IBS205";
	       selectSql += ",IBS206";
	       selectSql += ",IBS207";
	       selectSql += ",IBS208";
	       selectSql += ",IBS209";
	       selectSql += ",IBS210";
	       selectSql += ",IBS223";
	       selectSql += ",IBS211";
	       selectSql += ",IBS212";
	       selectSql += ",IBS224";
	       selectSql += ",IBS213";
	       selectSql += ",IBS214";
	       selectSql += ",IBS215";
	       selectSql += ",IBS216";
	       selectSql += ",IBS217";
	       selectSql += ",IBS218";
	       selectSql += ",IBS225";
	       selectSql += ",IBS226";
	       selectSql += ",IBS219";
	       selectSql += ",IBS221";
	       selectSql += ",IBS222";
	       selectSql += ",IBS220";
	       selectSql += ",IBS002";
	       selectSql += ",IBS301";
	       selectSql += ",IBS302";
	       selectSql += ",IBS302_2";
	       selectSql += ",IBS303";
	       selectSql += ",IBS304";
	       selectSql += ",IBS305";
	       selectSql += ",IBS308";
	       selectSql += ",IBS306";
	       selectSql += ",IBS309";
	       selectSql += ",IBS003";
	       selectSql += ",IBS004";
	       selectSql += ",IPL100";
	       selectSql += ",IPL101";
	       selectSql += ",IPL102";
	       selectSql += ",IPL102_1";
	       selectSql += ",IPL102_1_1";
	       selectSql += ",IPL102_1_2";
	       selectSql += ",IPL102_2";
	       selectSql += ",IPL103";
	       selectSql += ",IPL104";
	       selectSql += ",IPL104_1";
	       selectSql += ",IPL105";
	       selectSql += ",IPL105_1";
	       selectSql += ",IPL105_2";
	       selectSql += ",IPL106";
	       selectSql += ",IPL107";
	       selectSql += ",IPL200";
	       selectSql += ",IPL201";
	       selectSql += ",IPL202";
	       selectSql += ",IPL202_1";
	       selectSql += ",IPL203";
	       selectSql += ",IPL203_1";
	       selectSql += ",IPL204";
	       selectSql += ",IPL205";
	       selectSql += ",IPL206";
	       selectSql += ",IPL207";
	       selectSql += ",IPL208";
	       selectSql += ",IPL208_1";
	       selectSql += ",IPL210";
	       selectSql += ",IPL209";
	       selectSql += ",IPL300";
	       selectSql += ",IPL301";
	       selectSql += ",IPL302";
	       selectSql += ",IPL400";
	       selectSql += ",IPL401";
	       selectSql += ",IPL500";
	       selectSql += ",IPL501";
	       selectSql += ",IPL502";
	       selectSql += ",IPL503";
	       selectSql += ",IPL504";
	       selectSql += ",IPL505";
	       selectSql += ",IPL506";
	       selectSql += ",IPL507";
	       selectSql += ",IPL508";
	       selectSql += ",ICF101";
	       selectSql += ",ICF102";
	       selectSql += ",ICF103";
	       selectSql += ",ICF104";
	       selectSql += ",ICF105";
	       selectSql += ",ICF106";
	       selectSql += ",ICF107";
	       selectSql += ",ICF108";
	       selectSql += ",ICF100";
	       selectSql += ",ICF201";
	       selectSql += ",ICF202";
	       selectSql += ",ICF203";
	       selectSql += ",ICF204";
	       selectSql += ",ICF205";
	       selectSql += ",ICF206";
	       selectSql += ",ICF207";
	       selectSql += ",ICF208";
	       selectSql += ",ICF200";
	       selectSql += ",ICF001";
	       selectSql += ",ICF301";
	       selectSql += ",ICF302";
	       selectSql += ",ICF303";
	       selectSql += ",ICF304";
	       selectSql += ",ICF305";
	       selectSql += ",ICF300";
	       selectSql += ",ICF401";
	       selectSql += ",ICF402";
	       selectSql += ",ICF403";
	       selectSql += ",ICF404";
	       selectSql += ",ICF405";
	       selectSql += ",ICF400";
	       selectSql += ",ICF002";
	       selectSql += ",ICF501";
	       selectSql += ",ICF502";
	       selectSql += ",ICF503";
	       selectSql += ",ICF504";
	       selectSql += ",ICF505";
	       selectSql += ",ICF500";
	       selectSql += ",ICF601";
	       selectSql += ",ICF602";
	       selectSql += ",ICF603";
	       selectSql += ",ICF604";
	       selectSql += ",ICF600";
	       selectSql += ",ICF003";
	       selectSql += ",ICF701";
	       selectSql += ",ICF004";
	       selectSql += ",ICF801";
	       selectSql += ",ICF005";
	       selectSql += ",ICF901";
	       selectSql += ",ICF902";
	       selectSql += ",ICF903";
	       selectSql += ",ICF904";
	       selectSql += ",ICF905";
	       selectSql += ",ICF906";
	       selectSql += ",ICF907";
	       selectSql += ",ICF908";
	       selectSql += ",ICF909";
	       selectSql += ",ICF910";
	       selectSql += ",ICF911";
	       selectSql += ",ICF912";
	       selectSql += ",ICF913";
	       selectSql += ",ICF914";
	       selectSql += ",ICF915";
	       selectSql += ",ICF916";
	       selectSql += ",ICF917";
	       selectSql += ",ICF918";
	       selectSql += ",ICF919";
	       selectSql += ",ICF920";
	       selectSql += ",ICF921";
	       selectSql += ",ICF922";
	       selectSql += ",ICF923";
	       selectSql += ",ICF924";
	       selectSql += ",ICF925";
	       selectSql += ",ICF926";
	       selectSql += ",ICF927";
	       selectSql += ",ITN101";
	       selectSql += ",ITN102";
	       selectSql += ",ITN103";
	       selectSql += ",ITN104";
	       selectSql += ",ITN105";
	       selectSql += ",ITN106";
	       selectSql += ",ITN107";
	       selectSql += ",ITN108";
	       selectSql += ",ITN109";
	       selectSql += ",ITN110";
	       selectSql += ",ITN111";
	       selectSql += ",ITN112";
	       selectSql += ",ITN113";
	       selectSql += ",ITN114";
	       selectSql += ",ITN115";
	       selectSql += ",ITN116";
	       selectSql += ",ITN117";
	       selectSql += ",ITN118";
	       selectSql += ",ITN119";
	       selectSql += ",ITN120";
	       selectSql += ",last_update_timestamp";
	       selectSql += ",ROW_KEY";
		   return selectSql.substring(1);
	    }

	    public String insertBySelectSql(){
		   String selColumn = createSelectColumnSql();           
	       String sql = "insert into insu_fina_sheet_p ("+selColumn+") select "+selColumn+" from insu_fina_sheet";
	       return sql;
	    }
}
