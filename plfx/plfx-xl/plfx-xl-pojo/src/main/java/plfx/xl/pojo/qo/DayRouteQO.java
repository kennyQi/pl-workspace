package plfx.xl.pojo.qo;

import hg.common.component.BaseQo;

/**
 * 
 * @类功能说明：线路每日行程QO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：luoyun
 * @创建时间：2014年12月15日上午11:04:14
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class DayRouteQO extends BaseQo{

	/**
	 * 所属线路ID
	 */
	private String lineID;
	
	/**
	 * 线路属性是否延迟加载
	 */
	private Boolean isLineLazy = true;
	
	/**
	 * 是否按第几天排序
	 */
	private Boolean daysAsc = true;
	
	/**
	 * 第几天
	 */
	private Integer days;

	public String getLineID() {
		return lineID;
	}

	public void setLineID(String lineID) {
		this.lineID = lineID;
	}

	public Boolean getIsLineLazy() {
		return isLineLazy;
	}

	public void setIsLineLazy(Boolean isLineLazy) {
		this.isLineLazy = isLineLazy;
	}

	public Boolean getDaysAsc() {
		return daysAsc;
	}

	public void setDaysAsc(Boolean daysAsc) {
		this.daysAsc = daysAsc;
	}

	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

	
	
	
	
	
}
