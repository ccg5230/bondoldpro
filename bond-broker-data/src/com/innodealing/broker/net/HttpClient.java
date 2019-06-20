package com.innodealing.broker.net;

public interface HttpClient {
	public HttpResult get(String targetURL, String urlParameters);
	public HttpResult post(String targetURL, String urlParameters) ;
}
