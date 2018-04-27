package plfx.mp.tcclient.tc.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class TcClientUtil {
	/**
	 * 请求服务器URL地址返回数据POST方式
	 * 
	 * @param url
	 *            远程访问的地址
	 * @param data
	 *            参数
	 * @return // * 远程页面调用结果
	 * @throws Exception
	 */
	public static String callUrl(String url, String data) throws Exception {
		HttpURLConnection conn = null;
		String content = "";

		URL getUrl = new URL(url);
		conn = (HttpURLConnection) getUrl.openConnection();
		// conn.setConnectTimeout(timeOut);
		// conn.setReadTimeout(timeOut);

		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.setRequestMethod("POST");
		conn.setUseCaches(false);
		conn.setInstanceFollowRedirects(true);
		conn.setRequestProperty("Content-Type", "text/xml");

		byte[] bdata = data.getBytes("utf-8");
		conn.setRequestProperty("Content-Length", String.valueOf(bdata.length));
		conn.connect();
		DataOutputStream out = new DataOutputStream(conn.getOutputStream());
		out.write(bdata);
		out.flush();
		out.close();

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				conn.getInputStream(), "UTF-8"));
		String inputLine;

		while ((inputLine = reader.readLine()) != null) {
			content += inputLine;
		}
		reader.close();
		conn.disconnect();

		return content;
	}

}
