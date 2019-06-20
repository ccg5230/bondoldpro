package com.innodealing;

import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableTransactionManagement
@EnableSwagger2
@SpringBootApplication
public class BondPortfolioServiceApplication extends SpringBootServletInitializer {

	Logger LOG = Logger.getLogger(BondPortfolioServiceApplication.class);

	@Autowired(required = false)
	ServletContext context;

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(BondPortfolioServiceApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(BondPortfolioServiceApplication.class, args);
	}

	@Autowired
	public void setInfoProperties(ConfigurableEnvironment env) {
		if (context == null) {
			LOG.info("Might startup in STS, skip scm info intialization");
			return;
		}
		InputStream is = this.getClass().getResourceAsStream("/META-INF/MANIFEST.MF");
		if (is == null) {
			LOG.error("/META-INF/MANIFEST.MF doesn't exit");
			return;
		}
		Properties propMp = new Properties();
		try {
			propMp.load(is);
			// log all properties
			StringWriter writer = new StringWriter();
			propMp.list(new PrintWriter(writer));
			LOG.info("load META-INF/MANIFEST.MF to info:" + writer.getBuffer().toString());
			// save to info
			Properties props = new Properties();
			props.put("info.Implementation-Title", propMp.getOrDefault("Implementation-Title", ""));
			props.put("info.SCM-Revision", propMp.getOrDefault("SCM-Revision", ""));
			props.put("info.build-timestamp", propMp.getOrDefault("build-timestamp", ""));
			props.put("info.Build-Jdk", propMp.getOrDefault("Build-Jdk", ""));
			props.put("info.Built-By", propMp.getOrDefault("Built-By", ""));
			env.getPropertySources().addFirst(new PropertiesPropertySource("extra-info-props", props));
		} catch (Exception ex) {
			LOG.error("failed to set info properties", ex);
			return;
		}
	}

	@Bean
	public CorsFilter corsFilter() {
		final UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
		final CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowCredentials(true);
		corsConfiguration.addAllowedOrigin("*");
		corsConfiguration.addAllowedHeader("*");
		corsConfiguration.addAllowedMethod("*");
		urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
		return new CorsFilter(urlBasedCorsConfigurationSource);
	}

}
