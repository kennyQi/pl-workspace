package pay.record.domain.model.pay;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import pay.record.domain.base.M;

/**
 * 
 * @类功能说明：支付记录主表-支付记录整合（一个平台订单号一条记录）
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2015年12月21日上午11:23:10
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name=M.TABLE_PREFIX_PAYRECORD + "_MERGE")
public class PayRecordMerge extends PayRecordBaseModel{
	/**
	 * 平台支付供应商的金额
	 */
	@Column(name = "PALT_PAY_SUPPLIER_AMOUNT", columnDefinition = M.MONEY_COLUM)
	private Double paltPaysupplierAmount;
	
	/**
	 * 是否收到供应商退款
	 * "1":收到，null:未收到
	 */
	@Column(name="IS_SUPPLIER_REFUND", columnDefinition = M.CHAR_COLUM)
	private String isSupplierRefund;
	
	/**
	 * 供应商平台退款金额
	 */
	@Column(name = "SUPPLIER_REFUND_BALANCES", columnDefinition = M.MONEY_COLUM)
	private Double supplierRefundBalances;
	
	/**
	 * 是否退款给用户
	 * "1":收到，null:未收到
	 */
	@Column(name="IS_DEALER_REFUND", columnDefinition = M.CHAR_COLUM)
	private String isDealerRefund;
	
	/**
	 * 退款给用户金额
	 * 支付宝实退的金额
	 */
	@Column(name = "DEALER_REFUND_BALANCES", columnDefinition = M.MONEY_COLUM)
	private Double dealerRefundBalances;
	
	/**
	 * 最新记录时间
	 */
	@Column(name = "LAST_PAY_RECORD_DATE", columnDefinition = M.DATE_COLUM)
	private Date lastPayRecordDate;
	
	/**
	 * 商品名称
	 * "1":机票
	 * "2":线路	 
	 * */
	@Column(name="PRODUCT_TYPE", columnDefinition = M.CHAR_COLUM)
	private String productType;

	public Double getPaltPaysupplierAmount() {
		return paltPaysupplierAmount;
	}

	public void setPaltPaysupplierAmount(Double paltPaysupplierAmount) {
		this.paltPaysupplierAmount = paltPaysupplierAmount;
	}

	public String getIsSupplierRefund() {
		return isSupplierRefund;
	}

	public void setIsSupplierRefund(String isSupplierRefund) {
		this.isSupplierRefund = isSupplierRefund;
	}

	public Double getSupplierRefundBalances() {
		return supplierRefundBalances;
	}

	public void setSupplierRefundBalances(Double supplierRefundBalances) {
		this.supplierRefundBalances = supplierRefundBalances;
	}

	public String getIsDealerRefund() {
		return isDealerRefund;
	}

	public void setIsDealerRefund(String isDealerRefund) {
		this.isDealerRefund = isDealerRefund;
	}

	public Double getDealerRefundBalances() {
		return dealerRefundBalances;
	}

	public void setDealerRefundBalances(Double dealerRefundBalances) {
		this.dealerRefundBalances = dealerRefundBalances;
	}

	public Date getLastPayRecordDate() {
		return lastPayRecordDate;
	}

	public void setLastPayRecordDate(Date lastPayRecordDate) {
		this.lastPayRecordDate = lastPayRecordDate;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}
	
}
