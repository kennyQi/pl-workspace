package hsl.domain.model.jp;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * 机票基本信息
 * 
 * @author yuxx
 * 
 */
@Embeddable
public class JPTicket {

	/**
	 * 所属订单
	 */
//	@Column(name = "ORDER_ID")
//	private String orderId;

	/**
	 * 票号
	 */
	@Column(name = "TICKET_NO")
	private String ticketNo;

	/**
	 * 出票状态
	 */
	@Column(name = "STATUS")
	private Integer status;

	
	
	//public final static Integer STATUS_SUCCESS = 1; // 出票成功
	//public final static Integer STATUS_FAIL = 2; // 出票失败
	//public final static Integer STATUS_PROCESS = 3; // 出票中

//	public String getOrderId() {
//		return orderId;
//	}
//
//	public void setOrderId(String orderId) {
//		this.orderId = orderId;
//	}

	public String getTicketNo() {
		return ticketNo;
	}

	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
