package com.analysis.dex;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.jar.JarFile;

import com.analysis.manifest.ApkExtractor;

public class GetDexStringId {

	public void StringId(JarFile jarFile, DexMapItemMap DexMapItemMapper) throws IOException {
		GetFinal getFinal = new GetFinal();
		int flag = getFinal.getFlag(jarFile);
		byte[] bufferArray = new byte[4];

		ApkExtractor apkextractor = new ApkExtractor();
		byte[] temp = apkextractor.getDex(jarFile);
		int length = temp.length;
		DataInputStream input = apkextractor.getDexDataInputStreamWithBuffered(jarFile);
		input.mark(length);
		input.skipBytes(112);
		int count = DexMapItemMapper.getDexMapItemCount("kDexTypeStringIdItem");
		int offset = 0; //有问题
		for (int i = 0; i < count; i++) {
			input.read(bufferArray);
			offset = getFinal.GetSixteen(bufferArray, flag);
		//	System.out.println("offset: " + offset);
			DexMapItemMapper.addDexMapString(i, offset);
		}

		String MutfString = "";
		 int StringNum=0;
		input.reset();
		for (int j = 0; j < count; j++) {
			StringBuilder input2 = new StringBuilder();
			offset = DexMapItemMapper.getDexStringOffset(j);
			
		//	input.mark(length);
			input.skip(offset);
			MutfString = "";
			byte read = 0;
			Uleb128 u = new Uleb128();
			StringNum=u.readLeb128(input);
			while ((read = input.readByte()) != 0) {
				input2.append((char) (read));
			}
						
			MutfString = input2.toString();
			input2.replace(0, input2.length(), "");
//			if(StringNum>=1000){
//				System.out.println("j : " + j + "  offset : " + offset + "  num : " + StringNum);
//				System.out.println("MutfString : " + MutfString);
//			}
		//	System.out.println(offset + "	"+MutfString);
			if(StringNum<2000){
				DexMapItemMapper.addDexStringId(j, MutfString);				
			}
			MutfString = null;
			input.reset();
		}
		input.close();
		// jarFile.close();
	}

}
