/**
 * 
 */
package com.analysis.manifest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author huge
 *
 * 2014年12月3日
 */
public class ReadRules {
	
	public List<String> readRules(){
		
		String fileSeparator = System.getProperty("file.separator");
		String rulepath = System.getProperty("user.dir") + fileSeparator + "conf" + fileSeparator + "rules.txt";
		List<String> ruleList = new ArrayList<String>();
		File file = new File(rulepath);
		BufferedReader reader = null;
        try {
       //     System.out.println("以行为单位读取文件内容，一次读一整行：");
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
        //       System.out.println("line " + line + ": " + tempString);
                line++;
                ruleList.add(tempString);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
		return ruleList;
	}

	public List<List<String>> readLibRules(){
		
		String fileSeparator = System.getProperty("file.separator");
		String rulepath = System.getProperty("user.dir") + fileSeparator + "conf" + fileSeparator + "jiagurules-all.txt";
		List<List<String>> ruleList = new ArrayList<List<String>>();
		File file = new File(rulepath);
		BufferedReader reader = null;
        try {
       //     System.out.println("以行为单位读取文件内容，一次读一整行：");
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
        //       System.out.println("line " + line + ": " + tempString);
                line++;
                String temp = null;
                List<String> temprule = new ArrayList<String>();
                if(!tempString.startsWith("//")){
                	temprule.add(tempString); 
                }
                temp = reader.readLine();
                while( (temp != null)&&(!temp.startsWith("//")) ){
                	temprule.add(temp); 
                	temp = reader.readLine();
                }
                ruleList.add(temprule);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
		return ruleList;
	}
	
	
}
