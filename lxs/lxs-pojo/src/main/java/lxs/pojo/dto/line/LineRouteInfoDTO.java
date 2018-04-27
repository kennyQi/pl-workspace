package lxs.pojo.dto.line;

import java.util.List;

/**
 * 
 * @类功能说明：线路行程DTO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：luoyun
 * @创建时间：2014年12月16日上午11:27:22
 * @版本：V1.0
 *
 */
public class LineRouteInfoDTO{

	/**
	 * 行程名称
	 */
	private String routeName;
	
	/**
	 * 行程天数
	 */
	private Integer routeDays;
	
	/**
	 * 购物时长（小时）
	 */
	private Integer shoppingTimeHour;
	
	/**
	 * 每日行程列表
	 */
	private List<DayRouteDTO> dayRouteList;

	public String getRouteName() {
		return routeName;
	}

	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}

	public Integer getRouteDays() {
		return routeDays;
	}

	public void setRouteDays(Integer routeDays) {
		this.routeDays = routeDays;
	}

	public Integer getShoppingTimeHour() {
		return shoppingTimeHour;
	}

	public void setShoppingTimeHour(Integer shoppingTimeHour) {
		this.shoppingTimeHour = shoppingTimeHour;
	}

	public List<DayRouteDTO> getDayRouteList() {
		return dayRouteList;
	}

	public void setDayRouteList(List<DayRouteDTO> dayRouteList) {
		this.dayRouteList = dayRouteList;
	}

}
