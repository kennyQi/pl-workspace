package hsl.pojo.dto.jp;



public class FlightPriceInfoDTO {
	/**
	 * 参考机场建设费
	 */
	private Double buildFee;

	/**
	 * 参考燃油费
	 */
	private Double oilFee;

	/**
	 * 票面价
	 */
	private String ibePrice;
	/**
	 * 支付总价
	 */
	private Double payAmount;
	/**
	 * 支付现金,没有卡券支付
	 */
	private Double payCash;
	
	/**
	 * 退款金额
	 */
	private Double ReturnedPrice;
	/**
	 * (单个乘客)支付余额,没有卡券支付-->用户真实支付余额除以乘客数量，最后去整数。
	 */
	private Double payBalance;
	/**
	 * 单人支付总价(包括机建燃油)
	 * singleTotalPrice + 价格政策　＋　手续费
	 */
	private Double singlePlatTotalPrice;
	public Double getBuildFee() {
		return buildFee;
	}

	public void setBuildFee(Double buildFee) {
		this.buildFee = buildFee;
	}

	public Double getOilFee() {
		return oilFee;
	}

	public void setOilFee(Double oilFee) {
		this.oilFee = oilFee;
	}

	public String getIbePrice() {
		return ibePrice;
	}

	public void setIbePrice(String ibePrice) {
		this.ibePrice = ibePrice;
	}

	public Double getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(Double payAmount) {
		this.payAmount = payAmount;
	}
	public Double getReturnedPrice() {
		return ReturnedPrice;
	}

	public void setReturnedPrice(Double returnedPrice) {
		ReturnedPrice = returnedPrice;
	}

	public Double getPayCash() {
		return payCash;
	}

	public void setPayCash(Double payCash) {
		this.payCash = payCash;
	}

	public Double getPayBalance() {
		return payBalance;
	}

	public void setPayBalance(Double payBalance) {
		this.payBalance = payBalance;
	}

	public Double getSinglePlatTotalPrice() {
		return singlePlatTotalPrice;
	}

	public void setSinglePlatTotalPrice(Double singlePlatTotalPrice) {
		this.singlePlatTotalPrice = singlePlatTotalPrice;
	}
	
}
