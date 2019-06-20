package com.innodealing.util.security;

import java.security.MessageDigest;

/**
 * @author: zhufei
 * @date: 2015-1-8 上午11:39:30
 * @version 1.0
 * @descr
 */
public class MD5Util {

	public static String getMD5(String inStr) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			return "";
		}
		char[] charArray = inStr.toCharArray();
		byte[] byteArray = new byte[charArray.length];

		for (int i = 0; i < charArray.length; i++)
			byteArray[i] = (byte) charArray[i];

		byte[] md5Bytes = md5.digest(byteArray);

		StringBuffer hexValue = new StringBuffer();

		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}

		return hexValue.toString();
	}

	// 可逆的加密算法
	public static String KL(String inStr) {
		// String s = new String(inStr);
		char[] a = inStr.toCharArray();
		for (int i = 0; i < a.length; i++) {
			a[i] = (char) (a[i] ^ 't');
		}
		String s = new String(a);
		return s;
	}

	// 加密后解密
	public static String JM(String inStr) {
		char[] a = inStr.toCharArray();
		for (int i = 0; i < a.length; i++) {
			a[i] = (char) (a[i] ^ 't');
		}
		String k = new String(a);
		return k;
	}

	public static void main(String[] args) {

		String s = new String("123123");
		System.out.println("原始：" + s);
		System.out.println("MD5后：" + getMD5("selene"));
//		System.out.println("MD5后再加密：" + KL(getMD5(s)));
//		System.out.println("解密为MD5后的：" + JM(KL(s)));
//		System.out.println("加密的：" + KL(s));
//		System.out.println("解密的：" + JM("918257c822018220efcb53706232fe37"));
	}

}
