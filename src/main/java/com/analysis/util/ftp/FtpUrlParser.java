package com.analysis.util.ftp;



import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.softsec.tase.store.exception.FtpUtilsException;
import com.softsec.tase.store.util.StringUtils;

/**
 * Example : ftp://username:password@domain:port/path/a/addr.txt
 * @author wanghouming & yanwei
 *
 */
public class FtpUrlParser {
	
	private String domain;

	private int port;

	private String username;

	private String password;

	private String path;

	private String fileName;

	private Matcher matcher;
    /**
     * get ftp info by resultAddress
     * @param resultAddress
     * @throws FtpUtilsException
     */
	public FtpUrlParser(String resultAddress) throws FtpUtilsException {
		if (StringUtils.isEmpty(resultAddress)) {
			throw new FtpUtilsException("Address must not be null or empty.");
		}
		
		String regex = "ftp://(.+):(.+)@([^:]*):(\\d+)(/*[\\w\\./]+)";
		Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		matcher = pattern.matcher(resultAddress);
		
		if (!matcher.find()) {
			throw new FtpUtilsException("Address [" + resultAddress + "] is invalid.");
		} else {
			
			this.username = matcher.group(1);
			this.password = matcher.group(2);
			this.domain = matcher.group(3);
			this.port = Integer.parseInt(matcher.group(4));
			String filePath = matcher.group(5);
			
			if (filePath == null) {
				this.path = "/";
			} else if (filePath != null && filePath.endsWith("/")) {
				this.path = filePath;
			} else if (filePath != null && !filePath.endsWith("/")) {
				int index = filePath.lastIndexOf("/");
				if (index >= 0) {
					this.path = filePath.substring(0, index + 1);
					this.fileName = filePath.substring(index + 1, filePath.length());
				}
			}
		}
	}
	
	public String getDomain() {
		return domain;
	}

	public int getPort() {
		return port;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getPath() {
		return path;
	}

	public String getFileName() {
		return fileName;
	}
}
