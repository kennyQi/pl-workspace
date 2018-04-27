package hg.dzpw.domain.model.order;

import hg.dzpw.domain.model.M;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

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
@Embeddable
public class TicketOrderPayInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 支付时间
	 */
	@Column(name = "PAY_DATE", columnDefinition = M.DATE_COLUM)
	private Date payDate;

	/**
	 * 金额
	 */
	@Column(name = "PRICE", columnDefinition = M.MONEY_COLUM)
	private Double price;
	
	@Column(name = "REFUND_ALIPAY_ACCOUNT", length = 256)
	private String refundAliPayAccount;

	/**
	 * 支付状态(0,未支付；1,已支付；2,已收款)
	 
	@Column(name = "PAID", columnDefinition = M.TYPE_NUM_COLUM)
	private int paid;*/
	
	/**
	 * 此订单经销商的手续费
	 */
	@Column(name = "DEALER_SETTLEMENT_FEE", columnDefinition = M.MONEY_COLUM)
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