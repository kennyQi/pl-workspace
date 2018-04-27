package lxs.api.v1.dto.order.line;

import java.util.Date;
import java.util.List;

import lxs.api.v1.dto.BaseDTO;
import lxs.api.v1.dto.user.ContactsDTO;

@SuppressWarnings("serial")
public class LineOrderDTO extends BaseDTO{

	private String lineID;

	private String userID;

	private String linkName;

	private String linkMobile;

	private String linkEmail;

	private List<ContactsDTO> contactsList;

	private String payment;
	
	private String adultNO;

	private String adultUnitPrice;

	private String childNO;

	private String childUnitPrice;

	private String bargainMoney;

	private String salePrice;	

	private Date travelDate;
	
	/**
	 * 保险金额
	 */
	private Integer insurancePrice;
	
	/**
	 * 是否购买保险
	 * 1：购买
	 * 0：未购买
	 */
	private Integer isBuyInsurance;
	
	
	
	public Integer getInsurancePrice() {
		return insurancePrice;
	}

	public void setInsurancePrice(Integer insurancePrice) {
		this.insurancePrice = insurancePrice;
	}

	public Integer getIsBuyInsurance() {
		return isBuyInsurance;
	}

	public void setIsBuyInsurance(Integer isBuyInsurance) {
		this.isBuyInsurance = isBuyInsurance;
	}

	public String getAdultNO() {
		return adultNO;
	}

	public void setAdultNO(String adultNO) {
		this.adultNO = adultNO;
	}

	public String getAdultUnitPrice() {
		return adultUnitPrice;
	}

	public void setAdultUnitPrice(String adultUnitPrice) {
		this.adultUnitPrice = adultUnitPrice;
	}

	public String getChildNO() {
		return childNO;
	}

	public void setChildNO(String childNO) {
		this.childNO = childNO;
	}

	public String getChildUnitPrice() {
		return childUnitPrice;
	}

	public void setChildUnitPrice(String childUnitPrice) {
		this.childUnitPrice = childUnitPrice;
	}

	public String getBargainMoney() {
		return bargainMoney;
	}

	public void setBargainMoney(String bargainMoney) {
		this.bargainMoney = bargainMoney;
	}

	public String getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(String salePrice) {
		this.salePrice = salePrice;
	}

	public Date getTravelDate() {
		return travelDate;
	}

	public void setTravelDate(Date travelDate) {
		this.travelDate = travelDate;
	}

	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	public String getLineID() {
		return lineID;
	}

	public void setLineID(String lineID) {
		this.lineID = lineID;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getLinkName() {
		return linkName;
	}

	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}

	public String getLinkMobile() {
		return linkMobile;
	}

	public void setLinkMobile(String linkMobile) {
		this.linkMobile = linkMobile;
	}

	public String getLinkEmail() {
		return linkEmail;
	}

	public void setLinkEmail(String linkEmail) {
		this.linkEmail = linkEmail;
	}

	public List<ContactsDTO> getContactsList() {
		return contactsList;
	}

	public void setContactsList(List<ContactsDTO> contactsList) {
		this.contactsList = contactsList;
	}

}
