package com.innodealing.oss;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
@Configuration
@Component
public class UploadOss {
    
    private static final Logger log = LoggerFactory.getLogger(UploadOss .class);

    // endpoint以杭州为例，其它region请按实际情况填写
    @Value("${dmms.oss.endpoint}")
    public String endpoint;// = "http://oss-cn-hangzhou.aliyuncs.com";
    // accessKey请登录https://ak-console.aliyun.com/#/查看
    @Value("${dmms.oss.accessKeyId}")
    public String accessKeyId;
    @Value("${dmms.oss.accessKeySecret}")
    public String accessKeySecret;
    @Value("${dmms.oss.bucketName}")
    public String bucketName ;
    
    private OSSClient ossClient = null;

    /**
     * simple upload file
     * 
     * @param key
     *            文件key
     * @param file
     *            文件字节数组
     * @param contentType
     *            文件类型
     * @param modulePath
     *            文件模块路径(区分文件所属模块)例如：user/img
     */
    public String upload(Long id,String key,byte[] file, String contentType,String modulePath) throws Exception {
        // 创建上传Object的Metadata
        ObjectMetadata meta = new ObjectMetadata();
        if (contentType != null) {
            meta.setContentType(contentType);
        }
        // 创建OSSClient实例
        ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        // 上传文件
        Long start = System.currentTimeMillis();
        ossClient.putObject(bucketName,  modulePath + "/" + key, new ByteArrayInputStream(file), meta);
        Long end = System.currentTimeMillis();
        log.info("uploaded id="+id+", with time:" + (end - start) +" ms");
        return endpoint.replace("//", "//" + bucketName + ".") + "/" + modulePath + "/" + key;
    }
    private static  UploadOss instance = null;

    public static UploadOss newInstance() {
        if(instance == null){
            synchronized (UploadOss.class) {
                if(instance == null){
                    instance = new UploadOss();
                }
            }
        }
        return instance;
    }

    public UploadOss() {
		super();
	}


    /**
     * 下载Oss文件，获取完流后要调用closeOss方法
     * @param ossClient
     * @param fileNameKey:带路径
     * @return  
     */
	public OSSObject download(String fileNameKey) {
        // 创建OSSClient实例
		ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        OSSObject ossObject = ossClient.getObject(bucketName, fileNameKey);
        return ossObject;
    }
	
	
	public void closeOss() {
		if(null != ossClient) {
			ossClient.shutdown();
		}
	}
    
    public static byte[] fileToBytes(String filePath) {
        byte[] buffer = null;
        File file = new File(filePath);
        
        FileInputStream fis = null;
        ByteArrayOutputStream bos = null;

        try {
            fis = new FileInputStream(file);
            bos = new ByteArrayOutputStream();

            byte[] b = new byte[1024];

            int n;

            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            
            buffer = bos.toByteArray();
        } catch (FileNotFoundException ex) {
            //Logger.getLogger(JmsReceiver.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
           // Logger.getLogger(JmsReceiver.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (null != bos) {
                    bos.close();
                }
            } catch (IOException ex) {
               // Logger.getLogger(JmsReceiver.class.getName()).log(Level.SEVERE, null, ex);
            } finally{
                try {
                    if(null!=fis){
                        fis.close();
                    }
                } catch (IOException ex) {
                    //Logger.getLogger(JmsReceiver.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        return buffer;
    }
//    public static void main(String[] args) throws Exception {
//    	String filename="D:\\2017年第二期连云港恒驰实业有限公司公司债券簿记建档发行结果公告 (1).pdf";
//    	//String aString = "1";
//    	
//    	File f =new File(filename); 
//    	
//    	//new FileReader(file)
//    	byte[] byt = new byte[new FileInputStream(f).available()];
//    	String asd = UploadOss.upload( "P020170908342684521899.pdf",byt , null, "test/pdf");
//    	System.out.println("======"+asd);
//	}
}
