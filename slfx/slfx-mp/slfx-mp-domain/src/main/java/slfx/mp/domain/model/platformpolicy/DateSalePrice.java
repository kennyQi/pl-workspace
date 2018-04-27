package slfx.mp.domain.model.platformpolicy;

import hg.common.component.BaseModel;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import slfx.mp.domain.model.M;
import slfx.mp.domain.model.supplierpolicy.TCSupplierPolicySnapshot;

/**
 * 经销产品价格(缓存实体)
 * 
 * @author Administrator
 */
@Entity(name="dateSalePrice_mp")
@Table(name = M.TABLE_PREFIX + "DATE_SALE_PRICE")
public class DateSalePrice extends BaseModel {
	private static final long serialVersionUID = 1L;
	/**
	 * 经销商id
	 */
	@Column(name = "DEALER_ID", length = 64)
	private String dealerId;
	/**
	 * 平台价格
	 */
	@Column(name = "SALE_PRICE", columnDefinition = M.MONEY_COLUM)
	private Double salePrice;
	/**
	 * 是否可售
	 */
	@Type(type = "yes_no")
	@Column(name = "SALE")
	private Boolean sale;
	/**
	 * 销售日期
	 */
	@Column(name = "SALE_DATE", columnDefinition = M.DATE_COLUM)
	private Date saleDate;
	/**
	 * 供应商政策id
	 */
	@Column(name = "SUPPLIER_POLICY_ID", length = 64)
	private String supplierPolicyId;
	/**
	 * 供应商政策名称
	 */
	@Column(name = "SUPPLIER_PLOLICY_NAME", length = 64)
	private String supplierPlolicyName;
	/**
	 * 票面价
	 */
	@Column(name = "ORIGINAL_PRICE", columnDefinition = M.MONEY_COLUM)
	private Double originalPrice;
	/**
	 * 供应商价格政策快照
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SUPPLIER_POLICY_SNAPSHOT_ID")
	public TCSupplierPolicySnapshot supplierPolicySnapshot;
	/**
	 * 经销商价格政策快照
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SALE_POLICY_SNAPSHOT_ID")
	public SalePolicySnapshot salePolicySnapshot;
	
	/**
	 * 库存量
	 */
	@Column(name = "STOCK", columnDefinition = M.NUM_COLUM)
	private Integer stock;

	public String getDealerId() {
		return dealerId;
	}

	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}

	public Double getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(Double salePrice) {
		this.salePrice = salePrice;
	}

	public Boolean getSale() {
		return sale;
	}

	public void setSale(Boolean sale) {
		this.sale = sale;
	}

	public Date getSaleDate() {
		return saleDate;
	}

	public void setSaleDate(Date saleDate) {
		this.saleDate = saleDate;
	}

	public String getSupplierPolicyId() {
		return supplierPolicyId;
	}

	public void setSupplierPolicyId(String supplierPolicyId) {
		this.supplierPolicyId = supplierPolicyId;
	}

	public String getSupplierPlolicyName() {
		return supplierPlolicyName;
	}

	public void setSupplierPlolicyName(String supplierPlolicyName) {
		this.supplierPlolicyName = supplierPlolicyName;
	}

	public Double getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(Double originalPrice) {
		this.originalPrice = originalPrice;
	}

	public TCSupplierPolicySnapshot getSupplierPolicySnapshot() {
		return super.getProperty(supplierPolicySnapshot, TCSupplierPolicySnapshot.class);
	}

	public void setSupplierPolicySnapshot(
			TCSupplierPolicySnapshot supplierPolicySnapshot) {
		this.supplierPolicySnapshot = supplierPolicySnapshot;
	}

	public SalePolicySnapshot getSalePolicySnapshot() {
		return super.getProperty(salePolicySnapshot, SalePolicySnapshot.class);
	}

	public void setSalePolicySnapshot(SalePolicySnapshot salePolicySnapshot) {
		this.salePolicySnapshot = salePolicySnapshot;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

}