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
@Table(name = "secu_fina_sheet")
public class BondSecuFinaSheet implements Serializable {
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
    private BigDecimal SBS101;
    
	/**
	 * 其中:客户资金存款
	 */
    @Column(length=20)
    private BigDecimal SBS101_1;
    
	/**
	 * 结算备付金
	 */
    @Column(length=20)
    private BigDecimal SBS102;
    
	/**
	 * 其中:客户备付金
	 */
    @Column(length=20)
    private BigDecimal SBS102_1;
    
	/**
	 * 拆出资金
	 */
    @Column(length=20)
    private BigDecimal SBS103;
    
	/**
	 * 交易性金融资产
	 */
    @Column(length=20)
    private BigDecimal SBS104;
    
	/**
	 * 衍生金融资产
	 */
    @Column(length=20)
    private BigDecimal SBS105;
    
	/**
	 * 买入返售金融资产
	 */
    @Column(length=20)
    private BigDecimal SBS106;
    
	/**
	 * 应收利息
	 */
    @Column(length=20)
    private BigDecimal SBS107;
    
	/**
	 * 存出保证金
	 */
    @Column(length=20)
    private BigDecimal SBS108;
    
	/**
	 * 可供出售金融资产
	 */
    @Column(length=20)
    private BigDecimal SBS109;
    
	/**
	 * 持有至到期投资
	 */
    @Column(length=20)
    private BigDecimal SBS110;
    
	/**
	 * 长期股权投资
	 */
    @Column(length=20)
    private BigDecimal SBS111;
    
	/**
	 * 固定资产
	 */
    @Column(length=20)
    private BigDecimal SBS113;
    
	/**
	 * 无形资产
	 */
    @Column(length=20)
    private BigDecimal SBS114;
    
	/**
	 * 其中:交易席位费
	 */
    @Column(length=20)
    private BigDecimal SBS114_1;
    
	/**
	 * 递延所得税资产
	 */
    @Column(length=20)
    private BigDecimal SBS115;
    
	/**
	 * 投资性房地产
	 */
    @Column(length=20)
    private BigDecimal SBS112;
    
	/**
	 * 其他资产
	 */
    @Column(length=20)
    private BigDecimal SBS116;
    
	/**
	 * 资产总计
	 */
    @Column(length=20)
    private BigDecimal SBS001;
    
	/**
	 * 短期借款
	 */
    @Column(length=20)
    private BigDecimal SBS201;
    
	/**
	 * 其中:质押借款
	 */
    @Column(length=20)
    private BigDecimal SBS201_1;
    
	/**
	 * 拆入资金
	 */
    @Column(length=20)
    private BigDecimal SBS202;
    
	/**
	 * 交易性金融负债
	 */
    @Column(length=20)
    private BigDecimal SBS203;
    
	/**
	 * 衍生金融负债
	 */
    @Column(length=20)
    private BigDecimal SBS204;
    
	/**
	 * 卖出回购金融资产款
	 */
    @Column(length=20)
    private BigDecimal SBS205;
    
	/**
	 * 代理买卖证券款
	 */
    @Column(length=20)
    private BigDecimal SBS206;
    
	/**
	 * 代理承销证券款
	 */
    @Column(length=20)
    private BigDecimal SBS207;
    
	/**
	 * 应付职工薪酬
	 */
    @Column(length=20)
    private BigDecimal SBS208;
    
	/**
	 * 应交税费
	 */
    @Column(length=20)
    private BigDecimal SBS209;
    
	/**
	 * 应付利息
	 */
    @Column(length=20)
    private BigDecimal SBS210;
    
	/**
	 * 长期借款
	 */
    @Column(length=20)
    private BigDecimal SBS212;
    
	/**
	 * 应付债券
	 */
    @Column(length=20)
    private BigDecimal SBS213;
    
	/**
	 * 递延所得税负债
	 */
    @Column(length=20)
    private BigDecimal SBS214;
    
	/**
	 * 预计负债
	 */
    @Column(length=20)
    private BigDecimal SBS216;
    
	/**
	 * 其他负债
	 */
    @Column(length=20)
    private BigDecimal SBS215;
    
	/**
	 * 负债合计
	 */
    @Column(length=20)
    private BigDecimal SBS002;
    
	/**
	 * 实收资本(或股本)
	 */
    @Column(length=20)
    private BigDecimal SBS301;
    
	/**
	 * 资本公积
	 */
    @Column(length=20)
    private BigDecimal SBS302;
    
	/**
	 * 减:库存股
	 */
    @Column(length=20)
    private BigDecimal SBS302_2;
    
	/**
	 * 盈余公积
	 */
    @Column(length=20)
    private BigDecimal SBS303;
    
	/**
	 * 未分配利润
	 */
    @Column(length=20)
    private BigDecimal SBS305;
    
	/**
	 * 一般风险准备
	 */
    @Column(length=20)
    private BigDecimal SBS304;
    
	/**
	 * 交易风险准备
	 */
    @Column(length=20)
    private BigDecimal SBS309;
    
	/**
	 * 外币报表折算差额
	 */
    @Column(length=20)
    private BigDecimal SBS306;
    
	/**
	 * 少数股东权益
	 */
    @Column(length=20)
    private BigDecimal SBS310;
    
	/**
	 * 归属于母公司所有者权益合计
	 */
    @Column(length=20)
    private BigDecimal SBS307;
    
	/**
	 * 所有者权益(或股东权益)合计
	 */
    @Column(length=20)
    private BigDecimal SBS003;
    
	/**
	 * 负债和所有者权益(或股东权益)总计
	 */
    @Column(length=20)
    private BigDecimal SBS004;
    
	/**
	 * 营业收入
	 */
    @Column(length=20)
    private BigDecimal SPL100;
    
	/**
	 * 手续费及佣金收入
	 */
    @Column(length=20)
    private BigDecimal SPL101;
    
	/**
	 * 其中:代理买卖证券业务净收入
	 */
    @Column(length=20)
    private BigDecimal SPL101_1;
    
	/**
	 * 证券承销业务净收入
	 */
    @Column(length=20)
    private BigDecimal SPL101_2;
    
	/**
	 * 委托客户资产管理业务净收入
	 */
    @Column(length=20)
    private BigDecimal SPL101_3;
    
	/**
	 * 基金管理费收入
	 */
    @Column(length=20)
    private BigDecimal SPL101_4;
    
	/**
	 * 基金销售收入
	 */
    @Column(length=20)
    private BigDecimal SPL101_5;
    
	/**
	 * 利息净收入
	 */
    @Column(length=20)
    private BigDecimal SPL103;
    
	/**
	 * 投资收益(损失以“－”号填列)
	 */
    @Column(length=20)
    private BigDecimal SPL104;
    
	/**
	 * 其中:对联营企业和合营企业的投资收益
	 */
    @Column(length=20)
    private BigDecimal SPL104_1;
    
	/**
	 * 公允价值变动收益(损失以“－”号填列)
	 */
    @Column(length=20)
    private BigDecimal SPL105;
    
	/**
	 * 汇兑收益(损失以“-”号填列)
	 */
    @Column(length=20)
    private BigDecimal SPL106;
    
	/**
	 * 其他业务收入
	 */
    @Column(length=20)
    private BigDecimal SPL107;
    
	/**
	 * 营业支出
	 */
    @Column(length=20)
    private BigDecimal SPL200;
    
	/**
	 * 营业税金及附加
	 */
    @Column(length=20)
    private BigDecimal SPL201;
    
	/**
	 * 业务及管理费
	 */
    @Column(length=20)
    private BigDecimal SPL202;
    
	/**
	 * 资产减值损失
	 */
    @Column(length=20)
    private BigDecimal SPL203;
    
	/**
	 * 其他业务成本
	 */
    @Column(length=20)
    private BigDecimal SPL204;
    
	/**
	 * 营业利润(亏损以“－”号填列)
	 */
    @Column(length=20)
    private BigDecimal SPL300;
    
	/**
	 * 营业外收入
	 */
    @Column(length=20)
    private BigDecimal SPL301;
    
	/**
	 * 营业外支出
	 */
    @Column(length=20)
    private BigDecimal SPL302;
    
	/**
	 * 利润总额(亏损总额以“－”号填列)
	 */
    @Column(length=20)
    private BigDecimal SPL400;
    
	/**
	 * 所得税费用
	 */
    @Column(length=20)
    private BigDecimal SPL401;
    
	/**
	 * 净利润(净亏损以“－”号填列)
	 */
    @Column(length=20)
    private BigDecimal SPL500;
    
	/**
	 * 少数股东损益
	 */
    @Column(length=20)
    private BigDecimal SPL501;
    
	/**
	 * 归属于母公司所有者(或股东)的净利润
	 */
    @Column(length=20)
    private BigDecimal SPL502;
    
	/**
	 * 基本每股收益
	 */
    @Column(length=20)
    private BigDecimal SPL503;
    
	/**
	 * 稀释每股收益
	 */
    @Column(length=20)
    private BigDecimal SPL504;
    
	/**
	 * 其他综合收益
	 */
    @Column(length=20)
    private BigDecimal SPL505;
    
	/**
	 * 综合收益总额
	 */
    @Column(length=20)
    private BigDecimal SPL506;
    
	/**
	 * 归属于母公司所有者(或股东)的综合收益总额
	 */
    @Column(length=20)
    private BigDecimal SPL507;
    
	/**
	 * 归属于少数股东的综合收益总额
	 */
    @Column(length=20)
    private BigDecimal SPL508;
    
	/**
	 * 处置交易性金融资产净增加额
	 */
    @Column(length=20)
    private BigDecimal SCF101;
    
	/**
	 * 处置可供出售金融资产净增加额
	 */
    @Column(length=20)
    private BigDecimal SCF106;
    
	/**
	 * 收取利息、手续费及佣金的现金
	 */
    @Column(length=20)
    private BigDecimal SCF102;
    
	/**
	 * 拆入资金净增加额
	 */
    @Column(length=20)
    private BigDecimal SCF103;
    
	/**
	 * 回购业务资金净增加额
	 */
    @Column(length=20)
    private BigDecimal SCF104;
    
	/**
	 * 收到其他与经营活动有关的现金
	 */
    @Column(length=20)
    private BigDecimal SCF105;
    
	/**
	 * 经营活动现金流入小计
	 */
    @Column(length=20)
    private BigDecimal SCF100;
    
	/**
	 * 支付给职工以及为职工支付的现金
	 */
    @Column(length=20)
    private BigDecimal SCF202;
    
	/**
	 * 支付的各项税费
	 */
    @Column(length=20)
    private BigDecimal SCF203;
    
	/**
	 * 支付其他与经营活动有关的现金
	 */
    @Column(length=20)
    private BigDecimal SCF204;
    
	/**
	 * 经营活动现金流出小计
	 */
    @Column(length=20)
    private BigDecimal SCF200;
    
	/**
	 * 经营活动产生的现金流量净额
	 */
    @Column(length=20)
    private BigDecimal SCF001;
    
	/**
	 * 收回投资收到的现金
	 */
    @Column(length=20)
    private BigDecimal SCF301;
    
	/**
	 * 处置固定资产、无形资产和其他长期资产收回的现金净额
	 */
    @Column(length=20)
    private BigDecimal SCF304;
    
	/**
	 * 收到其他与投资活动有关的现金
	 */
    @Column(length=20)
    private BigDecimal SCF303;
    
	/**
	 * 投资活动现金流入小计
	 */
    @Column(length=20)
    private BigDecimal SCF300;
    
	/**
	 * 投资支付的现金
	 */
    @Column(length=20)
    private BigDecimal SCF401;
    
	/**
	 * 购建固定资产、无形资产和其他长期资产支付的现金
	 */
    @Column(length=20)
    private BigDecimal SCF402;
    
	/**
	 * 支付其他与投资活动有关的现金
	 */
    @Column(length=20)
    private BigDecimal SCF403;
    
	/**
	 * 投资活动现金流出小计
	 */
    @Column(length=20)
    private BigDecimal SCF400;
    
	/**
	 * 投资活动产生的现金流量净额
	 */
    @Column(length=20)
    private BigDecimal SCF002;
    
	/**
	 * 吸收投资收到的现金
	 */
    @Column(length=20)
    private BigDecimal SCF501;
    
	/**
	 * 发行债券收到的现金
	 */
    @Column(length=20)
    private BigDecimal SCF502;
    
	/**
	 * 收到其他与筹资活动有关的现金
	 */
    @Column(length=20)
    private BigDecimal SCF503;
    
	/**
	 * 筹资活动现金流入小计
	 */
    @Column(length=20)
    private BigDecimal SCF500;
    
	/**
	 * 偿还债务支付的现金
	 */
    @Column(length=20)
    private BigDecimal SCF601;
    
	/**
	 * 分配股利、利润或偿付利息支付的现金
	 */
    @Column(length=20)
    private BigDecimal SCF602;
    
	/**
	 * 支付其他与筹资活动有关的现金
	 */
    @Column(length=20)
    private BigDecimal SCF603;
    
	/**
	 * 筹资活动现金流出小计
	 */
    @Column(length=20)
    private BigDecimal SCF600;
    
	/**
	 * 筹资活动产生的现金流量净额
	 */
    @Column(length=20)
    private BigDecimal SCF003;
    
	/**
	 * 汇率变动对现金及现金等价物的影响
	 */
    @Column(length=20)
    private BigDecimal SCF004;
    
	/**
	 * 现金及现金等价物净增加额
	 */
    @Column(length=20)
    private BigDecimal SCF005;
    
	/**
	 * 加:期初现金及现金等价物余额
	 */
    @Column(length=20)
    private BigDecimal SCF006;
    
	/**
	 * 期末现金及现金等价物余额
	 */
    @Column(length=20)
    private BigDecimal SCF007;
    
	/**
	 * 净利润
	 */
    @Column(length=20)
    private BigDecimal SCF901;
    
	/**
	 * 加:资产减值准备
	 */
    @Column(length=20)
    private BigDecimal SCF902;
    
	/**
	 * 固定资产折旧、油气资产折耗、生产性生物资产折旧
	 */
    @Column(length=20)
    private BigDecimal SCF903;
    
	/**
	 * 无形资产摊销
	 */
    @Column(length=20)
    private BigDecimal SCF904;
    
	/**
	 * 长期待摊费用摊销
	 */
    @Column(length=20)
    private BigDecimal SCF905;
    
	/**
	 * 待摊费用减少(减:增加)
	 */
    @Column(length=20)
    private BigDecimal SCF906;
    
	/**
	 * 预提费用增加(减:减少)
	 */
    @Column(length=20)
    private BigDecimal SCF907;
    
	/**
	 * 处置固定资产、无形资产和其他长期资产的损失
	 */
    @Column(length=20)
    private BigDecimal SCF908;
    
	/**
	 * 固定资产报废损失
	 */
    @Column(length=20)
    private BigDecimal SCF909;
    
	/**
	 * 公允价值变动损失
	 */
    @Column(length=20)
    private BigDecimal SCF910;
    
	/**
	 * 财务费用
	 */
    @Column(length=20)
    private BigDecimal SCF911;
    
	/**
	 * 投资损失
	 */
    @Column(length=20)
    private BigDecimal SCF912;
    
	/**
	 * 递延所得税资产减少
	 */
    @Column(length=20)
    private BigDecimal SCF913;
    
	/**
	 * 递延所得税负债增加
	 */
    @Column(length=20)
    private BigDecimal SCF914;
    
	/**
	 * 存货的减少
	 */
    @Column(length=20)
    private BigDecimal SCF915;
    
	/**
	 * 经营性应收项目的减少
	 */
    @Column(length=20)
    private BigDecimal SCF916;
    
	/**
	 * 经营性应付项目的增加
	 */
    @Column(length=20)
    private BigDecimal SCF917;
    
	/**
	 * 其他
	 */
    @Column(length=20)
    private BigDecimal SCF918;
    
	/**
	 * (间接)经营活动产生的现金流量净额
	 */
    @Column(length=20)
    private BigDecimal SCF919;
    
	/**
	 * 债务转为资本
	 */
    @Column(length=20)
    private BigDecimal SCF920;
    
	/**
	 * 一年内到期的可转换公司债券
	 */
    @Column(length=20)
    private BigDecimal SCF921;
    
	/**
	 * 融资租入固定资产
	 */
    @Column(length=20)
    private BigDecimal SCF922;
    
	/**
	 * 现金的期末余额
	 */
    @Column(length=20)
    private BigDecimal SCF923;
    
	/**
	 * 减:现金的期初余额
	 */
    @Column(length=20)
    private BigDecimal SCF924;
    
	/**
	 * 加:现金等价物的期末余额
	 */
    @Column(length=20)
    private BigDecimal SCF925;
    
	/**
	 * 减:现金等价物的期初余额
	 */
    @Column(length=20)
    private BigDecimal SCF926;
    
	/**
	 * (间接)现金及现金等价物净增加额
	 */
    @Column(length=20)
    private BigDecimal SCF927;
    
	/**
	 * 各项风险准备金
	 */
    @Column(length=20)
    private BigDecimal STN101;
    
	/**
	 * 股权基金交易额
	 */
    @Column(length=20)
    private BigDecimal STN102;
    
	/**
	 * 员工人数
	 */
    @Column(length=20)
    private BigDecimal STN103;
    
	/**
	 * 营业部数量
	 */
    @Column(length=20)
    private BigDecimal STN104;
    
	/**
	 * 承销佣金率
	 */
    @Column(length=20)
    private BigDecimal STN105;
    
	/**
	 * 净资本
	 */
    @Column(length=20)
    private BigDecimal STN106;
    
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

	public BigDecimal getSBS101() {
		return SBS101;
	}

	public void setSBS101(BigDecimal sBS101) {
		SBS101 = sBS101;
	}

	public BigDecimal getSBS101_1() {
		return SBS101_1;
	}

	public void setSBS101_1(BigDecimal sBS101_1) {
		SBS101_1 = sBS101_1;
	}

	public BigDecimal getSBS102() {
		return SBS102;
	}

	public void setSBS102(BigDecimal sBS102) {
		SBS102 = sBS102;
	}

	public BigDecimal getSBS102_1() {
		return SBS102_1;
	}

	public void setSBS102_1(BigDecimal sBS102_1) {
		SBS102_1 = sBS102_1;
	}

	public BigDecimal getSBS103() {
		return SBS103;
	}

	public void setSBS103(BigDecimal sBS103) {
		SBS103 = sBS103;
	}

	public BigDecimal getSBS104() {
		return SBS104;
	}

	public void setSBS104(BigDecimal sBS104) {
		SBS104 = sBS104;
	}

	public BigDecimal getSBS105() {
		return SBS105;
	}

	public void setSBS105(BigDecimal sBS105) {
		SBS105 = sBS105;
	}

	public BigDecimal getSBS106() {
		return SBS106;
	}

	public void setSBS106(BigDecimal sBS106) {
		SBS106 = sBS106;
	}

	public BigDecimal getSBS107() {
		return SBS107;
	}

	public void setSBS107(BigDecimal sBS107) {
		SBS107 = sBS107;
	}

	public BigDecimal getSBS108() {
		return SBS108;
	}

	public void setSBS108(BigDecimal sBS108) {
		SBS108 = sBS108;
	}

	public BigDecimal getSBS109() {
		return SBS109;
	}

	public void setSBS109(BigDecimal sBS109) {
		SBS109 = sBS109;
	}

	public BigDecimal getSBS110() {
		return SBS110;
	}

	public void setSBS110(BigDecimal sBS110) {
		SBS110 = sBS110;
	}

	public BigDecimal getSBS111() {
		return SBS111;
	}

	public void setSBS111(BigDecimal sBS111) {
		SBS111 = sBS111;
	}

	public BigDecimal getSBS113() {
		return SBS113;
	}

	public void setSBS113(BigDecimal sBS113) {
		SBS113 = sBS113;
	}

	public BigDecimal getSBS114() {
		return SBS114;
	}

	public void setSBS114(BigDecimal sBS114) {
		SBS114 = sBS114;
	}

	public BigDecimal getSBS114_1() {
		return SBS114_1;
	}

	public void setSBS114_1(BigDecimal sBS114_1) {
		SBS114_1 = sBS114_1;
	}

	public BigDecimal getSBS115() {
		return SBS115;
	}

	public void setSBS115(BigDecimal sBS115) {
		SBS115 = sBS115;
	}

	public BigDecimal getSBS112() {
		return SBS112;
	}

	public void setSBS112(BigDecimal sBS112) {
		SBS112 = sBS112;
	}

	public BigDecimal getSBS116() {
		return SBS116;
	}

	public void setSBS116(BigDecimal sBS116) {
		SBS116 = sBS116;
	}

	public BigDecimal getSBS001() {
		return SBS001;
	}

	public void setSBS001(BigDecimal sBS001) {
		SBS001 = sBS001;
	}

	public BigDecimal getSBS201() {
		return SBS201;
	}

	public void setSBS201(BigDecimal sBS201) {
		SBS201 = sBS201;
	}

	public BigDecimal getSBS201_1() {
		return SBS201_1;
	}

	public void setSBS201_1(BigDecimal sBS201_1) {
		SBS201_1 = sBS201_1;
	}

	public BigDecimal getSBS202() {
		return SBS202;
	}

	public void setSBS202(BigDecimal sBS202) {
		SBS202 = sBS202;
	}

	public BigDecimal getSBS203() {
		return SBS203;
	}

	public void setSBS203(BigDecimal sBS203) {
		SBS203 = sBS203;
	}

	public BigDecimal getSBS204() {
		return SBS204;
	}

	public void setSBS204(BigDecimal sBS204) {
		SBS204 = sBS204;
	}

	public BigDecimal getSBS205() {
		return SBS205;
	}

	public void setSBS205(BigDecimal sBS205) {
		SBS205 = sBS205;
	}

	public BigDecimal getSBS206() {
		return SBS206;
	}

	public void setSBS206(BigDecimal sBS206) {
		SBS206 = sBS206;
	}

	public BigDecimal getSBS207() {
		return SBS207;
	}

	public void setSBS207(BigDecimal sBS207) {
		SBS207 = sBS207;
	}

	public BigDecimal getSBS208() {
		return SBS208;
	}

	public void setSBS208(BigDecimal sBS208) {
		SBS208 = sBS208;
	}

	public BigDecimal getSBS209() {
		return SBS209;
	}

	public void setSBS209(BigDecimal sBS209) {
		SBS209 = sBS209;
	}

	public BigDecimal getSBS210() {
		return SBS210;
	}

	public void setSBS210(BigDecimal sBS210) {
		SBS210 = sBS210;
	}

	public BigDecimal getSBS212() {
		return SBS212;
	}

	public void setSBS212(BigDecimal sBS212) {
		SBS212 = sBS212;
	}

	public BigDecimal getSBS213() {
		return SBS213;
	}

	public void setSBS213(BigDecimal sBS213) {
		SBS213 = sBS213;
	}

	public BigDecimal getSBS214() {
		return SBS214;
	}

	public void setSBS214(BigDecimal sBS214) {
		SBS214 = sBS214;
	}

	public BigDecimal getSBS216() {
		return SBS216;
	}

	public void setSBS216(BigDecimal sBS216) {
		SBS216 = sBS216;
	}

	public BigDecimal getSBS215() {
		return SBS215;
	}

	public void setSBS215(BigDecimal sBS215) {
		SBS215 = sBS215;
	}

	public BigDecimal getSBS002() {
		return SBS002;
	}

	public void setSBS002(BigDecimal sBS002) {
		SBS002 = sBS002;
	}

	public BigDecimal getSBS301() {
		return SBS301;
	}

	public void setSBS301(BigDecimal sBS301) {
		SBS301 = sBS301;
	}

	public BigDecimal getSBS302() {
		return SBS302;
	}

	public void setSBS302(BigDecimal sBS302) {
		SBS302 = sBS302;
	}

	public BigDecimal getSBS302_2() {
		return SBS302_2;
	}

	public void setSBS302_2(BigDecimal sBS302_2) {
		SBS302_2 = sBS302_2;
	}

	public BigDecimal getSBS303() {
		return SBS303;
	}

	public void setSBS303(BigDecimal sBS303) {
		SBS303 = sBS303;
	}

	public BigDecimal getSBS305() {
		return SBS305;
	}

	public void setSBS305(BigDecimal sBS305) {
		SBS305 = sBS305;
	}

	public BigDecimal getSBS304() {
		return SBS304;
	}

	public void setSBS304(BigDecimal sBS304) {
		SBS304 = sBS304;
	}

	public BigDecimal getSBS309() {
		return SBS309;
	}

	public void setSBS309(BigDecimal sBS309) {
		SBS309 = sBS309;
	}

	public BigDecimal getSBS306() {
		return SBS306;
	}

	public void setSBS306(BigDecimal sBS306) {
		SBS306 = sBS306;
	}

	public BigDecimal getSBS310() {
		return SBS310;
	}

	public void setSBS310(BigDecimal sBS310) {
		SBS310 = sBS310;
	}

	public BigDecimal getSBS307() {
		return SBS307;
	}

	public void setSBS307(BigDecimal sBS307) {
		SBS307 = sBS307;
	}

	public BigDecimal getSBS003() {
		return SBS003;
	}

	public void setSBS003(BigDecimal sBS003) {
		SBS003 = sBS003;
	}

	public BigDecimal getSBS004() {
		return SBS004;
	}

	public void setSBS004(BigDecimal sBS004) {
		SBS004 = sBS004;
	}

	public BigDecimal getSPL100() {
		return SPL100;
	}

	public void setSPL100(BigDecimal sPL100) {
		SPL100 = sPL100;
	}

	public BigDecimal getSPL101() {
		return SPL101;
	}

	public void setSPL101(BigDecimal sPL101) {
		SPL101 = sPL101;
	}

	public BigDecimal getSPL101_1() {
		return SPL101_1;
	}

	public void setSPL101_1(BigDecimal sPL101_1) {
		SPL101_1 = sPL101_1;
	}

	public BigDecimal getSPL101_2() {
		return SPL101_2;
	}

	public void setSPL101_2(BigDecimal sPL101_2) {
		SPL101_2 = sPL101_2;
	}

	public BigDecimal getSPL101_3() {
		return SPL101_3;
	}

	public void setSPL101_3(BigDecimal sPL101_3) {
		SPL101_3 = sPL101_3;
	}

	public BigDecimal getSPL101_4() {
		return SPL101_4;
	}

	public void setSPL101_4(BigDecimal sPL101_4) {
		SPL101_4 = sPL101_4;
	}

	public BigDecimal getSPL101_5() {
		return SPL101_5;
	}

	public void setSPL101_5(BigDecimal sPL101_5) {
		SPL101_5 = sPL101_5;
	}

	public BigDecimal getSPL103() {
		return SPL103;
	}

	public void setSPL103(BigDecimal sPL103) {
		SPL103 = sPL103;
	}

	public BigDecimal getSPL104() {
		return SPL104;
	}

	public void setSPL104(BigDecimal sPL104) {
		SPL104 = sPL104;
	}

	public BigDecimal getSPL104_1() {
		return SPL104_1;
	}

	public void setSPL104_1(BigDecimal sPL104_1) {
		SPL104_1 = sPL104_1;
	}

	public BigDecimal getSPL105() {
		return SPL105;
	}

	public void setSPL105(BigDecimal sPL105) {
		SPL105 = sPL105;
	}

	public BigDecimal getSPL106() {
		return SPL106;
	}

	public void setSPL106(BigDecimal sPL106) {
		SPL106 = sPL106;
	}

	public BigDecimal getSPL107() {
		return SPL107;
	}

	public void setSPL107(BigDecimal sPL107) {
		SPL107 = sPL107;
	}

	public BigDecimal getSPL200() {
		return SPL200;
	}

	public void setSPL200(BigDecimal sPL200) {
		SPL200 = sPL200;
	}

	public BigDecimal getSPL201() {
		return SPL201;
	}

	public void setSPL201(BigDecimal sPL201) {
		SPL201 = sPL201;
	}

	public BigDecimal getSPL202() {
		return SPL202;
	}

	public void setSPL202(BigDecimal sPL202) {
		SPL202 = sPL202;
	}

	public BigDecimal getSPL203() {
		return SPL203;
	}

	public void setSPL203(BigDecimal sPL203) {
		SPL203 = sPL203;
	}

	public BigDecimal getSPL204() {
		return SPL204;
	}

	public void setSPL204(BigDecimal sPL204) {
		SPL204 = sPL204;
	}

	public BigDecimal getSPL300() {
		return SPL300;
	}

	public void setSPL300(BigDecimal sPL300) {
		SPL300 = sPL300;
	}

	public BigDecimal getSPL301() {
		return SPL301;
	}

	public void setSPL301(BigDecimal sPL301) {
		SPL301 = sPL301;
	}

	public BigDecimal getSPL302() {
		return SPL302;
	}

	public void setSPL302(BigDecimal sPL302) {
		SPL302 = sPL302;
	}

	public BigDecimal getSPL400() {
		return SPL400;
	}

	public void setSPL400(BigDecimal sPL400) {
		SPL400 = sPL400;
	}

	public BigDecimal getSPL401() {
		return SPL401;
	}

	public void setSPL401(BigDecimal sPL401) {
		SPL401 = sPL401;
	}

	public BigDecimal getSPL500() {
		return SPL500;
	}

	public void setSPL500(BigDecimal sPL500) {
		SPL500 = sPL500;
	}

	public BigDecimal getSPL501() {
		return SPL501;
	}

	public void setSPL501(BigDecimal sPL501) {
		SPL501 = sPL501;
	}

	public BigDecimal getSPL502() {
		return SPL502;
	}

	public void setSPL502(BigDecimal sPL502) {
		SPL502 = sPL502;
	}

	public BigDecimal getSPL503() {
		return SPL503;
	}

	public void setSPL503(BigDecimal sPL503) {
		SPL503 = sPL503;
	}

	public BigDecimal getSPL504() {
		return SPL504;
	}

	public void setSPL504(BigDecimal sPL504) {
		SPL504 = sPL504;
	}

	public BigDecimal getSPL505() {
		return SPL505;
	}

	public void setSPL505(BigDecimal sPL505) {
		SPL505 = sPL505;
	}

	public BigDecimal getSPL506() {
		return SPL506;
	}

	public void setSPL506(BigDecimal sPL506) {
		SPL506 = sPL506;
	}

	public BigDecimal getSPL507() {
		return SPL507;
	}

	public void setSPL507(BigDecimal sPL507) {
		SPL507 = sPL507;
	}

	public BigDecimal getSPL508() {
		return SPL508;
	}

	public void setSPL508(BigDecimal sPL508) {
		SPL508 = sPL508;
	}

	public BigDecimal getSCF101() {
		return SCF101;
	}

	public void setSCF101(BigDecimal sCF101) {
		SCF101 = sCF101;
	}

	public BigDecimal getSCF106() {
		return SCF106;
	}

	public void setSCF106(BigDecimal sCF106) {
		SCF106 = sCF106;
	}

	public BigDecimal getSCF102() {
		return SCF102;
	}

	public void setSCF102(BigDecimal sCF102) {
		SCF102 = sCF102;
	}

	public BigDecimal getSCF103() {
		return SCF103;
	}

	public void setSCF103(BigDecimal sCF103) {
		SCF103 = sCF103;
	}

	public BigDecimal getSCF104() {
		return SCF104;
	}

	public void setSCF104(BigDecimal sCF104) {
		SCF104 = sCF104;
	}

	public BigDecimal getSCF105() {
		return SCF105;
	}

	public void setSCF105(BigDecimal sCF105) {
		SCF105 = sCF105;
	}

	public BigDecimal getSCF100() {
		return SCF100;
	}

	public void setSCF100(BigDecimal sCF100) {
		SCF100 = sCF100;
	}

	public BigDecimal getSCF202() {
		return SCF202;
	}

	public void setSCF202(BigDecimal sCF202) {
		SCF202 = sCF202;
	}

	public BigDecimal getSCF203() {
		return SCF203;
	}

	public void setSCF203(BigDecimal sCF203) {
		SCF203 = sCF203;
	}

	public BigDecimal getSCF204() {
		return SCF204;
	}

	public void setSCF204(BigDecimal sCF204) {
		SCF204 = sCF204;
	}

	public BigDecimal getSCF200() {
		return SCF200;
	}

	public void setSCF200(BigDecimal sCF200) {
		SCF200 = sCF200;
	}

	public BigDecimal getSCF001() {
		return SCF001;
	}

	public void setSCF001(BigDecimal sCF001) {
		SCF001 = sCF001;
	}

	public BigDecimal getSCF301() {
		return SCF301;
	}

	public void setSCF301(BigDecimal sCF301) {
		SCF301 = sCF301;
	}

	public BigDecimal getSCF304() {
		return SCF304;
	}

	public void setSCF304(BigDecimal sCF304) {
		SCF304 = sCF304;
	}

	public BigDecimal getSCF303() {
		return SCF303;
	}

	public void setSCF303(BigDecimal sCF303) {
		SCF303 = sCF303;
	}

	public BigDecimal getSCF300() {
		return SCF300;
	}

	public void setSCF300(BigDecimal sCF300) {
		SCF300 = sCF300;
	}

	public BigDecimal getSCF401() {
		return SCF401;
	}

	public void setSCF401(BigDecimal sCF401) {
		SCF401 = sCF401;
	}

	public BigDecimal getSCF402() {
		return SCF402;
	}

	public void setSCF402(BigDecimal sCF402) {
		SCF402 = sCF402;
	}

	public BigDecimal getSCF403() {
		return SCF403;
	}

	public void setSCF403(BigDecimal sCF403) {
		SCF403 = sCF403;
	}

	public BigDecimal getSCF400() {
		return SCF400;
	}

	public void setSCF400(BigDecimal sCF400) {
		SCF400 = sCF400;
	}

	public BigDecimal getSCF002() {
		return SCF002;
	}

	public void setSCF002(BigDecimal sCF002) {
		SCF002 = sCF002;
	}

	public BigDecimal getSCF501() {
		return SCF501;
	}

	public void setSCF501(BigDecimal sCF501) {
		SCF501 = sCF501;
	}

	public BigDecimal getSCF502() {
		return SCF502;
	}

	public void setSCF502(BigDecimal sCF502) {
		SCF502 = sCF502;
	}

	public BigDecimal getSCF503() {
		return SCF503;
	}

	public void setSCF503(BigDecimal sCF503) {
		SCF503 = sCF503;
	}

	public BigDecimal getSCF500() {
		return SCF500;
	}

	public void setSCF500(BigDecimal sCF500) {
		SCF500 = sCF500;
	}

	public BigDecimal getSCF601() {
		return SCF601;
	}

	public void setSCF601(BigDecimal sCF601) {
		SCF601 = sCF601;
	}

	public BigDecimal getSCF602() {
		return SCF602;
	}

	public void setSCF602(BigDecimal sCF602) {
		SCF602 = sCF602;
	}

	public BigDecimal getSCF603() {
		return SCF603;
	}

	public void setSCF603(BigDecimal sCF603) {
		SCF603 = sCF603;
	}

	public BigDecimal getSCF600() {
		return SCF600;
	}

	public void setSCF600(BigDecimal sCF600) {
		SCF600 = sCF600;
	}

	public BigDecimal getSCF003() {
		return SCF003;
	}

	public void setSCF003(BigDecimal sCF003) {
		SCF003 = sCF003;
	}

	public BigDecimal getSCF004() {
		return SCF004;
	}

	public void setSCF004(BigDecimal sCF004) {
		SCF004 = sCF004;
	}

	public BigDecimal getSCF005() {
		return SCF005;
	}

	public void setSCF005(BigDecimal sCF005) {
		SCF005 = sCF005;
	}

	public BigDecimal getSCF006() {
		return SCF006;
	}

	public void setSCF006(BigDecimal sCF006) {
		SCF006 = sCF006;
	}

	public BigDecimal getSCF007() {
		return SCF007;
	}

	public void setSCF007(BigDecimal sCF007) {
		SCF007 = sCF007;
	}

	public BigDecimal getSCF901() {
		return SCF901;
	}

	public void setSCF901(BigDecimal sCF901) {
		SCF901 = sCF901;
	}

	public BigDecimal getSCF902() {
		return SCF902;
	}

	public void setSCF902(BigDecimal sCF902) {
		SCF902 = sCF902;
	}

	public BigDecimal getSCF903() {
		return SCF903;
	}

	public void setSCF903(BigDecimal sCF903) {
		SCF903 = sCF903;
	}

	public BigDecimal getSCF904() {
		return SCF904;
	}

	public void setSCF904(BigDecimal sCF904) {
		SCF904 = sCF904;
	}

	public BigDecimal getSCF905() {
		return SCF905;
	}

	public void setSCF905(BigDecimal sCF905) {
		SCF905 = sCF905;
	}

	public BigDecimal getSCF906() {
		return SCF906;
	}

	public void setSCF906(BigDecimal sCF906) {
		SCF906 = sCF906;
	}

	public BigDecimal getSCF907() {
		return SCF907;
	}

	public void setSCF907(BigDecimal sCF907) {
		SCF907 = sCF907;
	}

	public BigDecimal getSCF908() {
		return SCF908;
	}

	public void setSCF908(BigDecimal sCF908) {
		SCF908 = sCF908;
	}

	public BigDecimal getSCF909() {
		return SCF909;
	}

	public void setSCF909(BigDecimal sCF909) {
		SCF909 = sCF909;
	}

	public BigDecimal getSCF910() {
		return SCF910;
	}

	public void setSCF910(BigDecimal sCF910) {
		SCF910 = sCF910;
	}

	public BigDecimal getSCF911() {
		return SCF911;
	}

	public void setSCF911(BigDecimal sCF911) {
		SCF911 = sCF911;
	}

	public BigDecimal getSCF912() {
		return SCF912;
	}

	public void setSCF912(BigDecimal sCF912) {
		SCF912 = sCF912;
	}

	public BigDecimal getSCF913() {
		return SCF913;
	}

	public void setSCF913(BigDecimal sCF913) {
		SCF913 = sCF913;
	}

	public BigDecimal getSCF914() {
		return SCF914;
	}

	public void setSCF914(BigDecimal sCF914) {
		SCF914 = sCF914;
	}

	public BigDecimal getSCF915() {
		return SCF915;
	}

	public void setSCF915(BigDecimal sCF915) {
		SCF915 = sCF915;
	}

	public BigDecimal getSCF916() {
		return SCF916;
	}

	public void setSCF916(BigDecimal sCF916) {
		SCF916 = sCF916;
	}

	public BigDecimal getSCF917() {
		return SCF917;
	}

	public void setSCF917(BigDecimal sCF917) {
		SCF917 = sCF917;
	}

	public BigDecimal getSCF918() {
		return SCF918;
	}

	public void setSCF918(BigDecimal sCF918) {
		SCF918 = sCF918;
	}

	public BigDecimal getSCF919() {
		return SCF919;
	}

	public void setSCF919(BigDecimal sCF919) {
		SCF919 = sCF919;
	}

	public BigDecimal getSCF920() {
		return SCF920;
	}

	public void setSCF920(BigDecimal sCF920) {
		SCF920 = sCF920;
	}

	public BigDecimal getSCF921() {
		return SCF921;
	}

	public void setSCF921(BigDecimal sCF921) {
		SCF921 = sCF921;
	}

	public BigDecimal getSCF922() {
		return SCF922;
	}

	public void setSCF922(BigDecimal sCF922) {
		SCF922 = sCF922;
	}

	public BigDecimal getSCF923() {
		return SCF923;
	}

	public void setSCF923(BigDecimal sCF923) {
		SCF923 = sCF923;
	}

	public BigDecimal getSCF924() {
		return SCF924;
	}

	public void setSCF924(BigDecimal sCF924) {
		SCF924 = sCF924;
	}

	public BigDecimal getSCF925() {
		return SCF925;
	}

	public void setSCF925(BigDecimal sCF925) {
		SCF925 = sCF925;
	}

	public BigDecimal getSCF926() {
		return SCF926;
	}

	public void setSCF926(BigDecimal sCF926) {
		SCF926 = sCF926;
	}

	public BigDecimal getSCF927() {
		return SCF927;
	}

	public void setSCF927(BigDecimal sCF927) {
		SCF927 = sCF927;
	}

	public BigDecimal getSTN101() {
		return STN101;
	}

	public void setSTN101(BigDecimal sTN101) {
		STN101 = sTN101;
	}

	public BigDecimal getSTN102() {
		return STN102;
	}

	public void setSTN102(BigDecimal sTN102) {
		STN102 = sTN102;
	}

	public BigDecimal getSTN103() {
		return STN103;
	}

	public void setSTN103(BigDecimal sTN103) {
		STN103 = sTN103;
	}

	public BigDecimal getSTN104() {
		return STN104;
	}

	public void setSTN104(BigDecimal sTN104) {
		STN104 = sTN104;
	}

	public BigDecimal getSTN105() {
		return STN105;
	}

	public void setSTN105(BigDecimal sTN105) {
		STN105 = sTN105;
	}

	public BigDecimal getSTN106() {
		return STN106;
	}

	public void setSTN106(BigDecimal sTN106) {
		STN106 = sTN106;
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
	       selectSql += ",SBS101";
	       selectSql += ",SBS101_1";
	       selectSql += ",SBS102";
	       selectSql += ",SBS102_1";
	       selectSql += ",SBS103";
	       selectSql += ",SBS104";
	       selectSql += ",SBS105";
	       selectSql += ",SBS106";
	       selectSql += ",SBS107";
	       selectSql += ",SBS108";
	       selectSql += ",SBS109";
	       selectSql += ",SBS110";
	       selectSql += ",SBS111";
	       selectSql += ",SBS113";
	       selectSql += ",SBS114";
	       selectSql += ",SBS114_1";
	       selectSql += ",SBS115";
	       selectSql += ",SBS112";
	       selectSql += ",SBS116";
	       selectSql += ",SBS001";
	       selectSql += ",SBS201";
	       selectSql += ",SBS201_1";
	       selectSql += ",SBS202";
	       selectSql += ",SBS203";
	       selectSql += ",SBS204";
	       selectSql += ",SBS205";
	       selectSql += ",SBS206";
	       selectSql += ",SBS207";
	       selectSql += ",SBS208";
	       selectSql += ",SBS209";
	       selectSql += ",SBS210";
	       selectSql += ",SBS212";
	       selectSql += ",SBS213";
	       selectSql += ",SBS214";
	       selectSql += ",SBS216";
	       selectSql += ",SBS215";
	       selectSql += ",SBS002";
	       selectSql += ",SBS301";
	       selectSql += ",SBS302";
	       selectSql += ",SBS302_2";
	       selectSql += ",SBS303";
	       selectSql += ",SBS305";
	       selectSql += ",SBS304";
	       selectSql += ",SBS309";
	       selectSql += ",SBS306";
	       selectSql += ",SBS310";
	       selectSql += ",SBS307";
	       selectSql += ",SBS003";
	       selectSql += ",SBS004";
	       selectSql += ",SPL100";
	       selectSql += ",SPL101";
	       selectSql += ",SPL101_1";
	       selectSql += ",SPL101_2";
	       selectSql += ",SPL101_3";
	       selectSql += ",SPL101_4";
	       selectSql += ",SPL101_5";
	       selectSql += ",SPL103";
	       selectSql += ",SPL104";
	       selectSql += ",SPL104_1";
	       selectSql += ",SPL105";
	       selectSql += ",SPL106";
	       selectSql += ",SPL107";
	       selectSql += ",SPL200";
	       selectSql += ",SPL201";
	       selectSql += ",SPL202";
	       selectSql += ",SPL203";
	       selectSql += ",SPL204";
	       selectSql += ",SPL300";
	       selectSql += ",SPL301";
	       selectSql += ",SPL302";
	       selectSql += ",SPL400";
	       selectSql += ",SPL401";
	       selectSql += ",SPL500";
	       selectSql += ",SPL501";
	       selectSql += ",SPL502";
	       selectSql += ",SPL503";
	       selectSql += ",SPL504";
	       selectSql += ",SPL505";
	       selectSql += ",SPL506";
	       selectSql += ",SPL507";
	       selectSql += ",SPL508";
	       selectSql += ",SCF101";
	       selectSql += ",SCF106";
	       selectSql += ",SCF102";
	       selectSql += ",SCF103";
	       selectSql += ",SCF104";
	       selectSql += ",SCF105";
	       selectSql += ",SCF100";
	       selectSql += ",SCF202";
	       selectSql += ",SCF203";
	       selectSql += ",SCF204";
	       selectSql += ",SCF200";
	       selectSql += ",SCF001";
	       selectSql += ",SCF301";
	       selectSql += ",SCF304";
	       selectSql += ",SCF303";
	       selectSql += ",SCF300";
	       selectSql += ",SCF401";
	       selectSql += ",SCF402";
	       selectSql += ",SCF403";
	       selectSql += ",SCF400";
	       selectSql += ",SCF002";
	       selectSql += ",SCF501";
	       selectSql += ",SCF502";
	       selectSql += ",SCF503";
	       selectSql += ",SCF500";
	       selectSql += ",SCF601";
	       selectSql += ",SCF602";
	       selectSql += ",SCF603";
	       selectSql += ",SCF600";
	       selectSql += ",SCF003";
	       selectSql += ",SCF004";
	       selectSql += ",SCF005";
	       selectSql += ",SCF006";
	       selectSql += ",SCF007";
	       selectSql += ",SCF901";
	       selectSql += ",SCF902";
	       selectSql += ",SCF903";
	       selectSql += ",SCF904";
	       selectSql += ",SCF905";
	       selectSql += ",SCF906";
	       selectSql += ",SCF907";
	       selectSql += ",SCF908";
	       selectSql += ",SCF909";
	       selectSql += ",SCF910";
	       selectSql += ",SCF911";
	       selectSql += ",SCF912";
	       selectSql += ",SCF913";
	       selectSql += ",SCF914";
	       selectSql += ",SCF915";
	       selectSql += ",SCF916";
	       selectSql += ",SCF917";
	       selectSql += ",SCF918";
	       selectSql += ",SCF919";
	       selectSql += ",SCF920";
	       selectSql += ",SCF921";
	       selectSql += ",SCF922";
	       selectSql += ",SCF923";
	       selectSql += ",SCF924";
	       selectSql += ",SCF925";
	       selectSql += ",SCF926";
	       selectSql += ",SCF927";
	       selectSql += ",STN101";
	       selectSql += ",STN102";
	       selectSql += ",STN103";
	       selectSql += ",STN104";
	       selectSql += ",STN105";
	       selectSql += ",STN106";
	       selectSql += ",last_update_timestamp";
	       selectSql += ",ROW_KEY";
		   return selectSql.substring(1);
	    }

	    public String insertBySelectSql(){
		   String selColumn = createSelectColumnSql();           
	       String sql = "insert into secu_fina_sheet_p ("+selColumn+") select "+selColumn+" from secu_fina_sheet";
	       return sql;
	    }
}
