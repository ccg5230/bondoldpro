package com.innodealing.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HttpClientUtil2 {
	
	private static final Logger LOG = LoggerFactory.getLogger(HttpClientUtil2.class);

	public static String doPost(String url,String parameters, String charset,String userid) {
		CloseableHttpClient httpClient = null;
		HttpPost httpPost = null;
		String result = null;
		try {
			// 创建默认的httpClient实例.
			httpClient = HttpClients.createDefault();
			httpPost = new HttpPost(url);
			httpPost.addHeader("userId", userid); //认证token 
			httpPost.addHeader("Content-Type", "application/json"); 
        	httpPost.setHeader("Accept", "application/json");  
			// 设置参数
			StringEntity entity = new StringEntity(parameters, charset);
			httpPost.setEntity(entity);
			CloseableHttpResponse response = httpClient.execute(httpPost);
			if (response != null) {
				try {
					HttpEntity resEntity = response.getEntity();
					if (resEntity != null) {
						result = EntityUtils.toString(resEntity, charset);
					}
				} finally {
					response.close();
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			// 关闭连接,释放资源
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return result;
	}
	
	public static String doGet(String url, Map<String, String> map, String charset,String userid) {
		LOG.info("doPost:"+map.toString());
		CloseableHttpClient httpClient = null;
		HttpGet httpGet = null;
		String result = null;
		try {
			// 创建默认的httpClient实例.
			httpClient = HttpClients.createDefault();
			// 设置参数
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			Iterator iterator = map.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<String, String> elem = (Entry<String, String>) iterator.next();
				list.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
			}
			if (list.size() > 0) {
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, charset);
				httpGet = new HttpGet(url+"?"+EntityUtils.toString(entity));
				httpGet.addHeader("userId", userid); //认证token 
				httpGet.addHeader("Content-Type", "application/json"); 
			}
			CloseableHttpResponse response = httpClient.execute(httpGet);
			if (response != null) {
				try {
					HttpEntity resEntity = response.getEntity();
					if (resEntity != null) {
						result = EntityUtils.toString(resEntity, charset);
					}
				} finally {
					response.close();
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			// 关闭连接,释放资源
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

}
