package hg.dzpw.dealer.client.dto.order;

import hg.dzpw.dealer.client.common.EmbeddDTO;

import java.util.Date;

/**
 * @类功能说明：订单支付信息
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：zzx
 * @创建时间：2014-11-10下午2:58:26
 * @版本：V1.0
 */
@SuppressWarnings("serial")
public class TicketOrderPayInfoDTO extends EmbeddDTO {

	/**
	 * 支付时间
	 */
	private Date payDate;

	/**
	 * 金额
	 */
	private Double price;
	
	/**
	 * 支付宝账号 用于退款
	 */
	private String refundAliPayAccount;
	
	/**
	 * 经销商手续费，计算得到
	 */
	private Double dealerSettlementFee = 0d;

	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getRefundAliPayAccount() {
		return refundAliPayAccount;
	}

	public void setRefundAliPayAccount(String refundAliPayAccount) {
		this.refundAliPayAccount = refundAliPayAccount;
	}

	public Double getDealerSettlementFee() {
		return dealerSettlementFee;
	}

	public void setDealerSettlementFee(Double dealerSettlementFee) {
		this.dealerSettlementFee = dealerSettlementFee;
	}

}