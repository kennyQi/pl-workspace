package plfx.api.client.api.v1.gn.response;

import plfx.api.client.common.ApiResponse;

@SuppressWarnings("serial")
public class JPSavePaymentInfoResponse extends ApiResponse {
	/**
	 * 是否保存成功
	 */
	private String is_Success;
	
	/**
	 * 错误信息
	 */
	private  String  error;

	public String getIs_Success() {
		return is_Success;
	}

	public void setIs_Success(String is_Success) {
		this.is_Success = is_Success;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
	
}
