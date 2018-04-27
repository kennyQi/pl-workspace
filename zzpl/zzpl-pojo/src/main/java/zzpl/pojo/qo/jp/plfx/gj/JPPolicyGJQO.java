package zzpl.pojo.qo.jp.plfx.gj;

import hg.common.component.BaseQo;

import java.util.List;

@SuppressWarnings("serial")
public class JPPolicyGJQO extends BaseQo{
	/**
	 * 航班舱位组合token
	 */
	private String flightCabinGroupToken;

	/**
	 * 使用人数(默认1，范围1-9)
	 */
	private Integer peopleNum = 1;

	/**
	 * 乘客类型
	 * 
	 * @see GJ#PASSENGER_TYPE_MAP
	 */
	private List<Integer> passengerType;

	public String getFlightCabinGroupToken() {
		return flightCabinGroupToken;
	}

	public void setFlightCabinGroupToken(String flightCabinGroupToken) {
		this.flightCabinGroupToken = flightCabinGroupToken;
	}

	public Integer getPeopleNum() {
		return peopleNum;
	}

	public void setPeopleNum(Integer peopleNum) {
		this.peopleNum = peopleNum;
	}

	public List<Integer> getPassengerType() {
		return passengerType;
	}

	public void setPassengerType(List<Integer> passengerType) {
		this.passengerType = passengerType;
	}

}