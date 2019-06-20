//package com.innodealing.datacanal.listen;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationListener;
//import org.springframework.context.event.ContextRefreshedEvent;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Component;
//
//import com.innodealing.datacanal.service.CanalStarterClient;
//
//@Component
//public class StartupApp implements ApplicationListener<ContextRefreshedEvent> {
//
//	private @Autowired JdbcTemplate jdbcTemplate;
//	private CanalStarterClient canalStarterClient = new CanalStarterClient("example");
//
//	@Override
//	public void onApplicationEvent(ContextRefreshedEvent event) {
//
//		//canalStarterClient.run(jdbcTemplate);
//	}
//
//}
