package com.analysis.startup;

import java.io.IOException;
import java.util.List;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import com.analysis.util.ftp.FtpConnFactory;
import com.analysis.util.ftp.FtpUtils;
import com.analysis.util.queue.QueueConnectionFactory;
import com.softsec.tase.store.exception.FtpUtilsException;

public class testClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Connection conn = QueueConnectionFactory.createActiveMQConnection("failover:(tcp://192.168.1.184:61616)");
		Session session = QueueConnectionFactory.createSession(conn);
		MessageProducer producer = QueueConnectionFactory.createPersistentProducer(session);
		Destination destination = null;
		try {
			destination = session.createQueue("analysisRequestQueue");
		} catch (JMSException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		List<String> firstDirList = null;
		List<String> secondDirList = null;
		FTPClient ftpClient = null;
		try {
			ftpClient = FtpConnFactory.connect("ftp://taseUser:62283748@192.168.1.184:21/");
		} catch (FtpUtilsException fue) {
		}
		firstDirList = FtpUtils.listFtpDirectory(ftpClient, "apk/");
		for(String firstDir : firstDirList) {
			secondDirList = FtpUtils.listFtpDirectory(ftpClient, "apk/" + firstDir + "/");
			for(String secondDir : secondDirList) {
				FTPFile[] files = null;
				try {
					files = ftpClient.listFiles("/apk/" + firstDir + "/" + secondDir + "/");
					;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				for(FTPFile f : files) {
					String apkPath = "apk/" + firstDir + "/" + secondDir + "/" + f.getName();
					ActiveMQTextMessage message = null;
					try {
						message = (ActiveMQTextMessage) session.createTextMessage();
					} catch (JMSException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						message.setText(apkPath);
						producer.send(destination, message);
					} catch (JMSException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}
		}
	}

}
