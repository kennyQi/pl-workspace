package hsl.pojo.dto.jp;

/**
 * 机票基本信息
 * 
 * @author yuxx
 * 
 */
public class TicketDto {

	/**
	 * 所属订单
	 */
	private String orderId;

	/**
	 * 票号
	 */
	private String ticketNo;

	/**
	 * 乘客证件号
	 */
	private String idCardNo;

	/**
	 * 出票状态
	 */
	private Integer status;

	public final static Integer STATUS_SUCCESS = 1; // 出票成功
	public final static Integer STATUS_FAIL = 2; // 出票失败
	public final static Integer STATUS_PROCESS = 3; // 出票中

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getTicketNo() {
		return ticketNo;
	}

	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}

	public String getIdCardNo() {
		return idCardNo;
	}

	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
