package jxc.api.base;

public class RequestHead {

	/**
	 * 来源客户端标识
	 */
	private String clientKey;

	/**
	 * 来源ip
	 */
	private String fromIp;

	/**
	 * 发送时间（客户端时间）
	 */
	private Long timestamp;

	/**
	 * 请求的接口名称
	 */
	private String actionName;

	/**
	 * 请求的会话id
	 */
	private String sessionId;

	/**
	 * 所调用的接口版本号
	 */
	private String version;

	/**
	 * 签名
	 */
	private String sign;

	public String getClientKey() {
		return clientKey;
	}

	public void setClientKey(String clientKey) {
		this.clientKey = clientKey;
	}

	public String getFromIp() {
		return fromIp;
	}

	public void setFromIp(String fromIp) {
		this.fromIp = fromIp;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

}
