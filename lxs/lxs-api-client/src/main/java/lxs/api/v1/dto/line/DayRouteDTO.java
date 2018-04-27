package lxs.api.v1.dto.line;

import java.util.List;
import java.util.Map;

import lxs.api.v1.dto.BaseDTO;

@SuppressWarnings("serial")
public class DayRouteDTO extends BaseDTO {
	/**
	 * 第几天
	 */
	private Integer days;

	/**
	 * 出发地城市名称
	 */
	private String starting;

	/**
	 * 目的地
	 */
	private String destination;

	/**
	 * 交通工具
	 */
	private String vehicle;

	public final static Integer VEHICLE_PLAIN = 1; // 飞机
	public final static Integer VEHICLE_BUS = 2; // 大巴
	public final static Integer VEHICLE_SHIP = 3; // 轮船
	public final static Integer VEHICLE_TRAIN = 4; // 火车

	/**
	 * 酒店信息JSON
	 */
	private List<HotelDTO> hotel;
	

	public List<HotelDTO> getHotel() {
		return hotel;
	}

	public void setHotel(List<HotelDTO> hotel) {
		this.hotel = hotel;
	}

	/**
	 * 是否包含早餐
	 */
	private Boolean includeBreakfast;

	/**
	 * 是否包含午餐
	 */
	private Boolean includeLunch;

	/**
	 * 是否包含晚餐
	 */
	private Boolean includeDinner;

	/**
	 * 行程安排
	 */
	private String schedulingDescription;

	/**
	 * 酒店星级
	 */
	private String stayLevel;

	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

	public String getStarting() {
		return starting;
	}

	public void setStarting(String starting) {
		this.starting = starting;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getVehicle() {
		return vehicle;
	}

	public void setVehicle(String vehicle) {
		this.vehicle = vehicle;
	}

	
	public Boolean getIncludeBreakfast() {
		return includeBreakfast;
	}

	public void setIncludeBreakfast(Boolean includeBreakfast) {
		this.includeBreakfast = includeBreakfast;
	}

	public Boolean getIncludeLunch() {
		return includeLunch;
	}

	public void setIncludeLunch(Boolean includeLunch) {
		this.includeLunch = includeLunch;
	}

	public Boolean getIncludeDinner() {
		return includeDinner;
	}

	public void setIncludeDinner(Boolean includeDinner) {
		this.includeDinner = includeDinner;
	}

	public String getSchedulingDescription() {
		return schedulingDescription;
	}

	public void setSchedulingDescription(String schedulingDescription) {
		this.schedulingDescription = schedulingDescription;
	}

	public String getStayLevel() {
		return stayLevel;
	}

	public void setStayLevel(String stayLevel) {
		this.stayLevel = stayLevel;
	}

}
