package slfx.xl.pojo.command.route;

import java.util.List;

import slfx.xl.pojo.command.route.dto.HotelRequestDTO;
import hg.common.component.BaseCommand;

/**
 * 
 * @类功能说明：修改一日行程信息
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：luoyun
 * @创建时间：2014年12月16日下午3:07:41
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class ModifyDayRouteCommand extends BaseCommand{

	/**
	 * 一日行程ID
	 */
	private String id;
	
	/**
	 * 所属线路
	 */
	private String lineID;
	
	/**
	 * 第几天
	 */
	private Integer days;

	/**
	 * 出发地
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
	 * 酒店信息列表
	 */
	private List<HotelRequestDTO> hotelList;
	
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLineID() {
		return lineID;
	}

	public void setLineID(String lineID) {
		this.lineID = lineID;
	}

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

	public List<HotelRequestDTO> getHotelList() {
		return hotelList;
	}

	public void setHotelList(List<HotelRequestDTO> hotelList) {
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
	
	
	
}
