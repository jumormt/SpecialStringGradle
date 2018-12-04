package com.analysis.manifest;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import brut.androlib.AndrolibException;
import brut.androlib.res.AndrolibResources;
import brut.androlib.res.data.ResPackage;
import brut.androlib.res.data.ResResource;
import brut.androlib.res.data.ResTable;
import brut.androlib.res.data.ResValuesFile;
import brut.androlib.res.data.value.ResScalarValue;
import brut.androlib.res.decoder.ARSCDecoder;
import brut.androlib.res.decoder.AXmlResourceParser;
import brut.androlib.res.decoder.ResAttrDecoder;
import brut.androlib.res.decoder.ResFileDecoder;
import brut.androlib.res.util.ExtFile;
import brut.directory.DirectoryException;
import brut.util.Duo;

import com.softsec.tase.store.apk.ApkManifest;

public class ResExtrator {

	static Logger logger = LogManager.getLogger(ResExtrator.class);
	private static String fileSeparator = System.getProperty("file.separator");
	public ApkManifest AAPTManifest(String apkpath) {
		ApkManifest apkManifest = new ApkManifest();
		String OS = System.getProperty("os.name").toLowerCase();
		String aaptPath = null;
		if(OS.indexOf("linux")>=0)
			aaptPath = System.getProperty("user.dir") + fileSeparator + "tools" + fileSeparator + "aapt_x64";
		else if(OS.indexOf("mac")>=0)
			aaptPath = System.getProperty("user.dir") + fileSeparator + "tools" + fileSeparator + "aapt_mac";
		else
			aaptPath = System.getProperty("user.dir") + fileSeparator + "tools" + fileSeparator + "aapt.exe";
		logger.info("aaptPath is "+aaptPath);
		String aaptCMD = aaptPath + " d badging " + apkpath ;
		logger.info("aaptCMD  is "+aaptCMD );
		String packagename = "null"; 
		String apkname = "null";
		try{
			Runtime rt = Runtime.getRuntime();    
			Process proc = rt.exec(aaptCMD);
			BufferedInputStream inf = new BufferedInputStream(proc.getInputStream());   
			BufferedReader inBrf = new BufferedReader(new InputStreamReader(inf));
			String lf;
			while((lf = inBrf.readLine()) !=null){
				if(lf.startsWith("package: name=")){//获取package name
					String[] a = lf.split("'");
					packagename = a[1];
				}
				if(lf.startsWith("application-label:")){
					String[] b = lf.split(":");
					apkname = b[1];
				//	int size = apkname.length()-1;
				//	apkname = apkname.substring(0, apkname.length()-1);
				}
//				System.out.println("apktool error!");
			}			
			
		} catch(Exception e){
			logger.error("aapt error is "+e);
		}
		apkManifest.setApkName(apkname);
		apkManifest.setPackageName(packagename);
		
		return apkManifest;
	}

	public synchronized ApkManifest Manifest(String apkpath) {
		//package name...
		ApkManifest apkManifest = new ApkManifest();
		ResTable resTable = new ResTable();
		InputStream resourcesStream = null;
		try {
			resourcesStream = new ExtFile(apkpath).getDirectory().getFileInput("resources.arsc");
			ResPackage[] resPackages = ARSCDecoder.decode(resourcesStream, false, false, resTable).getPackages();
			//ARSCDecoder decoder = new ARSCDecoder(new ExtFile(apkpath).getDirectory().getFileInput("resources.arsc"), resTable, false, false);
			//ResPackage[] resPackages = decoder.readTable();
			resTable.addPackage(resPackages[0], true);
			apkManifest.setPackageName(resPackages[0].getName());
			//		logger.info(resPackages[0].getName()); //package name
		//	System.out.println(apkpath + " package name :" + resPackages[0].getName());
	//	} catch (AndrolibException | DirectoryException e) {
		} catch (Exception e) {
			logger.error("failed to get packagename!");
			apkManifest.setPackageName("");
	//		e.printStackTrace();
			return null;
			
		} finally {
			if(resourcesStream != null) {
				try {
					resourcesStream.close();
				} catch (IOException e) {
					logger.error("failed to close resources stream : " + e.getMessage());
				}
			}
		}
		//apkManifest.setPackageName("package test");
		try {
			AndrolibResources ar = new AndrolibResources();
			Duo<ResFileDecoder, AXmlResourceParser> duo = ar.getResFileDecoder();
			ResAttrDecoder attrDecoder = duo.m2.getAttrDecoder();
			attrDecoder.setCurrentPackage(resTable.listMainPackages().iterator().next());
			for (ResPackage pkg : resTable.listMainPackages()) {
				attrDecoder.setCurrentPackage(pkg);
				for (ResValuesFile valuesFile : pkg.listValuesFiles()) {//所有资源文件
					for (ResResource res : valuesFile.listResources()) {
						if (valuesFile.isSynthesized(res)) {
							continue;
						}
						String type = res.getResSpec().getType().getName();
						if ("string".equals(type)) {
							ResScalarValue rv = (ResScalarValue) res.getValue();
					//		System.out.println("rv : " +rv.encodeAsResXmlItemValue());
							try {
								if("app_name".equals( res.getResSpec().getName())){
									apkManifest.setApkName(rv.encodeAsResXmlItemValue());
									//logger.info(rv.encodeAsResXmlItemValue()); //apk name
								//	System.out.println(apkpath + " app_name: " + rv.encodeAsResXmlItemValue());								
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								logger.error("failed to get apkname!");
								apkManifest.setApkName("");
//								e.printStackTrace();
								return null;
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("failed to get apkname!");
			apkManifest.setApkName("");
			// TODO: handle exception
		}
		
		return apkManifest;
	}
	
	public synchronized List<String> Resource(String apkpath) {
		//package name...
		List<String> resource = new ArrayList<String>();
		ResTable resTable = new ResTable();
		InputStream resourcesStream = null;
		try {
			resourcesStream = new ExtFile(apkpath).getDirectory().getFileInput("resources.arsc");
			ResPackage[] resPackages = ARSCDecoder.decode(resourcesStream, false, false, resTable).getPackages();
		    resTable.addPackage(resPackages[0], true);
	//	} catch (AndrolibException | DirectoryException e) {
		} catch (Exception  e) {
			logger.error("failed to get packagename!");
	//		e.printStackTrace();			
		} finally {
			if(resourcesStream != null) {
				try {
					resourcesStream.close();
				} catch (IOException e) {
					logger.error("failed to close resources stream : " + e.getMessage());
				}
			}
		}
		try {
			AndrolibResources ar = new AndrolibResources();
			Duo<ResFileDecoder, AXmlResourceParser> duo = ar.getResFileDecoder();
			ResAttrDecoder attrDecoder = duo.m2.getAttrDecoder();
			attrDecoder.setCurrentPackage(resTable.listMainPackages().iterator().next());
			for (ResPackage pkg : resTable.listMainPackages()) {
				attrDecoder.setCurrentPackage(pkg);
				for (ResValuesFile valuesFile : pkg.listValuesFiles()) {//所有资源文件
					for (ResResource res : valuesFile.listResources()) {
						if (valuesFile.isSynthesized(res)) {
							continue;
						}
						String type = res.getResSpec().getType().getName();
						if ("string".equals(type)) {
							ResScalarValue rv = (ResScalarValue) res.getValue();
							try {
							//	System.out.println("rv : " +rv.encodeAsResXmlItemValue());
								resource.add(rv.encodeAsResXmlItemValue());
							} catch (Exception e) {
								logger.error("failed to get srings!");
//								e.printStackTrace();
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("failed to get apkname!");
		}
		
		return resource;	
	}
}
