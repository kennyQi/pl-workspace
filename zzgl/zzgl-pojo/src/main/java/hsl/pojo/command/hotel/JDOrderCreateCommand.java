package hsl.pojo.command.hotel;
import hsl.pojo.dto.hotel.order.OrderCreateCommandDTO;

import java.io.Serializable;
@SuppressWarnings("serial")
public class JDOrderCreateCommand implements Serializable{
	/**
	 * 经销商平台id
	 */
	private String dealerProjcetId;
	/**
	 * 经销商订单号
	 */
	private String dealerOrderNO;
	/**
	 * 供应商平台Id
	 */
	private String supplierProjcetId;
	/**
	 * 酒店名称
	 */
	private String hotelName;
	/**
	 * 酒店编号
	 */
	private String hotelNO;
	/**
	 * 房间名称
	 */
	private String roomName;
	/**
	 * 价格政策名称
	 */
	private String ratePlanName;
	/**
	 * 总价
	 */
	private Double totalPrice;
	
	
	private OrderCreateCommandDTO orderCreateDTO;

	public String getRatePlanName() {
		return ratePlanName;
	}

	public void setRatePlanName(String ratePlanName) {
		this.ratePlanName = ratePlanName;
	}

	public OrderCreateCommandDTO getOrderCreateDTO() {
		return orderCreateDTO;
	}

	public void setOrderCreateDTO(OrderCreateCommandDTO orderCreateDTO) {
		this.orderCreateDTO = orderCreateDTO;
	}

	public String getDealerProjcetId() {
		return dealerProjcetId;
	}

	public void setDealerProjcetId(String dealerProjcetId) {
		this.dealerProjcetId = dealerProjcetId;
	}

	public String getDealerOrderNO() {
		return dealerOrderNO;
	}

	public void setDealerOrderNO(String dealerOrderNO) {
		this.dealerOrderNO = dealerOrderNO;
	}

	public String getSupplierProjcetId() {
		return supplierProjcetId;
	}

	public void setSupplierProjcetId(String supplierProjcetId) {
		this.supplierProjcetId = supplierProjcetId;
	}

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public String getHotelNO() {
		return hotelNO;
	}

	public void setHotelNO(String hotelNO) {
		this.hotelNO = hotelNO;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	
}
