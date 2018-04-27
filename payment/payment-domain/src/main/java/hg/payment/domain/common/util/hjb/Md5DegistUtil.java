package hg.payment.domain.common.util.hjb;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 * md5加密工具类
 * @author Administrator
 *
 */
public class Md5DegistUtil {
	/**
	 * 加密算法
	 * @param str 要加密的字符串
	 * @param signatureAlgorithm 加密算法，比如MD5(目前只支持MD5加密)
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static String getMd5Str(String str,String signatureAlgorithm) throws Exception {
	MessageDigest md =  MessageDigest .getInstance(signatureAlgorithm);
	md.update(str.getBytes("UTF-8"));
	byte[] b = md.digest();
	StringBuffer buf = new StringBuffer("");
	for(int offset = 0; offset < b.length; offset++ ){
		int i = b[offset];
		if(i<0){
			i+=256;
		}
		if(i<16){
			buf.append("0");//不足2位，补0
		}
		buf.append(Integer.toHexString(i));
	}
	
	return buf.toString();
	}
}
