package hg.common.util;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * RSA算法，实现数据的加密解密。
 * 
 * @author zqq
 * 
 */
public class RSAUtil {

	private static Cipher cipher;

	static {
		try {
			cipher = Cipher.getInstance("RSA");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 生成密钥对
	 * @author zqq
	 * @since hg-common
	 * @date 2016-5-5 下午2:32:39 
	 * @return
	 */
	public static KeyPairEntity generateKeyPair(/* String filePath */) {
		try {
			KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
			// 密钥位数
			keyPairGen.initialize(1024);
			// 密钥对
			KeyPair keyPair = keyPairGen.generateKeyPair();
			// 公钥
			PublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
			// 私钥
			PrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
			// 将生成的密钥对返回
			KeyPairEntity keyPartStr = new KeyPairEntity(publicKey, privateKey);
			return keyPartStr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 得到公钥
	 * @author zqq
	 * @since hg-common
	 * @date 2016-5-5 下午2:32:52 
	 * @param publicKeyBytes 密钥byte[]
	 * @return
	 * @throws Exception
	 */
	private static PublicKey getPublicKey(byte[] publicKeyBytes)
			throws Exception {
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey publicKey = keyFactory.generatePublic(keySpec);
		return publicKey;
	}

	/**
	 * 得到私钥
	 * @author zqq
	 * @since hg-common
	 * @date 2016-5-5 下午2:34:00 
	 * @param privateKeyBytes 密钥byte[]
	 * @return
	 * @throws Exception
	 */
	private static PrivateKey getPrivateKey(byte[] privateKeyBytes)
			throws Exception {
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
		return privateKey;
	}

	/**
	 * 使用公钥对明文进行加密
	 * @author zqq
	 * @since hg-common
	 * @date 2016-5-5 下午2:34:23 
	 * @param publicKey
	 * @param contentBytes
	 * @return
	 */
	public static byte[] encrypt(byte[] publicKey, byte[] contentBytes) {
		try {
			if (contentBytes.length > 1024) {
				throw new RuntimeException("明文要小于1K");
			}
			cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(publicKey));
			byte[] enBytes = cipher.doFinal(contentBytes);
			return enBytes;
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 使用公钥对明文进行加密
	 * @author zqq
	 * @since hg-common
	 * @date 2016-5-5 下午2:34:23 
	 * @param publicKey
	 * @param contentBytes
	 * @return
	 */
	public static String encrypt(byte[] publicKey, String content) {
		try {
			if (content.getBytes().length > 1024) {
				throw new RuntimeException("明文要小于1K");
			}
			cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(publicKey));
			byte[] enBytes = cipher.doFinal(content.getBytes("utf-8"));
			return Base64Utils.Base64Encode(enBytes);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 使用私钥对明文密文进行解密
	 * @author zqq
	 * @since hg-common
	 * @date 2016-5-5 下午2:34:56 
	 * @param privateKey
	 * @param enBytes
	 * @return
	 */
	public static byte[] decrypt(byte[] privateKey, byte[] enBytes) {
		try {
			cipher.init(Cipher.DECRYPT_MODE, getPrivateKey(privateKey));
			byte[] deBytes = cipher.doFinal(enBytes);
			return deBytes;
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 使用私钥对明文密文进行解密
	 * @author zqq
	 * @since hg-common
	 * @date 2016-5-5 下午2:34:56 
	 * @param privateKey
	 * @param enBytes base64编码的加密字串
	 * @return
	 */
	public static String decrypt(byte[] privateKey, String enBytes) {
		try {
			cipher.init(Cipher.DECRYPT_MODE, getPrivateKey(privateKey));
			byte[] deBytes = cipher.doFinal(Base64Utils.Base64Decode(enBytes));
			return new String(deBytes, "utf-8");
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}