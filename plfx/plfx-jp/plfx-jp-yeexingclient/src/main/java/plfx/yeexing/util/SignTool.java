package plfx.yeexing.util;

import hg.log.util.HgLogger;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;

public class SignTool {
	/**
	 * 计算签名
	 * @param params
	 * @param key
	 * @return
	 */
	public static String sign(Map<String,String> params, String key) {
//		HgLogger.getInstance().info("yuqz", "易行计算签名：params:" + JSON.toJSONString(params) + ",key=" + key);
		String sign = "";
//		System.out.println(key);
		try {
			 sign = MD5(URLEncoder.encode(getSign(params, key), "UTF-8").toUpperCase());
//			 HgLogger.getInstance().info("yuqz", "易行计算签名：sign=" + sign);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return sign;
	}


	/**
	 * 参数名排序，并拼接key值
	 * @param params
	 * @param privateKey
	 * @return
	 */
	public static String getSign(Map params, String privateKey) {
//		System.out.println(privateKey);
		List keys = new ArrayList(params.keySet());
		Collections.sort(keys);
//		System.out.println(keys.toString());
		String prestr = "";
		
		for (int i = 0; i < keys.size(); i++) {
			String key = (String) keys.get(i);
			String value = (String) params.get(key);
			if (i == keys.size() - 1) {
				prestr = prestr + value;
			} else {
				prestr = prestr + value;
			}
//			System.out.println(prestr);
		}
		
//		System.out.println("加密前"+prestr + privateKey);
//		HgLogger.getInstance().info("yuqz", "易行计算签名->getSign->prestr=" + prestr + ",privateKey=" + privateKey);
		return prestr + privateKey;
	}
	
	/**
	 * MD5加密
	 * @param s
	 * @return
	 */
	public final static String MD5(String s) {
		//System.out.println("MD5加密前 :"+s);
//		String s="";
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			byte[] strTemp = s.getBytes();
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
//			System.out.println("MD5加密后："+new String(str));
			return new String(str);
		} catch (Exception e) {
			return null;
		}
	}
	public static void main(String args[]){
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("orderid", "T2014022116929");
		map.put("passengerName","张温荣");
		map.put("refuseMemo","HN 状态");
		map.put("type","6");
//		map.put("pnrText", "RTJW7RXT 1.邓侃 2.刘伟明 JW7RXT 3. CA1815 Q FR21FEB PEKXMN HK2 1620 1910 E T3 4.BJS/T BJS/T 010-96135/PEK HUABEI QIMAO AVIATION SERVICE CENTRE/ZHANG JIANJUN ABCDEFG 5.TL/2300/14FEB/BJS326 6.SSR FOID CA HK1 NI110106197201032158/P2 7.SSR FOID CA HK1 NI440102197910223636/P1 8.SSR FQTV CA HK1 PEKXMN 1815 Q21FEB CA003671814636/P1 9.SSR FQTV CA HK1 PEKXMN 1815 Q21FEB CA008193519841/P2 10.SSR ADTK 1E BY BJS14FEB14/1620CXL CA ALL SEGS 11.OSI CA CTCT13718727030 12.RMK CA/NTDYJE PN 13.RMK TLWBINSD - 14.BJS326 PAT:A PAT:A 01 Q FARE:CNY1030.00 TAX:CNY50.00 YQ:CNY120.00 TOTAL:1200.00 SFC:01");
		
		String signT = SignTool.sign(map, "myxxjs$sh80yxgs");
//		System.out.println(signT);
	}
}
