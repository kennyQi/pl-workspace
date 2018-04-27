package slfx.api.base;

import com.alibaba.fastjson.JSON;

/**
 * api请求基类
 * 
 * @author yuxx
 * 
 */
public class ApiRequest {

	/**
	 * 请求头信息
	 */
	private RequestHead head;

	/**
	 * 请求主体信息
	 */
	private RequestBody body;

	public ApiRequest() {
		super();
	}

	/**
	 * 
	 * @类名：ApiRequest.java
	 * @描述：TODO
	 * @param actionName		调用的接口方法名	必填
	 * @param fromClientKey		请求来源标识		必填
	 * @param fromIp			请求来源IP		可选
	 * @param sessionId			sessionId		可选，为空服务端生成
	 * @@param payload
	 */
	public ApiRequest(String actionName,String fromClientKey, String fromIp, String sessionId, ApiPayload payload) {
		this.head = new RequestHead();
		this.body = new RequestBody();
		head.setActionName(actionName);
		head.setFromIp(fromIp);
		head.setSessionId(sessionId);
		head.setFromClientKey(fromClientKey);
		body.setPayload(JSON.toJSONString(payload));
	}
	
	/**
	 * 需要重写
	 * @return
	 */
	public Class<?> getResponseClazz() {
		return null;
	}
	
	public RequestHead getHead() {
		return head;
	}

	public void setHead(RequestHead head) {
		this.head = head;
	}

	public RequestBody getBody() {
		return body;
	}

	public void setBody(RequestBody body) {
		this.body = body;
	}

}
