package hgtech.jf.coupon;

/**
 * 券倾天下MD5加密专用类！
 */
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5 加密（摘要）
 *
 */

public class MD5 {
	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
	
	private static MessageDigest md = null;
	
	static{
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	private static final String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	private static final String byteToHexString(byte b) {
		int n = b;
		if (n < 0){
			n += 256;
		}
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	/**
	 * 将字符串使用 md5 加密 
	 * @param origin 待加密字符串
	 * @return 加密后字符串
	 */
	
	public static synchronized String digest(String origin) {
		String resultString = null;
		try {
			return resultString = byteArrayToHexString(md.digest(origin.getBytes("UTF-8")));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return resultString;
	}
	
	// test
	public static void main(String[] args) {
		System.out.println(digest(""));
	}
}
