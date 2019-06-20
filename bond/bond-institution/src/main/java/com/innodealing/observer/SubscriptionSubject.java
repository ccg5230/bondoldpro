package com.innodealing.observer;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 具体被观察者
 * 
 * @author liuqi
 *
 */
public class SubscriptionSubject implements Subject {
	
	private static final Logger LOG = LoggerFactory.getLogger(SubscriptionSubject.class);

	private List<Observer> ObserverList = new ArrayList<Observer>();

	@Override
	public void attach(Observer observer) {
		// TODO Auto-generated method stub
		ObserverList.add(observer);
	}

	@Override
	public void detach(Observer observer) {
		// TODO Auto-generated method stub
		ObserverList.remove(observer);
	}

	@Override
	public void notify(String message,String userid) {
		// TODO Auto-generated method stub
		try {
			for (Observer observer : ObserverList) {
				observer.update(message,userid);
			}
		} catch (Exception e) {
			// 回滚
			//当出现异常调用各接口回滚
			for (Observer observer : ObserverList) {
				try {
					observer.rollback(message,userid);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					LOG.error("Notification failed ,message ="+message);
				}
			}
		}
	}

}
