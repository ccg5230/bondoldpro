package com.innodealing.datacanal.service;

import java.net.InetSocketAddress;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.common.utils.AddressUtils;

/**
 * 单机模式
 * 
 */

public class CanalStarterClient extends CanalClientDm {

	
	
    public CanalStarterClient(String destination){
        super(destination);
    }

    private String hostIp = "192.168.8.221";
    
    public void setHostIp(String hostIp){
    	this.hostIp = hostIp;
    }


	public void run(){
    	   // 根据ip，直接创建链接，无HA的功能
        if(this.destination == null){
        	logger.error("destination can not be null!");
        	return;
        }
        String ip = AddressUtils.getHostIp();
        CanalConnector connector = CanalConnectors.newSingleConnector(new InetSocketAddress(this.hostIp, 11111),
            this.destination,
            "root",
            "root");

        CanalStarterClient clientTest = new CanalStarterClient(destination);
        clientTest.setConnector(connector);
        clientTest.start();
        Runtime.getRuntime().addShutdownHook(new Thread() {

            public void run() {
                try {
                    logger.info("## stop the canal client");
                    clientTest.stop();
                } catch (Throwable e) {
                    logger.warn("##something goes wrong when stopping canal:", e);
                } finally {
                    logger.info("## canal client is down.");
                }
            }

        });
    }
	
	public boolean getStatus(){
		return super.running;
	}

}
