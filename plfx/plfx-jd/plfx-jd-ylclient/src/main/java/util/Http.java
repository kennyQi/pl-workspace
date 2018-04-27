package util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

public class Http {

	public static String Send(String method, String url, String data)
	{
		HttpURLConnection conn = null;
		InputStream in = null;
		InputStreamReader isr = null;
		OutputStream out = null;
		StringBuffer result = null;
		try
		{
			
			URL u = new URL(url);
			//破解证书开始
			try {
				trustAllHttpsCertificates();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			HostnameVerifier hv = new HostnameVerifier() {
		        public boolean verify(String urlHostName, SSLSession session) {
		           // System.out.println("Warning: URL Host: " + urlHostName + " vs. "
		            //                   + session.getPeerHost());
		            return true;
		        }
		    };
			HttpsURLConnection.setDefaultHostnameVerifier(hv);
			//破解证书结束 正式开发的时候去掉
			
			conn = (HttpURLConnection) u.openConnection();
			conn.setRequestProperty("Accept-Encoding", "gzip");
			//conn.setRequestProperty("Content-Type", "");
			conn.setRequestProperty("Connection", "keep-alive");
			
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setConnectTimeout(60000);
			conn.setReadTimeout(60000);

			if(method.equals("POST")) {
				byte[] sendbyte = data.getBytes("UTF-8");
				out = conn.getOutputStream();
				out.write(sendbyte);
			}

			int status = conn.getResponseCode();
			if (status == 200)
			{
				String enc = conn.getContentEncoding();
				result = new StringBuffer();
				in = conn.getInputStream();
				enc = conn.getContentEncoding();
			
				if(enc != null && enc.equals("gzip")) {
					java.util.zip.GZIPInputStream gzin = new java.util.zip.GZIPInputStream(in);
					isr = new InputStreamReader(gzin, "UTF-8");
					
				} else {
					isr = new InputStreamReader(in, "UTF-8");
				}

				char[] c = new char[1024];
				int a = isr.read(c);
				while (a != -1)
				{
					result.append(new String(c, 0, a));
					a = isr.read(c);
				}
			} else {
				//System.out.println("http code = " + status);	
			
			}

		} catch (MalformedURLException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		} finally
		{
			if (conn != null)
			{
				conn.disconnect();
			}
			try
			{
				if (in != null)
				{
					in.close();
				}
				if (isr != null)
				{
					isr.close();
				}
				if (out != null)
				{
					out.close();
				}
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}

		return result == null ? null : result + "";
	}
	
	
	
	
	private static void trustAllHttpsCertificates() throws Exception {
		javax.net.ssl.TrustManager[] trustAllCerts = new javax.net.ssl.TrustManager[1];
		javax.net.ssl.TrustManager tm = new miTM();
		trustAllCerts[0] = tm;
		javax.net.ssl.SSLContext sc = javax.net.ssl.SSLContext
				.getInstance("SSL");
		sc.init(null, trustAllCerts, null);
		javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(sc
				.getSocketFactory());
	}

	static class miTM implements javax.net.ssl.TrustManager,
			javax.net.ssl.X509TrustManager {
		public java.security.cert.X509Certificate[] getAcceptedIssuers() {
			return null;
		}

		public boolean isServerTrusted(
				java.security.cert.X509Certificate[] certs) {
			return true;
		}

		public boolean isClientTrusted(
				java.security.cert.X509Certificate[] certs) {
			return true;
		}

		public void checkServerTrusted(
				java.security.cert.X509Certificate[] certs, String authType)
				throws java.security.cert.CertificateException {
			return;
		}

		public void checkClientTrusted(
				java.security.cert.X509Certificate[] certs, String authType)
				throws java.security.cert.CertificateException {
			return;
		}
	}

	
}
