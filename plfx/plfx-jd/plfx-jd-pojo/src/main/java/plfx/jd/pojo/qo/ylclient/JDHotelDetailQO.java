package plfx.jd.pojo.qo.ylclient;

import java.io.Serializable;

import plfx.jd.pojo.system.enumConstants.EnumPaymentType;
/***
 * 
 * @类功能说明：酒店详情搜索QO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yaosanfeng
 * @创建时间：2015年6月24日下午4:57:25
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class JDHotelDetailQO implements Serializable {
	/**
	 * 到达时间
	 */
	protected java.util.Date arrivalDate;
	/**
	 * 离店时间
	 */
	protected java.util.Date departureDate;
	/**
	 * 酒店ids
	 */
	protected String hotelIds;
	/**
	 * 房间类型id
	 */
	protected String roomTypeId;
	/**
	 * 产品Id
	 */
	protected int ratePlanId;
	/**
	 * 支付类型
	 */
	protected EnumPaymentType paymentType;
	/**
	 * 其他条件
	 */
	protected String options;

	public java.util.Date getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(java.util.Date value) {
		this.arrivalDate = value;
	}

	public java.util.Date getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(java.util.Date value) {
		this.departureDate = value;
	}

	public String getHotelIds() {
		return hotelIds;
	}

	public void setHotelIds(String value) {
		this.hotelIds = value;
	}

	public String getRoomTypeId() {
		return roomTypeId;
	}

	public void setRoomTypeId(String value) {
		this.roomTypeId = value;
	}

	public int getRatePlanId() {
		return ratePlanId;
	}

	public void setRatePlanId(int value) {
		this.ratePlanId = value;
	}

	public EnumPaymentType getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(EnumPaymentType value) {
		this.paymentType = value;
	}

	public String getOptions() {
		return options;
	}

	public void setOptions(String value) {
		this.options = value;
	}

}
