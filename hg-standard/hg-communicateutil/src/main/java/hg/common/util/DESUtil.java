package hg.common.util;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * DES加密工具类
 * @author zqq
 *
 */
public class DESUtil {

	/**
	* 加密
	* DES加密和解密过程中，密钥长度都必须是8的倍数
	* @param content byte[] 消息
	* @param keyBytes byte[]  密钥
	* @return byte[]
	*/
	public static byte[] encryptDES(byte[] content, byte[] keyBytes) { 
		try{
			SecureRandom random = new SecureRandom();
			DESKeySpec desKey = new DESKeySpec(keyBytes);
			//创建一个密匙工厂，然后用它把DESKeySpec转换成
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey securekey = keyFactory.generateSecret(desKey);
			//Cipher对象实际完成加密操作
			Cipher cipher = Cipher.getInstance("DES");
			//用密匙初始化Cipher对象
			cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
			//现在，获取数据并加密
			//正式执行加密操作
			return cipher.doFinal(content);
		}catch(Throwable e){
			e.printStackTrace();
		}
			return null;
	}
	
	/**
	* 解密
	* DES加密和解密过程中，密钥长度都必须是8的倍数
	* @param content byte[] 密文
	* @param keyBytes byte[] 密钥
	* @return byte[]
	* @throws Exception
	*/
	public static byte[] decryptDES(byte[] content, byte[] keyBytes) throws Exception {
		// DES算法要求有一个可信任的随机数源
		SecureRandom random = new SecureRandom();
		// 创建一个DESKeySpec对象
		DESKeySpec desKey = new DESKeySpec(keyBytes);
		// 创建一个密匙工厂
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		// 将DESKeySpec对象转换成SecretKey对象
		SecretKey securekey = keyFactory.generateSecret(desKey);
		// Cipher对象实际完成解密操作
		Cipher cipher = Cipher.getInstance("DES");
		// 用密匙初始化Cipher对象
		cipher.init(Cipher.DECRYPT_MODE, securekey, random);
		// 真正开始解密操作
		return cipher.doFinal(content);
	}
}
