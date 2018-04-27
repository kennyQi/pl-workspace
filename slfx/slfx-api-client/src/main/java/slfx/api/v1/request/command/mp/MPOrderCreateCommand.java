package slfx.api.v1.request.command.mp;

import java.util.Date;
import java.util.List;

import slfx.api.v1.request.command.BaseCommand;
import slfx.mp.pojo.dto.order.MPOrderUserInfoDTO;

/**
 * 门票下单
 * 
 * @author yuxx
 */
@SuppressWarnings("serial")
public class MPOrderCreateCommand extends BaseCommand {

	/**
	 * 商城订单号
	 */
	private String dealerOrderId;

	/**
	 * 平台给经销商的价格（供验价）
	 */
	private Double price;

	/**
	 * 所购政策id（供应商政策快照id）
	 */
	private String policyId;

	/**
	 * 订购数量
	 */
	private Integer number;

	/**
	 * 下单人信息
	 */
	public MPOrderUserInfoDTO orderUserInfo;

	/**
	 * 取票人信息
	 */
	public MPOrderUserInfoDTO takeTicketUserInfo;

	/**
	 * 旅游日期
	 */
	private Date travelDate;

	/**
	 * 预订人IP (可选)
	 */
	private String bookManIP;

	/**
	 * 陪玩游客
	 */
	public List<MPOrderUserInfoDTO> traveler;
	
	/**
	 * 渠道用户id
	 */
	private String channelUserId;

	public String getDealerOrderId() {
		return dealerOrderId;
	}

	public void setDealerOrderId(String dealerOrderId) {
		this.dealerOrderId = dealerOrderId;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getPolicyId() {
		return policyId;
	}

	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public MPOrderUserInfoDTO getOrderUserInfo() {
		return orderUserInfo;
	}

	public void setOrderUserInfo(MPOrderUserInfoDTO orderUserInfo) {
		this.orderUserInfo = orderUserInfo;
	}

	public MPOrderUserInfoDTO getTakeTicketUserInfo() {
		return takeTicketUserInfo;
	}

	public void setTakeTicketUserInfo(MPOrderUserInfoDTO takeTicketUserInfo) {
		this.takeTicketUserInfo = takeTicketUserInfo;
	}

	public Date getTravelDate() {
		return travelDate;
	}

	public void setTravelDate(Date travelDate) {
		this.travelDate = travelDate;
	}

	public String getBookManIP() {
		return bookManIP;
	}

	public void setBookManIP(String bookManIP) {
		this.bookManIP = bookManIP;
	}

	public List<MPOrderUserInfoDTO> getTraveler() {
		return traveler;
	}

	public void setTraveler(List<MPOrderUserInfoDTO> traveler) {
		this.traveler = traveler;
	}

	public String getChannelUserId() {
		return channelUserId;
	}

	public void setChannelUserId(String channelUserId) {
		this.channelUserId = channelUserId;
	}

}
