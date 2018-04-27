package lxs.api.v1.response.user;

import lxs.api.base.ApiResponse;

public class ResetPasswordResponse extends ApiResponse{

	/**
	 * 流程标识
	 */
	private String sagaId;

	public String getSagaId() {
		return sagaId;
	}

	public void setSagaId(String sagaId) {
		this.sagaId = sagaId;
	}
}
