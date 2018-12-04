/**
 * 
 */
package com.analysis.customer;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.jms.Connection;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.analysis.queue.AnalysisRequestQueue;
import com.analysis.startup.Configuration;
import com.analysis.startup.Constants;
import com.analysis.util.ftp.FtpConnFactory;
import com.analysis.util.queue.QueueConnectionFactory;
import com.softsec.tase.store.service.FileStorageService;

/**
 * ResultCollectorCustomer.java
 * 
 * @author yanwei
 * @date 2013-3-28 5:29:17am
 * @description
 */
public class AnalysisRequestConsumer {

	private static final Logger LOGGER = LogManager.getLogger(AnalysisRequestConsumer.class);

	private String analysisRequestQueueName = Configuration.get(Constants.ANALYSIS_REQUEST_QUEUE_NAME, "analysisRequestQueue");
	
	private Connection analysisRequestQueueConnection = AnalysisRequestQueue.getInstance().getAnalysisRequestQueueConnection();

	private int threadCount;

	private ExecutorService threadPool;
	
	private long missionId;

	public AnalysisRequestConsumer(long missionId) {
		this.missionId = missionId;
		threadCount = Configuration.getInt(Constants.ANALYSIS_THREAD_COUNT, 10);
	}

	public void start() {
		LOGGER.info("Create analysis customers, customer count [ " + threadCount + " ].");
		threadPool = Executors.newFixedThreadPool(threadCount);
		FileStorageService fileStorageService = new FileStorageService();
		List<String> sensitiveStringList = fileStorageService.getLatestSensitiveStringList();
		for (int i = 0; i < threadCount; i++) {
			String ftpServer = FtpConnFactory.getRandomFtpHost();			
			FTPClient ftpClient = FtpConnFactory.connect(ftpServer);
			Session analysisRequestQueueSession = QueueConnectionFactory.createSession(analysisRequestQueueConnection);
			MessageConsumer analysisRequestQueueConsumer = QueueConnectionFactory.createConsumer(analysisRequestQueueSession, analysisRequestQueueName);
			Runnable collectorCustomer = new AnalysisThread(ftpClient, sensitiveStringList, analysisRequestQueueConsumer, missionId);
			threadPool.submit(new Thread(collectorCustomer), "analysis-" + i);
		}
		threadPool.shutdown();
	}

	public void setMissionId(long missionId) {
		this.missionId = missionId;
	}
}
