package com.analysis.dex;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.jar.JarFile;

import com.analysis.manifest.ApkExtractor;

public class GetFinal {

	public int GetSixteen(byte bufferArray[], int flag) {
		int b1 = 0;
		if (flag == 1) {
			b1 = (int) (((bufferArray[3] & 0xff) << 24) | ((bufferArray[2] & 0xff) << 16) | ((bufferArray[1] & 0xff) << 8) | (bufferArray[0] & 0xff));
		} else if (flag == 0) {
			b1 = (int) ((bufferArray[2] & 0xff) << 16 | (bufferArray[3] & 0xff) << 16 | (bufferArray[0] & 0xff) | (bufferArray[1] & 0xff));
		}
		return b1;
	}

	public int getFlag(JarFile jarFile) throws IOException {
		ApkExtractor apkextractor = new ApkExtractor();
		// JarFile jarFile = new JarFile(fileName);
		DataInputStream input = apkextractor.getDexDataInputStreamWithBuffered(jarFile);
		int flag = 0;
		int oct = Integer.parseInt("28", 16);
		byte[] bufferArray = new byte[4];

		input.skip(oct);
		input.read(bufferArray);
		byte s1 = bufferArray[0];
		byte s2 = bufferArray[1];
		if (s1 > s2) {
			flag = 1;
		}
		input.close();
		// jarFile.close();
		return flag;
	}

	public int getEight(byte bufferArray[]) {
		int b1 = 0;
		b1 = (int) (((bufferArray[1] & 0xff) << 8) | (bufferArray[0] & 0xff));
		return b1;
	}

}
