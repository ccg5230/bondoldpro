package com.innodealing.exception;

/**
 * 业务异常
 * @author 赵正来
 * @since 2016年5月27日下午7:31:52 Copyright © 2015 DealingMatrix.cn. All Rights
 *        Reserved.
 */
public class WarnException extends BusinessException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public WarnException(String msg) {
        super("1",msg);
    }

   

}
