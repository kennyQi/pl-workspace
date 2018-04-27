package hsl.h5.base.result.jp;
import hsl.h5.base.result.api.ApiResult;
public class JPCancelOrderTicketResult extends ApiResult{
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
