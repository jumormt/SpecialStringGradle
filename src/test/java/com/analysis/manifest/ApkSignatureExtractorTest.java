package com.analysis.manifest;

import static org.junit.Assert.*;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.util.List;
import java.util.jar.JarFile;

import org.apache.ibatis.session.SqlSession;
import org.junit.AfterClass;
import org.junit.Test;

import com.softsec.tase.store.apk.ApkSignature;
import com.softsec.tase.store.dao.FileDao;
import com.softsec.tase.store.service.FileStorageService;
import com.softsec.tase.store.util.db.SQLMapperFactory;

public class ApkSignatureExtractorTest {

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void test() {
		List<ApkSignature> apkSignature = null;
		JarFile jarFile;
		try {
			jarFile = new JarFile("D:\\test\\0202c86c1eb8ef9e3febbe62fbabada0.apk");
			apkSignature = ApkSignatureExtractor.getApkSignature(jarFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		SqlSession session = null;
		try {
			session = SQLMapperFactory.openSession();
			FileDao fileDao = new FileDao(session);
			fileDao.saveFileApkSignature("123", apkSignature);
			session.commit();
		} catch(Exception e) {
			
		}
	}

}
