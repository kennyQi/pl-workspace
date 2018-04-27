package plfx.api.client.common.util;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

import plfx.api.client.common.HttpResponse;

public class HttpUtils {

	public static HttpResponse reqForPost(String postUrl, String context, Integer timeout) {
		HttpResponse res = new HttpResponse();
		Date sendTime = new Date();
		res.setSendTime(sendTime);
		try {
			URL url = new URL(postUrl);
			URLConnection urlConnection = url.openConnection();
			HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
			// 设置是否向httpUrlConnection输出，因为这个是post请求，参数要放在http正文内，因此需要设为true，默认是false
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setDoInput(true);
			// 设定请求的方法为"post"，默认为"get"
			httpURLConnection.setRequestMethod("POST");
			if (timeout == null) {
				timeout = 30000;
			}
			httpURLConnection.setConnectTimeout(timeout);
			OutputStreamWriter writer = new OutputStreamWriter(
					httpURLConnection.getOutputStream(), "UTF-8");
			if (context != null) {
				writer.write(context);
			}
			writer.flush();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					httpURLConnection.getInputStream(), "UTF-8"));
			String line;
			StringBuilder sb = new StringBuilder();
			while ((line = in.readLine()) != null) {
				sb.append(line);
			}
			writer.close();
			in.close();
			Date handOverTime = new Date();
			Long responseTime = handOverTime.getTime() - sendTime.getTime();
			if (responseTime > timeout) {
				res.setHandOverTime(new Date());
				res.setResult("请求连接超时！");
				res.setResponseTime(0L);
				res.setResponseState(3);
			} else {
				res.setHandOverTime(handOverTime);
				res.setResult(sb.toString());
				res.setResponseTime(handOverTime.getTime() - sendTime.getTime());
				res.setResponseState(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			res.setHandOverTime(new Date());
			res.setResponseTime(0L);
			res.setResponseState(3);
			res.setResult("响应超时!");
		}
		return res;
	}

	public static HttpResponse reqForGet(String getUrl, Integer timeout) {
		HttpResponse res = new HttpResponse();
		Date sendTime = new Date();
		res.setSendTime(sendTime);
		try {
			URL url = new URL(getUrl);
			URLConnection urlConnection = url.openConnection();
			HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
			// 默认是false
			httpURLConnection.setDoOutput(false);
			// 设置是否从httpUrlConnection读入，默认是true
			httpURLConnection.setDoInput(true);
			// 设定请求的方法为"post"，默认为"get"
			httpURLConnection.setRequestMethod("GET");
			if (timeout == null) {
				timeout = 30000;
			}
			httpURLConnection.setConnectTimeout(timeout);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					httpURLConnection.getInputStream()));
			String line;
			StringBuilder sb = new StringBuilder();
			while ((line = in.readLine()) != null) {
				sb.append(line);
			}
			in.close();
			Date handOverTime = new Date();
			Long responseTime = handOverTime.getTime() - sendTime.getTime();
			if (responseTime > timeout) {
				res.setHandOverTime(new Date());
				res.setResult("请求连接超时！");
				res.setResponseTime(0L);
				res.setResponseState(3);
			} else {
				res.setHandOverTime(handOverTime);
				res.setResult(httpURLConnection.getResponseMessage());
				res.setResponseTime(handOverTime.getTime() - sendTime.getTime());
				res.setResponseState(1);
			}
		} catch (Exception e) {
			res.setHandOverTime(new Date());
			res.setResult("请求连接超时！");
			res.setResponseTime(0L);
			res.setResponseState(3);
		}
		return res;
	}

}
