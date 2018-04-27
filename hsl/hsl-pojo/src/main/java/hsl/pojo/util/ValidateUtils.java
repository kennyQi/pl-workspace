package hsl.pojo.util;

/**
 * 数据有效性校验工具
 *
 * @author zhurz
 * @since 1.7
 */
public class ValidateUtils {

	/**
	 * @param str      需要校验的字符串
	 * @param regex    正则表达式
	 * @param nullable 是否可空
	 * @return 是否符合正则表达式
	 */
	public static boolean check(String str, String regex, boolean nullable) {
		return (nullable && (str == null || str.length() == 0)) || (str != null && str.matches(regex));
	}

	/**
	 * @param str      需要校验的字符串
	 * @param nullable 是否可空
	 * @return 是否都是中文字符
	 */
	public static boolean isAllChineseCharacter(String str, boolean nullable) {
		return check(str, "^[\\u4e00-\\u9fa5]{0,}$", nullable);
	}

	/**
	 * @param str      需要校验的字符串
	 * @param nullable 是否可空
	 * @return 是否是邮箱地址
	 */
	public static boolean isEmail(String str, boolean nullable) {
		return check(str, "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$", nullable);
	}

	/**
	 * @param str      需要校验的字符串
	 * @param nullable 是否可空
	 * @return 是否是网址
	 */
	public static boolean isUrl(String str, boolean nullable) {
		return check(str, "^https{0,1}://([\\w-]+\\.{0,1})+[\\w-]+(/[\\w-./?%&=]*)?$", nullable);
	}

	/**
	 * 检查是否是身份证号格式，并不校验合法性
	 *
	 * @param str      需要校验的字符串
	 * @param nullable 是否可空
	 * @return 是否是身份证号格式
	 */
	public static boolean isIdno(String str, boolean nullable) {
		return check(str, "^(\\d{15}$|^\\d{18}$|^\\d{17}(\\d|X|x))$", nullable);
	}

}
