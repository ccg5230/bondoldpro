package com.innodealing.param;

import com.innodealing.domain.vo.BondFavoriteSimpleVO;
import com.innodealing.model.dm.bond.BondFavoriteFinaIndex;
import com.innodealing.model.dm.bond.BondFavoritePriceIndex;
import com.innodealing.model.dm.bond.BondFavoriteRadarMapping;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaochao
 * @time 2017年5月10日
 * @description:
 */
public class FavoriteGroupReq {

	@ApiModelProperty(value = "投组编号")
	private Long groupId;

	@ApiModelProperty(value = "投组名称")
	private String groupName;

	@ApiModelProperty(value = "债券变动是否提醒，1 提醒， 0 不提醒")
	private Integer notifiedEnable;

	@ApiModelProperty(value = "事件提醒消息的类型List")
	private List<Integer> notifiedEventtypes;

	@ApiModelProperty(value = "email推送提醒，1 提醒， 0 不提醒")
	private Integer emailEnable;

	@ApiModelProperty(value = "email地址")
	private String email;

	@ApiModelProperty(value = "投组的债券列表，bondUniCode")
	private List<BondFavoriteSimpleVO> bonds = new ArrayList<>();

	@ApiModelProperty(value = "价格指标雷达列表")
	private List<BondFavoritePriceIndex> priceRadars = new ArrayList<>();

	@ApiModelProperty(value = "财务指标雷达列表")
	private List<BondFavoriteFinaIndex> finaRadars = new ArrayList<>();

	@ApiModelProperty(value = "普通雷达列表")
	private List<BondFavoriteRadarMapping> commonRadars = new ArrayList<>();

	/**
	 * @return the groupName
	 */
	public String getGroupName() {
		return groupName;
	}

	/**
	 * @param groupName
	 *            the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Integer getNotifiedEnable() {
		return notifiedEnable;
	}

	public void setNotifiedEnable(Integer notifiedEnable) {
		this.notifiedEnable = notifiedEnable;
	}

	public List<Integer> getNotifiedEventtypes() {
		return notifiedEventtypes;
	}

	public void setNotifiedEventtypes(List<Integer> notifiedEventtypes) {
		this.notifiedEventtypes = notifiedEventtypes;
	}

	public Integer getEmailEnable() {
		return emailEnable;
	}

	public void setEmailEnable(Integer emailEnable) {
		this.emailEnable = emailEnable;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<BondFavoriteSimpleVO> getBonds() {
		return bonds;
	}

	public void setBonds(List<BondFavoriteSimpleVO> bonds) {
		this.bonds = bonds;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public List<BondFavoritePriceIndex> getPriceRadars() {
		return priceRadars;
	}

	public void setPriceRadars(List<BondFavoritePriceIndex> priceRadars) {
		this.priceRadars = priceRadars;
	}

	public List<BondFavoriteFinaIndex> getFinaRadars() {
		return finaRadars;
	}

	public void setFinaRadars(List<BondFavoriteFinaIndex> finaRadars) {
		this.finaRadars = finaRadars;
	}

	public List<BondFavoriteRadarMapping> getCommonRadars() {
		return commonRadars;
	}

	public void setCommonRadars(List<BondFavoriteRadarMapping> commonRadars) {
		this.commonRadars = commonRadars;
	}
}
