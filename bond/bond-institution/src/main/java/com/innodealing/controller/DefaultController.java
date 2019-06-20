package com.innodealing.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import io.swagger.annotations.Api;

/**
 * 此处为测试用
 * 
 * @author 戴永杰
 * @date 2017年6月1日 下午5:02:55
 * @version V1.0
 */

@Api(description = "默认控制器")
@RestController
public class DefaultController extends BaseController{
	
	@RequestMapping("")
	public ModelAndView index() {
		return new ModelAndView("redirect:/swagger-ui.html");
	}

}
