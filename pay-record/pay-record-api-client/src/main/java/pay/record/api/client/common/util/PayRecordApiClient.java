package pay.record.api.client.common.util;

import hg.log.util.HgLogger;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.interfaces.RSAPublicKey;

import pay.record.api.client.common.ApiRequest;
import pay.record.api.client.common.ApiRequestBody;
import pay.record.api.client.common.ApiResponse;
import pay.record.api.client.common.HttpResponse;
import pay.record.api.client.common.api.PayRecordApiAction;
import pay.record.api.client.common.exception.PayRecordApiException;

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
public class PayRecordApiClient {

	/**
	 * 来源项目标识
	 * 票量分销生产、中智票量测试
	 */
	private String fromProjectCode;
	
	/**
	 * RSA模
	 */
	private String modulus;
	
	/**
	 * RSA指数
	 */
	private String public_exponent;

	private Integer timeout = 60000;

	/**
	 * 接口地址
	 */
	private String apiUrl = "http://127.0.0.1:8080/pay-record-api/api";

	public PayRecordApiClient(String url, String modulus, String public_exponent) throws PayRecordApiException {
		if (url == null || modulus == null || public_exponent == null) {
			throw new PayRecordApiException("参数不完整");
		}
		this.apiUrl = url;
		this.modulus = modulus;
		this.public_exponent = public_exponent;
		
	}

	public Integer getTimeout() {
		return timeout;
	}

	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}
	
	public String getFromProjectCode() {
		return fromProjectCode;
	}

	public void setFromProjectCode(String fromProjectCode) {
		this.fromProjectCode = fromProjectCode;
	}

	public String getModulus() {
		return modulus;
	}

	public void setModulus(String modulus) {
		this.modulus = modulus;
	}

	public String getPublic_exponent() {
		return public_exponent;
	}

	public void setPublic_exponent(String public_exponent) {
		this.public_exponent = public_exponent;
	}

	public String getApiUrl() {
		return apiUrl;
	}

	public void setApiUrl(String apiUrl) {
		this.apiUrl = apiUrl;
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

		PayRecordApiAction action = requestBody.getClass().getAnnotation(
				PayRecordApiAction.class);
		if (action == null)
			throw new RuntimeException("未找到接口方法名称");
		//获得公钥
		RSAPublicKey pubKey = RSAUtils.getPublicKey(modulus, public_exponent);
		String ciphertext = "";
		try {
			String requestBodyString = JSON.toJSONString(requestBody);
			//密文
			ciphertext = RSAUtils.encryptByPublicKey(requestBodyString, pubKey);
			requestBody = requestBody.getClass().newInstance();
			requestBody.setCiphertext(ciphertext);
		} catch (Exception e1) {
			e1.printStackTrace();
			HgLogger.getInstance().info("yuqz", "PayRecordApiClient->send->请求数据加密异常：" + HgLogger.getStackTrace(e1));
		}
		ApiRequest<ApiRequestBody> request = new ApiRequest<ApiRequestBody>();
		request.getHeader().setActionName(action.value());
		request.getHeader().setTimestamp(System.currentTimeMillis());
		request.setBody(requestBody);
		
		StringBuilder paramBuilder = new StringBuilder("msg=");
		try {
			paramBuilder.append(URLEncoder.encode(JSON.toJSONString(request),
					"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		HttpResponse response = HttpUtils.reqForPost(pathBuilder.toString(),
				paramBuilder.toString(), timeout);

		if (response.getResponseState() == 3)
			return (T) JSON.parseObject(
					"{\"message\":\"请求超时！\",\"result\":\"-1\"}", responseClass);

		return (T) JSON.parseObject(response.getResult(), responseClass);
	}

}
