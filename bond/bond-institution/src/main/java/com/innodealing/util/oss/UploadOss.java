package com.innodealing.util.oss;

import java.io.ByteArrayInputStream;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;

public class UploadOss {

	// endpoint以杭州为例，其它region请按实际情况填写
	private String endpoint;// = "http://oss-cn-hangzhou.aliyuncs.com";
	// accessKey请登录https://ak-console.aliyun.com/#/查看
	private String accessKeyId; // = "wPeNH8QSEy9C9PJ4";
	private String accessKeySecret;// = "HSwDxB4WE7aIrx77hywKdjVr1nOhe8";
	private String bucketName;// = "simpletech";

	/**
	 * simple upload file
	 * 
	 * @param key
	 *            文件key
	 * @param file
	 *            文件字节数组
	 * @param contentType
	 *            文件类型
	 */
	public String upload(String key, byte[] file, String contentType) throws Exception {
		// 创建上传Object的Metadata
		ObjectMetadata meta = new ObjectMetadata();
		System.out.println(bucketName);
		// 设置上传文件长度
		// meta.setContentLength(34);
		// 设置上传MD5校验
		// String md5 =
		// BinaryUtil.toBase64String(BinaryUtil.calculateMd5(file.getBytes()));
		// meta.setContentMD5(md5);
		// 设置上传内容类型
		if (contentType != null) {
			meta.setContentType(contentType);
			//指定该Object被下载时的内容编码格式
			meta.setContentEncoding("utf-8");
		}
		// 创建OSSClient实例
		OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		// ossClient.getu
		// 上传文件
		Long start = System.currentTimeMillis();
		ossClient.putObject(bucketName, key, new ByteArrayInputStream(file), meta);
		Long end = System.currentTimeMillis();
		System.out.println("upload time:" + (end - start));
		// 关闭client
		ossClient.shutdown();
		return endpoint.replace("//", "//" + bucketName + ".") + "/" + key;
	}

	public OSSObject download(String key) {
		// 创建OSSClient实例
		OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		OSSObject ossObject = ossClient.getObject(bucketName, key);
		// 关闭client
		ossClient.shutdown();
		return ossObject;
	}

	private UploadOss() {
		super();
	}

	private UploadOss(String endpoint, String accessKeyId, String accessKeySecret, String bucketName) {
		super();
		this.endpoint = endpoint;
		this.accessKeyId = accessKeyId;
		this.accessKeySecret = accessKeySecret;
		this.bucketName = bucketName;
	}

	private static UploadOss instance = null;

	public static UploadOss newInstance(String endpoint, String accessKeyId, String accessKeySecret,
			String bucketName) {
		if (instance == null) {
			synchronized (UploadOss.class) {
				if (instance == null) {
					instance = new UploadOss(endpoint, accessKeyId, accessKeySecret, bucketName);
				}
			}
		}
		return instance;
	}

}
