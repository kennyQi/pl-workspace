package hsl.h5.base.springmvc;

import java.math.BigInteger;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

public class TokenHandler {
	
	public static final String TOKEN_NAME = "SPRING_TOKEN";
     
    //生成一个唯一值的token
    public synchronized static void generateGUID(HttpSession session) {
        String token = "";
        try {
            token = new BigInteger(165, new Random()).toString(36)
                    .toUpperCase();
            session.setAttribute(TOKEN_NAME, token);
        } catch (IllegalStateException e) {
        	e.printStackTrace();
        }
    }
 
    //验证表单token值和session中的token值是否一致
    public static boolean validToken(HttpSession session,String tokenVal) {
    	synchronized (session) {
    		try{
    			String token = (String)session.getAttribute(TOKEN_NAME);
    			if(token!=null&&token.equals(tokenVal)){
    				generateGUID(session);
    				return true;
    			}
    			generateGUID(session);
    		}catch(RuntimeException e){
    			e.printStackTrace();
    		}
		}
    	return false;
    }
 
    //获取表单中token值
    public static String getInputToken(HttpServletRequest request) {
    	String tokenVal = request.getParameter(TOKEN_NAME);
    	if(StringUtils.isBlank(tokenVal)){
    		tokenVal = "";
    	}
    	return tokenVal;
    }


}
