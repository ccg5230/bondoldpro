package com.innodealing.observer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * @author liuqi
 *
 */
@Configuration
public class MyStartupRunner {

	@Value("${config.define.serviceUrl}")
	private String serviceUrl;

	@Bean(name = "induSubscriptionSubject")
	public SubscriptionSubject subscriptionSubject() {

		// 被观察者
		SubscriptionSubject subject = new SubscriptionSubject();

		// 观察者
		ConcrereObserver observer = new ConcrereObserver("bond-integration",
				serviceUrl + "/bond-web/api/bond/inst/indu/save", serviceUrl + "/bond-web/api/bond/inst/indu/save");

		subject.attach(observer);

		return subject;

	}

	@Bean(name = "comInduSubscriptionSubject")
	public SubscriptionSubject comInduSubscriptionSubject() {

		// 被观察者
		SubscriptionSubject subject = new SubscriptionSubject();

		// 观察者
		ConcrereObserver observer = new ConcrereObserver("bond-integration",
				serviceUrl + "/bond-web/api/bond/inst/comIndu/save",
				serviceUrl + "/bond-web/api/bond/inst/comIndu/save");

		subject.attach(observer);

		return subject;

	}

}
