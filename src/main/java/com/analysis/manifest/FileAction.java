package com.analysis.manifest;



import java.io.File;
import java.util.List;

public class FileAction {
	 public static void CalcalateAllFile(String strPath, List<String> returnList){
			File f = new File(strPath);
			if(f != null & f.exists() && f.isDirectory()){
				File[] flist = f.listFiles();
				for (int i = 0; i < flist.length; i++) {
					CalcalateAllFile(flist[i].getAbsolutePath(), returnList);
				}
			}
			else if (f != null & f.exists() && f.isFile() && f.getAbsolutePath().endsWith(".apk")) {
				returnList.add(f.getAbsolutePath());
			}
			else{
				
			}
	 }
}
