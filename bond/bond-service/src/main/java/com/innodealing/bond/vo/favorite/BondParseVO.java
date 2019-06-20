package com.innodealing.bond.vo.favorite;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.innodealing.bond.vo.msg.BasicBondVo;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BondParseVO {

    @ApiModelProperty(value = "解析成功的债券列表")
    private List<BasicBondVo> succeedList;

    @ApiModelProperty(value = "解析失败的债券列表")
    private List<String> failedList;

    public List<BasicBondVo> getSucceedList() {
        return succeedList;
    }

    public void setSucceedList(List<BasicBondVo> succeedList) {
        this.succeedList = succeedList;
    }

    public List<String> getFailedList() {
        return failedList;
    }

    public void setFailedList(List<String> failedList) {
        this.failedList = failedList;
    }
}
