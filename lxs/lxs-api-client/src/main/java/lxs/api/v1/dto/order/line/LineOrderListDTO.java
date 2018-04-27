package lxs.api.v1.dto.order.line;

import java.util.List;

import javax.persistence.Column;

import lxs.api.v1.dto.BaseDTO;
import lxs.api.v1.dto.user.ContactsDTO;

@SuppressWarnings("serial")
public class LineOrderListDTO extends BaseDTO{
	private String orderID;
	
	private String orderNO;
	
	private String createDate;
	
	private String lineName;
	
	private String lineID;
	
	private String lineNO;
	
	private String travelDate;
	
	private String adultNO;
	
	private String childNO;
	
	private String pictureUri;

	private List<ContactsDTO> contactsList;
	
	private String bargainMoney;

	private String salePrice;	
	
	private Integer orderStatus;
	
	private Integer payStatus;
	
	/**
	 * 需付全款提前天数
	 */
	private Integer payTotalDaysBeforeStart;
	
	/**
	 * 最晚付款时间出发日期前
	 */
	private Integer lastPayTotalDaysBeforeStart;
	
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
	
	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

	public String getLineID() {
		return lineID;
	}

	public void setLineID(String lineID) {
		this.lineID = lineID;
	}

	public String getLineNO() {
		return lineNO;
	}

	public void setLineNO(String lineNO) {
		this.lineNO = lineNO;
	}

	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	public String getOrderNO() {
		return orderNO;
	}

	public void setOrderNO(String orderNO) {
		this.orderNO = orderNO;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	public String getTravelDate() {
		return travelDate;
	}

	public void setTravelDate(String travelDate) {
		this.travelDate = travelDate;
	}

	public String getAdultNO() {
		return adultNO;
	}

	public void setAdultNO(String adultNO) {
		this.adultNO = adultNO;
	}

	public String getChildNO() {
		return childNO;
	}

	public void setChildNO(String childNO) {
		this.childNO = childNO;
	}


	public List<ContactsDTO> getContactsList() {
		return contactsList;
	}

	public void setContactsList(List<ContactsDTO> contactsList) {
		this.contactsList = contactsList;
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

	public String getPictureUri() {
		return pictureUri;
	}

	public void setPictureUri(String pictureUri) {
		this.pictureUri = pictureUri;
	}

	public Integer getPayTotalDaysBeforeStart() {
		return payTotalDaysBeforeStart;
	}

	public void setPayTotalDaysBeforeStart(Integer payTotalDaysBeforeStart) {
		this.payTotalDaysBeforeStart = payTotalDaysBeforeStart;
	}

	public Integer getLastPayTotalDaysBeforeStart() {
		return lastPayTotalDaysBeforeStart;
	}

	public void setLastPayTotalDaysBeforeStart(Integer lastPayTotalDaysBeforeStart) {
		this.lastPayTotalDaysBeforeStart = lastPayTotalDaysBeforeStart;
	}

}
