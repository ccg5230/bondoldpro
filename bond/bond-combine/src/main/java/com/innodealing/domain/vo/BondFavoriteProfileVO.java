package com.innodealing.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.innodealing.json.JsonDateSerializer;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import java.io.Serializable;
import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BondFavoriteProfileVO implements Serializable {
    @ApiModelProperty(value = "债券id")
    @Id
    @Indexed
    private Long bondUniCode;

    @ApiModelProperty(value = "关注Id")
    private Integer favoriteId;

    @ApiModelProperty(value = "债券简称")
    @Indexed
    private String name;

    @ApiModelProperty(value = "债券代码")
    @Indexed
    private String code;

    @ApiModelProperty(value = "期限")
    private String tenor;

    @ApiModelProperty(value = "变动消息数量")
    private Long eventMsgCount;

    @ApiModelProperty(value = "投组未读消息总数")
    private Long groupNewMsgCount;

    @ApiModelProperty(value = "是否过期:0-未过期;1-过期")
    private Integer expiredState;

    // 显示项目
    // @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = JsonDateSerializer.class)
    @ApiModelProperty(value = "更新时间")
    @Indexed
    private Date updateTime;

    // 内部数据
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    public Long getBondUniCode() {
        return bondUniCode;
    }

    public void setBondUniCode(Long bondUniCode) {
        this.bondUniCode = bondUniCode;
    }

    public Integer getFavoriteId() {
        return favoriteId;
    }

    public void setFavoriteId(Integer favoriteId) {
        this.favoriteId = favoriteId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTenor() {
        return tenor;
    }

    public void setTenor(String tenor) {
        this.tenor = tenor;
    }

    public Long getEventMsgCount() {
        return eventMsgCount;
    }

    public void setEventMsgCount(Long eventMsgCount) {
        this.eventMsgCount = eventMsgCount;
    }

    public Long getGroupNewMsgCount() {
        return groupNewMsgCount;
    }

    public void setGroupNewMsgCount(Long groupNewMsgCount) {
        this.groupNewMsgCount = groupNewMsgCount;
    }

    public Integer getExpiredState() {
        return expiredState;
    }

    public void setExpiredState(Integer expiredState) {
        this.expiredState = expiredState;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
