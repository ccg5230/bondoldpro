package com.innodealing.util.oss;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.CompleteMultipartUploadRequest;
import com.aliyun.oss.model.InitiateMultipartUploadRequest;
import com.aliyun.oss.model.InitiateMultipartUploadResult;
import com.aliyun.oss.model.ListPartsRequest;
import com.aliyun.oss.model.PartETag;
import com.aliyun.oss.model.PartListing;
import com.aliyun.oss.model.PartSummary;
import com.aliyun.oss.model.UploadPartRequest;
import com.aliyun.oss.model.UploadPartResult;

public class MultipartUploadOss {
	private static String endpoint = "*** Provide OSS endpoint ***";
	private static String accessKeyId = "*** Provide your AccessKeyId ***";
	private static String accessKeySecret = "*** Provide your AccessKeySecret ***";

	private static OSSClient client = null;

	private static String bucketName = "*** Provide bucket name ***";
	private static String key = "*** Provide key ***";
	private static String localFilePath = "*** Provide local file path ***";

	private static ExecutorService executorService = Executors.newFixedThreadPool(5);
	private static List<PartETag> partETags = Collections.synchronizedList(new ArrayList<PartETag>());
	
	private final static Logger LOGGER = LoggerFactory.getLogger(MultipartUploadOss.class);


	private static class PartUploader implements Runnable {

		private File localFile;
		private long startPos;

		private long partSize;
		private int partNumber;
		private String uploadId;

		public PartUploader(File localFile, long startPos, long partSize, int partNumber, String uploadId) {
			this.localFile = localFile;
			this.startPos = startPos;
			this.partSize = partSize;
			this.partNumber = partNumber;
			this.uploadId = uploadId;
		}

		@Override
		public void run() {
			InputStream instream = null;
			try {
				instream = new FileInputStream(this.localFile);
				instream.skip(this.startPos);

				UploadPartRequest uploadPartRequest = new UploadPartRequest();
				uploadPartRequest.setBucketName(bucketName);
				uploadPartRequest.setKey(key);
				uploadPartRequest.setUploadId(this.uploadId);
				uploadPartRequest.setInputStream(instream);
				uploadPartRequest.setPartSize(this.partSize);
				uploadPartRequest.setPartNumber(this.partNumber);

				UploadPartResult uploadPartResult = client.uploadPart(uploadPartRequest);
				LOGGER.info("Part#" + this.partNumber + " done\n");
				synchronized (partETags) {
					partETags.add(uploadPartResult.getPartETag());
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (instream != null) {
					try {
						instream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	private static File createSampleFile() throws IOException {
		File file = File.createTempFile("oss-java-sdk-", ".txt");
		file.deleteOnExit();

		Writer writer = new OutputStreamWriter(new FileOutputStream(file));
		for (int i = 0; i < 1000000; i++) {
			writer.write("abcdefghijklmnopqrstuvwxyz\n");
			writer.write("0123456789011234567890\n");
		}
		writer.close();

		return file;
	}

	private static String claimUploadId() {
		InitiateMultipartUploadRequest request = new InitiateMultipartUploadRequest(bucketName, key);
		InitiateMultipartUploadResult result = client.initiateMultipartUpload(request);
		return result.getUploadId();
	}

	private static void completeMultipartUpload(String uploadId) {
		// Make part numbers in ascending order
		Collections.sort(partETags, new Comparator<PartETag>() {

			@Override
			public int compare(PartETag p1, PartETag p2) {
				return p1.getPartNumber() - p2.getPartNumber();
			}
		});

		System.out.println("Completing to upload multiparts\n");
		CompleteMultipartUploadRequest completeMultipartUploadRequest = new CompleteMultipartUploadRequest(bucketName,
				key, uploadId, partETags);
		client.completeMultipartUpload(completeMultipartUploadRequest);
	}

	private static void listAllParts(String uploadId) {
		LOGGER.info("Listing all parts......");
		ListPartsRequest listPartsRequest = new ListPartsRequest(bucketName, key, uploadId);
		PartListing partListing = client.listParts(listPartsRequest);

		int partCount = partListing.getParts().size();
		for (int i = 0; i < partCount; i++) {
			PartSummary partSummary = partListing.getParts().get(i);
			LOGGER.info("\tPart#" + partSummary.getPartNumber() + ", ETag=" + partSummary.getETag());
		}
		System.out.println();
	}
}
