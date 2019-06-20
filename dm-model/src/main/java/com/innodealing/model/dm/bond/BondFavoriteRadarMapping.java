package com.innodealing.model.dm.bond;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.io.Serializable;

@Entity
@Table(name = "t_bond_favorite_radar_mapping")
@NamedQuery(name = "BondFavoriteRadarMapping.findAll", query = "SELECT t FROM BondFavoriteRadarMapping t")
@JsonInclude(Include.NON_NULL)
public class BondFavoriteRadarMapping implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	/**
	 * t_bond_favorite_group的ID
	 */
	@Column(name = "group_id", nullable = false, length = 19)
	private Long groupId = 0L;

	/**
	 * 关联t_bond_favorite_radar_schema
	 */
	@Column(name = "radar_id", nullable = false, length = 19)
	private Long radarId;

	/**
	 * 默认提醒阈值
	 */
	@Column(name = "threshold", length = 10)
	private Integer threshold;

	/**
	 * 创建时间
	 */
	@Column(name = "create_time", nullable = false, length = 19)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	
	@Transient
	private String radarName;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @param radarId
	 *            the radarId to set
	 */
	public void setRadarId(Long radarId) {
		this.radarId = radarId;
	}

	/**
	 * @return the radarId
	 */
	public Long getRadarId() {
		return this.radarId;
	}

	/**
	 * @param threshold
	 *            the threshold to set
	 */
	public void setThreshold(Integer threshold) {
		this.threshold = threshold;
	}

	/**
	 * @return the threshold
	 */
	public Integer getThreshold() {
		return this.threshold;
	}

	/**
	 * @param createTime
	 *            the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return this.createTime;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public String getRadarName() {
		return radarName;
	}

	public void setRadarName(String radarName) {
		this.radarName = radarName;
	}
}
