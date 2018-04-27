package slfx.jp.pojo.dto.flight;

import slfx.jp.pojo.dto.BaseJpDTO;

/**
 * 
 * @类功能说明：航班经停信息DTO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年7月31日下午4:54:16
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class FlightStopInfoDTO extends BaseJpDTO{
	
   /** 机场代码 */
   private String airportCode;
   
   /** 抵达时间   格式：0925 */
   private String arriveTime;
   
   /** 起飞时间  格式：0925 */
   private String departureTime;
   
   /** 机型代码 */
   private String aircraftCode;

	public String getAirportCode() {
		return airportCode;
	}
	
	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}
	
	public String getArriveTime() {
		return arriveTime;
	}
	
	public void setArriveTime(String arriveTime) {
		this.arriveTime = arriveTime;
	}
	
	public String getDepartureTime() {
		return departureTime;
	}
	
	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}

	public String getAircraftCode() {
		return aircraftCode;
	}

	public void setAircraftCode(String aircraftCode) {
		this.aircraftCode = aircraftCode;
	}
	
}