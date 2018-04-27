package hg.hjf.socket;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;


public class SocketUtil {
	/**
	 * 前补 0 
	 */
	public static String toFixedString_0(String s,int len, String charsetName) {
		char preChar = '0';
		return toFixedString(s, len, preChar, charsetName);
	}
	/**
	 * 前补 字符
	 * @param s
	 * @param len
	 * @param preChar
	 * @param charsetName
	 * @return
	 */
	public static String toFixedString(String s, int len, char preChar, String charsetName) {
		StringBuffer str =new StringBuffer();
		int lengthByte;
		try {
			byte[] bytes = s.getBytes(charsetName);
			lengthByte = bytes.length;
			if(lengthByte > len){
			    System.err.println( s + "超过长度"+len);
			    s = new String(Arrays.copyOfRange(bytes, 0, len), charsetName);
			}
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getCause());
		}
		
		for(int i=0;i<len-lengthByte;i++) {
			str.append(preChar);
		}
		return str+s;
	}
	/**
	 * 后补空格
	 * @param s
	 * @param len
	 * @param preChar
	 * @param charsetName
	 * @return
	 */
	public static String toFixedStringPost(String s, int len, char postChar, String charsetName) {
		StringBuffer str =new StringBuffer();
		int lengthByte;
		try {
		    	byte[] bytes =s.getBytes(charsetName);
			lengthByte = bytes.length;
			if(lengthByte > len){
			    System.err.println( s + "超过长度"+len);
			    s = new String(Arrays.copyOfRange(bytes, 0, len), charsetName);
			}			
		} catch (UnsupportedEncodingException e) {
		    	System.err.println(s + " 不支持字符格式编码 "+charsetName);
			throw new RuntimeException(e.getCause());
		}
		for(int i=0;i<len-lengthByte;i++) {
			str.append(postChar);
		}
		return s+str;
	}
	/**
	 * 前补 空格
	 * @param s
	 * @param len
	 * @param charsetname
	 * @return
	 */
	public static String toFixedString_Blank(String s,int len, String charsetname) {
		return toFixedString(s, len, ' ', charsetname);
	}
	
	public static void main(String[] args) {
		System.out.println(toFixedString_0("7", 10, "GBK"));
		System.out.println(toFixedString_Blank("tom", 10, "GBK"));
	}
}
