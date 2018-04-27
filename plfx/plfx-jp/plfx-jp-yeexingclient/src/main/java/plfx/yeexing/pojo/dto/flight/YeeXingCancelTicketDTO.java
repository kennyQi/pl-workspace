package plfx.yeexing.pojo.dto.flight;

public class YeeXingCancelTicketDTO {

	/**
	 * 是否成功 
	 * 表示该次操作是否成功 T:成功F：失败
	 */
	private String is_success;

	/**
	 * 错误信息
	 */
	private String error;

	/**
	 * 订单号
	 */
	private String orderid;

	public String getIs_success() {
		return is_success;
	}

	public void setIs_success(String is_success) {
		this.is_success = is_success;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	
	
	
	
}
