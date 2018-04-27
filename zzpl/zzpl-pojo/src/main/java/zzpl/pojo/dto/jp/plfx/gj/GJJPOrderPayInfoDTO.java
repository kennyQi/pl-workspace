package zzpl.pojo.dto.jp.plfx.gj;

import java.util.Date;

import zzpl.pojo.dto.BaseDTO;


@SuppressWarnings("serial")
public class GJJPOrderPayInfoDTO extends BaseDTO{
	/**
	 * 支付价格
	 */
	private Double totalPrice;

	/**
	 * 支付时间
	 */
	private Date payTime;

	/**
	 * 支付状态
	 * 
	 * @see GJ#ORDER_PAY_STATUS_MAP
	 */
	private Integer status;

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
