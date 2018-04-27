package hsl.api.base;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ApiPayload implements Serializable{
	
	/**
	 * 请求来源
	 */
	private String fromClientKey;
	
	public String getFromClientKey() {
		return fromClientKey;
	}

	public void setFromClientKey(String fromClientKey) {
		this.fromClientKey = fromClientKey;
	}

}
