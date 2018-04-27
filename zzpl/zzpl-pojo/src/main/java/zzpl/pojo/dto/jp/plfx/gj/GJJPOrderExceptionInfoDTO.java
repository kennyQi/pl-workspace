package zzpl.pojo.dto.jp.plfx.gj;

import zzpl.pojo.dto.BaseDTO;

@SuppressWarnings("serial")
public class GJJPOrderExceptionInfoDTO extends BaseDTO {
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
