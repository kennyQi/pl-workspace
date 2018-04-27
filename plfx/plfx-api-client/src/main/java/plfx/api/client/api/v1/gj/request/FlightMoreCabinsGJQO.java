package plfx.api.client.api.v1.gj.request;

import plfx.api.client.common.BaseClientQO;
import plfx.api.client.common.api.PlfxApiAction;

/**
 * @类功能说明：国际航班详情
 * @类修改者：
 * @修改日期：2015-7-6下午4:00:38
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-7-6下午4:00:38
 */
@SuppressWarnings("serial")
@PlfxApiAction(PlfxApiAction.GJ_QueryFlightMoreCabins)
public class FlightMoreCabinsGJQO extends BaseClientQO {

	/**
	 * 航班舱位组合token
	 */
	private String flightCabinGroupToken;

	public String getFlightCabinGroupToken() {
		return flightCabinGroupToken;
	}

	public void setFlightCabinGroupToken(String flightCabinGroupToken) {
		this.flightCabinGroupToken = flightCabinGroupToken;
	}

}
