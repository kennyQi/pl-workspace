package hsl.pojo.dto.line;

import hsl.pojo.dto.BaseDTO;

import java.util.List;


@SuppressWarnings("serial")
public class DayRouteDTO extends BaseDTO{
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

	/**
	 * 酒店信息
	 * 表中保存酒店信息JSON
	 */
	private List<HotelInfoDTO> hotelList;
	
	/**
	 * 酒店信息JSON
	 */
	private String hotelInfoJson;
	
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
	
	/**
	 * 线路行程图片列表
	 */
	private List<LineImageDTO> lineImageList;


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


	public List<HotelInfoDTO> getHotelList() {
		return hotelList;
	}


	public void setHotelList(List<HotelInfoDTO> hotelList) {
		this.hotelList = hotelList;
	}


	public String getHotelInfoJson() {
		return hotelInfoJson;
	}


	public void setHotelInfoJson(String hotelInfoJson) {
		this.hotelInfoJson = hotelInfoJson;
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


	public List<LineImageDTO> getLineImageList() {
		return lineImageList;
	}


	public void setLineImageList(List<LineImageDTO> lineImageList) {
		this.lineImageList = lineImageList;
	}
	
	
}
