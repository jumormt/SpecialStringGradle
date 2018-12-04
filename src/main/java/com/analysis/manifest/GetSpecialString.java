package com.analysis.manifest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.jar.JarFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.analysis.dex.DexHeader;
import com.analysis.dex.DexMapItemMap;
import com.analysis.dex.GetDexMethodId;
import com.analysis.dex.GetDexStringId;
import com.analysis.dex.GetDexTypeId;
import com.analysis.guang.ApkMain;
import com.analysis.guang.CommonData;




public class GetSpecialString {
	
	public static Logger logger = LogManager.getLogger(GetSpecialString.class);	

	public DexMapItemMap GetStrings(JarFile apkJarFile,List<String> stringTest,String filepath) throws Exception{	
		DexMapItemMap dexMapItemMapper = new DexMapItemMap();
		try {
			DexHeader dexheader = new DexHeader();
			dexheader.readDex(apkJarFile, dexMapItemMapper);
			GetDexStringId getDexStringId = new GetDexStringId();
			getDexStringId.StringId(apkJarFile, dexMapItemMapper);
			logger.info("Get StringId");
//			GetDexTypeId type = new GetDexTypeId();
//			type.TypeId(apkJarFile, dexMapItemMapper);
//			GetDexMethodId method = new GetDexMethodId();
//			boolean confusion = method.MethodId(apkJarFile, dexMapItemMapper);
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new Exception(e.getMessage(), e);
			
		}		
//		ResExtrator res = new ResExtrator();
//		List<String> resource = res.Resource(filepath);
//		int index=1;
//		for(String s : resource){
//			dexMapItemMapper.getDexStringIdMap().put(dexMapItemMapper.getDexStringIdMap().size()+index, s);
//			index++;
//		}
		return dexMapItemMapper;
	 }
	 
	//规则比对
//	 private void GetCommonList(List<String> stringTest,DexMapItemMap dexMapItemMapper){	
//		 System.out.println("stringTest : " + stringTest.toString());
//		 for(String test : stringTest){
//			 HashSet<String> hc = new HashSet<String>();
//			 List<String> CommonList = new ArrayList<String>();
//			 for (int index : dexMapItemMapper.getDexStringIdMap().keySet()) {
//				 String temp = dexMapItemMapper.getDexStringIdMap().get(index);
//				 if(temp.contains(test)){
//					 hc.add(temp);
//				 }
//			 }	
//			 CommonList.addAll(hc);
//			 if(!CommonList.isEmpty()){				 
//				 dexMapItemMapper.addResult(test, CommonList);
//			 }
//		 }
//	 }
	
	
	public Map<String, List<String>> GetString(List<List<String>> librules,List<String> stringTest,DexMapItemMap dexMapItemMapper){
		//比对	
		// GetCommonList(stringTest,dexMapItemMapper);
		GetRegular(librules,stringTest,dexMapItemMapper);
		dexMapItemMapper.getDexMapItemCountMap().clear();
		dexMapItemMapper.getDexMapItemOffsetMap().clear();
		//	dexMapItemMapper.getDexStringIdMap().clear();
		dexMapItemMapper.getDexStringOffsetMap().clear();
//		 System.gc();
//		int sss = dexMapItemMapper.getresultMap().size();
		logger.info("map size : " + dexMapItemMapper.getresultMap().size());
		return dexMapItemMapper.getresultMap();			  		
	}
	 
		private  void GetRegular(List<List<String>> librules,List<String> stringTest , DexMapItemMap dexMapItemMapper) {
			
		//	String url = "(?<=http|https://|\\.)[^.]*?\\.(com|cn|net|org|biz|info|cc|tv)";
		//	String phoneNumber="^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$";
			String IPNumber = "^(([0-2]*[0-9]+[0-9]+)\\.([0-2]*[0-9]+[0-9]+)\\.([0-2]*[0-9]+[0-9]+)\\.([0-2]*[0-9]+[0-9]+))$";		
			String url = "http://|https://";

			List<String> phone = new ArrayList<String>();
			phone.add("[^0-9]*((0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8})");
			phone.add("^106(\\d{2,9}$)");
			phone.add(	"(^955\\d{2}$)|(^953\\d{2}$)|(^951\\d{2}$)");
			phone.add(	"^123\\d{2}$");
			phone.add("^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$");
			
			HashSet<String> numberResult = new HashSet<String>();
			HashSet<String> IPNumberResult = new HashSet<String>();
			HashSet<String> urlResult = new HashSet<String>();
		//	HashSet<String> apiResult = new HashSet<String>();
			
			
			List<String> numberResultList = new ArrayList<String>();
			List<String> IPNumberResultList = new ArrayList<String>();
			List<String> urlResultList = new ArrayList<String>();
		//	List<String> apiResultList = new ArrayList<String>();		
			
			Pattern urlpattern = Pattern.compile(url);
			Pattern IPpattern = Pattern.compile(IPNumber);
			
//			String st =  dexMapItemMapper.getDexStringIdMap().get(1956);
			
			for (int index : dexMapItemMapper.getDexStringIdMap().keySet()) {
				String s = dexMapItemMapper.getDexStringIdMap().get(index);
	//			System.out.println("index: " + index +"   sssss: " + s);
				if(s!=null && !s.isEmpty()){
					Matcher urlmatcher = urlpattern.matcher(s);
					Matcher IPmatcher = IPpattern.matcher(s);
					
					for(String num : phone){
						Pattern numberpattern=Pattern.compile(num);
						Matcher numbermatcher = numberpattern.matcher(s);	
						if(numbermatcher.lookingAt()){
							numberResult.add(s);
//							System.out.println("numberResult : " + s);
						}				
					}
					
					if(urlmatcher.lookingAt()){
						urlResult.add(s);
//						System.out.println("urlResult : " + s);
					}
					
					if(IPmatcher.lookingAt()){
						IPNumberResult.add(s);
//						System.out.println("IPNumberResult : " + s);
					}					
				}
			}
			
			numberResultList.addAll(numberResult);
			if(!numberResultList.isEmpty()){				 
				dexMapItemMapper.addResult("PhoneNumber", numberResultList);
			}
			IPNumberResultList.addAll(IPNumberResult);
			if(!IPNumberResultList.isEmpty()){				 
				dexMapItemMapper.addResult("IPNumber", IPNumberResultList);
			}
			urlResultList.addAll(urlResult);
			if(!urlResultList.isEmpty()){				 
				dexMapItemMapper.addResult("URL", urlResultList);
			}
	
//			for(String rule : patternString){
//				Pattern p = Pattern.compile(rule,Pattern.CASE_INSENSITIVE);
//				HashSet<String> hc = new HashSet<String>();
//				List<String> CommonList = new ArrayList<String>();
//				for (int index : dexMapItemMapper.getDexStringIdMap().keySet()) {
//					 String temp = dexMapItemMapper.getDexStringIdMap().get(index);
//					 Matcher m = p.matcher(temp);
//					 if(m.lookingAt()){
////						 String s = m.group();
////						 s= s.replaceAll("/", "");
////						 s=s.replaceAll(":", "");
//						 hc.add(temp);
//						 System.out.println("rule : " + rule +  "  uip: " + temp);
//					 }
//				 }	
//				 CommonList.addAll(hc);
//				 if(!CommonList.isEmpty()){				 
//					 dexMapItemMapper.addResult(rule, CommonList);
//				 }
//			}	
			
			
		//	 System.out.println("stringTest : " + stringTest.toString());
			 for(String test : stringTest){
				 HashSet<String> hc = new HashSet<String>();
				 List<String> CommonList = new ArrayList<String>();
				 for (int index : dexMapItemMapper.getDexStringIdMap().keySet()) {
					 String temp = dexMapItemMapper.getDexStringIdMap().get(index);
					 if(temp.contains(test)){
						 hc.add(temp);
//						 System.out.println("rule: " + test + "	temp: " + temp);
					 }
				 }	
				 CommonList.addAll(hc);
				 if(!CommonList.isEmpty()){				 
					 dexMapItemMapper.addResult(test, CommonList);
				 }
			 }
			 
			 
			 for(List<String> jr : librules){
				 HashSet<String> hc = new HashSet<String>();
				 List<String> CommonList = new ArrayList<String>();
				 for(String test : jr){
					 for (int index : dexMapItemMapper.getDexStringIdMap().keySet()) {
						 String temp = dexMapItemMapper.getDexStringIdMap().get(index);
						 if(temp.contains(test)){
							 hc.add(test);
//							 System.out.println("rule: " + test + "	temp: " + temp);
						 }
					 }	
				 }
				 CommonList.addAll(hc);
				 if(CommonList.size()==jr.size()){				 
					 dexMapItemMapper.addResult("jingguojiagu", CommonList);
				 }
			 }		 
			 
		}

		public boolean GetDefect(JarFile apkJarFile){
			boolean confusion = true;
			DexMapItemMap dexMapItemMapper = new DexMapItemMap();
			try {
				DexHeader dexheader = new DexHeader();
				dexheader.readDex(apkJarFile, dexMapItemMapper);
				GetDexStringId getDexStringId = new GetDexStringId();
				getDexStringId.StringId(apkJarFile, dexMapItemMapper);
				GetDexTypeId type = new GetDexTypeId();
				type.TypeId(apkJarFile, dexMapItemMapper);
				GetDexMethodId method = new GetDexMethodId();
				confusion = method.MethodId(apkJarFile, dexMapItemMapper);
			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				
				
			}		
			return confusion;
			
		}
}
