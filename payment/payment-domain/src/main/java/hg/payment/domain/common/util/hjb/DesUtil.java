package hg.payment.domain.common.util.hjb;

import java.net.URLDecoder;
import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;


import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class DesUtil {
	private static SecretKey key;
	private static String KEY_STR="myKey";
	
	
	
	static{
		try {
			
			KEY_STR = Md5DegistUtil.getMd5Str(KEY_STR, "MD5");
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			//KeyGenerator generator = KeyGenerator.getInstance("DES");
//			generator.init(new SecureRandom(KEY_STR.getBytes("UTF-8")));
//			key = generator.generateKey();
//			generator = null;
			DESKeySpec keSpec = new DESKeySpec(KEY_STR.getBytes("UTF-8"));
			keyFactory.generateSecret(keSpec);
			key = keyFactory.generateSecret(keSpec);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static String getEncryptString(String str){
		BASE64Encoder base64en = new BASE64Encoder();
		try {
			byte[] strBytes = str.getBytes("UTF-8");
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] encryptStrBytes = cipher.doFinal(strBytes);
			return base64en.encode(encryptStrBytes);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static String getDecryptString(String str){
		BASE64Decoder base64De =new BASE64Decoder();
		try {
			byte[] strBytes = base64De.decodeBuffer(str);
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] decryptStrBytes = cipher.doFinal(strBytes);
			return new String(URLDecoder.decode( new String(decryptStrBytes,"UTF-8"),"UTF-8"));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
}	
