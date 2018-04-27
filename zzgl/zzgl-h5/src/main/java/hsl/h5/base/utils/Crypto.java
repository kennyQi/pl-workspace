package hsl.h5.base.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 字符串加密器
 * @author 胡永伟
 */
public final class Crypto {

	private Crypto() {}
	
	public static String sha1(String src) {
		return encrypt(src, "SHA1");
	}
	
	public static String sha1(byte[] src) {
		return encrypt(src, "SHA1");
	}
	
	public static String md5(String src) {
		return encrypt(src, "MD5");
	}
	
	public static String md5(byte[] src) {
		return encrypt(src, "MD5");
	}
	
	private static String encrypt(byte[] src, String type) {
		try {
			MessageDigest digester = MessageDigest.getInstance(type);
			byte[] buf = digester.digest(src);
			return encode(buf);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	private static String encrypt(String src, String type) {
		return encrypt(src.getBytes(), type);
	}

	private static String encode(byte[] buf) {
		StringBuilder sb = new StringBuilder(buf.length * 2);
		for (int i = 0; i < buf.length; i++) {
			if (((int) buf[i] & 0xff) < 0x10) {
				sb.append("0");
			}
			sb.append(Integer.toHexString(buf[i] & 0xff));
		}
		return sb.toString();
	}

}
