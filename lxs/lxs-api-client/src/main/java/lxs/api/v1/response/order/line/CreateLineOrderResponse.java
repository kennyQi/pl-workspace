package lxs.api.v1.response.order.line;

import java.util.List;

import lxs.api.base.ApiResponse;
import lxs.api.v1.dto.user.ContactsDTO;

public class CreateLineOrderResponse extends ApiResponse {

	private String orderNO;

	private String orderID;

	private List<ContactsDTO> contactsList;

	private String bargainMoney;

	private String salePrice;

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

	public List<ContactsDTO> getContactsList() {
		return contactsList;
	}

	public void setContactsList(List<ContactsDTO> contactsList) {
		this.contactsList = contactsList;
	}

	public String getOrderNO() {
		return orderNO;
	}

	public void setOrderNO(String orderNO) {
		this.orderNO = orderNO;
	}

	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

}
