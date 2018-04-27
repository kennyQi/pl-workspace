package slfx.jp.pojo.dto.order;

import slfx.jp.pojo.dto.BaseJpDTO;
import slfx.jp.pojo.dto.flight.SlfxFlightDTO;
import slfx.jp.pojo.dto.flight.SlfxFlightPolicyDTO;

/**
 * 
 * @类功能说明：平台订单详细信息DTO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年7月31日下午4:55:56
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class PlatformOrderDetailDTO extends BaseJpDTO{
	
	private JPOrderDTO order;
	private SlfxFlightDTO flight;
	private SlfxFlightPolicyDTO flightPolicy;
	
	//TODO add property {order operator log} here
	
	public JPOrderDTO getOrder() {
		return order;
	}
	public void setOrder(JPOrderDTO order) {
		this.order = order;
	}
	public SlfxFlightDTO getFlight() {
		return flight;
	}
	public void setFlight(SlfxFlightDTO flight) {
		this.flight = flight;
	}
	public SlfxFlightPolicyDTO getFlightPolicy() {
		return flightPolicy;
	}
	public void setFlightPolicy(SlfxFlightPolicyDTO flightPolicy) {
		this.flightPolicy = flightPolicy;
	}
	
}
