package hsl.domain.model.hotel.order;
import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;
import hsl.domain.model.M;
import hsl.pojo.command.hotel.OrderCancelAdminCommand;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
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
@Entity
@Table(name = M.TABLE_PREFIX_HSL_JD + "ORDER_CANCEL")
public class OrderCancel extends BaseModel{
	@Column(name = "REASON",length = 500)
	private String  reason;
	@Column(name = "CANCEL_DATE",columnDefinition = M.DATE_COLUM)
	private Date cancelDate;
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "HOTEL_ORDER_ID")
	private HotelOrder hotelOrder;
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
	public HotelOrder getHotelOrder() {
		return hotelOrder;
	}
	public void setHotelOrder(HotelOrder hotelOrder) {
		this.hotelOrder = hotelOrder;
	}
	public void createOrderCancel(OrderCancelAdminCommand command) {
		setId(UUIDGenerator.getUUID());
		setCancelDate(new Date());
		setReason(command.getReason());
		HotelOrder order = new HotelOrder();
		order.setId(command.getOrderId());
		setHotelOrder(order);
	}
	
}
