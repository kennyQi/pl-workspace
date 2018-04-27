package test;

import hg.common.util.DateUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import org.apache.commons.httpclient.HttpClient;
//import org.apache.commons.httpclient.HttpException;
//import org.apache.commons.httpclient.methods.PostMethod;

public class TestAPI {


	public static void main(String[] args) {
		
		
		Calendar cal = Calendar.getInstance();
		System.out.println(DateUtil.DATE_FORMAT2().format(cal.getTime()));
		cal.add(Calendar.DAY_OF_MONTH, 2);
		
		System.out.println(DateUtil.DATE_FORMAT2().format(cal.getTime()));
		
		
//		HttpClient client = new HttpClient();
//		client.getParams().setHttpElementCharset("UTF-8");
//		client.getParams().setContentCharset("UTF-8");
//		
//		PostMethod post = new PostMethod("http://192.168.10.27:8080/dzpw-app-api/api");
//		post.setParameter("msg", "{%22body%22:{%22loginName%22:%22admin%22,%22password%22:%22123456%22},%22header%22:{%22actionName%22:%22Login%22,%22deviceId%22:%22DeviceId-xxxxxxx%22,%22sessionId%22:%22sessionId-xxxxxx%22,%22timestamp%22:1416300263642}}");
//		post.setParameter("sign", "6aabfb35d9634cbd992340e3e818012e");
//		
//		try {
//			client.executeMethod(post);
//			String s = post.getResponseBodyAsString();
//			System.out.println(s);
//		} catch (HttpException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}
