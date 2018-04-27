package hg.fx.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.ui.Model;

//常用工具
public class Tools {

	private static final String DATE_TYPE = "yyyy-MM-dd HH:mm";
	private static SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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



	public static void modelAddFlag(String statusName, Model model) {
		model.addAttribute(statusName, 1);
	}

	public static Date strToDate(String dateString) {
		Date date = null;

		SimpleDateFormat sdf = new SimpleDateFormat(DATE_TYPE);
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

	public static void DateQueryProcess(String name, Criteria criteria, String DateStartStr, String DateEndStr, String pattern) {
		SimpleDateFormat sdf;
		if (pattern == null) {
			sdf = new SimpleDateFormat(DATE_TYPE);
		} else {
			sdf = new SimpleDateFormat(pattern);
		}
		try {

			if (StringUtils.isNotBlank(DateStartStr)) {
				criteria.add(Restrictions.ge(name, sdf.parse(DateStartStr)));
			}

			if (StringUtils.isNotBlank(DateEndStr)) {
				criteria.add(Restrictions.le(name, sdf.parse(DateEndStr)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	public static boolean ListAllNull(List<Object> list) {
		for (Object object : list) {
			if (object != null) {
				return false;
			}
		}
		
		return true;
	}
	public static boolean ArrayAllNull(Object[] list) {
		for (Object object : list) {
			if (object != null) {
				return false;
			}
		}
		
		return true;
	}
	
	public static String dateToStringForExport(Date date) {
		if (date == null) {
			return "";
		}
		return sdf1.format(date);
		
	}
}
