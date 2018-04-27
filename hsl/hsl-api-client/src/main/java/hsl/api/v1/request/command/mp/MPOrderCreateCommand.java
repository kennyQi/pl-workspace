package hsl.api.v1.request.command.mp;

import hsl.api.base.ApiPayload;
import hsl.pojo.dto.user.UserDTO;

import java.util.Date;
import java.util.List;

/**
 * 门票下单
 * 
 * @author yuxx
 * 
 */
@SuppressWarnings("serial")
public class MPOrderCreateCommand extends ApiPayload {

	/**
	 * 实付价格
	 */
	private Double price;

	/**
	 * 所购政策id
	 */
	private String policyId;

	/**
	 * 订购数量
	 */
	private Integer number;

	/**
	 * 下单人信息
	 */
	public UserDTO orderUserInfo;

	/**
	 * 取票人信息
	 */
	public UserDTO takeTicketUserInfo;

	/**
	 * 旅游日期
	 */
	private String travelDate;

	/**
	 * 预订人IP (可选)
	 */
	private String bookManIP;

	/**
	 * 陪玩游客
	 */
	public List<UserDTO> traveler;

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

	public UserDTO getOrderUserInfo() {
		return orderUserInfo;
	}

	public void setOrderUserInfo(UserDTO orderUserInfo) {
		this.orderUserInfo = orderUserInfo;
	}

	public UserDTO getTakeTicketUserInfo() {
		return takeTicketUserInfo;
	}

	public void setTakeTicketUserInfo(UserDTO takeTicketUserInfo) {
		this.takeTicketUserInfo = takeTicketUserInfo;
	}

	public void setTraveler(List<UserDTO> traveler) {
		this.traveler = traveler;
	}

	public String getTravelDate() {
		return travelDate;
	}

	public void setTravelDate(String travelDate) {
		this.travelDate = travelDate;
	}

	public String getBookManIP() {
		return bookManIP;
	}

	public void setBookManIP(String bookManIP) {
		this.bookManIP = bookManIP;
	}

	public List<UserDTO> getTraveler() {
		return traveler;
	}

}
