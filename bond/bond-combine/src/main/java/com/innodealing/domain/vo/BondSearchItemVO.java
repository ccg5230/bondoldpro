package com.innodealing.domain.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.mongodb.core.index.Indexed;


/**
 * 搜索接口实体(债券，投组)
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BondSearchItemVO {

    @ApiModelProperty(value = "投组id")
    private Long groupId;

    @ApiModelProperty(value = "投组名称")
    @Indexed
    private String groupName;

    @ApiModelProperty(value = "债券id")
    private Long bondId;

    @ApiModelProperty(value = "债券名称")
    @Indexed
    private String bondName;

    @ApiModelProperty(value = "债券是否被关注")
    private boolean favorite;

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Long getBondId() {
        return bondId;
    }

    public void setBondId(Long bondId) {
        this.bondId = bondId;
    }

    public String getBondName() {
        return bondName;
    }

    public void setBondName(String bondName) {
        this.bondName = bondName;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}
