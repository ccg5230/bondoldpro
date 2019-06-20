package com.innodealing.engine.jpa.im.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import com.innodealing.engine.jpa.im.BaseRepositoryIm;
import com.innodealing.model.im.user.SysuserPermission;

/**
 * @author 用户许可
 * @date 2016年7月27日
 * @clasename SysuserRepository.java
 * @decription TODO
 */
@Component
public interface SysuserPermissionRepository extends BaseRepositoryIm<SysuserPermission, Long> {
    /**
     * 查找用户许可
     * @param dmacct dm账号
     * @param codeId SysuserPermissionSchema 中的id
     * @param status 状态
     * @return 
     */
    @Query("select s from SysuserPermission s where s.dmacct =?1 and codeId = ?2 and status = ?3")
    public SysuserPermission findOneByDmacctAndCodeIdAndStatus(String dmacct, Long codeId, Integer status);
    
    /**
     * 查找用户许可
     * @param dmacct dm账号
     * @param codeId SysuserPermissionSchema 中的id
     * @return 
     */
    public SysuserPermission findOneByDmacctAndCodeId(String dmacct, Long codeId);
    
    

}
