package com.innodealing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.innodealing.annotation.EnableTokenFilter;

@SpringBootApplication
@EnableTokenFilter
public class DmSecurityWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(DmSecurityWebApplication.class, args);
	}


	
	
	
//	  @Bean
//    public FilterRegistrationBean getDemoFilter(){
//        TokenFilter tokenFilter=new TokenFilter(redisTemplate);
//        FilterRegistrationBean registrationBean=new FilterRegistrationBean();
//        registrationBean.setFilter(tokenFilter);
//        List<String> urlPatterns=new ArrayList<String>();
//        urlPatterns.add("/*");//拦截路径，可以添加多个
//        registrationBean.setUrlPatterns(urlPatterns);
//        registrationBean.setOrder(1);
//        return registrationBean;
//    }
	
	
}
