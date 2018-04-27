
package plfx.api.client.api.v1.jd.request.command;

import plfx.api.client.base.slfx.ApiPayload;
import plfx.jd.pojo.dto.ylclient.order.OrderCreateCommandDTO;
/***
 * 
 * @类功能说明：创建订单Command
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yaosanfeng
 * @创建时间：2015年6月24日下午4:31:11
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class JDOrderCreateApiCommand extends ApiPayload{
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
	/**
	 * 创建订单dto
	 */
	private OrderCreateCommandDTO orderCreateDTO;


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

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	
}
