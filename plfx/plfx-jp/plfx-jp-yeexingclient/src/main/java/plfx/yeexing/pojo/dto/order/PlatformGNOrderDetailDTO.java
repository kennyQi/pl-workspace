package plfx.yeexing.pojo.dto.order;

import plfx.jp.pojo.dto.BaseJpDTO;
import plfx.yeexing.pojo.dto.flight.YeeXingFlightDTO;
import plfx.yeexing.pojo.dto.flight.YeeXingFlightPolicyDTO;

/**
 * 
 * @类功能说明：平台订单详细信息DTO
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2015年7月8日下午3:00:25
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class PlatformGNOrderDetailDTO extends BaseJpDTO{
	
	private JPOrderDTO order;
	private YeeXingFlightDTO flight;
	private YeeXingFlightPolicyDTO flightPolicy;

	public JPOrderDTO getOrder() {
		return order;
	}
	public void setOrder(JPOrderDTO order) {
		this.order = order;
	}
	public YeeXingFlightDTO getFlight() {
		return flight;
	}
	public void setFlight(YeeXingFlightDTO flight) {
		this.flight = flight;
	}
	public YeeXingFlightPolicyDTO getFlightPolicy() {
		return flightPolicy;
	}
	public void setFlightPolicy(YeeXingFlightPolicyDTO flightPolicy) {
		this.flightPolicy = flightPolicy;
	}
	
}
