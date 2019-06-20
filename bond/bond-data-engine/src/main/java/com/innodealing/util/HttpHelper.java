package com.innodealing.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpHelper {
	public static Logger log = LoggerFactory.getLogger(HttpHelper.class);

	public static HttpResult get(String targetURL, String urlParameters) {
		HttpResult tmpresult = new HttpResult();
		URL url;
		HttpURLConnection connection = null;
		try {
			log.info("Http get TargetUrl:" + targetURL);
			// Create connection
			if(targetURL.contains("?")){
				if(!targetURL.endsWith("?"))targetURL+="&";
			}
			else targetURL+="?";
			targetURL+=urlParameters;
			url = new URL(targetURL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.connect();
			// Get Response
			InputStream is = connection.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is,"UTF-8"));
			String line;
			StringBuilder response = new StringBuilder();
			while ((line = rd.readLine()) != null) {
				response.append(line);
				response.append('\r');
			}
			rd.close();
			tmpresult.setResponseCode(connection.getResponseCode());
			tmpresult.setResponse(response.toString());

		} catch (Exception e) {

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

	public static HttpResult postJson(String targetURL,String urlParameters){
		HttpResult tmpresult = new HttpResult();
		try
		{
			URL url = new URL(targetURL);
			HttpURLConnection connection =(HttpURLConnection)url.openConnection();
			connection.setRequestProperty("Content-Type",
			"application/json;charset=utf-8");
			connection.setDoOutput(true);
			OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream(),"UTF-8");
			wr.write(urlParameters);
			wr.flush();
			BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
			String line;
			StringBuilder response = new StringBuilder();
			while((line = rd.readLine()) != null) {
				response.append(line);
				response.append('\r');
			}
			rd.close();
			tmpresult.setResponseCode(connection.getResponseCode());
			tmpresult.setResponse(response.toString());
			wr.close();
			rd.close();
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
		return tmpresult;
	}

		public static HttpResult postRaw(String targetURL,String urlParameters){
			 HttpResult tmpresult = new HttpResult();			  
			try
			{
				URL url = new URL(targetURL);
				HttpURLConnection connection =(HttpURLConnection)url.openConnection();
				connection.setDoOutput(true);
				OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream(),"UTF-8");
				wr.write(urlParameters);
				wr.flush();
				BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
				 String line;
			      StringBuilder response = new StringBuilder();
			      while((line = rd.readLine()) != null) {
			        response.append(line);
			        response.append('\r');
			      }
			      rd.close();
			      tmpresult.setResponseCode(connection.getResponseCode());
			      tmpresult.setResponse(response.toString());
				wr.close();
				rd.close();
			}
			catch(Exception e)
			{
				System.out.println(e.toString());
			} 
			return tmpresult;
		}
		
		public static HttpResult post(String targetURL, String urlParameters) {
			HttpResult tmpresult = new HttpResult();
			URL url;
			HttpURLConnection connection = null;
			try {
				log.info("HttpPost TargetUrl:" + targetURL);
				// Create connection
				url = new URL(targetURL);
				connection = (HttpURLConnection) url.openConnection();
				connection.setRequestMethod("POST");
				// urlCon.setRequestProperty("Content-Type", "text/xml");
				connection.setRequestProperty("Content-Type",
						"application/x-www-form-urlencoded;charset=utf-8");
				connection.setRequestProperty("Content-Length", ""
						+ Integer.toString(urlParameters.getBytes().length));
				connection.setRequestProperty("Content-Language", "en-US");

				connection.setUseCaches(false);
				connection.setDoInput(true);
				connection.setDoOutput(true);
				connection.setConnectTimeout(20000);
				connection.setReadTimeout(20000);
				// Send request
				DataOutputStream wr = new DataOutputStream(connection
						.getOutputStream());
				wr.write(urlParameters.getBytes("UTF-8"));
				wr.flush();
				wr.close();

				// Get Response
				InputStream is = connection.getInputStream();
				BufferedReader rd = new BufferedReader(new InputStreamReader(is,"UTF-8"));
				String line;
				StringBuilder response = new StringBuilder();
				while ((line = rd.readLine()) != null) {
					response.append(line);
					response.append('\r');
				}
				rd.close();
				tmpresult.setResponseCode(connection.getResponseCode());
				tmpresult.setResponse(response.toString());

			} catch (Exception e) {

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

	  public static HttpResult postXML(String targetURL, String xmlContent)
	  {
		  HttpResult tmpresult = new HttpResult();
	    URL url;
	    HttpURLConnection connection = null;  
	    try {
	      //Create connection
	      url = new URL(targetURL);
	      connection = (HttpURLConnection)url.openConnection();
	      connection.setRequestMethod("POST");
	      //urlCon.setRequestProperty("Content-Type", "text/xml");  
	      connection.setRequestProperty("Content-Type", 
	           "text/xml;charset=utf-8");				
	      connection.setRequestProperty("Content-Length", "" +
				  Integer.toString(xmlContent.getBytes("UTF-8").length));
	      connection.setRequestProperty("Content-Language", "en-US");  
				
	      connection.setUseCaches(false);
	      connection.setDoInput(true);
	      connection.setDoOutput(true);

	      //Send request
	      DataOutputStream wr = new DataOutputStream (
	                  connection.getOutputStream());
	      wr.write(xmlContent.getBytes("UTF-8"));
	      wr.flush ();
	      wr.close ();

	      //Get Response	
	      InputStream is = connection.getInputStream();
	      BufferedReader rd = new BufferedReader(new InputStreamReader(is,"UTF-8"));
	      String line;
	      StringBuilder response = new StringBuilder();
	      while((line = rd.readLine()) != null) {
	        response.append(line);
	        response.append('\r');
	      }
	      rd.close();
	      tmpresult.setResponseCode(connection.getResponseCode());
	      tmpresult.setResponse(response.toString());

	    } catch (Exception e) {
	    	tmpresult.setResponseCode(-1);
	    	tmpresult.setResponse("");
	        e.printStackTrace();
	      //return null;

	    } finally {
	      if(connection != null) {
	        connection.disconnect(); 
	      }
	    }
	    return tmpresult;
	  }

}
