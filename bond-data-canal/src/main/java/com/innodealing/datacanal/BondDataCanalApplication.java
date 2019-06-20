package com.innodealing.datacanal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.innodealing.datacanal.dao.BondComUniCodeMapDao;
import com.innodealing.datacanal.dao.BondDataCanalLogDao;
import com.innodealing.datacanal.dao.BondIssuerDao;
import com.innodealing.datacanal.dao.BondUniCodeMapDao;
import com.innodealing.datacanal.service.CanalClientDm;
import com.innodealing.datacanal.service.CanalStarterClient;
import com.innodealing.datacanal.service.DataCanalService;
import com.innodealing.datacanal.service.DmSyncService;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@RestController
@EnableSwagger2
public class BondDataCanalApplication {
	
	private static CanalStarterClient canalStarterClientBondCCxe = new CanalStarterClient("bond_ccxe");
	private static CanalStarterClient canalStarterClientBondCCxeDev = new CanalStarterClient("bond_ccxe_dev");
	private static CanalStarterClient canalStarterClientDmdb = new CanalStarterClient("dmdb");
	
	
	//private @Autowired DmSyncService dmSyncService;
	public static ConfigurableApplicationContext context;
	public static void main(String[] args) {
		context = SpringApplication.run(BondDataCanalApplication.class, args);
		initCanalClientDm(context);
		//设置canal 服务端ip
		String hostIp = context.getBean(Environment.class).getProperty("canal.server.host");
//		canalStarterClientBondCCxe.setHostIp(hostIp);
//		canalStarterClientBondCCxe.run();
		
//		canalStarterClientBondCCxeDev.setHostIp(hostIp);
//		canalStarterClientBondCCxeDev.run();
//		
//		canalStarterClientDmdb.setHostIp(hostIp);
//		canalStarterClientDmdb.run();
		
		
		DmSyncService dmSyncService = context.getBean(DmSyncService.class);
		//dmSyncService.viewSyncResult();
		//dmSyncService.checkMissData();
		
	}
	
	public static void initCanalClientDm(ConfigurableApplicationContext context){
		if (CanalClientDm.dataCanalService == null) {
			CanalClientDm.dataCanalService = context.getBean(DataCanalService.class);
		}
//		if (dmdbJdbcTemplate == null) {
//			dmdbJdbcTemplate = (JdbcTemplate) BondDataCanalApplication.context.getBean("dmdbJdbcTemplate");
//		}
		if (CanalClientDm.bondComUniCodeMapDao == null) {
			CanalClientDm.bondComUniCodeMapDao = context.getBean(BondComUniCodeMapDao.class);
		}
		if (CanalClientDm.bondUniCodeMapDao == null) {
			CanalClientDm.bondUniCodeMapDao = context.getBean(BondUniCodeMapDao.class);
		}
		if (CanalClientDm.bondIssuerDao == null) {
			CanalClientDm.bondIssuerDao = context.getBean(BondIssuerDao.class);
		}
		if (CanalClientDm.bondDataCanalLogDao == null) {
			CanalClientDm.bondDataCanalLogDao = context.getBean(BondDataCanalLogDao.class);
		}
		
	}
	
	@RequestMapping({"/",""})
	public ModelAndView home(){
		return new ModelAndView(new RedirectView("swagger-ui.html"));
	}
}
