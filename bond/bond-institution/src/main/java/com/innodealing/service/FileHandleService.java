package com.innodealing.service;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.innodealing.dao.BondInstRatingFileDao;
import com.innodealing.dao.QuoteFileMappingDao;
import com.innodealing.model.mysql.BondInstRatingFile;
import com.innodealing.model.mysql.QuoteFileMapping;
import com.innodealing.util.MD5Util;
import com.innodealing.util.oss.UploadOss;

@Service
public class FileHandleService {

	private @Autowired QuoteFileMappingDao fileMappingDao;

	@Value("${config.define.internalrating.filecode}")
	private String fileCode;
	@Value("${config.define.endpoint}")
	private String endpoint;
	@Value("${config.define.accessKeyId}")
	private String accessKeyId;
	@Value("${config.define.accessKeySecret}")
	private String accessKeySecret;
	@Value("${config.define.bucketName}")
	private String bucketName;

	private final static Logger LOGGER = LoggerFactory.getLogger(FileHandleService.class);

	public static final String SUFFIX_2007 = ".xlsx";
	

	/**
	 * 文件上传
	 * 
	 * @param file
	 * @param fileName
	 * @param contentType
	 * @return
	 * @throws Exception
	 */
	public String uploadFileOss(String tokenCode, byte[] file, String fileName, String contentType) throws Exception {
		if (!fileCode.equals(tokenCode)) {
			return "tokenCode不正确";
		}
		
		int lastIndex = fileName.lastIndexOf(".");
		String ossKey = MD5Util.getMD5(fileCode) + fileName.substring(lastIndex, fileName.length());
		LOGGER.info("oss info--->" + "{ossKey:" + ossKey + ",endpoint:" + endpoint + ",accessKeyId:" + accessKeyId
				+ ",accessKeySecret:" + accessKeySecret + ",bucketName:" + bucketName + "}");
//		String filePath = UploadOss.newInstance(endpoint, accessKeyId, accessKeySecret, bucketName).upload(ossKey, file,
//				contentType);
		//使用文件名做路径
		String filePath = UploadOss.newInstance(endpoint, accessKeyId, accessKeySecret, bucketName).upload(fileName, file,
				contentType);
		int res = saveFileMapping(fileName, ossKey, filePath);

		if (res <= 0) {
			return null;
		}
		return filePath;
	}

	private int saveFileMapping(String fileName, String ossKey, String filePath) {
		QuoteFileMapping filemapping = new QuoteFileMapping();
		filemapping.setFileName(fileName);
		filemapping.setOssKey(ossKey);
		filemapping.setUpdateTime(new Date());
		filemapping.setFilePath(filePath);
		filemapping.setStatus(1);
		int result = fileMappingDao.save(filemapping);
		return result;
	}

	private String generateDefauleOssKey() {
		return MD5Util.getMD5(fileCode) + SUFFIX_2007;
	}

	public String getFilepath() {
		String filePath = "";
		QuoteFileMapping fileMapping = fileMappingDao.queryByOssKey(generateDefauleOssKey());
		if (null != fileMapping) {
			filePath = fileMapping.getFilePath();
		}
		return filePath;
	}
	
	/**
	 * 文件上传
	 * 
	 * @param file
	 * @param fileName
	 * @param contentType
	 * @return
	 * @throws Exception
	 */
	public BondInstRatingFile uploadFileOss(Integer userid,Integer type,byte[] file, String fileName, String contentType) throws Exception {
		int lastIndex = fileName.lastIndexOf(".");
		String ossKey = MD5Util.getMD5(fileCode) + fileName.substring(lastIndex, fileName.length());
		LOGGER.info("oss info--->" + "{ossKey:" + ossKey + ",endpoint:" + endpoint + ",accessKeyId:" + accessKeyId
				+ ",accessKeySecret:" + accessKeySecret + ",bucketName:" + bucketName + "}");
		//使用文件名做路径
		String filePath = UploadOss.newInstance(endpoint, accessKeyId, accessKeySecret, bucketName).upload(fileName, file,
				contentType);
		
		BondInstRatingFile bondInstRatingFile = new BondInstRatingFile();
		bondInstRatingFile.setCreateBy(userid);
		bondInstRatingFile.setUpdateBy(userid);
		bondInstRatingFile.setType(type);
		bondInstRatingFile.setFileName(fileName);
		bondInstRatingFile.setFilePath(filePath);
		bondInstRatingFile.setOssKey(ossKey);
		
		return bondInstRatingFile;
	}
	
	
	

}
