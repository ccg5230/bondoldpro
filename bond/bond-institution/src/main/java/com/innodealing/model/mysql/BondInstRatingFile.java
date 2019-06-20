package com.innodealing.model.mysql;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 评级记录相关文件
 * 
 * @author liuqi
 *
 */
@ApiModel
@JsonInclude(Include.NON_NULL)
@Table(name = "t_bond_inst_rating_file")
public class BondInstRatingFile extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	@ApiModelProperty(value = "1发起相关资料 2评级说明相关资料 3投资说明相关资料")
	@Column(name = "type")
	private Integer type;

	@ApiModelProperty(value = "文件地址")
	@Column(name = "file_path")
	private String filePath;

	@ApiModelProperty(value = "文件名称")
	@Column(name = "file_name")
	private String fileName;

	@ApiModelProperty(value = "key")
	@Column(name = "oss_key")
	private String ossKey;

	@ApiModelProperty(value = "信评记录外键")
	@Column(name = "inst_rating_id")
	private Long instRatingId;

	@ApiModelProperty(value = "状态 0 不使用  1使用")
	@Column(name = "status")
	private Integer status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getOssKey() {
		return ossKey;
	}

	public void setOssKey(String ossKey) {
		this.ossKey = ossKey;
	}

	public Long getInstRatingId() {
		return instRatingId;
	}

	public void setInstRatingId(Long instRatingId) {
		this.instRatingId = instRatingId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
