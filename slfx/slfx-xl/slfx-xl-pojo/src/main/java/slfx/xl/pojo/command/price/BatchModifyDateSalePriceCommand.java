package slfx.xl.pojo.command.price;

import hg.common.component.BaseCommand;



/**
 * 
 * @类功能说明：批量修改价格
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：luoyun
 * @创建时间：2014年12月18日下午4:44:48
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class BatchModifyDateSalePriceCommand extends BaseCommand{

	/**
	 * 所属线路
	 */
	private String lineID;
	
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
	
	/**
	 * 成人价
	 */
	private Double adultPrice;

	/**
	 * 儿童价
	 */
	private Double childPrice;

	/**
	 * 累计人数
	 */
	private Integer number;

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

	public Double getAdultPrice() {
		return adultPrice;
	}

	public void setAdultPrice(Double adultPrice) {
		this.adultPrice = adultPrice;
	}

	public Double getChildPrice() {
		return childPrice;
	}

	public void setChildPrice(Double childPrice) {
		this.childPrice = childPrice;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getLineID() {
		return lineID;
	}

	public void setLineID(String lineID) {
		this.lineID = lineID;
	}
	
	
	
}
