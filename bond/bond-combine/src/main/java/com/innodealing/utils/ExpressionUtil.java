package com.innodealing.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** 
* @author feng.ma
* @date 2017年5月26日 下午7:39:41 
* @describe:表达式工具类
*/
public class ExpressionUtil {
	
	private final static String PATTERN_FINA_INDIC = "[0-9a-zA-Z_$][a-zA-Z_$0-9]*";

	public static List<String> extractFieldsInExpression(String expression){
		List<String> feilds = new ArrayList<>();
		Pattern pattern = Pattern.compile(PATTERN_FINA_INDIC);
		Matcher matcher = pattern.matcher(expression);    
		while (matcher.find()) {
		    String feildVar = matcher.group(0) ;
		    //System.out.println("feildVar:" + feildVar);
		    feilds.add(feildVar);
		}
		return feilds;
	}
	
	/*
	public static void main(String[] args) {
		String json = "3_Fee_Ratio/Csh_Days+(28_BS102_1+BS108)*BS001";
		System.out.println("extractFields:"+extractFieldsInExpression(json));
	}
	*/
}
