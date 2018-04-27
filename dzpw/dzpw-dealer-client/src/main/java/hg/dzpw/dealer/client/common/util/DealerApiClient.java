package hg.dzpw.dealer.client.common.util;

import hg.dzpw.dealer.client.common.ApiRequest;
import hg.dzpw.dealer.client.common.ApiRequestBody;
import hg.dzpw.dealer.client.common.ApiResponse;
import hg.dzpw.dealer.client.common.HttpResponse;
import hg.dzpw.dealer.client.common.api.DealerApiAction;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.commons.codec.digest.DigestUtils;

import com.alibaba.fastjson.JSON;

/**
 * @类功能说明：经销商接口客户端
 * @类修改者：
 * @修改日期：2014-12-17下午5:26:21
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-12-17下午5:26:21
 */
public class DealerApiClient {
	
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
	private String apiUrl = "http://127.0.0.1:8080/dzpw-dealer-api/api";

	public DealerApiClient(String url, String dealerKey, String secretKey) {
		if (url != null) {
			this.apiUrl = url;
		}
		this.dealerKey = dealerKey;
		this.secretKey = secretKey;
	}

	/**
	 * 
	 * @param request			发送请求
	 * @param responseClass		返回类型
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T extends ApiResponse> T send(ApiRequestBody requestBody, Class<? extends ApiResponse> responseClass) {

		StringBuilder pathBuilder = new StringBuilder(apiUrl);
		if (requestBody == null)
			throw new RuntimeException("请求体不能为空");

		DealerApiAction action = requestBody.getClass()
				.getAnnotation(DealerApiAction.class);
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

		System.out.println(paramBuilder.toString());
		HttpResponse response = HttpUtils.reqForPost(pathBuilder.toString(),
				paramBuilder.toString(), timeout);

		if (response.getResponseState() == 3)
			return (T) JSON.parseObject("{\"message\":\"请求超时！\",\"result\":\"-1\"}", responseClass);

		return (T) JSON.parseObject(response.getResult(), responseClass);
	}

	public Integer getTimeout() {
		return timeout;
	}

	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}

}
