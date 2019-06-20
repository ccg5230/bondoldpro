package com.innodealing.util;

import java.io.Serializable;

/**
 * 对外接口统一返回结果类
* @author 戴永杰
* @date 2017年6月1日 下午5:09:02 
* @version V1.0
 */
public class JsonResult<T> implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/**
	 * 结果代码(0正常，-1错误)
	 */
	private String code;
	
	/**
	 * 返回的结果信息
	 */
	private String message;
	
	/**
	 * 返回的结果数据
	 */
	private T data;

	
	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public T getData() {
		return data;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setData(T data) {
		this.data = data;
	}

	public JsonResult(String code, String message, T data) {
		super();
		this.code = code;
		this.message = message;
		this.data = data;
	}

	public JsonResult() {
		super();
	}

	@Override
	public String toString() {
		return "JsonResult [code=" + code + ", message=" + message + ", data=" + data + "]";
	}
	
	public JsonResult<T> ok(T t){
		return new JsonResult<T>("0", "success", t);
	}
	
}
