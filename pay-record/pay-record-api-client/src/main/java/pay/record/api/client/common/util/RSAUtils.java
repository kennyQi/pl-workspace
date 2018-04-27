package pay.record.api.client.common.util;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Hex;

import sun.security.rsa.RSAPrivateCrtKeyImpl;
import sun.security.rsa.RSAPublicKeyImpl;

public class RSAUtils {

	/**
	 * 生成公钥和私钥
	 *
	 * @throws NoSuchAlgorithmException
	 */
	public static HashMap<String, Object> getKeys() throws NoSuchAlgorithmException {
		HashMap<String, Object> map = new HashMap<String, Object>();
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
		keyPairGen.initialize(1024);
		KeyPair keyPair = keyPairGen.generateKeyPair();
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		map.put("public", publicKey);
		map.put("private", privateKey);
		return map;
	}

	/**
	 * 使用模和指数生成RSA公钥
	 * 注意：【此代码用了默认补位方式，为RSA/None/PKCS1Padding，不同JDK默认的补位方式可能不同，如Android默认是RSA
	 * /None/NoPadding】
	 *
	 * @param modulus  模
	 * @param exponent 指数
	 * @return
	 */
	public static RSAPublicKey getPublicKey(String modulus, String exponent) {
		try {
			BigInteger b1 = new BigInteger(modulus);
			BigInteger b2 = new BigInteger(exponent);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			RSAPublicKeySpec keySpec = new RSAPublicKeySpec(b1, b2);
			return (RSAPublicKey) keyFactory.generatePublic(keySpec);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 使用模和指数生成RSA私钥
	 * 注意：【此代码用了默认补位方式，为RSA/None/PKCS1Padding，不同JDK默认的补位方式可能不同，如Android默认是RSA
	 * /None/NoPadding】
	 *
	 * @param modulus  模
	 * @param exponent 指数
	 * @return
	 */
	public static RSAPrivateKey getPrivateKey(String modulus, String exponent) {
		try {
			BigInteger b1 = new BigInteger(modulus);
			BigInteger b2 = new BigInteger(exponent);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(b1, b2);
			return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 公钥加密
	 *
	 * @param data
	 * @param publicKey
	 * @return
	 * @throws Exception
	 */
	public static String encryptByPublicKey(String data, RSAPublicKey publicKey)
			throws Exception {

		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);

		// 模长
		int key_len = publicKey.getModulus().bitLength() / 8;

		// 加密数据长度 <= 模长-11
		List<byte[]> datas = splitBytes(data, key_len - 11);
		StringBuilder mi = new StringBuilder();

		// 如果明文长度大于模长-11则要分组加密
		for (byte[] s : datas) {
			mi.append(new String(Hex.encodeHex(cipher.doFinal(s))));
		}

		return mi.toString();
	}

	/**
	 * 私钥解密
	 *
	 * @param data
	 * @param privateKey
	 * @return
	 * @throws Exception
	 */
	public static String decryptByPrivateKey(String data, RSAPrivateKey privateKey)
			throws Exception {

		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, privateKey);

		// 模长
		int key_len = privateKey.getModulus().bitLength() / 8;
		// 解码
		byte[] bcd = Hex.decodeHex(data.toCharArray());

		// 如果密文长度大于模长则要分组解密
		List<byte[]> dat = splitBytes(bcd, key_len);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		for (byte[] arr : dat) {
			baos.write(cipher.doFinal(arr));
		}
		baos.close();

		return baos.toString("utf-8");
	}

	/**
	 * 拆分字符串
	 */
	public static List<byte[]> splitBytes(byte[] data, int len) throws UnsupportedEncodingException {
		List<byte[]> bytes = new ArrayList<byte[]>();
		for (int i = 0; i < data.length; i += len) {
			int dl = i + len > data.length ? data.length - i : len;
			byte[] cache = new byte[dl];
			System.arraycopy(data, i, cache, 0, dl);
			bytes.add(cache);
		}
		return bytes;
	}

	/**
	 * 拆分字符串
	 */
	public static List<byte[]> splitBytes(String string, int len) throws UnsupportedEncodingException {
		return splitBytes(string.getBytes("utf-8"), len);
	}

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		HashMap<String, Object> map = RSAUtils.getKeys();
		//生成公钥和私钥
		RSAPublicKey _pubKey = (RSAPublicKey) map.get("public");
		RSAPrivateKey _priKey = (RSAPrivateKey) map.get("private");

//		RSAPublicKeyImpl.parse()
		System.out.println(_pubKey.getClass());
		System.out.println(new String(Hex.encodeHex(_pubKey.getEncoded())));
		RSAPublicKeyImpl publicKey = new RSAPublicKeyImpl(_pubKey.getEncoded());
		RSAPrivateCrtKeyImpl privateKey = (RSAPrivateCrtKeyImpl) RSAPrivateCrtKeyImpl.newKey(_priKey.getEncoded());
		System.out.println(_priKey.getClass());
		System.out.println(new String(Hex.encodeHex(_priKey.getEncoded())));

		//模
		String modulus = publicKey.getModulus().toString();
		//公钥指数
		String public_exponent = publicKey.getPublicExponent().toString();
		//私钥指数
		String private_exponent = privateKey.getPrivateExponent().toString();
		//明文
		String ming = "你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好";
		//使用模和指数生成公钥和私钥
		RSAPublicKey pubKey = RSAUtils.getPublicKey(modulus, public_exponent);
		RSAPrivateKey priKey = RSAUtils.getPrivateKey(modulus, private_exponent);
		//加密后的密文
		String mi = RSAUtils.encryptByPublicKey(ming, pubKey);
		System.err.println(mi);
		//解密后的明文
		ming = RSAUtils.decryptByPrivateKey(mi, priKey);
		System.err.println(ming);

	}
}
