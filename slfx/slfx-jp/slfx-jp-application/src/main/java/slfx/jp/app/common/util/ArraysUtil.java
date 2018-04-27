package slfx.jp.app.common.util;

/**
 * 
 * @author zhangka
 *
 */
public class ArraysUtil {
	/**
	 * 把数组以slice分隔组装成字符串返回
	 * @param objs
	 * @param slice
	 * @return
	 */
	public static String toStringWithSlice(Object[] objs, String slice) {
		if (objs == null || objs.length == 0)
			return null;
		
		StringBuilder sb = new StringBuilder();
		for (Object obj : objs) {
			sb.append(obj).append(slice);
		}
		
		//去掉最后一个slice
		if (sb.length() > 0) {
			sb.delete(sb.length() - 1, sb.length());
		}
		
		return sb.toString();
	}
	
	public static void main(String[] args) {
		String[] str = {"1", "2", "3"};
		System.out.println(ArraysUtil.toStringWithSlice(str, ","));
	}
}
