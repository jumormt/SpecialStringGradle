package com.analysis.manifest;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
//
//import com.sun.org.apache.bcel.internal.generic.NEW;
//import com.sun.xml.internal.ws.util.ByteArrayBuffer;

public class ApkExtractor {

	// public static Logger logger = LogManager.getLogger(apkExtractor.class);
	private static int JSize1 = -1;
	private static int JSize2 = -1;
	private static int JSize3 = -1;

	// public static void main(String[] argv) {
	//
	//
	// DataInputStream c =
	// getDexDataInputStreamWithBuffered("E:/work/Android静态检测/fq.router2-2.apk");
	// byte data3[] = new byte[JSize3];
	// try {
	// for (int i = 0; i < 10; i++) {
	// byte[] testStr = new byte[5];
	// // logger.info(c.markSupported());
	// c.mark(0);
	// c.read(testStr, 0, 4);
	// // c.skipBytes(1);
	// c.reset();
	// System.out.println(testStr);
	// // logger.info(new String(testStr));
	// }
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } finally{
	// try {
	// c.close();
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }
	// // logger.info("DataInputStream Read Size: " + new
	// String(data3).length());
	//
	// }
	@SuppressWarnings("resource")
	public byte[] getDex(JarFile jarFile) {
//		JarFile jarFile;
		try {
//			jarFile = new JarFile(filePathString);
			Enumeration<JarEntry> entries = jarFile.entries();
			while (entries.hasMoreElements()) {
				final JarEntry je = entries.nextElement();
				if (je.getName().contains("classes.dex")) {
					// logger.info(je.getSize());
					int fileSize = (int) je.getSize();
					byte data[] = new byte[fileSize];
					InputStream is = new BufferedInputStream(jarFile.getInputStream(je));
					is.read(data, 0, fileSize);
					return data;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("resource")
	public static InputStream getDexInputStream(JarFile jarFile) {
		// JarFile jarFile;
		try {
			// jarFile = new JarFile(filePathString);
			Enumeration<JarEntry> entries = jarFile.entries();
			while (entries.hasMoreElements()) {
				final JarEntry je = entries.nextElement();
				if (je.getName().contains("classes.dex")) {
					// logger.info("InputStream way: " + je.getSize());
					JSize1 = (int) je.getSize();
					InputStream is = new BufferedInputStream(jarFile.getInputStream(je));
					return is;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("resource")
	public static DataInputStream getDexDataInputStream(String filePathString) {
		JarFile jarFile;
		try {
			jarFile = new JarFile(filePathString);
			Enumeration<JarEntry> entries = jarFile.entries();
			while (entries.hasMoreElements()) {
				final JarEntry je = entries.nextElement();
				if (je.getName().contains("classes.dex")) {
					// logger.info("DataInputStream way: " + je.getSize());
					JSize2 = (int) je.getSize();
					DataInputStream dis = new DataInputStream(jarFile.getInputStream(je));
					return dis;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("resource")
	public DataInputStream getDexDataInputStreamWithBuffered(JarFile jarFile) {
		// JarFile jarFile;
		try {
			// jarFile = new JarFile(filePathString);
			Enumeration<JarEntry> entries = jarFile.entries();
			while (entries.hasMoreElements()) {
				final JarEntry je = entries.nextElement();
				if (je.getName().contains("classes.dex")) {
					// logger.info("DataInputStream way: " + je.getSize());
					JSize3 = (int) je.getSize();
					DataInputStream dis = new DataInputStream(new BufferedInputStream(jarFile.getInputStream(je)));
					return dis;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("resource")
	public boolean getManifest(JarFile jarFile) {
		Enumeration<JarEntry> entries = jarFile.entries();
		while (entries.hasMoreElements()) {
			final JarEntry je = entries.nextElement();
			if (je.getName().contains("AndroidManifest.xml")) {
				// logger.info("DataInputStream way: " + je.getSize());
				return true;
			}
		}
		return false;
	}
}
