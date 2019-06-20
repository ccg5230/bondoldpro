package com.innodealing.observer;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.innodealing.util.HttpClientUtil2;

/**
 * 具体观察者
 * 
 * @author liuqi
 *
 */
public class ConcrereObserver implements Observer {
	
	private static final Logger LOG = LoggerFactory.getLogger(ConcrereObserver.class);

	private String name;
	private String url;
	private String rollbackUrl;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getRollbackUrl() {
		return rollbackUrl;
	}

	public void setRollbackUrl(String rollbackUrl) {
		this.rollbackUrl = rollbackUrl;
	}

	public ConcrereObserver() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ConcrereObserver(String name, String url , String rollbackUrl) {
		super();
		this.name = name;
		this.url = url;
		this.rollbackUrl = rollbackUrl;
	}

	@Override
	public void update(String message,String userid) throws Exception {
//		throw new NotFoundException("11");
		// TODO Auto-generated method stub
//		System.out.println(this.name + ":" + this.url + ";update,message=" + message);
		// 发送当前消息到指定url 接口返回正确代码 1成功 0失败 失败则手动抛出异常
		String result = HttpClientUtil2.doPost(this.url, message, "utf-8",userid);
		LOG.info("url:"+url+",json:"+message+",result:"+result);
	}

	@Override
	public void rollback(String message,String userid) throws Exception {
		// TODO Auto-generated method stub
//		System.out.println(this.name + ":" + this.rollbackUrl + ";rollback,message=" + message);
		// 发送当前消息到指定url 接口返回正确代码 1成功 0失败 失败则手动抛出异常
		String result = HttpClientUtil2.doPost(this.rollbackUrl, message, "utf-8",userid);
		LOG.info("url:"+url+",json:"+message+",result:"+result);
	}

}
