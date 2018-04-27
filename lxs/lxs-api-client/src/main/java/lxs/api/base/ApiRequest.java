package lxs.api.base;

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
		
	}

	/**
	 * @描述：
	 * @param actionName	调用的接口方法名	必填
	 * @param sessionId		可选，为空服务端生成
	 * @param payload		请求body信息
	 * @param version		接口版本号
	 */
	public ApiRequest(String actionName, String sessionId, ApiPayload payload, String version) {
		this.head = new RequestHead();
		this.body = new RequestBody();
		head.setActionName(actionName);
		head.setSessionId(sessionId);
		body.setPayload(JSON.toJSONString(payload));
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
