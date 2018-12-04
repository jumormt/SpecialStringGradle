/**
 * 
 */
package com.analysis.queue;

import java.util.List;
import java.util.Set;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.command.ActiveMQTextMessage;

import com.analysis.startup.Configuration;
import com.analysis.startup.Constants;
import com.analysis.util.queue.QueueConnectionFactory;

/**
 * RawResultQueue.java
 * 
 * @author yanwei
 * @date 2013-3-28 下午4:59:22
 * @description
 */
public class AnalysisRequestQueue {

	// private LinkedBlockingQueue<RawResult> analysisRequestQueue = new
	// LinkedBlockingQueue<RawResult>();

	private static final AnalysisRequestQueue analysisRequestQueueSingleton = new AnalysisRequestQueue();

	private static String analysisRequestQueueName;

	private static Connection analysisRequestQueueConnection;

	private static Session analysisRequestQueueSession;

	private static MessageProducer analysisRequestQueueProducer;
	
	private static long sensitiveStringVersion = 0;

	private AnalysisRequestQueue() {
		analysisRequestQueueName = Configuration.get(Constants.ANALYSIS_REQUEST_QUEUE_NAME, "analysisRequestQueue");
		analysisRequestQueueConnection = QueueConnectionFactory
				.createActiveMQConnection(Configuration.get(Constants.ACTIVEMQ_URL, "tcp://localhost:61616"));
		analysisRequestQueueSession = QueueConnectionFactory.createSession(analysisRequestQueueConnection);
		analysisRequestQueueProducer = QueueConnectionFactory.createPersistentProducer(analysisRequestQueueSession, analysisRequestQueueName);
	}

	public static AnalysisRequestQueue getInstance() {
		return analysisRequestQueueSingleton;
	}

	public Connection getAnalysisRequestQueueConnection() {
		return analysisRequestQueueConnection;
	}

	// public synchronized LinkedBlockingQueue<RawResult> getRawResultQueue() {
	// return analysisRequestQueue;
	// }

	public synchronized boolean addToAnalysisRequestQueue(String appFilePath) {
		TextMessage message;
		try {
			message = analysisRequestQueueSession.createTextMessage();
			message.setText(appFilePath);
			analysisRequestQueueProducer.send(message);
		} catch (JMSException je) {
			return false;
		}
		return true;
	}

	public synchronized boolean addToAnalysisRequestQueue(Set<String> appFilePathSet) {
		boolean success = true;
		for (String appFilePath : appFilePathSet) {
			success = addToAnalysisRequestQueue(appFilePath);
		}
		return success;
	}

	public String takeAnalysisRequest(MessageConsumer consumer) {
		String appFilePath = null;
		Message message;
		try {
			message = consumer.receive();
			ActiveMQTextMessage activeMQMessage = (ActiveMQTextMessage) message;
			appFilePath = (String) activeMQMessage.getText();
		} catch (JMSException je) {
			je.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return appFilePath;
	}

	public synchronized long getSensitiveStringVersion() {
		return sensitiveStringVersion;
	}

	public synchronized void setSensitiveStringVersion(long sensitiveStringVersion) {
		AnalysisRequestQueue.sensitiveStringVersion = sensitiveStringVersion;
	}
	
}
