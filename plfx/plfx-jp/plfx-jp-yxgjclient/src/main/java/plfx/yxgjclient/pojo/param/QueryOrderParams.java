package plfx.yxgjclient.pojo.param;

/**
 * 国际机票订单查询参数
 * @author guotx
 * 2015-14
 */
public class QueryOrderParams extends BaseParam{
	
	/**
	 * 订单号，非必填
	 */
	private String orderId;
	/**
	 * 外部订单号，非必填
	 */
	private String extOrderId;
	/**
	 * 旅客姓名
	 */
	private String passengerName;
	/**
	 * 机票票号
	 */
	private String ordDetEticketNo;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getExtOrderId() {
		return extOrderId;
	}

	public void setExtOrderId(String extOrderId) {
		this.extOrderId = extOrderId;
	}

	public String getPassengerName() {
		return passengerName;
	}

	public void setPassengerName(String passengerName) {
		this.passengerName = passengerName;
	}

	public String getOrdDetEticketNo() {
		return ordDetEticketNo;
	}

	public void setOrdDetEticketNo(String ordDetEticketNo) {
		this.ordDetEticketNo = ordDetEticketNo;
	}

}
