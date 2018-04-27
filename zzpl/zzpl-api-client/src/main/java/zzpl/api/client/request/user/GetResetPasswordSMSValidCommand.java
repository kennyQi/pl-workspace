package zzpl.api.client.request.user;

import zzpl.api.client.base.ApiPayload;

@SuppressWarnings("serial")
public class GetResetPasswordSMSValidCommand extends ApiPayload{

	private String loginName;

	private String companyID;

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

}
