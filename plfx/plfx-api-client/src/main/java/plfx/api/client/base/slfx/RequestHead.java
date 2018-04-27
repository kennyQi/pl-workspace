package plfx.api.client.base.slfx;

import java.util.Date;

public class RequestHead {

	/**
	 * 经销商ID
	 */
	private String fromClientKey;

	/**
	 * 来源标识：0 mobile , 1  pc
	 * 经销商所属用户KEY(规则 = 来源标识_key)
	 */
	private String clientUserKey;

	/**
	 * 来源ip
	 */
	private String fromIp;

	/**
	 * 发送时间（客户端时间）
	 */
	private Date sendDate;

	/**
	 * 请求的接口名称
	 */
	private String actionName;

	/**
	 * 请求的会话id
	 */
	private String sessionId;

	/**
	 * 签名
	 */
	private String sign;
	
	public String getFromClientKey() {
		return fromClientKey;
	}

	public void setFromClientKey(String fromClientKey) {
		this.fromClientKey = fromClientKey;
	}

	public String getFromIp() {
		return fromIp;
	}

	public void setFromIp(String fromIp) {
		this.fromIp = fromIp;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
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

	public String getClientUserKey() {
		return clientUserKey;
	}

	public void setClientUserKey(String clientUserKey) {
		this.clientUserKey = clientUserKey;
	}

}
