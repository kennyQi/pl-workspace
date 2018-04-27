package plfx.mp.pojo.dto.order;

import java.util.Date;
import java.util.List;

import plfx.mp.pojo.dto.BaseMpDTO;

/**
 * 同程门票订单
 * 
 * @author Administrator
 */
public class TCOrderDTO extends BaseMpDTO {
	private static final long serialVersionUID = 1L;
	/**
	 * 所属平台订单
	 */
	private String platformOrderId;
	/**
	 * 预订人姓名
	 */
	private String bookName;
	/**
	 * 预订人手机
	 */
	private String bookMobile;
	/**
	 * 预订人地址
	 */
	private String bookAddress;
	/**
	 * 预订人邮编
	 */
	private String bookPostcode;
	/**
	 * 预订人邮箱
	 */
	private String bookEmail;
	/**
	 * 政策id
	 */
	private Integer policyId;
	/**
	 * 预订票数
	 */
	private Integer number;
	/**
	 * 旅游日期
	 */
	private Date travelDate;
	/**
	 * 预订人IP
	 */
	private String bookManIP;
	/**
	 * 身份证号
	 */
	private String idCardNo;
	/**
	 * 游客(存JSON)
	 */
	public List<TravelerDTO> traveler;

	public String getPlatformOrderId() {
		return platformOrderId;
	}

	public void setPlatformOrderId(String platformOrderId) {
		this.platformOrderId = platformOrderId;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getBookMobile() {
		return bookMobile;
	}

	public void setBookMobile(String bookMobile) {
		this.bookMobile = bookMobile;
	}

	public String getBookAddress() {
		return bookAddress;
	}

	public void setBookAddress(String bookAddress) {
		this.bookAddress = bookAddress;
	}

	public String getBookPostcode() {
		return bookPostcode;
	}

	public void setBookPostcode(String bookPostcode) {
		this.bookPostcode = bookPostcode;
	}

	public String getBookEmail() {
		return bookEmail;
	}

	public void setBookEmail(String bookEmail) {
		this.bookEmail = bookEmail;
	}

	public Integer getPolicyId() {
		return policyId;
	}

	public void setPolicyId(Integer policyId) {
		this.policyId = policyId;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
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

	public String getIdCardNo() {
		return idCardNo;
	}

	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}

	public List<TravelerDTO> getTraveler() {
		return traveler;
	}

	public void setTraveler(List<TravelerDTO> traveler) {
		this.traveler = traveler;
	}

}