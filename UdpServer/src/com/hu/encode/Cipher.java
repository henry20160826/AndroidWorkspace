package com.hu.encode;

import java.io.ObjectInputStream.GetField;

import org.apache.commons.codec.binary.Base64;

public class Cipher {
	private static final char[] UC_ENCRYPT_CHARS = { 'H', 'D', 'X', 'U', 'P',
			'I', 'B', 'E', 'J', 'C', 'T', 'N', 'K', 'O', 'G', 'W', 'R', 'S',
			'F', 'Y', 'V', 'L', 'Z', 'Q', 'A', 'M' };

	private static final char[] LC_ENCRYPT_CHARS = { 'o', 'g', 'w', 'r', 's',
			'h', 'd', 'x', 'u', 'p', 'i', 'b', 'e', 'z', 'q', 'a', 'm', 'j',
			'c', 't', 'n', 'k', 'f', 'y', 'v', 'l' };

	private static char[] UC_DECRYPT_CHARS = new char[26];

	private static char[] LC_DECRYPT_CHARS = new char[26];

	static {
		for (int i = 0; i < 26; i++) {
			char b = UC_ENCRYPT_CHARS[i];
			UC_DECRYPT_CHARS[b - 'A'] = (char) ('A' + i);
			// System.out.println("UC:" + UC_DECRYPT_CHARS);

			b = LC_ENCRYPT_CHARS[i];
			LC_DECRYPT_CHARS[b - 'a'] = (char) ('a' + i);
			// System.out.println("LC:" + LC_DECRYPT_CHARS);
		}
	}

	// TODO ¼ÓÃÜ
	public static char encrypt(char b) {
		if (b >= 'A' && b <= 'Z') {
			return UC_ENCRYPT_CHARS[b - 'A'];
		} else if (b >= 'a' && b <= 'z') {
			return LC_ENCRYPT_CHARS[b - 'a'];
		} else {
			return b;
		}
	}

	// TODO ½âÃÜ
	public static char decrypt(char b) {
		if (b >= 'A' && b <= 'Z') {
			return UC_DECRYPT_CHARS[b - 'A'];
		} else if (b >= 'a' && b <= 'z') {
			return LC_DECRYPT_CHARS[b - 'a'];
		} else {
			return b;
		}
	}

	public static String encrypt(String input) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < input.length(); i++) {
			sb.append(encrypt(input.charAt(i)));
		}
		return sb.toString();
	}

	public static String decrypt(String input) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < input.length(); i++) {
			sb.append(decrypt(input.charAt(i)));
		}
		return sb.toString();
	}

	public static String encode(String input){
		for(int i=0;i<2;i++){
//			input=Base64.encodeToString(input.getBytes());
			byte[] in=Base64.encodeBase64(input.getBytes());
			input=encrypt(in.toString());
		}
		return input;
	}
	
	public static String decode(String input){
		for(int i=0;i<2;i++){
			input=decrypt(input);
			byte[] returnByte=Base64.decodeBase64(input);
			input=new String(returnByte);
		}
		return input;
	}
}