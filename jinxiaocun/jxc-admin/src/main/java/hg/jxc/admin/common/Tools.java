package hg.jxc.admin.common;

import hg.common.util.StringUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.ui.Model;

//常用工具
public class Tools {

	/**
	 * 处理手机号 11122223333 -> 111****3333
	 */
	public static String processPhoneStr(String s) {
		int l = s.length();
		if (l < 8) {
			return s;
		}
		return s.substring(0, 3) + "****" + s.substring(7, l);
	}

	/**
	 * <#if isie?? && isie< xxxx > ie do <#else> other do </#if>
	 */
	public static boolean browserIsIE(HttpServletRequest request, Model model) {
		String s = request.getHeader("user-agent");
		if (s == null) {
			model.addAttribute("isie", 1);
			return true;
		}
		for (int i = 6; i < 11; i++) {
			if (s.contains("MSIE " + i + ".0")) {
				model.addAttribute("isie", i);
				return true;
			}
		}
		return false;
	}

	public static void modelAddFlag(String statusName, Model model) {
		model.addAttribute(statusName, 1);
	}

	public static Date strToDate(String dateString) {
		Date date = null;
	
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		if (StringUtils.isBlank(dateString)) {
			return date;
		}

		try {
			date = sdf.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return date;
	}

}
