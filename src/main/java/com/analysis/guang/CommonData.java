/**
 * 
 */
package com.analysis.guang;

import java.util.List;
import java.util.Map;

import com.softsec.tase.store.apk.ApkManifest;
import com.softsec.tase.store.apk.ApkSignature;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author huge
 *
 * 2015年1月8日
 */
public class CommonData {
	
	
	public String fileChecksum = "";
	public ApkManifest apkManifest ;
	public List<ApkSignature> apkSignatureList = null;	
	public boolean Defect ; 	
	public Map<Integer, String> StringIdMap =
	new ConcurrentHashMap<Integer, String>();
	
	public boolean Consolidation;
	public Map<String, List<String>> resultMap;

	public String getFileChecksum() {
		return fileChecksum;
	}

	public void setFileChecksum(String fileChecksum) {
		this.fileChecksum = fileChecksum;
	}

	public boolean isConsolidation() {
		return Consolidation;
	}

	public void setConsolidation(boolean consolidation) {
		Consolidation = consolidation;
	}

	public Map<String, List<String>> getResultMap() {
		return resultMap;
	}

	public void setResultMap(Map<String, List<String>> resultMap) {
		this.resultMap = resultMap;
	}



	public boolean isDefect() {
		return Defect;
	}

	public void setDefect(boolean defect) {
		Defect = defect;
	}

	public Map<Integer, String> getStringIdMap() {
		return StringIdMap;
	}

	public void setStringIdMap(Map<Integer, String> stringIdMap) {
		StringIdMap = stringIdMap;
	}

	public void setApkManifest(ApkManifest apkManifest2) {
		// TODO Auto-generated method stub
		apkManifest = apkManifest2;
		
	}
	public ApkManifest getApkManifest() {
		// TODO Auto-generated method stub
		return apkManifest;
		
	}

	public void setApkSignatureList(List<ApkSignature> apkSignatureList2) {
		// TODO Auto-generated method stub
		apkSignatureList = apkSignatureList2;
		
	}

	public List<ApkSignature> getApkSignatureList() {
		// TODO Auto-generated method stub
		return apkSignatureList;
		
	}
}
