package hg.jxc.admin.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckInputUtil {
	private static final Pattern phonePattern = Pattern.compile("^\\d{11}$");
	private static final Pattern emailPattern = Pattern.compile("[0-9a-zA-Z]{3,}@[0-9a-zA-Z]{3,}\\.[a-zA-Z]{2,}");

	private static boolean check(Pattern p, String s) {
		Matcher m = p.matcher(s);
		return m.matches();
	}

	public static boolean checkEmail(String s) {
		if (s == null) {
			return false;
		}
		return check(emailPattern, s);
	}

	public static boolean checkPhone(String s) {
		if (s == null) {
			return false;
		}
		return check(phonePattern, s);
	}

}
