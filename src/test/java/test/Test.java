/**
 * 
 */
package test;

/**
 * @author huge
 *
 * 2014年8月5日
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.analysis.manifest.FileAction;
import com.analysis.manifest.FormatParse;
import com.analysis.manifest.RuleFresh;
import com.softsec.tase.store.StoreManager;
 
public class Test {
	
	private static int Pool_Max = 4;	
	static Logger logger = LogManager.getLogger(Test.class);
 
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(Pool_Max);
        
        RuleFresh.RuleExe();
            
        long startTime=System.currentTimeMillis();   //获取开始时间  
        System.out.println("程序开始运行时间： "+(startTime)+"ms");
    
		String[] testmain = {"-f","E:\\work\\test\\zip","-p","dfa","dfese","-s","1234567890987654321","com","-n","hjghtd","ojfidg"};     
        
        FormatParse format = new FormatParse();
        HashMap<String,List<String>> mainTemp = format.getCommand(testmain);
        
        String apkpath = mainTemp.get("filepath").get(0);
		List<String>fileList = new ArrayList<String>(); 
		FileAction.CalcalateAllFile(apkpath, fileList);
		
		List<String> specialString = mainTemp.get("special");		
		List<String> packagename = mainTemp.get("packagename");
		List<String> apkname = mainTemp.get("apkname");
		
		String speString= "";
		for(String s : specialString){
			speString =  speString + "," + s ;
		}
		speString = speString.substring(1);
		
		String packageString= "";
		for(String s : packagename){
			packageString =  packageString + "," + s ;
		}
		packageString = packageString.substring(1);
		
		String apkString= "";
		for(String s : apkname){
			apkString =  apkString + "," + s ;
		}
		apkString = apkString.substring(1);

     //   specialString.add("com");
     //   specialString.add("16858518145424727649933056498660090257938028834024614531807279123004174432243450701435915480207331070058018980640996970114664924595721996462420633436577638037200905330595086422869013811312746989152070453400757153846226677525749706019764596866640120258421254940021321226583202460629795560285891968341710281094872056200491420699403567143946437948332885658783820093598141803165201037010008079307300280225532863042313106598043518026300425994352469437334294025333467781054481930686780394079520231376683030299347953241192226954447774155252005439623341985847608294040377722245597091482547502849289313590160972967892168922580");

        int fileNumber = fileList.size();//获取总的任务量      
        System.out.println("fileNumber: " + fileNumber);
        StoreManager s = new StoreManager();
        long missionId = s.startStoreCustomer();
   //     s.saveMissionParameter(missionId, apkString, packageString, speString);

        for (int i = 0; i < fileNumber; i++) {  //线程接口为filename
        	String filepath = fileList.get(i);
//            Runnable worker = new WorkerThread(filepath,specialString,missionId);
//            executor.execute(worker);
          }
        executor.shutdown();
        while (!executor.isTerminated()) {
        }
        System.out.println("Finished all threads");
        System.out.println("Finished all threads time： "+ (System.currentTimeMillis()-startTime) );
        
//        s.flushApkInfoQueue();
//        System.out.println("flush:" + s.flushApkInfoQueue() );
        
        long endTime=System.currentTimeMillis(); //获取结束时间  
        System.out.println("程序结束时间： "+(endTime)+"ms");
        System.out.println("程序运行时间： "+(endTime-startTime)+"ms");
        
    }
 
}
