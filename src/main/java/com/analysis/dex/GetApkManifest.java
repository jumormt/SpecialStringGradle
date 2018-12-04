///**
// * 
// */
//package com.analysis.dex;
//
//import test.AXMLPrinter2;
//import zip.tool.Decompression;
//
//import com.softsec.tase.store.apk.ApkManifest;
//
//  
//
//
//
//
///**
// * @author huge
// *
// * 2014年8月5日
// */
//public class GetApkManifest {
//	
////	public static void main(String[] args){
////		String s = "E:/work/Android静态检测/ABC_Android_V1.1.01_修改smali (1).apk";
////		PackageName(s);
////	}
//	
//	public ApkManifest apkManifest(String fileName){
//		ApkManifest apkManifest = new ApkManifest();
//		
//		Decompression d = new Decompression(fileName);
//		String maniMessage = AXMLPrinter2.parse(d.getAndroidManifest());
//		String[] manifestTemp = maniMessage.split("\t"); 
//		for(String s : manifestTemp){
//			if(s.contains("package=")){
//			//	System.out.println(s);
//				apkManifest.setPackageName(s.substring(9, s.length()-1));
//			}
//			if(s.contains("android:label=")){
//			//	System.out.println(s);
//				apkManifest.setApkName(s.substring(15, s.length()-1));
//			}
//		}
//		//System.out.println("222222222");
//		return apkManifest;	
//	}
//}
