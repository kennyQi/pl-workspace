package lxs.api.v1.request.command.user;

import lxs.api.base.ApiPayload;

@SuppressWarnings("serial")
public class SMSCommand extends ApiPayload {

	private String mobile;

	private String content;

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
