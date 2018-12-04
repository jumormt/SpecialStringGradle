///**
// * 
// */
//package com.analysis.manifest;
//
///**
// * @author huge
// *
// * 2014年8月5日
// */
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//import com.softsec.tase.store.StoreManager;
// 
//public class ThreadPool {
//	public static Logger logger = LogManager.getLogger(ThreadPool.class);
//	private static int Pool_Max = 64;	
// 
//    public static void main(String[] args) throws InterruptedException {
//        ExecutorService executor = Executors.newFixedThreadPool(Pool_Max);
//        
//        long startTime=System.currentTimeMillis();
//        
//        FormatParse format = new FormatParse();
//        HashMap<String,List<String>> mainTemp = format.getCommand(args);
//        
//        String apkpath = mainTemp.get("filepath").get(0);
//		List<String>fileList = new ArrayList<String>(); 
//		FileAction.CalcalateAllFile(apkpath, fileList);
//		int fileNumber = fileList.size();//获取总的任务量      
//		
//		List<String> specialString = mainTemp.get("special");		
//		List<String> packagename = mainTemp.get("packagename");
//		List<String> apkname = mainTemp.get("apkname");
//		
////		String speString= "";
////		for(String s : specialString){
////			speString =  speString + "," + s ;
////		}
////		speString = speString.substring(1);
////		
////		String packageString= "";
////		for(String s : packagename){
////			packageString =  packageString + "," + s ;
////		}
////		packageString = packageString.substring(1);
////		
////		String apkString= "";
////		for(String s : apkname){
////			apkString =  apkString + "," + s ;
////		}
////		apkString = apkString.substring(1);
//		
//
//        logger.error("fileNumber is : "+ fileNumber);
//        StoreManager s = new StoreManager();
//		long missionId = s.startStoreCustomer(fileNumber);
//		
////		s.saveMissionParameter(missionId, apkString, packageString, speString);
//
//        for (int i = 0; i < fileNumber; i++) {  //线程接口为filename
//        	String filepath = fileList.get(i);
//            Runnable worker = new WorkerThread(filepath,specialString,missionId);
//            executor.execute(worker);
//          }
//        executor.shutdown();
//        while (!executor.isTerminated()) {
//        }
//        logger.error("Finished all threads");
//    //    System.out.println("Finished all threads");
//        
//       s.flushApkInfoQueue();
//        logger.error("Finished apk saved");
//        
//        long endTime=System.currentTimeMillis();
//        logger.error("time: " + (endTime-startTime) + "ms");
//    }
// 
//}
