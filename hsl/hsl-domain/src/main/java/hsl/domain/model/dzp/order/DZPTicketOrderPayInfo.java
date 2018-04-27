package hsl.domain.model.dzp.order;

import hsl.domain.model.M;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * 订单支付信息
 *
 * @author zhurz
 * @since 1.8
 */
@Embeddable
@SuppressWarnings("serial")
public class DZPTicketOrderPayInfo implements Serializable {

	/**
	 * 经销商（本平台）支付成功的时间
	 */
	@Column(name = "DEALER_PAY_DATE", columnDefinition = M.DATE_COLUM)
	private Date dealerPayDate;

	/**
	 * 需要经销商（本平台）支付的订单总金额
	 */
	@Column(name = "DEALER_PRICE", columnDefinition = M.MONEY_COLUM)
	private Double dealerPrice;

	/**
	 * 用户支付成功的时间（冗余）
	 */
	@Column(name = "USER_PAY_DATE", columnDefinition = M.DATE_COLUM)
	private Date userPayDate;

	/**
	 * 需要用户支付的订单总金额
	 */
	@Column(name = "USER_PRICE", columnDefinition = M.MONEY_COLUM)
	private Double userPrice;

	/**
	 * 经销商手续费，计算得到（电子票务回传）
	 */
	@Column(name = "DEALER_SETTLEMENT_FEE", columnDefinition = M.MONEY_COLUM)
	private Double dealerSettlementFee = 0d;
	
	/**
	 * 用户的支付宝账号，用于理财收益（联票用）
	 */
	@Column(name = "REFUND_ALIPAY_ACCOUNT", length = 128)
	private String refundAlipayAccount;

	/**
	 * 用户支付记录
	 * KEY为支付订单号
	 */
	@MapKey(name = "payOrderNo")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fromOrder")
	private Map<String, DZPTicketOrderPayRecord> payRecordMap;

	/**
	 * 用户支付成功的记录
	 */
	@OneToOne(mappedBy = "fromOrder", fetch = FetchType.LAZY)
	private DZPTicketOrderPayRecord paySuccessRecord;

	public Date getDealerPayDate() {
		return dealerPayDate;
	}

	public void setDealerPayDate(Date dealerPayDate) {
		this.dealerPayDate = dealerPayDate;
	}

	public Double getDealerPrice() {
		return dealerPrice;
	}

	public void setDealerPrice(Double dealerPrice) {
		this.dealerPrice = dealerPrice;
	}

	public Date getUserPayDate() {
		return userPayDate;
	}

	public void setUserPayDate(Date userPayDate) {
		this.userPayDate = userPayDate;
	}

	public Double getUserPrice() {
		return userPrice;
	}

	public void setUserPrice(Double userPrice) {
		this.userPrice = userPrice;
	}

	public Double getDealerSettlementFee() {
		return dealerSettlementFee;
	}

	public void setDealerSettlementFee(Double dealerSettlementFee) {
		this.dealerSettlementFee = dealerSettlementFee;
	}

	public String getRefundAlipayAccount() {
		return refundAlipayAccount;
	}

	public void setRefundAlipayAccount(String refundAlipayAccount) {
		this.refundAlipayAccount = refundAlipayAccount;
	}

	public Map<String, DZPTicketOrderPayRecord> getPayRecordMap() {
		return payRecordMap;
	}

	public void setPayRecordMap(Map<String, DZPTicketOrderPayRecord> payRecordMap) {
		this.payRecordMap = payRecordMap;
	}

	public DZPTicketOrderPayRecord getPaySuccessRecord() {
		return paySuccessRecord;
	}

	public void setPaySuccessRecord(DZPTicketOrderPayRecord paySuccessRecord) {
		this.paySuccessRecord = paySuccessRecord;
	}
}