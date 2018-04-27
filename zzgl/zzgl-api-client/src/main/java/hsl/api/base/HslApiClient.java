package hsl.api.base;

import hsl.api.util.HttpUtil;
import hsl.api.util.Md5Util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.alibaba.fastjson.JSON;

public class HslApiClient {

	private String clientKey;
	private String secretKey;
	
	private Integer timeout = 60000;
	private String apiUrl = "http://127.0.0.1:8080/bao-api-server";

	/**
	 * 
	 * @param url
	 * @param clientKey
	 * @param secretKey
	 */
	public HslApiClient(String url, String clientKey, String secretKey) {
		if (url != null) {
			this.apiUrl = url;
		}
		this.clientKey = clientKey;
		this.secretKey = secretKey;
	}

	/**
	 * 
	 * @param request			发送请求
	 * @param responseClass		返回类型
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T extends ApiResponse> T send(ApiRequest request, Class<? extends ApiResponse> responseClass) {

		StringBuilder pathBuilder = new StringBuilder(apiUrl);

		request.getHead().setClientKey(clientKey);
		request.getHead().setTimestamp(System.currentTimeMillis());
		
		String msg = JSON.toJSONString(request);
		
		String sign = Md5Util.MD5(clientKey + secretKey + msg);
		request.getHead().setSign(sign);
		
		System.out.println(JSON.toJSONString(request));
		
		StringBuilder paramBuilder = new StringBuilder("msg=");
		try {
			paramBuilder.append(URLEncoder.encode(JSON.toJSONString(request),
					"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		HttpResponse response = HttpUtil.reqForPost(pathBuilder.toString(),
				paramBuilder.toString(), timeout);
		
		if (response.getResponseState() == 3) {
			return (T) JSON.parseObject("{\"message\":\"请求超时！\",\"result\":\"0\"}", responseClass);
		}
		
		return (T) JSON.parseObject(response.getResult(), responseClass);
	}

	public Integer getTimeout() {
		return timeout;
	}

	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}

}