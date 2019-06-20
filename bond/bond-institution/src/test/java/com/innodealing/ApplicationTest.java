package com.innodealing;

import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;
import org.junit.runner.notification.RunListener;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class,  webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource(locations = "classpath:application-dev.properties")
@AutoConfigureMockMvc
public class ApplicationTest  {

	@Test	
	public void init(){
	}
	
	public static void main(String[] args) {
		JUnitCore core= new JUnitCore();
		core.addListener(new RunListener());
		core.run(ApplicationTest.class);
	}


}
