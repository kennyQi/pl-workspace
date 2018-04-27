package hsl.pojo.dto.mp;

import hsl.pojo.dto.BaseDTO;
import hsl.pojo.dto.user.UserDTO;

import java.util.Date;
import java.util.List;

/**
 * 景区门票平台订单
 * 
 * @author Administrator
 */
@SuppressWarnings("serial")
public class MPOrderDTO extends BaseDTO {


	/**
	 * 经销商订单号
	 */
	private String dealerOrderCode;
	
	/**
	 * 平台订单号
	 */
	private String platformOrderCode;
	
	/**
	 * 下单时间
	 */
	private Date createDate;
	
	/**
	 * 游玩时间
	 */
	private String travelDate;
	/**
	 * 实付价
	 */
	private Double price;

	/**
	 * 订单所购政策
	 */
	private PolicyDTO mpPolicy;
	
	/**
	 * 订单中景区信息
	 */
	private ScenicSpotDTO scenicSpot;
	
	/**
	 * 订购数量
	 */
	private Integer number;

	/**
	 * 下单人信息
	 */
	public UserDTO orderUser;

	/**
	 * 取票人信息
	 */
	public UserDTO takeTicketUser;

	/**
	 * 陪同游玩人信息
	 */
	public List<UserDTO> travelers;

	/**
	 * 门票订单状态
	 */
	public MPOrderStatusDTO status;

	/**
	 * 取消原因
	 */
	public String cancelRemark;


	public String getDealerOrderCode() {
		return dealerOrderCode;
	}

	public void setDealerOrderCode(String dealerOrderCode) {
		this.dealerOrderCode = dealerOrderCode;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getTravelDate() {
		return travelDate;
	}

	public void setTravelDate(String travelDate) {
		this.travelDate = travelDate;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public MPOrderStatusDTO getStatus() {
		return status;
	}

	public void setStatus(MPOrderStatusDTO status) {
		this.status = status;
	}

	public String getCancelRemark() {
		return cancelRemark;
	}

	public void setCancelRemark(String cancelRemark) {
		this.cancelRemark = cancelRemark;
	}

	public PolicyDTO getMpPolicy() {
		return mpPolicy;
	}

	public void setMpPolicy(PolicyDTO mpPolicy) {
		this.mpPolicy = mpPolicy;
	}

	public UserDTO getOrderUser() {
		return orderUser;
	}

	public void setOrderUser(UserDTO orderUser) {
		this.orderUser = orderUser;
	}

	public UserDTO getTakeTicketUser() {
		return takeTicketUser;
	}

	public void setTakeTicketUser(UserDTO takeTicketUser) {
		this.takeTicketUser = takeTicketUser;
	}

	public List<UserDTO> getTravelers() {
		return travelers;
	}

	public void setTravelers(List<UserDTO> travelers) {
		this.travelers = travelers;
	}

	public ScenicSpotDTO getScenicSpot() {
		return scenicSpot;
	}

	public void setScenicSpot(ScenicSpotDTO scenicSpot) {
		this.scenicSpot = scenicSpot;
	}

	public String getPlatformOrderCode() {
		return platformOrderCode;
	}

	public void setPlatformOrderCode(String platformOrderCode) {
		this.platformOrderCode = platformOrderCode;
	}


}