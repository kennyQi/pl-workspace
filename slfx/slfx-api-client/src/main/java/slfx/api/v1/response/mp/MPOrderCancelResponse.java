package slfx.api.v1.response.mp;

import slfx.api.base.ApiResponse;

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
