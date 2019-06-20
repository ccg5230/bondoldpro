package com.innodealing.util.security;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 签名
 * @author zhaozhenglai
 * @since 2016年12月7日 下午4:51:49 
 * Copyright © 2016 DealingMatrix.cn. All Rights Reserved.
 */
public class Signature {
    public static void main(String[] args) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String stringToSign = "name=tom,age=2";
        MessageDigest sha = MessageDigest.getInstance("SHA-1");
        String key = "1234567890";
        //String basedText = Base64Utils.encodeToString(sha.digest(stringToSign.getBytes()));
        
        String d = "";
        //java.security.Signature.getInstance(algorithm)
        //System.out.println(basedText.toUpperCase());
        byte[] in = stringToSign.getBytes("utf-8");
        //sha.update(in);
        byte[] hash = sha.digest(in);
        for (int i = 0; i < hash.length; i++) {
            int v = hash[i] & 0xff;
            if(v < 16){
                d += "0";
            }
            d += Integer.toString(v, 16);
        }
        //7e639dc7bc105b3e849fce276267b942a264484d
        System.out.println(d);
        System.out.println(byte2hex(hash));
    }
    
    /**
     * 字符加密
     * @param stringToSign 签名前的字符串
     * @param key  用户更新摘要的key
     * @return 加密后的sign
     */
    public String sign(String stringToSign, String key){
        if(stringToSign == null || stringToSign.length() == 0){
            return "";
        }
        String sign = "";
        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            byte[] in = stringToSign.getBytes("utf-8");
            byte[] keyIn = key.getBytes("utf-8");
            sha.update(keyIn);
            byte[] hash = sha.digest(in);
            sign = byte2hex(hash);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return sign;
    }
    
    
    /**
     * 将二进制转化为16进制字符串
     * 
     * @param b 二进制字节数组
     * @return String
     */
    public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }
        return hs.toUpperCase();
    }
}


