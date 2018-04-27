package hg.common.util.web;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.params.SyncBasicHttpParams;

/**
 * 避免HttpClient的”SSLPeerUnverifiedException: peer not authenticated”异常
 * 不用导入SSL证书
 * @author shipengzhi(shipengzhi@sogou-inc.com)
 *
 */
public class WebClientWrapper {

    private static HttpClient httpclient;

    public static org.apache.http.client.HttpClient wrapClientWithHttps(org.apache.http.client.HttpClient base) {
        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            X509TrustManager tm = new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
				public void checkClientTrusted(X509Certificate[] chain,
						String authType) throws CertificateException {
					
				}
				public void checkServerTrusted(X509Certificate[] chain,
						String authType) throws CertificateException {
				}
            };
            ctx.init(null, new TrustManager[] { tm }, null);
            SSLSocketFactory ssf = new SSLSocketFactory(ctx, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            SchemeRegistry registry = new SchemeRegistry();
            registry.register(new Scheme("https", 443, ssf));
            
            //加上原来的scheme
            for(String schema: base.getConnectionManager().getSchemeRegistry().getSchemeNames()){
        	registry.register(base.getConnectionManager().getSchemeRegistry().get(schema));
            }
            
            ThreadSafeClientConnManager mgr = new ThreadSafeClientConnManager(registry);
            return new DefaultHttpClient(mgr, base.getParams());
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    /**
     * 支持http和https的client
     * @方法功能说明：
     * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
     * @修改时间：2016年4月26日上午11:39:06
     * @version：
     * @修改内容：
     * @参数：@return
     * @return:HttpClient
     * @throws
     */
    public static HttpClient getClient(){
		if(httpclient!=null)
		    return httpclient;
		
	       HttpParams params = new SyncBasicHttpParams();
	        params.setParameter(HttpProtocolParams.PROTOCOL_VERSION,           HttpVersion.HTTP_1_1);
	        params.setBooleanParameter(HttpProtocolParams.USE_EXPECT_CONTINUE, false);
	        params.setBooleanParameter(HttpConnectionParams.STALE_CONNECTION_CHECK,           false);
	        params.setIntParameter(HttpConnectionParams.SOCKET_BUFFER_SIZE,         20 * 1024);
	        params.setIntParameter(HttpConnectionParams.SO_TIMEOUT,10000);
	        params.setIntParameter(HttpConnectionParams.CONNECTION_TIMEOUT,5000);
	        SchemeRegistry schemeRegistry = new SchemeRegistry();
	        schemeRegistry.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));
	        
	        
	        ThreadSafeClientConnManager mgr = new ThreadSafeClientConnManager(schemeRegistry);
	        httpclient =  WebClientWrapper.wrapClientWithHttps( new DefaultHttpClient(mgr, params));
	        return httpclient;
    }
}