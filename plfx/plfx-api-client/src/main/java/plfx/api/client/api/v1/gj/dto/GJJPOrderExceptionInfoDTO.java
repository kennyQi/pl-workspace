package plfx.api.client.api.v1.gj.dto;

/**
 * @类功能说明：国际机票订单异常信息
 * @类修改者：
 * @修改日期：2015-7-13上午10:27:02
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-7-13上午10:27:02
 */
public class GJJPOrderExceptionInfoDTO {

	/**
	 * 是否为异常订单
	 */
	private Boolean exceptionOrder = false;

	/**
	 * 异常订单调整 金额
	 */
	private Double adjustAmount;

	public Boolean getExceptionOrder() {
		return exceptionOrder;
	}

	public void setExceptionOrder(Boolean exceptionOrder) {
		this.exceptionOrder = exceptionOrder;
	}

	public Double getAdjustAmount() {
		return adjustAmount;
	}

	public void setAdjustAmount(Double adjustAmount) {
		this.adjustAmount = adjustAmount;
	}

}
