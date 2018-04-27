package hsl.pojo.dto.jp;
import hsl.pojo.dto.BaseDTO;

import java.util.Date;
import java.util.List;

public class FlightOrderDTO extends BaseDTO{
	private static final long serialVersionUID = 1L;

	/**
	 * 订单编号
	 */
	private String orderNO;
	
	/**
	 * ---------------------------------订单基本信息----------------------------------
	 * 订单创建时间
	 */
	private Date createTime;

	/**
	 * 订单状态
	 */
	private Integer status;

	/** 支付状态 */
	private Integer payStatus;
	/**
	 * 备注
	 */
	private String note;
	/**
	 * 支付帐号
	 */
	private String buyerEmail;
	/**
	 * 支付订单号
	 */
	private String payTradeNo;
	
	/**
	 * 订单种类
	 * 1：正常订单
	 * 2：取消订单
	 * 3：退款订单
	 */
	private String orderType;
	
	/**
	 * 乘客集合
	 */
	private List<PassengerGNDTO> passengers;
	/**
	 * 下单用户
	 */
	private JPOrderUserDTO jpOrderUser;
	/**
	 * 订单联系人信息
	 */
	private JPOrderLinkInfoDTO jpLinkInfo;
	/**
	 * 航班基本信息
	 */
	private FlightBaseInfoDTO flightBaseInfo;
	/**
	 * 航班价格信息
	 */
	private FlightPriceInfoDTO flightPriceInfo;
	/**
	 * 经销商返回信息
	 */
	private DealerReturnInfoDTO dealerReturnInfo;
	
	
	
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getOrderNO() {
		return orderNO;
	}
	public void setOrderNO(String orderNO) {
		this.orderNO = orderNO;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public List<PassengerGNDTO> getPassengers() {
		return passengers;
	}
	public void setPassengers(List<PassengerGNDTO> passengers) {
		this.passengers = passengers;
	}
	public JPOrderUserDTO getJpOrderUser() {
		return jpOrderUser;
	}
	public void setJpOrderUser(JPOrderUserDTO jpOrderUser) {
		this.jpOrderUser = jpOrderUser;
	}
	public JPOrderLinkInfoDTO getJpLinkInfo() {
		return jpLinkInfo;
	}
	public void setJpLinkInfo(JPOrderLinkInfoDTO jpLinkInfo) {
		this.jpLinkInfo = jpLinkInfo;
	}
	public FlightBaseInfoDTO getFlightBaseInfo() {
		return flightBaseInfo;
	}
	public void setFlightBaseInfo(FlightBaseInfoDTO flightBaseInfo) {
		this.flightBaseInfo = flightBaseInfo;
	}
	public FlightPriceInfoDTO getFlightPriceInfo() {
		return flightPriceInfo;
	}
	public void setFlightPriceInfo(FlightPriceInfoDTO flightPriceInfo) {
		this.flightPriceInfo = flightPriceInfo;
	}
	public DealerReturnInfoDTO getDealerReturnInfo() {
		return dealerReturnInfo;
	}
	public void setDealerReturnInfo(DealerReturnInfoDTO dealerReturnInfo) {
		this.dealerReturnInfo = dealerReturnInfo;
	}
	public String getBuyerEmail() {
		return buyerEmail;
	}
	public void setBuyerEmail(String buyerEmail) {
		this.buyerEmail = buyerEmail;
	}
	public String getPayTradeNo() {
		return payTradeNo;
	}
	public void setPayTradeNo(String payTradeNo) {
		this.payTradeNo = payTradeNo;
	}
}
