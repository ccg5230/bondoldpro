package com.innodealing.model.dm.bond;

import java.io.Serializable;
import java.math.BigDecimal;

public class BondIndicatorBank extends BondAnalysisBank implements Serializable {
    private BigDecimal bank_ratio2;// 净资产 规模和成长性——规模  1
    private BigDecimal bank_ratio43;//    不良贷款率   资产质量    2
    private BigDecimal BANK_RATIO34;//    资产利用率（%）    盈利能力    3
    private BigDecimal bank_ratio156;//   利润利息支出比（X）  盈利能力    4
    private BigDecimal bank_ratio157;//   调整后存贷比率（%）  资产负债匹配结构    5
    private BigDecimal bank_ratio158;//   流动性比率   资产流动性   6
    private BigDecimal bank_ratio47;//    拨贷比 资产质量    7
    private BigDecimal BANK_RATIO65;//    资本资产比例（%）   资本充足性   8
    private BigDecimal BANK_RATIO68;//    权益乘数(倍)（X）  资本充足性   9

}
