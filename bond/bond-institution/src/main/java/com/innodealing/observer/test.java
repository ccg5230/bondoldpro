package com.innodealing.observer;

public class test {
	
	public static void main(String[] args) {
		
		//被观察者
		SubscriptionSubject subject = new SubscriptionSubject();
		
		//观察者
		ConcrereObserver observer1 = new ConcrereObserver("张三","http://192.168.10.57:18080/bond-web/api/bond/inst/comIndu/save","http://192.168.10.57:18080/bond-web/api/bond/inst/indu/save");
		
		subject.attach(observer1);
		
		subject.notify("[{\"comChiName\":\"安徽江淮汽车集团有限公司\",\"comUniCode\":200036382,\"instId\":2385,\"induUniName\":\"22233\",\"induUniCode\":730305}]","518602");
	}

}
