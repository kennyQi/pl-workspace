package hg.dzpw.dealer.client.common;

import java.io.Serializable;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @类功能说明：API请求
 * @类修改者：
 * @修改日期：2014-11-18下午2:19:07
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-11-18下午2:19:07
 */
public class ApiRequest<T extends ApiRequestBody> implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 请求头
	 */
	private ApiRequestHead header;

	/**
	 * 请求体
	 */
	private T body;
	
	/**
	 * @方法功能说明：将JSON转换为ApiRequest
	 * @修改者名字：zhurz
	 * @修改时间：2014-11-18下午2:54:34
	 * @修改内容：
	 * @参数：@param json
	 * @参数：@param bodyClass
	 * @参数：@return
	 * @return:ApiRequest<T>
	 * @throws
	 */
	public static <T extends ApiRequestBody> ApiRequest<T> parseRequest(String json, Class<T> bodyClass) {
		JSONObject jsonObject = JSON.parseObject(json);
		return parseRequest(jsonObject, bodyClass);
	}

	/**
	 * @方法功能说明：将JSON转换为ApiRequest
	 * @修改者名字：zhurz
	 * @修改时间：2014-11-27下午5:12:38
	 * @修改内容：
	 * @参数：@param jsonObject
	 * @参数：@param bodyClass
	 * @参数：@return
	 * @return:ApiRequest<T>
	 * @throws
	 */
	public static <T extends ApiRequestBody> ApiRequest<T> parseRequest(JSONObject jsonObject, Class<T> bodyClass) {
		Object body = jsonObject.get("body");
		Object header = jsonObject.get("header");
		ApiRequest<T> apiRequest = new ApiRequest<T>();
		if (header != null && header instanceof JSONObject)
			apiRequest.setHeader(JSONObject.toJavaObject((JSONObject) header, ApiRequestHead.class));
		if (body != null && body instanceof JSONObject)
			apiRequest.setBody(JSONObject.toJavaObject((JSONObject) body, bodyClass));
		return apiRequest;
	}
	
	public ApiRequestHead getHeader() {
		if (header == null)
			header = new ApiRequestHead();
		return header;
	}

	public void setHeader(ApiRequestHead header) {
		this.header = header;
	}

	public T getBody() {
		return body;
	}

	public void setBody(T body) {
		this.body = body;
	}
	
}
