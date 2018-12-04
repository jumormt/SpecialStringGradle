/**
 * 
 */
package com.analysis.customer;

import java.io.File;
import java.util.List;

import javax.jms.MessageConsumer;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.analysis.manifest.GetApkInfo;
import com.analysis.queue.AnalysisRequestQueue;
import com.analysis.util.ftp.FtpConnFactory;
import com.analysis.util.ftp.FtpSync;

/**
 * ResultCollectorThread.java
 * 
 * @author yanwei
 * @date 2013-3-28 下午5:28:34
 * @description
 */
public class AnalysisThread implements Runnable {

	private static final Logger LOGGER = LogManager.getLogger(AnalysisThread.class);

	private MessageConsumer rawResultQueueConsumer;

	private static List<String> sensitiveString;

	private long missionId;
	
	private FTPClient ftpClient;

	public AnalysisThread(FTPClient ftpClient, List<String> sensitiveString, MessageConsumer rawResultQueueConsumer, long missionId) {
		this.ftpClient = ftpClient;
		AnalysisThread.sensitiveString = sensitiveString;
		this.rawResultQueueConsumer = rawResultQueueConsumer;
		this.missionId = missionId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		FtpSync ftpdown = new FtpSync();
		GetApkInfo getApkInfo = new GetApkInfo();
		while (!Thread.currentThread().isInterrupted()) {
			if(sensitiveString == null || sensitiveString.size() == 0) {
				LOGGER.error("sensitiveStringList is empty");
				continue;
			}
			String appFilePath = AnalysisRequestQueue.getInstance().takeAnalysisRequest(rawResultQueueConsumer);
			if (appFilePath != null) {			
				try {				
					String destinationFilePath = missionId + File.separator + Thread.currentThread().getId() + File.separator + System.currentTimeMillis();
					destinationFilePath = ftpdown.DownloadFile(ftpClient, appFilePath, destinationFilePath);
					if(destinationFilePath != null) {
						getApkInfo.getApkInfo(destinationFilePath, appFilePath, sensitiveString, missionId);
					} else {
						LOGGER.error("failed to download app file [ " + appFilePath + " ] ");
					}
				} catch (Exception e) {
					LOGGER.error("failed to analysis app file [ " + appFilePath + " ]: " + e.getMessage(), e);
				}
			}
		}
	}

	public static void setSpecialStringList(long specialStringVersion, List<String> sensitiveString) {
		if (AnalysisRequestQueue.getInstance().getSensitiveStringVersion() < specialStringVersion) {
			LOGGER.info("get new special string version [ " + specialStringVersion + " ] ");
			AnalysisThread.sensitiveString = sensitiveString;
		}
	}
}
