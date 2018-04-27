package lxs.api.base;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import lxs.api.util.HttpUtil;
import lxs.api.util.Md5Util;

import com.alibaba.fastjson.JSON;

public class LxsApiClient {

	private String clientKey;
	private String secretKey;

	private Integer timeout = 60000;
	private String apiUrl = "http://127.0.0.1:8080/hlx-api/service";

	/**
	 * 
	 * @param url
	 *            请求接口地址
	 * @param clientKey
	 *            分配到的客户端标识
	 * @param secretKey
	 *            分配到的客户端密钥
	 */
	public LxsApiClient(String url, String clientKey, String secretKey) {
		if (url != null) {
			this.apiUrl = url;
		}
		this.clientKey = clientKey;
		this.secretKey = secretKey;
	}

	/**
	 * 
	 * @param request
	 *            发送请求
	 * @param responseClass
	 *            返回类型
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T extends ApiResponse> T send(ApiRequest request,
			Class<? extends ApiResponse> responseClass) {

		StringBuilder pathBuilder = new StringBuilder(apiUrl);

		request.getHead().setClientKey(clientKey);
		request.getHead().setTimestamp(System.currentTimeMillis());

		String bodymsg = JSON.toJSONString(request.getBody());
		String headmsg = JSON.toJSONString(request.getHead());
//		String  headmsg= "{\"timestamp\":1429061686,\"actionName\":\"GetSMSValidCode\",\"clientKey\":\"ios\"}";
//		String bodymsg = "{\"payload\":{\"version\":\"1.0\",\"mobile\":\"13346464646\",\"sceneType\":2}}";
		String sign = Md5Util.MD5(clientKey + secretKey + headmsg + bodymsg);

		StringBuilder paramBuilder = new StringBuilder("headmsg=");
		try {
//			paramBuilder
//					.append(URLEncoder.encode(headmsg, "UTF-8"))
//					.append("&bodymsg="
//							+ URLEncoder.encode(bodymsg,"UTF-8")).append("&sign=" + sign);
			paramBuilder
			.append(URLEncoder.encode(
					JSON.toJSONString(request.getHead()), "UTF-8"))
			.append("&bodymsg="
					+ URLEncoder.encode(
							JSON.toJSONString(request.getBody()),
							"UTF-8")).append("&sign=" + sign);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		HttpResponse response = HttpUtil.reqForPost(pathBuilder.toString(),
				paramBuilder.toString(), timeout);

		if (response.getResponseState() == 3) {
			return (T) JSON.parseObject(
					"{\"message\":\"请求超时！\",\"result\":\"0\"}", responseClass);
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
