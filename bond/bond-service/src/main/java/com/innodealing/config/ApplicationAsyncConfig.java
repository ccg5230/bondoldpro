package com.innodealing.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource(locations={"classpath:spring/application-async.xml"})
public class ApplicationAsyncConfig {

}
