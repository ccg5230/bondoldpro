package com.innodealing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableTransactionManagement
@EnableSwagger2
@SpringBootApplication
public class BondCombineApplication {

	public static void main(String[] args) {
		SpringApplication.run(BondCombineApplication.class, args);
	}
}
