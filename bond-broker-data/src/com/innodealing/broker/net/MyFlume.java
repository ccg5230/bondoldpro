package com.innodealing.broker.net;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.innodealing.broker.model.BaseQuoteBaordParam;
import com.innodealing.broker.model.BondBrokerDealParam;
import com.innodealing.broker.model.BrokerBondQuoteParam;
import com.innodealing.broker.util.StringUtils;

public class MyFlume {

	private static final Logger LOG = Logger.getLogger(MyFlume.class);

	private static SimpleDateFormat insdf = new SimpleDateFormat("HH:mm:ss");
	private static SimpleDateFormat outsdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static SimpleDateFormat endsdf = new SimpleDateFormat("yyyy-MM-dd");

	private HttpClientImpl HttpClientUtil = new HttpClientImpl();

	private String bondQuoteUrl = Application.properties.getProperty("config.bondQuoteUrl");
	private String bondDealsBrokerUrl = Application.properties.getProperty("config.bondDealsBrokerUrl");;

	private int serverPort = 0;

	private String bondJson = new String();
	private String bondDealJson = new String();

	/**
	 * FIFO队列 长度:未指定容量Integer.MAX_VALUE 线程阻塞-安全的
	 */
	private BlockingQueue<String> msgQueue = new LinkedBlockingQueue<String>();
	private BlockingQueue<String> dealQueue = new LinkedBlockingQueue<String>();
	private BlockingQueue<String> quoteQueue = new LinkedBlockingQueue<String>();

	public MyFlume(int port) {
		this.serverPort = port;
	}

	public void mainLoop() throws IOException, ParseException {

		BasicConfigurator.configure();
		@SuppressWarnings("resource")
		DatagramSocket serverSocket = new DatagramSocket(serverPort);
		byte[] receiveData = new byte[1024];
		// 设置缓存区大小
		serverSocket.setReceiveBufferSize(8 * 1024 * 1024);

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					processIncomingMsgLoop();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					sendDealLoop();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					sendQuoteLoop();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();

		while (true) {

			try {
				DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
				serverSocket.receive(receivePacket);
				// 放入队列
				// 把Object加到BlockingQueue里,如果BlockQueue没有空间,则调用此方法的线程被阻断直到BlockingQueue里面有空间再继续.
				msgQueue.put(new String(receivePacket.getData(), 0, receivePacket.getLength()));
			} catch (Exception e) {
				e.printStackTrace();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}

	}

	private void processIncomingMsgLoop() throws InterruptedException {

		while (true) {
			try {
				exec(msgQueue.take());
			} catch (Exception e) {
				e.printStackTrace();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}
	}


	List<String> str = new ArrayList<String>();
	List<String> dealstr = new ArrayList<String>();

	public void exec(String jsonLine) throws Exception {
		
		JsonElement jelement = new JsonParser().parse(jsonLine);
		JsonObject jobject = jelement.getAsJsonObject();
		int row = jobject.get("row").getAsInt();
		int col = jobject.get("col").getAsInt();
		String text = jobject.get("val").getAsString();
		
		if (row == 0) {
			return;
		}

		if (row != 0 && text.indexOf(":") == -1) {
			str.add(jsonLine);
			dealstr.add(jsonLine);
		} else if (text.indexOf(":") != -1) {
			if (col > 22) {
				str.add(jsonLine);
				for (String json : str) {
					processIncomingMsg(json);
				}
				
				LOG.info("str:"+str.toString());
				str.clear();
				
				//如果没有最后一列
				if(bondJson.indexOf("sendTime")==-1){
					String newdata = "{" + bondJson + "}";
					try {
						if (!StringUtils.isEmpty(newdata)) {
							ObjectMapper mapper = new ObjectMapper();
							BaseQuoteBaordParam vo = mapper.readValue(newdata, BaseQuoteBaordParam.class);
							addBondQuote(vo);
						}
					} catch (Exception e) {
						LOG.error("json to model is error", e);
					}
				}
				
			} else {
				dealstr.add(jsonLine);
				for (String json : dealstr) {
					processIncomingDealMsg(json);
				}
				LOG.info("dealstr:"+dealstr.toString());
				dealstr.clear();
			}
		}
	}

	public void processIncomingMsg(String jsonLine)
			throws Exception {
		LOG.info("processIncomingMsg:" + jsonLine);
		JsonElement jelement = new JsonParser().parse(jsonLine);
		JsonObject jobject = jelement.getAsJsonObject();
		int row = jobject.get("row").getAsInt();
		int col = jobject.get("col").getAsInt();
		String text = jobject.get("val").getAsString();

		String str = getName(col);
		if (str.isEmpty())
			return;

		if ("residualMaturity".equals(str)) {
			bondJson = "\"" + str + "\":\"" + text + "\"";
		} else if ("bondCode".equals(str)) {
			bondJson += "," + "\"" + str + "\":\"" + text + "\"";
		} else if ("sendTime".equals(str)) {
			Calendar cal = Calendar.getInstance();
			String newdata = new String();
			if (row > 0) {
				try {
					Calendar time = Calendar.getInstance();
					time.setTime(insdf.parse(text));
					cal.set(Calendar.HOUR_OF_DAY, time.get(Calendar.HOUR_OF_DAY));
					cal.set(Calendar.MINUTE, time.get(Calendar.MINUTE));
					cal.set(Calendar.SECOND, time.get(Calendar.SECOND));
				} catch (ParseException e) {
					if (isTradingEnd(text)) {
						LOG.info("trading end!!!!!");
					} else {
						LOG.error(bondJson, e);
					}
					return;
				}
				bondJson += "," + "\"" + str + "\":\"" + outsdf.format(cal.getTime()) + "\"";
				newdata = "{" + bondJson + "}";

				try {
					if (!StringUtils.isEmpty(newdata)) {
//						LOG.info(newdata);
						ObjectMapper mapper = new ObjectMapper();
						BaseQuoteBaordParam vo = mapper.readValue(newdata, BaseQuoteBaordParam.class);
						addBondQuote(vo);
					}
				} catch (Exception e) {
					LOG.error("json to model is error", e);
				}
			}
//			LOG.info(newdata);
		} else {
			bondJson += "," + "\"" + str + "\":\"" + text + "\"";
		}

	}

	public void processIncomingDealMsg(String jsonLine)
			throws ParseException, JsonParseException, JsonMappingException, IOException {
		LOG.info("processIncomingMsg:" + jsonLine);
		JsonElement jelement = new JsonParser().parse(jsonLine);
		JsonObject jobject = jelement.getAsJsonObject();
		int row = jobject.get("row").getAsInt();
		int col = jobject.get("col").getAsInt();
		String text = jobject.get("val").getAsString();

		String str = getDealName(col);
		if (str.isEmpty())
			return;

		if ("residualMaturity".equals(str)) {
			bondDealJson = "\"" + str + "\":\"" + text + "\"";
		} else if ("bondCode".equals(str)) {
			bondDealJson += "," + "\"" + str + "\":\"" + text + "\"";
		} else if ("sendTime".equals(str)) {
			Calendar cal = Calendar.getInstance();
			String newdata = new String();
			if (row > 0) {
				try {
					Calendar time = Calendar.getInstance();
					time.setTime(insdf.parse(text));
					cal.set(Calendar.HOUR_OF_DAY, time.get(Calendar.HOUR_OF_DAY));
					cal.set(Calendar.MINUTE, time.get(Calendar.MINUTE));
					cal.set(Calendar.SECOND, time.get(Calendar.SECOND));
				} catch (ParseException e) {
					if (isTradingEnd(text)) {
						LOG.info("trading end!!!!!");
					} else {
						LOG.error(bondDealJson, e);
					}
					return;
				}
				bondDealJson += "," + "\"" + str + "\":\"" + outsdf.format(cal.getTime()) + "\"";
				newdata = "{" + bondDealJson + "}";

				try {
					if (!StringUtils.isEmpty(newdata)) {
//						LOG.info(newdata);
						ObjectMapper mapper = new ObjectMapper();
						BaseQuoteBaordParam vo = mapper.readValue(newdata, BaseQuoteBaordParam.class);
						addBondQuote(vo);
					}
				} catch (Exception e) {
					LOG.error("json to model is error", e);
				}
			}
//			LOG.info(newdata);
		} else {
			bondDealJson += "," + "\"" + str + "\":\"" + text + "\"";
		}

	}

	static private boolean isTradingEnd(String timefield) {
		try {
			endsdf.parse(timefield);
		} catch (ParseException e) {
			return false;
		}
		return true;
	}

	private String getName(int col) throws Exception {
		String str = new String();
		
		switch (col) {
		case 1:
			str = "residualMaturity";
			break;
		case 2:
			str = "bondCode";
			break;
		case 3:
			str = "bondShortName";
			break;
		case 5:
			str = "bondStrikePrice";
			break;
		case 6:
			str = "bondVol";
			break;
		case 7:
			str = "bondPrice";
			break;
		case 8:
			str = "ofrBondPrice";
			break;
		case 9:
			str = "ofrBondVol";
			break;
		case 30:
			str = "brokerType";
			break;
		case 31:
			str = "sendTime";
			break;
		default:
			break;
		}
		return str;
	}

	private String getDealName(int col) {
		String str = new String();
		switch (col) {
		case 1:
			str = "residualMaturity";
			break;
		case 2:
			str = "bondCode";
			break;
		case 3:
			str = "bondShortName";
			break;
		case 5:
			str = "bondStrikePrice";
			break;
		case 21:
			str = "brokerType";
			break;
		case 22:
			str = "sendTime";
			break;
		}
		return str;
	}

	private void addBondQuote(BaseQuoteBaordParam vo) throws ParseException, IOException {

		BrokerBondQuoteParam paramVO = new BrokerBondQuoteParam();

		if (null != vo) {
			/** 不是匿名报价 */
			paramVO.setAnonymous(0);
			/** 备注 */
			paramVO.setRemark("");
			/** Broker */
			paramVO.setPostfrom(9);
			/** 债券代码 */
			paramVO.setBondCode(vo.getBondCode());
			/** 债券简称 */
			paramVO.setBondShortName(vo.getBondShortName());
			/** 经纪商 */
			paramVO.setBrokerType(vo.getBrokerType());
			/** 最后更新时间 */
			if (!StringUtils.isEmpty(vo.getSendTime())) {
				paramVO.setSendTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(vo.getSendTime()));
			}else{
				paramVO.setSendTime(new Date());
			}

			ObjectMapper mapper = new ObjectMapper();

			if (paramIsNotNull(vo.getBondPrice()) && paramIsNotNull(vo.getBondVol())) {
//				LOG.info(vo.getBondPrice() + "======" + vo.getBondVol());
				/** 收券 */
				paramVO.setSide(1);
				paramVO.setBondPrice(HandleNumber(vo.getBondPrice()));
				paramVO.setBondVol(HandleNumber(vo.getBondVol()));
				// bondQuoteService.saveBrokerBondQuote(paramVO);
				String params = mapper.writeValueAsString(paramVO);
				// HttpClientUtil.doPostJson(bondQuoteUrl, params);
				quoteQueue.add(params);
			}

			if (paramIsNotNull(vo.getOfrBondPrice()) && paramIsNotNull(vo.getOfrBondVol())) {
//				LOG.info(vo.getOfrBondPrice() + "======" + vo.getOfrBondVol());
				/** 出券 */
				paramVO.setSide(2);
				paramVO.setBondPrice(HandleNumber(vo.getOfrBondPrice()));
				paramVO.setBondVol(HandleNumber(vo.getOfrBondVol()));
				// bondQuoteService.saveBrokerBondQuote(paramVO);
				String params = mapper.writeValueAsString(paramVO);
				// HttpClientUtil.doPostJson(bondQuoteUrl, params);
				quoteQueue.add(params);
			}

			/** 成交价 */
			if (!StringUtils.isEmpty(vo.getBondStrikePrice())) {

				BondBrokerDealParam brokerDealParam = new BondBrokerDealParam();

				if (!paramIsNotNull(vo.getBondPrice())) {
					brokerDealParam.setBidPrice(new BigDecimal("0"));
				} else {
					brokerDealParam.setBidPrice(HandleNumber(vo.getBondPrice()));
				}

				if (!paramIsNotNull(vo.getOfrBondPrice())) {
					brokerDealParam.setOfrPrice(new BigDecimal("0"));
				} else {
					brokerDealParam.setOfrPrice(HandleNumber(vo.getOfrBondPrice()));
				}

				// if (paramIsNotNull(vo.getBondVol())) {
				brokerDealParam.setBondStrikePrice(HandleNumber(vo.getBondStrikePrice()));
				// }

				brokerDealParam.setBondCode(vo.getBondCode());

				/** 经济商 */
				brokerDealParam.setBrokerType(vo.getBrokerType());
				brokerDealParam.setBidVol(HandleNumber(vo.getBondVol()));
				brokerDealParam.setOfrVol(HandleNumber(vo.getOfrBondVol()));

				/** 最后更新时间 */
				if (!StringUtils.isEmpty(vo.getSendTime())) {
					brokerDealParam.setSendTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(vo.getSendTime()));
				}else{
					brokerDealParam.setSendTime(new Date());
				}

				String params = mapper.writeValueAsString(brokerDealParam);
				// HttpClientUtil.doPostJson(bondDealsBrokerUrl, params);
				dealQueue.add(params);
				// LOG.info(vo.getOfrBondPrice() + "======" +
				// vo.getOfrBondVol());
			}

		}

	}

	/**
	 * model转map
	 * 
	 * @param obj
	 * @return
	 */
	public static Map<String, Object> transBean2Map(Object obj) {

		if (obj == null) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();

				// 过滤class属性
				if (!key.isEmpty() && !key.equals("class")) {
					// 得到property对应的getter方法
					Method getter = property.getReadMethod();
					Object value = getter.invoke(obj);
					map.put(key, value);
				}

			}
		} catch (Exception e) {
			System.out.println("transBean2Map Error " + e);
		}

		return map;

	}

	/**
	 * 非空判断
	 * 
	 * @param str
	 * @return
	 */
	private boolean paramIsNotNull(String str) {
		if (StringUtils.isEmpty(str) || "--".equals(str) || "Ofr".equals(str) || "Bid".equals(str)) {
			return false;
		}
		return true;
	}

	/**
	 * 价格,数量处理
	 * 
	 * @param vol
	 * @return
	 */
	private BigDecimal HandleNumber(String vol) {
		BigDecimal sum = null;

		if (!StringUtils.isEmpty(vol)) {

			if (vol.indexOf("+") != -1) {
				String[] s = vol.replaceAll(" ", "").split("\\+");
				for (int i = 0; i < s.length; i++) {
					if (matcher("\\d+", s[i])) {
						if (sum == null)
							sum = new BigDecimal(0);
						sum = sum.add(new BigDecimal(s[i]));
						return sum;
					}
				}
			} else if (matcher("\\d+", vol)) {
				sum = new BigDecimal(vol);
			}

		}
		return sum;
	}

	private void sendDealLoop() throws InterruptedException {

		while (true) {
			try {
				String dealData = dealQueue.take();
				HttpClientUtil.doPostJson(bondDealsBrokerUrl, dealData);
				LOG.info("sendDeal:" + dealData);
			} catch (Exception e) {
				e.printStackTrace();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	private void sendQuoteLoop() throws InterruptedException {
		while (true) {
			try {
				String quoteData = quoteQueue.take();
				HttpClientUtil.doPostJson(bondQuoteUrl, quoteData);
				LOG.info("sendQuote:" + quoteData);
			} catch (Exception e) {
				e.printStackTrace();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}
	}


	/**
	 * 正则匹配
	 * 
	 * @param regEx
	 * @param text
	 * @return
	 */
	public static boolean matcher(String regEx, String text) {
		Pattern p = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(text);
		return m.find();
	}

}
