package hg.payment.domain.common.util.hjb;
/**
 *  报文处理工具类
 * @author Administrator
 *
 */
public class XmlUtil {
	// 判断字符串是否为空
	public static boolean isNull(String str) {
		if (null == str || str.matches("[ ]*") || "null".equals(str)) {
			return true;
		}
		return false;
	}

	/**
	 * 获取某个标签下的内容(不区分大小写)
	 * 
	 * @param tag
	 * @return
	 */
	public static String getNodeVal(String str, String tag) {

		if (isNull(tag)) {
			return "";
		}

		String statTag = "<" + tag + ">";
		String endTag = "</" + tag + ">";

		int starTagNum = str.indexOf(statTag);
		int endTagNum = str.indexOf(endTag);

		if (starTagNum == -1 || endTagNum == -1) {
			// 考虑可能出现大小写而不匹配的情况
			String strTmp = str.toUpperCase();
			String starTagTmp = statTag.toUpperCase();
			String endTagTmp = endTag.toUpperCase();

			int starTagNumTmp = strTmp.indexOf(starTagTmp);
			int endTagNumTmp = strTmp.lastIndexOf(endTagTmp);

			if (starTagNumTmp == -1 || endTagNumTmp == -1) {
				return "";
			} else {
				return str.substring(starTagNumTmp + starTagTmp.getBytes().length, endTagNumTmp);
			}
		}
		return str.substring(starTagNum + statTag.getBytes().length, endTagNum);
	}
	
	/**
	 * 替换指定标签中的值
	 * @param str 报文内容
	 * @param tag 标签名称
	 * @param newValue 替换内容
	 * @return
	 */
	public static String replaceNodeValue(String str, String tag, String newValue) {
		if (isNull(tag)) {
			return str;
		}

		String statTag = "<" + tag + ">";
		String endTag = "</" + tag + ">";

		int starTagNum = str.indexOf(statTag);
		int endTagNum = str.indexOf(endTag);

		if (starTagNum == -1 || endTagNum == -1) {
			// 考虑可能出现大小写而不匹配的情况
			String strTmp = str.toUpperCase();
			String starTagTmp = statTag.toUpperCase();
			String endTagTmp = endTag.toUpperCase();

			int starTagNumTmp = strTmp.indexOf(starTagTmp);
			int endTagNumTmp = strTmp.lastIndexOf(endTagTmp);

			if (starTagNumTmp == -1 || endTagNumTmp == -1) {
				return str;
			} else {
				return str.substring(0, starTagNumTmp + starTagTmp.getBytes().length) + newValue + str.substring(endTagNumTmp);
			}
		}
		return str.substring(0, starTagNum + statTag.getBytes().length) + newValue + str.substring(endTagNum);
	}
	
	/**
	 * 从xml报文中去掉指定的标签
	 * @param str
	 * @param tag
	 * @return
	 */
	public static String removeNode(String str, String tag) {
		if (isNull(tag)) {
			return str;
		}

		String statTag = "<" + tag + ">";
		String endTag = "</" + tag + ">";

		int starTagNum = str.indexOf(statTag);
		int endTagNum = str.indexOf(endTag);

		if (starTagNum == -1 || endTagNum == -1) {
			// 考虑可能出现大小写而不匹配的情况
			String strTmp = str.toUpperCase();
			String starTagTmp = statTag.toUpperCase();
			String endTagTmp = endTag.toUpperCase();

			int starTagNumTmp = strTmp.indexOf(starTagTmp);
			int endTagNumTmp = strTmp.lastIndexOf(endTagTmp);

			if (starTagNumTmp == -1 || endTagNumTmp == -1) {
				return str;
			} else {
				return str.substring(0, starTagNumTmp) + str.substring(endTagNumTmp + endTagTmp.getBytes().length);
			}
		}
		return str.substring(0, starTagNum) + str.substring(endTagNum + endTag.getBytes().length);
	}
}
