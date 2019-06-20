package com.innodealing.model.dm.bond;

import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

public class BondTodayExtAggr {
    @ApiModelProperty(value="总数")
    private int count;

    @ApiModelProperty(value="行节点列表")
    private List<BondTodayExtRow> rowList = new ArrayList<>();

    public List<BondTodayExtRow> getRowList() {
        return rowList;
    }

    public void setRowList(List<BondTodayExtRow> rowList) {
        this.rowList = rowList;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
