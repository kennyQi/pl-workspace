package plfx.yxgjclient.common.util;

import hg.log.util.HgLogger;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import plfx.jp.common.ThreadTrackingTokenGenerator;

/**
 * 调用远程接口封装转换为结果实体返回
 * @修改 2015-08-07 09:23:25
 * 接口地址可配
 * @author guotx
 * 
 */
public class ServicesUtil {
	/**
	 * 易行接口地址，默认为测试地址
	 */
	public static String YIGJ_INTER_URL=null;
	
	private static URL endpointAddress=null;
	
	private static Logger logger=LoggerFactory.getLogger(ServicesUtil.class);
	/**
	 * 调用易行国际接口，发送xml报文
	 * @param requestXml
	 * @return
	 */
	public static String getServiceResult(String requestXml) {
		if (endpointAddress==null) {
			try {
				endpointAddress=new URL(YIGJ_INTER_URL);
			} catch (MalformedURLException e) {
				logger.error("易行国际接口地址错误");
				e.printStackTrace();
			} 
		}
		String space = "http://service.webservice.yeexing.com";
		Service serv = new Service();
		String result = null;
		try {
			Call call = (Call) serv.createCall();
			call.setTargetEndpointAddress(endpointAddress);
			// 方法名
			QName qName = new QName(space, "iatService");
			QName paramName = new javax.xml.namespace.QName("", "requestXML");
			QName xmlType = new javax.xml.namespace.QName(
					"http://www.w3.org/2001/XMLSchema", "string");
			QName returnType = new QName("http://www.w3.org/2001/XMLSchema",
					"string");
			call.addParameter(paramName, xmlType, ParameterMode.IN);
			call.setReturnType(returnType);
			call.setOperationName(qName);
			result = (String) call.invoke(new Object[] { requestXml });
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 准备接口参数，调用接口并将返回xml转为实体对象返回
	 * @param returnClass
	 * @param paramObject
	 * @return
	 */
	public static <T> T callService(Class<T> returnClass, Object paramObject) {
		NullConverter.threadLocal.set(paramObject.getClass());
		String paramXml = XStreamUtil.objectToXML(paramObject);
//		String serialString=RandomStringUtils.random(8,"123456789abcdefg");
//		FileUtil.stringToFile("E:\\gjjp_file\\param_"+serialString+".xml", paramXml);
		NullConverter.threadLocal.set(returnClass);
		String resultXml = getServiceResult(paramXml);
//		FileUtil.stringToFile("E:\\gjjp_file\\result_"+serialString+".xml", resultXml);
		// 跟踪代码作为日志搜索关键字
		String trackingToken = ThreadTrackingTokenGenerator.get();
		HgLogger.getInstance().info(ServicesUtil.class,"guotx",
				String.format("(%s)->调用国际机票接口\n请求参数：%s\n返回参数：%s",
						trackingToken, paramXml, resultXml), trackingToken);
		
		// 接口返回字段isSuccess为T才按照指定类型转换为Bean
		if (!isServiceSuccess(resultXml)) {
			// 将错误返回报文的root替换为要求实体对象名
			String rootName = returnClass.getSimpleName();
			rootName = rootName.substring(0, 1).toLowerCase()
					+ rootName.substring(1);
			resultXml = resultXml.replaceAll("baseRS", rootName);
		}
		return XStreamUtil.xmlToObject(returnClass, resultXml);
	}

	/**
	 * 根据返回xml判断接口调用是否成功
	 * 
	 * @return
	 */
	private static boolean isServiceSuccess(String xml) {
		int index = xml.indexOf("isSuccess") + 10;
		String flag = xml.substring(index, index + 1);
		return flag.equalsIgnoreCase("T") ? true : false;
	}
	 
}
