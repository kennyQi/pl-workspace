package slfx.api.v1.response.mp;

import slfx.api.base.ApiResponse;
import slfx.mp.pojo.dto.order.MPOrderStatusDTO;

@SuppressWarnings("serial")
public class MPSyncOrderResponse extends ApiResponse {

	/**
	 * 门票订单状态
	 */
	public MPOrderStatusDTO status;

	/**
	 * 取消原因
	 */
	public String cancelRemark;

	public MPOrderStatusDTO getStatus() {
		return status;
	}

	public void setStatus(MPOrderStatusDTO status) {
		this.status = status;
	}

	public String getCancelRemark() {
		return cancelRemark;
	}

	public void setCancelRemark(String cancelRemark) {
		this.cancelRemark = cancelRemark;
	}

}
