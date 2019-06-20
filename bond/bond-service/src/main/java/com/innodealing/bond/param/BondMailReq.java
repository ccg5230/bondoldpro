package com.innodealing.bond.param;

import io.swagger.annotations.ApiModelProperty;

public class BondMailReq {
    @ApiModelProperty(value = "发件人")
    private String from;

    @ApiModelProperty(value = "收件人(可用分号分隔收件人)")
    private String to;

    @ApiModelProperty(value = "邮件标题")
    private String subject;

    @ApiModelProperty(value = "邮件内容")
    private String content;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
