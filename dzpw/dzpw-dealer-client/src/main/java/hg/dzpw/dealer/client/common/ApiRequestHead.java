package hg.dzpw.dealer.client.common;

import java.io.Serializable;

/**
 * @类功能说明：API请求头
 * @类修改者：
 * @修改日期：2014-11-18下午2:15:58
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-11-18下午2:15:58
 */
public class ApiRequestHead implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 接口方法名称
	 */
	private String actionName;

	/**
	 * 经销商代码
	 */
	private String dealerKey;

	/**
	 * 会话ID
	 */
	private String sessionId;

	/**
	 * 时间戳
	 */
	private long timestamp;

	/**
	 * 签名在请求时带上
	 */
	@Deprecated
	private String sign;

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName == null ? null : actionName.trim();
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId == null ? null : sessionId.trim();
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign == null ? null : sign.trim();
	}

	public String getDealerKey() {
		return dealerKey;
	}

	public void setDealerKey(String dealerKey) {
		this.dealerKey = dealerKey;
	}

}