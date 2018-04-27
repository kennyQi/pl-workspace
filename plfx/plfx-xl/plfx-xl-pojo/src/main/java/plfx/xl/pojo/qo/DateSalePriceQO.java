package plfx.xl.pojo.qo;

import hg.common.component.BaseQo;

/**
 * 
 * @类功能说明：价格日历QO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：luoyun
 * @创建时间：2014年12月17日下午4:25:04
 * @版本：V1.0
 *
 */@SuppressWarnings("serial")
public class DateSalePriceQO extends BaseQo{

	/**
	 * ID
	 */
	private String dateSalePriceID;
	
	/**
	 * 线路ID
	 */
	private String lineID;
	
	/**
	 * 日期
	 */
	private String saleDate;
	
	/**
	 * 按日期排序
	 */
	private Boolean saleDateAsc;
	
	/**
	 * 星期列表
	 */
	private String weekDay;
	
	/**
	 * 开始时间
	 */
	private String beginDate;
	
	/**
	 * 结束时间
	 */
	private String endDate;
	

	public String getLineID() {
		return lineID;
	}

	public void setLineID(String lineID) {
		this.lineID = lineID;
	}

	public Boolean getSaleDateAsc() {
		return saleDateAsc;
	}

	public void setSaleDateAsc(Boolean saleDateAsc) {
		this.saleDateAsc = saleDateAsc;
	}

	public String getSaleDate() {
		return saleDate;
	}

	public void setSaleDate(String saleDate) {
		this.saleDate = saleDate;
	}

	public String getDateSalePriceID() {
		return dateSalePriceID;
	}

	public void setDateSalePriceID(String dateSalePriceID) {
		this.dateSalePriceID = dateSalePriceID;
	}

	public String getWeekDay() {
		return weekDay;
	}

	public void setWeekDay(String weekDay) {
		this.weekDay = weekDay;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	
	
	
	
	
}

