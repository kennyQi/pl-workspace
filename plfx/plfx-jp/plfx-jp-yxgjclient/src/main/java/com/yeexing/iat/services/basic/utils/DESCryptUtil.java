package com.yeexing.iat.services.basic.utils;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class DESCryptUtil {
	private final static String KEY = "11111111"; // 字节数必须是8的倍数

	/**
	 * 加密
	 * 
	 * @param src
	 * @return
	 */
	public static String encrypt(String src) throws Exception {
		SecureRandom sr = new SecureRandom();
		DESKeySpec ks = new DESKeySpec(KEY.getBytes());
		SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
		SecretKey sk = skf.generateSecret(ks);
		Cipher cip = Cipher.getInstance("DES");
		cip.init(Cipher.ENCRYPT_MODE, sk, sr);
		String dest = byteToHex(cip.doFinal(src.getBytes()));
		return dest;
	}

	/**
	 * 解密
	 * 
	 * @param src
	 * @return
	 */
	public static String decrypt(String src) throws Exception {
		SecureRandom sr = new SecureRandom();
		DESKeySpec ks = new DESKeySpec(KEY.getBytes());
		SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
		SecretKey sk = skf.generateSecret(ks);
		Cipher cip = Cipher.getInstance("DES");
		cip.init(Cipher.DECRYPT_MODE, sk, sr);
		String dest = new String(cip.doFinal(hexToByte(src)));
		return dest;
	}

	private static String byteToHex(byte[] bs) {
		String str = "";
		for (int i = 0; i < bs.length; i++) {
			String s = Integer.toHexString(bs[i]);
			int len = s.length();
			if (len == 1) {// 长度只有1位，那么在前面补0
				s = "0" + s;
			} else if (len > 2) {// 长度大于2位是因为Integer是16位的，所以截取后2位
				s = s.substring(len - 2);
			}
			str += s;
		}
		return str;
	}

	private static byte[] hexToByte(String hex) {
		byte[] srcBs = hex.getBytes();
		byte[] desBs = new byte[srcBs.length / 2];
		for (int i = 0; i < srcBs.length; i += 2) {
			byte b = (byte) Integer.parseInt(new String(srcBs, i, 2), 16);
			desBs[i / 2] = b;
		}
		return desBs;
	}

}
