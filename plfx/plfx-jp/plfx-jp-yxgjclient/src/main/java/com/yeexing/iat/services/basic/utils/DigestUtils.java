/*
 * DigestUtils.java
 * 作者：杨成科
 * 2013-6-21 创建文件
 */
package com.yeexing.iat.services.basic.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 **************************************************
 * 对数据作MD5信息摘要，也可作为加密方式
 * 当将数据信息与唯一密钥组合成新的messge进行摘要时可达到验证数据完整性、来源真实性等功能。
 *                                 
 * @version 1.0    
 * @author  Ckyang                                                                 
 *************************************************
 */
public class DigestUtils {

	/**
	 * 用于二进制向十六进制的转换
	 */
	private static final char[] DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', 
		'8', '9', 'a', 'b', 'c', 'd', 'e','f' };
	
	/**
	 * 编码
	 */
	private static final String CHAR_SET = "UTF-8";
	
	/**
	 * 对数据信息作SHA信息摘要
	 * @param message 数据信息
	 * @param charSet 编码类型
	 * @return 摘要信息
	 * @since 1.0
	 * @author Ckyang 2013-6-21 创建方法
	 */
	public static String shaMessage(String message,String charSet) {
		return digest(message, charSet, "SHA");
	}
	
	/**
	 * 对数据信息作SHA信息摘要，默认编码UTF-8
	 * @param message 数据信息
	 * @return 摘要信息
	 * @since 1.0
	 * @author Ckyang 2013-6-21 创建方法
	 */
	public static String shaMessage(String message) {
		return md5Message(message, CHAR_SET);
	}
	
	/**
	 * 对数据信息作MD5信息摘要
	 * @param message 数据信息
	 * @param charSet 编码类型
	 * @return 摘要信息
	 * @since 1.0
	 * @author Ckyang 2013-6-21 创建方法
	 */
	public static String md5Message(String message,String charSet) {
		return digest(message, charSet, "MD5");
	}
	
	/**
	 * 对数据信息作MD5信息摘要，默认编码UTF-8
	 * @param message 数据信息
	 * @return 摘要信息
	 * @since 1.0
	 * @author Ckyang 2013-6-21 创建方法
	 */
	public static String md5Message(String message) {
		return md5Message(message, CHAR_SET);
	}

	/**
	 * 对数据信息作MD5信息摘要
	 * @param message 数据信息
	 * @param charSet 编码类型
	 * @param digestType 搞要类型
	 * @return 摘要信息
	 * @since 1.0
	 * @author Ckyang 2013-6-21 创建方法
	 */
	public static String digest(String message,String charSet,String digestType) {
		MessageDigest msgDigest = null;
		try {
			msgDigest = MessageDigest.getInstance(digestType);
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException("System doesn't support MD5 algorithm.");
		}

		try {
			msgDigest.update(message.getBytes(charSet));
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException("System doesn't support your  EncodingException.");
		}

		byte[] bytes = msgDigest.digest();
		//因为得到的二进制中有可能某些byte是不可打印的字符，所以需要转换成十六进制
		String md5Str = new String(encodeHex(bytes));
		return md5Str;
	}

	/**
	 * 将二进制转换成十六入进制
	 * @param data 二进制数据
	 * @return 十六进制数据
	 * @since 1.0
	 * @author Ckyang 2013-6-21 创建方法
	 */
	private static char[] encodeHex(byte[] data) {
		int l = data.length;
		char[] out = new char[l << 1];
		for (int i = 0, j = 0; i < l; i++) {
			out[j++] = DIGITS[(0xF0 & data[i]) >>> 4];
			out[j++] = DIGITS[0x0F & data[i]];
		}

		return out;
	}
}
