package plfx.api.client.api.v1.gj.request;

import java.util.List;

import plfx.api.client.common.BaseClientQO;
import plfx.api.client.common.PlfxApiConstants.GJ;
import plfx.api.client.common.api.PlfxApiAction;

/**
 * @类功能说明：国际航班组合政策查询对象
 * @类修改者：
 * @修改日期：2015-7-8下午5:03:22
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-7-8下午5:03:22
 */
@SuppressWarnings("serial")
@PlfxApiAction(PlfxApiAction.GJ_QueryFlightPolicy)
public class FlightPolicyGJQO extends BaseClientQO {

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
