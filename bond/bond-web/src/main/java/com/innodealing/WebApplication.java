package com.innodealing;

import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Properties;

import javax.servlet.Filter;
import javax.servlet.ServletContext;

import org.apache.catalina.Context;
import org.apache.catalina.filters.RequestDumperFilter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.springframework.web.filter.CorsFilter;

import com.innodealing.annotation.EnableTokenFilter;
import com.innodealing.util.SafeUtils;

import ch.qos.logback.access.tomcat.LogbackValve;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@EnableTokenFilter
@SpringBootApplication
//@EnableAspectJAutoProxy(proxyTargetClass = true)
public class WebApplication  extends SpringBootServletInitializer {
	
	Logger LOG = Logger.getLogger(WebApplication.class);
	
	@Autowired(required=false)
	ServletContext context; 
	
	@Override  
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {  
		return application.sources(WebApplication.class);  
	} 

	public static void main(String[] args) throws Exception {
	    SafeUtils.checkTimeZone();
		SpringApplication.run(WebApplication.class, args);
	}

	@Bean
	public CorsFilter corsFilter() {
		final UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
		final CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowCredentials(true);
		corsConfiguration.addAllowedOrigin("*");
		corsConfiguration.addAllowedHeader("*");
		corsConfiguration.addAllowedMethod("*");
		corsConfiguration.addExposedHeader("Content-Disposition");
		urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
		return new CorsFilter(urlBasedCorsConfigurationSource);
	}

	@Autowired
	public void setInfoProperties(ConfigurableEnvironment env) {
		if (context == null) {
			LOG.info("Might startup in STS, skip scm info intialization");
			return;
		}
		InputStream is = context.getResourceAsStream("/META-INF/MANIFEST.MF");
		if(is == null) {
			LOG.error("/META-INF/MANIFEST.MF doesn't exit");
			return;
		}
		Properties propMp = new Properties();
		try {
			propMp.load( is );
			//log all properties
			StringWriter writer = new StringWriter();
			propMp.list(new PrintWriter(writer));
			LOG.info("load META-INF/MANIFEST.MF to info:" + writer.getBuffer().toString());
			//save to info
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
	
	@Bean(name = "TeeFilter")
	public Filter teeFilter() {
		return new ch.qos.logback.access.servlet.TeeFilter();
	}

	//@Bean
	public EmbeddedServletContainerCustomizer containerCustomizer() {
		//http://stackoverflow.com/questions/33280732/spring-boot-tomcat-access-log-with-logback-config-file-in-a-packaged-applicatio
		return new EmbeddedServletContainerCustomizer() {
			@Override
			public void customize(ConfigurableEmbeddedServletContainer container) {
				if (container instanceof TomcatEmbeddedServletContainerFactory) {
					((TomcatEmbeddedServletContainerFactory) container)
					.addContextCustomizers(new TomcatContextCustomizer() {
						@Override
						public void customize(Context context) {
							LogbackValve logbackValve = new LogbackValve();
							logbackValve.setFilename("logback-access.xml");
							context.getPipeline().addValve(logbackValve);
						}
					});
				}
			}
		};
	}

	//注销下面的两种request log filter, 暂定使用logback的teefilter来记录所有访问数据
	//teefilter不推荐在生产环境中使用，将在服务趋于稳定后调整为仅在QA/Dev环境中启用
	//@Bean
	public Filter logFilter() {
		CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
		filter.setIncludeQueryString(true);
		filter.setIncludePayload(true);
		filter.setMaxPayloadLength(5120);
		return filter;
	}

	//@Bean
	public FilterRegistrationBean requestDumperFilter() {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		RequestDumperFilter requestDumperFilter = new RequestDumperFilter();
		registration.setFilter(requestDumperFilter);
		registration.addUrlPatterns("/*");
		return registration;
	}

}
