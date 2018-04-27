package zzpl.api.client.request.user;

import zzpl.api.client.base.ApiPayload;

@SuppressWarnings("serial")
public class CheckSMSValidCommand extends ApiPayload {

	private String sagaID;

	private String smsValid;

	public String getSagaID() {
		return sagaID;
	}

	public void setSagaID(String sagaID) {
		this.sagaID = sagaID;
	}

	public String getSmsValid() {
		return smsValid;
	}

	public void setSmsValid(String smsValid) {
		this.smsValid = smsValid;
	}

}
