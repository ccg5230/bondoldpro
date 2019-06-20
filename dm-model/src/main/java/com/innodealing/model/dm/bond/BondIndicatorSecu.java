package com.innodealing.model.dm.bond;

import java.io.Serializable;
import java.math.BigDecimal;

public class BondIndicatorSecu extends BondAnalysisBase implements Serializable {
    private BigDecimal secu_ratio1;//     资产总额    规模指标    1
    private BigDecimal secu_ratio39;//    净资产收益率  盈利能力    2
    private BigDecimal secu_ratio67;//    营业利润率   盈利能力    3
    private BigDecimal SECU_RATIO69;//    流动性比率（X）    盈利能力    4
    private BigDecimal secu_ratio68;//    手续费及佣金收入比例  资本结构    5
    private BigDecimal secu_ratio51;//    有价证券占比  资本结构    6
    private BigDecimal secu_ratio71;//    资产负债率   资本结构    7
    private BigDecimal SECU_RATIO70;//    资本充足率（%）    资本充足性   8
    private BigDecimal secu_ratio72;//    净利润增长率  盈利能力    9

}
