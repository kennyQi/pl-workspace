package hsl.pojo.qo.mp;

import hg.common.component.BaseQo;

/**
 * 商旅分销后台订单查询
 * @author liujz
 *
 */
@SuppressWarnings("serial")
public class DWZPlatdormOrderQO extends BaseQo {


	/**
	 * 经销商订单号
	 */
	private String dealerOrderCode;

	/**
	 * 下单起始时间
	 */
	private String createDateBegin;
	/**
	 * 下单终止时间
	 */
	private String createDateEnd;
	/**
	 * 预定人
	 */
	private String bookMan;
	/**
	 * 订单状态
	 */
	private Integer orderStatus;
	/**
	 * 景点名称
	 */
	private String scenicSpotsName;
	/**
	 * 游玩人
	 */
	private String travelerName;
	/**
	 * 支付状态
	 */
	private String paymentStatus;

	/**
	 * 支付类型
	 */
	private Integer paymentType;

	/**
	 * 景点名称是否支持模糊查询
	 */
	private Boolean scenicSpotsNameLike = true;

	/**
	 * 下单时间排序
	 */
	private Boolean createDateAsc;



	public String getDealerOrderCode() {
		return dealerOrderCode;
	}

	public void setDealerOrderCode(String dealerOrderCode) {
		this.dealerOrderCode = dealerOrderCode;
	}



	public String getCreateDateBegin() {
		return createDateBegin;
	}

	public void setCreateDateBegin(String createDateBegin) {
		this.createDateBegin = createDateBegin;
	}

	public String getCreateDateEnd() {
		return createDateEnd;
	}

	public void setCreateDateEnd(String createDateEnd) {
		this.createDateEnd = createDateEnd;
	}

	public String getBookMan() {
		return bookMan;
	}

	public void setBookMan(String bookMan) {
		this.bookMan = bookMan;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getScenicSpotsName() {
		return scenicSpotsName;
	}

	public void setScenicSpotsName(String scenicSpotsName) {
		this.scenicSpotsName = scenicSpotsName;
	}

	public String getTravelerName() {
		return travelerName;
	}

	public void setTravelerName(String travelerName) {
		this.travelerName = travelerName;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public Integer getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(Integer paymentType) {
		this.paymentType = paymentType;
	}

	public Boolean getScenicSpotsNameLike() {
		return scenicSpotsNameLike;
	}

	public void setScenicSpotsNameLike(Boolean scenicSpotsNameLike) {
		this.scenicSpotsNameLike = scenicSpotsNameLike;
	}


	public Boolean getCreateDateAsc() {
		return createDateAsc;
	}

	public void setCreateDateAsc(Boolean createDateAsc) {
		this.createDateAsc = createDateAsc;
	}
	
}
