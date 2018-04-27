package lxs.pojo.qo.mp;

import javax.persistence.Column;

import hg.common.component.BaseQo;

@SuppressWarnings("serial")
public class TicketOrderQO extends BaseQo {
	/**
	 * 当前用户id
	 */
	private String userID;

	/**
	 * 订单编号
	 */
	private String orderNO;

	private String serialNumber;
	
	/**
	 * 景区名称
	 */
	private String name;
	
	/**
	 * 预订人姓名
	 */
	private String bookMan;

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getOrderNO() {
		return orderNO;
	}

	public void setOrderNO(String orderNO) {
		this.orderNO = orderNO;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBookMan() {
		return bookMan;
	}

	public void setBookMan(String bookMan) {
		this.bookMan = bookMan;
	}

}
