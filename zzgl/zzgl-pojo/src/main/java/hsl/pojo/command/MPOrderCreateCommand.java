package hsl.pojo.command;

import hg.common.component.BaseCommand;
import hsl.pojo.dto.user.UserDTO;

import java.util.List;

/**
 * 门票下单
 * 
 * @author zhuxy
 * 
 */
@SuppressWarnings("serial")
public class MPOrderCreateCommand extends BaseCommand{

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
	

	/**
	 * 组织id
	 */
	private String companyId;
	
	/**
	 * 组织名称
	 */
	private String companyName;
	
	/**
	 * 部门id
	 */
	private String departmentId;
	
	/**
	 * 部门名称
	 */
	private String departmentName;
	
	/**
	 * 来源
	 */
	private String source;

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

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

}
