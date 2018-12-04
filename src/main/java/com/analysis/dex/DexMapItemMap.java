package com.analysis.dex;
/**
 * 
 */
//package edu.bupt.isc.instance;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class DexMapItemMap {

	private Map<String, Integer> dexMapItemCountMap =
			new ConcurrentHashMap<String, Integer>();
	
	private Map<String, Integer> dexMapItemOffsetMap =
			new ConcurrentHashMap<String, Integer>();
	
	private Map<Integer, Integer> dexStringOffsetMap =
			new ConcurrentHashMap<Integer, Integer>();
	
	private Map<Integer, String> dexStringIdMap =
			new ConcurrentHashMap<Integer, String>();
	
	private Map<Integer, String> dexTypeIdMap =
			new ConcurrentHashMap<Integer, String>();
	
	private Map<String, List<String>> resultMap = 
			new ConcurrentHashMap<String,List<String>>();
	
	

	
//-------------------------------------------------------------------------------------------------------

	
	public synchronized Map<String, Integer> getDexMapItemCountMap() {
		return dexMapItemCountMap;
	}
	
	public synchronized Map<String, Integer> getDexMapItemOffsetMap() {
		return dexMapItemOffsetMap;
	}
	
	public synchronized Map<Integer, Integer> getDexStringOffsetMap() {
		return dexStringOffsetMap;
	}
	
	public synchronized Map<Integer,String> getDexStringIdMap(){
		return dexStringIdMap;
	}
	
	public synchronized Map<Integer,String> getDexTypeIdMap(){
		return dexTypeIdMap;
	}
	
	public synchronized Map<String, List<String>> getresultMap(){
		return resultMap;
	}
	
	
	
//==============================================================================================
	
	
	
//	public List<String> getUrlStringList() {
//		return urlStringList;
//	}
//	
//	
//	public void addUrlStringList(String urlItem){
//		urlStringList.add(urlItem);
//	}
//--------------------------------------------------------------------------------------------------------
	
	public synchronized void addDexMapItemCount(String dexMapItem, Integer count) {
		dexMapItemCountMap.put(dexMapItem, count);
	}
	
	public synchronized void addDexMapItemOffset(String dexMapItem, Integer offset) {
		dexMapItemOffsetMap.put(dexMapItem, offset);
	}
	
	public synchronized void addDexMapString(Integer dexMapItem, Integer offset) {
		dexStringOffsetMap.put(dexMapItem, offset);
	}
	
	public synchronized void addDexStringId(Integer dexStringNum, String StringId) {
		dexStringIdMap.put(dexStringNum, StringId);
	}
	
	public synchronized void addDexTypeId(Integer dexTypeNum, String TypeId) {
		dexTypeIdMap.put(dexTypeNum, TypeId);
	}
	
	public synchronized void addResult(String stringId, List<String> resultList) {
		resultMap.put(stringId, resultList);
	}
	
	
//-----------------------------------------------------------------------------------------------------------------------
	
	public synchronized int getDexMapItemCount(String dexMapItem) {
		return dexMapItemCountMap.get(dexMapItem);
	}
	
	public synchronized int getDexMapItemOffset(String dexMapItem) {
		return dexMapItemOffsetMap.get(dexMapItem);
	}
	
	public synchronized int getDexStringOffset(Integer dexMapItem) {
		return dexStringOffsetMap.get(dexMapItem);
	}
	
	public synchronized String getDexString(Integer dexStringNum){
		return dexStringIdMap.get(dexStringNum);		
	}
	
	public synchronized String getDexType(Integer dexTypeNum){
		return dexTypeIdMap.get(dexTypeNum);
		
	}
	
	public synchronized List<String> getResultList(String StringId){
		return resultMap.get(StringId);
	}

}
