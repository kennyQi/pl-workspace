package hg.demo.web.filter;

import java.security.*;
import java.util.Arrays;

/***
 * 消息接口认证token摘要类
 * 
 * 这个摘要类实现为单例，校验一个签名是否合法的例子如下
 * <pre>
 * HGMessageDigest hgDigest = HGMessageDigest.getInstance();
 * boolean bValid = hgDigest.validate(signature, timestamp, appId);
 * </pre>
 * 
 * 
 * @author xuww
 */
public final class HGMessageDigest {
	
	/**
	 * 单例持有类
	 * @author xuww
	 *
	 */
	private static class SingletonHolder{
		static final HGMessageDigest INSTANCE = new HGMessageDigest();
	}
	
	/**
	 * 获取单例
	 * @return
	 */
	public static HGMessageDigest getInstance() {
		return SingletonHolder.INSTANCE;
	}
	
	private MessageDigest digest;
	
	private HGMessageDigest() {
		try {
			digest = MessageDigest.getInstance("SHA-1");
		} catch(Exception e) {
			throw new InternalError("init MessageDigest error:" + e.getMessage());
		}
	}

	

	/**
	 * 将字节数组转换成16进制字符串
	 * @param b
	 * @return
	 */
	private static String byte2hex(byte[] b) {
		StringBuilder sbDes = new StringBuilder();
		String tmp = null;
		for (int i = 0; i < b.length; i++) {
			tmp = (Integer.toHexString(b[i] & 0xFF));
			if (tmp.length() == 1) {
				sbDes.append("0");
			}
			sbDes.append(tmp);
		}
		return sbDes.toString();
	}
	
	private String encrypt(String strSrc) {
		String strDes = null;
		byte[] bt = strSrc.getBytes();
		digest.update(bt);
		strDes = byte2hex(digest.digest());
		return strDes;
	}

	/**
	 * 校验请求的签名是否合法
	 * 
	 * 加密/校验流程：
     * 1. 将token、timestamp、appId三个参数进行字典序排序
     * 2. 将三个参数字符串拼接成一个字符串进行sha1加密
	 * 3. 开发者获得加密后的字符串可与signature对比，标识该请求来源于
	 * @param signature
	 * @param timestamp
	 * @param appId
	 * @return
	 */
	public String genSign(String appId, String timestamp, String token){
		String[] arrTmp = { token, timestamp, appId };
		Arrays.sort(arrTmp);
		StringBuffer sb = new StringBuffer();
		//2.将三个参数字符串拼接成一个字符串进行sha1加密
		for (int i = 0; i < arrTmp.length; i++) {
			sb.append(arrTmp[i]);
		}
		String expectedSignature = encrypt(sb.toString());
		
		return expectedSignature;
	}
	
	public boolean validate(String signature, String timestamp, String appId){
		//1. 将token、timestamp、appId三个参数进行字典序排序
		String token = getToken();
		String expectedSignature = genSign(appId, timestamp, token);
		//3. 开发者获得加密后的字符串可与signature对比，标识该请求来源
		if(expectedSignature.equals(signature)){
			return true;
		}else{
			return false;
		}
	}
	
	public String getToken(){
		return "111111";
	}

	public static void main(String[] args) {
		
		String signature="f86944503c10e7caefe35d6bc19a67e6e8d0e564";//加密需要验证的签名
		String timestamp="1371608072";//时间戳
		String appId="1372170854";//随机数
		
		HGMessageDigest hgDigest = HGMessageDigest.getInstance();
		boolean bValid = hgDigest.validate(signature, timestamp, appId);	
		System.out.println(bValid);
		System.out.println(hgDigest.genSign("1", "2017-06-28 14:14:55", hgDigest.getToken()));
	}

}
