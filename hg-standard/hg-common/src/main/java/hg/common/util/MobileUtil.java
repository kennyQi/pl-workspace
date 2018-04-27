package hg.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 手机号码校验工具类
 * 
 * @author guotx
 * @date 2016-4-22下午4:11:42
 * @since hg-common-0.8
 */
public class MobileUtil {
	/**
	 * 手机号校验结果类
	 * 
	 * @author guotx
	 * @date 2016-4-22下午4:35:02
	 * @since hg-common-0.8
	 */
	public static class Result {
		public Result(int code, String text) {
			this.code = code;
			this.text = text;
		}

		public Result(int code) {
			this.code = code;
		}

		/** -1,不是手机号，其他值为有效手机号 */
		private int code;
		/** 详细文本信息 */
		private String text;

		public int getCode() {
			return code;
		}

		public void setCode(int code) {
			this.code = code;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}
	}

	/**
	 * 校验给定手机号是否为合法手机号
	 * @author guotx
	 * @since hg-common-0.8
	 * @date 2016-4-22 下午4:39:10
	 * @param mobile
	 *            需要校验的手机号
	 * @return hg.common.util.MobileUtil.Result，若是合法手机好，result.code=1,不合法为-1
	 */
	public static Result checkMobile(String mobile) {
		if (mobile==null || mobile.equals("")) {
			return new Result(-1,"手机号不能为空");
		}
		if (isMobileNO(mobile)) {
			return new Result(1);
		} else {
			return new Result(-1,"无效手机号");
		}
	}

	/**
	 * 验证手机号码，格式匹配截至2015-08，包含三大运营商和虚拟运营商
	 * 
	 * @author guotx
	 * @date 2016-04-22 15:52:33
	 * @param mobile
	 *            要验证的手机号
	 * @return 是手机号返回true，否则返回false
	 */
	public static boolean isMobileNO(String mobile) {
		boolean flag = false;
		try {
			Pattern p = Pattern
					.compile("^((13[0-9])|(15[0-3,5-9])|(18[0-9])|(17[01678])|147)\\d{8}$");
			Matcher m = p.matcher(mobile);
			flag = m.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

}
