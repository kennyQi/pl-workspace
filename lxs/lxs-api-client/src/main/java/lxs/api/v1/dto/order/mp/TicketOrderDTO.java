package lxs.api.v1.dto.order.mp;

import java.util.Date;

import javax.persistence.Column;

import lxs.api.v1.dto.BaseDTO;

@SuppressWarnings("serial")
public class TicketOrderDTO extends BaseDTO {

	/**
	 * 订单ID
	 */
	private String orderID;

	/**
	 * 订单编号
	 */
	private String orderNO;

	/**
	 * 景区名称
	 */
	private String name;

	/**
	 * 政策类型
	 */
	private Integer type;

	/**
	 * 游玩日期（独立单票必传）
	 */
	private Date travelDate;

	/**
	 * 有效天数(独立单票可入园天数 or 联票自出票后的有效天数)
	 */
	private Integer validDays;

	/**
	 * 联票(与经销商)游玩价总价
	 */
	private Double playPriceSUM;

	/**
	 * 当前状态
	 */
	private Integer currentValue;

	/**
	 * 本地支付状态
	 */
	private Integer localPayStatus;

	/**
	 * 本地状态
	 */
	private Integer localStatus;

	/**
	 * 创建订单时间
	 */
	private Date creatDate;

	/**
	 * 出票时间 即 收到支付宝通知时间
	 */
	private Date printTicketDate;

	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Date getTravelDate() {
		return travelDate;
	}

	public void setTravelDate(Date travelDate) {
		this.travelDate = travelDate;
	}

	public Integer getValidDays() {
		return validDays;
	}

	public void setValidDays(Integer validDays) {
		this.validDays = validDays;
	}

	public Date getCreatDate() {
		return creatDate;
	}

	public void setCreatDate(Date creatDate) {
		this.creatDate = creatDate;
	}

	public Double getPlayPriceSUM() {
		return playPriceSUM;
	}

	public void setPlayPriceSUM(Double playPriceSUM) {
		this.playPriceSUM = playPriceSUM;
	}

	public Integer getCurrentValue() {
		return currentValue;
	}

	public void setCurrentValue(Integer currentValue) {
		this.currentValue = currentValue;
	}

	public String getOrderNO() {
		return orderNO;
	}

	public void setOrderNO(String orderNO) {
		this.orderNO = orderNO;
	}

	public Integer getLocalPayStatus() {
		return localPayStatus;
	}

	public void setLocalPayStatus(Integer localPayStatus) {
		this.localPayStatus = localPayStatus;
	}

	public Integer getLocalStatus() {
		return localStatus;
	}

	public void setLocalStatus(Integer localStatus) {
		this.localStatus = localStatus;
	}

	public Date getPrintTicketDate() {
		return printTicketDate;
	}

	public void setPrintTicketDate(Date printTicketDate) {
		this.printTicketDate = printTicketDate;
	}

}
