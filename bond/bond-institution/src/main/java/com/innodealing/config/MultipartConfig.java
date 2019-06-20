package com.innodealing.config;

import java.io.File;

import javax.servlet.MultipartConfigElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.innodealing.consts.InstConstants;
import com.innodealing.util.StringUtils;

@Configuration
public class MultipartConfig {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(MultipartConfig.class);

	@Value("${server.multipart.path}")
	private String projectPath;

	@Bean
	MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		String location = StringUtils.isNotBlank(projectPath) ? projectPath + InstConstants.MULTIPART_TMPPATH
				: InstConstants.MULTIPART_TMPPATH;
		File file = new File(location);
		if (!file.exists()) {
			file.mkdirs();
			LOGGER.info("location["+location+"] is not exsiting..commit mkdirs.");
		}
		
		factory.setLocation(location);
		return factory.createMultipartConfig();
	}

}
