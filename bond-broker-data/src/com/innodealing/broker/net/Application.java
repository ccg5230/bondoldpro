package com.innodealing.broker.net;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class Application {

	private static final Logger LOG = Logger.getLogger(Application.class);

	static Properties properties = new Properties();;

	static {
		InputStream in = Object.class.getResourceAsStream("/config.properties");
		try {
			properties.load(in);
		} catch (IOException e) {
			LOG.error("读取文件失败", e);
		}
	}

	public static void main(String[] args) {
		try {
			
			MyFlume server = new MyFlume(9876);
			LOG.info("listen on port " + 9876);
			server.mainLoop();
		} catch (Exception ex) {
			LOG.error("抓取程序错误!", ex);
		}
	}
}
