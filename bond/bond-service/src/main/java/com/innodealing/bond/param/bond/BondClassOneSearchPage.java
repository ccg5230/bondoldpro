package com.innodealing.bond.param.bond;

import java.util.List;

import com.innodealing.model.mongo.dm.BondBasicInfoClassOneDoc;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel
public class BondClassOneSearchPage {
	
	@ApiModelProperty(value = "新债列表")
	List<BondBasicInfoClassOneDoc> bondList;
	@ApiModelProperty(value = "新债总数")
	Long totalCount;
	@ApiModelProperty(value = "总页数")
    Long totalPage;
	@ApiModelProperty(value = "当前页")
	Long currentPage;
    public List<BondBasicInfoClassOneDoc> getBondList() {
        return bondList;
    }
    public void setBondList(List<BondBasicInfoClassOneDoc> bondList) {
        this.bondList = bondList;
    }
    public Long getTotalCount() {
        return totalCount;
    }
    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }
    public Long getTotalPage() {
        return totalPage;
    }
    public void setTotalPage(Long totalPage) {
        this.totalPage = totalPage;
    }
    public Long getCurrentPage() {
        return currentPage;
    }
    public void setCurrentPage(Long currentPage) {
        this.currentPage = currentPage;
    }
	
	

}
