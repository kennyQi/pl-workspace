package plfx.api.client.api.v1.gj.dto;

import java.util.Date;

import plfx.api.client.common.PlfxApiConstants.GJ;

/**
 * @类功能说明：平台订单支付信息
 * @类修改者：
 * @修改日期：2015-7-7下午4:30:01
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-7-7下午4:30:01
 */
public class GJJPOrderPayInfoDTO {

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
