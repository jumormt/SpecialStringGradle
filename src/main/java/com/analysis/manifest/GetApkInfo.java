/**
 * 
 */
package com.analysis.manifest;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.jar.JarFile;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.analysis.queue.AnalysisRequestQueue;
import com.softsec.tase.store.StoreManager;
import com.softsec.tase.store.apk.ApkManifest;
import com.softsec.tase.store.apk.ApkSignature;
import com.softsec.tase.store.util.fs.FileUtils;

/**
 * @author huge
 * 
 *         2014年8月6日
 */
public class GetApkInfo {
	public static Logger logger = LogManager.getLogger(GetApkInfo.class);

	public void getApkInfo(String filePath, String remoteFilePath, List<String> stringSpecial, long missionId) throws Exception {
		ApkExtractor apkextractor = new ApkExtractor();
		FileInputStream fis = new FileInputStream(new File(filePath));
		String fileChecksum = FileUtils.getFileMd5(fis);
		// System.out.println(filePath + " fileChecksum: " +fileChecksum );
		JarFile jarFile = null;
		try {
			jarFile = new JarFile(filePath);
		} catch (IOException ioe) {
			logger.error("failed to get jar file for app [ " + filePath + " ] : " + ioe.getMessage(), ioe);
			FileUtils.deleteFile(new File(filePath));
			return;
		}
		ApkManifest apkManifest = null;
		List<ApkSignature> apkSignatureList = null;
		if (apkextractor.getManifest(jarFile)) {
			ResExtrator res = new ResExtrator();
			apkManifest = res.Manifest(filePath);
			apkSignatureList = ApkSignatureExtractor.getApkSignature(jarFile);
		} else {
			logger.error(filePath + " has no Manifest!");
		}

		Map<String, List<String>> specialStringMap = null;
		
		DataInputStream input = apkextractor.getDexDataInputStreamWithBuffered(jarFile);
		if (input == null) {
			logger.error(filePath + " has no dex!");
		} else {
			GetSpecialString specialString = new GetSpecialString();
//			specialStringMap = specialString.GetStrings(jarFile, librules,stringSpecial,filePath);
		}
		if(jarFile != null) {
			jarFile.close();
		}
		if(input != null) {
			input.close();
		}
		if(fis != null) {
			fis.close();
		}
		System.gc();
		StoreManager s = new StoreManager();
		int t = s.saveApkInfo(fileChecksum, remoteFilePath, apkManifest, apkSignatureList, AnalysisRequestQueue.getInstance().getSensitiveStringVersion(), specialStringMap, missionId);
		logger.info(filePath + " : " + fileChecksum + " : " + t);
		logger.info(filePath + " package: " + apkManifest.getPackageName() + " apkname: " + apkManifest.getApkName());
		FileUtils.deleteFile(new File(filePath));
	}
}
