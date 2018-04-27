package plfx.gjjp.domain.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import plfx.jp.domain.model.J;

/**
 * @类功能说明：乘机人机票总的价格明细
 * @类修改者：
 * @修改日期：2015-6-29下午5:03:57
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-6-29下午5:03:57
 */
@Embeddable
@SuppressWarnings("serial")
public class GJPassengerTicketPriceDetail implements Serializable {

	/**
	 * 票面价
	 */
	@Column(name = "PAR_VALUE", columnDefinition = J.MONEY_COLUM)
	private Double parValue;

	// ------------------ 供应商价格 ------------------

	/**
	 * 供应商基础返点
	 */
	@Column(name = "SUPPLIER_BASIC_DISC", columnDefinition = J.DOUBLE_COLUM)
	private Double supplierBasicDisc;

	/**
	 * 供应商奖励返点
	 */
	@Column(name = "SUPPLIER_GDISC", columnDefinition = J.DOUBLE_COLUM)
	private Double supplierGDisc;

	/**
	 * 供应商单张税费
	 */
	@Column(name = "SUPPLIER_TAX", columnDefinition = J.MONEY_COLUM)
	private Double supplierTax;

	/**
	 * 供应商开票费
	 */
	@Column(name = "SUPPLIER_OUT_TICK_MONEY", columnDefinition = J.MONEY_COLUM)
	private Double supplierOutTickMoney;

	/**
	 * 与供应商单张结算价
	 * 
	 * 与供应商单张结算价=公布运价*(1-基础返点)*(1-奖励返点)+开票费+税费
	 */
	@Column(name = "SUPPLIER_TOTAL_PRICE", columnDefinition = J.MONEY_COLUM)
	private Double supplierTotalPrice;

	// ------------------ 平台价格 ------------------

	/**
	 * 平台政策调价幅度
	 * 
	 * 平台政策调价幅度=(票面价*调价百分比)｜价格政策
	 */
	@Column(name = "PLATFORM_POLICY", columnDefinition = J.MONEY_COLUM)
	private Double platformPolicy;

	/**
	 * 平台支付总价
	 * 
	 * 平台价格=平台给经销商的票面价+开票费+税费
	 */
	@Column(name = "PLATFORM_TOTAL_PRICE", columnDefinition = J.MONEY_COLUM)
	private Double platformTotalPrice;

	/**
	 * 平台所得佣金 =平台支付总价-与供应商结算价
	 */
	@Column(name = "COMM_AMOUNT", columnDefinition = J.MONEY_COLUM)
	private Double commAmount;

	public Double getParValue() {
		return parValue;
	}

	public void setParValue(Double parValue) {
		this.parValue = parValue;
	}

	public Double getSupplierBasicDisc() {
		return supplierBasicDisc;
	}

	public void setSupplierBasicDisc(Double supplierBasicDisc) {
		this.supplierBasicDisc = supplierBasicDisc;
	}

	public Double getSupplierGDisc() {
		return supplierGDisc;
	}

	public void setSupplierGDisc(Double supplierGDisc) {
		this.supplierGDisc = supplierGDisc;
	}

	public Double getSupplierTax() {
		return supplierTax;
	}

	public void setSupplierTax(Double supplierTax) {
		this.supplierTax = supplierTax;
	}

	public Double getSupplierOutTickMoney() {
		return supplierOutTickMoney;
	}

	public void setSupplierOutTickMoney(Double supplierOutTickMoney) {
		this.supplierOutTickMoney = supplierOutTickMoney;
	}

	public Double getSupplierTotalPrice() {
		return supplierTotalPrice;
	}

	public void setSupplierTotalPrice(Double supplierTotalPrice) {
		this.supplierTotalPrice = supplierTotalPrice;
	}

	public Double getPlatformPolicy() {
		return platformPolicy;
	}

	public void setPlatformPolicy(Double platformPolicy) {
		this.platformPolicy = platformPolicy;
	}

	public Double getPlatformTotalPrice() {
		return platformTotalPrice;
	}

	public void setPlatformTotalPrice(Double platformTotalPrice) {
		this.platformTotalPrice = platformTotalPrice;
	}

	public Double getCommAmount() {
		return commAmount;
	}

	public void setCommAmount(Double commAmount) {
		this.commAmount = commAmount;
	}

}
