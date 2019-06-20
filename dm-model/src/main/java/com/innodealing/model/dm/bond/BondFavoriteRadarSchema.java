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

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

@Entity
@Table(name = "t_bond_favorite_radar_schema")
@NamedQuery(name = "BondFavoriteRadarSchema.findAll", query = "SELECT t FROM BondFavoriteRadarSchema t")
public class BondFavoriteRadarSchema implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	/**
	 * 父节点id
	 */
	@Column(name = "parent_id", length = 19)
	@JsonIgnore
	private Long parentId;

	/**
	 * 名称
	 */
	@Column(name = "name", nullable = false, length = 20)
	private String name;

	/**
	 * 类型名称
	 */
	@Column(name = "type_name", nullable = false, length = 45)
	private String typeName;

	/**
	 * 状态: 0-无效;1-有效
	 */
	@Column(name = "status", nullable = false, length = 4)
	private Integer status;

	/**
	 * 创建时间
	 */
	@Column(name = "create_time", length = 19)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonIgnore
	private Date createTime;

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
	 * @param parentId
	 *            the parentId to set
	 */
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	/**
	 * @return the parentId
	 */
	public Long getParentId() {
		return this.parentId;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return this.name;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
}
