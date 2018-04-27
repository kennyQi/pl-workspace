package hsl.domain.model.dzp.order;

import hg.common.component.BaseModel;
import hsl.domain.model.M;
import hsl.pojo.util.HSLConstants;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

/**
 * 支付记录
 *
 * @author zhurz
 * @since 1.8
 */
@Entity
@SuppressWarnings("serial")
@Table(name = M.TABLE_PREFIX_HSL_DZP + "PAY_RECORD")
public class DZPTicketOrderPayRecord extends BaseModel implements HSLConstants.PaymentPlatformType, HSLConstants.FromType {

	/**
	 * 所属订单
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORDER_ID")
	private DZPTicketOrder fromOrder;

	/**
	 * 支付来源标识：0 mobile , 1  pc
	 *
	 * @see HSLConstants.FromType
	 */
	@Column(name = "FROM_CLIENT_TYPE", columnDefinition = M.TYPE_NUM_COLUM)
	private Integer fromType;

	/**
	 * 支付订单号=订单号+"-"+md5(支付来源标识+游客ID升序拼装)
	 * 如果超出64位则截断到64位
	 */
	@Column(name = "PAY_ORDER_NO", length = 64)
	private String payOrderNo;

	/**
	 * 支付订单名称
	 */
	@Column(name = "PAY_ORDER_NAME", length = 64)
	private String payOrderName;

	/**
	 * 第三方支付平台类型
	 *
	 * @see HSLConstants.PaymentPlatformType
	 */
	@Column(name = "PAYMENT_PLATFORM_TYPE", columnDefinition = M.TYPE_NUM_COLUM)
	private Integer paymentPlatformType;
	/**
	 * 第三方支付流水号
	 */
	@Column(name = "TRADE_NO", length = 64)
	private String tradeNo;
	/**
	 * 第三方支付帐号
	 */
	@Column(name = "FROM_ACCOUNT", length = 64)
	private String fromAccount;
	/**
	 * 第三方收款帐号
	 */
	@Column(name = "TO_ACCOUNT", length = 64)
	private String toAccount;
	/**
	 * 支付记录创建时间
	 */
	@Column(name = "CREATE_DATE", columnDefinition = M.DATE_COLUM)
	private Date createDate;
	/**
	 * 最后一次支付请求时间
	 */
	@Column(name = "LAST_REQUEST_DATE", columnDefinition = M.DATE_COLUM)
	private Date lastRequestDate;
	/**
	 * 支付成功时间
	 */
	@Column(name = "PAY_DATE", columnDefinition = M.DATE_COLUM)
	private Date payDate;
	/**
	 * 支付金额
	 */
	@Column(name = "PAY_AMOUNT", columnDefinition = M.MONEY_COLUM)
	private Double payAmount = 0d;
	/**
	 * 备注
	 */
	@Column(name = "REMARK", length = 512)
	private String remark;
	/**
	 * 是否成功
	 */
	@Type(type = "yes_no")
	@Column(name = "PAY_SUCCESS")
	private Boolean paySuccess = false;
	/**
	 * 是否结束（当为true时，不可原路退款）
	 */
	@Type(type = "yes_no")
	@Column(name = "PAY_FINISHED")
	private Boolean payFinished = false;

	public DZPTicketOrder getFromOrder() {
		return fromOrder;
	}

	public void setFromOrder(DZPTicketOrder fromOrder) {
		this.fromOrder = fromOrder;
	}

	public Integer getFromType() {
		return fromType;
	}

	public void setFromType(Integer fromType) {
		this.fromType = fromType;
	}

	public String getPayOrderNo() {
		return payOrderNo;
	}

	public void setPayOrderNo(String payOrderNo) {
		this.payOrderNo = payOrderNo;
	}

	public String getPayOrderName() {
		return payOrderName;
	}

	public void setPayOrderName(String payOrderName) {
		this.payOrderName = payOrderName;
	}

	public Integer getPaymentPlatformType() {
		return paymentPlatformType;
	}

	public void setPaymentPlatformType(Integer paymentPlatformType) {
		this.paymentPlatformType = paymentPlatformType;
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

	public String getToAccount() {
		return toAccount;
	}

	public void setToAccount(String toAccount) {
		this.toAccount = toAccount;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getLastRequestDate() {
		return lastRequestDate;
	}

	public void setLastRequestDate(Date lastRequestDate) {
		this.lastRequestDate = lastRequestDate;
	}

	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

	public Double getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(Double payAmount) {
		this.payAmount = payAmount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Boolean getPaySuccess() {
		return paySuccess;
	}

	public void setPaySuccess(Boolean paySuccess) {
		this.paySuccess = paySuccess;
	}

	public Boolean getPayFinished() {
		return payFinished;
	}

	public void setPayFinished(Boolean payFinished) {
		this.payFinished = payFinished;
	}
}
