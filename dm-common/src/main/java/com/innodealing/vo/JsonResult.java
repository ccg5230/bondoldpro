package com.innodealing.vo;

import java.io.Serializable;

//@ApiModel(description="对外接口统一返回结果�?")
public class JsonResult<T> implements Serializable{
	private static final long serialVersionUID = 1L;
	
	//@ApiModelProperty(value="结果代码(0正常�?-1错误)")
	private String code;
	
	//@ApiModelProperty(value="返回的结果信�?")
	private String message;
	
	//@ApiModelProperty(value="返回的结果数�?")
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
