package plfx.mp.tcclient.tc.dto.order;

import java.util.Date;
import java.util.List;

import plfx.mp.tcclient.tc.domain.order.Guest;

/**
 * 提交订单接口
 * @author zhangqy
 *
 */
public class SubmitSceneryOrderDto extends OrderDto{
	/**
	 * 景区ID
	 */
	private String sceneryId;
	/**
	 * 预订人
	 */
	private String bMan;
	/**
	 * 预订人手机
	 */
	private String bMobile;
	/**
	 * 预订人地址
	 */
	private String bAddress;
	/**
	 * 预订人邮编
	 */
	private String bPostCode;
	/***
	 * 预订人邮箱
	 */
	private String bEmail;
	/**
	 * 取票人姓名
	 */
	private String tName;
	/**
	 * 取票人手机
	 */
	private String tMobile;
	/**
	 * 政策ID
	 */
	private Integer policyId;
	/**
	 * 预订票数
	 */
	private Integer tickets;
	/**
	 * 旅游日期
	 */
	private Date travelDate;
	/**
	 * 预订人IP
	 */
	private String orderIP;
	/**
	 * 二代身份证
	 */
	private String idCard;
	/**
	 * 
	 */
	private List<Guest> otherGuest;
	
	public SubmitSceneryOrderDto() {
		super();
		this.setParam("slfx.mp.tcclient.tc.pojo.order.request.ParamSubmitSceneryOrder");
		this.setResult("slfx.mp.tcclient.tc.pojo.order.response.ResultSubmitSceneryOrder");
		this.setServiceName("SubmitSceneryOrder");
	}
	public String getSceneryId() {
		return sceneryId;
	}
	public void setSceneryId(String sceneryId) {
		this.sceneryId = sceneryId;
	}
	public String getbMan() {
		return bMan;
	}
	public void setbMan(String bMan) {
		this.bMan = bMan;
	}
	public String getbMobile() {
		return bMobile;
	}
	public void setbMobile(String bMobile) {
		this.bMobile = bMobile;
	}
	public String getbAddress() {
		return bAddress;
	}
	public void setbAddress(String bAddress) {
		this.bAddress = bAddress;
	}
	public String getbPostCode() {
		return bPostCode;
	}
	public void setbPostCode(String bPostCode) {
		this.bPostCode = bPostCode;
	}
	public String getbEmail() {
		return bEmail;
	}
	public void setbEmail(String bEmail) {
		this.bEmail = bEmail;
	}
	public String gettName() {
		return tName;
	}
	public void settName(String tName) {
		this.tName = tName;
	}
	public String gettMobile() {
		return tMobile;
	}
	public void settMobile(String tMobile) {
		this.tMobile = tMobile;
	}
	public Integer getPolicyId() {
		return policyId;
	}
	public void setPolicyId(Integer policyId) {
		this.policyId = policyId;
	}
	public Integer getTickets() {
		return tickets;
	}
	public void setTickets(Integer tickets) {
		this.tickets = tickets;
	}
	public Date getTravelDate() {
		return travelDate;
	}
	public void setTravelDate(Date travelDate) {
		this.travelDate = travelDate;
	}
	public String getOrderIP() {
		return orderIP;
	}
	public void setOrderIP(String orderIP) {
		this.orderIP = orderIP;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public List<Guest> getOtherGuest() {
		return otherGuest;
	}
	public void setOtherGuest(List<Guest> otherGuest) {
		this.otherGuest = otherGuest;
	}
	
	
	
}
