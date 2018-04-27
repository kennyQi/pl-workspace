package hsl.pojo.qo.yxjp;

import hg.common.annotation.QOAttr;
import hg.common.annotation.QOAttrType;
import hg.common.annotation.QOConfig;
import hg.common.component.BaseQo;

import java.util.Date;

/**
 * 易行机票订单支付记录查询
 *
 * @author zhurz
 * @since 1.7
 */
@SuppressWarnings("serial")
@QOConfig(daoBeanId = "yxjpOrderPayRecordDAO")
public class YXJPOrderPayRecordQO extends BaseQo {

	/**
	 * 所属订单
	 */
	@QOAttr(name = "fromOrder.id")
	private String fromOrderId;

	/**
	 * 支付订单号
	 */
	@QOAttr(name = "payOrderNo")
	private String payOrderNo;

	/**
	 * 第三方支付流水号
	 */
	@QOAttr(name = "tradeNo")
	private String tradeNo;

	/**
	 * 第三方支付帐号
	 */
	@QOAttr(name = "fromAccount")
	private String fromAccount;

	/**
	 * 支付时间
	 */
	@QOAttr(name = "payDate", type = QOAttrType.GE)
	private Date payDateBegin;
	@QOAttr(name = "payDate", type = QOAttrType.LE)
	private Date payDateEnd;

	/**
	 * 是否成功
	 */
	@QOAttr(name = "paySuccess")
	private Boolean paySuccess;

	/**
	 * 包含的乘客
	 */
	private YXJPOrderPassengerQO passengerQO;

	public String getFromOrderId() {
		return fromOrderId;
	}

	public void setFromOrderId(String fromOrderId) {
		this.fromOrderId = fromOrderId;
	}

	public String getPayOrderNo() {
		return payOrderNo;
	}

	public void setPayOrderNo(String payOrderNo) {
		this.payOrderNo = payOrderNo;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getFromAccount() {
		return fromAccount;
	}

	public void setFromAccount(String fromAccount) {
		this.fromAccount = fromAccount;
	}

	public Date getPayDateBegin() {
		return payDateBegin;
	}

	public void setPayDateBegin(Date payDateBegin) {
		this.payDateBegin = payDateBegin;
	}

	public Date getPayDateEnd() {
		return payDateEnd;
	}

	public void setPayDateEnd(Date payDateEnd) {
		this.payDateEnd = payDateEnd;
	}

	public Boolean getPaySuccess() {
		return paySuccess;
	}

	public void setPaySuccess(Boolean paySuccess) {
		this.paySuccess = paySuccess;
	}

	public YXJPOrderPassengerQO getPassengerQO() {
		return passengerQO;
	}

	public void setPassengerQO(YXJPOrderPassengerQO passengerQO) {
		this.passengerQO = passengerQO;
	}
}
