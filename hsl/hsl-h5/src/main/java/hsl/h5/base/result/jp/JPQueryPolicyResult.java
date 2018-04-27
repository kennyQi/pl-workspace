package hsl.h5.base.result.jp;
import hsl.h5.base.result.api.ApiResult;
import hsl.pojo.dto.jp.FlightPolicyDTO;

/**
 * 下单前查政策的返回
 * @author yuxx
 *
 */
public class JPQueryPolicyResult extends ApiResult {

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
