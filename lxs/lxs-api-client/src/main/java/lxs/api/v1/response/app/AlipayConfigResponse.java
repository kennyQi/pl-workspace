package lxs.api.v1.response.app;

import lxs.api.base.ApiResponse;

public class AlipayConfigResponse extends ApiResponse {
	private String partner;
	private String seller;
	private String rsa_private;
	private String rsa_public;
	private String notify_url;

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public String getSeller() {
		return seller;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}

	public String getRsa_private() {
		return rsa_private;
	}

	public void setRsa_private(String rsa_private) {
		this.rsa_private = rsa_private;
	}

	public String getRsa_public() {
		return rsa_public;
	}

	public void setRsa_public(String rsa_public) {
		this.rsa_public = rsa_public;
	}

	public String getNotify_url() {
		return notify_url;
	}

	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}

}
