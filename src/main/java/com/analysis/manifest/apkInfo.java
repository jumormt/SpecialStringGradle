/**
 * 
 */
package com.analysis.manifest;

import java.util.List;
import java.util.Map;

import com.softsec.tase.store.apk.ApkManifest;
import com.softsec.tase.store.apk.ApkSignature;

/**
 * @author huge
 *
 * 2014年8月6日
 */
public class apkInfo {
	
	private String fileChecksum; 
	private String filePath; 
	private ApkManifest apkManifest; 
	private ApkSignature apkSignature; 
	private Map<String, List<String>> specialStringMap;
	public String getFileChecksum() {
		return fileChecksum;
	}
	public void setFileChecksum(String fileChecksum) {
		this.fileChecksum = fileChecksum;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public ApkManifest getApkManifest() {
		return apkManifest;
	}
	public void setApkManifest(ApkManifest apkManifest) {
		this.apkManifest = apkManifest;
	}
	public ApkSignature getApkSignature() {
		return apkSignature;
	}
	public void setApkSignature(ApkSignature apkSignature) {
		this.apkSignature = apkSignature;
	}
	public Map<String, List<String>> getSpecialStringMap() {
		return specialStringMap;
	}
	public void setSpecialStringMap(Map<String, List<String>> specialStringMap) {
		this.specialStringMap = specialStringMap;
	}
}
