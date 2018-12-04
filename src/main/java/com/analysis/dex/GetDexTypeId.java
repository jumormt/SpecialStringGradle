package com.analysis.dex;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.jar.JarFile;

import com.analysis.manifest.ApkExtractor;


public class GetDexTypeId {
	
	
	public static void TypeId(JarFile fileName , DexMapItemMap DexMapItemMapper) throws IOException {

		GetFinal getFinal = new GetFinal();
		int flag=getFinal.getFlag(fileName);
		
		int offset=DexMapItemMapper.getDexMapItemOffset("kDexTypeTypeIdItem");
		int count=DexMapItemMapper.getDexMapItemCount("kDexTypeTypeIdItem");
		byte[] bufferArray = new byte[4];
		
		ApkExtractor apkextractor = new ApkExtractor();
		byte[] temp = apkextractor.getDex(fileName);
		int length = temp.length;		
		DataInputStream input = apkextractor.getDexDataInputStreamWithBuffered(fileName);
		input.mark(length);
		input.skipBytes(offset);
		
		String TypeStr="";
		for(int i =0;i<count;i++){
	    	 
	    	 input.read(bufferArray);
		     int TypeOffset=getFinal.GetSixteen(bufferArray, flag);
		     TypeStr=DexMapItemMapper.getDexString(TypeOffset);
		     DexMapItemMapper.addDexTypeId(i, TypeStr);
	    	 
	     }      
       
	}

}
