package pay.record.pojo.qo.authip;

import hg.common.component.BaseQo;

@SuppressWarnings("serial")
public class AuthIPQO extends BaseQo{
	/**
	 * 来源项目IP
	 */
	public String fromProjectIP;
	
	/**
	 * 状态,是否启用
	 * 0:未启用，1：启用
	 */
	public String status;

	public String getFromProjectIP() {
		return fromProjectIP;
	}

	public void setFromProjectIP(String fromProjectIP) {
		this.fromProjectIP = fromProjectIP;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
