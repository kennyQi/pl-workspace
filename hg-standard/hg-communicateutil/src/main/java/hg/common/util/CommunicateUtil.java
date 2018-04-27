package hg.common.util;

import java.io.UnsupportedEncodingException;

/**
 * 传输数据解密
 * @author zqq
 *
 */
public class CommunicateUtil {

	/**
	 * 对请求加密
	 * @param data 待加密对象
	 * @param publicKey 公钥
	 * @return 返回加密后的对象
	 */
	public static EncodedCommunicateData  encryptRequest(CommunicateData data,String publicKey){
		EncodedCommunicateData enCode = null;
		try {
			enCode = new EncodedCommunicateData();
			//对业务数据进行加密
			String enData =Base64Utils.Base64Encode(
					AESUtil.encryptAES(
							data.data.getBytes("utf-8"),data.aesKey.getBytes("utf-8")));
			enCode.data = enData;
			String k = Base64Utils.Base64Encode(
					RSAUtil.encrypt(Base64Utils.Base64Decode(publicKey), data.aesKey.getBytes("utf-8")));
			enCode.encryptKey = k;
			//生成sign
			String sign = Md5Util.MD5(data.appId+enData+data.time+k+publicKey);
			enCode.sign = sign;
			enCode.time = data.time;
			enCode.appId = data.appId;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		return enCode;
	}
	/**
	 * 解密请求
	 * @param data 待解密对象
	 * @param privateKey 私钥
	 * @return 解密对象
	 */
	public static CommunicateData decyrptRequest(EncodedCommunicateData  data,String privateKey){
		CommunicateData communicateData = null;
		try {
			//解密aesKey
			communicateData = new CommunicateData();
			byte[] aesKey = RSAUtil.decrypt(Base64Utils.Base64Decode(privateKey), Base64Utils.Base64Decode(data.encryptKey));
			communicateData.aesKey = new String(aesKey,"utf-8");
			//解密业务数据
			String content = new String(AESUtil.decryptAES(Base64Utils.Base64Decode(data.data), aesKey),"utf-8");
			communicateData.data = content;
			communicateData.time = data.time;
			communicateData.appId = data.appId;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("解密失败");
			return null;
		}
		return communicateData;
	}
	/**
	 * 校验签名
	 * @param data 待校验对象
	 * @param publicKey 公钥
	 * @return
	 */
	public static boolean checkSign(EncodedCommunicateData data , String publicKey){
		String sign = Md5Util.MD5(data.appId+data.data+data.time+data.encryptKey+publicKey);
		if(!data.sign.equals(sign)){
			return false;
		}
		return true;
	}
	/**
	 * 使用AES加密响应字段
	 * @param aesKey
	 * @param content
	 * @return base64序列化后的加密字串
	 */
	public static String encryptResponse(String aesKey,String content){
		try {
			byte[] encryptBytes = AESUtil.encryptAES(content.getBytes("utf-8"), aesKey.getBytes("utf-8"));
			return Base64Utils.Base64Encode(encryptBytes);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
