package hg.jxc.admin.common;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class LcfxDateUtil {
	private static SimpleDateFormat sdf = new SimpleDateFormat();
	
	public static Date parseStrDate(String str){
		if (StringUtils.isBlank(str)) {
			return null;
		}
		try {
			return sdf.parse(str);
		} catch (ParseException e) {
			return null;
		}
		
		
		
	}
	
}
