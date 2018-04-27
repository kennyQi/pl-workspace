package plfx.api.client.common.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * @类功能说明：DES工具
 * @类修改者：
 * @修改日期：2015-7-2下午3:05:11
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-7-2下午3:05:11
 */
public class DESUtils {
	
	private static byte[] iv = { 1, 2, 3, 4, 5, 6, 7, 8 };

	/**
	 * @方法功能说明：加密字符串
	 * @修改者名字：zhurz
	 * @修改时间：2015-7-2下午3:04:06
	 * @修改内容：
	 * @参数：@param encryptString
	 * @参数：@param encryptKey
	 * @参数：@return
	 * @参数：@throws Exception
	 * @return:String
	 * @throws
	 */
	public static String encryptDES(String encryptString, String encryptKey)
			throws Exception {
		// IvParameterSpec zeroIv = new IvParameterSpec(new byte[8]);
		IvParameterSpec zeroIv = new IvParameterSpec(iv);
		SecretKeySpec key = new SecretKeySpec(encryptKey.getBytes(), "DES");
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
		byte[] encryptedData = cipher.doFinal(encryptString.getBytes());

		return Base64.encodeBase64String(encryptedData);
	}

	/**
	 * @方法功能说明：解密字符串
	 * @修改者名字：zhurz
	 * @修改时间：2015-7-2下午3:04:14
	 * @修改内容：
	 * @参数：@param decryptString
	 * @参数：@param decryptKey
	 * @参数：@return
	 * @参数：@throws Exception
	 * @return:String
	 * @throws
	 */
	public static String decryptDES(String decryptString, String decryptKey)
			throws Exception {
		byte[] byteMi = Base64.decodeBase64(decryptString);
		IvParameterSpec zeroIv = new IvParameterSpec(iv);
		// IvParameterSpec zeroIv = new IvParameterSpec(new byte[8]);
		SecretKeySpec key = new SecretKeySpec(decryptKey.getBytes(), "DES");
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
		byte decryptedData[] = cipher.doFinal(byteMi);

		return new String(decryptedData);
	}
	
	public static void main(String[] args) throws Exception {
		//System.out.println(encryptDES("你好111wwwwwwwwwwwwwww", "12345678"));
		//System.out.println("--------------------------------------------");
		//System.out.println(decryptDES("9QRih8Zyk1U=", "12345678"));
	}
}