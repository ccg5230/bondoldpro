package com.innodealing.util.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

	private static String amaresunUrlAuth ;
	private static String amaresunUrlGate ;
	private static String amaresunUserId;
	private static String amaresunUserName;
	private static String amaresunPassword;
	
	
    public static String getAmaresunUrlAuth() {
		return amaresunUrlAuth;
	}
    
    @Value("${amaresun_url_auth}")
	public void setAmaresunUrlAuth(String amaresunUrlAuth) {
		AppConfig.amaresunUrlAuth = amaresunUrlAuth;
	}
	
	public static String getAmaresunUrlGate() {
		return amaresunUrlGate;
	}
	
	@Value("${amaresun_url_gate}")
	public void setAmaresunUrlGate(String amaresunUrlGate) {
		AppConfig.amaresunUrlGate = amaresunUrlGate;
	}
	
	public static String getAmaresunUserId() {
		return amaresunUserId;
	}
	
	@Value("${amaresun_user_id}")
	public void setAmaresunUserId(String amaresunUserId) {
		AppConfig.amaresunUserId = amaresunUserId;
	}
	
	public static String getAmaresunUserName() {
		return amaresunUserName;
	}
	
	@Value("${amaresun_user_name}")
	public void setAmaresunUserName(String amaresunUserName) {
		AppConfig.amaresunUserName = amaresunUserName;
	}
	
	public static String getAmaresunPassword() {
		return amaresunPassword;
	}
	
	@Value("${amaresun_password}")
	public void setAmaresunPassword(String amaresunPassword) {
		AppConfig.amaresunPassword = amaresunPassword;
	}
}
