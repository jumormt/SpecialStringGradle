package com.analysis.dex;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.jar.JarFile;

import com.analysis.manifest.ApkExtractor;




public class GetDexMethodId {
	public  boolean MethodId(JarFile fileName , DexMapItemMap DexMapItemMapper) throws IOException{
		
		
		GetFinal getFinal = new GetFinal();
		int flag=getFinal.getFlag(fileName);
		
//	 File file = new File(fileName);
//	 FileInputStream input1=new FileInputStream(file);
//	 DataInputStream input=new DataInputStream(input1);
		
		
		int offset=DexMapItemMapper.getDexMapItemOffset("kDexTypeMethodIdItem");
		int count=DexMapItemMapper.getDexMapItemCount("kDexTypeMethodIdItem");
		
		ApkExtractor apkextractor = new ApkExtractor();
		byte[] temp = apkextractor.getDex(fileName);
		int length = temp.length;		
		DataInputStream input = apkextractor.getDexDataInputStreamWithBuffered(fileName);
		input.mark(length);
		input.skipBytes(offset);
		
		byte[] bufferArray = new byte[4];  
		byte[] bufferArray2= new byte[2];
		
		String classIdx=""; String protoIdx=""; String nameIdx="";
		HashSet methodname = new HashSet<String>();
		
		for(int i=0;i<count;i++){
			classIdx="";  protoIdx="";  nameIdx="";
			input.read(bufferArray2);
			classIdx=DexMapItemMapper.getDexType(getFinal.getEight(bufferArray2));
			methodname.add(classIdx);
			input.read(bufferArray2);
		//	dexproto=DexMapItemMapper.getInstance().getDexProto(GetFinal.getEight(bufferArray2));
		//	protoIdx=dexproto.getShortyIdx();
			input.read(bufferArray);
		//	nameIdx=DexMapItemMapper.getDexString(getFinal.GetSixteen(bufferArray, flag));
		//	methodname.add(nameIdx);
		//	System.out.println(nameIdx);
			
//			DexMethodId dexmethodId= new DexMethodId(classIdx,protoIdx,nameIdx);
//			DexMapItemMapper.getInstance().addDexMethodId(i, dexmethodId);  
		}
     
		//com.weibo.sdk.android.R
	
		boolean r = false; boolean re = false;
		Iterator ite = methodname.iterator();
		while (ite.hasNext())
		{
			String s = (String) ite.next();
	//		System.out.println(s);
			if(s.endsWith("/R;")){
				r=true;
			}
			if(s.contains("/R$")){
				re=true;
			}
		//	System.out.println(ite.next());
		}
		if(r&&re){//未混淆
			return true;
		}     
     return false;
	}
	
	 
}
