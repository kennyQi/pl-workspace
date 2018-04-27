package hg.fx.command.mileOrder;

import hg.framework.common.base.BaseSPICommand;

/**
 * 接口创建订单
 * @date 2016-6-16上午10:45:26
 * @since
 */
@SuppressWarnings("serial")
public class ApiCreateMileOrderCommand extends BaseSPICommand{
	/**
	 * 商户id
	 */
	private String distributorId;
	/**
	 * 商品编码
	 */
	private String productCode;
	/**
	 * 商户订单号
	 */
	private String orderCode;

	/**
	 * 南航卡号
	 */
	private String csairCard;

	/**
	 * 南航姓名
	 */
	private String csairName;

	/**
	 * 数量
	 */
	private Integer num;

	/**
	 * 单价UNIT_PRICE
	 */
	private Integer unitPrice = 1;

	public String getOrderCode() {
		return orderCode;
	}

	public String getCsairCard() {
		return csairCard;
	}

	public String getCsairName() {
		return csairName;
	}

	public Integer getNum() {
		return num;
	}

 

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public void setCsairCard(String csairCard) {
		this.csairCard = csairCard;
	}

	public void setCsairName(String csairName) {
		this.csairName = csairName;
	}

	public void setNum(Integer num) {
		this.num = num;
	}
 

	public String getDistributorId() {
		return distributorId;
	}

	public void setDistributorId(String distributorId) {
		this.distributorId = distributorId;
	}

	public Integer getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Integer unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
 

	
}
