package com.analysis.util.ftp;

/**
 * 
 */


import java.io.IOException;
import java.net.SocketException;
import java.util.Random;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.analysis.startup.Configuration;
import com.softsec.tase.store.exception.FtpUtilsException;

/**
 * FtpConnFactory.java
 * 
 * @author yanwei
 * @date 2013-2-9 下午2:52:14
 * @description
 */
public class FtpConnFactory {

	public static Logger LOGGER = LogManager.getLogger(FtpConnFactory.class);
	
	/**
	 * get random ftp server url
	 * 
	 * @return ftpHost ftp://username:password@hostname:port/
	 */
	public static String getRandomFtpHost() {
		String[] ftpHosts = Configuration.get("ftp.server.urls", null).split(",");
		// TODO for throw exception
		return ftpHosts[new Random().nextInt(ftpHosts.length)];
	}

	/**
	 * connect and login
	 * 
	 * @param ftpHost
	 * @return Map< ftpClient, ftpHost >
	 * @throws FtpUtilsException
	 */
	public static FTPClient connect(String ftpHost) throws FtpUtilsException {

		// parse ftp server url
		FtpUrlParser parser = null;
		try {
			parser = new FtpUrlParser(ftpHost);
		} catch (FtpUtilsException fe) {
			LOGGER.error("Invalid ftp address format : " + ftpHost, fe);
			return null;
		}

		String domain = parser.getDomain();
		int port = parser.getPort();
		String username = parser.getUsername();
		String password = parser.getPassword();

		// connect ftp server and login
		FTPClient ftpClient = new FTPClient();
		try {
			ftpClient.connect(domain, port);
			int replyCode = ftpClient.getReplyCode();
			if (!FTPReply.isPositiveCompletion(replyCode)) {
				ftpClient.disconnect();
				LOGGER.error("Failed to connect ftp server : " + ftpHost);
				return null;
			}

			ftpClient.login(username, password);
			// initialization
			ftpClient.setKeepAlive(true);
			ftpClient.enterLocalPassiveMode();
			ftpClient.setControlEncoding("UTF-8");
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			ftpClient.setBufferSize(10240);
			ftpClient.setDataTimeout(120000);
			ftpClient.setConnectTimeout(60000);

		} catch (SocketException se) {
			LOGGER.error("Failed to establish ftp socket connection : " + se.getMessage(), se);
			return null;
		} catch (IOException ioe) {
			LOGGER.error("Failed to read/write from ftp connection : " + ioe.getMessage(), ioe);
			return null;
		} catch (Exception e) {
			LOGGER.error("Failed to read/write from ftp connection : " + e.getMessage(), e);
			return null;
		}

		return ftpClient;
	}
}
