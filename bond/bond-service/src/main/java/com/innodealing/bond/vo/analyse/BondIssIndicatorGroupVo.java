package com.innodealing.bond.vo.analyse;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;

public class BondIssIndicatorGroupVo implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "类别[bank:银行,indu:非金融，insu:保险，secu:券商]")
    private String groupName;
    
    @ApiModelProperty(value = "债券发行人指标")
    private List<BondIssIndicatorVo> bondIssIndicators;

    public String getGroupName() {
        switch (groupName) {
        case "bank":
            return "银行类发行人";
        case "indu":
            return "非金融机构发行人";
        case "insu":
            return "保险类发行人";
        case "secu":
            return "券商类发行人";

        default:
            break;
        }
        return groupName;
    }

    public List<BondIssIndicatorVo> getBondIssIndicators() {
        return bondIssIndicators;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setBondIssIndicators(List<BondIssIndicatorVo> bondIssIndicators) {
        this.bondIssIndicators = bondIssIndicators;
    }

    public BondIssIndicatorGroupVo() {
        super();
    }

    public BondIssIndicatorGroupVo(String groupName, List<BondIssIndicatorVo> bondIssIndicators) {
        super();
        this.groupName = groupName;
        this.bondIssIndicators = bondIssIndicators;
    }
    
    
}
