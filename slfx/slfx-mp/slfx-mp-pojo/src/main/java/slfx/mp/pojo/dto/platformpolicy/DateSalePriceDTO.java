package slfx.mp.pojo.dto.platformpolicy;

import java.util.Date;

import slfx.mp.pojo.dto.BaseMpDTO;
import slfx.mp.pojo.dto.supplierpolicy.TCSupplierPolicySnapshotDTO;

/**
 * 经销产品价格(缓存实体)
 * 
 * @author Administrator
 */
public class DateSalePriceDTO extends BaseMpDTO {
	private static final long serialVersionUID = 1L;
	/**
	 * 经销商id
	 */
	private String dealerId;
	/**
	 * 平台价格
	 */
	private Double salePrice;
	/**
	 * 是否可售
	 */
	private Boolean sale;
	/**
	 * 销售日期
	 */
	private Date saleDate;
	/**
	 * 供应商政策id
	 */
	private String supplierPolicyId;
	/**
	 * 供应商政策名称
	 */
	private String supplierPlolicyName;
	/**
	 * 票面价
	 */
	private Double originalPrice;
	/**
	 * 供应商价格政策快照
	 */
	public TCSupplierPolicySnapshotDTO supplierPolicySnapshot;
	/**
	 * 经销商价格政策快照
	 */
	public SalePolicySnapshotDTO salePolicySnapshot;
	/**
	 * 库存量
	 */
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

	public TCSupplierPolicySnapshotDTO getSupplierPolicySnapshot() {
		return supplierPolicySnapshot;
	}

	public void setSupplierPolicySnapshot(
			TCSupplierPolicySnapshotDTO supplierPolicySnapshot) {
		this.supplierPolicySnapshot = supplierPolicySnapshot;
	}

	public SalePolicySnapshotDTO getSalePolicySnapshot() {
		return salePolicySnapshot;
	}

	public void setSalePolicySnapshot(SalePolicySnapshotDTO salePolicySnapshot) {
		this.salePolicySnapshot = salePolicySnapshot;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

}