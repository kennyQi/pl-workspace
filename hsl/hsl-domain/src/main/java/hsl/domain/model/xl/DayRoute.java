package hsl.domain.model.xl;
import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;
import hsl.domain.model.M;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import slfx.xl.pojo.command.route.CreateDayRouteCommand;
import slfx.xl.pojo.command.route.ModifyDayRouteCommand;
import slfx.xl.pojo.command.route.dto.HotelRequestDTO;

import com.alibaba.fastjson.JSON;
/**
 * @类功能说明：一日行程
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuxx
 * @创建时间：2014年11月26日下午3:07:21
 */
@SuppressWarnings("serial")
@Entity
@Table(name = M.TABLE_PREFIX_HSL_XL + "DAY_ROUTE")
public class DayRoute extends BaseModel {
	
	/**
	 * 所属线路
	 */
	@ManyToOne
	@JoinColumn(name = "LINE_ID")
	private Line line;

	/**
	 * 第几天
	 */
	@Column(name = "DAYS")
	private Integer days;

	/**
	 * 出发地城市名称
	 */
	@Column(name = "STARTING_CITY")
	private String starting;

	/**
	 * 目的地
	 */
	@Column(name = "DESTINATION")
	private String destination;

	/**
	 * 交通工具
	 */
	@Column(name = "VEHICLE")
	private String vehicle;

	public final static Integer VEHICLE_PLAIN = 1; // 飞机
	public final static Integer VEHICLE_BUS = 2; // 大巴
	public final static Integer VEHICLE_SHIP = 3; // 轮船
	public final static Integer VEHICLE_TRAIN = 4; // 火车
	public final static Integer SELF_GOING = 5; // 自行前往

	/**
	 * 酒店信息
	 * 表中保存酒店信息JSON
	 */
	@Transient
	private List<HotelInfo> hotelList;
	
	/**
	 * 酒店信息JSON
	 */
	@Column(name = "HOTEL_INFO_JSON")
	private String hotelInfoJson;
	
	/**
	 * 是否包含早餐
	 */
	@Column(name = "INCLUDE_BREAKFAST")
	private Boolean includeBreakfast;
	
	/**
	 * 是否包含午餐
	 */
	@Column(name = "INCLUDE_LUNCH")
	private Boolean includeLunch;
	
	/**
	 * 是否包含晚餐
	 */
	@Column(name = "INCLUDE_DINNER")
	private Boolean includeDinner;
	
	/**
	 * 行程安排
	 */
	@Column(name = "SCHEDULING_DESCRIPTION", columnDefinition=M.TEXT_COLUM)
	private String schedulingDescription;

	
	/**
	 * 酒店星级
	 */
	@Column(name = "STAY_LEVEL")
	private String stayLevel;
	
	/**
	 * 行程图片列表
	 */
	@OneToMany(fetch = FetchType.LAZY,mappedBy = "dayRoute",cascade={CascadeType.ALL})
	private List<LineImage> lineImageList;
	/**
	 * @方法功能说明：添加每日行程
	 * @修改者名字：luoyun
	 * @修改时间：2014年12月15日下午3:03:56
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	public void createDayRoute(CreateDayRouteCommand command){
		
		//所属线路
		Line line = new Line();
		line.setId(command.getLineID());
		setLine(line);
		
		setDays(command.getDays());
		setDestination(command.getDestination());
		setId(UUIDGenerator.getUUID());
		setIncludeBreakfast(command.getIncludeBreakfast());
		setIncludeDinner(command.getIncludeDinner());
		setIncludeLunch(command.getIncludeLunch());
		setSchedulingDescription(command.getSchedulingDescription());
		setStarting(command.getStarting());
		setVehicle(command.getVehicle());

		//酒店信息
		List<HotelRequestDTO> hotelDTOList = command.getHotelList();
		String stayLevel = "";
		if(hotelDTOList != null && hotelDTOList.size() != 0){
			List<HotelInfo> hotelInfoList = new ArrayList<HotelInfo>();
			for(HotelRequestDTO hotelDTO:hotelDTOList){
				HotelInfo hotelInfo = new HotelInfo();
				hotelInfo.setHotelName(hotelDTO.getHotelName());
				hotelInfo.setStayLevel(hotelDTO.getStayLevel());
				hotelInfoList.add(hotelInfo);
				stayLevel += hotelDTO.getStayLevel() + ",";
			}
			//将酒店列表信息转换为JSON格式字符串保存到数据库中
			String hotelInfoJson  = JSON.toJSONString(hotelInfoList);
			setHotelInfoJson(hotelInfoJson);
		}
		setStayLevel(stayLevel);
	}
		
	/**
	 * 
	 * @方法功能说明：修改每日行程信息
	 * @修改者名字：luoyun
	 * @修改时间：2014年12月16日下午3:09:43
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	public void modifyDayRoute(ModifyDayRouteCommand command){
		
		setDays(command.getDays());
		setDestination(command.getDestination());
		setIncludeBreakfast(command.getIncludeBreakfast());
		setIncludeDinner(command.getIncludeDinner());
		setIncludeLunch(command.getIncludeLunch());
		setSchedulingDescription(command.getSchedulingDescription());
		setStarting(command.getStarting());
		setVehicle(command.getVehicle());

		//酒店信息
		List<HotelRequestDTO> hotelDTOList = command.getHotelList();
		if(hotelDTOList != null && hotelDTOList.size() != 0){
			List<HotelInfo> hotelInfoList = new ArrayList<HotelInfo>();
			for(HotelRequestDTO hotelDTO:hotelDTOList){
				HotelInfo hotelInfo = new HotelInfo();
				hotelInfo.setHotelName(hotelDTO.getHotelName());
				hotelInfo.setStayLevel(hotelDTO.getStayLevel());
				hotelInfoList.add(hotelInfo);
			}
			//将酒店列表信息转换为JSON格式字符串保存到数据库中
			String hotelInfoJson  = JSON.toJSONString(hotelInfoList);
			setHotelInfoJson(hotelInfoJson);
		}
		
	}
	
	/**
	 * 
	 * @方法功能说明：复制一日行程信息
	 * @修改者名字：luoyun
	 * @修改时间：2014年12月25日下午2:18:48
	 * @修改内容：
	 * @参数：@param dayRoute
	 * @return:void
	 * @throws
	 */
	public void copy(DayRoute dayRoute,Line line){
		//拷贝每日行程信息时，去掉线路的图片文件夹信息
		
		setLine(line);
		setDays(dayRoute.getDays());
		setStarting(dayRoute.getStarting());
		setDestination(dayRoute.getDestination());
		setVehicle(dayRoute.getVehicle());
		setHotelInfoJson(dayRoute.getHotelInfoJson());
		setIncludeBreakfast(dayRoute.getIncludeBreakfast());
		setIncludeLunch(dayRoute.getIncludeLunch());
		setIncludeDinner(dayRoute.getIncludeDinner());
		setSchedulingDescription(dayRoute.getSchedulingDescription());
		setId(UUIDGenerator.getUUID());
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

	public Line getLine() {
		return line;
	}

	public void setLine(Line line) {
		this.line = line;
	}

	public List<HotelInfo> getHotelList() {
		return hotelList;
	}

	public void setHotelList(List<HotelInfo> hotelList) {
		this.hotelList = hotelList;
	}

	public String getHotelInfoJson() {
		return hotelInfoJson;
	}

	public void setHotelInfoJson(String hotelInfoJson) {
		this.hotelInfoJson = hotelInfoJson;
	}

	public String getStayLevel() {
		return stayLevel;
	}

	public void setStayLevel(String stayLevel) {
		this.stayLevel = stayLevel;
	}

	public List<LineImage> getLineImageList() {
		return lineImageList;
	}

	public void setLineImageList(List<LineImage> lineImageList) {
		this.lineImageList = lineImageList;
	}
	
}