package com.innodealing.service;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.innodealing.amqp.SenderService;
import com.innodealing.controller.BaseController;
import com.innodealing.domain.JsonResult;
import com.innodealing.engine.jdbc.bond.BondAnnAttInfoDao;
import com.innodealing.model.dm.bond.BondClassOneAnnAttInfo;
import com.innodealing.model.dm.bond.ccxe.BondDAnnMain;
import com.innodealing.oss.UploadOss;
import com.innodealing.util.DateUtils;
//import com.innodealing.util.JayCommonUtil;

@Service
public class BondAnnAttInfoService extends BaseController {
    private final static Logger log = LoggerFactory.getLogger(BondAnnAttInfoService.class);
    public static String ATT_FILE_DOWNLOAD_JSP = "http://www.shclearing.com/wcm/shch/pages/client/download/download.jsp";
   
    /** 中诚信附件ftp服务器配置参数*/
    @Value("${bond.ccxe.ftp.hostip}")
    public String FTP_HOST;
    @Value("${bond.ccxe.ftp.username}")
    public String FTP_LOGIN_USER;
    @Value("${bond.ccxe.ftp.password}")
    public String FTP_LOGIN_PASSWORD;
    /** 附件存放首地址 */
    @Value("${bond.ccxe.ftp.prepath}")
    public String FTP_PRE_PATH;
    public static int FTP_PORT = 21;
    public static String FTP_PARSER = "com.innodealing.config.UnixFTPEntryParser";
   
    public static int FTP_CONN_TIME_OUT = 6 * 60 *1000;//FTP链接超时
    /** 本地字符编码 */
    private static String LOCAL_CHARSET = "GBK";
    
    @Autowired
    private BondAnnAttInfoDao bondAnnAttInfoDao;
    
    @Autowired
    private UploadOss uploadOss;
    
    @Autowired
    private SenderService senderService;
    
    public JsonResult<String> sendAttInfo2Mq() {
        List<BondClassOneAnnAttInfo> attList = bondAnnAttInfoDao.queryByUrl();
        if(attList != null && attList.size()>0){
//            List<List<BondClassOneAnnAttInfo>> spList =JayCommonUtil.splitList(attList, 10);
//            for(List<BondClassOneAnnAttInfo> list :spList) {//10个一个消息mq acked会超时，改为单个上传
//                StringBuffer sb = new StringBuffer();
                for(BondClassOneAnnAttInfo att :attList) {
//                    sb.append(att.getId()).append(",");
                    senderService.sendNewBondAtt2RabbitMQ(att.getId()+"");
                }
//                sb.deleteCharAt(sb.length() - 1);
//                senderService.sendNewBondAtt2RabbitMQ(sb.toString());
            }
//        }
        return new JsonResult<String>().ok("success");
    }
    
    public JsonResult<String> bondUploadOss(String quotJson) {
        if(null != quotJson) {
              if(!StringUtils.isEmpty(quotJson)) {
                List<BondClassOneAnnAttInfo> attList = bondAnnAttInfoDao.queryUnUploadAtt(quotJson);
                if(attList != null && attList.size()>0){
                    ThreadPoolExecutor pool = new ThreadPoolExecutor(1, 10, 10,TimeUnit.MINUTES, new ArrayBlockingQueue<Runnable>(attList.size())); 
                    for (final BondClassOneAnnAttInfo attInfo : attList) {
                        pool.execute(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        uploadSingleFile(attInfo);
                                    }
                                }
                            );
                    }
                    try {
                        // 关闭线程池:会等待所有线程执行完
                        pool.shutdown();
                        pool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
                    } catch (InterruptedException e) {
                        log.error("MQ bondUploadOss threadPool InterruptedException error:"+e.getMessage(),e);
                    } finally {
                        uploadOss.closeOss();
                    }
                }
            }
        }
        return new JsonResult<String>().ok("success");
    }
    
    public JsonResult<String> bondUploadOss() {
        List<BondClassOneAnnAttInfo> url = bondAnnAttInfoDao.queryByUrl();
        if(url != null && url.size()>0){
        	ThreadPoolExecutor pool = new ThreadPoolExecutor(5, 10, 10,TimeUnit.MINUTES, new ArrayBlockingQueue<Runnable>(url.size())); 
        	for (final BondClassOneAnnAttInfo attInfo : url) {
    			pool.execute(
    					new Runnable() {
    						@Override
    						public void run() {
    							uploadSingleFile(attInfo);
    						}
    					}
    				);
        	}
            try {
            	// 关闭线程池:会等待所有线程执行完
                pool.shutdown();
				pool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
			} catch (InterruptedException e) {
			    log.error("bondUploadOss threadPool InterruptedException error:"+e.getMessage(),e);
			} finally {
				uploadOss.closeOss();
			}

        }
        return new JsonResult<String>().ok("success");
    }
    
    public JsonResult<String> deleteUploadFailedAttDatas() {
        JsonResult<String>  rs = new JsonResult<String>().ok("success");
        try {
            bondAnnAttInfoDao.deleteUploadFailedAttDatas();
        } catch (Exception e) {
            log.error("deleteUploadFailedAttDatas error:"+e.getMessage(),e);
            rs = new JsonResult<String>("-1", "deleteUploadFailedAttDatas failed", null);
        } 
        return rs;
    }
    
    private void uploadSingleFile(BondClassOneAnnAttInfo attInfo) {
        String ftp_file_path = "";
    	try {
    		String type = attInfo.getAttType();
        	String srcUrl = attInfo.getSrcUrl();
            Date date = attInfo.getPublishDate();
            String publishDate = DateUtils.convert2String(date, DateUtils.YYYY_MM_DD_HH_MM_SS);
            String stype = null;
            if(type.equals("pdf")){
            	stype = "application/pdf";
            }if(type.equals("doc")){
            	stype = "application/msword";
            }if(type.equals("docx")){
            	stype = "application/msword";
            }
            String pathnames = publishDate.substring(0, 10);
            String times = pathnames.replace("-", "");
            String pathname = "bond_attr/" + times;
            String originfilename = null;
            String filename = null;
            boolean a = srcUrl.startsWith("http");
            Long start = System.currentTimeMillis();
            Long end = System.currentTimeMillis();
            if (a == false) {
                originfilename = ATT_FILE_DOWNLOAD_JSP + "?FileName=" + srcUrl + "&DownName=tem";
                filename = originfilename.substring(originfilename.indexOf("=") + 1, originfilename.indexOf("&"));
                ResponseEntity<byte[]> res = new RestTemplate().postForEntity(originfilename, null, byte[].class, new HashMap<>());
                byte[] byt = res.getBody();
                end = System.currentTimeMillis();
                log.info(" get upload file byte id="+attInfo.getId()+", time:" + (end - start));
                upload(attInfo.getId(),filename, byt, stype, pathname);
                ftp_file_path = "/bond_attr/" + times + "/" + filename;
            } else {
                originfilename = srcUrl;
                filename = originfilename.substring(originfilename.lastIndexOf("/") + 1);
                URL urs = new URL(originfilename);
            	HttpURLConnection conn = (HttpURLConnection) urs.openConnection();
            	conn.setConnectTimeout(600*1000);
            	conn.setReadTimeout(600*1000); 
            	conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            	InputStream in = conn.getInputStream();
            	ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
            	byte[] buff = new byte[2048];  
                int rc = 0;  
                while ((rc = in.read(buff, 0, buff.length)) > 0) {  
                    swapStream.write(buff, 0, rc);  
                }  
                byte[] byt = swapStream.toByteArray();
                swapStream.close();
                conn.disconnect();//关闭底层连接的socket连接，防止对方限制ip连接数，报Server returned HTTP response code: 503
                end = System.currentTimeMillis();
                log.info(" get upload file byte id="+attInfo.getId()+", time:" + (end - start));
                upload(attInfo.getId(),filename, byt, stype, pathname);
                ftp_file_path = "/bond_attr/" + times + "/" + filename;
            }
    	}
    	catch (Exception e){
    		log.error("uploadSingleFile error "+e.getMessage()+" : id ="+attInfo.getId(),e);
    	}
    	
        try {
            attInfo.setFtpFilePath(ftp_file_path);
            attInfo.setUploadTimes(attInfo.getUploadTimes()+1);
            attInfo.setLastUpdateTime(new Date());
            bondAnnAttInfoDao.updateFtpFilePath(attInfo);
        } catch (Exception e) {
            log.error("uploadSingleFile 更新数据库失败 id ="+attInfo.getId());
        }
    	
    }
    
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
    private String upload(Long id,String key,byte[] file, String contentType,String modulePath) throws Exception {
        // 创建上传Object的Metadata
        ObjectMetadata meta = new ObjectMetadata();
        if (contentType != null) {
            meta.setContentType(contentType);
        }
        return uploadOss.upload(id,key,file,contentType,modulePath);
    }
    
    /*
     * 下载新债公告附件
     */
    public JsonResult<String> downloadAttInfo(Long id, HttpServletRequest request, HttpServletResponse response) {
        String result = "success";
        byte[] buffer = null;
        InputStream ins = null;
        ByteArrayOutputStream byteOut = null;
        try {
            BondClassOneAnnAttInfo att = bondAnnAttInfoDao.queryAttInfoById(id);
            String ftpFilePath = att.getFtpFilePath();
            if (StringUtils.isEmpty(ftpFilePath)) {
                return new JsonResult<String>().ok("文件路径为空");
            }
            
            String downfileName = att.getFileName();
            String ftpFileName = ftpFilePath.substring(ftpFilePath.lastIndexOf("/") + 1);
            //下载文件名称代码.类型
            if(StringUtils.isEmpty(downfileName)) {
                downfileName = ftpFileName;
            } else {
                if(downfileName.lastIndexOf(".") == -1) {
                    if(ftpFileName.lastIndexOf(".") != -1) {
                        downfileName = downfileName + ftpFileName.substring(ftpFileName.lastIndexOf("."));
                    }
                }
            }
            if(downfileName.lastIndexOf(".") == -1) {
                downfileName = downfileName +".pdf";
            }
            // 在这里加入设置Cookie ————-
            Cookie fileDownload = new Cookie("fileDownload", "true");
            fileDownload.setPath("/");
            response.addCookie(fileDownload);
            
            
            //OSS key名称在使用UTF-8编码后长度必须在 1-1023字节之间，而且不能包含回车、换行、以及xml1.0不支持的字符，同时也不能以“/”或者“\”开头。
            OSSObject oSSObject = uploadOss.download(ftpFilePath.substring(1));
            ins = oSSObject.getObjectContent();
            byteOut = new ByteArrayOutputStream();
            byte[] buf = new byte[4096];
            int bufsize = 0;
            while ((bufsize = ins.read(buf)) != -1) {
            	byteOut.write(buf, 0, bufsize);
            }
            buffer = byteOut.toByteArray();
            
            if (null == buffer || buffer.length == 0) {
                log.error("downFtpFile read file " + ftpFileName + " falied, id=" + id);
            }
            // 清空response
            response.reset();
            response.setCharacterEncoding("GBK");// 设置服务器端的编码
            response.setHeader("contentType", "text/html; charset=GBK");// 通知浏览器服务器发送的数据格式
            response.setContentType("text/html;charset=GBK");// 通知浏览器服务器发送的数据格式
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(downfileName, "UTF-8"));
            response.setContentType("application/octet-stream");

            OutputStream os = new BufferedOutputStream(response.getOutputStream());
            os.write(buffer);
            os.flush();
            os.close();
        } catch(EmptyResultDataAccessException empty) {
            result = "数据库没有附件记录";
        }catch (Exception e) {
            result = "failed";
        } finally {
        	if(null != byteOut) {
        		try {
					byteOut.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
        	}
        	if(null != ins) {
        		try {
					ins.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
        	}
        	uploadOss.closeOss();
        }
        return new JsonResult<String>("success".equalsIgnoreCase(result)? "0":"1",result,null);

    }
    
    /*
     * 下载债券主体评级公告附件
     */
    public JsonResult<String> downloadComRatingAttInfo(Long annId, HttpServletRequest request, HttpServletResponse response) {
        String result = "success";
        try {
            BondDAnnMain att = bondAnnAttInfoDao.queryComRatingAtt(annId);
            String ftpFilePath = att.getFilePath();
            if (StringUtils.isEmpty(ftpFilePath)) {
                return new JsonResult<String>().ok("文件路径为空");
            }
            ftpFilePath = FTP_PRE_PATH + ftpFilePath;
            String downfileName = att.getAnnTitle();
            String ftpFileName = ftpFilePath.substring(ftpFilePath.lastIndexOf("/") + 1);
            //下载文件名称代码.类型
            if(StringUtils.isEmpty(downfileName)) {
                downfileName = ftpFileName;
            } else {
                if(downfileName.lastIndexOf(".") == -1) {
                    if(ftpFileName.lastIndexOf(".") != -1) {
                        downfileName = downfileName + ftpFileName.substring(ftpFileName.lastIndexOf("."));
                    }
                }
            }
            if(downfileName.lastIndexOf(".") == -1) {
                downfileName = downfileName +".pdf";
            }
            // 在这里加入设置Cookie ————-
            Cookie fileDownload = new Cookie("fileDownload", "true");
            fileDownload.setPath("/");
            response.addCookie(fileDownload);
            String downDir = ftpFilePath.substring(0, ftpFilePath.lastIndexOf("/"));
            FTPClient ftpClient = new FTPClient();
            byte[] buffer = null;
            try {
                ftpLoginAndChangeWorkDir(downDir, ftpClient);
                buffer = downFileByte(ftpFileName, ftpClient);// 根据文件名下载FTP服务器上的文件
                if (null == buffer || buffer.length == 0) {
                    log.error("downFtpFile read file " + ftpFileName + " falied, annId=" + annId);
                }
            } catch (Exception e) {
                log.error("downFtpFile falied =================:" + e.getMessage());
            }
            // 清空response
            response.reset();
            response.setCharacterEncoding("GBK");// 设置服务器端的编码
            response.setHeader("contentType", "text/html; charset=GBK");// 通知浏览器服务器发送的数据格式
            response.setContentType("text/html;charset=GBK");// 通知浏览器服务器发送的数据格式
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(downfileName, "UTF-8"));
            response.setContentType("application/octet-stream");

            OutputStream os = new BufferedOutputStream(response.getOutputStream());
            os.write(buffer);
            os.flush();
            os.close();
        } catch(EmptyResultDataAccessException empty) {
            result = "数据库没有附件记录";
        }catch (Exception e) {
            result = "failed";
        } finally {

        }
        return new JsonResult<String>("success".equalsIgnoreCase(result)? "0":"1",result,null);

    }
    
    /**
     * 
     * ftpLoginAndChangeWorkDir:(FTP登录并切换工作目录)
     *
     * @param  @param workDir    设定文件
     * @return void    DOM对象
     * @throws 
     * @since  CodingExample　Ver 1.1
     */
    public void ftpLoginAndChangeWorkDir(String workDir, FTPClient ftpClient) {
        try {
            workDir.replaceAll("//", "/");
            if (workDir.startsWith("/")) {
                workDir = workDir.substring(1);// 要以用户目录为起点，不能以"/"开始
            }
            if (!workDir.endsWith("/")) {
                workDir = workDir + "/";
            }
            
            ftpClient.connect(FTP_HOST, FTP_PORT);// FTP服务器IP地址
            ftpClient.login(FTP_LOGIN_USER, FTP_LOGIN_PASSWORD);// FTP服务器用户名和密码
            ftpClient.setDataTimeout(3 * 60 * 1000); // 超时时间N毫秒
            
            log.info(ftpClient.getReplyString());
            int reply = ftpClient.getReplyCode();// 230表示连接成功
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                ftpClient = null;
            } else {
                if(FTPReply.isPositiveCompletion(ftpClient.sendCommand("OPTS UTF8", "ON"))) {// 开启服务器对UTF-8的支持，如果服务器支持就用UTF-8编码，否则就使用本地编码（GBK）.
                    LOCAL_CHARSET = "UTF-8";
                }
                ftpClient.setControlEncoding(LOCAL_CHARSET);
                ftpClient.enterLocalPassiveMode();// 设置被动模式
            }
            // 转移到设置的目录下
            if (workDir != null && !workDir.equals("")) {
                // 先切换到根目录然后再以全路径切换
                boolean changeDirectoryOk = ftpClient.changeWorkingDirectory(workDir);
                log.info("ftpLoginAndChangeWorkDir changeWorkingDirectory=" + FTP_HOST + "/" + workDir + "  is " + changeDirectoryOk);
            }

        } catch (Exception e) {
            log.error("create ftpClient falied :" + e.getMessage(),e);
        }
    }
    
    /**
     * 下载文件 返回byte[]:3.4M耗时13秒多
     * 
     * @param fileName
     *            需要下载的文件名
     * @return
     * @throws Exception
     */
    public byte[] downFileByte(String fileName, FTPClient ftpClient) {
        byte[] return_arraybyte = null;
        if (ftpClient != null) {
            try {
                ftpClient.enterLocalPassiveMode(); // java中ftpClient.listFiles()结果为空问题解决方案
                ftpClient.configure(new FTPClientConfig(FTP_PARSER)); // java中ftpClient.listFiles()结果为空问题解决方案
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);// 防止乱码
                FTPFile[] files = ftpClient.listFiles();
//                boolean findfileYes = false;
                /** 防止名字上有各种字符 */
                fileName = fileName.trim();
                fileName.substring(0, fileName.lastIndexOf("."));
//                log.info("%%%%%%%%%%%%%%%%%%%%%% filesSize=" + (files == null ? 0 : files.length));
                for (FTPFile file : files) {
                    if (file.getName().contains(fileName)) {
//                        findfileYes = true;
//                        log.info("#############get the right fileName=" + file.getName() + " ,findFile=" + fileName);
                        InputStream ins = ftpClient.retrieveFileStream(file.getName());
                        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
                        byte[] buf = new byte[4096];
                        int bufsize = 0;
                        while ((bufsize = ins.read(buf, 0, buf.length)) != -1) {
                            byteOut.write(buf, 0, bufsize);
                        }
                        return_arraybyte = byteOut.toByteArray();
                        byteOut.close();
                        ins.close();
                        break;
                    }
                }
                // 这句很重要 要多次操作这个ftp的流的通道,要等他的每次命令完成
                ftpClient.completePendingCommand();
                ftpClient.logout();
//                log.info("@@@@@@@@@@@@@@@@@@@@find fileName=" + fileName + ", resutl=" + findfileYes);
            } catch (Exception e) {
                log.error("downFileByte失败！" + e.getMessage(),e);
            } finally {
                try {
                    ftpClient.disconnect();
                } catch (Exception e) {
                    log.error("FTP disconnect失败！" + e.getMessage());
                }
            }
        }
        return return_arraybyte;
    }

    
}