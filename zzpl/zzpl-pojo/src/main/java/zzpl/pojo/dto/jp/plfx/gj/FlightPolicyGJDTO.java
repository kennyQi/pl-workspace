package zzpl.pojo.dto.jp.plfx.gj;

import zzpl.pojo.dto.BaseDTO;


@SuppressWarnings("serial")
public class FlightPolicyGJDTO extends BaseDTO{
	/**
	 * 国际航班组合政策
	 */
	private GJFlightPolicyDTO flightPolicy;

	public GJFlightPolicyDTO getFlightPolicy() {
		return flightPolicy;
	}

	public void setFlightPolicy(GJFlightPolicyDTO flightPolicy) {
		this.flightPolicy = flightPolicy;
	}

}
