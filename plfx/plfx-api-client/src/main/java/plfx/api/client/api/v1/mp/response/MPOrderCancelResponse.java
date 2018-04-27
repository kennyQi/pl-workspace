package plfx.api.client.api.v1.mp.response;

import plfx.api.client.base.slfx.ApiResponse;

public class MPOrderCancelResponse extends ApiResponse {
	private static final long serialVersionUID = 1L;
	/**
	 * 是否取消成功
	 */
	private Boolean cancelSuccess;

	public Boolean getCancelSuccess() {
		return cancelSuccess;
	}

	public void setCancelSuccess(Boolean cancelSuccess) {
		this.cancelSuccess = cancelSuccess;
	}

}
