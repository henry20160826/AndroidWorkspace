package com.hu.encode;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * �����ṩgetMD5(String)����
 * 
 * @author randyjia
 * 
 */
public class MD5 {

	public static String getMD5(String val) {
		byte[] m = null;
		try {
			MessageDigest md5;
			md5 = MessageDigest.getInstance("MD5");
			md5.update(val.getBytes());
			m = md5.digest();// ����

		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return getString(m);
	}

	private static String getString(byte[] b) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			sb.append(b[i]);
		}
		return sb.toString();
	}
}
