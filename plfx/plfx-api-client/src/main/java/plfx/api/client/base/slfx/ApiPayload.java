package plfx.api.client.base.slfx;

import java.io.Serializable;

@SuppressWarnings("serial")
public abstract class ApiPayload implements Serializable {

	/**
	 * 经销商ID
	 */
	private String fromClientKey;

	/**
	 * 来源标识：0 mobile , 1  pc
	 * 经销商所属用户KEY(规则 = 来源标识_key)
	 */
	private String clientUserKey;

	public String getFromClientKey() {
		return fromClientKey;
	}

	public void setFromClientKey(String fromClientKey) {
		this.fromClientKey = fromClientKey;
	}

	public String getClientUserKey() {
		return clientUserKey;
	}

	public void setClientUserKey(String clientUserKey) {
		this.clientUserKey = clientUserKey;
	}

}
