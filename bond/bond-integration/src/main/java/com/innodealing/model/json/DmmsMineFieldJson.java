package com.innodealing.model.json;

import java.io.Serializable;

public class DmmsMineFieldJson implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;

	// 网址
	private String skipUrl;

	// 发行人代码
	private Long comCode;

	// 发行人名称
	private String comName;

	// 标题
	private String title;

	// 内容
	private String content;

	// 2 删除
	private int status;

	// 采集时间
	private String createTime;

	// 更新时间
	private String updateTime;

	// 发布时间
	private String publishtime;

	// 栏目
	private String topic;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSkipUrl() {
		return skipUrl;
	}

	public void setSkipUrl(String skipUrl) {
		this.skipUrl = skipUrl;
	}

	public Long getComCode() {
		return comCode;
	}

	public void setComCode(Long comCode) {
		this.comCode = comCode;
	}

	public String getComName() {
		return comName;
	}

	public void setComName(String comName) {
		this.comName = comName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

    public String getPublishtime() {
        return publishtime;
    }

    public void setPublishtime(String publishtime) {
        this.publishtime = publishtime;
    }

	

}
