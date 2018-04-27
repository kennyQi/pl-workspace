package hsl.domain.model.yxjp;

import hg.common.component.BaseModel;
import hg.common.util.DateUtil;
import hg.common.util.MoneyUtil;
import hg.common.util.UUIDGenerator;
import hsl.domain.model.M;
import hsl.payment.alipay.pojo.PayNotify;
import hsl.pojo.dto.coupon.CouponDTO;
import hsl.pojo.util.HSLConstants;
import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 支付记录
 *
 * @author zhurz
 * @since 1.7
 */
@Entity
@SuppressWarnings("serial")
@Table(name = M.TABLE_PREFIX_HSL_YJ + "PAY_RECORD")
public class YXJPOrderPayRecord extends BaseModel implements HSLConstants.PaymentPlatformType, HSLConstants.FromType {

	/**
	 * 所属订单
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORDER_ID")
	private YXJPOrder fromOrder;

	/**
	 * 支付来源标识：0 mobile , 1  pc
	 *
	 * @see HSLConstants.FromType
	 */
	@Column(name = "FROM_CLIENT_TYPE", columnDefinition = M.TYPE_NUM_COLUM)
	private Integer fromType;

	/**
	 * 支付订单号=订单号+"-"+md5(支付来源标识+乘客ID+卡券ID)
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
	 * 最后一次请求时间
	 */
	@Column(name = "LAST_REQUEST_DATE", columnDefinition = M.DATE_COLUM)
	private Date lastRequestDate;
	/**
	 * 支付时间
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
	 * 是否结束（当为true时，不可退款）
	 */
	@Type(type = "yes_no")
	@Column(name = "PAY_FINISHED")
	private Boolean payFinished = false;
	/**
	 * 包含的乘客
	 */
	@ManyToMany
	private List<YXJPOrderPassenger> passengers = new ArrayList<YXJPOrderPassenger>();

	/**
	 * 总支付额=支付金额+实际卡券优惠金额
	 */
	@Column(name = "TOTALPAY_AMOUNT", columnDefinition = M.MONEY_COLUM)
	private Double totalPayAmount = 0d;

	/**
	 * 包含的优惠券ID（半角逗号分隔）
	 */
	@Column(name = "USED_COUPON_IDS", columnDefinition = M.TEXT_COLUM)
	private String usedCouponIds = "";
	/**
	 * 卡券优惠金额
	 */
	@Column(name = "COUPON_AMOUNT", columnDefinition = M.MONEY_COLUM)
	private Double couponAmount = 0d;
	/**
	 * 实际卡券优惠金额（如果此次支付金额为101使用200的优惠券，实际为101）
	 */
	@Column(name = "COUPON_USED_AMOUNT", columnDefinition = M.MONEY_COLUM)
	private Double couponUsedAmount = 0d;
	/**
	 * 退款明细
	 */
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "payRecord")
	private List<YXJPOrderPayRefundRecord> refundRecords;

	public YXJPOrder getFromOrder() {
		return fromOrder;
	}

	public void setFromOrder(YXJPOrder fromOrder) {
		this.fromOrder = fromOrder;
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
		if (paySuccess == null)
			paySuccess = false;
		return paySuccess;
	}

	public void setPaySuccess(Boolean paySuccess) {
		this.paySuccess = paySuccess;
	}

	public List<YXJPOrderPassenger> getPassengers() {
		if (passengers == null)
			passengers = new ArrayList<YXJPOrderPassenger>();
		return passengers;
	}

	public void setPassengers(List<YXJPOrderPassenger> passengers) {
		this.passengers = passengers;
	}

	public Double getTotalPayAmount() {
		return totalPayAmount;
	}

	public void setTotalPayAmount(Double totalPayAmount) {
		this.totalPayAmount = totalPayAmount;
	}

	public String getUsedCouponIds() {
		if (usedCouponIds == null)
			usedCouponIds = "";
		return usedCouponIds;
	}

	public void setUsedCouponIds(String usedCouponIds) {
		this.usedCouponIds = usedCouponIds;
	}

	public Double getCouponAmount() {
		return couponAmount;
	}

	public void setCouponAmount(Double couponAmount) {
		this.couponAmount = couponAmount;
	}

	public Double getCouponUsedAmount() {
		return couponUsedAmount;
	}

	public void setCouponUsedAmount(Double couponUsedAmount) {
		this.couponUsedAmount = couponUsedAmount;
	}

	public List<YXJPOrderPayRefundRecord> getRefundRecords() {
		return refundRecords;
	}

	public void setRefundRecords(List<YXJPOrderPayRefundRecord> refundRecords) {
		this.refundRecords = refundRecords;
	}

	public Date getLastRequestDate() {
		return lastRequestDate;
	}

	public void setLastRequestDate(Date lastRequestDate) {
		this.lastRequestDate = lastRequestDate;
	}

	public Integer getFromType() {
		return fromType;
	}

	public void setFromType(Integer fromType) {
		this.fromType = fromType;
	}

	public Boolean getPayFinished() {
		if (payFinished == null)
			payFinished = false;
		return payFinished;
	}

	public void setPayFinished(Boolean payFinished) {
		this.payFinished = payFinished;
	}

	// --------------------------------------------------------------------

	/**
	 * 生成支付订单号
	 *
	 * @param fromType     支付来源类型{@link HSLConstants.FromType}
	 * @param orderNo      易行机票订单号
	 * @param passengerIds 乘客ID
	 * @param couponIds    卡券ID
	 * @return
	 */
	public static String generatePayOrderNo(Integer fromType, String orderNo, List<String> passengerIds, List<String> couponIds) {
		StringBuilder payOrderNo = new StringBuilder(orderNo);
		payOrderNo.append('-');
		StringBuilder ids = new StringBuilder(fromType.toString());
		for (String passengerId : passengerIds) {
			ids.append(passengerId);
		}
		if (couponIds != null) {
			for (String couponId : couponIds) {
				ids.append(couponId);
			}
		}
		payOrderNo.append(DigestUtils.md5Hex(ids.toString()));
		if (payOrderNo.length() > 64) {
			return payOrderNo.subSequence(0, 64).toString();
		}
		return payOrderNo.toString();
	}

	private transient Manager manager;

	public Manager manager() {
		if (manager == null)
			manager = new Manager();
		return manager;
	}

	public class Manager {

		/**
		 * 计算金额
		 *
		 * @param passengers 待支付乘客
		 * @param coupons    使用的卡券
		 */
		private void computePrice(List<YXJPOrderPassenger> passengers, List<CouponDTO> coupons) {

			// 总支付额=支付金额+实际卡券优惠金额
			Double totalPayAmount = 0d;
			// 支付金额
			Double payAmount = 0d;
			// 卡券优惠金额
			Double couponAmount = 0d;
			// 实际卡券优惠金额（如果此次支付金额为101使用200的优惠券，实际为101）
			Double couponUsedAmount = 0d;
			// 包含的优惠券ID（半角逗号分隔）
			StringBuilder usedCouponIds = new StringBuilder();

			// 计算总支付额
			for (YXJPOrderPassenger passenger : passengers) {
				if (HSLConstants.YXJPOrderPassengerTicket.STATUS_PAY_WAIT.equals(passenger.getTicket().getStatus())) {
					totalPayAmount = MoneyUtil.round(totalPayAmount + passenger.getPayInfo().getPrice(), 2);
				}
			}

			// 计算卡券优惠金额
			if (coupons != null) {
				for (CouponDTO coupon : coupons) {
					// 卡券面值
					double faceValue = coupon.getBaseInfo().getCouponActivity().getBaseInfo().getFaceValue();
					couponAmount = MoneyUtil.round(couponAmount + faceValue, 2);
					// 记录使用的卡券ID
					usedCouponIds.append(usedCouponIds.length() > 0 ? "," : "").append(coupon.getId());
				}
			}

			// 计算实际卡券优惠金额
			if (couponAmount > totalPayAmount) {
				couponUsedAmount = totalPayAmount;
			} else {
				couponUsedAmount = couponAmount;
			}

			// 计算支付金额
			payAmount = MoneyUtil.round(totalPayAmount - couponUsedAmount, 2);

			// 设置
			setPayAmount(payAmount);
			setTotalPayAmount(totalPayAmount);
			setUsedCouponIds(usedCouponIds.toString());
			setCouponAmount(couponAmount);
			setCouponUsedAmount(couponUsedAmount);

			// 如果需要付款的金额等于0则直接算支付成功
			if (getPayAmount() == 0) {
				setPayDate(new Date());
				setPaySuccess(true);
			}
		}

		/**
		 * 创建订单支付记录
		 *
		 * @param fromType   支付来源类型{@link HSLConstants.FromType}
		 * @param order      易行机票订单
		 * @param payOrderNo 支付订单号
		 * @param toAccount  收款帐号
		 * @param remark     备注
		 * @param passengers 待支付乘客
		 * @param coupons    使用的卡券
		 * @return
		 */
		public YXJPOrderPayRecord create(Integer fromType, YXJPOrder order, String payOrderNo, String toAccount, String remark,
										 List<YXJPOrderPassenger> passengers, List<CouponDTO> coupons) {

			// 设置ID
			setId(UUIDGenerator.getUUID());

			// 设置基本信息
			setFromType(fromType);
			setFromOrder(order);
			setPayOrderNo(payOrderNo);
			setRemark(remark);

			// 订单名称 = 机票[出发日期(yyyyMMdd)][出发城市(三字码)-到达城市(三字码)][航班号]x乘客数量
			// 例：机票[20151212][上海(SHA)-广州(CAN)][CZ3538]x3
			YXJPOrderFlightBaseInfo flightInfo = order.getFlight().getBaseInfo();
			setPayOrderName(String.format("机票[%s][%s(%s)-%s(%s)][%s]x%d",
					DateUtil.formatDateTime(flightInfo.getStartTime(), "yyyyMMdd"),
					flightInfo.getOrgCityName(), flightInfo.getOrgCity(),
					flightInfo.getDstCityName(), flightInfo.getDstCity(),
					flightInfo.getFlightNo(), passengers.size()));

			// 设置支付信息
			setPaymentPlatformType(PAYMENT_PLATFORM_TYPE_ALIPAY);
			setToAccount(toAccount);

			// 设置创建日期
			Date now = new Date();
			setCreateDate(now);
			setLastRequestDate(now);

			// 计算金额
			computePrice(passengers, coupons);

			// 设置包含的乘客
			getPassengers().addAll(passengers);

			return YXJPOrderPayRecord.this;
		}

		/**
		 * 支付成功处理
		 *
		 * @param payNotify 支付宝通知
		 */
		public void processPaySuccess(PayNotify payNotify) {
			setPaySuccess(payNotify.isSuccess() || payNotify.isFinished());
			setPayFinished(payNotify.isFinished());
			setTradeNo(payNotify.getTradeNo());
			setFromAccount(payNotify.getBuyerEmail());
			setPayDate(payNotify.getGmtPayment());
		}

	}

}
