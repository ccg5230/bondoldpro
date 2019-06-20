package com.innodealing.model.dm.bond;

import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

public class BondTodayDMAggr {
    @ApiModelProperty(value="总数")
    private int count;

    @ApiModelProperty(value="根节点列表")
    private List<BondTodayDMRow> rootRowList = new ArrayList<>();

    public List<BondTodayDMRow> getRootRowList() {
        return rootRowList;
    }

    public void setRootRowList(List<BondTodayDMRow> rootRowList) {
        this.rootRowList = rootRowList;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
