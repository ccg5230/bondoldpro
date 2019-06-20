package com.innodealing.observer;

public interface Observer {
	
	public void update(String message,String userid) throws Exception;
	
	public void rollback(String message,String userid) throws Exception;

}
