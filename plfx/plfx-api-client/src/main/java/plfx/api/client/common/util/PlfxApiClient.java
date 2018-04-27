package plfx.api.client.common.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.commons.codec.digest.DigestUtils;

import plfx.api.client.common.ApiRequest;
import plfx.api.client.common.ApiRequestBody;
import plfx.api.client.common.ApiResponse;
import plfx.api.client.common.HttpResponse;
import plfx.api.client.common.api.PlfxApiAction;

import com.alibaba.fastjson.JSON;

/**
 * @类功能说明：接口客户端
 * @类修改者：
 * @修改日期：2015-7-2下午3:38:06
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-7-2下午3:38:06
 */
public class PlfxApiClient {

	/**
	 * 经销商KEY
	 */
	private String dealerKey;

	/**
	 * 密钥
	 */
	private String secretKey;

	/**
	 * 超时时间
	 */
	private Integer timeout = 60000;

	/**
	 * 接口地址
	 */
	private String apiUrl = "http://127.0.0.1:8080/plfx-api/api";

	public PlfxApiClient(String url, String dealerKey, String secretKey) {
		if (url != null) {
			this.apiUrl = url;
		}
		this.dealerKey = dealerKey;
		this.secretKey = secretKey;
	}

	public Integer getTimeout() {
		return timeout;
	}

	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}

	/**
	 * 
	 * @param request
	 *            发送请求
	 * @param responseClass
	 *            返回类型
	 * @return
	 */
	public <T extends ApiResponse> T send(ApiRequestBody requestBody,
			Class<T> responseClass) {

		StringBuilder pathBuilder = new StringBuilder(apiUrl);
		if (requestBody == null)
			throw new RuntimeException("请求体不能为空");

		PlfxApiAction action = requestBody.getClass().getAnnotation(
				PlfxApiAction.class);
		if (action == null)
			throw new RuntimeException("未找到接口方法名称");

		ApiRequest<ApiRequestBody> request = new ApiRequest<ApiRequestBody>();
		request.getHeader().setDealerKey(dealerKey);
		request.getHeader().setActionName(action.value());
		request.getHeader().setTimestamp(System.currentTimeMillis());
		request.setBody(requestBody);

		String msg = JSON.toJSONString(request);
		String sign = DigestUtils.md5Hex(secretKey + msg);

		StringBuilder paramBuilder = new StringBuilder("msg=");
		try {
			paramBuilder.append(URLEncoder.encode(JSON.toJSONString(request),
					"UTF-8"));
			paramBuilder.append("&sign=").append(sign);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		//System.out.println(paramBuilder.toString());
		HttpResponse response = HttpUtils.reqForPost(pathBuilder.toString(),
				paramBuilder.toString(), timeout);

		if (response.getResponseState() == 3)
			return (T) JSON.parseObject(
					"{\"message\":\"请求超时！\",\"result\":\"-1\"}", responseClass);

		return (T) JSON.parseObject(response.getResult(), responseClass);
	}

}
