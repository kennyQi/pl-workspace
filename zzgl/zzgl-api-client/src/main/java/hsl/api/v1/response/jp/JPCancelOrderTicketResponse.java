package hsl.api.v1.response.jp;

import hsl.api.base.ApiResponse;

public class JPCancelOrderTicketResponse extends ApiResponse{
	/**
	 * 订单状态
	 */
	private Integer status;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
