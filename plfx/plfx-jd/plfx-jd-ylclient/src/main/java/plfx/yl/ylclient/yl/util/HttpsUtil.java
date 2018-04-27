package plfx.yl.ylclient.yl.util;

import hg.log.util.HgLogger;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;


/**
 * 
 * @类功能说明：艺龙接口工具类
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江票量云数据科技股份有限公司
 * @部门：技术部
 * @作者：caizhenghao
 * @创建时间：2015年2月5日上午9:56:25
 * @版本：V1.0
 * 
 */
public class HttpsUtil {
	private static String appUser = "e05db991b359c82cdf4495c8487116e3";
	private static String appKey = "081c42a1da14550163faf815cece5c20";
	private static String appSecret = "c3ab4b467ec59b5e474564c0619a108b";
	private static double version = 1.18;
	private static elong.EnumLocal locale = elong.EnumLocal.zh_CN;
	private static String serverHost = "api.elong.com";
	private static String format = "json";

	public static String send(String methodName, Object condition) {

		if (appUser == null || appUser.equals("")) {
			//System.out.println("please set appUser and keys.");
			return null;
		}
		String result = "";

		try {
			BaseRequst req = new BaseRequst();
			req.Version = version;
			req.Local = locale;
			req.Request = condition;

			String str = null;
			if (format.equals("xml")) {
				str = objectToXml(req);
			} else {
				str = objectToJson(req);
			}

			//System.out.println(str);

			long epoch = System.currentTimeMillis() / 1000;
			String sig = util.Tool.md5(epoch + util.Tool.md5(str + appKey)
					+ appSecret);

			String url = "http" + (isRequiredSSL() ? "s" : "") + "://"
					+ serverHost + "/rest?format=" + format + "&method=";
			url += methodName;
			url += "&user=" + appUser + "&timestamp=";
			url += epoch;
			url += "&signature=";
			url += sig;
			url += "&data=" + util.Tool.encodeUri(str);

			//System.out.println();
			//System.out.println("url:" + url);

			result = util.Http.Send("GET", url, "");
			HgLogger.getInstance().info("zhaows", "send="+JSON.toJSONString(result));
			//System.out.println(result+"=============================================================");
			if(result == null){
				return "";
			}
			result = result.trim();
			result = result.replaceAll("0001-01-01T00:00:00",
					"2001-01-01T00:00:00");


		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;

	}

	public static boolean isRequiredSSL() {
		return true;
	}

	public static Object jsonToObject(String str, Class<?> clazz) {

		return  JSON.parseObject(str, clazz, Feature.AllowISO8601DateFormat);
	}

	public static Object xmlToObject(String str, Class<?> clazz) {
		JAXBContext context = null;
		Object obj = null;

		try {
			context = JAXBContext.newInstance(clazz);
			StringReader reader = new StringReader(str);
			Unmarshaller unmar = context.createUnmarshaller();
			obj =  unmar.unmarshal(reader);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return obj;
	}

	public static String objectToXml(Object value) {
		String str = null;

		JAXBContext context = null;

		StringWriter writer = null;
		try {

			context = JAXBContext.newInstance(value.getClass());
			Marshaller mar = context.createMarshaller();
			writer = new StringWriter();
			mar.marshal(value, writer);
			str = writer.toString();
		} catch (JAXBException e) {
			e.printStackTrace();
		} finally {
			if (context != null)
				context = null;
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				writer = null;
			}
		}

		return str;
	}

	public static String objectToJson(Object value) {

		String str = null;

		str = JSON.toJSONStringWithDateFormat(value, "yyyy-MM-dd HH:mm:ss");

		return str;
	}

	public static String getAppUser() {
		return appUser;
	}

	public static void setAppUser(String appUser) {
		HttpsUtil.appUser = appUser;
	}

	public static String getAppKey() {
		return appKey;
	}

	public static void setAppKey(String appKey) {
		HttpsUtil.appKey = appKey;
	}

	public static String getAppSecret() {
		return appSecret;
	}

	public static void setAppSecret(String appSecret) {
		HttpsUtil.appSecret = appSecret;
	}

	public static double getVersion() {
		return version;
	}

	public static void setVersion(double version) {
		HttpsUtil.version = version;
	}

	public static elong.EnumLocal getLocale() {
		return locale;
	}

	public static void setLocale(elong.EnumLocal locale) {
		HttpsUtil.locale = locale;
	}

	public static String getServerHost() {
		return serverHost;
	}

	public static void setServerHost(String serverHost) {
		HttpsUtil.serverHost = serverHost;
	}

	public static String getFormat() {
		return format;
	}

	public static void setFormat(String format) {
		HttpsUtil.format = format;
	}
	
	
}
