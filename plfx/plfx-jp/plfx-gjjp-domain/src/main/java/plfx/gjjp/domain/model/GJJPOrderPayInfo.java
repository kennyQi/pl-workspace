package plfx.gjjp.domain.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import plfx.gjjp.domain.common.GJJPConstants;
import plfx.jp.domain.model.J;

/**
 * @类功能说明：平台订单支付信息
 * @类修改者：
 * @修改日期：2015-7-7下午4:30:01
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-7-7下午4:30:01
 */
@Embeddable
@SuppressWarnings("serial")
public class GJJPOrderPayInfo implements Serializable {

	// ------------- 供应商支付信息 -------------

	/**
	 * 供应商订单总支付价格
	 */
	@Column(name = "SUPPLIER_TOTAL_PRICE", columnDefinition = J.MONEY_COLUM)
	private Double supplierTotalPrice;

	/**
	 * 供应商实际返点
	 */
	@Column(name = "SUPPLIER_DISC", columnDefinition = J.DOUBLE_COLUM)
	private Double supplierDisc;

	/**
	 * 供应商支付方式
	 * 
	 * 1- 汇付2.-支付宝
	 */
	@Column(name = "SUPPLIER_PAY_PLATFORM", columnDefinition = J.TYPE_NUM_COLUM)
	private Integer supplierPayPlatform;

	/**
	 * 供应商支付流水号
	 */
	@Column(name = "SUPPLIER_PAY_TRADE_NO", length = 64)
	private String supplierPayTradeNo;

	/**
	 * 供应商支付时间
	 */
	@Column(name = "SUPPLIER_PAY_TIME", columnDefinition = J.DATE_COLUM)
	private Date supplierPayTime;

	// ------------- 平台支付信息 -------------

	/**
	 * 平台订单总支付价格
	 */
	@Column(name = "TOTAL_PRICE", columnDefinition = J.MONEY_COLUM)
	private Double totalPrice;

	/**
	 * 平台支付时间
	 */
	@Column(name = "PAY_TIME", columnDefinition = J.DATE_COLUM)
	private Date payTime;

	/**
	 * 平台支付状态
	 * 
	 * @see GJJPConstants#ORDER_PAY_STATUS_MAP
	 */
	@Column(name = "PAY_STATUS", columnDefinition = J.TYPE_NUM_COLUM)
	private Integer status;

	/**
	 * 平台所得佣金 =订单下所有乘机人平台所得佣金总和
	 */
	@Column(name = "COMM_AMOUNT", columnDefinition = J.MONEY_COLUM)
	private Double commAmount;

	public Double getSupplierTotalPrice() {
		return supplierTotalPrice;
	}

	public void setSupplierTotalPrice(Double supplierTotalPrice) {
		this.supplierTotalPrice = supplierTotalPrice;
	}

	public Double getSupplierDisc() {
		return supplierDisc;
	}

	public void setSupplierDisc(Double supplierDisc) {
		this.supplierDisc = supplierDisc;
	}

	public Integer getSupplierPayPlatform() {
		return supplierPayPlatform;
	}

	public void setSupplierPayPlatform(Integer supplierPayPlatform) {
		this.supplierPayPlatform = supplierPayPlatform;
	}

	public String getSupplierPayTradeNo() {
		return supplierPayTradeNo;
	}

	public void setSupplierPayTradeNo(String supplierPayTradeNo) {
		this.supplierPayTradeNo = supplierPayTradeNo;
	}

	public Date getSupplierPayTime() {
		return supplierPayTime;
	}

	public void setSupplierPayTime(Date supplierPayTime) {
		this.supplierPayTime = supplierPayTime;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Double getCommAmount() {
		return commAmount;
	}

	public void setCommAmount(Double commAmount) {
		this.commAmount = commAmount;
	}

}
