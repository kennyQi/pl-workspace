package hsl.api.v1.response.jp;

import hsl.api.base.ApiResponse;
import hsl.pojo.dto.jp.FlightPolicyDTO;

/**
 * 下单前查政策的返回
 * @author yuxx
 *
 */
public class JPQueryPolicyResponse extends ApiResponse {

	/**
	 * 政策信息
	 */
	private FlightPolicyDTO flightPolicyDTO;

	public FlightPolicyDTO getFlightPolicyDTO() {
		return flightPolicyDTO;
	}

	public void setFlightPolicyDTO(FlightPolicyDTO flightPolicyDTO) {
		this.flightPolicyDTO = flightPolicyDTO;
	}

}
