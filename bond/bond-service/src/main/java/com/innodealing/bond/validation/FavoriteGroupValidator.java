package com.innodealing.bond.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.innodealing.engine.jpa.dm.BondFavoriteGroupRepository;
import com.innodealing.engine.jpa.im.user.SysuserRepository;
import com.innodealing.exception.BusinessException;
import com.innodealing.model.dm.bond.BondFavoriteGroup;

@Component
public class FavoriteGroupValidator {

	@Autowired
	SysuserRepository sysuserRepository;
	
	@Autowired
	BondFavoriteGroupRepository favoriteGroupReposityory;

	public FavoriteGroupValidator(){
	}
	
	/**
	 * Validate groupName with regular expression
	 * @param username groupName for validation
	 * @return true valid groupName, false invalid groupName
	 */
	public void validateAddGroup(final Integer userId, final String groupName){

		if (userId <= 0) {
			throw new BusinessException("非法用户编号:" + userId);
		}
		
		if (sysuserRepository.findById((long)userId) == null) {
			throw new BusinessException("改用户不存在:" + userId);
		}
			
		if (groupName.isEmpty()) {
			throw new BusinessException("组名不能为空");
		}
		
		//matcher = pattern.matcher(groupName);
		//if (! matcher.matches()) {
		//	throw new BusinessException("关注组名称非法，长度在3～15之间，仅包含字符或数字");
		//}

		if(favoriteGroupReposityory.countGroupNameByUserId(userId, groupName) > 0) {
			throw new BusinessException("该关注组已存在");
		}
	}
	
	public BondFavoriteGroup validateUpdateGroup(final Integer userId, final Integer groupId, final String groupName){
		BondFavoriteGroup group = favoriteGroupReposityory.findOne(groupId);
		if (group == null) {
			throw new BusinessException("该关注组不存在:" + groupId);
		}
		/*
		if (group.getGroupType() == 0) {
			throw new BusinessException("无法更新持仓关注组:" + groupId);
		}
		*/
		if(!group.getGroupName().equals(groupName)) {
			if(favoriteGroupReposityory.countGroupNameByUserId(userId, groupName) > 0) {
				throw new BusinessException("该关注组已存在");
			}
		}
		return group;
	}
	
	public void validateDeleteGroup(final Integer groupId){
		BondFavoriteGroup group = favoriteGroupReposityory.findOne(groupId);
		if (group == null) {
			throw new BusinessException("该关注组不存在:" + groupId);
		}
		if (group.getGroupType() == 0) {
			throw new BusinessException("无法删除持仓关注组:" + groupId);
		}
	}
	
}
