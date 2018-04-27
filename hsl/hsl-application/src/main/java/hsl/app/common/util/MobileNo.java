package hsl.app.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MobileNo {
	 public static boolean isMobileNO(String mobiles){  
	     boolean flag = false;  
	     try{  
	      Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]|14[0-9]))\\d{8}$");  
	      Matcher m = p.matcher(mobiles);  
	      flag = m.matches();  
	     }catch(Exception e){  
	      flag = false;  
	     }  
	     return flag;  
	    }  
}
