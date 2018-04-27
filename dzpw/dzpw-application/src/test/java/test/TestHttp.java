package test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.client.methods.HttpPost;

public class TestHttp {
	
	public static void main(String[] args) {
		
		HttpClient client = new HttpClient();
		client.getParams().setHttpElementCharset("UTF-8");
		client.getParams().setContentCharset("UTF-8");
		
		PostMethod post = new PostMethod("http://localhost:7101/dzpw-dealer-api/api");
		String msg = "啊啊啊啊";
		
		try {
			msg = URLEncoder.encode(msg, "utf-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		post.setParameter("msg", msg);
		post.setParameter("sign", "6aabfb35d9634cbd992340e3e818012e");
		
		try {
			client.executeMethod(post);
			String s = post.getResponseBodyAsString();
			System.out.println(s);
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
