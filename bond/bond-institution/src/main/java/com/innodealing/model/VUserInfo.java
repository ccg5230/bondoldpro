package com.innodealing.model;

import com.innodealing.service.BondInduService;
import com.innodealing.util.Column;
import com.innodealing.util.SpringUtil;

public class VUserInfo {
	@Column(name = "user_id")
	private int userId;

	@Column(name = "role_id")
	private Integer roleId;

	@Column(name = "role_id")
	public Integer orgId;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	/**
	 * 行业类型 0 [GICS 行业] 1[申万] 2[自定义]
	 * 
	 * @return
	 */
	public int getInduType() {
		BondInduService induService = SpringUtil.getBean(BondInduService.class);
		if (induService.isInduInstitution(Long.valueOf(userId))) {
			return 2;
		} else {
			if (induService.isGicsInduClass(Long.valueOf(userId))) {
				return 0;
			} else {
				return 1;
			}
		}
	}

}
