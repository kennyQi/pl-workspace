package slfx.xl.pojo.command.route;

import hg.common.component.BaseCommand;


/**
 * 
 * @类功能说明：修改线路行程信息
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：luoyun
 * @创建时间：2014年12月16日下午2:20:32
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class ModifyLineRouteInfoCommand extends BaseCommand{
	
	/**
	 * 行程ID
	 */
	private String lineID;

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

	public String getLineID() {
		return lineID;
	}

	public void setLineID(String lineID) {
		this.lineID = lineID;
	}
	
	
	
}
