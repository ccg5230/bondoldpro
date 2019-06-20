package com.innodealing.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.innodealing.domain.JsonResult;
import com.innodealing.model.BuildResult;
import com.innodealing.service.InduService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description = "构建债券违约概率和信誉评级接口")
@RestController
@RequestMapping("api/bond/tasks/")
public class BondPdRatingController{
    @Autowired
    private InduService induService;
    
    @ApiOperation(value = "构建债券违约概率和信誉评级接口-[zzl]")
    @RequestMapping(value = "/pdAndRating", method = RequestMethod.POST, produces = "application/json")
    public JsonResult<List<BuildResult>> build() {
        return new JsonResult<List<BuildResult>>()
                .ok(induService.build());
    }

}
