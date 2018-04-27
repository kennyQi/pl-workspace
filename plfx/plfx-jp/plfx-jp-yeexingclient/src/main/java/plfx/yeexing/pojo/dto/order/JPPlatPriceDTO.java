package plfx.yeexing.pojo.dto.order;

import plfx.jp.pojo.dto.BaseJpDTO;

@SuppressWarnings("serial")
public class JPPlatPriceDTO extends BaseJpDTO{
	/**
	 * 订单总支付价格
	 */
	private Double totalPrice;

	/**
	 * 平台加价政策金额(针对单张票)
	 */
	private Double platPolicyPirce;
	
	/**
	 * 平台计算政策价格后需要支付的总价，totalPrice基础上算的价格 
	 * 票面价-价格政策的数字+支付宝手续费+基建燃油，最后十位取整
	 */
	private Double platTotalPrice;
	
	/**
	 * 手续费
	 * 手续费=（票面价+税款）*0.008（支付宝千分之8手续费）
	 */
	private Double serviceCharge;

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Double getPlatPolicyPirce() {
		return platPolicyPirce;
	}

	public void setPlatPolicyPirce(Double platPolicyPirce) {
		this.platPolicyPirce = platPolicyPirce;
	}

	public Double getPlatTotalPrice() {
		return platTotalPrice;
	}

	public void setPlatTotalPrice(Double platTotalPrice) {
		this.platTotalPrice = platTotalPrice;
	}

	public Double getServiceCharge() {
		return serviceCharge;
	}

	public void setServiceCharge(Double serviceCharge) {
		this.serviceCharge = serviceCharge;
	}
}
