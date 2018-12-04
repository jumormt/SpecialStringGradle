package com.analysis.startup;

//String url, int port,String username, String password, String remotePath,String fileName,String localPath
public final class Constants {
	
	/** ftp settings */
	
	public static final String FTP_URL = "ftp.server.urls";
	
	public static final String FTP_PORT = "ftp.app.repo";
	
	public static final String USERNAME = "ftp.program.repo";
	
	/** adtimeMQ settings*/
	
	public static final String ACTIVEMQ_URL = "activemq.url";
	
	public static final String ANALYSIS_REQUEST_QUEUE_NAME = "analysis.request.queue.name";
	
	public static final String JMX_SERVICE_URL = "jmx.service.url";
	
	public static final String JMX_DOMAIN_NAME = "jmx.domain.name";
	
	public static final String JMX_BROKER_NAME = "jmx.broker.name";
	
	/** customer settings*/
	
	public static final String ANALYSIS_THREAD_COUNT = "analysis.thread.count";
	
}
