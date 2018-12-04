/**
 * 
 */
package com.analysis.util.ftp;

import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author huge
 *
 * 2014年8月15日
 */
 
 
public class FtpSync {
	
	public static Logger LOGGER = LogManager.getLogger(FtpSync.class);
	
	private static String ftpServer = FtpConnFactory.getRandomFtpHost();
	
//	private static FTPClient ftpClient = FtpConnFactory.connect(ftpServer);
	
	public String DownloadFile(FTPClient ftpClient, String remoteFilePath,String destinationFilePath ) throws Exception{
		String localFilePath = null;		
		for(int tryTime = 0; tryTime <= 2 && localFilePath == null; tryTime ++) {
			try {
				if(ftpClient == null || !ftpClient.isConnected() || !ftpClient.isAvailable()) {					
					ftpClient = null;
					ftpClient = FtpConnFactory.connect(ftpServer);
				}
				if (!FtpUtils.isFileExist(ftpClient, remoteFilePath)) {
					LOGGER.error("Failed to download file [ " + remoteFilePath + " ] : file doesn't exist");
					return localFilePath;
				}
				localFilePath = FtpUtils.download(ftpClient, remoteFilePath, destinationFilePath);
				LOGGER.info("success to fetchApp, local file path [ " + localFilePath + " ]");
			} catch(Exception e) {
				LOGGER.error("Failed to download app [ " + remoteFilePath + " ], try to reconnect to ftp server : " + e.getMessage(), e);
				if(ftpClient != null) {
					try {
						ftpClient.disconnect();
					} catch(IOException ioe) {
						LOGGER.error("Failed to disconnect ftpCliane : " + ioe.getMessage(), ioe);
					}
				}
				ftpClient = null;
				ftpClient = FtpConnFactory.connect(ftpServer);
			}
		}
		return localFilePath;
	}
}