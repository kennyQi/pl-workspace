package slfx.xl.pojo.dto.route;

import java.util.List;

import slfx.xl.pojo.dto.BaseXlDTO;
import slfx.xl.pojo.dto.line.LineDTO;
import slfx.xl.pojo.dto.line.LineImageDTO;

/**
 * 
 * @类功能说明：线路每日行程DTO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：luoyun
 * @创建时间：2014年12月15日下午1:46:49
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class DayRouteDTO extends BaseXlDTO{

	/**
	 * 所属线路
	 */
	private LineDTO line;

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
	 * 酒店星级，用都好隔开
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

	public LineDTO getLine() {
		return line;
	}

	public void setLine(LineDTO line) {
		this.line = line;
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

	public String getHotelInfoJson() {
		return hotelInfoJson;
	}

	public void setHotelInfoJson(String hotelInfoJson) {
		this.hotelInfoJson = hotelInfoJson;
	}

	public List<LineImageDTO> getLineImageList() {
		return lineImageList;
	}

	public void setLineImageList(List<LineImageDTO> lineImageList) {
		this.lineImageList = lineImageList;
	}
	
}
