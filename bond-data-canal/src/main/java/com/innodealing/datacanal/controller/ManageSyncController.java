package com.innodealing.datacanal.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.innodealing.datacanal.service.DmSyncService;
import com.innodealing.datacanal.util.BondDataTableMapUtil;
import com.innodealing.datacanal.vo.DataCompareVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 
 * <p>同步管理控制器
 * @author 赵正来
 *
 */

@RestController
@RequestMapping("api/datacanal/")
@Api(tags = "同步管理控制器")
public class ManageSyncController{
	
	@Autowired private DmSyncService dmSyncService;
	
	
	@ApiOperation(value="查看同步结果")
	@ResponseBody
	@RequestMapping(value = "/sync/result", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<DataCompareVo> > syncResult() throws Exception{
		List<DataCompareVo>  result = dmSyncService.viewSyncResult();
		return new ResponseEntity<List<DataCompareVo>>(result,HttpStatus.OK);
	}
	
	@ApiOperation(value="同步部分数据")
	@ResponseBody
	@RequestMapping(value = "/sync/tables", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<String > syncData(@ApiParam(name = "tables", value = "需要同步的源数据表信息[冒号表示同步的其实位置，CREATETIME正序排列]<p>，多个用，号隔开。例如：table:0")  @RequestParam String tables) throws Exception{
		String msg = "已开始同步，可以通过同步接口查看同步结果[/sync/result]";
		if(tables != null){
			Map<String,Integer> offsets = new HashMap<>();
			String[] tbs = tables.split(",");
			String[] srcTables = new String[tbs.length];
			for (int i = 0; i < tbs.length; i++) {
				String tableName = tbs[i].toUpperCase();
				String[] tableNameArr = tableName.split(":");
				if(tableNameArr.length != 2 ){
					msg = "参数格式不对!";
					break;
				}
				srcTables[i] = tableNameArr[0];
				offsets.put(tableNameArr[0], Integer.valueOf(tableNameArr[1]));
			}
			dmSyncService.syncData(srcTables, offsets);
		}else{
			msg = "tables can not be null!";
		}
		return new ResponseEntity<String>(msg,HttpStatus.OK);
	}
	
	@ApiOperation(value="更新所有status状态为未删除")
	@ResponseBody
	@RequestMapping(value = "/sync/updateStatusNoDelete", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<String > updateStatusNoDelete() throws Exception{
		String msg = "已开始同步，可以通过同步接口查看同步结果[/sync/result]";
		dmSyncService.updateStatusNoDelete();
		return new ResponseEntity<String>(msg,HttpStatus.OK);
	}
	
	@ApiOperation(value="修正错误数据")
	@ResponseBody
	@RequestMapping(value = "/sync/correction/all", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<String > syncFinance() throws Exception{
		String msg = "已开始同步，可以通过同步接口查看同步结果[/sync/result]";
		dmSyncService.chekErrorData();
		return new ResponseEntity<String>(msg,HttpStatus.OK);
	}
	
	@ApiOperation(value="同步丢失数据")
	@ResponseBody
	@RequestMapping(value = "/sync/missing", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<String > sysnMissing(@ApiParam(name = "tables", value = "需要同步的源数据表信息[多个用，号隔开。例如：table1,table2")  @RequestParam String tables) throws Exception{
		String msg = "正在同步";
		if(tables != null){
			
			dmSyncService.chechMissAndMakeUp(tables.split(";"));
		}else{
			msg = "tables can not be null !";
		}
		return new ResponseEntity<String>(msg,HttpStatus.OK);
	}
	
	
	@ApiOperation(value="同步所有数据")
	@ResponseBody
	@RequestMapping(value = "/sync/all", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<String > syncData() throws Exception{
		String msg = "开始同步，可以通过同步接口查看同步结果[/sync/result]。同步的表信息：" + BondDataTableMapUtil.getSrcToDestTableVo().toString();
		dmSyncService.syncAllData();
		return new ResponseEntity<String>(msg,HttpStatus.OK);
	}
	
	@ApiOperation(value="开启canal实时同步")
	@ResponseBody
	@RequestMapping(value = "/sync/canal", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<String > syncCanal() throws Exception{
		String msg = "开始同步，可以通过同步接口查看同步结果[/sync/result]。同步的表信息：" + BondDataTableMapUtil.getSrcToDestTableVo().toString();
		dmSyncService.syncCanal();
		return new ResponseEntity<String>(msg,HttpStatus.OK);
	}
	
	/*@ApiOperation(value="关闭canal实时同步")
	@ResponseBody
	@RequestMapping(value = "/sync/canal/stop", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<String > stopCanal() throws Exception{
		String msg = "关闭";
		dmSyncService.syncCanal();
		return new ResponseEntity<String>(msg,HttpStatus.OK);
	}*/
	/*
	@ApiOperation(value="查看canal实时同步状态")
	@ResponseBody
	@RequestMapping(value = "/sync/canal/status", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Map<String,Boolean> > syncCanalstatus() throws Exception{
		
		Map<String,Boolean> result = dmSyncService.syncCanalstatus();
		
		return new ResponseEntity<Map<String,Boolean>>(result,HttpStatus.OK);
	}*/
	
	@ApiOperation(value="数据同步源数据表的唯一索引字段")
	@ResponseBody
	@RequestMapping(value = "/sync/tables/unique", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Map<String,List<String>> > tablesUnique() throws Exception{
		Map<String,List<String>> result = new HashMap<>();
		BondDataTableMapUtil.getSrcToDestTableVo().forEach(srcDest -> {
			result.put(srcDest.getSrcTableName(), BondDataTableMapUtil.getSks(srcDest.getSrcTableName())) ;
		});
		return new ResponseEntity<Map<String,List<String>>>(result,HttpStatus.OK);
	}
	
	/*public JsonResult<String > calculate(
			@ApiParam(name = "issuerId", value = "主体|发行人id")  @RequestParam Long issuerId, 
			@ApiParam(name = "finDate", value = "财报日期") @RequestParam(required = false) String finDate,
			@RequestHeader("userid") long userid) throws Exception{
		FinSpclindicatorJson json = new FinSpclindicatorJson();
		json.setComUniCode(issuerId);
		json.setFinDate(finDate);
		bondAmqpSender.send(json);
		return new JsonResult<String>().ok("success");
	}*/
	
}
