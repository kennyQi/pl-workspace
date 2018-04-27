package slfx.xl.pojo.command.price;

import hg.common.component.BaseCommand;

/**
 * @类功能说明：修改单天价格
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：luoyun
 * @创建时间：2014年12月18日下午1:50:52
 * @版本：V1.0
 */
@SuppressWarnings("serial")
public class ModifyDateSalePriceCommand extends BaseCommand{

	/**
	 * ID
	 */
	private String id;
	
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
	
	
}
