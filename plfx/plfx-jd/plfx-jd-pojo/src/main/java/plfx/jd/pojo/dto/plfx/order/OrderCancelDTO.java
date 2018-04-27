package plfx.jd.pojo.dto.plfx.order;

import java.util.Date;

import plfx.jd.pojo.dto.ylclient.order.BaseJDDTO;
/**
 * 
 * @类功能说明：订单取消表
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江票量云数据科技股份有限公司
 * @部门：技术部
 * @作者：caizhenghao
 * @创建时间：2015年3月6日上午10:31:51
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class OrderCancelDTO extends BaseJDDTO{
	/**
	 * 取消原因
	 */
	private String  reason;
	/**
	 * 取消日期
	 */
	private Date cancelDate;
	/**
	 * 订单
	 */
	private HotelOrderDTO hotelOrder;
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public Date getCancelDate() {
		return cancelDate;
	}
	public void setCancelDate(Date cancelDate) {
		this.cancelDate = cancelDate;
	}
	public HotelOrderDTO getHotelOrder() {
		return hotelOrder;
	}
	public void setHotelOrder(HotelOrderDTO hotelOrder) {
		this.hotelOrder = hotelOrder;
	}
	
}
