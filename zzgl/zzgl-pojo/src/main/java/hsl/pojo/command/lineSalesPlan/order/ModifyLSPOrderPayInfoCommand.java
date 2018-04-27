package hsl.pojo.command.lineSalesPlan.order;

import hg.common.component.BaseCommand;

import javax.persistence.Column;

/**
 * @类功能说明：
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称： 浙江票量云数据科技有限公司
 * @部门： 技术部
 * @作者： chenxy
 * @创建时间： 2015/12/7 14:06
 */
public class ModifyLSPOrderPayInfoCommand extends BaseCommand {
	/**
	 * 订单ID
	 */
	private String orderId;
	/**
	 * 买家支付宝号
	 */
	private String buyerEmail;
	/**
	 * 支付交易号
	 */
	private String payTradeNo;
	/**
	 * 真实支付价格
	 */
	private Double payPrice;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getBuyerEmail() {
		return buyerEmail;
	}

	public void setBuyerEmail(String buyerEmail) {
		this.buyerEmail = buyerEmail;
	}

	public String getPayTradeNo() {
		return payTradeNo;
	}

	public void setPayTradeNo(String payTradeNo) {
		this.payTradeNo = payTradeNo;
	}

	public Double getPayPrice() {
		return payPrice;
	}

	public void setPayPrice(Double payPrice) {
		this.payPrice = payPrice;
	}
}
