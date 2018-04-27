package hsl.pojo.qo.hotel;

import java.util.Date;

import slfx.jd.pojo.system.enumConstants.EnumPaymentType;
import hg.common.component.BaseQo;

@SuppressWarnings("serial")
public class JDHotelDetailQO extends BaseQo {
	/**
	 * 入住日期
	 */
	protected Date arrivalDate;
	/**
	 * 离店日期
	 */
	protected  Date departureDate;
	/**
	 * 酒店ID列表
	 */
	protected String hotelIds;
	/**
	 * 房型编码
	 */
	protected String roomTypeId;
	/**
	 * 产品编码   默认请传0
	 */
	protected int ratePlanId;
	/**
	 * 支付方式
	 */
	protected EnumPaymentType paymentType;
	/**
	 * 其他条件   (0.无1.酒店详情2.房型3.图片4.当前不可销售的rp不出现在结果里（该选项多个酒店也有效）5.每日价格数组输出未DRR计算的原始价格 7.返回汇率信息#ExchangeRateList)
	 */
	protected String options;
	
	
	public Date getArrivalDate() {
		return arrivalDate;
	}
	public void setArrivalDate(Date arrivalDate) {
		this.arrivalDate = arrivalDate;
	}
	public Date getDepartureDate() {
		return departureDate;
	}
	public void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
	}
	public String getHotelIds() {
		return hotelIds;
	}
	public void setHotelIds(String hotelIds) {
		this.hotelIds = hotelIds;
	}
	public String getRoomTypeId() {
		return roomTypeId;
	}
	public void setRoomTypeId(String roomTypeId) {
		this.roomTypeId = roomTypeId;
	}
	public int getRatePlanId() {
		return ratePlanId;
	}
	public void setRatePlanId(int ratePlanId) {
		this.ratePlanId = ratePlanId;
	}
	public EnumPaymentType getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(EnumPaymentType paymentType) {
		this.paymentType = paymentType;
	}
	public String getOptions() {
		return options;
	}
	public void setOptions(String options) {
		this.options = options;
	}
	
	
	
	


}
