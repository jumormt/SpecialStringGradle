/**
 * 
 */
package com.analysis.guang;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.jar.JarFile;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.analysis.dex.DexMapItemMap;
import com.analysis.dex.GetDexMethodId;
import com.analysis.dex.GetDexTypeId;
import com.analysis.manifest.ApkExtractor;
import com.analysis.manifest.ApkSignatureExtractor;
import com.analysis.manifest.GetSpecialString;
import com.analysis.manifest.ReadRules;
import com.analysis.manifest.ResExtrator;
import com.softsec.tase.store.apk.ApkManifest;
import com.softsec.tase.store.apk.ApkSignature;
import com.softsec.tase.store.util.fs.FileUtils;


/**
 * @author huge
 *
 * 2015年1月6日
 */
public class ApkMain {
	
	public static Logger logger = LogManager.getLogger(ApkMain.class);	
	
	
	public static void main(String[] args){
		try {
			CommonData d = GetApkInfo("E:\\work\\test\\test\\大众点评.apk");
			
		//	Map<Integer, String> StringMap = d.getStringIdMap();
			
			Map<String,List<String>> resultMap = d.getResultMap();
			System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			System.out.println(resultMap);
			System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			System.out.println(resultMap.keySet());
			System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			String s = d.getApkManifest().toString();
			String a = d.getApkSignatureList().toString();
			System.out.println("");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static  CommonData GetApkInfo(String filePath) throws Exception {
		
		List<String> stringSpecial = new ArrayList<String>();
		logger.info("filePath " + filePath);
		ReadRules rules = new ReadRules();
		stringSpecial= rules.readRules();
		List<List<String>> librules = rules.readLibRules();
		ApkExtractor apkextractor = new ApkExtractor();
		FileInputStream fis = new FileInputStream(new File(filePath));
		String fileChecksum = FileUtils.getFileMd5(fis);
//		System.out.println(filePath + " fileChecksum: " +fileChecksum );
		JarFile jarFile = null;
		try {
			jarFile = new JarFile(filePath);
		} catch (IOException ioe) {
			logger.error("failed to get jar file for app [ " + filePath + " ] : " + ioe.getMessage(), ioe);
			FileUtils.deleteFile(new File(filePath));
			return null;
		}
		ApkManifest apkManifest = null;
		List<ApkSignature> apkSignatureList = null;
		if (apkextractor.getManifest(jarFile)) {
			ResExtrator res = new ResExtrator();
			//apkManifest = res.Manifest(filePath);
			apkManifest = res.AAPTManifest(filePath);
			apkSignatureList = ApkSignatureExtractor.getApkSignature(jarFile);
		} else {
			logger.error(filePath + " has no Manifest!");
		}

		Map<String, List<String>> specialStringMap = null;
		
		DataInputStream input = apkextractor.getDexDataInputStreamWithBuffered(jarFile);
		DexMapItemMap dexMapItemMapper = new DexMapItemMap();
		boolean confusion = true;
		if (input == null) {
			logger.error(filePath + " has no dex!");
		} else {
			GetSpecialString specialString = new GetSpecialString();
			dexMapItemMapper = specialString.GetStrings(jarFile, stringSpecial,filePath);
			specialStringMap = specialString.GetString(librules,stringSpecial,dexMapItemMapper);
			confusion = specialString.GetDefect(jarFile);
			logger.info("Get Strings!");;
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
		logger.info("map size : " + specialStringMap.size());
		logger.info(filePath + " package: " + apkManifest.getPackageName() + " apkname: " + apkManifest.getApkName());
	//	FileUtils.deleteFile(new File(filePath));
//		for(String key : specialStringMap.keySet()){
//			System.out.println("11111 " + key);
//		}
		CommonData commondata = new CommonData();
		commondata.setApkManifest(apkManifest);
		commondata.setApkSignatureList(apkSignatureList);
		commondata.setStringIdMap(dexMapItemMapper.getDexStringIdMap());
//		System.out.println("apkSignatureList" + apkSignatureList.toString());
		commondata.setFileChecksum(fileChecksum);
		commondata.setResultMap(specialStringMap);
		commondata.setDefect(confusion);//flase 混淆
		logger.info("fileChecksum : " + fileChecksum);
		logger.info("Get CommonData!");
		return commondata;
	}
}
