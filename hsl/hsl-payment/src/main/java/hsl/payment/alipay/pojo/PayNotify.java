package hsl.payment.alipay.pojo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * 支付结果通知
 *
 * @author zhurz
 * @since 1.7
 */
public class PayNotify {

	/**
	 * out_trade_no
	 * 商户订单号
	 */
	private String outTradeNo;

	/**
	 * trade_no
	 * 支付宝交易号
	 */
	private String tradeNo;

	/**
	 * total_fee
	 * 交易金额
	 */
	private Double totalFee;

	/**
	 * buyer_email
	 * 付款用户
	 */
	private String buyerEmail;

	/**
	 * seller_Email
	 * 收款用户
	 */
	private String sellerEmail;

	/**
	 * trade_status
	 * 交易状态
	 *
	 * @see PayNotify#TRADE_SUCCESS
	 * @see PayNotify#TRADE_FINISHED
	 */
	private String tradeStatus;

	/**
	 * gmt_create
	 * 交易创建时间
	 */
	private Date gmtCreate;

	/**
	 * gmt_payment
	 * 交易付款时间
	 */
	private Date gmtPayment;

	/**
	 * notify_id
	 * 通知校验ID
	 */
	private String notifyId;

	public PayNotify() {
	}

	public PayNotify(Map<String, String> alipayParams) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		setOutTradeNo(alipayParams.get("out_trade_no"));
		setTradeNo(alipayParams.get("trade_no"));
		setTotalFee(Double.valueOf(alipayParams.get("total_fee")));
		setBuyerEmail(alipayParams.get("buyer_email"));
		setSellerEmail(alipayParams.get("seller_email"));
		setNotifyId(alipayParams.get("notify_id"));
		setTradeStatus(alipayParams.get("trade_status"));
		try {
			setGmtCreate(sdf.parse(alipayParams.get("gmt_create")));
			setGmtPayment(sdf.parse(alipayParams.get("gmt_payment")));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return 是否成功（可退款）
	 */
	public boolean isSuccess() {
		return TRADE_SUCCESS.equals(getTradeStatus());
	}

	/**
	 * @return 是否交易成功且结束（不可退款）
	 */
	public boolean isFinished() {
		return TRADE_FINISHED.equals(getTradeStatus());
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public Double getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(Double totalFee) {
		this.totalFee = totalFee;
	}

	public String getBuyerEmail() {
		return buyerEmail;
	}

	public void setBuyerEmail(String buyerEmail) {
		this.buyerEmail = buyerEmail;
	}

	public String getSellerEmail() {
		return sellerEmail;
	}

	public void setSellerEmail(String sellerEmail) {
		this.sellerEmail = sellerEmail;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Date getGmtPayment() {
		return gmtPayment;
	}

	public void setGmtPayment(Date gmtPayment) {
		this.gmtPayment = gmtPayment;
	}

	public String getNotifyId() {
		return notifyId;
	}

	public void setNotifyId(String notifyId) {
		this.notifyId = notifyId;
	}

	public String getTradeStatus() {
		return tradeStatus;
	}

	public void setTradeStatus(String tradeStatus) {
		this.tradeStatus = tradeStatus;
	}

	// -- 交易状态 --
	/**
	 * 交易成功，且可对该交易做操作，如：多级分润、退款等。
	 */
	public static final String TRADE_SUCCESS = "TRADE_SUCCESS";
	/**
	 * 交易成功且结束，即不可再做任何操作
	 */
	public static final String TRADE_FINISHED = "TRADE_FINISHED";
	/**
	 * 交易创建，等待买家付款。
	 */
	public static final String WAIT_BUYER_PAY = "WAIT_BUYER_PAY";
	/**
	 * 在指定时间段内未支付时关闭的交易；
	 * 在交易完成全额退款成功时关闭的交易。
	 */
	public static final String TRADE_CLOSED = "TRADE_CLOSED";
	/**
	 * 等待卖家收款（买家付款后，如果卖家账号被冻结）。
	 */
	public static final String TRADE_PENDING = "TRADE_PENDING";

}
