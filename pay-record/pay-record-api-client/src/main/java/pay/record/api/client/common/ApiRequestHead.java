package pay.record.api.client.common;

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
	 * 会话ID
	 */
	private String sessionId;

	/**
	 * 时间戳
	 */
	private long timestamp;

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
}