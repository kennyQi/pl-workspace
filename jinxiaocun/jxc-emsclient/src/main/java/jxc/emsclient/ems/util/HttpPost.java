package jxc.emsclient.ems.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import jxc.emsclient.common.LogisticProperties;
import jxc.emsclient.ems.dto.response.CommonResponseDTO;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.hibernate.service.spi.ServiceException;

public class HttpPost {
	
	@SuppressWarnings("restriction")
	public static CommonResponseDTO post(String content,String service){
		
		try {
			
			HttpClient httpclient = new HttpClient();
			HttpMethodParams httpMethodParams=new HttpMethodParams();
			
			
			PostMethod method = new PostMethod(LogisticProperties.getInstance().get("ems_url"));
			method.setParams(httpMethodParams);
			//编码格式
			method.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=gbk");
			
			method.addParameter("appkey", LogisticProperties.getInstance().get("ems_appkey"));
			
			DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			method.addParameter("notify_time", dateFormat.format(new Date()));
		
			method.addParameter("service", service);
			
			
			String text=content+LogisticProperties.getInstance().get("ems_key_value");
			System.out.println(content);
			//MD5加密
			String sign=DigestUtils.md5Hex(text.getBytes("GBK"));
			System.out.println(sign);
			
			//base64加密
			sign=new String(new sun.misc.BASE64Encoder().encode(sign.getBytes("GBK")));
			method.addParameter("sign", sign);
			System.out.println(sign);

			method.addParameter("content", content);
			
			httpclient.executeMethod(method);

			if (method.getStatusCode() == 200) {
				
				String response = method.getResponseBodyAsString();
				System.out.println(response);
				CommonResponseDTO commonResponse = XmlUtil.toBean(response,
						CommonResponseDTO.class);
			
				return commonResponse;
			}

			throw new ServiceException("接口通讯失败");
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("接口通讯失败");
		}		
	}

}
