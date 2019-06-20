package com.innodealing.model.dm.bond;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.omg.CORBA.ServerRequest;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author xiaochao
 * @time 2017年4月10日
 * @description: 
 */
@Entity
@Table(name = "innodealing.t_mine_field_deleted")
public class MineFieldDeleted implements Serializable {
	private static final long serialVersionUID = 3685874213556990487L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@ApiModelProperty(value = "id")
	@Column(name = "id")
	private Long id;

	@ApiModelProperty(value = "今日动态主表")
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property="id", scope=ServerRequest.class)
	@OneToOne(cascade=CascadeType.REFRESH)
    @JoinColumn(name="mine_id")
    private DmmsMineField deletedDMF;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public DmmsMineField getDeletedDMF() {
		return deletedDMF;
	}

	public void setDeletedDMF(DmmsMineField deletedDMF) {
		this.deletedDMF = deletedDMF;
	}
}
