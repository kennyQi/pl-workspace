package slfx.jp.qo.admin;

import hg.common.component.BaseQo;
import slfx.jp.pojo.dto.flight.FlightPassengerDTO;

public class PassengerQO extends BaseQo{

	public PassengerQO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PassengerQO(FlightPassengerDTO dto) {
		this.cardNo=dto.getCardNo();
	}

	private static final long serialVersionUID = 7959513913746382673L;
	
	/**
	 * 旅客姓名
	 */
	private String name;
	
	/**
	 * 移动电话
	 */
	private String mobilePhone;
	
	/**
	 * 证件号
	 */
	private String cardNo;
	
	/**
	 * 机票订单ID
	 */
	private String jpOrderId;
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getJpOrderId() {
		return jpOrderId;
	}

	public void setJpOrderId(String jpOrderId) {
		this.jpOrderId = jpOrderId;
	}
	
}
