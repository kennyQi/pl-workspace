package lxs.api.v1.request.qo.line;

import lxs.api.base.ApiPayload;

@SuppressWarnings("serial")
public class LineActivityQO extends ApiPayload {
	private String startingCity;
	
	private String startingProvince;
	
	public String getStartingProvince() {
		return startingProvince;
	}

	public void setStartingProvince(String startingProvince) {
		this.startingProvince = startingProvince;
	}

	public String getStartingCity() {
		return startingCity;
	}

	public void setStartingCity(String startingCity) {
		this.startingCity = startingCity;
	}

}
