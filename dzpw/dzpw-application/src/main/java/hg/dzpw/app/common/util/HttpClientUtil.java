package hg.dzpw.app.common.util;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

public class HttpClientUtil {

	/**
	 * 以HTTP方式调用接口
	 * @param param
	 * @param notifyUrl
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String sendMessageByHttp(String param, String notifyUrl) throws ClientProtocolException, IOException {
		DefaultHttpClient httpClient= new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(notifyUrl);
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		//for(String key: mapParam.keySet()){
			nvps.add(new BasicNameValuePair("reqData", param));
		//}
		httpPost.setEntity(new UrlEncodedFormEntity(nvps,"UTF-8"));
		HttpResponse response = httpClient.execute(httpPost);
		HttpEntity entity = response.getEntity();
		String result = EntityUtils.toString(entity);
		result = new String(result.getBytes("UTF-8"), "UTF-8");
		return result;
	}
	/**
	 * 通过URl请求接口统一入口，根据URl类型自适应调用方式
	 * @param param
	 * @param notifyUrl
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String sendMessage(String param, String notifyUrl) throws ClientProtocolException, IOException {
		if (notifyUrl.startsWith("https")) {
			return sendMessageByHttps(param, notifyUrl);
		}
		return sendMessageByHttp(param, notifyUrl);
	}
	
	
	/**
	 * 以HTTPS方式调用
	 * @param param
	 * @param notifyUrl
	 * @return
	 */
	public static String sendMessageByHttps(String param, String notifyUrl) {
		String responseContent = null; // 响应内容
		HttpClient httpClient = new DefaultHttpClient(); // 创建默认的httpClient实例
		X509TrustManager xtm = new X509TrustManager() { // 创建TrustManager
			@Override
			public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			}
			@Override
			public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			}
			@Override
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		};
		try {
			// TLS1.0与SSL3.0基本上没有太大的差别，可粗略理解为TLS是SSL的继承者，但它们使用的是相同的SSLContext
			SSLContext ctx = SSLContext.getInstance("TLS");

			// 使用TrustManager来初始化该上下文，TrustManager只是被SSL的Socket所使用
			ctx.init(null, new TrustManager[] { xtm }, null);

			// 创建SSLSocketFactory
			SSLSocketFactory socketFactory = new SSLSocketFactory(ctx);

			// 通过SchemeRegistry将SSLSocketFactory注册到我们的HttpClient上
			httpClient.getConnectionManager().getSchemeRegistry().register(new Scheme("https", 443, socketFactory));

			HttpPost httpPost = new HttpPost(notifyUrl); // 创建HttpPost
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(); // 构建POST请求的表单参数
			nameValuePairs.add(new BasicNameValuePair("reqData", param));
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));

			HttpResponse response = httpClient.execute(httpPost); // 执行POST请求
			HttpEntity entity = response.getEntity(); // 获取响应实体

			if (null != entity) {
				responseContent = EntityUtils.toString(entity, "UTF-8");
				EntityUtils.consume(entity); // Consume response content
			}
		} catch (KeyManagementException e) {
			//e.printStackTrace();
			return null;
		} catch (NoSuchAlgorithmException e) {
			//e.printStackTrace();
			return null;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return null;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			httpClient.getConnectionManager().shutdown(); // 关闭连接,释放资源
		}
		return responseContent;
	}
}
