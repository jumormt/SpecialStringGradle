package com.analysis.manifest;

/**
 * apk signature result
 * @author ������
 * @since 2013-04-27 11:38:00
 */
import java.io.IOException;
import java.math.BigInteger;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.jar.JarFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.security.auth.x500.X500Principal;

import org.apache.commons.net.util.Base64;
import org.yaml.snakeyaml.parser.ParserException;

import sun.security.x509.CertificateAlgorithmId;
import sun.security.x509.CertificateIssuerName;
import sun.security.x509.CertificateSerialNumber;
import sun.security.x509.CertificateValidity;
import sun.security.x509.CertificateVersion;
import sun.security.x509.X500Name;
import sun.security.x509.X509CertImpl;
import sun.security.x509.X509CertInfo;

import com.softsec.tase.store.apk.ApkSignature;
import com.softsec.tase.store.util.StringUtils;

public class ApkSignatureExtractor {

	/**
	 * get apk signature info from apk file
	 * 
	 * @param apkPath
	 * @return apkSignature
	 * @throws ParserException
	 * @throws CertificateException
	 * @throws IOException
	 */
	public static List<ApkSignature> getApkSignature(JarFile jarFile) throws CertificateException, IOException {
		Certificate[] certificates = null;
		try {
			certificates = CertificateUtils.getCertificates(jarFile);
		} catch (Exception e) {
			return null;
		}

		List<ApkSignature> apkSignatureList = new ArrayList<ApkSignature>();
		if (certificates != null && certificates.length > 0) {
			for (int i = 0; i < certificates.length; i++) {
				ApkSignature apkSignature = getApkCertificate(certificates[i]);
				apkSignatureList.add(apkSignature);
			}
		}
		certificates = null;
		return apkSignatureList;
	}

	/**
	 * get apk certificate info from certificate extracted from apk file
	 * 
	 * @param certificate
	 * @param publicKeyModulus
	 * @param publicExponent
	 * @return apkCertificate
	 * @throws CertificateException
	 * @throws IOException
	 */
	private static ApkSignature getApkCertificate(Certificate certificate) throws CertificateException, IOException {
		ApkSignature apkSignature = new ApkSignature();
		/** get certificate type */
		String certType = null;
		certType = certificate.getType();
		if (certType != null && !certType.isEmpty()) {
			apkSignature.setCertificateType(certType);
		}
		/** get certificate public key */
		PublicKey publicKey = certificate.getPublicKey();
		String publicKeyString = null;
		if (publicKey != null) {
			publicKeyString = publicKey.toString();
		}
		if (publicKeyString != null && !publicKeyString.isEmpty()) {
			Matcher modulusMatcher = Pattern.compile("modulus:\\s+(\\d+)\\s+public").matcher(publicKeyString);
			String publicKeyModulus = null;
			while (modulusMatcher.find()) {
				publicKeyModulus = modulusMatcher.group(1);
			}
			if (publicKeyModulus != null && !publicKeyModulus.isEmpty()) {
				apkSignature.setPublicKeyModulus(publicKeyModulus);
			}
			Matcher exponentMatcher = Pattern.compile("exponent:\\s+(\\d+)").matcher(publicKeyString);
			String publicExponent = null;
			while (exponentMatcher.find()) {
				publicExponent = exponentMatcher.group(1);
			}
			if (publicExponent != null && !publicExponent.isEmpty()) {
				apkSignature.setPublicKeyExponent(publicExponent);
			}
		}
		apkSignature.setCertificateHashCode(certificate.hashCode());
		
		/** get certificate info */
		X509CertImpl x509Cert = (X509CertImpl) certificate;
		apkSignature.setVersion(String.valueOf(x509Cert.getVersion()));
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
		Date fromDate = x509Cert.getNotBefore();
		if(fromDate != null) {
			apkSignature.setFromDate(dateFormat.format(fromDate));
		}
		Date toDate = x509Cert.getNotAfter();
		if(toDate != null) {
			apkSignature.setToDate(dateFormat.format(toDate));
		}
		apkSignature.setAlgorithm(x509Cert.getSigAlgName());
		BigInteger serialNumber = x509Cert.getSerialNumber();
		if(serialNumber != null) {
			apkSignature.setSerialNumber(StringUtils.byteArrayToHexString(serialNumber.toByteArray()));
		}
		byte[] signature = x509Cert.getSignature();
		if(signature != null) {
			apkSignature.setCertificateContent(StringUtils.byteArrayToHexString(signature));
		}
		X500Principal subject = x509Cert.getSubjectX500Principal();
		if(subject != null) {
			apkSignature.setSubject(subject.toString());
		}
		X500Principal issuer = x509Cert.getIssuerX500Principal();
		if(issuer != null) {
			apkSignature.setIssuer(issuer.toString());
		}
		return apkSignature;
	}
}
