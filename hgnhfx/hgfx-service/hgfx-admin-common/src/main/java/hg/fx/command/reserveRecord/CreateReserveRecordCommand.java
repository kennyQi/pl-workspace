package hg.fx.command.reserveRecord;

import hg.framework.common.base.BaseSPICommand;


/**
 * 创建里程变更记录
 * @author cangs
 */
@SuppressWarnings("serial")
public class CreateReserveRecordCommand extends BaseSPICommand {

	/** 1--交易 */
	public static final Integer RECORD_TYPE_TRADE = 1;
	/** 2--交易退款 */
	public static final Integer RECORD_TYPE_REFUND = 2;
	/** 3--(后台)备付金充值 */
	public static final Integer RECORD_TYPE_RECHARGE = 3;
	/** 4--(在线)备付金充值  0.1版本无该类型*/
	public static final Integer RECORD_TYPE_RECHARGE_ONLINE = 4;
	/** 5--月末返利 */
	public static final Integer RECORD_TYPE_REBATE = 5;
	
	
	/** 扣款成功 */
	public static final Integer RECORD_STATUS_KOUKUAN_SUCC = 1;
	/** 充值成功 */
	public static final Integer RECORD_STATUS_CHONGZHI_SUCC = 2;
	/** 退款成功 */
	public static final Integer RECORD_STATUS_REFUND_SUCC = 3;
	
	
	
	/**
	 * 商户ID
	 */
	private String distributorID;
	
	/**
	 * 变化额 
	 */
	private Long increment;
	
	/**
	 * 明细类型  
	 * 1--交易  2--交易退款  3--后台备付金充值  4--在线备付金充值  5--月末返利
	 */
	private Integer type;
	
	
	/**
	 * 商品名称
	 * type=3,4时 商品名字=备付金充值
	 * type=5时 商品名字=月末返利
	 * 其他类型名字为product里的商品名称
	 * */
	private String prodName;
	
	/**
	 * 订单编号
	 * type=1,2(交易或交易退)时  此字段才有值
	 */
	private String orderCode;
	
	/**
	 * 交易号
	 */
	private String tradeNo;
	
	/**
	 * 会员卡号
	 * type=1,2(交易或交易退)时  此字段才有值
	 */
	private String cardNo;
	
	/**
	 * 状态
	 *  1--扣款成功   2--充值成功  3--退款成功
	 */
	private Integer status;
	
	/**
	 * 截图证明
	 * type=3 此字段有值
	 */
	private String provePath;
	
	/**
	 * 处理人ID
	 */
	private String authUserID;
	
	public String getDistributorID() {
		return distributorID;
	}

	public void setDistributorID(String distributorID) {
		this.distributorID = distributorID;
	}

	public Long getIncrement() {
		return increment;
	}

	public void setIncrement(Long increment) {
		this.increment = increment;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getProvePath() {
		return provePath;
	}

	public void setProvePath(String provePath) {
		this.provePath = provePath;
	}

	public String getAuthUserID() {
		return authUserID;
	}

	public void setAuthUserID(String authUserID) {
		this.authUserID = authUserID;
	}
	
}
