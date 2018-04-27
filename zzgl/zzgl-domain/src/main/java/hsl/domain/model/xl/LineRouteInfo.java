package hsl.domain.model.xl;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;


/**
 * @类功能说明：线路行程
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuxx
 * @创建时间：2014年11月26日上午11:21:40
 */
@SuppressWarnings("serial")
@Embeddable
public class LineRouteInfo implements Serializable{
	
	/**
	 * 行程名称
	 */
	@Column(name = "ROUTE_NAME")
	private String routeName;
	
	/**
	 * 行程天数
	 */
	@Column(name = "ROUTE_DAYS")
	private Integer routeDays;
	
	/**
	 * 购物时长（小时）
	 */
	@Column(name = "SHOPPING_TIME_HOUR")
	private Integer shoppingTimeHour;
	
	/**
	 * 每日行程列表
	 */
	@OneToMany(mappedBy="line",cascade={CascadeType.ALL},fetch=FetchType.LAZY)
	@OrderBy("days")
	private List<DayRoute> dayRouteList;

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

	public List<DayRoute> getDayRouteList() {
		return dayRouteList;
	}

	public void setDayRouteList(List<DayRoute> dayRouteList) {
		this.dayRouteList = dayRouteList;
	}
	
	
}