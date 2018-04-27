package hsl.domain.model.lineSalesPlan.order;
import hsl.domain.model.M;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Date;

/**
* @类功能说明：订单的支付信息
* @类修改者：
* @公司名称： 浙江票量云科技有限公司
* @部门： 技术部
* @作者： chenxy
* @创建时间：  2015-11-28 14:56:10
* @版本： V1.0
*/
@Embeddable
public class LSPOrderPayInfo {
	/**
	 * 支付帐号
	 */
	@Column(name = "BUYEREMAIL", length=128)
	private String buyerEmail;
	/**
	 * 支付交易号
	 */
	@Column(name = "PAYTRADENO", length=128)
	private String payTradeNo;
	/**
	 * 真实支付价格
	 */
	@Column(name = "PAYPRICE",columnDefinition = M.DOUBLE_COLUM)
	private Double payPrice;
	/**
	 * 支付时间
	 */
	@Column(name = "PAYDATE",columnDefinition = M.DATE_COLUM)
	private Date payDate;
	/**
	 * 退款金额
	 */
	@Column(name="REFUNDPRICE",columnDefinition = M.DOUBLE_COLUM)
	private Double refundPrice;
	/**
	 * 退款时间
	 */
	@Column(name = "REFUNDDATE",columnDefinition = M.DATE_COLUM)
	private Date refundDate;
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

	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

	public Double getRefundPrice() {
		return refundPrice;
	}

	public void setRefundPrice(Double refundPrice) {
		this.refundPrice = refundPrice;
	}

	public Date getRefundDate() {
		return refundDate;
	}

	public void setRefundDate(Date refundDate) {
		this.refundDate = refundDate;
	}
}