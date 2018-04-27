package plfx.api.client.api.v1.gj.response;

import plfx.api.client.api.v1.gj.dto.GJFlightPolicyDTO;
import plfx.api.client.common.ApiResponse;

/**
 * @类功能说明：国际航班组合政策响应
 * @类修改者：
 * @修改日期：2015-7-8下午5:52:23
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-7-8下午5:52:23
 */
@SuppressWarnings("serial")
public class FlightPolicyGJResponse extends ApiResponse {

	/**
	 * 结果代码：航班舱位组合token过期或不存在
	 */
	public final static String RESULT_NOT_EXIST = "-1";
	
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
