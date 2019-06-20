package com.innodealing.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpgradeConfig {
    @ApiModelProperty(value = "强制升级")
    private boolean mandatory = false;
    @ApiModelProperty(value = "可升级")
    private boolean available = false;
    @ApiModelProperty(value = "android升级包下载地址或者ios的AppStore跳转地址")
    private String urlAPK;
    @ApiModelProperty(value = "新版本的版本号")
    private String newVersion;
    @ApiModelProperty(value = "ios升级提示信息")
    private String text = "客户端版本过低，请前往AppStore更新到最新版本。";

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isMandatory() {
        return mandatory;
    }

    public void setMandatory(boolean mandatory) {
        this.mandatory = mandatory;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getUrlAPK() {
        return urlAPK;
    }

    public void setUrlAPK(String urlAPK) {
        this.urlAPK = urlAPK;
    }

    public String getNewVersion() {
        return newVersion;
    }

    public void setNewVersion(String newVersion) {
        this.newVersion = newVersion;
    }
}
