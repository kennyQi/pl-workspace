package lxs.api.v1.response.line;

import lxs.api.base.ApiResponse;

public class GetInsuranceResponse extends ApiResponse{

	private String insurance;

	public String getInsurance() {
		return insurance;
	}

	public void setInsurance(String insurance) {
		this.insurance = insurance;
	}

}
