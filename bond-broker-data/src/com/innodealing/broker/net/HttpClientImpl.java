package com.innodealing.broker.net;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

public class HttpClientImpl implements HttpClient {

	static Logger log = Logger.getLogger(HttpClientImpl.class);

	public static final String CHARSET = "UTF-8";

	private static CloseableHttpClient httpclient = HttpClients.createDefault();

	public HttpResult get(String targetURL, String urlParameters) {

		log.debug("http get:" + targetURL + ", parameters:" + urlParameters);

		HttpResult tmpresult = new HttpResult();
		URL url;
		HttpURLConnection connection = null;
		try {
			// Create connection
			if (targetURL.indexOf("?") != -1) {
				if (!targetURL.endsWith("?"))
					targetURL += "&";
			} else
				targetURL += "?";
			targetURL += urlParameters;
			url = new URL(targetURL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.connect();
			// Get Response
			InputStream is = (InputStream) connection.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			String line;
			StringBuffer response = new StringBuffer();
			while ((line = rd.readLine()) != null) {
				response.append(line);
				response.append('\r');
			}
			rd.close();
			tmpresult.setResponseCode(connection.getResponseCode());
			tmpresult.setResponse(response.toString());

		} catch (Exception e) {
			log.error("http get had an exception:" + e.toString(), e);
			e.printStackTrace();
			tmpresult.setResponseCode(-1);
			tmpresult.setResponse("");

		} finally {

			if (connection != null) {
				connection.disconnect();
			}
		}
		return tmpresult;
	}

	public HttpResult post(String targetURL, String urlParameters) {
		return post(targetURL, urlParameters, null);
	}

	public HttpResult post(String targetURL, String urlParameters, Map<String, String> extraHeaders) {

		log.debug("http post, url:" + targetURL + ", parameters:" + urlParameters);

		HttpResult tmpresult = new HttpResult();
		URL url;
		HttpURLConnection connection = null;
		try {
			// Create connection
			url = new URL(targetURL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");

			// urlCon.setRequestProperty("Content-Type", "text/xml");
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
			connection.setRequestProperty("Content-Length", "" + Integer.toString(urlParameters.getBytes().length));
			connection.setRequestProperty("Content-Language", "en-US");
			if (extraHeaders != null && extraHeaders.size() > 0) {
				for (Map.Entry<String, String> entry : extraHeaders.entrySet())
					connection.setRequestProperty(entry.getKey(), entry.getValue());
			}

			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setConnectTimeout(30000);
			connection.setReadTimeout(30000);
			// Send request
			DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
			wr.write(urlParameters.getBytes("UTF-8"));
			wr.flush();
			wr.close();

			// Get Response
			// InputStream is = (InputStream) connection.getInputStream();
			// BufferedReader rd = new BufferedReader(new
			// InputStreamReader(is,"UTF-8"));
			// String line;
			// StringBuffer response = new StringBuffer();
			// while ((line = rd.readLine()) != null) {
			// response.append(line);
			// response.append('\r');
			// }
			// rd.close();
			tmpresult.setResponseCode(connection.getResponseCode());
			// tmpresult.setResponse(response.toString());
			tmpresult.setResponse("");

			log.debug("http post successed");

		} catch (Exception e) {
			log.error("http post had an exception:" + e.toString(), e);
			e.printStackTrace();
			tmpresult.setResponseCode(-1);
			tmpresult.setResponse("");
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
		return tmpresult;
	}

	public String doPostJson(String url, String parameters) {
		int status = 0;
		HttpPost method = new HttpPost(url);
		String body = null;

		if (method != null & parameters != null && !"".equals(parameters.trim())) {
			try {
				// 建立一个NameValuePair数组，用于存储欲传送的参数
				method.addHeader("Content-type", "application/json; charset=utf-8");
				method.setHeader("Accept", "application/json");
				method.setEntity(new StringEntity(parameters, Charset.forName("UTF-8")));

				HttpResponse response = httpclient.execute(method);

				int statusCode = response.getStatusLine().getStatusCode();

				if (statusCode != HttpStatus.SC_OK) {
					log.error("Method failed:" + response.getStatusLine());
					status = 1;
				}
				body = EntityUtils.toString(response.getEntity());

			} catch (IOException e) {
				// 网络错误
				status = 3;
			} finally {
				log.info("调用接口状态：" + status);
			}

		}
		return body;
	}
}
