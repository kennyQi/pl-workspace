package zzpl.api.client.request.user;

import zzpl.api.client.base.ApiPayload;

@SuppressWarnings("serial")
public class DeleteFrequentFlyerCommand extends ApiPayload {

	/**
	 * id
	 */
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	

}
