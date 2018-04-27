package plfx.gjjp.domain.model;

import hg.common.component.BaseModel;
import hg.common.util.DateUtil;
import hg.common.util.UUIDGenerator;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import plfx.jp.domain.model.J;
import plfx.yxgjclient.pojo.param.Flight;

/**
 * @类功能说明：国际机票订单中的航班信息
 * @类修改者：
 * @修改日期：2015-6-30下午3:20:25
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-6-30下午3:20:25
 */
@Entity
@SuppressWarnings("serial")
@Table(name = J.TABLE_PREFIX_GJ + "FLIGHT_CABIN")
public class GJFlightCabin extends BaseModel {

	/** 去程 */
	public final static int TYPE_TAKEOFF = 1;
	/** 回程 */
	public final static int TYPE_BACK = 2;

	/**
	 * 航班类型
	 * 
	 * 1-去程,2-回程
	 */
	@Column(name = "TYPE", columnDefinition = J.TYPE_NUM_COLUM)
	private Integer type;

	/**
	 * 所属订单
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "JP_ORDER_ID")
	private GJJPOrder jpOrder;

	/**
	 * 始发地三字码
	 */
	@Column(name = "ORG_CITY", length = 32)
	private String orgCity;

	/**
	 * 始发地航站楼
	 */
	@Column(name = "ORG_TERM", length = 16)
	private String orgTerm;

	/**
	 * 目的地
	 */
	@Column(name = "DST_CITY", length = 32)
	private String dstCity;

	/**
	 * 目的地航站楼
	 */
	@Column(name = "DST_TERM", length = 16)
	private String dstTerm;

	/**
	 * 市场方航空公司二字码
	 */
	@Column(name = "MARKETING_AIRLINE", length = 32)
	private String marketingAirline;

	/**
	 * 出票方航司二字码
	 */
	@Column(name = "TICKETING_CARRIER", length = 32)
	private String ticketingCarrier;

	/**
	 * 航班号
	 */
	@Column(name = "FLIGHT_NO", length = 32)
	private String flightNo;

	/**
	 * 承运方航空公司二字码
	 */
	@Column(name = "CARRIAGE_AIRLINE", length = 32)
	private String carriageAirline;

	/**
	 * 承运方航班号
	 */
	@Column(name = "CARRIAGE_FLIGHT_NO", length = 32)
	private String carriageFlightno;

	/**
	 * 餐食代码
	 * 
	 * B:早餐；L：午餐；S：小吃
	 */
	@Column(name = "MEAL_CODE", length = 16)
	private String mealCode;

	/**
	 * 舱位等级代码
	 */
	@Column(name = "CABIN_CODE", length = 16)
	private String cabinCode;

	/**
	 * 舱位等级 FIRST、PREMIUM_FIRST、BUSINESS等等
	 */
	@Column(name = "CABIN_CLASS", length = 32)
	private String cabinClass;

	/**
	 * Q 值(税费的一种)
	 */
	@Column(name = "QCHARGE", columnDefinition = J.MONEY_COLUM)
	private Double qcharge;

	/**
	 * 机型
	 */
	@Column(name = "PLANE_TYPE", length = 32)
	private String planeType;

	/**
	 * 出发时间
	 */
	@Column(name = "START_TIME", columnDefinition = J.DATE_COLUM)
	private Date startTime;

	/**
	 * 到达时间
	 */
	@Column(name = "END_TIME", columnDefinition = J.DATE_COLUM)
	private Date endTime;

	/**
	 * 是否共享
	 */
	@Type(type = "yes_no")
	@Column(name = "IS_SHARE")
	private Boolean isShare;

	/**
	 * 飞行时间(包含经停时间,单位分钟)
	 */
	@Column(name = "DURATION", columnDefinition = J.NUM_COLUM)
	private Integer duration;
	
	public void create(Flight flight, Integer type, GJJPOrder jpOrder) {
		setId(UUIDGenerator.getUUID());
		setType(type);
		setJpOrder(jpOrder);
		setOrgCity(flight.getOrgCity());
		setOrgTerm(flight.getOrgTerm());
		setDstCity(flight.getDstCity());
		setDstTerm(flight.getDstTerm());
		setMarketingAirline(flight.getMarketingAirline());
		setTicketingCarrier(flight.getTicketingCarrier());
		setFlightNo(flight.getFlightNo());
		setCarriageAirline(flight.getCarriageAirline());
		setCarriageFlightno(flight.getCarriageFlightno());
		setMealCode(flight.getMealCode());
		setCabinCode(flight.getCabinCode());
		setCabinClass(flight.getCabinClass());
		setQcharge(Double.valueOf(flight.getQcharge()));
		setPlaneType(flight.getPlaneType());
		setStartTime(DateUtil.parseDateTime(flight.getStartTime(), "yyyy-MM-dd HH:mm"));
		setEndTime(DateUtil.parseDateTime(flight.getEndTime(), "yyyy-MM-dd HH:mm"));
		setDuration(GJJPOrder.convertMinutes(flight.getDuration()));
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public GJJPOrder getJpOrder() {
		return jpOrder;
	}

	public void setJpOrder(GJJPOrder jpOrder) {
		this.jpOrder = jpOrder;
	}

	public String getOrgCity() {
		return orgCity;
	}

	public void setOrgCity(String orgCity) {
		this.orgCity = orgCity;
	}

	public String getOrgTerm() {
		return orgTerm;
	}

	public void setOrgTerm(String orgTerm) {
		this.orgTerm = orgTerm;
	}

	public String getDstCity() {
		return dstCity;
	}

	public void setDstCity(String dstCity) {
		this.dstCity = dstCity;
	}

	public String getDstTerm() {
		return dstTerm;
	}

	public void setDstTerm(String dstTerm) {
		this.dstTerm = dstTerm;
	}

	public String getMarketingAirline() {
		return marketingAirline;
	}

	public void setMarketingAirline(String marketingAirline) {
		this.marketingAirline = marketingAirline;
	}

	public String getTicketingCarrier() {
		return ticketingCarrier;
	}

	public void setTicketingCarrier(String ticketingCarrier) {
		this.ticketingCarrier = ticketingCarrier;
	}

	public String getFlightNo() {
		return flightNo;
	}

	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	public String getCarriageAirline() {
		return carriageAirline;
	}

	public void setCarriageAirline(String carriageAirline) {
		this.carriageAirline = carriageAirline;
	}

	public String getCarriageFlightno() {
		return carriageFlightno;
	}

	public void setCarriageFlightno(String carriageFlightno) {
		this.carriageFlightno = carriageFlightno;
	}

	public String getMealCode() {
		return mealCode;
	}

	public void setMealCode(String mealCode) {
		this.mealCode = mealCode;
	}

	public String getCabinCode() {
		return cabinCode;
	}

	public void setCabinCode(String cabinCode) {
		this.cabinCode = cabinCode;
	}

	public String getCabinClass() {
		return cabinClass;
	}

	public void setCabinClass(String cabinClass) {
		this.cabinClass = cabinClass;
	}

	public Double getQcharge() {
		return qcharge;
	}

	public void setQcharge(Double qcharge) {
		this.qcharge = qcharge;
	}

	public String getPlaneType() {
		return planeType;
	}

	public void setPlaneType(String planeType) {
		this.planeType = planeType;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Boolean getIsShare() {
		return isShare;
	}

	public void setIsShare(Boolean isShare) {
		this.isShare = isShare;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

}
