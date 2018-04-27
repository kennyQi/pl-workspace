package hg.fx.command.mileOrder;

import hg.framework.common.base.BaseSPICommand;
import hg.fx.domain.MileOrder;
import hg.fx.domain.Product;

import java.util.Date;

/**
 * 文件创建订单
 * @date 2016-6-16上午10:46:23
 * @since
 */
@SuppressWarnings("serial")
public class CreateMileOrderCommand extends BaseSPICommand{
	private String distributorId;
	private String importUser;
	private Product product;
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
	/**
	 * 支付时间
	 */
	private Date payDate;

	 

	//订单提示
	private String errorTip;
	
	//保存后的订单对象
	private MileOrder savedOrder;
	
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

	public Date getPayDate() {
		return payDate;
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

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
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

 
	/**
	 * 
	 * @return 错误信息 or null
	 */
	public String getErrorTip() {
		return errorTip;
	}

	
	public void setErrorTip(String orderTip) {
		this.errorTip = orderTip;
	}

	public void setSavedOrder(MileOrder mileOrder) {
		this.savedOrder = mileOrder;
		
	}

	public MileOrder getSavedOrder() {
		return savedOrder;
	}

	public String getImportUser() {
		return importUser;
	}

	public void setImportUser(String importUser) {
		this.importUser = importUser;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}


	
}
