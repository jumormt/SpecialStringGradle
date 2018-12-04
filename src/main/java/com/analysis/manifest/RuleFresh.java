/**
 * 
 */
package com.analysis.manifest;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author huge
 *
 * 2014年8月14日
 */
public class RuleFresh {  
    public static void RuleExe() {  
        //调用schedule方法执行任务  
        new Timer().schedule(new TimerTask(){   
            public void run() {
            	//TODO 扫描规则库
                System.out.println("boom...");  
            }  
        },0,3000);//过10秒执行，之后每隔3秒执行一次  
          
        //每隔5分钟扫一次规则库  
//        while(true) {  
//            System.out.println(new Date().getSeconds());  
//            try{  
//                Thread.sleep(1000);  
//            }catch(InterruptedException e) {  
//                e.printStackTrace();  
//            }  
//        }  
    }  
} 