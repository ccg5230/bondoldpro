package com.innodealing.bond.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.innodealing.engine.jpa.dm.BondAnnAttInfoFtpFilePathRepository;
import com.innodealing.model.dm.bond.BondAnnAttInfoFtpFilePath;

@Service
public class BondAnnAttInfoFtpFilePathService {
	
	private final static Logger LOG = LoggerFactory.getLogger(BondAnnAttInfoFtpFilePathService.class);
	
	
	
	@Value(value = "${dmms.oss.bucketName}")
    public String  bucketName;
	
	private @Autowired BondAnnAttInfoFtpFilePathRepository bondAnnAttInfoRepository;
	
	public List<BondAnnAttInfoFtpFilePath> findFtpFilePathByPublishDate(Integer id,Integer index,Integer limit){
		List<BondAnnAttInfoFtpFilePath> list = bondAnnAttInfoRepository.findFtpFilePathByPublishDate(id,index,limit);
		List<BondAnnAttInfoFtpFilePath> list1 = new ArrayList<>();
		for (BondAnnAttInfoFtpFilePath bondAnnAttInfoFtpFilePath : list) {
			bondAnnAttInfoFtpFilePath.getFtpFilePath();
			bondAnnAttInfoFtpFilePath.getAnnId();
			bondAnnAttInfoFtpFilePath.getAttType();
			bondAnnAttInfoFtpFilePath.getFileMd5();
			bondAnnAttInfoFtpFilePath.getFileName();
			bondAnnAttInfoFtpFilePath.getId();
			bondAnnAttInfoFtpFilePath.getPublishDate();
			bondAnnAttInfoFtpFilePath.getSrcUrl();
			bondAnnAttInfoFtpFilePath.getSrcUrlMd5();
			BondAnnAttInfoFtpFilePath baf = new BondAnnAttInfoFtpFilePath();
			baf.setFtpFilePath("http://"+bucketName+".oss-cn-hangzhou.aliyuncs.com"+bondAnnAttInfoFtpFilePath.getFtpFilePath());
			baf.setAnnId(bondAnnAttInfoFtpFilePath.getAnnId());
			baf.setAttType(bondAnnAttInfoFtpFilePath.getAttType());
			baf.setFileMd5(bondAnnAttInfoFtpFilePath.getFileMd5());
			baf.setFileName(bondAnnAttInfoFtpFilePath.getFileName());
			baf.setId(bondAnnAttInfoFtpFilePath.getId());
			baf.setPublishDate(bondAnnAttInfoFtpFilePath.getPublishDate());
			baf.setSrcUrl(bondAnnAttInfoFtpFilePath.getSrcUrl());
			baf.setSrcUrlMd5(bondAnnAttInfoFtpFilePath.getSrcUrlMd5());
			list1.add(baf);
		}
		return list1;
	}
	
	public int getFtpFilePathCount(){
		
		return bondAnnAttInfoRepository.getFtpFilePathCount();
		
	}
	

}
