package plfx.yeexing.pojo.command.order;

@SuppressWarnings("serial")
public class JPOrderCommand extends JPBaseCommand {
	/**
	 * 订单ID
	 */
	private String id;
	
	/**
	 * 订单号
	 */
	private String orderNo;
	
	/**
	 * 乘客姓名
	 * 多个乘客之间用 ^ 分隔(获取时使用urldecode解密)
	 */
	private String passengerName;
	
	/**
	 * 票号
	 * 票号之间用 ^分隔，并与姓名相对应
	 */
	private String airId;	
	
	/**
	 * 通知类型
	 * 1：出票成功通知
	 */
	private String type;					

	/**
	 * 新pnr
	 * 换编码出票后的pnr，如没有，则为空
	 */
	private String newPnr;
	
	/** 异常订单调整 金额 admin端用*/
	private Double adjustAmount;
	
	/**
	 * 异常订单调整凭证 admin端用
	 */
	private String voucherPicture;
	
	/**
	 * 异常订单调整原因 admin端用
	 */
	private String adjustReason;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Double getAdjustAmount() {
		return adjustAmount;
	}

	public void setAdjustAmount(Double adjustAmount) {
		this.adjustAmount = adjustAmount;
	}

	public String getVoucherPicture() {
		return voucherPicture;
	}

	public void setVoucherPicture(String voucherPicture) {
		this.voucherPicture = voucherPicture;
	}

	public String getAdjustReason() {
		return adjustReason;
	}

	public void setAdjustReason(String adjustReason) {
		this.adjustReason = adjustReason;
	}

	public String getPassengerName() {
		return passengerName;
	}

	public void setPassengerName(String passengerName) {
		this.passengerName = passengerName;
	}

	public String getAirId() {
		return airId;
	}

	public void setAirId(String airId) {
		this.airId = airId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getNewPnr() {
		return newPnr;
	}

	public void setNewPnr(String newPnr) {
		this.newPnr = newPnr;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
