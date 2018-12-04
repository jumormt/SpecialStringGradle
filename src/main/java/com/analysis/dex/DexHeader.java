package com.analysis.dex;

import java.io.DataInputStream;
import java.io.IOException;

import java.util.jar.JarFile;

import com.analysis.manifest.ApkExtractor;

public class DexHeader {

	public void readDex(JarFile jarFile, DexMapItemMap DexMapItemMapper) throws IOException {

		int oct = 0;
		GetFinal getFinal = new GetFinal();
		int flag = getFinal.getFlag(jarFile);
		byte[] bufferArray = new byte[4];

		ApkExtractor apkextractor = new ApkExtractor();
		// JarFile jarFile = new JarFile(fileName);
		DataInputStream input2 = apkextractor.getDexDataInputStreamWithBuffered(jarFile);

		if (flag == 1) {
			oct = Integer.parseInt("34", 16);
			input2.skip(oct);
			input2.read(bufferArray);
			input2.close();

			int mapAddress = getFinal.GetSixteen(bufferArray, flag);
			DataInputStream input = apkextractor.getDexDataInputStreamWithBuffered(jarFile);

			input.skip(mapAddress);
			input.read(bufferArray);
			int NumDexMapItem = getFinal.GetSixteen(bufferArray, flag);
			int i = 1;
			byte[] array2 = new byte[2];
			int size1 = 0;
			int offset1 = 0;
			for (i = 1; i <= NumDexMapItem; i++) {
				input.read(array2);
				int type = (int) (((array2[1] & 0xff) << 8) | (array2[0] & 0xff));
				switch (type) {
				case PrivateFinal.kDexTypeHeaderItem:// HeaderItem
					input.skip(2);
					input.read(bufferArray);
					size1 = getFinal.GetSixteen(bufferArray, flag);
					input.read(bufferArray);
					offset1 = getFinal.GetSixteen(bufferArray, flag);
					DexMapItemMapper.addDexMapItemCount("kDexTypeHeaderItem", size1);
					DexMapItemMapper.addDexMapItemOffset("kDexTypeHeaderItem", offset1);
					break;
				case PrivateFinal.kDexTypeStringIdItem:// StringIdItem
					input.skip(2);
					input.read(bufferArray);
					size1 = getFinal.GetSixteen(bufferArray, flag);
					input.read(bufferArray);
					offset1 = getFinal.GetSixteen(bufferArray, flag);
					DexMapItemMapper.addDexMapItemCount("kDexTypeStringIdItem", size1);
					DexMapItemMapper.addDexMapItemOffset("kDexTypeStringIdItem", offset1);
					break;
				case PrivateFinal.kDexTypeTypeIdItem:// TypeIdItem
					input.skip(2);
					input.read(bufferArray);
					size1 = getFinal.GetSixteen(bufferArray, flag);
					input.read(bufferArray);
					offset1 = getFinal.GetSixteen(bufferArray, flag);
					DexMapItemMapper.addDexMapItemCount("kDexTypeTypeIdItem", size1);
					DexMapItemMapper.addDexMapItemOffset("kDexTypeTypeIdItem", offset1);
					break;
				case PrivateFinal.kDexTypeProtoIdItem:// ProtoIdItem
					input.skip(2);
					input.read(bufferArray);
					size1 = getFinal.GetSixteen(bufferArray, flag);
					input.read(bufferArray);
					offset1 = getFinal.GetSixteen(bufferArray, flag);
					DexMapItemMapper.addDexMapItemCount("kDexTypeProtoIdItem", size1);
					DexMapItemMapper.addDexMapItemOffset("kDexTypeProtoIdItem", offset1);
					break;
				case PrivateFinal.kDexTypeFieldIdItem:// FieldIdItem
					input.skip(2);
					input.read(bufferArray);
					size1 = getFinal.GetSixteen(bufferArray, flag);
					input.read(bufferArray);
					offset1 = getFinal.GetSixteen(bufferArray, flag);
					DexMapItemMapper.addDexMapItemCount("kDexTypeFieldIdItem", size1);
					DexMapItemMapper.addDexMapItemOffset("kDexTypeFieldIdItem", offset1);
					break;
				case PrivateFinal.kDexTypeMethodIdItem:// MethodIdItem
					input.skip(2);
					input.read(bufferArray);
					size1 = getFinal.GetSixteen(bufferArray, flag);
					input.read(bufferArray);
					offset1 = getFinal.GetSixteen(bufferArray, flag);
					DexMapItemMapper.addDexMapItemCount("kDexTypeMethodIdItem", size1);
					DexMapItemMapper.addDexMapItemOffset("kDexTypeMethodIdItem", offset1);
					break;
				case PrivateFinal.kDexTypeClassDefItem:// ClassDefIdItem
					input.skip(2);
					input.read(bufferArray);
					size1 = getFinal.GetSixteen(bufferArray, flag);
					input.read(bufferArray);
					offset1 = getFinal.GetSixteen(bufferArray, flag);
					DexMapItemMapper.addDexMapItemCount("kDexTypeClassDefItem", size1);
					DexMapItemMapper.addDexMapItemOffset("kDexTypeClassDefItem", offset1);
					break;
				case PrivateFinal.kDexTypeMapList:// TypeList
					input.skip(2);
					input.read(bufferArray);
					size1 = getFinal.GetSixteen(bufferArray, flag);
					input.read(bufferArray);
					offset1 = getFinal.GetSixteen(bufferArray, flag);
					DexMapItemMapper.addDexMapItemCount("kDexTypeMapList", size1);
					DexMapItemMapper.addDexMapItemOffset("kDexTypeMapList", offset1);
					break;
				case PrivateFinal.kDexTypeTypeList:// TypeList
					input.skip(2);
					input.read(bufferArray);
					size1 = getFinal.GetSixteen(bufferArray, flag);
					input.read(bufferArray);
					offset1 = getFinal.GetSixteen(bufferArray, flag);
					DexMapItemMapper.addDexMapItemCount("kDexTypeTypeList", size1);
					DexMapItemMapper.addDexMapItemOffset("kDexTypeTypeList", offset1);
					break;
				case PrivateFinal.kDexTypeAnnotationSetRefList:// TypeList
					input.skip(2);
					input.read(bufferArray);
					size1 = getFinal.GetSixteen(bufferArray, flag);
					input.read(bufferArray);
					offset1 = getFinal.GetSixteen(bufferArray, flag);
					DexMapItemMapper.addDexMapItemCount("kDexTypeAnnotationSetRefList", size1);
					DexMapItemMapper.addDexMapItemOffset("kDexTypeAnnotationSetRefList", offset1);
					break;
				case PrivateFinal.kDexTypeAnnotationSetItem:// TypeList
					input.skip(2);
					input.read(bufferArray);
					size1 = getFinal.GetSixteen(bufferArray, flag);
					input.read(bufferArray);
					offset1 = getFinal.GetSixteen(bufferArray, flag);
					DexMapItemMapper.addDexMapItemCount("kDexTypeAnnotationSetItem", size1);
					DexMapItemMapper.addDexMapItemOffset("kDexTypeAnnotationSetItem", offset1);
					break;
				case PrivateFinal.kDexTypeClassDataItem:// TypeList
					input.skip(2);
					input.read(bufferArray);
					size1 = getFinal.GetSixteen(bufferArray, flag);
					input.read(bufferArray);
					offset1 = getFinal.GetSixteen(bufferArray, flag);
					DexMapItemMapper.addDexMapItemCount("kDexTypeClassDataItem", size1);
					DexMapItemMapper.addDexMapItemOffset("kDexTypeClassDataItem", offset1);
					break;
				case PrivateFinal.kDexTypeCodeItem:// CodeIdItem
					input.skip(2);
					input.read(bufferArray);
					size1 = getFinal.GetSixteen(bufferArray, flag);
					input.read(bufferArray);
					offset1 = getFinal.GetSixteen(bufferArray, flag);
					DexMapItemMapper.addDexMapItemCount("kDexTypeCodeItem", size1);
					DexMapItemMapper.addDexMapItemOffset("kDexTypeCodeItem", offset1);
					break;
				case PrivateFinal.kDexTypeStringDataItem:// TypeList
					input.skip(2);
					input.read(bufferArray);
					size1 = getFinal.GetSixteen(bufferArray, flag);
					input.read(bufferArray);
					offset1 = getFinal.GetSixteen(bufferArray, flag);
					DexMapItemMapper.addDexMapItemCount("kDexTypeStringDataItem", size1);
					DexMapItemMapper.addDexMapItemOffset("kDexTypeStringDataItem", offset1);
					break;
				case PrivateFinal.kDexTypeDebugInfoItem:// TypeList
					input.skip(2);
					input.read(bufferArray);
					size1 = getFinal.GetSixteen(bufferArray, flag);
					input.read(bufferArray);
					offset1 = getFinal.GetSixteen(bufferArray, flag);
					DexMapItemMapper.addDexMapItemCount("kDexTypeDebugInfoItem", size1);
					DexMapItemMapper.addDexMapItemOffset("kDexTypeDebugInfoItem", offset1);
					break;
				case PrivateFinal.kDexTypeAnnotationItem:// TypeList
					input.skip(2);
					input.read(bufferArray);
					size1 = getFinal.GetSixteen(bufferArray, flag);
					input.read(bufferArray);
					offset1 = getFinal.GetSixteen(bufferArray, flag);
					DexMapItemMapper.addDexMapItemCount("kDexTypeAnnotationItem", size1);
					DexMapItemMapper.addDexMapItemOffset("kDexTypeAnnotationItem", offset1);
					break;
				case PrivateFinal.kDexTypeEncodedArrayItem:// TypeList
					input.skip(2);
					input.read(bufferArray);
					size1 = getFinal.GetSixteen(bufferArray, flag);
					input.read(bufferArray);
					offset1 = getFinal.GetSixteen(bufferArray, flag);
					DexMapItemMapper.addDexMapItemCount("kDexTypeEncodedArrayItem", size1);
					DexMapItemMapper.addDexMapItemOffset("kDexTypeEncodedArrayItem", offset1);
					break;
				case PrivateFinal.kDexTypeAnnotationDirectoryItem:// TypeList
					input.skip(2);
					input.read(bufferArray);
					size1 = getFinal.GetSixteen(bufferArray, flag);
					input.read(bufferArray);
					offset1 = getFinal.GetSixteen(bufferArray, flag);
					DexMapItemMapper.addDexMapItemCount("kDexTypeAnnotationDirectoryItem", size1);
					DexMapItemMapper.addDexMapItemOffset("kDexTypeAnnotationDirectoryItem", offset1);
					break;
				default:
					break;
				}

			}
			input.close();
		}
		// jarFile.close();
	}

}