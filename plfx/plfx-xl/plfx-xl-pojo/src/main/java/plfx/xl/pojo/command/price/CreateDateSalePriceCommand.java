package plfx.xl.pojo.command.price;

import hg.common.component.BaseCommand;

/**
 * 
 * @类功能说明：添加单个团期
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：luoyun
 * @创建时间：2014年12月19日下午4:46:04
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class CreateDateSalePriceCommand extends BaseCommand{

	/**
	 * 所属线路
	 */
	private String lineID;

	/**
	 * 成人价
	 */
	private Double adultPrice;

	/**
	 * 儿童价
	 */
	private Double childPrice;

	/**
	 * 累加人数
	 */
	private Integer number;
	
	/**
	 * 日期
	 */
	private String saleDate;

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

	public String getSaleDate() {
		return saleDate;
	}

	public void setSaleDate(String saleDate) {
		this.saleDate = saleDate;
	}

	public String getLineID() {
		return lineID;
	}

	public void setLineID(String lineID) {
		this.lineID = lineID;
	}
	
	

}
