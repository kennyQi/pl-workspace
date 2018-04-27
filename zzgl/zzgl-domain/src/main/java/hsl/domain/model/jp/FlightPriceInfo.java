package hsl.domain.model.jp;
import hsl.domain.model.M;
import javax.persistence.Column;
import javax.persistence.Embeddable;
@Embeddable
public class FlightPriceInfo {
	/**
	 * 参考机场建设费
	 */
	@Column(name = "BUILD_FEE", columnDefinition = M.DOUBLE_COLUM)
	private Double buildFee;

	/**
	 * 参考燃油费
	 */
	@Column(name = "OIL_FEE", columnDefinition = M.DOUBLE_COLUM)
	private Double oilFee;

	/**
	 * 票面价
	 */
	@Column(name = "IBE_PRICE", length = 512)
	private String ibePrice;
	/**
	 * 支付总价=现金+卡券+余额
	 */
	@Column(name="PAYAMOUNT",columnDefinition=M.DOUBLE_COLUM)
	private Double payAmount;
	/**
	 * (单个乘客)支付现金,没有卡券支付-->用户真实支付金额除以乘客数量，最后去整数。
	 */
	@Column(name="PAYCASH",columnDefinition=M.DOUBLE_COLUM)
	private Double payCash;
	/**
	 * 退款金额
	 */
	@Column(name="RETURNEDPRICE",columnDefinition=M.DOUBLE_COLUM)
	private Double ReturnedPrice;
	/**
	 * (单个乘客)支付余额,没有卡券支付-->用户真实支付余额除以乘客数量，最后取整数。
	 */
	@Column(name="PAYBALANCE",columnDefinition=M.DOUBLE_COLUM)
	private Double payBalance;
	/**
	 * 单人支付总价(包括机建燃油)
	 * singleTotalPrice + 价格政策　＋　手续费
	 */
	@Column(name="SINGLEPLATTOTALPRICE",columnDefinition=M.DOUBLE_COLUM)
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
