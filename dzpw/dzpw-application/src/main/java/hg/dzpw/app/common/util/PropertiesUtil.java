package hg.dzpw.app.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {
	
	//证书公钥路径
	private static String cerPath;
	//私钥验签证书路径
	private static String signPath;
	//私钥验签证书密码
	private static String signPassword;
	//汇金宝支付服务地址
	private static String hjbServerUrl;
	

	public static String getCerPath() {
		return cerPath;
	}

	public static String getSignPath() {
		return signPath;
	}

	public static String getSignPassword() {
		return signPassword;
	}

	public static String getHjbServerUrl() {
		return hjbServerUrl;
	}


	static{
            Properties p = new Properties();
            InputStream in = null;
            try {
            		in = PropertiesUtil.class.getClassLoader()
            				.getResourceAsStream("Signature.properties");
                    p.load(in);
                    cerPath = p.getProperty("cerPath");
                    signPath = p.getProperty("signPath");
                    signPassword = p.getProperty("signPassword");
                    hjbServerUrl=p.getProperty("HJBServerUrl");
            } catch (IOException e) {
                    e.printStackTrace();
            }finally{
            	if(in != null){
            		try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
            		in = null;
            	}
            }
    }
	
}

