package com.innodealing.engine.jpa.im.user;

import org.springframework.stereotype.Component;

import com.innodealing.engine.jpa.im.BaseRepositoryIm;
import com.innodealing.model.im.user.SysuserPermissionSchema;

/**
 * @author 用户许可
 * @date 2016年7月27日
 * @clasename SysuserRepository.java
 * @decription TODO
 */
@Component
public interface SysuserPermissionSchemaRepository extends BaseRepositoryIm<SysuserPermissionSchema, Long> {
   
    /**
     * 查找用户许可模板
     * @param code 编号
     * @param status 状态
     * @return
     */
    public SysuserPermissionSchema findByCodeAndStatus(String code, Integer status);
    
    

}
